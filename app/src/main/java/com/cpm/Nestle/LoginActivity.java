package com.cpm.Nestle;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;

import com.cpm.Nestle.Get_IMEI_number.ImeiNumberClass;
import com.cpm.Nestle.autoupdate.AutoUpdateActivity;
import com.cpm.Nestle.getterSetter.GsonGetterSetter;
import com.cpm.Nestle.getterSetter.NoticeBoardGetterSetter;
import com.cpm.Nestle.password.MPinActivity;
import com.cpm.Nestle.upload.Retrofit_method.UploadImageWithRetrofit;
import com.cpm.Nestle.utilities.AlertandMessages;
import com.cpm.Nestle.utilities.CommonString;
import com.google.android.material.snackbar.Snackbar;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.crashlytics.internal.common.CommonUtils;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private TextView tv_version;
    private String app_ver;
    private SharedPreferences preferences = null;
    private SharedPreferences.Editor editor = null;
    private double lat = 0.0;
    private double lon = 0.0;
    // UI references.
    private EditText mPasswordView, museridView;
    private Context context;
    private String userid;
    private String password;
    private FirebaseAnalytics mFirebaseAnalytics;
    private int versionCode;
    private String[] imeiNumbers;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;
    private Button museridSignInButton;
    private ImeiNumberClass imei;
    private String manufacturer;
    private String model;
    private String os_version;

    GoogleApiClient mGoogleApiClient;
    private static int UPDATE_INTERVAL = 200; // 5 sec
    private static int FATEST_INTERVAL = 100; // 1 sec
    private static int DISPLACEMENT = 1; // 10 meters
    private static final int REQUEST_LOCATION = 1;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 10;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 11;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE_READ = 12;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE_WRITE = 14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        declaration();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE},
                    PERMISSIONS_REQUEST_READ_PHONE_STATE);
        } else {
            imeiNumbers = imei.getDeviceImei();
        }
        getDeviceName();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        checkAppPermission(Manifest.permission.CAMERA, MY_PERMISSIONS_REQUEST_CAMERA);
        if (requestCode == PERMISSIONS_REQUEST_READ_PHONE_STATE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            imeiNumbers = imei.getDeviceImei();
        }

        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();

        }
        checkgpsEnableDevice();
    }

    private void attemptLogin() {
        // Reset errors.
        museridView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        userid = museridView.getText().toString();
        password = mPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (museridView.getText().toString().contains(" ")) {
            museridView.setError("User Id Contains Space ");
            focusView = mPasswordView;
            cancel = true;
        } else if (mPasswordView.getText().toString().contains(" ")) {
            mPasswordView.setError("Password Contains Space ");
            focusView = mPasswordView;
            cancel = true;
        } else if (TextUtils.isEmpty(mPasswordView.getText().toString())) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        // Check for a valid userid address.
        else if (TextUtils.isEmpty(museridView.getText().toString())) {
            museridView.setError(getString(R.string.error_field_required));
            focusView = museridView;
            cancel = true;
        } else {
            if (cancel) {
                // There was an error; don't attempt login and focus the first
                // form field with an error.
                focusView.requestFocus();
            } else if (!isuseridValid(userid)) {
                Snackbar.make(museridView, getString(R.string.error_incorrect_username), Snackbar.LENGTH_SHORT).show();
            } else if (!isPasswordValid(password)) {
                Snackbar.make(museridView, getString(R.string.error_incorrect_password), Snackbar.LENGTH_SHORT).show();
            } else {
                // Show a progress spinner, and kick off a background task to
                // perform the user login attempt.
                new AuthenticateTask().execute();
            }
        }
    }

    private boolean isuseridValid(String userid) {
        //TODO: Replace this with your own logic
        boolean flag = true;
        String u_id = preferences.getString(CommonString.KEY_USERNAME, "");
        if (!u_id.equals("") && !userid.equalsIgnoreCase(u_id)) {
            flag = false;
        }
        return flag;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        boolean flag = true;
        String pw = preferences.getString(CommonString.KEY_PASSWORD, "");
        if (!pw.equals("") && !password.equals(pw)) {
            flag = false;
        }
        return flag;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */

    private class AuthenticateTask extends AsyncTask<Void, Void, String> {
        private ProgressDialog dialog = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setTitle("Login");
            dialog.setMessage("Authenticating....");
            dialog.setCancelable(false);
            if (!dialog.isShowing()) {
                dialog.show();
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
                imeiNumbers = imei.getDeviceImei();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("UserName", userid.toLowerCase());
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
                if (result_str.equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result_str.equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
                } else {
                    Gson gson = new Gson();
                    GsonGetterSetter userObject = gson.fromJson(result_str, GsonGetterSetter.class);
                    if (userObject != null && userObject.getLOGIN().size() > 0) {
                        JSONObject jsonObject_notice = new JSONObject();
                        jsonObject_notice.put("Downloadtype", "Notice_Board");
                        jsonObject_notice.put("UserName", userid.toLowerCase());
                        jsonObject_notice.put("Param1", "");
                        jsonObject_notice.put("Param2", "");
                        jsonObject_notice.put("SecurityToken", userObject.getLOGIN().get(0).getSecurityToken());

                        UploadImageWithRetrofit upload2 = new UploadImageWithRetrofit(context);
                        String result_notice = upload2.downloadDataUniversal(jsonObject_notice.toString(), CommonString.DOWNLOAD_ALL_SERVICE);
                        gson = new Gson();
                        NoticeBoardGetterSetter noticeObject = gson.fromJson(result_notice, NoticeBoardGetterSetter.class);

                        // PUT IN PREFERENCES
                        if (noticeObject != null && noticeObject.getNoticeBoard().size() > 0) {
                            editor.putString(CommonString.KEY_NOTICE_BOARD, noticeObject.getNoticeBoard().get(0).getNoticeUrl());
                            editor.putString(CommonString.KEY_QUIZ_URL, noticeObject.getNoticeBoard().get(0).getQuizUrl());

                        } else {
                            editor.putString(CommonString.KEY_NOTICE_BOARD, "");
                            editor.putString(CommonString.KEY_QUIZ_URL, "");

                        }
                        editor.putString(CommonString.KEY_USERNAME, userid.toLowerCase());
                        editor.putString(CommonString.KEY_PASSWORD, password);
                        editor.putString(CommonString.KEY_RIGHTNAME, String.valueOf(userObject.getLOGIN().get(0).getUserId()));
                        editor.putString(CommonString.KEY_VERSION, String.valueOf(userObject.getLOGIN().get(0).getAppVersion()));
                        editor.putString(CommonString.KEY_PATH, userObject.getLOGIN().get(0).getAppPath());
                        editor.putString(CommonString.KEY_PASSWORD_STATUS, userObject.getLOGIN().get(0).getPasswordStatus());
                        editor.putString(CommonString.KEY_DESIGNATION, userObject.getLOGIN().get(0).getDesignation());
                        editor.putString(CommonString.KEY_SecurityToken, userObject.getLOGIN().get(0).getSecurityToken());
                        editor.putString(CommonString.KEY_DATE, userObject.getLOGIN().get(0).getVisitDate());
                        Date initDate = new SimpleDateFormat("MM/dd/yyyy").parse(userObject.getLOGIN().get(0).getVisitDate());
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                        String parsedDate = formatter.format(initDate);
                        editor.putString(CommonString.KEY_YYYYMMDD_DATE, parsedDate);
                        //date is changed for previous day data
                        editor.commit();
                        Bundle bundle = new Bundle();
                        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, userid.toLowerCase());
                        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, CommonString.KEY_LOGIN_DATA);
                        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Data");
                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                        FirebaseCrashlytics.getInstance().setUserId(userid.toLowerCase());
                        return CommonString.KEY_SUCCESS;
                    } else {
                        return CommonString.KEY_FAILURE;
                    }
                }
            } catch (final MalformedURLException e) {
                dialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertandMessages.showAlert((Activity) context, CommonString.MESSAGE_EXCEPTION + "- Error - " + e.toString(), false);
                    }
                });


            } catch (final IOException e) {
                dialog.dismiss();
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        AlertandMessages.showAlert((Activity) context, CommonString.MESSAGE_SOCKETEXCEPTION + "- Error - " + e.toString(), false);
                    }
                });
            } catch (final Exception e) {
                dialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertandMessages.showAlert((Activity) context, CommonString.MESSAGE_CHANGED + "- Error - " + e.toString(), false);
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
                    Intent in = new Intent(context, MPinActivity.class);
                    in.putExtra(CommonString.IS_PASSWORD_CHECK, false);
                    startActivity(in);
                    overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                    LoginActivity.this.finish();
                } else {
                    // if app version code does not match with live apk version code then update will be called.
                    Intent intent = new Intent(context, AutoUpdateActivity.class);
                    intent.putExtra(CommonString.KEY_PATH, preferences.getString(CommonString.KEY_PATH, ""));
                    startActivity(intent);
                    overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                    LoginActivity.this.finish();
                }
            }
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

    private void declaration() {
        context = this;
        tv_version = (TextView) findViewById(R.id.tv_version_code);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        museridView = (EditText) findViewById(R.id.userid);
        mPasswordView = (EditText) findViewById(R.id.password);
      // museridView.setText("testmer");
      //  mPasswordView.setText("Cpm@123%");
        //museridView.setText("jitendra.s");
      /*  museridView.setText("nitin.s");
       mPasswordView.setText("Cpm@123%");*/
        museridSignInButton = (Button) findViewById(R.id.user_login_button);
        museridSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CommonUtils.isRooted(context)) {
                    AlertandMessages.showToastMsg(context, "This device is rooted. You cannot use this app.");
                } else {
                    if (checkNetIsAvailable()) {
                        attemptLogin();
                    } else {
                        AlertandMessages.showAlert((Activity) context, CommonString.MESSAGE_INTERNET_NOT_AVALABLE, false);
                    }
                }
            }
        });
        try {

            app_ver = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);

        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            FirebaseCrashlytics.getInstance().recordException(e);
        }

        tv_version.setText("Version - " + app_ver);
        imei = new ImeiNumberClass(context);

    }

    private boolean checkNetIsAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, (Activity) context, PLAY_SERVICES_RESOLUTION_REQUEST).show();
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

    void checkAppPermission(String permission, int requestCode) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
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
            }else {

                // Create a Folder for Images
                if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(context,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(context,
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

                //attemptLogin();
            }

        }
    }

    void showOnPermissiondenied(final String permissionsRequired, final int request_code, final int check) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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
}

