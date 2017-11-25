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

package com.vayetek.vayesurvey.activities;

import android.content.Context;

import com.vayetek.vayesurvey.activities.pages.CustomerInfoPage;
import com.vayetek.vayesurvey.models.Citizen;
import com.vayetek.vayesurvey.models.Question;
import com.vayetek.vayesurvey.models.Response;
import com.vayetek.vayesurvey.models.Survey;
import com.vayetek.vayesurvey.utils.wizardpager.model.AbstractWizardModel;
import com.vayetek.vayesurvey.utils.wizardpager.model.BranchPage;
import com.vayetek.vayesurvey.utils.wizardpager.model.MultipleFixedChoicePage;
import com.vayetek.vayesurvey.utils.wizardpager.model.NumberPage;
import com.vayetek.vayesurvey.utils.wizardpager.model.Page;
import com.vayetek.vayesurvey.utils.wizardpager.model.PageList;
import com.vayetek.vayesurvey.utils.wizardpager.model.SingleFixedChoicePage;
import com.vayetek.vayesurvey.utils.wizardpager.model.TextPage;

import java.util.ArrayList;

public class QuestionWizardModel extends AbstractWizardModel {

    private Citizen citizen;

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    public QuestionWizardModel(Context context) {
        super(context);

    }

   public Question[] questions;

    public Question[] getQuestions() {
        return questions;
    }

    public void setQuestions(Question[] questions) {
        this.questions = questions;
    }

    @Override
    protected PageList onNewRootPageList() {

        //CustomerInfoPage citizPage = (CustomerInfoPage) new CustomerInfoPage(this, "Citizen Information").setRequired(true);

        //get citizen data from CustomerInfoPage in order to get it from SurveyActivity
       // this.citizen = citizPage.getCitizen();

        //PageList p= new PageList(citizPage);
       // MultipleFixedChoicePage question0 = new MultipleFixedChoicePage(this, SurveyActivity.q[0].getContent()+"?");
        PageList p= new PageList();

        //creating pages of questions responses

                for(int i=0; i< SurveyActivity.q.length; i++ ) {
                    MultipleFixedChoicePage question = new MultipleFixedChoicePage(this, SurveyActivity.q[i].getContent()+"?");
                    for(int j=0; j< SurveyActivity.q[i].getResponses().length; j++){
                        Response[] responses = SurveyActivity.q[i].getResponses();
                        question.setChoices(responses[j].getChoice());
                }
                    p.add(question);};




        return p;
    }


}
