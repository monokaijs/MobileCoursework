package com.example.m_expense.Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.m_expense.Elements.Trip;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TripDatabaseHandler extends SQLiteOpenHelper {
  private static final String DATABASE_NAME = "mExpense";
  private static final int DATABASE_VERSION = 1;
  private static final String TABLE_NAME = "trips";
  private static final String KEY_ID = "id";
  private static final String KEY_NAME = "name";
  private static final String KEY_DESCRIPTION = "description";
  private static final String KEY_DESTINATION = "destination";
  private static final String KEY_DATE = "date";
  private static final String KEY_RISK_ASSESSMENT = "risk_assessment";
  private static final String KEY_BUDGET = "budget";

  public TripDatabaseHandler(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    String create_students_table = String.format(
      "CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER)",
        TABLE_NAME, KEY_ID, KEY_NAME, KEY_DESCRIPTION, KEY_DESTINATION, KEY_DATE, KEY_RISK_ASSESSMENT, KEY_BUDGET
    );
    db.execSQL(create_students_table);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    String drop_students_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
    db.execSQL(drop_students_table);

    onCreate(db);
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
    Log.i("ASSESS", trip.requiresRiskAssessment ? "REQUIRES": "NOT REQUIRES");

    db.insert(TABLE_NAME, null, values);
    db.close();
  }

  public Trip getTrip(int tripId) {
    SQLiteDatabase db = this.getReadableDatabase();

    Cursor cursor = db.query(TABLE_NAME, null, KEY_ID + " = ?", new String[]{String.valueOf(tripId)}, null, null, null);
    if (cursor != null)
      cursor.moveToFirst();
    assert cursor != null;
    return new Trip(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), Objects.equals(cursor.getString(5), "TRUE"), cursor.getInt(6));
  }

  public void deleteTrip(int tripId) {
    SQLiteDatabase db = this.getWritableDatabase();
    db.delete(TABLE_NAME, KEY_ID + " = ?", new String[]{String.valueOf(tripId)});
    db.close();
  }

  public void deleteAllTrips() {
    SQLiteDatabase db = this.getReadableDatabase();
    db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
    onCreate(db);
  }

  public List<Trip> getAllTrips() {
    List<Trip> tripList = new ArrayList<>();
    String query = "SELECT * FROM " + TABLE_NAME;

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

    db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[] { String.valueOf(trip.id) });
    db.close();
  }
}
