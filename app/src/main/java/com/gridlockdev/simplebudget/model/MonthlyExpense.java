package com.gridlockdev.simplebudget.model;

import com.gridlockdev.simplebudget.utils.BudgetUtils;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.List;

/**
 * Created by Sander on 6-9-2015.
 */
public class MonthlyExpense extends SugarRecord<MonthlyExpense> {

    @Ignore
    private List<DailyExpense> expenses;
    private double monthlyBudget;
    private String month;


    public MonthlyExpense() {
        month = BudgetUtils.getMonthString();
    }

    public MonthlyExpense(double budget) {
        month = BudgetUtils.getMonthString();
        this.monthlyBudget = budget;
    }


    public double getMonthlyBudget() {
        return monthlyBudget;
    }

    public void setMonthlyBudget(double monthlyBudget) {
        this.monthlyBudget = monthlyBudget;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getMonthlyExpenses() {
        double expense = 0;

        List<DailyExpense> currentMonth = DailyExpense.find(DailyExpense.class, "month =?", month);

        for (DailyExpense exp : currentMonth) {
            expense += exp.getSpent();
        }

        return expense;
    }

    public List<DailyExpense> getMonthlyExpensesList() {
        String[] args = {month};
        List<DailyExpense> expenses = DailyExpense.find(DailyExpense.class, "month = ?", args,null,"timestamp desc",null);
        return expenses;
    }

    public List<DailyExpense> getExpensesOfTheDay(){
        String day = BudgetUtils.getDayString();
        String[] args = {day};
        List<DailyExpense> expenses = DailyExpense.find(DailyExpense.class, "day = ?", args, null, "timestamp asc", null);
        return expenses;
    }

    public double getDailyBudgetLeft(){
        List<DailyExpense> exp = getExpensesOfTheDay();
        double expense = 0;
        for(DailyExpense ex : exp){
            expense += ex.getSpent();
        }
        return getDailyBudgetLeft();
    }

    public double getMonthlyBudgetLeft(){
        return getMonthlyBudget() - getMonthlyExpenses();
    }
}
