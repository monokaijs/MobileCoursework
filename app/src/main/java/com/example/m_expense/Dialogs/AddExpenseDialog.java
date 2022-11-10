package com.example.m_expense.Dialogs;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.m_expense.R;

public class AddExpenseDialog extends DialogFragment {

  private EditText mEditText;

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
    // Get field from view
//    mEditText = (EditText) view.findViewById(R.id.txt_your_name);
//    // Fetch arguments from bundle and set title
//    String title = getArguments().getString("title", "Enter Name");
//    getDialog().setTitle(title);
    // Show soft keyboard automatically and request focus to field
//    mEditText.requestFocus();
//    getDialog().getWindow().setSoftInputMode(
//        WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
//    );
  }
}