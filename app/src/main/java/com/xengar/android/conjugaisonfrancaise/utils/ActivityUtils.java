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
package com.xengar.android.conjugaisonfrancaise.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.AppCompatDrawableManager;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;

import com.xengar.android.conjugaisonfrancaise.R;
import com.xengar.android.conjugaisonfrancaise.ui.HelpActivity;
import com.xengar.android.conjugaisonfrancaise.ui.SettingsActivity;

import static android.content.Context.MODE_PRIVATE;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.DEFAULT_FONT_SIZE;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.SHARED_PREF_NAME;


/**
 * ActivityUtils. To handle common tasks.
 */
public class ActivityUtils {

    private static final String TAG = ActivityUtils.class.getSimpleName();

    /**
     * Saves the variable into Preferences.
     * @param context context
     * @param name name of preference
     * @param value value
     */
    public static void saveIntToPreferences(final Context context, final String name,
                                            final int value) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor e = prefs.edit();
        e.putInt(name, value);
        e.commit();
    }

    /**
     * Saves the variable into Preferences.
     * @param context context
     * @param name name of preference
     * @param value value
     */
    public static void saveLongToPreferences(final Context context, final String name,
                                            final long value) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor e = prefs.edit();
        e.putLong(name, value);
        e.commit();
    }

    /**
     * Saves the variable into Preferences.
     * @param context context
     * @param name name of preference
     * @param value value
     */
    public static void saveBooleanToPreferences(final Context context, final String name,
                                                final boolean value) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor e = prefs.edit();
        e.putBoolean(name, value);
        e.commit();
    }

    /**
     * Saves the variable into Preferences.
     * @param context context
     * @param name name of preference
     * @param value value
     */
    public static void saveStringToPreferences(final Context context, final String name,
                                                final String value) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor e = prefs.edit();
        e.putString(name, value);
        e.commit();
    }

    /*
    public static void launchDetailsActivity(final Context context, final long id,
                                             final boolean demoMode) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Bundle bundle = new Bundle();
        bundle.putLong(VERB_ID, id);
        bundle.putBoolean(DEMO_MODE, demoMode);
        intent.putExtras(bundle);

        context.startActivity(intent);
    }*/

    /**
     * Launches Help Activity.
     * @param context context
     */
    public static void launchHelpActivity(final Context context) {
        Intent intent = new Intent(context, HelpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * Launches Settings Activity.
     * @param context context
     */
    public static void launchSettingsActivity(final Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        intent.putExtra( PreferenceActivity.EXTRA_SHOW_FRAGMENT,
                SettingsActivity.GeneralPreferenceFragment.class.getName() );
        intent.putExtra( PreferenceActivity.EXTRA_NO_HEADERS, true );
        intent.putExtra( PreferenceActivity.EXTRA_SHOW_FRAGMENT_TITLE, R.string.settings);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * Launches Search Activity.
     * @param context context
     *//*
    public static void launchSearchActivity(final Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }*/

    /**
     * Helper class to handle deprecated method.
     * Source: http://stackoverflow.com/questions/37904739/html-fromhtml-deprecated-in-android-n
     * @param html html string
     * @return Spanned
     */
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        Spanned result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    /**
     * Returns the value of show definitions from preferences.
     * @param context context
     * @return boolean or default(true)
     *//*
    public static boolean getPreferenceShowDefinitions(final Context context) {
        String key = context.getString(R.string.pref_show_definitions_switch);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(key, true);
    }*/

    /**
     * Returns the value of show definitions from preferences.
     * @param context context
     * @return boolean or default(true)
     */
    public static String getPreferenceFontSize(final Context context) {
        String key = context.getString(R.string.pref_font_size);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(key, DEFAULT_FONT_SIZE);
    }

    /**
     * Returns the translation language from preferences.
     * @param context Context
     * @return code of language (default NONE)
     *//*
    public static String getPreferenceTranslationLanguage(final Context context) {
        String key = context.getString(R.string.pref_translation_language);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String lang =  prefs.getString(key, "None");
        switch (lang){
            case "":
            case "None":
            default:
                return NONE;
            case "fr_FR":
                return FRENCH;
            case "es_ES":
                return SPANISH;
            case "pt_PT":
                return PORTUGUESE;
        }
    }*/


    /**
     * Returns the value of enable verb notifications from preferences.
     * @param context context
     * @return boolean or default(true)
     *//*
    public static boolean getPreferenceEnableNotifications(final Context context) {
        String key = context.getString(R.string.pref_enable_notifications);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(key, true);
    }*/

    /**
     * Returns the notification list from preferences.
     * @param context Context
     * @return list of notifications to use
     *//*
    public static String getPreferenceNotificationList(final Context context) {
        String key = context.getString(R.string.pref_notification_list);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String notificationList =  prefs.getString(key, "25");
        switch (notificationList){
            case "1":
                return FAVORITES;
            case "50":
                return MOST_COMMON_50;
            case "100":
                return MOST_COMMON_100;
            case "250":
                return MOST_COMMON_250;
            case "500":
                return MOST_COMMON_500;
            case "1000":
                return MOST_COMMON_1000;
            case "9000":
                return MOST_COMMON_ALL;
            case "25":
            default:
                return MOST_COMMON_25;
        }
    }*/

    /**
     * Returns the notification frequency from preferences.
     * @param context Context
     * @return notification frequency
     *//*
    public static int getPreferenceNotificationFrequency(final Context context) {
        String key = context.getString(R.string.pref_notification_frequency);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String frequency =  prefs.getString(key, "24");
        switch (frequency){
            case "1":
            case "3":
            case "6":
            case "12":
                return Integer.parseInt(frequency);
            case "24":
            default:
                return 24;
        }
    }*/

    /**
     * Returns the notification time from preferences.
     * @param context Context
     * @return notification time
     *//*
    public static Long getPreferenceNotificationTime(final Context context) {
        String key = context.getString(R.string.pref_notification_time);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Long time =  prefs.getLong(key, 0);
        return time;
    }*/

    /**
     * Set the correct translation or hide the view.
     * @param context Context
     * @param textView view
     * @param verb Verb
     *//*
    public static void setTranslation(final Context context, TextView textView, Verb verb) {
        int fontSize = Integer.parseInt(getPreferenceFontSize(context));
        switch (getPreferenceTranslationLanguage(context)) {
            case NONE:
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
                textView.setVisibility(View.GONE);
                break;
            case FRENCH:
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
                textView.setText(verb.getTranslationFR());
                break;
            case SPANISH:
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
                textView.setText(verb.getTranslationES());
                break;
            case PORTUGUESE:
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
                textView.setText(verb.getTranslationPT());
                break;
        }
    }*/

    /**
     * Text we want to speak.
     * @param text String
     *//*
    public static void speak(final Context context, TextToSpeech tts, String text){
        // Use the current media player volume
        AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        int volume = am.getStreamVolume(am.STREAM_MUSIC);
        am.setStreamVolume(am.STREAM_MUSIC, volume, 0);

        // Speak
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }else{
            //noinspection deprecation
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }*/

    /**
     * Generate all table verb columns.
     * @return String[]
     *//*
    public static String[] allVerbColumns(){
        return new String[]{
                VerbEntry.COLUMN_INFINITIVE,
                VerbEntry.COLUMN_SIMPLE_PAST,
                VerbEntry.COLUMN_PAST_PARTICIPLE,
                VerbEntry.COLUMN_ID,
                VerbEntry.COLUMN_DEFINITION,
                VerbEntry.COLUMN_PHONETIC_INFINITIVE,
                VerbEntry.COLUMN_PHONETIC_SIMPLE_PAST,
                VerbEntry.COLUMN_PHONETIC_PAST_PARTICIPLE,
                VerbEntry.COLUMN_SAMPLE_1,
                VerbEntry.COLUMN_SAMPLE_2,
                VerbEntry.COLUMN_SAMPLE_3,
                VerbEntry.COLUMN_COMMON,
                VerbEntry.COLUMN_REGULAR,
                VerbEntry.COLUMN_COLOR,
                VerbEntry.COLUMN_SCORE,
                VerbEntry.COLUMN_SOURCE,
                VerbEntry.COLUMN_NOTES,
                VerbEntry.COLUMN_TRANSLATION_ES,
                VerbEntry.COLUMN_TRANSLATION_FR,
                VerbEntry.COLUMN_TRANSLATION_PT, };
    }*/

    /**
     * Create a Verb from the current cursor position.
     * Note: columns must exist.
     * @param cursor Cursor
     * @return Verb
     *//*
    public static Verb verbFromCursor(final Cursor cursor){
        return new Verb(cursor.getLong(cursor.getColumnIndex(VerbEntry.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(VerbEntry.COLUMN_INFINITIVE)),
                cursor.getString(cursor.getColumnIndex(VerbEntry.COLUMN_SIMPLE_PAST)),
                cursor.getString(cursor.getColumnIndex(VerbEntry.COLUMN_PAST_PARTICIPLE)),
                cursor.getString(cursor.getColumnIndex(VerbEntry.COLUMN_DEFINITION)),
                cursor.getString(cursor.getColumnIndex(VerbEntry.COLUMN_SAMPLE_1)),
                cursor.getString(cursor.getColumnIndex(VerbEntry.COLUMN_SAMPLE_2)),
                cursor.getString(cursor.getColumnIndex(VerbEntry.COLUMN_SAMPLE_3)),
                cursor.getString(cursor.getColumnIndex(VerbEntry.COLUMN_PHONETIC_INFINITIVE)),
                cursor.getString(cursor.getColumnIndex(VerbEntry.COLUMN_PHONETIC_SIMPLE_PAST)),
                cursor.getString(cursor.getColumnIndex(VerbEntry.COLUMN_PHONETIC_PAST_PARTICIPLE)),
                cursor.getInt(cursor.getColumnIndex(VerbEntry.COLUMN_COMMON)),
                cursor.getInt(cursor.getColumnIndex(VerbEntry.COLUMN_REGULAR)),
                cursor.getInt(cursor.getColumnIndex(VerbEntry.COLUMN_COLOR)),
                cursor.getInt(cursor.getColumnIndex(VerbEntry.COLUMN_SCORE)),
                cursor.getInt(cursor.getColumnIndex(VerbEntry.COLUMN_SOURCE)),
                cursor.getString(cursor.getColumnIndex(VerbEntry.COLUMN_NOTES)),
                cursor.getString(cursor.getColumnIndex(VerbEntry.COLUMN_TRANSLATION_ES)),
                cursor.getString(cursor.getColumnIndex(VerbEntry.COLUMN_TRANSLATION_FR)),
                cursor.getString(cursor.getColumnIndex(VerbEntry.COLUMN_TRANSLATION_PT)) );
    }*/

    /**
     * Sets the image id into the view.
     * @param context context
     * @param view imageView
     * @param id id
     */
    @SuppressWarnings("deprecation")
    public static void setImage(final Context context, final ImageView view, final int id){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setImageDrawable(context.getResources().getDrawable(id, context.getTheme()));
        } else {
            view.setImageDrawable(AppCompatDrawableManager.get().getDrawable(context, id));
        }
        view.setVisibility(View.VISIBLE);
    }

    /**
     * Launch share text intent.
     * @param activity Activity
     * @param text String
     */
    public static void launchShareText(final Activity activity, final String text){
        // https://medium.com/google-developers/sharing-content-between-android-apps-2e6db9d1368b#.6usvw9n9p
        Intent shareIntent = ShareCompat.IntentBuilder.from(activity)
                .setType("text/plain")
                .setText(text)
                .getIntent();
        if (shareIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(shareIntent);
        }
    }

    /**
     * Creates a pending intent.
     * @param context Context
     * @return PendingIntent
     *//*
    public static PendingIntent createPendingIntent(final Context context) {
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        return PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
    }*/

    /**
     * Start an Alarm
     * @param context Context
     *//*
    public static void startAlarm(final Context context) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        PendingIntent pendingIntent = createPendingIntent(context);
        long startTime = getPreferenceNotificationTime(context);
        long interval = INTERVAL_HOUR * getPreferenceNotificationFrequency(context);

        if (USE_TEST_ALARM_INTERVALS){
            interval = 8000; // 8 seconds
        }
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, startTime, interval, pendingIntent);
        if (LOG) {
            Toast.makeText(context, "Alarm Set", Toast.LENGTH_SHORT).show();
        }
    }*/

    /**
     * Cancel Alarm
     * @param context Context
     *//*
    public static void cancelAlarm(final Context context) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = createPendingIntent(context);
        manager.cancel(pendingIntent);
        if (LOG) {
            Toast.makeText(context, "Alarm Canceled", Toast.LENGTH_SHORT).show();
        }
    }*/


    /**
     * Check if it's the first run, and launch the Verb Notifications with AlarmManager.
     * @param context Context
     * @param firebaseAnalytics FirebaseAnalytics
     *//*
    public static void checkFirstRun(Context context, FirebaseAnalytics firebaseAnalytics) {
        final int DOESNT_EXIST = -1;

        // Get current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;

        // Get saved version code
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        long savedVersionCode = prefs.getLong(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        if (currentVersionCode == savedVersionCode) {
            // This is just a normal run
            return;

        } else if (savedVersionCode == DOESNT_EXIST
                || currentVersionCode > savedVersionCode) {
            // This is a new install (or the user cleared the shared preferences) or upgrade
            ActivityUtils.startAlarm(context);
            ActivityUtils.firebaseAnalyticsLogEventSelectContent(firebaseAnalytics,
                    TYPE_START_NOTIFICATIONS, "First Run", TYPE_VERB_NOTIFICATION);
        }

        // Update the shared preferences with the current version code
        saveLongToPreferences(context, PREF_VERSION_CODE_KEY, currentVersionCode);
    }*/


    /**
     * Initializes and show the AdMob banner.
     * Needs to be called in onCreate of the activity.
     * https://firebase.google.com/docs/admob/android/quick-start
     * @param activity activity
     * @param listener LogAdListener
     *//*
    public static AdView createAdMobBanner(final AppCompatActivity activity,
                                           final LogAdListener listener) {
        final String adMobAppId = activity.getString(R.string.admob_app_id);
        // Initialize AdMob
        MobileAds.initialize(activity.getApplicationContext(), adMobAppId);

        AdView adView = (AdView) activity.findViewById(R.id.adView);
        // Set listener
        // https://firebase.google.com/docs/admob/android/ad-events
        adView.setAdListener(listener);

        // Load an ad into the AdMob banner view.
        AdRequest adRequest;
        if (USE_TEST_ADS) {
            adRequest = new AdRequest.Builder()
                    // Use AdRequest.Builder.addTestDevice() to get test ads on this device.
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    // SLONE SLONE Pilot_S5004 (Android 6.0, API 23)
                    .addTestDevice("4DF5D2AB04EBFA06FB2656A06D2C0EE3")
                    .build();
        } else {
            adRequest = new AdRequest.Builder().build();
        }
        adView.loadAd(adRequest);

        return adView;
    }*/

    /**
     * Logs a Firebase Analytics select content event.
     * https://firebase.google.com/docs/reference/android/com/google/firebase/analytics/FirebaseAnalytics.Event#SELECT_CONTENT
     * @param analytics FirebaseAnalytics
     * @param id id
     * @param name name
     * @param type type
     *//*
    public static void firebaseAnalyticsLogEventSelectContent(final FirebaseAnalytics analytics,
                                                              final String id, final String name,
                                                              final String type) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, type);
        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }*/

    /**
     * Logs a Firebase Analytics search event.
     * https://firebase.google.com/docs/reference/android/com/google/firebase/analytics/FirebaseAnalytics.Event#SEARCH
     * @param analytics FirebaseAnalytics
     * @param search string to search
     *//*
    public static void firebaseAnalyticsLogEventSearch(final FirebaseAnalytics analytics,
                                                       final String search) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SEARCH_TERM, search);
        analytics.logEvent(FirebaseAnalytics.Event.SEARCH, bundle);
    }*/

    /**
     * Logs a Firebase Analytics view search results event.
     * https://firebase.google.com/docs/reference/android/com/google/firebase/analytics/FirebaseAnalytics.Event#VIEW_SEARCH_RESULTS
     * @param analytics FirebaseAnalytics
     * @param search string to search
     *//*
    public static void firebaseAnalyticsLogEventViewSearchResults(final FirebaseAnalytics analytics,
                                                                  final String search) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SEARCH_TERM, search);
        analytics.logEvent(FirebaseAnalytics.Event.VIEW_SEARCH_RESULTS, bundle);
    }*/

    /**
     * Logs a Firebase Analytics view item event.
     * https://firebase.google.com/docs/reference/android/com/google/firebase/analytics/FirebaseAnalytics.Event#VIEW_ITEM
     * @param analytics FirebaseAnalytics
     * @param id id
     * @param name name
     * @param category category
     *//*
    public static void firebaseAnalyticsLogEventViewItem(final FirebaseAnalytics analytics,
                                                         final String id, final String name,
                                                         final String category) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, category);
        analytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
    }*/
}
