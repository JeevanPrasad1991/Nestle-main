package com.cpm.Nestle.dailyEntry;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cpm.Nestle.getterSetter.MasterProgram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cpm.Nestle.R;
import com.cpm.Nestle.database.NestleDb;
import com.cpm.Nestle.getterSetter.MappingJourneyPlan;
import com.cpm.Nestle.getterSetter.MenuMaster;
import com.cpm.Nestle.utilities.CommonString;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProgramEntryMenuActivity extends AppCompatActivity {
    NestleDb db;
    Context context;
    SharedPreferences preferences;
    String username;
    RecyclerView rec_window;
    ValueAdapter adapter;
    MappingJourneyPlan journeyPlan = null;
    MenuMaster menuMaster = null;
    ArrayList<MasterProgram> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_menu);
        declaration();
    }

    @Override
    protected void onResume() {
        super.onResume();
        db.open();
        data = db.getmasterProgram(journeyPlan);
        if (data.size() > 0) {
            adapter = new ValueAdapter(context, data);
            rec_window.setAdapter(adapter);
            rec_window.setLayoutManager(new GridLayoutManager(context, 2));
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }


    public class ValueAdapter extends RecyclerView.Adapter<ValueAdapter.MyViewHolder> {
        private LayoutInflater inflator;
        List<MasterProgram> data;

        public ValueAdapter(Context context, List<MasterProgram> data) {
            inflator = LayoutInflater.from(context);
            this.data = data;
        }

        @Override
        public ValueAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            View view = inflator.inflate(R.layout.custom_menu_row, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ValueAdapter.MyViewHolder viewHolder, final int position) {
            final MasterProgram current = data.get(position);

            viewHolder.menu_name_txt.setText(current.getProgramName());
            viewHolder.menu_name_txt.setId(position);

            viewHolder.card_view.setCardBackgroundColor(Color.parseColor(current.getProgramIconBaseColor()));
            viewHolder.card_view.setId(position);
            String icon_path = current.getProgramNormalIcon();
            if (check_allfilledSubProgram(current.getSubprogramList())) {
                icon_path = current.getProgramTickIcon();
            } else {
                icon_path = current.getProgramNormalIcon();
            }

            Glide.with(context)
                    .load(Uri.fromFile(new File(CommonString.FILE_PATH_Downloaded
                            + icon_path)))
                    .apply(new RequestOptions())
                    .into(viewHolder.menu_icon);

            viewHolder.card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (current.getSubprogramList().size() > 1) {
                        startActivity(new Intent(context, SubProgramEntryMenu.class).
                                putExtra(CommonString.TAG_OBJECT, journeyPlan).putExtra(CommonString.TAG_PROGRAM_OBJECT, current));
                    } else {
                        startActivity(new Intent(context, ProgramChecklistActivity.class).
                                putExtra(CommonString.TAG_OBJECT, journeyPlan).putExtra(CommonString.TAG_PROGRAM_OBJECT, current.getSubprogramList().get(0)));
                    }
                }
            });


        }

        @Override
        public int getItemCount() {
            return data.size();
        }


        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView menu_name_txt;
            ImageView menu_icon;
            CardView card_view;

            public MyViewHolder(View itemView) {
                super(itemView);
                menu_name_txt = (TextView) itemView.findViewById(R.id.list_txt);
                menu_icon = (ImageView) itemView.findViewById(R.id.list_icon);
                card_view = (CardView) itemView.findViewById(R.id.card_view);
            }
        }

    }

    private boolean check_allfilledSubProgram(ArrayList<MasterProgram> subprogramList) {
        boolean flag = true;
        if (subprogramList.size() > 0) {
            for (int k = 0; k < subprogramList.size(); k++) {
                if (db.IsSubProgramChecklistfilled(journeyPlan, subprogramList.get(k), false) == false) {
                    flag = false;
                    break;
                }
            }
        }

        return flag;
    }


    void declaration() {
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextAppearance(context, R.style.changestext_sizefor_mobile);
        db = new NestleDb(context);
        db.open();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        username = preferences.getString(CommonString.KEY_USERNAME, "");
        rec_window = (RecyclerView) findViewById(R.id.rec_menu);
        if (getIntent().getSerializableExtra(CommonString.TAG_OBJECT) != null && getIntent().getSerializableExtra(CommonString.KEY_MENU_ID) != null) {
            journeyPlan = (MappingJourneyPlan) getIntent().getSerializableExtra(CommonString.TAG_OBJECT);
            menuMaster = (MenuMaster) getIntent().getSerializableExtra(CommonString.KEY_MENU_ID);
        }

        setTitle(menuMaster.getMenuName() + " Task Menu - " + journeyPlan.getVisitDate());
    }
}
