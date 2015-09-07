package com.gridlockdev.simplebudget.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gridlockdev.simplebudget.R;
import com.gridlockdev.simplebudget.model.DailyExpense;
import com.gridlockdev.simplebudget.model.MonthlyExpense;
import com.gridlockdev.simplebudget.ui.dialogs.ExpenseDialog;
import com.gridlockdev.simplebudget.utils.BudgetUtils;

public class MainActivity extends AppCompatActivity    implements ExpenseDialog.NoticeDialogListener{

    private Toolbar mToolbar;
    private Button addExpenseButton;
    private String value;
    private MonthlyExpense currentMonthlyExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        currentMonthlyExpense = BudgetUtils.getCurrentMonth();
        if(currentMonthlyExpense.getMonthlyBudget() == 0){
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
                        }
                    }).show();
        }else{
            //Toast.makeText(MainActivity.this, "Budget is "+currentMonthlyExpense.getMonthlyBudget(), Toast.LENGTH_LONG).show();
            Snackbar.make(findViewById(android.R.id.content), "Budget is "+currentMonthlyExpense.getMonthlyBudget()+", Total spent is "+currentMonthlyExpense.getMonthlyExpenses(), Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();

        }

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
                                Double spent = Double.parseDouble(input.toString());
                                DailyExpense today = BudgetUtils.getCurrentDay();
                                today.setSpentToday(today.getSpentToday() + spent);
                                today.save();

                                currentMonthlyExpense.addExpense(today);
                                currentMonthlyExpense.save();

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




    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        ExpenseDialog dia = (ExpenseDialog) dialog;
        Toast.makeText(this, "Input was "+dia.getExpenseValue(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
