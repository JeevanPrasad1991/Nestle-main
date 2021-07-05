package com.cpm.Nestle.dailyEntry;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.cpm.Nestle.R;
import com.cpm.Nestle.database.NestleDb;
import com.cpm.Nestle.getterSetter.MappingJourneyPlan;
import com.cpm.Nestle.getterSetter.MenuMaster;
import com.cpm.Nestle.utilities.CommonString;

public class NewPerformance extends AppCompatActivity {

    private RecyclerView lv_performance;
    NestleDb db;
    String store_cd, visit_date;
  //  ArrayList<PerformanceSale> performance_list = new ArrayList<>();
    Toolbar toolbar;
    LinearLayout linearLayout, no_data_lay;
    private SharedPreferences preferences;
   // PerformanceAdapter performanceAdapter;
   MappingJourneyPlan jcpGetset;
    MenuMaster menuMaster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_performance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lv_performance = (RecyclerView) findViewById(R.id.lv_performance);

        linearLayout = findViewById(R.id.ll_layout);
        no_data_lay = findViewById(R.id.no_data_lay);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        visit_date = preferences.getString(CommonString.KEY_DATE, null);
        setTitle("Performance - " + visit_date);
        if (getIntent().getSerializableExtra(CommonString.TAG_OBJECT) != null && getIntent().getSerializableExtra(CommonString.KEY_MENU_ID) != null) {
            jcpGetset = (MappingJourneyPlan) getIntent().getSerializableExtra(CommonString.TAG_OBJECT);
            menuMaster = (MenuMaster) getIntent().getSerializableExtra(CommonString.KEY_MENU_ID);

        }
        db = new NestleDb(getApplicationContext());
        db.open();
       /* performance_list = db.getPerformrmanceSalesData();
        if (performance_list.size() > 0) {
            performanceAdapter = new PerformanceAdapter(getApplicationContext(), performance_list);
            lv_performance.setAdapter(performanceAdapter);
            lv_performance.setLayoutManager(new LinearLayoutManager(this));
            linearLayout.setVisibility(View.VISIBLE);
            no_data_lay.setVisibility(View.GONE);
        } else {
            linearLayout.setVisibility(View.GONE);
            no_data_lay.setVisibility(View.VISIBLE);
        }*/

    }

   /* public class PerformanceAdapter extends RecyclerView.Adapter<PerformanceAdapter.MyViewHolder> {
        private LayoutInflater inflator;
        List<PerformanceSale> data = Collections.emptyList();

        public PerformanceAdapter(Context context, List<PerformanceSale> data) {
            inflator = LayoutInflater.from(context);
            this.data = data;
        }

        @Override
        public PerformanceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            View view = inflator.inflate(R.layout.performance_list, parent, false);
            PerformanceAdapter.MyViewHolder holder = new PerformanceAdapter.MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final PerformanceAdapter.MyViewHolder viewHolder, final int position) {

            final PerformanceSale current = data.get(position);

            viewHolder.tvroute.setText(String.valueOf(current.getMonth()));
            viewHolder.tvpss.setText(String.valueOf(current.getTarget()));
            viewHolder.tvin.setText(String.valueOf(current.getAchv()));
            viewHolder.tvout.setText(String.valueOf(current.getAchvPer()));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvroute, tvpss, tvin, tvout;

            public MyViewHolder(View itemView) {
                super(itemView);
                tvroute = (TextView) itemView.findViewById(R.id.tvroute);
                tvpss = (TextView) itemView.findViewById(R.id.tvpss);
                tvin = (TextView) itemView.findViewById(R.id.tvin);
                tvout = (TextView) itemView.findViewById(R.id.tvout);
            }
        }
    }*/


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
