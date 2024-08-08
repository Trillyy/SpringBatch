package com.batchexample.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JobsEnum {
    CSV_TO_DB_CHUNK("CSV_TO_DB_CHUNK", "Carica il file CSV sul database a chunk");

    private final String code;
    private final String description;
}
