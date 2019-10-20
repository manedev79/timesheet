package com.github.manedev79.timesheet.domain.fixtures;

import java.time.LocalDate;
import java.time.YearMonth;

public class TestFixtures {
    public static final YearMonth HACKTOBER = YearMonth.of(2018, 10);
    public static final LocalDate HACKTOBER_LAST_DAY_DATE = LocalDate.of(HACKTOBER.getYear(), HACKTOBER.getMonth(), 31);
}
