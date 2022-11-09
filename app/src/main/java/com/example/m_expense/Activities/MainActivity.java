package com.example.m_expense.Activities;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.m_expense.Controllers.TripDatabaseHandler;
import com.example.m_expense.Elements.Trip;
import com.example.m_expense.HomeTripListAdapter;
import com.example.m_expense.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.m_expense.databinding.ActivityMainBinding;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainActivity extends AppCompatActivity {
  @NotNull static MainActivity instance = null;
  private AppBarConfiguration appBarConfiguration;
  private ActivityMainBinding binding;
  public TripDatabaseHandler tripDb;
  public static HomeTripListAdapter homeListAdapter;
  static public List<Trip> trips;
  static public Trip currentTrip;
  LocationManager mLocationManager;

  @SuppressLint("MissingPermission")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    MainActivity.instance = this;

    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    tripDb = new TripDatabaseHandler(this.getBaseContext());
    MainActivity.trips = tripDb.getAllTrips();
    homeListAdapter = new HomeTripListAdapter(getBaseContext(), MainActivity.trips);
  }

  private final LocationListener mLocationListener = new LocationListener() {
    @Override
    public void onLocationChanged(final Location location) {
      //your code here
    }
  };

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onSupportNavigateUp() {
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
    return NavigationUI.navigateUp(navController, appBarConfiguration)
        || super.onSupportNavigateUp();
  }

  @NonNull
  public static MainActivity getInstance() {
    return instance;
  }
}