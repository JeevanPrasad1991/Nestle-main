package com.cpm.Nestle.visitor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cpm.Nestle.R;
import com.cpm.Nestle.database.NestleDb;
import com.cpm.Nestle.upload.Retrofit_method.UploadImageWithRetrofit;
import com.cpm.Nestle.upload.Retrofit_method.UploadImageWithRetrofitOne;
import com.cpm.Nestle.utilities.AlertandMessages;
import com.cpm.Nestle.utilities.CommonFunctions;
import com.cpm.Nestle.utilities.CommonString;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;

public class VisitorLoginActivity extends AppCompatActivity implements View.OnClickListener {
    private NestleDb database;
    LinearLayout heading;
    FloatingActionButton fab_save;
    RecyclerView recyclerView;
    VisitorDetail visitorLoginGetterSetter, visitordata;
    ArrayList<VisitorDetail> visitorLoginData = new ArrayList<>();
    TextView tv_in_time, tv_out_time;
    String empid, emp_code, name, designation;
    boolean isUpdate = false;
    String intime_img, outtime_img;
    TextView tvname, tvdesignation, txt_user;
    String error_msg = "", msg_str = "";
    EditText et_empid;
    Button btngo;
    protected String _pathforcheck = "";
    protected String _path, str;
    boolean ResultFlag = true;
    boolean camin_clicked = false, camout_clicked = false;
    Activity activity;
    ProgressBar progressBar;
    String visit_date, username, visit_date_formatted;
    RelativeLayout rel_intime, rel_outtime;
    ImageView img_intime, img_outtime;
    SharedPreferences preferences;
    String Path;
    ImageView imgcam_in, imgcam_out;
    Context context;
    VisitorDetailGetterSetter vistorObject;
    Button btnclear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_login);
        context = this;
        declaration();
    }

    @Override
    protected void onResume() {
        super.onResume();
        database.open();
        setLoginData();
    }

    public void setLoginData() {
        visitorLoginData = database.getVisitorLoginData(visit_date);
        if (visitorLoginData.size() > 0) {
            heading.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new RecyclerAdapter(context));
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            heading.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        switch (id) {
            case R.id.fab_save:
                name = tvname.getText().toString();
                designation = tvdesignation.getText().toString();
                if (check_condition()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(getString(R.string.main_menu_activity_name));
                    builder.setMessage(getString(R.string.alertsaveData))
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            if (isUpdate) {
                                                String out_time = tv_out_time.getText().toString();
                                                visitordata.setOut_time(out_time);
                                                database.open();
                                                database.updateOutTimeVisitorLoginData(visitordata.getOut_time_img(), out_time, String.valueOf(visitordata.getEmpId()));
                                                if (CheckNetAvailability()) {
                                                    new UploadVisitorData().execute();
                                                } else {
                                                    AlertandMessages.showToastMsg(context, getResources().getString(R.string.nonetwork));
                                                    clearVisitorData();
                                                    setLoginData();
                                                }

                                            } else {
                                                visitorLoginGetterSetter = new VisitorDetail();
                                                visitorLoginGetterSetter.setEmpId(Integer.valueOf(empid));
                                                visitorLoginGetterSetter.setName(name);
                                                visitorLoginGetterSetter.setDesignation(designation);
                                                visitorLoginGetterSetter.setVisit_date(visit_date);
                                                visitorLoginGetterSetter.setIn_time_img(intime_img);
                                                visitorLoginGetterSetter.setIn_time(tv_in_time.getText().toString());
                                                String out_time = tv_out_time.getText().toString();
                                                if (out_time.equalsIgnoreCase("Out Time")) {
                                                    visitorLoginGetterSetter.setOut_time("");
                                                } else {
                                                    visitorLoginGetterSetter.setOut_time(out_time);
                                                }

                                                visitorLoginGetterSetter.setEmp_code(emp_code);
                                                if (outtime_img == null) {
                                                    visitorLoginGetterSetter.setOut_time_img("");
                                                } else {
                                                    visitorLoginGetterSetter.setOut_time_img(outtime_img);
                                                }

                                                visitorLoginData.add(visitorLoginGetterSetter);
                                                database.InsertVisitorLogindata(visitorLoginData);
                                                clearVisitorData();
                                                setLoginData();

                                            }
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();

                } else {
                    if (error_msg.equals("Employee ID already entered")) {
                        clearVisitorData();
                    }
                    AlertandMessages.showToastMsg(context, error_msg);
                }
                break;

            case R.id.btngo:
                emp_code = et_empid.getText().toString().replaceAll("[&^<>{}'$]", "");
                if (emp_code.equals("")) {
                    AlertandMessages.showToastMsg(context, "Please Enter Employee Id");
                } else if (CheckNetAvailability()) {
                    new GetCredentials().execute();
                } else {
                    AlertandMessages.showToastMsg(context, getResources().getString(R.string.nonetwork));
                }

                break;

            case R.id.rel_intime:
                camin_clicked = true;
                _pathforcheck = username.replace(".", "") + "_Visitor_Intime-" + visit_date_formatted + "-" + CommonFunctions.getCurrentTimeHHMMSS() + ".jpg";
                _path = str + _pathforcheck;
                CommonFunctions.startAnncaCameraActivity(context, _path, null, false,CommonString.CAMERA_FACE_FRONT);
                break;

            case R.id.rel_outtime:
                if (!isUpdate) {
                    error_msg = "Please click Out Time image at out time";
                    AlertandMessages.showToastMsg(context, error_msg);

                } else {
                    camout_clicked = true;
                    _pathforcheck = username.replace(".", "") + "_Visitor_Outtime-" + visit_date_formatted + "-" + CommonFunctions.getCurrentTimeHHMMSS() + ".jpg";
                    _path = str + _pathforcheck;
                    CommonFunctions.startAnncaCameraActivity(context, _path, null, false,CommonString.CAMERA_FACE_FRONT);
                }
                break;

            case R.id.btnClear:
                clearVisitorData();
                break;
        }

    }


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
                    if (new File(str + _pathforcheck).exists()) {

                        if (camin_clicked) {
                            intime_img = _pathforcheck;

                            tv_in_time.setText(getClicktime());

                            _pathforcheck = "";
                            camin_clicked = false;

                            rel_intime.setVisibility(View.GONE);
                            rel_intime.setClickable(false);

                            setScaledImage(img_intime, _path);
                            img_intime.setVisibility(View.VISIBLE);
                            btnclear.setVisibility(View.VISIBLE);

                        } else if (camout_clicked) {

                            visitordata.setOut_time_img(_pathforcheck);
                            tv_out_time.setText(getClicktime());

                            _pathforcheck = "";
                            camout_clicked = false;

                            rel_outtime.setVisibility(View.GONE);
                            rel_outtime.setClickable(false);

                            img_outtime.setVisibility(View.VISIBLE);
                            setScaledImage(img_outtime, _path);
                            btnclear.setVisibility(View.VISIBLE);

                        }

                        break;

                    }
                }

                break;
        }
    }

    public String getCurrentTime() {

        Calendar m_cal = Calendar.getInstance();
        String intime = m_cal.get(Calendar.DAY_OF_MONTH) + "" + m_cal.get(Calendar.MONTH) + "" + m_cal.get(Calendar.YEAR) + "" + m_cal.get(Calendar.HOUR_OF_DAY) + ""
                + m_cal.get(Calendar.MINUTE) + "" + m_cal.get(Calendar.SECOND);

        return intime;

    }


    public boolean check_condition() {
        database.open();
        if (isUpdate) {
            if (visitordata.getOut_time_img().equals("")) {
                error_msg = "Please Click Out Time Image";
                return false;
            } else {
                return true;
            }
        } else {
            if (empid == null || name == null || designation == null) {
                error_msg = "Please Enter Emp Id";
                return false;
            } else if (intime_img == null) {
                error_msg = "Please Click InTime Image";
                return false;
            } else if (database.isVistorDataExists(empid, visit_date)) {
                error_msg = "Employee Id Already Entered";
                return false;
            } else {
                return true;
            }
        }
    }

    class GetCredentials extends AsyncTask<Void, Void, String> {

        private ProgressDialog dialog = null;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            dialog = new ProgressDialog(context);
            dialog.setTitle("Employee Id");
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
                jsonObject.put("Downloadtype", "Visitor_Search");
                jsonObject.put("Username", username);
                jsonObject.put("Param1", emp_code.trim().toLowerCase());
                jsonObject.put("Param2", "");

                String jsonString2 = jsonObject.toString();

                UploadImageWithRetrofit upload = new UploadImageWithRetrofit(context);
                String result_str = upload.downloadDataUniversal(jsonString2, CommonString.DOWNLOAD_ALL_SERVICE);

                if (result_str.equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result_str.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
                } else {
                    Gson gson = new Gson();
                    vistorObject = gson.fromJson(result_str, VisitorDetailGetterSetter.class);
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
            } catch (JsonSyntaxException e) {
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
                if (vistorObject != null && vistorObject.getVisitorDetail().size() > 0) {
                    tvname.setText(vistorObject.getVisitorDetail().get(0).getName());
                    tvdesignation.setText(vistorObject.getVisitorDetail().get(0).getDesignation());
                    empid = String.valueOf(vistorObject.getVisitorDetail().get(0).getEmpId());
                    name = vistorObject.getVisitorDetail().get(0).getName();
                    designation = vistorObject.getVisitorDetail().get(0).getDesignation();
                } else {
                    dialog.dismiss();
                    AlertandMessages.showToastMsg(context, "Please Enter Valid Employee Code");

                }

            } else {
                dialog.dismiss();
                AlertandMessages.showToastMsg(context, "Please Enter Valid Employee Code");
            }
        }

    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
        Context context;
        LayoutInflater inflater;

        RecyclerAdapter(Context context) {
            this.context=context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.child_visitor_login, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.tv_name.setText(visitorLoginData.get(position).getName());
            holder.tv_intime.setText(visitorLoginData.get(position).getIn_time());
            holder.tv_outtime.setText(visitorLoginData.get(position).getOut_time());
            if (visitorLoginData.get(position).getUpload_status() != null && visitorLoginData.get(position).getUpload_status().equals("U")) {
                holder.img_upload_tick.setVisibility(View.VISIBLE);
            } else {
                holder.img_upload_tick.setVisibility(View.INVISIBLE);
            }

            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    visitordata = visitorLoginData.get(position);

                    if (visitordata.getUpload_status() != null && visitordata.getUpload_status().equalsIgnoreCase(CommonString.KEY_U)) {
                        AlertandMessages.showToastMsg(context, "Data Already Uploaded");
                    } else {

                        btnclear.setVisibility(View.VISIBLE);
                        tvname.setText(visitordata.getName());
                        tvdesignation.setText(visitordata.getDesignation());
                        et_empid.setText(visitordata.getEmp_code());
                        tv_in_time.setText(visitordata.getIn_time());
                        btngo.setClickable(false);
                        String intime_img = visitordata.getIn_time_img();
                        if (intime_img != null && !intime_img.equals("")) {

                            rel_intime.setVisibility(View.GONE);
                            rel_intime.setClickable(false);
                            setScaledImage(img_intime, str + intime_img);
                            img_intime.setVisibility(View.VISIBLE);
                        }

                        String outtime_img = visitordata.getOut_time_img();
                        if (outtime_img != null && !outtime_img.equals("")) {

                            rel_outtime.setVisibility(View.GONE);
                            rel_outtime.setClickable(false);
                            tv_out_time.setText(visitordata.getOut_time());
                            setScaledImage(img_outtime, str + outtime_img);
                            img_outtime.setVisibility(View.VISIBLE);
                        } else {
                            tv_out_time.setText(visitordata.getOut_time());
                            rel_outtime.setVisibility(View.VISIBLE);
                            img_outtime.setVisibility(View.GONE);
                            rel_outtime.setClickable(true);
                        }


                        if (!visitordata.getOut_time().equals("")) {
                            fab_save.setClickable(true);
                            fab_save.show();
                        }
                        isUpdate = true;

                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return visitorLoginData.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView tv_name;
            TextView tv_intime;
            TextView tv_outtime;
            LinearLayout layout;
            ImageView img_upload_tick;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                tv_intime = (TextView) itemView.findViewById(R.id.tv_intime);
                tv_outtime = (TextView) itemView.findViewById(R.id.tv_outtime);
                layout = (LinearLayout) itemView.findViewById(R.id.ll_item);
                img_upload_tick = (ImageView) itemView.findViewById(R.id.img_upload_tick);
            }
        }
    }


    public void clearVisitorData() {

        et_empid.setText("");
        tvname.setText("");
        tvdesignation.setText("");
        tv_in_time.setText("");
        tv_out_time.setText("");
        img_intime.setVisibility(View.GONE);
        img_outtime.setVisibility(View.GONE);
        rel_intime.setVisibility(View.VISIBLE);
        rel_outtime.setVisibility(View.VISIBLE);

        empid = null;
        name = null;
        designation = null;
        intime_img = null;
        outtime_img = null;

        fab_save.setClickable(true);
        fab_save.show();

        rel_intime.setClickable(true);
        btngo.setClickable(true);
        rel_outtime.setClickable(true);
        isUpdate = false;
        btnclear.setVisibility(View.INVISIBLE);

    }

    private void setScaledImage(ImageView imageView, final String path) {
        final ImageView iv = imageView;
        ViewTreeObserver viewTreeObserver = iv.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                iv.getViewTreeObserver().removeOnPreDrawListener(this);
                int imageViewHeight = iv.getMeasuredHeight();
                int imageViewWidth = iv.getMeasuredWidth();
                iv.setImageBitmap(decodeSampledBitmapFromPath(path, imageViewWidth, imageViewHeight));
                return true;
            }
        });
    }

    private static Bitmap decodeSampledBitmapFromPath(String path,
                                                      int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds = true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //BitmapFactory.decodeResource(res, resId, options);

        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {

        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    //upload Visitor data

    private class UploadVisitorData extends AsyncTask<Void, Void, String> {

        private ProgressDialog dialog = null;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setTitle("Visitor data");
            dialog.setMessage("Uploading....");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub
            try {
                UploadImageWithRetrofit upload = new UploadImageWithRetrofit(context);
                UploadImageWithRetrofitOne uploadRetro = new UploadImageWithRetrofitOne();
                JSONObject jsonObject;
                JSONArray topUpArray = new JSONArray();
                JSONObject obj = new JSONObject();
                obj.put("MID", 0);
                obj.put("CreatedBy", username);
                obj.put("EmpId", visitordata.getEmpId());
                obj.put("VisitDate", visit_date);
                obj.put("InTimeImage", visitordata.getIn_time_img());
                obj.put("OutTimeImage", visitordata.getOut_time_img());
                obj.put("InTime", visitordata.getIn_time());
                obj.put("OutTime", visitordata.getOut_time());

                topUpArray.put(obj);

                jsonObject = new JSONObject();
                jsonObject.put("MID", "0");
                jsonObject.put("Keys", "Visitor_Login");
                jsonObject.put("JsonData", topUpArray.toString());
                jsonObject.put("UserId", username);

                String jsonString2 = jsonObject.toString();
                String result = upload.downloadDataUniversal(jsonString2, CommonString.UPLOADJsonDetail);

                if (result.equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                    throw new SocketTimeoutException();
                } else if (result.equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result.equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                    throw new JsonSyntaxException("Visitor_login");
                } else if (result.equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
                } else {
                    result = CommonString.KEY_SUCCESS;
                }

                if (result.equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                    if (visitordata.getIn_time_img() != null && !visitordata.getIn_time_img().equals("")) {
                        if (new File(CommonString.FILE_PATH + visitordata.getIn_time_img()).exists()) {
                            result = uploadRetro.UploadImage2(visitordata.getIn_time_img(), "VisitorLogin", CommonString.FILE_PATH,"","");
                            if (result.equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                                throw new SocketTimeoutException();
                            } else if (result.equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                                throw new IOException();
                            } else if (result.equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                                throw new JsonSyntaxException("VisitorLogin_InTime_Image");
                            } else if (result.equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                throw new Exception();
                            } else {
                                result = CommonString.KEY_SUCCESS;
                            }
                        }
                    }

                    if (visitordata.getOut_time_img() != null && !visitordata.getOut_time_img().equals("")) {

                        if (new File(CommonString.FILE_PATH + visitordata.getOut_time_img()).exists()) {
                            result = uploadRetro.UploadImage2(visitordata.getOut_time_img(), "VisitorLogin", CommonString.FILE_PATH,"","");
                            if (result.equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                                throw new SocketTimeoutException();
                            } else if (result.equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                                throw new IOException();
                            } else if (result.equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                                throw new JsonSyntaxException("VisitorLogin_OutTime_Image");
                            } else if (result.equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                throw new Exception();
                            } else {
                                result = CommonString.KEY_SUCCESS;
                            }
                        }
                    }
                }


                if (result == CommonString.KEY_SUCCESS) {
                    return CommonString.KEY_SUCCESS;
                } else {
                    return "";
                }

            } catch (SocketTimeoutException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return CommonString.MESSAGE_NO_RESPONSE_SERVER;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return CommonString.MESSAGE_SOCKETEXCEPTION;
            } catch (JsonSyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return CommonString.MESSAGE_INVALID_JSON;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return CommonString.KEY_FAILURE;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            dialog.cancel();
            if (result.equals(CommonString.KEY_SUCCESS)) {
                AlertandMessages.showToastMsg(context, "Visitor Data Uploaded");
                //update upload_status to U
                database.open();
                database.updateVisitorUploadData(visitordata.getEmpId().toString());
                clearVisitorData();
                setLoginData();
            } else {
                AlertandMessages.showToastMsg(context, "Data not uploaded! - " + result);
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

    public String getClicktime() {

        Calendar m_cal = Calendar.getInstance();
        String time = m_cal.get(Calendar.HOUR_OF_DAY) + ":" + m_cal.get(Calendar.MINUTE) + ":" + m_cal.get(Calendar.SECOND);

        return time;
    }


    void declaration() {
        activity = this;
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btngo = (Button) findViewById(R.id.btngo);
        btnclear = (Button) findViewById(R.id.btnClear);
        tvname = (TextView) findViewById(R.id.tv_name);
        tvdesignation = (TextView) findViewById(R.id.tv_designation);
        txt_user = (TextView) findViewById(R.id.txt_user);
        et_empid = (EditText) findViewById(R.id.et_empid);
        imgcam_in = (ImageView) findViewById(R.id.imgcam_intime);
        imgcam_out = (ImageView) findViewById(R.id.imgcam_outtime);
        tv_in_time = (TextView) findViewById(R.id.tvintime);
        tv_out_time = (TextView) findViewById(R.id.tvouttime);
        rel_intime = (RelativeLayout) findViewById(R.id.rel_intime);
        rel_outtime = (RelativeLayout) findViewById(R.id.rel_outtime);
        fab_save = (FloatingActionButton) findViewById(R.id.fab_save);
        img_intime = (ImageView) findViewById(R.id.img_intime);
        img_outtime = (ImageView) findViewById(R.id.img_outtime);

        progressBar = (ProgressBar) findViewById(R.id.progress_empid);

        heading = (LinearLayout) findViewById(R.id.lay_heading);
        recyclerView = (RecyclerView) findViewById(R.id.rv_visitor);

        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        database = new NestleDb(context);
        database.open();

        visit_date = preferences.getString(CommonString.KEY_DATE, "");
        username = preferences.getString(CommonString.KEY_USERNAME, "");
        visit_date_formatted = preferences.getString(CommonString.KEY_YYYYMMDD_DATE, "");
        toolbar.setTitleTextAppearance(context, R.style.changestext_sizefor_mobile);
        setTitle("Visitor Login - " + visit_date);
        str = CommonString.FILE_PATH;

        fab_save.setOnClickListener(this);
        btngo.setOnClickListener(this);
        rel_intime.setOnClickListener(this);
        rel_outtime.setOnClickListener(this);
        btnclear.setOnClickListener(this);
        txt_user.setText(username);
        Path = CommonString.FILE_PATH;
    }

    @Override
    public void onBackPressed() {
        if (camin_clicked) {
            new AlertandMessages((Activity) context, null, null, null).backpressedAlert();
        } else {
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
            VisitorLoginActivity.this.finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // NavUtils.navigateUpFromSameTask(this);
            if (camin_clicked) {
                new AlertandMessages((Activity) context, null, null, null).backpressedAlert();
            } else {
                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                VisitorLoginActivity.this.finish();
            }

        }
        return super.onOptionsItemSelected(item);
    }

}
