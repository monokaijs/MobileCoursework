package com.example.m_expense.Controllers;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.m_expense.Elements.Trip;
import com.example.m_expense.Elements.TripExpense;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TripDatabaseHandler extends SQLiteOpenHelper {
  private static final String DATABASE_NAME = "mExpense";
  private static final int DATABASE_VERSION = 1;
  private static final String TRIPS_TABLE_NAME = "trips";
  private static final String TRIP_EXPENSES_TABLE_NAME = "trip_expenses";
  private static final String KEY_ID = "id";
  private static final String KEY_TRIP_ID = "trip_id";
  private static final String KEY_NAME = "name";
  private static final String KEY_DESCRIPTION = "description";
  private static final String KEY_DESTINATION = "destination";
  private static final String KEY_COST = "cost";
  private static final String KEY_CATEGORY = "category";
  private static final String KEY_DATE = "date";
  private static final String KEY_RISK_ASSESSMENT = "risk_assessment";
  private static final String KEY_BUDGET = "budget";

  public TripDatabaseHandler(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @SuppressLint("DefaultLocale")
  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(String.format(
      "CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER)",
      TRIPS_TABLE_NAME, KEY_ID, KEY_NAME, KEY_DESCRIPTION, KEY_DESTINATION, KEY_DATE, KEY_RISK_ASSESSMENT, KEY_BUDGET
    ));
    db.execSQL(String.format(
      "CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s INTEGER, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER)",
      TRIP_EXPENSES_TABLE_NAME, KEY_ID, KEY_TRIP_ID, KEY_NAME, KEY_DESCRIPTION, KEY_CATEGORY, KEY_DATE, KEY_COST
    ));
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL(
      String.format("DROP TABLE IF EXISTS %s", TRIPS_TABLE_NAME)
    );
    db.execSQL(
      String.format("DROP TABLE IF EXISTS %s", TRIP_EXPENSES_TABLE_NAME)
    );

    onCreate(db);
  }

  public void addTripExpense(TripExpense expense) {
    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(KEY_NAME, expense.name);
    values.put(KEY_TRIP_ID, expense.tripId);
    values.put(KEY_CATEGORY, expense.category);
    values.put(KEY_COST, expense.cost);
    values.put(KEY_DATE, expense.date);
    db.insert(TRIP_EXPENSES_TABLE_NAME, null, values);
    db.close();
  }

  public List<TripExpense> getAllTripExpenses(int tripId) {
    SQLiteDatabase db = this.getReadableDatabase();
    List<TripExpense> expenseList = new ArrayList<>();

    Cursor cursor = db.rawQuery("SELECT * FROM " + TRIP_EXPENSES_TABLE_NAME + " WHERE " + KEY_TRIP_ID + " = ?", new String[]{String.valueOf(tripId)});
    cursor.moveToFirst();

    while (!cursor.isAfterLast()) {
      TripExpense expense = new TripExpense(
        cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
        cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6)
      );
      Log.i("DEBUG", cursor.getString(2));
      expenseList.add(expense);
      cursor.moveToNext();
    }
    return expenseList;
  }

  public List<TripExpense> getAllExpenses() {
    SQLiteDatabase db = this.getReadableDatabase();
    List<TripExpense> expenseList = new ArrayList<>();

    Cursor cursor = db.rawQuery("SELECT * FROM " + TRIP_EXPENSES_TABLE_NAME + " WHERE ?", new String[]{"1"});
    cursor.moveToFirst();

    while (!cursor.isAfterLast()) {
      TripExpense expense = new TripExpense(
        cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
        cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6)
      );
      Log.i("DEBUG", cursor.getString(2));
      expenseList.add(expense);
      cursor.moveToNext();
    }
    return expenseList;
  }

  public void addTrip(Trip trip) {
    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(KEY_NAME, trip.name);
    values.put(KEY_DATE, trip.date);
    values.put(KEY_DESCRIPTION, trip.description);
    values.put(KEY_DESTINATION, trip.destination);
    values.put(KEY_RISK_ASSESSMENT, trip.requiresRiskAssessment ? "TRUE" : "FALSE");
    values.put(KEY_BUDGET, trip.budget);
    db.insert(TRIPS_TABLE_NAME, null, values);
    db.close();
  }

  public Trip getTrip(int tripId) {
    SQLiteDatabase db = this.getReadableDatabase();

    Cursor cursor = db.query(TRIPS_TABLE_NAME, null, KEY_ID + " = ?", new String[]{String.valueOf(tripId)}, null, null, null);
    if (cursor != null)
      cursor.moveToFirst();
    assert cursor != null;
    return new Trip(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), Objects.equals(cursor.getString(5), "TRUE"), cursor.getInt(6));
  }

  public void deleteTrip(int tripId) {
    SQLiteDatabase db = this.getWritableDatabase();
    db.delete(TRIPS_TABLE_NAME, KEY_ID + " = ?", new String[]{String.valueOf(tripId)});
    db.close();
  }

  public void deleteAllExpenses() {
    SQLiteDatabase db = this.getWritableDatabase();
    db.execSQL(
      String.format("DROP TABLE IF EXISTS %s", TRIP_EXPENSES_TABLE_NAME)
    );
    db.close();
  }

  public void deleteData() {
    this.deleteAllTrips();
    this.deleteAllExpenses();
  }

  public void deleteAllTrips() {
    SQLiteDatabase db = this.getReadableDatabase();
    db.execSQL(String.format("DROP TABLE IF EXISTS %s", TRIPS_TABLE_NAME));
    onCreate(db);
  }

  public List<Trip> getAllTrips() {
    List<Trip> tripList = new ArrayList<>();
    String query = "SELECT * FROM " + TRIPS_TABLE_NAME;

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(query, null);
    cursor.moveToFirst();

    while (!cursor.isAfterLast()) {
      Trip trip = new Trip(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), Objects.equals(cursor.getString(5), "TRUE"), cursor.getInt(6));
      tripList.add(trip);
      cursor.moveToNext();
    }
    return tripList;
  }

  public List<Trip> getTripsByName(String name) {
    List<Trip> tripList = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM " + TRIPS_TABLE_NAME + " WHERE instr(" + KEY_NAME + ", ?)", new String[]{name});
    cursor.moveToFirst();

    while (!cursor.isAfterLast()) {
      Trip trip = new Trip(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), Objects.equals(cursor.getString(5), "TRUE"), cursor.getInt(6));
      tripList.add(trip);
      cursor.moveToNext();
    }
    return tripList;
  }

  public void updateTrip(Trip trip) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(KEY_NAME, trip.name);
    values.put(KEY_DESCRIPTION, trip.description);
    values.put(KEY_DESTINATION, trip.destination);
    values.put(KEY_BUDGET, trip.budget);
    values.put(KEY_DATE, trip.date);
    values.put(KEY_RISK_ASSESSMENT, trip.requiresRiskAssessment ? "TRUE" : "FALSE");
    values.put(KEY_ID, trip.id);

    db.update(TRIPS_TABLE_NAME, values, KEY_ID + " = ?", new String[]{String.valueOf(trip.id)});
    db.close();
  }
}
