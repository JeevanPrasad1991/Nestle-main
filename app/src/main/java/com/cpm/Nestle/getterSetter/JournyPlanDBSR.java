
package com.cpm.Nestle.getterSetter;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JournyPlanDBSR implements Parcelable {

    @SerializedName("ChannelId")
    @Expose
    private Integer channelId;
    @SerializedName("StoreId")
    @Expose
    private Integer storeId;
    @SerializedName("StoreCode")
    @Expose
    private String storeCode;
    @SerializedName("EmpId")
    @Expose
    private Integer empId;
    @SerializedName("VisitDate")
    @Expose
    private String visitDate;
    @SerializedName("DistributorId")
    @Expose
    private Integer distributorId;
    @SerializedName("DistributorName")
    @Expose
    private String distributorName;
    @SerializedName("StoreName")
    @Expose
    private String storeName;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Location")
    @Expose
    private String location;
    @SerializedName("Landmark")
    @Expose
    private String landmark;
    @SerializedName("CityId")
    @Expose
    private Integer cityId;
    @SerializedName("CityName")
    @Expose
    private String cityName;
    @SerializedName("StateId")
    @Expose
    private Integer stateId;
    @SerializedName("Pincode")
    @Expose
    private String pincode;
    @SerializedName("StoreType")
    @Expose
    private String storeType;
    @SerializedName("StoreTypeId")
    @Expose
    private Integer storeTypeId;
    @SerializedName("StoreCategoryId")
    @Expose
    private Integer storeCategoryId;
    @SerializedName("StoreCategory")
    @Expose
    private String storeCategory;
    @SerializedName("StoreClassId")
    @Expose
    private Integer storeClassId;
    @SerializedName("StoreClass")
    @Expose
    private String storeClass;
    @SerializedName("ManagerId")
    @Expose
    private Integer managerId;
    @SerializedName("UploadStatus")
    @Expose
    private String uploadStatus;
    @SerializedName("ReasonId")
    @Expose
    private Integer reasonId;
    @SerializedName("Remark")
    @Expose
    private String remark;
    @SerializedName("GeoTag")
    @Expose
    private String geoTag;
    @SerializedName("WeeklyUpload")
    @Expose
    private String weeklyUpload;

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public Integer getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Integer distributorId) {
        this.distributorId = distributorId;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public Integer getStoreTypeId() {
        return storeTypeId;
    }

    public void setStoreTypeId(Integer storeTypeId) {
        this.storeTypeId = storeTypeId;
    }

    public Integer getStoreCategoryId() {
        return storeCategoryId;
    }

    public void setStoreCategoryId(Integer storeCategoryId) {
        this.storeCategoryId = storeCategoryId;
    }

    public String getStoreCategory() {
        return storeCategory;
    }

    public void setStoreCategory(String storeCategory) {
        this.storeCategory = storeCategory;
    }

    public Integer getStoreClassId() {
        return storeClassId;
    }

    public void setStoreClassId(Integer storeClassId) {
        this.storeClassId = storeClassId;
    }

    public String getStoreClass() {
        return storeClass;
    }

    public void setStoreClass(String storeClass) {
        this.storeClass = storeClass;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public String getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public Integer getReasonId() {
        return reasonId;
    }

    public void setReasonId(Integer reasonId) {
        this.reasonId = reasonId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGeoTag() {
        return geoTag;
    }

    public void setGeoTag(String geoTag) {
        this.geoTag = geoTag;
    }

    public String getWeeklyUpload() {
        return weeklyUpload;
    }

    public void setWeeklyUpload(String weeklyUpload) {
        this.weeklyUpload = weeklyUpload;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
