package com.cpm.Nestle.getterSetter;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jeevanp on 14-12-2017.
 */

public class LoginGsonGetterSetter {
    @SerializedName("Result")
    @Expose
    private List<Result> result = null;

    public List<Result> getResult() {
        return result;
    }
    public void setResult(List<Result> result) {
        this.result = result;
    }

    //today question
    @SerializedName("OQAD")
    @Expose
    private List<TodayQuestion> todayQuestion = null;

    public List<TodayQuestion> getTodayQuestion() {
        return todayQuestion;
    }

}
