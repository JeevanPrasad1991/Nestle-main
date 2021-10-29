package com.cpm.Nestle.dailyEntry;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cpm.Nestle.R;
import com.cpm.Nestle.database.NestleDb;
import com.cpm.Nestle.getterSetter.MappingJourneyPlan;
import com.cpm.Nestle.getterSetter.MenuMaster;
import com.cpm.Nestle.getterSetter.StoreGrading;
import com.cpm.Nestle.utilities.CommonString;

import java.util.ArrayList;
import java.util.List;

public class ReportStoreGradingActivity extends AppCompatActivity {
    public ArrayList<StoreGrading> reportsList = new ArrayList<>();
    MappingJourneyPlan jcpGetset;
    MenuMaster menuMaster;
    String visit_date, username;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor = null;
    RecyclerView recycle_reports;
    NestleDb db;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_grading);
        context = this;
        db = new NestleDb(context);
        db.open();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextAppearance(context, R.style.changestext_sizefor_mobile);
        if (getIntent().getSerializableExtra(CommonString.TAG_OBJECT) != null && getIntent().getSerializableExtra(CommonString.KEY_MENU_ID) != null) {
            jcpGetset = (MappingJourneyPlan) getIntent().getSerializableExtra(CommonString.TAG_OBJECT);
            menuMaster = (MenuMaster) getIntent().getSerializableExtra(CommonString.KEY_MENU_ID);

        }

        iduserinterface();

    }


    private void iduserinterface() {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        username = preferences.getString(CommonString.KEY_USERNAME, "");
        visit_date = preferences.getString(CommonString.KEY_DATE, "");
        recycle_reports = (RecyclerView) findViewById(R.id.recycle_reports);
        setTitle(menuMaster.getMenuName() + " - " + visit_date);
        db = new NestleDb(context);
        db.open();
        calculatedatafor_Value();
    }


    private void calculatedatafor_Value() {
        db.open();
        reportsList = db.getstoreGrading(jcpGetset);
        try {
            if (reportsList.size() > 0) {
                recycle_reports.setAdapter(new ReportsAdapter(context, reportsList));
                recycle_reports.setLayoutManager(new LinearLayoutManager(context));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.MyViewHolder> {
        private LayoutInflater inflator;
        List<StoreGrading> data;


        public ReportsAdapter(Context context, List<StoreGrading> data) {
            inflator = LayoutInflater.from(context);
            this.data = data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            View view = inflator.inflate(R.layout.row_reports_list, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final StoreGrading current = data.get(position);

            holder.txt_period.setText("" + current.getPeriod());
            holder.txt_period.setId(position);

            holder.txt_programs.setText("" + current.getProgramName());
            holder.txt_programs.setId(position);

            holder.txt_grade.setText("" + current.getGrade());
            holder.txt_grade.setId(position);

            if (current.getGrade() != null && current.getGrade().equalsIgnoreCase("B")) {
                holder.card_grade.setCardBackgroundColor(getResources().getColor(R.color.text_yellow));
                holder.card_grade.setId(position);
            } else if (current.getGrade() != null && current.getGrade().equalsIgnoreCase("A")) {
                holder.card_grade.setCardBackgroundColor(getResources().getColor(R.color.green));
                holder.card_grade.setId(position);
            } else {
                holder.card_grade.setCardBackgroundColor(getResources().getColor(R.color.red));
                holder.card_grade.setId(position);
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView txt_period, txt_programs, txt_grade;
            CardView card_grade;

            public MyViewHolder(View itemView) {
                super(itemView);
                card_grade = (CardView) itemView.findViewById(R.id.card_grade);
                txt_period = (TextView) itemView.findViewById(R.id.txt_period);
                txt_programs = (TextView) itemView.findViewById(R.id.txt_programs);
                txt_grade = (TextView) itemView.findViewById(R.id.txt_grade);

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
            finish();

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
        finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
