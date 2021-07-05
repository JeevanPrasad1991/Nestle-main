package com.cpm.Nestle.dailyEntry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.cpm.Nestle.R;
import com.cpm.Nestle.getterSetter.MappingJourneyPlan;
import com.cpm.Nestle.getterSetter.MenuMaster;
import com.cpm.Nestle.utilities.CommonString;

public class PerformanceActivity extends AppCompatActivity implements View.OnClickListener{
    Button attendance,Performance;
    String  visit_date;
    private SharedPreferences preferences;
    MappingJourneyPlan jcpGetset;
    MenuMaster menuMaster;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        visit_date = preferences.getString(CommonString.KEY_DATE, null);
        setTitle("Performance List - " + visit_date);
        if (getIntent().getSerializableExtra(CommonString.TAG_OBJECT) != null && getIntent().getSerializableExtra(CommonString.KEY_MENU_ID) != null) {
            jcpGetset = (MappingJourneyPlan) getIntent().getSerializableExtra(CommonString.TAG_OBJECT);
            menuMaster = (MenuMaster) getIntent().getSerializableExtra(CommonString.KEY_MENU_ID);

        }
        attendance=(Button)findViewById(R.id.attendance);
        Performance=(Button)findViewById(R.id.Performance);
        attendance.setOnClickListener(this);
        Performance.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.attendance:

                Intent startDownload = new Intent(this, AttendanceActivity.class);
                startActivity(startDownload);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                break;
            case R.id.Performance:
                Intent perf = new Intent(this, NewPerformance.class);
                startActivity(perf);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                break;
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {

            // NavUtils.navigateUpFromSameTask(this);
            finish();

            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);

        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
