package com.greenfield.propertyapp.utils.wizard.views;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.greenfield.propertyapp.R;
import com.greenfield.propertyapp.utils.wizard.pages.Page;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationSource;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.services.android.telemetry.location.LocationEngine;
import com.mapbox.services.android.telemetry.location.LocationEngineListener;
import com.mapbox.services.android.telemetry.permissions.PermissionsListener;
import com.mapbox.services.android.telemetry.permissions.PermissionsManager;

import java.util.List;

/**
 * Created by root on 7/4/17.
 */

public class GeoFragment extends Fragment implements PermissionsListener {


    public static final int PERMISSION_ACCESS_COARSE_LOCATION = 99;
    public static final String TAG = GeoFragment.class.getName();
    protected static final String ARG_KEY = "key";
    ProgressDialog progressDialog;
    MapView mapView;
    Location myLocation;
    double longi;
    double lati;
    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private Page mPage;
    private MapboxMap map;
    private FloatingActionButton floatingActionButton;
    private LocationEngine locationEngine;
    private LocationEngineListener locationEngineListener;
    private PermissionsManager permissionsManager;
    private boolean isEndNotified;
    private ContactInfoFragment.OnFragmentInteractionListener mListener;

    public static GeoFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        GeoFragment f = new GeoFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = mCallbacks.onGetPage(mKey);
        Mapbox.getInstance(getContext(), getString(R.string.access_token));


//        mGeocoder = new Geocoder(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page_geo, container,
                false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage
                .getTitle());
        mapView = (MapView) rootView.findViewById(R.id.mapView);

        progressDialog = new ProgressDialog(getActivity());
//        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        String currentData = mPage.getData().getString(Page.SIMPLE_DATA_KEY);

        // Get the location engine object for later use.
        locationEngine = LocationSource.getLocationEngine(getContext());
        locationEngine.activate();

        mapView.onCreate(savedInstanceState);


        mapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(MapboxMap mapboxMap) {



                map = mapboxMap;
                progressDialog.hide();
            }
        });
        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.location_toggle_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (map != null) {
                    toggleGps(!map.isMyLocationEnabled());
                }
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof PageFragmentCallbacks)) {
            throw new ClassCastException(
                    "Activity must implement PageFragmentCallbacks");
        }
        mCallbacks = (PageFragmentCallbacks) activity;

    }

    @Override
    public void onDetach() {

        mListener = null;
        mCallbacks = null;
        super.onDetach();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();


    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        // Ensure no memory leak occurs if we register the location listener but the call hasn't
        // been made yet.
        if (locationEngineListener != null) {
            locationEngine.removeLocationEngineListener(locationEngineListener);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    private void toggleGps(boolean enableGps) {
        if (enableGps) {
            // Check if user has granted location permission
            permissionsManager = new PermissionsManager(this);
            if (!PermissionsManager.areLocationPermissionsGranted(getContext())) {
                permissionsManager.requestLocationPermissions(getActivity());
            } else {
                enableLocation(true);
            }
        } else {
            enableLocation(false);
        }


    }

    private void enableLocation(boolean enabled) {
        if (enabled) {
            // If we have the last location of the user, we can move the camera to that position.
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Enable GPS")
                        .setMessage("Kindly enable location")
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        PERMISSION_ACCESS_COARSE_LOCATION);
                            }
                        })
                        .create()
                        .show();

            }

            Location lastLocation = locationEngine.getLastLocation();
            if (lastLocation != null) {

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastLocation), 16));

            }

            locationEngineListener = new LocationEngineListener() {
                @Override
                public void onConnected() {
                    // No action needed here.

                }

                @Override
                public void onLocationChanged(final Location location) {
                    if (location != null) {
                        // Move the map camera to where the user location is and then remove the
                        // listener so the camera isn't constantly updating when the user location
                        // changes. When the user disables and then enables the location again, this
                        // listener is registered again and will adjust the camera once again.

                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location), 16));
                        longi = location.getLongitude();
                        lati = location.getLatitude();
                        String locationString = location.getLatitude() + "," + location.getLongitude();
                        mPage.getData().putString(Page.SIMPLE_DATA_KEY, locationString);
                        mPage.notifyDataChanged();
                        Toast.makeText(getContext(), longi + " | " + lati, Toast.LENGTH_SHORT).show();

                        locationEngine.removeLocationEngineListener(this);

                    }

                }
            };
            locationEngine.addLocationEngineListener(locationEngineListener);
            floatingActionButton.setImageResource(R.drawable.ic_location_disabled_24dp);
        } else {
            floatingActionButton.setImageResource(R.drawable.ic_my_location_24dp);
        }
        // Enable or disable the location layer on the map
        map.setMyLocationEnabled(enabled);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(getContext(), "This app needs location permissions in order to show its functionality.",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocation(true);
        } else {
            Toast.makeText(getContext(), "You didn't grant location permissions.",
                    Toast.LENGTH_LONG).show();
            getActivity().finish();
        }
    }
}