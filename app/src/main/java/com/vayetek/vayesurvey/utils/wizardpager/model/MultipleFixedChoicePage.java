/*
 * Copyright 2012 Roman Nurik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vayetek.vayesurvey.utils.wizardpager.model;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.vayetek.vayesurvey.utils.wizardpager.ui.MultipleChoiceFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * A page offering the user a number of non-mutually exclusive choices.
 */
public class MultipleFixedChoicePage extends SingleFixedChoicePage {
    private static LinkedHashMap<String,String> tempQuests = new LinkedHashMap<>();

    public static LinkedHashMap<String, String> getTempQuests() {
        return tempQuests;
    }

    public static void setTempQuests(LinkedHashMap<String, String> tempQuests) {
        MultipleFixedChoicePage.tempQuests = tempQuests;
    }

    public MultipleFixedChoicePage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return MultipleChoiceFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        StringBuilder sb = new StringBuilder();

        ArrayList<String> selections = mData.getStringArrayList(SIMPLE_DATA_KEY);
        if (selections != null && selections.size() > 0) {
            for (String selection : selections) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(selection);
            }
        }

        dest.add(new ReviewItem(getTitle(), sb.toString(), getKey()));

        tempQuests.put(getTitle(),sb.toString());

    }

    @Override
    public boolean isCompleted() {
        ArrayList<String> selections = mData.getStringArrayList(SIMPLE_DATA_KEY);
        return selections != null && selections.size() > 0;
    }
}
