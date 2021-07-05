package com.cpm.Nestle.dailyEntry;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.cpm.Nestle.getterSetter.MasterAsset;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cpm.Nestle.R;
import com.cpm.Nestle.database.NestleDb;
import com.cpm.Nestle.getterSetter.MappingJourneyPlan;
import com.cpm.Nestle.getterSetter.MenuMaster;
import com.cpm.Nestle.utilities.AlertandMessages;
import com.cpm.Nestle.utilities.CommonFunctions;
import com.cpm.Nestle.utilities.CommonString;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaidVisibilityActivity extends AppCompatActivity implements View.OnClickListener {
    public ArrayList<MasterAsset> masterTots = new ArrayList<>();
    MappingJourneyPlan journeyPlan;
    MenuMaster menuMaster = null;
    NestleDb db;
    Context context;
    SharedPreferences preferences;
    PaidVisibilityAdapter adapter;
    String username, visit_date_formatted, _pathforcheck = "", _path, img_str1 = "", error_message = "";
    boolean ischangedflag = false, check_flag = true;
    FloatingActionButton save_btn;
    RecyclerView rec_checklist;
    int _pos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_visibility);
        declaration();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                rec_checklist.invalidate();
                adapter.notifyDataSetChanged();
                if (check_validate()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(getString(R.string.main_menu_activity_name)).
                            setMessage(getString(R.string.alertsaveData)).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.open();
                            long l = db.Insertpaidvisibility(journeyPlan, masterTots);
                            if (l > 0) {
                                AlertandMessages.showToastMsg(context, "Data Saved Successfully.");
                                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                                PaidVisibilityActivity.this.finish();
                            } else {
                                AlertandMessages.showToastMsg(context, "Data Not Saved");
                            }
                        }
                    }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    AlertandMessages.showToastMsg(context, error_message);
                }

                break;

        }

    }


    @Override
    public void onBackPressed() {
        if (ischangedflag) {
            new AlertandMessages((Activity) context, null, null, null).backpressedAlert();
        } else {
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
            PaidVisibilityActivity.this.finish();
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
            if (ischangedflag) {
                new AlertandMessages((Activity) context, null, null, null).backpressedAlert();
            } else {
                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                PaidVisibilityActivity.this.finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void calling_adapter() {
        db.open();
        masterTots = db.getinsertedpaidVisibility(journeyPlan);
        if (masterTots.size() == 0) {
            db.open();
            masterTots = db.getasset(journeyPlan);
        } else {
            save_btn.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.edit_txt));
        }

        if (masterTots.size() > 0) {
            adapter = new PaidVisibilityAdapter(context, masterTots);
            rec_checklist.setAdapter(adapter);
            rec_checklist.setLayoutManager(new LinearLayoutManager(context));
            Collections.reverse(masterTots);
        }
    }


    class PaidVisibilityAdapter extends RecyclerView.Adapter<PaidVisibilityAdapter.MyViewHolder> {
        private LayoutInflater inflator;
        List<MasterAsset> data;

        public PaidVisibilityAdapter(Context context, List<MasterAsset> data) {
            inflator = LayoutInflater.from(context);
            this.data = data;
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflator.inflate(R.layout.paid_visibility_child_list, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            MasterAsset asset = data.get(position);

            holder.txt_category.setText("" + asset.getCategory());
            holder.txt_category.setId(position);

            holder.txt_assetname.setText("" + asset.getAssetName());
            holder.txt_assetname.setId(position);

            if (position != 0) {
                if (asset.getCategoryId() == data.get(position - 1).getCategoryId()) {
                    holder.rl_category.setVisibility(View.GONE);
                    holder.rl_category.setId(position);
                } else {
                    holder.rl_category.setVisibility(View.VISIBLE);
                    holder.rl_category.setId(position);
                }
            } else {
                holder.rl_category.setVisibility(View.VISIBLE);
                holder.rl_category.setId(position);
            }
            holder.checklist_btn_first.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ischangedflag = true;
                    holder.checklist_btn_first.setBackgroundDrawable(getResources().getDrawable(R.drawable.rouded_corner_pinki));
                    holder.checklist_btn_first.setTextColor(getResources().getColor(R.color.white));
                    holder.checklist_btn_first.setId(position);
                    holder.checklist_btn_second.setBackgroundDrawable(getResources().getDrawable(R.drawable.rouded_corner));
                    holder.checklist_btn_second.setTextColor(getResources().getColor(R.color.black));
                    holder.checklist_btn_second.setId(position);


                    asset.setIspresent(holder.checklist_btn_first.getText().toString());
                    holder.rl_for_yespaidvisib.setVisibility(View.VISIBLE);
                    holder.rl_for_yespaidvisib.setId(position);
                    holder.rl_for_nonassetreason.setVisibility(View.GONE);
                    holder.rl_for_nonassetreason.setId(position);
                    asset.setNon_reason("");
                    asset.setNonreasonId(0);
                    asset.setCheckidfor_nonreason(-1);
                }
            });


            holder.checklist_btn_second.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ischangedflag = true;
                    holder.checklist_btn_second.setBackgroundDrawable(getResources().getDrawable(R.drawable.rouded_corner_pinki));
                    holder.checklist_btn_second.setTextColor(getResources().getColor(R.color.white));
                    holder.checklist_btn_second.setId(position);
                    holder.checklist_btn_first.setBackgroundDrawable(getResources().getDrawable(R.drawable.rouded_corner));
                    holder.checklist_btn_first.setTextColor(getResources().getColor(R.color.black));
                    holder.checklist_btn_first.setId(position);

                    asset.setIspresent(holder.checklist_btn_second.getText().toString());
                    holder.rl_for_yespaidvisib.setVisibility(View.GONE);
                    holder.rl_for_yespaidvisib.setId(position);
                    holder.rl_for_nonassetreason.setVisibility(View.VISIBLE);
                    holder.rl_for_nonassetreason.setId(position);

                    holder.programchecklistImg_one.setImageResource(R.mipmap.camera_orange);
                    holder.programchecklistImg_one.setId(position);
                    asset.setAsset_img("");
                    asset.setLocationId(0);
                    asset.setLocation("");
                    asset.setCheckedIdforlocation(-1);

                }
            });


            if (!asset.getIspresent().equals("") && asset.getIspresent().equalsIgnoreCase("Yes")) {
                holder.checklist_btn_first.setBackgroundDrawable(getResources().getDrawable(R.drawable.rouded_corner_pinki));
                holder.checklist_btn_first.setTextColor(getResources().getColor(R.color.white));
                holder.checklist_btn_first.setId(position);
                holder.checklist_btn_second.setBackgroundDrawable(getResources().getDrawable(R.drawable.rouded_corner));
                holder.checklist_btn_second.setTextColor(getResources().getColor(R.color.black));
                holder.checklist_btn_second.setId(position);

            } else if (!asset.getIspresent().equals("") && asset.getIspresent().equalsIgnoreCase("No")) {
                holder.checklist_btn_second.setBackgroundDrawable(getResources().getDrawable(R.drawable.rouded_corner_pinki));
                holder.checklist_btn_second.setTextColor(getResources().getColor(R.color.white));
                holder.checklist_btn_second.setId(position);
                holder.checklist_btn_first.setBackgroundDrawable(getResources().getDrawable(R.drawable.rouded_corner));
                holder.checklist_btn_first.setTextColor(getResources().getColor(R.color.black));
                holder.checklist_btn_first.setId(position);
            } else {
                holder.checklist_btn_first.setBackgroundDrawable(getResources().getDrawable(R.drawable.rouded_corner));
                holder.checklist_btn_first.setTextColor(getResources().getColor(R.color.black));
                holder.checklist_btn_first.setId(position);
                holder.checklist_btn_second.setBackgroundDrawable(getResources().getDrawable(R.drawable.rouded_corner));
                holder.checklist_btn_second.setTextColor(getResources().getColor(R.color.black));
                holder.checklist_btn_second.setId(position);
            }

            holder.programchecklistImg_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _pos = position;
                    _pathforcheck = journeyPlan.getStoreId() + "-" + asset.getCategoryId() + "-" + asset.getAssetId() + "_TOTImg-" + visit_date_formatted + "-" + CommonFunctions.getCurrentTimeHHMMSS() + ".jpg";
                    _path = CommonString.FILE_PATH + _pathforcheck;
                    CommonFunctions.startAnncaCameraActivity(context, _path, null, false, CommonString.CAMERA_FACE_REAR);
                }
            });


            ///for location
            holder.radiogrpforlocation.removeAllViews();
            for (int i = 0; i < asset.getLocations().size(); i++) {
                RadioButton rdbtn = new RadioButton(context);
                rdbtn.setText("" + asset.getLocations().get(i).getLocationName());
                holder.radiogrpforlocation.addView(rdbtn);
                rdbtn.setId(i);
            }

            holder.radiogrpforlocation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int loccheckedId) {
                    int locationId = asset.getLocations().get(loccheckedId).getLocationId();
                    String location = asset.getLocations().get(loccheckedId).getLocationName();
                    asset.setLocationId(locationId);
                    asset.setLocation(location);
                    asset.setCheckedIdforlocation(loccheckedId);

                    if (asset.getLocationId() != 0) {
                        for (int k = 0; k < holder.radiogrpforlocation.getChildCount(); k++) {
                            if (holder.radiogrpforlocation.getChildAt(k).getId() == asset.getCheckedIdforlocation()) {
                                ((RadioButton) holder.radiogrpforlocation.getChildAt(k)).setChecked(true);
                                ((RadioButton) holder.radiogrpforlocation.getChildAt(k)).setId(asset.getCheckedIdforlocation());
                                holder.radiogrpforlocation.getChildAt(k);
                            } else {
                                ((RadioButton) holder.radiogrpforlocation.getChildAt(k)).setChecked(false);

                            }
                        }
                    }

                }
            });

            if (asset.getLocationId() != 0) {
                for (int k = 0; k < holder.radiogrpforlocation.getChildCount(); k++) {
                    if (holder.radiogrpforlocation.getChildAt(k).getId() == asset.getCheckedIdforlocation()) {
                        ((RadioButton) holder.radiogrpforlocation.getChildAt(k)).setChecked(true);
                        ((RadioButton) holder.radiogrpforlocation.getChildAt(k)).setId(asset.getCheckedIdforlocation());
                        holder.radiogrpforlocation.getChildAt(k);
                    } else {
                        ((RadioButton) holder.radiogrpforlocation.getChildAt(k)).setChecked(false);

                    }
                }
            }


            ///for Non Asset Reason
            holder.radiogrp.removeAllViews();
            for (int i = 0; i < asset.getNonAssetReasons().size(); i++) {
                RadioButton rdbtn = new RadioButton(context);
                rdbtn.setText("" + asset.getNonAssetReasons().get(i).getReason());
                holder.radiogrp.addView(rdbtn);
                rdbtn.setId(i);
            }

            holder.radiogrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int ansId = asset.getNonAssetReasons().get(checkedId).getReasonId();
                    String answer = asset.getNonAssetReasons().get(checkedId).getReason();
                    asset.setNonreasonId(ansId);
                    asset.setNon_reason(answer);
                    asset.setCheckidfor_nonreason(checkedId);

                    if (asset.getNonreasonId() != 0) {
                        for (int k = 0; k < holder.radiogrp.getChildCount(); k++) {
                            if (holder.radiogrp.getChildAt(k).getId() == asset.getCheckidfor_nonreason()) {
                                ((RadioButton) holder.radiogrp.getChildAt(k)).setChecked(true);
                                ((RadioButton) holder.radiogrp.getChildAt(k)).setId(asset.getCheckidfor_nonreason());
                                holder.radiogrp.getChildAt(k);
                            } else {
                                ((RadioButton) holder.radiogrp.getChildAt(k)).setChecked(false);

                            }
                        }
                    }

                }
            });

            if (asset.getNonreasonId() != 0) {
                for (int k = 0; k < holder.radiogrp.getChildCount(); k++) {
                    if (holder.radiogrp.getChildAt(k).getId() == asset.getCheckidfor_nonreason()) {
                        ((RadioButton) holder.radiogrp.getChildAt(k)).setChecked(true);
                        ((RadioButton) holder.radiogrp.getChildAt(k)).setId(asset.getCheckidfor_nonreason());
                        holder.radiogrp.getChildAt(k);

                    } else {
                        ((RadioButton) holder.radiogrp.getChildAt(k)).setChecked(false);

                    }
                }
            }


            if (!asset.getIspresent().equals("") && asset.getIspresent().equalsIgnoreCase("Yes")) {
                holder.rl_for_yespaidvisib.setVisibility(View.VISIBLE);
                holder.rl_for_yespaidvisib.setId(position);
                holder.rl_for_nonassetreason.setVisibility(View.GONE);
                holder.rl_for_nonassetreason.setId(position);

            } else if (!asset.getIspresent().equals("") && asset.getIspresent().equalsIgnoreCase("No")) {
                holder.rl_for_yespaidvisib.setVisibility(View.GONE);
                holder.rl_for_yespaidvisib.setId(position);
                holder.rl_for_nonassetreason.setVisibility(View.VISIBLE);
                holder.rl_for_nonassetreason.setId(position);
            } else {
                holder.rl_for_yespaidvisib.setVisibility(View.GONE);
                holder.rl_for_yespaidvisib.setId(position);
                holder.rl_for_nonassetreason.setVisibility(View.GONE);
                holder.rl_for_nonassetreason.setId(position);

            }

            if (!img_str1.equals("")) {
                if (_pos == position) {
                    asset.setAsset_img(img_str1);
                    img_str1 = "";
                    _pos = -1;
                }
            }


            if (!asset.getAsset_img().equals("")) {
                holder.programchecklistImg_one.setImageResource(R.mipmap.camera_green);
                holder.programchecklistImg_one.setId(position);
            } else {
                holder.programchecklistImg_one.setImageResource(R.mipmap.camera_orange);
                holder.programchecklistImg_one.setId(position);
            }

            if (check_flag == false) {
                boolean card_flag = false;
                if (asset.getIspresent().equals("")) {
                    card_flag = true;
                } else if (asset.getIspresent().equalsIgnoreCase("Yes") && asset.getLocationId() == 0 && asset.getAsset_img().equals("")) {
                    card_flag = true;
                } else if (asset.getIspresent().equalsIgnoreCase("No") && asset.getNonreasonId() == 0) {
                    card_flag = true;
                }

                if (card_flag) {
                    holder.card_view.setCardBackgroundColor(getResources().getColor(R.color.red));
                    holder.card_view.setId(position);
                } else {
                    holder.card_view.setCardBackgroundColor(getResources().getColor(R.color.white));
                    holder.card_view.setId(position);
                }
            } else {
                holder.card_view.setCardBackgroundColor(getResources().getColor(R.color.white));
                holder.card_view.setId(position);
            }

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            LinearLayout rl_for_nonassetreason, rl_for_yespaidvisib, rl_category;

            ImageView programchecklistImg_one;
            Button checklist_btn_first, checklist_btn_second;
            TextView txt_category, txt_assetname;
            RadioGroup radiogrp, radiogrpforlocation;
            CardView card_view;


            public MyViewHolder(View itemView) {
                super(itemView);
                txt_category = (TextView) itemView.findViewById(R.id.txt_category);
                txt_assetname = (TextView) itemView.findViewById(R.id.txt_assetname);

                rl_category = (LinearLayout) itemView.findViewById(R.id.rl_category);
                rl_for_yespaidvisib = (LinearLayout) itemView.findViewById(R.id.rl_for_yespaidvisib);
                programchecklistImg_one = (ImageView) itemView.findViewById(R.id.programchecklistImg_one);
                radiogrpforlocation = (RadioGroup) itemView.findViewById(R.id.radiogrpforlocation);

                checklist_btn_first = (Button) itemView.findViewById(R.id.checklist_btn_first);
                checklist_btn_second = (Button) itemView.findViewById(R.id.checklist_btn_second);
                rl_for_nonassetreason = (LinearLayout) itemView.findViewById(R.id.rl_for_nonassetreason);
                radiogrp = (RadioGroup) itemView.findViewById(R.id.radiogrp);
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
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        toolbar.setTitleTextAppearance(context, R.style.changestext_sizefor_mobile);
        visit_date_formatted = preferences.getString(CommonString.KEY_YYYYMMDD_DATE, "");
        rec_checklist = (RecyclerView) findViewById(R.id.programchecklistRecycle);
        save_btn = (FloatingActionButton) findViewById(R.id.fab);
        db = new NestleDb(context);
        db.open();

        username = preferences.getString(CommonString.KEY_USERNAME, "");
        if (getIntent().getSerializableExtra(CommonString.TAG_OBJECT) != null
                && getIntent().getSerializableExtra(CommonString.KEY_MENU_ID) != null) {
            journeyPlan = (MappingJourneyPlan) getIntent().getSerializableExtra(CommonString.TAG_OBJECT);
            menuMaster = (MenuMaster) getIntent().getSerializableExtra(CommonString.KEY_MENU_ID);
        }

        setTitle(menuMaster.getMenuName() + " - " + journeyPlan.getVisitDate());
        save_btn.setOnClickListener(this);
        calling_adapter();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rec_checklist.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY > oldScrollY) {
                        save_btn.hide();
                        Log.e("ProductFragment", "down");
                    } else {
                        save_btn.show();
                        Log.e("ProductFragment", "up");
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("MakeMachine", "resultCode: " + resultCode);
        switch (resultCode) {
            case 0:
                Log.i("MakeMachine", "User cancelled");
                break;
            case -1:
                if (_pathforcheck != null && !_pathforcheck.equals("")) {
                    if (new File(CommonString.FILE_PATH + _pathforcheck).exists()) {
                        try {
                            CommonFunctions.convertBitmap(CommonString.FILE_PATH + _pathforcheck);
                            String metadata = CommonFunctions.setMetadataAtImages(journeyPlan.getStoreName(), journeyPlan.getStoreId().toString(), menuMaster.getMenuName(), username);
                            CommonFunctions.addMetadataAndTimeStampToImage(context, _path, metadata, journeyPlan.getVisitDate());
                            img_str1 = _pathforcheck;

                            _pathforcheck = "";
                            adapter.notifyDataSetChanged();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean check_validate() {
        check_flag = true;
        if (masterTots.size() > 0) {
            for (int i = 0; i < masterTots.size(); i++) {
                if (masterTots.get(i).getIspresent().equals("")) {
                    check_flag = false;
                    error_message = "Please Select Present";
                    break;
                } else if (masterTots.get(i).getIspresent().equalsIgnoreCase("Yes")) {
                    if (masterTots.get(i).getAsset_img().equals("")) {
                        check_flag = false;
                        error_message = "Please Capture image";
                        break;
                    } else if (masterTots.get(i).getLocationId() == 0) {
                        check_flag = false;
                        error_message = "Please Select Location";
                        break;
                    }
                } else if (masterTots.get(i).getIspresent().equalsIgnoreCase("No") && masterTots.get(i).getNonreasonId() == 0) {
                    check_flag = false;
                    error_message = "Please Select Non Asset Reason";
                    break;
                } else {
                    check_flag = true;
                }
            }
        }

        return check_flag;
    }
}
