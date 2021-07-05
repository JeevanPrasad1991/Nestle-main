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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cpm.Nestle.R;
import com.cpm.Nestle.database.NestleDb;
import com.cpm.Nestle.getterSetter.MappingJourneyPlan;
import com.cpm.Nestle.getterSetter.MasterChecklist;
import com.cpm.Nestle.getterSetter.MasterChecklistAnswer;
import com.cpm.Nestle.getterSetter.MenuMaster;
import com.cpm.Nestle.utilities.AlertandMessages;
import com.cpm.Nestle.utilities.CommonFunctions;
import com.cpm.Nestle.utilities.CommonString;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VisicoolerActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView img_button_yes, img_button_no;
    public ArrayList<MasterChecklist> checklists = new ArrayList<>();
    MappingJourneyPlan journeyPlan;
    MenuMaster menuMaster = null;
    NestleDb db;
    Context context;
    SharedPreferences preferences;
    ChecklistAdapter adapter;
    String username, visit_date_formatted, _pathforcheck = "", _path, img_str1 = "", img_str2 = "", question_img_str1 = "",
            question_img_str2 = "", Ispresent = "", error_message = "", bar_code = "";
    boolean ischangedflag = false, yes_flag = false, check_flag = true;
    FloatingActionButton save_btn;
    RecyclerView rec_checklist;
    ScrollView scroll_view;
    LinearLayout ll_hide;
    int _pos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visi_cooler);
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
                if (!db.getsubprogramChecklistpresent(journeyPlan, null, true).getIschecked().equals("")) {
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
                            long l = db.InsertVisicooler(journeyPlan, Ispresent, checklists);
                            if (l > 0) {
                                AlertandMessages.showToastMsg(context, "Data Saved Successfully.");
                                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                                VisicoolerActivity.this.finish();
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
            VisicoolerActivity.this.finish();
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
                VisicoolerActivity.this.finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void calling_adapter(boolean for_refresh_recYcl) {
        if (for_refresh_recYcl) {
            db.open();
            checklists = db.getmasterChecklist(journeyPlan, null, true);
            if (checklists.size() > 0) {
                adapter = new ChecklistAdapter(context, checklists);
                rec_checklist.setAdapter(adapter);
                rec_checklist.setLayoutManager(new LinearLayoutManager(context));
            }
        } else {
            db.open();
            MasterChecklist inserted_obj = db.getsubprogramChecklistpresent(journeyPlan, null, true);
            if (inserted_obj != null && !inserted_obj.getIschecked().equals("") && inserted_obj.getIschecked().equals("1")) {
                save_btn.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.edit_txt));
                img_button_yes.setImageResource(R.mipmap.yes_green);
                Ispresent = "1";
                img_button_no.setImageResource(R.mipmap.no_white);
                ll_hide.setVisibility(View.VISIBLE);
                db.open();
                checklists = db.getinsertedVisicoolerChecklist(journeyPlan);
                if (checklists.size() == 0) {
                    db.open();
                    checklists = db.getmasterChecklist(journeyPlan, null, true);
                }

                Collections.reverse(checklists);
                if (checklists.size() > 0) {
                    adapter = new ChecklistAdapter(context, checklists);
                    rec_checklist.setAdapter(adapter);
                    rec_checklist.setLayoutManager(new LinearLayoutManager(context));
                }

            } else if (inserted_obj != null && !inserted_obj.getIschecked().equals("") && inserted_obj.getIschecked().equals("0")) {
                save_btn.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.edit_txt));
                img_button_no.setImageResource(R.mipmap.no_red);
                img_button_yes.setImageResource(R.mipmap.yes_white);
                Ispresent = "0";
                ll_hide.setVisibility(View.GONE);
            } else {
                db.open();
                checklists = db.getmasterChecklist(journeyPlan, null, true);
                if (checklists.size() > 0) {
                    adapter = new ChecklistAdapter(context, checklists);
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


    class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.MyViewHolder> {
        private LayoutInflater inflator;
        List<MasterChecklist> data;

        public ChecklistAdapter(Context context, List<MasterChecklist> data) {
            inflator = LayoutInflater.from(context);
            this.data = data;
        }


        @Override
        public ChecklistAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflator.inflate(R.layout.item_visicooler, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ChecklistAdapter.MyViewHolder holder, final int position) {
            MasterChecklist checkList = data.get(position);

            ///for category
            holder.txt_visicooler_category.setText("VCid - " + checkList.getVVcId() + "/" + checkList.getVisicooler_category());
            holder.txt_visicooler_category.setId(position);

            holder.tv_checklist.setText("" + checkList.getChecklistName());
            holder.tv_checklist.setId(position);

            holder.questionImg_txt_holder.setText("" + checkList.getQuestionImageLeble1());
            holder.questionImg_txt_holder.setId(position);

            holder.questionImg_two_txt_holder.setText("" + checkList.getQuestionImageLeble2());
            holder.questionImg_two_txt_holder.setId(position);

            if (position != 0) {
                if (checkList.getVisicooler_categoryId() == data.get(position - 1).getVisicooler_categoryId() && checkList.getVVcId() == data.get(position - 1).getVVcId()) {
                    holder.rl_for_visicoolercategory.setVisibility(View.GONE);
                    holder.rl_for_visicoolercategory.setId(position);
                } else {
                    holder.rl_for_visicoolercategory.setVisibility(View.VISIBLE);
                    holder.rl_for_visicoolercategory.setId(position);
                }
            } else {
                holder.rl_for_visicoolercategory.setVisibility(View.VISIBLE);
                holder.rl_for_visicoolercategory.setId(position);
            }


            if (checkList.getAnswerType().equalsIgnoreCase("Binary")) {
                holder.rl_for_binary.setVisibility(View.VISIBLE);
                holder.rl_for_binary.setId(position);
                holder.rl_for_sigle_selection.setVisibility(View.GONE);
                holder.rl_for_sigle_selection.setId(position);
                holder.rl_for_textValue.setVisibility(View.GONE);
                holder.rl_for_textValue.setId(position);

            } else if (checkList.getAnswerType().equalsIgnoreCase("Single Choice List")) {
                holder.rl_for_binary.setVisibility(View.GONE);
                holder.rl_for_binary.setId(position);
                holder.rl_for_sigle_selection.setVisibility(View.VISIBLE);
                holder.rl_for_sigle_selection.setId(position);

                holder.rl_for_textValue.setVisibility(View.GONE);
                holder.rl_for_textValue.setId(position);

            } else if (checkList.getAnswerType().equalsIgnoreCase("Text")) {
                holder.rl_for_binary.setVisibility(View.GONE);
                holder.rl_for_binary.setId(position);
                holder.rl_for_sigle_selection.setVisibility(View.GONE);
                holder.rl_for_sigle_selection.setId(position);
                holder.rl_for_textValue.setVisibility(View.VISIBLE);
                holder.rl_for_textValue.setId(position);
                holder.edt_textValue.setInputType(InputType.TYPE_CLASS_TEXT);

                holder.edt_textValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
                if (checkList.getBarcode()) {
                    holder.img_barcode_scanner.setVisibility(View.VISIBLE);
                    holder.img_barcode_scanner.setId(position);
                } else {
                    holder.img_barcode_scanner.setVisibility(View.INVISIBLE);
                    holder.img_barcode_scanner.setId(position);
                }

            } else if (checkList.getAnswerType().equalsIgnoreCase("Number")) {
                holder.rl_for_binary.setVisibility(View.GONE);
                holder.rl_for_binary.setId(position);
                holder.rl_for_sigle_selection.setVisibility(View.GONE);
                holder.rl_for_sigle_selection.setId(position);
                holder.rl_for_textValue.setVisibility(View.VISIBLE);
                holder.rl_for_textValue.setId(position);
                holder.edt_textValue.setInputType(InputType.TYPE_CLASS_NUMBER);
                holder.edt_textValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});

            } else if (checkList.getAnswerType().equalsIgnoreCase("Image")) {
                holder.rl_for_binary.setVisibility(View.GONE);
                holder.rl_for_binary.setId(position);
                holder.rl_for_sigle_selection.setVisibility(View.GONE);
                holder.rl_for_sigle_selection.setId(position);
                holder.rl_for_textValue.setVisibility(View.GONE);
                holder.rl_for_textValue.setId(position);

            }


            holder.checklist_btn_first.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.open();
                    MasterChecklistAnswer binary_ans = db.getchecklistansusingtext(checkList.getChecklistId(), holder.checklist_btn_first.getText().toString());
                    if (binary_ans != null && binary_ans.getAnswer() != null) {
                        holder.checklist_btn_first.setBackgroundDrawable(getResources().getDrawable(R.drawable.rouded_corner_pinki));
                        holder.checklist_btn_first.setTextColor(getResources().getColor(R.color.white));
                        holder.checklist_btn_first.setId(position);
                        holder.checklist_btn_second.setBackgroundDrawable(getResources().getDrawable(R.drawable.rouded_corner));
                        holder.checklist_btn_second.setTextColor(getResources().getColor(R.color.black));
                        holder.checklist_btn_second.setId(position);

                        checkList.setBinary_btn_value(binary_ans.getAnswer());
                        checkList.setBinary_btn_ansId(binary_ans.getAnswerId());
                        checkList.setImageAllow1(binary_ans.getImageAllow1());
                        checkList.setImageAllow2(binary_ans.getImageAllow2());

                        ///new changessssssss
                        checkList.setChecklistImglevel1(binary_ans.getImageLable1());
                        checkList.setChecklistImglevel2(binary_ans.getImageLable2());
                        checkList.setChecklistimgGridAllow1(binary_ans.getImageGridAllow1());
                        checkList.setChecklistimgGridAllow2(binary_ans.getImageGridAllow2());

                        holder.programchecklistImg_txt_holder.setText("" + checkList.getChecklistImglevel1());
                        holder.programchecklistImg_txt_holder.setId(position);

                        holder.programchecklistImg_two_txt_holder.setText("" + checkList.getChecklistImglevel2());
                        holder.programchecklistImg_two_txt_holder.setId(position);

                        if (checkList.getImageAllow1() || checkList.getImageAllow2()) {
                            holder.rl_img.setVisibility(View.VISIBLE);
                            holder.rl_img.setId(position);
                            if (checkList.getImageAllow1()) {
                                holder.programchecklistImg_one.setVisibility(View.VISIBLE);
                                holder.programchecklistImg_one.setId(position);
                            } else {
                                holder.programchecklistImg_one.setImageResource(R.mipmap.camera_orange);
                                holder.programchecklistImg_one.setVisibility(View.GONE);
                                holder.programchecklistImg_one.setId(position);
                                checkList.setChecklist_img1("");
                            }

                            if (checkList.getImageAllow2()) {
                                holder.programchecklistImg_two.setVisibility(View.VISIBLE);
                                holder.programchecklistImg_two.setId(position);
                            } else {
                                holder.programchecklistImg_two.setImageResource(R.mipmap.camera_orange);
                                holder.programchecklistImg_two.setVisibility(View.GONE);
                                holder.programchecklistImg_two.setId(position);
                                checkList.setChecklist_img2("");
                            }
                        } else {
                            holder.rl_img.setVisibility(View.GONE);
                            holder.rl_img.setId(position);
                            checkList.setChecklist_img1("");
                            checkList.setChecklist_img2("");
                            holder.programchecklistImg_one.setImageResource(R.mipmap.camera_orange);
                            holder.programchecklistImg_one.setId(position);

                            holder.programchecklistImg_two.setImageResource(R.mipmap.camera_orange);
                            holder.programchecklistImg_two.setId(position);
                        }

                    } else {
                        AlertandMessages.showToastMsg(context, "Answer Not found for it.");
                    }
                }
            });


            holder.checklist_btn_second.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.open();
                    MasterChecklistAnswer binary_ans = db.getchecklistansusingtext(checkList.getChecklistId(), holder.checklist_btn_second.getText().toString());
                    if (binary_ans != null && binary_ans.getAnswer() != null) {
                        holder.checklist_btn_second.setBackgroundDrawable(getResources().getDrawable(R.drawable.rouded_corner_pinki));
                        holder.checklist_btn_second.setTextColor(getResources().getColor(R.color.white));
                        holder.checklist_btn_second.setId(position);
                        holder.checklist_btn_first.setBackgroundDrawable(getResources().getDrawable(R.drawable.rouded_corner));
                        holder.checklist_btn_first.setTextColor(getResources().getColor(R.color.black));
                        holder.checklist_btn_first.setId(position);


                        checkList.setBinary_btn_value(binary_ans.getAnswer());
                        checkList.setBinary_btn_ansId(binary_ans.getAnswerId());
                        checkList.setImageAllow1(binary_ans.getImageAllow1());
                        checkList.setImageAllow2(binary_ans.getImageAllow2());

                        ///new changessssssss
                        checkList.setChecklistImglevel1(binary_ans.getImageLable1());
                        checkList.setChecklistImglevel2(binary_ans.getImageLable2());
                        checkList.setChecklistimgGridAllow1(binary_ans.getImageGridAllow1());
                        checkList.setChecklistimgGridAllow2(binary_ans.getImageGridAllow2());

                        holder.programchecklistImg_txt_holder.setText("" + checkList.getChecklistImglevel1());
                        holder.programchecklistImg_txt_holder.setId(position);

                        holder.programchecklistImg_two_txt_holder.setText("" + checkList.getChecklistImglevel2());
                        holder.programchecklistImg_two_txt_holder.setId(position);


                        if (checkList.getImageAllow1() || checkList.getImageAllow2()) {
                            holder.rl_img.setVisibility(View.VISIBLE);
                            holder.rl_img.setId(position);
                            if (checkList.getImageAllow1()) {
                                holder.programchecklistImg_one.setVisibility(View.VISIBLE);
                                holder.programchecklistImg_one.setId(position);
                            } else {
                                holder.programchecklistImg_one.setImageResource(R.mipmap.camera_orange);
                                holder.programchecklistImg_one.setVisibility(View.GONE);
                                holder.programchecklistImg_one.setId(position);
                                checkList.setChecklist_img1("");
                            }

                            if (checkList.getImageAllow2()) {
                                holder.programchecklistImg_two.setVisibility(View.VISIBLE);
                                holder.programchecklistImg_two.setId(position);
                            } else {
                                holder.programchecklistImg_two.setImageResource(R.mipmap.camera_orange);
                                holder.programchecklistImg_two.setVisibility(View.GONE);
                                holder.programchecklistImg_two.setId(position);
                                checkList.setChecklist_img2("");
                            }

                        } else {
                            holder.rl_img.setVisibility(View.GONE);
                            holder.rl_img.setId(position);
                            checkList.setChecklist_img1("");
                            checkList.setChecklist_img2("");
                            holder.programchecklistImg_one.setImageResource(R.mipmap.camera_orange);
                            holder.programchecklistImg_one.setId(position);

                            holder.programchecklistImg_two.setImageResource(R.mipmap.camera_orange);
                            holder.programchecklistImg_two.setId(position);
                        }

                    } else {
                        AlertandMessages.showToastMsg(context, "Answer Not found for it.");
                    }

                }
            });


            if (!checkList.getBinary_btn_value().equals("") && checkList.getBinary_btn_value().equalsIgnoreCase("Yes")) {
                holder.checklist_btn_first.setBackgroundDrawable(getResources().getDrawable(R.drawable.rouded_corner_pinki));
                holder.checklist_btn_first.setTextColor(getResources().getColor(R.color.white));
                holder.checklist_btn_first.setId(position);
                holder.checklist_btn_second.setBackgroundDrawable(getResources().getDrawable(R.drawable.rouded_corner));
                holder.checklist_btn_second.setTextColor(getResources().getColor(R.color.black));
                holder.checklist_btn_second.setId(position);

            } else if (!checkList.getBinary_btn_value().equals("") && checkList.getBinary_btn_value().equalsIgnoreCase("No")) {
                holder.checklist_btn_second.setBackgroundDrawable(getResources().getDrawable(R.drawable.rouded_corner_pinki));
                holder.checklist_btn_second.setTextColor(getResources().getColor(R.color.white));
                holder.checklist_btn_second.setId(position);
                holder.checklist_btn_first.setBackgroundDrawable(getResources().getDrawable(R.drawable.rouded_corner));
                holder.checklist_btn_first.setTextColor(getResources().getColor(R.color.black));
                holder.checklist_btn_first.setId(position);
            }


            if (checkList.getChecklistImglevel1() != null && !checkList.getChecklistImglevel1().equals("")) {
                holder.programchecklistImg_txt_holder.setText("" + checkList.getChecklistImglevel1());
                holder.programchecklistImg_txt_holder.setId(position);
            }


            if (checkList.getChecklistImglevel2() != null && !checkList.getChecklistImglevel2().equals("")) {
                holder.programchecklistImg_two_txt_holder.setText("" + checkList.getChecklistImglevel2());
                holder.programchecklistImg_two_txt_holder.setId(position);
            }


            holder.programchecklistImg_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _pos = position;
                    _pathforcheck = journeyPlan.getStoreId() + "-" + checkList.getVVcId() + "-" + checkList.getChecklistId() + "_VisicoolerImgOne-" + visit_date_formatted + "-" + CommonFunctions.getCurrentTimeHHMMSS() + ".jpg";
                    _path = CommonString.FILE_PATH + _pathforcheck;
                    if (checkList.getChecklistimgGridAllow1()) {
                        CommonFunctions.startAnncaCameraActivity(context, _path, null, true, CommonString.CAMERA_FACE_REAR);
                    } else {
                        CommonFunctions.startAnncaCameraActivity(context, _path, null, false, CommonString.CAMERA_FACE_REAR);
                    }

                }
            });


            holder.programchecklistImg_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _pos = position;
                    _pathforcheck = journeyPlan.getStoreId() + "-" + checkList.getVVcId() + "-" + checkList.getChecklistId() + "_VisicoolerImgTwo-" + visit_date_formatted + "-" + CommonFunctions.getCurrentTimeHHMMSS() + ".jpg";
                    _path = CommonString.FILE_PATH + _pathforcheck;
                    if (checkList.getChecklistimgGridAllow2()) {
                        CommonFunctions.startAnncaCameraActivity(context, _path, null, true, CommonString.CAMERA_FACE_REAR);
                    } else {
                        CommonFunctions.startAnncaCameraActivity(context, _path, null, false, CommonString.CAMERA_FACE_REAR);
                    }

                }
            });


            holder.radiogrp.removeAllViews();

            for (int i = 0; i < checkList.getAnswrs().size(); i++) {
                RadioButton rdbtn = new RadioButton(context);
                rdbtn.setText("" + checkList.getAnswrs().get(i).getAnswer());
                holder.radiogrp.addView(rdbtn);
                rdbtn.setId(i);
            }

            holder.radiogrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int ansId = checkList.getAnswrs().get(checkedId).getAnswerId();
                    String answer = checkList.getAnswrs().get(checkedId).getAnswer();
                    checkList.setImageAllow1(checkList.getAnswrs().get(checkedId).getImageAllow1());
                    checkList.setImageAllow2(checkList.getAnswrs().get(checkedId).getImageAllow2());
                    checkList.setAnsId(ansId);
                    checkList.setAnswer(answer);
                    checkList.setCheckedId(checkedId);

                    ///new changessssssss
                    checkList.setChecklistImglevel1(checkList.getAnswrs().get(checkedId).getImageLable1());
                    checkList.setChecklistImglevel2(checkList.getAnswrs().get(checkedId).getImageLable2());
                    checkList.setChecklistimgGridAllow1(checkList.getAnswrs().get(checkedId).getImageGridAllow1());
                    checkList.setChecklistimgGridAllow2(checkList.getAnswrs().get(checkedId).getImageGridAllow2());

                    holder.programchecklistImg_txt_holder.setText("" + checkList.getChecklistImglevel1());
                    holder.programchecklistImg_txt_holder.setId(position);

                    holder.programchecklistImg_two_txt_holder.setText("" + checkList.getChecklistImglevel2());
                    holder.programchecklistImg_two_txt_holder.setId(position);


                    if (checkList.getImageAllow1() || checkList.getImageAllow2()) {
                        holder.rl_img.setVisibility(View.VISIBLE);
                        holder.rl_img.setId(position);

                        if (checkList.getImageAllow1()) {
                            holder.programchecklistImg_one.setVisibility(View.VISIBLE);
                            holder.programchecklistImg_one.setId(position);
                        } else {
                            holder.programchecklistImg_one.setImageResource(R.mipmap.camera_orange);
                            holder.programchecklistImg_one.setVisibility(View.GONE);
                            holder.programchecklistImg_one.setId(position);
                            checkList.setChecklist_img1("");
                        }
                        if (checkList.getImageAllow2()) {
                            holder.programchecklistImg_two.setVisibility(View.VISIBLE);
                            holder.programchecklistImg_two.setId(position);
                        } else {
                            holder.programchecklistImg_two.setImageResource(R.mipmap.camera_orange);
                            holder.programchecklistImg_two.setVisibility(View.GONE);
                            holder.programchecklistImg_two.setId(position);
                            checkList.setChecklist_img2("");
                        }
                    } else {
                        holder.rl_img.setVisibility(View.GONE);
                        holder.rl_img.setId(position);
                        checkList.setChecklist_img1("");
                        checkList.setChecklist_img2("");
                        holder.programchecklistImg_one.setImageResource(R.mipmap.camera_orange);
                        holder.programchecklistImg_one.setId(position);

                        holder.programchecklistImg_two.setImageResource(R.mipmap.camera_orange);
                        holder.programchecklistImg_two.setId(position);
                    }

                    if (checkList.getAnsId() != 0) {
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

            if (checkList.getAnsId() != 0) {
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


            if (checkList.getImageAllow1() || checkList.getImageAllow2()) {
                holder.rl_img.setVisibility(View.VISIBLE);
                holder.rl_img.setId(position);

                if (checkList.getImageAllow1()) {
                    holder.programchecklistImg_one.setVisibility(View.VISIBLE);
                    holder.programchecklistImg_one.setId(position);
                } else {
                    holder.programchecklistImg_one.setVisibility(View.GONE);
                    holder.programchecklistImg_one.setId(position);
                }
                if (checkList.getImageAllow2()) {
                    holder.programchecklistImg_two.setVisibility(View.VISIBLE);
                    holder.programchecklistImg_two.setId(position);
                } else {
                    holder.programchecklistImg_two.setVisibility(View.GONE);
                    holder.programchecklistImg_two.setId(position);
                }
            } else {

                holder.rl_img.setVisibility(View.GONE);
                holder.rl_img.setId(position);
            }

            if (!img_str1.equals("")) {
                if (_pos == position) {
                    checkList.setChecklist_img1(img_str1);
                    img_str1 = "";
                    _pos = -1;
                }
            }

            if (!img_str2.equals("")) {
                if (_pos == position) {
                    checkList.setChecklist_img2(img_str2);
                    img_str2 = "";
                    _pos = -1;
                }
            }


            if (!checkList.getChecklist_img1().equals("")) {
                holder.programchecklistImg_one.setImageResource(R.mipmap.camera_green);
                holder.programchecklistImg_one.setId(position);
            } else {
                holder.programchecklistImg_one.setImageResource(R.mipmap.camera_orange);
                holder.programchecklistImg_one.setId(position);
            }


            if (!checkList.getChecklist_img2().equals("")) {
                holder.programchecklistImg_two.setImageResource(R.mipmap.camera_green);
                holder.programchecklistImg_two.setId(position);
            } else {
                holder.programchecklistImg_two.setImageResource(R.mipmap.camera_orange);
                holder.programchecklistImg_two.setId(position);
            }


            holder.edt_textValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        final EditText Caption = (EditText) v;
                        String value1 = "";
                        if (checkList.getAnswerType().equalsIgnoreCase("Text")) {
                            value1 = CommonFunctions.removed_special_char(Caption);
                        } else {
                            //value1 = Caption.getText().toString().replaceFirst("^0+(?!$)", "");
                            value1 = Caption.getText().toString().replaceAll("[^a-zA-Z0-9]", "").trim();


                        }

                        if (value1.equals("")) {
                            checkList.setEdt_text_value("");
                        } else {
                            checkList.setEdt_text_value(value1);

                        }
                    }
                }
            });


            if (checkList.getQuestionImageAllow2() || checkList.getQuestionImageAllow1() == 1) {
                holder.QuestionimagRl.setVisibility(View.VISIBLE);
                holder.QuestionimagRl.setId(position);

                if (checkList.getQuestionImageAllow1() == 1) {
                    holder.questionImg_one.setVisibility(View.VISIBLE);
                    holder.questionImg_one.setId(position);
                } else {
                    holder.questionImg_one.setVisibility(View.GONE);
                    holder.questionImg_one.setId(position);
                }

                if (checkList.getQuestionImageAllow2()) {
                    holder.questionImg_two.setVisibility(View.VISIBLE);
                    holder.questionImg_two.setId(position);
                } else {
                    holder.questionImg_two.setVisibility(View.GONE);
                    holder.questionImg_two.setId(position);
                }
            } else {
                holder.QuestionimagRl.setVisibility(View.GONE);
                holder.QuestionimagRl.setId(position);
            }


            holder.questionImg_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _pos = position;
                    _pathforcheck = journeyPlan.getStoreId() + "-" + checkList.getVVcId() + "-" + checkList.getChecklistId() + "_VisicoolerQuestionImgOne-" + visit_date_formatted + "-" + CommonFunctions.getCurrentTimeHHMMSS() + ".jpg";
                    _path = CommonString.FILE_PATH + _pathforcheck;
                    if (checkList.getChecklistimgGridAllow1()) {
                        CommonFunctions.startAnncaCameraActivity(context, _path, null, true, CommonString.CAMERA_FACE_REAR);
                    } else {
                        CommonFunctions.startAnncaCameraActivity(context, _path, null, false, CommonString.CAMERA_FACE_REAR);
                    }

                }
            });


            holder.questionImg_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _pos = position;
                    _pathforcheck = journeyPlan.getStoreId() + "-" + checkList.getVVcId() + "-" + checkList.getChecklistId() + "_VisicoolerQuestionImgTwo-" + visit_date_formatted + "-" + CommonFunctions.getCurrentTimeHHMMSS() + ".jpg";
                    _path = CommonString.FILE_PATH + _pathforcheck;
                    if (checkList.getChecklistimgGridAllow2()) {
                        CommonFunctions.startAnncaCameraActivity(context, _path, null, true, CommonString.CAMERA_FACE_REAR);
                    } else {
                        CommonFunctions.startAnncaCameraActivity(context, _path, null, false, CommonString.CAMERA_FACE_REAR);
                    }
                }
            });


            if (!question_img_str1.equals("")) {
                if (_pos == position) {
                    checkList.setQuestionImage1(question_img_str1);
                    question_img_str1 = "";
                    _pos = -1;
                }
            }

            if (!question_img_str2.equals("")) {
                if (_pos == position) {
                    checkList.setQuestionImage2(question_img_str2);
                    question_img_str2 = "";
                    _pos = -1;
                }
            }


            if (!checkList.getQuestionImage1().equals("")) {
                holder.questionImg_one.setImageResource(R.mipmap.camera_green);
                holder.questionImg_one.setId(position);
            } else {
                holder.questionImg_one.setImageResource(R.mipmap.camera_orange);
                holder.questionImg_one.setId(position);
            }


            if (!checkList.getQuestionImage2().equals("")) {
                holder.questionImg_two.setImageResource(R.mipmap.camera_green);
                holder.questionImg_two.setId(position);
            } else {
                holder.questionImg_two.setImageResource(R.mipmap.camera_orange);
                holder.questionImg_two.setId(position);
            }


            holder.img_barcode_scanner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openScanner();
                    _pos = position;
                }
            });

            if (!bar_code.equals("") && _pos == position) {
                checkList.setEdt_text_value(bar_code);
                bar_code = "";
                _pos = -1;
            }

            holder.edt_textValue.setText("" + checkList.getEdt_text_value());
            holder.edt_textValue.setId(position);

            if (check_flag == false) {
                boolean card_flag = false;
                if (checkList.getQuestionImageAllow1() == 1 && checkList.getQuestionImage1().equals("")) {
                    card_flag = true;
                } else if (checkList.getQuestionImageAllow2() && checkList.getQuestionImage2().equals("")) {
                    card_flag = true;
                } else if (checkList.getAnswerType().equalsIgnoreCase("Binary")) {
                    if (checkList.getBinary_btn_value().equals("")) {
                        card_flag = true;
                    } else if (checkList.getImageAllow1() && checkList.getChecklist_img1().equals("")) {
                        card_flag = true;

                    } else if (checkList.getImageAllow2() && checkList.getChecklist_img2().equals("")) {
                        card_flag = true;
                    }
                } else if (checkList.getAnswerType().equalsIgnoreCase("Single Choice List")) {
                    if (checkList.getAnsId() == 0) {
                        card_flag = true;
                    } else if (checkList.getImageAllow1() && checkList.getChecklist_img1().equals("")) {
                        card_flag = true;

                    } else if (checkList.getImageAllow2() && checkList.getChecklist_img2().equals("")) {
                        card_flag = true;
                    }
                } else if (checkList.getAnswerType().equalsIgnoreCase("Text") || checkList.getAnswerType().equalsIgnoreCase("Number")) {
                    if (checkList.getEdt_text_value().equals("")) {
                        card_flag = true;
                    }
                } else if (checkList.getAnswerType().equalsIgnoreCase("Image")) {
                    if (checkList.getQuestionImageAllow1() == 1 && checkList.getQuestionImage1().equals("")) {
                        card_flag = true;
                    } else if (checkList.getQuestionImageAllow2() && checkList.getQuestionImage2().equals("")) {
                        card_flag = true;
                    }
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
            TextView programchecklistImg_two_txt_holder, programchecklistImg_txt_holder, questionImg_two_txt_holder, questionImg_txt_holder;
            LinearLayout rl_for_binary, rl_for_sigle_selection, rl_for_textValue, rl_img, QuestionimagRl, rl_for_visicoolercategory;
            ImageView programchecklistImg_one, programchecklistImg_two, questionImg_one, questionImg_two, img_barcode_scanner;
            Button checklist_btn_first, checklist_btn_second;
            EditText edt_textValue;
            TextView tv_checklist, txt_visicooler_category;
            RadioGroup radiogrp;
            CardView card_view;


            public MyViewHolder(View itemView) {
                super(itemView);
                QuestionimagRl = (LinearLayout) itemView.findViewById(R.id.QuestionimagRl);
                rl_for_visicoolercategory = (LinearLayout) itemView.findViewById(R.id.rl_for_visicoolercategory);
                rl_for_binary = (LinearLayout) itemView.findViewById(R.id.rl_for_binary);
                rl_for_sigle_selection = (LinearLayout) itemView.findViewById(R.id.rl_for_sigle_selection);
                rl_for_textValue = (LinearLayout) itemView.findViewById(R.id.rl_for_textValue);
                rl_img = (LinearLayout) itemView.findViewById(R.id.rl_img);
                edt_textValue = (EditText) itemView.findViewById(R.id.edt_textValue);

                tv_checklist = (TextView) itemView.findViewById(R.id.checklist_txt);
                txt_visicooler_category = (TextView) itemView.findViewById(R.id.txt_visicooler_category);
                checklist_btn_first = (Button) itemView.findViewById(R.id.checklist_btn_first);
                checklist_btn_second = (Button) itemView.findViewById(R.id.checklist_btn_second);
                radiogrp = (RadioGroup) itemView.findViewById(R.id.radiogrp);
                card_view = (CardView) itemView.findViewById(R.id.card_view);

                programchecklistImg_one = (ImageView) itemView.findViewById(R.id.programchecklistImg_one);
                programchecklistImg_two = (ImageView) itemView.findViewById(R.id.programchecklistImg_two);
                questionImg_one = (ImageView) itemView.findViewById(R.id.questionImg_one);
                questionImg_two = (ImageView) itemView.findViewById(R.id.questionImg_two);
                img_barcode_scanner = (ImageView) itemView.findViewById(R.id.img_barcode_scanner);
                ////new changessssssssss.........
                programchecklistImg_two_txt_holder = (TextView) itemView.findViewById(R.id.programchecklistImg_two_txt_holder);
                programchecklistImg_txt_holder = (TextView) itemView.findViewById(R.id.programchecklistImg_txt_holder);
                questionImg_two_txt_holder = (TextView) itemView.findViewById(R.id.questionImg_two_txt_holder);
                questionImg_txt_holder = (TextView) itemView.findViewById(R.id.questionImg_txt_holder);

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
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result == null) {
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
                                if (_pathforcheck.contains("_VisicoolerImgOne-")) {
                                    img_str1 = _pathforcheck;
                                } else if (_pathforcheck.contains("_VisicoolerQuestionImgOne-")) {
                                    question_img_str1 = _pathforcheck;
                                } else if (_pathforcheck.contains("_VisicoolerQuestionImgTwo-")) {
                                    question_img_str2 = _pathforcheck;
                                } else {
                                    img_str2 = _pathforcheck;
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
        } else if (result != null) {
            if (result.getContents() == null) {
                Log.d("ScanActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                bar_code = result.getContents();
                adapter.notifyDataSetChanged();
            }

        } else
            super.onActivityResult(requestCode, resultCode, data);
    }


    private boolean check_validate() {
        check_flag = true;
        if (Ispresent.equals("")) {
            check_flag = false;
            error_message = "Please Select Present Or Not";
        } else if (Ispresent.equals("1")) {
            if (checklists.size() > 0) {
                for (int i = 0; i < checklists.size(); i++) {
                    if (checklists.get(i).getQuestionImageAllow1() == 1 && checklists.get(i).getQuestionImage1().equals("")) {
                        check_flag = false;
                        error_message = "Please Capture Question " + checklists.get(i).getQuestionImageLeble1() + " Image";
                        break;
                    } else if (checklists.get(i).getQuestionImageAllow2() && checklists.get(i).getQuestionImage2().equals("")) {
                        check_flag = false;
                        error_message = "Please Capture Question " + checklists.get(i).getQuestionImageLeble2() + " Image";
                        break;
                    } else if (checklists.get(i).getAnswerType().equalsIgnoreCase("Binary")) {
                        if (checklists.get(i).getBinary_btn_value().equals("")) {
                            check_flag = false;
                            error_message = "Please Select 'Yes' Or 'No'";
                            break;
                        } else if (checklists.get(i).getImageAllow1() && checklists.get(i).getChecklist_img1().equals("")) {
                            check_flag = false;
                            error_message = "Please Capture " + checklists.get(i).getChecklistImglevel1() + " Image";
                            break;
                        } else if (checklists.get(i).getImageAllow2() && checklists.get(i).getChecklist_img2().equals("")) {
                            check_flag = false;
                            error_message = "Please Capture " + checklists.get(i).getChecklistImglevel2() + " Image";
                            break;
                        }

                    } else if (checklists.get(i).getAnswerType().equalsIgnoreCase("Single Choice List")) {
                        if (checklists.get(i).getAnsId() == 0) {
                            check_flag = false;
                            error_message = "Please Select Answer";
                            break;
                        } else if (checklists.get(i).getImageAllow1() && checklists.get(i).getChecklist_img1().equals("")) {
                            check_flag = false;
                            error_message = "Please Capture " + checklists.get(i).getChecklistImglevel1() + " Image";
                            break;
                        } else if (checklists.get(i).getImageAllow2() && checklists.get(i).getChecklist_img2().equals("")) {
                            check_flag = false;
                            error_message = "Please Capture " + checklists.get(i).getChecklistImglevel2() + " Image";
                            break;
                        }
                    } else if (checklists.get(i).getAnswerType().equalsIgnoreCase("Text") || checklists.get(i).getAnswerType().equalsIgnoreCase("Number")) {
                        if (checklists.get(i).getEdt_text_value().equals("")) {
                            check_flag = false;
                            error_message = "Please Enter Value";
                            break;
                        }
                    } else if (checklists.get(i).getAnswerType().equalsIgnoreCase("Image")) {
                        if (checklists.get(i).getQuestionImageAllow1() == 1 && checklists.get(i).getQuestionImage1().equals("")) {
                            check_flag = false;
                            error_message = "Please Capture Question " + checklists.get(i).getQuestionImageLeble1() + " Image";
                            break;
                        } else if (checklists.get(i).getQuestionImageAllow2() && checklists.get(i).getQuestionImage2().equals("")) {
                            check_flag = false;
                            error_message = "Please Capture Question " + checklists.get(i).getQuestionImageLeble2() + " Image";
                            break;
                        }
                    }
                }

            } else {
                check_flag = true;
            }

        }

        return check_flag;

    }

    private void openScanner() {
        IntentIntegrator intentIntegrator = new IntentIntegrator((Activity) context);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setPrompt("Scan the barcode or QR code to get the data");
        intentIntegrator.setCaptureActivity(CaptureActivity.class);
        intentIntegrator.initiateScan();
    }

}
