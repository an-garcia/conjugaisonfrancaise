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

/**
 * Data that is contained for a conjugation.
 */
public class Conjugation {

    private long id = 0;
    private String termination = "";
    private String infinitivePresent = "";
    private String infinitivePasse = "";
    private String participePresent = "";
    private String participePasse1 = "";
    private String participePasse2 = "";
    private String gerondifPresent = "";
    private String gerondifPasse = "";
    private String imperatifPresentTu = "";
    private String imperatifPresentNous = "";
    private String imperatifPresentVous = "";
    private String imperatifPasseTu = "";
    private String imperatifPasseNous = "";
    private String imperatifPasseVous = "";

    private String indicatifPresentJe = "";
    private String indicatifPresentTu = "";
    private String indicatifPresentIl = "";
    private String indicatifPresentNous = "";
    private String indicatifPresentVous = "";
    private String indicatifPresentIls = "";
    private String indicatifPasseComposeJe = "";
    private String indicatifPasseComposeTu = "";
    private String indicatifPasseComposeIl = "";
    private String indicatifPasseComposeNous = "";
    private String indicatifPasseComposeVous = "";
    private String indicatifPasseComposeIls = "";
    private String indicatifImperfaitJe = "";
    private String indicatifImperfaitTu = "";
    private String indicatifImperfaitIl = "";
    private String indicatifImperfaitNous = "";
    private String indicatifImperfaitVous = "";
    private String indicatifImperfaitIls = "";
    private String indicatifPlusQueParfaitJe = "";
    private String indicatifPlusQueParfaitTu = "";
    private String indicatifPlusQueParfaitIl = "";
    private String indicatifPlusQueParfaitNous = "";
    private String indicatifPlusQueParfaitVous = "";
    private String indicatifPlusQueParfaitIls = "";
    private String indicatifPasseSimplJe = "";
    private String indicatifPasseSimpleTu = "";
    private String indicatifPasseSimpleIl = "";
    private String indicatifPasseSimpleNous = "";
    private String indicatifPasseSimpleVous = "";
    private String indicatifPasseSimpleIls = "";
    private String indicatifPasseAnterieurJe = "";
    private String indicatifPasseAnterieurTu = "";
    private String indicatifPasseAnterieurIl = "";
    private String indicatifPasseAnterieurNous = "";
    private String indicatifPasseAnterieurVous = "";
    private String indicatifPasseAnterieurIls = "";
    private String indicatifFuturSimpleJe = "";
    private String indicatifFuturSimpleTu = "";
    private String indicatifFuturSimpleIl = "";
    private String indicatifFuturSimpleNous = "";
    private String indicatifFuturSimpleVous = "";
    private String indicatifFuturSimpleIls = "";
    private String indicatifFuturAnterieurJe = "";
    private String indicatifFuturAnterieurTu = "";
    private String indicatifFuturAnterieurIl = "";
    private String indicatifFuturAnterieurNous = "";
    private String indicatifFuturAnterieurVous = "";
    private String indicatifFuturAnterieurIls = "";

    private String subjontifPresentJe = "";
    private String subjontifPresentTu = "";
    private String subjontifPresentIl = "";
    private String subjontifPresentNous = "";
    private String subjontifPresentVous = "";
    private String subjontifPresentIls = "";
    private String subjontifPasseJe = "";
    private String subjontifPasseTu = "";
    private String subjontifPasseIl = "";
    private String subjontifPasseNous = "";
    private String subjontifPasseVous = "";
    private String subjontifPasseIls = "";
    private String subjontifImperfaitJe = "";
    private String subjontifImperfaitTu = "";
    private String subjontifImperfaitIl = "";
    private String subjontifImperfaitNous = "";
    private String subjontifImperfaitVous = "";
    private String subjontifImperfaitIls = "";
    private String subjontifPlusQueParfaitJe = "";
    private String subjontifPlusQueParfaitTu = "";
    private String subjontifPlusQueParfaitIl = "";
    private String subjontifPlusQueParfaitNous = "";
    private String subjontifPlusQueParfaitVous = "";
    private String subjontifPlusQueParfaitIls = "";

    private String conditionnelPresentJe = "";
    private String conditionnelPresentTu = "";
    private String conditionnelPresentIl = "";
    private String conditionnelPresentNous = "";
    private String conditionnelPresentVous = "";
    private String conditionnelPresentIls = "";
    private String conditionnelPasseJe = "";
    private String conditionnelPasseTu = "";
    private String conditionnelPasseIl = "";
    private String conditionnelPasseNous = "";
    private String conditionnelPasseVous = "";
    private String conditionnelPasseIls = "";

    /*** Constructor */
    public Conjugation(long id, String termination,
            String infinitivePresent, String infinitivePasse,
            String participePresent, String participePasse1, String participePasse2,
            String gerondifPresent, String gerondifPasse,
            String imperatifPresentTu, String imperatifPresentNous, String imperatifPresentVous,
            String imperatifPasseTu, String imperatifPasseNous, String imperatifPasseVous,

            String indicatifPresentJe, String indicatifPresentTu, String indicatifPresentIl,
            String indicatifPresentNous, String indicatifPresentVous, String indicatifPresentIls,
            String indicatifPasseComposeJe, String indicatifPasseComposeTu,
            String indicatifPasseComposeIl,
            String indicatifPasseComposeNous,
            String indicatifPasseComposeVous,
            String indicatifPasseComposeIls,
            String indicatifImperfaitJe,
            String indicatifImperfaitTu,
            String indicatifImperfaitIl,
            String indicatifImperfaitNous,
            String indicatifImperfaitVous,
            String indicatifImperfaitIls,
            String indicatifPlusQueParfaitJe,
            String indicatifPlusQueParfaitTu,
            String indicatifPlusQueParfaitIl,
            String indicatifPlusQueParfaitNous,
            String indicatifPlusQueParfaitVous,
            String indicatifPlusQueParfaitIls,
            String indicatifPasseSimplJe,
            String indicatifPasseSimpleTu,
            String indicatifPasseSimpleIl,
            String indicatifPasseSimpleNous,
            String indicatifPasseSimpleVous,
            String indicatifPasseSimpleIls,
            String indicatifPasseAnterieurJe,
            String indicatifPasseAnterieurTu,
            String indicatifPasseAnterieurIl,
            String indicatifPasseAnterieurNous,
            String indicatifPasseAnterieurVous,
            String indicatifPasseAnterieurIls,
            String indicatifFuturSimpleJe,
            String indicatifFuturSimpleTu,
            String indicatifFuturSimpleIl,
            String indicatifFuturSimpleNous,
            String indicatifFuturSimpleVous,
            String indicatifFuturSimpleIls,
            String indicatifFuturAnterieurJe,
            String indicatifFuturAnterieurTu,
            String indicatifFuturAnterieurIl,
            String indicatifFuturAnterieurNous,
            String indicatifFuturAnterieurVous,
            String indicatifFuturAnterieurIls,

            String subjontifPresentJe,
            String subjontifPresentTu,
            String subjontifPresentIl,
            String subjontifPresentNous,
            String subjontifPresentVous,
            String subjontifPresentIls,
            String subjontifPasseJe,
            String subjontifPasseTu,
            String subjontifPasseIl,
            String subjontifPasseNous,
            String subjontifPasseVous,
            String subjontifPasseIls,
            String subjontifImperfaitJe,
            String subjontifImperfaitTu,
            String subjontifImperfaitIl,
            String subjontifImperfaitNous,
            String subjontifImperfaitVous,
            String subjontifImperfaitIls,
            String subjontifPlusQueParfaitJe,
            String subjontifPlusQueParfaitTu,
            String subjontifPlusQueParfaitIl,
            String subjontifPlusQueParfaitNous,
            String subjontifPlusQueParfaitVous,
            String subjontifPlusQueParfaitIls,

            String conditionnelPresentJe,
            String conditionnelPresentTu,
            String conditionnelPresentIl,
            String conditionnelPresentNous,
            String conditionnelPresentVous,
            String conditionnelPresentIls,
            String conditionnelPasseJe,
            String conditionnelPasseTu,
            String conditionnelPasseIl,
            String conditionnelPasseNous,
            String conditionnelPasseVous,
            String conditionnelPasseIls) {
        this.id = id;
        this.termination = termination;
        this.infinitivePresent = infinitivePresent;
        this.infinitivePasse = infinitivePasse;
        this.participePresent = participePresent;
        this.participePasse1 = participePasse1;
        this.participePasse2 = participePasse2;
        this.gerondifPresent = gerondifPresent;
        this.gerondifPasse = gerondifPasse;
        this.imperatifPresentTu = imperatifPresentTu;
        this.imperatifPresentNous = imperatifPresentNous;
        this.imperatifPresentVous = imperatifPresentVous;
        this.imperatifPasseTu = imperatifPasseTu;
        this.imperatifPasseNous = imperatifPasseNous;
        this.imperatifPasseVous = imperatifPasseVous;

        this.indicatifPresentJe = indicatifPresentJe;
        this.indicatifPresentTu = indicatifPresentTu;
        this.indicatifPresentIl = indicatifPresentIl;
        this.indicatifPresentNous = indicatifPresentNous;
        this.indicatifPresentVous = indicatifPresentVous;
        this.indicatifPresentIls = indicatifPresentIls;
        this.indicatifPasseComposeJe = indicatifPasseComposeJe;
        this.indicatifPasseComposeTu = indicatifPasseComposeTu;
        this.indicatifPasseComposeIl = indicatifPasseComposeIl;
        this.indicatifPasseComposeNous = indicatifPasseComposeNous;
        this.indicatifPasseComposeVous = indicatifPasseComposeVous;
        this.indicatifPasseComposeIls = indicatifPasseComposeIls;
        this.indicatifImperfaitJe = indicatifImperfaitJe;
        this.indicatifImperfaitTu = indicatifImperfaitTu;
        this.indicatifImperfaitIl = indicatifImperfaitIl;
        this.indicatifImperfaitNous = indicatifImperfaitNous;
        this.indicatifImperfaitVous = indicatifImperfaitVous;
        this.indicatifImperfaitIls = indicatifImperfaitIls;
        this.indicatifPlusQueParfaitJe = indicatifPlusQueParfaitJe;
        this.indicatifPlusQueParfaitTu = indicatifPlusQueParfaitTu;
        this.indicatifPlusQueParfaitIl = indicatifPlusQueParfaitIl;
        this.indicatifPlusQueParfaitNous = indicatifPlusQueParfaitNous;
        this.indicatifPlusQueParfaitVous = indicatifPlusQueParfaitVous;
        this.indicatifPlusQueParfaitIls = indicatifPlusQueParfaitIls;
        this.indicatifPasseSimplJe = indicatifPasseSimplJe;
        this.indicatifPasseSimpleTu = indicatifPasseSimpleTu;
        this.indicatifPasseSimpleIl = indicatifPasseSimpleIl;
        this.indicatifPasseSimpleNous = indicatifPasseSimpleNous;
        this.indicatifPasseSimpleVous = indicatifPasseSimpleVous;
        this.indicatifPasseSimpleIls = indicatifPasseSimpleIls;
        this.indicatifPasseAnterieurJe = indicatifPasseAnterieurJe;
        this.indicatifPasseAnterieurTu = indicatifPasseAnterieurTu;
        this.indicatifPasseAnterieurIl = indicatifPasseAnterieurIl;
        this.indicatifPasseAnterieurNous = indicatifPasseAnterieurNous;
        this.indicatifPasseAnterieurVous = indicatifPasseAnterieurVous;
        this.indicatifPasseAnterieurIls = indicatifPasseAnterieurIls;
        this.indicatifFuturSimpleJe = indicatifFuturSimpleJe;
        this.indicatifFuturSimpleTu = indicatifFuturSimpleTu;
        this.indicatifFuturSimpleIl = indicatifFuturSimpleIl;
        this.indicatifFuturSimpleNous = indicatifFuturSimpleNous;
        this.indicatifFuturSimpleVous = indicatifFuturSimpleVous;
        this.indicatifFuturSimpleIls = indicatifFuturSimpleIls;
        this.indicatifFuturAnterieurJe = indicatifFuturAnterieurJe;
        this.indicatifFuturAnterieurTu = indicatifFuturAnterieurTu;
        this.indicatifFuturAnterieurIl = indicatifFuturAnterieurIl;
        this.indicatifFuturAnterieurNous = indicatifFuturAnterieurNous;
        this.indicatifFuturAnterieurVous = indicatifFuturAnterieurVous;
        this.indicatifFuturAnterieurIls = indicatifFuturAnterieurIls;

        this.subjontifPresentJe = subjontifPresentJe;
        this.subjontifPresentTu = subjontifPresentTu;
        this.subjontifPresentIl = subjontifPresentIl;
        this.subjontifPresentNous = subjontifPresentNous;
        this.subjontifPresentVous = subjontifPresentVous;
        this.subjontifPresentIls = subjontifPresentIls;
        this.subjontifPasseJe = subjontifPasseJe;
        this.subjontifPasseTu = subjontifPasseTu;
        this.subjontifPasseIl = subjontifPasseIl;
        this.subjontifPasseNous = subjontifPasseNous;
        this.subjontifPasseVous = subjontifPasseVous;
        this.subjontifPasseIls = subjontifPasseIls;
        this.subjontifImperfaitJe = subjontifImperfaitJe;
        this.subjontifImperfaitTu = subjontifImperfaitTu;
        this.subjontifImperfaitIl = subjontifImperfaitIl;
        this.subjontifImperfaitNous = subjontifImperfaitNous;
        this.subjontifImperfaitVous = subjontifImperfaitVous;
        this.subjontifImperfaitIls = subjontifImperfaitIls;
        this.subjontifPlusQueParfaitJe = subjontifPlusQueParfaitJe;
        this.subjontifPlusQueParfaitTu = subjontifPlusQueParfaitTu;
        this.subjontifPlusQueParfaitIl = subjontifPlusQueParfaitIl;
        this.subjontifPlusQueParfaitNous = subjontifPlusQueParfaitNous;
        this.subjontifPlusQueParfaitVous = subjontifPlusQueParfaitVous;
        this.subjontifPlusQueParfaitIls = subjontifPlusQueParfaitIls;

        this.conditionnelPresentJe = conditionnelPresentJe;
        this.conditionnelPresentTu = conditionnelPresentTu;
        this.conditionnelPresentIl = conditionnelPresentIl;
        this.conditionnelPresentNous = conditionnelPresentNous;
        this.conditionnelPresentVous = conditionnelPresentVous;
        this.conditionnelPresentIls = conditionnelPresentIls;
        this.conditionnelPasseJe = conditionnelPasseJe;
        this.conditionnelPasseTu = conditionnelPasseTu;
        this.conditionnelPasseIl = conditionnelPasseIl;
        this.conditionnelPasseNous = conditionnelPasseNous;
        this.conditionnelPasseVous = conditionnelPasseVous;
        this.conditionnelPasseIls = conditionnelPasseIls;
    }

    /* Getters and Setters */
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTermination() { return termination; }
    public void setTermination(String termination) { this.termination = termination; }

    public String getInfinitivePresent() {
        return infinitivePresent;
    }
    public void setInfinitivePresent(String infinitivePresent) {
        this.infinitivePresent = infinitivePresent;
    }

    public String getInfinitivePasse() {
        return infinitivePasse;
    }
    public void setInfinitivePasse(String infinitivePasse) {
        this.infinitivePasse = infinitivePasse;
    }

    public String getParticipePresent() {
        return participePresent;
    }
    public void setParticipePresent(String participePresent) {
        this.participePresent = participePresent;
    }

    public String getParticipePasse1() {
        return participePasse1;
    }
    public void setParticipePasse1(String participePasse1) {
        this.participePasse1 = participePasse1;
    }

    public String getParticipePasse2() {
        return participePasse2;
    }
    public void setParticipePasse2(String participePasse2) {
        this.participePasse2 = participePasse2;
    }

    public String getGerondifPresent() {
        return gerondifPresent;
    }
    public void setGerondifPresent(String gerondifPresent) {
        this.gerondifPresent = gerondifPresent;
    }

    public String getGerondifPasse() {
        return gerondifPasse;
    }
    public void setGerondifPasse(String gerondifPasse) {
        this.gerondifPasse = gerondifPasse;
    }

    public String getImperatifPresentTu() {
        return imperatifPresentTu;
    }
    public void setImperatifPresentTu(String imperatifPresentTu) {
        this.imperatifPresentTu = imperatifPresentTu;
    }

    public String getImperatifPresentNous() {
        return imperatifPresentNous;
    }
    public void setImperatifPresentNous(String imperatifPresentNous) {
        this.imperatifPresentNous = imperatifPresentNous;
    }

    public String getImperatifPresentVous() {
        return imperatifPresentVous;
    }
    public void setImperatifPresentVous(String imperatifPresentVous) {
        this.imperatifPresentVous = imperatifPresentVous;
    }

    public String getImperatifPasseTu() {
        return imperatifPasseTu;
    }
    public void setImperatifPasseTu(String imperatifPasseTu) {
        this.imperatifPasseTu = imperatifPasseTu;
    }

    public String getImperatifPasseNous() {
        return imperatifPasseNous;
    }
    public void setImperatifPasseNous(String imperatifPasseNous) {
        this.imperatifPasseNous = imperatifPasseNous;
    }

    public String getImperatifPasseVous() {
        return imperatifPasseVous;
    }
    public void setImperatifPasseVous(String imperatifPasseVous) {
        this.imperatifPasseVous = imperatifPasseVous;
    }

    public String getIndicatifPresentJe() {
        return indicatifPresentJe;
    }
    public void setIndicatifPresentJe(String indicatifPresentJe) {
        this.indicatifPresentJe = indicatifPresentJe;
    }

    public String getIndicatifPresentTu() {
        return indicatifPresentTu;
    }
    public void setIndicatifPresentTu(String indicatifPresentTu) {
        this.indicatifPresentTu = indicatifPresentTu;
    }

    public String getIndicatifPresentIl() {
        return indicatifPresentIl;
    }
    public void setIndicatifPresentIl(String indicatifPresentIl) {
        this.indicatifPresentIl = indicatifPresentIl;
    }

    public String getIndicatifPresentNous() {
        return indicatifPresentNous;
    }
    public void setIndicatifPresentNous(String indicatifPresentNous) {
        this.indicatifPresentNous = indicatifPresentNous;
    }

    public String getIndicatifPresentVous() {
        return indicatifPresentVous;
    }
    public void setIndicatifPresentVous(String indicatifPresentVous) {
        this.indicatifPresentVous = indicatifPresentVous;
    }

    public String getIndicatifPresentIls() {
        return indicatifPresentIls;
    }
    public void setIndicatifPresentIls(String indicatifPresentIls) {
        this.indicatifPresentIls = indicatifPresentIls;
    }

    public String getIndicatifPasseComposeJe() {
        return indicatifPasseComposeJe;
    }
    public void setIndicatifPasseComposeJe(String indicatifPasseComposeJe) {
        this.indicatifPasseComposeJe = indicatifPasseComposeJe;
    }

    public String getIndicatifPasseComposeTu() {
        return indicatifPasseComposeTu;
    }
    public void setIndicatifPasseComposeTu(String indicatifPasseComposeTu) {
        this.indicatifPasseComposeTu = indicatifPasseComposeTu;
    }

    public String getIndicatifPasseComposeIl() {
        return indicatifPasseComposeIl;
    }
    public void setIndicatifPasseComposeIl(String indicatifPasseComposeIl) {
        this.indicatifPasseComposeIl = indicatifPasseComposeIl;
    }

    public String getIndicatifPasseComposeNous() {
        return indicatifPasseComposeNous;
    }
    public void setIndicatifPasseComposeNous(String indicatifPasseComposeNous) {
        this.indicatifPasseComposeNous = indicatifPasseComposeNous;
    }

    public String getIndicatifPasseComposeVous() {
        return indicatifPasseComposeVous;
    }
    public void setIndicatifPasseComposeVous(String indicatifPasseComposeVous) {
        this.indicatifPasseComposeVous = indicatifPasseComposeVous;
    }

    public String getIndicatifPasseComposeIls() {
        return indicatifPasseComposeIls;
    }
    public void setIndicatifPasseComposeIls(String indicatifPasseComposeIls) {
        this.indicatifPasseComposeIls = indicatifPasseComposeIls;
    }

    public String getIndicatifImperfaitJe() {
        return indicatifImperfaitJe;
    }
    public void setIndicatifImperfaitJe(String indicatifImperfaitJe) {
        this.indicatifImperfaitJe = indicatifImperfaitJe;
    }

    public String getIndicatifImperfaitTu() {
        return indicatifImperfaitTu;
    }
    public void setIndicatifImperfaitTu(String indicatifImperfaitTu) {
        this.indicatifImperfaitTu = indicatifImperfaitTu;
    }

    public String getIndicatifImperfaitIl() {
        return indicatifImperfaitIl;
    }
    public void setIndicatifImperfaitIl(String indicatifImperfaitIl) {
        this.indicatifImperfaitIl = indicatifImperfaitIl;
    }

    public String getIndicatifImperfaitNous() {
        return indicatifImperfaitNous;
    }
    public void setIndicatifImperfaitNous(String indicatifImperfaitNous) {
        this.indicatifImperfaitNous = indicatifImperfaitNous;
    }

    public String getIndicatifImperfaitVous() {
        return indicatifImperfaitVous;
    }
    public void setIndicatifImperfaitVous(String indicatifImperfaitVous) {
        this.indicatifImperfaitVous = indicatifImperfaitVous;
    }

    public String getIndicatifImperfaitIls() {
        return indicatifImperfaitIls;
    }
    public void setIndicatifImperfaitIls(String indicatifImperfaitIls) {
        this.indicatifImperfaitIls = indicatifImperfaitIls;
    }

    public String getIndicatifPlusQueParfaitJe() {
        return indicatifPlusQueParfaitJe;
    }
    public void setIndicatifPlusQueParfaitJe(String indicatifPlusQueParfaitJe) {
        this.indicatifPlusQueParfaitJe = indicatifPlusQueParfaitJe;
    }

    public String getIndicatifPlusQueParfaitTu() {
        return indicatifPlusQueParfaitTu;
    }
    public void setIndicatifPlusQueParfaitTu(String indicatifPlusQueParfaitTu) {
        this.indicatifPlusQueParfaitTu = indicatifPlusQueParfaitTu;
    }

    public String getIndicatifPlusQueParfaitIl() {
        return indicatifPlusQueParfaitIl;
    }
    public void setIndicatifPlusQueParfaitIl(String indicatifPlusQueParfaitIl) {
        this.indicatifPlusQueParfaitIl = indicatifPlusQueParfaitIl;
    }

    public String getIndicatifPlusQueParfaitNous() {
        return indicatifPlusQueParfaitNous;
    }
    public void setIndicatifPlusQueParfaitNous(String indicatifPlusQueParfaitNous) {
        this.indicatifPlusQueParfaitNous = indicatifPlusQueParfaitNous;
    }

    public String getIndicatifPlusQueParfaitVous() {
        return indicatifPlusQueParfaitVous;
    }
    public void setIndicatifPlusQueParfaitVous(String indicatifPlusQueParfaitVous) {
        this.indicatifPlusQueParfaitVous = indicatifPlusQueParfaitVous;
    }

    public String getIndicatifPlusQueParfaitIls() {
        return indicatifPlusQueParfaitIls;
    }
    public void setIndicatifPlusQueParfaitIls(String indicatifPlusQueParfaitIls) {
        this.indicatifPlusQueParfaitIls = indicatifPlusQueParfaitIls;
    }

    public String getIndicatifPasseSimplJe() {
        return indicatifPasseSimplJe;
    }
    public void setIndicatifPasseSimplJe(String indicatifPasseSimplJe) {
        this.indicatifPasseSimplJe = indicatifPasseSimplJe;
    }

    public String getIndicatifPasseSimpleTu() {
        return indicatifPasseSimpleTu;
    }
    public void setIndicatifPasseSimpleTu(String indicatifPasseSimpleTu) {
        this.indicatifPasseSimpleTu = indicatifPasseSimpleTu;
    }

    public String getIndicatifPasseSimpleIl() {
        return indicatifPasseSimpleIl;
    }
    public void setIndicatifPasseSimpleIl(String indicatifPasseSimpleIl) {
        this.indicatifPasseSimpleIl = indicatifPasseSimpleIl;
    }

    public String getIndicatifPasseSimpleNous() {
        return indicatifPasseSimpleNous;
    }
    public void setIndicatifPasseSimpleNous(String indicatifPasseSimpleNous) {
        this.indicatifPasseSimpleNous = indicatifPasseSimpleNous;
    }

    public String getIndicatifPasseSimpleVous() {
        return indicatifPasseSimpleVous;
    }
    public void setIndicatifPasseSimpleVous(String indicatifPasseSimpleVous) {
        this.indicatifPasseSimpleVous = indicatifPasseSimpleVous;
    }

    public String getIndicatifPasseSimpleIls() {
        return indicatifPasseSimpleIls;
    }
    public void setIndicatifPasseSimpleIls(String indicatifPasseSimpleIls) {
        this.indicatifPasseSimpleIls = indicatifPasseSimpleIls;
    }

    public String getIndicatifPasseAnterieurJe() {
        return indicatifPasseAnterieurJe;
    }
    public void setIndicatifPasseAnterieurJe(String indicatifPasseAnterieurJe) {
        this.indicatifPasseAnterieurJe = indicatifPasseAnterieurJe;
    }

    public String getIndicatifPasseAnterieurTu() {
        return indicatifPasseAnterieurTu;
    }
    public void setIndicatifPasseAnterieurTu(String indicatifPasseAnterieurTu) {
        this.indicatifPasseAnterieurTu = indicatifPasseAnterieurTu;
    }

    public String getIndicatifPasseAnterieurIl() {
        return indicatifPasseAnterieurIl;
    }
    public void setIndicatifPasseAnterieurIl(String indicatifPasseAnterieurIl) {
        this.indicatifPasseAnterieurIl = indicatifPasseAnterieurIl;
    }

    public String getIndicatifPasseAnterieurNous() {
        return indicatifPasseAnterieurNous;
    }
    public void setIndicatifPasseAnterieurNous(String indicatifPasseAnterieurNous) {
        this.indicatifPasseAnterieurNous = indicatifPasseAnterieurNous;
    }

    public String getIndicatifPasseAnterieurVous() {
        return indicatifPasseAnterieurVous;
    }
    public void setIndicatifPasseAnterieurVous(String indicatifPasseAnterieurVous) {
        this.indicatifPasseAnterieurVous = indicatifPasseAnterieurVous;
    }

    public String getIndicatifPasseAnterieurIls() {
        return indicatifPasseAnterieurIls;
    }
    public void setIndicatifPasseAnterieurIls(String indicatifPasseAnterieurIls) {
        this.indicatifPasseAnterieurIls = indicatifPasseAnterieurIls;
    }

    public String getIndicatifFuturSimpleJe() {
        return indicatifFuturSimpleJe;
    }
    public void setIndicatifFuturSimpleJe(String indicatifFuturSimpleJe) {
        this.indicatifFuturSimpleJe = indicatifFuturSimpleJe;
    }

    public String getIndicatifFuturSimpleTu() {
        return indicatifFuturSimpleTu;
    }
    public void setIndicatifFuturSimpleTu(String indicatifFuturSimpleTu) {
        this.indicatifFuturSimpleTu = indicatifFuturSimpleTu;
    }

    public String getIndicatifFuturSimpleIl() {
        return indicatifFuturSimpleIl;
    }
    public void setIndicatifFuturSimpleIl(String indicatifFuturSimpleIl) {
        this.indicatifFuturSimpleIl = indicatifFuturSimpleIl;
    }

    public String getIndicatifFuturSimpleNous() {
        return indicatifFuturSimpleNous;
    }
    public void setIndicatifFuturSimpleNous(String indicatifFuturSimpleNous) {
        this.indicatifFuturSimpleNous = indicatifFuturSimpleNous;
    }

    public String getIndicatifFuturSimpleVous() {
        return indicatifFuturSimpleVous;
    }
    public void setIndicatifFuturSimpleVous(String indicatifFuturSimpleVous) {
        this.indicatifFuturSimpleVous = indicatifFuturSimpleVous;
    }

    public String getIndicatifFuturSimpleIls() {
        return indicatifFuturSimpleIls;
    }
    public void setIndicatifFuturSimpleIls(String indicatifFuturSimpleIls) {
        this.indicatifFuturSimpleIls = indicatifFuturSimpleIls;
    }

    public String getIndicatifFuturAnterieurJe() {
        return indicatifFuturAnterieurJe;
    }

    public void setIndicatifFuturAnterieurJe(String indicatifFuturAnterieurJe) {
        this.indicatifFuturAnterieurJe = indicatifFuturAnterieurJe;
    }

    public String getIndicatifFuturAnterieurTu() {
        return indicatifFuturAnterieurTu;
    }
    public void setIndicatifFuturAnterieurTu(String indicatifFuturAnterieurTu) {
        this.indicatifFuturAnterieurTu = indicatifFuturAnterieurTu;
    }

    public String getIndicatifFuturAnterieurIl() {
        return indicatifFuturAnterieurIl;
    }
    public void setIndicatifFuturAnterieurIl(String indicatifFuturAnterieurIl) {
        this.indicatifFuturAnterieurIl = indicatifFuturAnterieurIl;
    }

    public String getIndicatifFuturAnterieurNous() {
        return indicatifFuturAnterieurNous;
    }
    public void setIndicatifFuturAnterieurNous(String indicatifFuturAnterieurNous) {
        this.indicatifFuturAnterieurNous = indicatifFuturAnterieurNous;
    }

    public String getIndicatifFuturAnterieurVous() {
        return indicatifFuturAnterieurVous;
    }
    public void setIndicatifFuturAnterieurVous(String indicatifFuturAnterieurVous) {
        this.indicatifFuturAnterieurVous = indicatifFuturAnterieurVous;
    }

    public String getIndicatifFuturAnterieurIls() {
        return indicatifFuturAnterieurIls;
    }
    public void setIndicatifFuturAnterieurIls(String indicatifFuturAnterieurIls) {
        this.indicatifFuturAnterieurIls = indicatifFuturAnterieurIls;
    }

    public String getSubjontifPresentJe() {
        return subjontifPresentJe;
    }
    public void setSubjontifPresentJe(String subjontifPresentJe) {
        this.subjontifPresentJe = subjontifPresentJe;
    }

    public String getSubjontifPresentTu() {
        return subjontifPresentTu;
    }
    public void setSubjontifPresentTu(String subjontifPresentTu) {
        this.subjontifPresentTu = subjontifPresentTu;
    }

    public String getSubjontifPresentIl() {
        return subjontifPresentIl;
    }
    public void setSubjontifPresentIl(String subjontifPresentIl) {
        this.subjontifPresentIl = subjontifPresentIl;
    }

    public String getSubjontifPresentNous() {
        return subjontifPresentNous;
    }
    public void setSubjontifPresentNous(String subjontifPresentNous) {
        this.subjontifPresentNous = subjontifPresentNous;
    }

    public String getSubjontifPresentVous() {
        return subjontifPresentVous;
    }
    public void setSubjontifPresentVous(String subjontifPresentVous) {
        this.subjontifPresentVous = subjontifPresentVous;
    }

    public String getSubjontifPresentIls() {
        return subjontifPresentIls;
    }
    public void setSubjontifPresentIls(String subjontifPresentIls) {
        this.subjontifPresentIls = subjontifPresentIls;
    }

    public String getSubjontifPasseJe() {
        return subjontifPasseJe;
    }
    public void setSubjontifPasseJe(String subjontifPasseJe) {
        this.subjontifPasseJe = subjontifPasseJe;
    }

    public String getSubjontifPasseTu() {
        return subjontifPasseTu;
    }
    public void setSubjontifPasseTu(String subjontifPasseTu) {
        this.subjontifPasseTu = subjontifPasseTu;
    }

    public String getSubjontifPasseIl() {
        return subjontifPasseIl;
    }
    public void setSubjontifPasseIl(String subjontifPasseIl) {
        this.subjontifPasseIl = subjontifPasseIl;
    }

    public String getSubjontifPasseNous() {
        return subjontifPasseNous;
    }
    public void setSubjontifPasseNous(String subjontifPasseNous) {
        this.subjontifPasseNous = subjontifPasseNous;
    }

    public String getSubjontifPasseVous() {
        return subjontifPasseVous;
    }
    public void setSubjontifPasseVous(String subjontifPasseVous) {
        this.subjontifPasseVous = subjontifPasseVous;
    }

    public String getSubjontifPasseIls() {
        return subjontifPasseIls;
    }
    public void setSubjontifPasseIls(String subjontifPasseIls) {
        this.subjontifPasseIls = subjontifPasseIls;
    }

    public String getSubjontifImperfaitJe() {
        return subjontifImperfaitJe;
    }
    public void setSubjontifImperfaitJe(String subjontifImperfaitJe) {
        this.subjontifImperfaitJe = subjontifImperfaitJe;
    }

    public String getSubjontifImperfaitTu() {
        return subjontifImperfaitTu;
    }
    public void setSubjontifImperfaitTu(String subjontifImperfaitTu) {
        this.subjontifImperfaitTu = subjontifImperfaitTu;
    }

    public String getSubjontifImperfaitIl() {
        return subjontifImperfaitIl;
    }
    public void setSubjontifImperfaitIl(String subjontifImperfaitIl) {
        this.subjontifImperfaitIl = subjontifImperfaitIl;
    }

    public String getSubjontifImperfaitNous() {
        return subjontifImperfaitNous;
    }
    public void setSubjontifImperfaitNous(String subjontifImperfaitNous) {
        this.subjontifImperfaitNous = subjontifImperfaitNous;
    }

    public String getSubjontifImperfaitVous() {
        return subjontifImperfaitVous;
    }
    public void setSubjontifImperfaitVous(String subjontifImperfaitVous) {
        this.subjontifImperfaitVous = subjontifImperfaitVous;
    }

    public String getSubjontifImperfaitIls() {
        return subjontifImperfaitIls;
    }
    public void setSubjontifImperfaitIls(String subjontifImperfaitIls) {
        this.subjontifImperfaitIls = subjontifImperfaitIls;
    }

    public String getSubjontifPlusQueParfaitJe() {
        return subjontifPlusQueParfaitJe;
    }
    public void setSubjontifPlusQueParfaitJe(String subjontifPlusQueParfaitJe) {
        this.subjontifPlusQueParfaitJe = subjontifPlusQueParfaitJe;
    }

    public String getSubjontifPlusQueParfaitTu() {
        return subjontifPlusQueParfaitTu;
    }
    public void setSubjontifPlusQueParfaitTu(String subjontifPlusQueParfaitTu) {
        this.subjontifPlusQueParfaitTu = subjontifPlusQueParfaitTu;
    }

    public String getSubjontifPlusQueParfaitIl() {
        return subjontifPlusQueParfaitIl;
    }
    public void setSubjontifPlusQueParfaitIl(String subjontifPlusQueParfaitIl) {
        this.subjontifPlusQueParfaitIl = subjontifPlusQueParfaitIl;
    }

    public String getSubjontifPlusQueParfaitNous() {
        return subjontifPlusQueParfaitNous;
    }
    public void setSubjontifPlusQueParfaitNous(String subjontifPlusQueParfaitNous) {
        this.subjontifPlusQueParfaitNous = subjontifPlusQueParfaitNous;
    }

    public String getSubjontifPlusQueParfaitVous() {
        return subjontifPlusQueParfaitVous;
    }
    public void setSubjontifPlusQueParfaitVous(String subjontifPlusQueParfaitVous) {
        this.subjontifPlusQueParfaitVous = subjontifPlusQueParfaitVous;
    }

    public String getSubjontifPlusQueParfaitIls() {
        return subjontifPlusQueParfaitIls;
    }
    public void setSubjontifPlusQueParfaitIls(String subjontifPlusQueParfaitIls) {
        this.subjontifPlusQueParfaitIls = subjontifPlusQueParfaitIls;
    }

    public String getConditionnelPresentJe() {
        return conditionnelPresentJe;
    }
    public void setConditionnelPresentJe(String conditionnelPresentJe) {
        this.conditionnelPresentJe = conditionnelPresentJe;
    }

    public String getConditionnelPresentTu() {
        return conditionnelPresentTu;
    }
    public void setConditionnelPresentTu(String conditionnelPresentTu) {
        this.conditionnelPresentTu = conditionnelPresentTu;
    }

    public String getConditionnelPresentIl() {
        return conditionnelPresentIl;
    }
    public void setConditionnelPresentIl(String conditionnelPresentIl) {
        this.conditionnelPresentIl = conditionnelPresentIl;
    }

    public String getConditionnelPresentNous() {
        return conditionnelPresentNous;
    }
    public void setConditionnelPresentNous(String conditionnelPresentNous) {
        this.conditionnelPresentNous = conditionnelPresentNous;
    }

    public String getConditionnelPresentVous() {
        return conditionnelPresentVous;
    }
    public void setConditionnelPresentVous(String conditionnelPresentVous) {
        this.conditionnelPresentVous = conditionnelPresentVous;
    }

    public String getConditionnelPresentIls() {
        return conditionnelPresentIls;
    }
    public void setConditionnelPresentIls(String conditionnelPresentIls) {
        this.conditionnelPresentIls = conditionnelPresentIls;
    }

    public String getConditionnelPasseJe() {
        return conditionnelPasseJe;
    }
    public void setConditionnelPasseJe(String conditionnelPasseJe) {
        this.conditionnelPasseJe = conditionnelPasseJe;
    }

    public String getConditionnelPasseTu() {
        return conditionnelPasseTu;
    }
    public void setConditionnelPasseTu(String conditionnelPasseTu) {
        this.conditionnelPasseTu = conditionnelPasseTu;
    }

    public String getConditionnelPasseIl() {
        return conditionnelPasseIl;
    }
    public void setConditionnelPasseIl(String conditionnelPasseIl) {
        this.conditionnelPasseIl = conditionnelPasseIl;
    }

    public String getConditionnelPasseNous() {
        return conditionnelPasseNous;
    }
    public void setConditionnelPasseNous(String conditionnelPasseNous) {
        this.conditionnelPasseNous = conditionnelPasseNous;
    }

    public String getConditionnelPasseVous() {
        return conditionnelPasseVous;
    }
    public void setConditionnelPasseVous(String conditionnelPasseVous) {
        this.conditionnelPasseVous = conditionnelPasseVous;
    }

    public String getConditionnelPasseIls() {
        return conditionnelPasseIls;
    }
    public void setConditionnelPasseIls(String conditionnelPasseIls) {
        this.conditionnelPasseIls = conditionnelPasseIls;
    }

}
