package org.gmalliaris.m222proj2gmalliaris.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.gmalliaris.m222proj2gmalliaris.validation.ValidDate;

public class DateTimeRange {

    @ValidDate(message = "Invalid date in format yyyy-MM-dd hh:mm:ss")
    private final String rangeStart;
    @ValidDate(message = "Invalid date in format yyyy-MM-dd hh:mm:ss")
    private final String rangeEnd;

    @JsonCreator
    public DateTimeRange(String rangeStart, String rangeEnd) {
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    public String getRangeStart() {
        return rangeStart;
    }

    public String getRangeEnd() {
        return rangeEnd;
    }
}
