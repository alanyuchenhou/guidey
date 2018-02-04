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
package com.tomtom.online.sdk.samples.cases.map.manipulation.compass;

import android.content.Context;

import com.tomtom.online.sdk.map.MapConstants;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.activities.BaseFunctionalExamplePresenter;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.utils.Locations;

public class CompassAndCurrentLocationPresenter extends BaseFunctionalExamplePresenter {


    @Override
    public void bind(final FunctionalExampleFragment view, final TomtomMap map) {
        super.bind(view, map);
        if (!view.isMapRestored()) {
            centerOnAmsterdam();
            defaultMapComponentIcons();
        }
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new CompassAndCurrentLocationFunctionalExample();
    }

    @Override
    public void cleanup() {
        tomtomMap.centerOn(
                Locations.AMSTERDAM_LOCATION.getLatitude(),
                Locations.AMSTERDAM_LOCATION.getLongitude(),
                DEFAULT_ZOOM_LEVEL,
                MapConstants.ORIENTATION_NORTH);
        tomtomMap.getUiSettings().getCompassView().show();
        tomtomMap.getUiSettings().getCurrentLocationView().show();

        defaultMapComponentIcons();
    }

    @Override
    public void onResume(final Context context) {

    }

    @Override
    public void onPause() {

    }

    private void centerOnAmsterdam() {
        tomtomMap.centerOn(
                Locations.AMSTERDAM_LOCATION.getLatitude(),
                Locations.AMSTERDAM_LOCATION.getLongitude(),
                DEFAULT_ZOOM_LEVEL,
                MapConstants.ORIENTATION_NORTH_WEST);
    }

    public void defaultMapComponentIcons() {
        tomtomMap.getUiSettings().getCompassView().getView().setImageResource(R.drawable.btn_compass);
        tomtomMap.getUiSettings().getCurrentLocationView().getView().setImageResource(R.drawable.btn_current_location);
    }

    public void show() {
        centerOnAmsterdam();
        //tag::show_compass[]
        tomtomMap.getUiSettings().getCompassView().show();
        //end::show_compass[]

        //tag::show_current_location[]
        tomtomMap.getUiSettings().getCurrentLocationView().show();
        //end::show_current_location[]
    }

    public void hide() {
        centerOnAmsterdam();
        //tag::hide_compass[]
        tomtomMap.getUiSettings().getCompassView().hide();
        //end::hide_compass[]

        //tag::hide_current_location[]
        tomtomMap.getUiSettings().getCurrentLocationView().hide();
        //end::hide_current_location[]
    }

    public void customMapComponentIcons() {
        centerOnAmsterdam();
        tomtomMap.getUiSettings().getCompassView().getView()
                .setImageResource(R.drawable.ic_custom_compass);

        tomtomMap.getUiSettings().getCurrentLocationView().getView()
                .setImageResource(R.drawable.ic_map_position);
    }


}
