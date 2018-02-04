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
package com.tomtom.online.sdk.samples.cases;

import android.support.annotation.IdRes;

import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.cases.map.layers.shapes.ShapesCustomFragment;
import com.tomtom.online.sdk.samples.cases.map.layers.tilestypes.MapTilesTypesFragment;
import com.tomtom.online.sdk.samples.cases.map.layers.traffic.TrafficLayersFragment;
import com.tomtom.online.sdk.samples.cases.map.manipulation.centering.MapCenteringFragment;
import com.tomtom.online.sdk.samples.cases.map.manipulation.compass.CompassAndCurrentLocationFragment;
import com.tomtom.online.sdk.samples.cases.map.manipulation.events.MapManipulationEventsFragment;
import com.tomtom.online.sdk.samples.cases.map.manipulation.perspective.MapViewPerspectiveFragment;
import com.tomtom.online.sdk.samples.cases.map.markers.MarkerCustomFragment;
import com.tomtom.online.sdk.samples.cases.map.markers.balloons.BalloonCustomFragment;
import com.tomtom.online.sdk.samples.cases.route.alternatives.RouteAlternativesFragment;
import com.tomtom.online.sdk.samples.cases.route.avoid.RouteAvoidsFragment;
import com.tomtom.online.sdk.samples.cases.route.departureandarrivaltime.DepartureAndArrivalTimeFragment;
import com.tomtom.online.sdk.samples.cases.route.maneuvers.ManeuversFragment;
import com.tomtom.online.sdk.samples.cases.route.modes.RouteTravelModesFragment;
import com.tomtom.online.sdk.samples.cases.route.types.RouteTypesFragment;
import com.tomtom.online.sdk.samples.cases.route.waypoints.RouteWaypointsFragment;
import com.tomtom.online.sdk.samples.fragments.CurrentLocationFragment;
import com.tomtom.online.sdk.samples.fragments.FunctionalExampleFragment;
import com.tomtom.online.sdk.samples.license.AboutFragment;
import com.tomtom.online.sdk.samples.search.CategoriesSearchFragment;
import com.tomtom.online.sdk.samples.search.FuzzySearchFragment;
import com.tomtom.online.sdk.samples.search.LanguageSelectorSearchFragment;
import com.tomtom.online.sdk.samples.search.ReverseGeocodingFragment;
import com.tomtom.online.sdk.samples.search.SearchFragment;
import com.tomtom.online.sdk.samples.search.TypeAheadSearchFragment;

public class FunctionalExamplesFactory {

    public FunctionalExampleFragment create(@IdRes int itemId) {

        switch (itemId) {

            case R.id.maneuvers_list:
                return new ManeuversFragment();

            case R.id.route_avoids:
                return new RouteAvoidsFragment();

            case R.id.departure_and_arrival_time:
                return new DepartureAndArrivalTimeFragment();

            case R.id.route_travel_modes:
                return new RouteTravelModesFragment();

            case R.id.route_types:
                return new RouteTypesFragment();

            case R.id.traffic_layer:
                return new TrafficLayersFragment();

            case R.id.map_types:
                return new MapTilesTypesFragment();

            case R.id.mapcentering:
                return new MapCenteringFragment();

            case R.id.mapmode:
                return new MapViewPerspectiveFragment();

            case R.id.map_events:
                return new MapManipulationEventsFragment();

            case R.id.address_search:
                return new SearchFragment();

            case R.id.category_search:
                return new CategoriesSearchFragment();

            case R.id.language_selector_search:
                return new LanguageSelectorSearchFragment();

            case R.id.markers_custom:
                return new MarkerCustomFragment();

            case R.id.markers_balloons:
                return new BalloonCustomFragment();

            case R.id.custom_shapes:
                return new ShapesCustomFragment();

            case R.id.fuzzy_search:
                return new FuzzySearchFragment();

            case R.id.typeahead_search:
                return new TypeAheadSearchFragment();

            case R.id.reverse_geocoding:
                return new ReverseGeocodingFragment();

            case R.id.map_compass_and_current_location:
                return new CompassAndCurrentLocationFragment();

            case R.id.license:
                return new AboutFragment();

            case R.id.route_waypoints:
                return new RouteWaypointsFragment();

            case R.id.route_alternatives:
                return new RouteAlternativesFragment();

            default:
                return new CurrentLocationFragment();
        }
    }

}