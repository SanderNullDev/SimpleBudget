package com.gridlockdev.simplebudget.model;

import com.gridlockdev.simplebudget.utils.BudgetUtils;
import com.orm.SugarRecord;

/**
 * Created by Sander on 6-9-2015.
 */
public class DailyExpense extends SugarRecord<DailyExpense>{

    private double spentToday;
    private String day;
    private String month;

    public DailyExpense(){
        day = BudgetUtils.getDayString();
        month = BudgetUtils.getMonthString();
        spentToday = 0;
    }
    public DailyExpense(double spent){
        this.spentToday = spent;
        day = BudgetUtils.getDayString();
        month = BudgetUtils.getMonthString();
    }

    public double getSpentToday() {
        return spentToday;
    }

    public void setSpentToday(double spentToday) {
        this.spentToday = spentToday;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
