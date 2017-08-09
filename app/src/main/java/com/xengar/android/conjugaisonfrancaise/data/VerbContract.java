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

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * API Contract for the Verbs
 */

public final class VerbContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private VerbContract() {}

    public static final String CONTENT_AUTHORITY = "com.xengar.android.conjugaisonfrancaise";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_VERBS = "verbs";
    public static final String PATH_FAVORITES = "favorites";
    public static final String PATH_FAVORITE_VERBS = "favorite_verbs";

    /**
     * Inner class that defines constant values for the verbs database table.
     * Each entry in the table represents a single verb.
     */
    public static final class VerbEntry implements BaseColumns {
        /** The content URI to access the verb data in the provider */
        public static final Uri CONTENT_VERBS_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_VERBS);

        public static final Uri CONTENT_FAVORITES_URI = Uri.withAppendedPath(BASE_CONTENT_URI,
                PATH_FAVORITES);
        public static final Uri CONTENT_FAVORITE_VERBS_URI = Uri.withAppendedPath(BASE_CONTENT_URI,
                PATH_FAVORITE_VERBS);

        /** The MIME type of the {@link #CONTENT_VERBS_URI} for a list of verbs. */
        public static final String CONTENT_LIST_TYPE_VERB =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VERBS;
        public static final String CONTENT_LIST_TYPE_FAVORITE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITES;

        /** The MIME type of the {@link #CONTENT_VERBS_URI} for a single verb. */
        public static final String CONTENT_ITEM_TYPE_VERB =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VERBS;
        public static final String CONTENT_ITEM_TYPE_FAVORITE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITES;

        /** Name of database table for verbs */
        public final static String VERBS_TBL = "VERBS_TBL";
        public final static String FAVORITES_TBL = "FAVORITES_TBL";

        /** Unique ID number for the verb (only for use in the database table). - Type: INTEGER */
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_ID ="ID";

        /** Table of Conjugation number - Type: TEXT */
        public final static String COLUMN_CONJUGATION_NUMBER = "CONJUGATION_ID";

        /** Verb in infinitive form. - Type: TEXT  */
        public final static String COLUMN_INFINITIVE = "INFINITIVE";

        /** Common usage of the verb. - Type: INTEGER */
        public final static String COLUMN_COMMON = "COMMON";

        /** Possible values for the common usage of the verb. */
        public static final int TOP_25 = 1;
        public static final int TOP_50 = 2;
        public static final int TOP_100 = 3;
        public static final int TOP_250 = 4;
        public static final int TOP_500 = 5;
        public static final int TOP_1000 = 6;
        public static final int OTHER = 99;
        public static final String S_TOP_25 = "" + TOP_25;
        public static final String S_TOP_50 = "" + TOP_50;
        public static final String S_TOP_100 = "" + TOP_100;
        public static final String S_TOP_250 = "" + TOP_250;
        public static final String S_TOP_500 = "" + TOP_500;
        public static final String S_TOP_1000 = "" + TOP_1000;
        public static final String S_OTHER = "" + OTHER;

        public static boolean isValidCommonUsage(int usage) {
            return usage == TOP_25 || usage == TOP_50 || usage == TOP_100 || usage == TOP_250
                    || usage == TOP_500 || usage == TOP_1000 || usage == OTHER;
        }

        /** Group verbs (1st, 2nd, 3rd, all). - Type: INTEGER */
        public final static String COLUMN_GROUP = "GROUPE";

        /** Possible values for verb groups. */
        public static final int GROUP_1 = 1;
        public static final int GROUP_2 = 2;
        public static final int GROUP_3 = 3;
        public static final int GROUP_ALL = 0;
        public static final String S_GROUP_1 = "" + GROUP_1;
        public static final String S_GROUP_2 = "" + GROUP_2;
        public static final String S_GROUP_3 = "" + GROUP_3;
        public static final String S_GROUP_ALL = "" + GROUP_ALL;

        public static boolean isValidGroup(int value) {
            return value == GROUP_1 || value == GROUP_2 || value == GROUP_3 || value == GROUP_ALL;
        }

        /** Definition of the verb. - Type: TEXT */
        public final static String COLUMN_DEFINITION = "DEFINITION";

        /** Examples of the verb. - Type: TEXT  */
        public final static String COLUMN_SAMPLE_1 = "SAMPLE1";
        public final static String COLUMN_SAMPLE_2 = "SAMPLE2";
        public final static String COLUMN_SAMPLE_3 = "SAMPLE3";

        /** Other information for the verb. - Type: TEXT */
        public final static String COLUMN_NOTES = "NOTES";

        /** Color assigned by the user. - Type: INTEGER */
        public final static String COLUMN_COLOR = "COLOR";

        /** Score assigned by using the exercises or tests. - Type: INTEGER */
        public final static String COLUMN_SCORE = "SCORE";

        /**
         * Translations of the verb into the language.
         * Type: TEXT
         */
        public final static String COLUMN_TRANSLATION_EN = "TRANSLATION_EN";
        public final static String COLUMN_TRANSLATION_ES = "TRANSLATION_ES";
        public final static String COLUMN_TRANSLATION_PT = "TRANSLATION_PT";
    }

}
