
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class WindowMaster implements Serializable {

    @SerializedName("Window_Id")
    @Expose
    private Integer windowId;

    @SerializedName("Window")
    @Expose
    private String window;

    @SerializedName("Window_Image")
    @Expose
    private String windowImage;

    public String getWindowImage() {
        return windowImage;
    }

    public void setWindowImage(String windowImage) {
        this.windowImage = windowImage;
    }

    public Integer getWindowId() {
        return windowId;
    }

    public void setWindowId(Integer windowId) {
        this.windowId = windowId;
    }

    public String getWindow() {
        return window;
    }

    public void setWindow(String window) {
        this.window = window;
    }

    String brand_Id;

    public String getBrand_Id() {
        return brand_Id;
    }

    public void setBrand_Id(String brand_Id) {
        this.brand_Id = brand_Id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    String brand;

    public boolean isIntiativepresent() {
        return intiativepresent;
    }

    public void setIntiativepresent(boolean intiativepresent) {
        this.intiativepresent = intiativepresent;
    }

    boolean intiativepresent = true;

    public String getInitiativeIMG() {
        return initiativeIMG;
    }

    public void setInitiativeIMG(String initiativeIMG) {
        this.initiativeIMG = initiativeIMG;
    }

    String initiativeIMG = "";
    String checklist_Id;
    String checklist;
    String image;
    String Window_Image_refrance;

    public String getWindow_Image_refrance() {
        return Window_Image_refrance;
    }

    public void setWindow_Image_refrance(String window_Image_refrance) {
        Window_Image_refrance = window_Image_refrance;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    String image2;

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    boolean exist;

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }

    boolean isFilled;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getChecklist_Id() {
        return checklist_Id;
    }

    public void setChecklist_Id(String checklist_Id) {
        this.checklist_Id = checklist_Id;
    }

    public String getChecklist() {
        return checklist;
    }

    public void setChecklist(String checklist) {
        this.checklist = checklist;
    }

    public String getChecklistRightAns_Id() {
        return checklistRightAns_Id;
    }

    public void setChecklistRightAns_Id(String checklistRightAns_Id) {
        this.checklistRightAns_Id = checklistRightAns_Id;
    }

    String checklistRightAns_Id = "";

    public ArrayList<WindowMaster> getAnsList() {
        return ansList;
    }

    public void setAnsList(ArrayList<WindowMaster> ansList) {
        this.ansList = ansList;
    }

    ArrayList<WindowMaster> ansList = new ArrayList<>();

    String ckecklist_anserId;

    public String getCkecklist_anserId() {
        return ckecklist_anserId;
    }

    public void setCkecklist_anserId(String ckecklist_anserId) {
        this.ckecklist_anserId = ckecklist_anserId;
    }

    public String getChecklist_answer() {
        return checklist_answer;
    }

    public void setChecklist_answer(String checklist_answer) {
        this.checklist_answer = checklist_answer;
    }

    String checklist_answer;

    public String getInitiativeRightAns() {
        return initiativeRightAns;
    }

    public void setInitiativeRightAns(String initiativeRightAns) {
        this.initiativeRightAns = initiativeRightAns;
    }

    String initiativeRightAns = "";

    public String getInitiativeRightAns_Id() {
        return initiativeRightAns_Id;
    }

    public void setInitiativeRightAns_Id(String initiativeRightAns_Id) {
        this.initiativeRightAns_Id = initiativeRightAns_Id;
    }

    String initiativeRightAns_Id = "";

    public String getKey_Id() {
        return key_Id;
    }

    public void setKey_Id(String key_Id) {
        this.key_Id = key_Id;
    }

    String key_Id;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
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

    int reasonId;
    String reason;
    String temp;

    //---------------

    String img_close_up = "";
    String img_long_shot = "";

    String answered;
    int answered_id = -1;

    int nonExecutionReasonId = 0;

    public int getNonExecutionReasonId() {
        return nonExecutionReasonId;
    }

    public void setNonExecutionReasonId(int nonExecutionReasonId) {
        this.nonExecutionReasonId = nonExecutionReasonId;
    }

  /*  ArrayList<BrandMaster> brandList = new ArrayList<>();
    HashMap<BrandMaster, ArrayList<ChecklistMaster>> hashMapListChildData = new HashMap<>();

    public ArrayList<BrandMaster> getBrandList() {
        return brandList;
    }

    public void setBrandList(ArrayList<BrandMaster> brandList) {
        this.brandList = brandList;
    }

    public HashMap<BrandMaster, ArrayList<ChecklistMaster>> getHashMapListChildData() {
        return hashMapListChildData;
    }

    public void setHashMapListChildData(HashMap<BrandMaster, ArrayList<ChecklistMaster>> hashMapListChildData) {
        this.hashMapListChildData = hashMapListChildData;
    }*/

    public String getAnswered() {
        return answered;
    }

    public void setAnswered(String answered) {
        this.answered = answered;
    }

    public int getAnswered_id() {
        return answered_id;
    }

    public void setAnswered_id(int answered_id) {
        this.answered_id = answered_id;
    }


    public String getImg_close_up() {
        return img_close_up;
    }

    public void setImg_close_up(String img_close_up) {
        this.img_close_up = img_close_up;
    }

    public String getImg_long_shot() {
        return img_long_shot;
    }

    public void setImg_long_shot(String img_long_shot) {
        this.img_long_shot = img_long_shot;
    }
}
