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

import com.cpm.Nestle.getterSetter.MasterChecklist;
import com.cpm.Nestle.getterSetter.MasterChecklistAnswer;
import com.cpm.Nestle.getterSetter.MasterPosm;
import com.cpm.Nestle.getterSetter.MenuMaster;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cpm.Nestle.R;
import com.cpm.Nestle.database.NestleDb;
import com.cpm.Nestle.getterSetter.MappingJourneyPlan;
import com.cpm.Nestle.utilities.AlertandMessages;
import com.cpm.Nestle.utilities.CommonFunctions;
import com.cpm.Nestle.utilities.CommonString;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class POSMDeploymentActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView img_button_yes, img_button_no;
    public ArrayList<MasterPosm> masterPosms = new ArrayList<>();
    MappingJourneyPlan journeyPlan;
    MenuMaster menuMaster = null;
    NestleDb db;
    Context context;
    SharedPreferences preferences;
    PosmAdapter adapter;
    String username, visit_date_formatted, _pathforcheck = "", _path, img_str1 = "", Ispresent = "", error_message = "", bar_code = "";
    boolean ischangedflag = false, yes_flag = false, check_flag = true;
    FloatingActionButton save_btn;
    RecyclerView rec_checklist;
    ScrollView scroll_view;
    LinearLayout ll_hide;
    int _pos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_o_s_m_deployment);
        declaration();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_button_yes:
                ischangedflag = true;
                yes_flag = true;
                Ispresent = "1";
                img_button_yes.setImageResource(R.mipmap.yes_green);
                img_button_no.setImageResource(R.mipmap.no_white);
                ll_hide.setVisibility(View.VISIBLE);
                boolean for_refresh = true;
                calling_adapter(for_refresh);

                break;

            case R.id.img_button_no:
                db.open();
                if (!db.getinsertedposmpresent(journeyPlan).getIspresnt().equals("")) {
                    final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context).setTitle(R.string.main_menu_activity_name).setMessage(getString(R.string.messageM));
                    builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            clear_not_present();
                            ischangedflag = true;
                            dialog.dismiss();
                        }
                    }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            ischangedflag = true;
                            if (yes_flag) {
                                img_button_yes.setImageResource(R.mipmap.yes_green);
                                img_button_no.setImageResource(R.mipmap.no_white);
                                ll_hide.setVisibility(View.VISIBLE);

                            } else {
                                img_button_yes.setImageResource(R.mipmap.yes_white);
                                img_button_no.setImageResource(R.mipmap.no_white);
                                ll_hide.setVisibility(View.GONE);

                            }

                        }
                    });

                    builder.show();
                } else {
                    Ispresent = "0";
                    clear_not_present();
                }

                break;

            case R.id.fab:
                rec_checklist.invalidate();
                adapter.notifyDataSetChanged();
                if (check_validate()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(getString(R.string.main_menu_activity_name)).
                            setMessage(getString(R.string.alertsaveData)).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.open();
                            long l = db.Insertposm(journeyPlan, Ispresent, masterPosms);
                            if (l > 0) {
                                AlertandMessages.showToastMsg(context, "Data Saved Successfully.");
                                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                                POSMDeploymentActivity.this.finish();
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

    private void clear_not_present() {
        try {
            Ispresent = "0";
            yes_flag = false;
            img_button_no.setImageResource(R.mipmap.no_red);
            img_button_yes.setImageResource(R.mipmap.yes_white);
            ll_hide.setVisibility(View.GONE);
            boolean for_refresh = true;
            calling_adapter(for_refresh);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (ischangedflag) {
            new AlertandMessages((Activity) context, null, null, null).backpressedAlert();
        } else {
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
            POSMDeploymentActivity.this.finish();
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
                POSMDeploymentActivity.this.finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void calling_adapter(boolean for_refresh_recYcl) {
        if (for_refresh_recYcl) {
            db.open();
            masterPosms = db.getPOSMDeploymentData(journeyPlan);
            if (masterPosms.size() > 0) {
                adapter = new PosmAdapter(context, masterPosms);
                rec_checklist.setAdapter(adapter);
                rec_checklist.setLayoutManager(new LinearLayoutManager(context));
            }
        } else {
            db.open();
            MasterPosm inserted_obj = db.getinsertedposmpresent(journeyPlan);
            if (inserted_obj != null && !inserted_obj.getIspresnt().equals("") && inserted_obj.getIspresnt().equals("1")) {
                save_btn.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.edit_txt));
                img_button_yes.setImageResource(R.mipmap.yes_green);
                Ispresent = "1";
                img_button_no.setImageResource(R.mipmap.no_white);
                ll_hide.setVisibility(View.VISIBLE);
                db.open();
                masterPosms = db.getinsertedposm(journeyPlan);
                if (masterPosms.size() == 0) {
                    db.open();
                    masterPosms = db.getPOSMDeploymentData(journeyPlan);
                }

                Collections.reverse(masterPosms);
                if (masterPosms.size() > 0) {
                    adapter = new PosmAdapter(context, masterPosms);
                    rec_checklist.setAdapter(adapter);
                    rec_checklist.setLayoutManager(new LinearLayoutManager(context));
                }

            } else if (inserted_obj != null && !inserted_obj.getIspresnt().equals("") && inserted_obj.getIspresnt().equals("0")) {
                save_btn.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.edit_txt));
                img_button_no.setImageResource(R.mipmap.no_red);
                img_button_yes.setImageResource(R.mipmap.yes_white);
                Ispresent = "0";
                ll_hide.setVisibility(View.GONE);
            } else {
                db.open();
                masterPosms = db.getPOSMDeploymentData(journeyPlan);
                if (masterPosms.size() > 0) {
                    adapter = new PosmAdapter(context, masterPosms);
                    rec_checklist.setAdapter(adapter);
                    rec_checklist.setLayoutManager(new LinearLayoutManager(context));
                    adapter.notifyDataSetChanged();
                    img_button_no.setImageResource(R.mipmap.no_white);
                    img_button_yes.setImageResource(R.mipmap.yes_white);
                    ll_hide.setVisibility(View.GONE);
                    Ispresent = "";
                }

            }
        }
    }


    class PosmAdapter extends RecyclerView.Adapter<PosmAdapter.MyViewHolder> {
        private LayoutInflater inflator;
        List<MasterPosm> data;

        public PosmAdapter(Context context, List<MasterPosm> data) {
            inflator = LayoutInflater.from(context);
            this.data = data;
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflator.inflate(R.layout.posm_list_item, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            MasterPosm checkList = data.get(position);

            holder.txt_posm.setText("" + checkList.getPosmName());
            holder.txt_posm.setId(position);

            holder.checklist_btn_first.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.checklist_btn_first.setBackgroundDrawable(getResources().getDrawable(R.drawable.rouded_corner_pinki));
                    holder.checklist_btn_first.setTextColor(getResources().getColor(R.color.white));
                    holder.checklist_btn_first.setId(position);
                    holder.checklist_btn_second.setBackgroundDrawable(getResources().getDrawable(R.drawable.rouded_corner));
                    holder.checklist_btn_second.setTextColor(getResources().getColor(R.color.black));
                    holder.checklist_btn_second.setId(position);


                    checkList.setPosm_yesorno(holder.checklist_btn_first.getText().toString());
                    holder.rl_img.setVisibility(View.VISIBLE);
                    holder.rl_img.setId(position);
                    holder.rl_for_sigle_selection.setVisibility(View.GONE);
                    holder.rl_for_sigle_selection.setId(position);
                    checkList.setReason("");
                    checkList.setReasonId(0);
                }
            });


            holder.checklist_btn_second.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.checklist_btn_second.setBackgroundDrawable(getResources().getDrawable(R.drawable.rouded_corner_pinki));
                    holder.checklist_btn_second.setTextColor(getResources().getColor(R.color.white));
                    holder.checklist_btn_second.setId(position);
                    holder.checklist_btn_first.setBackgroundDrawable(getResources().getDrawable(R.drawable.rouded_corner));
                    holder.checklist_btn_first.setTextColor(getResources().getColor(R.color.black));
                    holder.checklist_btn_first.setId(position);

                    checkList.setPosm_yesorno(holder.checklist_btn_second.getText().toString());
                    holder.rl_img.setVisibility(View.GONE);
                    holder.rl_img.setId(position);
                    holder.rl_for_sigle_selection.setVisibility(View.VISIBLE);
                    holder.rl_for_sigle_selection.setId(position);

                    checkList.setPosm_img("");
                    holder.programchecklistImg_one.setImageResource(R.mipmap.camera_orange);
                    holder.programchecklistImg_one.setId(position);
                }
            });


            if (!checkList.getPosm_yesorno().equals("") && checkList.getPosm_yesorno().equalsIgnoreCase("Yes")) {
                holder.checklist_btn_first.setBackgroundDrawable(getResources().getDrawable(R.drawable.rouded_corner_pinki));
                holder.checklist_btn_first.setTextColor(getResources().getColor(R.color.white));
                holder.checklist_btn_first.setId(position);
                holder.checklist_btn_second.setBackgroundDrawable(getResources().getDrawable(R.drawable.rouded_corner));
                holder.checklist_btn_second.setTextColor(getResources().getColor(R.color.black));
                holder.checklist_btn_second.setId(position);

            } else if (!checkList.getPosm_yesorno().equals("") && checkList.getPosm_yesorno().equalsIgnoreCase("No")) {
                holder.checklist_btn_second.setBackgroundDrawable(getResources().getDrawable(R.drawable.rouded_corner_pinki));
                holder.checklist_btn_second.setTextColor(getResources().getColor(R.color.white));
                holder.checklist_btn_second.setId(position);
                holder.checklist_btn_first.setBackgroundDrawable(getResources().getDrawable(R.drawable.rouded_corner));
                holder.checklist_btn_first.setTextColor(getResources().getColor(R.color.black));
                holder.checklist_btn_first.setId(position);
            }

            holder.programchecklistImg_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _pos = position;
                    _pathforcheck = journeyPlan.getStoreId() + "-" + checkList.getPosmId() + "_PosmImg-" + visit_date_formatted + "-" + CommonFunctions.getCurrentTimeHHMMSS() + ".jpg";
                    _path = CommonString.FILE_PATH + _pathforcheck;
                    CommonFunctions.startAnncaCameraActivity(context, _path, null, false, CommonString.CAMERA_FACE_REAR);
                }
            });


            holder.radiogrp.removeAllViews();

            for (int i = 0; i < checkList.getReasonArrayList().size(); i++) {
                RadioButton rdbtn = new RadioButton(context);
                rdbtn.setText("" + checkList.getReasonArrayList().get(i).getReason());
                holder.radiogrp.addView(rdbtn);
                rdbtn.setId(i);
            }

            holder.radiogrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int ansId = checkList.getReasonArrayList().get(checkedId).getReasonId();
                    String answer = checkList.getReasonArrayList().get(checkedId).getReason();
                    checkList.setReasonId(ansId);
                    checkList.setReason(answer);
                    checkList.setCheckedId(checkedId);

                    if (checkList.getReasonId() != 0) {
                        for (int k = 0; k < holder.radiogrp.getChildCount(); k++) {
                            if (holder.radiogrp.getChildAt(k).getId() == checkList.getCheckedId()) {
                                ((RadioButton) holder.radiogrp.getChildAt(k)).setChecked(true);
                                ((RadioButton) holder.radiogrp.getChildAt(k)).setId(checkList.getCheckedId());
                                holder.radiogrp.getChildAt(k);
                            } else {
                                ((RadioButton) holder.radiogrp.getChildAt(k)).setChecked(false);

                            }
                        }
                    }

                }
            });

            if (checkList.getReasonId() != 0) {
                for (int k = 0; k < holder.radiogrp.getChildCount(); k++) {
                    if (holder.radiogrp.getChildAt(k).getId() == checkList.getCheckedId()) {
                        ((RadioButton) holder.radiogrp.getChildAt(k)).setChecked(true);
                        ((RadioButton) holder.radiogrp.getChildAt(k)).setId(checkList.getCheckedId());
                        holder.radiogrp.getChildAt(k);

                    } else {
                        ((RadioButton) holder.radiogrp.getChildAt(k)).setChecked(false);

                    }
                }
            }


            if (!checkList.getPosm_yesorno().equals("") && checkList.getPosm_yesorno().equalsIgnoreCase("Yes")) {
                holder.rl_img.setVisibility(View.VISIBLE);
                holder.rl_img.setId(position);
                holder.rl_for_sigle_selection.setVisibility(View.GONE);
                holder.rl_for_sigle_selection.setId(position);

            } else if (!checkList.getPosm_yesorno().equals("") && checkList.getPosm_yesorno().equalsIgnoreCase("No")) {
                holder.rl_img.setVisibility(View.GONE);
                holder.rl_img.setId(position);
                holder.rl_for_sigle_selection.setVisibility(View.VISIBLE);
                holder.rl_for_sigle_selection.setId(position);
            } else {
                holder.rl_img.setVisibility(View.GONE);
                holder.rl_img.setId(position);
                holder.rl_for_sigle_selection.setVisibility(View.GONE);
                holder.rl_for_sigle_selection.setId(position);

            }

            if (!img_str1.equals("")) {
                if (_pos == position) {
                    checkList.setPosm_img(img_str1);
                    img_str1 = "";
                    _pos = -1;
                }
            }


            if (!checkList.getPosm_img().equals("")) {
                holder.programchecklistImg_one.setImageResource(R.mipmap.camera_green);
                holder.programchecklistImg_one.setId(position);
            } else {
                holder.programchecklistImg_one.setImageResource(R.mipmap.camera_orange);
                holder.programchecklistImg_one.setId(position);
            }

            if (check_flag == false) {
                boolean card_flag = false;
                if (checkList.getPosm_yesorno().equals("")) {
                    card_flag = true;
                } else if (checkList.getPosm_yesorno().equalsIgnoreCase("Yes")&& checkList.getPosm_img().equals("")) {
                    card_flag = true;
                } else if (checkList.getPosm_yesorno().equalsIgnoreCase("No") && checkList.getReasonId() == 0) {
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
            LinearLayout rl_for_sigle_selection, rl_img;

            ImageView programchecklistImg_one;
            Button checklist_btn_first, checklist_btn_second;
            TextView txt_posm;
            RadioGroup radiogrp;
            CardView card_view;


            public MyViewHolder(View itemView) {
                super(itemView);
                txt_posm = (TextView) itemView.findViewById(R.id.txt_posm);
                checklist_btn_first = (Button) itemView.findViewById(R.id.checklist_btn_first);
                checklist_btn_second = (Button) itemView.findViewById(R.id.checklist_btn_second);
                rl_for_sigle_selection = (LinearLayout) itemView.findViewById(R.id.rl_for_sigle_selection);
                rl_img = (LinearLayout) itemView.findViewById(R.id.rl_img);
                radiogrp = (RadioGroup) itemView.findViewById(R.id.radiogrp);
                card_view = (CardView) itemView.findViewById(R.id.card_view);
                programchecklistImg_one = (ImageView) itemView.findViewById(R.id.programchecklistImg_one);
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
        img_button_yes = (ImageView) findViewById(R.id.img_button_yes);
        img_button_no = (ImageView) findViewById(R.id.img_button_no);
        save_btn = (FloatingActionButton) findViewById(R.id.fab);
        ll_hide = (LinearLayout) findViewById(R.id.ll_hide);
        scroll_view = (ScrollView) findViewById(R.id.scroll_view);
        db = new NestleDb(context);
        db.open();

        username = preferences.getString(CommonString.KEY_USERNAME, "");
        if (getIntent().getSerializableExtra(CommonString.TAG_OBJECT) != null
                && getIntent().getSerializableExtra(CommonString.KEY_MENU_ID) != null) {
            journeyPlan = (MappingJourneyPlan) getIntent().getSerializableExtra(CommonString.TAG_OBJECT);
            menuMaster = (MenuMaster) getIntent().getSerializableExtra(CommonString.KEY_MENU_ID);
        }

        setTitle(menuMaster.getMenuName() + " - " + journeyPlan.getVisitDate());
        img_button_yes.setOnClickListener(this);
        img_button_no.setOnClickListener(this);
        save_btn.setOnClickListener(this);
        boolean for_refresh = false;
        calling_adapter(for_refresh);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scroll_view.setOnScrollChangeListener(new View.OnScrollChangeListener() {
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
                            if (_pathforcheck.contains("_PosmImg-")) {
                                img_str1 = _pathforcheck;
                            }

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
        if (Ispresent.equals("")) {
            check_flag = false;
            error_message = "Please Select Present Or Not";
        } else if (Ispresent.equals("1")) {
            if (masterPosms.size() > 0) {
                for (int i = 0; i < masterPosms.size(); i++) {
                    if (masterPosms.get(i).getPosm_yesorno().equals("")) {
                        check_flag = false;
                        error_message = "Please Select Yes Or No";
                        break;
                    } else if (masterPosms.get(i).getPosm_yesorno().equalsIgnoreCase("Yes") && masterPosms.get(i).getPosm_img().equals("")) {
                        check_flag = false;
                        error_message = "Please Capture image";
                        break;
                    } else if (masterPosms.get(i).getPosm_yesorno().equalsIgnoreCase("No") && masterPosms.get(i).getReasonId() == 0) {
                        check_flag = false;
                        error_message = "Please Select Yes Reason";
                        break;
                    } else {
                        check_flag = true;
                    }

                }
            }
        } else {
            check_flag = true;
        }

        return check_flag;
    }
}
