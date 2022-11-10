package com.example.m_expense.Dialogs;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.m_expense.Activities.MainActivity;
import com.example.m_expense.Elements.TripExpense;
import com.example.m_expense.Fragments.TripDetail;
import com.example.m_expense.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddExpenseDialog extends DialogFragment {
  MainActivity activity = MainActivity.getInstance();

  public AddExpenseDialog() {
    // Empty constructor is required for DialogFragment
    // Make sure not to add arguments to the constructor
    // Use `newInstance` instead as shown below
  }

  public static AddExpenseDialog newInstance(String title) {
    AddExpenseDialog frag = new AddExpenseDialog();
    Bundle args = new Bundle();
    args.putString("title", title);
    frag.setArguments(args);
    return frag;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_add_expense, container);
  }

  @Override
  public void onResume() {
    Window window = getDialog().getWindow();
    Point size = new Point();
    Display display = window.getWindowManager().getDefaultDisplay();
    display.getSize(size);
    // Set the width of the dialog proportional to 75% of the screen width
    window.setLayout((int) (size.x * .8), WindowManager.LayoutParams.WRAP_CONTENT);
    window.setGravity(Gravity.CENTER);

    super.onResume();
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    Button btnComplete = view.findViewById(R.id.btnComplete);
    Log.i("DEBUG", String.valueOf(btnComplete));
    if (btnComplete != null) {
      EditText inpExpenseName = view.findViewById(R.id.inpExpenseName);
      EditText inpExpenseCost = view.findViewById(R.id.inpExpenseCost);
      EditText inpExpenseDescription = view.findViewById(R.id.inpExpenseDescription);
      RadioGroup radioGroup = view.findViewById(R.id.radioGroupExpenseCategory);

      btnComplete.setOnClickListener(new View.OnClickListener() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onClick(View v) {
          if (inpExpenseCost.getText().toString().isEmpty() || inpExpenseName.getText().toString().isEmpty()) {
            Toast.makeText(activity, "Please enter name and cost for expense.", Toast.LENGTH_SHORT).show();
            return;
          }
          int selectedId = radioGroup.getCheckedRadioButtonId();
          RadioButton currentRadioButton = view.findViewById(selectedId);

          SimpleDateFormat dateFormat = new SimpleDateFormat("E, MMM dd yyyy", Locale.US);
          String timeString = dateFormat.format(Calendar.getInstance().getTime().getTime());

          TripExpense expense = new TripExpense(
            // int tripId, String name, String description, String category, String date, int cost
            MainActivity.currentTrip.id, inpExpenseName.getText().toString(),
            inpExpenseDescription.getText().toString(),
            currentRadioButton.getText().toString(), timeString, Integer.parseInt(inpExpenseCost.getText().toString())
          );
          activity.tripDb.addTripExpense(expense);
          List<TripExpense> newList = activity.tripDb.getAllTripExpenses(MainActivity.currentTrip.id);
          TripDetail.getInstance().tripExpensesListAdapter.setItems(newList);
          TripDetail.getInstance().tripExpensesListAdapter.notifyDataSetChanged();

          Toast.makeText(activity, "Expense added.", Toast.LENGTH_SHORT).show();
          TripDetail.addExpenseDialog.dismiss();
        }
      });
    }
  }
}