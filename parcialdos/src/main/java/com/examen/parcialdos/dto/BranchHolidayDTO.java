package com.examen.parcialdos.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BranchHolidayDTO {
    private LocalDate date;
    private String name;
}
