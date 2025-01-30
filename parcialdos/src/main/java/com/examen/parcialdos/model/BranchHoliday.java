package com.examen.parcialdos.model;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BranchHoliday {
    private LocalDate date;
    private String name;
}
