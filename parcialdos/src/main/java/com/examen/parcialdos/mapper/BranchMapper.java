package com.examen.parcialdos.mapper;

import com.examen.parcialdos.dto.BranchDTO;
import com.examen.parcialdos.dto.BranchHolidayDTO;
import com.examen.parcialdos.model.Branch;
import com.examen.parcialdos.model.BranchHoliday;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BranchMapper {
    BranchDTO toDTO(Branch model);
    Branch toModel(BranchDTO dto);
    
    BranchHolidayDTO toDTO(BranchHoliday model);
    BranchHoliday toModel(BranchHolidayDTO dto);
}
