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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cpm.Nestle.R;
import com.cpm.Nestle.database.NestleDb;
import com.cpm.Nestle.delegates.CoverageBean;
import com.cpm.Nestle.getterSetter.MappingAdhocJourneyPlan;
import com.cpm.Nestle.getterSetter.MappingJourneyPlan;
import com.cpm.Nestle.upload.Retrofit_method.UploadImageWithRetrofit;
import com.cpm.Nestle.utilities.AlertandMessages;
import com.cpm.Nestle.utilities.CommonFunctions;
import com.cpm.Nestle.utilities.CommonString;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by jeevanp on 01-09-2020.
 */
public class StoreimageActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    String tag_from = "";
    String table;
    int result_mid = 0;
    ImageView img_cam, img_clicked;
    FloatingActionButton btn_save;
    String store_id = "0", visit_date, visit_date_formatted, username, intime, date, app_ver, _pathforcheck, _path, str, SecurityToken, designation;
    private SharedPreferences preferences;
    AlertDialog alert;
    String img_str, strflag;
    private NestleDb database;
    double lat, lon;
    GoogleApiClient mGoogleApiClient;
    Toolbar toolbar;
    boolean ResultFlag = true;
    MappingJourneyPlan jcpGetset;
    LocationManager locationManager;
    boolean enabled;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private LocationRequest mLocationRequest;
    private static int UPDATE_INTERVAL = 500; // 5 sec
    private static int FATEST_INTERVAL = 100; // 1 sec
    private static int DISPLACEMENT = 5; // 10 meters
    private Location mLastLocation;
    Context context;
    boolean ischangedflag = false;
    Integer mid = 0;
    private static final String TAG = StoreimageActivity.class.getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storeimage);
        declaration();
        if (getIntent().getSerializableExtra(CommonString.TAG_OBJECT) != null) {
            jcpGetset = (MappingJourneyPlan) getIntent().getSerializableExtra(CommonString.TAG_OBJECT);
            store_id = jcpGetset.getStoreId().toString();
        }

        if (getIntent().getStringExtra(CommonString.TAG_FROM) != null) {
            tag_from = getIntent().getStringExtra(CommonString.TAG_FROM);
        }

        getMid();
        try {
            app_ver = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        img_cam.setOnClickListener(this);
        img_clicked.setOnClickListener(this);
        btn_save.setOnClickListener(this);

        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
            createLocationRequest();
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!enabled) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            // Setting Dialog Title
            alertDialog.setTitle(getResources().getString(R.string.gps));
            // Setting Dialog Message
            alertDialog.setMessage(getResources().getString(R.string.gpsebale));
            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton(getResources().getString(R.string.yes),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    });
            // Setting Negative "NO" Button
            alertDialog.setNegativeButton(getResources().getString(R.string.no),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event
                            dialog.cancel();
                        }
                    });
            // Showing Alert Message
            alertDialog.show();
        }

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
    }


    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, (Activity) context,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.notsuppoted)
                        , Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    protected void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    /**
     * Stopping location updates
     */
    protected void stopLocationUpdates() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (mLastLocation != null) {
                lat = mLastLocation.getLatitude();
                lon = mLastLocation.getLongitude();
            }
        }
        // if (mRequestingLocationUpdates) {
        startLocationUpdates();
        // }
        // startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }


    protected void onStart() {
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        //client.connect();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        // AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (ischangedflag) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(getString(R.string.main_menu_activity_name)).setMessage(CommonString.ONBACK_ALERT_MESSAGE).setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                                StoreimageActivity.this.finish();
                                dialog.dismiss();
                            }
                        }).setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                StoreimageActivity.this.finish();

            }

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (ischangedflag) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(getString(R.string.main_menu_activity_name)).setMessage(CommonString.ONBACK_ALERT_MESSAGE).setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                            StoreimageActivity.this.finish();
                            dialog.dismiss();
                        }
                    }).setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
            StoreimageActivity.this.finish();

        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_cam_selfie:
                if (designation.equalsIgnoreCase("DBSR")) {
                    if (CheckNetAvailability()) {
                        if (tag_from.equalsIgnoreCase(CommonString.TAG_FROM_JCP)) {
                            table = "JournyPlan_DBSR";
                        } else if (tag_from.equalsIgnoreCase(CommonString.TAG_FROM_NON_MERCHANDIZED)) {
                            table = "JourneyPlan_NonMerchandised";
                        } else if (tag_from.equalsIgnoreCase(CommonString.TAG_FROM_NOT_COVERED)) {
                            table = "JourneyPlan_NotCovered";
                        }

                        new GetTableMid().execute();
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.nonetwork), Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (tag_from.equalsIgnoreCase("from_jcp")) {
                        _pathforcheck = store_id + "_" + username.replace(".", "") + "_StoreImg-" + visit_date_formatted + "-" + CommonFunctions.getCurrentTimeHHMMSS() + ".jpg";
                        _path = CommonString.FILE_PATH + _pathforcheck;
                        intime = getCurrentTime();
                        CommonFunctions.startAnncaCameraActivity(context, _path, null, false,CommonString.CAMERA_FACE_FRONT);
                        ischangedflag = true;
                    } else {
                        if (CheckNetAvailability()) {
                            if (tag_from.equalsIgnoreCase(CommonString.TAG_FROM_JCP)) {
                                table = "Mapping_JourneyPlan";
                            } else if (tag_from.equalsIgnoreCase(CommonString.TAG_FROM_NON_MERCHANDIZED)) {
                                table = "JourneyPlan_NonMerchandised";
                            } else if (tag_from.equalsIgnoreCase(CommonString.TAG_FROM_NOT_COVERED)) {
                                table = "JourneyPlan_NotCovered";
                            }
                            new GetTableMid().execute();
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.nonetwork), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                break;
            case R.id.btn_save_selfie:
                if (img_str != null) {
                    if (mid != null) {
                        if (designation.equalsIgnoreCase("DBSR")) {
                            database.open();
                            database.insertJCP_DBSRSavedData(jcpGetset);
                        }

                        CoverageBean cdata = new CoverageBean();
                        cdata.setStoreId(store_id);
                        cdata.setVisitDate(visit_date);
                        cdata.setUserId(username);
                        cdata.setReason("");
                        cdata.setReasonid("0");
                        cdata.setLatitude(String.valueOf(lat));
                        cdata.setLongitude(String.valueOf(lon));
                        cdata.setImage(img_str);
                        cdata.setRemark("");
                        cdata.setCkeckout_image("");
                        cdata.setFlag_from(tag_from);
                        cdata.setMID(mid);

                        if (tag_from.equalsIgnoreCase(CommonString.TAG_FROM_JCP)) {
                            if (designation.equalsIgnoreCase("DBSR")) {
                                table = "Journey_Plan_DBSR_Saved";
                            } else {
                                table = "Mapping_JourneyPlan";
                            }
                        } else if (tag_from.equalsIgnoreCase(CommonString.TAG_FROM_NON_MERCHANDIZED)) {
                            table = "JourneyPlan_NonMerchandised";
                        } else if (tag_from.equals(CommonString.TAG_FROM_NOT_COVERED)) {
                            table = "JourneyPlan_NotCovered";
                        }
                        new GeoTagUpload(cdata).execute();
                    } else {
                        AlertandMessages.showToastMsg(context, "MID is null. Please Download Data again.");
                    }
                } else {
                    AlertandMessages.showToastMsg(context, getResources().getString(R.string.clickimage));
                }
                break;
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
                    if (new File(str + _pathforcheck).exists()) {
                        try {
                            convertBitmap(str + _pathforcheck);
                            String metadata = CommonFunctions.setMetadataAtImages(jcpGetset.getStoreName(), jcpGetset.getStoreId().toString(), "Store Image", username);
                            Bitmap bmp = CommonFunctions.addMetadataAndTimeStampToImage(context, _path, metadata, jcpGetset.getVisitDate());
                            img_cam.setImageBitmap(bmp);
                        } catch (OutOfMemoryError ex) {
                            CommonFunctions.setScaledImage(img_cam, str + _pathforcheck);
                        }

                        img_clicked.setVisibility(View.GONE);
                        img_cam.setVisibility(View.VISIBLE);

                        img_str = _pathforcheck;
                        _pathforcheck = "";
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getCurrentTime() {
        Calendar m_cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:mmm");
        String cdate = formatter.format(m_cal.getTime());

        return cdate;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Resuming the periodic location updates
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
        getMid();

    }

    public static Bitmap convertBitmap(String path) {
        Bitmap bitmap = null;
        BitmapFactory.Options ourOptions = new BitmapFactory.Options();
        ourOptions.inDither = false;
        ourOptions.inPurgeable = true;
        ourOptions.inInputShareable = true;
        ourOptions.inTempStorage = new byte[32 * 1024];
        File file = new File(path);
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (fs != null) {
                bitmap = BitmapFactory.decodeFileDescriptor(fs.getFD(), null, ourOptions);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fs != null) {
                try {
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    @Override
    public void onLocationChanged(Location location) {

    }


    //region GeoTagUpload
    public class GeoTagUpload extends AsyncTask<Void, Void, String> {
        ProgressDialog dialog = null;
        private CoverageBean cdata;

        GeoTagUpload(CoverageBean cdata) {
            this.cdata = cdata;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setTitle("Store Image Data");
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

                jsonObject = new JSONObject();
                jsonObject.put("Mid", cdata.getMID());
                jsonObject.put("StoreId", cdata.getStoreId());
                jsonObject.put("VisitDate", cdata.getVisitDate());
                jsonObject.put("Latitude", cdata.getLatitude());
                jsonObject.put("Longitude", cdata.getLongitude());
                jsonObject.put("ReasonId", cdata.getReasonid());
                jsonObject.put("Remark", cdata.getRemark());
                jsonObject.put("ImageName", cdata.getImage());
                jsonObject.put("Appversion", app_ver);
                jsonObject.put("UploadStatus", CommonString.KEY_CHECK_IN);
                jsonObject.put("CheckoutImage", cdata.getCkeckout_image());
                jsonObject.put("UserName", username);
                jsonObject.put("SecurityToken", SecurityToken);

                jsonString2 = jsonObject.toString();
                result = upload.downloadDataUniversal(jsonString2, CommonString.COVERAGE_DETAIL);

                if (result.equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                    throw new SocketTimeoutException();
                } else if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                    throw new JsonSyntaxException("Mapping_Mydb_Posm");
                } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
                } else if (result.contains("[{Result:your SecurityToken is Invalid}])")) {
                    ResultFlag = false;
                    return strflag = "[{Result:your SecurityToken is Invalid}])";
                } else {
                    int mid = 0;
                    try {
                        mid = Integer.parseInt(result);
                        if (mid > 0) {

                            return CommonString.KEY_SUCCESS;
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

            } catch (IOException e) {
                ResultFlag = false;
                strflag = CommonString.MESSAGE_SOCKETEXCEPTION;

            } catch (NumberFormatException e) {
                ResultFlag = false;
                strflag = CommonString.MESSAGE_NUMBER_FORMATE_EXEP;

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
            if (result != null && !result.equals("") && result.equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                database.open();
                if (database.InsertCoverageData(cdata) > 0) {
                    CoverageBean inoutTimeob = new CoverageBean();
                    inoutTimeob.setStoreId("" + jcpGetset.getStoreId());
                    inoutTimeob.setVisitDate(jcpGetset.getVisitDate());
                    inoutTimeob.setJcp_type(tag_from);
                    inoutTimeob.setIntime(CommonFunctions.getCurrentTime());
                    inoutTimeob.setOutTime("");
                    database.open();
                    database.InsertIntimeOutTime(inoutTimeob);
                    database.open();
                    if (database.updateCheckoutStatus(store_id, CommonString.KEY_CHECK_IN, table) > 0) {
                        Intent in = new Intent(context, StoreProfileActivity.class);
                        in.putExtra(CommonString.TAG_OBJECT, jcpGetset);
                        startActivity(in);
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        StoreimageActivity.this.finish();
                    }
                }
            } else if (result.contains("[{Result:your SecurityToken is Invalid}])")) {
                database.open();
                dialog.dismiss();
                database.deleteTableWithStoreID(store_id);
                showAlert(getString(R.string.invalid_token));
            } else {
                database.open();
                dialog.dismiss();
                database.deleteTableWithStoreID(store_id);
                showAlert(getString(R.string.datanotfound) + " " + result);
            }
        }

    }
    //endregion

    public void showAlert(String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(getResources().getString(R.string.main_menu_activity_name));
        builder.setMessage(str).setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        StoreimageActivity.this.finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    void declaration() {
        context = this;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        img_cam = (ImageView) findViewById(R.id.img_selfie);
        img_clicked = (ImageView) findViewById(R.id.img_cam_selfie);
        btn_save = (FloatingActionButton) findViewById(R.id.btn_save_selfie);
        visit_date = preferences.getString(CommonString.KEY_DATE, "");
        date = preferences.getString(CommonString.KEY_DATE, "");
        username = preferences.getString(CommonString.KEY_USERNAME, "");
        designation = preferences.getString(CommonString.KEY_DESIGNATION, "");
        intime = preferences.getString(CommonString.KEY_STORE_IN_TIME, "");
        SecurityToken = preferences.getString(CommonString.KEY_SecurityToken, "");
        visit_date_formatted = preferences.getString(CommonString.KEY_YYYYMMDD_DATE, "");
        str = CommonString.FILE_PATH;
        toolbar.setTitleTextAppearance(context, R.style.changestext_sizefor_mobile);
        setTitle("Store Image - " + date);
        database = new NestleDb(context);
        database.open();
    }

    void getMid() {
        database.open();
        ArrayList<MappingJourneyPlan> jcp = database.getStoreDataCheking(visit_date, store_id, tag_from, designation);
        if (jcp.size() > 0) {
            mid = jcp.get(0).getMID();

        }
    }


    class GetTableMid extends AsyncTask<Void, Void, String> {
        boolean ResultFlag = false;
        String strflag;

        @Override
        protected String doInBackground(Void... params) {
            try {

                UploadImageWithRetrofit upload = new UploadImageWithRetrofit(context);
                JSONObject jsonObject;
                String jsonString2 = "", result;
                if (tag_from.equalsIgnoreCase(CommonString.TAG_FROM_NON_MERCHANDIZED)) {
                    jsonObject = new JSONObject();
                    jsonObject.put("StoreId", store_id);
                    jsonObject.put("VisitDate", visit_date);
                    jsonObject.put("UserName", username);
                    jsonObject.put("JcpType", 1);
                    jsonObject.put("SecurityToken", SecurityToken);
                } else if (tag_from.equalsIgnoreCase(CommonString.TAG_FROM_JCP)) {
                    //dbsr
                    jsonObject = new JSONObject();
                    jsonObject.put("StoreId", store_id);
                    jsonObject.put("VisitDate", visit_date);
                    jsonObject.put("UserName", username);
                    jsonObject.put("JcpType", 3);
                    jsonObject.put("SecurityToken", SecurityToken);
                } else {
                    jsonObject = new JSONObject();
                    jsonObject.put("StoreId", store_id);
                    jsonObject.put("VisitDate", visit_date);
                    jsonObject.put("UserName", username);
                    jsonObject.put("JcpType", 2);
                    jsonObject.put("SecurityToken", SecurityToken);
                }

                jsonString2 = jsonObject.toString();
                result = upload.downloadDataUniversal(jsonString2, CommonString.CreateJ_ourney_Plan);
                result_mid = Integer.parseInt(result);
                if (result.equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                    throw new SocketTimeoutException();
                } else if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result.toString().equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                    throw new JsonSyntaxException("Server_time");
                } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
                } else if (result.contains("[{Result:your SecurityToken is Invalid}])")) {
                    ResultFlag = false;
                    return strflag = "[{Result:your SecurityToken is Invalid}])";
                } else {
                    int mid = 0;
                    try {
                        mid = Integer.parseInt(result);
                        if (mid > 0) {
                            return CommonString.KEY_SUCCESS;
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

            } catch (IOException e) {
                ResultFlag = false;
                strflag = CommonString.MESSAGE_SOCKETEXCEPTION;

            } catch (NumberFormatException e) {
                ResultFlag = false;
                strflag = CommonString.MESSAGE_NUMBER_FORMATE_EXEP;

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
            if (result != null && !result.equals("") && result.equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                database.open();
                if (database.updateTableMid(store_id, result_mid, table) > 0) {
                    _pathforcheck = store_id + "_" + username.replace(".", "") + "_StoreImg-" + visit_date_formatted + "-" + CommonFunctions.getCurrentTimeHHMMSS() + ".jpg";
                    _path = CommonString.FILE_PATH + _pathforcheck;
                    intime = getCurrentTime();
                    CommonFunctions.startAnncaCameraActivity(context, _path, null, false,CommonString.CAMERA_FACE_FRONT);
                    ischangedflag = true;

                }
            } else if (result.contains("[{Result:your SecurityToken is Invalid}])")) {
                showAlert(getString(R.string.invalid_token));
            } else {
                AlertandMessages.showToastMsg(context, getResources().getString(R.string.datanotfound) + " " + result);
            }
        }

    }

    public boolean CheckNetAvailability() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState() == NetworkInfo.State.CONNECTED
                || connectivityManager.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            // we are connected to a network
            connected = true;
        }
        return connected;
    }

}
