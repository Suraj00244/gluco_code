package org.medcada.android.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.medcada.android.R;
import org.medcada.android.tools.LabelledSpinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddMedicationActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    SwitchCompat switchCompat;
    private FloatingActionButton doneFAB;

    private EditText reminderTextView;
    private EditText quantityText;
    private EditText doseText;
    private LabelledSpinner formSpinner,shapeSpinner,catSpinner, intervalSpinner ;
    Calendar cal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
        switchCompat = (SwitchCompat) findViewById(R.id.compatSwitch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
         quantityText = (EditText) findViewById(R.id.add_quantity);
         doseText = (EditText) findViewById(R.id.add_dose);
        formSpinner = (LabelledSpinner) findViewById(R.id.glucose_add_form);
        shapeSpinner = (LabelledSpinner) findViewById(R.id.glucose_add_shape);
        catSpinner = (LabelledSpinner) findViewById(R.id.glucose_add_schedule);
        intervalSpinner = (LabelledSpinner) findViewById(R.id.glucose_add_interval);
        reminderTextView = (EditText) findViewById(R.id.glucose_Time);
        reminderTextView.setText("09:00 AM");
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(2);
            setTitle("Add Medication");
        }

        formSpinner.setItemsArray(R.array.Medicaton_Form);
        shapeSpinner.setItemsArray(R.array.Medicaton_Shape);
        catSpinner.setItemsArray(R.array.Medicaton_Schedule);
        intervalSpinner.setItemsArray(R.array.Medicaton_Interval);
        cal = Calendar.getInstance();
        doneFAB = (FloatingActionButton) findViewById(R.id.done_fab);
        doneFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialogOnAddButtonPressed();
            }
        });
        reminderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        AddMedicationActivity.this,
                        cal.get(Calendar.HOUR_OF_DAY),
                        cal.get(Calendar.MINUTE),
                        false);
                tpd.show(getFragmentManager(), "Timepickerdialog");
            }
        });
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        SimpleDateFormat mSDF = new SimpleDateFormat("hh:mm a");
        String time = mSDF.format(cal.getTime());
        reminderTextView.setText(time);

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
