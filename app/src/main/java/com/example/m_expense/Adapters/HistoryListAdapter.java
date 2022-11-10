package com.example.m_expense.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.m_expense.Activities.MainActivity;
import com.example.m_expense.Elements.Trip;
import com.example.m_expense.Elements.TripExpense;
import com.example.m_expense.R;

import java.util.List;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ViewHolder> {
  private List<TripExpense> mData;
  private LayoutInflater mInflater;
  private HistoryListAdapter.ItemClickListener mClickListener;

  // data is passed into the constructor
  public HistoryListAdapter(Context context, List<TripExpense> data) {
    this.mInflater = LayoutInflater.from(context);
    this.mData = data;
  }

  // inflates the row layout from xml when needed
  @Override
  public HistoryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = mInflater.inflate(R.layout.history_expense_list_row, parent, false);
    return new HistoryListAdapter.ViewHolder(view);
  }



  // binds the data to the TextView in each row
  @SuppressLint("SetTextI18n")
  @Override
  public void onBindViewHolder(HistoryListAdapter.ViewHolder holder, int position) {
    TripExpense expense = mData.get(position);
    Trip trip = MainActivity.getInstance().tripDb.getTrip(expense.tripId);
    holder.txtExpenseName.setText(expense.name);
    holder.txtExpenseCategory.setText(expense.category.toUpperCase());
    holder.txtCost.setText("$" + expense.cost);
    holder.txtExpenseDate.setText(expense.date);
    holder.txtExpenseTripName.setText(trip.name);
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
    TextView txtExpenseTripName;

    ViewHolder(View itemView) {
      super(itemView);
      txtExpenseName = itemView.findViewById(R.id.txtExpenseName);
      txtExpenseCategory = itemView.findViewById(R.id.txtExpenseCategory);
      txtCost = itemView.findViewById(R.id.txtCost);
      txtExpenseDate = itemView.findViewById(R.id.txtExpenseDate);
      txtExpenseTripName = itemView.findViewById(R.id.txtExpenseTripName);
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

  public void setItems(List<TripExpense> trips) {
    mData = trips;
  }

  // allows clicks events to be caught
  void setClickListener(HistoryListAdapter.ItemClickListener itemClickListener) {
    this.mClickListener = itemClickListener;
  }

  // parent activity will implement this method to respond to click events
  public interface ItemClickListener {
    void onItemClick(View view, int position);
  }
}