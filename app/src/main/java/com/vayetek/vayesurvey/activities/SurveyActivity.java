package com.vayetek.vayesurvey.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.vayetek.vayesurvey.activities.pages.CustomerInfoFragment;
import com.vayetek.vayesurvey.activities.pages.CustomerInfoPage;
import com.vayetek.vayesurvey.models.Citizen;
import com.vayetek.vayesurvey.models.FilledSurvey;
import com.vayetek.vayesurvey.models.Question;
import com.vayetek.vayesurvey.models.Survey;
import com.vayetek.vayesurvey.utils.wizardpager.model.AbstractWizardModel;
import com.vayetek.vayesurvey.utils.wizardpager.model.ModelCallbacks;
import com.vayetek.vayesurvey.utils.wizardpager.model.MultipleFixedChoicePage;
import com.vayetek.vayesurvey.utils.wizardpager.model.Page;
import com.vayetek.vayesurvey.utils.wizardpager.ui.PageFragmentCallbacks;
import com.vayetek.vayesurvey.utils.wizardpager.ui.ReviewFragment;
import com.vayetek.vayesurvey.utils.wizardpager.ui.StepPagerStrip;
import com.vayetek.vayesurvey.fragments.AlertSubmitSurveyDFragment;

import com.vayetek.vayesurvey.R;

import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SurveyActivity extends ActionBarActivity implements
        PageFragmentCallbacks, ReviewFragment.Callbacks, ModelCallbacks {
    Survey survey;
    Citizen citizen;
    HashMap<String,String> tempQuests;
    private ViewPager mPager;
    private MyPagerAdapter mPagerAdapter;

    private boolean mEditingAfterReview;

    private QuestionWizardModel mWizardModel;

    private boolean mConsumePageSelectedEvent;

    private Button mNextButton;
    private Button mPrevButton;

    private List<Page> mCurrentPageSequence;
    private StepPagerStrip mStepPagerStrip;

    public static Question[] q;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        citizen = new Citizen();
        tempQuests = new HashMap<>();

        Bundle extras = getIntent().getExtras();
        survey = extras.getParcelable("survey");
        Log.d("ssssssurveyy ",survey.getTitle());
        q =survey.getQuestions();
        Log.d("recovering the intent", q[0].getContent());
        mWizardModel = new QuestionWizardModel(this);
        mWizardModel.setQuestions(q);

        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);

        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }

        mWizardModel.registerListener(this);

        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);
        mStepPagerStrip = (StepPagerStrip) findViewById(R.id.strip);
        mStepPagerStrip
                .setOnPageSelectedListener(new StepPagerStrip.OnPageSelectedListener() {
                    @Override
                    public void onPageStripSelected(int position) {
                        position = Math.min(mPagerAdapter.getCount() - 1,
                                position);
                        if (mPager.getCurrentItem() != position) {
                            mPager.setCurrentItem(position);
                        }
                    }
                });

        mNextButton = (Button) findViewById(R.id.next_button);
        mPrevButton = (Button) findViewById(R.id.prev_button);

        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mStepPagerStrip.setCurrentPage(position);

                if (mConsumePageSelectedEvent) {
                    mConsumePageSelectedEvent = false;
                    return;
                }

                mEditingAfterReview = false;
                updateBottomBar();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPager.getCurrentItem() == mCurrentPageSequence.size()) {


                    //in order to save citizen
                    //getting the citizenPage
                    CustomerInfoPage cf = (CustomerInfoPage)mCurrentPageSequence.get(0);

                    citizen = cf.getCitizen();

                    //just for logging
                    Gson json = new Gson();
                    String citizenJson = json.toJson(citizen);
                    Log.d("citizen from surveyAct",citizenJson);
                    //just for logging

                    //in order to save questions responses
                    MultipleFixedChoicePage Qf = (MultipleFixedChoicePage)mCurrentPageSequence.get(mCurrentPageSequence.size()-1);
                    tempQuests = Qf.getTempQuests();

                    //just for logging
                    String questionsJson = json.toJson(tempQuests);
                    Log.d("questions surveyAct",questionsJson);
                    //just for logging



                    AlertSubmitSurveyDFragment alertdFragment = new AlertSubmitSurveyDFragment();
                    alertdFragment.setSurvey(survey);
                    alertdFragment.setCitizen(citizen);
                    alertdFragment.setTempQuests(tempQuests);
                    // Show Alert DialogFragment
                    alertdFragment.show(getSupportFragmentManager(), "");

                } else {
                    if (mEditingAfterReview) {
                        mPager.setCurrentItem(mPagerAdapter.getCount() - 1);
                    } else {
                        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                    }
                }
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            }
        });

        onPageTreeChanged();
        updateBottomBar();
    }

    @Override
    public void onPageTreeChanged() {
        mCurrentPageSequence = mWizardModel.getCurrentPageSequence();
        recalculateCutOffPage();
        mStepPagerStrip.setPageCount(mCurrentPageSequence.size() + 1); // + 1 =
        // review
        // step
        mPagerAdapter.notifyDataSetChanged();
        updateBottomBar();
    }

    private void updateBottomBar() {
        int position = mPager.getCurrentItem();
        if (position == mCurrentPageSequence.size()) {
            mNextButton.setText(R.string.finish);
            mNextButton.setBackgroundResource(R.drawable.finish_background);
            mNextButton.setTextAppearance(this, R.style.TextAppearanceFinish);
        } else {
            mNextButton.setText(mEditingAfterReview ? R.string.review
                    : R.string.next);
            mNextButton
                    .setBackgroundResource(R.drawable.selectable_item_background);
            TypedValue v = new TypedValue();
            getTheme().resolveAttribute(android.R.attr.textAppearanceMedium, v,
                    true);
            mNextButton.setTextAppearance(this, v.resourceId);
            mNextButton.setEnabled(position != mPagerAdapter.getCutOffPage());
        }

        mPrevButton
                .setVisibility(position <= 0 ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWizardModel.unregisterListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("model", mWizardModel.save());
    }

    @Override
    public AbstractWizardModel onGetModel() {
        return mWizardModel;
    }

    @Override
    public void onEditScreenAfterReview(String key) {
        for (int i = mCurrentPageSequence.size() - 1; i >= 0; i--) {
            if (mCurrentPageSequence.get(i).getKey().equals(key)) {
                mConsumePageSelectedEvent = true;
                mEditingAfterReview = true;
                mPager.setCurrentItem(i);
                updateBottomBar();
                break;
            }
        }
    }

    @Override
    public void onPageDataChanged(Page page) {
        if (page.isRequired()) {
            if (recalculateCutOffPage()) {
                mPagerAdapter.notifyDataSetChanged();
                updateBottomBar();
            }
        }
    }

    @Override
    public Page onGetPage(String key) {
        return mWizardModel.findByKey(key);
    }

    private boolean recalculateCutOffPage() {
        // Cut off the pager adapter at first required page that isn't completed
        int cutOffPage = mCurrentPageSequence.size() + 1;
        for (int i = 0; i < mCurrentPageSequence.size(); i++) {
            Page page = mCurrentPageSequence.get(i);
            if (page.isRequired() && !page.isCompleted()) {
                cutOffPage = i;
                break;
            }
        }

        if (mPagerAdapter.getCutOffPage() != cutOffPage) {
            mPagerAdapter.setCutOffPage(cutOffPage);
            return true;
        }

        return false;
    }

    public class MyPagerAdapter extends FragmentStatePagerAdapter {
        private int mCutOffPage;
        private Fragment mPrimaryItem;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if (i >= mCurrentPageSequence.size()) {
                return new ReviewFragment();
            }


            return mCurrentPageSequence.get(i).createFragment();
        }

        @Override
        public int getItemPosition(Object object) {
            // TODO: be smarter about this
            if (object == mPrimaryItem) {
                // Re-use the current fragment (its position never changes)
                return POSITION_UNCHANGED;
            }

            return POSITION_NONE;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position,
                                   Object object) {
            super.setPrimaryItem(container, position, object);
            mPrimaryItem = (Fragment) object;
        }

        @Override
        public int getCount() {
            return Math.min(mCutOffPage + 1, mCurrentPageSequence == null ? 1
                    : mCurrentPageSequence.size() + 1);
        }

        public void setCutOffPage(int cutOffPage) {
            if (cutOffPage < 0) {
                cutOffPage = Integer.MAX_VALUE;
            }
            mCutOffPage = cutOffPage;
        }

        public int getCutOffPage() {
            return mCutOffPage;
        }
    }
}
