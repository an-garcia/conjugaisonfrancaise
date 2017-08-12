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

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.colorpicker.ColorPickerPalette;
import com.android.colorpicker.ColorPickerSwatch;
import com.xengar.android.conjugaisonfrancaise.R;
import com.xengar.android.conjugaisonfrancaise.data.Verb;
import com.xengar.android.conjugaisonfrancaise.data.VerbContract;
import com.xengar.android.conjugaisonfrancaise.utils.ActivityUtils;

import java.util.Locale;

import static com.xengar.android.conjugaisonfrancaise.data.VerbContract.VerbEntry.COLUMN_ID;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.DEMO_MODE;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.LOG;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.VERB_ID;

/**
 * DetailsActivity
 */
public class DetailsActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {

    private static final String TAG = DetailsActivity.class.getSimpleName();

    private static final int EXISTING_VERB_LOADER = 0;
    private FloatingActionButton fabAdd, fabDel;
    private long verbID = -1;
    private Verb verb;
    private TextToSpeech tts;
    private TextView infinitive, group, pastParticiple;
    private TextView pInfinitive, pSimplePast, pPastParticiple;
    private TextView definition, translation, sample1, sample2, sample3;

    //private FirebaseAnalytics mFirebaseAnalytics;
    //private AdView mAdView;
    //private LogAdListener listener;

    // Demo
    //private ShowcaseView showcaseView;
    private boolean demo = false;
    //private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        // changing status bar color
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            demo = bundle.getBoolean(DEMO_MODE, false);
            verbID = bundle.getLong(VERB_ID, -1);
        } else {
            if (LOG) {
                Log.e(TAG, "bundle is null! This should not happen. verbId needed");
            }
        }

        //Text
        infinitive = (TextView) findViewById(R.id.infinitive);
        group = (TextView) findViewById(R.id.groupe);
        definition = (TextView) findViewById(R.id.definition);
        translation = (TextView) findViewById(R.id.translation);
        sample1 = (TextView) findViewById(R.id.sample1);
        sample2 = (TextView) findViewById(R.id.sample2);
        sample3 = (TextView) findViewById(R.id.sample3);

        // define click listeners
        LinearLayout header = (LinearLayout) findViewById(R.id.play_infinitive);
        header.setOnClickListener(this);
        header = (LinearLayout) findViewById(R.id.play_definition);
        header.setOnClickListener(this);
        header = (LinearLayout) findViewById(R.id.play_sample1);
        header.setOnClickListener(this);
        header = (LinearLayout) findViewById(R.id.play_sample2);
        header.setOnClickListener(this);
        header = (LinearLayout) findViewById(R.id.play_sample3);
        header.setOnClickListener(this);

        // initialize Speaker
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.FRENCH);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        if (LOG) {
                            Log.e("TTS", "This Language is not supported");
                        }
                    }
                } else {
                    if (LOG) {
                        Log.e("TTS", "Initilization Failed!");
                    }
                }
            }
        });

        // Initialize a loader to read the verb data from the database and display it
        getLoaderManager().initLoader(EXISTING_VERB_LOADER, null, this);
        showFavoriteButtons();

        // Obtain the FirebaseAnalytics instance.
        //mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        //ActivityUtils.firebaseAnalyticsLogEventSelectContent(
        //        mFirebaseAnalytics, PAGE_VERB_DETAILS, PAGE_VERB_DETAILS, TYPE_PAGE);

        // create AdMob banner
        //listener = new LogAdListener(mFirebaseAnalytics, DETAILS_ACTIVITY);
        //mAdView = ActivityUtils.createAdMobBanner(this, listener);

        //if (demo){
        //    defineDemoMode();
        //}
    }

    /** Called when leaving the activity */
    @Override
    public void onPause() {
        //if (mAdView != null) {
        //    mAdView.pause();
        //}
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        //if (mAdView != null) {
        //    mAdView.resume();
        //}
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        //if (mAdView != null) {
        //    mAdView.destroy();
        //}
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_change_color:
                changeColorDialog();
                return true;

            case R.id.action_search:
                ActivityUtils.launchSearchActivity(getApplicationContext());
                return true;

            case R.id.action_share:
                ActivityUtils.launchShareText(this, createShareText());
                //String verbName = (verb != null)? verb.getInfinitive() : "verb name not available";
                //ActivityUtils.firebaseAnalyticsLogEventSelectContent(mFirebaseAnalytics,
                //        "Verb: " + verbName + ", VerbId: " + verbID, TYPE_SHARE, TYPE_SHARE);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Create the text to share.
     * @return String
     */
    private String createShareText() {
        String text = "";
        if (verb != null) {
            text = "Infinitive: " + verb.getInfinitive()
                    + "\n\nDefinition:\n" + verb.getDefinition()
                    + "\n\nExamples:\n" + verb.getSample1()
                    + "\n" + verb.getSample2()
                    + "\n" + verb.getSample3();
        }
        return text;
    }

    /**
     * Changes the color
     */
    private void changeColorDialog() {
        final int[] colors = {
                ContextCompat.getColor(getApplicationContext(), R.color.colorBlack),
                ContextCompat.getColor(getApplicationContext(), R.color.colorRed),
                ContextCompat.getColor(getApplicationContext(), R.color.colorGreen),
                ContextCompat.getColor(getApplicationContext(), R.color.colorBlue),
                ContextCompat.getColor(getApplicationContext(), R.color.colorPink),
                ContextCompat.getColor(getApplicationContext(), R.color.colorPurple),
                ContextCompat.getColor(getApplicationContext(), R.color.colorDeepPurple),
                ContextCompat.getColor(getApplicationContext(), R.color.colorIndigo),
                ContextCompat.getColor(getApplicationContext(), R.color.colorOrange),
                ContextCompat.getColor(getApplicationContext(), R.color.colorDeepOrange),
                ContextCompat.getColor(getApplicationContext(), R.color.colorBrown),
                ContextCompat.getColor(getApplicationContext(), R.color.colorBlueGray) };

        final int[] selectedColor = {colors[0]};
        if (verb!= null) {
            selectedColor[0] = verb.getColor();
        }
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        final ColorPickerPalette colorPickerPalette = (ColorPickerPalette) layoutInflater
                .inflate(R.layout.custom_picker, null);

        ColorPickerSwatch.OnColorSelectedListener listener = new ColorPickerSwatch.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int color) {
                selectedColor[0] = color;
                colorPickerPalette.drawPalette(colors, selectedColor[0]);
            }
        };

        colorPickerPalette.init(colors.length, 4, listener);
        colorPickerPalette.drawPalette(colors, selectedColor[0]);

        AlertDialog alert = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
                .setTitle(R.string.select_color)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Save changes
                        saveColor(selectedColor[0]);
                        setVerbColor(selectedColor[0]);
                        verb.setColor(selectedColor[0]);
                    }
                })
                .setView(colorPickerPalette)
                .create();
        alert.show();
    }

    /**
     * Save the color to database.
     * @param color Color
     */
    private void saveColor(int color) {
        ContentValues values = new ContentValues();
        values.put(VerbContract.VerbEntry.COLUMN_COLOR, "" + color);
        int rowsAffected = getContentResolver().update(
                VerbContract.VerbEntry.CONTENT_VERBS_URI, values,
                COLUMN_ID + " = ?", new String[]{Long.toString(verbID)});

        // Show a toast message depending on whether or not the update was successful.
        if (rowsAffected == 0) {
            // If no rows were affected, then there was an error with the update.
            if (LOG){
                Log.e(TAG, "Failed to change color to verb!");
            }
        }
    }

    /**
     * Defines if add or remove from Favorites should be initially visible for this movieId.
     */
    private void showFavoriteButtons() {
        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        fabDel = (FloatingActionButton) findViewById(R.id.fab_minus);

        Cursor cursor = getContentResolver().query(VerbContract.VerbEntry.CONTENT_FAVORITES_URI,
                new String[]{ COLUMN_ID}, //select
                COLUMN_ID + " = ?" , // where
                new String[]{Long.toString(verbID)}, //whereArgs
                null);
        if (cursor != null && cursor.getCount() != 0) {
            fabDel.setVisibility(View.VISIBLE);
        } else {
            fabAdd.setVisibility(View.VISIBLE);
        }
        if (cursor != null)
            cursor.close();
    }

    /**
     * Defines what to do when click on add/remove from Favorites buttons.
     */
    private void defineClickFavoriteButtons() {
        final int DURATION = 1000;

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getString(R.string.favorites_add_message), DURATION)
                        .setAction("Action", null).show();
                ContentValues values = new ContentValues();
                values.put(COLUMN_ID, verbID);
                getContentResolver().insert(VerbContract.VerbEntry.CONTENT_FAVORITES_URI, values);

                fabAdd.setVisibility(View.INVISIBLE);
                fabDel.setVisibility(View.VISIBLE);
                //String verbName = (verb != null)? verb.getInfinitive() : "verb name not available";
                //ActivityUtils.firebaseAnalyticsLogEventSelectContent(mFirebaseAnalytics,
                //        VERB_ID + " " + verbID, verbName, TYPE_ADD_FAV);
            }
        });

        fabDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getString(R.string.favorites_del_message), DURATION)
                        .setAction("Action", null).show();
                getContentResolver().delete(VerbContract.VerbEntry.CONTENT_FAVORITES_URI,
                        COLUMN_ID + " = ?",
                        new String[]{Long.toString(verbID)} );

                fabAdd.setVisibility(View.VISIBLE);
                fabDel.setVisibility(View.INVISIBLE);
                //String verbName = (verb != null)? verb.getInfinitive() : "verb name not available";
                //ActivityUtils.firebaseAnalyticsLogEventSelectContent(mFirebaseAnalytics,
                //        VERB_ID + " " + verbID, verbName, TYPE_DEL_FAV);
            }
        });
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = ActivityUtils.allVerbColumns();

        return new CursorLoader(this,   // Parent activity context
                VerbContract.VerbEntry.CONTENT_VERBS_URI,
                projection,             // Columns to include in the resulting Cursor
                COLUMN_ID + " = ?",     // selection clause
                new String[]{Long.toString(verbID)}, // selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            finish(); // the verb doesn't exist, this should not happen.
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            verb = ActivityUtils.verbFromCursor(cursor);
            //noinspection ConstantConditions
            getSupportActionBar().setTitle(verb.getInfinitive());
            setVerbColor(verb.getColor());
            fillVerbDetails(verb);
            defineClickFavoriteButtons();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    /**
     * Fill verb details.
     * @param verb Verb
     */
    private void fillVerbDetails(Verb verb) {
        // Update the views on the screen with the values from the database
        infinitive.setText(verb.getInfinitive());
        switch (verb.getGroup()) {
            case 1:
                group.setText(getString(R.string.group1));
                break;
            case 2:
                group.setText(getString(R.string.group2));
                break;
            case 3:
                group.setText(getString(R.string.group3));
                break;
        }

        definition.setText(verb.getDefinition());
        sample1.setText(verb.getSample1());
        sample2.setText(verb.getSample2());
        sample3.setText(verb.getSample3());

        int fontSize = Integer.parseInt(ActivityUtils.getPreferenceFontSize(getApplicationContext()));
        infinitive.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        definition.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        sample1.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        sample2.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        sample3.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);

        ActivityUtils.setTranslation(getApplicationContext(), translation, verb);

        changeTextFontInConjugation(fontSize);

        // Try to put the verb image
        //ImageView imageVerb = (ImageView) findViewById(R.id.verb_image);
        //int imageId = getResources().getIdentifier(VERB  + verb.getInfinitive() , DRAWABLE,
        //        getPackageName());
        //if (imageId != 0) {
        //    ActivityUtils.setImage(getApplicationContext(), imageVerb, imageId);
        //}

        //ActivityUtils.firebaseAnalyticsLogEventViewItem(
        //        mFirebaseAnalytics, "" + verbID, verb.getInfinitive(), VERBS);
    }

    /**
     * Changes text font size.
     * @param fontSize
     */
    private void changeTextFontInConjugation(int fontSize) {
        int unit = TypedValue.COMPLEX_UNIT_SP;
        ((TextView)findViewById(R.id.indicative_present_je)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_present_tu)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_present_il)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_present_nous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_present_vous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_present_ils)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_passe_compose_je)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_passe_compose_tu)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_passe_compose_il)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_passe_compose_nous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_passe_compose_vous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_passe_compose_ils)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_imperfait_je)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_imperfait_tu)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_imperfait_il)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_imperfait_nous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_imperfait_vous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_imperfait_ils)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_plus_que_parfait_je)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_plus_que_parfait_tu)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_plus_que_parfait_il)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_plus_que_parfait_nous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_plus_que_parfait_vous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_plus_que_parfait_ils)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_passe_simple_je)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_passe_simple_tu)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_passe_simple_il)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_passe_simple_nous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_passe_simple_vous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_passe_simple_ils)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_passe_anterieur_je)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_passe_anterieur_tu)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_passe_anterieur_il)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_passe_anterieur_nous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_passe_anterieur_vous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_passe_anterieur_ils)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_futur_simple_je)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_futur_simple_tu)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_futur_simple_il)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_futur_simple_nous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_futur_simple_vous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_futur_simple_ils)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_futur_anterieur_je)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_futur_anterieur_tu)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_futur_anterieur_il)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_futur_anterieur_nous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_futur_anterieur_vous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.indicative_futur_anterieur_ils)).setTextSize(unit, fontSize);

        ((TextView)findViewById(R.id.conditionnel_present_je)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.conditionnel_present_tu)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.conditionnel_present_il)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.conditionnel_present_nous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.conditionnel_present_vous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.conditionnel_present_ils)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.conditionnel_passe_je)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.conditionnel_passe_tu)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.conditionnel_passe_il)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.conditionnel_passe_nous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.conditionnel_passe_vous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.conditionnel_passe_ils)).setTextSize(unit, fontSize);

        ((TextView)findViewById(R.id.subjonctif_present_je)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.subjonctif_present_tu)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.subjonctif_present_il)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.subjonctif_present_nous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.subjonctif_present_vous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.subjonctif_present_ils)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.subjonctif_passe_je)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.subjonctif_passe_tu)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.subjonctif_passe_il)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.subjonctif_passe_nous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.subjonctif_passe_vous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.subjonctif_passe_ils)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.subjonctif_imperfait_je)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.subjonctif_imperfait_tu)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.subjonctif_imperfait_il)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.subjonctif_imperfait_nous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.subjonctif_imperfait_vous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.subjonctif_imperfait_ils)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.subjonctif_plus_que_parfait_je)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.subjonctif_plus_que_parfait_tu)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.subjonctif_plus_que_parfait_il)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.subjonctif_plus_que_parfait_nous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.subjonctif_plus_que_parfait_vous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.subjonctif_plus_que_parfait_ils)).setTextSize(unit, fontSize);

        ((TextView)findViewById(R.id.imperatif_present_tu)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.imperatif_present_nous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.imperatif_present_vous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.imperatif_passe_tu)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.imperatif_passe_nous)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.imperatif_passe_vous)).setTextSize(unit, fontSize);

        ((TextView)findViewById(R.id.infinitive_present)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.infinitive_passe)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.participe_present)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.participe_passe1)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.participe_passe2)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.gerondif_present)).setTextSize(unit, fontSize);
        ((TextView)findViewById(R.id.gerondif_passe)).setTextSize(unit, fontSize);
    }

    /**
     * Set the text color.
     * @param color color
     */
    private void setVerbColor(int color) {
        infinitive.setTextColor(color);
    }

    @Override
    public void onClick(View view) {
        // Play the sounds
        switch(view.getId()){
            case R.id.play_infinitive:
                if (verb != null) {
                    ActivityUtils.speak(getApplicationContext(), tts, verb.getInfinitive());
                    Toast.makeText(getApplicationContext(), verb.getInfinitive(), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.play_definition:
                if (verb != null) {
                    ActivityUtils.speak(getApplicationContext(), tts, verb.getDefinition());
                }
                break;

            case R.id.play_sample1:
                if (verb != null) {
                    ActivityUtils.speak(getApplicationContext(), tts, verb.getSample1());
                }
                break;

            case R.id.play_sample2:
                if (verb != null) {
                    ActivityUtils.speak(getApplicationContext(), tts, verb.getSample2());
                }
                break;

            case R.id.play_sample3:
                if (verb != null) {
                    ActivityUtils.speak(getApplicationContext(), tts, verb.getSample3());
                }
                break;

            default:
                //onClickDemo();
                break;
        }
    }

    /**
     * Start a show case view for demo mode.
     *//*
    private void defineDemoMode() {
        showcaseView = new ShowcaseView.Builder(this)
                .withMaterialShowcase()
                .setTarget(new ViewTarget(findViewById(R.id.infinitive)))
                .setContentTitle(getString(R.string.details))
                .setContentText(getString(R.string.infinitive))
                .setStyle(R.style.CustomShowcaseTheme2)
                .replaceEndButton(R.layout.view_custom_button)
                .setOnClickListener(this)
                .build();
        showcaseView.setButtonText(getString(R.string.next));
    }*/

    /**
     * Defines what item to show case view for demo mode.
     *//*
    private void onClickDemo() {
        if (!demo) return;
        switch (counter) {
            case 0:
                showcaseView.setShowcase(new ViewTarget(findViewById(R.id.simple_past)), true);
                showcaseView.setContentText(getString(R.string.simple_past));
                break;

            case 1:
                showcaseView.setShowcase(new ViewTarget(findViewById(R.id.past_participle)), true);
                showcaseView.setContentText(getString(R.string.past_participle));
                break;

            case 2:
                showcaseView.setShowcase(new ViewTarget(findViewById(R.id.phonetic_infinitive)), true);
                showcaseView.setContentText(getString(R.string.phonetics));
                break;

            case 3:
                showcaseView.setShowcase(new ViewTarget(findViewById(R.id.play_simple_past)), true);
                showcaseView.setContentText(getString(R.string.pronunciation));
                break;

            case 4:
                showcaseView.setShowcase(new ViewTarget(findViewById(R.id.definition)), true);
                showcaseView.setContentText(getString(R.string.definition));
                break;

            case 5:
                showcaseView.setShowcase(new ViewTarget(findViewById(R.id.sample2)), true);
                showcaseView.setContentText(getString(R.string.examples));
                break;

            case 6:
                showcaseView.setShowcase(new ViewTarget(fabAdd), true);
                showcaseView.setContentTitle(getString(R.string.favorites));
                showcaseView.setContentText(getString(R.string.add_remove_from_favorites));
                showcaseView.setButtonText(getString(R.string.got_it));
                break;

            case 7:
                showcaseView.hide();
                demo = false;
                break;
        }
        counter++;
    }*/

}
