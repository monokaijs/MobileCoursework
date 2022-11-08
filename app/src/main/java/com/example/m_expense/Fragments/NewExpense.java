package com.example.m_expense.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.m_expense.Activities.MainActivity;
import com.example.m_expense.R;

public class NewExpense extends Fragment {

  public NewExpense() {
    // Required empty public constructor
  }
  public static NewExpense newInstance(String param1, String param2) {
    NewExpense fragment = new NewExpense();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  private void setupToolbar() {
    AppCompatActivity activity = ((AppCompatActivity)getActivity());
    if (activity != null) {
      Toolbar toolbar = getActivity().findViewById(R.id.newExpenseToolbar);
      if (toolbar != null) {
        activity.setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Fragment fragment = getParentFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
            if (fragment != null) {
              NavHostFragment.findNavController(fragment).navigate(R.id.act_newExpense_to_home);
            }
          }
        });
      }
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    this.setupToolbar();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_new_expense, container, false);
  }
}