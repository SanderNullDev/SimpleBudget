package com.gridlockdev.simplebudget.utils;

import com.gridlockdev.simplebudget.model.DailyExpense;
import com.gridlockdev.simplebudget.model.MonthlyExpense;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Sander on 6-9-2015.
 */
public class BudgetUtils {

    /**
     * Retrieve the current month in monthyear format
     * @return Current month in monthyear format
     */
    public static String getMonthString() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        String fMonth = month + "" + year;
        return fMonth;
    }


    /**
     * Retrieve the current month in monthyear format
     * @return Current month in monthyear format
     */
    public static String getDayString() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String fMonth = day+""+ month + "" + year;
        return fMonth;
    }


    public static MonthlyExpense getCurrentMonth(){
        String[] args = {BudgetUtils.getMonthString()};
        try {
            List<MonthlyExpense> expenses = MonthlyExpense.find(MonthlyExpense.class, "month = ?", args, null, null, "1");

            if (expenses != null && expenses.size() > 0) {
                return expenses.get(0);
            } else {
                MonthlyExpense expense = new MonthlyExpense();
                //expense.save();
                return expense;
            }
        }catch(Exception e){
            MonthlyExpense expense = new MonthlyExpense();
            //expense.save();
            return expense;
        }

    }
    public static  List<DailyExpense> getCurrentDay(){
        String[] args = {BudgetUtils.getDayString()};
        List<DailyExpense> expenses = DailyExpense.find(DailyExpense.class,"day = ?",args,null,null, null);

        if(expenses != null && expenses.size() > 0){
            return expenses;
        }else{
           expenses = new ArrayList<DailyExpense>();
           return expenses;
        }

    }

    public static MonthlyExpense getExpensesByMonth(String month){
        String[] args = {month};
        List<MonthlyExpense> expenses = MonthlyExpense.find(MonthlyExpense.class, "month = ?", args, null, null, "1");

        if(expenses != null && expenses.size() > 0){
            return expenses.get(0);
        }else{
            MonthlyExpense expense = new MonthlyExpense();
            expense.save();
            return expense;
        }

    }



}
