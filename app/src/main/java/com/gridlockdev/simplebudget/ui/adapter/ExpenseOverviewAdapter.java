package com.gridlockdev.simplebudget.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gridlockdev.simplebudget.R;
import com.gridlockdev.simplebudget.model.DailyExpense;

import java.util.List;

/**
 * Created by Sander on 7-9-2015.
 */
public class ExpenseOverviewAdapter extends RecyclerView.Adapter<ExpenseOverviewAdapter.ViewHolder> {
    private List<DailyExpense> mExpenses;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ExpenseOverviewAdapter(List<DailyExpense> mExpenses) {
        this.mExpenses = mExpenses;
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
        holder.mTextView.setText("Spent today: "+mExpenses.get(position).getSpentToday());

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
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.budget_view_expense);
        }
    }
}
