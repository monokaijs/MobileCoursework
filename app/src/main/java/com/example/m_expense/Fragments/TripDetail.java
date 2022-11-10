package com.example.m_expense.Fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.m_expense.Activities.MainActivity;
import com.example.m_expense.Dialogs.AddExpenseDialog;
import com.example.m_expense.Elements.Trip;
import com.example.m_expense.R;
import com.example.m_expense.RecyclerTouchListener;
import com.example.m_expense.Adapters.TripExpensesListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TripDetail extends Fragment {
  MainActivity activity = MainActivity.getInstance();
  public static TripDetail instance;
  public Trip currentTrip = activity.tripDb.getTrip(MainActivity.currentTrip.id);
  public TripExpensesListAdapter tripExpensesListAdapter = new TripExpensesListAdapter(activity.getBaseContext(), activity.tripDb.getAllTripExpenses(currentTrip.id));
  public static AddExpenseDialog addExpenseDialog = AddExpenseDialog.newInstance("Add Expense");

  public TripDetail() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    TripDetail.instance = this;
    super.onCreate(savedInstanceState);
  }

  public void setupToolbar() {
    Toolbar toolbar = activity.findViewById(R.id.tripDetailToolbar);
    if (toolbar != null) {
      activity.setSupportActionBar(toolbar);
      toolbar.setTitle(currentTrip.name);
      toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
      toolbar.setNavigationOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          goHome();
        }
      });
    } else {
      Log.e("WARNING", "TOOLBAR NOT AVAILABLE");
    }
  }

  public void goHome() {
    Fragment fragment = getParentFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
    if (fragment != null) {
      NavHostFragment.findNavController(fragment).navigate(R.id.action_tripDetail_to_HomeFragment);
    }
  }

  @SuppressLint("SetTextI18n")
  @Override
  public void onResume() {
    super.onResume();
    this.setupToolbar();
    TextView txtTripName = activity.findViewById(R.id.txtTripName);
    TextView txtDetailTripBudget = activity.findViewById(R.id.txtDetailTripBudget);
    TextView txtDetailTripDate = activity.findViewById(R.id.txtDetailTripDate);
    TextView txtTripDesc = activity.findViewById(R.id.txtTripDescription);
    txtTripName.setText(currentTrip.name);
    txtTripDesc.setText(currentTrip.description.isEmpty() ? "No description" : currentTrip.description);
    txtDetailTripBudget.setText("Trip budget: $" + currentTrip.budget);
    txtDetailTripDate.setText("Trip date: " + currentTrip.date);

    FloatingActionButton btnAddExpense = activity.findViewById(R.id.btnAddExpense);
    Button btnEdit = activity.findViewById(R.id.btnEditTrip);
    Button btnDelete = activity.findViewById(R.id.btnDeleteTrip);

    Fragment fragment = getParentFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);

    btnEdit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        MainActivity.newTripMode = "edit";
        NavHostFragment.findNavController(fragment).navigate(R.id.action_tripDetail_to_NewTrip);
      }
    });

    btnAddExpense.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        FragmentManager fm = getParentFragmentManager();
        addExpenseDialog.show(fm, "add_expense");
      }
    });

    btnDelete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        new AlertDialog.Builder(activity, R.style.DialogTheme)
          .setTitle("WARNING")
          .setMessage("Do you really want to delete this trip?")
          .setIcon(R.drawable.ic_baseline_delete_24)
          .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            public void onClick(DialogInterface dialog, int whichButton) {
              activity.tripDb.deleteTrip(MainActivity.currentTrip.id);
              MainActivity.trips.remove(MainActivity.currentTrip);
              MainActivity.homeListAdapter.notifyDataSetChanged();
              goHome();
            }})
          .setNegativeButton(android.R.string.no, null).show();
      }
    });

    if (this.getView() != null) {
      // set up the RecyclerView
      RecyclerView recyclerView = this.getView().findViewById(R.id.listTripExpenses);
      recyclerView.setLayoutManager(new LinearLayoutManager(this.getView().getContext()));
      recyclerView.setAdapter(tripExpensesListAdapter);
      recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
        @Override
        public void onClick(View view, int position) {
        }

        @Override
        public void onLongClick(View view, int position) {

        }
      }));
    }
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
//    activity.getMenuInflater().inflate(R.menu.menu_trip_detail, menu);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_trip_detail, container, false);
  }

  @NonNull
  public static TripDetail getInstance() {
    return instance;
  }
}