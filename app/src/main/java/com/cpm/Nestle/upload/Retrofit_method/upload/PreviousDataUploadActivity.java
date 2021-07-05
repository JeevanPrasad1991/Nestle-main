package com.cpm.Nestle.upload.Retrofit_method.upload;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cpm.Nestle.R;
import com.cpm.Nestle.database.NestleDb;
import com.cpm.Nestle.delegates.CoverageBean;
import com.cpm.Nestle.getterSetter.MappingJourneyPlan;
import com.cpm.Nestle.getterSetter.MasterProgram;
import com.cpm.Nestle.getterSetter.MenuMaster;
import com.cpm.Nestle.upload.Retrofit_method.UploadImageWithRetrofit;
import com.cpm.Nestle.utilities.AlertandMessages;
import com.cpm.Nestle.utilities.CommonString;
import com.google.gson.JsonSyntaxException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class PreviousDataUploadActivity extends AppCompatActivity {

    NestleDb db;
    ArrayList<CoverageBean> coverageList;
    String date, userId, app_version, SecurityToken, designation;
    String Path;
    Context context;
    boolean ResultFlag = true;
    private Dialog dialog;
    private ProgressBar pb;
    private ProgressDialog pd;
    private TextView percentage, message;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        context = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        date = preferences.getString(CommonString.KEY_DATE, "");
        userId = preferences.getString(CommonString.KEY_USERNAME, "");
        designation = preferences.getString(CommonString.KEY_DESIGNATION, "");
        SecurityToken = preferences.getString(CommonString.KEY_SecurityToken, "");
        app_version = preferences.getString(CommonString.KEY_VERSION, "");
        db = new NestleDb(context);
        db.open();
        Path = CommonString.FILE_PATH;
        isDataValid();
    }

    void isDataValid() {
        boolean flag_invalid = false;
        String from = "";
        MappingJourneyPlan jcp = null;
        db.open();
        ArrayList<CoverageBean> coverage_list = db.getCoverageDataPrevious(date);
        for (int i = 0; i < coverage_list.size(); i++) {
            db.open();
            jcp = db.getSpecificStoreDataPrevious(date, coverage_list.get(i).getStoreId());
            if (jcp.getUploadStatus() != null && jcp.getUploadStatus().equalsIgnoreCase(CommonString.KEY_CHECK_IN)) {
                flag_invalid = true;
                break;
            }
        }

        if (flag_invalid) {
            if (isValid(jcp)) {
                new checkoutData(jcp, from).execute();
            } else {
                new DeleteCoverageData(jcp.getStoreId().toString(), jcp.getVisitDate(), userId).execute();
            }
        } else {
            //start upload
            db.open();
            coverageList = db.getCoverageDataPrevious(date);
            if (coverageList.size() > 0) {
                pd = new ProgressDialog(context);
                pd.setCancelable(false);
                pd.setMessage("Uploading Data");
                pd.show();
                UploadImageWithRetrofit upload = new UploadImageWithRetrofit(context, db, pd, CommonString.TAG_FROM_PREVIOUS);
                upload.uploadDataUsingCoverageRecursive(coverageList, 0);
            }
        }
    }


    private boolean isValid(MappingJourneyPlan journeyPlan) {
        boolean status = true;
        db.open();
        ArrayList<MenuMaster> menu_list = db.getMenuData(journeyPlan, designation);
        for (int i = 0; i < menu_list.size(); i++) {
            switch (menu_list.get(i).getMenuId()) {
                case 1:
                    db.open();
                    if (db.getmasterProgram(journeyPlan).size() > 0) {
                        db.open();
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
                db.open();
                ArrayList<MasterProgram> subprogramList = programs.get(i).getSubprogramList();
                if (subprogramList.size() > 0) {
                    for (int k = 0; k < subprogramList.size(); k++) {
                        db.open();
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


    public void showAlert(String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Parinaam");
        builder.setMessage(str).setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    //endregion


    public class checkoutData extends AsyncTask<Void, Void, String> {

        private MappingJourneyPlan cdata;
        private String from;

        checkoutData(MappingJourneyPlan cdata, String from) {
            this.cdata = cdata;
            this.from = from;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.custom);
            dialog.setTitle(getResources().getString(R.string.dialog_title));
            dialog.setCancelable(false);
            dialog.show();
            pb = (ProgressBar) dialog.findViewById(R.id.progressBar1);
            percentage = (TextView) dialog.findViewById(R.id.percentage);
            message = (TextView) dialog.findViewById(R.id.message);
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
                jsonObject.put("Latitude", "0");
                jsonObject.put("Longitude", "0");
                jsonObject.put("CheckoutDate", cdata.getVisitDate());
                jsonObject.put("SecurityToken", SecurityToken);
                String jsonString2 = jsonObject.toString();

                UploadImageWithRetrofit upload = new UploadImageWithRetrofit(context);
                String result_str;
                if (designation.equalsIgnoreCase("DBSR")) {
                    result_str = upload.downloadDataUniversal(jsonString2, CommonString.COVERAGEStatusDetailDBSR);
                } else {
                    result_str = upload.downloadDataUniversal(jsonString2, CommonString.COVERAGEStatusDetail);
                }

                if (result_str.equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result_str.equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                    throw new SocketTimeoutException();
                } else if (result_str.equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                    throw new JsonSyntaxException("Check out Upload");
                } else if (result_str.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
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
            dialog.dismiss();
            if (result != null && !result.equals("") && result.equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                db.open();
                if (from.equalsIgnoreCase(CommonString.TAG_FROM_JCP)) {
                    db.updateCheckoutStatus(String.valueOf(cdata.getStoreId()), CommonString.KEY_C, CommonString.TABLE_Journey_Plan);
                } else if (from.equalsIgnoreCase(CommonString.TAG_FROM_NON_MERCHANDIZED)) {
                    db.updateCheckoutStatus(String.valueOf(cdata.getStoreId()), CommonString.KEY_C, "JourneyPlan_NonMerchandised");
                } else if (from.equalsIgnoreCase(CommonString.TAG_FROM_NOT_COVERED)) {
                    db.updateCheckoutStatus(String.valueOf(cdata.getStoreId()), CommonString.KEY_C, "JourneyPlan_NotCovered");
                } else {
                    db.updateCheckoutStatus(String.valueOf(cdata.getStoreId()), CommonString.KEY_C, CommonString.TABLE_Journey_Plan_DBSR_Saved);
                }

                //start upload
                db.open();
                coverageList = db.getCoverageDataPrevious(date);
                if (coverageList.size() > 0) {
                    pd = new ProgressDialog(context);
                    pd.setCancelable(false);
                    pd.setMessage("Uploading Data");
                    pd.show();
                    UploadImageWithRetrofit upload = new UploadImageWithRetrofit(context, db, pd, CommonString.TAG_FROM_PREVIOUS);
                    upload.uploadDataUsingCoverageRecursive(coverageList, 0);
                }
            } else {
                showAlert(getString(R.string.datanotfound) + " " + result);
            }
        }

    }

    public class DeleteCoverageData extends AsyncTask<Void, Void, String> {
        String storeID, visitDate, userId;

        DeleteCoverageData(String storeId, String visitDate, String userId) {
            this.storeID = storeId;
            this.visitDate = visitDate;
            this.userId = userId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.custom);
            dialog.setTitle(getResources().getString(R.string.dialog_title));
            dialog.setCancelable(false);
            dialog.show();
            pb = (ProgressBar) dialog.findViewById(R.id.progressBar1);
            percentage = (TextView) dialog.findViewById(R.id.percentage);
            message = (TextView) dialog.findViewById(R.id.message);
        }

        @Override
        protected String doInBackground(Void... params) {
            String strflag = null;
            try {
                NestleDb db = new NestleDb(context);
                db.open();
                // for failure
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("StoreId", storeID);
                jsonObject.put("VisitDate", visitDate);
                jsonObject.put("UserName", userId);
                jsonObject.put("SecurityToken", SecurityToken);
                String jsonString2 = jsonObject.toString();

                UploadImageWithRetrofit upload = new UploadImageWithRetrofit(context);
                String result_str = upload.downloadDataUniversal(jsonString2, CommonString.DELETE_COVERAGE);

                if (result_str.equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                    throw new IOException();
                } else if (result_str.equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                    throw new SocketTimeoutException();
                } else if (result_str.equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                    throw new JsonSyntaxException("Check out Upload");
                } else if (result_str.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    throw new Exception();
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
            dialog.dismiss();
            if (result!=null && !result.equals("") && result.equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                db.open();
                db.deleteTableWithStoreID(storeID);
                //start upload
                db.open();
                coverageList = db.getCoverageDataPrevious(date);
                if (coverageList.size() > 0) {
                    pd = new ProgressDialog(context);
                    pd.setCancelable(false);
                    pd.setMessage("Uploading Data");
                    pd.show();
                    UploadImageWithRetrofit upload = new UploadImageWithRetrofit(context, db, pd, CommonString.TAG_FROM_PREVIOUS);
                    upload.uploadDataUsingCoverageRecursive(coverageList, 0);
                } else {
                    AlertandMessages.showAlert((Activity) context, "Data Uploaded Successfully", true);
                }

            } else {
                showAlert(getString(R.string.datanotfound) + " " + result);
            }
        }

    }

}
