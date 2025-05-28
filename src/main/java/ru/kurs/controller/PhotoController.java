package ru.kurs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kurs.entity.Photo;
import ru.kurs.entity.Quarry;
import ru.kurs.repository.PhotoRepository;
import ru.kurs.repository.QuarryRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/photos")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoRepository photoRepository;
    private final QuarryRepository quarryRepository;
    private static final String UPLOAD_DIR = "uploads/photos/";

    @GetMapping
    public String listPhotos(Model model) {
        log.info("Accessing photos list page");
        List<Photo> photos = photoRepository.findAll();
        log.info("Found {} photos", photos.size());
        model.addAttribute("photos", photos);
        return "photos";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'USER', 'BLASTING_ENGINEER')")
    @GetMapping("/new")
    public String newPhotoForm(Model model) {
        log.info("Accessing new photo form");
        model.addAttribute("photo", new Photo());
        List<Quarry> quarries = quarryRepository.findAll();
        model.addAttribute("quarries", quarries);
        return "photo-form";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'USER', 'BLASTING_ENGINEER')")
    @PostMapping
    public String createPhoto(@ModelAttribute Photo photo, @RequestParam("file") MultipartFile file) {
        log.info("Creating new photo: {}", photo);
        try {
            // Создаем директорию, если она не существует
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Генерируем уникальное имя файла
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            // Сохраняем файл
            Files.copy(file.getInputStream(), filePath);

            // Устанавливаем путь к файлу в объекте Photo
            photo.setFilePath("/" + UPLOAD_DIR + fileName);
            photo.setUploadDate(LocalDateTime.now());

            photoRepository.save(photo);
            log.info("Successfully created photo with id: {}", photo.getId());
            return "redirect:/photos";
        } catch (IOException e) {
            log.error("Error saving photo file", e);
            throw new RuntimeException("Could not save photo file", e);
        } catch (Exception e) {
            log.error("Error creating photo", e);
            throw e;
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping("/edit/{id}")
    public String editPhotoForm(@PathVariable Long id, Model model) {
        log.info("Accessing edit photo form for id: {}", id);
        Photo photo = photoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Photo not found with id: " + id));
        List<Quarry> quarries = quarryRepository.findAll();
        model.addAttribute("photo", photo);
        model.addAttribute("quarries", quarries);
        return "photo-form";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping("/edit/{id}")
    public String updatePhoto(@PathVariable Long id, @ModelAttribute Photo photo, @RequestParam(value = "file", required = false) MultipartFile file) {
        log.info("Updating photo with id: {}", id);
        Photo existingPhoto = photoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Photo not found with id: " + id));

        try {
            // Обновляем только если загружен новый файл
            if (file != null && !file.isEmpty()) {
                // Создаем директорию, если она не существует
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Генерируем уникальное имя файла
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);

                // Сохраняем новый файл
                Files.copy(file.getInputStream(), filePath);

                // Удаляем старый файл, если он существует
                if (existingPhoto.getFilePath() != null) {
                    Path oldFilePath = Paths.get(existingPhoto.getFilePath().substring(1)); // Убираем начальный слеш
                    if (Files.exists(oldFilePath)) {
                        Files.delete(oldFilePath);
                    }
                }

                // Устанавливаем новый путь к файлу
                photo.setFilePath("/" + UPLOAD_DIR + fileName);
            } else {
                // Если файл не загружен, сохраняем старый путь
                photo.setFilePath(existingPhoto.getFilePath());
            }

            // Сохраняем остальные поля
            photo.setId(id);
            photo.setUploadDate(existingPhoto.getUploadDate());
            photoRepository.save(photo);
            log.info("Successfully updated photo with id: {}", id);
            return "redirect:/photos";
        } catch (IOException e) {
            log.error("Error updating photo file", e);
            throw new RuntimeException("Could not update photo file", e);
        } catch (Exception e) {
            log.error("Error updating photo", e);
            throw e;
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping("/delete/{id}")
    public String deletePhoto(@PathVariable Long id) {
        log.info("Attempting to delete photo with id: {}", id);
        try {
            // Проверяем существование фотографии
            Photo photo = photoRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Photo not found with id: {}", id);
                        return new RuntimeException("Photo not found with id: " + id);
                    });
            
            log.info("Found photo: id={}, title={}, filePath={}", 
                    photo.getId(), photo.getTitle(), photo.getFilePath());
            
            // Удаляем физический файл
            if (photo.getFilePath() != null) {
                String filePathStr = photo.getFilePath().substring(1); // Убираем начальный слеш
                log.info("Attempting to delete physical file: {}", filePathStr);
                
                Path filePath = Paths.get(filePathStr);
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                    log.info("Successfully deleted physical file: {}", filePathStr);
                } else {
                    log.warn("Physical file not found: {}", filePathStr);
                }
            } else {
                log.warn("No file path specified for photo id: {}", id);
            }
            
            // Удаляем запись из базы данных
            photoRepository.deleteById(id);
            log.info("Successfully deleted photo record from database with id: {}", id);
            return "redirect:/photos";
        } catch (IOException e) {
            log.error("Error deleting photo file for id {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Could not delete photo file: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Error deleting photo with id {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error deleting photo: " + e.getMessage(), e);
        }
    }
} 