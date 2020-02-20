/*
 * Copyright (C) 2017 Angel Newton
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

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.xengar.android.conjugaisonfrancaise.data.VerbContract.VerbEntry;

import static com.xengar.android.conjugaisonfrancaise.data.VerbContract.VerbEntry.COLUMN_GROUP;
import static com.xengar.android.conjugaisonfrancaise.data.VerbContract.VerbEntry.COLUMN_ID;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.LOG;


/**
 * {@link ContentProvider} for Verbs app.
 */
public class VerbProvider extends ContentProvider{

    /** Tag for the log messages */
    private static final String TAG = VerbProvider.class.getSimpleName();

    /** URI matcher code for the content URI for the verbs table */
    private static final int VERBS = 100;

    /** URI matcher code for the content URI for a single pet in the verbs table */
    private static final int VERB_ID = 101;
    private static final int FAVORITE_VERBS = 104;

    private static final int FAVORITES = 200;
    private static final int FAVORITE_ID = 201;

    private static final int CONJUGATIONS = 300;
    private static final int CONJUGATION_ID = 301;



    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        // The content URI of the form "content://com.xengar.android.englishverbs/verbs" will map to
        // the integer code {@link #VERBS}. This URI is used to provide access to MULTIPLE rows
        // of the verbs table.
        sUriMatcher.addURI(VerbContract.CONTENT_AUTHORITY, VerbContract.PATH_VERBS, VERBS);
        sUriMatcher.addURI(VerbContract.CONTENT_AUTHORITY, VerbContract.PATH_FAVORITE_VERBS, FAVORITE_VERBS);

        sUriMatcher.addURI(VerbContract.CONTENT_AUTHORITY, VerbContract.PATH_FAVORITES, FAVORITES);

        // The content URI of the form "content://com.xengar.android.englishverbs/verbs/#" will map
        // to the integer code {@link #VERB_ID}. This URI is used to provide access to ONE single
        // row of the verbs table.
        //
        // In this case, the "#" wildcard is used where "#" can be substituted for an integer.
        // For example, "content://com.xengar.android.englishverbs/verbs/3" matches, but
        // "content://com.xengar.android.englishverbs/verbs" (without a number at the end) doesn't.
        sUriMatcher.addURI(VerbContract.CONTENT_AUTHORITY, VerbContract.PATH_VERBS + "/#", VERB_ID);
        sUriMatcher.addURI(VerbContract.CONTENT_AUTHORITY, VerbContract.PATH_FAVORITES + "/#", FAVORITE_ID);

        sUriMatcher.addURI(VerbContract.CONTENT_AUTHORITY, VerbContract.PATH_CONJUGATIONS, CONJUGATIONS);
        sUriMatcher.addURI(VerbContract.CONTENT_AUTHORITY, VerbContract.PATH_CONJUGATIONS + "/#", CONJUGATION_ID);
    }

    /** Database helper object */
    private VerbDBHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new VerbDBHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        String columns = "";
        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case VERBS:
                // For the VERBS code, query the verbs table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the verbs table.
                cursor = database.query(VerbEntry.VERBS_TBL, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case CONJUGATIONS:
                cursor = database.query(VerbEntry.CONJUGATION_TBL, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case FAVORITES:
                cursor = database.query(VerbEntry.FAVORITES_TBL, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case VERB_ID:
                // For the VERB_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.xengar.android.englishverbs/verbs/3",
                // the selection will be "COLUMN_ID=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = VerbEntry.COLUMN_ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // This will perform a query on the verbs table where the COLUMN_ID equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(VerbEntry.VERBS_TBL, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case CONJUGATION_ID:
                selection = VerbEntry.COLUMN_ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                cursor = database.query(VerbEntry.CONJUGATION_TBL, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case FAVORITE_ID:
                selection = VerbEntry.COLUMN_ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                cursor = database.query(VerbEntry.FAVORITES_TBL, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case FAVORITE_VERBS:
                for (int i = 0; i < projection.length; i++) {
                    if (i > 0){
                        columns += ",";
                    }
                    columns += projection[i];
                }
                String sort = (sortOrder != null)? " ORDER BY " + sortOrder : "";
                cursor = database.rawQuery("SELECT " + columns + " FROM " + VerbEntry.VERBS_TBL
                        + " WHERE " + COLUMN_ID + " in "
                        + " (SELECT " + COLUMN_ID + " FROM " + VerbEntry.FAVORITES_TBL + ")"
                        + sort, null);
                break;

            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        // Set notification URI on the Cursor,
        // so we know what content URI the Cursor was created for.
        // If the data at this URI changes, then we know we need to update the Cursor.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the cursor
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case VERBS:
            case FAVORITE_VERBS:
                return VerbEntry.CONTENT_LIST_TYPE_VERB;
            case CONJUGATIONS:
                return VerbEntry.CONTENT_LIST_TYPE_CONJUGATION;
            case VERB_ID:
                return VerbEntry.CONTENT_ITEM_TYPE_VERB;
            case CONJUGATION_ID:
                return VerbEntry.CONTENT_ITEM_TYPE_CONJUGATION;
            case FAVORITES:
                return VerbEntry.CONTENT_LIST_TYPE_FAVORITE;
            case FAVORITE_ID:
                return VerbEntry.CONTENT_ITEM_TYPE_FAVORITE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FAVORITES:
                return insertFavorite(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        // Get writable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FAVORITES:
                rowsDeleted = database.delete(VerbEntry.FAVORITES_TBL, selection, selectionArgs);
                break;
            case FAVORITE_ID:
                selection = VerbEntry.COLUMN_ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(VerbEntry.FAVORITES_TBL, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case VERBS:
                return updateVerb(uri, contentValues, selection, selectionArgs);
            case VERB_ID:
                // For the VERB_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = VerbEntry.COLUMN_ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateVerb(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private void checkNull(String value, String message){
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
    }

    private void checkNullAndPositive(Integer value, String message){
        if (value != null && value < 0) {
            throw new IllegalArgumentException(message);
        }
    }

    private void checkCommonUsage(Integer commonUsage, String message){
        if (commonUsage == null || !VerbEntry.isValidCommonUsage(commonUsage)) {
            throw new IllegalArgumentException(message);
        }
    }

    private void checkGroup(Integer regular, String message){
        if (regular == null || !VerbEntry.isValidGroup(regular)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Insert a favorite into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertFavorite(Uri uri, ContentValues values) {
        // Check the values
        checkNull(values.getAsString(VerbEntry.COLUMN_ID), "Favorite requires verb id");

        // Get writable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert the new verb with the given values
        long id = database.insert(VerbEntry.FAVORITES_TBL, null, values);
        if (id == -1) {
            if (LOG) {
                Log.e(TAG, "Failed to insert row for " + uri);
            }
            return null;
        }

        // Notify all listeners that the data has changed for the favorite content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    /** If the key is there, checks that is not null */
    private void checkNotNullKeyString(ContentValues values, String key, String message){
        if (values.containsKey(key)) {
            checkNull(values.getAsString(key), message);
        }
    }

    /** If the key is there, checks that is valid */
    private void checkCommonUsage(ContentValues values, String key, String message){
        if (values.containsKey(key)) {
            checkCommonUsage(values.getAsInteger(key), message);
        }
    }

    /** If the key is there, checks that is valid */
    private void checkGroup(ContentValues values, String key, String message){
        if (values.containsKey(key)) {
            checkGroup(values.getAsInteger(key), message);
        }
    }

    /** If the key is there, checks that is valid */
    private void checkNullAndPositive(ContentValues values, String key, String message){
        if (values.containsKey(key)) {
            checkNullAndPositive(values.getAsInteger(key), message);
        }
    }

    /**
     * Update verbs in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more verbs).
     * Return the number of rows that were successfully updated.
     */
    private int updateVerb(Uri uri, ContentValues values, String selection,
                           String[] selectionArgs) {
        // Check possible value changes
        checkNotNullKeyString(values, VerbEntry.COLUMN_INFINITIVE, "Verb requires infinitive");
        checkCommonUsage(values, VerbEntry.COLUMN_COMMON,
                "Verb requires valid common usage (top 50, top 100)");
        checkGroup(values, COLUMN_GROUP, "Verb requires valid group value (1, 2, 3)");
        checkNotNullKeyString(values, VerbEntry.COLUMN_DEFINITION, "Verb requires a definition");
        /*checkNotNullKeyString(values, VerbEntry.COLUMN_SAMPLE_1, "Verb requires sample 1");
        checkNotNullKeyString(values, VerbEntry.COLUMN_SAMPLE_2, "Verb requires sample 2");
        checkNotNullKeyString(values, VerbEntry.COLUMN_SAMPLE_3, "Verb requires sample 3");*/
        checkNotNullKeyString(values, VerbEntry.COLUMN_COLOR, "Verb requires valid color");
        checkNullAndPositive(values, VerbEntry.COLUMN_SCORE, "Verb requires valid score");

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(VerbEntry.VERBS_TBL, values, selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows updated
        return rowsUpdated;
    }
}
