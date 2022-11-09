package com.example.m_expense.Elements;

public class Trip {
  public int id;
  public String name;
  public String description;
  public String destination;
  public String date;
  public int budget = 0;
  public Boolean requiresRiskAssessment;

  public Trip(int id, String name, String description, String destination, String date, Boolean requiresRiskAssessment) {
    super();
    this.id = id;
    this.name = name;
    this.description = description;
    this.destination = destination;
    this.date = date;
    this.requiresRiskAssessment = requiresRiskAssessment;
  }
  public Trip(int id, String name, String description, String destination, String date, Boolean requiresRiskAssessment, int budget) {
    super();
    this.id = id;
    this.name = name;
    this.description = description;
    this.destination = destination;
    this.date = date;
    this.requiresRiskAssessment = requiresRiskAssessment;
    this.budget = budget;
  }

  public Trip(String name, String description, String destination, String date, Boolean requiresRiskAssessment, int budget) {
    super();
    this.name = name;
    this.description = description;
    this.destination = destination;
    this.date = date;
    this.requiresRiskAssessment = requiresRiskAssessment;
    this.budget = budget;
  }

}
