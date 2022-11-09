package com.example.m_expense.Fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.m_expense.Activities.MainActivity;
import com.example.m_expense.Elements.Trip;
import com.example.m_expense.HomeTripListAdapter;
import com.example.m_expense.R;
import com.example.m_expense.RecyclerTouchListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
  protected RecyclerView mRecyclerView;
  protected RecyclerView.LayoutManager mLayoutManager;

  MainActivity activity = MainActivity.getInstance();

  public HomeFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_home, container, false);
  }

  @Override
  public void onResume() {
    super.onResume();
    if (this.getView() != null) {
      // set up the RecyclerView
      RecyclerView recyclerView = this.getView().findViewById(R.id.homeExpenseList);
      recyclerView.setLayoutManager(new LinearLayoutManager(this.getView().getContext()));
      recyclerView.setAdapter(MainActivity.homeListAdapter);
      recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
        @Override
        public void onClick(View view, int position) {
          MainActivity.currentTrip = MainActivity.trips.get(position);
          MainActivity.navigate(R.id.action_HomeFragment_to_tripDetail);
        }

        @Override
        public void onLongClick(View view, int position) {

        }
      }));
    }
    setupFAB();

    Button btnSettings = activity.findViewById(R.id.btnSettings);
    Button btnPersonal = activity.findViewById(R.id.btnPersonal);
    Button btnHistory = activity.findViewById(R.id.btnHistory);

    btnSettings.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        MainActivity.navigate(R.id.action_HomeFragment_to_settings);
      }
    });
    btnHistory.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        MainActivity.navigate(R.id.action_HomeFragment_to_history);
      }
    });

  }

  private void setupFAB() {
    FloatingActionButton fab = MainActivity.getInstance().findViewById(R.id.fabNewTrip);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        MainActivity.newTripMode = "create";
        MainActivity.navigate(R.id.act_home_to_newTrip);
      }
    });
  }
}