package org.gmalliaris.m222proj2gmalliaris.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.gmalliaris.m222proj2gmalliaris.validation.ValidDate;

public class SpecificDate {

    @ValidDate
    private final String date;

    @JsonCreator
    public SpecificDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
