package com.example.m_expense.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.m_expense.Activities.MainActivity;
import com.example.m_expense.Adapters.HistoryListAdapter;
import com.example.m_expense.Elements.TripExpense;
import com.example.m_expense.R;

import java.util.List;

public class History extends Fragment {
  MainActivity activity = MainActivity.getInstance();
  HistoryListAdapter historyListAdapter;
  List<TripExpense> expenses = activity.tripDb.getAllExpenses();

  public History() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    historyListAdapter = new HistoryListAdapter(activity, expenses);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_history, container, false);

    Toolbar toolbar = rootView.findViewById(R.id.expenseHistoryToolbar);
    activity.setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        goHome();
      }
    });

    RecyclerView recyclerView = rootView.findViewById(R.id.expenseHistoryList);
    recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
    recyclerView.setAdapter(historyListAdapter);

    return rootView;
  }

  public void goHome() {
    Fragment fragment = getParentFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
    if (fragment != null) {
      NavHostFragment.findNavController(fragment).navigate(R.id.action_history_to_HomeFragment);
    }
  }
}