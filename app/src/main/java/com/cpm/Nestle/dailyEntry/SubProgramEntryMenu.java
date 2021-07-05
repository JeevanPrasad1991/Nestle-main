package com.cpm.Nestle.dailyEntry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cpm.Nestle.R;
import com.cpm.Nestle.database.NestleDb;
import com.cpm.Nestle.getterSetter.MappingJourneyPlan;
import com.cpm.Nestle.getterSetter.MasterProgram;
import com.cpm.Nestle.utilities.CommonString;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SubProgramEntryMenu extends AppCompatActivity {
    NestleDb db;
    Context context;
    SharedPreferences preferences;
    String username;
    RecyclerView rec_window;
    ValueAdapter adapter;
    MappingJourneyPlan journeyPlan;
    MasterProgram current_object = new MasterProgram();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_program_entry_menu);
        declaration();
    }

    @Override
    protected void onResume() {
        super.onResume();
        db.open();
        if (current_object.getSubprogramList().size() > 0) {
            adapter = new ValueAdapter(context, current_object.getSubprogramList());
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
        //super.onBackPressed();
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
            return new ValueAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
            final MasterProgram current = data.get(position);

            viewHolder.menu_name_txt.setText(current.getSubProgramName());
            viewHolder.menu_name_txt.setId(position);

            viewHolder.card_view.setCardBackgroundColor(Color.parseColor(current.getSubProgramIconBaseColor()));
            viewHolder.card_view.setId(position);

            String icon_path = current.getSubProgramNormalIcon();
            if (db.IsSubProgramChecklistfilled(journeyPlan, current, false)) {
                icon_path = current.getSubProgramTickIcon();
            } else {
                icon_path = current.getSubProgramNormalIcon();
            }

            Glide.with(context)
                    .load(Uri.fromFile(new File(CommonString.FILE_PATH_Downloaded + icon_path)))
                    .apply(new RequestOptions())
                    .into(viewHolder.menu_icon);

            viewHolder.card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(context, ProgramChecklistActivity.class).
                            putExtra(CommonString.TAG_OBJECT, journeyPlan).putExtra(CommonString.TAG_PROGRAM_OBJECT, current));
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
        if (getIntent().getSerializableExtra(CommonString.TAG_OBJECT) != null
                && getIntent().getSerializableExtra(CommonString.TAG_PROGRAM_OBJECT) != null) {
            journeyPlan = (MappingJourneyPlan) getIntent().getSerializableExtra(CommonString.TAG_OBJECT);
            current_object = (MasterProgram) getIntent().getSerializableExtra(CommonString.TAG_PROGRAM_OBJECT);
        }

        setTitle("Program/" + current_object.getProgramName() + " Task Menu");

    }

}
