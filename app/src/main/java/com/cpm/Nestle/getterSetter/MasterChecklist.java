
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MasterChecklist {
    public Integer getChecklistSequence() {
        return ChecklistSequence;
    }

    public void setChecklistSequence(Integer checklistSequence) {
        ChecklistSequence = checklistSequence;
    }

    @SerializedName("ChecklistSequence")
    @Expose
    private Integer ChecklistSequence;
    @SerializedName("ChecklistId")
    @Expose
    private Integer checklistId;
    @SerializedName("ChecklistName")
    @Expose
    private String checklistName;
    @SerializedName("AnswerType")
    @Expose
    private String answerType;
    @SerializedName("QuestionImageAllow1")
    @Expose
    private Integer questionImageAllow1;
    @SerializedName("QuestionImageAllow2")
    @Expose
    private Boolean questionImageAllow2;

    String keyId = "";

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public Boolean getBarcode() {
        return Barcode;
    }

    public void setBarcode(Boolean barcode) {
        Barcode = barcode;
    }

    @SerializedName("Barcode")
    @Expose
    private Boolean Barcode;

    @SerializedName("QuestionImageLeble1")
    @Expose
    private String questionImageLeble1;

    @SerializedName("QuestionImageLeble2")
    @Expose
    private String questionImageLeble2;

    @SerializedName("QuestionGridAllow1")
    @Expose
    private boolean questionGridAllow1;

    public String getQuestionImageLeble1() {
        return questionImageLeble1;
    }

    public void setQuestionImageLeble1(String questionImageLeble1) {
        this.questionImageLeble1 = questionImageLeble1;
    }

    public String getQuestionImageLeble2() {
        return questionImageLeble2;
    }

    public void setQuestionImageLeble2(String questionImageLeble2) {
        this.questionImageLeble2 = questionImageLeble2;
    }

    public boolean getQuestionGridAllow1() {
        return questionGridAllow1;
    }

    public void setQuestionGridAllow1(boolean questionGridAllow1) {
        this.questionGridAllow1 = questionGridAllow1;
    }

    public boolean getQuestionGridAllow2() {
        return questionGridAllow2;
    }

    public void setQuestionGridAllow2(boolean questionGridAllow2) {
        this.questionGridAllow2 = questionGridAllow2;
    }

    @SerializedName("QuestionGridAllow2")
    @Expose
    private boolean questionGridAllow2;

    public Integer getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(Integer checklistId) {
        this.checklistId = checklistId;
    }

    public String getChecklistName() {
        return checklistName;
    }

    public void setChecklistName(String checklistName) {
        this.checklistName = checklistName;
    }

    public String getAnswerType() {
        return answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public Integer getQuestionImageAllow1() {
        return questionImageAllow1;
    }

    public void setQuestionImageAllow1(Integer questionImageAllow1) {
        this.questionImageAllow1 = questionImageAllow1;
    }

    public Boolean getQuestionImageAllow2() {
        return questionImageAllow2;
    }

    public void setQuestionImageAllow2(Boolean questionImageAllow2) {
        this.questionImageAllow2 = questionImageAllow2;
    }


    String answer = "";

    String checklist_img1 = "";
    String checklist_img2 = "";

    public String getBinary_btn_value() {
        return binary_btn_value;
    }

    public void setBinary_btn_value(String binary_btn_value) {
        this.binary_btn_value = binary_btn_value;
    }

    String binary_btn_value = "";
    int binary_btn_ansId = 0;

    public int getBinary_btn_ansId() {
        return binary_btn_ansId;
    }

    public void setBinary_btn_ansId(int binary_btn_ansId) {
        this.binary_btn_ansId = binary_btn_ansId;
    }

    public String getEdt_text_value() {
        return edt_text_value;
    }

    public void setEdt_text_value(String edt_text_value) {
        this.edt_text_value = edt_text_value;
    }

    String edt_text_value = "";
    String questionImage1 = "";
    String checklistImglevel1 = "";

    public String getChecklistImglevel1() {
        return checklistImglevel1;
    }

    public void setChecklistImglevel1(String checklistImglevel1) {
        this.checklistImglevel1 = checklistImglevel1;
    }

    public String getChecklistImglevel2() {
        return checklistImglevel2;
    }

    public void setChecklistImglevel2(String checklistImglevel2) {
        this.checklistImglevel2 = checklistImglevel2;
    }

    String checklistImglevel2 = "";

    public String getQuestionImage1() {
        return questionImage1;
    }

    public void setQuestionImage1(String questionImage1) {
        this.questionImage1 = questionImage1;
    }

    public String getQuestionImage2() {
        return questionImage2;
    }

    public void setQuestionImage2(String questionImage2) {
        this.questionImage2 = questionImage2;
    }

    String questionImage2 = "";
    int ansId = 0, rating = 0;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getChecklist_img1() {
        return checklist_img1;
    }

    public void setChecklist_img1(String checklist_img1) {
        this.checklist_img1 = checklist_img1;
    }

    public String getChecklist_img2() {
        return checklist_img2;
    }

    public void setChecklist_img2(String checklist_img2) {
        this.checklist_img2 = checklist_img2;
    }

    public int getAnsId() {
        return ansId;
    }

    public void setAnsId(int ansId) {
        this.ansId = ansId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public ArrayList<MasterChecklistAnswer> getAnswrs() {
        return answrs;
    }

    public void setAnswrs(ArrayList<MasterChecklistAnswer> answrs) {
        this.answrs = answrs;
    }

    ArrayList<MasterChecklistAnswer> answrs = new ArrayList<>();

    int checkedId = -1;
    boolean checklistimgGridAllow1 = false;

    public boolean getChecklistimgGridAllow1() {
        return checklistimgGridAllow1;
    }

    public void setChecklistimgGridAllow1(boolean checklistimgGridAllow1) {
        this.checklistimgGridAllow1 = checklistimgGridAllow1;
    }

    public boolean getChecklistimgGridAllow2() {
        return checklistimgGridAllow2;
    }

    public void setChecklistimgGridAllow2(boolean checklistimgGridAllow2) {
        this.checklistimgGridAllow2 = checklistimgGridAllow2;
    }

    boolean checklistimgGridAllow2 = false;

    public int getCheckedId() {
        return checkedId;
    }

    public void setCheckedId(int checkedId) {
        this.checkedId = checkedId;
    }


    Boolean imageAllow1 = false;
    String Ischecked = "";

    public String getIschecked() {
        return Ischecked;
    }

    public void setIschecked(String ischecked) {
        Ischecked = ischecked;
    }

    public Boolean getImageAllow1() {
        return imageAllow1;
    }

    public void setImageAllow1(Boolean imageAllow1) {
        this.imageAllow1 = imageAllow1;
    }

    public Boolean getImageAllow2() {
        return imageAllow2;
    }

    public void setImageAllow2(Boolean imageAllow2) {
        this.imageAllow2 = imageAllow2;
    }

    Boolean imageAllow2 = false;

    int subprogramId = 0;

    public int getSubprogramId() {
        return subprogramId;
    }

    public void setSubprogramId(int subprogramId) {
        this.subprogramId = subprogramId;
    }

    String visicooler_category = "";
    int visicooler_categoryId = 0;

    public int getVVcId() {
        return VVcId;
    }

    public void setVVcId(int VVcId) {
        this.VVcId = VVcId;
    }

    int VVcId = 0;

    public String getVisicooler_category() {
        return visicooler_category;
    }

    public void setVisicooler_category(String visicooler_category) {
        this.visicooler_category = visicooler_category;
    }

    public int getVisicooler_categoryId() {
        return visicooler_categoryId;
    }

    public void setVisicooler_categoryId(int visicooler_categoryId) {
        this.visicooler_categoryId = visicooler_categoryId;
    }

    public String getResion_name() {
        return resion_name;
    }

    public void setResion_name(String resion_name) {
        this.resion_name = resion_name;
    }

    public String getResion_id() {
        return resion_id;
    }

    public void setResion_id(String resion_id) {
        this.resion_id = resion_id;
    }

    public String resion_name;
    public String resion_id;
}
