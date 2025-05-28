package ru.kurs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kurs.entity.SafetyInspection;
import ru.kurs.entity.Quarry;
import ru.kurs.entity.Worker;
import ru.kurs.repository.SafetyInspectionRepository;
import ru.kurs.repository.QuarryRepository;
import ru.kurs.repository.WorkerRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/safety-inspections")
@RequiredArgsConstructor
public class SafetyInspectionController {

    private final SafetyInspectionRepository inspectionRepository;
    private final QuarryRepository quarryRepository;
    private final WorkerRepository workerRepository;

    @GetMapping
    public String listInspections(Model model) {
        log.info("Accessing safety inspections list page");
        List<SafetyInspection> inspections = inspectionRepository.findAll();
        log.info("Found {} inspections", inspections.size());
        model.addAttribute("inspections", inspections);
        return "safety-inspections";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping("/new")
    public String newInspectionForm(Model model) {
        log.info("Accessing new safety inspection form");
        model.addAttribute("inspection", new SafetyInspection());
        List<Quarry> quarries = quarryRepository.findAll();
        List<Worker> inspectors = workerRepository.findAll();
        log.info("Found {} quarries and {} inspectors", quarries.size(), inspectors.size());
        model.addAttribute("quarries", quarries);
        model.addAttribute("inspectors", inspectors);
        return "safety-inspection-form";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping
    public String createInspection(@ModelAttribute SafetyInspection inspection) {
        log.info("Creating new safety inspection: {}", inspection);
        try {
            inspection.setInspectionDate(LocalDateTime.now());
            inspectionRepository.save(inspection);
            log.info("Successfully created inspection with id: {}", inspection.getId());
            return "redirect:/safety-inspections";
        } catch (Exception e) {
            log.error("Error creating inspection", e);
            throw e;
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping("/edit/{id}")
    public String editInspectionForm(@PathVariable Long id, Model model) {
        log.info("Accessing edit safety inspection form for id: {}", id);
        try {
            SafetyInspection inspection = inspectionRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Inspection not found with id: {}", id);
                        return new RuntimeException("Inspection not found with id: " + id);
                    });
            log.info("Found inspection: {}", inspection);
            
            List<Quarry> quarries = quarryRepository.findAll();
            List<Worker> inspectors = workerRepository.findAll();
            log.info("Found {} quarries and {} inspectors", quarries.size(), inspectors.size());
            
            model.addAttribute("inspection", inspection);
            model.addAttribute("quarries", quarries);
            model.addAttribute("inspectors", inspectors);
            
            log.info("Successfully prepared model for edit form");
            return "safety-inspection-form";
        } catch (Exception e) {
            log.error("Error accessing edit form for inspection id: {} - {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping("/edit/{id}")
    public String updateInspection(@PathVariable Long id, @ModelAttribute SafetyInspection inspection) {
        log.info("Updating safety inspection with id: {}", id);
        try {
            SafetyInspection existingInspection = inspectionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Inspection not found with id: " + id));
            
            inspection.setId(id);
            inspection.setInspectionDate(existingInspection.getInspectionDate());
            inspectionRepository.save(inspection);
            log.info("Successfully updated inspection with id: {}", id);
            return "redirect:/safety-inspections";
        } catch (Exception e) {
            log.error("Error updating inspection with id: " + id, e);
            throw e;
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping("/delete/{id}")
    public String deleteInspection(@PathVariable Long id) {
        log.info("Deleting safety inspection with id: {}", id);
        try {
            inspectionRepository.deleteById(id);
            log.info("Successfully deleted inspection with id: {}", id);
            return "redirect:/safety-inspections";
        } catch (Exception e) {
            log.error("Error deleting inspection with id: " + id, e);
            throw e;
        }
    }
} 