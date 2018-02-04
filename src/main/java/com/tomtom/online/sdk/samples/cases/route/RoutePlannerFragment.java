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
package com.tomtom.online.sdk.samples.cases.route;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;

import com.tomtom.online.sdk.routing.data.FullRoute;
import com.tomtom.online.sdk.routing.data.Summary;
import com.tomtom.online.sdk.samples.cases.ExampleFragment;
import com.tomtom.online.sdk.samples.utils.CheckedButtonCleaner;
import com.tomtom.online.sdk.samples.utils.views.OptionsButtonsView;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public abstract class RoutePlannerFragment<T extends RoutePlannerPresenter> extends ExampleFragment<T>
        implements CheckedButtonCleaner, RoutingUiListener {


    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    private static final String KEY_ROUTING_IN_PROGRESS = "ROUTING_IN_PROGRESS";
    private static final String KEY_ETA_ATA = "KEY_ETA_ATA";
    private static final String KEY_ETA_DTA = "KEY_ETA_DTA";
    private static final String KEY_ETA_ICON = "KEY_ICON";
    private static final String KEY_ROUTES = "KEY_ROUTES";
    private static final String KEY_SELECTED_ROUTE = "KEY_ROUTES_SELECTED";

    private int distance;
    private Date estimatedTimeArrival;
    private RoutePlanningInProgressDialog routingInProgressDialog = new RoutePlanningInProgressDialog();
    private boolean routeInProgress = false;
    private int selectedKey;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onRestoreSavedInstanceState(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.setCheckedButtonCleaner(this);
    }

    @Override
    public void cleanCheckedButtons() {
        optionsView.unSelectAll();
    }


    @Override
    protected void onOptionsButtonsView(OptionsButtonsView view) {

    }


    @Override
    public void repeatRequestWhenNotFinished() {
        if (routeInProgress) {
            optionsView.selectItem(selectedKey, true);
            optionsView.performClickSelected();
        }
    }

    @Override
    public void onChange(boolean[] oldValues, boolean[] newValues) {

    }

    @Override
    public void showRoutingInProgressDialog() {
        routeInProgress = true;
        optionsView.setEnabled(false);
        routingInProgressDialog.show(getActivity().getSupportFragmentManager(), KEY_ROUTING_IN_PROGRESS);
    }

    @Override
    public void hideRoutingInProgressDialog() {
        if (routingInProgressDialog.isAdded()) {
            routingInProgressDialog.dismiss();
        }
        optionsView.setEnabled(true);
    }

    @Override
    public void routeUpdated(FullRoute route) {
        getEtaPanelHolder().showEtaPanel();
        Summary routeSummary = route.getSummary();
        distance = routeSummary.getLengthInMeters();
        getEtaPanelHolder().setDistanceToArrival(distance, false);

        try {
            if (getEtaPanelHolder().isDefaultPanelIcon()) {
                estimatedTimeArrival = dateFormat.parse(routeSummary.getArrivalTime());
            } else {
                estimatedTimeArrival = dateFormat.parse(routeSummary.getDepartureTime());
            }
        } catch (ParseException e) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(0);
            estimatedTimeArrival = calendar.getTime();
        }

        getEtaPanelHolder().setEstimatedTimeToArrival(estimatedTimeArrival);
        routeInProgress = false;
    }


    @Override
    public void showError(@StringRes int message) {
        new AlertDialog.Builder(getContext()).setMessage(message).show();
    }

    public void onRestoreSavedInstanceState(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            boolean isIconDefault = savedInstanceState.getBoolean(KEY_ETA_ICON);
            estimatedTimeArrival = (Date) savedInstanceState.getSerializable(KEY_ETA_ATA);
            distance = savedInstanceState.getInt(KEY_ETA_DTA);
            routeInProgress = savedInstanceState.getBoolean(KEY_ROUTING_IN_PROGRESS);
            selectedKey = savedInstanceState.getInt(KEY_SELECTED_ROUTE);
            if (routeInProgress) {
                getEtaPanelHolder().hideEtaPanel();
                return;
            }


            Map<Long, FullRoute> routes = (Map<Long, FullRoute>) savedInstanceState.getSerializable(KEY_ROUTES);
            presenter.setRoutesMap(routes);

            getEtaPanelHolder().setDistanceToArrival(distance, false);
            getEtaPanelHolder().setEstimatedTimeToArrival(estimatedTimeArrival);
            if (!isIconDefault) {
                getEtaPanelHolder().setPanelIconToArrivalTime();
            }

            getEtaPanelHolder().showEtaPanel();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(KEY_ETA_ICON, getEtaPanelHolder().isDefaultPanelIcon());
        outState.putSerializable(KEY_ETA_ATA, estimatedTimeArrival);
        outState.putInt(KEY_ETA_DTA, distance);
        outState.putSerializable(KEY_ROUTES, (Serializable) presenter.getRoutesMap());
        outState.putBoolean(KEY_ROUTING_IN_PROGRESS, routeInProgress);
        outState.putInt(KEY_SELECTED_ROUTE, optionsView.getFirstSelectedItem());
        super.onSaveInstanceState(outState);
    }

}
