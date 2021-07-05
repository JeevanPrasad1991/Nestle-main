package com.cpm.Nestle;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;

import androidx.annotation.RequiresApi;

import com.cpm.Nestle.dailyEntry.QuizActivity;
import com.cpm.Nestle.dailyEntry.RDVisibilityDriveActivity;
import com.cpm.Nestle.dailyEntry.VisibilityDriveActivity;
import com.cpm.Nestle.getterSetter.MappingVQPSVisibilityDrive;
import com.cpm.Nestle.getterSetter.TrainingContentGetterSetter;
import com.cpm.Nestle.upload.Retrofit_method.UploadImageWithRetrofit;
import com.cpm.Nestle.utilities.CommonFunctions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cpm.Nestle.dailyEntry.ServiceActivity;
import com.cpm.Nestle.dailyEntry.StoreListActivity;
import com.cpm.Nestle.database.NestleDb;
import com.cpm.Nestle.delegates.CoverageBean;
import com.cpm.Nestle.download.DownloadActivity;
import com.cpm.Nestle.getterSetter.MappingJourneyPlan;
import com.cpm.Nestle.password.ChangePasswordActivity;
import com.cpm.Nestle.upload.Retrofit_method.upload.PreviousDataUploadActivity;
import com.cpm.Nestle.upload.Retrofit_method.upload.UploadWithoutWaitActivity;
import com.cpm.Nestle.utilities.AlertandMessages;
import com.cpm.Nestle.utilities.CommonString;
import com.cpm.Nestle.visitor.VisitorLoginActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<MappingVQPSVisibilityDrive> visibilityDrives = new ArrayList<>();
    private WebView webView;
    private ImageView imageView;
    private String noticeboard, quiz_url;
    public static final int REQUEST_SELECT_FILE = 100;
    private final static int FILECHOOSER_RESULTCODE = 1;
    WebSettings mWebSettings;
    ValueCallback<Uri[]> uploadMessage;
    private ValueCallback<Uri> mUploadMessage;
    private NestleDb db;
    private View headerView;
    private String error_msg;
    private Context context;
    private int downloadIndex;
    private SharedPreferences preferences;
    String user_name;
    FloatingActionButton fab;
    Toolbar toolbar;
    String visit_date;
    private ArrayList<MappingJourneyPlan> storelist = new ArrayList<>();
    private ArrayList<MappingJourneyPlan> storelistNonMercchandise = new ArrayList<>();
    private ArrayList<MappingJourneyPlan> storelistNotCovered = new ArrayList<>();
    private ArrayList<MappingJourneyPlan> storelist_dbsr = new ArrayList<>();
    private ArrayList<CoverageBean> coverageList;
    private FirebaseAnalytics mFirebaseAnalytics;

    String  msg_str = "";
    boolean ResultFlag = true;
    String SecurityToken;
    TrainingContentGetterSetter trainingContentGetterSetter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        declaration();
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        if (!noticeboard.equals("")) {
            webView.loadUrl(noticeboard);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_main, navigationView, false);
        TextView tv_username = (TextView) headerView.findViewById(R.id.nav_user_name);
        tv_username.setText(user_name);

        ImageView img_change_password = (ImageView) headerView.findViewById(R.id.img_change_password);
        img_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                startActivity(in);
            }
        });

        //tv_usertype.setText(user_type);
        navigationView.addHeaderView(headerView);
        navigationView.setNavigationItemSelectedListener(this);

        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/" + CommonString.FOLDER_NAME_IMAGE);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quiz_url != null) {
                    webView.loadUrl(quiz_url);
                    fab.hide();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (quiz_url == null || quiz_url.equals("")) {
            fab.hide();
        } else {
            fab.show();
        }

        setTitle("Notice Board");
        downloadIndex = preferences.getInt(CommonString.KEY_DOWNLOAD_INDEX, 0);
        coverageList = db.getCoverageData(visit_date, null);
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(false);
        webView.clearCache(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.setLongClickable(true);
        mWebSettings = webView.getSettings();

        webView.setWebChromeClient(new WebChromeClient() {
            // For 3.0+ Devices (Start)
            // onActivityResult attached before constructor
            // For Lollipop 5.0+ Devices
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                if (uploadMessage != null) {
                    uploadMessage.onReceiveValue(null);
                    uploadMessage = null;
                }

                uploadMessage = filePathCallback;
                Intent intent = fileChooserParams.createIntent();
                try {
                    startActivityForResult(intent, REQUEST_SELECT_FILE);
                } catch (ActivityNotFoundException e) {
                    uploadMessage = null;
                    Toast.makeText(getApplicationContext(), "Cannot Open File Chooser", Toast.LENGTH_LONG).show();
                    return false;
                }

                return true;
            }
        });


        webView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!noticeboard.equals("")) {
                    webView.loadUrl(noticeboard);
                }
            }
        }, 300000);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode == REQUEST_SELECT_FILE) {
                if (uploadMessage == null)
                    return;
                uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
                uploadMessage = null;
            }
        } else if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
            // Use RESULT_OK only if you're implementing WebView inside an Activity
            Uri result = intent == null || resultCode != MainActivity.RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        } else
            Toast.makeText(getApplicationContext(), "Failed to Upload Image", Toast.LENGTH_LONG).show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_route_plan) {
            Intent startDownload = new Intent(context, StoreListActivity.class);
            startDownload.putExtra(CommonString.TAG_FROM, CommonString.TAG_FROM_JCP);
            startActivity(startDownload);
            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

        } else if (id == R.id.nav_download) {
            if (checkNetIsAvailable()) {
                //  visit_date = "24/04/2019";
                if (!db.isCoverageDataFilled(visit_date)) {
                    storelist = db.getStoreData(visit_date);
                    if (storelist.size() > 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle(getString(R.string.main_menu_activity_name));
                        builder.setMessage(getResources().getString(R.string.want_download_data)).setCancelable(false)
                                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        try {
                                            db.open();
                                            db.deletePreviousUploadedData(visit_date);
                                            db.deletePreviousJouneyPlanDBSRData(visit_date);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        Intent in = new Intent(context, DownloadActivity.class);
                                        startActivity(in);
                                        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);

                                    }
                                })
                                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });

                        AlertDialog alert = builder.create();
                        alert.show();
                    } else {
                        try {
                            db.open();
                            db.deletePreviousUploadedData(visit_date);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Intent in = new Intent(context, DownloadActivity.class);
                        startActivity(in);
                        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                    }


                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(getString(R.string.main_menu_activity_name));
                    builder.setMessage(getResources().getString(R.string.previous_data_upload)).setCancelable(false)
                            .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent in = new Intent(context, PreviousDataUploadActivity.class);
                                    startActivity(in);
                                    overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }

            } else {
                AlertandMessages.showToastMsg(context, getResources().getString(R.string.nonetwork));
            }

        } else if (id == R.id.nav_upload) {
            db.open();
            if (checkNetIsAvailable()) {
                storelist = db.getStoreData(visit_date);
                storelist_dbsr = db.getStoreData_DBSR(visit_date);
                storelistNonMercchandise = db.getStoreDataNonmerchandise(visit_date);
                storelistNotCovered = db.getStoreDataNotCovered(visit_date);
                if ((storelist_dbsr.size() > 0 || storelist.size() > 0 || storelistNonMercchandise.size() > 0 || storelistNotCovered.size() > 0) && downloadIndex == 0) {
                    if (coverageList.size() == 0) {
                        Snackbar.make(webView, R.string.no_data_for_upload, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                    } else {
                        if (isStoreCheckedIn() || isStoreCheckedInForDBSR() || isStoreNonMerchandizedIn() || isStoreNotCoverd()) {
                            if (isValid() || isValidForDBSR() || isValidNonMerchandis() || isValidNonMerchandisCovered()) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Parinaam");
                                builder.setMessage(getResources().getString(R.string.want_upload_data)).setCancelable(false)
                                        .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Intent i = new Intent(getBaseContext(), UploadWithoutWaitActivity.class);
                                                startActivity(i);
                                                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                                            }
                                        })
                                        .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        });

                                AlertDialog alert = builder.create();
                                alert.show();
                            } else {
                                AlertandMessages.showSnackbarMsg(context, "No data for Upload");
                            }
                        } else {
                            Snackbar.make(webView, error_msg, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                        }
                    }
                } else {
                    Snackbar.make(webView, R.string.title_store_list_download_data, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                }
                //}
            } else {
                AlertandMessages.showToastMsg(context, getString(R.string.nonetwork));
            }
        } else if (id == R.id.nav_visitor) {
            db.open();
            if (db.getStoreData(visit_date).size() > 0) {
                Intent intent = new Intent(context, VisitorLoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
            } else {
                AlertandMessages.showToastMsg(context, getString(R.string.title_store_list_download_data));
            }

        } else if (id == R.id.nav_exit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(getResources().getString(R.string.dialog_title));
            builder.setMessage(getResources().getString(R.string.want_to_exit)).setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            ActivityCompat.finishAffinity((Activity) context);
                            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                            MainActivity.this.finish();
                        }
                    }).setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

        } else if (id == R.id.nav_personal_docs) {
            if (CommonFunctions.checkForPermission(context)) {
                startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
            } else {
                AlertandMessages.showToastMsg(context, "Permission Not allowed");
            }
        } else if (id == R.id.nav_report) {


        } else if (id == R.id.nav_non_merchandize) {
            db.open();
            if (downloadIndex == 0) {
                ArrayList<MappingJourneyPlan> deviationList = db.getStoreDataNonmerchandise(visit_date);
                if (deviationList.size() > 0) {
                    Intent startDownload = new Intent(context, StoreListActivity.class);
                    startDownload.putExtra(CommonString.TAG_FROM, CommonString.TAG_FROM_NON_MERCHANDIZED);
                    startActivity(startDownload);
                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                } else {
                    AlertandMessages.showToastMsg(context, "No Deviation data found.");
                }
            } else {
                AlertandMessages.showToastMsg(context, "Please Download Data First");
            }

        } else if (id == R.id.nav_visibility_drive) {
            db.open();
            visibilityDrives = db.getVisibilityDriveData();
            if (visibilityDrives.size() > 0) {
                Intent startDownload = new Intent(context, VisibilityDriveActivity.class);
                startActivity(startDownload);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            } else {
                AlertandMessages.showToastMsg(context, "Please Download Data First");
            }

        } else if (id == R.id.nav_training) {
            if (CheckNetAvailability()) {
                new GetCredentials().execute();
            } else {
                Toast.makeText(getApplicationContext(), "No internet connection! try again later", Toast.LENGTH_LONG).show();
            }

        } else if (id == R.id.nav_rdvisibility_drive) {
            db.open();
            visibilityDrives = db.getVisibilityDriveData();
            if (visibilityDrives.size() > 0) {
                Intent startDownload = new Intent(context, RDVisibilityDriveActivity.class);
                startActivity(startDownload);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            } else {
                AlertandMessages.showToastMsg(context, "Please Download Data First");
            }

        } else if (id == R.id.nav_not_covered) {
            if (downloadIndex == 0) {
                db.open();
                ArrayList<MappingJourneyPlan> notCoverdList = db.getStoreDataNotCovered(visit_date);
                if (notCoverdList.size() > 0) {
                    Intent startDownload = new Intent(context, StoreListActivity.class);
                    startDownload.putExtra(CommonString.TAG_FROM, CommonString.TAG_FROM_NOT_COVERED);
                    startActivity(startDownload);
                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                } else {
                    AlertandMessages.showToastMsg(context, "No covered data found.");
                }
            } else {
                AlertandMessages.showToastMsg(context, "Please Download Data First");
            }
        } else if (id == R.id.nav_services) {
            Intent startservice = new Intent(context, ServiceActivity.class);
            startActivity(startservice);
            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (checkNetIsAvailable()) {
                imageView.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            } else {
                imageView.setVisibility(View.VISIBLE);
                webView.setVisibility(View.GONE);
            }
            super.onPageFinished(view, url);
            view.clearCache(true);
        }
    }

    private boolean checkNetIsAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private boolean isStoreCheckedIn() {
        boolean result_flag = true;
        for (int i = 0; i < coverageList.size(); i++) {
            ArrayList<MappingJourneyPlan> journeyPlanList = db.getSpecificStoreData(null, coverageList.get(i).getStoreId());
            String status = null;
            if (journeyPlanList.size() > 0) {
                status = journeyPlanList.get(0).getUploadStatus();
            } else {
                result_flag = false;
                status = null;
            }
            if (status != null && (status.equals(CommonString.KEY_CHECK_IN) || status.equals(CommonString.KEY_VALID))) {
                result_flag = false;
                error_msg = getResources().getString(R.string.title_store_list_checkout_current);
                break;
            }
        }
        return result_flag;
    }


    private boolean isValid() {
        boolean flag = false;
        for (int i = 0; i < coverageList.size(); i++) {
            String storestatus;
            ArrayList<MappingJourneyPlan> journeyPlans = db.getSpecificStoreData(null, coverageList.get(i).getStoreId());
            if (journeyPlans.size() > 0) {
                storestatus = journeyPlans.get(0).getUploadStatus();
            } else {
                storestatus = null;
                flag = false;
            }

            if (storestatus != null && !storestatus.equalsIgnoreCase(CommonString.KEY_U)) {
                if ((storestatus.equalsIgnoreCase(CommonString.KEY_C) || storestatus.equalsIgnoreCase(CommonString.KEY_P) ||
                        (storestatus.equalsIgnoreCase(CommonString.STORE_STATUS_LEAVE) && !coverageList.get(i).getReasonid().equalsIgnoreCase("11")) || storestatus.equalsIgnoreCase(CommonString.KEY_D))) {
                    flag = true;
                    break;
                }
            }
        }
        if (!flag) {
            error_msg = getResources().getString(R.string.no_data_for_upload);
        }
        return flag;
    }


    void declaration() {
        context = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        user_name = preferences.getString(CommonString.KEY_USERNAME, null);
        visit_date = preferences.getString(CommonString.KEY_DATE, null);
        noticeboard = preferences.getString(CommonString.KEY_NOTICE_BOARD, "");
        quiz_url = preferences.getString(CommonString.KEY_QUIZ_URL, "");
        SecurityToken = preferences.getString(CommonString.KEY_SecurityToken, "");
        imageView = (ImageView) findViewById(R.id.img_main);
        webView = (WebView) findViewById(R.id.webview);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        TextView version_code = (TextView) findViewById(R.id.version_code);
        toolbar.setTitleTextAppearance(context, R.style.changestext_sizefor_mobile);
        toolbar.setTitle("Notice Board");
        db = new NestleDb(context);
        db.open();

        try {
            String app_ver = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
            version_code.setText("Version - " + app_ver);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private boolean isStoreNonMerchandizedIn() {
        boolean result_flag = true;
        for (int i = 0; i < coverageList.size(); i++) {
            ArrayList<MappingJourneyPlan> journeyPlanList = db.getSpecificDeviationJourneyPlan(coverageList.get(i).getStoreId());
            String status = null;
            if (journeyPlanList.size() > 0) {
                status = journeyPlanList.get(0).getUploadStatus();
            } else {
                result_flag = false;
                status = null;
            }
            if (status != null && (status.equals(CommonString.KEY_CHECK_IN) || status.equals(CommonString.KEY_VALID))) {
                result_flag = false;
                error_msg = getResources().getString(R.string.title_store_list_checkout_current);
                break;
            }
        }
        return result_flag;
    }

    private boolean isStoreNotCoverd() {
        boolean result_flag = true;
        for (int i = 0; i < coverageList.size(); i++) {
            ArrayList<MappingJourneyPlan> journeyPlanList = db.getSpecificDeviationNotJourneyPlan(coverageList.get(i).getStoreId());
            String status = null;
            if (journeyPlanList.size() > 0) {
                status = journeyPlanList.get(0).getUploadStatus();
            } else {
                result_flag = false;
                status = null;
            }
            if (status != null && (status.equals(CommonString.KEY_CHECK_IN) || status.equals(CommonString.KEY_VALID))) {
                result_flag = false;
                error_msg = getResources().getString(R.string.title_store_list_checkout_current);
                break;
            }
        }
        return result_flag;
    }

    private boolean isValidNonMerchandisCovered() {
        boolean flag = false;
        for (int i = 0; i < coverageList.size(); i++) {
            String storestatus;
            ArrayList<MappingJourneyPlan> journeyPlans = db.getSpecificDeviationNotJourneyPlan(coverageList.get(i).getStoreId());
            if (journeyPlans.size() > 0) {
                storestatus = journeyPlans.get(0).getUploadStatus();
            } else {
                storestatus = null;
                flag = false;
            }

            if (storestatus != null && !storestatus.equalsIgnoreCase(CommonString.KEY_U)) {
                if ((storestatus.equalsIgnoreCase(CommonString.KEY_C) || storestatus.equalsIgnoreCase(CommonString.KEY_P) ||
                        (storestatus.equalsIgnoreCase(CommonString.STORE_STATUS_LEAVE) && !coverageList.get(i).getReasonid().equalsIgnoreCase("11")) || storestatus.equalsIgnoreCase(CommonString.KEY_D))) {
                    flag = true;
                    break;
                }
            }
        }
        if (!flag) {
            error_msg = getResources().getString(R.string.no_data_for_upload);
        }
        return flag;
    }

    private boolean isValidNonMerchandis() {
        boolean flag = false;
        for (int i = 0; i < coverageList.size(); i++) {
            String storestatus;
            ArrayList<MappingJourneyPlan> journeyPlans = db.getSpecificDeviationJourneyPlan(coverageList.get(i).getStoreId());
            if (journeyPlans.size() > 0) {
                storestatus = journeyPlans.get(0).getUploadStatus();
            } else {
                storestatus = null;
                flag = false;
            }

            if (storestatus != null && !storestatus.equalsIgnoreCase(CommonString.KEY_U)) {
                if ((storestatus.equalsIgnoreCase(CommonString.KEY_C) || storestatus.equalsIgnoreCase(CommonString.KEY_P) ||
                        (storestatus.equalsIgnoreCase(CommonString.STORE_STATUS_LEAVE) && !coverageList.get(i).getReasonid().equalsIgnoreCase("11")) || storestatus.equalsIgnoreCase(CommonString.KEY_D))) {
                    flag = true;
                    break;
                }
            }
        }
        if (!flag) {
            error_msg = getResources().getString(R.string.no_data_for_upload);
        }
        return flag;
    }

    private boolean isStoreCheckedInForDBSR() {
        boolean result_flag = true;
        for (int i = 0; i < coverageList.size(); i++) {
            ArrayList<MappingJourneyPlan> journeyPlanList = db.getSpecificStore_DBSRSavedData(coverageList.get(i).getStoreId());
            String status = null;
            if (journeyPlanList.size() > 0) {
                status = journeyPlanList.get(0).getUploadStatus();
            } else {
                result_flag = false;
                status = null;
            }

            if (status != null && (status.equals(CommonString.KEY_CHECK_IN) || status.equals(CommonString.KEY_VALID))) {
                result_flag = false;
                error_msg = getResources().getString(R.string.title_store_list_checkout_current);
                break;
            }
        }
        return result_flag;
    }

    private boolean isValidForDBSR() {
        boolean flag = false;
        for (int i = 0; i < coverageList.size(); i++) {
            String storestatus;
            ArrayList<MappingJourneyPlan> journeyPlans = db.getSpecificStore_DBSRSavedData(coverageList.get(i).getStoreId());
            if (journeyPlans.size() > 0) {
                storestatus = journeyPlans.get(0).getUploadStatus();
            } else {
                storestatus = null;
                flag = false;
            }
            if (storestatus != null && !storestatus.equalsIgnoreCase(CommonString.KEY_U)) {
                if ((storestatus.equalsIgnoreCase(CommonString.KEY_C) || storestatus.equalsIgnoreCase(CommonString.KEY_P) ||
                        (storestatus.equalsIgnoreCase(CommonString.STORE_STATUS_LEAVE) && !coverageList.get(i).getReasonid().equalsIgnoreCase("11")) || storestatus.equalsIgnoreCase(CommonString.KEY_D))) {
                    flag = true;
                    break;
                }
            }
        }
        if (!flag) {
            error_msg = getResources().getString(R.string.no_data_for_upload);
        }
        return flag;
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

    class GetCredentials extends AsyncTask<Void, Void, String> {

        private ProgressDialog dialog = null;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Getting Training Data");
            dialog.setMessage("Fetching....");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub
            msg_str = "";
            try {
                // for failure
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Downloadtype", "Training_Content");
                jsonObject.put("UserName", user_name);
                jsonObject.put("Param1", "");
                jsonObject.put("Param2", "");
                jsonObject.put("SecurityToken", SecurityToken);
                String jsonString2 = jsonObject.toString();

                UploadImageWithRetrofit upload = new UploadImageWithRetrofit(context);
                String result_str = upload.downloadDataUniversal(jsonString2, CommonString.DOWNLOAD_ALL_SERVICE);

                if (result_str.equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result_str.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
                } else {
                    Gson gson = new Gson();
                    trainingContentGetterSetter = gson.fromJson(result_str, TrainingContentGetterSetter.class);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
                ResultFlag = false;
                msg_str = CommonString.MESSAGE_EXCEPTION;
                return CommonString.MESSAGE_EXCEPTION;
            } catch (SocketTimeoutException e) {
                ResultFlag = false;
                msg_str = CommonString.MESSAGE_NO_RESPONSE_SERVER;
                return CommonString.MESSAGE_NO_RESPONSE_SERVER;
            } catch (IOException e) {
                ResultFlag = false;
                msg_str = CommonString.MESSAGE_SOCKETEXCEPTION;
                return CommonString.MESSAGE_SOCKETEXCEPTION;
            } /*catch (TableNotCreatedException e) {
                ResultFlag = false;
                msg_str = CommonString.MESSAGE_ERROR_IN_EXECUTING + e.getMessage();
                return CommonString.MESSAGE_ERROR_IN_EXECUTING + e.getMessage();
            }*/ catch (JsonSyntaxException e) {
                ResultFlag = false;
                msg_str = CommonString.MESSAGE_INVALID_JSON;
                return CommonString.MESSAGE_INVALID_JSON;
            } catch (Exception e) {
                e.printStackTrace();
                ResultFlag = false;
                msg_str = CommonString.MESSAGE_EXCEPTION;
                return CommonString.MESSAGE_EXCEPTION;
            }

            if (ResultFlag) {
                return "";
            } else {
                return msg_str;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            if (result.equalsIgnoreCase("")) {
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                intent.putExtra(CommonString.KEY_QUIZ_URL_NEW, trainingContentGetterSetter.getTrainingContent().get(0));
                startActivity(intent);

            } else {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Server Not responding", Toast.LENGTH_LONG).show();
            }
        }

    }

}
