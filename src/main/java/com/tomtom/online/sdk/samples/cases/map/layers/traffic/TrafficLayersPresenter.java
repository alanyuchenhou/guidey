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
package com.tomtom.online.sdk.samples.cases.map.layers.traffic;

import android.content.Context;

import com.tomtom.online.sdk.map.MapConstants;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.UiSettings;
import com.tomtom.online.sdk.map.model.MapTilesType;
import com.tomtom.online.sdk.map.model.MapTrafficType;
import com.tomtom.online.sdk.samples.activities.BaseFunctionalExamplePresenter;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.utils.Locations;


public class TrafficLayersPresenter extends BaseFunctionalExamplePresenter {

    private UiSettings settings;

    @Override
    public void bind(FunctionalExampleFragment view, TomtomMap map) {
        super.bind(view, map);
        settings = tomtomMap.getUiSettings();

        if (!view.isMapRestored()) {
            centerOnLondon();
        }
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new TrafficLayersFunctionalExample();
    }

    @Override
    public void cleanup() {
        hideTrafficInformations();
        settings.setMapTilesType(MapTilesType.VECTOR);
    }

    @Override
    public void onResume(Context context) {
    }

    @Override
    public void onPause() {
    }

    public void showTrafficFlowTiles() {
        //tag::doc_traffic_flow_on[]
        tomtomMap.getUiSettings().setMapTrafficType(MapTrafficType.TRAFFIC_FLOW);
        //end::doc_traffic_flow_on[]
    }

    public void showTrafficIncidents() {
        settings.setMapTrafficType(MapTrafficType.TRAFFIC_INCIDENTS);
    }

    public void showTrafficFlowAndIncidentsTiles() {
        settings.setMapTrafficType(MapTrafficType.TRAFFIC_FLOW_AND_INCIDENTS);
    }

    public void hideTrafficInformations() {
        settings.setMapTrafficType(MapTrafficType.TRAFFIC_NONE);
    }

    public void centerOnLondon() {
        tomtomMap.centerOn(Locations.LONDON_LOCATION.getLatitude(), Locations.LONDON_LOCATION.getLongitude(), DEFAULT_ZOOM_LEVEL, MapConstants.ORIENTATION_NORTH);
    }
}
