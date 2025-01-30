package com.examen.parcialdos.controller;

import com.examen.parcialdos.dto.BranchDTO;
import com.examen.parcialdos.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService service;
    private static final Logger logger = LoggerFactory.getLogger(BranchController.class);

    @GetMapping
    public ResponseEntity<List<BranchDTO>> getAllBranches() {
        logger.info("Solicitud para obtener todas las sucursales");
        return ResponseEntity.ok(service.getAllBranches());
    }

    @PostMapping
    public ResponseEntity<BranchDTO> createBranch(@RequestBody BranchDTO branchDTO) {
        logger.info("Solicitud para crear una nueva sucursal");
        return ResponseEntity.ok(service.createBranch(branchDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDTO> getBranchById(@PathVariable String id) {
        logger.info("Solicitud para obtener sucursal con ID: {}", id);
        Optional<BranchDTO> branch = service.getBranchById(id);
        return branch.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
