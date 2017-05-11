package org.medcada.android.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import org.medcada.android.R;
import org.medcada.android.presenter.RemindersPresenter;

public class MedicationActivity extends AppCompatActivity {
    private FloatingActionButton addFab;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(2);
            getSupportActionBar().setTitle("Medication");
            addFab = (FloatingActionButton) findViewById(R.id.activity_reminders_fab_add);

            addFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    startAddMedicationActivity();
                }
            });
        }

    }
    private void startAddMedicationActivity() {
        Intent intent = new Intent(this, AddMedicationActivity.class);
        startActivity(intent);
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
