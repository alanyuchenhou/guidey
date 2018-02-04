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
package com.tomtom.online.sdk.samples.cases.route.departureandarrivaltime;

import android.support.annotation.VisibleForTesting;

import com.tomtom.online.sdk.routing.data.InstructionsType;
import com.tomtom.online.sdk.routing.data.Report;
import com.tomtom.online.sdk.routing.data.RouteQuery;
import com.tomtom.online.sdk.routing.data.RouteQueryBuilder;
import com.tomtom.online.sdk.samples.activities.FunctionalExampleModel;
import com.tomtom.online.sdk.samples.cases.route.RoutePlannerPresenter;
import com.tomtom.online.sdk.samples.cases.route.RoutingUiListener;
import com.tomtom.online.sdk.samples.routes.AmsterdamToRotterdamRouteConfig;
import com.tomtom.online.sdk.samples.routes.RouteConfigExample;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DepartureAndArrivalTimePresenter extends RoutePlannerPresenter {

    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);

    public DepartureAndArrivalTimePresenter(RoutingUiListener viewModel) {
        super(viewModel);
    }

    @Override
    public FunctionalExampleModel getModel() {
        return new DepartureAndArrivalTimeFunctionalExample();
    }

    @Override
    public RouteConfigExample getRouteConfig() {
        return new AmsterdamToRotterdamRouteConfig();
    }


    public void clearRoute() {
        tomtomMap.clearRoute();
    }

    public void displayArivalAtRoute(DateTime arrivalDateTime) {
        viewModel.showRoutingInProgressDialog();
        String arrivalTime = dateFormat.format(arrivalDateTime.toDate());
        showRoute(getArrivalRouteQuery(arrivalTime));
    }

    @VisibleForTesting
    protected RouteQuery getArrivalRouteQuery(String arrivalTime) {
        //tag::doc_route_arrival_time[]
        RouteQueryBuilder queryBuilder = new RouteQueryBuilder(getRouteConfig().getOrigin(), getRouteConfig().getDestination())
                .withReport(Report.EFFECTIVE_SETTINGS)
                .withInstructionsType(InstructionsType.TEXT)
                .withArriveAt(arrivalTime);
        //end::doc_route_arrival_time[]
        return queryBuilder;
    }

    public void displayDepartureAtRoute(DateTime departureDate) {
        viewModel.showRoutingInProgressDialog();
        String departureTime = dateFormat.format(departureDate.toDate());
        showRoute(getDepartureRouteQuery(departureTime));
    }

    protected RouteQuery getDepartureRouteQuery(String departureTime) {
        //tag::doc_route_departure_time[]
        return new RouteQueryBuilder(getRouteConfig().getOrigin(), getRouteConfig().getDestination())
                .withReport(Report.EFFECTIVE_SETTINGS)
                .withInstructionsType(InstructionsType.TEXT)
                .withDepartAt(departureTime);
        //end::doc_route_departure_time[]
    }

}
