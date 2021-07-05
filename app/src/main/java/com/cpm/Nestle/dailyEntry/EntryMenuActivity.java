package com.cpm.Nestle.dailyEntry;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

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

/*import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;*/
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cpm.Nestle.R;
import com.cpm.Nestle.database.NestleDb;
import com.cpm.Nestle.getterSetter.AssetMaster;
import com.cpm.Nestle.getterSetter.AuditDataGetterSetter;
import com.cpm.Nestle.getterSetter.AvailabilityGetterSetter;
import com.cpm.Nestle.getterSetter.CategoryMaster;
import com.cpm.Nestle.getterSetter.CommonChillerDataGetterSetter;
import com.cpm.Nestle.getterSetter.MappingAssetChecklist;
import com.cpm.Nestle.getterSetter.MappingJourneyPlan;
import com.cpm.Nestle.getterSetter.MasterFeedbackQuestion;
import com.cpm.Nestle.getterSetter.MasterProgram;
import com.cpm.Nestle.getterSetter.MenuGetterSetter;
import com.cpm.Nestle.getterSetter.MenuMaster;
import com.cpm.Nestle.getterSetter.WindowMaster;
import com.cpm.Nestle.utilities.CommonString;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EntryMenuActivity extends AppCompatActivity {
    NestleDb db;
    Context context;
    MappingJourneyPlan journeyPlan;
    String visit_date = "", designation = "";
    List<MenuGetterSetter> data = new ArrayList<>();
    RecyclerView recyclerView;
    ValueAdapter adapter;
    SharedPreferences preferences;
    List<MenuMaster> menu_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_menu);
        context = this;
        declaration();

        if (getIntent().getSerializableExtra(CommonString.TAG_OBJECT) != null) {
            journeyPlan = (MappingJourneyPlan) getIntent().getSerializableExtra(CommonString.TAG_OBJECT);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        db.open();
        menu_list = db.getMenuData(journeyPlan, designation);
        adapter = new ValueAdapter(context, menu_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        if (chekDataforCheckout(journeyPlan)) {
            Runnable progressRunnable = new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            };
            Handler pdCanceller = new Handler();
            pdCanceller.postDelayed(progressRunnable, 15000);
        }
    }


    public class ValueAdapter extends RecyclerView.Adapter<ValueAdapter.MyViewHolder> {
        private LayoutInflater inflator;
        List<MenuMaster> data;

        public ValueAdapter(Context context, List<MenuMaster> data) {
            inflator = LayoutInflater.from(context);
            this.data = data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            View view = inflator.inflate(R.layout.custom_menu_row, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
            final MenuMaster current = data.get(position);

            viewHolder.menu_name_txt.setText(current.getMenuName());
            viewHolder.menu_name_txt.setId(position);

            viewHolder.card_view.setCardBackgroundColor(Color.parseColor(current.getBGColor()));
            viewHolder.card_view.setId(position);

            String icon_path = current.getNormalIcon();
            switch (current.getMenuId()) {
                case 1:
                    db.open();
                    if (db.getmasterProgram(journeyPlan).size() > 0) {
                        if (check_allfilledSubProgram()) {
                            icon_path = current.getTickIcon();
                        } else {
                            icon_path = current.getNormalIcon();
                        }
                    } else {
                        icon_path = current.getGreyIcon();
                    }

                    break;

                case 2:
                    db.open();
                    if (db.getasset(journeyPlan).size() > 0) {
                        db.open();
                        if (db.getinsertedpaidVisibility(journeyPlan).size() > 0) {
                            icon_path = current.getTickIcon();
                        } else {
                            icon_path = current.getNormalIcon();
                        }
                    } else {
                        icon_path = current.getGreyIcon();
                    }

                    break;


                case 3:
                    db.open();
                    boolean flag = true;
                    if (db.VisicoolerCheckMapping(journeyPlan) && db.getmasterChecklist(journeyPlan, null, flag).size() > 0) {
                        db.open();
                        if (db.IsSubProgramChecklistfilled(journeyPlan, null, flag)) {
                            icon_path = current.getTickIcon();
                        } else {
                            icon_path = current.getNormalIcon();
                        }
                    } else {
                        icon_path = current.getGreyIcon();
                    }


                    break;


                case 4:
                    db.open();
                    if (db.getPOSMDeploymentData(journeyPlan).size() > 0) {
                        db.open();
                        if (db.IsPosmfilled(journeyPlan)) {
                            icon_path = current.getTickIcon();
                        } else {
                            icon_path = current.getNormalIcon();
                        }

                    } else {
                        icon_path = current.getGreyIcon();
                    }
                    break;

                case 5:
                    db.open();
                    if (db.getPromotionData(journeyPlan).size() > 0) {
                        db.open();
                        if (db.getinsertedpromotions(journeyPlan).size() > 0) {
                            icon_path = current.getTickIcon();
                        } else {
                            icon_path = current.getNormalIcon();
                        }

                    } else {
                        icon_path = current.getGreyIcon();
                    }

                    break;


                case 6:
                    flag = true;
                    db.open();
                    if (db.getcompanies().size() > 0) {
                        db.open();
                        if (db.getinsertedcomp_promotion(journeyPlan, flag).size() > 0) {
                            icon_path = current.getTickIcon();
                        } else {
                            icon_path = current.getNormalIcon();
                        }

                    } else {
                        icon_path = current.getGreyIcon();
                    }

                    break;

                case 7:
                    flag = false;
                    String cat = "";
                    db.open();
                    if (db.getcategories(flag, cat).size() > 0) {
                        db.open();
                        if (db.getinsertedcomp_promotion(journeyPlan, flag).size() > 0) {
                            icon_path = current.getTickIcon();
                        } else {
                            icon_path = current.getNormalIcon();
                        }

                    } else {
                        icon_path = current.getGreyIcon();
                    }

                    break;

                case 8:
                    db.open();
                    flag = false;
                    cat = "";
                    if (db.getcategories(flag, cat).size() > 0) {
                        db.open();
                        if (db.getinsertedVQPS(journeyPlan).size() > 0) {
                            icon_path = current.getTickIcon();
                        } else {
                            icon_path = current.getNormalIcon();
                        }
                    } else {
                        icon_path = current.getGreyIcon();
                    }

                    break;


            }


            Glide.with(context)
                    .load(Uri.fromFile(new File(CommonString.FILE_PATH_Downloaded + icon_path)))
                    .apply(new RequestOptions())
                    .into(viewHolder.menu_icon);

            viewHolder.card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int menu_id = current.getMenuId();
                    switch (menu_id) {
                        case 1:
                            db.open();
                            if (db.getmasterProgram(journeyPlan).size() > 0) {
                                startActivity(new Intent(context, ProgramEntryMenuActivity.class).putExtra(CommonString.TAG_OBJECT, journeyPlan)
                                        .putExtra(CommonString.KEY_MENU_ID, current));
                                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                            }

                            break;

                        case 2:
                            db.open();
                            if (db.getasset(journeyPlan).size() > 0) {
                                startActivity(new Intent(context, PaidVisibilityActivity.class).putExtra(CommonString.TAG_OBJECT, journeyPlan)
                                        .putExtra(CommonString.KEY_MENU_ID, current));
                                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                            }

                            break;

                        case 3:
                            boolean flag = true;
                            if (db.VisicoolerCheckMapping(journeyPlan) && db.getmasterChecklist(journeyPlan, null, flag).size() > 0) {
                                startActivity(new Intent(context, VisicoolerActivity.class).putExtra(CommonString.TAG_OBJECT, journeyPlan)
                                        .putExtra(CommonString.KEY_MENU_ID, current));
                                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                            }


                            break;

                        case 4:
                            db.open();
                            if (db.getPOSMDeploymentData(journeyPlan).size() > 0) {
                                startActivity(new Intent(context, POSMDeploymentActivity.class).putExtra(CommonString.TAG_OBJECT, journeyPlan)
                                        .putExtra(CommonString.KEY_MENU_ID, current));
                                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

                            }

                            break;

                        case 5:
                            db.open();
                            if (db.getPromotionData(journeyPlan).size() > 0) {
                                startActivity(new Intent(context, PromotionActivity.class).putExtra(CommonString.TAG_OBJECT, journeyPlan)
                                        .putExtra(CommonString.KEY_MENU_ID, current));
                                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

                            }

                            break;

                        case 6:
                            db.open();
                            if (db.getcompanies().size() > 0) {
                                startActivity(new Intent(context, CompPromotionActivity.class).putExtra(CommonString.TAG_OBJECT, journeyPlan)
                                        .putExtra(CommonString.KEY_MENU_ID, current));
                                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

                            }

                            break;

                        case 7:
                            flag = false;
                            String cat = "";
                            db.open();
                            if (db.getcategories(flag, cat).size() > 0) {
                                startActivity(new Intent(context, CompNPDLaunchActivity.class).putExtra(CommonString.TAG_OBJECT, journeyPlan)
                                        .putExtra(CommonString.KEY_MENU_ID, current));
                                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

                            }

                            break;

                        case 8:
                            flag = false;
                            cat = "";
                            db.open();
                            if (db.getcategories(flag, cat).size() > 0) {
                                startActivity(new Intent(context, VQPSActivity.class).putExtra(CommonString.TAG_OBJECT, journeyPlan)
                                        .putExtra(CommonString.KEY_MENU_ID, current));
                                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

                            }

                            break;


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


    void declaration() {
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextAppearance(context, R.style.changestext_sizefor_mobile);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        visit_date = preferences.getString(CommonString.KEY_DATE, "");
        designation = preferences.getString(CommonString.KEY_DESIGNATION, "");
        recyclerView = (RecyclerView) findViewById(R.id.rec_menu);
        setTitle("The KPI Menu - " + visit_date);

        db = new NestleDb(context);
        db.open();
    }

    private boolean chekDataforCheckout(MappingJourneyPlan journeyPlan) {
        boolean status = true;
        db.open();
        ArrayList<MenuMaster> menu_list = db.getMenuData(journeyPlan, designation);
        for (int i = 0; i < menu_list.size(); i++) {
            switch (menu_list.get(i).getMenuId()) {
                case 1:
                    db.open();
                    if (db.getmasterProgram(journeyPlan).size() > 0) {
                        if (check_allfilledSubProgram()) {
                            status = true;
                        } else {
                            status = false;
                        }
                    } else {
                        status = true;
                    }

                    break;

                case 2:

                    db.open();
                    if (db.getasset(journeyPlan).size() > 0) {
                        db.open();
                        if (db.getinsertedpaidVisibility(journeyPlan).size() > 0) {
                            status = true;
                        } else {
                            status = false;
                        }
                    } else {
                        status = true;
                    }


                    break;

                case 3:
                    String cat = "";
                    boolean flag = true;
                    db.open();
                    if (db.VisicoolerCheckMapping(journeyPlan) && db.getmasterChecklist(journeyPlan, null, flag).size() > 0) {
                        db.open();
                        if (db.IsSubProgramChecklistfilled(journeyPlan, null, flag)) {
                            status = true;
                        } else {
                            status = false;
                        }
                    } else {
                        status = true;
                    }

                    break;

                case 4:
                    db.open();
                    if (db.getPOSMDeploymentData(journeyPlan).size() > 0) {
                        db.open();
                        if (db.IsPosmfilled(journeyPlan)) {
                            status = true;
                        } else {
                            status = false;
                        }

                    } else {
                        status = true;
                    }

                    break;

                case 5:
                    db.open();
                    if (db.getPromotionData(journeyPlan).size() > 0) {
                        db.open();
                        if (db.getinsertedpromotions(journeyPlan).size() > 0) {
                            status = true;
                        } else {
                            status = false;
                        }
                    } else {
                        status = true;
                    }

                    break;

                case 6:
                    flag = true;
                    db.open();
                    if (db.getcompanies().size() > 0) {
                        db.open();
                        if (db.getinsertedcomp_promotion(journeyPlan, flag).size() > 0) {
                            status = true;
                        } else {
                            status = false;
                        }
                    } else {
                        status = true;
                    }

                    break;

                case 7:
                    flag = false;
                    cat = "";
                    db.open();
                    if (db.getcategories(flag, cat).size() > 0) {
                        db.open();
                        if (db.getinsertedcomp_promotion(journeyPlan, flag).size() > 0) {
                            status = true;
                        } else {
                            status = false;
                        }
                    } else {
                        status = true;
                    }

                    break;

                case 8:
                    flag = false;
                    cat = "";
                    db.open();
                    if (db.getcategories(flag, cat).size() > 0) {
                        db.open();
                        if (db.getinsertedVQPS(journeyPlan).size() > 0) {
                            status = true;
                        } else {
                            status = false;
                        }
                    } else {
                        status = true;
                    }

                    break;


            }

            if (!status) {
                break;
            }

        }


        return status;
    }

    private boolean check_allfilledSubProgram() {
        boolean flag = true;
        db.open();
        ArrayList<MasterProgram> programs = db.getmasterProgram(journeyPlan);
        if (programs.size() > 0) {
            for (int i = 0; i < programs.size(); i++) {
                ArrayList<MasterProgram> subprogramList = programs.get(i).getSubprogramList();
                if (subprogramList.size() > 0) {
                    for (int k = 0; k < subprogramList.size(); k++) {
                        if (db.IsSubProgramChecklistfilled(journeyPlan, subprogramList.get(k), false) == false) {
                            flag = false;
                            break;
                        }
                    }
                }

                if (flag == false) {
                    break;
                }
            }
        }

        return flag;
    }


}
