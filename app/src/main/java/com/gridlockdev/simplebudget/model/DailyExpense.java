package com.gridlockdev.simplebudget.model;

import com.gridlockdev.simplebudget.utils.BudgetUtils;
import com.orm.SugarRecord;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Sander on 6-9-2015.
 */
public class DailyExpense extends SugarRecord<DailyExpense> {

    private double spentToday;
    private String day;
    private String month;
    private long timestamp;
    private double budgetLeft;

    public DailyExpense() {

    }

    public DailyExpense(double spent) {
        this.spentToday = spent;
        day = BudgetUtils.getDayString();
        month = BudgetUtils.getMonthString();
        timestamp = System.currentTimeMillis();
        budgetLeft = getDailyBudgeLeft();
    }

    public DailyExpense(boolean neww){
        setupExpense();
    }

    private void setupExpense(){
        day = BudgetUtils.getDayString();
        month = BudgetUtils.getMonthString();
        spentToday = 0;
        timestamp = System.currentTimeMillis();
        budgetLeft = getDailyBudgeLeft();
    }

    public double getBudgetLeft() {
        return budgetLeft;
    }

    public void setBudgetLeft(double budgetLeft) {
        this.budgetLeft = budgetLeft;
    }

    private double getDailyBudgeLeft() {
        Calendar calendar = Calendar.getInstance();
        List<DailyExpense> expenses = BudgetUtils.getCurrentDay();
        double mMonthlyBudget = 0;
        double mDailyBudget;
        mMonthlyBudget = BudgetUtils.getCurrentMonth().getMonthlyBudget();
        int numDays = calendar.getActualMaximum(Calendar.DATE);
        mDailyBudget = mMonthlyBudget / numDays;

        for (DailyExpense exp : expenses) {
            mDailyBudget -= exp.getSpent();
        }
        mDailyBudget -= this.spentToday;
        return mDailyBudget;

    }

    public long getTimestamp() {
        return timestamp;
    }

    public double getSpent() {
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
