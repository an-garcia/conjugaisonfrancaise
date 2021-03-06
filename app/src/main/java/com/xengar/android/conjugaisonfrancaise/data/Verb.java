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

/**
 * Data that is contained for a verb item in the list mode in the main list.
 */
public class Verb {

    private long id = 0;
    private int conjugation = 0;      // Conjugation id
    private String infinitive = "";
    private String definition = "";
    private String sample1 = "";
    private String sample2 = "";
    private String sample3 = "";
    private int common = VerbContract.VerbEntry.OTHER;
    private int group = VerbContract.VerbEntry.GROUP_ALL;
    private int color = 0;
    private int score = 0;
    private String notes = "";
    private String translationEN = "";
    private String translationES = "";
    private String translationPT = "";

    /*** Constructor */
    public Verb(long id, int conjugation, String infinitive, String definition,
                String sample1, String sample2, String sample3,
                int common, int group, int color, int score,
                String notes,
                String translationEN, String translationES, String translationPT) {
        this.id = id;
        this.conjugation = conjugation;
        this.infinitive = infinitive;
        this.definition = definition;
        this.sample1 = sample1;
        this.sample2 = sample2;
        this.sample3 = sample3;
        this.common = common;
        this.group = group;
        this.color = color;
        this.score = score;
        this.notes = notes;
        this.translationEN = translationEN;
        this.translationES = translationES;
        this.translationPT = translationPT;
    }

    /* Getters and Setters */
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getInfinitive() { return infinitive; }
    public void setInfinitive(String infinitive) { this.infinitive = infinitive; }

    public String getDefinition() { return definition; }
    public void setDefinition(String definition) { this.definition = definition; }

    public String getSample1() { return sample1; }
    public void setSample1(String sample1) { this.sample1 = sample1; }
    public String getSample2() { return sample2; }
    public void setSample2(String sample1) { this.sample2 = sample2; }
    public String getSample3() { return sample3; }
    public void setSample3(String sample3) { this.sample3 = sample3; }

    public int getGroup() { return group; }
    public void setGroup(int group) { this.group = group; }

    public int getConjugation() { return conjugation; }
    public void setConjugation(int conjugation) { this.conjugation = conjugation; }

    public int getCommon() { return common; }
    public void setCommon(int common) { this.common = common; }

    public int getColor() { return color; }
    public void setColor(int color) { this.color = color; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getTranslationEN() { return translationEN; }
    public void setTranslationEN(String translationEN) { this.translationEN = translationEN; }

    public String getTranslationES() { return translationES; }
    public void setTranslationES(String translationES) { this.translationES = translationES; }

    public String getTranslationPT() { return translationPT; }
    public void setTranslationPT(String translationPT) { this.translationPT = translationPT; }

}
