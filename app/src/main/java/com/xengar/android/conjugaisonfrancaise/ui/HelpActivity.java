package com.xengar.android.conjugaisonfrancaise.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xengar.android.conjugaisonfrancaise.BuildConfig;
import com.xengar.android.conjugaisonfrancaise.R;
import com.xengar.android.conjugaisonfrancaise.utils.ActivityUtils;

/**
 * HelpActivity
 */
public class HelpActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // define click listeners
        LinearLayout header = (LinearLayout) findViewById(R.id.header_verbs);
        header.setOnClickListener(this);

        // Change text size
        int fontSize = Integer.parseInt(ActivityUtils.getPreferenceFontSize(getApplicationContext()));
        ((TextView)findViewById(R.id.description_main)).setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        ((TextView)findViewById(R.id.description_volume)).setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);

        // Obtain the FirebaseAnalytics instance.
        //mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        //ActivityUtils.firebaseAnalyticsLogEventSelectContent(mFirebaseAnalytics,
        //        PAGE_HELP, PAGE_HELP, TYPE_PAGE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.help, menu);
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
            case R.id.action_feedback:
                giveFeedback();
                return true;

            case R.id.action_problem:
                reportProblem();
                return true;

            case R.id.action_license:
                showLicense();
                return true;

            case R.id.action_version:
                showVersion();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Send feedback email.
     */
    private void reportProblem(){
        Intent sendMessage = new Intent(Intent.ACTION_SEND);
        sendMessage.setType("message/rfc822");
        sendMessage.putExtra(Intent.EXTRA_EMAIL, new String[]{
                getResources().getString(R.string.feedback_email)});
        sendMessage.putExtra(Intent.EXTRA_SUBJECT, "Conjugaison Française Problem");
        sendMessage.putExtra(Intent.EXTRA_TEXT,
                getResources().getString(R.string.problem_message));
        try {
            startActivity(Intent.createChooser(sendMessage, "Report a problem"));
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Communication app not found",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Send feedback email.
     */
    private void giveFeedback(){
        Intent sendMessage = new Intent(Intent.ACTION_SEND);
        sendMessage.setType("message/rfc822");
        sendMessage.putExtra(Intent.EXTRA_EMAIL, new String[]{
                getResources().getString(R.string.feedback_email)});
        sendMessage.putExtra(Intent.EXTRA_SUBJECT, "Conjugaison Française Feedback");
        sendMessage.putExtra(Intent.EXTRA_TEXT,
                getResources().getString(R.string.feedback_message));
        try {
            startActivity(Intent.createChooser(sendMessage, "Give Feedback"));
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Communication app not found",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Show version information
     */
    private void showVersion() {
        //set up dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.version_dialog);
        dialog.setCancelable(true);

        //set up text
        TextView text = (TextView) dialog.findViewById(R.id.version_number);
        text.setText(BuildConfig.VERSION_NAME);

        dialog.show();
    }

    /**
     * Show License information
     */
    private void showLicense() {
        // retrieve display dimensions
        Rect displayRectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

        LayoutInflater inflater =
                (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.license_dialog, null);
        layout.setMinimumWidth((int)(displayRectangle.width() * 0.75f));
        layout.setMinimumHeight((int)(displayRectangle.height() * 0.75f));

        //set up dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(layout);
        dialog.setCancelable(true);

        //set up text
        TextView text = (TextView) dialog.findViewById(R.id.copyright);
        text.setText(ActivityUtils.fromHtml(getString(R.string.eula_string)));

        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.header_verbs:
                // begin
                //ActivityUtils.launchDetailsActivity(getApplicationContext(), 26, true);
                //ActivityUtils.firebaseAnalyticsLogEventSelectContent(mFirebaseAnalytics,
                //        "Contextual help", "begin", TYPE_CONTEXT_HELP);
                break;
        }
    }

}
