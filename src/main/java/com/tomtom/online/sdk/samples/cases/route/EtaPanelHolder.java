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

import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tomtom.online.sdk.common.formatter.AndroidTextFormatter;
import com.tomtom.online.sdk.common.formatter.StringFormatter;
import com.tomtom.online.sdk.samples.R;

import java.util.Date;

import timber.log.Timber;

public class EtaPanelHolder {

    private final int fontSize;
    private final StringFormatter stringFormatter;
    private final AndroidTextFormatter androidTextFormatter;

    private final View etaPanel;
    private final TextView dtaView;
    private final TextView etaView;
    private final ImageView panelIcon;

    public EtaPanelHolder(View etaPanel) {
        this.etaPanel = etaPanel;
        dtaView = etaPanel.findViewById(R.id.nav_eta_dta);
        etaView = etaPanel.findViewById(R.id.nav_eta_eta);
        panelIcon = etaPanel.findViewById(R.id.nav_eta_flag);

        androidTextFormatter = new AndroidTextFormatter();
        stringFormatter = new StringFormatter(etaPanel.getContext());
        fontSize = etaPanel.getContext().getResources().getDimensionPixelSize(R.dimen.text_size_big);
    }

    public void showEtaPanel() {
        etaPanel.setVisibility(View.VISIBLE);
    }

    public void hideEtaPanel() {
        etaPanel.setVisibility(View.GONE);
    }

    public void setDistanceToArrival(int distanceToDestination, boolean useImperial) {
        Timber.v("distanceToDestination " + distanceToDestination + " useImperial " + useImperial);
        SpannableStringBuilder styleDTA = stringFormatter.styleDTA(useImperial, distanceToDestination, true, fontSize);

        androidTextFormatter.condenseTextFor(styleDTA, dtaView, etaPanel.getContext().getResources()
                .getDimensionPixelSize(R.dimen.button_width));
        dtaView.setText(styleDTA);
    }

    public void setEstimatedTimeToArrival(Date eta) {
        Timber.v("setEstimatedTimeToArrival " + eta);
        SpannableStringBuilder styleETA = stringFormatter.styleETA(eta, true, fontSize);
        androidTextFormatter.condenseTextFor(styleETA, etaView, etaPanel.getContext().getResources().getDimensionPixelSize(R.dimen.button_width));
        etaView.setText(styleETA);
    }

    public void setPanelIconToArrivalTime() {
        panelIcon.setImageDrawable(ContextCompat.getDrawable(etaPanel.getContext(), R.drawable.ic_arrival_time));
        panelIcon.setTag(R.drawable.ic_arrival_time);
    }

    public void resetPanelIconToDefault() {
        panelIcon.setImageDrawable(ContextCompat.getDrawable(etaPanel.getContext(), R.drawable.maneuver_arrival_flag));
        panelIcon.setTag(null);
    }

    public boolean isDefaultPanelIcon() {
        return panelIcon.getTag() == null;
    }
}
