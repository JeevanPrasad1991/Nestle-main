package com.cpm.Nestle.geotag;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;

import com.cpm.Nestle.database.NestleDb;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cpm.Nestle.R;
import com.cpm.Nestle.dailyEntry.StoreimageActivity;
import com.cpm.Nestle.delegates.CoverageBean;
import com.cpm.Nestle.getterSetter.GeotaggingBeans;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
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
import java.util.List;

import static com.cpm.Nestle.utilities.CommonFunctions.convertBitmap;

public class GeoTaggingActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private static final String TAG = GeoTaggingActivity.class.getSimpleName();
    private static int UPDATE_INTERVAL = 500; // 5 sec
    private static int FATEST_INTERVAL = 100; // 1 sec
    private static int DISPLACEMENT = 5; // 10 meters
    protected String _path, _pathforcheck, img_str = "";
    String tag_from = "";
    double latitude = 0.0;
    double longitude = 0.0;
    FloatingActionButton fab, fabcarmabtn;
    SharedPreferences preferences;
    String username, str;
    NestleDb db;
    LocationManager locationManager;
    Marker currLocationMarker;
    Geocoder geocoder;
    boolean enabled;
    ArrayList<GeotaggingBeans> geotaglist = new ArrayList<>();
    Context context;
    Activity activity;
    String app_ver = "0";
    UploadImageWithRetrofit upload;
    FloatingActionButton camera_fab;
    String status;
    ArrayList<CoverageBean> coverageBeanList;
    private GoogleMap mMap;
    private Location mLastLocation;
    private LocationManager locmanager = null;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private SharedPreferences.Editor editor = null;
    MappingJourneyPlan jcpGetset;
    String SecurityToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_tagging);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextAppearance(this, R.style.changestext_sizefor_mobile);
        getSupportActionBar().setTitle("GeoTag");
        declaration();
        upload = new UploadImageWithRetrofit(context);

        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
            createLocationRequest();
        } else {
            if (mGoogleApiClient == null) {
                mGoogleApiClient = new GoogleApiClient.Builder(context)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API)
                        .build();
            }
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!img_str.equals("")) {
                    status = "N";
                    coverageBeanList = new ArrayList<>();
                    db.updateStatus(jcpGetset.getStoreId().toString(), status);
                    if (db.InsertSTOREgeotag(jcpGetset.getStoreId().toString(), latitude, longitude, img_str, status) > 0) {
                        img_str = "";
                        new GeoTagUpload(context, coverageBeanList).execute();
                    } else {
                        AlertandMessages.showSnackbarMsg(view, "Error in saving Geotag");
                    }

                } else {
                    AlertandMessages.showSnackbarMsg(view, getResources().getString(R.string.takeimage));
                }

            }
        });

        camera_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkNetIsAvailable()) {
                    if (latitude != 0.0 && longitude != 0.0) {
                        _pathforcheck = jcpGetset.getStoreId().toString() + "_GeoTag-" + jcpGetset.getVisitDate().replace("/", "") + "_" + getCurrentTime().replace(":", "") + ".jpg";
                        _path = CommonString.FILE_PATH + _pathforcheck;
                        CommonFunctions.startAnncaCameraActivity(context, _path, null, false, CommonString.CAMERA_FACE_REAR);
                    } else {
                        AlertandMessages.showToastMsg(context, "Please wait for location");
                    }
                } else {
                    AlertandMessages.showToastMsg(context, getResources().getString(R.string.nonetwork));
                }
            }
        });

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }


    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.notsuppoted), Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    protected void buildGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API).build();

        }

    }


    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }


    protected void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (LocationListener) this);
        }
    }

    private boolean checkNetIsAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            String result = null;
            LatLng latLng;
            try {
                List<Address> addressList = geocoder.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1);
                if (addressList != null && addressList.size() > 0) {
                    result = addressList.get(0).getAddressLine(0);
                }
            } catch (IOException e) {
                Log.e(TAG, "Unable connect to Geocoder", e);
            }

            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (mLastLocation != null) {
                    latitude = mLastLocation.getLatitude();
                    longitude = mLastLocation.getLongitude();

                }
            }

            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(result);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            currLocationMarker = mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));


        }

        startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    protected void onResume() {
        super.onResume();
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
    }

    private void stopLocationUpdates() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }


    protected void onStart() {
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    public String getCurrentTime() {
        Calendar m_cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:mmm");
        String cdate = formatter.format(m_cal.getTime());
        return cdate;
    }

    @Override
    protected void onStop() {
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        // AppIndex.AppIndexApi.end(client, getIndexApiAction());
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client.disconnect();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 0:
                Log.i("MakeMachine", "User cancelled");
                break;
            case -1:
                if (_pathforcheck != null && !_pathforcheck.equals("")) {
                    try {
                        if (new File(CommonString.FILE_PATH + _pathforcheck).exists()) {
                            convertBitmap(CommonString.FILE_PATH + _pathforcheck);
                            String metadata = CommonFunctions.setMetadataAtImages(jcpGetset.getStoreName(), jcpGetset.getStoreId().toString(), "GeoTag Image", username);
                            CommonFunctions.addMetadataAndTimeStampToImage(context, _path, metadata, jcpGetset.getVisitDate());
                            fabcarmabtn.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.mipmap.camera_green));
                            fabcarmabtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#888888")));
                            img_str = _pathforcheck;
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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    public void onLocationChanged(Location location) {

    }


    void declaration() {
        activity = this;
        context = this;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        username = preferences.getString(CommonString.KEY_USERNAME, null);
        SecurityToken = preferences.getString(CommonString.KEY_SecurityToken, "");
        tag_from = getIntent().getStringExtra(CommonString.TAG_FROM);
        fab = findViewById(R.id.fab);
        fabcarmabtn = findViewById(R.id.camrabtn);
        db = new NestleDb(context);
        db.open();
        str = CommonString.FILE_PATH;
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        geocoder = new Geocoder(this);
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
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        try {
            app_ver = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (getIntent().getSerializableExtra(CommonString.TAG_OBJECT) != null) {
            jcpGetset = (MappingJourneyPlan) getIntent().getSerializableExtra(CommonString.TAG_OBJECT);
        }
        fab = (FloatingActionButton) findViewById(R.id.fab);
        camera_fab = (FloatingActionButton) findViewById(R.id.camrabtn);
    }

    public class GeoTagUpload extends AsyncTask<Void, Void, String> {
        boolean uploadflag;
        String errormsg = "";
        private Context context;
        private ProgressDialog pdialog = null;


        GeoTagUpload(Context context, ArrayList<CoverageBean> coverageBeanList) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new ProgressDialog(context);
            pdialog.setTitle("GeoTag Data");
            pdialog.setMessage("Uploading....");
            pdialog.setCancelable(false);
            pdialog.show();

        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                // uploading Geotag
                uploadflag = false;
                geotaglist = db.getinsertGeotaggingData(jcpGetset.getStoreId().toString(), "N");
                if (geotaglist.size() > 0) {
                    JSONArray topUpArray = new JSONArray();
                    for (int j = 0; j < geotaglist.size(); j++) {
                        JSONObject obj = new JSONObject();
                        obj.put(CommonString.KEY_STORE_ID, geotaglist.get(j).getStoreid());
                        obj.put(CommonString.KEY_VISIT_DATE, jcpGetset.getVisitDate());
                        obj.put(CommonString.KEY_LATITUDE, geotaglist.get(j).getLatitude());
                        obj.put(CommonString.KEY_LONGITUDE, geotaglist.get(j).getLongitude());
                        obj.put("FRONT_IMAGE", geotaglist.get(j).getImage());

                        topUpArray.put(obj);
                    }

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("MID", "0");
                    jsonObject.put("Keys", "GeoTag");
                    jsonObject.put("JsonData", topUpArray.toString());
                    jsonObject.put("UserName", username);
                    jsonObject.put("SecurityToken", SecurityToken);
                    String jsonString2 = jsonObject.toString();
                    String result = upload.downloadDataUniversal(jsonString2, CommonString.UPLOADJsonDetail);

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
            super.onPostExecute(result);
            pdialog.dismiss();
            if (result.equals(CommonString.KEY_SUCCESS)) {
                status = "Y";
                db.open();
                if (tag_from.equalsIgnoreCase("from_jcp")) {
                    db.updateStatus(jcpGetset.getStoreId().toString(), status);
                } else if (tag_from.equalsIgnoreCase("from_non_merchandized")) {
                    db.updateStatusNonemerchandise(jcpGetset.getStoreId().toString(), status);
                } else if (tag_from.equalsIgnoreCase("from_not_covered")) {
                    db.updateStatusNotCoveredemerchandise(jcpGetset.getStoreId().toString(), status);
                }

                if (db.updateInsertedGeoTagStatus(jcpGetset.getStoreId().toString(), status) > 0) {
                    img_str = "";
                    AlertandMessages.showToastMsg(context, "Geotag Saved Successfully");
                    Intent in = new Intent(context, StoreimageActivity.class);
                    in.putExtra(CommonString.TAG_OBJECT, jcpGetset);
                    in.putExtra(CommonString.TAG_FROM, tag_from);
                    startActivity(in);
                    GeoTaggingActivity.this.finish();
                } else {
                    AlertandMessages.showAlert((Activity) context, "Error in updating Geotag status", true);
                }

            } else {
                AlertandMessages.showAlert((Activity) context, getResources().getString(R.string.failure) + " : " + errormsg, true);
                GeoTaggingActivity.this.finish();
            }

        }

    }

}
