package com.example.hasanmdmehadi.jamattimee;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends AppCompatActivity  implements LocationListener {


    private static final long UPDATE_INTERVAL = 10 * 1000;
    private static final long FASTEST_INTERVAL = 5000;

    private static final float DEFAULT_ZOOM_LEVEL = 9.00f;
    private static final float MIN_ZOOM_LEVEL = 2.00f;
    private static final float MAX_ZOOM_LEVEL = 20.00f;

    private Location location;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient fusedLocationClient;

    private GoogleMap broadGoogleMap;

    List<MasjidModel> masjid_info_list;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        provider = "gps";

        if (isNetworkAvailable()) {


            masjid_info_list = new ArrayList<>();

            addDataInList();

            startLocationUpdates();
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                checkLocationPermission();
//            } else {
//                startLocationUpdates();
//            }

            SupportMapFragment supportMapFragment = (SupportMapFragment)
                    getSupportFragmentManager().findFragmentById(R.id.map);
            supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @SuppressLint("MissingPermission")
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    broadGoogleMap = googleMap;

                    broadGoogleMap.setMyLocationEnabled(true);
                    broadGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
                    broadGoogleMap.getUiSettings().setZoomControlsEnabled(false);
                    broadGoogleMap.setMinZoomPreference(MIN_ZOOM_LEVEL);
                    broadGoogleMap.setMaxZoomPreference(MAX_ZOOM_LEVEL);
                }
            });
        }
    }


    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //Permission Check
            LocationPermissionUtils.requestPermission(this);
        } else {
            startLocationUpdates();
        }
    }

    @Override
    public void onLocationChanged(final Location location) {
        this.location = location;

        Log.d("LAT", String.valueOf(location.getLatitude()));
        Log.d("LONG", String.valueOf(location.getLongitude()));

        // DEFAULT STATE
        goToMapLocation(24.4100476,90.3309697,6.75f);
//        goToMapLocation(location.getLatitude(), location.getLongitude(), DEFAULT_ZOOM_LEVEL);
    }

    private void goToMapLocation(double lat, double lng, float zoom) {
        LatLng latLng = new LatLng(lat, lng);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        broadGoogleMap.animateCamera(cameraUpdate);
//        broadGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        broadGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(zoom));
    }

    protected void startLocationUpdates() {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                //location = locationResult.getLastLocation();
                location = locationResult.getLocations().get(0);
                goToMapLocation(24.4100476,90.3309697,6.75f);

//                goToMapLocation(location.getLatitude(), location.getLongitude(), DEFAULT_ZOOM_LEVEL);
//                MarkerOptions options = new MarkerOptions();
//                options.position(new LatLng(location.getLatitude(), location.getLongitude()));
//                BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN);
//                options.icon(icon);
//                Marker marker = homeGoogleMap.addMarker(options);
//
//                homeGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 17));
            }

            @Override
            public void onLocationAvailability(LocationAvailability availability) {
                boolean isLocationAvailable = availability.isLocationAvailable();

                Log.d("LocationAvailability", String.valueOf(isLocationAvailable));
            }
        };

        // Create the location request to start receiving updates
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        locationRequest.setNumUpdates(1);

        if (provider.equalsIgnoreCase(LocationManager.GPS_PROVIDER)) {
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }else{
            locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        }


        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)

        Task<LocationSettingsResponse> locationResponse = settingsClient.checkLocationSettings(locationSettingsRequest);
        locationResponse.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                Log.e("Response", "Successful acquisition of location information!!");

                if (ActivityCompat.checkSelfPermission(LocationActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback,
                        Looper.myLooper());
            }
        });

        locationResponse.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode = ((ApiException) e).getStatusCode();
                switch (statusCode) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.e("onFailure", "RESOLUTION_REQUIRED");
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.e("onFailure", "SETTINGS_CHANGE_UNAVAILABLE");
                        break;
                    default:
                        Log.e("onFailure", e.getMessage());
                        break;
                }
            }
        });
    }

    private void addCentrePointPlaceMarkers(GoogleMap broadGoogleMap) {

        for (MasjidModel masjidModel : masjid_info_list) {

            String makeAddition = masjidModel.getMasjid_id();

            double lat = Double.valueOf(masjidModel.getLat());
            double lng = Double.valueOf(masjidModel.getLon());

            LatLng latLng = new LatLng(lat, lng);

            broadGoogleMap.addMarker(new MarkerOptions().position(latLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    .title(masjidModel.getMasjid_name())
            ).setTag(makeAddition);


            final Marker[] lastOpenned = {null};
            broadGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    goToMapLocation(marker.getPosition().latitude,
                            marker.getPosition().longitude, DEFAULT_ZOOM_LEVEL);

                    // Check if there is an open info window
                    if (lastOpenned[0] != null) {
                        // Close the info window
                        lastOpenned[0].hideInfoWindow();

                        // Is the marker the same marker that was already open
                        if (lastOpenned[0].equals(marker)) {
                            // Nullify the lastOpenned object
                            lastOpenned[0] = null;
                            // Return so that the info window isn't openned again
                            return true;
                        }
                    }

                    // Open the info window for the marker
                    marker.showInfoWindow();
                    // Re-assign the last openned such that we can close it later
                    lastOpenned[0] = marker;

                    // Event was handled by our code do not launch default behaviour.
                    return true;
                }
            });

            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }


    private void addDataInList() {

        MasjidModel masjidModel = new MasjidModel();
        masjidModel.setMasjid_name("University Central Masjid");
        masjidModel.setMasjid_id("1");
        masjidModel.setLat("24.917371");
        masjidModel.setLon("91.830995");
        masjidModel.setFajr("5:15 AM");
        masjidModel.setZahor("1:20 PM");
        masjidModel.setAsr("4:45 PM");
        masjidModel.setMagrib("6: 55 PM");
        masjidModel.setIsha("8:25 PM");
        masjid_info_list.add(masjidModel);
//
//        MasjidModel masjidModel = new MasjidModel();
//        masjidModel.setMasjid_name("");
//        masjidModel.setMasjid_id("");
//        masjidModel.setLat("24.91");
//        masjidModel.setLon("91.83");
//        masjidModel.setFajr("5:15 AM");
//        masjidModel.setZahor("1:20 PM");
//        masjidModel.setAsr("4:45 PM");
//        masjidModel.setMagrib("6: 55 PM");
//        masjidModel.setIsha("8:25 PM");
//        masjid_info_list.add(masjidModel);

        MasjidModel masjidModel1 = new MasjidModel();
        masjidModel1.setMasjid_name("Staff Quarter Jame Mosque");
        masjidModel1.setMasjid_id("2");
        masjidModel1.setLat("24.922771");
        masjidModel1.setLon("91.828624");
        masjidModel1.setFajr("5:10 AM");
        masjidModel1.setZahor("1:30 PM");
        masjidModel1.setAsr("4:50 PM");
        masjidModel1.setMagrib("6: 55 PM");
        masjidModel1.setIsha("8:20 PM");
        masjid_info_list.add(masjidModel1);

        MasjidModel masjidModel2 = new MasjidModel();
        masjidModel2.setMasjid_name("Hall Masjid");
        masjidModel2.setMasjid_id("3");
        masjidModel2.setLat("24.924921");
        masjidModel2.setLon("91.835705");
        masjidModel2.setFajr("5:10 AM");
        masjidModel2.setZahor("1:25 PM");
        masjidModel2.setAsr("5:10 PM");
        masjidModel2.setMagrib("6: 50 PM");
        masjidModel2.setIsha("8:30 PM");
        masjid_info_list.add(masjidModel2);


        MasjidModel masjidModel3 = new MasjidModel();
        masjidModel3.setMasjid_name("Najirergaon Dighirpar Jame Mosque");
        masjidModel3.setMasjid_id("4");
        masjidModel3.setLat("24.922566");
        masjidModel3.setLon("91.823592");
        masjidModel3.setFajr("5:15 AM");
        masjidModel3.setZahor("1:25 PM");
        masjidModel3.setAsr("4:55 PM");
        masjidModel3.setMagrib("6: 55 PM");
        masjidModel3.setIsha("8:25 PM");
        masjid_info_list.add(masjidModel3);


        MasjidModel masjidModel4 = new MasjidModel();
        masjidModel.setMasjid_name("Haidarpur Shahi Jame Masjid");
        masjidModel.setMasjid_id("5");
        masjidModel.setLat("24.914574");
        masjidModel.setLon("91.818634");
        masjidModel.setFajr("5:20 AM");
        masjidModel.setZahor("1:30 PM");
        masjidModel.setAsr("5:15 PM");
        masjidModel.setMagrib("6: 503 PM");
        masjidModel.setIsha("8:30 PM");
        masjid_info_list.add(masjidModel4);


        MasjidModel masjidModel5 = new MasjidModel();
        masjidModel.setMasjid_name("Pirpur Jame Masjid");
        masjidModel.setMasjid_id("6");
        masjidModel.setLat("24.915220");
        masjidModel.setLon("91.817174");
        masjidModel.setFajr("5:20 AM");
        masjidModel.setZahor("1:30 PM");
        masjidModel.setAsr("4:45 PM");
        masjidModel.setMagrib("6: 55 PM");
        masjidModel.setIsha("8:30 PM");
        masjid_info_list.add(masjidModel5);


        MasjidModel masjidModel6 = new MasjidModel();
        masjidModel.setMasjid_name("Tukerbazar Shahi Eidgah Mosque");
        masjidModel.setMasjid_id("7");
        masjidModel.setLat("24.915247");
        masjidModel.setLon("91.816928");
        masjidModel.setFajr("5:15 AM");
        masjidModel.setZahor("1:20 PM");
        masjidModel.setAsr("4:45 PM");
        masjidModel.setMagrib("6: 55 PM");
        masjidModel.setIsha("8:25 PM");
        masjid_info_list.add(masjidModel6);


        MasjidModel masjidModel7 = new MasjidModel();
        masjidModel.setMasjid_name("Chorugaon Jame Mosque");
        masjidModel.setMasjid_id("8");
        masjidModel.setLat("24.913738");
        masjidModel.setLon("91.821491");
        masjidModel.setFajr("5:10 AM");
        masjidModel.setZahor("1:30 PM");
        masjidModel.setAsr("5:05 PM");
        masjidModel.setMagrib("6: 55 PM");
        masjidModel.setIsha("8:30 PM");
        masjid_info_list.add(masjidModel7);

        MasjidModel masjidModel8 = new MasjidModel();
        masjidModel.setMasjid_name("Topobon Residential Area Mosque");
        masjidModel.setMasjid_id("9");
        masjidModel.setLat("24.908425");
        masjidModel.setLon("91.835556");
        masjidModel.setFajr("5:15 AM");
        masjidModel.setZahor("1:30 PM");
        masjidModel.setAsr("4:55 PM");
        masjidModel.setMagrib("6: 50 PM");
        masjidModel.setIsha("8:20 PM");
        masjid_info_list.add(masjidModel8);


        MasjidModel masjidModel9 = new MasjidModel();
        masjidModel.setMasjid_name("Tilargaon Old Jame Mosque");
        masjidModel.setMasjid_id("10");
        masjidModel.setLat("24.926871");
        masjidModel.setLon("91.826922");
        masjidModel.setFajr("5:20 AM");
        masjidModel.setZahor("1:30 PM");
        masjidModel.setAsr("4:55 PM");
        masjidModel.setMagrib("6: 55 PM");
        masjidModel.setIsha("8:30 PM");
        masjid_info_list.add(masjidModel9);


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode != LocationPermissionUtils.REQUEST_CODE) {
            return;
        }

        if (LocationPermissionUtils.isPermissionGranted(new String[]{
                android.Manifest.permission.ACCESS_FINE_LOCATION}, grantResults)) {
            startLocationUpdates();
        } else {
            Toast.makeText(LocationActivity.this, "Permission Denied!!", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onStart() {
        super.onStart();

        if(fusedLocationClient != null) {
            startLocationUpdates();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (fusedLocationClient != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;

        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

}
