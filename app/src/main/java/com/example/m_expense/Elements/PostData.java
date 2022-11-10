package com.example.m_expense.Elements;

import java.util.List;

public class PostData {
  public String userId = "test";
  List<Trip> detailList;
  public PostData(List<Trip> trips) {
    this.detailList = trips;
  }
}
