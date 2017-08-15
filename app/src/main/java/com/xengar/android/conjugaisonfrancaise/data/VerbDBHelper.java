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
package com.xengar.android.conjugaisonfrancaise.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.xengar.android.conjugaisonfrancaise.R;
import com.xengar.android.conjugaisonfrancaise.data.VerbContract.VerbEntry;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE;
import static com.xengar.android.conjugaisonfrancaise.data.VerbContract.VerbEntry.COLUMN_ID;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.LOG;

/**
 * Database helper for Verbs app. Manages database creation and version management.
 */
public class VerbDBHelper extends SQLiteOpenHelper {

    private static final String TAG = VerbDBHelper.class.getSimpleName();

    /** Name of the database file */
    public static final String DATABASE_NAME = "verbs.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    private final Context context;


    /**
     * Constructs a new instance of {@link VerbDBHelper}.
     * @param context of the app
     */
    public VerbDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        createCurrentSchemaVersion(db);
        insertVerbs(db);
        insertFavorites(db);
        insertConjugation(db);
    }

    /**
     * Creates the schema for version 1.
     * NOTE: If the version changes, add code for the upgrade also.
     * @param db SQLiteDatabase
     */
    private void createCurrentSchemaVersion(SQLiteDatabase db){
        // Create a String that contains the SQL statement to create the verbs table
        String query =  "CREATE TABLE " + VerbEntry.VERBS_TBL + " ("
                + VerbEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + VerbEntry.COLUMN_ID + " INTEGER NOT NULL, "
                + VerbEntry.COLUMN_CONJUGATION_NUMBER + " INTEGER NOT NULL DEFAULT 0, "
                + VerbEntry.COLUMN_INFINITIVE + " TEXT NOT NULL, "
                + VerbEntry.COLUMN_SAMPLE_1 + " TEXT, "
                + VerbEntry.COLUMN_SAMPLE_2 + " TEXT, "
                + VerbEntry.COLUMN_SAMPLE_3 + " TEXT, "
                + VerbEntry.COLUMN_COMMON + " INTEGER NOT NULL DEFAULT 0, "
                + VerbEntry.COLUMN_GROUP + " INTEGER NOT NULL DEFAULT 0, "
                + VerbEntry.COLUMN_COLOR + " INTEGER NOT NULL DEFAULT 0, "
                + VerbEntry.COLUMN_SCORE + " INTEGER NOT NULL DEFAULT 0, "
                + VerbEntry.COLUMN_DEFINITION + " TEXT NOT NULL, "
                + VerbEntry.COLUMN_NOTES + " TEXT, "
                + VerbEntry.COLUMN_TRANSLATION_EN + " TEXT, "
                + VerbEntry.COLUMN_TRANSLATION_ES + " TEXT, "
                + VerbEntry.COLUMN_TRANSLATION_PT + " TEXT);";

        // Execute the SQL statement
        db.execSQL(query);

        query = "CREATE TABLE " + VerbEntry.FAVORITES_TBL + " ("
                + VerbEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + VerbEntry.COLUMN_ID + " INTEGER NOT NULL); ";
        db.execSQL(query);

        query = "CREATE TABLE " + VerbEntry.CONJUGATION_TBL + " ("
                + VerbEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + VerbEntry.COLUMN_ID + " INTEGER NOT NULL, "
                + VerbEntry.COLUMN_TERMINATION + " TEXT, "
                + VerbEntry.COLUMN_RADICALS + " TEXT, "
                + VerbEntry.COLUMN_INFINITIVE_PRESENT + " TEXT NOT NULL, "
                + VerbEntry.COLUMN_INFINITIVE_PASSE + " TEXT NOT NULL, "
                + VerbEntry.COLUMN_PARTICIPE_PRESENT + " TEXT NOT NULL, "
                + VerbEntry.COLUMN_PARTICIPE_PASSE_1 + " TEXT NOT NULL, "
                + VerbEntry.COLUMN_PARTICIPE_PASSE_2 + " TEXT NOT NULL, "
                + VerbEntry.COLUMN_GERONDIF_PRESENT + " TEXT NOT NULL, "
                + VerbEntry.COLUMN_GERONDIF_PASSE + " TEXT NOT NULL, "

                + VerbEntry.COLUMN_IMPERATIF_PRESENT_TU + " TEXT NOT NULL, "
                + VerbEntry.COLUMN_IMPERATIF_PRESENT_NOUS + " TEXT NOT NULL, "
                + VerbEntry.COLUMN_IMPERATIF_PRESENT_VOUS + " TEXT NOT NULL, "
                + VerbEntry.COLUMN_IMPERATIF_PASSE_TU + " TEXT NOT NULL, "
                + VerbEntry.COLUMN_IMPERATIF_PASSE_NOUS + " TEXT NOT NULL, "
                + VerbEntry.COLUMN_IMPERATIF_PASSE_VOUS + " TEXT NOT NULL, "

                + VerbEntry.COLUMN_INDICATIF_PRESENT_JE + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PRESENT_TU + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PRESENT_IL + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PRESENT_NOUS + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PRESENT_VOUS + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PRESENT_ILS + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PASSE_COMPOSE_JE + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PASSE_COMPOSE_TU + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PASSE_COMPOSE_IL + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PASSE_COMPOSE_NOUS + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PASSE_COMPOSE_VOUS + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PASSE_COMPOSE_ILS + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_IMPERFAIT_JE + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_IMPERFAIT_TU + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_IMPERFAIT_IL + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_IMPERFAIT_NOUS + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_IMPERFAIT_VOUS + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_IMPERFAIT_ILS + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PLUS_QUE_PARFAIT_JE + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PLUS_QUE_PARFAIT_TU + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PLUS_QUE_PARFAIT_IL + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PLUS_QUE_PARFAIT_NOUS + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PLUS_QUE_PARFAIT_VOUS + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PLUS_QUE_PARFAIT_ILS + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PASSE_SIMPLE_JE + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PASSE_SIMPLE_TU + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PASSE_SIMPLE_IL + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PASSE_SIMPLE_NOUS + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PASSE_SIMPLE_VOUS + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PASSE_SIMPLE_ILS + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PASSE_ANTERIEUR_JE + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PASSE_ANTERIEUR_TU + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PASSE_ANTERIEUR_IL + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PASSE_ANTERIEUR_NOUS + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PASSE_ANTERIEUR_VOUS + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_PASSE_ANTERIEUR_ILS + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_FUTUR_SIMPLE_JE + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_FUTUR_SIMPLE_TU + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_FUTUR_SIMPLE_IL + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_FUTUR_SIMPLE_NOUS + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_FUTUR_SIMPLE_VOUS + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_FUTUR_SIMPLE_ILS + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_FUTUR_ANTERIEUR_JE + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_FUTUR_ANTERIEUR_TU + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_FUTUR_ANTERIEUR_IL + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_FUTUR_ANTERIEUR_NOUS + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_FUTUR_ANTERIEUR_VOUS + " TEXT, "
                + VerbEntry.COLUMN_INDICATIF_FUTUR_ANTERIEUR_ILS + " TEXT, "

                + VerbEntry.COLUMN_SUBJONTIF_PRESENT_JE + " TEXT, "
                + VerbEntry.COLUMN_SUBJONTIF_PRESENT_TU + " TEXT, "
                + VerbEntry.COLUMN_SUBJONTIF_PRESENT_IL + " TEXT, "
                + VerbEntry.COLUMN_SUBJONTIF_PRESENT_NOUS + " TEXT, "
                + VerbEntry.COLUMN_SUBJONTIF_PRESENT_VOUS + " TEXT, "
                + VerbEntry.COLUMN_SUBJONTIF_PRESENT_ILS + " TEXT, "
                + VerbEntry.COLUMN_SUBJONTIF_PASSE_JE + " TEXT, "
                + VerbEntry.COLUMN_SUBJONTIF_PASSE_TU + " TEXT, "
                + VerbEntry.COLUMN_SUBJONTIF_PASSE_IL + " TEXT, "
                + VerbEntry.COLUMN_SUBJONTIF_PASSE_NOUS + " TEXT, "
                + VerbEntry.COLUMN_SUBJONTIF_PASSE_VOUS + " TEXT, "
                + VerbEntry.COLUMN_SUBJONTIF_PASSE_ILS + " TEXT, "
                + VerbEntry.COLUMN_SUBJONTIF_IMPERFAIT_JE + " TEXT, "
                + VerbEntry.COLUMN_SUBJONTIF_IMPERFAIT_TU + " TEXT, "
                + VerbEntry.COLUMN_SUBJONTIF_IMPERFAIT_IL + " TEXT, "
                + VerbEntry.COLUMN_SUBJONTIF_IMPERFAIT_NOUS + " TEXT, "
                + VerbEntry.COLUMN_SUBJONTIF_IMPERFAIT_VOUS + " TEXT, "
                + VerbEntry.COLUMN_SUBJONTIF_IMPERFAIT_ILS + " TEXT, "
                + VerbEntry.COLUMN_SUBJONTIF_PLUS_QUE_PARFAIT_JE + " TEXT, "
                + VerbEntry.COLUMN_SUBJONTIF_PLUS_QUE_PARFAIT_TU + " TEXT, "
                + VerbEntry.COLUMN_SUBJONTIF_PLUS_QUE_PARFAIT_IL + " TEXT, "
                + VerbEntry.COLUMN_SUBJONTIF_PLUS_QUE_PARFAIT_NOUS + " TEXT, "
                + VerbEntry.COLUMN_SUBJONTIF_PLUS_QUE_PARFAIT_VOUS + " TEXT, "
                + VerbEntry.COLUMN_SUBJONTIF_PLUS_QUE_PARFAIT_ILS + " TEXT, "

                + VerbEntry.COLUMN_CONDITIONNEL_PRESENT_JE + " TEXT, "
                + VerbEntry.COLUMN_CONDITIONNEL_PRESENT_TU + " TEXT, "
                + VerbEntry.COLUMN_CONDITIONNEL_PRESENT_IL + " TEXT, "
                + VerbEntry.COLUMN_CONDITIONNEL_PRESENT_NOUS + " TEXT, "
                + VerbEntry.COLUMN_CONDITIONNEL_PRESENT_VOUS + " TEXT, "
                + VerbEntry.COLUMN_CONDITIONNEL_PRESENT_ILS + " TEXT, "
                + VerbEntry.COLUMN_CONDITIONNEL_PASSE_JE + " TEXT, "
                + VerbEntry.COLUMN_CONDITIONNEL_PASSE_TU + " TEXT, "
                + VerbEntry.COLUMN_CONDITIONNEL_PASSE_IL + " TEXT, "
                + VerbEntry.COLUMN_CONDITIONNEL_PASSE_NOUS + " TEXT, "
                + VerbEntry.COLUMN_CONDITIONNEL_PASSE_VOUS + " TEXT, "
                + VerbEntry.COLUMN_CONDITIONNEL_PASSE_ILS + " TEXT);";

        db.execSQL(query);
    }


    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion > newVersion) {
            // This should not happen, version numbers should increment. Start clean.
            db.execSQL("DROP TABLE IF EXISTS " + VerbEntry.VERBS_TBL);
            db.execSQL("DROP TABLE IF EXISTS " + VerbEntry.FAVORITES_TBL);
            db.execSQL("DROP TABLE IF EXISTS " + VerbEntry.CONJUGATION_TBL);
        }

        // Update version by version using a method for the update.
        switch(oldVersion) {
            default:
                break;
        }
    }

    /**
     * Insert the 5 most common verbs.
     * @param db SQLiteDatabase
     */
    private void insertFavorites(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        ContentValues updateValues = new ContentValues();
        final String FAVORITES_COLOR = "" + ContextCompat.getColor(context, R.color.colorDeepOrange);
        for (int i = 0; i < favorites.length; i++) {
            values.put("_id", i);
            values.put(COLUMN_ID, favorites[i][0]);
            db.insertWithOnConflict(VerbEntry.FAVORITES_TBL, null, values, CONFLICT_REPLACE );

            // Change color
            updateValues.put(VerbEntry.COLUMN_COLOR, FAVORITES_COLOR);
            db.updateWithOnConflict(VerbEntry.VERBS_TBL, updateValues,
                    COLUMN_ID + " = ?", new String[]{Integer.toString(i)}, CONFLICT_REPLACE);
        }
    }

    // List of pre-loaded favorite verbs.
    public static final String[][] favorites = {
            {"0"}, // be
            {"1"}, // have
            {"2"}, // do
            {"3"}, // say
            {"4"}  // get
    };

    /**
     * Insert default verbs.
     * @param db SQLiteDatabase
     */
    private void insertVerbs(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        final String DEFAULT_COLOR = "" + ContextCompat.getColor(context, R.color.colorBlack);
        final String DEFAULT_SCORE = "0";

        // Initialize a XmlResourceParser instance
        XmlResourceParser parser = context.getResources().getXml(R.xml.verbs);
        int eventType = -1, i = 0;
        String verbName, verbId;
        try{
            // Loop through the XML data
            while (eventType != XmlPullParser.END_DOCUMENT){
                if(eventType == XmlResourceParser.START_TAG){
                    String item = parser.getName();
                    if (item.equals("verb")){
                        values.put("_id", i);
                        verbId = parser.getAttributeValue(null, "id");
                        verbName = parser.getAttributeValue(null, "in");
                        values.put(VerbEntry.COLUMN_ID, verbId);
                        values.put(VerbEntry.COLUMN_CONJUGATION_NUMBER, parser.getAttributeValue(null, "ta"));
                        values.put(VerbEntry.COLUMN_INFINITIVE, verbName);
                        values.put(VerbEntry.COLUMN_SAMPLE_1, parser.getAttributeValue(null, "s1"));
                        values.put(VerbEntry.COLUMN_SAMPLE_2, parser.getAttributeValue(null, "s2"));
                        values.put(VerbEntry.COLUMN_SAMPLE_3, parser.getAttributeValue(null, "s3"));
                        values.put(VerbEntry.COLUMN_COMMON, parser.getAttributeValue(null, "co"));
                        values.put(VerbEntry.COLUMN_GROUP, parser.getAttributeValue(null, "gr"));
                        values.put(VerbEntry.COLUMN_COLOR, DEFAULT_COLOR);
                        values.put(VerbEntry.COLUMN_SCORE, DEFAULT_SCORE);
                        values.put(VerbEntry.COLUMN_DEFINITION, parser.getAttributeValue(null, "de"));
                        values.put(VerbEntry.COLUMN_NOTES, parser.getAttributeValue(null, "no"));
                        values.put(VerbEntry.COLUMN_TRANSLATION_EN, parser.getAttributeValue(null, "tren"));
                        values.put(VerbEntry.COLUMN_TRANSLATION_ES, parser.getAttributeValue(null, "tres"));
                        values.put(VerbEntry.COLUMN_TRANSLATION_PT, parser.getAttributeValue(null, "trpt"));
                        try {
                            db.insertWithOnConflict(VerbEntry.VERBS_TBL, null, values, CONFLICT_REPLACE);
                        } catch (Exception e){
                            if (LOG){
                                Log.e(TAG, "Error inserting verb: " + verbId + " " + verbName );
                            }
                            throw e;
                        }
                        i++;
                    }
                }
                eventType = parser.next();
            }
        } catch (IOException|XmlPullParserException e){
            e.printStackTrace();
            if (LOG){
                Log.e(TAG, "Error loading verbs xml file. ");
            }
        }
    }

    /**
     * Insert conjugation verb models.
     * @param db SQLiteDatabase
     */
    private void insertConjugation(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        // Initialize a XmlResourceParser instance
        XmlResourceParser parser = context.getResources().getXml(R.xml.conjugations);
        int eventType = -1, i = 1;
        String verbName, conjugationId;
        try{
            // Loop through the XML data
            while (eventType != XmlPullParser.END_DOCUMENT){
                if(eventType == XmlResourceParser.START_TAG){
                    String item = parser.getName();
                    if (item.equals("conjugation")){
                        values.put("_id", i);
                        conjugationId = parser.getAttributeValue(null, "id");
                        verbName = parser.getAttributeValue(null, "inf_pr");
                        values.put(VerbEntry.COLUMN_ID, conjugationId);
                        values.put(VerbEntry.COLUMN_TERMINATION, parser.getAttributeValue(null, "term"));
                        values.put(VerbEntry.COLUMN_RADICALS, parser.getAttributeValue(null, "radicals"));
                        values.put(VerbEntry.COLUMN_INFINITIVE_PRESENT, verbName);
                        values.put(VerbEntry.COLUMN_INFINITIVE_PASSE, parser.getAttributeValue(null, "inf_pa"));
                        values.put(VerbEntry.COLUMN_PARTICIPE_PRESENT, parser.getAttributeValue(null, "pa_pr"));
                        values.put(VerbEntry.COLUMN_PARTICIPE_PASSE_1, parser.getAttributeValue(null, "pa_pa1"));
                        values.put(VerbEntry.COLUMN_PARTICIPE_PASSE_2, parser.getAttributeValue(null, "pa_pa2"));
                        values.put(VerbEntry.COLUMN_GERONDIF_PRESENT, parser.getAttributeValue(null, "ge_pr"));
                        values.put(VerbEntry.COLUMN_GERONDIF_PASSE, parser.getAttributeValue(null, "ge_pa"));

                        values.put(VerbEntry.COLUMN_IMPERATIF_PRESENT_TU, parser.getAttributeValue(null, "im_pr_t"));
                        values.put(VerbEntry.COLUMN_IMPERATIF_PRESENT_NOUS, parser.getAttributeValue(null, "im_pr_n"));
                        values.put(VerbEntry.COLUMN_IMPERATIF_PRESENT_VOUS, parser.getAttributeValue(null, "im_pr_v"));
                        values.put(VerbEntry.COLUMN_IMPERATIF_PASSE_TU, parser.getAttributeValue(null, "im_pa_t"));
                        values.put(VerbEntry.COLUMN_IMPERATIF_PASSE_NOUS, parser.getAttributeValue(null, "im_pa_n"));
                        values.put(VerbEntry.COLUMN_IMPERATIF_PASSE_VOUS, parser.getAttributeValue(null, "im_pa_v"));

                        values.put(VerbEntry.COLUMN_INDICATIF_PRESENT_JE, parser.getAttributeValue(null, "in_pr_j"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PRESENT_TU, parser.getAttributeValue(null, "in_pr_t"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PRESENT_IL, parser.getAttributeValue(null, "in_pr_il"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PRESENT_NOUS, parser.getAttributeValue(null, "in_pr_n"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PRESENT_VOUS, parser.getAttributeValue(null, "in_pr_v"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PRESENT_ILS, parser.getAttributeValue(null, "in_pr_ils"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PASSE_COMPOSE_JE, parser.getAttributeValue(null, "in_pc_j"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PASSE_COMPOSE_TU, parser.getAttributeValue(null, "in_pc_t"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PASSE_COMPOSE_IL, parser.getAttributeValue(null, "in_pc_il"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PASSE_COMPOSE_NOUS, parser.getAttributeValue(null, "in_pc_n"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PASSE_COMPOSE_VOUS, parser.getAttributeValue(null, "in_pc_v"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PASSE_COMPOSE_ILS, parser.getAttributeValue(null, "in_pc_ils"));
                        values.put(VerbEntry.COLUMN_INDICATIF_IMPERFAIT_JE, parser.getAttributeValue(null, "in_im_j"));
                        values.put(VerbEntry.COLUMN_INDICATIF_IMPERFAIT_TU, parser.getAttributeValue(null, "in_im_t"));
                        values.put(VerbEntry.COLUMN_INDICATIF_IMPERFAIT_IL, parser.getAttributeValue(null, "in_im_il"));
                        values.put(VerbEntry.COLUMN_INDICATIF_IMPERFAIT_NOUS, parser.getAttributeValue(null, "in_im_n"));
                        values.put(VerbEntry.COLUMN_INDICATIF_IMPERFAIT_VOUS, parser.getAttributeValue(null, "in_im_v"));
                        values.put(VerbEntry.COLUMN_INDICATIF_IMPERFAIT_ILS, parser.getAttributeValue(null, "in_im_ils"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PLUS_QUE_PARFAIT_JE, parser.getAttributeValue(null, "in_pqp_j"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PLUS_QUE_PARFAIT_TU, parser.getAttributeValue(null, "in_pqp_t"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PLUS_QUE_PARFAIT_IL, parser.getAttributeValue(null, "in_pqp_il"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PLUS_QUE_PARFAIT_NOUS, parser.getAttributeValue(null, "in_pqp_n"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PLUS_QUE_PARFAIT_VOUS, parser.getAttributeValue(null, "in_pqp_v"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PLUS_QUE_PARFAIT_ILS, parser.getAttributeValue(null, "in_pqp_ils"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PASSE_SIMPLE_JE, parser.getAttributeValue(null, "in_ps_j"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PASSE_SIMPLE_TU, parser.getAttributeValue(null, "in_ps_t"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PASSE_SIMPLE_IL, parser.getAttributeValue(null, "in_ps_il"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PASSE_SIMPLE_NOUS, parser.getAttributeValue(null, "in_ps_n"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PASSE_SIMPLE_VOUS, parser.getAttributeValue(null, "in_ps_v"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PASSE_SIMPLE_ILS, parser.getAttributeValue(null, "in_ps_ils"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PASSE_ANTERIEUR_JE, parser.getAttributeValue(null, "in_pa_j"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PASSE_ANTERIEUR_TU, parser.getAttributeValue(null, "in_pa_t"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PASSE_ANTERIEUR_IL, parser.getAttributeValue(null, "in_pa_il"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PASSE_ANTERIEUR_NOUS, parser.getAttributeValue(null, "in_pa_n"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PASSE_ANTERIEUR_VOUS, parser.getAttributeValue(null, "in_pa_v"));
                        values.put(VerbEntry.COLUMN_INDICATIF_PASSE_ANTERIEUR_ILS, parser.getAttributeValue(null, "in_pa_ils"));
                        values.put(VerbEntry.COLUMN_INDICATIF_FUTUR_SIMPLE_JE, parser.getAttributeValue(null, "in_fs_j"));
                        values.put(VerbEntry.COLUMN_INDICATIF_FUTUR_SIMPLE_TU, parser.getAttributeValue(null, "in_fs_t"));
                        values.put(VerbEntry.COLUMN_INDICATIF_FUTUR_SIMPLE_IL, parser.getAttributeValue(null, "in_fs_il"));
                        values.put(VerbEntry.COLUMN_INDICATIF_FUTUR_SIMPLE_NOUS, parser.getAttributeValue(null, "in_fs_n"));
                        values.put(VerbEntry.COLUMN_INDICATIF_FUTUR_SIMPLE_VOUS, parser.getAttributeValue(null, "in_fs_v"));
                        values.put(VerbEntry.COLUMN_INDICATIF_FUTUR_SIMPLE_ILS, parser.getAttributeValue(null, "in_fs_ils"));
                        values.put(VerbEntry.COLUMN_INDICATIF_FUTUR_ANTERIEUR_JE, parser.getAttributeValue(null, "in_fa_j"));
                        values.put(VerbEntry.COLUMN_INDICATIF_FUTUR_ANTERIEUR_TU, parser.getAttributeValue(null, "in_fa_t"));
                        values.put(VerbEntry.COLUMN_INDICATIF_FUTUR_ANTERIEUR_IL, parser.getAttributeValue(null, "in_fa_il"));
                        values.put(VerbEntry.COLUMN_INDICATIF_FUTUR_ANTERIEUR_NOUS, parser.getAttributeValue(null, "in_fa_n"));
                        values.put(VerbEntry.COLUMN_INDICATIF_FUTUR_ANTERIEUR_VOUS, parser.getAttributeValue(null, "in_fa_v"));
                        values.put(VerbEntry.COLUMN_INDICATIF_FUTUR_ANTERIEUR_ILS, parser.getAttributeValue(null, "in_fa_ils"));

                        values.put(VerbEntry.COLUMN_SUBJONTIF_PRESENT_JE, parser.getAttributeValue(null, "su_pr_j"));
                        values.put(VerbEntry.COLUMN_SUBJONTIF_PRESENT_TU, parser.getAttributeValue(null, "su_pr_t"));
                        values.put(VerbEntry.COLUMN_SUBJONTIF_PRESENT_IL, parser.getAttributeValue(null, "su_pr_il"));
                        values.put(VerbEntry.COLUMN_SUBJONTIF_PRESENT_NOUS, parser.getAttributeValue(null, "su_pr_n"));
                        values.put(VerbEntry.COLUMN_SUBJONTIF_PRESENT_VOUS, parser.getAttributeValue(null, "su_pr_v"));
                        values.put(VerbEntry.COLUMN_SUBJONTIF_PRESENT_ILS, parser.getAttributeValue(null, "su_pr_ils"));
                        values.put(VerbEntry.COLUMN_SUBJONTIF_PASSE_JE, parser.getAttributeValue(null, "su_pa_j"));
                        values.put(VerbEntry.COLUMN_SUBJONTIF_PASSE_TU, parser.getAttributeValue(null, "su_pa_t"));
                        values.put(VerbEntry.COLUMN_SUBJONTIF_PASSE_IL, parser.getAttributeValue(null, "su_pa_il"));
                        values.put(VerbEntry.COLUMN_SUBJONTIF_PASSE_NOUS, parser.getAttributeValue(null, "su_pa_n"));
                        values.put(VerbEntry.COLUMN_SUBJONTIF_PASSE_VOUS, parser.getAttributeValue(null, "su_pa_v"));
                        values.put(VerbEntry.COLUMN_SUBJONTIF_PASSE_ILS, parser.getAttributeValue(null, "su_pa_ils"));
                        values.put(VerbEntry.COLUMN_SUBJONTIF_IMPERFAIT_JE, parser.getAttributeValue(null, "su_im_j"));
                        values.put(VerbEntry.COLUMN_SUBJONTIF_IMPERFAIT_TU, parser.getAttributeValue(null, "su_im_t"));
                        values.put(VerbEntry.COLUMN_SUBJONTIF_IMPERFAIT_IL, parser.getAttributeValue(null, "su_im_il"));
                        values.put(VerbEntry.COLUMN_SUBJONTIF_IMPERFAIT_NOUS, parser.getAttributeValue(null, "su_im_n"));
                        values.put(VerbEntry.COLUMN_SUBJONTIF_IMPERFAIT_VOUS, parser.getAttributeValue(null, "su_im_v"));
                        values.put(VerbEntry.COLUMN_SUBJONTIF_IMPERFAIT_ILS, parser.getAttributeValue(null, "su_im_ils"));
                        values.put(VerbEntry.COLUMN_SUBJONTIF_PLUS_QUE_PARFAIT_JE, parser.getAttributeValue(null, "su_pqp_j"));
                        values.put(VerbEntry.COLUMN_SUBJONTIF_PLUS_QUE_PARFAIT_TU, parser.getAttributeValue(null, "su_pqp_t"));
                        values.put(VerbEntry.COLUMN_SUBJONTIF_PLUS_QUE_PARFAIT_IL, parser.getAttributeValue(null, "su_pqp_il"));
                        values.put(VerbEntry.COLUMN_SUBJONTIF_PLUS_QUE_PARFAIT_NOUS, parser.getAttributeValue(null, "su_pqp_n"));
                        values.put(VerbEntry.COLUMN_SUBJONTIF_PLUS_QUE_PARFAIT_VOUS, parser.getAttributeValue(null, "su_pqp_v"));
                        values.put(VerbEntry.COLUMN_SUBJONTIF_PLUS_QUE_PARFAIT_ILS, parser.getAttributeValue(null, "su_pqp_ils"));

                        values.put(VerbEntry.COLUMN_CONDITIONNEL_PRESENT_JE, parser.getAttributeValue(null, "co_pr_j"));
                        values.put(VerbEntry.COLUMN_CONDITIONNEL_PRESENT_TU, parser.getAttributeValue(null, "co_pr_t"));
                        values.put(VerbEntry.COLUMN_CONDITIONNEL_PRESENT_IL, parser.getAttributeValue(null, "co_pr_il"));
                        values.put(VerbEntry.COLUMN_CONDITIONNEL_PRESENT_NOUS, parser.getAttributeValue(null, "co_pr_n"));
                        values.put(VerbEntry.COLUMN_CONDITIONNEL_PRESENT_VOUS, parser.getAttributeValue(null, "co_pr_v"));
                        values.put(VerbEntry.COLUMN_CONDITIONNEL_PRESENT_ILS, parser.getAttributeValue(null, "co_pr_ils"));
                        values.put(VerbEntry.COLUMN_CONDITIONNEL_PASSE_JE, parser.getAttributeValue(null, "co_pa_j"));
                        values.put(VerbEntry.COLUMN_CONDITIONNEL_PASSE_TU, parser.getAttributeValue(null, "co_pa_t"));
                        values.put(VerbEntry.COLUMN_CONDITIONNEL_PASSE_IL, parser.getAttributeValue(null, "co_pa_il"));
                        values.put(VerbEntry.COLUMN_CONDITIONNEL_PASSE_NOUS, parser.getAttributeValue(null, "co_pa_n"));
                        values.put(VerbEntry.COLUMN_CONDITIONNEL_PASSE_VOUS, parser.getAttributeValue(null, "co_pa_v"));
                        values.put(VerbEntry.COLUMN_CONDITIONNEL_PASSE_ILS, parser.getAttributeValue(null, "co_pa_ils"));

                        try {
                            db.insertWithOnConflict(VerbEntry.CONJUGATION_TBL, null, values, CONFLICT_REPLACE);
                        } catch (Exception e){
                            if (LOG){
                                Log.e(TAG, "Error inserting conjugation: " + conjugationId + " " + verbName );
                            }
                            throw e;
                        }
                        i++;
                    }
                }
                eventType = parser.next();
            }
        } catch (IOException|XmlPullParserException e){
            e.printStackTrace();
            if (LOG){
                Log.e(TAG, "Error loading conjugations xml file. ");
            }
        }
    }

    /**
     * Count the predefined verbs in the xml file.
     * @param context Context
     * @return count
     */
    public static int countPredefinedVerbs(final Context context){
        XmlResourceParser parser =  context.getResources().getXml(R.xml.verbs);
        int eventType = -1, count = 0;
        try{
            // Loop through the XML data
            while (eventType != XmlPullParser.END_DOCUMENT){
                if(eventType == XmlResourceParser.START_TAG){
                    String verbValue = parser.getName();
                    if (verbValue.equals("verb")){
                        count++;
                    }
                }
                eventType = parser.next();
            }
        } catch (IOException|XmlPullParserException e){
            e.printStackTrace();
            if (LOG){
                Log.e(TAG, "Error loading verbs xml file. ");
            }
        }
        return count;
    }

}
