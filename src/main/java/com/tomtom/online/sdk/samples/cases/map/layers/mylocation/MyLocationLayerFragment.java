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

import com.tomtom.online.sdk.map.MapFragment;
import com.tomtom.online.sdk.map.OnMapReadyCallback;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.samples.R;
import com.tomtom.online.sdk.samples.cases.ExampleFragment;
import com.tomtom.online.sdk.samples.utils.views.OptionsButtonsView;


public class MyLocationLayerFragment extends ExampleFragment<MyLocationLayerPresenter> {

    public static final int DELAY_MILLIS = 1000;
    public static final int FIRST_INDEX = 0;

    @Override
    protected MyLocationLayerPresenter createPresenter() {
        return new MyLocationLayerPresenter();
    }

    @Override
    protected void onOptionsButtonsView(OptionsButtonsView view) {
        view.addOption(R.string.my_location_on);
        view.addOption(R.string.my_location_off);
        view.selectItem(FIRST_INDEX, true);

        initState();
    }

    private void initState() {
        MapFragment mapFragment = (MapFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getAsyncMap(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final TomtomMap tomtomMap) {
                tomtomMap.setMyLocationEnabled(false);
            }
        });
    }

    @Override
    public void onChange(boolean[] oldValues, boolean[] newValues) {
        optionsView.setEnabled(false);
        if (newValues[0]) {
            presenter.myLocationEnabled();
        } else if (newValues[1]) {
            presenter.myLocationDisabled();
        }
        optionsView.postDelayed(new Runnable() {

            public void run() {
                optionsView.setEnabled(true);
            }
        }, DELAY_MILLIS);
    }

}
