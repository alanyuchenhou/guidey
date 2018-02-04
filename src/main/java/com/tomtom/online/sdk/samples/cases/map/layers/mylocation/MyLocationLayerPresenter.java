/**
 * Copyright (c) 2015-2018 TomTom N.V. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom N.V. and its subsidiaries and may be used
 * for internal evaluation purposes or commercial use strictly subject to separate licensee
 * agreement between you and TomTom. If you are the licensee, you are only permitted to use
 * this Software in accordance with the terms of your license agreement. If you are not the
 * licensee then you are not authorised to use this software in any manner and should
 * immediately return it to TomTom N.V.
 */
package com.tomtom.online.sdk.samples.cases.map.layers.mylocation;

import android.content.Context;
import android.location.Location;

import com.tomtom.online.sdk.map.MapConstants;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.samples.activities.BaseFunctionalExamplePresenter;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.utils.Locations;

public class MyLocationLayerPresenter extends BaseFunctionalExamplePresenter {

    @Override
    public void bind(final FunctionalExampleFragment view, TomtomMap map) {
        super.bind(view, map);
        centerOnAmsterdam();
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new MyLocationLayerFunctionalExample();
    }

    @Override
    public void cleanup() {
        tomtomMap.setMyLocationEnabled(true);
    }

    @Override
    public void onResume(Context context) {

    }

    @Override
    public void onPause() {

    }

    public Location getCurrentLocation(){
        //tag::doc_get_current_location[]
        Location location = tomtomMap.getUserLocation();
        //end::doc_get_current_location[]
        return location;
    }

    public void myLocationDisabled() {
        //tag::doc_my_location_disabled[]
        tomtomMap.setMyLocationEnabled(false);
        //end::doc_my_location_disabled[]
    }


    public void myLocationEnabled() {
        //tag::doc_my_location_enabled[]
        tomtomMap.setMyLocationEnabled(true);
        //end::doc_my_location_enabled[]
    }

    public void centerOnAmsterdam() {

        tomtomMap.centerOn(
                Locations.AMSTERDAM_LOCATION.getLatitude(),
                Locations.AMSTERDAM_LOCATION.getLongitude(),
                DEFAULT_ZOOM_LEVEL,
                MapConstants.ORIENTATION_NORTH);

    }
}
