package com.example.m_expense.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.m_expense.Activities.MainActivity;
import com.example.m_expense.R;
import com.example.m_expense.databinding.FragmentNewExpenseBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewExpense extends Fragment {
  AppCompatActivity activity = MainActivity.getInstance();
  EditText editText;
  Calendar myCalendar = Calendar.getInstance();

  public NewExpense() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  private void bindListeners() {
    ImageView img = activity.findViewById(R.id.btnOpenMapView);
    img.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        if (fragment != null) {
          NavHostFragment.findNavController(fragment).navigate(R.id.act_newExpense_to_mapPicker);
        }
      }
    });


    editText = (EditText) activity.findViewById(R.id.inpTripDate);
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, month);
        myCalendar.set(Calendar.DAY_OF_MONTH, day);
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, MMM dd yyyy", Locale.US);
        editText.setText(dateFormat.format(myCalendar.getTime()));
      }
    };
    editText.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        new DatePickerDialog(activity, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
      }
    });
  }

  private void setupToolbar() {
    Toolbar toolbar = activity.findViewById(R.id.newExpenseToolbar);
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

  @Override
  public void onResume() {
    super.onResume();
    this.setupToolbar();
    this.bindListeners();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_new_expense, container, false);
  }

}