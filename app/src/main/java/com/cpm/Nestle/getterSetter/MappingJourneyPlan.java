
package com.cpm.Nestle.getterSetter;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MappingJourneyPlan implements Serializable {

    @SerializedName("MID")
    @Expose
    private Integer mID;
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
    @SerializedName("StateName")
    @Expose
    private String stateName;
    @SerializedName("StoreTypeId")
    @Expose
    private Integer storeTypeId;
    @SerializedName("StoreType")
    @Expose
    private String storeType;
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
    @SerializedName("Pincode")
    @Expose
    private String pincode;
    @SerializedName("Latitude")
    @Expose
    private Float latitude;
    @SerializedName("Longitude")
    @Expose
    private Float longitude;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("ContactPerson")
    @Expose
    private String contactPerson;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("PosExist")
    @Expose
    private Boolean posExist;
    @SerializedName("PosCompany")
    @Expose
    private String posCompany;
    @SerializedName("PosRemark")
    @Expose
    private String posRemark;
    @SerializedName("TaxType")
    @Expose
    private String taxType;
    @SerializedName("GSTno")
    @Expose
    private String gSTno;
    @SerializedName("GSTImage")
    @Expose
    private String gSTImage;
    @SerializedName("UploadStatus")
    @Expose
    private String uploadStatus;
    @SerializedName("GeoTag")
    @Expose
    private String geoTag;

    public String getLastMonthScore() {
        return lastMonthScore;
    }

    public void setLastMonthScore(String lastMonthScore) {
        this.lastMonthScore = lastMonthScore;
    }

    @SerializedName("LastMonthScore")
    @Expose
    private String lastMonthScore;
    public Integer getMID() {
        return mID;
    }

    public void setMID(Integer mID) {
        this.mID = mID;
    }

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

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Integer getStoreTypeId() {
        return storeTypeId;
    }

    public void setStoreTypeId(Integer storeTypeId) {
        this.storeTypeId = storeTypeId;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
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

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getPosExist() {
        return posExist;
    }

    public void setPosExist(Boolean posExist) {
        this.posExist = posExist;
    }

    public String getPosCompany() {
        return posCompany;
    }

    public void setPosCompany(String posCompany) {
        this.posCompany = posCompany;
    }

    public String getPosRemark() {
        return posRemark;
    }

    public void setPosRemark(String posRemark) {
        this.posRemark = posRemark;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public String getGSTno() {
        return gSTno;
    }

    public void setGSTno(String gSTno) {
        this.gSTno = gSTno;
    }

    public String getGSTImage() {
        return gSTImage;
    }

    public void setGSTImage(String gSTImage) {
        this.gSTImage = gSTImage;
    }

    public String getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public String getGeoTag() {
        return geoTag;
    }

    public void setGeoTag(String geoTag) {
        this.geoTag = geoTag;
    }
}
