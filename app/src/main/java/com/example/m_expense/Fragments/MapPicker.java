package com.example.m_expense.Fragments;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.m_expense.Activities.MainActivity;
import com.example.m_expense.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;

public class MapPicker extends Fragment {
  MainActivity activity = MainActivity.getInstance();
  MapView mapView;

  public MapPicker() {
    // Required empty public constructor
  }

  @SuppressLint("MissingPermission")
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_map_picker, container, false);

    mapView = view.findViewById(R.id.mapView);
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync((GoogleMap map) -> {
      map.getUiSettings().setMyLocationButtonEnabled(false);
//      map.setMyLocationEnabled(true);
      MapsInitializer.initialize(activity);
    });
    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    if (mapView != null) {
      mapView.onResume();
    }
  }
  @Override
  public void onDestroy() {
    super.onDestroy();
    if (mapView != null) {
      mapView.onDestroy();
    }
  }
}