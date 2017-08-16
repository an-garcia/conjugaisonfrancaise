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
import com.xengar.android.conjugaisonfrancaise.data.Conjugation;
import com.xengar.android.conjugaisonfrancaise.data.Verb;
import com.xengar.android.conjugaisonfrancaise.data.VerbContract;
import com.xengar.android.conjugaisonfrancaise.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.xengar.android.conjugaisonfrancaise.data.VerbContract.VerbEntry.COLUMN_ID;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.CONJUGATION_ID;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.DEMO_MODE;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.IL;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.ILS;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.JE;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.JEA;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.LOG;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.NOUS;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.QUE;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.QUEA;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.TU;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.VERB_ID;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.VERB_NAME;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.VOUS;

/**
 * DetailsActivity
 */
public class DetailsActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {

    private static final String TAG = DetailsActivity.class.getSimpleName();
    private static final int VERB_LOADER = 0;
    private static final int CONJUGATION_LOADER = 1;

    private FloatingActionButton fabAdd, fabDel;
    private String verbName = "";
    private long verbID = -1;
    private long conjugationID = -1;
    private Verb verb;
    private Conjugation conjugation;
    private TextToSpeech tts;
    private TextView infinitive, group;
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
            verbName = bundle.getString(VERB_NAME, "");
            verbID = bundle.getLong(VERB_ID, -1);
            conjugationID = bundle.getLong(CONJUGATION_ID, -1);
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
        getLoaderManager().initLoader(VERB_LOADER, null, this);
        getLoaderManager().initLoader(CONJUGATION_LOADER, null, this);
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
        CursorLoader cursorLoader = null;
        switch (id) {
            case CONJUGATION_LOADER:
                cursorLoader = new CursorLoader(this,
                        VerbContract.VerbEntry.CONTENT_CONJUGATIONS_URI,
                        ActivityUtils.allConjugationColumns(), // Columns in the resulting Cursor
                        COLUMN_ID + " = ?",     // selection clause
                        new String[]{Long.toString(conjugationID)}, // selection arguments
                        null);                  // Default sort order
                break;

            case VERB_LOADER:
                cursorLoader = new CursorLoader(this,   // Parent activity context
                        VerbContract.VerbEntry.CONTENT_VERBS_URI,
                        ActivityUtils.allVerbColumns(), // Columns in the resulting Cursor
                        COLUMN_ID + " = ?",     // selection clause
                        new String[]{Long.toString(verbID)}, // selection arguments
                        null);                  // Default sort order
                break;
            default:
                break;
        }
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            finish(); // the verb or conjugation doesn't exist, this should not happen.
            return;
        }

        switch (loader.getId()) {
            case CONJUGATION_LOADER:
                if (cursor.moveToFirst()) {
                    conjugation = ActivityUtils.conjugationFromCursor(cursor);
                    if (!conjugation.getInfinitivePresent().isEmpty()
                            && !conjugation.getInfinitivePresent().contentEquals(verbName)) {
                        // if we need, conjugate the verb model.
                        conjugateVerb(conjugation, verbName);
                        // TODO : Check auxiliar verb in verb, like partir and change auxiliar
                    }
                    addPronoms(conjugation);
                    fillConjugationDetails(conjugation);
                }
                break;

            case VERB_LOADER:
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
                break;
            default:
                break;
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
     * Conjugates the verb according to the model.
     * @param c Conjugation conjugation
     * @param verbInfinitive String
     */
    private void conjugateVerb(Conjugation c, String verbInfinitive) {
        // Generate verb radicals for each time and person based on the radical's model.
        List<String> modelRadicals = new ArrayList<>();
        List<String> verbRadicals = new ArrayList<>();
        String modelRs = c.getRadicals();
        if (modelRs != null && !modelRs.isEmpty()) {
            String[] arrayModelRs = modelRs.split(", ");
            for (String modelR : arrayModelRs) {
                modelRadicals.add(modelR);
                String verbR = generateRadical(verbInfinitive, modelR, (int)c.getId());
                verbRadicals.add(verbR);
            }
            replaceRadicals(c, modelRadicals, verbRadicals);
        }
    }

    /**
     * Generates the verb radical based on the model.
     * @param infinitive String verb
     * @param modelR String radical of the model
     * @param id int model id
     * @return list of radicals
     */
    private String generateRadical(String infinitive, String modelR, int id) {
        String verbR = infinitive;
        if (verbR.endsWith("er")) {
            verbR = verbR.replace("er", "");
        } else if (verbR.endsWith("ir")) {
            verbR = verbR.replace("ir", "");
        }

        // TODO: Check all models, check reflechi
        // know models
        switch (id) {
            case 8:
                // placer, plaçer : verbes en -cer
                if (modelR.contains("ç")) {
                    verbR = infinitive.endsWith("cer")? infinitive.replace("cer", "ç") : verbR;
                }
            case 10:
                // peser, pèser : verbes ayant un e muet à l'avant dèrniere syllabe de l'infinitif: verbes en e(.)er
                if (modelR.contains("è")) {
                    int last = verbR.contains("e")? verbR.lastIndexOf("e") : -1;
                    if (last > -1) {
                        verbR = verbR.substring(0, last) + "è" + verbR.substring(last + 1, verbR.length());
                    }
                }
                break;
            case 11:
                // céder, cède : verbes ayant un e muet à l'avant dèrniere syllabe de l'infinitif: verbes en é(.)er
                if (modelR.contains("è")) {
                    int last = verbR.contains("é")? verbR.lastIndexOf("é") : -1;
                    if (last > -1) {
                        verbR = verbR.substring(0, last) + "è" + verbR.substring(last + 1, verbR.length());
                    }
                }
                break;
            case 12:
                // jeter, jetter : verbes en -eler ou -eter, doublant 1 ou t devant e muet
                if (modelR.contains("tt")) {
                    verbR = verbR.endsWith("l")? verbR + "l" : verbR;
                    verbR = verbR.endsWith("t")? verbR + "t" : verbR;
                }
                break;
            case 13:
                // model, modèl : verbes en -eler ou -eter, changeant e en è devant syllabe muette
                if (modelR.contains("è")) {
                    verbR = infinitive.endsWith("eler")? infinitive.replace("eler", "èl") : verbR;
                    verbR = infinitive.endsWith("eter")? infinitive.replace("eter", "èt") : verbR;
                }
                break;
            case 15:
                // assiéger, assiège : verbes en -éger
                if (modelR.contains("è")) {
                    int last = verbR.contains("é")? verbR.lastIndexOf("é") : -1;
                    if (last > -1) {
                        verbR = verbR.substring(0, last) + "è" + verbR.substring(last + 1, verbR.length());
                    }
                }
                break;
            case 17:
                // paie / paye : verbes en -ayer
                if (modelR.contains("i")) {
                    verbR = infinitive.endsWith("ayer")? infinitive.replace("ayer", "ai") : verbR;
                }
                break;
            case 18:
                // broyer, broie : verbes en -oyer, -uyer
                if (modelR.contains("i")) {
                    verbR = infinitive.endsWith("oyer")? infinitive.replace("oyer", "oi") : verbR;
                    verbR = infinitive.endsWith("uyer")? infinitive.replace("uyer", "ui") : verbR;
                }
                break;
            case 19:
                // envoyer, envoie, enverra : all verbes, envoyer, renvoyer, s'envoyer, se renvoyer, avoyer
                if (modelR.contains("i")) {
                    verbR = infinitive.endsWith("yer")? infinitive.replace("yer", "i") : verbR;
                } else if (modelR.contains("enverr")) {
                    // 2 special cases for enverr
                    verbR = infinitive.endsWith("envoyer")? infinitive.replace("envoyer", "enverr") : verbR;
                    verbR = infinitive.endsWith("avoyer")? infinitive.replace("avoyer", "avoier") : verbR;
                }
                break;
            case 21:
                // haïr est le seul verbe
                if (modelR.contains("ha")) {
                    verbR = infinitive.endsWith("haïr")? infinitive.replace("haïr", "ha") : verbR;
                }
                break;
            case 24:
                // tenir, tiens, tinsse, tînt : verbes -enir
                if (modelR.contains("ten")) {
                    verbR = infinitive.endsWith("enir")? infinitive.replace("enir", "en") : verbR;
                } else if (modelR.contains("tien")) {
                    verbR = infinitive.endsWith("enir")? infinitive.replace("enir", "ien") : verbR;
                } else if (modelR.contains("tin")) {
                    verbR = infinitive.endsWith("enir")? infinitive.replace("enir", "in") : verbR;
                } else if (modelR.contains("tîn")) {
                    verbR = infinitive.endsWith("enir")? infinitive.replace("enir", "în") : verbR;
                }
                break;
            case 25:
                // acquerir : verbes en -érir
                if (modelR.contains("acqu")) {
                    verbR = infinitive.endsWith("érir")? infinitive.replace("érir", "") : verbR;
                }
                break;
            case 26:
                // sentir : verbes eb -tir
                if (modelR.contains("sen")) {
                    verbR = infinitive.endsWith("tir")? infinitive.replace("tir", "") : verbR;
                }
                break;
            case 28:
                // souffrir, souffert : verbes en -vrir, frir
                if (modelR.contains("couve")) {
                    verbR = infinitive.endsWith("vrir")? infinitive.replace("vrir", "ve") : verbR;
                    verbR = infinitive.endsWith("frir")? infinitive.replace("frir", "fe") : verbR;
                }
                break;
            case 32:
                // bouillir, bous : all verbes, known: bouillir, debouillir, racabouillir
                if (modelR.contentEquals("bou")) {
                    verbR = infinitive.endsWith("bouillir")? infinitive.replace("bouillir", "bou") : verbR;
                }
                break;
            case 33:
                // dormir, dors : all verbes, known: dormir, endormir, rendormir
                if (modelR.contentEquals("dor")) {
                    verbR = infinitive.endsWith("dormir")? infinitive.replace("dormir", "dor") : verbR;
                }
                break;
            case 35:
                // mourir, meurs : all verbes, known: mourir, se mourir
                if (modelR.contains("meur")) {
                    verbR = infinitive.endsWith("mourir")? infinitive.replace("mourir", "meur") : verbR;
                }
                break;
            case 36:
                // servir, sers : all verbes, known: servir, desservir, reservir
                if (modelR.contentEquals("ser")) {
                    verbR = infinitive.endsWith("servir")? infinitive.replace("servir", "ser") : verbR;
                }
                break;
            case 40:
                // recevoir : verbes en -cevoir
                if (modelR.contentEquals("re")) {
                    verbR = infinitive.endsWith("cevoir")? infinitive.replace("cevoir", "") : verbR;
                }
                break;
            case 41:
                // voir, vu : all verbes, known: voir, entrevoir, prevoir, revoir
                if (modelR.contentEquals("voi")) {
                    verbR = infinitive.endsWith("voir")? infinitive.replace("voir", "voi") : verbR;
                } else if (modelR.contentEquals("voy")) {
                    verbR = infinitive.endsWith("voir")? infinitive.replace("voir", "voy") : verbR;
                } else if (modelR.contentEquals("vi")) {
                    verbR = infinitive.endsWith("voir")? infinitive.replace("voir", "vi") : verbR;
                } else if (modelR.contentEquals("vî")) {
                    verbR = infinitive.endsWith("voir")? infinitive.replace("voir", "vî") : verbR;
                } else if (modelR.contentEquals("verr")) {
                    verbR = infinitive.endsWith("voir")? infinitive.replace("voir", "verr") : verbR;
                } else if (modelR.contentEquals("vu")) {
                    verbR = infinitive.endsWith("voir")? infinitive.replace("voir", "vu") : verbR;
                }
                break;
            case 42:
                // pourvoir, pourvu : all verbes, known: pourvoir, depourvoir
                if (modelR.contentEquals("pourv")) {
                    verbR = infinitive.endsWith("pourvoir")? infinitive.replace("pourvoir", "pourv") : verbR;
                }
                break;
            case 44:
                // devoir, dois : all verbes, known: devoir, redevoir
                if (modelR.contentEquals("d")) {
                    verbR = infinitive.endsWith("devoir")? infinitive.replace("devoir", "d") : verbR;
                }
                break;
            case 45:
                // pouvoir, pu : all verbes, known: pouvoir
                if (modelR.contentEquals("p")) {
                    verbR = infinitive.endsWith("pouvoir")? infinitive.replace("pouvoir", "p") : verbR;
                }
                break;
            case 46:
                // mouvoir, mu : all verbes, known: mouvoir, émouvoir, promouvoir
                if (modelR.contentEquals("m")) {
                    verbR = infinitive.endsWith("mouvoir")? infinitive.replace("mouvoir", "m") : verbR;
                }
                break;
            case 47:
                // pleuvoir, plu : all verbes, known: pleuvoir, repleuvoir
                if (modelR.contentEquals("pl")) {
                    verbR = infinitive.endsWith("pleuvoir")? infinitive.replace("pleuvoir", "pl") : verbR;
                }
                break;
            case 49:
                // valoir, valu : all verbes, known: valoir, équivaloir, prévaloir, revaloir
                if (modelR.contentEquals("va")) {
                    verbR = infinitive.endsWith("valoir")? infinitive.replace("valoir", "va") : verbR;
                }
                break;
            case 50:
                // vouloir, veux : all verbes, known: vouloir, revouloir
                if (modelR.contentEquals("veu")) {
                    verbR = infinitive.endsWith("vouloir")? infinitive.replace("vouloir", "veu") : verbR;
                } else if (modelR.contentEquals("voul")) {
                    verbR = infinitive.endsWith("vouloir")? infinitive.replace("vouloir", "voul") : verbR;
                } else if (modelR.contentEquals("voud")) {
                    verbR = infinitive.endsWith("vouloir")? infinitive.replace("vouloir", "voud") : verbR;
                }
                break;

        }
        return verbR;
    }

    /**
     * Replaces the radicals with the ones from the verb.
     * @param c
     * @param modelR  List of model radicals
     * @param verbR  List of verb radicals
     */
    private void replaceRadicals(Conjugation c, List<String> modelR, List<String> verbR) {

        c.setImperatifPresentTu(replaceRadical(c.getImperatifPresentTu(), modelR, verbR));
        c.setImperatifPresentNous(replaceRadical(c.getImperatifPresentNous(), modelR, verbR));
        c.setImperatifPresentVous(replaceRadical(c.getImperatifPresentVous(), modelR, verbR));
        c.setImperatifPasseTu(replaceRadical(c.getImperatifPasseTu(), modelR, verbR));
        c.setImperatifPasseNous(replaceRadical(c.getImperatifPasseNous(), modelR, verbR));
        c.setImperatifPasseVous(replaceRadical(c.getImperatifPasseVous(), modelR, verbR));

        c.setInfinitivePresent(verbName);
        c.setInfinitivePasse(replaceRadical(c.getInfinitivePasse(), modelR, verbR));
        c.setParticipePresent(replaceRadical(c.getParticipePresent(), modelR, verbR));
        c.setParticipePasse1(replaceRadical(c.getParticipePasse1(), modelR, verbR));
        c.setParticipePasse2(replaceRadical(c.getParticipePasse2(), modelR, verbR));
        c.setGerondifPresent(replaceRadical(c.getGerondifPresent(), modelR, verbR));
        c.setGerondifPasse(replaceRadical(c.getGerondifPasse(), modelR, verbR));

        c.setIndicatifPresentJe(replaceRadical(c.getIndicatifPresentJe(), modelR, verbR));
        c.setIndicatifPresentTu(replaceRadical(c.getIndicatifPresentTu(), modelR, verbR));
        c.setIndicatifPresentIl(replaceRadical(c.getIndicatifPresentIl(), modelR, verbR));
        c.setIndicatifPresentNous(replaceRadical(c.getIndicatifPresentNous(), modelR, verbR));
        c.setIndicatifPresentVous(replaceRadical(c.getIndicatifPresentVous(), modelR, verbR));
        c.setIndicatifPresentIls(replaceRadical(c.getIndicatifPresentIls(), modelR, verbR));

        c.setIndicatifPasseComposeJe(replaceRadical(c.getIndicatifPasseComposeJe(), modelR, verbR));
        c.setIndicatifPasseComposeTu(replaceRadical(c.getIndicatifPasseComposeTu(), modelR, verbR));
        c.setIndicatifPasseComposeIl(replaceRadical(c.getIndicatifPasseComposeIl(), modelR, verbR));
        c.setIndicatifPasseComposeNous(replaceRadical(c.getIndicatifPasseComposeNous(), modelR, verbR));
        c.setIndicatifPasseComposeVous(replaceRadical(c.getIndicatifPasseComposeVous(), modelR, verbR));
        c.setIndicatifPasseComposeIls(replaceRadical(c.getIndicatifPasseComposeIls(), modelR, verbR));

        c.setIndicatifImperfaitJe(replaceRadical(c.getIndicatifImperfaitJe(), modelR, verbR));
        c.setIndicatifImperfaitTu(replaceRadical(c.getIndicatifImperfaitTu(), modelR, verbR));
        c.setIndicatifImperfaitIl(replaceRadical(c.getIndicatifImperfaitIl(), modelR, verbR));
        c.setIndicatifImperfaitNous(replaceRadical(c.getIndicatifImperfaitNous(), modelR, verbR));
        c.setIndicatifImperfaitVous(replaceRadical(c.getIndicatifImperfaitVous(), modelR, verbR));
        c.setIndicatifImperfaitIls(replaceRadical(c.getIndicatifImperfaitIls(), modelR, verbR));

        c.setIndicatifPlusQueParfaitJe(replaceRadical(c.getIndicatifPlusQueParfaitJe(), modelR, verbR));
        c.setIndicatifPlusQueParfaitTu(replaceRadical(c.getIndicatifPlusQueParfaitTu(), modelR, verbR));
        c.setIndicatifPlusQueParfaitIl(replaceRadical(c.getIndicatifPlusQueParfaitIl(), modelR, verbR));
        c.setIndicatifPlusQueParfaitNous(replaceRadical(c.getIndicatifPlusQueParfaitNous(), modelR, verbR));
        c.setIndicatifPlusQueParfaitVous(replaceRadical(c.getIndicatifPlusQueParfaitVous(), modelR, verbR));
        c.setIndicatifPlusQueParfaitIls(replaceRadical(c.getIndicatifPlusQueParfaitIls(), modelR, verbR));

        c.setIndicatifPasseSimpleJe(replaceRadical(c.getIndicatifPasseSimpleJe(), modelR, verbR));
        c.setIndicatifPasseSimpleTu(replaceRadical(c.getIndicatifPasseSimpleTu(), modelR, verbR));
        c.setIndicatifPasseSimpleIl(replaceRadical(c.getIndicatifPasseSimpleIl(), modelR, verbR));
        c.setIndicatifPasseSimpleNous(replaceRadical(c.getIndicatifPasseSimpleNous(), modelR, verbR));
        c.setIndicatifPasseSimpleVous(replaceRadical(c.getIndicatifPasseSimpleVous(), modelR, verbR));
        c.setIndicatifPasseSimpleIls(replaceRadical(c.getIndicatifPasseSimpleIls(), modelR, verbR));

        c.setIndicatifPasseAnterieurJe(replaceRadical(c.getIndicatifPasseAnterieurJe(), modelR, verbR));
        c.setIndicatifPasseAnterieurTu(replaceRadical(c.getIndicatifPasseAnterieurTu(), modelR, verbR));
        c.setIndicatifPasseAnterieurIl(replaceRadical(c.getIndicatifPasseAnterieurIl(), modelR, verbR));
        c.setIndicatifPasseAnterieurNous(replaceRadical(c.getIndicatifPasseAnterieurNous(), modelR, verbR));
        c.setIndicatifPasseAnterieurVous(replaceRadical(c.getIndicatifPasseAnterieurVous(), modelR, verbR));
        c.setIndicatifPasseAnterieurIls(replaceRadical(c.getIndicatifPasseAnterieurIls(), modelR, verbR));

        c.setIndicatifFuturSimpleJe(replaceRadical(c.getIndicatifFuturSimpleJe(), modelR, verbR));
        c.setIndicatifFuturSimpleTu(replaceRadical(c.getIndicatifFuturSimpleTu(), modelR, verbR));
        c.setIndicatifFuturSimpleIl(replaceRadical(c.getIndicatifFuturSimpleIl(), modelR, verbR));
        c.setIndicatifFuturSimpleNous(replaceRadical(c.getIndicatifFuturSimpleNous(), modelR, verbR));
        c.setIndicatifFuturSimpleVous(replaceRadical(c.getIndicatifFuturSimpleVous(), modelR, verbR));
        c.setIndicatifFuturSimpleIls(replaceRadical(c.getIndicatifFuturSimpleIls(), modelR, verbR));

        c.setIndicatifFuturAnterieurJe(replaceRadical(c.getIndicatifFuturAnterieurJe(), modelR, verbR));
        c.setIndicatifFuturAnterieurTu(replaceRadical(c.getIndicatifFuturAnterieurTu(), modelR, verbR));
        c.setIndicatifFuturAnterieurIl(replaceRadical(c.getIndicatifFuturAnterieurIl(), modelR, verbR));
        c.setIndicatifFuturAnterieurNous(replaceRadical(c.getIndicatifFuturAnterieurNous(), modelR, verbR));
        c.setIndicatifFuturAnterieurVous(replaceRadical(c.getIndicatifFuturAnterieurVous(), modelR, verbR));
        c.setIndicatifFuturAnterieurIls(replaceRadical(c.getIndicatifFuturAnterieurIls(), modelR, verbR));


        c.setConditionnelPresentJe(replaceRadical(c.getConditionnelPresentJe(), modelR, verbR));
        c.setConditionnelPresentTu(replaceRadical(c.getConditionnelPresentTu(), modelR, verbR));
        c.setConditionnelPresentIl(replaceRadical(c.getConditionnelPresentIl(), modelR, verbR));
        c.setConditionnelPresentNous(replaceRadical(c.getConditionnelPresentNous(), modelR, verbR));
        c.setConditionnelPresentVous(replaceRadical(c.getConditionnelPresentVous(), modelR, verbR));
        c.setConditionnelPresentIls(replaceRadical(c.getConditionnelPresentIls(), modelR, verbR));

        c.setConditionnelPasseJe(replaceRadical(c.getConditionnelPasseJe(), modelR, verbR));
        c.setConditionnelPasseTu(replaceRadical(c.getConditionnelPasseTu(), modelR, verbR));
        c.setConditionnelPasseIl(replaceRadical(c.getConditionnelPasseIl(), modelR, verbR));
        c.setConditionnelPasseNous(replaceRadical(c.getConditionnelPasseNous(), modelR, verbR));
        c.setConditionnelPasseVous(replaceRadical(c.getConditionnelPasseVous(), modelR, verbR));
        c.setConditionnelPasseIls(replaceRadical(c.getConditionnelPasseIls(), modelR, verbR));


        c.setSubjonctifPresentJe(replaceRadical(c.getSubjonctifPresentJe(), modelR, verbR));
        c.setSubjonctifPresentTu(replaceRadical(c.getSubjonctifPresentTu(), modelR, verbR));
        c.setSubjonctifPresentIl(replaceRadical(c.getSubjonctifPresentIl(), modelR, verbR));
        c.setSubjonctifPresentNous(replaceRadical(c.getSubjonctifPresentNous(), modelR, verbR));
        c.setSubjonctifPresentVous(replaceRadical(c.getSubjonctifPresentVous(), modelR, verbR));
        c.setSubjonctifPresentIls(replaceRadical(c.getSubjonctifPresentIls(), modelR, verbR));

        c.setSubjonctifPasseJe(replaceRadical(c.getSubjonctifPasseJe(), modelR, verbR));
        c.setSubjonctifPasseTu(replaceRadical(c.getSubjonctifPasseTu(), modelR, verbR));
        c.setSubjonctifPasseIl(replaceRadical(c.getSubjonctifPasseIl(), modelR, verbR));
        c.setSubjonctifPasseNous(replaceRadical(c.getSubjonctifPasseNous(), modelR, verbR));
        c.setSubjonctifPasseVous(replaceRadical(c.getSubjonctifPasseVous(), modelR, verbR));
        c.setSubjonctifPasseIls(replaceRadical(c.getSubjonctifPasseIls(), modelR, verbR));

        c.setSubjonctifImperfaitJe(replaceRadical(c.getSubjonctifImperfaitJe(), modelR, verbR));
        c.setSubjonctifImperfaitTu(replaceRadical(c.getSubjonctifImperfaitTu(), modelR, verbR));
        c.setSubjonctifImperfaitIl(replaceRadical(c.getSubjonctifImperfaitIl(), modelR, verbR));
        c.setSubjonctifImperfaitNous(replaceRadical(c.getSubjonctifImperfaitNous(), modelR, verbR));
        c.setSubjonctifImperfaitVous(replaceRadical(c.getSubjonctifImperfaitVous(), modelR, verbR));
        c.setSubjonctifImperfaitIls(replaceRadical(c.getSubjonctifImperfaitIls(), modelR, verbR));

        c.setSubjonctifPlusQueParfaitJe(replaceRadical(c.getSubjonctifPlusQueParfaitJe(), modelR, verbR));
        c.setSubjonctifPlusQueParfaitTu(replaceRadical(c.getSubjonctifPlusQueParfaitTu(), modelR, verbR));
        c.setSubjonctifPlusQueParfaitIl(replaceRadical(c.getSubjonctifPlusQueParfaitIl(), modelR, verbR));
        c.setSubjonctifPlusQueParfaitNous(replaceRadical(c.getSubjonctifPlusQueParfaitNous(), modelR, verbR));
        c.setSubjonctifPlusQueParfaitVous(replaceRadical(c.getSubjonctifPlusQueParfaitVous(), modelR, verbR));
        c.setSubjonctifPlusQueParfaitIls(replaceRadical(c.getSubjonctifPlusQueParfaitIls(), modelR, verbR));
    }

    /**
     * Replaces the radical in the conjugation form.
     * @param text  verb conjugation
     * @param modelR List of model radicals
     * @param verbR List of verb radicals
     * @return
     */
    private String replaceRadical(String text, List<String> modelR, List<String> verbR) {
        String newText = text;
        String radicalM, radicalV;
        for (int i = 0; i < modelR.size(); i++) {
            radicalM = modelR.get(i);
            radicalV = verbR.get(i);
            if (!radicalM.isEmpty() && !radicalV.isEmpty() && text.contains(radicalM)) {
                newText = newText.replace(radicalM, radicalV);
                // if it's just one form, if it's a double form (like Je pay / paye) continue
                if (!text.contains("/")) {
                    break;
                }
            }
        }
        return newText;
    }

    /**
     * Ads the pronoms
     * @param c Conjugation
     */
    private void addPronoms(Conjugation c) {
        // Add pronoms
        // TODO: Show pronoms in different color
        String text = c.getIndicatifPresentJe();
        c.setIndicatifPresentJe((ActivityUtils.useApostrophe(text))? JEA + text : JE + text);
        c.setIndicatifPresentTu(TU + c.getIndicatifPresentTu());
        c.setIndicatifPresentIl(IL + c.getIndicatifPresentIl());
        c.setIndicatifPresentNous(NOUS + c.getIndicatifPresentNous());
        c.setIndicatifPresentVous(VOUS + c.getIndicatifPresentVous());
        c.setIndicatifPresentIls(ILS + c.getIndicatifPresentIls());

        text = c.getIndicatifPasseComposeJe();
        c.setIndicatifPasseComposeJe((ActivityUtils.useApostrophe(text))? JEA + text : JE + text);
        c.setIndicatifPasseComposeTu(TU + c.getIndicatifPasseComposeTu());
        c.setIndicatifPasseComposeIl(IL + c.getIndicatifPasseComposeIl());
        c.setIndicatifPasseComposeNous(NOUS + c.getIndicatifPasseComposeNous());
        c.setIndicatifPasseComposeVous(VOUS + c.getIndicatifPasseComposeVous());
        c.setIndicatifPasseComposeIls(ILS + c.getIndicatifPasseComposeIls());

        text = c.getIndicatifImperfaitJe();
        c.setIndicatifImperfaitJe((ActivityUtils.useApostrophe(text))? JEA + text : JE + text);
        c.setIndicatifImperfaitTu(TU + c.getIndicatifImperfaitTu());
        c.setIndicatifImperfaitIl(IL + c.getIndicatifImperfaitIl());
        c.setIndicatifImperfaitNous(NOUS + c.getIndicatifImperfaitNous());
        c.setIndicatifImperfaitVous(VOUS + c.getIndicatifImperfaitVous());
        c.setIndicatifImperfaitIls(ILS + c.getIndicatifImperfaitIls());

        text = c.getIndicatifPlusQueParfaitJe();
        c.setIndicatifPlusQueParfaitJe((ActivityUtils.useApostrophe(text))? JEA + text : JE + text);
        c.setIndicatifPlusQueParfaitTu(TU + c.getIndicatifPlusQueParfaitTu());
        c.setIndicatifPlusQueParfaitIl(IL + c.getIndicatifPlusQueParfaitIl());
        c.setIndicatifPlusQueParfaitNous(NOUS + c.getIndicatifPlusQueParfaitNous());
        c.setIndicatifPlusQueParfaitVous(VOUS + c.getIndicatifPlusQueParfaitVous());
        c.setIndicatifPlusQueParfaitIls(ILS + c.getIndicatifPlusQueParfaitIls());

        text = c.getIndicatifPasseSimpleJe();
        c.setIndicatifPasseSimpleJe((ActivityUtils.useApostrophe(text))? JEA + text : JE + text);
        c.setIndicatifPasseSimpleTu(TU + c.getIndicatifPasseSimpleTu());
        c.setIndicatifPasseSimpleIl(IL + c.getIndicatifPasseSimpleIl());
        c.setIndicatifPasseSimpleNous(NOUS + c.getIndicatifPasseSimpleNous());
        c.setIndicatifPasseSimpleVous(VOUS + c.getIndicatifPasseSimpleVous());
        c.setIndicatifPasseSimpleIls(ILS + c.getIndicatifPasseSimpleIls());

        text = c.getIndicatifPasseAnterieurJe();
        c.setIndicatifPasseAnterieurJe((ActivityUtils.useApostrophe(text))? JEA + text : JE + text);
        c.setIndicatifPasseAnterieurTu(TU + c.getIndicatifPasseAnterieurTu());
        c.setIndicatifPasseAnterieurIl(IL + c.getIndicatifPasseAnterieurIl());
        c.setIndicatifPasseAnterieurNous(NOUS + c.getIndicatifPasseAnterieurNous());
        c.setIndicatifPasseAnterieurVous(VOUS + c.getIndicatifPasseAnterieurVous());
        c.setIndicatifPasseAnterieurIls(ILS + c.getIndicatifPasseAnterieurIls());

        text = c.getIndicatifFuturSimpleJe();
        c.setIndicatifFuturSimpleJe((ActivityUtils.useApostrophe(text))? JEA + text : JE + text);
        c.setIndicatifFuturSimpleTu(TU + c.getIndicatifFuturSimpleTu());
        c.setIndicatifFuturSimpleIl(IL + c.getIndicatifFuturSimpleIl());
        c.setIndicatifFuturSimpleNous(NOUS + c.getIndicatifFuturSimpleNous());
        c.setIndicatifFuturSimpleVous(VOUS + c.getIndicatifFuturSimpleVous());
        c.setIndicatifFuturSimpleIls(ILS + c.getIndicatifFuturSimpleIls());

        text = c.getIndicatifFuturAnterieurJe();
        c.setIndicatifFuturAnterieurJe((ActivityUtils.useApostrophe(text))? JEA + text : JE + text);
        c.setIndicatifFuturAnterieurTu(TU + c.getIndicatifFuturAnterieurTu());
        c.setIndicatifFuturAnterieurIl(IL + c.getIndicatifFuturAnterieurIl());
        c.setIndicatifFuturAnterieurNous(NOUS + c.getIndicatifFuturAnterieurNous());
        c.setIndicatifFuturAnterieurVous(VOUS + c.getIndicatifFuturAnterieurVous());
        c.setIndicatifFuturAnterieurIls(ILS + c.getIndicatifFuturAnterieurIls());

        text = c.getConditionnelPresentJe();
        c.setConditionnelPresentJe((ActivityUtils.useApostrophe(text))? JEA + text : JE + text);
        c.setConditionnelPresentTu(TU + c.getConditionnelPresentTu());
        c.setConditionnelPresentIl(IL + c.getConditionnelPresentIl());
        c.setConditionnelPresentNous(NOUS + c.getConditionnelPresentNous());
        c.setConditionnelPresentVous(VOUS + c.getConditionnelPresentVous());
        c.setConditionnelPresentIls(ILS + c.getConditionnelPresentIls());

        text = c.getConditionnelPasseJe();
        c.setConditionnelPasseJe((ActivityUtils.useApostrophe(text))? JEA + text : JE + text);
        c.setConditionnelPasseTu(TU + c.getConditionnelPasseTu());
        c.setConditionnelPasseIl(IL + c.getConditionnelPasseIl());
        c.setConditionnelPasseNous(NOUS + c.getConditionnelPasseNous());
        c.setConditionnelPasseVous(VOUS + c.getConditionnelPasseVous());
        c.setConditionnelPasseIls(ILS + c.getConditionnelPasseIls());

        text = c.getSubjonctifPresentJe();
        c.setSubjonctifPresentJe((ActivityUtils.useApostrophe(text))? QUE + JEA + text : QUE + JE + text);
        c.setSubjonctifPresentTu(QUE + TU + c.getSubjonctifPresentTu());
        c.setSubjonctifPresentIl(QUEA + IL + c.getSubjonctifPresentIl());
        c.setSubjonctifPresentNous(QUE + NOUS + c.getSubjonctifPresentNous());
        c.setSubjonctifPresentVous(QUE + VOUS + c.getSubjonctifPresentVous());
        c.setSubjonctifPresentIls(QUEA + ILS + c.getSubjonctifPresentIls());

        text = c.getSubjonctifPasseJe();
        c.setSubjonctifPasseJe((ActivityUtils.useApostrophe(text))? QUE + JEA + text : QUE + JE + text);
        c.setSubjonctifPasseTu(QUE + TU + c.getSubjonctifPasseTu());
        c.setSubjonctifPasseIl(QUEA + IL + c.getSubjonctifPasseIl());
        c.setSubjonctifPasseNous(QUE + NOUS + c.getSubjonctifPasseNous());
        c.setSubjonctifPasseVous(QUE + VOUS + c.getSubjonctifPasseVous());
        c.setSubjonctifPasseIls(QUEA + ILS + c.getSubjonctifPasseIls());

        text = c.getSubjonctifImperfaitJe();
        c.setSubjonctifImperfaitJe((ActivityUtils.useApostrophe(text))? QUE + JEA + text : QUE + JE + text);
        c.setSubjonctifImperfaitTu(QUE + TU + c.getSubjonctifImperfaitTu());
        c.setSubjonctifImperfaitIl(QUEA + IL + c.getSubjonctifImperfaitIl());
        c.setSubjonctifImperfaitNous(QUE + NOUS + c.getSubjonctifImperfaitNous());
        c.setSubjonctifImperfaitVous(QUE + VOUS + c.getSubjonctifImperfaitVous());
        c.setSubjonctifImperfaitIls(QUEA + ILS + c.getSubjonctifImperfaitIls());

        text = c.getSubjonctifPlusQueParfaitJe();
        c.setSubjonctifPlusQueParfaitJe((ActivityUtils.useApostrophe(text))? QUE + JEA + text : QUE + JE + text);
        c.setSubjonctifPlusQueParfaitTu(QUE + TU + c.getSubjonctifPlusQueParfaitTu());
        c.setSubjonctifPlusQueParfaitIl(QUEA + IL + c.getSubjonctifPlusQueParfaitIl());
        c.setSubjonctifPlusQueParfaitNous(QUE + NOUS + c.getSubjonctifPlusQueParfaitNous());
        c.setSubjonctifPlusQueParfaitVous(QUE + VOUS + c.getSubjonctifPlusQueParfaitVous());
        c.setSubjonctifPlusQueParfaitIls(QUEA + ILS + c.getSubjonctifPlusQueParfaitIls());
    }


    /**
     * Fills the conjugation section.
     * @param c Conjugation ready to display
     */
    private void fillConjugationDetails(Conjugation c) {
        int fontSize = Integer.parseInt(ActivityUtils.getPreferenceFontSize(getApplicationContext()));
        changeTextFontInConjugation(fontSize);

        ((TextView)findViewById(R.id.infinitive_present)).setText(c.getInfinitivePresent());
        ((TextView)findViewById(R.id.infinitive_passe)).setText(c.getInfinitivePasse());
        ((TextView)findViewById(R.id.participe_present)).setText(c.getParticipePresent());
        ((TextView)findViewById(R.id.participe_passe1)).setText(c.getParticipePasse1());
        ((TextView)findViewById(R.id.participe_passe2)).setText(c.getParticipePasse2());
        ((TextView)findViewById(R.id.gerondif_present)).setText(c.getGerondifPresent());
        ((TextView)findViewById(R.id.gerondif_passe)).setText(c.getGerondifPasse());

        ((TextView)findViewById(R.id.imperatif_present_tu)).setText(c.getImperatifPresentTu());
        ((TextView)findViewById(R.id.imperatif_present_nous)).setText(c.getImperatifPresentNous());
        ((TextView)findViewById(R.id.imperatif_present_vous)).setText(c.getImperatifPresentVous());
        ((TextView)findViewById(R.id.imperatif_passe_tu)).setText(c.getImperatifPasseTu());
        ((TextView)findViewById(R.id.imperatif_passe_nous)).setText(c.getImperatifPasseNous());
        ((TextView)findViewById(R.id.imperatif_passe_vous)).setText(c.getImperatifPasseVous());

        ((TextView)findViewById(R.id.indicative_present_je)).setText(c.getIndicatifPresentJe());
        ((TextView)findViewById(R.id.indicative_present_tu)).setText(c.getIndicatifPresentTu());
        ((TextView)findViewById(R.id.indicative_present_il)).setText(c.getIndicatifPresentIl());
        ((TextView)findViewById(R.id.indicative_present_nous)).setText(c.getIndicatifPresentNous());
        ((TextView)findViewById(R.id.indicative_present_vous)).setText(c.getIndicatifPresentVous());
        ((TextView)findViewById(R.id.indicative_present_ils)).setText(c.getIndicatifPresentIls());
        ((TextView)findViewById(R.id.indicative_passe_compose_je)).setText(c.getIndicatifPasseComposeJe());
        ((TextView)findViewById(R.id.indicative_passe_compose_tu)).setText(c.getIndicatifPasseComposeTu());
        ((TextView)findViewById(R.id.indicative_passe_compose_il)).setText(c.getIndicatifPasseComposeIl());
        ((TextView)findViewById(R.id.indicative_passe_compose_nous)).setText(c.getIndicatifPasseComposeNous());
        ((TextView)findViewById(R.id.indicative_passe_compose_vous)).setText(c.getIndicatifPasseComposeVous());
        ((TextView)findViewById(R.id.indicative_passe_compose_ils)).setText(c.getIndicatifPasseComposeIls());
        ((TextView)findViewById(R.id.indicative_imperfait_je)).setText(c.getIndicatifImperfaitJe());
        ((TextView)findViewById(R.id.indicative_imperfait_tu)).setText(c.getIndicatifImperfaitTu());
        ((TextView)findViewById(R.id.indicative_imperfait_il)).setText(c.getIndicatifImperfaitIl());
        ((TextView)findViewById(R.id.indicative_imperfait_nous)).setText(c.getIndicatifImperfaitNous());
        ((TextView)findViewById(R.id.indicative_imperfait_vous)).setText(c.getIndicatifImperfaitVous());
        ((TextView)findViewById(R.id.indicative_imperfait_ils)).setText(c.getIndicatifImperfaitIls());
        ((TextView)findViewById(R.id.indicative_plus_que_parfait_je)).setText(c.getIndicatifPlusQueParfaitJe());
        ((TextView)findViewById(R.id.indicative_plus_que_parfait_tu)).setText(c.getIndicatifPlusQueParfaitTu());
        ((TextView)findViewById(R.id.indicative_plus_que_parfait_il)).setText(c.getIndicatifPlusQueParfaitIl());
        ((TextView)findViewById(R.id.indicative_plus_que_parfait_nous)).setText(c.getIndicatifPlusQueParfaitNous());
        ((TextView)findViewById(R.id.indicative_plus_que_parfait_vous)).setText(c.getIndicatifPlusQueParfaitVous());
        ((TextView)findViewById(R.id.indicative_plus_que_parfait_ils)).setText(c.getIndicatifPlusQueParfaitIls());
        ((TextView)findViewById(R.id.indicative_passe_simple_je)).setText(c.getIndicatifPasseSimpleJe());
        ((TextView)findViewById(R.id.indicative_passe_simple_tu)).setText(c.getIndicatifPasseSimpleTu());
        ((TextView)findViewById(R.id.indicative_passe_simple_il)).setText(c.getIndicatifPasseSimpleIl());
        ((TextView)findViewById(R.id.indicative_passe_simple_nous)).setText(c.getIndicatifPasseSimpleNous());
        ((TextView)findViewById(R.id.indicative_passe_simple_vous)).setText(c.getIndicatifPasseSimpleVous());
        ((TextView)findViewById(R.id.indicative_passe_simple_ils)).setText(c.getIndicatifPasseSimpleIls());
        ((TextView)findViewById(R.id.indicative_passe_anterieur_je)).setText(c.getIndicatifPasseAnterieurJe());
        ((TextView)findViewById(R.id.indicative_passe_anterieur_tu)).setText(c.getIndicatifPasseAnterieurTu());
        ((TextView)findViewById(R.id.indicative_passe_anterieur_il)).setText(c.getIndicatifPasseAnterieurIl());
        ((TextView)findViewById(R.id.indicative_passe_anterieur_nous)).setText(c.getIndicatifPasseAnterieurNous());
        ((TextView)findViewById(R.id.indicative_passe_anterieur_vous)).setText(c.getIndicatifPasseAnterieurVous());
        ((TextView)findViewById(R.id.indicative_passe_anterieur_ils)).setText(c.getIndicatifPasseAnterieurIls());
        ((TextView)findViewById(R.id.indicative_futur_simple_je)).setText(c.getIndicatifFuturSimpleJe());
        ((TextView)findViewById(R.id.indicative_futur_simple_tu)).setText(c.getIndicatifFuturSimpleTu());
        ((TextView)findViewById(R.id.indicative_futur_simple_il)).setText(c.getIndicatifFuturSimpleIl());
        ((TextView)findViewById(R.id.indicative_futur_simple_nous)).setText(c.getIndicatifFuturSimpleNous());
        ((TextView)findViewById(R.id.indicative_futur_simple_vous)).setText(c.getIndicatifFuturSimpleVous());
        ((TextView)findViewById(R.id.indicative_futur_simple_ils)).setText(c.getIndicatifFuturSimpleIls());
        ((TextView)findViewById(R.id.indicative_futur_anterieur_je)).setText(c.getIndicatifFuturAnterieurJe());
        ((TextView)findViewById(R.id.indicative_futur_anterieur_tu)).setText(c.getIndicatifFuturAnterieurTu());
        ((TextView)findViewById(R.id.indicative_futur_anterieur_il)).setText(c.getIndicatifFuturAnterieurIl());
        ((TextView)findViewById(R.id.indicative_futur_anterieur_nous)).setText(c.getIndicatifFuturAnterieurNous());
        ((TextView)findViewById(R.id.indicative_futur_anterieur_vous)).setText(c.getIndicatifFuturAnterieurVous());
        ((TextView)findViewById(R.id.indicative_futur_anterieur_ils)).setText(c.getIndicatifFuturAnterieurIls());

        ((TextView)findViewById(R.id.conditionnel_present_je)).setText(c.getConditionnelPresentJe());
        ((TextView)findViewById(R.id.conditionnel_present_tu)).setText(c.getConditionnelPresentTu());
        ((TextView)findViewById(R.id.conditionnel_present_il)).setText(c.getConditionnelPresentIl());
        ((TextView)findViewById(R.id.conditionnel_present_nous)).setText(c.getConditionnelPresentNous());
        ((TextView)findViewById(R.id.conditionnel_present_vous)).setText(c.getConditionnelPresentVous());
        ((TextView)findViewById(R.id.conditionnel_present_ils)).setText(c.getConditionnelPresentIls());
        ((TextView)findViewById(R.id.conditionnel_passe_je)).setText(c.getConditionnelPasseJe());
        ((TextView)findViewById(R.id.conditionnel_passe_tu)).setText(c.getConditionnelPasseTu());
        ((TextView)findViewById(R.id.conditionnel_passe_il)).setText(c.getConditionnelPasseIl());
        ((TextView)findViewById(R.id.conditionnel_passe_nous)).setText(c.getConditionnelPasseNous());
        ((TextView)findViewById(R.id.conditionnel_passe_vous)).setText(c.getConditionnelPasseVous());
        ((TextView)findViewById(R.id.conditionnel_passe_ils)).setText(c.getConditionnelPasseIls());

        ((TextView)findViewById(R.id.subjonctif_present_je)).setText(c.getSubjonctifPresentJe());
        ((TextView)findViewById(R.id.subjonctif_present_tu)).setText(c.getSubjonctifPresentTu());
        ((TextView)findViewById(R.id.subjonctif_present_il)).setText(c.getSubjonctifPresentIl());
        ((TextView)findViewById(R.id.subjonctif_present_nous)).setText(c.getSubjonctifPresentNous());
        ((TextView)findViewById(R.id.subjonctif_present_vous)).setText(c.getSubjonctifPresentVous());
        ((TextView)findViewById(R.id.subjonctif_present_ils)).setText(c.getSubjonctifPresentIls());
        ((TextView)findViewById(R.id.subjonctif_passe_je)).setText(c.getSubjonctifPasseJe());
        ((TextView)findViewById(R.id.subjonctif_passe_tu)).setText(c.getSubjonctifPasseTu());
        ((TextView)findViewById(R.id.subjonctif_passe_il)).setText(c.getSubjonctifPasseIl());
        ((TextView)findViewById(R.id.subjonctif_passe_nous)).setText(c.getSubjonctifPasseNous());
        ((TextView)findViewById(R.id.subjonctif_passe_vous)).setText(c.getSubjonctifPasseVous());
        ((TextView)findViewById(R.id.subjonctif_passe_ils)).setText(c.getSubjonctifPasseIls());
        ((TextView)findViewById(R.id.subjonctif_imperfait_je)).setText(c.getSubjonctifImperfaitJe());
        ((TextView)findViewById(R.id.subjonctif_imperfait_tu)).setText(c.getSubjonctifImperfaitTu());
        ((TextView)findViewById(R.id.subjonctif_imperfait_il)).setText(c.getSubjonctifImperfaitIl());
        ((TextView)findViewById(R.id.subjonctif_imperfait_nous)).setText(c.getSubjonctifImperfaitNous());
        ((TextView)findViewById(R.id.subjonctif_imperfait_vous)).setText(c.getSubjonctifImperfaitVous());
        ((TextView)findViewById(R.id.subjonctif_imperfait_ils)).setText(c.getSubjonctifImperfaitIls());
        ((TextView)findViewById(R.id.subjonctif_plus_que_parfait_je)).setText(c.getSubjonctifPlusQueParfaitJe());
        ((TextView)findViewById(R.id.subjonctif_plus_que_parfait_tu)).setText(c.getSubjonctifPlusQueParfaitTu());
        ((TextView)findViewById(R.id.subjonctif_plus_que_parfait_il)).setText(c.getSubjonctifPlusQueParfaitIl());
        ((TextView)findViewById(R.id.subjonctif_plus_que_parfait_nous)).setText(c.getSubjonctifPlusQueParfaitNous());
        ((TextView)findViewById(R.id.subjonctif_plus_que_parfait_vous)).setText(c.getSubjonctifPlusQueParfaitVous());
        ((TextView)findViewById(R.id.subjonctif_plus_que_parfait_ils)).setText(c.getSubjonctifPlusQueParfaitIls());
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
