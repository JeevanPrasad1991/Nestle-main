package com.cpm.Nestle.password;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.cpm.Nestle.LoginActivity;
import com.cpm.Nestle.R;
import com.cpm.Nestle.upload.Retrofit_method.UploadImageWithRetrofit;
import com.cpm.Nestle.utilities.AlertandMessages;
import com.cpm.Nestle.utilities.CommonString;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.regex.Pattern;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_old_password, et_new_password, et_confirm_password;
    Button btn_submit;
    ImageView img_info;

    private SharedPreferences preferences = null;
    private SharedPreferences.Editor editor = null;

    String user_id;
    String new_pw, old_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_old_password = (EditText) findViewById(R.id.et_old_password);
        et_new_password = (EditText) findViewById(R.id.et_new_password);
        et_confirm_password = (EditText) findViewById(R.id.et_confirm_password);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        img_info = (ImageView) findViewById(R.id.img_info);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        user_id = preferences.getString(CommonString.KEY_USERNAME, "");
        old_password = preferences.getString(CommonString.KEY_PASSWORD, "");

        //new password
        et_new_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String result = s.toString().replaceAll(" ", "");
                if (!s.toString().equals(result)) {
                    et_new_password.setText(result);
                    et_new_password.setSelection(result.length());
                    et_new_password.setError(getString(R.string.space_not_allowed_in_password));
                    et_new_password.requestFocus();
                    // alert the user
                }
            }
        });

        //confirm password
        et_confirm_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String result = s.toString().replaceAll(" ", "");
                if (!s.toString().equals(result)) {
                    et_confirm_password.setText(result);
                    et_confirm_password.setSelection(result.length());
                    et_confirm_password.setError(getString(R.string.space_not_allowed_in_password));
                    et_confirm_password.requestFocus();
                    // alert the user
                }
            }
        });

        img_info.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.img_info:

                Dialog dialog = new Dialog(ChangePasswordActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.password_rule_dialog);
                dialog.show();

                break;

            case R.id.btn_submit:

                submit();

                break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
            builder.setMessage(CommonString.ONBACK_ALERT_MESSAGE)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                            finish();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        return super.onOptionsItemSelected(item);
    }

    void submit(){
        boolean flag = true;

        boolean cancel = false;
        View focusView = null;


        //New Password
        new_pw = et_new_password.getText().toString();

        if(new_pw.equals("")){
            et_new_password.setError(getString(R.string.error_field_required));
            focusView = et_new_password;
            cancel = true;
        }
        else {
            //final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
             /*  if(!PASSWORD_PATTERN.matches(new_pw)){
                et_new_password.setError(getString(R.string.invalid_password));
                focusView = et_new_password;
                cancel = true;
            }*/

            Pattern textPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%])(?=.*\\d).+$");

            if(new_pw.equals(old_password)){
                et_new_password.setError(getString(R.string.new_password_not_equal_to_old_password));
                focusView = et_new_password;
                cancel = true;
            }
            else if(new_pw.length()>=6 && new_pw.length()<=8 && textPattern.matcher(new_pw).matches()){
                //New Password
                String confirm_pw = et_confirm_password.getText().toString();
                if(confirm_pw.equals("")){
                    et_confirm_password.setError(getString(R.string.error_field_required));
                    focusView = et_confirm_password;
                    cancel = true;
                }
                else if(!confirm_pw.equals(new_pw)){
                    et_confirm_password.setError(getString(R.string.password_and_confirm_password_not_match));
                    focusView = et_confirm_password;
                    cancel = true;
                }
                else {
                   new AuthenticateTask().execute();
                }

            }
            else {
                et_new_password.setError(getString(R.string.invalid_password));
                focusView = et_new_password;
                cancel = true;
            }

        }

        //Old Password
        String old_pw = et_old_password.getText().toString().trim();

        if(old_pw.equals("")){
            et_old_password.setError(getString(R.string.error_field_required));
            focusView = et_old_password;
            cancel = true;
        }
        else {



            if (!old_password.equals("") && !old_pw.equals(old_password)) {
                et_old_password.setError(getString(R.string.error_incorrect_password));
                focusView = et_old_password;
                cancel = true;
            }
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();

        } else{

        }

    }

    private class AuthenticateTask extends AsyncTask<Void, Void, String> {
        private ProgressDialog dialog = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ChangePasswordActivity.this);
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

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("UserId", user_id);
                jsonObject.put("OldPassword", old_password);
                jsonObject.put("NewPassword", new_pw);


                String jsonString2 = jsonObject.toString();
                UploadImageWithRetrofit upload = new UploadImageWithRetrofit(getApplicationContext());
                String result_str = upload.downloadDataUniversal(jsonString2, CommonString.CHANGE_PASSWORD_SERVICE);

                //ResponseResult result = new Gson().fromJson(result_str, ResponseResult.class);
                if(result_str.contains(CommonString.KEY_SUCCESS)){
                    return CommonString.KEY_SUCCESS;
                } else if (result_str.equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result_str.equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
                }


            } catch (MalformedURLException e) {

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        AlertandMessages.showAlert(ChangePasswordActivity.this, CommonString.MESSAGE_EXCEPTION, false);
                    }
                });

            } catch (IOException e) {

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                   /*     if (counter < 3) {
                            new AuthenticateTask().execute();
                        } else {
                            showAlert(CommonString.MESSAGE_SOCKETEXCEPTION);
                            counter = 1;
                        }*/
                        AlertandMessages.showAlert(ChangePasswordActivity.this, CommonString.MESSAGE_SOCKETEXCEPTION, false);
                    }
                });
            } catch (Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertandMessages.showAlert(ChangePasswordActivity.this, CommonString.MESSAGE_CHANGED, false);
                    }
                });
            }
            return "";

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if (result.equals(CommonString.KEY_SUCCESS)) {
                editor.putString(CommonString.KEY_PASSWORD, new_pw);

                editor.commit();

                dialog.dismiss();
                showAlert(getString(R.string.password_updated_successfully), true);
            }else {
                dialog.dismiss();
                showAlert(getString(R.string.error), true);
            }
        }
    }

    /*private class AuthenticateTask extends AsyncTask<Void, Void, String> {
        private ProgressDialog dialog = null;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            dialog = new ProgressDialog(ChangePasswordActivity.this);
            dialog.setTitle("Login");
            dialog.setMessage("Authenticating....");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            try {

                String userauth_xml = "[DATA]" + "[USER_DATA][USER_ID]"
                        + user_id + "[/USER_ID]" + "[OLD_PASSWORD]" + old_password
                        + "[/OLD_PASSWORD]" + "[NEW_PASSWORD]" + new_pw
                        + "[/NEW_PASSWORD]" + "[/USER_DATA][/DATA]";

                SoapObject request = new SoapObject(CommonString.NAMESPACE,
                        CommonString.METHOD_UPDATE_PASSWORD);
                request.addProperty("onXML", userauth_xml);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE androidHttpTransport = new HttpTransportSE(
                        CommonString.URL);

                androidHttpTransport.call(CommonString.SOAP_ACTION_UPDATE_PASSWORD,
                        envelope);

                Object result = (Object) envelope.getResponse();

                if (result.toString()
                        .equalsIgnoreCase(CommonString.KEY_SUCCESS)) {

                    return CommonString.KEY_SUCCESS;

                } else if (result.toString()
                        .equalsIgnoreCase(CommonString.KEY_FAILURE)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            showAlert(CommonString.MESSAGE_FAILURE, false);
                        }
                    });

                } else if (result.toString().equalsIgnoreCase(
                        CommonString.KEY_FALSE)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            showAlert(CommonString.MESSAGE_FALSE, false);
                        }
                    });

                } else if (result.toString().equalsIgnoreCase(
                        CommonString.KEY_CHANGED)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            //showAlert(CommonString.MESSAGE_CHANGED);
                        }
                    });

                } else {

                }

                return "";

            } catch (MalformedURLException e) {

             *//*  final AlertMessage message = new AlertMessage(
                        LoginActivity.this, AlertMessage.MESSAGE_EXCEPTION,
                        "acra_login", e);

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        //showAlert(CommonString.MESSAGE_EXCEPTION);
                    }
                });*//*

            } catch (IOException e) {
                final AlertMessage message = new AlertMessage(
                        ChangePasswordActivity.this,
                        AlertMessage.MESSAGE_SOCKETEXCEPTION, "socket_login", e);
            } catch (Exception e) {
              *//*  final AlertMessage message = new AlertMessage(
                        LoginActivity.this, AlertMessage.MESSAGE_EXCEPTION,
                        "acra_login", e);*//*
                Crashlytics.log(7, CommonString.MESSAGE_EXCEPTION, e.toString());
                Crashlytics.logException(e.getCause());
                Crashlytics.logException(new Exception("My custom login Exception"));
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        //showAlert(CommonString.MESSAGE_EXCEPTION);
                    }
                });
            }
            return "";

        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            //Stop performance trace
            //myTrace.stop();

            if (result.equals(CommonString.KEY_SUCCESS)) {

                // PUT IN PREFERENCES

                editor.putString(CommonString.KEY_PASSWORD, new_pw);

                editor.commit();

                dialog.dismiss();
                showAlert(getString(R.string.password_updated_successfully), true);
            }
            else {
                dialog.dismiss();
                showAlert(getString(R.string.error), true);
            }

        }

    }*/

    public void showAlert(String str, final boolean flag_finish) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
        builder.setTitle("Parinaam");
        builder.setMessage(str).setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if(flag_finish){
                            //finish();

                            Intent i = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                            // set the new task and clear flags
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        }

                       /* Intent i = new Intent(activity, StorelistActivity.class);
                        activity.startActivity(i);
                        activity.finish();*/

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
