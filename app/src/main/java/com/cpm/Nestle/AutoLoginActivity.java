package com.cpm.Nestle;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.cpm.Nestle.Get_IMEI_number.ImeiNumberClass;
import com.cpm.Nestle.autoupdate.AutoUpdateActivity;
import com.cpm.Nestle.blurlockview.BlurLockView;
import com.cpm.Nestle.blurlockview.Password;
import com.cpm.Nestle.getterSetter.GsonGetterSetter;
import com.cpm.Nestle.getterSetter.NoticeBoardGetterSetter;
import com.cpm.Nestle.oneQad.OneQADActivity;
import com.cpm.Nestle.upload.Retrofit_method.UploadImageWithRetrofit;
import com.cpm.Nestle.utilities.AlertandMessages;
import com.cpm.Nestle.utilities.CommonString;
//import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//import io.fabric.sdk.android.Fabric;

public class AutoLoginActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    Dialog dialog;
    private int versionCode;
    private SharedPreferences preferences = null;
    private SharedPreferences.Editor editor = null;
    String userId, password, app_ver, result_str_OQad = "";
    Context context;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 10;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 11;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE_READ = 12;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE_WRITE = 14;
    private String[] imeiNumbers;
    private ImeiNumberClass imei;
    GoogleApiClient mGoogleApiClient;
    private static int UPDATE_INTERVAL = 200; // 5 sec
    private static int FATEST_INTERVAL = 100; // 1 sec
    private static int DISPLACEMENT = 1; // 10 meters
    private static final int REQUEST_LOCATION = 1;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private FirebaseAnalytics mFirebaseAnalytics;
    private double lat = 0.0;
    private double lon = 0.0;
    private String manufacturer;
    private String model;
    private String os_version;
    BlurLockView blurLockView;
    String visit_date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_auto_login);

        blurLockView = (BlurLockView) findViewById(R.id.blurlockview);
        blurLockView.setType(Password.NUMBER, true);
        blurLockView.setLeftButton("Forgot MPin");
        blurLockView.setEnabled(false);
        blurLockView.setCorrectPassword("1234");

        context = this;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();

        //Fabric.with(context, new Crashlytics());
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        userId = preferences.getString(CommonString.KEY_USERNAME, "");
        password = preferences.getString(CommonString.KEY_PASSWORD, "");

        try {
            app_ver = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);

        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        imei = new ImeiNumberClass(context);

        dialog = new Dialog(AutoLoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.progress_layout);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);
        dialog.setCancelable(false);
        if (!dialog.isShowing()) {
            dialog.show();
        }

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE},
                    PERMISSIONS_REQUEST_READ_PHONE_STATE);
        } else {
            checkAppPermission(Manifest.permission.CAMERA, MY_PERMISSIONS_REQUEST_CAMERA);
            imeiNumbers = imei.getDeviceImei();
        }
        getDeviceName();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();

        }
        checkgpsEnableDevice();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        checkAppPermission(Manifest.permission.CAMERA, MY_PERMISSIONS_REQUEST_CAMERA);
        if (requestCode == PERMISSIONS_REQUEST_READ_PHONE_STATE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            imeiNumbers = imei.getDeviceImei();
        }

        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
            createLocationRequest();
        }

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
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
                Toast.makeText(context, getResources().getString(R.string.notsuppoted), Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    protected synchronized void buildGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        //  updateLocation(latLng);
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    protected void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (mGoogleApiClient != null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
        }

    }

    void checkAppPermission(String permission, int requestCode) {

        boolean permission_flag = false;
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context,
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    permission)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                showOnPermissiondenied(Manifest.permission.CAMERA, MY_PERMISSIONS_REQUEST_CAMERA, 1);
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{permission},
                        requestCode);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
                checkAppPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, MY_PERMISSIONS_REQUEST_STORAGE_WRITE);
            } else if (requestCode == MY_PERMISSIONS_REQUEST_STORAGE_WRITE) {
                checkAppPermission(Manifest.permission.READ_EXTERNAL_STORAGE, MY_PERMISSIONS_REQUEST_STORAGE_READ);
            } else if (requestCode == MY_PERMISSIONS_REQUEST_STORAGE_READ) {
                checkAppPermission(Manifest.permission.ACCESS_FINE_LOCATION, MY_PERMISSIONS_REQUEST_LOCATION);
            } else {

                // Create a Folder for Images
                if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(getApplicationContext(),
                                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                if (checkPlayServices()) {
                    // Building the GoogleApi client
                    buildGoogleApiClient();

                    createLocationRequest();
                }

                // Create an instance of GoogleAPIClient.
                if (mGoogleApiClient == null) {
                    mGoogleApiClient = new GoogleApiClient.Builder(context)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .addApi(LocationServices.API)
                            .build();
                }

                Runnable progressRunnable = new Runnable() {

                    @Override
                    public void run() {

                        new AuthenticateTask().execute();
                    }
                };

                Handler pdCanceller = new Handler();
                pdCanceller.postDelayed(progressRunnable, 2000);

            }

        }
    }

    void showOnPermissiondenied(final String permissionsRequired, final int request_code, final int check) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Need Multiple Permissions");
        builder.setMessage("This app needs Camera, Storage and Location permissions.");
        builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                if (check == 0) {
                    checkAppPermission(permissionsRequired, request_code);
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{permissionsRequired}, request_code);
                }

            }
        });
        builder.show();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    private boolean checkgpsEnableDevice() {
        boolean flag = true;
        if (!hasGPSDevice(context)) {
            Toast.makeText(context, "Gps not Supported", Toast.LENGTH_SHORT).show();
        }
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(context)) {
            enableLoc();
            flag = false;
        } else if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(context)) {
            flag = true;
        }
        return flag;
    }

    private boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains(LocationManager.GPS_PROVIDER);
    }

    private void enableLoc() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        if (mGoogleApiClient != null) {
            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult((Activity) context, REQUEST_LOCATION);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                    }
                }
            });
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */

    private class AuthenticateTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {
            try {

                versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
                imeiNumbers = imei.getDeviceImei();

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("UserName", userId.toLowerCase());
                jsonObject.put("Password", password);
                jsonObject.put("Intime", getCurrentTime());
                jsonObject.put("Latitude", lat);
                jsonObject.put("Longitude", lon);
                jsonObject.put("Appversion", app_ver);
                jsonObject.put("Attmode", "0");
                jsonObject.put("Networkstatus", "0");
                jsonObject.put("Manufacturer", manufacturer);
                jsonObject.put("ModelNumber", model);
                jsonObject.put("OSVersion", os_version);

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                    String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                    jsonObject.put("IMEINumber1", android_id);
                    jsonObject.put("IMEINumber2", android_id);
                } else {
                    if (imeiNumbers != null && imeiNumbers.length > 0) {
                        jsonObject.put("IMEINumber1", imeiNumbers[0]);
                        if (imeiNumbers != null && imeiNumbers.length > 1) {
                            jsonObject.put("IMEINumber2", imeiNumbers[1]);
                        } else {
                            jsonObject.put("IMEINumber2", "0");
                        }
                    } else {
                        jsonObject.put("IMEINumber1", "0");
                        jsonObject.put("IMEINumber2", "0");
                    }
                }

                String jsonString2 = jsonObject.toString();
                UploadImageWithRetrofit upload = new UploadImageWithRetrofit(context);
                String result_str = upload.downloadDataUniversal(jsonString2, CommonString.LOGIN_SERVICE);

                Gson gson = new Gson();
                GsonGetterSetter userObject = gson.fromJson(result_str, GsonGetterSetter.class);
                JSONObject jsonObject_notice = new JSONObject();
                jsonObject_notice.put("Downloadtype", "Notice_Board");
                jsonObject_notice.put("UserName", userId.toLowerCase());
                jsonObject_notice.put("Param1", "");
                jsonObject_notice.put("Param2", "");
                jsonObject_notice.put("SecurityToken", userObject.getLOGIN().get(0).getSecurityToken());

                UploadImageWithRetrofit upload2 = new UploadImageWithRetrofit(context);
                String result_notice = upload2.downloadDataUniversal(jsonObject_notice.toString(), CommonString.DOWNLOAD_ALL_SERVICE);

                JSONObject jsonOoqad = new JSONObject();
                jsonOoqad.put("Downloadtype", "OQAD");
                jsonOoqad.put("UserName", userId.toLowerCase());
                jsonOoqad.put("Param1", "");
                jsonOoqad.put("Param2", "");
                jsonOoqad.put("SecurityToken", userObject.getLOGIN().get(0).getSecurityToken());

                UploadImageWithRetrofit upload3 = new UploadImageWithRetrofit(context);
                result_str_OQad = upload3.downloadDataUniversal(jsonOoqad.toString(), CommonString.DOWNLOAD_ALL_SERVICE);

                if (result_str.equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result_str.equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
                } else {
                    NoticeBoardGetterSetter noticeObject = gson.fromJson(result_notice, NoticeBoardGetterSetter.class);
                    // PUT IN PREFERENCES
                    // PUT IN PREFERENCES
                    if (noticeObject != null && noticeObject.getNoticeBoard().size() > 0) {
                        editor.putString(CommonString.KEY_NOTICE_BOARD, noticeObject.getNoticeBoard().get(0).getNoticeUrl());
                        editor.putString(CommonString.KEY_QUIZ_URL, noticeObject.getNoticeBoard().get(0).getQuizUrl());

                    } else {
                        editor.putString(CommonString.KEY_NOTICE_BOARD, "");
                        editor.putString(CommonString.KEY_QUIZ_URL, "");

                    }
                    editor.putString(CommonString.KEY_USERNAME, userId.toLowerCase());
                    editor.putString(CommonString.KEY_PASSWORD, password);

                    editor.putString(CommonString.KEY_RIGHTNAME, String.valueOf(userObject.getLOGIN().get(0).getUserId()));
                    editor.putString(CommonString.KEY_VERSION, String.valueOf(userObject.getLOGIN().get(0).getAppVersion()));
                    editor.putString(CommonString.KEY_PATH, userObject.getLOGIN().get(0).getAppPath());
                    editor.putString(CommonString.KEY_PASSWORD_STATUS, userObject.getLOGIN().get(0).getPasswordStatus());
                    editor.putString(CommonString.KEY_DESIGNATION, userObject.getLOGIN().get(0).getDesignation());
                    editor.putString(CommonString.KEY_DATE, userObject.getLOGIN().get(0).getVisitDate());
                    editor.putString(CommonString.KEY_SecurityToken, userObject.getLOGIN().get(0).getSecurityToken());
                    visit_date = userObject.getLOGIN().get(0).getVisitDate();
                    Date initDate = new SimpleDateFormat("MM/dd/yyyy").parse(userObject.getLOGIN().get(0).getVisitDate());
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                    String parsedDate = formatter.format(initDate);
                    editor.putString(CommonString.KEY_YYYYMMDD_DATE, parsedDate);
                    //date is changed for previous day data
                    editor.commit();
                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, userId.toLowerCase());
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, CommonString.KEY_LOGIN_DATA);
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Data");
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                    return CommonString.KEY_SUCCESS;
                }


            } catch (MalformedURLException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertandMessages.showAlert((Activity) context, CommonString.MESSAGE_EXCEPTION, false);
                    }
                });

            } catch (IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        AlertandMessages.showAlert((Activity) context, CommonString.MESSAGE_SOCKETEXCEPTION, false);
                    }
                });
            } catch (Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertandMessages.showAlert((Activity) context, CommonString.MESSAGE_CHANGED, true);
                    }
                });
            }
            return "";

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if (result!=null && !result.equals("") && result.equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                if (preferences.getString(CommonString.KEY_VERSION, "").equals(Integer.toString(versionCode))) {
                    Intent in;
                    if (result_str_OQad == null || result_str_OQad.equals("") && result_str_OQad.contains("No Data")) {
                        in = new Intent(context, MainActivity.class);
                    } else {
                        if (preferences.getBoolean(CommonString.KEY_IS_QUIZ_DONE + visit_date, false)) {
                            in = new Intent(context, MainActivity.class);
                        } else {
                            in = new Intent(context, OneQADActivity.class);
                        }
                    }

                    startActivity(in);
                    overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                    AutoLoginActivity.this.finish();

                } else {
                    // if app version code does not match with live apk version code then update will be called.
                    Intent intent = new Intent(context, AutoUpdateActivity.class);
                    intent.putExtra(CommonString.KEY_PATH, preferences.getString(CommonString.KEY_PATH, ""));
                    startActivity(intent);
                    overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                    AutoLoginActivity.this.finish();
                }
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.cancel();
        }
    }


    public void getDeviceName() {
        manufacturer = Build.MANUFACTURER;
        model = Build.MODEL;
        os_version = android.os.Build.VERSION.RELEASE;
    }


    private String getCurrentTime() {
        Calendar m_cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(m_cal.getTime());
    }
}
