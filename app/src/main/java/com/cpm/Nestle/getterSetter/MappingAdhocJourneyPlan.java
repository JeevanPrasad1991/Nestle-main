
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MappingAdhocJourneyPlan implements Serializable {
    private int colourCode;

    public int getColourCode() {
        return colourCode;
    }

    public void setColourCode(int colourCode) {
        this.colourCode = colourCode;
    }

    @SerializedName("MID")
    @Expose
    private Integer mID;
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
    @SerializedName("ChainId")
    @Expose
    private Integer chainId;
    @SerializedName("ChainName")
    @Expose
    private String chainName;
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
    @SerializedName("Pincode")
    @Expose
    private String pincode;
    @SerializedName("Latitude")
    @Expose
    private Double latitude;
    @SerializedName("Longitude")
    @Expose
    private Double longitude;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("TaxType")
    @Expose
    private String taxType;
    @SerializedName("GSTno")
    @Expose
    private String gSTno;
    @SerializedName("PancardNo")
    @Expose
    private String pancardNo;
    @SerializedName("ContactPerson")
    @Expose
    private String contactPerson;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("UploadStatus")
    @Expose
    private String uploadStatus;
    @SerializedName("GeoTag")
    @Expose
    private String geoTag;
    @SerializedName("LocationTypeId")
    @Expose
    private Integer locationTypeId;
    @SerializedName("Drugs_Licence")
    @Expose
    private String drugsLicence;

    public Integer getMID() {
        return mID;
    }

    public void setMID(Integer mID) {
        this.mID = mID;
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

    public Integer getChainId() {
        return chainId;
    }

    public void setChainId(Integer chainId) {
        this.chainId = chainId;
    }

    public String getChainName() {
        return chainName;
    }

    public void setChainName(String chainName) {
        this.chainName = chainName;
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

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
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

    public String getPancardNo() {
        return pancardNo;
    }

    public void setPancardNo(String pancardNo) {
        this.pancardNo = pancardNo;
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

    public Integer getLocationTypeId() {
        return locationTypeId;
    }

    public void setLocationTypeId(Integer locationTypeId) {
        this.locationTypeId = locationTypeId;
    }

    public String getDrugsLicence() {
        return drugsLicence;
    }

    public void setDrugsLicence(String drugsLicence) {
        this.drugsLicence = drugsLicence;
    }

}
