
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MappingPromotion {
    @SerializedName("PromoId")
    @Expose
    private Integer promoId;
    @SerializedName("ChannelId")
    @Expose
    private Integer channelId;
    @SerializedName("StateId")
    @Expose
    private Integer stateId;
    @SerializedName("StoreTypeId")
    @Expose
    private Integer storeTypeId;
    @SerializedName("CategoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("Promotion")
    @Expose
    private String promotion;

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getStoreTypeId() {
        return storeTypeId;
    }

    public void setStoreTypeId(Integer storeTypeId) {
        this.storeTypeId = storeTypeId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }



    private String closeShotStr ="";

    public String getLongShotStr() {
        return longShotStr;
    }

    public void setLongShotStr(String longShotStr) {
        this.longShotStr = longShotStr;
    }

    private String longShotStr="";
    private String present= "";
    int isChecked=-1;
    int keyId;
    int reasonId=0;
    String reason="";
    String category_name ="";

    public String getCategory_name() {
        return category_name;
    }
    public void setCategory_name(String category_name) {
        this.category_name = category_name;
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
    public String getCloseShotStr() {
        return closeShotStr;
    }
    public void setCloseShotStr(String closeShotStr) {
        this.closeShotStr = closeShotStr;
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
    ArrayList<MasterPromotionCheck>checklists=new ArrayList<>();

    public ArrayList<MasterPromotionCheck> getChecklists() {
        return checklists;
    }

    public void setChecklists(ArrayList<MasterPromotionCheck> checklists) {
        this.checklists = checklists;
    }
}
