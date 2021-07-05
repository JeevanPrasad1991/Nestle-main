package com.cpm.Nestle.upload.Retrofit_method;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.cpm.Nestle.R;
import com.cpm.Nestle.dailyEntry.StoreListActivity;
import com.cpm.Nestle.database.NestleDb;
import com.cpm.Nestle.delegates.CoverageBean;
import com.cpm.Nestle.getterSetter.AssetMaster;
import com.cpm.Nestle.getterSetter.AttendanceReportGetterSetter;
import com.cpm.Nestle.getterSetter.AuditDataGetterSetter;
import com.cpm.Nestle.getterSetter.AvailabilityGetterSetter;
import com.cpm.Nestle.getterSetter.CategoryMaster;
import com.cpm.Nestle.getterSetter.ChecklistGetterSetter;
import com.cpm.Nestle.getterSetter.CommonChillerDataGetterSetter;
import com.cpm.Nestle.getterSetter.GeotaggingBeans;
import com.cpm.Nestle.getterSetter.JCPGetterSetter;
import com.cpm.Nestle.getterSetter.MappingAssetChecklist;
import com.cpm.Nestle.getterSetter.MappingAssetChecklistGetterSetter;
import com.cpm.Nestle.getterSetter.MappingAssetGetterSetter;
import com.cpm.Nestle.getterSetter.MappingCategoryChecklistGetterSetter;
import com.cpm.Nestle.getterSetter.MappingCategoryGetterSetter;
import com.cpm.Nestle.getterSetter.MappingJourneyPlan;
import com.cpm.Nestle.getterSetter.MappingMenuGetterSetter;
import com.cpm.Nestle.getterSetter.MappingPosmGetterSetter;
import com.cpm.Nestle.getterSetter.MappingProductAssortmentGetterSetter;
import com.cpm.Nestle.getterSetter.MappingPromotion;
import com.cpm.Nestle.getterSetter.MappingPromotionGetterSetter;
import com.cpm.Nestle.getterSetter.MappingRDVisibilityDriveGetterSetter;
import com.cpm.Nestle.getterSetter.MappingVQPSVisibilityDriveGetterSetter;
import com.cpm.Nestle.getterSetter.MappingWindowChecklistGetterSetter;
import com.cpm.Nestle.getterSetter.MappingWindowGetterSetter;
import com.cpm.Nestle.getterSetter.MasterAsset;
import com.cpm.Nestle.getterSetter.MasterAssetGetterSetter;
import com.cpm.Nestle.getterSetter.MasterBrandGetterSetter;
import com.cpm.Nestle.getterSetter.MasterCategoryGetterSetter;


import com.cpm.Nestle.getterSetter.MasterChecklist;
import com.cpm.Nestle.getterSetter.MasterChecklistAnswerGetterSetter;
import com.cpm.Nestle.getterSetter.MasterChecklistGetterSetter;
import com.cpm.Nestle.getterSetter.MasterCompany;
import com.cpm.Nestle.getterSetter.MasterDriveNonVisibilityGetterSetter;
import com.cpm.Nestle.getterSetter.MasterFeedbackAnswerGetterSetter;
import com.cpm.Nestle.getterSetter.MasterFeedbackQuestionGetterSetter;
import com.cpm.Nestle.getterSetter.MasterNonProgramReasonGetterSetter;
import com.cpm.Nestle.getterSetter.MasterPosm;
import com.cpm.Nestle.getterSetter.MasterPosmGetterSetter;
import com.cpm.Nestle.getterSetter.MasterProgram;
import com.cpm.Nestle.getterSetter.MasterWindowGetterSetter;
import com.cpm.Nestle.getterSetter.MenuMaster;
import com.cpm.Nestle.getterSetter.MenuMasterGetterSetter;
import com.cpm.Nestle.getterSetter.MerchandiserPerformanceGetterSetter;
import com.cpm.Nestle.getterSetter.NonAssetReasonGetterSetter;
import com.cpm.Nestle.getterSetter.NonPosmReasonGetterSetter;
import com.cpm.Nestle.getterSetter.NonWindowReasonGetterSetter;
import com.cpm.Nestle.getterSetter.NonWorkingReasonGetterSetter;
import com.cpm.Nestle.getterSetter.PosCompanyGetterSetter;
import com.cpm.Nestle.getterSetter.ProductMasterGetterSetter;
import com.cpm.Nestle.getterSetter.ReferenceVariablesForDownloadActivity;
import com.cpm.Nestle.getterSetter.StoreProfileGetterSetter;
import com.cpm.Nestle.getterSetter.TableStructure;
import com.cpm.Nestle.getterSetter.TableStructureGetterSetter;
import com.cpm.Nestle.getterSetter.ViisbilityDriveGetterSetter;
import com.cpm.Nestle.getterSetter.WindowMaster;
import com.cpm.Nestle.upload.Retrofit_method.upload.ToStringConverterFactory;
import com.cpm.Nestle.utilities.AlertandMessages;
import com.cpm.Nestle.utilities.CommonString;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by upendra on 01/09/2020.
 */

public class UploadImageWithRetrofit extends ReferenceVariablesForDownloadActivity {
    String storeflagjcp;
    boolean isvalid;
    RequestBody body1;
    private Retrofit adapter;
    Context context;
    public int totalFiles = 0;
    public static int uploadedFiles = 0;
    public int listSize = 0;
    int status = 0;
    NestleDb db;
    ProgressDialog pd;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String _UserId, date, app_ver, rightname;
    String[] jj;
    boolean statusUpdated = true;
    int from;
    String SecurityToken, designation;

    public UploadImageWithRetrofit(Context context) {
        this.context = context;
    }

    public UploadImageWithRetrofit(Context context, String progessTitle, String progressStr, NestleDb db) {
        this.context = context;
        this.db = db;
        pd = new ProgressDialog(this.context);
        pd.setTitle(progessTitle);
        pd.setMessage(progressStr);
        pd.setCancelable(false);
        if (pd != null && (!pd.isShowing())) {
            pd.show();
        }
    }

    public UploadImageWithRetrofit(Context context, NestleDb db, ProgressDialog pd, int from) {
        this.context = context;
        this.db = db;
        this.pd = pd;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        this.from = from;
        _UserId = preferences.getString(CommonString.KEY_USERNAME, "");
        date = preferences.getString(CommonString.KEY_DATE, null);
        SecurityToken = preferences.getString(CommonString.KEY_SecurityToken, "");
        designation = preferences.getString(CommonString.KEY_DESIGNATION, "");
        rightname = preferences.getString(CommonString.KEY_RIGHTNAME, null);
        try {
            app_ver = String.valueOf(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        db.open();
    }

    public String downloadDataUniversal(final String jsonString, int type) {
        try {
            status = 0;
            isvalid = false;
            final String[] data_global = {""};
            RequestBody jsonData = RequestBody.create(MediaType.parse("application/json"), jsonString);
            OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(CommonString.TIMEOUT, TimeUnit.SECONDS).writeTimeout(CommonString.TIMEOUT,
                    TimeUnit.SECONDS).connectTimeout(CommonString.TIMEOUT, TimeUnit.SECONDS).build();
            adapter = new Retrofit.Builder().baseUrl(CommonString.URL).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
            PostApi api = adapter.create(PostApi.class);
            Call<ResponseBody> call = null;
            if (type == CommonString.LOGIN_SERVICE) {
                call = api.getLogindetail(jsonData);
            } else if (type == CommonString.DOWNLOAD_ALL_SERVICE) {
                call = api.getDownloadAll(jsonData);
            } else if (type == CommonString.COVERAGE_DETAIL) {
                call = api.getCoverageDetail(jsonData);
            } else if (type == CommonString.COVERAGE_DETAIL_CLIENT) {
                call = api.getCoverageDetailClient(jsonData);
            } else if (type == CommonString.UPLOADJCPDetail) {
                call = api.getUploadJCPDetail(jsonData);
            } else if (type == CommonString.UPLOADJsonDetail) {
                call = api.getUploadJsonDetail(jsonData);
            } else if (type == CommonString.COVERAGEStatusDetail) {
                call = api.getCoverageStatusDetail(jsonData);
            } else if (type == CommonString.CHECKOUTDetail) {
                call = api.getCheckout(jsonData);
            } else if (type == CommonString.CHECKOUTDetail_CLIENT) {
                call = api.getCheckoutClient(jsonData);
            }/* else if (type == CommonString.JOURNEYDetail) {
                call = api.getmidClient(jsonData);
            }*/ else if (type == CommonString.DELETE_COVERAGE) {
                call = api.deleteCoverageData(jsonData);
            } else if (type == CommonString.COVERAGE_NONWORKING) {
                call = api.setCoverageNonWorkingData(jsonData);
            } else if (type == CommonString.CHANGE_PASSWORD_SERVICE) {
                call = api.setNewPassword(jsonData);
            } else if (type == CommonString.COVERAGE_DBSR) {
                call = api.getCoverageDBSR(jsonData);
            } else if (type == CommonString.COVERAGEStatusDetailDBSR) {
                call = api.getStoreCoverageStatusDBSR(jsonData);
            } else if (type == CommonString.CreateJ_ourney_Plan) {
                call = api.getmidClient(jsonData);
            }


            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ResponseBody responseBody = response.body();
                    String data = null;
                    if (responseBody != null && response.isSuccessful()) {
                        try {
                            data = response.body().string();
                            if (data.equals("")) {
                                data_global[0] = "";
                                isvalid = true;
                                status = 1;
                            } else {
                                data = data.substring(1, data.length() - 1).replace("\\", "");
                                data_global[0] = data;
                                isvalid = true;
                                status = 1;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            isvalid = true;
                            status = -2;
                        }
                    } else {
                        isvalid = true;
                        status = -1;
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    isvalid = true;
                    if (t instanceof SocketTimeoutException) {
                        status = 3;
                    } else if (t instanceof IOException) {
                        status = 3;
                    } else {
                        status = 3;
                    }

                }
            });

            while (isvalid == false) {
                synchronized (this) {
                    this.wait(25);
                }
            }
            if (isvalid) {
                synchronized (this) {
                    this.notify();
                }
            }
            if (status == 1) {
                return data_global[0];
            } else if (status == 2) {
                return CommonString.MESSAGE_NO_RESPONSE_SERVER;
            } else if (status == 3) {
                return CommonString.MESSAGE_SOCKETEXCEPTION;
            } else if (status == -2) {
                return CommonString.MESSAGE_INVALID_JSON;
            } else {
                return CommonString.KEY_FAILURE;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommonString.KEY_FAILURE;
        }
    }


    public static File saveBitmapToFileSmaller(File file) {
        File file2 = file;
        try {
            int inWidth = 0;
            int inHeight = 0;

            InputStream in = new FileInputStream(file2);
            // decode image size (decode metadata only, not the whole image)
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, options);
            in.close();
            in = null;

            // save width and height
            inWidth = options.outWidth;
            inHeight = options.outHeight;

            // decode full image pre-resized
            in = new FileInputStream(file2);
            options = new BitmapFactory.Options();
            // calc rought re-size (this is no exact resize)
            options.inSampleSize = Math.max(inWidth / 800, inHeight / 500);
            // decode full image
            Bitmap roughBitmap = BitmapFactory.decodeStream(in, null, options);

            // calc exact destination size
            Matrix m = new Matrix();
            RectF inRect = new RectF(0, 0, roughBitmap.getWidth(), roughBitmap.getHeight());
            RectF outRect = new RectF(0, 0, 800, 500);
            m.setRectToRect(inRect, outRect, Matrix.ScaleToFit.CENTER);
            float[] values = new float[9];
            m.getValues(values);
            // resize bitmap
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(roughBitmap, (int) (roughBitmap.getWidth() * values[0]), (int) (roughBitmap.getHeight() * values[4]), true);
            // save image
            FileOutputStream out = new FileOutputStream(file2);
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);

        } catch (Exception e) {
            Log.e("Image", e.toString(), e);
            return file;
        }
        return file2;
    }


    public void UploadImageRecursive(final Context context, final String coverageDate) {
        try {
            String filename = null, foldername = null;
            int totalfiles = 0;
            File f = new File(CommonString.FILE_PATH);
            File file[] = f.listFiles();
            if (file.length > 0) {
                filename = "";
                totalfiles = f.listFiles().length;
                for (int i = 0; i < file.length; i++) {
                    if (new File(CommonString.FILE_PATH + file[i].getName()).exists()) {
                        if (file[i].getName().contains("_StoreImg-") || file[i].getName().contains("_NonworkImg-")) {
                            foldername = "CoverageImage";
                        } else if (file[i].getName().contains("_GeoTag-")) {
                            foldername = "GeoTagImages";
                        } else if (file[i].getName().contains("_VisicoolerImgOne-") || file[i].getName().contains("_VisicoolerImgTwo-") ||
                                file[i].getName().contains("_VisicoolerQuestionImgOne-") || file[i].getName().contains("_VisicoolerQuestionImgTwo-")) {
                            foldername = "VisicoolerImage";
                        } else if (file[i].getName().contains("_SubprogChecklImgOne-") || file[i].getName().contains("_SubprogChecklImgTwo-") ||
                                file[i].getName().contains("_SubprogQuestionImgOne-") || file[i].getName().contains("_SubprogQuestionImgTwo-")) {
                            foldername = "ProgramChecklistImage";
                        } else if (file[i].getName().contains("_PosmImg-")) {
                            foldername = "PosmImages";
                        } else if (file[i].getName().contains("_TOTImg-")) {
                            foldername = "TOTImages";
                        } else if (file[i].getName().contains("_VQPSImg-")) {
                            foldername = "VQPSImages";
                        } else if (file[i].getName().contains("_promoimg-")) {
                            foldername = "PromotionImages";
                        } else if (file[i].getName().contains("_compPromoImg-") || file[i].getName().contains("_compPromoImgTwo-")) {
                            foldername = "CopmPromotionImages";
                        } else if (file[i].getName().contains("_compNPDImg-") || file[i].getName().contains("_compNPDImgTwo-")) {
                            foldername = "CompNPDLaunchImages";
                        }else if (file[i].getName().contains("SHOP_BOARD_IMG_") || file[i].getName().contains("LONG_SHOT_IMG")||file[i].getName().contains("CLOSE_SHOT_IMG_")) {
                            foldername = "VisibilityDriveImages";
                        }else if (file[i].getName().contains("RD_SHOP_BOARD_IMG_") || file[i].getName().contains("RD_LONG_SHOT_IMG")||file[i].getName().contains("RD_CLOSE_SHOT_IMG_")) {
                            foldername = "RDVisibilityDriveImages";
                        } else {
                            foldername = "BulkImages";
                        }
                        filename = file[i].getName();
                    }
                    break;
                }


                status = 0;
                File originalFile = new File(CommonString.FILE_PATH + filename);
                File finalFile = saveBitmapToFileSmaller(originalFile);
                if (finalFile == null) {
                    finalFile = originalFile;
                }
                String date;
                if (false) {
                    date = getParsedDate(filename);
                } else {
                    date = this.date;
                }
                isvalid = false;

                OkHttpClient.Builder b = new OkHttpClient.Builder();
                b.connectTimeout(CommonString.TIMEOUT, TimeUnit.SECONDS);
                b.readTimeout(CommonString.TIMEOUT, TimeUnit.SECONDS);
                b.writeTimeout(CommonString.TIMEOUT, TimeUnit.SECONDS);
                OkHttpClient client = b.build();
                pd.setMessage("uploading images (" + uploadedFiles + "/" + totalFiles + ")");
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), finalFile);
                body1 = new MultipartBody.Builder()
                        .setType(MediaType.parse("multipart/form-data"))
                        .addFormDataPart("file", finalFile.getName(), requestFile).
                                addFormDataPart("Foldername", foldername).
                                addFormDataPart("UserName", _UserId).
                                addFormDataPart("SecurityToken", SecurityToken).
                                addFormDataPart("Path", date).build();

                adapter = new Retrofit.Builder()
                        .baseUrl(CommonString.URL3).addConverterFactory(new ToStringConverterFactory()).client(client).build();

                PostApi api = adapter.create(PostApi.class);
                Call<String> observable = api.getUploadImage(body1);
                final File finalFile1 = finalFile;
                observable.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful() && response.body().contains("Success")) {
                            finalFile1.delete();
                            uploadedFiles++;
                            status = 1;
                        } else {
                            status = 0;
                        }
                        if (status == 0) {
                            pd.dismiss();
                            if (!((Activity) context).isFinishing()) {
                                AlertandMessages.showAlert((Activity) context, "Image not uploaded." + "\n" + uploadedFiles + " images uploaded out of " + totalFiles, true);
                            }
                        } else {
                            UploadImageRecursive(context, coverageDate);
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        if (t instanceof IOException || t instanceof SocketTimeoutException || t instanceof SocketException) {
                            status = -1;
                            pd.dismiss();
                            if (!((Activity) context).isFinishing()) {
                                AlertandMessages.showAlert((Activity) context, "Network Error in upload." + "\n" + uploadedFiles + " images uploaded out of " + totalFiles, true);
                            } else {

                            }
                        }

                    }
                });

            } else {
                if (totalFiles == uploadedFiles) {
                    //region Coverage upload status Data
                    new StatusUpload(coverageDate).execute();
                    //endregion
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String getParsedDate(String filename) {
        String testfilename = filename;
        testfilename = testfilename.substring(testfilename.indexOf("-") + 1);
        testfilename = testfilename.substring(0, testfilename.indexOf("-"));
        return testfilename;
    }

    public void uploadDataWithoutWait(final ArrayList<String> keyList, final int keyIndex, final ArrayList<CoverageBean> coverageList, final int coverageIndex) {

        try {
            status = 0;
            isvalid = false;
            final String[] data_global = {""};
            String jsonString = "";
            int type = 0;

            JSONObject jsonObject, jsonObject1, jsonObject4;

            //region Creating json data
            switch (keyList.get(keyIndex)) {
                case "CoverageDetail_latest":
                    //region Coverage Data
                    db.open();
                    jsonObject = new JSONObject();
                    jsonObject.put("Mid", coverageList.get(coverageIndex).getMID());
                    jsonObject.put("StoreId", coverageList.get(coverageIndex).getStoreId());
                    jsonObject.put("VisitDate", coverageList.get(coverageIndex).getVisitDate());
                    jsonObject.put("Latitude", coverageList.get(coverageIndex).getLatitude());
                    jsonObject.put("Longitude", coverageList.get(coverageIndex).getLongitude());
                    jsonObject.put("ReasonId", coverageList.get(coverageIndex).getReasonid());
                    jsonObject.put("Remark", "");
                    jsonObject.put("ImageName", coverageList.get(coverageIndex).getImage());
                    jsonObject.put("Appversion", app_ver);
                    jsonObject.put("UploadStatus", CommonString.KEY_P);
                    jsonObject.put("CheckoutImage", coverageList.get(coverageIndex).getCkeckout_image());
                    jsonObject.put("UserName", coverageList.get(coverageIndex).getUserId());
                    jsonObject.put("SecurityToken", SecurityToken);
                    jsonString = jsonObject.toString();
                    type = CommonString.COVERAGE_DETAIL;

                    //endregion
                    break;


                case "GeoTag":
                    //region GeoTag
                    ArrayList<GeotaggingBeans> geotaglist = db.getinsertGeotaggingData(coverageList.get(coverageIndex).getStoreId(), CommonString.KEY_N);
                    if (geotaglist.size() > 0) {
                        JSONArray topUpArray = new JSONArray();
                        for (int j = 0; j < geotaglist.size(); j++) {
                            JSONObject obj = new JSONObject();
                            obj.put(CommonString.KEY_STORE_ID, geotaglist.get(j).getStoreid());
                            obj.put(CommonString.KEY_VISIT_DATE, coverageList.get(coverageIndex).getVisitDate());
                            obj.put(CommonString.KEY_LATITUDE, geotaglist.get(j).getLatitude());
                            obj.put(CommonString.KEY_LONGITUDE, geotaglist.get(j).getLongitude());
                            obj.put("FRONT_IMAGE", geotaglist.get(j).getImage());
                            topUpArray.put(obj);
                        }

                        jsonObject = new JSONObject();
                        jsonObject.put("MID", coverageList.get(coverageIndex).getMID());
                        jsonObject.put("Keys", "GeoTag");
                        jsonObject.put("JsonData", topUpArray.toString());
                        jsonObject.put("UserName", coverageList.get(coverageIndex).getUserId());
                        jsonObject.put("SecurityToken", SecurityToken);

                        jsonString = jsonObject.toString();
                        type = CommonString.UPLOADJsonDetail;
                    }
                    //endregion
                    break;
                case "Store_Profile":
                    //region Coverage Data
                    db.open();
                    StoreProfileGetterSetter storePGT = db.getStoreProfileData(coverageList.get(coverageIndex).
                            getStoreId(), coverageList.get(coverageIndex).getVisitDate());
                    JSONArray storeDetail = new JSONArray();
                    if (storePGT != null && storePGT.getStore_name() != null && !storePGT.getStore_name().equals("")) {
                        jsonObject = new JSONObject();
                        jsonObject.put("MID", coverageList.get(coverageIndex).getMID());
                        jsonObject.put("UserName", coverageList.get(coverageIndex).getUserId());
                        jsonObject.put("Store_Id", storePGT.getStoreCd());
                        jsonObject.put("Visit_date", storePGT.getStore_visit_date());
                        jsonObject.put("Store_Name", storePGT.getStore_name());
                        jsonObject.put("StoreAddress", storePGT.getStore_addres());
                        jsonObject.put("StoreLandmark", storePGT.getLand_mark());
                        jsonObject.put("StorePincode", storePGT.getPin_code());
                        jsonObject.put("CityId", storePGT.getStore_cityId());

                        storeDetail.put(jsonObject);
                        jsonObject = new JSONObject();
                        jsonObject.put("MID", coverageList.get(coverageIndex).getMID());
                        jsonObject.put("Keys", "Store_Profile");
                        jsonObject.put("JsonData", storeDetail.toString());
                        jsonObject.put("UserName", coverageList.get(coverageIndex).getUserId());
                        jsonObject.put("SecurityToken", SecurityToken);
                        jsonString = jsonObject.toString();
                        type = CommonString.UPLOADJsonDetail;
                    }


                    break;

                case "Program_Data":
                    db.open();
                    JSONArray abl_childArray1 = new JSONArray();
                   /* ArrayList<MasterChecklist> program_presents = db.getsubprogramChecklistpresentforupload(coverageList.get(coverageIndex).getStoreId(),
                            coverageList.get(coverageIndex).getVisitDate(), false); */
                    ArrayList<MasterChecklist> program_presents = db.getsubprogramChecklistpresentforuploadResionNew(coverageList.get(coverageIndex).getStoreId(),
                            coverageList.get(coverageIndex).getVisitDate(), false);
                    if (program_presents.size() > 0) {
                        for (int i = 0; i < program_presents.size(); i++) {
                            if (program_presents.get(i).getIschecked().equals("1")) {
                                JSONArray subprogramChecklistArray = new JSONArray();
                                ArrayList<MasterChecklist> checklistArrayList = db.getsubprogramChecklistforupload(coverageList.get(coverageIndex).getStoreId(), coverageList.get(coverageIndex).getVisitDate(), program_presents.get(i).getSubprogramId());
                                if (checklistArrayList.size() > 0) {
                                    for (int k = 0; k < checklistArrayList.size(); k++) {
                                        jsonObject = new JSONObject();
                                        jsonObject.put("MID", coverageList.get(coverageIndex).getMID());
                                        jsonObject.put("UserName", _UserId);
                                        jsonObject.put("KeyId", program_presents.get(i).getKeyId());
                                        jsonObject.put("SubProgramId", program_presents.get(i).getSubprogramId());
                                        jsonObject.put("CheckListId", checklistArrayList.get(k).getChecklistId());
                                        jsonObject.put("AnswerType", checklistArrayList.get(k).getAnswerType());
                                        jsonObject.put("QuestionImg1", checklistArrayList.get(k).getQuestionImage1());
                                        jsonObject.put("QuestionImg2", checklistArrayList.get(k).getQuestionImage2());

                                        if (checklistArrayList.get(k).getAnswerType().equalsIgnoreCase("Binary")) {
                                            jsonObject.put("Answer", checklistArrayList.get(k).getBinary_btn_value());
                                            jsonObject.put("AnswerId", checklistArrayList.get(k).getBinary_btn_ansId());
                                            jsonObject.put("Image1", checklistArrayList.get(k).getChecklist_img1());
                                            jsonObject.put("Image2", checklistArrayList.get(k).getChecklist_img2());

                                        } else if (checklistArrayList.get(k).getAnswerType().equalsIgnoreCase("Single Choice List")) {
                                            jsonObject.put("Answer", checklistArrayList.get(k).getAnswer());
                                            jsonObject.put("AnswerId", checklistArrayList.get(k).getAnsId());
                                            jsonObject.put("Image1", checklistArrayList.get(k).getChecklist_img1());
                                            jsonObject.put("Image2", checklistArrayList.get(k).getChecklist_img2());

                                        } else if (checklistArrayList.get(k).getAnswerType().equalsIgnoreCase("Text")
                                                || checklistArrayList.get(k).getAnswerType().equalsIgnoreCase("Number")) {
                                            jsonObject.put("Answer", checklistArrayList.get(k).getEdt_text_value());
                                            jsonObject.put("AnswerId", 0);
                                            jsonObject.put("Image1", "");
                                            jsonObject.put("Image2", "");
                                        } else if (checklistArrayList.get(k).getAnswerType().equalsIgnoreCase("Image")) {
                                            jsonObject.put("Answer", "");
                                            jsonObject.put("AnswerId", 0);
                                            jsonObject.put("Image1", "");
                                            jsonObject.put("Image2", "");
                                        }

                                        subprogramChecklistArray.put(jsonObject);
                                    }
                                }

                                jsonObject1 = new JSONObject();
                                jsonObject1.put("MID", coverageList.get(coverageIndex).getMID());
                                jsonObject1.put("UserName", _UserId);
                                jsonObject1.put("Ispresent", 1);
                                jsonObject1.put("ResionId", "");
                                jsonObject1.put("KeyId", program_presents.get(i).getKeyId());
                                jsonObject1.put("SubProgramId", program_presents.get(i).getSubprogramId());
                                jsonObject1.put("SubProgramChecklist", subprogramChecklistArray);

                            } else {
                                jsonObject1 = new JSONObject();
                                jsonObject1.put("MID", coverageList.get(coverageIndex).getMID());
                                jsonObject1.put("UserName", _UserId);
                                jsonObject1.put("Ispresent", 0);
                                jsonObject1.put("ResionId", program_presents.get(i).getResion_id());
                                jsonObject1.put("KeyId", program_presents.get(i).getKeyId());
                                jsonObject1.put("SubProgramId", program_presents.get(i).getSubprogramId());
                                jsonObject1.put("SubProgramChecklist", "");
                            }

                            abl_childArray1.put(jsonObject1);
                        }

                        jsonObject = new JSONObject();
                        jsonObject.put("MID", coverageList.get(coverageIndex).getMID());
                        jsonObject.put("Keys", "Program_Data_new");
                        jsonObject.put("JsonData", abl_childArray1.toString());
                        jsonObject.put("UserName", coverageList.get(coverageIndex).getUserId());
                        jsonObject.put("SecurityToken", SecurityToken);
                        jsonString = jsonObject.toString();
                        type = CommonString.UPLOADJsonDetail;
                    }

                    break;

                case "Visicooler_Data":
                    db.open();
                    JSONArray VisicoolerArray = new JSONArray();
                    ArrayList<MasterChecklist> visicooler_presents = db.getsubprogramChecklistpresentforupload(coverageList.get(coverageIndex).getStoreId(), coverageList.get(coverageIndex).getVisitDate(), true);
                    if (visicooler_presents.size() > 0) {
                        for (int i = 0; i < visicooler_presents.size(); i++) {
                            if (visicooler_presents.get(i).getIschecked().equals("1")) {
                                JSONArray subprogramChecklistArray = new JSONArray();
                                ArrayList<MasterChecklist> visicooler_checklists = db.getVisicoolerChecklistforupload(coverageList.get(coverageIndex).getStoreId(), coverageList.get(coverageIndex).getVisitDate());
                                if (visicooler_checklists.size() > 0) {
                                    for (int k = 0; k < visicooler_checklists.size(); k++) {
                                        jsonObject = new JSONObject();
                                        jsonObject.put("MID", coverageList.get(coverageIndex).getMID());
                                        jsonObject.put("UserName", _UserId);
                                        jsonObject.put("KeyId", visicooler_presents.get(i).getKeyId());
                                        jsonObject.put("VCid", visicooler_checklists.get(k).getVVcId());
                                        jsonObject.put("VCCategoryId", visicooler_checklists.get(k).getVisicooler_categoryId());
                                        jsonObject.put("CheckListId", visicooler_checklists.get(k).getChecklistId());
                                        jsonObject.put("AnswerType", visicooler_checklists.get(k).getAnswerType());
                                        jsonObject.put("QuestionImg1", visicooler_checklists.get(k).getQuestionImage1());
                                        jsonObject.put("QuestionImg2", visicooler_checklists.get(k).getQuestionImage2());

                                        if (visicooler_checklists.get(k).getAnswerType().equalsIgnoreCase("Binary")) {
                                            jsonObject.put("Answer", visicooler_checklists.get(k).getBinary_btn_value());
                                            jsonObject.put("AnswerId", visicooler_checklists.get(k).getBinary_btn_ansId());
                                            jsonObject.put("Image1", visicooler_checklists.get(k).getChecklist_img1());
                                            jsonObject.put("Image2", visicooler_checklists.get(k).getChecklist_img2());

                                        } else if (visicooler_checklists.get(k).getAnswerType().equalsIgnoreCase("Single Choice List")) {
                                            jsonObject.put("Answer", visicooler_checklists.get(k).getAnswer());
                                            jsonObject.put("AnswerId", visicooler_checklists.get(k).getAnsId());
                                            jsonObject.put("Image1", visicooler_checklists.get(k).getChecklist_img1());
                                            jsonObject.put("Image2", visicooler_checklists.get(k).getChecklist_img2());

                                        } else if (visicooler_checklists.get(k).getAnswerType().equalsIgnoreCase("Text")
                                                || visicooler_checklists.get(k).getAnswerType().equalsIgnoreCase("Number")) {
                                            jsonObject.put("Answer", visicooler_checklists.get(k).getEdt_text_value());
                                            jsonObject.put("AnswerId", 0);
                                            jsonObject.put("Image1", "");
                                            jsonObject.put("Image2", "");
                                        } else if (visicooler_checklists.get(k).getAnswerType().equalsIgnoreCase("Image")) {
                                            jsonObject.put("Answer", "");
                                            jsonObject.put("AnswerId", 0);
                                            jsonObject.put("Image1", "");
                                            jsonObject.put("Image2", "");
                                        }

                                        subprogramChecklistArray.put(jsonObject);
                                    }
                                }

                                jsonObject1 = new JSONObject();
                                jsonObject1.put("MID", coverageList.get(coverageIndex).getMID());
                                jsonObject1.put("UserName", _UserId);
                                jsonObject1.put("Ispresent", 1);
                                jsonObject1.put("KeyId", visicooler_presents.get(i).getKeyId());
                                jsonObject1.put("VisicoolerChecklist", subprogramChecklistArray);
                            } else {
                                jsonObject1 = new JSONObject();
                                jsonObject1.put("MID", coverageList.get(coverageIndex).getMID());
                                jsonObject1.put("UserName", _UserId);
                                jsonObject1.put("Ispresent", 0);
                                jsonObject1.put("KeyId", visicooler_presents.get(i).getKeyId());
                                jsonObject1.put("VisicoolerChecklist", "");
                            }

                            VisicoolerArray.put(jsonObject1);
                        }

                        jsonObject = new JSONObject();
                        jsonObject.put("MID", coverageList.get(coverageIndex).getMID());
                        jsonObject.put("Keys", "Visicooler_Data");
                        jsonObject.put("JsonData", VisicoolerArray.toString());
                        jsonObject.put("UserName", coverageList.get(coverageIndex).getUserId());
                        jsonObject.put("SecurityToken", SecurityToken);
                        jsonString = jsonObject.toString();
                        type = CommonString.UPLOADJsonDetail;
                    }

                    break;

                case "POSM_Data":
                    db.open();
                    JSONArray posmArray = new JSONArray();
                    MasterPosm posm_presents = db.getinsertedposmpresentforupload(coverageList.get(coverageIndex).getStoreId(), coverageList.get(coverageIndex).getVisitDate());
                    if (posm_presents != null && posm_presents.getIspresnt() != null && !posm_presents.getIspresnt().equals("")) {
                        if (posm_presents.getIspresnt().equals("1")) {
                            ArrayList<MasterPosm> posmArrayList = db.getinsertedposmforupload(coverageList.get(coverageIndex).getStoreId(), coverageList.get(coverageIndex).getVisitDate());
                            if (posmArrayList.size() > 0) {
                                for (int k = 0; k < posmArrayList.size(); k++) {
                                    jsonObject = new JSONObject();
                                    jsonObject.put("MID", coverageList.get(coverageIndex).getMID());
                                    jsonObject.put("UserName", _UserId);
                                    jsonObject.put("PosmId", posmArrayList.get(k).getPosmId());
                                    jsonObject.put("PosmAvailebility", posmArrayList.get(k).getPosm_yesorno());
                                    String availe = posmArrayList.get(k).getPosm_yesorno();
                                    if (!availe.equals("") && availe.equalsIgnoreCase("Yes")) {
                                        jsonObject.put("PosmImg", posmArrayList.get(k).getPosm_img());
                                        jsonObject.put("ReasonId", 0);
                                    } else if (!availe.equals("") && availe.equalsIgnoreCase("No")) {
                                        jsonObject.put("PosmImg", "");
                                        jsonObject.put("ReasonId", posmArrayList.get(k).getReasonId());

                                    }

                                    posmArray.put(jsonObject);
                                }
                            }
                            jsonObject1 = new JSONObject();
                            jsonObject1.put("MID", coverageList.get(coverageIndex).getMID());
                            jsonObject1.put("UserName", _UserId);
                            jsonObject1.put("Ispresent", 1);
                            jsonObject1.put("Posms", posmArray);
                        } else {

                            jsonObject1 = new JSONObject();
                            jsonObject1.put("MID", coverageList.get(coverageIndex).getMID());
                            jsonObject1.put("UserName", _UserId);
                            jsonObject1.put("Ispresent", 0);
                            jsonObject1.put("Posms", "");
                        }

                        jsonObject = new JSONObject();
                        jsonObject.put("MID", coverageList.get(coverageIndex).getMID());
                        jsonObject.put("Keys", "POSM_Data");
                        jsonObject.put("JsonData", jsonObject1.toString());
                        jsonObject.put("UserName", coverageList.get(coverageIndex).getUserId());
                        jsonObject.put("SecurityToken", SecurityToken);
                        jsonString = jsonObject.toString();
                        type = CommonString.UPLOADJsonDetail;

                    }

                    break;

                case "TOT_Data":
                    JSONArray TotsArray = new JSONArray();
                    db.open();
                    ArrayList<MasterAsset> TOTs = db.getinsertedpaidVisibilityupload(coverageList.get(coverageIndex).getStoreId(), coverageList.get(coverageIndex).getVisitDate());
                    if (TOTs.size() > 0) {
                        for (int k = 0; k < TOTs.size(); k++) {
                            jsonObject = new JSONObject();
                            jsonObject.put("MID", coverageList.get(coverageIndex).getMID());
                            jsonObject.put("UserName", _UserId);
                            jsonObject.put("CategoryId", TOTs.get(k).getCategoryId());
                            jsonObject.put("AssetId", TOTs.get(k).getAssetId());
                            jsonObject.put("Present", TOTs.get(k).getIspresent());
                            String availe = TOTs.get(k).getIspresent();

                            if (!availe.equals("") && availe.equalsIgnoreCase("Yes")) {
                                jsonObject.put("Image", TOTs.get(k).getAsset_img());
                                jsonObject.put("LocationId", TOTs.get(k).getLocationId());
                                jsonObject.put("ReasonId", 0);
                            } else if (!availe.equals("") && availe.equalsIgnoreCase("No")) {
                                jsonObject.put("Image", "");
                                jsonObject.put("LocationId", 0);
                                jsonObject.put("ReasonId", TOTs.get(k).getNonreasonId());
                            }


                            TotsArray.put(jsonObject);
                        }


                        jsonObject = new JSONObject();
                        jsonObject.put("MID", coverageList.get(coverageIndex).getMID());
                        jsonObject.put("Keys", "TOT_Data");
                        jsonObject.put("JsonData", TotsArray.toString());
                        jsonObject.put("UserName", coverageList.get(coverageIndex).getUserId());
                        jsonObject.put("SecurityToken", SecurityToken);
                        jsonString = jsonObject.toString();
                        type = CommonString.UPLOADJsonDetail;
                    }

                    break;

                case "Promotion_Data":
                    JSONArray promoArray = new JSONArray();
                    db.open();
                    ArrayList<MappingPromotion> promotions = db.getinsertedpromotionsupload(coverageList.get(coverageIndex).getStoreId(), coverageList.get(coverageIndex).getVisitDate());
                    if (promotions.size() > 0) {
                        for (int k = 0; k < promotions.size(); k++) {
                            jsonObject = new JSONObject();
                            jsonObject.put("MID", coverageList.get(coverageIndex).getMID());
                            jsonObject.put("UserName", _UserId);
                            jsonObject.put("CategoryId", promotions.get(k).getCategoryId());
                            jsonObject.put("PromoId", promotions.get(k).getPromoId());
                            String availe = promotions.get(k).getPresent();
                            jsonObject.put("Present", availe);


                            if (!availe.equals("") && availe.equalsIgnoreCase("Yes")) {
                                jsonObject.put("Image", promotions.get(k).getImage1());
                                jsonObject.put("PReasonId", 0);
                            } else if (!availe.equals("") && availe.equalsIgnoreCase("No")) {
                                jsonObject.put("Image", "");
                                jsonObject.put("PReasonId", promotions.get(k).getReasonId());
                            }

                            promoArray.put(jsonObject);
                        }


                        jsonObject = new JSONObject();
                        jsonObject.put("MID", coverageList.get(coverageIndex).getMID());
                        jsonObject.put("Keys", "Promotion_Data");
                        jsonObject.put("JsonData", promoArray.toString());
                        jsonObject.put("UserName", coverageList.get(coverageIndex).getUserId());
                        jsonObject.put("SecurityToken", SecurityToken);
                        jsonString = jsonObject.toString();
                        type = CommonString.UPLOADJsonDetail;
                    }

                    break;


                case "CompPromotion_Data":
                    JSONArray comPpromoArray = new JSONArray();
                    db.open();
                    ArrayList<MasterCompany> comppromotions = db.getcomp_promotionforupload(coverageList.get(coverageIndex).getStoreId(), coverageList.get(coverageIndex).getVisitDate(), true);
                    if (comppromotions.size() > 0) {
                        MasterCompany comp_promo_present = comppromotions.get(0);
                        if (comp_promo_present != null && !comp_promo_present.getIspresent().equals("") && comp_promo_present.getIspresent().equals("1")) {
                            for (int k = 0; k < comppromotions.size(); k++) {
                                jsonObject = new JSONObject();
                                jsonObject.put("MID", coverageList.get(coverageIndex).getMID());
                                jsonObject.put("UserName", _UserId);
                                jsonObject.put("KeyId", comppromotions.get(k).getKeyId());
                                jsonObject.put("CompanyId", comppromotions.get(k).getCompanyId());
                                jsonObject.put("BrandId", comppromotions.get(k).getBrandId());
                                jsonObject.put("PromoTypeId", comppromotions.get(k).getProTypeId());
                                jsonObject.put("Description", comppromotions.get(k).getDesc());
                                jsonObject.put("CompPromoImage1", comppromotions.get(k).getComp_img1());
                                jsonObject.put("CompPromoImage2", comppromotions.get(k).getComp_img2());
                                comPpromoArray.put(jsonObject);
                            }

                            jsonObject1 = new JSONObject();
                            jsonObject1.put("MID", coverageList.get(coverageIndex).getMID());
                            jsonObject1.put("UserName", _UserId);
                            jsonObject1.put("Ispresent", 1);
                            jsonObject1.put("CompPromotions", comPpromoArray);

                        } else {
                            jsonObject = new JSONObject();
                            jsonObject.put("MID", coverageList.get(coverageIndex).getMID());
                            jsonObject.put("UserName", _UserId);
                            jsonObject.put("KeyId", comp_promo_present.getKeyId());
                            jsonObject.put("CompanyId", 0);
                            jsonObject.put("BrandId", 0);
                            jsonObject.put("PromoTypeId", 0);
                            jsonObject.put("Description", "");
                            jsonObject.put("CompPromoImage1", "");
                            jsonObject.put("CompPromoImage2", "");
                            comPpromoArray.put(jsonObject);


                            ///adeed
                            jsonObject1 = new JSONObject();
                            jsonObject1.put("MID", coverageList.get(coverageIndex).getMID());
                            jsonObject1.put("UserName", _UserId);
                            jsonObject1.put("Ispresent", 0);
                            jsonObject1.put("CompPromotions", comPpromoArray);
                        }


                        jsonObject = new JSONObject();
                        jsonObject.put("MID", coverageList.get(coverageIndex).getMID());
                        jsonObject.put("Keys", "CompPromotion_Data");
                        jsonObject.put("JsonData", jsonObject1.toString());
                        jsonObject.put("UserName", coverageList.get(coverageIndex).getUserId());
                        jsonObject.put("SecurityToken", SecurityToken);
                        jsonString = jsonObject.toString();

                        type = CommonString.UPLOADJsonDetail;
                    }

                    break;

                case "NPDLaunch_Data":
                    JSONArray NPDArray = new JSONArray();
                    db.open();
                    ArrayList<MasterCompany> compNPDLaunchs = db.getcomp_promotionforupload(coverageList.get(coverageIndex).getStoreId(), coverageList.get(coverageIndex).getVisitDate(), false);
                    if (compNPDLaunchs.size() > 0) {
                        MasterCompany comp_npd_present = compNPDLaunchs.get(0);
                        if (comp_npd_present != null && !comp_npd_present.getIspresent().equals("") && comp_npd_present.getIspresent().equals("1")) {
                            for (int k = 0; k < compNPDLaunchs.size(); k++) {
                                jsonObject = new JSONObject();
                                jsonObject.put("MID", coverageList.get(coverageIndex).getMID());
                                jsonObject.put("UserName", _UserId);
                                jsonObject.put("KeyId", compNPDLaunchs.get(k).getKeyId());
                                jsonObject.put("CompanyId", compNPDLaunchs.get(k).getCompanyId());
                                jsonObject.put("CategoryId", compNPDLaunchs.get(k).getCategoryId());
                                jsonObject.put("Image1", compNPDLaunchs.get(k).getComp_img1());
                                jsonObject.put("Image2", compNPDLaunchs.get(k).getComp_img2());
                                jsonObject.put("SKUDescription", compNPDLaunchs.get(k).getDesc());
                                NPDArray.put(jsonObject);
                            }

                            jsonObject1 = new JSONObject();
                            jsonObject1.put("MID", coverageList.get(coverageIndex).getMID());
                            jsonObject1.put("UserName", _UserId);
                            jsonObject1.put("Ispresent", 1);
                            jsonObject1.put("NPDLaunch", NPDArray);

                        } else {
                            jsonObject = new JSONObject();
                            jsonObject.put("MID", coverageList.get(coverageIndex).getMID());
                            jsonObject.put("UserName", _UserId);
                            jsonObject.put("KeyId", comp_npd_present.getKeyId());
                            jsonObject.put("CompanyId", 0);
                            jsonObject.put("CategoryId", 0);
                            jsonObject.put("Image1", "");
                            jsonObject.put("Image2", "");
                            jsonObject.put("SKUDescription", "");
                            NPDArray.put(jsonObject);

                            ///adeed
                            jsonObject1 = new JSONObject();
                            jsonObject1.put("MID", coverageList.get(coverageIndex).getMID());
                            jsonObject1.put("UserName", _UserId);
                            jsonObject1.put("Ispresent", 0);
                            jsonObject1.put("NPDLaunch", NPDArray);
                        }


                        jsonObject = new JSONObject();
                        jsonObject.put("MID", coverageList.get(coverageIndex).getMID());
                        jsonObject.put("Keys", "NPDLaunch_Data");
                        jsonObject.put("JsonData", jsonObject1.toString());
                        jsonObject.put("UserName", coverageList.get(coverageIndex).getUserId());
                        jsonObject.put("SecurityToken", SecurityToken);
                        jsonString = jsonObject.toString();

                        type = CommonString.UPLOADJsonDetail;
                    }

                    break;

                case "VQPS_Data":
                    JSONArray VQPSArray = new JSONArray();
                    db.open();
                    ArrayList<MasterCompany> VQPSlist = db.getVQPSforupload(coverageList.get(coverageIndex).getStoreId(), coverageList.get(coverageIndex).getVisitDate());
                    if (VQPSlist.size() > 0) {
                        MasterCompany vqps_present = VQPSlist.get(0);
                        if (vqps_present != null && !vqps_present.getIspresent().equals("") && vqps_present.getIspresent().equals("1")) {
                            for (int k = 0; k < VQPSlist.size(); k++) {
                                jsonObject = new JSONObject();
                                jsonObject.put("MID", coverageList.get(coverageIndex).getMID());
                                jsonObject.put("UserName", _UserId);
                                jsonObject.put("KeyId", VQPSlist.get(k).getKeyId());
                                jsonObject.put("CategoryId", VQPSlist.get(k).getCategoryId());
                                jsonObject.put("AssetId", VQPSlist.get(k).getAssetId());
                                jsonObject.put("Image", VQPSlist.get(k).getComp_img1());
                                VQPSArray.put(jsonObject);
                            }

                            jsonObject1 = new JSONObject();
                            jsonObject1.put("MID", coverageList.get(coverageIndex).getMID());
                            jsonObject1.put("UserName", _UserId);
                            jsonObject1.put("Ispresent", 1);
                            jsonObject1.put("VQPS", VQPSArray);

                        } else {
                            jsonObject = new JSONObject();
                            jsonObject.put("MID", coverageList.get(coverageIndex).getMID());
                            jsonObject.put("UserName", _UserId);
                            jsonObject.put("KeyId", vqps_present.getKeyId());
                            jsonObject.put("CategoryId", 0);
                            jsonObject.put("AssetId", 0);
                            jsonObject.put("Image", "");
                            VQPSArray.put(jsonObject);

                            ///adeed
                            jsonObject1 = new JSONObject();
                            jsonObject1.put("MID", coverageList.get(coverageIndex).getMID());
                            jsonObject1.put("UserName", _UserId);
                            jsonObject1.put("Ispresent", 0);
                            jsonObject1.put("VQPS", VQPSArray);
                        }


                        jsonObject = new JSONObject();
                        jsonObject.put("MID", coverageList.get(coverageIndex).getMID());
                        jsonObject.put("Keys", "VQPS_Data");
                        jsonObject.put("JsonData", jsonObject1.toString());
                        jsonObject.put("UserName", coverageList.get(coverageIndex).getUserId());
                        jsonObject.put("SecurityToken", SecurityToken);
                        jsonString = jsonObject.toString();

                        type = CommonString.UPLOADJsonDetail;
                    }

                    break;

            }
            //endregion

            final int[] finalJsonIndex = {keyIndex};
            final String finalKeyName = keyList.get(keyIndex);

            if (jsonString != null && !jsonString.equals("")) {
                OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(CommonString.TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(CommonString.TIMEOUT, TimeUnit.SECONDS).connectTimeout(CommonString.TIMEOUT, TimeUnit.SECONDS).build();
                pd.setMessage("Uploading (" + keyIndex + "/" + keyList.size() + ") \n" + keyList.get(keyIndex) + "\n Store uploading " + (coverageIndex + 1) + "/" + coverageList.size());
                RequestBody jsonData = RequestBody.create(MediaType.parse("application/json"), jsonString);
                adapter = new Retrofit.Builder().baseUrl(CommonString.URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
                PostApi api = adapter.create(PostApi.class);
                Call<ResponseBody> call = null;

                if (type == CommonString.COVERAGE_DETAIL) {
                    call = api.getCoverageDetail(jsonData);
                } else if (type == CommonString.UPLOADJsonDetail) {
                    call = api.getUploadJsonDetail(jsonData);
                }


                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ResponseBody responseBody = response.body();
                        String data = null;
                        if (responseBody != null && response.isSuccessful()) {
                            try {
                                data = response.body().string();
                                if (data.equals("")) {
                                    data_global[0] = "";
                                    AlertandMessages.showAlert((Activity) context, "Invalid Data : problem occured at " + keyList.get(keyIndex), true);
                                } else {
                                    data = data.substring(1, data.length() - 1).replace("\\", "");
                                    data_global[0] = data;

                                    if (data.contains("[{Result:your SecurityToken is Invalid}])")) {
                                        AlertandMessages.showAlert((Activity) context, "In case of a invalid TOKEN can we show a message Invalid Security Token. Multiple Users are logged in using Same User ID.", true);
                                    } else {
                                        if (finalKeyName.equalsIgnoreCase("CoverageDetail_latest")) {
                                            try {
                                                coverageList.get(coverageIndex).setMID(Integer.parseInt(data_global[0]));
                                            } catch (NumberFormatException ex) {
                                                AlertandMessages.showAlert((Activity) context, "Error in Uploading Data at " + finalKeyName, true);
                                            }
                                        } else if (data_global[0].contains(CommonString.KEY_SUCCESS)) {

                                            if (finalKeyName.equalsIgnoreCase("GeoTag")) {
                                                db.open();
                                                db.updateInsertedGeoTagStatus(coverageList.get(coverageIndex).getStoreId(), CommonString.KEY_Y);
                                                db.updateStatus(coverageList.get(coverageIndex).getStoreId(), CommonString.KEY_Y);
                                            }
                                        } else {
                                            AlertandMessages.showAlert((Activity) context, "Error in Uploading Data at " + finalKeyName + " : " + data_global[0], true);
                                        }


                                        finalJsonIndex[0]++;
                                        if (finalJsonIndex[0] != keyList.size()) {
                                            uploadDataWithoutWait(keyList, finalJsonIndex[0], coverageList, coverageIndex);
                                        } else {
                                            pd.setMessage("updating status :" + coverageIndex);
                                            //uploading status D for current store from coverageList
                                            updateStatus(coverageList, coverageIndex, CommonString.KEY_D);
                                        }
                                    }
                                }

                            } catch (Exception e) {
                                pd.dismiss();
                                AlertandMessages.showAlert((Activity) context, "Error in Uploading Data at " + finalKeyName, true);
                            }
                        } else {
                            pd.dismiss();
                            AlertandMessages.showAlert((Activity) context, "Error in Uploading Data at " + finalKeyName, true);

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        isvalid = true;
                        pd.dismiss();
                        AlertandMessages.showAlert((Activity) context, CommonString.MESSAGE_INTERNET_NOT_AVALABLE, true);

                    }
                });

            } else {
                finalJsonIndex[0]++;
                if (finalJsonIndex[0] != keyList.size()) {
                    uploadDataWithoutWait(keyList, finalJsonIndex[0], coverageList, coverageIndex);
                } else {
                    pd.setMessage("updating status :" + coverageIndex);
                    //uploading status D for current store from coverageList
                    updateStatus(coverageList, coverageIndex, CommonString.KEY_D);

                }
            }
        } catch (Exception ex) {
            pd.dismiss();
            AlertandMessages.showAlert((Activity) context, "Error in Uploading status at coverage :" + coverageIndex, true);

        }

    }

    void updateStatus(final ArrayList<CoverageBean> coverageList, final int coverageIndex, String status) {
        if (coverageList.get(coverageIndex) != null) {
            try {
                final int[] tempcoverageIndex = {coverageIndex};
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("Mid", coverageList.get(coverageIndex).getMID());
                jsonObject.put("Status", status);
                jsonObject.put("UserName", coverageList.get(coverageIndex).getUserId());
                jsonObject.put("SecurityToken", SecurityToken);
                OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(CommonString.TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(CommonString.TIMEOUT, TimeUnit.SECONDS).connectTimeout(CommonString.TIMEOUT, TimeUnit.SECONDS).build();

                RequestBody jsonData = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
                adapter = new Retrofit.Builder().baseUrl(CommonString.URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
                PostApi api = adapter.create(PostApi.class);
                Call<ResponseBody> call = null;

                if (designation.equalsIgnoreCase("DBSR")) {
                    call = api.getStoreCoverageStatusDBSR(jsonData);
                } else {
                    call = api.getCoverageStatusDetail(jsonData);
                }


                pd.setMessage("Uploading store status " + (coverageIndex + 1) + "/" + coverageList.size());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ResponseBody responseBody = response.body();
                        String data = null;
                        if (responseBody != null && response.isSuccessful()) {
                            try {
                                data = response.body().string();
                                if (data.equals("")) {
                                    pd.dismiss();
                                    AlertandMessages.showAlert((Activity) context, "Error in Uploading status at coverage :" + coverageIndex, true);
                                } else {
                                    data = data.substring(1, data.length() - 1).replace("\\", "");
                                    if (data.contains("1")) {
                                        db.open();
                                        db.updateCheckoutStatus(coverageList.get(tempcoverageIndex[0]).getStoreId().toString(), CommonString.KEY_D, CommonString.TABLE_Journey_Plan);
                                        db.updateCheckoutStatus(coverageList.get(tempcoverageIndex[0]).getStoreId(), CommonString.KEY_D, "JourneyPlan_NonMerchandised");
                                        // db.updateCheckoutStatus(coverageList.get(tempcoverageIndex[0]).getStoreId(), CommonString.KEY_D, "JourneyPlan_NotCovered");
                                        // db.updateCheckoutStatusDBSR(coverageList.get(tempcoverageIndex[0]).getStoreId(), CommonString.KEY_D, CommonString.TABLE_Journey_Plan_DBSR_Saved);
                                        tempcoverageIndex[0]++;
                                        if (tempcoverageIndex[0] != coverageList.size()) {
                                            uploadDataUsingCoverageRecursive(coverageList, tempcoverageIndex[0]);
                                        } else {
                                            pd.setMessage("uploading images");
                                            String coverageDate = null;
                                            if (coverageList.size() > 0) {
                                                coverageDate = coverageList.get(0).getVisitDate();
                                            } else {
                                                coverageDate = date;
                                            }
                                            uploadImage(coverageDate);
                                        }

                                    } else {
                                        pd.dismiss();
                                        AlertandMessages.showAlert((Activity) context, "Error in Uploading status at coverage :" + coverageIndex, true);
                                    }
                                }

                            } catch (Exception e) {
                                pd.dismiss();
                                AlertandMessages.showAlert((Activity) context, "Error in Uploading status at coverage :" + coverageIndex, true);
                            }
                        } else {
                            pd.dismiss();
                            AlertandMessages.showAlert((Activity) context, "Error in Uploading status at coverage :" + coverageIndex, true);

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        isvalid = true;
                        pd.dismiss();
                        AlertandMessages.showAlert((Activity) context, CommonString.MESSAGE_INTERNET_NOT_AVALABLE, true);

                    }
                });

            } catch (JSONException ex) {
                pd.dismiss();
                AlertandMessages.showAlert((Activity) context, CommonString.MESSAGE_INVALID_JSON, true);
            }
        }

    }

    public void uploadDataUsingCoverageRecursive(ArrayList<CoverageBean> coverageList, int coverageIndex) {
        try {

            preferences = PreferenceManager.getDefaultSharedPreferences(context);
            _UserId = preferences.getString(CommonString.KEY_USERNAME, "");
            designation = preferences.getString(CommonString.KEY_DESIGNATION, "");
            date = preferences.getString(CommonString.KEY_DATE, "");
            SecurityToken = preferences.getString(CommonString.KEY_SecurityToken, "");
            ArrayList<String> keyList = new ArrayList<>();
            keyList.clear();
            String store_id = coverageList.get(coverageIndex).getStoreId();
            storeflagjcp = coverageList.get(coverageIndex).getFlag_from();
            String status = null;
            pd.setMessage("Uploading store " + (coverageIndex + 1) + "/" + coverageList.size());
            db.open();

            ArrayList<MappingJourneyPlan> journeyPlans = db.getSpecificStoreData(storeflagjcp, store_id);
            if (journeyPlans.size() > 0) {
                status = journeyPlans.get(0).getUploadStatus();
            } else {
                ArrayList<MappingJourneyPlan> journeyPlans_DBSR = db.getSpecificStore_DBSRSavedData(store_id);
                if (journeyPlans_DBSR.size() > 0) {
                    status = journeyPlans_DBSR.get(0).getUploadStatus();
                } else {
                    status = null;
                }
            }

            if (status != null && !status.equalsIgnoreCase(CommonString.KEY_D) && !coverageList.get(coverageIndex).getReasonid().equals("11")) {
                keyList.add("CoverageDetail_latest");
                keyList.add("GeoTag");
                keyList.add("Store_Profile");
                keyList.add("Program_Data");
                keyList.add("Visicooler_Data");
                keyList.add("POSM_Data");
                keyList.add("TOT_Data");
                keyList.add("Promotion_Data");
                keyList.add("CompPromotion_Data");
                keyList.add("NPDLaunch_Data");
                keyList.add("VQPS_Data");

            }

            if (keyList.size() > 0) {
                UploadImageWithRetrofit upload = new UploadImageWithRetrofit(context, db, pd, from);
                upload.uploadDataWithoutWait(keyList, 0, coverageList, coverageIndex);
            } else {

                if (++coverageIndex != coverageList.size()) {
                    uploadDataUsingCoverageRecursive(coverageList, coverageIndex);
                } else {
                    String coverageDate = null;
                    if (coverageList.size() > 0) {
                        coverageDate = coverageList.get(0).getVisitDate();
                    } else {
                        coverageDate = date;
                    }

                    uploadImage(coverageDate);
                }
            }
            //endregion
        } catch (Exception e) {
            e.printStackTrace();
            pd.dismiss();
            AlertandMessages.showAlert((Activity) context, CommonString.MESSAGE_SOCKETEXCEPTION, true);
        }

    }

    void uploadImage(String coverageDate) {

        File f = new File(CommonString.FILE_PATH);
        File file[] = f.listFiles();
        if (file.length > 0) {
            uploadedFiles = 0;
            totalFiles = file.length;
            UploadImageRecursive(context, coverageDate);
        } else {
            uploadedFiles = 0;
            totalFiles = file.length;
            new StatusUpload(coverageDate).execute();
        }
    }

    //region StatusUpload
    class StatusUpload extends AsyncTask<String, String, String> {
        String coverageDate;

        StatusUpload(String coverageDate) {
            this.coverageDate = coverageDate;
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                db = new NestleDb(context);
                db.open();
                ArrayList<MappingJourneyPlan> storeList = db.getStoreCheckTableNot_CoverdData(coverageDate, CommonString.KEY_D);
                if (storeList.size() == 0) {
                    storeList = db.getStoreData_DBSR_Saved(coverageDate);
                }

                int l = storeList.size();
                for (int i = 0; i < l; i++) {
                    try {
                        if (storeList.get(i).getUploadStatus().equalsIgnoreCase(CommonString.KEY_D)) {
                            JSONObject jsonObject = new JSONObject();
                            if (storeList.get(i).getMID() == null) {
                                jsonObject.put("Mid", storeList.get(i).getMID());
                            } else {
                                jsonObject.put("Mid", storeList.get(i).getMID());
                            }

                            jsonObject.put("Status", CommonString.KEY_U);
                            jsonObject.put("UserName", _UserId);
                            jsonObject.put("SecurityToken", SecurityToken);

                            UploadImageWithRetrofit upload = new UploadImageWithRetrofit(context);
                            String jsonString2 = jsonObject.toString();
                            String result;
                            result = upload.downloadDataUniversal(jsonString2, CommonString.COVERAGEStatusDetail);
                            if (result.equalsIgnoreCase(CommonString.MESSAGE_NO_RESPONSE_SERVER)) {
                                statusUpdated = false;
                                throw new SocketTimeoutException();
                            } else if (result.equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                                statusUpdated = false;
                                throw new IOException();
                            } else if (result.equalsIgnoreCase(CommonString.MESSAGE_INVALID_JSON)) {
                                statusUpdated = false;
                                throw new JsonSyntaxException("Coverage Status Detail");
                            } else if (result.equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                statusUpdated = false;
                                throw new Exception();
                            } else {
                                statusUpdated = true;

                                if (db.updateCheckoutStatus(String.valueOf(storeList.get(i).getStoreId()), CommonString.KEY_U, CommonString.TABLE_Journey_Plan) > 0) {
                                    db.deleteTableWithStoreID((storeList.get(i).getStoreId().toString()));
                                }

                                if (db.updateCheckoutStatus(String.valueOf(storeList.get(i).getStoreId()), CommonString.KEY_U, "JourneyPlan_NonMerchandised") > 0) {
                                    db.deleteTableWithStoreID((storeList.get(i).getStoreId().toString()));
                                }
                               /* if (db.updateCheckoutStatus(String.valueOf(storeList.get(i).getStoreId()), CommonString.KEY_U, "JourneyPlan_NotCovered") > 0) {
                                    db.deleteTableWithStoreID((storeList.get(i).getStoreId().toString()));
                                }
                                if (db.updateCheckoutStatusDBSR(String.valueOf(storeList.get(i).getStoreId()), CommonString.KEY_U, CommonString.TABLE_Journey_Plan_DBSR_Saved) > 0) {
                                    db.deleteTableWithStoreID((storeList.get(i).getStoreId().toString()));
                                }*/

                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                AlertandMessages.showAlert((Activity) context, CommonString.MESSAGE_INVALID_JSON, true);
            } catch (Exception e) {
                e.printStackTrace();

            }
            if (statusUpdated) {
                return CommonString.KEY_SUCCESS;
            } else {
                return CommonString.KEY_FAILURE;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            if (s.equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                if (totalFiles == uploadedFiles && statusUpdated) {
                    db.deletePreviousUploadedData(date);
                    // db.deletePreviousJouneyPlanDBSRData(date);
                    AlertandMessages.showAlert((Activity) context, "All Data and Images Uploaded", true);
                } else if (totalFiles == uploadedFiles && !statusUpdated) {
                    AlertandMessages.showAlert((Activity) context, "All images uploaded Successfully, but status not updated", true);
                } else {
                    AlertandMessages.showAlert((Activity) context, "Some images not uploaded", true);
                }
            }
        }
    }

    //region DownloadImageTask
    class DownloadImageTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            try {
                downloadImages();
                downloadprogramImages();
                return CommonString.KEY_SUCCESS;
            } catch (FileNotFoundException ex) {
                return CommonString.KEY_FAILURE;
            } catch (IOException ex) {
                return CommonString.KEY_FAILURE;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                pd.dismiss();
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
                builder.setCancelable(false).setTitle(context.getString(R.string.main_menu_activity_name));
                builder.setMessage("All data downloaded Successfully").setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent in = new Intent(context, StoreListActivity.class);
                                in.putExtra(CommonString.TAG_FROM, CommonString.TAG_FROM_JCP);
                                context.startActivity(in);
                                ((Activity) context).finish();
                                dialog.dismiss();
                            }

                        });

                androidx.appcompat.app.AlertDialog alert = builder.create();
                alert.show();

            } else {
                pd.dismiss();
                AlertandMessages.showAlert((Activity) context, "Error in downloading", true);
            }

        }

    }
    //endregion

    //region downloadImages
    void downloadImages() throws IOException {
        //Menu Image Download
        List<MenuMaster> menu_master_list = db.getMenuData(null, "");
        if (menu_master_list.size() > 0) {
            for (int i = 0; i < menu_master_list.size(); i++) {
                String normal_img = menu_master_list.get(i).getNormalIcon();
                String path = menu_master_list.get(i).getMenuPath();
                downloadImageWithname(normal_img, path);
                String done_image = menu_master_list.get(i).getTickIcon();
                downloadImageWithname(done_image, path);
                String greyIcon = menu_master_list.get(i).getGreyIcon();
                downloadImageWithname(greyIcon, path);

            }

        }
    }

    void downloadprogramImages() throws IOException {
        //Menu Image Download
        List<MasterProgram> programs = db.getmasterProgram(null);
        if (programs.size() > 0) {
            for (int i = 0; i < programs.size(); i++) {
                String normal_img = programs.get(i).getProgramNormalIcon();
                String path = programs.get(i).getImagePath();
                downloadImageWithname(normal_img, path);
                String done_image = programs.get(i).getProgramTickIcon();
                downloadImageWithname(done_image, path);

                String subpiconn = programs.get(i).getSubProgramNormalIcon();
                downloadImageWithname(subpiconn, path);
                String subpTickO = programs.get(i).getSubProgramTickIcon();
                downloadImageWithname(subpTickO, path);

            }
        }
    }

    void downloadImageWithname(String image_name, String path) throws IOException {
        if (!new File(CommonString.FILE_PATH_Downloaded + image_name).exists()) {
            if (image_name != null && !image_name.equalsIgnoreCase("NA") && !image_name.equals("")) {
                URL url = new URL(path + image_name);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.getResponseCode();
                c.setConnectTimeout(20000);
                c.connect();

                if (c.getResponseCode() == 200) {
                    int length = c.getContentLength();
                    String size = new DecimalFormat("##.##").format((double) ((double) length / 1024)) + " KB";
                    File file = new File(CommonString.FILE_PATH_Downloaded);
                    file.mkdirs();
                    if (!size.equalsIgnoreCase("0 KB")) {
                        jj = image_name.split("\\/");
                        image_name = jj[jj.length - 1];
                        File outputFile = new File(file, image_name);
                        FileOutputStream fos = null;
                        fos = new FileOutputStream(outputFile);
                        InputStream is1 = (InputStream) c.getInputStream();
                        int bytes = 0;
                        byte[] buffer = new byte[1024];
                        int len1 = 0;

                        while ((len1 = is1.read(buffer)) != -1) {
                            bytes = (bytes + len1);
                            // data.value = (int) ((double) (((double)
                            // bytes) / length) * 100);
                            fos.write(buffer, 0, len1);
                        }

                        fos.close();
                        is1.close();

                    }
                } else {
                    c.disconnect();
                }
            }
        }

    }


    String createTable(TableStructureGetterSetter tableGetSet) {
        List<TableStructure> tableList = tableGetSet.getTableStructure();
        for (int i = 0; i < tableList.size(); i++) {
            String table = tableList.get(i).getSqlText();
            if (db.createtable(table) == 0) {
                return table;
            }
        }
        return CommonString.KEY_SUCCESS;
    }

    //region downloadDataUniversalWithoutWait
    public void downloadDataUniversalWithoutWait(final ArrayList<String> jsonStringList, final ArrayList<String> KeyNames, int downloadindex, int type, final int close) {
        status = 0;
        isvalid = false;
        final String[] data_global = {""};
        String jsonString = "", KeyName = "";
        int jsonIndex = 0;

        if (jsonStringList.size() > 0) {
            jsonString = jsonStringList.get(downloadindex);
            KeyName = KeyNames.get(downloadindex);
            jsonIndex = downloadindex;
            OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(CommonString.TIMEOUT, TimeUnit.SECONDS).writeTimeout(CommonString.TIMEOUT,
                    TimeUnit.SECONDS).connectTimeout(CommonString.TIMEOUT, TimeUnit.SECONDS).build();
            pd.setMessage("Downloading (" + downloadindex + "/" + listSize + ") \n" + KeyName + "");
            RequestBody jsonData = RequestBody.create(MediaType.parse("application/json"), jsonString);
            adapter = new Retrofit.Builder().baseUrl(CommonString.URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
            PostApi api = adapter.create(PostApi.class);
            Call<ResponseBody> call = null;
            if (type == CommonString.LOGIN_SERVICE) {
                call = api.getLogindetail(jsonData);
            } else if (type == CommonString.DOWNLOAD_ALL_SERVICE) {
                call = api.getDownloadAll(jsonData);
            } else if (type == CommonString.COVERAGE_DETAIL) {
                call = api.getCoverageDetail(jsonData);
            } else if (type == CommonString.UPLOADJCPDetail) {
                call = api.getUploadJCPDetail(jsonData);
            } else if (type == CommonString.UPLOADJsonDetail) {
                call = api.getUploadJsonDetail(jsonData);
            } else if (type == CommonString.COVERAGEStatusDetail) {
                call = api.getCoverageStatusDetail(jsonData);
            } else if (type == CommonString.CHECKOUTDetail) {
                call = api.getCheckout(jsonData);
            } else if (type == CommonString.DELETE_COVERAGE) {
                call = api.deleteCoverageData(jsonData);
            }

            final int[] finalJsonIndex = {jsonIndex};
            final String finalKeyName = KeyName;
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ResponseBody responseBody = response.body();
                    String data = null;
                    if (responseBody != null && response.isSuccessful()) {
                        try {
                            data = response.body().string();
                            if (data.equalsIgnoreCase("")) {
                                data_global[0] = "";
                            } else {
                                data = data.substring(1, data.length() - 1).replace("\\", "");
                                data_global[0] = data;
                                if (finalKeyName.equalsIgnoreCase("Table_Structure")) {
                                    editor.putInt(CommonString.KEY_DOWNLOAD_INDEX, finalJsonIndex[0]);
                                    editor.apply();
                                    tableStructureObj = new Gson().fromJson(data, TableStructureGetterSetter.class);
                                    String isAllTableCreated = createTable(tableStructureObj);
                                    if (isAllTableCreated != CommonString.KEY_SUCCESS) {
                                        pd.dismiss();
                                        AlertandMessages.showAlert((Activity) context, isAllTableCreated + " not created", true);
                                    }
                                } else {
                                    editor.putInt(CommonString.KEY_DOWNLOAD_INDEX, finalJsonIndex[0]);
                                    editor.apply();
                                    //region Description
                                    switch (finalKeyName) {
                                        case "Mapping_JourneyPlan":
                                            if (!data.contains("No Data")) {
                                                jcpObject = new Gson().fromJson(data, JCPGetterSetter.class);
                                                if (jcpObject != null && !db.insertJCPData(jcpObject)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showToastMsg(context, "JCP data data not saved");
                                                }
                                            } else {
                                                throw new java.lang.Exception();
                                            }

                                            break;

                                        case "Master_Program":
                                            if (!data.contains("No Data")) {
                                                masterprogram = new Gson().fromJson(data, JCPGetterSetter.class);
                                                if (masterprogram != null && !db.insertmasterprogram(masterprogram)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showToastMsg(context, "Master_Program data data not saved");
                                                }
                                            } else {
                                                throw new java.lang.Exception();
                                            }

                                            break;

                                        case "Menu_Master":
                                            if (!data.contains("No Data")) {
                                                menuMasterGetterSetter = new Gson().fromJson(data, MenuMasterGetterSetter.class);
                                                if (menuMasterGetterSetter != null && !db.insertMenuMasterData(menuMasterGetterSetter)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showToastMsg(context, "Menu Master data not saved");
                                                }
                                            }


                                            break;

                                        case "Mapping_Menu":
                                            if (!data.contains("No Data")) {
                                                mappingMenuGetterSetter = new Gson().fromJson(data, MappingMenuGetterSetter.class);
                                                if (mappingMenuGetterSetter != null && !db.insertMappingMenuData(mappingMenuGetterSetter)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showToastMsg(context, "Mapping Menu data not saved");
                                                }
                                            } else {
                                                throw new java.lang.Exception();
                                            }

                                            break;


                                        case "Master_Checklist":
                                            if (!data.contains("No Data")) {
                                                masterChecklistGetterSetter = new Gson().fromJson(data, MasterChecklistGetterSetter.class);
                                                if (masterChecklistGetterSetter != null && !db.insertMaster_Checklist(masterChecklistGetterSetter)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showSnackbarMsg(context, "Master_Checklist data not saved");
                                                }
                                            } else {
                                                throw new java.lang.Exception();
                                            }

                                            break;


                                        case "Master_ChecklistAnswer":
                                            if (!data.contains("No Data")) {
                                                masterChecklistAnswerGetterSetter = new Gson().fromJson(data, MasterChecklistAnswerGetterSetter.class);
                                                if (masterChecklistAnswerGetterSetter != null && !db.insertMaster_ChecklistAnswer(masterChecklistAnswerGetterSetter)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showToastMsg(context, "Master_ChecklistAnswer data not saved");
                                                }
                                            } else {
                                                throw new java.lang.Exception();
                                            }

                                            break;


                                        case "Mapping_SubProgramChecklist":
                                            if (!data.contains("No Data")) {
                                                mappingsubprogramchecklist = new Gson().fromJson(data, MasterChecklistAnswerGetterSetter.class);
                                                if (mappingsubprogramchecklist != null && !db.insertmappingsubprogramChecklist(mappingsubprogramchecklist)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showToastMsg(context, "Mapping_SubProgramChecklist data not saved");
                                                }
                                            } else {
                                                db.open();
                                                db.delete_table("Mapping_SubProgramChecklist");
                                            }

                                            break;


                                        case "Mapping_Program":
                                            if (!data.contains("No Data")) {
                                                mappingprogram = new Gson().fromJson(data, MasterChecklistAnswerGetterSetter.class);
                                                if (mappingprogram != null && !db.insertmappingprogram(mappingprogram)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showToastMsg(context, "Mapping_Program data not saved");
                                                }
                                            } else {
                                                db.open();
                                                db.delete_table("Mapping_Program");
                                            }

                                            break;

                                        case "Mapping_VisicoolerChecklist":
                                            if (!data.contains("No Data")) {
                                                mappingvisicooler_checklist = new Gson().fromJson(data, MasterChecklistAnswerGetterSetter.class);
                                                if (mappingvisicooler_checklist != null && !db.insertmappingvisicoolerchecklist(mappingvisicooler_checklist)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showToastMsg(context, "Mapping_VisicoolerChecklist data not saved");
                                                }
                                            } else {
                                                db.open();
                                                db.delete_table("Mapping_VisicoolerChecklist");
                                            }

                                            break;

                                        case "Mapping_Visicooler":
                                            if (!data.contains("No Data")) {
                                                mappingvisicooler = new Gson().fromJson(data, MasterChecklistAnswerGetterSetter.class);
                                                if (mappingvisicooler != null && !db.insertmappingvisicooler(mappingvisicooler)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showToastMsg(context, "Mapping_Visicooler data not saved");
                                                }
                                            } else {
                                                db.open();
                                                db.delete_table("Mapping_Visicooler");
                                            }

                                            break;

                                        case "Mapping_Posm":
                                            if (!data.contains("No Data")) {
                                                mappingPosmGetterSetter = new Gson().fromJson(data, MappingPosmGetterSetter.class);
                                                if (mappingPosmGetterSetter != null && !db.insertMapping_Posm(mappingPosmGetterSetter)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showSnackbarMsg(context, "Mapping_Posm data not saved");
                                                }
                                            } else {
                                                db.open();
                                                db.delete_table("Mapping_Posm");
                                            }

                                            break;


                                        case "Master_Posm":
                                            if (!data.contains("No Data")) {
                                                masterPosmGetterSetter = new Gson().fromJson(data, MasterPosmGetterSetter.class);
                                                if (masterPosmGetterSetter != null && !db.insertMaster_PosmData(masterPosmGetterSetter)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showSnackbarMsg(context, "Master_Posm data not saved");
                                                }
                                            } else {
                                                throw new Exception();
                                            }

                                            break;

                                        case "Non_Posm_Reason":
                                            if (!data.contains("No Data")) {
                                                nonPosmReasonGetterSetter = new Gson().fromJson(data, NonPosmReasonGetterSetter.class);
                                                if (nonPosmReasonGetterSetter != null && !db.insertNon_Posm_ReasonData(nonPosmReasonGetterSetter)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showSnackbarMsg(context, "Non_Posm_Reason  data not saved");
                                                }
                                            } else {
                                                db.open();
                                                db.delete_table("Non_Posm_Reason");
                                            }

                                            break;

                                        case "Non_Working_Reason":
                                            if (!data.contains("No Data")) {
                                                nonWorkingObj = new Gson().fromJson(data, NonWorkingReasonGetterSetter.class);
                                                if (nonWorkingObj != null && !db.insertNonWorkingData(nonWorkingObj)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showToastMsg(context, "Non Working Reason data not saved");
                                                }
                                            } else {
                                                throw new java.lang.Exception();
                                            }

                                            break;

                                        case "Master_Category":
                                            if (!data.contains("No Data")) {
                                                masterCategoryGetterSetter = new Gson().fromJson(data, MasterCategoryGetterSetter.class);
                                                if (masterCategoryGetterSetter != null && !db.insertMasterCategory(masterCategoryGetterSetter)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showToastMsg(context, "Master category data not saved");
                                                }
                                            } else {
                                                throw new java.lang.Exception();
                                            }

                                            break;

                                        case "Master_Brand":
                                            if (!data.contains("No Data")) {
                                                masterBrandGetterSetter = new Gson().fromJson(data, MasterBrandGetterSetter.class);
                                                if (masterBrandGetterSetter != null && !db.insertMaster_Brand(masterBrandGetterSetter)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showSnackbarMsg(context, "Master_Brand data not saved");
                                                }
                                            } else {
                                                throw new java.lang.Exception();
                                            }

                                            break;

                                        case "Master_Company":
                                            if (!data.contains("No Data")) {
                                                mastercompany = new Gson().fromJson(data, MasterBrandGetterSetter.class);
                                                if (mastercompany != null && !db.insertMaster_Company(mastercompany)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showSnackbarMsg(context, "Master_Company data not saved");
                                                }
                                            } else {
                                                throw new java.lang.Exception();
                                            }

                                            break;

                                        case "Master_Asset":
                                            if (!data.contains("No Data")) {
                                                masterAssetGetterSetter = new Gson().fromJson(data, MasterAssetGetterSetter.class);
                                                if (masterAssetGetterSetter != null && !db.insertMaster_Asset(masterAssetGetterSetter)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showSnackbarMsg(context, "Master_Document data not saved");
                                                }
                                            } else {
                                                throw new java.lang.Exception();
                                            }

                                            break;


                                        case "Mapping_PaidVisibility":
                                            if (!data.contains("No Data")) {
                                                mappingpaidVisib = new Gson().fromJson(data, MasterAssetGetterSetter.class);
                                                if (mappingpaidVisib != null && !db.insertmappingpaidVisibility(mappingpaidVisib)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showSnackbarMsg(context, "Mapping_PaidVisibility data not saved");
                                                }
                                            } else {
                                                db.open();
                                                db.delete_table("Mapping_PaidVisibility");
                                            }

                                            break;

                                        case "Master_NonAssetReason":
                                            if (!data.contains("No Data")) {
                                                masaternonassetreason = new Gson().fromJson(data, MasterAssetGetterSetter.class);
                                                if (masaternonassetreason != null && !db.insertnonAssetReason(masaternonassetreason)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showSnackbarMsg(context, "Master_NonAssetReason data not saved");
                                                }
                                            } else {
                                                throw new java.lang.Exception();
                                            }


                                            break;

                                        case "Master_AssetLocation":
                                            if (!data.contains("No Data")) {
                                                masterassetlocation = new Gson().fromJson(data, MasterAssetGetterSetter.class);
                                                if (masterassetlocation != null && !db.insertAssetLocation(masterassetlocation)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showSnackbarMsg(context, "Master_AssetLocation data not saved");
                                                }
                                            } else {
                                                throw new java.lang.Exception();
                                            }

                                            break;

                                        case "Mapping_Promotion":
                                            if (!data.contains("No Data")) {
                                                mappingPromotionGetterSetter = new Gson().fromJson(data, MappingPromotionGetterSetter.class);
                                                if (mappingPromotionGetterSetter != null && !db.insertMappingPromotionData(mappingPromotionGetterSetter)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showSnackbarMsg(context, "Mapping_Promotion data not saved");
                                                }
                                            } else {
                                                db.open();
                                                db.delete_table("Mapping_Promotion");
                                            }

                                            break;

                                        case "Master_NonPromotionReason":
                                            if (!data.contains("No Data")) {
                                                masternonpromoReason = new Gson().fromJson(data, MappingPromotionGetterSetter.class);
                                                if (masternonpromoReason != null && !db.insertMasterNonPromotion(masternonpromoReason)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showSnackbarMsg(context, "Master_NonPromotionReason data not saved");
                                                }
                                            } else {
                                                throw new java.lang.Exception();
                                            }

                                            break;

                                        case "Master_PromoType":
                                            if (!data.contains("No Data")) {
                                                masterpromoType = new Gson().fromJson(data, MappingPromotionGetterSetter.class);
                                                if (masterpromoType != null && !db.insertMasterPromoType(masterpromoType)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showSnackbarMsg(context, "Master_PromoType data not saved");
                                                }
                                            } else {
                                                throw new java.lang.Exception();
                                            }

                                            break;


                                        case "JourneyPlan_NonMerchandised":
                                            if (!data.contains("No Data")) {
                                                jcpObject = new Gson().fromJson(data, JCPGetterSetter.class);
                                                if (jcpObject != null && !db.insertJourneyPlan_NonMerchandised(jcpObject)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showToastMsg(context, "JourneyPlan_NonMerchandisednot saved");
                                                }
                                            } else {
                                                //  throw new java.lang.Exception();
                                            }


                                            break;
                                        case "Master_NonProgramReason":
                                            if (!data.contains("No Data")) {
                                                masterNonProgramReasonGetterSetter = new Gson().fromJson(data, MasterNonProgramReasonGetterSetter.class);
                                                if (masterNonProgramReasonGetterSetter != null && !db.insertMaster_NonProgramReason(masterNonProgramReasonGetterSetter)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showToastMsg(context, "Master_NonProgramReason saved");
                                                }
                                            } else {
                                                //  throw new java.lang.Exception();
                                            }


                                            break;


                                        case "JourneyPlan_NotCovered":
                                            if (!data.contains("No Data")) {
                                                jcpObject = new Gson().fromJson(data, JCPGetterSetter.class);
                                                if (jcpObject != null && !db.insertJourneyPlan_NotCovered(jcpObject)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showToastMsg(context, "JourneyPlan_NotCovered not saved");
                                                }
                                            } else {
                                                //  throw new java.lang.Exception();
                                            }

                                            break;
                                            case "Mapping_VQPS_VisibilityDrive":
                                            if (!data.contains("No Data")) {
                                                mappingVQPSVisibilityDriveGetterSetter = new Gson().fromJson(data, MappingVQPSVisibilityDriveGetterSetter.class);
                                                if (mappingVQPSVisibilityDriveGetterSetter != null && !db.insertVisibilityDriveData(mappingVQPSVisibilityDriveGetterSetter)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showToastMsg(context, "Mapping_VQPS_VisibilityDrive not saved");
                                                }
                                            } else {
                                                //  throw new java.lang.Exception();
                                            }

                                            break;
                                            case "Mapping_RD_VisibilityDrive":
                                            if (!data.contains("No Data")) {
                                                mappingRDVisibilityDriveGetterSetter = new Gson().fromJson(data, MappingRDVisibilityDriveGetterSetter.class);
                                                if (mappingRDVisibilityDriveGetterSetter != null && !db.insertRDVisibilityDriveData(mappingRDVisibilityDriveGetterSetter)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showToastMsg(context, "Mapping_RD_VisibilityDrive not saved");
                                                }
                                            } else {
                                                //  throw new java.lang.Exception();
                                            }

                                            break;
                                            case "Master_DriveNonVisibility":
                                            if (!data.contains("No Data")) {
                                                masterDriveNonVisibilityGetterSetter = new Gson().fromJson(data, MasterDriveNonVisibilityGetterSetter.class);
                                                if (masterDriveNonVisibilityGetterSetter != null && !db.insertMaster_DriveNonVisibilityData(masterDriveNonVisibilityGetterSetter)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showToastMsg(context, "Master_DriveNonVisibility not saved");
                                                }
                                            } else {
                                                //  throw new java.lang.Exception();
                                            }

                                            break;

                                        case "JournyPlan_DBSR":
                                            if (!data.contains("No Data")) {
                                                jcpObject = new Gson().fromJson(data, JCPGetterSetter.class);
                                                if (jcpObject != null && !db.insertJCP_DBSRData(jcpObject)) {
                                                    pd.dismiss();
                                                    AlertandMessages.showToastMsg(context, "JournyPlan_DBSR not saved");
                                                }
                                            } else {
                                                throw new java.lang.Exception();
                                            }
                                            break;
                                    }
                                }
                            }

                            finalJsonIndex[0]++;
                            if (finalJsonIndex[0] != KeyNames.size()) {
                                editor.putInt(CommonString.KEY_DOWNLOAD_INDEX, finalJsonIndex[0]);
                                editor.apply();
                                downloadDataUniversalWithoutWait(jsonStringList, KeyNames, finalJsonIndex[0], CommonString.DOWNLOAD_ALL_SERVICE, 1);
                            } else {
                                editor.putInt(CommonString.KEY_DOWNLOAD_INDEX, 0);
                                editor.apply();

                                if (close == 0) {
                                    pd.dismiss();
                                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
                                    builder.setCancelable(false);
                                    builder.setMessage("Report data downloaded Successfully").setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.dismiss();
                                                }

                                            });

                                    androidx.appcompat.app.AlertDialog alert = builder.create();
                                    alert.show();

                                } else {
                                    pd.setMessage("Downloading Images");
                                    new DownloadImageTask().execute();
                                }
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            editor.putInt(CommonString.KEY_DOWNLOAD_INDEX, finalJsonIndex[0]);
                            editor.apply();
                            pd.dismiss();
                            AlertandMessages.showAlert((Activity) context, "Error in downloading Data at " + finalKeyName, true);
                        }
                    } else {
                        editor.putInt(CommonString.KEY_DOWNLOAD_INDEX, finalJsonIndex[0]);
                        editor.apply();
                        pd.dismiss();
                        AlertandMessages.showAlert((Activity) context, "Error in downloading Data at " + finalKeyName, true);

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    isvalid = true;
                    pd.dismiss();
                    if (t instanceof SocketTimeoutException) {
                        AlertandMessages.showAlert((Activity) context, CommonString.MESSAGE_INTERNET_NOT_AVALABLE, true);
                    } else if (t instanceof IOException) {
                        AlertandMessages.showAlert((Activity) context, CommonString.MESSAGE_INTERNET_NOT_AVALABLE, true);
                    } else if (t instanceof SocketException) {
                        AlertandMessages.showAlert((Activity) context, CommonString.MESSAGE_INTERNET_NOT_AVALABLE, true);
                    } else {
                        AlertandMessages.showAlert((Activity) context, CommonString.MESSAGE_INTERNET_NOT_AVALABLE, true);
                    }

                }
            });

        } else {
            editor.putInt(CommonString.KEY_DOWNLOAD_INDEX, 0);
            editor.apply();

        }

    }
    //endregion

}
