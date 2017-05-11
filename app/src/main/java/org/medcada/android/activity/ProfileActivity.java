package org.medcada.android.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import org.medcada.android.R;
import org.medcada.android.presenter.HelloPresenter;
import org.medcada.android.tools.AnimationTools;
import org.medcada.android.tools.LabelledSpinner;

public class ProfileActivity extends AppCompatActivity {
    static final int CUSTOM_TYPE_SPINNER_VALUE = 11;
    private LabelledSpinner bloodTypeSpinner;
    private LabelledSpinner countrySpinner;
    private HelloPresenter presenter;
    private FloatingActionButton doneFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(2);
            setTitle("Add Profile Details");
        }
        bloodTypeSpinner = (LabelledSpinner) findViewById(R.id.activity_hello_spinner_blood);
        countrySpinner = (LabelledSpinner) findViewById(R.id.activity_hello_spinner_country);
        bloodTypeSpinner.setItemsArray(R.array.profile_add_bloodtype_list);
        countrySpinner.setItemsArray(R.array.profile_add_country_list);
        doneFAB = (FloatingActionButton) findViewById(R.id.done_fab);
        doneFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialogOnAddButtonPressed();
            }
        });

    }
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
//                Intent homeIntent = new Intent(this, HomeActivity.class);
//                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(homeIntent);
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
