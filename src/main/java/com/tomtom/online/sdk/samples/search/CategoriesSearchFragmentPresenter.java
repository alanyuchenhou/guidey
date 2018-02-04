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
package com.tomtom.online.sdk.samples.search;

import com.tomtom.online.sdk.search.data.Category;
import com.tomtom.online.sdk.search.data.SearchQuery;

public class CategoriesSearchFragmentPresenter extends SearchFragmentPresenter {

    public CategoriesSearchFragmentPresenter(final SearchView presenterListener) {
        super(presenterListener);
    }

    @Override
    public void performSearch(final String text) {

        searchView.disableToggleButtons();

        //tag::doc_create_category_query_plain_text[]
        final SearchQuery searchQuery = SearchQuery.builder()
                .term(text)
                .isCategory(true)
                .location(getLastKnownPosition())
                .radius(STANDARD_RADIUS)
                .build();
        //end::doc_create_category_query_plain_text[]

        performSearch(searchQuery);
    }

    public void performSearch(final Category category) {

        searchView.disableToggleButtons();

        //tag::doc_create_category_query[]
        final SearchQuery searchQuery = SearchQuery.builder()
                .category(category)
                .location(getLastKnownPosition())
                .radius(STANDARD_RADIUS)
                .build();
        //end::doc_create_category_query[]

        performSearch(searchQuery);

    }

}
