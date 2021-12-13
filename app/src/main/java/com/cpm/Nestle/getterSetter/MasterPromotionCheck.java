
package com.cpm.Nestle.getterSetter;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterPromotionCheck {
    @SerializedName("ChecklistId")
    @Expose
    private int checklistId;
    @SerializedName("Checklist")
    @Expose
    private String checklist;
    @SerializedName("QImageAllow")
    @Expose
    private boolean qImageAllow;
    @SerializedName("ImageMandatory")
    @Expose
    private boolean imageMandatory;
    @SerializedName("StockAllow")
    @Expose
    private boolean stockAllow;
    @SerializedName("AnswerId")
    @Expose
    private int answerId;
    @SerializedName("Answer")
    @Expose
    private String answer;
    @SerializedName("ImageAllow")
    @Expose
    private boolean imageAllow;
    @SerializedName("ReasonAllow")
    @Expose
    private boolean reasonAllow;

    @SerializedName("LongShotImage")
    @Expose
    private boolean longShotImage;

    @SerializedName("FirstSelect")
    @Expose
    private boolean firstSelect;

    public boolean isFirstSelect() {
        return firstSelect;
    }

    public void setFirstSelect(boolean firstSelect) {
        this.firstSelect = firstSelect;
    }

    public boolean isLongShotImage() {
        return longShotImage;
    }

    public void setLongShotImage(boolean longShotImage) {
        this.longShotImage = longShotImage;
    }

    public int getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(int checklistId) {
        this.checklistId = checklistId;
    }

    public String getChecklist() {
        return checklist;
    }

    public void setChecklist(String checklist) {
        this.checklist = checklist;
    }

    public boolean isQImageAllow() {
        return qImageAllow;
    }

    public void setQImageAllow(boolean qImageAllow) {
        this.qImageAllow = qImageAllow;
    }

    public boolean isImageMandatory() {
        return imageMandatory;
    }

    public void setImageMandatory(boolean imageMandatory) {
        this.imageMandatory = imageMandatory;
    }

    public boolean isStockAllow() {
        return stockAllow;
    }

    public void setStockAllow(boolean stockAllow) {
        this.stockAllow = stockAllow;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isImageAllow() {
        return imageAllow;
    }

    public void setImageAllow(boolean imageAllow) {
        this.imageAllow = imageAllow;
    }

    public boolean isReasonAllow() {
        return reasonAllow;
    }

    public void setReasonAllow(boolean reasonAllow) {
        this.reasonAllow = reasonAllow;
    }

    public boolean nonreasonimageAllow = false;
    String checklist_img="";
    String checklistAnsImg="";
    String checkNonReasonImg="";

    public String getCheckAnsLongShotImg() {
        return checkAnsLongShotImg;
    }

    public void setCheckAnsLongShotImg(String checkAnsLongShotImg) {
        this.checkAnsLongShotImg = checkAnsLongShotImg;
    }

    String checkAnsLongShotImg ="";

    public boolean isqImageAllow() {
        return qImageAllow;
    }

    public void setqImageAllow(boolean qImageAllow) {
        this.qImageAllow = qImageAllow;
    }

    public boolean isNonreasonimageAllow() {
        return nonreasonimageAllow;
    }

    public void setNonreasonimageAllow(boolean nonreasonimageAllow) {
        this.nonreasonimageAllow = nonreasonimageAllow;
    }

    public String getChecklist_img() {
        return checklist_img;
    }

    public void setChecklist_img(String checklist_img) {
        this.checklist_img = checklist_img;
    }

    public String getChecklistAnsImg() {
        return checklistAnsImg;
    }

    public void setChecklistAnsImg(String checklistAnsImg) {
        this.checklistAnsImg = checklistAnsImg;
    }

    public String getCheckNonReasonImg() {
        return checkNonReasonImg;
    }

    public void setCheckNonReasonImg(String checkNonReasonImg) {
        this.checkNonReasonImg = checkNonReasonImg;
    }

    ArrayList<MasterPromotionCheck>checkAns=new ArrayList<>();

    public ArrayList<MasterPromotionCheck> getCheckAns() {
        return checkAns;
    }

    public void setCheckAns(ArrayList<MasterPromotionCheck> checkAns) {
        this.checkAns = checkAns;
    }

    public ArrayList<MasterPromotionChecklistReason> getChecklistNonReasonList() {
        return checklistNonReasonList;
    }

    public void setChecklistNonReasonList(ArrayList<MasterPromotionChecklistReason> checklistNonReasonList) {
        this.checklistNonReasonList = checklistNonReasonList;
    }

    ArrayList<MasterPromotionChecklistReason>checklistNonReasonList=new ArrayList<>();

    String stock="",nonReason="";
    int nonReasonId=0;

    public String getNonReason() {
        return nonReason;
    }

    public void setNonReason(String nonReason) {
        this.nonReason = nonReason;
    }

    public int getNonReasonId() {
        return nonReasonId;
    }

    public void setNonReasonId(int nonReasonId) {
        this.nonReasonId = nonReasonId;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
    int categoryId=0,promoId=0;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getPromoId() {
        return promoId;
    }

    public void setPromoId(int promoId) {
        this.promoId = promoId;
    }
}
