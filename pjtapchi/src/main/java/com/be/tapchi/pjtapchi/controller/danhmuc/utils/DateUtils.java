package com.be.tapchi.pjtapchi.controller.danhmuc.utils;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class DateUtils {
    public static LocalDate getStartOfWeek() {
        return LocalDate.now()
                .with(TemporalAdjusters.previousOrSame(WeekFields.of(Locale.getDefault()).getFirstDayOfWeek()));
    }

    public static LocalDate getEndOfWeek() {
        return getStartOfWeek().plusDays(6);
    }
}
