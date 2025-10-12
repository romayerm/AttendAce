package com.attendace.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AttendanceStatus {
    PRESENT("Present"),
    ABSENT("Absent");

    private final String dbValue;

    AttendanceStatus(String dbValue) { this.dbValue = dbValue; }

    @JsonValue
    public String getDbValue() { return dbValue; }

    @JsonCreator
    public static AttendanceStatus fromString(String value) {
        if (value == null) return null;
        for (AttendanceStatus s : AttendanceStatus.values()) {
            if (s.dbValue.equalsIgnoreCase(value) || s.name().equalsIgnoreCase(value)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown AttendanceStatus: " + value);
    }

    @Override
    public String toString() { return dbValue; }
}