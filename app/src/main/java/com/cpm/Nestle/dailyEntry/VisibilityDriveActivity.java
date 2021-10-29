package com.cpm.Nestle.dailyEntry;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import com.cpm.Nestle.database.NestleDb;
import com.cpm.Nestle.getterSetter.MappingVQPSVisibilityDrive;
import com.cpm.Nestle.getterSetter.MasterDriveNonVisibility;
import com.cpm.Nestle.getterSetter.ViisbilityDriveGetterSetter;
import com.cpm.Nestle.upload.Retrofit_method.UploadImageWithRetrofit;
import com.cpm.Nestle.upload.Retrofit_method.UploadImageWithRetrofitOne;
import com.cpm.Nestle.utilities.AlertandMessages;
import com.cpm.Nestle.utilities.CommonFunctions;
import com.cpm.Nestle.utilities.CommonString;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cpm.Nestle.R;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class VisibilityDriveActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    ArrayList<MappingVQPSVisibilityDrive> reasondata = new ArrayList<>();
    ArrayList<MasterDriveNonVisibility> reasondata_visibility = new ArrayList<>();
    PerformanceAdapter performanceAdapter;
    RecyclerView listview;
    private ArrayAdapter<CharSequence> reason_adapter;
    private ArrayAdapter<CharSequence> reason_adapter_visibility;
    UploadImageWithRetrofit upload;
    private EditText edt_store_name, edt_city;
    private ImageView img_shop_board, img_long_shot, img_close_shot;
    private Spinner spinner, spinner_reasion;
    FloatingActionButton fab;
    NestleDb db;
    Context context;
    private SharedPreferences preferences;
    private String image_shop = "", image_long = "", image_close = "";
    String reasonname = "", reasonid = "", Ispresent = "", Ispresent_visibility = "";
    String reasonname_visibility = "", reasonid_visiblity = "";
    SharedPreferences.Editor editor;
    String _UserId, date, app_ver, rightname;
    String SecurityToken, designation;
    String error_msg = "", msg_str = "";
    String _pathforcheck, _path;
    AlertDialog alert;
    ArrayList<ViisbilityDriveGetterSetter> performance_list = new ArrayList<>();
    LinearLayout lay_image_log_close, lay_lon_close;
    ImageView img_button_yes, img_button_no;
    ImageView img_button_visibility_yes, img_button_visibility_no;
    boolean ischangedflag = false, yes_flag = false, check_flag = true;
    boolean ischangedflag_visibility = false, yes_flag_visibility = false, check_flag_visibility = true;
    LinearLayout lay_reasion, lay_visibilty_drive, lay_photo_allow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visibility_drive);
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        _UserId = preferences.getString(CommonString.KEY_USERNAME, "");
        date = preferences.getString(CommonString.KEY_DATE, null);
        SecurityToken = preferences.getString(CommonString.KEY_SecurityToken, "");
        designation = preferences.getString(CommonString.KEY_DESIGNATION, "");
        rightname = preferences.getString(CommonString.KEY_RIGHTNAME, null);
        toolbar.setTitleTextAppearance(context, R.style.changestext_sizefor_mobile);

        fab = findViewById(R.id.fab);
        lay_image_log_close = (LinearLayout) findViewById(R.id.lay_image_log_close);
        lay_lon_close = (LinearLayout) findViewById(R.id.lay_lon_close);
        listview = (RecyclerView) findViewById(R.id.listview);
        lay_photo_allow = (LinearLayout) findViewById(R.id.lay_photo_allow);
        lay_visibilty_drive = (LinearLayout) findViewById(R.id.lay_visibilty_drive);
        lay_reasion = (LinearLayout) findViewById(R.id.lay_reasion);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner_reasion = (Spinner) findViewById(R.id.spinner_reasion);
        img_button_yes = (ImageView) findViewById(R.id.img_button_yes);
        img_button_visibility_yes = (ImageView) findViewById(R.id.img_button_visibility_yes);
        img_button_visibility_no = (ImageView) findViewById(R.id.img_button_visibility_no);
        img_button_no = (ImageView) findViewById(R.id.img_button_no);
        img_shop_board = (ImageView) findViewById(R.id.img_shop_board);
        img_long_shot = (ImageView) findViewById(R.id.img_long_shot);
        img_close_shot = (ImageView) findViewById(R.id.img_close_shot);
        edt_store_name = (EditText) findViewById(R.id.edt_store_name);
        edt_city = (EditText) findViewById(R.id.edt_city);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        db = new NestleDb(context);
        db.open();
        upload = new UploadImageWithRetrofit(context);
        setTitle("Visibility Drive - " + date);
        fab.setOnClickListener(this);
        img_shop_board.setOnClickListener(this);
        img_long_shot.setOnClickListener(this);
        img_close_shot.setOnClickListener(this);
        img_button_yes.setOnClickListener(this);
        img_button_no.setOnClickListener(this);
        img_button_visibility_yes.setOnClickListener(this);
        img_button_visibility_no.setOnClickListener(this);


        lay_visibilty_drive.setVisibility(View.GONE);
        lay_photo_allow.setVisibility(View.GONE);
        lay_reasion.setVisibility(View.GONE);


        performance_list = db.getStoreChekingSup(date);
        if (performance_list.size() > 0) {
            performanceAdapter = new PerformanceAdapter(getApplicationContext(), performance_list);
            listview.setAdapter(performanceAdapter);
            listview.setLayoutManager(new LinearLayoutManager(this));
        }

        reasondata = db.getVisibilityDriveData();
        reason_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        reason_adapter.add(getResources().getString(R.string.select_reason));
        for (int i = 0; i < reasondata.size(); i++) {
            reason_adapter.add(reasondata.get(i).getPosmName());
        }
        spinner.setAdapter(reason_adapter);
        reason_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(this);

        reasondata_visibility = db.getMasterDriveNonVisibilityData();
        reason_adapter_visibility = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        reason_adapter_visibility.add(getResources().getString(R.string.select_reason));
        for (int i = 0; i < reasondata_visibility.size(); i++) {
            reason_adapter_visibility.add(reasondata_visibility.get(i).getReason());
        }
        spinner_reasion.setAdapter(reason_adapter_visibility);
        reason_adapter_visibility.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_reasion.setOnItemSelectedListener(this);
    }

    /* public boolean validation() {
         boolean value = true;
         if (edt_store_name.getText().toString().equalsIgnoreCase("")) {
             value = false;
             AlertandMessages.showToastMsg(context, "Please Enter Store Name");
         } else if (edt_city.getText().toString().equalsIgnoreCase("")) {
             value = false;
             AlertandMessages.showToastMsg(context, "Please Enter City");
         } else if (image_shop.equals("")) {
             value = false;
             AlertandMessages.showToastMsg(context, "Please click image with shop board");
         } else if (reasonid != null && reasonid.equals("")) {
             value = false;
             AlertandMessages.showToastMsg(context, "Please Select Visibility Drive Name");
         } else if (Ispresent.equals("")) {
             value = false;
             AlertandMessages.showToastMsg(context, "Please Select Photo Allowed");
         } else if (Ispresent.equals("1")) {
             if (image_long.equals("")) {
                 value = false;
                 AlertandMessages.showToastMsg(context, "Please click image long shot");
             } else if (image_close.equals("")) {
                 value = false;
                 AlertandMessages.showToastMsg(context, "Please click image close shot");
             } else {
                 value = true;
             }
         } else {
             value = true;
         }

         return value;
     }*/
    public boolean validation() {
        boolean value = true;
        if (edt_store_name.getText().toString().equalsIgnoreCase("")) {
            value = false;
            AlertandMessages.showToastMsg(context, "Please Enter Store Name");
        } else if (edt_city.getText().toString().equalsIgnoreCase("")) {
            value = false;
            AlertandMessages.showToastMsg(context, "Please Enter City");
        } else if (image_shop.equals("")) {
            value = false;
            AlertandMessages.showToastMsg(context, "Please click image with shop board");
        } else if (Ispresent_visibility.equals("")) {
            value = false;
            AlertandMessages.showToastMsg(context, "Please Select Visbility Deployed");
        } else if (Ispresent_visibility.equals("1")) {
            if (reasonid != null && reasonid.equals("")) {
                value = false;
                AlertandMessages.showToastMsg(context, "Please Select Visibility Drive Name");
            } else if (Ispresent.equals("")) {
                value = false;
                AlertandMessages.showToastMsg(context, "Please Select Photo Allowed");
            } else if (Ispresent.equals("1")) {
                if (image_long.equals("")) {
                    value = false;
                    AlertandMessages.showToastMsg(context, "Please click image long shot");
                } else if (image_close.equals("")) {
                    value = false;
                    AlertandMessages.showToastMsg(context, "Please click image close shot");
                } else {
                    value = true;
                }
            } else {
                value = true;
            }
        } else if (reasonid_visiblity != null && reasonid_visiblity.equals("")) {
            value = false;
            AlertandMessages.showToastMsg(context, "Please Select Reasion");
        } else {
            value = true;
        }
        return value;
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.fab:
                if (checkNetIsAvailable()) {
                    if (validation()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(getString(R.string.main_menu_activity_name));
                        builder.setMessage(R.string.alertsaveData)
                                .setCancelable(false)
                                .setPositiveButton(R.string.ok,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int id) {
                                                alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                                                ArrayList<ViisbilityDriveGetterSetter> coverageBeanList = new ArrayList<>();
                                                ViisbilityDriveGetterSetter cdata = new ViisbilityDriveGetterSetter();
                                                cdata.setUserd(_UserId);
                                                cdata.setVisitdate(date);
                                                cdata.setStorename(edt_store_name.getText().toString().trim().replaceAll("[(!@#$%^&*?)\"]", ""));
                                                cdata.setCity(edt_city.getText().toString().trim().replaceAll("[(!@#$%^&*?)\"]", ""));
                                                cdata.setMagic_stick_name(reasonname);
                                                cdata.setMagic_stick_id(reasonid);
                                                cdata.setImage_shop_board(image_shop);
                                                cdata.setImage_long(image_long);
                                                cdata.setImage_close(image_close);
                                                cdata.setPresent(Ispresent);
                                                cdata.setPresent_deployed(Ispresent_visibility);
                                                cdata.setVisibility_deployed_name(reasonname_visibility);
                                                cdata.setVisibility_deployed_id(reasonid_visiblity);
                                                coverageBeanList.add(cdata);
                                                db.open();
                                                new GetCredentials(coverageBeanList).execute();
                                            }
                                        })
                                .setNegativeButton(R.string.closed,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int id) {
                                                dialog.cancel();
                                            }
                                        });

                        alert = builder.create();
                        alert.show();

                    }
                } else {
                    AlertandMessages.showToastMsg(context, getString(R.string.nonetwork));
                }

                break;

            case R.id.img_shop_board:
                _pathforcheck = "SHOP_BOARD_IMG_" + getCurrentTime().replace(":", "") + ".jpg";
                _path = CommonString.FILE_PATH + _pathforcheck;
                CommonFunctions.startAnncaCameraActivity(context, _path, null, false, CommonString.CAMERA_FACE_REAR);

                break;

            case R.id.img_long_shot:
                _pathforcheck = "LONG_SHOT_IMG" + getCurrentTime().replace(":", "") + ".jpg";
                _path = CommonString.FILE_PATH + _pathforcheck;
                CommonFunctions.startAnncaCameraActivity(context, _path, null, true, CommonString.CAMERA_FACE_REAR);

                break;
            case R.id.img_close_shot:
                _pathforcheck = "CLOSE_SHOT_IMG_" + getCurrentTime().replace(":", "") + ".jpg";
                _path = CommonString.FILE_PATH + _pathforcheck;
                CommonFunctions.startAnncaCameraActivity(context, _path, null, false, CommonString.CAMERA_FACE_REAR);

                break;
            case R.id.img_button_yes:
                ischangedflag = true;
                yes_flag = true;
                Ispresent = "1";
                img_button_yes.setImageResource(R.mipmap.yes_green);
                img_button_no.setImageResource(R.mipmap.no_white);
                lay_image_log_close.setVisibility(View.VISIBLE);
                lay_lon_close.setVisibility(View.VISIBLE);
                boolean for_refresh = true;
                break;

            case R.id.img_button_visibility_yes:
                ischangedflag_visibility = true;
                yes_flag_visibility = true;
                Ispresent_visibility = "1";
                img_button_visibility_yes.setImageResource(R.mipmap.yes_green);
                img_button_visibility_no.setImageResource(R.mipmap.no_white);
                lay_visibilty_drive.setVisibility(View.VISIBLE);
                lay_photo_allow.setVisibility(View.VISIBLE);
                lay_reasion.setVisibility(View.GONE);

                break;

            case R.id.img_button_visibility_no:
                db.open();
                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context).setTitle(R.string.main_menu_activity_name).setMessage(getString(R.string.messageM));
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clear_not_present_visibility();
                        ischangedflag_visibility = true;
                        dialog.dismiss();
                        image_long = "";
                        image_close = "";
                        img_long_shot.setImageResource(R.mipmap.camera_orange);
                        img_close_shot.setImageResource(R.mipmap.camera_orange);
                        lay_reasion.setVisibility(View.VISIBLE);
                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ischangedflag_visibility = true;
                        if (yes_flag_visibility) {
                            img_button_visibility_yes.setImageResource(R.mipmap.yes_green);
                            img_button_visibility_no.setImageResource(R.mipmap.no_white);
                            lay_reasion.setVisibility(View.GONE);
                            lay_visibilty_drive.setVisibility(View.VISIBLE);
                            lay_photo_allow.setVisibility(View.VISIBLE);

                        } else {
                            img_button_visibility_yes.setImageResource(R.mipmap.yes_white);
                            img_button_visibility_no.setImageResource(R.mipmap.no_white);
                            lay_reasion.setVisibility(View.GONE);
                            lay_visibilty_drive.setVisibility(View.GONE);
                            lay_photo_allow.setVisibility(View.GONE);

                        }
                    }
                });

                builder.show();
                break;

            case R.id.img_button_no:
                db.open();
                final android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(context).setTitle(R.string.main_menu_activity_name).setMessage(getString(R.string.messageM));
                builder1.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clear_not_present();
                        ischangedflag = true;
                        dialog.dismiss();
                        image_long = "";
                        image_close = "";
                        img_long_shot.setImageResource(R.mipmap.camera_orange);
                        img_close_shot.setImageResource(R.mipmap.camera_orange);
                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ischangedflag = true;
                        if (yes_flag) {
                            img_button_yes.setImageResource(R.mipmap.yes_green);
                            img_button_no.setImageResource(R.mipmap.no_white);
                            lay_image_log_close.setVisibility(View.VISIBLE);
                            lay_lon_close.setVisibility(View.VISIBLE);

                        } else {
                            img_button_yes.setImageResource(R.mipmap.yes_white);
                            img_button_no.setImageResource(R.mipmap.no_white);
                            lay_image_log_close.setVisibility(View.GONE);
                            lay_lon_close.setVisibility(View.GONE);

                        }

                    }
                });

                builder1.show();
                break;
        }
    }

    class GetCredentials extends AsyncTask<Void, Void, String> {
        boolean uploadflag;
        private ProgressDialog dialog = null;
        private ArrayList<ViisbilityDriveGetterSetter> attdata;
        String errormsg = "";
        private Context context;
        private ProgressDialog pdialog = null;

        GetCredentials(ArrayList<ViisbilityDriveGetterSetter> attendenceData) {
            this.attdata = attendenceData;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = new ProgressDialog(VisibilityDriveActivity.this);
            dialog.setTitle("My Attendance");
            dialog.setMessage("Fetching....");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub
            msg_str = "";
            uploadflag = false;
            String result = null;
            UploadImageWithRetrofit uploadRetro = new UploadImageWithRetrofit(context);
            try {
                if (attdata.size() > 0) {
                    JSONArray topUpArray = new JSONArray();
                    for (int j = 0; j < attdata.size(); j++) {
                        JSONObject obj = new JSONObject();
                        obj.put("VisitDate", attdata.get(j).getVisitdate());
                        obj.put("StoreName", attdata.get(j).getStorename());
                        obj.put("City", attdata.get(j).getCity());
                        obj.put("ShopBoardImage", attdata.get(j).getImage_shop_board());
                        obj.put("MagicStickId", attdata.get(j).getMagic_stick_id());
                        obj.put("Present", attdata.get(j).getPresent());
                        obj.put("LongShotImage", attdata.get(j).getImage_long());
                        obj.put("CloseShotImage", attdata.get(j).getImage_close());
                        obj.put("PresentVisibilityDeployed", attdata.get(j).getPresent_deployed());
                        obj.put("ResionId", attdata.get(j).getVisibility_deployed_id());
                        obj.put("UserName", _UserId);
                        topUpArray.put(obj);
                    }

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("MID", "0");
                    jsonObject.put("Keys", "Visibility_Drive_Data_New");
                    jsonObject.put("JsonData", topUpArray.toString());
                    jsonObject.put("UserName", _UserId);
                    jsonObject.put("SecurityToken", SecurityToken);
                    String jsonString2 = jsonObject.toString();
                    result = upload.downloadDataUniversal(jsonString2, CommonString.UPLOADJsonDetail);

                    if (result.equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                        uploadflag = false;
                        throw new SocketTimeoutException();
                    } else if (result.equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                        uploadflag = false;
                        throw new IOException();
                    } else if (result.equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                        uploadflag = false;
                        throw new JsonSyntaxException("Primary_Grid_Image");
                    } else if (result.equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                        uploadflag = false;
                        throw new Exception();
                    } else {
                        uploadflag = true;
                    }
                }
                //usk
                if (result.contains(CommonString.KEY_SUCCESS)) {
                    if (attdata.get(0).getImage_shop_board() != null && !attdata.get(0).getImage_shop_board().equals("")) {
                        if (new File(CommonString.FILE_PATH + attdata.get(0).getImage_shop_board()).exists()) {
                            //  result = uploadRetro.UploadImage2(attdata.get(0).getImage_shop_board(), "VisibilityDriveImages", CommonString.FILE_PATH,_UserId,SecurityToken);
                            uploadRetro.UploadImageRecursive(context, date);
                            if (result.equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                                throw new SocketTimeoutException();
                            } else if (result.equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                                throw new IOException();
                            } else if (result.equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                                throw new JsonSyntaxException("Attendance_Image");
                            } else if (result.equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                throw new Exception();
                            } else {
                                result = CommonString.KEY_SUCCESS;
                            }
                        }
                    }

                }

                if (result == CommonString.KEY_SUCCESS) {
                    return CommonString.KEY_SUCCESS;
                } else {
                    return "";
                }

            } catch (SocketException ex) {
                uploadflag = false;
                ex.printStackTrace();
                errormsg = CommonString.MESSAGE_INTERNET_NOT_AVALABLE;
            } catch (IOException ex) {
                uploadflag = false;
                ex.printStackTrace();
                errormsg = CommonString.MESSAGE_INTERNET_NOT_AVALABLE;
            } catch (JsonSyntaxException ex) {
                uploadflag = false;
                ex.printStackTrace();
                errormsg = CommonString.MESSAGE_INVALID_JSON;
            } catch (NumberFormatException e) {
                uploadflag = false;
                errormsg = CommonString.MESSAGE_NUMBER_FORMATE_EXEP;
            } catch (Exception ex) {
                uploadflag = false;
                errormsg = CommonString.MESSAGE_EXCEPTION;
            }

            if (uploadflag) {
                return CommonString.KEY_SUCCESS;
            } else {
                return errormsg;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            dialog.dismiss();
            if (result.equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                if (db.InsertVisibilityDriveData(attdata) > 0)
                    performance_list = db.getStoreChekingSup(date);

                if (performance_list.size() > 0) {
                    performanceAdapter = new PerformanceAdapter(getApplicationContext(), performance_list);
                    listview.setAdapter(performanceAdapter);
                    listview.setLayoutManager(new LinearLayoutManager(VisibilityDriveActivity.this));
                }

                showAlert(getString(R.string.visibility_drive));
            } else {
                Snackbar.make(fab, getResources().getString(R.string.data_not_uploaded), Snackbar.LENGTH_LONG).show();

            }

        }

    }

    public void showAlert(String str) {

        AlertDialog.Builder builder = new AlertDialog.Builder(VisibilityDriveActivity.this);
        builder.setTitle("Parinaam");
        builder.setMessage(str).setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public String getCurrentTime() {
        Calendar m_cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String cdate = formatter.format(m_cal.getTime());
        return cdate;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("MakeMachine", "resultCode: " + resultCode);
        switch (resultCode) {
            case 0:
                Log.i("MakeMachine", "User cancelled");
                break;
            case -1:
                if (_pathforcheck != null && !_pathforcheck.equals("")) {
                    try {
                        if (new File(CommonString.FILE_PATH + _pathforcheck).exists()) {
                            if (_pathforcheck.contains("SHOP_BOARD_IMG_")) {
                                img_shop_board.setImageResource(R.mipmap.camera_green);
                                image_shop = _pathforcheck;

                            } else if (_pathforcheck.contains("LONG_SHOT_IMG")) {
                                img_long_shot.setImageResource(R.mipmap.camera_green);
                                image_long = _pathforcheck;

                            } else if (_pathforcheck.contains("CLOSE_SHOT_IMG_")) {
                                img_close_shot.setImageResource(R.mipmap.camera_green);
                                image_close = _pathforcheck;

                            }
                            _pathforcheck = "";
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                               long arg3) {
        // TODO Auto-generated method stub

        switch (arg0.getId()) {
            case R.id.spinner:
                if (position != 0) {
                    reasonname = reasondata.get(position - 1).getPosmName();
                    reasonid = reasondata.get(position - 1).getPosmId().toString();

                } else {
                    reasonname = "";
                    reasonid = "";
                }
                break;

            case R.id.spinner_reasion:
                if (position != 0) {
                    reasonname_visibility = reasondata_visibility.get(position - 1).getReason();
                    reasonid_visiblity = reasondata_visibility.get(position - 1).getReasonId().toString();

                } else {
                    reasonname_visibility = "";
                    reasonid_visiblity = "";
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public class PerformanceAdapter extends RecyclerView.Adapter<PerformanceAdapter.MyViewHolder> {
        private LayoutInflater inflator;
        List<ViisbilityDriveGetterSetter> data = Collections.emptyList();

        public PerformanceAdapter(Context context, List<ViisbilityDriveGetterSetter> data) {
            inflator = LayoutInflater.from(context);
            this.data = data;
        }

        @Override
        public PerformanceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            View view = inflator.inflate(R.layout.item__visibility_drive, parent, false);
            PerformanceAdapter.MyViewHolder holder = new PerformanceAdapter.MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final PerformanceAdapter.MyViewHolder viewHolder, final int position) {

            final ViisbilityDriveGetterSetter current = data.get(position);

            viewHolder.txt_stotre_name.setText("Store Name : " + String.valueOf(current.getStorename()));
            viewHolder.txt_city.setText("City : " + String.valueOf(current.getCity()));
            viewHolder.txt_magic.setText("Drive Name : " + String.valueOf(current.getMagic_stick_name()));

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView txt_stotre_name, txt_city, txt_magic;

            public MyViewHolder(View itemView) {
                super(itemView);
                txt_stotre_name = (TextView) itemView.findViewById(R.id.txt_stotre_name);
                txt_city = (TextView) itemView.findViewById(R.id.txt_city);
                txt_magic = (TextView) itemView.findViewById(R.id.txt_magic);

            }
        }
    }

    private boolean checkNetIsAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private void clear_not_present() {
        try {
            Ispresent = "0";
            yes_flag = false;
            img_button_no.setImageResource(R.mipmap.no_red);
            img_button_yes.setImageResource(R.mipmap.yes_white);
            lay_image_log_close.setVisibility(View.GONE);
            lay_lon_close.setVisibility(View.GONE);
            boolean for_refresh = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clear_not_present_visibility() {
        try {
            Ispresent_visibility = "0";
            yes_flag_visibility = false;
            img_button_visibility_no.setImageResource(R.mipmap.no_red);
            img_button_visibility_yes.setImageResource(R.mipmap.yes_white);
            lay_visibilty_drive.setVisibility(View.GONE);
            lay_photo_allow.setVisibility(View.GONE);


            boolean for_refresh = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}