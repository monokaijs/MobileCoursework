package com.example.m_expense.Elements;

import com.example.m_expense.Fragments.Personal;

import java.util.List;

public class PostData {
  public String userId = "test";
  List<Trip> detailList;
  public PostData(List<Trip> trips) {
    this.userId = Personal.userId;
    this.detailList = trips;
  }
}
