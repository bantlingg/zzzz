package ru.kurs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kurs.entity.Worker;
import ru.kurs.entity.Quarry;
import ru.kurs.repository.WorkerRepository;
import ru.kurs.repository.QuarryRepository;

@Controller
@RequestMapping("/workers")
@RequiredArgsConstructor
@Slf4j
public class WorkerController {
    private final WorkerRepository workerRepository;
    private final QuarryRepository quarryRepository;

    @GetMapping
    public String listWorkers(Model model) {
        log.info("Accessing workers list page");
        model.addAttribute("workers", workerRepository.findAll());
        return "workers";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping("/new")
    public String newWorkerForm(Model model) {
        log.info("Accessing new worker form");
        model.addAttribute("worker", new Worker());
        model.addAttribute("quarries", quarryRepository.findAll());
        return "worker-form";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping("/new")
    public String createWorker(@ModelAttribute Worker worker) {
        log.info("Creating new worker: {} {}", worker.getFirstName(), worker.getLastName());
        workerRepository.save(worker);
        return "redirect:/workers";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping("/edit/{id}")
    public String editWorkerForm(@PathVariable Long id, Model model) {
        log.info("Accessing edit worker form for id: {}", id);
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Worker not found with id: " + id));
        model.addAttribute("worker", worker);
        model.addAttribute("quarries", quarryRepository.findAll());
        return "worker-form";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping("/edit/{id}")
    public String updateWorker(@PathVariable Long id, @ModelAttribute Worker worker) {
        log.info("Updating worker with id: {}", id);
        Worker existingWorker = workerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Worker not found with id: " + id));
        
        // Обновляем все поля существующего работника
        existingWorker.setFirstName(worker.getFirstName());
        existingWorker.setLastName(worker.getLastName());
        existingWorker.setPosition(worker.getPosition());
        existingWorker.setStatus(worker.getStatus());
        existingWorker.setQuarry(worker.getQuarry());
        
        workerRepository.save(existingWorker);
        log.info("Successfully updated worker with id: {}", id);
        return "redirect:/workers";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping("/delete/{id}")
    public String deleteWorker(@PathVariable Long id) {
        log.info("Deleting worker with id: {}", id);
        workerRepository.deleteById(id);
        return "redirect:/workers";
    }
} 