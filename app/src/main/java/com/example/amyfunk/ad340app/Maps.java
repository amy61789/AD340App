package com.example.amyfunk.ad340app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class Maps extends AppCompatActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private FusedLocationProviderClient mapFusedLocationClient;
    private LocationCallback mapLocationCallback;

    private LocationRequest mapLocationRequest;
    private Location mapLastLocation;
    private GoogleMap mapMap;

    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9;
    protected boolean mapAddressRequested;
    protected String mapAddressOutput;

    protected TextView mapLatitudeText;
    protected TextView mapLongitudeText;
    protected TextView mapLocationText;

    private AddressResultReceiver mapAddressReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        mapAddressRequested = true;
        mapAddressOutput = "";

        mapLatitudeText = (TextView) findViewById(R.id.textLatitude);
        mapLongitudeText = (TextView) findViewById(R.id.textLongitude);
        mapLocationText = (TextView) findViewById(R.id.textLocation);

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mapFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        createLocationCallback();
        getLocation();

    }

    /**
     * Creates a callback for receiving location events.
     */
    private void createLocationCallback() {
        mapLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Log.d("LOCATION", "onLocationResult");
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    mapLastLocation = location;
                    updateUI();
                }
            }

            ;
        };
    }


    @Override
    public void onConnected(Bundle connectionHint) {
    }

    public void getLocation() {
        Log.d("LOCATION", "getLocation");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d("LOCATION", "permissionGranted");
            mapFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            Log.d("LOCATION", "gotLocation");

                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                mapLastLocation = location;
                                updateUI();
                            }
                        }
                    });
        } else {
            Log.d("LOCATION", "permissionNotGranted");
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.
                Log.d("LOCATION", "requestPermission");

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d("LOCATION", "onRequestPermissionsResult");
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    updateUI();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // store map object for use once location is available
        mapMap = googleMap;
        Log.d("LOCATION", "onMapReady");
    }


    public void updateUI() {
        if (mapLastLocation == null) {
            // get location updates
            Log.d("LOCATION", "startLocationUpdates");
            startLocationUpdates();
        } else {

            // initiate geocode request
            if (mapAddressRequested) {
            }

            mapLatitudeText.setText(String.valueOf(mapLastLocation.getLatitude()));
            mapLongitudeText.setText(String.valueOf(mapLastLocation.getLongitude()));

            LatLng myLocation = new LatLng(mapLastLocation.getLatitude(), mapLastLocation.getLongitude());
            mapMap.setMinZoomPreference(10); // zoom to city level
            mapMap.addMarker(new MarkerOptions().position(myLocation)
                    .title("My current location"));
            mapMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
        }
    }

    /**
     * Shows a toast with the given text.
     */
    protected void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    protected void createLocationRequest() {
        mapLocationRequest = new LocationRequest();
        mapLocationRequest.setInterval(10000);
        mapLocationRequest.setFastestInterval(5000);
        mapLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mapFusedLocationClient.requestLocationUpdates(mapLocationRequest,
                    mapLocationCallback,
                    null /* Looper */);        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("LOCATION","onLocationChanged");
        mapLastLocation = location;
        updateUI();
    }


    /**
     * Receiver for data sent from FetchAddressIntentService.
     */
    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }
        /**
         *  Receives data sent from FetchAddressIntentService and updates the UI in MainActivity.
         */

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            Log.d("receivedResult", resultData.toString());
            if (resultData == null) {
                return;
            }

            // Display the address string or an error message sent from the intent service.
            mapAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
            Log.d("LOCATION", mapAddressOutput);
            mapLocationText.setText(mapAddressOutput);

            // Show a toast message if an address was found.
            if (resultCode == Constants.SUCCESS_RESULT) {
                showToast(getString(R.string.address_found));
            }

            // Reset. Enable the Fetch Address button and stop showing the progress bar.
            mapAddressRequested = false;
        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {
        showToast("Connection failed.");
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (i == CAUSE_SERVICE_DISCONNECTED) {
            showToast("Disconnected. Please re-connect.");
        } else if (i == CAUSE_NETWORK_LOST) {
            showToast("Network lost. Please re-connect.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (mRequestingLocationUpdates) {
//            startLocationUpdates();
//        }
        mapAddressReceiver = new AddressResultReceiver(new Handler());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    protected void onStop() {
        super.onStop();
    }

}