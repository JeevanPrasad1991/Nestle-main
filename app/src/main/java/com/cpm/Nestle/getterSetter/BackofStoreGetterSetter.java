package com.cpm.Nestle.getterSetter;

import java.io.Serializable;

public class BackofStoreGetterSetter implements Serializable {

    private String present_cd;
    private String present_name="";
    private String visit_date="";
    private String image_close_up="";
    private String image_long_shot="";
    private  String  brand_id,brand,sku,sku_id;
    private String stock="";
    private String checklist="";
    private String checklist_id="";
    private String reason;
    private Integer reasonId;
    private String Answer;
    private Integer answerId;
    private Integer common_id;
    private String key_id;
    private String store_id;

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    public Integer getCommon_id() {
        return common_id;
    }

    public void setCommon_id(Integer common_id) {
        this.common_id = common_id;
    }

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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getReasonId() {
        return reasonId;
    }

    public void setReasonId(Integer reasonId) {
        this.reasonId = reasonId;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSku_id() {
        return sku_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getChecklist() {
        return checklist;
    }

    public void setChecklist(String checklist) {
        this.checklist = checklist;
    }

    public String getChecklist_id() {
        return checklist_id;
    }

    public void setChecklist_id(String checklist_id) {
        this.checklist_id = checklist_id;
    }

    public String getPresent_cd() {
        return present_cd;
    }

    public void setPresent_cd(String present_cd) {
        this.present_cd = present_cd;
    }

    public String getPresent_name() {
        return present_name;
    }

    public void setPresent_name(String present_name) {
        this.present_name = present_name;
    }

    public String getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(String visit_date) {
        this.visit_date = visit_date;
    }

    public String getImage_close_up() {
        return image_close_up;
    }

    public void setImage_close_up(String image_close_up) {
        this.image_close_up = image_close_up;
    }

    public String getImage_long_shot() {
        return image_long_shot;
    }

    public void setImage_long_shot(String image_long_shot) {
        this.image_long_shot = image_long_shot;
    }
}
