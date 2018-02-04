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
package com.tomtom.online.sdk.samples.cases.route.maneuvers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.tomtom.online.sdk.routing.data.Instruction;
import com.tomtom.online.sdk.samples.R;

//Dummy implementation, just to get the example
public class ManeuversIconProvider {

    public Drawable getIcon(Context context, Instruction instruction) {

        String iconName;
        String maneuver = instruction.getManeuver();
        int angleInDecimalDegree = Math.abs(instruction.getTurnAngleInDecimalDegrees());

        if (maneuver.contains("TURN") || maneuver.contains("SHARP")) {
            String[] maneuverParts = maneuver.split("_");
            iconName = "maneuver_" + maneuverParts[maneuverParts.length - 1].toLowerCase() + "_" + angleInDecimalDegree;
        } else if (maneuver.contains("ROUNDABOUT")) {
            iconName = "maneuver_roundabout_right_" + angleInDecimalDegree;
        } else if (maneuver.contains("ENTER")) {
            iconName = "maneuver_left_45";
        } else if (maneuver.contains("EXIT")) {
            iconName = "maneuver_right_45";
        } else if (maneuver.contains("ARRIVE")) {
            iconName = "maneuver_arrival_flag";
        } else {
            iconName = "maneuver_straight";
        }

        int resId = context.getResources().getIdentifier(iconName, "drawable", context.getPackageName());
        if(resId == 0){
            resId = R.drawable.maneuver_straight;
        }

        return ContextCompat.getDrawable(context, resId);
    }

}
