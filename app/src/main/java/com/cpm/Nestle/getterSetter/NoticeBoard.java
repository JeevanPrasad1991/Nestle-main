
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoticeBoard {

    @SerializedName("USER_ID")
    @Expose
    private String USER_ID;
    @SerializedName("PROJECT_CODE")
    @Expose
    private String projectCode;
    @SerializedName("NOTICE_BOARD")
    @Expose
    private String noticeUrl;
    @SerializedName("QUIZ_URL")
    @Expose
    private String quizUrl;

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getNoticeUrl() {
        return noticeUrl;
    }

    public void setNoticeUrl(String noticeUrl) {
        this.noticeUrl = noticeUrl;
    }

    public String getQuizUrl() {
        return quizUrl;
    }

    public void setQuizUrl(String quizUrl) {
        this.quizUrl = quizUrl;
    }

}
