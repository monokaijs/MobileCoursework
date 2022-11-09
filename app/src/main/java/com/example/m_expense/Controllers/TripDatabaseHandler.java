package com.example.m_expense.Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.m_expense.Elements.Trip;

import java.util.ArrayList;
import java.util.List;

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

  public TripDatabaseHandler(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    String create_students_table = String.format(
      "CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)", TABLE_NAME, KEY_ID, KEY_NAME, KEY_DESCRIPTION, KEY_DESTINATION, KEY_DATE, KEY_RISK_ASSESSMENT
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

    db.insert(TABLE_NAME, null, values);
    db.close();
  }

  public Trip getTrip(int tripId) {
    SQLiteDatabase db = this.getReadableDatabase();

    Cursor cursor = db.query(TABLE_NAME, null, KEY_ID + " = ?", new String[]{String.valueOf(tripId)}, null, null, null);
    if (cursor != null)
      cursor.moveToFirst();
    assert cursor != null;
    return new Trip(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(6) == "TRUE");
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
      Trip trip = new Trip(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5) == "TRUE");
      tripList.add(trip);
      cursor.moveToNext();
    }
    return tripList;
  }
}
