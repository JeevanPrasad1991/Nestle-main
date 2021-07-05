package com.cpm.Nestle.dailyEntry;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cpm.Nestle.R;
import com.cpm.Nestle.database.NestleDb;
import com.cpm.Nestle.getterSetter.AvailabilityGetterSetter;
import com.cpm.Nestle.getterSetter.MappingJourneyPlan;
import com.cpm.Nestle.getterSetter.MasterAsset;
import com.cpm.Nestle.getterSetter.MasterCompany;
import com.cpm.Nestle.getterSetter.MenuMaster;
import com.cpm.Nestle.utilities.AlertandMessages;
import com.cpm.Nestle.utilities.CommonFunctions;
import com.cpm.Nestle.utilities.CommonString;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import static com.cpm.Nestle.dailyEntry.StoreimageActivity.convertBitmap;

public class VQPSActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Spinner spn_visibility_element, spn_category;
    Context context;
    Toolbar toolBar;
    SharedPreferences preferences;
    ArrayList<AvailabilityGetterSetter> categories = new ArrayList<>();
    ArrayList<MasterAsset> visibilityelements = new ArrayList<>();
    ArrayList<MasterCompany> inserteslistData = new ArrayList<>();
    NestleDb db;
    private ArrayAdapter<CharSequence> visibility_adapter, categoryAdapter;
    ImageView img_button_yes, img_button_no, img_VQPS;
    LinearLayout ll_hide;
    FloatingActionButton fab, btn_add;
    MappingJourneyPlan jcpGetset;
    MenuMaster menuMaster;
    VQPSAdapter adapter;
    RecyclerView recyclerview_visibility;
    boolean sampleaddflag = false, ischangedflag = false, yes_flag = false;
    String Ispresent = "", visit_date_formatted = "", username = "",
            visit_date, asset_name = "", categoryName = "", str_img1 = "", _pathforcheck, _path;
    int assetId = 0, categoryId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vqps);
        declaration();
        SetSpinnerData();
        setDataToListView();
    }

    private void declaration() {
        context = this;
        db = new NestleDb(context);
        db.open();
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        spn_visibility_element = (Spinner) findViewById(R.id.spn_visibility_element);
        spn_category = (Spinner) findViewById(R.id.spn_category);
        recyclerview_visibility = (RecyclerView) findViewById(R.id.recyclerview_visibility);
        btn_add = (FloatingActionButton) findViewById(R.id.btn_add);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        img_button_yes = (ImageView) findViewById(R.id.img_button_yes);
        img_button_no = (ImageView) findViewById(R.id.img_button_no);
        img_VQPS = (ImageView) findViewById(R.id.img_VQPS);
        ll_hide = (LinearLayout) findViewById(R.id.ll_hide);

        if (getIntent().getSerializableExtra(CommonString.TAG_OBJECT) != null && getIntent().getSerializableExtra(CommonString.KEY_MENU_ID) != null) {
            jcpGetset = (MappingJourneyPlan) getIntent().getSerializableExtra(CommonString.TAG_OBJECT);
            menuMaster = (MenuMaster) getIntent().getSerializableExtra(CommonString.KEY_MENU_ID);

        }

        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        visit_date = preferences.getString(CommonString.KEY_DATE, "");
        visit_date_formatted = preferences.getString(CommonString.KEY_YYYYMMDD_DATE, "");
        username = preferences.getString(CommonString.KEY_USERNAME, "");
        setSupportActionBar(toolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitleTextAppearance(context, R.style.changestext_sizefor_mobile);
        getSupportActionBar().setTitle(menuMaster.getMenuName() + " - " + visit_date);
        img_button_yes.setOnClickListener(this);
        img_button_no.setOnClickListener(this);
        img_VQPS.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        fab.setOnClickListener(this);

    }


    public void SetSpinnerData() {
        ///for adapter visibility elements
        db.open();
        visibilityelements = db.getassetforvqps();
        visibility_adapter = new ArrayAdapter<CharSequence>(context, android.R.layout.simple_spinner_item);
        visibility_adapter.add("- Select -");

        for (int i = 0; i < visibilityelements.size(); i++) {

            visibility_adapter.add(visibilityelements.get(i).getAssetName());
        }

        spn_visibility_element.setAdapter(visibility_adapter);
        visibility_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_visibility_element.setOnItemSelectedListener(this);

///for adapter category
        db.open();
        categories = db.getcategories(false, "");
        categoryAdapter = new ArrayAdapter<CharSequence>(context, android.R.layout.simple_spinner_item);
        categoryAdapter.add("- Select -");

        for (int i = 0; i < categories.size(); i++) {

            categoryAdapter.add(categories.get(i).getCategoryName());
        }

        spn_category.setAdapter(categoryAdapter);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_category.setOnItemSelectedListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (ischangedflag) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                builder.setTitle(getString(R.string.main_menu_activity_name)).setMessage(CommonString.ONBACK_ALERT_MESSAGE).setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                                VQPSActivity.this.finish();
                                dialog.dismiss();
                            }
                        }).setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                android.app.AlertDialog alert = builder.create();
                alert.show();
            } else {
                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                VQPSActivity.this.finish();
            }

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (ischangedflag) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
            builder.setTitle(getString(R.string.main_menu_activity_name)).setMessage(CommonString.ONBACK_ALERT_MESSAGE).setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                            VQPSActivity.this.finish();
                            dialog.dismiss();
                        }
                    }).setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });
            android.app.AlertDialog alert = builder.create();
            alert.show();
        } else {
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
            VQPSActivity.this.finish();

        }


    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("MakeMachine", "resultCode: " + resultCode);
        switch (resultCode) {
            case 0:
                Log.i("MakeMachine", "User cancelled");
                break;
            case -1:

                if (new File(CommonString.FILE_PATH + _pathforcheck).exists()) {
                    try {

                        convertBitmap(CommonString.FILE_PATH + _pathforcheck);
                        String metadata = CommonFunctions.setMetadataAtImages(jcpGetset.getStoreName(),
                                String.valueOf(jcpGetset.getStoreId()), menuMaster.getMenuName(), username);
                        CommonFunctions.addMetadataAndTimeStampToImage(context, _path, metadata, jcpGetset.getVisitDate());
                        str_img1 = _pathforcheck;
                        img_VQPS.setImageResource(R.mipmap.camera_green);

                        _pathforcheck = "";

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.img_VQPS:
                _pathforcheck = jcpGetset.getStoreId() + "_VQPSImg-" + visit_date.replace("/", "") + getCurrentTime().replace(":", "") + ".jpg";
                _path = CommonString.FILE_PATH + _pathforcheck;
                CommonFunctions.startAnncaCameraActivity(context, _path, null, false, CommonString.CAMERA_FACE_REAR);

                break;

            case R.id.img_button_yes:
                ischangedflag = true;
                yes_flag = true;
                Ispresent = "1";
                if (inserteslistData.size() > 0) {
                    if (inserteslistData.get(0).getIspresent().equals("0")) {
                        inserteslistData.clear();
                        db.open();
                        db.deleteVQPS(jcpGetset, null);
                    }
                }

                img_button_yes.setImageResource(R.mipmap.yes_green);
                img_button_no.setImageResource(R.mipmap.no_white);
                ll_hide.setVisibility(View.VISIBLE);
                recyclerview_visibility.setVisibility(View.VISIBLE);

                break;


            case R.id.img_button_no:
                db.open();
                ischangedflag = true;
                if (db.getinsertedVQPS(jcpGetset).size() > 0) {
                    final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context).setTitle(R.string.main_menu_activity_name).setMessage(getString(R.string.messageM));
                    builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            clear_not_present();
                            dialog.dismiss();
                        }
                    }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if (yes_flag) {
                                img_button_yes.setImageResource(R.mipmap.yes_green);
                                img_button_no.setImageResource(R.mipmap.no_white);
                                ll_hide.setVisibility(View.VISIBLE);
                                recyclerview_visibility.setVisibility(View.VISIBLE);

                            } else {
                                img_button_yes.setImageResource(R.mipmap.yes_white);
                                img_button_no.setImageResource(R.mipmap.no_white);
                                ll_hide.setVisibility(View.GONE);
                                recyclerview_visibility.setVisibility(View.GONE);

                            }

                        }
                    });

                    builder.show();


                } else {
                    clear_not_present();
                }
                break;


            case R.id.btn_add:
                try {
                    if (!Ispresent.equals("")) {
                        if (validation()) {
                            if (validationDuplication()) {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(context).setTitle(getString(R.string.main_menu_activity_name))
                                        .setMessage("Are you sure you want to add ?")
                                        .setCancelable(false).setPositiveButton("Yes",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        db.open();
                                                        MasterCompany comp = new MasterCompany();
                                                        comp.setCategorY(categoryName);
                                                        comp.setCategoryId(categoryId);
                                                        comp.setAsset_name(asset_name);
                                                        comp.setAssetId(assetId);

                                                        comp.setComp_img1(str_img1);
                                                        comp.setIspresent("1");
                                                        inserteslistData.add(comp);

                                                        adapter = new VQPSAdapter(context, inserteslistData);
                                                        recyclerview_visibility.setVisibility(View.VISIBLE);
                                                        recyclerview_visibility.setAdapter(adapter);
                                                        recyclerview_visibility.setLayoutManager(new LinearLayoutManager(context));
                                                        adapter.notifyDataSetChanged();
                                                        clear_all_field();
                                                        sampleaddflag = true;
                                                        dialog.dismiss();
                                                        AlertandMessages.showToastMsg(context, "Data has been added.");

                                                    }
                                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();
                                            }
                                        });
                                AlertDialog alert = builder1.create();
                                alert.show();
                            }
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case R.id.fab:
                if (inserteslistData.size() > 0) {
                    if (sampleaddflag) {
                        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(context).setTitle(getString(R.string.main_menu_activity_name));
                        builder1.setMessage(getString(R.string.alertsaveData))
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        db.open();
                                        db.insertVQPS(jcpGetset, inserteslistData);
                                        VQPSActivity.this.finish();
                                        sampleaddflag = false;
                                        AlertandMessages.showToastMsg(context, "Data has been saved");
                                    }
                                })
                                .setNegativeButton("No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {
                                                dialog.cancel();
                                            }
                                        });
                        android.app.AlertDialog alert1 = builder1.create();
                        alert1.show();
                    } else {
                        AlertandMessages.showToastMsg(context, "Please add data");
                    }
                } else {
                    AlertandMessages.showToastMsg(context, "Please add data");
                }
                break;


        }
    }

    private void clear_not_present() {
        try {
            yes_flag = false;
            Ispresent = "0";
            img_button_no.setImageResource(R.mipmap.no_red);
            img_button_yes.setImageResource(R.mipmap.yes_white);
            ll_hide.setVisibility(View.GONE);
            recyclerview_visibility.setVisibility(View.GONE);

            if (inserteslistData.size() > 0) {
                inserteslistData.clear();
                db.open();
                db.deleteVQPS(jcpGetset, null);
            }

            MasterCompany comp = new MasterCompany();
            comp.setCategorY("");
            comp.setCategoryId(0);
            comp.setAssetId(0);
            comp.setAsset_name("");
            comp.setComp_img1("");
            comp.setIspresent("0");

            inserteslistData.add(comp);
            sampleaddflag = true;
            clear_all_field();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clear_all_field() {
        spn_visibility_element.setSelection(0);
        spn_category.setSelection(0);
        asset_name = "";
        assetId = 0;
        categoryName = "";
        categoryId = 0;
        str_img1 = "";
        img_VQPS.setImageResource(R.mipmap.camera_orange);
    }

    public String getCurrentTime() {
        Calendar m_cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String cdate = formatter.format(m_cal.getTime());
        return cdate;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {

            case R.id.spn_category:
                if (position != 0) {
                    categoryId = (categories.get(position - 1).getCategoryId());
                    categoryName = categories.get(position - 1).getCategoryName();
                } else {
                    categoryId = 0;
                    categoryName = "";
                }

                break;


            case R.id.spn_visibility_element:
                if (position != 0) {
                    assetId = visibilityelements.get(position - 1).getAssetId();
                    asset_name = visibilityelements.get(position - 1).getAssetName();
                } else {
                    assetId = 0;
                    asset_name = "";
                }

                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public boolean validation() {
        boolean value = true;
        if (Ispresent.equals("")) {
            AlertandMessages.showToastMsg(context, "Please select Present");
            value = false;
        } else {
            if (Ispresent.equals("1")) {
                if (spn_category.getSelectedItemId() == 0) {
                    AlertandMessages.showToastMsg(context, "Please Select Category");
                    value = false;
                } else if (spn_visibility_element.getSelectedItemId() == 0) {
                    AlertandMessages.showToastMsg(context, "Please Select Visibility Elements");
                    value = false;
                } else if (str_img1.equals("")) {
                    AlertandMessages.showToastMsg(context, "Please Capture Image");
                    value = false;
                } else {
                    value = true;
                }
            } else {
                value = true;
            }


        }
        return value;
    }


    private class VQPSAdapter extends RecyclerView.Adapter<VQPSAdapter.MyViewHolder> {
        private LayoutInflater inflator;
        Context context;
        ArrayList<MasterCompany> insertedlist_Data;

        VQPSAdapter(Context context, ArrayList<MasterCompany> insertedlist_Data) {
            inflator = LayoutInflater.from(context);
            this.context = context;
            this.insertedlist_Data = insertedlist_Data;

        }

        @Override
        public int getItemCount() {
            return insertedlist_Data.size();

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflator.inflate(R.layout.item_vqps, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final MasterCompany current = insertedlist_Data.get(position);

            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (current.getKeyId() == null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(getString(R.string.main_menu_activity_name));
                        builder.setMessage("Are you sure you want to Delete ?")
                                .setCancelable(false)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                insertedlist_Data.remove(position);
                                                if (insertedlist_Data.size() > 0) {
                                                    adapter = new VQPSAdapter(context, insertedlist_Data);
                                                    recyclerview_visibility.setAdapter(adapter);
                                                    adapter.notifyDataSetChanged();
                                                }
                                                notifyDataSetChanged();
                                            }
                                        })
                                .setNegativeButton("No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {
                                                dialog.cancel();
                                            }
                                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(getString(R.string.main_menu_activity_name));
                        builder.setMessage("Are you sure you want to Delete ?")
                                .setCancelable(false)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                db.open();
                                                db.deleteVQPS(jcpGetset, current.getKeyId());
                                                insertedlist_Data.remove(position);
                                                if (insertedlist_Data.size() > 0) {
                                                    adapter = new VQPSAdapter(context, insertedlist_Data);
                                                    recyclerview_visibility.setAdapter(adapter);
                                                    adapter.notifyDataSetChanged();
                                                }
                                                notifyDataSetChanged();
                                                dialog.dismiss();
                                            }
                                        })
                                .setNegativeButton("No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {
                                                dialog.dismiss();
                                            }
                                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }


                }
            });


            holder.txt_cate.setText("CategorY : " + current.getCategorY());
            holder.txt_cate.setId(position);

            holder.txt_visibility.setText("Visibility Element : " + current.getAsset_name());
            holder.txt_visibility.setId(position);

            holder.remove.setId(position);
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView txt_cate, txt_visibility;
            ImageView remove;

            public MyViewHolder(View convertView) {
                super(convertView);
                txt_cate = (TextView) convertView.findViewById(R.id.txt_cate);
                txt_visibility = (TextView) convertView.findViewById(R.id.txt_visibility);
                remove = (ImageView) convertView.findViewById(R.id.imgDelete);
            }
        }
    }

    public void setDataToListView() {
        try {
            db.open();
            inserteslistData = db.getinsertedVQPS(jcpGetset);
            if (inserteslistData.size() > 0) {
                try {
                    if (inserteslistData.size() > 0 && inserteslistData.get(0).getIspresent().equals("1")) {
                        img_button_yes.setImageResource(R.mipmap.yes_green);
                        Ispresent = "1";
                        sampleaddflag = true;
                        img_button_no.setImageResource(R.mipmap.no_white);
                        recyclerview_visibility.setVisibility(View.VISIBLE);
                        ll_hide.setVisibility(View.VISIBLE);
                        Collections.reverse(inserteslistData);
                        adapter = new VQPSAdapter(context, inserteslistData);
                        recyclerview_visibility.setAdapter(adapter);
                        recyclerview_visibility.setLayoutManager(new LinearLayoutManager(context));
                        adapter.notifyDataSetChanged();

                    } else if (inserteslistData.size() > 0 && inserteslistData.get(0).getIspresent().equals("0")) {
                        img_button_no.setImageResource(R.mipmap.no_red);
                        img_button_yes.setImageResource(R.mipmap.yes_white);
                        Ispresent = "0";
                        sampleaddflag = true;
                        recyclerview_visibility.setVisibility(View.GONE);
                        ll_hide.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                img_button_no.setImageResource(R.mipmap.no_white);
                img_button_yes.setImageResource(R.mipmap.yes_white);
                recyclerview_visibility.setVisibility(View.GONE);
                ll_hide.setVisibility(View.GONE);

                Ispresent = "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean validationDuplication() {
        boolean value = true;
        if (inserteslistData.size() > 0) {
            for (int i = 0; i < inserteslistData.size(); i++) {
                if (inserteslistData.get(i).getAssetId() == assetId && inserteslistData.get(i).getCategoryId() == categoryId) {
                    value = false;
                    AlertandMessages.showToastMsg(context, "Already added. Please Select Another");
                    break;
                }
            }
        }

        return value;
    }

}
