package com.cpm.Nestle.dailyEntry;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.cpm.Nestle.database.NestleDb;
import com.cpm.Nestle.getterSetter.MappingPromotion;
import com.cpm.Nestle.getterSetter.MasterPromotionCheck;
import com.cpm.Nestle.getterSetter.MasterPromotionChecklistReason;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cpm.Nestle.R;
import com.cpm.Nestle.getterSetter.MappingJourneyPlan;
import com.cpm.Nestle.getterSetter.MenuMaster;
import com.cpm.Nestle.utilities.AlertandMessages;
import com.cpm.Nestle.utilities.CommonFunctions;
import com.cpm.Nestle.utilities.CommonString;
//import com.crashlytics.android.Crashlytics;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PromotionActivity extends AppCompatActivity implements View.OnClickListener {
    MappingJourneyPlan journeyPlan;
    MenuMaster menuMaster = null;
    NestleDb db;
    Context context;
    SharedPreferences preferences;
    String username, visit_date_formatted, _pathforcheck = "", _path, checklistImg_str = "", checkAnsImg_str = "",
            checkNonReasonImg_str = "", error_message = "";
    boolean ischangedflag = false, check_flag = true, onspinTouch = false, onspinTouchReason = false;
    FloatingActionButton save_btn;
    int checklist_pos = -1, grp_position = -1, child_position = -1;
    HashMap<MappingPromotion, List<MappingPromotion>> listDataChild;
    List<Integer> checkHeaderArray = new ArrayList<Integer>();
    ChecklistAdapter checklistAdapter = null;
    ArrayList<MappingPromotion> promoCategories;
    ExpandableListAdapter promotionAdapter;
    ExpandableListView expListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);
        declaration();
        calledExpandListView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                expListView.clearFocus();
                expListView.invalidateViews();
                if (validateData(listDataChild, promoCategories)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(getString(R.string.main_menu_activity_name)).
                            setMessage(getString(R.string.alertsaveData)).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.open();
                            long l = 0;

                            l = db.insertpromotions(journeyPlan, listDataChild, promoCategories);
                            if (l > 0) {
                                AlertandMessages.showToastMsg(context, "Data Saved Successfully.");
                                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                                PromotionActivity.this.finish();
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
            PromotionActivity.this.finish();
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
                PromotionActivity.this.finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }


    class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.MyViewCheckHolder> {
        private LayoutInflater inflator;
        List<MasterPromotionCheck> data;
        MappingPromotion childText;
        int child_pos, grp_pos;

        public ChecklistAdapter(Context context, final MappingPromotion childText, int grp_pos, int child_pos, List<MasterPromotionCheck> data) {
            inflator = LayoutInflater.from(context);
            this.data = data;
            this.grp_pos = grp_pos;
            this.child_pos = child_pos;
            this.childText = childText;
        }


        @Override
        public MyViewCheckHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflator.inflate(R.layout.item_promo_checklist, parent, false);
            MyViewCheckHolder holder = new MyViewCheckHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewCheckHolder holder, @SuppressLint("RecyclerView") final int position) {

            holder.txt_checklist.setText(data.get(position).getChecklist());
            holder.txt_checklist.setId(position);

            data.get(position).setPromoId(childText.getPromoId());
            data.get(position).setCategoryId(childText.getCategoryId());

            hidetaskoption(holder, data.get(position), position);

            ChecklistNonReasonAdapter nonReasonAdapter = new ChecklistNonReasonAdapter(context, data.get(position).getChecklistNonReasonList());
            holder.Reason_spin.setAdapter(nonReasonAdapter);

            holder.Reason_spin.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    onspinTouchReason = true;
                    return false;
                }
            });


            holder.Reason_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int itemPos, long l) {
                    if (onspinTouchReason) {
                        if (itemPos == 0) {
                            ischangedflag = true;
                            data.get(position).setNonReasonId(0);
                            data.get(position).setNonreasonimageAllow(false);
                            data.get(position).setCheckNonReasonImg("");
                            holder.reason_Img.setImageResource(R.mipmap.camera_orange);
                            holder.reason_Img.setId(position);
                            hidetaskoption(holder, data.get(position), position);
                        } else {
                            MasterPromotionChecklistReason ans = data.get(position).getChecklistNonReasonList().get(itemPos);
                            data.get(position).setNonReason(ans.getReason());
                            data.get(position).setNonReasonId(ans.getReasonId());
                            data.get(position).setNonreasonimageAllow(ans.isImageAllow());
                            hidetaskoption(holder, data.get(position), position);
                            if (!data.get(position).getCheckNonReasonImg().equals("")) {
                                data.get(position).setCheckNonReasonImg("");
                                holder.reason_Img.setImageResource(R.mipmap.camera_orange);
                                holder.reason_Img.setId(position);
                            }
                        }
                    }
                    onspinTouchReason = false;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            if (data.get(position).getChecklistNonReasonList().size() > 0) {
                for (int i = 0; i < data.get(position).getChecklistNonReasonList().size(); i++) {
                    if (data.get(position).getChecklistNonReasonList().get(i).getReasonId() == data.get(position).getNonReasonId()) {
                        holder.Reason_spin.setSelection(i);
                        break;
                    }
                }
            }


            ChecklistAnserAdapter customAdapter = new ChecklistAnserAdapter(context, data.get(position).getCheckAns());
            holder.check_spin.setAdapter(customAdapter);

            holder.check_spin.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    onspinTouch = true;
                    return false;
                }
            });


            holder.check_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int itemPos, long l) {
                    if (onspinTouch) {
                        if (itemPos == 0) {
                            data.get(position).setAnswerId(0);
                            data.get(position).setLongShotImage(false);
                            data.get(position).setqImageAllow(false);
                            data.get(position).setImageAllow(false);
                            data.get(position).setStockAllow(false);
                            data.get(position).setReasonAllow(false);
                            data.get(position).setNonreasonimageAllow(false);
                            data.get(position).setNonReasonId(0);
                            data.get(position).setStock("");
                            data.get(position).setChecklist_img("");
                            data.get(position).setChecklistAnsImg("");
                            data.get(position).setCheckNonReasonImg("");
                            data.get(position).setCheckAnsLongShotImg("");

                            hidetaskoption(holder, data.get(position), position);
                            holder.Etchecklist_stock.setText("");
                            holder.Etchecklist_stock.setId(position);
                            holder.promochecklist_AnsImg.setImageResource(R.mipmap.camera_orange);
                            holder.promochecklist_AnsImg.setId(position);
                            holder.promochecklist_Img.setImageResource(R.mipmap.camera_orange);
                            holder.promochecklist_Img.setId(position);
                            holder.reason_Img.setImageResource(R.mipmap.camera_orange);
                            holder.reason_Img.setId(position);
                            holder.promochecklist_AnsImg_longShot.setImageResource(R.mipmap.camera_orange);
                            holder.promochecklist_AnsImg_longShot.setId(position);

                        } else {
                            ischangedflag = true;
                            MasterPromotionCheck ans = data.get(position).getCheckAns().get(itemPos);
                            data.get(position).setNonreasonimageAllow(false);
                            data.get(position).setAnswerId(ans.getAnswerId());
                            data.get(position).setqImageAllow(ans.isqImageAllow());
                            data.get(position).setImageAllow(ans.isImageAllow());
                            data.get(position).setStockAllow(ans.isStockAllow());
                            data.get(position).setReasonAllow(ans.isReasonAllow());
                            data.get(position).setLongShotImage(ans.isLongShotImage());
                            hidetaskoption(holder, data.get(position), position);

                            if (!data.get(position).getStock().equals("")) {
                                data.get(position).setStock("");
                                holder.Etchecklist_stock.setText("");
                                holder.Etchecklist_stock.setId(position);
                            }
                            if (!data.get(position).getChecklistAnsImg().equals("")) {
                                data.get(position).setChecklistAnsImg("");
                                holder.promochecklist_AnsImg.setImageResource(R.mipmap.camera_orange);
                                holder.promochecklist_AnsImg.setId(position);
                            }

                            if (!data.get(position).getChecklist_img().equals("")) {
                                data.get(position).setChecklist_img("");
                                holder.promochecklist_Img.setImageResource(R.mipmap.camera_orange);
                                holder.promochecklist_Img.setId(position);
                            }

                            if (!data.get(position).getCheckAnsLongShotImg().equals("")) {
                                data.get(position).setCheckAnsLongShotImg("");
                                holder.promochecklist_AnsImg_longShot.setImageResource(R.mipmap.camera_orange);
                                holder.promochecklist_AnsImg_longShot.setId(position);
                            }

                        }
                    }
                    onspinTouch = false;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            if (data.get(position).getCheckAns().size() > 0) {
                for (int i = 0; i < data.get(position).getCheckAns().size(); i++) {
                    if (data.get(position).getCheckAns().get(i).getAnswerId() == data.get(position).getAnswerId()) {
                        holder.check_spin.setSelection(i);
                        break;
                    }
                }
            }

            holder.Etchecklist_stock.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        final EditText Caption = (EditText) v;
                        String final_value = Caption.getText().toString().replaceFirst("^0+(?!$)", "");
                        if (final_value.equals("")) {
                            data.get(position).setStock("");
                        } else {
                            data.get(position).setStock(final_value);
                        }
                    }
                }
            });

            holder.Etchecklist_stock.setText(data.get(position).getStock());
            holder.Etchecklist_stock.setId(position);


            holder.promochecklist_Img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checklist_pos = position;
                    child_position = child_pos;
                    grp_position = grp_pos;
                    _pathforcheck = journeyPlan.getStoreId() + "-" + data.get(position).getChecklistId() + "_PromoChecklistImg-"
                            + visit_date_formatted + "-" + CommonFunctions.getCurrentTimeHHMMSS() + ".jpg";
                    _path = CommonString.FILE_PATH + _pathforcheck;
                    CommonFunctions.startAnncaCameraActivity(context, _path, null, false, CommonString.CAMERA_FACE_REAR);
                }
            });

            holder.promochecklist_AnsImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checklist_pos = position;
                    child_position = child_pos;
                    grp_position = grp_pos;
                    _pathforcheck = journeyPlan.getStoreId() + "-" + data.get(position).getChecklistId() + "-" + data.get(position).getAnswerId() + "_PromoChecklistAnsImg-"
                            + visit_date_formatted + "-" + CommonFunctions.getCurrentTimeHHMMSS() + ".jpg";
                    _path = CommonString.FILE_PATH + _pathforcheck;
                    CommonFunctions.startAnncaCameraActivity(context, _path, null, false, CommonString.CAMERA_FACE_REAR);
                }
            });

            holder.promochecklist_AnsImg_longShot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checklist_pos = position;
                    child_position = child_pos;
                    grp_position = grp_pos;
                    _pathforcheck = journeyPlan.getStoreId() + "-" + data.get(position).getChecklistId() + "-" +
                            data.get(position).getAnswerId() + "_PCheckLongShotImg-"
                            + visit_date_formatted + "-" + CommonFunctions.getCurrentTimeHHMMSS() + ".jpg";
                    _path = CommonString.FILE_PATH + _pathforcheck;
                    CommonFunctions.startAnncaCameraActivity(context, _path, null, false, CommonString.CAMERA_FACE_REAR);
                }
            });
            holder.promochecklist_AnsImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checklist_pos = position;
                    child_position = child_pos;
                    grp_position = grp_pos;
                    _pathforcheck = journeyPlan.getStoreId() + "-" + data.get(position).getChecklistId() + "-" + data.get(position).getAnswerId() + "_PromoChecklistAnsImg-"
                            + visit_date_formatted + "-" + CommonFunctions.getCurrentTimeHHMMSS() + ".jpg";
                    _path = CommonString.FILE_PATH + _pathforcheck;
                    CommonFunctions.startAnncaCameraActivity(context, _path, null, false, CommonString.CAMERA_FACE_REAR);
                }
            });

            holder.reason_Img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checklist_pos = position;
                    child_position = child_pos;
                    grp_position = grp_pos;
                    _pathforcheck = journeyPlan.getStoreId() + "-" + data.get(position).getChecklistId() + "-" + data.get(position).getAnswerId()
                            + "_PromoCheckNonRImg-"
                            + visit_date_formatted + "-" + CommonFunctions.getCurrentTimeHHMMSS() + ".jpg";
                    _path = CommonString.FILE_PATH + _pathforcheck;
                    CommonFunctions.startAnncaCameraActivity(context, _path, null, false, CommonString.CAMERA_FACE_REAR);
                }
            });


            if (!checklistImg_str.equals("")) {
                if (checklist_pos == position && child_position == child_pos && grp_position == grp_pos) {
                    data.get(position).setChecklist_img(checklistImg_str);
                    checklistImg_str = "";
                    checklist_pos = -1;
                    child_position = -1;
                    grp_position = -1;
                }
            }

            if (!checkAnsImg_str.equals("")) {
                if (checklist_pos == position && child_position == child_pos && grp_position == grp_pos) {
                    data.get(position).setChecklistAnsImg(checkAnsImg_str);
                    checkAnsImg_str = "";
                    checklist_pos = -1;
                    child_position = -1;
                    grp_position = -1;
                }
            }

            if (!checkNonReasonImg_str.equals("")) {
                if (checklist_pos == position && child_position == child_pos && grp_position == grp_pos) {
                    data.get(position).setCheckNonReasonImg(checkNonReasonImg_str);
                    checkNonReasonImg_str = "";
                    checklist_pos = -1;
                    child_position = -1;
                    grp_position = -1;
                }
            }


            if (!data.get(position).getChecklist_img().equals("")) {
                holder.promochecklist_Img.setImageResource(R.mipmap.camera_green);
                holder.promochecklist_Img.setId(position);
            } else {
                holder.promochecklist_Img.setImageResource(R.mipmap.camera_orange);
                holder.promochecklist_Img.setId(position);

            }

            if (!data.get(position).getChecklistAnsImg().equals("")) {
                holder.promochecklist_AnsImg.setImageResource(R.mipmap.camera_green);
                holder.promochecklist_AnsImg.setId(position);
            } else {
                holder.promochecklist_AnsImg.setImageResource(R.mipmap.camera_orange);
                holder.promochecklist_AnsImg.setId(position);
            }

            if (!data.get(position).getCheckNonReasonImg().equals("")) {
                holder.reason_Img.setImageResource(R.mipmap.camera_green);
                holder.reason_Img.setId(position);
            } else {
                holder.reason_Img.setImageResource(R.mipmap.camera_orange);
                holder.reason_Img.setId(position);
            }


            if (check_flag == false) {
                boolean card_flag = false;
                if (data.get(position).getAnswerId() == 0) {
                    card_flag = true;
                } else if (data.get(position).isImageMandatory() && data.get(position).isQImageAllow() && data.get(position).getChecklist_img().equals("")) {
                    card_flag = true;
                } else if (data.get(position).isImageMandatory() && data.get(position).isImageAllow() && data.get(position).getChecklistAnsImg().equals("")) {
                    card_flag = true;
                } else if (data.get(position).isStockAllow() && data.get(position).getStock().equals("")) {
                    card_flag = true;
                } else if (data.get(position).isReasonAllow() && data.get(position).getNonReasonId() == 0) {
                    card_flag = true;
                } else if (data.get(position).isImageMandatory() && data.get(position).isNonreasonimageAllow() && data.get(position).getChecklist_img().equals("")) {
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

        class MyViewCheckHolder extends RecyclerView.ViewHolder {
            LinearLayout rl_checklistImg, rl_stock, rl_checklistReason, rl_checklistReasonImg, rl_checklistAnsImg;
            ImageView reason_Img, promochecklist_Img, promochecklist_AnsImg;
            AppCompatSpinner Reason_spin, check_spin;
            TextView txt_checklist;
            CardView card_view;
            AppCompatEditText Etchecklist_stock;

            LinearLayout rlCheckAnsLongShot;
            ImageView promochecklist_AnsImg_longShot;


            public MyViewCheckHolder(View itemView) {
                super(itemView);
                txt_checklist = (TextView) itemView.findViewById(R.id.txt_checklist);
                rlCheckAnsLongShot = (LinearLayout) itemView.findViewById(R.id.rlCheckAnsLongShot);
                promochecklist_AnsImg_longShot = (ImageView) itemView.findViewById(R.id.promochecklist_AnsImg_longShot);

                rl_checklistImg = (LinearLayout) itemView.findViewById(R.id.rl_checklistImg);
                rl_stock = (LinearLayout) itemView.findViewById(R.id.rl_stock);
                rl_checklistReason = (LinearLayout) itemView.findViewById(R.id.rl_checklistReason);
                rl_checklistReasonImg = (LinearLayout) itemView.findViewById(R.id.rl_checklistReasonImg);
                rl_checklistAnsImg = (LinearLayout) itemView.findViewById(R.id.rl_checklistAnsImg);

                reason_Img = (ImageView) itemView.findViewById(R.id.Nonreason_Img);
                promochecklist_Img = (ImageView) itemView.findViewById(R.id.promochecklist_Img);
                promochecklist_AnsImg = (ImageView) itemView.findViewById(R.id.promochecklist_AnsImg);

                check_spin = (AppCompatSpinner) itemView.findViewById(R.id.check_spin);
                Reason_spin = (AppCompatSpinner) itemView.findViewById(R.id.Reason_spin);

                Etchecklist_stock = (AppCompatEditText) itemView.findViewById(R.id.Etchecklist_stock);
                card_view = (CardView) itemView.findViewById(R.id.card_view);

            }
        }


    }


    private void hidetaskoption(ChecklistAdapter.MyViewCheckHolder holder, MasterPromotionCheck checklist, int position) {
        if (checklist.isLongShotImage()) {
            holder.rlCheckAnsLongShot.setVisibility(View.VISIBLE);
            holder.rlCheckAnsLongShot.setId(position);
        } else {
            holder.rlCheckAnsLongShot.setVisibility(View.GONE);
            holder.rlCheckAnsLongShot.setId(position);
        }
        if (checklist.isQImageAllow()) {
            holder.rl_checklistImg.setVisibility(View.VISIBLE);
            holder.rl_checklistImg.setId(position);
        } else {
            holder.rl_checklistImg.setVisibility(View.GONE);
            holder.rl_checklistImg.setId(position);
        }

        if (checklist.isStockAllow()) {
            holder.rl_stock.setVisibility(View.VISIBLE);
            holder.rl_stock.setId(position);
        } else {
            holder.rl_stock.setVisibility(View.GONE);
            holder.rl_stock.setId(position);
        }

        if (checklist.isImageAllow()) {
            holder.rl_checklistAnsImg.setVisibility(View.VISIBLE);
            holder.rl_checklistAnsImg.setId(position);
        } else {
            holder.rl_checklistAnsImg.setVisibility(View.GONE);
            holder.rl_checklistAnsImg.setId(position);
        }

        if (checklist.isReasonAllow()) {
            holder.rl_checklistReason.setVisibility(View.VISIBLE);
            holder.rl_checklistReason.setId(position);
        } else {
            holder.rl_checklistReason.setVisibility(View.GONE);
            holder.rl_checklistReason.setId(position);
        }

        if (checklist.isNonreasonimageAllow()) {
            holder.rl_checklistReasonImg.setVisibility(View.VISIBLE);
            holder.rl_checklistReasonImg.setId(position);
        } else {
            holder.rl_checklistReasonImg.setVisibility(View.GONE);
            holder.rl_checklistReasonImg.setId(position);
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
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
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
                            if (_pathforcheck.contains("_PromoChecklistImg-")) {
                                checklistImg_str = _pathforcheck;
                            } else if (_pathforcheck.contains("_PromoCheckNonRImg-")) {
                                checkNonReasonImg_str = _pathforcheck;
                            } else {
                                checkAnsImg_str = _pathforcheck;
                            }

                            _pathforcheck = "";
                            promotionAdapter.notifyDataSetChanged();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    boolean validateData(HashMap<MappingPromotion, List<MappingPromotion>> listDataChild2,
                         List<MappingPromotion> listDataHeader2) {
        checkHeaderArray.clear();
        check_flag = true;
        if (listDataHeader2.size() > 0) {
            for (int i = 0; i < listDataHeader2.size(); i++) {
                for (int j = 0; j < listDataChild2.get(listDataHeader2.get(i)).size(); j++) {
                    if (check_validate(listDataChild2.get(listDataHeader2.get(i)).get(j).getChecklists()) == false) {
                        check_flag = false;
                        break;
                    }

                }


                if (check_flag == false) {
                    if (!checkHeaderArray.contains(i)) {
                        checkHeaderArray.add(i);
                    }

                    break;
                }

            }

        }

        promotionAdapter.notifyDataSetChanged();

        return check_flag;
    }


    private boolean check_validate(ArrayList<MasterPromotionCheck> checklists) {
        boolean flag = true;
        if (checklists.size() > 0) {
            for (int i = 0; i < checklists.size(); i++) {
                if (checklists.get(i).getAnswerId() == 0) {
                    AlertandMessages.showToastMsg(context, "Please Select Checklist");
                    flag = false;
                    break;
                } else if (checklists.get(i).isImageMandatory() && checklists.get(i).isQImageAllow() && checklists.get(i).getChecklist_img().equals("")) {
                    error_message = "Please Capture Photo";
                    AlertandMessages.showToastMsg(context, error_message);
                    flag = false;
                    break;
                } else if (checklists.get(i).isImageMandatory() && checklists.get(i).isImageAllow() && checklists.get(i).getChecklistAnsImg().equals("")) {
                    error_message = "Please capture Photo";
                    AlertandMessages.showToastMsg(context, error_message);
                    flag = false;
                    break;
                } else if (checklists.get(i).isStockAllow() && checklists.get(i).getStock().equals("")) {
                    error_message = "Please Enter Stock";
                    AlertandMessages.showToastMsg(context, error_message);
                    flag = false;
                    break;
                } else if (checklists.get(i).isReasonAllow() && checklists.get(i).getNonReasonId() == 0) {
                    error_message = "Please Select Non Promotion Reason";
                    AlertandMessages.showToastMsg(context, error_message);
                    flag = false;
                    break;
                } else if (checklists.get(i).isImageMandatory() && checklists.get(i).isNonreasonimageAllow() && checklists.get(i).getCheckNonReasonImg().equals("")) {
                    error_message = "Please Capture Photo";
                    AlertandMessages.showToastMsg(context, error_message);
                    flag = false;
                    break;
                }
            }

        }
        return flag;
    }

    private class ChecklistAnserAdapter extends BaseAdapter {
        Context context;
        ArrayList<MasterPromotionCheck> anserlist;

        public ChecklistAnserAdapter(Context context, ArrayList<MasterPromotionCheck> reasonData) {
            this.context = context;
            this.anserlist = reasonData;
        }

        @Override
        public int getCount() {
            return anserlist.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(context).inflate(R.layout.custom_spinner_item, null);
            TextView names = (TextView) view.findViewById(R.id.tv_ans);
            names.setText(anserlist.get(i).getAnswer());
            return view;
        }
    }

    private class ChecklistNonReasonAdapter extends BaseAdapter {
        Context context;
        ArrayList<MasterPromotionChecklistReason> anserlist;

        public ChecklistNonReasonAdapter(Context context, ArrayList<MasterPromotionChecklistReason> reasonData) {
            this.context = context;
            this.anserlist = reasonData;
        }

        @Override
        public int getCount() {
            return anserlist.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(context).inflate(R.layout.custom_spinner_item, null);
            TextView names = (TextView) view.findViewById(R.id.tv_ans);
            names.setText(anserlist.get(i).getReason());
            return view;
        }
    }

    private void calledExpandListView() {
        // preparing list data
        prepareListData();

        expListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastItem = firstVisibleItem + visibleItemCount;
                if (firstVisibleItem == 0) {
                    save_btn.show();//.setVisibility(View.VISIBLE);
                } else if (lastItem == totalItemCount) {
                    save_btn.hide();//setVisibility(View.INVISIBLE);
                } else {
                    save_btn.show();//setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {
                InputMethodManager inputManager = (InputMethodManager) getApplicationContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                if (getCurrentFocus() != null) {
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    getCurrentFocus().clearFocus();
                }

            }
        });

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                InputMethodManager inputManager = (InputMethodManager) getApplicationContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                if (getWindow().getCurrentFocus() != null) {
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    getCurrentFocus().clearFocus();
                }
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

                InputMethodManager inputManager = (InputMethodManager) getApplicationContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                if (getWindow().getCurrentFocus() != null) {
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    getCurrentFocus().clearFocus();
                }
            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                return false;
            }
        });
    }

    private class ViewHolder {
        RecyclerView checklistRecycl;
        TextView txt_promotion;
        CardView card_view;
    }


    public class ExpandableListAdapter extends BaseExpandableListAdapter {
        private Context _context;
        private List<MappingPromotion> _listDataHeader;
        private HashMap<MappingPromotion, List<MappingPromotion>> _listDataChild;

        public ExpandableListAdapter(Context context, List<MappingPromotion> listDataHeader,
                                     HashMap<MappingPromotion, List<MappingPromotion>> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            final MappingPromotion childText = (MappingPromotion) getChild(groupPosition, childPosition);
            final MappingPromotion headerTitle = (MappingPromotion) getGroup(groupPosition);
            ViewHolder holder = null;
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.item_promotion, null);
                holder = new ViewHolder();
                holder.card_view = (CardView) convertView.findViewById(R.id.card_view);
                holder.checklistRecycl = (RecyclerView) convertView.findViewById(R.id.checklistRecycl);
                holder.txt_promotion = (TextView) convertView.findViewById(R.id.txt_promotion);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.txt_promotion.setText("" + childText.getPromotion());
            holder.txt_promotion.setId(childPosition);

            childText.setCategoryId(headerTitle.getCategoryId());
            childText.setCategory_name(headerTitle.getCategory_name());

            checklistAdapter = new ChecklistAdapter(context, childText, groupPosition, childPosition, childText.getChecklists());
            holder.checklistRecycl.setAdapter(checklistAdapter);
            holder.checklistRecycl.setLayoutManager(new LinearLayoutManager(context));

            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this._listDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this._listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            final MappingPromotion headerTitle = (MappingPromotion) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_group_opening, null);
            }

            CardView card_view = (CardView) convertView.findViewById(R.id.card_view);
            card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expListView.isGroupExpanded(groupPosition)) {
                        expListView.collapseGroup(groupPosition);
                    } else {
                        expListView.expandGroup(groupPosition);
                    }
                }
            });

            TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);


            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle.getCategory_name());


            if (check_flag) {
                if (checkHeaderArray.contains(groupPosition)) {
                    card_view.setCardBackgroundColor(getResources().getColor(R.color.red));
                } else {
                    card_view.setCardBackgroundColor(getResources().getColor(R.color.per_closed));
                }
            }


            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    //Preparing the list data
    private void prepareListData() {
        listDataChild = new HashMap<MappingPromotion, List<MappingPromotion>>();
        db.open();
        promoCategories = db.getPromotionCategory(journeyPlan);
        if (promoCategories.size() > 0) {
            // Adding child data
            for (int i = 0; i < promoCategories.size(); i++) {
                db.open();
                ArrayList<MappingPromotion> promoeslist = db.getPromotionData(journeyPlan, promoCategories.get(i).getCategoryId());
                if (promoeslist.size() > 0) {
                    if (promoeslist.get(0).getChecklists().get(0).getAnswerId() != 0) {
                        save_btn.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.edit_txt));
                    }
                }

                // Header, Child data
                listDataChild.put(promoCategories.get(i), promoeslist);
            }
        }

        promotionAdapter = new ExpandableListAdapter(context, promoCategories, listDataChild);
        // setting list adapter
        expListView.setAdapter(promotionAdapter);
        for (int i = 0; i < promotionAdapter.getGroupCount(); i++) {
            expListView.expandGroup(i);
        }

    }

}
