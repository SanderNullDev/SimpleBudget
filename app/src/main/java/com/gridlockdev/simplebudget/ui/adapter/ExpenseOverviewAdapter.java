package com.gridlockdev.simplebudget.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gridlockdev.simplebudget.R;
import com.gridlockdev.simplebudget.model.DailyExpense;
import com.gridlockdev.simplebudget.utils.BudgetUtils;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Sander on 7-9-2015.
 */
public class ExpenseOverviewAdapter extends RecyclerView.Adapter<ExpenseOverviewAdapter.ViewHolder> {
    Calendar calendar = Calendar.getInstance();
    int numDays;
    private List<DailyExpense> mExpenses;
    private double mMonthlyBudget = 0;
    private double mDailyBudget;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ExpenseOverviewAdapter(List<DailyExpense> mExpenses) {
        this.mExpenses = mExpenses;
        this.mMonthlyBudget = BudgetUtils.getCurrentMonth().getMonthlyBudget();
        this.numDays = calendar.getActualMaximum(Calendar.DATE);
        this.mDailyBudget = mMonthlyBudget / numDays;
    }






    // Create new views (invoked by the layout manager)
    @Override
    public ExpenseOverviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_view, parent, false);



        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Double value = Double.valueOf(mExpenses.get(position).getSpent()) ;
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String gainString = formatter.format(value );

        holder.mSpent.setText(gainString);

        SimpleDateFormat fmt = new SimpleDateFormat("dd MMM HH:mm");
        Date date = new Date(mExpenses.get(position).getTimestamp());
        String dateString = fmt.format(date);

        holder.mTimestamp.setText(dateString);

        this.mDailyBudget -= value;
        holder.mLeft.setText(formatter.format(mDailyBudget));






    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mExpenses.size();
    }

    public void add(DailyExpense item, int position) {
        mExpenses.add(position, item);

    }

    public void remove(DailyExpense item) {
        int position = mExpenses.indexOf(item);
        mExpenses.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.expense_list_spent) TextView mSpent;
        @Bind(R.id.expense_list_left) TextView mLeft;
        @Bind(R.id.expense_list_timestamp) TextView mTimestamp;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
