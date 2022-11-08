package com.example.m_expense.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.m_expense.HomeExpenseListAdapter;
import com.example.m_expense.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
  protected RecyclerView mRecyclerView;
  protected RecyclerView.LayoutManager mLayoutManager;
  HomeExpenseListAdapter adapter;

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
      // data to populate the RecyclerView with
      ArrayList<String> items = new ArrayList<>();
      items.add("Horse");
      items.add("Cow");
      items.add("Camel");
      items.add("Sheep");
      items.add("Goat");

      // set up the RecyclerView
      RecyclerView recyclerView = this.getView().findViewById(R.id.homeExpenseList);
      recyclerView.setLayoutManager(new LinearLayoutManager(this.getView().getContext()));
      adapter = new HomeExpenseListAdapter(this.getContext(), items);
      recyclerView.setAdapter(adapter);
    }
  }
}