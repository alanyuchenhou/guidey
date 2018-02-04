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
package com.tomtom.online.sdk.samples.utils;

import com.tomtom.online.sdk.common.location.LatLng;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class DistanceCalculator {

    public static double EARTH_RADIUS = 6371.01; // Earth's radius in Kilometers

    public static double calcDistInKilometers(LatLng destination, LatLng origin) {
        double nDLat = degreesToRadians(destination.getLatitude() - origin.getLatitude());
        double nDLon = degreesToRadians(destination.getLongitude() - origin.getLongitude());

        double fromLat = degreesToRadians(origin.getLatitude());
        double toLat = degreesToRadians(destination.getLatitude());

        double nA = pow(sin(nDLat / 2), 2) + cos(fromLat) * cos(toLat) * pow(sin(nDLon / 2), 2);
        double nC = 2 * atan2(sqrt(nA), sqrt(1 - nA));

        double nD = EARTH_RADIUS * nC;
        return nD;
    }

    public static double degreesToRadians(double degrees) {
        return degrees * PI / 180.0;
    }

}

