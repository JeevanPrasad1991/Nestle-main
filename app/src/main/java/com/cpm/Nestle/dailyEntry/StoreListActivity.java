package com.cpm.Nestle.dailyEntry;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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

import com.cpm.Nestle.database.NestleDb;
import com.cpm.Nestle.getterSetter.MasterProgram;
import com.cpm.Nestle.utilities.CommonFunctions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cpm.Nestle.R;
import com.cpm.Nestle.delegates.CoverageBean;
import com.cpm.Nestle.download.DownloadActivity;
import com.cpm.Nestle.geotag.GeoTagStoreList;
import com.cpm.Nestle.geotag.GeoTaggingActivity;
import com.cpm.Nestle.getterSetter.AssetMaster;
import com.cpm.Nestle.getterSetter.AuditDataGetterSetter;
import com.cpm.Nestle.getterSetter.CategoryMaster;
import com.cpm.Nestle.getterSetter.CommonChillerDataGetterSetter;
import com.cpm.Nestle.getterSetter.MappingJourneyPlan;
import com.cpm.Nestle.getterSetter.MappingAssetChecklist;
import com.cpm.Nestle.getterSetter.MasterFeedbackQuestion;
import com.cpm.Nestle.getterSetter.MenuMaster;
import com.cpm.Nestle.getterSetter.StoreProfileGetterSetter;
import com.cpm.Nestle.getterSetter.WindowMaster;
import com.cpm.Nestle.upload.Retrofit_method.UploadImageWithRetrofit;
import com.cpm.Nestle.upload.Retrofit_method.upload.UploadWithoutWaitActivity;
import com.cpm.Nestle.utilities.AlertandMessages;
import com.cpm.Nestle.utilities.CommonString;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationSettingsStatusCodes;

/**
 * Created by jeevanp on 19-08-2020.
 */

public class StoreListActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    String distanceGeoPhence;
    private static final int REQUEST_LOCATION = 1;
    boolean enabled;
    private Context context;
    private String userId, rightname;
    private boolean ResultFlag = true;
    private ArrayList<CoverageBean> coverage = new ArrayList<>();
    private ArrayList<MappingJourneyPlan> storelist = new ArrayList<>();
    private String date;
    private NestleDb db;
    private ValueAdapter adapter;
    private RecyclerView recyclerView;
    private Button search_btn;
    private LinearLayout linearlay, storelist_ll;
    private String tag_from = "";
    private Dialog dialog;
    TextView txt_label;
    private FloatingActionButton fab;
    double lat = 0.0, lon = 0.0;
    private GoogleApiClient mGoogleApiClient;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private LocationRequest mLocationRequest;
    Intent geotagIntent;
    SearchListAdapter adapter2;
    ArrayList<MappingJourneyPlan> filterdNames;
    ArrayList<MappingJourneyPlan> searchList;
    private int downloadIndex;
    SharedPreferences preferences;
    private LocationManager locationManager = null;
    private SharedPreferences.Editor editor = null;
    Integer mid;
    String visit_date, designation = "";
    String SecurityToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storelistfablayout);
        declaration();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.dialog_title);
                builder.setMessage(getResources().getString(R.string.want_download_data)).setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
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
            }
        });

        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
            createLocationRequest();
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        checkgpsEnableDevice();


        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }

    }


    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        db.open();
        mGoogleApiClient.connect();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        checkgpsEnableDevice();
        downloadIndex = preferences.getInt(CommonString.KEY_DOWNLOAD_INDEX, 0);
        getMid();
        setLitData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // NavUtils.navigateUpFromSameTask(this);
            finish();
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }


    //region ValueAdapter
    public class ValueAdapter extends RecyclerView.Adapter<ValueAdapter.MyViewHolder> {

        private LayoutInflater inflator;
        List<MappingJourneyPlan> data = Collections.emptyList();

        public ValueAdapter(Context context, List<MappingJourneyPlan> data) {
            inflator = LayoutInflater.from(context);
            this.data = data;

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            View view = inflator.inflate(R.layout.storeviewlist, parent, false);
            return new MyViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
            final MappingJourneyPlan current = data.get(position);
            ArrayList<CoverageBean> checkinTym = db.getstorecheckinTym(current, tag_from);
            if (checkinTym.size() > 0) {
                CoverageBean cCheckinTymOb = checkinTym.get(0);
                viewHolder.rl_checkin_Tym.setVisibility(View.VISIBLE);
                viewHolder.txt_checkin.setText("Checkin : " + cCheckinTymOb.getIntime());
                if (cCheckinTymOb.getOutTime() != null && !cCheckinTymOb.getOutTime().equals("")) {
                    viewHolder.txt_checkoutTime.setText("CheckOut : " + cCheckinTymOb.getOutTime());
                } else {
                    viewHolder.txt_checkoutTime.setText("CheckOut : 0:0");
                }
            } else {
                viewHolder.rl_checkin_Tym.setVisibility(View.GONE);
            }


            viewHolder.checkout_img.setImageResource(R.mipmap.checkout);
            viewHolder.txt_storename.setText(current.getStoreName().trim() + " - " + current.getStoreType().trim());
            viewHolder.txt_storeId.setText(":  " + current.getStoreId());
            viewHolder.txt_store_code.setText(":  " + current.getStoreCode());
            viewHolder.txt_store_address.setText(":  " + current.getAddress().trim());
            viewHolder.txt_last_store.setText(":  " + current.getLastMonthScore());

            if (current.getUploadStatus().equalsIgnoreCase(CommonString.KEY_U)) {
                viewHolder.rl_uploadedStatus.setVisibility(View.VISIBLE);
                viewHolder.status_img.setImageResource(R.mipmap.u_tick);
                viewHolder.rl_checkout.setVisibility(View.GONE);

            } else if (current.getUploadStatus().equalsIgnoreCase(CommonString.KEY_D)) {
                viewHolder.rl_uploadedStatus.setVisibility(View.VISIBLE);
                viewHolder.status_img.setBackgroundResource(R.mipmap.d_tick);
                viewHolder.rl_checkout.setVisibility(View.GONE);

            } else if (current.getUploadStatus().equalsIgnoreCase(CommonString.KEY_P)) {
                viewHolder.rl_uploadedStatus.setVisibility(View.VISIBLE);
                viewHolder.status_img.setBackgroundResource(R.mipmap.p_tick);
                viewHolder.rl_checkout.setVisibility(View.GONE);

            } else if (current.getUploadStatus().equalsIgnoreCase(CommonString.KEY_C)) {
                viewHolder.rl_uploadedStatus.setVisibility(View.VISIBLE);
                viewHolder.status_img.setBackgroundResource(R.mipmap.c_tick);
                viewHolder.rl_checkout.setVisibility(View.GONE);

            } else if (current.getUploadStatus().equalsIgnoreCase(CommonString.KEY_CHECK_IN) ||
                    (designation.equalsIgnoreCase("DBSR")) && current.getUploadStatus().equalsIgnoreCase(CommonString.KEY_VALID)
                    || db.getSpecificCoverageData(String.valueOf(current.getStoreId()), current.getVisitDate()).size() > 0) {
                if (chekDataforCheckout(current) || (designation.equalsIgnoreCase("DBSR"))
                        && current.getUploadStatus().equalsIgnoreCase(CommonString.KEY_VALID)) {

                    viewHolder.rl_uploadedStatus.setVisibility(View.GONE);
                    viewHolder.rl_checkout.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.rl_checkout.setVisibility(View.GONE);
                    viewHolder.rl_uploadedStatus.setVisibility(View.VISIBLE);
                    viewHolder.status_img.setImageResource(R.mipmap.diamond_icon3);
                }

            } else if (current.getUploadStatus().equalsIgnoreCase(CommonString.STORE_STATUS_LEAVE)) {
                boolean isVisitlater = false;
                for (int i = 0; i < coverage.size(); i++) {
                    if (current.getStoreId() == Integer.parseInt(coverage.get(i).getStoreId())) {
                        if (coverage.get(i).getReasonid().equals("11") || coverage.get(i).getReason().equalsIgnoreCase("Visit Later")) {
                            isVisitlater = true;
                            break;
                        }
                    }
                }
                if (isVisitlater) {
                    viewHolder.status_img.setBackgroundResource(R.drawable.visit_later);
                } else {
                    viewHolder.status_img.setBackgroundResource(R.drawable.exclamation);
                }

                viewHolder.rl_uploadedStatus.setVisibility(View.VISIBLE);
                viewHolder.rl_checkout.setVisibility(View.GONE);

            } else {
                viewHolder.rl_uploadedStatus.setVisibility(View.GONE);
                viewHolder.rl_checkout.setVisibility(View.GONE);
            }

            if(current.getGeoTag().equalsIgnoreCase("N")){
                viewHolder.imgview_navigation.setVisibility(View.INVISIBLE);
            }
            else {
                viewHolder.imgview_navigation.setVisibility(View.VISIBLE);
            }

            viewHolder.store_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int store_id = current.getStoreId();
                    if (current.getUploadStatus().equalsIgnoreCase(CommonString.KEY_U)) {
                        Snackbar.make(v, R.string.title_store_list_activity_store_already_done, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    } else if (current.getUploadStatus().equalsIgnoreCase(CommonString.KEY_D)) {
                        Snackbar.make(v, R.string.title_store_list_activity_store_data_uploaded, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    } else if (current.getUploadStatus().equalsIgnoreCase(CommonString.KEY_C)) {
                        Snackbar.make(v, R.string.title_store_list_activity_store_already_checkout, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    } else if (current.getUploadStatus().equalsIgnoreCase(CommonString.KEY_P)) {
                        Snackbar.make(v, R.string.title_store_list_activity_store_again_uploaded, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    } else if (current.getUploadStatus().equalsIgnoreCase(CommonString.STORE_STATUS_LEAVE)) {
                        boolean isVisitlater = false;
                        for (int i = 0; i < coverage.size(); i++) {
                            if (store_id == Integer.parseInt(coverage.get(i).getStoreId())) {
                                if (coverage.get(i).getReasonid().equalsIgnoreCase("11")
                                        || coverage.get(i).getReason().equalsIgnoreCase("Visit Later")) {
                                    isVisitlater = true;
                                    break;
                                }
                            }
                        }

                        if (isVisitlater) {
                            boolean entry_flag = false;
                            if (tag_from.equalsIgnoreCase("from_jcp")) {
                                //region Check for Checking in JCP stores
                                boolean entry_flag_from_jcp = true;
                                for (int j = 0; j < storelist.size(); j++) {
                                    if (storelist.get(j).getUploadStatus().equalsIgnoreCase(CommonString.KEY_CHECK_IN)) {
                                        if (store_id != storelist.get(j).getStoreId()) {
                                            entry_flag_from_jcp = false;
                                            break;
                                        } else {
                                            break;
                                        }
                                    }
                                }

                                //endregion
                                if (entry_flag_from_jcp) {
                                    entry_flag = true;
                                }
                            }

                            if (entry_flag) {
                                showMyDialog(current, isVisitlater);
                            } else {
                                Snackbar.make(v, R.string.title_store_list_checkout_current, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                            }
                        } else {
                            Snackbar.make(v, R.string.title_store_list_activity_already_store_closed, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        }
                    } else if (checkotherCheckedIn(tag_from, current).isFlagcheckin()) {
                        Snackbar.make(v, getString(R.string.title_store_list_checkout_current) + " - " + checkotherCheckedIn(tag_from, current).getStrMsg(), Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                    } else {
                        boolean entry_flag = false;
                        String msg = "";
                        //region Check for Checkin in JCP stores
                        boolean entry_flag_from_jcp = true;
                        for (int j = 0; j < storelist.size(); j++) {
                            if (storelist.get(j).getUploadStatus().equalsIgnoreCase(CommonString.KEY_CHECK_IN)) {
                                if (store_id != storelist.get(j).getStoreId()) {
                                    entry_flag_from_jcp = false;
                                    msg = getResources().getString(R.string.title_store_list_checkout_current);
                                    break;
                                } else {
                                    break;
                                }
                            }
                        }
                        //endregion
                        if (entry_flag_from_jcp) {
                            entry_flag = true;
                        }

                        if (entry_flag) {
                            if (tag_from.equalsIgnoreCase("from_jcp")) {
                                showMyDialog(current, false);
                            } else if (tag_from.equalsIgnoreCase("from_non_merchandized")) {
                                showMyNonMerchandized(current, false);
                            } else if (tag_from.equalsIgnoreCase("from_not_covered")) {
                                showMyNontCoveredMerchandized(current, false);
                            }
                        } else {
                            Snackbar.make(v, R.string.title_store_list_checkout_current, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                        }
                    }
                }
            });


            viewHolder.imgview_navigation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Intent in = new Intent(getApplicationContext(), StoreRouteActivity.class);
                    Intent in = new Intent(getApplicationContext(), StoreRouteActivity.class);
                    in.putExtra(CommonString.TAG_OBJECT,current);
                    startActivity(in);
                }
            });

            viewHolder.checkout_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(R.string.dialog_title);
                    builder.setMessage(R.string.wantcheckout)
                            .setCancelable(false)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    if (CheckNetAvailability()) {
                                        new checkoutData(current).execute();
                                    } else {
                                        Snackbar.make(recyclerView, R.string.nonetwork, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                                    }
                                }
                            })
                            .setNegativeButton(R.string.closed, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });

        }

        @SuppressWarnings("deprecation")
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

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView txt_storename, txt_storeId, txt_store_code, txt_store_address, txt_checkin, txt_checkoutTime,txt_last_store;
            LinearLayout rl_checkout, rl_uploadedStatus, rl_checkin_Tym;
            ImageView checkout_img, status_img;
            CardView store_card;
            ImageView imgview_navigation;

            public MyViewHolder(View itemView) {
                super(itemView);
                txt_last_store = (TextView) itemView.findViewById(R.id.txt_last_store);
                txt_storename = (TextView) itemView.findViewById(R.id.txt_storename);
                rl_checkout = (LinearLayout) itemView.findViewById(R.id.rl_checkout);
                checkout_img = (ImageView) itemView.findViewById(R.id.checkout_img);
                rl_uploadedStatus = (LinearLayout) itemView.findViewById(R.id.rl_uploadedStatus);
                status_img = (ImageView) itemView.findViewById(R.id.status_img);
                txt_storeId = (TextView) itemView.findViewById(R.id.txt_storeId);
                txt_store_code = (TextView) itemView.findViewById(R.id.txt_store_code);
                txt_store_address = (TextView) itemView.findViewById(R.id.txt_store_address);
                store_card = (CardView) itemView.findViewById(R.id.store_card);
                imgview_navigation = (ImageView) itemView.findViewById(R.id.navigate);

                txt_checkin = (TextView) itemView.findViewById(R.id.txt_checkin);
                txt_checkoutTime = (TextView) itemView.findViewById(R.id.txt_checkoutTime);
                rl_checkin_Tym = (LinearLayout) itemView.findViewById(R.id.rl_checkin_Tym);
            }
        }

    }
    //endregion

    //region showMyDialog
    private void showMyDialog(final MappingJourneyPlan current, final boolean isVisitLater) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogbox);
        RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radiogrpvisit);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.yes) {
                    if (!current.getGeoTag().equalsIgnoreCase(CommonString.KEY_N)) {
                        boolean flag = true;
                        if (coverage.size() > 0) {
                            for (int i = 0; i < coverage.size(); i++) {
                                if (String.valueOf(current.getStoreId()).equals(coverage.get(i).getStoreId())) {
                                    flag = false;
                                    break;
                                }
                            }
                        }

                        boolean flag_entry = true;
                        if (!designation.equalsIgnoreCase("DBSR")) {
                            int distance = 0;
                            if (flag) {
                                double store_lat = Double.parseDouble(String.valueOf(current.getLatitude()));
                                double store_lon = Double.parseDouble(String.valueOf(current.getLongitude()));
                                distanceGeoPhence = (current.getGeoTag());
                                if (store_lat != 0.0 && store_lon != 0.0) {
                                }
                            }
                            if (flag_entry) {
                                gotoActivity(flag, isVisitLater, current);
                            } else {
                                String msg = getString(R.string.you_need_to_be_in_the_store) + "\n " + getString(R.string.distance_from_the_store) + " - " + distance + " " + getString(R.string.meters);
                                dialog.cancel();
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle(getResources().getString(R.string.dialog_title));
                                builder.setMessage(msg).setCancelable(false)
                                        .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog1, int id) {
                                                dialog1.cancel();
                                            }
                                        });

                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        } else {
                            gotoActivity(flag, isVisitLater, current);
                        }
                    } else {
                        dialog.cancel();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle(getResources().getString(R.string.dialog_title));
                        builder.setMessage(R.string.first_geotag_the_store).setCancelable(false)
                                .setPositiveButton(getResources().getString(R.string.ok),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog1, int id) {
                                                dialog1.cancel();
                                                editor = preferences.edit();
                                                editor.putString(CommonString.KEY_STORE_ID, String.valueOf(current.getStoreId()));
                                                editor.putString(CommonString.KEY_STORE_NAME, current.getStoreName());
                                                editor.putString(CommonString.KEY_VISIT_DATE, current.getVisitDate());
                                                editor.commit();
                                                Intent in = new Intent(context, GeoTaggingActivity.class);
                                                in.putExtra(CommonString.TAG_OBJECT, current);
                                                in.putExtra(CommonString.TAG_FROM, tag_from);
                                                startActivity(in);
                                            }
                                        });

                        AlertDialog alert = builder.create();
                        alert.show();

                    }


                } else if (checkedId == R.id.no) {
                    dialog.cancel();
                    db.open();
                    if (current.getUploadStatus().equals(CommonString.KEY_CHECK_IN)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage(R.string.DELETE_ALERT_MESSAGE)
                                .setCancelable(false)
                                .setPositiveButton(getResources().getString(R.string.yes),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                if (mid != null) {
                                                    new DeleteCoverageData((current.getStoreId().toString()), (current.getVisitDate()),
                                                            userId, true).execute();
                                                } else {
                                                    Snackbar.make(recyclerView, CommonString.ba_message, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                                                }


                                            }
                                        }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alert = builder.create();

                        alert.show();

                    } else {
                        UpdateStore(current.getStoreId().toString());
                        Intent in = new Intent(context, NonWorkingActivity.class);
                        in.putExtra(CommonString.TAG_FROM, tag_from);
                        in.putExtra(CommonString.KEY_STORE_ID, current.getStoreId().toString());
                        startActivity(in);
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                    }

                }
            }

        });


        dialog.show();
    }

    //region UpdateStore
    private void UpdateStore(String storeid) {
        db.open();
        db.deleteTableWithStoreID(storeid);
        db.delete_storeTym(storeid);
        if (tag_from != null) {
            if (tag_from.equalsIgnoreCase("from_jcp")) {
                if (designation.equalsIgnoreCase("DBSR")) {
                    db.updateStoreStatusDBSR(storeid, storelist.get(0).getVisitDate(), CommonString.KEY_N);
                } else {
                    db.updateStoreStatus(storeid, storelist.get(0).getVisitDate(), CommonString.KEY_N);
                }
            } else if (tag_from.equalsIgnoreCase("from_non_merchandized")) {
                db.updateStoreStatusNonMerchandised(storeid, storelist.get(0).getVisitDate(), CommonString.KEY_N);
            }
        }


    }

    //endregion


    private void declaration() {
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        date = preferences.getString(CommonString.KEY_DATE, "");
        SecurityToken = preferences.getString(CommonString.KEY_SecurityToken, "");
        linearlay = (LinearLayout) findViewById(R.id.no_data_lay_ll);
        storelist_ll = (LinearLayout) findViewById(R.id.storelist_ll);
        recyclerView = (RecyclerView) findViewById(R.id.drawer_layout_recycle);
        search_btn = (Button) findViewById(R.id.search_btn);
        userId = preferences.getString(CommonString.KEY_USERNAME, "");
        rightname = preferences.getString(CommonString.KEY_RIGHTNAME, "");
        visit_date = preferences.getString(CommonString.KEY_DATE, "");
        designation = preferences.getString(CommonString.KEY_DESIGNATION, "");
        txt_label = (TextView) findViewById(R.id.txt_label);
        tag_from = getIntent().getStringExtra(CommonString.TAG_FROM);
        db = new NestleDb(context);
        db.open();
        txt_label.setText("Store List - " + date);
        geotagIntent = new Intent(context, GeoTagStoreList.class);

        if (designation.equalsIgnoreCase("DBSR")) {
            search_btn.setVisibility(View.VISIBLE);
        } else {
            search_btn.setVisibility(View.GONE);
        }

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchList.size() > 0) {
                    if (isStoreValidInDBSR()) {
                        //region Search Dialog
                        final Dialog dialog = new Dialog(context);
                        dialog.setCancelable(true);
                        dialog.setContentView(R.layout.layout_search_store);
                        dialog.setTitle("DBSR");
                        final RecyclerView searchRecyclerView = dialog.findViewById(R.id.searchRecyclerView);
                        final EditText inputSearch = dialog.findViewById(R.id.inputSearch);
                        adapter2 = new SearchListAdapter(context, searchList, dialog);
                        searchRecyclerView.setAdapter(adapter2);
                        searchRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                        dialog.show();

                        inputSearch.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                                String text = inputSearch.getText().toString().toLowerCase(Locale.getDefault()).replaceAll("[&^<>{}'$]", "").replaceFirst("^0+(?!$)", "");
                                adapter2.filter(text);
                            }

                            @Override
                            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                                // TODO Auto-generated method stub
                                adapter2 = new SearchListAdapter(context, searchList, dialog);
                                searchRecyclerView.setAdapter(adapter2);
                                searchRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                            }

                            @Override
                            public void afterTextChanged(Editable arg0) {
                                // TODO Auto-generated method stub

                            }
                        });
                        //endregion
                    } else {
                        AlertandMessages.showSnackbarMsg(linearlay, getResources().getString(R.string.title_store_list_checkout_current));
                    }
                } else {
                    Snackbar.make(linearlay, R.string.title_store_list_download_data, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                }
            }
        });

    }


    class SearchListAdapter extends RecyclerView.Adapter<MyViewHolder> {
        LayoutInflater inflater;
        ArrayList<MappingJourneyPlan> list;
        Dialog dialog;

        SearchListAdapter(Context context, ArrayList<MappingJourneyPlan> list, Dialog dialog) {
            this.list = list;
            inflater = LayoutInflater.from(context);
            this.dialog = dialog;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_add_store_list_item_view2, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final MappingJourneyPlan journeyPlan = list.get(position);

            holder.textView.setText(journeyPlan.getStoreId() + "\n" + journeyPlan.getStoreName() + "\n" + journeyPlan.getAddress());

           /* if (journeyPlan.getWeeklyUpload() != null && journeyPlan.getWeeklyUpload().equalsIgnoreCase("Y")) {
                holder.img_storeImage.setVisibility(View.VISIBLE);
            } else {
                holder.img_storeImage.setVisibility(View.GONE);
            }*/

            holder.storelist_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*if (journeyPlan.getWeeklyUpload() != null && journeyPlan.getWeeklyUpload().equalsIgnoreCase("Y")) {
                        Snackbar.make(linearlay, "Store Already done", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                    } else*/
                    {
                        dialog.cancel();
                        boolean isvalid = true;
                        int store_id = journeyPlan.getStoreId();
                        ArrayList<MappingJourneyPlan> jcp_dbsr_saved = db.getSpecificStore_DBSRSavedData(String.valueOf(journeyPlan.getStoreId()));
                        if (jcp_dbsr_saved.size() > 0) {
                            String status = jcp_dbsr_saved.get(0).getUploadStatus();
                            if (list.size() > 0) {
                                for (int i = 0; i < list.size(); i++) {
                                    if (list.get(i).getStoreId() == (store_id) && (status.equalsIgnoreCase(CommonString.KEY_C) || status.equalsIgnoreCase(CommonString.KEY_U))) {
                                        isvalid = false;
                                        break;
                                    }
                                }
                            }

                        }
                        if (isvalid) {
                            showMyNonMerchandized(journeyPlan, false);
                        } else {
                            Snackbar.make(linearlay, "Store Already done", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                        }
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void filterList(ArrayList<MappingJourneyPlan> filterdNames) {
            this.list = filterdNames;
            notifyDataSetChanged();
        }

        private void filter(String text) {
            //new array list that will hold the filtered data
            filterdNames = new ArrayList<>();
            //looping through existing elements
            for (MappingJourneyPlan s : list) {
                //if the existing elements contains the search input
                if (s.getStoreName().toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(s);
                }
            }
            //calling a method of the adapter class and passing the filtered list
            adapter2.filterList(filterdNames);
            //notifyDataSetChanged();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        RelativeLayout storelist_ll;
        Button checkoutbtn;
        ImageView img_storeImage;

        MyViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.txt_storeName);
            //checkoutbtn = view.findViewById(R.id.checkoutbtn);
            storelist_ll = view.findViewById(R.id.storelist_ll);
            img_storeImage = view.findViewById(R.id.storelistviewxml_storeico);
        }
    }


    public boolean isStoreValidInDBSR() {
        boolean isValid = true;
        String status;
        if (storelist != null && storelist.size() > 0) {
            for (int i = 0; i < storelist.size(); i++) {
                status = storelist.get(i).getUploadStatus();
                if (status != null && status.equalsIgnoreCase(CommonString.KEY_CHECK_IN)) {
                    isValid = false;
                    break;
                }
            }
        }
        return isValid;
    }


    //region checkoutData
    public class checkoutData extends AsyncTask<Void, Void, String> {
        private MappingJourneyPlan cdata;
        ProgressDialog pdialog = null;

        checkoutData(MappingJourneyPlan cdata) {
            this.cdata = cdata;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new ProgressDialog(context);
            pdialog.setTitle("Store Checkout Data");
            pdialog.setMessage("Uploading....");
            pdialog.setCancelable(false);
            pdialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            String strflag = null;
            try {
                NestleDb db = new NestleDb(context);
                db.open();
                // for failure
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("UserName", userId);
                jsonObject.put("StoreId", cdata.getStoreId());
                jsonObject.put("Latitude", lat);
                jsonObject.put("Longitude", lon);
                jsonObject.put("CheckoutDate", cdata.getVisitDate());
                jsonObject.put("SecurityToken", SecurityToken);
                String jsonString2 = jsonObject.toString();

                UploadImageWithRetrofit upload = new UploadImageWithRetrofit(context);
                String result_str = upload.downloadDataUniversal(jsonString2, CommonString.CHECKOUTDetail);

                if (result_str.equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result_str.equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                    throw new SocketTimeoutException();
                } else if (result_str.equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                    throw new JsonSyntaxException("Check out Upload");
                } else if (result_str.equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
                } else if (result_str.contains("[{Result:your SecurityToken is Invalid}])")) {
                    ResultFlag = false;
                    return strflag = "[{Result:your SecurityToken is Invalid}])";
                } else {
                    ResultFlag = true;
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

            } catch (JsonSyntaxException e) {
                ResultFlag = false;
                strflag = CommonString.MESSAGE_INVALID_JSON;

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
            pdialog.dismiss();
            if (result != null && !result.equals("") && result.equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                long id = 0;
                String store_flag_str;
                db.updateoutTime(cdata, CommonFunctions.getCurrentTime(), tag_from);
                if (designation.equalsIgnoreCase("DBSR")) {
                    db.open();
                    id = db.updateCheckoutStatusDBSR(String.valueOf(cdata.getStoreId()), CommonString.KEY_C, CommonString.TABLE_Journey_Plan_DBSR_Saved);
                } else if (tag_from.equals(CommonString.TAG_FROM_JCP)) {
                    db.open();
                    store_flag_str = "Mapping_JourneyPlan";
                    id = db.updateCheckoutStatus(String.valueOf(cdata.getStoreId()), CommonString.KEY_C, store_flag_str);
                } else if (tag_from.equals(CommonString.TAG_FROM_NON_MERCHANDIZED)) {
                    db.open();
                    store_flag_str = "JourneyPlan_NonMerchandised";
                    id = db.updateCheckoutStatus(String.valueOf(cdata.getStoreId()), CommonString.KEY_C, store_flag_str);
                } else if (tag_from.equals(CommonString.TAG_FROM_NOT_COVERED)) {
                    db.open();
                    store_flag_str = "JourneyPlan_NotCovered";
                    id = db.updateCheckoutStatus(String.valueOf(cdata.getStoreId()), CommonString.KEY_C, store_flag_str);

                }

                if (id > 0) {
                    recyclerView.invalidate();
                    adapter.notifyDataSetChanged();
                    AlertandMessages.showSnackbarMsg(fab, "Store checked out successfully");
                    setLitData();
                    Intent i = new Intent(context, UploadWithoutWaitActivity.class);
                    startActivity(i);
                }
            } else if (result.contains("[{Result:your SecurityToken is Invalid}])")) {
                showAlert(getString(R.string.invalid_token));
            } else {
                showAlert(getString(R.string.datanotfound) + " " + result);
            }
        }

    }
    //endregion

    public class DeleteCoverageData extends AsyncTask<Void, Void, String> {
        ProgressDialog pdialog = null;
        String storeID, visitDate, userId;
        boolean showDeleteCoverageMsg;

        DeleteCoverageData(String storeId, String visitDate, String userId, boolean showDeleteCoverageMsg) {
            this.storeID = storeId;
            this.visitDate = visitDate;
            this.userId = userId;
            this.showDeleteCoverageMsg = showDeleteCoverageMsg;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pdialog = new ProgressDialog(context);
            pdialog.setTitle("Coverage Deleting Data");
            pdialog.setMessage("Deleting....");
            pdialog.setCancelable(false);
            pdialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            String strflag = null;
            try {
                NestleDb db = new NestleDb(context);
                db.open();
                // for failure
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Mid", mid);
                jsonObject.put("UserName", userId);
                String jsonString2 = jsonObject.toString();

                UploadImageWithRetrofit upload = new UploadImageWithRetrofit(context);
                String result_str = upload.downloadDataUniversal(jsonString2, CommonString.DELETE_COVERAGE);
                if (result_str.equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result_str.equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                    throw new SocketTimeoutException();
                } else if (result_str.equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                    throw new JsonSyntaxException("Check out Upload");
                } else if (result_str.equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
                } else if (result_str.contains("[{Result:your SecurityToken is Invalid}])")) {
                    ResultFlag = false;
                    return strflag = "[{Result:your SecurityToken is Invalid}])";
                } else {
                    ResultFlag = true;
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

            } catch (JsonSyntaxException e) {
                ResultFlag = false;
                strflag = CommonString.MESSAGE_INVALID_JSON;

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
            pdialog.dismiss();
            if (result != null && !result.equals("") && result.equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                if (showDeleteCoverageMsg) {
                    AlertandMessages.showToastMsg(context, "Store Coverage Deleted Successfully.");
                    UpdateStore(storeID);
                    Intent in = new Intent(context, NonWorkingActivity.class);
                    in.putExtra(CommonString.KEY_STORE_ID, storeID);
                    in.putExtra(CommonString.TAG_FROM, tag_from);
                    startActivity(in);
                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

                }
            } else if (result.contains("[{Result:your SecurityToken is Invalid}])")) {
                showAlert(getString(R.string.invalid_token));
            } else {
                showAlert(getString(R.string.NodataAvailable) + " " + result);
            }
        }

    }

    private void showAlert(String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Parinaam");
        builder.setMessage(str).setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @SuppressWarnings("deprecation")
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, (Activity) context,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                AlertandMessages.showToastMsg(context, getResources().getString(R.string.notsuppoted));
                finish();
            }
            return false;
        }
        return true;
    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        int UPDATE_INTERVAL = 200;
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        int FATEST_INTERVAL = 100;
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        int DISPLACEMENT = 5;
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    /**
     * Stopping location updates
     */
    private void stopLocationUpdates() {
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
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
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
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //  Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
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

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void setLitData() {
        db.open();
        if (tag_from.equals(CommonString.TAG_FROM_JCP)) {
            txt_label.setText(getString(R.string.title_activity_store_list) + " - " + date);
            storelist = db.getStoreData(date);
        } else if (tag_from.equals(CommonString.TAG_FROM_NON_MERCHANDIZED)) {
            txt_label.setText(getString(R.string.non_merchandized_store_list) + " - " + date);
            storelist = db.getStoreDataNonmerchandise(date);
        } else if (tag_from.equals(CommonString.TAG_FROM_NOT_COVERED)) {
            txt_label.setText(getString(R.string.not_covered_merchandized_store_list) + " - " + date);
            storelist = db.getStoreDataNotCovered(date);
        }

        if (designation.equalsIgnoreCase("DBSR")) {
            searchList = db.getStoreData_DBSR(date);
            storelist = db.getStoreData_DBSR_Saved(date);
        }

        coverage = db.getCoverageData(date, tag_from);
        if (storelist.size() > 0 && downloadIndex == 0) {
            adapter = new ValueAdapter(context, storelist);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            recyclerView.setVisibility(View.VISIBLE);
            storelist_ll.setVisibility(View.VISIBLE);
            linearlay.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);

        } else {
            if (designation.equalsIgnoreCase("DBSR") && searchList.size() > 0) {
                recyclerView.setVisibility(View.VISIBLE);
                storelist_ll.setVisibility(View.VISIBLE);
                linearlay.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            } else {
                recyclerView.setVisibility(View.GONE);
                storelist_ll.setVisibility(View.GONE);
                linearlay.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
            }

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
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
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

    private void gotoActivity(boolean flag, boolean isVisitLater, MappingJourneyPlan current) {
        StoreProfileGetterSetter storePGT = db.getStoreProfileData(String.valueOf(current.getStoreId()), current.getVisitDate());

        if (flag) {
            Intent in = new Intent(context, StoreimageActivity.class);
            in.putExtra(CommonString.TAG_OBJECT, current);
            in.putExtra(CommonString.TAG_FROM, tag_from);
            startActivity(in);
            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

        } else if (storePGT.getStore_name().equals("")) {
            Intent in = new Intent(context, StoreProfileActivity.class);
            in.putExtra(CommonString.TAG_OBJECT, current);
            in.putExtra(CommonString.TAG_FROM, tag_from);
            startActivity(in);
            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        } else {
            Intent in = new Intent(context, EntryMenuActivity.class);
            in.putExtra(CommonString.TAG_OBJECT, current);
            in.putExtra(CommonString.TAG_FROM, tag_from);
            startActivity(in);
            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        }
        if (isVisitLater) {
            if (mid != null) {
                new DeleteCoverageData(current.getStoreId().toString(), (current.getVisitDate()), userId, false).execute();
                UpdateStore(current.getStoreId().toString());
                dialog.cancel();
            } else {
                Snackbar.make(recyclerView, CommonString.ba_message, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }

        }
        dialog.cancel();
    }

    private boolean chekDataforCheckout(MappingJourneyPlan journeyPlan) {
        boolean status = true;
        db.open();
        ArrayList<MenuMaster> menu_list = db.getMenuData(journeyPlan, designation);
        for (int i = 0; i < menu_list.size(); i++) {
            switch (menu_list.get(i).getMenuId()) {
                case 1:
                    db.open();
                    if (db.getmasterProgram(journeyPlan).size() > 0) {
                        if (check_allfilledSubProgram(journeyPlan)) {
                            status = true;
                        } else {
                            status = false;
                        }
                    } else {
                        status = true;
                    }

                    break;

                case 2:

                    db.open();
                    if (db.getasset(journeyPlan).size() > 0) {
                        db.open();
                        if (db.getinsertedpaidVisibility(journeyPlan).size() > 0) {
                            status = true;
                        } else {
                            status = false;
                        }
                    } else {
                        status = true;
                    }


                    break;

                case 3:
                    String cat = "";
                    boolean flag = true;
                    db.open();
                    if (db.VisicoolerCheckMapping(journeyPlan) && db.getmasterChecklist(journeyPlan, null, flag).size() > 0) {
                        db.open();
                        if (db.IsSubProgramChecklistfilled(journeyPlan, null, flag)) {
                            status = true;
                        } else {
                            status = false;
                        }
                    } else {
                        status = true;
                    }

                    break;

                case 4:
                    db.open();
                    if (db.getPOSMDeploymentData(journeyPlan).size() > 0) {
                        db.open();
                        if (db.IsPosmfilled(journeyPlan)) {
                            status = true;
                        } else {
                            status = false;
                        }

                    } else {
                        status = true;
                    }

                    break;

                case 5:
                    db.open();
                    if (db.getPromotionData(journeyPlan).size() > 0) {
                        db.open();
                        if (db.getinsertedpromotions(journeyPlan).size() > 0) {
                            status = true;
                        } else {
                            status = false;
                        }
                    } else {
                        status = true;
                    }

                    break;

                case 6:
                    flag = true;
                    db.open();
                    if (db.getcompanies().size() > 0) {
                        db.open();
                        if (db.getinsertedcomp_promotion(journeyPlan, flag).size() > 0) {
                            status = true;
                        } else {
                            status = false;
                        }
                    } else {
                        status = true;
                    }

                    break;

                case 7:
                    flag = false;
                    cat = "";
                    db.open();
                    if (db.getcategories(flag, cat).size() > 0) {
                        db.open();
                        if (db.getinsertedcomp_promotion(journeyPlan, flag).size() > 0) {
                            status = true;
                        } else {
                            status = false;
                        }
                    } else {
                        status = true;
                    }

                    break;

                case 8:
                    flag = false;
                    cat = "";
                    db.open();
                    if (db.getcategories(flag, cat).size() > 0) {
                        db.open();
                        if (db.getinsertedVQPS(journeyPlan).size() > 0) {
                            status = true;
                        } else {
                            status = false;
                        }
                    } else {
                        status = true;
                    }

                    break;

            }

            if (!status) {
                break;
            }

        }


        return status;
    }

    private boolean check_allfilledSubProgram(MappingJourneyPlan journeyPlan) {
        boolean flag = true;
        db.open();
        ArrayList<MasterProgram> programs = db.getmasterProgram(journeyPlan);
        if (programs.size() > 0) {
            for (int i = 0; i < programs.size(); i++) {
                ArrayList<MasterProgram> subprogramList = programs.get(i).getSubprogramList();
                if (subprogramList.size() > 0) {
                    for (int k = 0; k < subprogramList.size(); k++) {
                        if (db.IsSubProgramChecklistfilled(journeyPlan, subprogramList.get(k), false) == false) {
                            flag = false;
                            break;
                        }
                    }
                }

                if (flag == false) {
                    break;
                }
            }
        }

        return flag;
    }


    void getMid() {
        db.open();
        ArrayList<MappingJourneyPlan> jcp;
        if (designation.equalsIgnoreCase("DBSR")) {
            db.open();
            jcp = db.getStoreData_DBSR_Saved(visit_date);
        } else {
            db.open();
            jcp = db.getStoreData(visit_date);
        }


        if (jcp.size() > 0) {
            mid = jcp.get(0).getMID();

        }
    }

    //
    CheckinObject checkotherCheckedIn(String store_flag_str, MappingJourneyPlan jcp) {
        ArrayList<MappingJourneyPlan> other_coverage_list;
        CheckinObject checkinObject = new CheckinObject();

        if (store_flag_str.equals(CommonString.TAG_FROM_JCP)) {

            other_coverage_list = db.getSpecificStoreDataTable(CommonString.TAG_FROM_NON_MERCHANDIZED);
            if (checkingloop(other_coverage_list)) {
                checkinObject.setFlagcheckin(true);
                checkinObject.setStrMsg(getString(R.string.non_merchandized_store_list));
            } else {
                other_coverage_list = db.getSpecificStoreDataTable(CommonString.TAG_FROM_NOT_COVERED);
                if (checkingloop(other_coverage_list)) {
                    checkinObject.setFlagcheckin(true);
                    checkinObject.setStrMsg(getString(R.string.not_covered));
                }
            }
        } else if (store_flag_str.equals(CommonString.TAG_FROM_NON_MERCHANDIZED)) {
            other_coverage_list = db.getSpecificStoreDataTable(CommonString.TAG_FROM_JCP);
            if (checkingloop(other_coverage_list)) {
                checkinObject.setFlagcheckin(true);
                checkinObject.setStrMsg(getString(R.string.non_journeyplan_store_list));

            } else {
                other_coverage_list = db.getSpecificStoreDataTable(CommonString.TAG_FROM_NOT_COVERED);
                if (checkingloop(other_coverage_list)) {
                    checkinObject.setFlagcheckin(true);
                    checkinObject.setStrMsg(getString(R.string.not_covered));
                }
            }
        } else {
            other_coverage_list = db.getSpecificStoreDataTable(CommonString.TAG_FROM_JCP);
            if (checkingloop(other_coverage_list)) {
                checkinObject.setFlagcheckin(true);
                checkinObject.setStrMsg(getString(R.string.non_journeyplan_store_list));
            } else {
                other_coverage_list = db.getSpecificStoreDataTable(CommonString.TAG_FROM_NON_MERCHANDIZED);
                if (checkingloop(other_coverage_list)) {
                    checkinObject.setFlagcheckin(true);
                    checkinObject.setStrMsg(getString(R.string.non_merchandized_store_list));

                }
            }
        }

        return checkinObject;

    }

    class CheckinObject {
        boolean flagcheckin = false;
        String strMsg = "";

        public boolean isFlagcheckin() {
            return flagcheckin;
        }

        public void setFlagcheckin(boolean flagcheckin) {
            this.flagcheckin = flagcheckin;
        }

        public String getStrMsg() {
            return strMsg;
        }

        public void setStrMsg(String strMsg) {
            this.strMsg = strMsg;
        }
    }

    private boolean checkingloop(ArrayList<MappingJourneyPlan> other_coverage_list) {
        boolean flag_checked_in = false;
        for (int i = 0; i < other_coverage_list.size(); i++) {
            if (other_coverage_list.get(i).getUploadStatus().equalsIgnoreCase(CommonString.KEY_CHECK_IN) ||
                    other_coverage_list.get(i).getUploadStatus().equalsIgnoreCase(CommonString.KEY_VALID)) {
                flag_checked_in = true;
                break;
            }
        }
        return flag_checked_in;
    }

    private void showMyNonMerchandized(final MappingJourneyPlan current, final boolean isVisitLater) {
        //   dialog = new Dialog(this);
        if (!current.getGeoTag().equalsIgnoreCase("N")) {
            boolean flag = true;
            if (coverage.size() > 0) {
                for (int i = 0; i < coverage.size(); i++) {
                    if (String.valueOf(current.getStoreId()).equals(coverage.get(i).getStoreId())) {
                        flag = false;
                        break;
                    }
                }
            }
            boolean flag_entry = true;

            int distance = 0;
            if (flag) {
                double store_lat = Double.parseDouble(String.valueOf(current.getLatitude()));
                double store_lon = Double.parseDouble(String.valueOf(current.getLongitude()));
                // distanceGeoPhence = current.getGeoFencing();
                if (store_lat != 0.0 && store_lon != 0.0) {
                }
            }

            if (flag_entry) {

                if (flag) {

                    Intent in = new Intent(context, StoreimageActivity.class);
                    in.putExtra(CommonString.TAG_OBJECT, current);
                    in.putExtra(CommonString.TAG_FROM, tag_from);
                    startActivity(in);
                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

                } else {
                    db.open();
                    StoreProfileGetterSetter storePGT = db.getStoreProfileData(String.valueOf(current.getStoreId()), current.getVisitDate());
                    if (storePGT.getStore_name().equalsIgnoreCase("")) {
                        Intent in = new Intent(context, StoreProfileActivity.class);
                        in.putExtra(CommonString.TAG_OBJECT, current);
                        in.putExtra(CommonString.TAG_FROM, tag_from);
                        startActivity(in);
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                    } else {
                        Intent in = new Intent(context, EntryMenuActivity.class);
                        in.putExtra(CommonString.TAG_OBJECT, current);
                        in.putExtra(CommonString.TAG_FROM, tag_from);
                        startActivity(in);
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                    }
                }

            } else {

                String msg = getString(R.string.you_need_to_be_in_the_store) + "\n " + getString(R.string.distance_from_the_store) + " - " + distance + " " + getString(R.string.meters);
                //  dialog.cancel();
                AlertDialog.Builder builder = new AlertDialog.Builder(StoreListActivity.this);
                builder.setTitle(getResources().getString(R.string.dialog_title));
                builder.setMessage(msg).setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.ok),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog1,
                                                        int id) {

                                        dialog1.cancel();
                                    }
                                });

                AlertDialog alert = builder.create();
                alert.show();
            }

        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(getResources().getString(R.string.dialog_title));
            builder.setMessage(R.string.first_geotag_the_store).setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog1,
                                                    int id) {
                                    dialog1.cancel();
                                    //usk
                                    //startActivity(geotagIntent);

                                    editor = preferences.edit();
                                    editor.putString(CommonString.KEY_STORE_ID, String.valueOf(current.getStoreId()));
                                    editor.putString(CommonString.KEY_STORE_NAME, current.getStoreName());
                                    editor.putString(CommonString.KEY_VISIT_DATE, current.getVisitDate());
                                    editor.commit();
                                    Intent in = new Intent(context, GeoTaggingActivity.class);
                                    in.putExtra(CommonString.TAG_OBJECT, current);
                                    in.putExtra(CommonString.TAG_FROM, tag_from);
                                    startActivity(in);
                                }
                            });

            AlertDialog alert = builder.create();
            alert.show();
        }

    }

    private void showMyNontCoveredMerchandized(final MappingJourneyPlan current, final boolean isVisitLater) {
        if (!current.getGeoTag().equalsIgnoreCase("N")) {
            boolean flag = true;
            if (coverage.size() > 0) {
                for (int i = 0; i < coverage.size(); i++) {
                    if (String.valueOf(current.getStoreId()).equals(coverage.get(i).getStoreId())) {
                        flag = false;
                        break;
                    }
                }
            }
            boolean flag_entry = true;

            int distance = 0;
            if (flag) {
                double store_lat = Double.parseDouble(String.valueOf(current.getLatitude()));
                double store_lon = Double.parseDouble(String.valueOf(current.getLongitude()));
                // distanceGeoPhence = current.getGeoFencing();
                if (store_lat != 0.0 && store_lon != 0.0) {

                }
            }

            if (flag_entry) {
                if (flag) {
                    Intent in = new Intent(context, StoreimageActivity.class);
                    in.putExtra(CommonString.TAG_OBJECT, current);
                    in.putExtra(CommonString.TAG_FROM, tag_from);
                    startActivity(in);
                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                } else {
                    StoreProfileGetterSetter storePGT = db.getStoreProfileData(String.valueOf(current.getStoreId()), current.getVisitDate());
                    if (storePGT.getStore_name().equals("")) {
                        Intent in = new Intent(context, StoreProfileActivity.class);
                        in.putExtra(CommonString.TAG_OBJECT, current);
                        in.putExtra(CommonString.TAG_FROM, tag_from);
                        startActivity(in);
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                    } else {
                        Intent in = new Intent(context, EntryMenuActivity.class);
                        in.putExtra(CommonString.TAG_OBJECT, current);
                        in.putExtra(CommonString.TAG_FROM, tag_from);
                        startActivity(in);
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                    }
                }

            } else {

                String msg = getString(R.string.you_need_to_be_in_the_store) + "\n " + getString(R.string.distance_from_the_store) + " - " + distance + " " + getString(R.string.meters);
                // dialog.cancel();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(getResources().getString(R.string.dialog_title));
                builder.setMessage(msg).setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.ok),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog1, int id) {

                                        dialog1.cancel();
                                    }
                                });

                AlertDialog alert = builder.create();
                alert.show();
            }

        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(getResources().getString(R.string.dialog_title));
            builder.setMessage(R.string.first_geotag_the_store).setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog1, int id) {
                            dialog1.cancel();
                            editor = preferences.edit();
                            editor.putString(CommonString.KEY_STORE_ID, String.valueOf(current.getStoreId()));
                            editor.putString(CommonString.KEY_STORE_NAME, current.getStoreName());
                            editor.putString(CommonString.KEY_VISIT_DATE, current.getVisitDate());
                            editor.commit();
                            Intent in = new Intent(context, GeoTaggingActivity.class);
                            in.putExtra(CommonString.TAG_OBJECT, current);
                            in.putExtra(CommonString.TAG_FROM, tag_from);
                            startActivity(in);
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
        }

    }

}




