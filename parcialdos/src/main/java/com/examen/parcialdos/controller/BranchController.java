package com.examen.parcialdos.controller;

import com.examen.parcialdos.dto.BranchDTO;
import com.examen.parcialdos.dto.BranchHolidayDTO;
import com.examen.parcialdos.service.BranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/v1/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService service;

    @GetMapping
    public ResponseEntity<List<BranchDTO>> getAllBranches() {
        log.info("Solicitud para obtener todas las sucursales");
        return ResponseEntity.ok(service.getAllBranches());
    }

    @PostMapping
    public ResponseEntity<BranchDTO> createBranch(@RequestBody BranchDTO branchDTO) {
        log.info("Solicitud para crear una nueva sucursal con email: {}", branchDTO.getEmail());
        return ResponseEntity.ok(service.createBranch(branchDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDTO> getBranchById(@PathVariable String id) {
        log.info("Solicitud para obtener sucursal con ID: {}", id);
        Optional<BranchDTO> branch = service.getBranchById(id);
        return branch.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Sucursal con ID {} no encontrada", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}/phone")
    public ResponseEntity<BranchDTO> updatePhoneNumber(@PathVariable String id, @RequestParam String phoneNumber) {
        log.info("Solicitud para actualizar número de teléfono de la sucursal con ID: {}", id);
        return ResponseEntity.ok(service.updatePhoneNumber(id, phoneNumber));
    }

    @PostMapping("/{id}/holidays")
    public ResponseEntity<BranchDTO> addBranchHoliday(@PathVariable String id, @RequestBody BranchHolidayDTO holidayDTO) {
        log.info("Solicitud para agregar feriado '{}' a la sucursal con ID: {}", holidayDTO.getName(), id);
        return ResponseEntity.ok(service.addBranchHoliday(id, holidayDTO));
    }

    @DeleteMapping("/{id}/holidays")
    public ResponseEntity<Void> removeBranchHoliday(@PathVariable String id, @RequestParam LocalDate date) {
        log.info("Solicitud para eliminar feriado {} de la sucursal con ID: {}", date, id);
        service.removeBranchHoliday(id, date);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/holidays")
    public ResponseEntity<List<BranchHolidayDTO>> getBranchHolidays(@PathVariable String id) {
        log.info("Solicitud para obtener feriados de la sucursal con ID: {}", id);
        return ResponseEntity.ok(service.getBranchHolidays(id));
    }

    @GetMapping("/{id}/isHoliday")
    public ResponseEntity<Boolean> isHoliday(@PathVariable String id, @RequestParam LocalDate date) {
        log.info("Solicitud para verificar si la fecha {} es feriado en la sucursal con ID: {}", date, id);
        return ResponseEntity.ok(service.isHoliday(id, date));
    }
}
