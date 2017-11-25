package com.vayetek.vayesurvey.activities.pages;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vayetek.vayesurvey.R;
import com.vayetek.vayesurvey.activities.BaseActivity;
import com.vayetek.vayesurvey.activities.Config;
import com.vayetek.vayesurvey.activities.LoginActivity;
import com.vayetek.vayesurvey.models.Citizen;
import com.vayetek.vayesurvey.utils.wizardpager.ui.PageFragmentCallbacks;


public class CustomerInfoFragment extends Fragment {
    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private CustomerInfoPage mPage;
    private RadioGroup radioGroup;
    private RadioButton mSexView;
    private TextView mAgeView;
    Spinner mSocLevView;
    Spinner mEducLevView;
    private TextView mProfessionView;
    private Spinner mRegionView;
    private TextView mLocalityView;
    /* private static Citizen citizen;

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }
*/
    public static CustomerInfoFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        CustomerInfoFragment fragment = new CustomerInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CustomerInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //citizen = new Citizen();

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (CustomerInfoPage) mCallbacks.onGetPage(mKey);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page_citizen_info, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());

        radioGroup = (RadioGroup)rootView.findViewById(R.id.radioGroup1);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        mSexView = (RadioButton) rootView.findViewById(selectedId);
        //mSexView.setText(mPage.getData().getString(CustomerInfoPage.SEX_DATA_KEY));


        mAgeView = ((TextView) rootView.findViewById(R.id.et_age));
        mAgeView.setText(mPage.getData().getString(CustomerInfoPage.AGE_DATA_KEY));


        mProfessionView = ((TextView) rootView.findViewById(R.id.et_profession));
        mProfessionView.setText(mPage.getData().getString(CustomerInfoPage.PROFESSION_DATA_KEY));



                //educ_level spiner init
        mRegionView = (Spinner) rootView.findViewById(R.id.region);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this.getContext(),R.array.region_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        mRegionView.setAdapter(adapter2);


        mLocalityView = ((TextView) rootView.findViewById(R.id.et_locality));
        mLocalityView.setText(mPage.getData().getString(CustomerInfoPage.LOCALITY_DATA_KEY));


        mSocLevView = (Spinner) rootView.findViewById(R.id.soc_level);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),R.array.soc_level_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        mSocLevView.setAdapter(adapter);

        //educ_level spiner init
         mEducLevView = (Spinner) rootView.findViewById(R.id.educ_level);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this.getContext(),R.array.educ_level_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        mEducLevView.setAdapter(adapter1);


        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof PageFragmentCallbacks)) {
            throw new ClassCastException("Activity must implement PageFragmentCallbacks");
        }

        mCallbacks = (PageFragmentCallbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }




    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //sex attribute setting data

        mPage.getData().putString(CustomerInfoPage.SEX_DATA_KEY,
                (mSexView.getText() != null) ? mSexView.getText().toString() : null);
       // citizen.setSex(mSexView.getText().toString());
        mPage.notifyDataChanged();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId==R.id.male){

                    mPage.getData().putString(CustomerInfoPage.SEX_DATA_KEY,
                            (mSexView.getText() != null) ? "Male" : null);
               // citizen.setSex("Male");
                    mPage.notifyDataChanged();
                }
                else if(checkedId==R.id.female){
                    mPage.getData().putString(CustomerInfoPage.SEX_DATA_KEY,
                            (mSexView.getText() != null) ? "Female" : null);
                    //citizen.setSex("Female");
                    mPage.notifyDataChanged();
                }
            }
        });

        //age attribute setting data

        mAgeView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1,
                                          int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPage.getData().putString(CustomerInfoPage.AGE_DATA_KEY,
                        (editable != null) ? editable.toString() : null);
                //citizen.setSex(editable.toString());
                mPage.notifyDataChanged();

            }
        });

        //soclev attribute setting data

        mSocLevView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPage.getData().putString(CustomerInfoPage.SOC_LEV_DATA_KEY,
                        (parent.getItemAtPosition(position) != null) ? parent.getItemAtPosition(position).toString() : null);
                //citizen.setSocLevel(parent.getItemAtPosition(position).toString());
                mPage.notifyDataChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });

        //educlev attribute setting data

        mEducLevView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPage.getData().putString(CustomerInfoPage.EDUC_LEV_DATA_KEY,
                        (parent.getItemAtPosition(position) != null) ? parent.getItemAtPosition(position).toString() : null);
                //citizen.setEducLevel(parent.getItemAtPosition(position).toString());
                mPage.notifyDataChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });


        //profession attribute setting data

       mProfessionView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1,
                                          int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPage.getData().putString(CustomerInfoPage.PROFESSION_DATA_KEY,
                        (editable != null) ? editable.toString() : null);
                //citizen.setProfession(editable.toString());
                mPage.notifyDataChanged();
            }
        });

        //region attribute setting data



        mRegionView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPage.getData().putString(CustomerInfoPage.REGION_DATA_KEY,
                        (parent.getItemAtPosition(position) != null) ? parent.getItemAtPosition(position).toString() : null);
                //citizen.setEducLevel(parent.getItemAtPosition(position).toString());
                mPage.notifyDataChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });


        //locality attribute setting data

        mLocalityView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1,
                                          int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPage.getData().putString(CustomerInfoPage.LOCALITY_DATA_KEY,
                        (editable != null) ? editable.toString() : null);
                //citizen.setLocality(editable.toString());
                mPage.notifyDataChanged();
            }
        });

    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        // In a future update to the support library, this should override setUserVisibleHint
        // instead of setMenuVisibility.
        if (mAgeView != null && mProfessionView != null  && mRegionView != null && mLocalityView != null ) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            if (!menuVisible) {
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }
        }
    }
}
