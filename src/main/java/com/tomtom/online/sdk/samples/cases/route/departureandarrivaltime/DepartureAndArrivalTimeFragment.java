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

import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.cases.route.RoutePlannerFragment;
import com.tomtom.online.sdk.samples.utils.views.OptionsButtonsView;

import org.joda.time.DateTime;

import java.util.Date;

//TODO serializable
public class DepartureAndArrivalTimeFragment extends RoutePlannerFragment<DepartureAndArrivalTimePresenter> {

    @Override
    protected DepartureAndArrivalTimePresenter createPresenter() {
        return new DepartureAndArrivalTimePresenter(this);
    }

    @Override
    protected void onOptionsButtonsView(OptionsButtonsView view) {
        view.addOption(R.string.btn_text_departure_at);
        view.addOption(R.string.btn_text_arrival_at);
    }

    @Override
    public void onPause() {
        super.onPause();
        getEtaPanelHolder().hideEtaPanel();
    }

    @Override
    public void onChange(final boolean[] oldValues, final boolean[] newValues) {
        presenter.centerOnDefaultLocation();
        getEtaPanelHolder().hideEtaPanel();
        if (newValues[0]) {
            getEtaPanelHolder().resetPanelIconToDefault();
            presenter.clearRoute();
            startDepartureAt();
        } else if (newValues[1]) {
            getEtaPanelHolder().setPanelIconToArrivalTime();
            presenter.clearRoute();
            startArivalAt();
        }
    }

    public void startDepartureAt() {
        showDateAndTimeDialog(R.string.label_departure_time, new SingleDateAndTimePickerDialog.Listener() {
            @Override
            public void onDateSelected(Date date) {
                DateTime departureDate = new DateTime(date);
                if (!isValidDate(departureDate)) {
                    return;
                }
                presenter.displayDepartureAtRoute(departureDate);
            }
        });
    }


    public void startArivalAt() {
        showDateAndTimeDialog(R.string.label_arrival_time, new SingleDateAndTimePickerDialog.Listener() {
            @Override
            public void onDateSelected(Date date) {
                DateTime arrivalDateTime = new DateTime(date);
                if (!isValidDate(arrivalDateTime)) {
                    return;
                }
                presenter.displayArivalAtRoute(arrivalDateTime);
            }
        });

    }

    private void showDateAndTimeDialog(int title, SingleDateAndTimePickerDialog.Listener listener) {
        DateTime initialDateTime = DateTime.now().plusHours(5);
        new SingleDateAndTimePickerDialog.Builder(getContext())
                .bottomSheet()
                .defaultDate(initialDateTime.toDate())
                .curved()
                .title(getResources().getString(title))
                .listener(listener)
                .display();
    }

    private boolean isValidDate(DateTime departureDate) {
        if (departureDate.isBeforeNow()) {
            showError(R.string.msg_error_date_in_past);
            return false;
        }
        return true;
    }
}
