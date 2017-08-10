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


import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xengar.android.conjugaisonfrancaise.R;
import com.xengar.android.conjugaisonfrancaise.adapter.VerbAdapter;
import com.xengar.android.conjugaisonfrancaise.data.Verb;
import com.xengar.android.conjugaisonfrancaise.sync.FetchVerbs;
import com.xengar.android.conjugaisonfrancaise.utils.CustomErrorView;
import com.xengar.android.conjugaisonfrancaise.utils.FragmentUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

import static com.xengar.android.conjugaisonfrancaise.utils.Constants.ALPHABET;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.COMMON_TYPE;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.GROUP_ALL;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.ITEM_TYPE;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.LIST;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.LOG;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.MOST_COMMON_ALL;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.SORT_TYPE;
import static com.xengar.android.conjugaisonfrancaise.utils.Constants.VERB_GROUP;


/**
 * UniversalFragment
 */
public class UniversalFragment extends Fragment {

    private CustomErrorView mCustomErrorView;
    private RecyclerView mRecyclerView;
    private CircularProgressBar progressBar;
    private VerbAdapter mAdapter;
    private List<Verb> mVerbs;
    private String verbGroup = GROUP_ALL;    // 1er group, 2nd group, 3rd group, all groups
    private String sortTYpe = ALPHABET; // alphabet, color, group
    private String itemType = LIST;     // card, list
    private String commonType = MOST_COMMON_ALL;
    private TextToSpeech tts;

    public UniversalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            verbGroup = getArguments().getString(VERB_GROUP, GROUP_ALL);
            itemType = getArguments().getString(ITEM_TYPE, LIST);
            sortTYpe = getArguments().getString(SORT_TYPE, ALPHABET);
            commonType = getArguments().getString(COMMON_TYPE, MOST_COMMON_ALL);
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_universal, container, false);

        mCustomErrorView = (CustomErrorView) view.findViewById(R.id.error);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        progressBar = (CircularProgressBar) view.findViewById(R.id.progressBar);
        mVerbs = new ArrayList<>();

        // initialize Speaker
        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
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
        mAdapter = new VerbAdapter(mVerbs, itemType, tts);

        return view;
    }

    public String getVerbGroup(){
        return verbGroup;
    }

    public String getSortType(){
        return sortTYpe;
    }

    public String getCommonType(){
        return commonType;
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();

        /*
        if (!FragmentUtils.checkInternetConnection(getActivity())) {
            if (LOG) {
                Log.e(TAG, "Network is not available");
            }
            onLoadFailed(new Throwable(getString(R.string.network_not_available_message)));
            return;
        }*/

        mVerbs.clear();
        fillVerbs();
    }

    private void onLoadFailed(Throwable t) {
        mCustomErrorView.setError(t);
        mCustomErrorView.setVisibility(View.VISIBLE);
        FragmentUtils.updateProgressBar(progressBar, false);
    }

    private void fillVerbs(){
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
        FragmentUtils.updateProgressBar(progressBar, true);

        FetchVerbs fetch =
                new FetchVerbs(verbGroup, sortTYpe, commonType, mAdapter,
                        getActivity().getContentResolver(), mVerbs, progressBar);
        fetch.execute();
    }

}
