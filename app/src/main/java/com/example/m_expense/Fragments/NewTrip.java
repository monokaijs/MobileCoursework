package com.example.m_expense.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.m_expense.Activities.MainActivity;
import com.example.m_expense.Controllers.TripDatabaseHandler;
import com.example.m_expense.Elements.Trip;
import com.example.m_expense.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewTrip extends Fragment {
  AppCompatActivity activity = MainActivity.getInstance();
  EditText editText;
  Calendar myCalendar = Calendar.getInstance();
  EditText inpTripName;
  EditText inpTripDate;
  EditText inpTripDesc;
  EditText inpTripDest;
  EditText inpTripBudget;
  Switch swRiskAssessment;
  Button finishBtn;

  public NewTrip() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  private void bindListeners() {
    finishBtn = activity.findViewById(R.id.btnFinish);
    finishBtn.setOnClickListener(new View.OnClickListener() {
      @SuppressLint("NotifyDataSetChanged")
      public void onClick(View v) {
        String tripName = inpTripName.getText().toString();
        String tripDate = inpTripDate.getText().toString();
        String tripDest = inpTripDest.getText().toString();
        String tripDesc = inpTripDesc.getText().toString();
        String budgetText = inpTripBudget.getText().toString();
        int budget = Integer.parseInt(budgetText.isEmpty() ? "0" : budgetText);
        boolean tripRiskAssessment = swRiskAssessment.isChecked();

        if (tripName.isEmpty() || tripDate.isEmpty() || tripDest.isEmpty()) {
          Toast.makeText(activity, "Please enter all required information.", Toast.LENGTH_SHORT).show();

          return;
        }

        Log.i("CHECKED", tripRiskAssessment ? "CHECKED": "NOT CHECKED");

        Trip trip = new Trip(tripName, tripDesc, tripDest, tripDate, tripRiskAssessment, budget);

        if (MainActivity.newTripMode.equals("edit")) {
          trip.id = MainActivity.currentTrip.id;
          MainActivity.getInstance().tripDb.updateTrip(trip);
        } else {
          MainActivity.getInstance().tripDb.addTrip(trip);
        }
        MainActivity.trips = MainActivity.getInstance().tripDb.getAllTrips();
        MainActivity.homeListAdapter.setItems(MainActivity.trips);
        MainActivity.homeListAdapter.notifyDataSetChanged();
        goHome();
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

    if (MainActivity.newTripMode.equals("edit")) {
      Trip currentTrip = MainActivity.currentTrip;
      inpTripName.setText(currentTrip.name);
      inpTripDate.setText(currentTrip.date);
      inpTripDesc.setText(currentTrip.description);
      inpTripDest.setText(currentTrip.destination);
      inpTripBudget.setText(String.valueOf(currentTrip.budget));
      swRiskAssessment.setChecked(currentTrip.requiresRiskAssessment);
      Log.i("DEBUG", currentTrip.requiresRiskAssessment ? "ASSESS": "NOT ASSESS");

      finishBtn.setText("SAVE");
    } else {
      inpTripBudget.setText("0");
    }
  }

  private void setupToolbar() {
    Toolbar toolbar = activity.findViewById(R.id.newTripToolbar);
    if (toolbar != null) {
      activity.setSupportActionBar(toolbar);
      toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
      if (MainActivity.newTripMode.equals("edit")) {
        toolbar.setTitle("Edit Trip");
      } else {
        toolbar.setTitle("New Trip");
      }
      toolbar.setNavigationOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          goHome();
        }
      });
    }
  }

  public void goHome() {
    Fragment fragment = getParentFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
    if (fragment != null) {
      NavHostFragment.findNavController(fragment).navigate(R.id.act_newTrip_to_home);
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    this.setupToolbar();
    inpTripName = (EditText) activity.findViewById(R.id.inpTripName);
    inpTripDate = (EditText) activity.findViewById(R.id.inpTripDate);
    inpTripDest = (EditText) activity.findViewById(R.id.inpTripDestination);
    inpTripDesc = (EditText) activity.findViewById(R.id.inpTripDescription);
    inpTripBudget = (EditText) activity.findViewById(R.id.inpTripBudget);
    swRiskAssessment = (Switch) activity.findViewById(R.id.swRiskAssessment);
    this.bindListeners();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_new_trip, container, false);
  }

}