package com.example.m_expense.Elements;

public class TripExpense {
  public int id;
  public String name;
  public String description;
  public String category;
  public String date;
  public int cost;
  public int tripId;

  public TripExpense(int id, int tripId, String name, String description, String category, String date, int cost) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.category = category;
    this.date = date;
    this.cost = cost;
    this.tripId = tripId;
  }

  public TripExpense(int tripId, String name, String description, String category, String date, int cost) {
    this.name = name;
    this.description = description;
    this.category = category;
    this.date = date;
    this.cost = cost;
    this.tripId = tripId;
  }
}
