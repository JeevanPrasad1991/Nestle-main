
package com.cpm.Nestle.getterSetter;

import java.io.Serializable;
import java.util.ArrayList;

public class BrandBlockPresentGetterSetter implements Serializable {

    private String store_id;
    private String image="";
    private Integer Right_Answer;

    public Integer getRight_Answer() {
        return Right_Answer;
    }

    public void setRight_Answer(Integer right_Answer) {
        Right_Answer = right_Answer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String present= "";
    int isChecked=-1;
    int keyId;
    int reasonId;
    String reason;
    String window="";
    String windowId="";
    String refrenceImage="";
    String checklist="";
    String checklistId="";

    public String getChecklist() {
        return checklist;
    }

    public void setChecklist(String checklist) {
        this.checklist = checklist;
    }

    public String getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(String checklistId) {
        this.checklistId = checklistId;
    }

    public String getRefrenceImage() {
        return refrenceImage;
    }

    public void setRefrenceImage(String refrenceImage) {
        this.refrenceImage = refrenceImage;
    }

    public String getWindow() {
        return window;
    }

    public void setWindow(String window) {
        this.window = window;
    }

    public String getWindowId() {
        return windowId;
    }

    public void setWindowId(String windowId) {
        this.windowId = windowId;
    }

    String product="";
    String productId="";
    String promotion="";
    String promotionId="";
    String promoId="";

    public String getPromoId() {
        return promoId;
    }

    public void setPromoId(String promoId) {
        this.promoId = promoId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getReasonId() {
        return reasonId;
    }

    public void setReasonId(int reasonId) {
        this.reasonId = reasonId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public int getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(int isChecked) {
        this.isChecked = isChecked;
    }

    public int getKeyId() {
        return keyId;
    }

    public void setKeyId(int keyId) {
        this.keyId = keyId;
    }


    private String Answer;
    private Integer answerId;

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }
    private String CurrectanswerCd;

    public String getCurrectanswer() {
        return Currectanswer;
    }

    public void setCurrectanswer(String currectanswer) {
        Currectanswer = currectanswer;
    }

    private String Currectanswer;

    public String getCurrectanswerCd() {
        return CurrectanswerCd;
    }

    public void setCurrectanswerCd(String currectanswerCd) {
        CurrectanswerCd = currectanswerCd;
    }

    String checklistAnswer;
    Integer checklistAnswertId;

    public Integer getChecklistAnswertId() {
        return checklistAnswertId;
    }

    public void setChecklistAnswertId(Integer checklistAnswertId) {
        this.checklistAnswertId = checklistAnswertId;
    }

    public String getChecklistAnswer() {
        return checklistAnswer;
    }

    public void setChecklistAnswer(String checklistAnswer) {
        this.checklistAnswer = checklistAnswer;
    }

    public ArrayList<BrandBlockPresentGetterSetter> getStock_headerList() {
        return stock_headerList;
    }

    public void setStock_headerList(ArrayList<BrandBlockPresentGetterSetter> stock_headerList) {
        this.stock_headerList = stock_headerList;
    }

    ArrayList<BrandBlockPresentGetterSetter>stock_headerList=new ArrayList<>();

}
