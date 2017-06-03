package org.medcada.android.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.medcada.android.R;
import org.medcada.android.db.DatabaseHandler;
import org.medcada.android.db.MedicationBean;
import org.medcada.android.tools.LabelledSpinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMedicationActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    @BindView(R.id.activity_main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.done_fab)
    FloatingActionButton doneFab;
    @BindView(R.id.add_medicationName)
    EditText addMedicationName;
    @BindView(R.id.glucose_add_form)
    LabelledSpinner glucoseAddForm;
    @BindView(R.id.glucose_add_shape)
    LabelledSpinner glucoseAddShape;
    @BindView(R.id.add_quantity)
    EditText addQuantity;
    @BindView(R.id.add_dose)
    EditText addDose;

    @BindView(R.id.compatSwitch)
    SwitchCompat switchCompat;

    @BindView(R.id.glucose_add_interval)
    LabelledSpinner glucoseAddInterval;
    @BindView(R.id.glucose_add_schedule)
    LabelledSpinner glucoseAddSchedule;

    @BindView(R.id.glucose_Time)
    EditText glucoseTime;

    Calendar cal;
    boolean isForUpdate;
    long pid = 0;
    @BindView(R.id.frequrcy_container)
    LinearLayout frequrcyContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
        ButterKnife.bind(this);


        glucoseTime.setText("09:00 AM");
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(2);
            setTitle("Add Medication");
        }


        glucoseAddForm.setItemsArray(R.array.Medicaton_Form);
        glucoseAddShape.setItemsArray(R.array.Medicaton_Shape);
        glucoseAddSchedule.setItemsArray(R.array.Medicaton_Schedule);
        glucoseAddInterval.setItemsArray(R.array.Medicaton_Interval);
        cal = Calendar.getInstance();
//        doneFAB = (FloatingActionButton) findViewById(R.id.done_fab);
//        doneFAB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //dialogOnAddButtonPressed();
//            }
//        });
        glucoseTime.setOnClickListener(new View.OnClickListener() {
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
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    frequrcyContainer.setVisibility(View.GONE);
                }else{
                    frequrcyContainer.setVisibility(View.VISIBLE);
                }
            }
        });

        isForUpdate = getIntent().getBooleanExtra("isForUpdate", false);
        if (isForUpdate) {
            MedicationBean bean = new DatabaseHandler(this).getMedicationBean(getIntent().getLongExtra("id", 0l));
            pid = bean.getId();
            getSavedData(bean);
        }



    }


    private void saveMedicationData() {
        String name = addMedicationName.getText().toString();
        String time = glucoseTime.getText().toString();
        String form = glucoseAddForm.getSpinner().getSelectedItem().toString();
        String shape = glucoseAddShape.getSpinner().getSelectedItem().toString();
        String qty = addQuantity.getText().toString();
        String dose = addDose.getText().toString();
        boolean isNeeded = switchCompat.isChecked();
        String interval = glucoseAddInterval.getSpinner().getSelectedItem().toString();
        String repeat = glucoseAddSchedule.getSpinner().getSelectedItem().toString();
//        MedicationBean medicationBean = new MedicationBean(name,form,shape,time,Repeat,
// Integer.parseInt(qty),Integer.parseInt(dose)
//        ,Integer.parseInt(interval),isNeeded);


        MedicationBean medicationBean = new MedicationBean();
        medicationBean.setMedicationName(name);
        medicationBean.setAsNeeded(isNeeded);
        medicationBean.setDose(Integer.parseInt(dose));
        medicationBean.setForm(form);
        medicationBean.setShape(shape);
        medicationBean.setRepeat(repeat);
        medicationBean.setReminderTime(time);
        medicationBean.setQty(Integer.parseInt(qty));
        medicationBean.setInterval(Integer.parseInt(interval));
        if (isForUpdate) {
            medicationBean.setId(pid);
            new DatabaseHandler(this).updateMedicationData(medicationBean);

        } else {
            new DatabaseHandler(this).addMedicationData(medicationBean);

        }
        finish();

    }


    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        SimpleDateFormat mSDF = new SimpleDateFormat("hh:mm a");
        String time = mSDF.format(cal.getTime());
        glucoseTime.setText(time);

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

    public void getSavedData(MedicationBean bean) {
        addMedicationName.setText(bean.getMedicationName());
        glucoseTime.setText(bean.getReminderTime());
//        glucoseAddForm.getSpinner().getSelectedItem().toString();
//        glucoseAddShape.getSpinner().getSelectedItem().toString();
        addQuantity.setText(bean.getQty() + "");
        addDose.setText(bean.getDose() + "");
        switchCompat.setChecked(bean.isAsNeeded());


        String[] med_form = getResources().getStringArray(R.array.Medicaton_Form);
        for (int i = 0; i < med_form.length; i++) {
            if (med_form[i].equals(bean.getForm())) {
                glucoseAddForm.getSpinner().setSelection(i);
                break;
            }
        }
        String[] med_shape = getResources().getStringArray(R.array.Medicaton_Shape);
        for (int i = 0; i < med_shape.length; i++) {
            if (med_shape[i].equals(bean.getShape())) {
                glucoseAddShape.getSpinner().setSelection(i);
                break;
            }
        }
        String[] med_int = getResources().getStringArray(R.array.Medicaton_Interval);

        for (int i = 0; i < med_int.length; i++) {
            if (med_int[i].equals(String.valueOf(bean.getInterval()))) {
                glucoseAddInterval.getSpinner().setSelection(i);
                break;
            }
        }

        String[] med_sch = getResources().getStringArray(R.array.Medicaton_Schedule);

        for (int i = 0; i < med_sch.length; i++) {
            if (med_sch[i].equals(bean.getRepeat())) {
                glucoseAddSchedule.getSpinner().setSelection(i);
                break;
            }
        }

//        glucoseAddInterval.getSpinner().get
//        glucoseAddInterval.getSpinner().getSelectedItem().toString();
//        glucoseAddSchedule.getSpinner().getSelectedItem().toString();
    }

    @OnClick(R.id.done_fab)
    public void onViewClicked() {
        saveMedicationData();
    }
}
