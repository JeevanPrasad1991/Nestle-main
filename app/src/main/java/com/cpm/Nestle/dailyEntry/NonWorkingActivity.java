package com.cpm.Nestle.dailyEntry;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.cpm.Nestle.database.NestleDb;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.cpm.Nestle.R;
import com.cpm.Nestle.delegates.CoverageBean;
import com.cpm.Nestle.getterSetter.MappingJourneyPlan;
import com.cpm.Nestle.getterSetter.NonWorkingReason;
import com.cpm.Nestle.upload.Retrofit_method.UploadImageWithRetrofit;
import com.cpm.Nestle.upload.Retrofit_method.upload.UploadWithoutWaitActivity;
import com.cpm.Nestle.utilities.AlertandMessages;
import com.cpm.Nestle.utilities.CommonFunctions;
import com.cpm.Nestle.utilities.CommonString;
//import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class NonWorkingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    ArrayList<NonWorkingReason> reasondata = new ArrayList<>();
    private Spinner reasonspinner;
    private NestleDb db;
    String reasonname = "", reasonid = "", entry_allow = "", image_allow = "",tag_from = "", inTime = "",_pathforcheck = "",image1 = "",
            _UserId, visit_date, app_ver = "", store_id = "", SecurityToken, designation,_path;
    FloatingActionButton save;
    private ArrayAdapter<CharSequence> reason_adapter;
    private SharedPreferences preferences;
    protected boolean status = true;
    AlertDialog alert;
    ImageView camera;
    RelativeLayout rel_cam;
    ArrayList<MappingJourneyPlan> jcp;
    boolean update_flag = false;
    GoogleApiClient mGoogleApiClient;
    double lat = 0.0, lon = 0.0;
    Context context;
    ArrayList<MappingJourneyPlan> storelist;
    ArrayList<MappingJourneyPlan> journeyPlan;
    boolean nonflag;
    Integer mid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_working);
        declaration();
        inTime = CommonFunctions.getCurrentTime();
        db.open();
        if (getIntent().getStringExtra(CommonString.TAG_FROM) != null) {
            tag_from = getIntent().getStringExtra(CommonString.TAG_FROM);
        }


        storelist = db.getStoreData(visit_date);
        journeyPlan = db.getSpecificStoreData(null, store_id);

        for (int i = 0; i < storelist.size(); i++) {
            if (!storelist.get(i).getUploadStatus().equalsIgnoreCase(CommonString.KEY_N)) {
                nonflag = true;
                break;
            } else {
                nonflag = false;
            }
        }

        if (nonflag) {
            reasondata = db.getNonWorkingEntryAllowData();
        } else {
            reasondata = db.getNonWorkingData();
        }

        reason_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        reason_adapter.add(getResources().getString(R.string.select_reason));
        for (int i = 0; i < reasondata.size(); i++) {
            reason_adapter.add(reasondata.get(i).getReason());
        }
        reasonspinner.setAdapter(reason_adapter);
        reason_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reasonspinner.setOnItemSelectedListener(this);

        reason_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _pathforcheck = store_id + "_NonworkImg-" + visit_date.replace("/", "") + "_" + getCurrentTime().replace(":", "") + ".jpg";
                _path = CommonString.FILE_PATH + _pathforcheck;
                CommonFunctions.startAnncaCameraActivity(context, _path, null, false, CommonString.CAMERA_FACE_REAR);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validatedata()) {
                    if (imageAllowed()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(getString(R.string.main_menu_activity_name));
                        builder.setMessage(R.string.alertsaveData)
                                .setCancelable(false)
                                .setPositiveButton(R.string.ok,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int id) {
                                                alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                                                getMid(store_id);
                                                if (entry_allow.equalsIgnoreCase("false")) {
                                                    CoverageBean cdata = new CoverageBean();
                                                    cdata.setStoreId(store_id);
                                                    cdata.setVisitDate(visit_date);
                                                    cdata.setUserId(_UserId);
                                                    cdata.setReason(reasonname);
                                                    cdata.setReasonid(reasonid);
                                                    cdata.setLatitude(String.valueOf(lat));
                                                    cdata.setLongitude(String.valueOf(lon));
                                                    cdata.setImage(image1);
                                                    cdata.setCkeckout_image(image1);
                                                    cdata.setRemark("");
                                                    cdata.setStatus(CommonString.KEY_U);
                                                    new CoverageNonWorkingUpload(cdata).execute();

                                                } else {
                                                    CoverageBean cdata = new CoverageBean();
                                                    cdata.setStoreId(store_id);
                                                    cdata.setVisitDate(visit_date);
                                                    cdata.setUserId(_UserId);
                                                    cdata.setReason(reasonname);
                                                    cdata.setReasonid(reasonid);
                                                    cdata.setLatitude(String.valueOf(lat));
                                                    cdata.setLongitude(String.valueOf(lon));
                                                    cdata.setImage(image1);
                                                    cdata.setRemark("");
                                                    cdata.setStatus(CommonString.STORE_STATUS_LEAVE);
                                                    if (mid != null) {
                                                        if (CommonFunctions.checkNetIsAvailable(context)) {
                                                            if (db.InsertCoverageData(cdata) > 0) {
                                                                if (db.updateStoreStatusOnLeave(store_id, visit_date, CommonString.STORE_STATUS_LEAVE, designation) > 0) {
                                                                    new GeoTagUpload(cdata).execute();
                                                                } else {
                                                                    AlertandMessages.showToastMsg(context, "Store status not updated!!");
                                                                }
                                                            } else {
                                                                AlertandMessages.showToastMsg(context, "Coverage not saved!!");
                                                            }
                                                        } else {
                                                            AlertandMessages.showToastMsg(context, getString(R.string.nonetwork));
                                                        }
                                                    } else {
                                                        Snackbar.make(save, CommonString.ba_message, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                                                    }

                                                }

                                                //  finish();
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

                    } else {
                        Toast.makeText(getApplicationContext(), R.string.clickimage, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), R.string.please_select_reason, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                               long arg3) {
        // TODO Auto-generated method stub

        switch (arg0.getId()) {
            case R.id.spinner2:
                if (position != 0) {
                    reasonname = reasondata.get(position - 1).getReason();
                    reasonid = reasondata.get(position - 1).getReasonId().toString();
                    entry_allow = reasondata.get(position - 1).getEntryAllow().toString();
                    image_allow = reasondata.get(position - 1).getImageAllow().toString();
                    if (image_allow.equalsIgnoreCase("true")) {
                        rel_cam.setVisibility(View.VISIBLE);
                    } else {
                        rel_cam.setVisibility(View.GONE);
                    }
                } else {
                    reasonname = "";
                    reasonid = "";
                    image_allow = "";
                    entry_allow = "";
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("MakeMachine", "resultCode: " + resultCode);
        switch (resultCode) {
            case 0:
                Log.i("MakeMachine", "User cancelled");
                break;
            case -1:
                if (_pathforcheck != null && !_pathforcheck.equals("")) {
                    try {
                        if (new File(CommonString.FILE_PATH + _pathforcheck).exists()) {
                            Bitmap bmp = BitmapFactory.decodeFile(CommonString.FILE_PATH + _pathforcheck);
                            Bitmap dest = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);
                            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                            String dateTime = sdf.format(Calendar.getInstance().getTime()); // reading local time in the system

                            Canvas cs = new Canvas(dest);
                            Paint tPaint = new Paint();
                            tPaint.setTextSize(70);
                            tPaint.setColor(Color.RED);
                            tPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                            cs.drawBitmap(bmp, 0f, 0f, null);
                            float height = tPaint.measureText("yY");
                            cs.drawText(dateTime, 20f, height + 15f, tPaint);
                            try {
                                dest.compress(Bitmap.CompressFormat.JPEG, 100,
                                        new FileOutputStream(new File(CommonString.FILE_PATH + _pathforcheck)));
                            } catch (FileNotFoundException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            camera.setImageResource((R.mipmap.camera_green));
                            image1 = _pathforcheck;
                            _pathforcheck = "";
                        }
                    } catch (Resources.NotFoundException e) {
                        // Crashlytics.logException(e);
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    public boolean imageAllowed() {
        boolean result = true;
        if (image_allow.equalsIgnoreCase("true")) {
            if (image1.equals("")) {
                result = false;
            }
        }
        return result;
    }

    public boolean validatedata() {
        boolean result = false;
        if (reasonid != null && !reasonid.equals("")) {
            result = true;
        }
        return result;
    }


    public String getCurrentTime() {
        Calendar m_cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String cdate = formatter.format(m_cal.getTime());
        return cdate;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (!update_flag) {
                finish();
                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(CommonString.ONBACK_ALERT_MESSAGE)
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                dialog.dismiss();
                                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public class CoverageNonWorkingUpload extends AsyncTask<Void, Void, String> {

        private CoverageBean coverageBean;
        ProgressDialog dialog = null;
        boolean ResultFlag = false;
        String strflag;

        CoverageNonWorkingUpload(CoverageBean coverageBean) {
            this.coverageBean = coverageBean;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setTitle("Nonworkoing Data");
            dialog.setMessage("Uploading....");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                UploadImageWithRetrofit upload = new UploadImageWithRetrofit(context);
                JSONObject jsonObject;
                String jsonString2 = "", result = "5";
                //region Coverage Data
                jsonObject = new JSONObject();
                jsonObject.put("Mid", mid);
                jsonObject.put("StoreId", coverageBean.getStoreId());
                jsonObject.put("VisitDate", coverageBean.getVisitDate());
                jsonObject.put("Latitude", coverageBean.getLatitude());
                jsonObject.put("Longitude", coverageBean.getLongitude());
                jsonObject.put("ReasonId", coverageBean.getReasonid());
                jsonObject.put("Remark", coverageBean.getRemark());
                jsonObject.put("Appversion", app_ver);
                jsonObject.put("UploadStatus", CommonString.KEY_U);
                jsonObject.put("UserName", _UserId);
                jsonObject.put("SecurityToken", SecurityToken);
                jsonString2 = jsonObject.toString();
                result = upload.downloadDataUniversal(jsonString2, CommonString.COVERAGE_NONWORKING);
                if (result.equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                    throw new SocketTimeoutException();
                } else if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                    throw new JsonSyntaxException("non_working");
                } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
                } else {
                    int mid = 0;
                    try {
                        mid = Integer.parseInt(result);
                        if (mid > 0) {
                            ResultFlag = true;
                        }

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        throw new NumberFormatException();
                    }

                }


            } catch (MalformedURLException e) {
                ResultFlag = false;
                strflag = CommonString.MESSAGE_EXCEPTION;
            } catch (SocketTimeoutException e) {
                ResultFlag = false;
                strflag = CommonString.MESSAGE_SOCKETEXCEPTION;
            } catch (InterruptedIOException e) {
                ResultFlag = false;
                strflag = CommonString.MESSAGE_EXCEPTION;
            } catch (NumberFormatException e) {
                ResultFlag = false;
                strflag = CommonString.MESSAGE_NUMBER_FORMATE_EXEP;
            } catch (IOException e) {
                ResultFlag = false;
                strflag = CommonString.MESSAGE_SOCKETEXCEPTION;
            } catch (XmlPullParserException e) {
                ResultFlag = false;
                strflag = CommonString.MESSAGE_XmlPull;
            } catch (Exception e) {
                ResultFlag = false;
                strflag = CommonString.MESSAGE_EXCEPTION;
            }
            if (ResultFlag) {
                return CommonString.KEY_SUCCESS;
            } else {
                return strflag;
            }

        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if (result.equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                jcp = db.getStoreData(visit_date);
                for (int i = 0; i < jcp.size(); i++) {
                    CoverageBean inoutTimeob = new CoverageBean();
                    inoutTimeob.setStoreId("" + jcp.get(i).getStoreId());
                    inoutTimeob.setVisitDate(jcp.get(i).getVisitDate());
                    inoutTimeob.setJcp_type(tag_from);
                    inoutTimeob.setIntime(inTime);
                    inoutTimeob.setOutTime(CommonFunctions.getCurrentTime());
                    db.open();
                    db.InsertIntimeOutTime(inoutTimeob);

                    String storeid = String.valueOf(jcp.get(i).getStoreId());
                    db.open();
                    if (db.updateStoreStatusOnLeave(storeid, visit_date, CommonString.KEY_U, designation) > 0) {
                    } else {
                        AlertandMessages.showSnackbarMsg(context, "Store status not updated!!");
                        break;
                    }
                }

                dialog.dismiss();
                finish();
            } else {
                AlertandMessages.showAlert((Activity) context, getString(R.string.datanotfound) + " " + result, false);
            }
        }

    }


    public class GeoTagUpload extends AsyncTask<Void, Void, String> {

        private CoverageBean coverageBeanList;
        ProgressDialog dialog = null;
        boolean ResultFlag = true;
        String strflag = "";

        GeoTagUpload(CoverageBean coverageBeanList) {
            this.coverageBeanList = coverageBeanList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setTitle("NonWorking Data");
            dialog.setMessage("Uploading....");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                UploadImageWithRetrofit upload = new UploadImageWithRetrofit(context);
                JSONObject jsonObject;
                String jsonString2 = "", result = "5";
                //region Coverage Data
                jsonObject = new JSONObject();
                jsonObject.put("Mid", mid);
                jsonObject.put("StoreId", coverageBeanList.getStoreId());
                jsonObject.put("VisitDate", coverageBeanList.getVisitDate());
                jsonObject.put("Latitude", coverageBeanList.getLatitude());
                jsonObject.put("Longitude", coverageBeanList.getLongitude());
                jsonObject.put("ReasonId", coverageBeanList.getReasonid());
                jsonObject.put("Remark", coverageBeanList.getRemark());
                jsonObject.put("ImageName", coverageBeanList.getImage());
                jsonObject.put("AppVersion", app_ver);
                jsonObject.put("UploadStatus", CommonString.STORE_STATUS_LEAVE);
                jsonObject.put("CheckoutImage", coverageBeanList.getImage());
                jsonObject.put("UserName", _UserId);
                jsonObject.put("SecurityToken", SecurityToken);
                jsonString2 = jsonObject.toString();
                result = upload.downloadDataUniversal(jsonString2, CommonString.COVERAGE_DETAIL);

                if (result.equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                    throw new SocketTimeoutException();
                } else if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                    throw new JsonSyntaxException("non_working");
                } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
                } else {
                    int mid = 0;
                    try {
                        mid = Integer.parseInt(result);
                        if (mid > 0) {
                            ResultFlag = true;
                        }

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        throw new NumberFormatException();
                    }

                }


            } catch (MalformedURLException e) {
                ResultFlag = false;
                strflag = CommonString.MESSAGE_EXCEPTION;
            } catch (SocketTimeoutException e) {
                ResultFlag = false;
                strflag = CommonString.MESSAGE_SOCKETEXCEPTION;
            } catch (InterruptedIOException e) {
                ResultFlag = false;
                strflag = CommonString.MESSAGE_EXCEPTION;
            } catch (NumberFormatException e) {
                ResultFlag = false;
                strflag = CommonString.MESSAGE_NUMBER_FORMATE_EXEP;
            } catch (IOException e) {
                ResultFlag = false;
                strflag = CommonString.MESSAGE_SOCKETEXCEPTION;
            } catch (XmlPullParserException e) {
                ResultFlag = false;
                strflag = CommonString.MESSAGE_XmlPull;
            } catch (Exception e) {
                ResultFlag = false;
                strflag = CommonString.MESSAGE_EXCEPTION;
            }
            if (ResultFlag) {
                return CommonString.KEY_SUCCESS;
            } else {
                return strflag;
            }

        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if (result.equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                CoverageBean inoutTimeob = new CoverageBean();
                inoutTimeob.setStoreId("" + coverageBeanList.getStoreId());
                inoutTimeob.setVisitDate(coverageBeanList.getVisitDate());
                inoutTimeob.setJcp_type(tag_from);
                inoutTimeob.setIntime(inTime);
                inoutTimeob.setOutTime(CommonFunctions.getCurrentTime());
                db.open();
                db.InsertIntimeOutTime(inoutTimeob);
                dialog.dismiss();
                Intent in = new Intent(context, UploadWithoutWaitActivity.class);
                startActivity(in);
                finish();
            } else {
                dialog.dismiss();
                db.open();
                db.deleteTableWithStoreID(store_id);
                db.open();
                db.updateStoreStatus(store_id, visit_date, CommonString.KEY_N);
                AlertandMessages.showAlert((Activity) context, getString(R.string.datanotfound) + " " + result, false);
            }
        }

    }


    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            lat = mLastLocation.getLatitude();
            lon = mLastLocation.getLongitude();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        db.open();
        getMid(store_id);

    }


    void declaration() {
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        reasonspinner = (Spinner) findViewById(R.id.spinner2);
        camera = (ImageView) findViewById(R.id.imgcam);
        save = (FloatingActionButton) findViewById(R.id.save);
        rel_cam = (RelativeLayout) findViewById(R.id.relimgcam);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        _UserId = preferences.getString(CommonString.KEY_USERNAME, "");
        designation = preferences.getString(CommonString.KEY_DESIGNATION, null);
        visit_date = preferences.getString(CommonString.KEY_DATE, "");
        SecurityToken = preferences.getString(CommonString.KEY_SecurityToken, "");
        store_id = (getIntent().getStringExtra(CommonString.KEY_STORE_ID));
        app_ver = preferences.getString(CommonString.KEY_VERSION, "");
        toolbar.setTitleTextAppearance(context, R.style.changestext_sizefor_mobile);
        getSupportActionBar().setTitle("Non Working -" + visit_date);
        db = new NestleDb(context);
    }

    void getMid(String storeCd) {
        db.open();
        ArrayList<MappingJourneyPlan> jcp = db.getStoreDataCheking(visit_date, storeCd, null, designation);
        if (jcp.size() > 0) {
            mid = jcp.get(0).getMID();

        }
    }

}
