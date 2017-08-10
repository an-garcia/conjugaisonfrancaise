/*
 * Copyright (C) 2017 Angel Garcia
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xengar.android.conjugaisonfrancaise.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.xengar.android.conjugaisonfrancaise.R;
import com.xengar.android.conjugaisonfrancaise.utils.ActivityUtils;

import static com.xengar.android.conjugaisonfrancaise.utils.Constants.ALPHABET;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.COLOR;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.COMMON_TYPE;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.CURRENT_PAGE;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.DISPLAY_COMMON_TYPE;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.DISPLAY_SORT_TYPE;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.DISPLAY_VERB_TYPE;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.FAVORITES;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.GROUPS;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.GROUP_1;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.GROUP_2;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.GROUP_3;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.GROUP_ALL;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.ITEM_TYPE;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.LAST_ACTIVITY;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.LIST;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.MAIN_ACTIVITY;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.MOST_COMMON_100;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.MOST_COMMON_25;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.MOST_COMMON_250;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.MOST_COMMON_50;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.MOST_COMMON_500;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.MOST_COMMON_ALL;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.PAGE_CARDS;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.PAGE_FAVORITES;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.PAGE_VERBS;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.SHARED_PREF_NAME;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.SORT_TYPE;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.VERB_GROUP;

/**
 * MainActivity
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private UniversalFragment verbsFragment;
    private UniversalFragment favoritesFragment;

    private final String[] VERB_TYPES = {GROUP_1, GROUP_2, GROUP_3, GROUP_ALL};
    private final int[] verbSelection = {3};
    private final String[] verbType = {VERB_TYPES[verbSelection[0]]}; // current verb type list in screen

    private final String[] SORT_TYPES = {ALPHABET, COLOR, GROUPS};
    private final int[] sortSelection = {0};
    private final String[] sortType = {SORT_TYPES[sortSelection[0]]}; // current sort type list in screen

    private final String[] COMMON_TYPES = {MOST_COMMON_25, MOST_COMMON_50, MOST_COMMON_100,
            MOST_COMMON_250, MOST_COMMON_500, MOST_COMMON_ALL};
    private final int[] commonSelection = {5};
    private final String[] commonType = {COMMON_TYPES[commonSelection[0]]}; // current most common type list in screen

    private String page = PAGE_VERBS; // Current page


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Save name of activity, in case of calling SettingsActivity
        ActivityUtils.saveStringToPreferences(getApplicationContext(), LAST_ACTIVITY,
                MAIN_ACTIVITY);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        SharedPreferences prefs = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        page = prefs.getString(CURRENT_PAGE, PAGE_VERBS);
        // read verb Type
        verbType[0] = prefs.getString(DISPLAY_VERB_TYPE, GROUP_ALL);
        for (int i = 0; i < VERB_TYPES.length ; i++ ){
            if (verbType[0].contentEquals(VERB_TYPES[i])){
                verbSelection[0] = i;
                break;
            }
        }
        sortType[0] = prefs.getString(DISPLAY_SORT_TYPE, ALPHABET);
        for (int i = 0; i < SORT_TYPES.length ; i++ ){
            if (sortType[0].contentEquals(SORT_TYPES[i])){
                sortSelection[0] = i;
                break;
            }
        }
        commonType[0] = prefs.getString(DISPLAY_COMMON_TYPE, MOST_COMMON_ALL);
        for (int i = 0; i < COMMON_TYPES.length ; i++ ){
            if (commonType[0].contentEquals(COMMON_TYPES[i])){
                commonSelection[0] = i;
                break;
            }
        }

        verbsFragment = createUniversalFragment(verbType[0], LIST, sortType[0], commonType[0]);
        //cardsFragment = createUniversalFragment(verbType[0], CARD, sortType[0], commonType[0]);
        favoritesFragment = createUniversalFragment(FAVORITES, LIST, sortType[0], MOST_COMMON_ALL);
        showPage(page);
        assignCheckedItem(page);
        //checkFirstRun(getApplicationContext(), mFirebaseAnalytics);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_change_group:
                //changeVerbGroup();
                return true;

            case R.id.action_sort:
                //sortVerbs();
                return true;

            case R.id.action_most_common:
                //showMostCommon();
                return true;

            case R.id.action_search:
                //ActivityUtils.launchSearchActivity(getApplicationContext());
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Creates a Fragment.
     * @param verbGroup Type of verbs to display GROUP_1, GROUP_2, GROUP_3, ALL_GROUPS
     * @param itemType Display mode LIST, CARD
     * @param sortType Alphabet, groups
     * @param commonType Display Top 25, Top 50, Top 100, Top 250, Top 500
     * @return fragment
     */
    private UniversalFragment createUniversalFragment(String verbGroup, String itemType,
                                                      String sortType, String commonType) {
        UniversalFragment fragment = new UniversalFragment();
        Bundle bundle = new Bundle();
        bundle.putString(VERB_GROUP, verbGroup);
        bundle.putString(ITEM_TYPE, itemType);
        bundle.putString(SORT_TYPE, sortType);
        bundle.putString(COMMON_TYPE, commonType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_verbs:
                page = PAGE_VERBS;
                //noinspection ConstantConditions
                getSupportActionBar().setTitle(R.string.verbs);
                ActivityUtils.saveStringToPreferences(this, CURRENT_PAGE, PAGE_VERBS);
                launchFragment(PAGE_VERBS);
                break;
            case R.id.nav_favorites:
                //page = PAGE_FAVORITES;
                //noinspection ConstantConditions
                getSupportActionBar().setTitle(R.string.favorites);
                //ActivityUtils.saveStringToPreferences(this, CURRENT_PAGE, PAGE_FAVORITES);
                //launchFragment(PAGE_FAVORITES);
                break;
            case R.id.nav_settings:
                ActivityUtils.launchSettingsActivity(getApplicationContext());
                break;
            case R.id.nav_help:
                ActivityUtils.launchHelpActivity(getApplicationContext());
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Launches the selected fragment.
     * @param category The type of search
     */
    private void launchFragment(String category) {
        android.support.v4.app.FragmentTransaction fragmentTransaction
                = getSupportFragmentManager().beginTransaction();
        switch (category) {
            case PAGE_VERBS:
                fragmentTransaction.replace(R.id.fragment_container, verbsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case PAGE_CARDS:
                //fragmentTransaction.replace(R.id.fragment_container, cardsFragment);
                //fragmentTransaction.addToBackStack(null);
                //fragmentTransaction.commit();
                break;
            case PAGE_FAVORITES:
                fragmentTransaction.replace(R.id.fragment_container, favoritesFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
        }
    }

    /***
     * Shows the correct page on screen.
     * @param page name of page
     */
    private void showPage(String page) {
        switch (page){
            case PAGE_VERBS:
                //noinspection ConstantConditions
                getSupportActionBar().setTitle(R.string.verbs);
                ActivityUtils.saveStringToPreferences(this, CURRENT_PAGE, PAGE_VERBS);
                //ActivityUtils.firebaseAnalyticsLogEventSelectContent(
                //        mFirebaseAnalytics, PAGE_VERBS, PAGE_VERBS, TYPE_PAGE);
                launchFragment(PAGE_VERBS);
                break;
            /*case PAGE_CARDS:
                //noinspection ConstantConditions
                getSupportActionBar().setTitle(R.string.verbs);
                ActivityUtils.saveStringToPreferences(this, CURRENT_PAGE, PAGE_CARDS);
                ActivityUtils.firebaseAnalyticsLogEventSelectContent(
                        mFirebaseAnalytics, PAGE_CARDS, PAGE_CARDS, TYPE_PAGE);
                launchFragment(PAGE_CARDS);
                break;*/
            case PAGE_FAVORITES:
                //noinspection ConstantConditions
                getSupportActionBar().setTitle(R.string.favorites);
                ActivityUtils.saveStringToPreferences(this, CURRENT_PAGE, PAGE_FAVORITES);
                //ActivityUtils.firebaseAnalyticsLogEventSelectContent(
                //        mFirebaseAnalytics, PAGE_FAVORITES, PAGE_FAVORITES, TYPE_PAGE);
                launchFragment(PAGE_FAVORITES);
                break;
        }
    }

    private void assignCheckedItem(String page){
        // set selected
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        switch (page){
            case PAGE_VERBS:
                navigationView.setCheckedItem(R.id.nav_verbs);
                break;
            case PAGE_CARDS:
                //navigationView.setCheckedItem(R.id.nav_cards);
                break;
            case PAGE_FAVORITES:
                navigationView.setCheckedItem(R.id.nav_favorites);
                break;
        }
    }
}
