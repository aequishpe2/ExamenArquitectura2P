package com.examen.parcialdos.service;

import com.examen.parcialdos.dto.BranchDTO;
import com.examen.parcialdos.dto.BranchHolidayDTO;
import com.examen.parcialdos.mapper.BranchMapper;
import com.examen.parcialdos.model.Branch;
import com.examen.parcialdos.model.BranchHoliday;
import com.examen.parcialdos.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository repository;
    private final BranchMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(BranchService.class);

    public List<BranchDTO> getAllBranches() {
        logger.info("Obteniendo todas las sucursales");
        return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }


}
