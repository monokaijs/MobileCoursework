package com.example.m_expense.Fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.m_expense.Activities.MainActivity;
import com.example.m_expense.Elements.Trip;
import com.example.m_expense.R;

import java.util.ArrayList;
import java.util.List;

public class Settings extends Fragment {
  MainActivity activity = MainActivity.getInstance();

  public Settings() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_settings, container, false);
  }

  @Override
  public void onResume() {
    super.onResume();
    this.setupToolbar();
    Button btnClearData = activity.findViewById(R.id.btnClearData);
    btnClearData.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        new AlertDialog.Builder(activity, R.style.DialogTheme)
          .setTitle("WARNING")
          .setMessage("Do you really want to delete this trip?")
          .setIcon(R.drawable.ic_baseline_delete_24)
          .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            public void onClick(DialogInterface dialog, int whichButton) {
              activity.tripDb.deleteData();
              MainActivity.trips = new ArrayList<Trip>();
              MainActivity.homeListAdapter.setItems(MainActivity.trips);
              MainActivity.homeListAdapter.notifyDataSetChanged();
              Toast.makeText(activity, "Data cleared!", Toast.LENGTH_SHORT).show();
            }})
          .setNegativeButton(android.R.string.no, null).show();
      }
    });
  }

  private void setupToolbar() {
    Toolbar toolbar = activity.findViewById(R.id.settingsToolbar);
    if (toolbar != null) {
      activity.setSupportActionBar(toolbar);
      toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
      toolbar.setTitle("Settings");
      toolbar.setNavigationOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          MainActivity.navigate(R.id.action_settings_to_HomeFragment);
        }
      });
    }
  }
}