package com.guidey.app1.guidey;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.map.MapConstants;
import com.tomtom.online.sdk.map.MapFragment;
import com.tomtom.online.sdk.map.OnMapReadyCallback;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.routing.OnlineRoutingApi;
import com.tomtom.online.sdk.routing.RoutingApi;

import java.util.ArrayList;
import java.util.List;

import static com.tomtom.online.sdk.map.MapConstants.DEFAULT_ZOOM_LEVEL;

public class MapActivity extends AppCompatActivity {

    protected MapFragment mapFragment;
    protected TomtomMap tomtomMap;
    protected FloatingActionButton centerMapButton;

    protected RoutingApi routePlannerAPI;
    private static final LatLng BRUSSELS = new LatLng(50.854954, 4.3051791);
    private static final LatLng HAMBURG = new LatLng(53.5584902, 9.7877407);
    private static final LatLng ZURICH = new LatLng(47.3774336, 8.466504);
    private List<LatLng> wayPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        centerMapButton = (FloatingActionButton) findViewById(R.id.fab);
        centerMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Location myLocation = tomtomMap.getUserLocation();

                tomtomMap.centerOn(
                        myLocation.getLatitude(),
                        myLocation.getLongitude(),
                        DEFAULT_ZOOM_LEVEL,
                        MapConstants.ORIENTATION_NORTH);
            }
        });

        mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getAsyncMap(onMapReadyCallback);

        routePlannerAPI = OnlineRoutingApi.create(getApplicationContext());

        wayPoints = new ArrayList<>();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        tomtomMap.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private OnMapReadyCallback onMapReadyCallback =
            new OnMapReadyCallback() {
                @Override
                public void onMapReady(TomtomMap map) {
                    //Map is ready here
                    tomtomMap = map;
                    tomtomMap.setMyLocationEnabled(true);
                }
            };


}
