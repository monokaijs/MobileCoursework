package com.example.m_expense.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.m_expense.Elements.TripExpense;
import com.example.m_expense.R;

import java.util.List;

public class TripExpensesListAdapter  extends RecyclerView.Adapter<TripExpensesListAdapter.ViewHolder> {
  private List<TripExpense> mData;
  private LayoutInflater mInflater;
  private TripExpensesListAdapter.ItemClickListener mClickListener;

  // data is passed into the constructor
  public TripExpensesListAdapter(Context context, List<TripExpense> data) {
    Log.i("DEBUG", String.valueOf(data.size()));
    this.mInflater = LayoutInflater.from(context);
    this.mData = data;
  }

  // inflates the row layout from xml when needed
  @Override
  public TripExpensesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = mInflater.inflate(R.layout.trip_expense_list_row, parent, false);
    return new TripExpensesListAdapter.ViewHolder(view);
  }



  // binds the data to the TextView in each row
  @SuppressLint("SetTextI18n")
  @Override
  public void onBindViewHolder(TripExpensesListAdapter.ViewHolder holder, int position) {
    TripExpense expense = mData.get(position);
    holder.txtExpenseName.setText(expense.name);
    holder.txtExpenseCategory.setText(expense.category.toUpperCase());
    holder.txtCost.setText("$" + expense.cost);
    holder.txtExpenseDate.setText(expense.date);
  }

  // total number of rows
  @Override
  public int getItemCount() {
    return mData.size();
  }


  // stores and recycles views as they are scrolled off screen
  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView txtExpenseName;
    TextView txtExpenseCategory;
    TextView txtCost;
    TextView txtExpenseDate;

    ViewHolder(View itemView) {
      super(itemView);
      txtExpenseName = itemView.findViewById(R.id.txtExpenseName);
      txtExpenseCategory = itemView.findViewById(R.id.txtExpenseCategory);
      txtCost = itemView.findViewById(R.id.txtCost);
      txtExpenseDate = itemView.findViewById(R.id.txtExpenseDate);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      Log.i("DEBUG", "Clicked");
      if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
    }
  }

  // convenience method for getting data at click position
  TripExpense getItem(int id) {
    return mData.get(id);
  }

  public void setItems(List<TripExpense> expenses) {
    mData = expenses;
  }

  // allows clicks events to be caught
  void setClickListener(TripExpensesListAdapter.ItemClickListener itemClickListener) {
    this.mClickListener = itemClickListener;
  }

  // parent activity will implement this method to respond to click events
  public interface ItemClickListener {
    void onItemClick(View view, int position);
  }
}
