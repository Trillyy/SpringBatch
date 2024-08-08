package com.batchexample.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StepsEnum {
    LOAD_CSV("LOAD_CSV", "Carica il file CSV in memoria"),
    SAVE_DB("SAVE_DB", "Salva il contenuto sul database");

    private final String code;
    private final String description;
}
