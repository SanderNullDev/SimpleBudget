package com.gridlockdev.simplebudget.utils;

import java.util.Calendar;

/**
 * Created by Sander on 6-9-2015.
 */
public class BudgetDateUtils {

    /**
     * Retrieve the current month in monthyear format
     * @return Current month in monthyear format
     */
    public static String getMonth() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        String fMonth = month + "" + year;
        return fMonth;
    }
}
