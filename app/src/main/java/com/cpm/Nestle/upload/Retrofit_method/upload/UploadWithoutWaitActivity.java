package com.cpm.Nestle.upload.Retrofit_method.upload;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.WindowManager;

import com.cpm.Nestle.R;
import com.cpm.Nestle.database.NestleDb;
import com.cpm.Nestle.delegates.CoverageBean;
import com.cpm.Nestle.getterSetter.MappingJourneyPlan;
import com.cpm.Nestle.upload.Retrofit_method.UploadImageWithRetrofit;
import com.cpm.Nestle.utilities.CommonString;

import java.util.ArrayList;

/**
 * Created by on 11/7/2017.
 */

public class UploadWithoutWaitActivity extends AppCompatActivity {

    NestleDb db;
    ArrayList<CoverageBean> coverageList;
    String date, userId, app_version,SecurityToken;
    String Path, app_ver;
    private ProgressDialog pb;
    private SharedPreferences preferences;
    Toolbar toolbar;
    MappingJourneyPlan jcpGetset;
    Context context;
    private String tag_from = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        context = this;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (getIntent().getSerializableExtra(CommonString.TAG_OBJECT) != null) {
            jcpGetset = (MappingJourneyPlan) getIntent().getSerializableExtra(CommonString.TAG_OBJECT);
        }

        if (getIntent().getStringExtra(CommonString.TAG_FROM) != null) {
            tag_from = getIntent().getStringExtra(CommonString.TAG_FROM);
        }


        try {
            app_ver = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        date = preferences.getString(CommonString.KEY_DATE, null);
        userId = preferences.getString(CommonString.KEY_USERNAME, null);
        SecurityToken = preferences.getString(CommonString.KEY_SecurityToken, "");
        app_version = preferences.getString(CommonString.KEY_VERSION, null);
        toolbar.setTitleTextAppearance(context, R.style.changestext_sizefor_mobile);
        toolbar.setTitle("Upload - " + date);
        Path = CommonString.FILE_PATH;
        db = new NestleDb(context);
        db.open();
        coverageList = db.getCoverageData(date,tag_from);
        pb = new ProgressDialog(context);
        pb.setCancelable(false);
        pb.setMessage("Uploading Data");
        if (pb != null && (!pb.isShowing())) {
            pb.show();
        }
        UploadImageWithRetrofit upload = new UploadImageWithRetrofit(context, db, pb, CommonString.TAG_FROM_CURRENT);
        upload.uploadDataUsingCoverageRecursive(coverageList, 0);
    }


}
