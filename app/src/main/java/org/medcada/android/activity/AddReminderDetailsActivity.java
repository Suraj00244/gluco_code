package org.medcada.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import org.medcada.android.R;
import org.medcada.android.db.DatabaseHandler;
import org.medcada.android.db.Reminder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddReminderDetailsActivity extends AppCompatActivity {

    @BindView(R.id.activity_main_toolbar)
    Toolbar activityMainToolbar;
    @BindView(R.id.et_reminderName)
    EditText etReminderName;
    @BindView(R.id.imageView)
    ImageView imageView;
//    @BindView(R.id.sp_doctor)
//    Spinner spDoctor;


    @BindView(R.id.et_reminder)
    TextView etReminder;
    @BindView(R.id.tv_reminder)
    TextView tvReminder;
    @BindView(R.id.et_location)
    EditText etLocation;
    @BindView(R.id.et_note)
    EditText etNote;
    @BindView(R.id.cont_doc_sp)
    LinearLayout contDocSp;
    @BindView(R.id.cont_locaction)
    LinearLayout contLocaction;
    @BindView(R.id.tv_doctor)
    TextView tvDoctor;

    Reminder reminder;
    Date alarmTime;
    String doctor;
    String label;

    boolean shouldSeectDr = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder_details);
        ButterKnife.bind(this);
        reminder = new Reminder();
        label = getIntent().getStringExtra("label");
        if (label.equals("General")) {
            contDocSp.setVisibility(View.GONE);
            shouldSeectDr = false;
        } else if (label.equals("Birthday")) {
            contDocSp.setVisibility(View.GONE);
            contLocaction.setVisibility(View.GONE);
            shouldSeectDr = false;
        }
        setSupportActionBar(activityMainToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Add " + label);

        etReminderName.setHint("Add " + label);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_converter_a1c, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu_save:
                saveReminder();
                break;
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private DatabaseHandler db;

    private void saveReminder() {
        db = new DatabaseHandler(this);
        String title = etReminderName.getText().toString();
        String location = etLocation.getText().toString();
        String note = etNote.getText().toString();
        if (title.isEmpty()) {
            Toast.makeText(this, "Title can not be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (alarmTime == null) {
            Toast.makeText(this, "Please select alarm time", Toast.LENGTH_SHORT).show();
            return;
        }
        if (shouldSeectDr) {
            if (doctor == null ? true : doctor.isEmpty() ? true : false) {
                Toast.makeText(this, "Please select a Doctor", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        reminder.setId(alarmTime.getTime());
        reminder.setOneTime(false);
        reminder.setActive(true);
        reminder.setMetric("glucose");
        reminder.setAlarmTime(alarmTime);
        reminder.setLabel(label);
        reminder.setLocation(location);
        reminder.setNote(note);
        boolean added = db.addReminder(reminder);
        if (added) {
            Toast.makeText(this, "Reminder Added Successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "something went with with Reminder", Toast.LENGTH_SHORT).show();
        }
    }

    public static int SELECT_CONTACT = 123;

    @OnClick(R.id.tv_doctor)
    public void onViewClicked() {
        Intent locationIntent = new Intent(this, SelectContactActivity.class);
        startActivityForResult(locationIntent, SELECT_CONTACT);
    }

    @OnClick(R.id.et_reminder)
    public void onTimeClick() {
        dialog(etReminder);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {


            if (requestCode == SELECT_CONTACT) {
                String number = data.getStringExtra("number");
                String name = data.getStringExtra("name");
                String html = name + " ( " + number + " )";
                tvDoctor.setText(html);
                doctor = html;
            }

        }
    }

    public void dialog(final TextView tv) {

        final SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd hh:mm");

        new SlideDateTimePicker.Builder(getSupportFragmentManager())
                .setListener(new SlideDateTimeListener() {
                    @Override
                    public void onDateTimeSet(Date date) {
                        alarmTime = date;
                        String fDate = changeDateTimeFormate(format.format(date));
                        SimpleDateFormat newformater = new SimpleDateFormat("MMM dd, yyyy hh:mm");
                        tv.setText(newformater.format(date));
                    }
                })
                .setInitialDate(new Date())
                .build()
                .show();
    }

    public static String changeDateTimeFormate(String date) {
        String returnDate = "";
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        SimpleDateFormat newformater = new SimpleDateFormat("MMM dd, yyyy hh:mm");
        try {
            Date d = formater.parse(date);
            returnDate = newformater.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnDate;
    }
}
