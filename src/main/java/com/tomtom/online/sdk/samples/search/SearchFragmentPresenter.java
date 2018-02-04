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

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.common.util.Contextable;
import com.tomtom.online.sdk.location.FusedLocationSource;
import com.tomtom.online.sdk.location.LocationRequestsFactory;
import com.tomtom.online.sdk.location.LocationSource;
import com.tomtom.online.sdk.location.Locations;
import com.tomtom.online.sdk.search.OnlineSearchAPI;
import com.tomtom.online.sdk.search.SearchAPI;
import com.tomtom.online.sdk.search.data.SearchQuery;
import com.tomtom.online.sdk.search.data.SearchQueryBuilder;
import com.tomtom.online.sdk.search.data.SearchResponse;
import com.tomtom.online.sdk.search.data.SearchResult;
import com.tomtom.online.sdk.search.extensions.SearchService;
import com.tomtom.online.sdk.search.extensions.SearchServiceConnectionCallback;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class SearchFragmentPresenter implements SearchPresenter,
        LocationSource.LocationUpdateListener, SearchServiceConnectionCallback, Contextable {

    protected final static String LAST_SEARCH_QUERY_BUNDLE_KEY = "LAST_SEARCH_QUERY_BUNDLE_KEY";
    public static final int STANDARD_RADIUS = 30 * 1000; //30 km

    protected SearchService searchService;
    protected SearchView searchView;
    protected LocationSource locationSource;
    protected Disposable currentRequest;

    protected SearchQuery lastSearchQuery;


    public SearchFragmentPresenter(SearchView searchView) {
        Timber.d("SearchFragmentPresenter()");
        this.searchView = searchView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            lastSearchQuery = savedInstanceState.getParcelable(LAST_SEARCH_QUERY_BUNDLE_KEY);
        }
    }

    @Override
    public void onCreate(Context context) {
        Timber.d("onCreate()");

        locationSource = getLocationSource(context);
    }

    @NonNull
    protected SearchAPI createSearchApi() {
        Context context = searchView.getContext();
        //tag::doc_create_search_object[]
        SearchAPI search = OnlineSearchAPI
                .create(context)
                .callsRequireInternet(context);
        //end::doc_create_search_object[]
        return search;
    }

    @NonNull
    public FusedLocationSource getLocationSource(Context context) {
        return new FusedLocationSource(context, this, LocationRequestsFactory.create().createSearchLocationRequest());
    }


    @Override
    public void onResume() {
        Timber.d("onResume()");
        //tag::doc_location_source_activation[]
        locationSource.activate();
        //end::doc_location_source_activation[]
    }

    @Override
    public void onPause() {
        Timber.d("onPause()");
        //tag::doc_location_source_deactivation[]
        locationSource.deactivate();
        //end::doc_location_source_deactivation[]
        if (currentRequest != null) {
            currentRequest.dispose();
        }
    }

    @Override
    public void performSearch(String text) {
        Timber.d("performSearch(): %s", text);

        if (TextUtils.isEmpty(text)) {
            return;
        }

        performSearch(createSimpleQuery(text));
    }

    @Override
    public void performSearch(String query, String lang) {
        Timber.d("performSearch(): %s", query);

        if (TextUtils.isEmpty(query)) {
            return;
        }

        performSearch(createSimpleQuery(query, lang));
    }

    @Override
    public void performSearchWithPosition(String text) {
        Timber.d(";performSearchWithPosition(): %s", text);

        if (TextUtils.isEmpty(text)) {
            return;
        }

        performSearch(createQueryWithPosition(text, getLastKnownPosition()));
    }


    protected void searchFinished() {
        Timber.d("searchFinished()");
        enableSearchUI();
        searchView.getSearchProgressBar().setVisibility(View.GONE);
        currentRequest.dispose();
    }


    protected void performSearch(SearchQuery query) {

        disableSearchUI();
        cancelPreviousSearch();

        performSearchWithoutBlockingUI(query);
    }

    protected void performSearchWithoutBlockingUI(SearchQuery query) {
        searchView.getSearchProgressBar().setVisibility(View.VISIBLE);

        lastSearchQuery = query;

        currentRequest =
                //tag::doc_perform_search[]
                getSearchProvider().search(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError(new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                                if (!currentRequest.isDisposed()) {
                                    searchView.showSearchFailedMessage(throwable.getMessage());
                                    searchView.updateSearchResults(new ArrayList<SearchResult>());
                                    searchFinished();
                                }
                            }

                        })
                        .subscribe(new Consumer<SearchResponse>() {
                            @Override
                            public void accept(SearchResponse response) throws Exception {

                                if (!currentRequest.isDisposed()) {
                                    searchView.updateSearchResults(response.getSearchResults());
                                    searchFinished();
                                }
                            }

                        });
        //end::doc_perform_search[]
    }

    protected SearchService getSearchProvider() {
        return searchService;
    }

    protected SearchQuery createSimpleQuery(String text) {
        Timber.d("createSimpleQuery(): %s", text);
        return getSimpleQueryBuilderWithTerm(text)
                .build();

    }

    protected SearchQuery createSimpleQuery(String text, String lang) {
        Timber.d("createSimpleQuery(): %s, %s", text, lang);
        return
                //tag::doc_create_simple_query_with_lang[]
                getSimpleQueryBuilderWithTerm(text)
                        .language(lang)
                        .build();
        //end::doc_create_simple_query_with_lang[]
    }

    private SearchQueryBuilder getSimpleQueryBuilderWithTerm(String text) {
        return
                //tag::doc_create_basic_query[]
                SearchQuery.builder().term(text);
        //end::doc_create_basic_query[]

    }


    protected SearchQuery createQueryWithPosition(String text, LatLng position) {

        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }

        return
                //tag::doc_create_query_with_position[]
                SearchQuery.builder()
                        .term(text)
                        .location(position).radius(STANDARD_RADIUS)
                        .build();
        //end::doc_create_query_with_position[]
    }

    @Override
    public LatLng getLastKnownPosition() {
        Location location = locationSource.getLastKnownLocation();
        if (location == null) {
            location = Locations.AMSTERDAM;
        }
        return new LatLng(location);
    }

    @Override
    public void enableSearchUI() {
        searchView.enableToggleButtons();
        searchView.enableInputField();
    }

    @Override
    public void disableSearchUI() {
        searchView.disableToggleButtons();
        searchView.disableInputField();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if (lastSearchQuery != null) {
            outState.putParcelable(LAST_SEARCH_QUERY_BUNDLE_KEY, lastSearchQuery);
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        Timber.d("onLocationChanged()");
        searchView.refreshSearchResults();
    }

    //tag::doc_search_service_connection_callback[]
    @Override
    public void onBindSearchService(SearchService service) {
        searchService = service;
        repeatLastSearchIfRequired();
    }
    //end::doc_search_service_connection_callback[]

    public void cancelPreviousSearch() {
        //tag::doc_cancel_search[]
        getSearchProvider().cancelSearchIfRunning();
        //end::doc_cancel_search[]
    }

    protected void repeatLastSearchIfRequired() {

        if (lastSearchQuery == null) {
            searchView.updateSearchResults(new ArrayList<SearchResult>());
            return;
        }

        performSearch(lastSearchQuery);
    }

    @Override
    public Context getContext() {
        return null;
    }
}
