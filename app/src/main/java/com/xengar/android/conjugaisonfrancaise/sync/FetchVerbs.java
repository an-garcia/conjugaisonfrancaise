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
package com.xengar.android.conjugaisonfrancaise.sync;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.xengar.android.conjugaisonfrancaise.adapter.VerbAdapter;
import com.xengar.android.conjugaisonfrancaise.data.Verb;
import com.xengar.android.conjugaisonfrancaise.data.VerbContract.VerbEntry;
import com.xengar.android.conjugaisonfrancaise.utils.ActivityUtils;
import com.xengar.android.conjugaisonfrancaise.utils.FragmentUtils;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

import static com.xengar.android.conjugaisonfrancaise.data.VerbContract.VerbEntry.COLUMN_COMMON;
import static com.xengar.android.conjugaisonfrancaise.data.VerbContract.VerbEntry.COLUMN_GROUP;
import static com.xengar.android.conjugaisonfrancaise.data.VerbContract.VerbEntry.S_TOP_100;
import static com.xengar.android.conjugaisonfrancaise.data.VerbContract.VerbEntry.S_TOP_1000;
import static com.xengar.android.conjugaisonfrancaise.data.VerbContract.VerbEntry.S_TOP_25;
import static com.xengar.android.conjugaisonfrancaise.data.VerbContract.VerbEntry.S_TOP_250;
import static com.xengar.android.conjugaisonfrancaise.data.VerbContract.VerbEntry.S_TOP_50;
import static com.xengar.android.conjugaisonfrancaise.data.VerbContract.VerbEntry.S_TOP_500;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.ALPHABET;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.COLOR;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.FAVORITES;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.GROUPS;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.GROUP_1;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.GROUP_2;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.GROUP_3;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.GROUP_ALL;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.LOG;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.MOST_COMMON_100;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.MOST_COMMON_1000;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.MOST_COMMON_25;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.MOST_COMMON_250;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.MOST_COMMON_50;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.MOST_COMMON_500;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.MOST_COMMON_ALL;

/**
 * FetchVerbs from the database.
 */
public class FetchVerbs extends AsyncTask<Void, Void, ArrayList<Verb>> {

    private final String TAG = FetchVerbs.class.getSimpleName();
    private final String group; // Verb group (1st group, 2nd group, 3rd group, all)
    private final String sort; // Sort order (alphabet, color, groups)
    private final String common; // Common (Top50, Top100, Top25, all)
    private final ContentResolver contentResolver;
    private final VerbAdapter adapter;
    private final List<Verb> verbs;
    private final CircularProgressBar progressBar;

    // Constructor
    public FetchVerbs(final String group, final String sort, final String common,
                      final VerbAdapter adapter, final ContentResolver contentResolver,
                      final List<Verb> verbs, final CircularProgressBar progressBar) {
        this.group = group;
        this.sort = sort;
        this.common = common;
        this.adapter = adapter;
        this.contentResolver = contentResolver;
        this.verbs = verbs;
        this.progressBar = progressBar;
    }

    @Override
    protected ArrayList<Verb> doInBackground(Void... voids) {
        // Define a projection that specifies the columns from the table we care about.
        String[] columns = ActivityUtils.allVerbColumns();
        String sortOrder;
        switch (sort){
            case ALPHABET:
            default:
                sortOrder = VerbEntry.COLUMN_INFINITIVE + " ASC";
                break;
            case COLOR:
                sortOrder = VerbEntry.COLUMN_COLOR + " DESC, " + VerbEntry.COLUMN_INFINITIVE + " ASC";
                break;
            case GROUPS:
                sortOrder = VerbEntry.COLUMN_GROUP + " ASC, " + VerbEntry.COLUMN_INFINITIVE + " ASC";
                break;
        }

        String where = null;
        List<String> listArgs = new ArrayList<>();
        switch (common){
            case MOST_COMMON_25:
                where = COLUMN_COMMON + " = ?";
                listArgs.add(S_TOP_25);
                break;
            case MOST_COMMON_50:
                where = COLUMN_COMMON + " = ? OR " + COLUMN_COMMON + " = ?";
                listArgs.add(S_TOP_25);
                listArgs.add(S_TOP_50);
                break;
            case MOST_COMMON_100:
                where = COLUMN_COMMON + " = ? OR " + COLUMN_COMMON + " = ? OR " + COLUMN_COMMON + " = ?";
                listArgs.add(S_TOP_25);
                listArgs.add(S_TOP_50);
                listArgs.add(S_TOP_100);
                break;
            case MOST_COMMON_250:
                where = COLUMN_COMMON + " = ? OR " + COLUMN_COMMON
                        + " = ? OR " + COLUMN_COMMON + " = ? OR " + COLUMN_COMMON + " = ?";
                listArgs.add(S_TOP_25);
                listArgs.add(S_TOP_50);
                listArgs.add(S_TOP_100);
                listArgs.add(S_TOP_250);
                break;
            case MOST_COMMON_500:
                where = COLUMN_COMMON + " = ? OR " + COLUMN_COMMON
                        + " = ? OR " + COLUMN_COMMON + " = ? OR " + COLUMN_COMMON + " = ? OR "
                        + COLUMN_COMMON + " = ?";
                listArgs.add(S_TOP_25);
                listArgs.add(S_TOP_50);
                listArgs.add(S_TOP_100);
                listArgs.add(S_TOP_250);
                listArgs.add(S_TOP_500);
                break;
            case MOST_COMMON_1000:
                where = COLUMN_COMMON + " = ? OR " + COLUMN_COMMON
                        + " = ? OR " + COLUMN_COMMON + " = ? OR " + COLUMN_COMMON + " = ? OR "
                        + COLUMN_COMMON + " = ? OR " + COLUMN_COMMON + " = ?";
                listArgs.add(S_TOP_25);
                listArgs.add(S_TOP_50);
                listArgs.add(S_TOP_100);
                listArgs.add(S_TOP_250);
                listArgs.add(S_TOP_500);
                listArgs.add(S_TOP_1000);
                break;
            case MOST_COMMON_ALL:
            default:
                break;
        }

        Cursor cursor;
        switch (group){
            case GROUP_1:
            case GROUP_2:
            case GROUP_3:
                if (where == null) {
                    where = COLUMN_GROUP + " = ?";
                } else {
                    where = "(" + where + ") AND " + COLUMN_GROUP + " = ?";
                }
                // group substring should match group numbers (1,2,3)
                listArgs.add(group.substring(0, 1));
            case GROUP_ALL:
            default:
                String []whereArgs = (listArgs.size() > 0)? listArgs.toArray(new String[0]) : null;
                cursor = contentResolver.query(VerbEntry.CONTENT_VERBS_URI, columns, where, whereArgs, sortOrder);
                break;

            case FAVORITES:
                cursor = contentResolver.query(VerbEntry.CONTENT_FAVORITE_VERBS_URI, columns, null, null, sortOrder);
                break;
        }

        ArrayList<Verb> verbs = new ArrayList<>();
        if (cursor != null && cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                verbs.add(ActivityUtils.verbFromCursor(cursor));
            }
        } else {
            if (LOG) {
                Log.d(TAG, "Cursor is empty");
            }
        }
        if (cursor != null)
            cursor.close();
        return verbs;
    }

    @Override
    protected void onPostExecute(ArrayList<Verb> list) {
        super.onPostExecute(list);
        if (list != null) {
            verbs.addAll(list);
            adapter.notifyDataSetChanged();
        }
        FragmentUtils.updateProgressBar(progressBar, false);
    }
}
