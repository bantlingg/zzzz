package ru.kurs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kurs.entity.Explosion;
import ru.kurs.entity.Quarry;
import ru.kurs.repository.ExplosionRepository;
import ru.kurs.repository.QuarryRepository;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/explosions")
@RequiredArgsConstructor
public class ExplosionController {

    private final ExplosionRepository explosionRepository;
    private final QuarryRepository quarryRepository;

    @GetMapping
    public String listExplosions(Model model) {
        log.info("Accessing explosions list page");
        List<Explosion> explosions = explosionRepository.findAll();
        log.info("Found {} explosions", explosions.size());
        model.addAttribute("explosions", explosions);
        return "explosions";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'BLASTING_ENGINEER')")
    @GetMapping("/new")
    public String newExplosionForm(Model model) {
        log.info("Accessing new explosion form");
        model.addAttribute("explosion", new Explosion());
        List<Quarry> quarries = quarryRepository.findAll();
        model.addAttribute("quarries", quarries);
        return "explosion-form";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'BLASTING_ENGINEER')")
    @PostMapping("/new")
    public String createExplosion(@ModelAttribute Explosion explosion) {
        log.info("Creating new explosion: {}", explosion);
        try {
            explosionRepository.save(explosion);
            log.info("Successfully created explosion with id: {}", explosion.getId());
            return "redirect:/explosions";
        } catch (Exception e) {
            log.error("Error creating explosion", e);
            throw e;
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'BLASTING_ENGINEER')")
    @GetMapping("/edit/{id}")
    public String editExplosionForm(@PathVariable Long id, Model model) {
        log.info("Accessing edit explosion form for id: {}", id);
        Explosion explosion = explosionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Explosion not found with id: " + id));
        model.addAttribute("explosion", explosion);
        model.addAttribute("quarries", quarryRepository.findAll());
        return "explosion-form";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'BLASTING_ENGINEER')")
    @PostMapping("/edit/{id}")
    public String updateExplosion(@PathVariable Long id, @ModelAttribute Explosion explosion) {
        log.info("Updating explosion with id: {}", id);
        Explosion existingExplosion = explosionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Explosion not found with id: " + id));
        
        // Обновляем все поля существующего взрыва
        existingExplosion.setDateTime(explosion.getDateTime());
        existingExplosion.setLocation(explosion.getLocation());
        existingExplosion.setPower(explosion.getPower());
        existingExplosion.setStatus(explosion.getStatus());
        existingExplosion.setQuarry(explosion.getQuarry());
        
        explosionRepository.save(existingExplosion);
        log.info("Successfully updated explosion with id: {}", id);
        return "redirect:/explosions";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping("/delete/{id}")
    public String deleteExplosion(@PathVariable Long id) {
        log.info("Deleting explosion with id: {}", id);
        explosionRepository.deleteById(id);
        return "redirect:/explosions";
    }
} 