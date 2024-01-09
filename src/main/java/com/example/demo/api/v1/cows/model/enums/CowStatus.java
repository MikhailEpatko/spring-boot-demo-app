package com.example.demo.api.v1.cows.model.enums;

public enum CowStatus {
    OK("Здоровая, доится"),
    REST("В запуске"),
    TREATMENT("На лечении"),
    DEAD("Умерла"),
    SOLD("Продана");

    CowStatus(String description) {
    }
}
