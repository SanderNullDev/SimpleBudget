package com.gridlockdev.simplebudget.model;

import com.orm.SugarRecord;

/**
 * Created by Sander on 6-9-2015.
 */
public class DailyExpense extends SugarRecord<DailyExpense>{

    private double spentToday;

    public DailyExpense(){}
    public DailyExpense(double spent){
        this.spentToday = spent;
    }

    public double getSpentToday() {
        return spentToday;
    }

    public void setSpentToday(double spentToday) {
        this.spentToday = spentToday;
    }
}
