
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MasterAsset {
    @SerializedName("AssetId")
    @Expose
    private Integer assetId;
    @SerializedName("AssetName")
    @Expose
    private String assetName;
    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    String category="";
    String asset_img="";
    String location="";
    String non_reason="";

    public String getIspresent() {
        return Ispresent;
    }

    public void setIspresent(String ispresent) {
        Ispresent = ispresent;
    }

    String Ispresent="";
    int categoryId=0;
    int locationId=0;
    int nonreasonId=0;
    int checkedIdforlocation=-1;

    public int getCheckedIdforlocation() {
        return checkedIdforlocation;
    }

    public void setCheckedIdforlocation(int checkedIdforlocation) {
        this.checkedIdforlocation = checkedIdforlocation;
    }

    public int getCheckidfor_nonreason() {
        return checkidfor_nonreason;
    }

    public void setCheckidfor_nonreason(int checkidfor_nonreason) {
        this.checkidfor_nonreason = checkidfor_nonreason;
    }

    int checkidfor_nonreason=-1;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAsset_img() {
        return asset_img;
    }

    public void setAsset_img(String asset_img) {
        this.asset_img = asset_img;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNon_reason() {
        return non_reason;
    }

    public void setNon_reason(String non_reason) {
        this.non_reason = non_reason;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getNonreasonId() {
        return nonreasonId;
    }

    public void setNonreasonId(int nonreasonId) {
        this.nonreasonId = nonreasonId;
    }

    ArrayList<MasterAssetLocation>locations=new ArrayList<>();

    public ArrayList<MasterAssetLocation> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<MasterAssetLocation> locations) {
        this.locations = locations;
    }

    public ArrayList<MasterNonAssetReason> getNonAssetReasons() {
        return nonAssetReasons;
    }

    public void setNonAssetReasons(ArrayList<MasterNonAssetReason> nonAssetReasons) {
        this.nonAssetReasons = nonAssetReasons;
    }

    ArrayList<MasterNonAssetReason>nonAssetReasons=new ArrayList<>();
}
