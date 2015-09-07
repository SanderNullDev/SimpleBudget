package com.gridlockdev.simplebudget.model;

import com.gridlockdev.simplebudget.utils.BudgetUtils;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Sander on 6-9-2015.
 */
public class MonthlyExpense extends SugarRecord<MonthlyExpense> {

    @Ignore
    private HashMap<String, DailyExpense> expenseList;
    @Ignore
    private List<DailyExpense> expenses;
    private double monthlyBudget;
    private String month;


    public MonthlyExpense() {
        month = BudgetUtils.getMonthString();
        expenseList = new HashMap<String, DailyExpense>();
    }

    public MonthlyExpense(double budget) {
        month = BudgetUtils.getMonthString();
        expenseList = new HashMap<String, DailyExpense>();
        this.monthlyBudget = budget;
    }

    public HashMap<String, DailyExpense> getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(HashMap<String, DailyExpense> expenseList) {
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

    public void addExpense(DailyExpense expense){
        this.expenseList.put(expense.getDay(), expense);
    }

    public double getMonthlyExpenses(){
        double expense = 0;

        List<DailyExpense> currentMonth = DailyExpense.find(DailyExpense.class, "month =?", month);

        for(DailyExpense exp : currentMonth){
            expense += exp.getSpentToday();
        }

        return expense;
    }
}
