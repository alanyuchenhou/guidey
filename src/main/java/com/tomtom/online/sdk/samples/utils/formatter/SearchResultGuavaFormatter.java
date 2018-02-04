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
package com.tomtom.online.sdk.samples.utils.formatter;

import com.google.common.base.Joiner;
import com.tomtom.online.sdk.search.data.SearchResult;


public class SearchResultGuavaFormatter implements SearchResultFormatter {

    public static final String SEPARATOR = " ";
    public static final String UNIT = "km";

    @Override
    public String formatTitleWithDistance(SearchResult resultItem, double distance) {
        return Joiner.on(SEPARATOR)
                .skipNulls()
                .join(new String[]{getDistanceInBrackets(distance), formatTitle(resultItem)})
                .trim();
    }

    private String getDistanceInBrackets(double distance) {
        return String.format("( %.1f %s )", distance, UNIT);
    }

    @Override
    public String formatTitle(SearchResult resultItem) {
        return Joiner.on(SEPARATOR)
                .skipNulls()
                .join(new String[]{resultItem.getName(), resultItem.getAddressLine1(), resultItem.getAddressLine2(),
                        resultItem.getCountryCode()})
                .trim();
    }
}
