package com.example.m_expense.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.m_expense.Activities.MainActivity;
import com.example.m_expense.Controllers.ApiHandler;
import com.example.m_expense.Elements.SyncTripResponse;
import com.example.m_expense.R;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Personal extends Fragment {
  MainActivity activity = MainActivity.getInstance();
  public static String userId = UUID.randomUUID().toString();
  boolean syncing = false;

  public Personal() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.fragment_personal, container, false);

    TextView txtUserId = rootView.findViewById(R.id.txtUserId);
    txtUserId.setText("User ID: " + Personal.userId);

    Toolbar toolbar = rootView.findViewById(R.id.personalToolbar);
    activity.setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        goHome();
      }
    });

    Button btnSync = rootView.findViewById(R.id.btnSyncData);

    btnSync.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        btnSync.setClickable(false);
        btnSync.setEnabled(false);
        btnSync.setText("SYNCING, PLEASE WAIT...");
        try {
          ApiHandler.postAllTrips(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
              btnSync.setText("SYNC");
              btnSync.setEnabled(true);
              btnSync.setClickable(true);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
              Gson gson = new Gson();
              SyncTripResponse data = gson.fromJson(response.body().string(), SyncTripResponse.class);
              activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                  alertDialog.setTitle(data.uploadResponseCode);
                  alertDialog.setMessage("Synced " + data.number + " trips.\n\n" + data.message);
                  alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        btnSync.setText("SYNC");
                        btnSync.setEnabled(true);
                        btnSync.setClickable(true);
                      }
                    });
                  alertDialog.show();
                }
              });
            }
          });
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });

    return rootView;
  }

  public void goHome() {
    Fragment fragment = getParentFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
    if (fragment != null) {
      NavHostFragment.findNavController(fragment).navigate(R.id.action_personal_to_HomeFragment);
    }
  }
}