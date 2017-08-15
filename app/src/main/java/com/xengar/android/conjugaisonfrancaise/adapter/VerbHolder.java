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
package com.xengar.android.conjugaisonfrancaise.adapter;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xengar.android.conjugaisonfrancaise.R;
import com.xengar.android.conjugaisonfrancaise.data.Verb;
import com.xengar.android.conjugaisonfrancaise.utils.ActivityUtils;

import static com.xengar.android.conjugaisonfrancaise.utils.Constants.LIST;

/**
 * VerbHolder. Represents an item view in screen
 */
public class VerbHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final Context context;
    private String layoutType = LIST;
    private TextToSpeech tts;
    private Verb verb;
    private final TextView infinitive;
    private final TextView definition;
    private final TextView translation;



    public VerbHolder(View view) {
        super(view);
        infinitive = (TextView) view.findViewById(R.id.infinitive);
        definition = (TextView) view.findViewById(R.id.definition);
        translation = (TextView) view.findViewById(R.id.translation);

        // define click listeners
        LinearLayout header = (LinearLayout) view.findViewById(R.id.verb_list_item);
        if (header != null) {
            header.setOnClickListener(this);
        }
        context = view.getContext();
        view.setOnClickListener(this);
    }

    /**
     * Fills the view with the verb object.
     * @param verb Verb
     * @param layoutType LIST, CARD
     * @param tts TextToSpeech
     */
    public void bindVerb(Verb verb, String layoutType, TextToSpeech tts) {
        this.verb = verb;
        this.layoutType = layoutType;
        this.tts = tts;
        int fontSize = Integer.parseInt(ActivityUtils.getPreferenceFontSize(context));

        // Set values
        infinitive.setText(verb.getInfinitive());
        infinitive.setTextColor(verb.getColor());
        infinitive.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);

        definition.setText(verb.getDefinition());
        definition.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        if(!ActivityUtils.getPreferenceShowDefinitions(context)) {
            definition.setVisibility(View.GONE);
        }

        ActivityUtils.setTranslation(context, translation, verb);
    }

    // Handles the item click.
    @Override
    public void onClick(View view) {
        int position = getAdapterPosition(); // gets item position
        // Check if an item was deleted, but the user clicked it before the UI removed it
        if (position == RecyclerView.NO_POSITION) {
            return;
        }

        // TODO: In card mode, Play the sounds
        switch(view.getId()) {
            default:
                if (verb != null) {
                    ActivityUtils.launchDetailsActivity(
                            context, verb.getId(), verb.getConjugation(), false);
                    //Toast.makeText(context, verb.getInfinitive(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
