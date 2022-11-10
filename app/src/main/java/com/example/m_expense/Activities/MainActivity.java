package com.example.m_expense.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.m_expense.Controllers.TripDatabaseHandler;
import com.example.m_expense.Elements.Trip;
import com.example.m_expense.Adapters.HomeTripListAdapter;
import com.example.m_expense.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.m_expense.databinding.ActivityMainBinding;

import android.os.StrictMode;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

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
  static public String newTripMode = "create";
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

    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);
  }

  private final LocationListener mLocationListener = new LocationListener() {
    @Override
    public void onLocationChanged(final Location location) {
      //your code here
    }
  };

  static public void navigate(int actionId) {
    Fragment fragment = MainActivity.getInstance().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
    if (fragment != null) {
      NavHostFragment.findNavController(fragment).navigate(actionId);
    }
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

  @Override
  public boolean dispatchTouchEvent(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      View v = getCurrentFocus();
      if ( v instanceof EditText) {
        Rect outRect = new Rect();
        v.getGlobalVisibleRect(outRect);
        if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
          v.clearFocus();
          InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
          imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
      }
    }
    return super.dispatchTouchEvent( event );
  }
}