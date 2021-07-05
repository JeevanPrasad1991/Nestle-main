package com.cpm.Nestle.dailyEntry;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.cpm.Nestle.R;
import com.cpm.Nestle.database.NestleDb;
import com.cpm.Nestle.getterSetter.MappingJourneyPlan;
import com.cpm.Nestle.getterSetter.StoreProfileGetterSetter;
import com.cpm.Nestle.utilities.AlertandMessages;
import com.cpm.Nestle.utilities.CommonFunctions;
import com.cpm.Nestle.utilities.CommonString;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StoreProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txt_storeId, txt_store_name, txt_city, txt_storeType;
    EditText edt_address, edt_landmark, edt_pincode;
    FloatingActionButton fab, btn_next;
    NestleDb db;
    Context context;
    private SharedPreferences preferences;
    String username, tag_from = "";
    boolean Isclicked = false;
    MappingJourneyPlan journeyPlan;
    StoreProfileGetterSetter storeProfileGetterSetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_profile);
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        toolbar.setTitleTextAppearance(context, R.style.changestext_sizefor_mobile);

        txt_storeId = (TextView) findViewById(R.id.txt_storeId);
        txt_store_name = (TextView) findViewById(R.id.txt_store_name);
        txt_storeType = (TextView) findViewById(R.id.txt_storeType);
        txt_city = (TextView) findViewById(R.id.txt_city);

        edt_address = (EditText) findViewById(R.id.edt_address);
        edt_landmark = (EditText) findViewById(R.id.edt_landmark);
        edt_pincode = (EditText) findViewById(R.id.edt_pincode);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        btn_next = (FloatingActionButton) findViewById(R.id.btn_next);
        fab.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        db = new NestleDb(context);
        db.open();
        if (getIntent().getSerializableExtra(CommonString.TAG_OBJECT) != null) {
            journeyPlan = (MappingJourneyPlan) getIntent().getSerializableExtra(CommonString.TAG_OBJECT);
        }

        if (getIntent().getStringExtra(CommonString.TAG_FROM) != null) {
            tag_from = getIntent().getStringExtra(CommonString.TAG_FROM);
        }

        setTitle("Store Profile - " + journeyPlan.getVisitDate());
        setjcp_data(journeyPlan);
    }


    private void setjcp_data(MappingJourneyPlan jcp) {
        if (jcp != null && jcp.getStoreId() != null) {
            txt_storeId.setText("" + jcp.getStoreId());
            txt_store_name.setText("" + jcp.getStoreName());
            txt_storeType.setText("" + jcp.getStoreType());
            txt_city.setText("" + jcp.getCityName());

            db.open();
            storeProfileGetterSetter = db.getStoreProfileData(jcp.getStoreId().toString(), jcp.getVisitDate());
            if (storeProfileGetterSetter != null && storeProfileGetterSetter.getStoreCd() != null) {
                edt_address.setText("" + storeProfileGetterSetter.getStore_addres());
                edt_landmark.setText("" + storeProfileGetterSetter.getLand_mark());
                edt_pincode.setText("" + storeProfileGetterSetter.getPin_code());
            } else {
                edt_address.setText("" + jcp.getAddress());
                edt_landmark.setText("" + jcp.getLandmark());
                edt_pincode.setText("" + jcp.getPincode());
            }
        }

    }


    public boolean validation() {
        boolean value = true;
        if (!edt_pincode.getText().toString().equals("")) {
            if (edt_pincode.getText().toString().length() < 6) {
                value = false;
                AlertandMessages.showToastMsg(context, "Please Enter Correct Pin Code");
            } else {
                value = true;
            }
        } else {
            value = true;
        }

        return value;
    }

    @Override
    public void onBackPressed() {
        if (Isclicked) {
            new AlertandMessages((Activity) context, null, null, null).backpressedAlert();
        } else {
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
            StoreProfileActivity.this.finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (Isclicked) {
                new AlertandMessages((Activity) context, null, null, null).backpressedAlert();
            } else {
                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                StoreProfileActivity.this.finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                if (validation()) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context).setTitle(R.string.dialog_title).setMessage(getString(R.string.alertsaveData))
                            .setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @SuppressWarnings("resource")
                                public void onClick(DialogInterface dialog, int id) {
                                    storeProfileGetterSetter = new StoreProfileGetterSetter();
                                    storeProfileGetterSetter.setStore_addres(edt_address.getText().toString().trim().replaceAll("[(!@#$%^&*?)\"]", ""));
                                    storeProfileGetterSetter.setLand_mark(edt_landmark.getText().toString().trim().replaceAll("[(!@#$%^&*?)\"]", ""));
                                    storeProfileGetterSetter.setPin_code(edt_pincode.getText().toString().trim());

                                    db.insertStoreProfileData(journeyPlan, storeProfileGetterSetter);
                                    Intent in = new Intent(context, EntryMenuActivity.class);
                                    in.putExtra(CommonString.TAG_OBJECT, journeyPlan);
                                    in.putExtra(CommonString.TAG_FROM, tag_from);
                                    startActivity(in);
                                    AlertandMessages.showToastMsg(StoreProfileActivity.this, "Data Saved");
                                    finish();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert1 = builder1.create();
                    alert1.show();
                }
                break;
            case R.id.btn_next:
                if (Isclicked) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Alert");
                    builder.setMessage("Filled data will be lost, Without save ?").setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Intent in = new Intent(context, EntryMenuActivity.class);
                                    in.putExtra(CommonString.TAG_OBJECT, journeyPlan);
                                    in.putExtra(CommonString.TAG_FROM, tag_from);
                                    startActivity(in);
                                    StoreProfileActivity.this.finish();
                                    overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                                }
                            });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();


                } else {
                    Intent in = new Intent(context, EntryMenuActivity.class);
                    in.putExtra(CommonString.TAG_OBJECT, journeyPlan);
                    in.putExtra(CommonString.TAG_FROM, tag_from);
                    startActivity(in);
                    StoreProfileActivity.this.finish();
                    overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                }
                break;
        }

    }
}
