package com.example.m_expense;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.m_expense.Elements.Trip;

import java.util.List;

public class HomeExpenseListAdapter extends RecyclerView.Adapter<HomeExpenseListAdapter.ViewHolder> {
  private List<Trip> mData;
  private LayoutInflater mInflater;
  private ItemClickListener mClickListener;

  // data is passed into the constructor
  public HomeExpenseListAdapter(Context context, List<Trip> data) {
    this.mInflater = LayoutInflater.from(context);
    this.mData = data;
  }

  // inflates the row layout from xml when needed
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = mInflater.inflate(R.layout.expenselist_row, parent, false);
    return new ViewHolder(view);
  }



  // binds the data to the TextView in each row
  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    Trip trip = mData.get(position);
    holder.myTextView.setText(trip.name);
  }

  // total number of rows
  @Override
  public int getItemCount() {
    return mData.size();
  }


  // stores and recycles views as they are scrolled off screen
  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView myTextView;

    ViewHolder(View itemView) {
      super(itemView);
      myTextView = itemView.findViewById(R.id.expenseItemName);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
    }
  }

  // convenience method for getting data at click position
  Trip getItem(int id) {
    return mData.get(id);
  }

  public void setItems(List<Trip> trips) {
    mData = trips;
  }

  // allows clicks events to be caught
  void setClickListener(ItemClickListener itemClickListener) {
    this.mClickListener = itemClickListener;
  }

  // parent activity will implement this method to respond to click events
  public interface ItemClickListener {
    void onItemClick(View view, int position);
  }
}
