package com.cpm.Nestle.download;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cpm.Nestle.R;
import com.cpm.Nestle.database.NestleDb;
import com.cpm.Nestle.upload.Retrofit_method.UploadImageWithRetrofit;
import com.cpm.Nestle.utilities.CommonString;

import org.json.JSONObject;

import java.util.ArrayList;

public class DownloadActivity extends AppCompatActivity {
    NestleDb db;
    String userId, date, rightname, SecurityToken;
    private SharedPreferences preferences = null;
    Context context;
    int downloadindex = 0;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new NestleDb(context);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        userId = preferences.getString(CommonString.KEY_USERNAME, null);
        date = preferences.getString(CommonString.KEY_DATE, "");
        rightname = preferences.getString(CommonString.KEY_DESIGNATION, "");
        SecurityToken = preferences.getString(CommonString.KEY_SecurityToken, "");
        downloadindex = preferences.getInt(CommonString.KEY_DOWNLOAD_INDEX, 0);
        toolbar.setTitleTextAppearance(context, R.style.changestext_sizefor_mobile);
        setTitle("Download - " + date);
        UploadDataTask();
    }

    public void UploadDataTask() {
        try {
            ArrayList<String> keysList = new ArrayList<>();
            ArrayList<String> jsonList = new ArrayList<>();
            ArrayList<String> KeyNames = new ArrayList<>();
            KeyNames.clear();
            keysList.clear();
            keysList.add("Table_Structure");
            if (rightname.equalsIgnoreCase("DBSR")) {
                keysList.add("JournyPlan_DBSR");
            } else {
                keysList.add("Mapping_JourneyPlan");
            }

            keysList.add("JourneyPlan_NonMerchandised");
            keysList.add("Master_NonProgramReason");
            keysList.add("Master_DriveNonVisibility");
            keysList.add("Mapping_RD_VisibilityDrive");
            keysList.add("Mapping_VQPS_VisibilityDrive");
            keysList.add("Non_Working_Reason");
            keysList.add("Menu_Master");
            keysList.add("Mapping_Menu");
            keysList.add("Master_Program");
            keysList.add("Master_Checklist");
            keysList.add("Master_ChecklistAnswer");
            keysList.add("Mapping_SubProgramChecklist");
            keysList.add("Mapping_Program");
            keysList.add("Mapping_VisicoolerChecklist");
            keysList.add("Mapping_Visicooler");
            keysList.add("Master_Posm");
            keysList.add("Mapping_Posm");
            keysList.add("Non_Posm_Reason");

            keysList.add("Master_Category");
            keysList.add("Master_Brand");
            keysList.add("Master_Company");
            keysList.add("Master_Asset");
            keysList.add("Mapping_PaidVisibility");
            keysList.add("Master_NonAssetReason");
            keysList.add("Master_AssetLocation");
            keysList.add("Mapping_Promotion");
            keysList.add("Master_PromoType");
            keysList.add("Master_NonPromotionReason");
            // keysList.add("JourneyPlan_NonMerchandised");
            //keysList.add("Attendance_Report");
            // keysList.add("Merchandiser_Performance");
            // keysList.add("JourneyPlan_NotCovered");

            if (keysList.size() > 0) {
                for (int i = 0; i < keysList.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Downloadtype", keysList.get(i));
                    jsonObject.put("UserName", userId);
                    jsonObject.put("Param1", "");
                    jsonObject.put("Param2", "");
                    jsonObject.put("SecurityToken", SecurityToken);
                    jsonList.add(jsonObject.toString());
                    KeyNames.add(keysList.get(i));
                }

                if (jsonList.size() > 0) {
                    ProgressDialog pd = new ProgressDialog(context);
                    pd.setCancelable(false);
                    pd.setMessage("Downloading Data" + "(" + "/" + ")");
                    pd.show();
                    UploadImageWithRetrofit downloadData = new UploadImageWithRetrofit(context, db, pd, CommonString.TAG_FROM_CURRENT);
                    downloadData.listSize = jsonList.size();
                    downloadData.downloadDataUniversalWithoutWait(jsonList, KeyNames, downloadindex, CommonString.DOWNLOAD_ALL_SERVICE, 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
