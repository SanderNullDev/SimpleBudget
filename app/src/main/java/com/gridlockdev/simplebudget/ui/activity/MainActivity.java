package com.gridlockdev.simplebudget.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gridlockdev.simplebudget.R;
import com.gridlockdev.simplebudget.model.DailyExpense;
import com.gridlockdev.simplebudget.model.MonthlyExpense;
import com.gridlockdev.simplebudget.ui.adapter.ExpenseOverviewAdapter;
import com.gridlockdev.simplebudget.utils.BudgetUtils;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button addExpenseButton;
    private String value;
    private MonthlyExpense currentMonthlyExpense;

    private RecyclerView mRecyclerView;
    private ExpenseOverviewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private TextView mMonthlyBudget;
    private TextView mCurrentlySpent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMonthlyBudget = (TextView) findViewById(R.id.budget_amount_left);
        mCurrentlySpent = (TextView) findViewById(R.id.budget_amount_spent);


        setCurrentExpense();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ExpenseOverviewAdapter(currentMonthlyExpense.getExpensesOfTheDay());
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        addExpenseButton = (Button) findViewById(R.id.add_expense_button);
        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DialogFragment newFragment = new ExpenseDialog();
//                newFragment.show(getSupportFragmentManager(), "expense");

                new MaterialDialog.Builder(MainActivity.this)
                        .title("Input expense")
                        .content("Input your expense below")
                        .inputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL)
                        .input(null, null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something
                                // MainActivity.this.value = input.toString();
//                                Double spent = Double.parseDouble(input.toString());
//                                DailyExpense today = BudgetUtils.getCurrentDay();
//                                today.setSpentToday(today.getSpent() + spent);
//                                today.save();

                                Double spent = Double.parseDouble(input.toString());
                                DailyExpense spentExpense = new DailyExpense(spent);
                                spentExpense.save();

                                mAdapter.add(spentExpense, 0);
                                mAdapter.notifyItemInserted(0);
                                mRecyclerView.scrollToPosition(0);

                                mMonthlyBudget.setText("€" + (currentMonthlyExpense.getMonthlyBudget() - currentMonthlyExpense.getMonthlyExpenses()));
                                mCurrentlySpent.setText("€" + currentMonthlyExpense.getMonthlyExpenses());

                            }
                        }).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setCurrentExpense() {
        currentMonthlyExpense = BudgetUtils.getCurrentMonth();
        if (currentMonthlyExpense.getMonthlyBudget() == 0) {
            new MaterialDialog.Builder(MainActivity.this)
                    .title("Input budget")
                    .content("Input your budget below")
                    .inputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL)
                    .input(null, null, new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(MaterialDialog dialog, CharSequence input) {
                            // Do something
                            double budget = Double.parseDouble(input.toString());
                            currentMonthlyExpense.setMonthlyBudget(budget);
                            currentMonthlyExpense.save();

                            mMonthlyBudget.setText("€" + currentMonthlyExpense.getMonthlyBudget());
                            mCurrentlySpent.setText("€" + currentMonthlyExpense.getMonthlyExpenses());

                        }
                    }).show();
        } else {
            //Toast.makeText(MainActivity.this, "Budget is "+currentMonthlyExpense.getMonthlyBudget(), Toast.LENGTH_LONG).show();
            Snackbar.make(findViewById(android.R.id.content), "Budget is " + currentMonthlyExpense.getMonthlyBudget() + ", Total spent is " + currentMonthlyExpense.getMonthlyExpenses(), Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();


            mMonthlyBudget.setText("€" + (currentMonthlyExpense.getMonthlyBudget() - currentMonthlyExpense.getMonthlyExpenses()));
            mCurrentlySpent.setText("€" + currentMonthlyExpense.getMonthlyExpenses());

        }
    }

}
