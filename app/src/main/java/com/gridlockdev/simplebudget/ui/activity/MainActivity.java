package com.gridlockdev.simplebudget.ui.activity;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gridlockdev.simplebudget.R;
import com.gridlockdev.simplebudget.ui.dialogs.ExpenseDialog;

public class MainActivity extends AppCompatActivity    implements ExpenseDialog.NoticeDialogListener{

    private Toolbar mToolbar;
    private Button addExpenseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        addExpenseButton = (Button) findViewById(R.id.add_expense_button);
        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new ExpenseDialog();
                newFragment.show(getSupportFragmentManager(), "expense");
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
