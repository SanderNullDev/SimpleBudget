package com.gridlockdev.simplebudget.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gridlockdev.simplebudget.R;
import com.gridlockdev.simplebudget.model.DailyExpense;
import com.gridlockdev.simplebudget.model.MonthlyExpense;
import com.gridlockdev.simplebudget.ui.adapter.ExpenseOverviewAdapter;
import com.gridlockdev.simplebudget.utils.BudgetUtils;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button addExpenseButton;
    private String value;
    private MonthlyExpense currentMonthlyExpense;

    private RecyclerView mRecyclerView;
    private ExpenseOverviewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private TextView mMonthlyBudget;
    private TextView mCurrentlySpent;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mMonthlyBudget = (TextView) getActivity().findViewById(R.id.budget_amount_left);
        mCurrentlySpent = (TextView) getActivity().findViewById(R.id.budget_amount_spent);


        setCurrentExpense();

      //  mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        //mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ExpenseOverviewAdapter(currentMonthlyExpense.getExpensesOfTheDay());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        int position = mAdapter.getItemCount();
        mRecyclerView.scrollToPosition(position-1);


        addExpenseButton = (Button) getActivity().findViewById(R.id.add_expense_button);
        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DialogFragment newFragment = new ExpenseDialog();
//                newFragment.show(getSupportFragmentManager(), "expense");

                new MaterialDialog.Builder(getActivity())
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

                                mAdapter.add(spentExpense);
                                int position = mAdapter.getItemCount();
                                mAdapter.notifyItemInserted(position);
                                mRecyclerView.scrollToPosition(position-1);

                                mMonthlyBudget.setText("€" + (currentMonthlyExpense.getMonthlyBudget() - currentMonthlyExpense.getMonthlyExpenses()));
                                mCurrentlySpent.setText("€" + currentMonthlyExpense.getMonthlyExpenses());

                            }
                        }).show();
            }
        });
    }

    private void setCurrentExpense() {
        currentMonthlyExpense = BudgetUtils.getCurrentMonth();
        if (currentMonthlyExpense.getMonthlyBudget() == 0) {
            new MaterialDialog.Builder(getActivity())
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
//            Snackbar.make(findViewById(android.R.id.content), "Budget is " + currentMonthlyExpense.getMonthlyBudget() + ", Total spent is " + currentMonthlyExpense.getMonthlyExpenses(), Snackbar.LENGTH_LONG)
//                    .setActionTextColor(Color.RED)
//                    .show();


            mMonthlyBudget.setText("€" + (currentMonthlyExpense.getMonthlyBudget() - currentMonthlyExpense.getMonthlyExpenses()));
            mCurrentlySpent.setText("€" + currentMonthlyExpense.getMonthlyExpenses());

        }
    }
}
