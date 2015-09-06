package com.gridlockdev.simplebudget.model;

import com.gridlockdev.simplebudget.utils.BudgetDateUtils;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sander on 6-9-2015.
 */
public class MonthlyExpense extends SugarRecord<MonthlyExpense> {

    private List<DailyExpense> expenseList;
    private double monthlyBudget;
    private String month;


    public MonthlyExpense() {
        month = BudgetDateUtils.getMonth();
        expenseList = new ArrayList<DailyExpense>();
    }

    public MonthlyExpense(double budget) {
        month = BudgetDateUtils.getMonth();
        expenseList = new ArrayList<DailyExpense>();
        this.monthlyBudget = budget;
    }

    public List<DailyExpense> getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(List<DailyExpense> expenseList) {
        this.expenseList = expenseList;
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
}
