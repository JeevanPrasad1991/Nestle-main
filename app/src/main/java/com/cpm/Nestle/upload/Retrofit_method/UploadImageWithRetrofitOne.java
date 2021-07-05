package com.cpm.Nestle.upload.Retrofit_method;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Log;

import com.cpm.Nestle.R;
import com.cpm.Nestle.database.NestleDb;
import com.cpm.Nestle.utilities.AlertandMessages;
import com.cpm.Nestle.utilities.CommonString;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import retrofit.Converter;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by jeevanp on 10/4/2017.
 */

public class UploadImageWithRetrofitOne {

    RequestBody body1;
    PostApiForUpload api;
    retrofit.Call<String> call;
    private Retrofit adapter;
    int status = 0;
    int count = 0;
    public static int uploadedFiles = 0;
    public static int totalFiles = 0;
    boolean isvalid = false, statusUpdated = true;
    Context context;
    String visitDate, userID, uploadStatus;
    int storeId = 0;
    NestleDb db;
    ProgressDialog pd;
    // ArrayList<JourneyPlan> storeList, storeList_deviation;

    public UploadImageWithRetrofitOne(Context context) {
        this.context = context;
        pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setTitle("Please wait");
        pd.setMessage("Uploading images");
        pd.show();
    }

    public UploadImageWithRetrofitOne() {
    }

    public UploadImageWithRetrofitOne(Context context, String msg) {
        this.context = context;
        pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setTitle("Please wait");
        pd.setMessage(msg);
        pd.show();
    }


    public UploadImageWithRetrofitOne(String visitDate, String userId, Context context) {
        this.visitDate = visitDate;
        this.userID = userId;
        this.context = context;
        pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setTitle("Please wait");
        pd.setMessage("Uploading images");
        pd.show();
    }


    //region UploadImageRecursive
    //endregion

    String getParsedDate(String filename) {
        String testfilename = filename;
        testfilename = testfilename.substring(testfilename.indexOf("-") + 1);
        testfilename = testfilename.substring(0, testfilename.indexOf("-"));
        return testfilename;
    }


    public File saveBitmapToFileSmaller(File file) {
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

    class StringConverterFactory implements Converter.Factory {
        private StringConverterFactory() {
        }

        @Override
        public Converter<String> get(Type type) {
            Class<?> cls = (Class<?>) type;
            if (String.class.isAssignableFrom(cls)) {
                return new StringConverter();
            }
            return null;
        }
    }

    private static class StringConverter implements Converter<String> {
        private static final MediaType PLAIN_TEXT = MediaType.parse("text/plain; charset=UTF-8");

        @Override
        public String fromBody(ResponseBody body) throws IOException {
            return new String(body.bytes());
        }

        @Override
        public RequestBody toBody(String value) {
            return RequestBody.create(PLAIN_TEXT, convertToBytes(value));
        }

        private static byte[] convertToBytes(String string) {
            try {
                return string.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public String UploadImage2(final String filename, String foldername, String folderPath,String _UserId,String SecurityToken) {
        try {
            status = 0;
            File originalFile = new File(folderPath + filename);
            final File finalFile = saveBitmapToFileSmaller(originalFile);
            isvalid = false;
            String date;
            if (filename.contains("-")) {
                date = getParsedDate(filename);
            } else {
                date = visitDate;
            }

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setConnectTimeout(20, TimeUnit.SECONDS);
            okHttpClient.setWriteTimeout(20, TimeUnit.SECONDS);
            okHttpClient.setReadTimeout(20, TimeUnit.SECONDS);

            com.squareup.okhttp.RequestBody photo = com.squareup.okhttp.RequestBody.create(com.squareup.okhttp.MediaType.parse("application/octet-stream"), finalFile);
            body1 = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("file", finalFile.getName(), photo)
                    .addFormDataPart("Foldername", foldername).
                            addFormDataPart("UserName", _UserId).
                            addFormDataPart("SecurityToken", SecurityToken).
                            addFormDataPart("Path", date).build();

            adapter = new retrofit.Retrofit.Builder()
                    .baseUrl(CommonString.URL3)
                    .client(okHttpClient)
                    .addConverterFactory(new StringConverterFactory())
                    .build();
            PostApiForUpload api = adapter.create(PostApiForUpload.class);

            retrofit.Call<String> call = api.getUploadImageRetrofitOne(body1);
           // Call<ResponseBody> observable = api.getUploadImage(body);
            call.enqueue(new retrofit.Callback<String>() {

                @Override
                public void onResponse(Response<String> response) {
                    if (response.isSuccess() && response.body().contains("Success")) {
                        finalFile.delete();
                        uploadedFiles++;
                        isvalid = true;
                        status = 1;
                    } else {
                        isvalid = true;
                        status = 0;
                        uploadedFiles++;
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    isvalid = true;
                    //Toast.makeText(context, finalFile.getName() + " not uploaded", Toast.LENGTH_SHORT).show();
                    if (t instanceof IOException || t instanceof SocketTimeoutException || t instanceof SocketException) {
                        status = -1;
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
                return CommonString.KEY_SUCCESS;
            } else if (status == -1) {
                return CommonString.MESSAGE_SOCKETEXCEPTION;
            } else {
                return CommonString.KEY_FAILURE;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommonString.KEY_FAILURE;

        }
    }
}
