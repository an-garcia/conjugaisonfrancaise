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
    }


    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query;
        if (oldVersion > newVersion) {
            // This should not happen, version numbers should increment. Start clean.
            query = "DROP TABLE IF EXISTS " +  VerbEntry.VERBS_TBL;
            db.execSQL(query);
            query = "DROP TABLE IF EXISTS " +  VerbEntry.FAVORITES_TBL;
            db.execSQL(query);
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
                    String verbValue = parser.getName();
                    if (verbValue.equals("verb")){
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
