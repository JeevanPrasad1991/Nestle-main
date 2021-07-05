package com.cpm.Nestle.oneQad;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import androidx.annotation.RequiresApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cpm.Nestle.MainActivity;
import com.cpm.Nestle.R;
import com.cpm.Nestle.getterSetter.LoginGsonGetterSetter;
import com.cpm.Nestle.getterSetter.TodayQuestion;
import com.cpm.Nestle.progressbar.CircularProgressBar;
import com.cpm.Nestle.upload.Retrofit_method.PostApi;
import com.cpm.Nestle.utilities.AlertandMessages;
import com.cpm.Nestle.utilities.CommonString;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class OneQADActivity extends AppCompatActivity {
    int durationMillis = 2000;
    CircularProgressBar c2;
    CountDownTimer countDownTimer;
    private Dialog dialog;
    RelativeLayout rel_text, rel_progress;
    TextView tv_welldone, tv_question, tv_time_Up;
    FloatingActionButton fab;

    Animation animBounce, animBounceZoom;
    RecyclerView rec_quiz;
    boolean checkFlag = false;
    boolean timeElapseFlag = false;
    ValueAdapter valueAdapter;
    private Retrofit adapter;
    RelativeLayout rel_qad;

    private SharedPreferences preferences = null;
    private SharedPreferences.Editor editor = null;
    String userId, visit_date,SecurityToken;
    TodayQuestion selectedObj = new TodayQuestion();
    final AnswerData answerData = new AnswerData();
    List<TodayQuestion> todayQuestion = new ArrayList<>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_qad);
        context = this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextAppearance(context, R.style.changestext_sizefor_mobile);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        userId = preferences.getString(CommonString.KEY_USERNAME, "");
        visit_date = preferences.getString(CommonString.KEY_DATE, "");
        SecurityToken= preferences.getString(CommonString.KEY_SecurityToken, "");

        animBounce = AnimationUtils.loadAnimation(context, R.anim.bounce);
        animBounceZoom = AnimationUtils.loadAnimation(context, R.anim.bounce_with_zoom);

        rec_quiz = (RecyclerView) findViewById(R.id.rec_quiz);
        rel_text = (RelativeLayout) findViewById(R.id.rel_layout_text);
        rel_progress = (RelativeLayout) findViewById(R.id.rel_layout_progress);
        tv_welldone = (TextView) findViewById(R.id.tv_welldone);
        tv_question = (TextView) findViewById(R.id.tv_question);
        tv_time_Up = (TextView) findViewById(R.id.tv_time_Up);
        rel_qad = (RelativeLayout) findViewById(R.id.rel_qad);


        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedObj.getQuestion() == null) {
                    answerData.setAnswer_id("0");
                    answerData.setQuestion_id(todayQuestion.get(0).getQuestionId().toString());
                } else {
                    answerData.setAnswer_id(selectedObj.getAnswerId().toString());
                    answerData.setQuestion_id(selectedObj.getQuestionId().toString());
                }

                answerData.setUsername(userId);
                answerData.setVisit_date(visit_date);
                if ((checkNetIsAvailable())) {
                    try {
                        final String[] data_global = {""};
                        final OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(20, TimeUnit.SECONDS).writeTimeout(20, TimeUnit.SECONDS).connectTimeout(20, TimeUnit.SECONDS).build();
                        JSONArray answerDetaills = new JSONArray();
                        JSONObject object = new JSONObject();

                        //upload answer OneQad
                        object.put("ANSWER_ID", answerData.getAnswer_id());
                        object.put("QUESTION_ID", answerData.getQuestion_id());
                        object.put("VISIT_DATE", answerData.getVisit_date());
                        object.put("USER_NAME", answerData.getUsername());
                        answerDetaills.put(object);

                        object = new JSONObject();
                        object.put("MID", "0");
                        object.put("Keys", "TODAY_ANSWER");
                        object.put("JsonData", answerDetaills.toString());
                        object.put("UserName", userId);
                        object.put("SecurityToken", SecurityToken);


                        String jsonString = object.toString();
                        if (jsonString != null && !jsonString.equals("")) {
                            showProgress();
                            RequestBody jsonData = RequestBody.create(MediaType.parse("application/json"), jsonString);
                            adapter = new Retrofit.Builder().baseUrl(CommonString.URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
                            PostApi api = adapter.create(PostApi.class);
                            Call<ResponseBody> call = api.getUploadJsonDetail(jsonData);
                            call.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    ResponseBody responseBody = response.body();
                                    String data = null;
                                    if (responseBody != null && response.isSuccessful()) {
                                        try {
                                            data = response.body().string();
                                            if (data.equals("")) {
                                            } else {
                                                data = data.substring(1, data.length() - 1).replace("\\", "");
                                                data_global[0] = data;
                                                if (data.contains("Success")) {
                                                    String visit_date = preferences.getString(CommonString.KEY_DATE, null);
                                                    editor = preferences.edit();
                                                    editor.putBoolean(CommonString.KEY_IS_QUIZ_DONE + visit_date, true);
                                                    editor.commit();
                                                    Intent intent = new Intent(context, MainActivity.class);
                                                    startActivity(intent);
                                                    overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                                                    OneQADActivity.this.finish();
                                                } else {
                                                    editor = preferences.edit();
                                                    editor.putString(CommonString.KEY_QUESTION_CD + visit_date, answerData.getQuestion_id());
                                                    editor.putString(CommonString.KEY_ANSWER_CD + visit_date, answerData.getAnswer_id());
                                                    editor.commit();
                                                    Intent intent = new Intent(context, MainActivity.class);
                                                    startActivity(intent);
                                                    overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                                                    OneQADActivity.this.finish();
                                                }
                                            }

                                        } catch (Exception e) {
                                            dialog.dismiss();
                                            AlertandMessages.showAlertlogin((Activity) context, CommonString.MESSAGE_SOCKETEXCEPTION);
                                        }
                                    } else {
                                        dialog.dismiss();
                                        AlertandMessages.showAlertlogin((Activity) context, getString(R.string.nonetwork));

                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    dialog.dismiss();
                                    AlertandMessages.showAlert((Activity) context, CommonString.MESSAGE_INTERNET_NOT_AVALABLE, true);
                                }
                            });

                        }
                        dialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    showToast(getString(R.string.nonetwork));
                }

            }
        });

        c2 = (CircularProgressBar) findViewById(R.id.circularprogressbar1);
        c2.setTitle("June");

        getOneqad();
    }

    private boolean checkNetIsAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public void showComment() {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.comment_layout);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.TOP;
        //wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        dialog.show();

    }

    void showProgress() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.progress_layout);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        dialog.setCancelable(false);
        //wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        dialog.show();
    }

    public class ValueAdapter extends RecyclerView.Adapter<ValueAdapter.MyViewHolder> {
        private LayoutInflater inflator;

        List<TodayQuestion> data;

        public ValueAdapter(Context context, List<TodayQuestion> data) {
            inflator = LayoutInflater.from(context);
            this.data = data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            View view = inflator.inflate(R.layout.oneqad_answer_layout, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
            final TodayQuestion current = data.get(position);
            viewHolder.btn_answer.setText(current.getAnswer());

            viewHolder.btn_answer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!timeElapseFlag && !checkFlag) {
                        checkFlag = true;
                        selectedObj = current;
                        //disable timer
                        countDownTimer.cancel();
                        fab.show();
                        valueAdapter.notifyDataSetChanged();
                    }
                }
            });

            if (checkFlag) {
                if (selectedObj == current) {
                    if (current.getRightAnswer()) {
                        viewHolder.btn_answer.setBackground(getResources().getDrawable(R.drawable.transition_1));
                        TransitionDrawable transition6 = (TransitionDrawable) viewHolder.btn_answer.getBackground();
                        transition6.startTransition(durationMillis);
                        showCorrect();
                    } else {
                        viewHolder.btn_answer.setBackground(getResources().getDrawable(R.drawable.transition_red));
                        TransitionDrawable transition6 = (TransitionDrawable) viewHolder.btn_answer.getBackground();
                        transition6.startTransition(durationMillis);
                        showComment();
                    }

                } else if (current.getRightAnswer()) {
                    viewHolder.btn_answer.setBackground(getResources().getDrawable(R.drawable.transition_1));
                    TransitionDrawable transition6 = (TransitionDrawable) viewHolder.btn_answer.getBackground();
                    transition6.startTransition(durationMillis);
                }
            } else if (timeElapseFlag && current.getRightAnswer()) {
                viewHolder.btn_answer.setBackground(getResources().getDrawable(R.drawable.transition_1));
                TransitionDrawable transition6 = (TransitionDrawable) viewHolder.btn_answer.getBackground();
                transition6.startTransition(durationMillis);
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            Button btn_answer;

            public MyViewHolder(View itemView) {
                super(itemView);
                btn_answer = (Button) itemView.findViewById(R.id.btn_answer);

            }
        }
    }

    void showCorrect() {
        c2.setTitle("");
        rel_progress.setVisibility(View.GONE);
        rel_text.setVisibility(View.VISIBLE);
        tv_welldone.startAnimation(animBounceZoom);
    }

    private void getOneqad() {
        try {
            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.progress_layout);
            Window window = dialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;

            window.setAttributes(wlp);
            dialog.show();
            final OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(20, TimeUnit.SECONDS).writeTimeout(20, TimeUnit.SECONDS)
                    .connectTimeout(20, TimeUnit.SECONDS).build();
            //Download Todays Questions
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserName", userId.toLowerCase());
            jsonObject.put("Downloadtype", "OQAD");
            jsonObject.put("Param1", "");
            jsonObject.put("Param2", "");
            jsonObject.put("SecurityToken", SecurityToken);
            String jsonString = jsonObject.toString();

            RequestBody questionjsonData = RequestBody.create(MediaType.parse("application/json"), jsonString);
            adapter = new Retrofit.Builder().baseUrl(CommonString.URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
            PostApi api1 = adapter.create(PostApi.class);
            Call<ResponseBody> callquest = api1.getDownloadAllUSINGLOGIN(questionjsonData);
            callquest.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ResponseBody responseBody = response.body();
                    String data = null;
                    if (responseBody != null && response.isSuccessful()) {
                        try {
                            data = response.body().string();
                            data = data.substring(1, data.length() - 1).replace("\\", "");
                            if (data.equalsIgnoreCase(CommonString.MESSAGE_SOCKETEXCEPTION)) {
                                dialog.dismiss();
                                AlertandMessages.showAlertlogin((Activity) context, getString(R.string.nonetwork));
                            } else if (data.contains("No Data")) {
                                dialog.dismiss();
                                Intent intent = new Intent(context, MainActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                                OneQADActivity.this.finish();
                            } else {
                                Gson gs = new Gson();
                                final LoginGsonGetterSetter userques = gs.fromJson(data.trim(), LoginGsonGetterSetter.class);
                                dialog.dismiss();
                                if (userques.getTodayQuestion().size() > 0 &&
                                        userques.getTodayQuestion().get(0).getStatus().equals("N")) {
                                    for (int i = 0; i < userques.getTodayQuestion().size(); i++) {
                                        if (userques.getTodayQuestion().get(i).getRightAnswer().toString()
                                                .equalsIgnoreCase("true")) {
                                            break;
                                        }
                                    }

                                    todayQuestion = userques.getTodayQuestion();
                                    rel_qad.setVisibility(View.VISIBLE);
                                    c2.setSubTitle("");
                                    countDownTimer = new CountDownTimer(60000, 1000) {

                                        public void onTick(long millisUntilFinished) {
                                            c2.setTitle(millisUntilFinished / 1000 + "");
                                            c2.setProgress(((int) millisUntilFinished / 1000) * 5);
                                        }

                                        public void onFinish() {
                                            c2.setTitle("Time Up");
                                            timeElapseFlag = true;
                                            c2.setVisibility(View.INVISIBLE);
                                            tv_time_Up.setVisibility(View.VISIBLE);
                                            fab.show();
                                            valueAdapter.notifyDataSetChanged();

                                        }

                                    }.start();

                                    tv_question.setText(todayQuestion.get(0).getQuestion());
                                    valueAdapter = new ValueAdapter(context, todayQuestion);
                                    rec_quiz.setAdapter(valueAdapter);
                                    rec_quiz.setLayoutManager(new LinearLayoutManager(context));

                                } else {
                                    Intent intent = new Intent(context, MainActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                                    OneQADActivity.this.finish();
                                }


                            }
                        } catch (Exception e) {
                            dialog.dismiss();
                            AlertandMessages.showAlertlogin((Activity) context, CommonString.MESSAGE_NO_RESPONSE_SERVER + "(" + e.toString() + ")");
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    dialog.dismiss();
                    AlertandMessages.showAlertlogin((Activity) context, CommonString.MESSAGE_INTERNET_NOT_AVALABLE);

                }
            });


        } catch (Exception e) {
            dialog.dismiss();
            e.printStackTrace();
            AlertandMessages.showAlertlogin((Activity) context, CommonString.MESSAGE_SOCKETEXCEPTION + "(" + e.toString() + ")");
        }
    }

    class AnswerData {
        public String question_id, answer_id, username, visit_date, right_answer;

        public String getQuestion_id() {
            return question_id;
        }

        public void setQuestion_id(String question_id) {
            this.question_id = question_id;
        }

        public String getAnswer_id() {
            return answer_id;
        }

        public void setAnswer_id(String answer_id) {
            this.answer_id = answer_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getVisit_date() {
            return visit_date;
        }

        public void setVisit_date(String visit_date) {
            this.visit_date = visit_date;
        }

        public String getRight_answer() {
            return right_answer;
        }

        public void setRight_answer(String right_answer) {
            this.right_answer = right_answer;
        }
    }
}
