package com.examen.parcialdos.service;

import com.examen.parcialdos.dto.BranchDTO;
import com.examen.parcialdos.dto.BranchHolidayDTO;
import com.examen.parcialdos.mapper.BranchMapper;
import com.examen.parcialdos.model.Branch;
import com.examen.parcialdos.model.BranchHoliday;
import com.examen.parcialdos.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j 
@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository repository;
    private final BranchMapper mapper;

    public List<BranchDTO> getAllBranches() {
        log.info("Obteniendo todas las sucursales");
        return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public BranchDTO createBranch(BranchDTO branchDTO) {
        Branch branch = mapper.toModel(branchDTO);
        branch.setCreationDate(LocalDateTime.now());
        branch.setLastModifiedDate(LocalDateTime.now());
        branch.setState("ACTIVE");
        
        log.info("Creando nueva sucursal con email: {}", branch.getEmail());
        
        return mapper.toDTO(repository.save(branch));
    }

    public Optional<BranchDTO> getBranchById(String id) {
        log.info("Obteniendo sucursal con ID: {}", id);
        return repository.findById(id).map(mapper::toDTO);
    }

    public BranchDTO updatePhoneNumber(String id, String newPhoneNumber) {
        Branch branch = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Sucursal con ID {} no encontrada", id);
                    return new RuntimeException("Sucursal no encontrada");
                });

        branch.setPhoneNumber(newPhoneNumber);
        branch.setLastModifiedDate(LocalDateTime.now());
        log.info("Número de teléfono actualizado para la sucursal con ID: {}", id);
        return mapper.toDTO(repository.save(branch));
    }

    public BranchDTO addBranchHoliday(String id, BranchHolidayDTO holidayDTO) {
        Branch branch = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Sucursal con ID {} no encontrada", id);
                    return new RuntimeException("Sucursal no encontrada");
                });

        BranchHoliday holiday = mapper.toModel(holidayDTO);
        branch.getBranchHolidays().add(holiday);
        log.info("Añadiendo feriado '{}' a la sucursal con ID: {}", holiday.getName(), id);
        return mapper.toDTO(repository.save(branch));
    }

    public void removeBranchHoliday(String id, LocalDate date) {
        Branch branch = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Sucursal con ID {} no encontrada", id);
                    return new RuntimeException("Sucursal no encontrada");
                });

        boolean removed = branch.getBranchHolidays().removeIf(h -> h.getDate().equals(date));
        if (removed) {
            log.info("Eliminando feriado {} de la sucursal con ID: {}", date, id);
            repository.save(branch);
        } else {
            log.warn("No se encontró el feriado {} en la sucursal con ID: {}", date, id);
            throw new RuntimeException("Feriado no encontrado en la sucursal");
        }
    }

    public List<BranchHolidayDTO> getBranchHolidays(String id) {
        Branch branch = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Sucursal con ID {} no encontrada", id);
                    return new RuntimeException("Sucursal no encontrada");
                });

        log.info("Obteniendo feriados de la sucursal con ID: {}", id);
        return branch.getBranchHolidays().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public boolean isHoliday(String id, LocalDate date) {
        Branch branch = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Sucursal con ID {} no encontrada", id);
                    return new RuntimeException("Sucursal no encontrada");
                });

        boolean isHoliday = branch.getBranchHolidays().stream().anyMatch(h -> h.getDate().equals(date));
        log.info("¿La fecha {} es feriado en la sucursal con ID {}?: {}", date, id, isHoliday);
        return isHoliday;
    }
}
