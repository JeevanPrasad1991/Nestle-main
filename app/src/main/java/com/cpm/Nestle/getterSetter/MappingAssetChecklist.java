
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MappingAssetChecklist {

    @SerializedName("AssetId")
    @Expose
    private Integer assetId;
    @SerializedName("ChecklistId")
    @Expose
    private Integer checklistId;

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public Integer getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(Integer checklistId) {
        this.checklistId = checklistId;
    }

    private String correctAnswer_Id = "0";
    private String brand_Id="0";
    private String category_Id="0";
    private String store_Id="";
    private String menu_Id="";
    private String visited_date="";
    private String checklist="";

    public String getChecklist() {
        return checklist;
    }

    public void setChecklist(String checklist) {
        this.checklist = checklist;
    }

    public String getCorrectAnswer_Id() {
        return correctAnswer_Id;
    }

    public void setCorrectAnswer_Id(String correctAnswer_Id) {
        this.correctAnswer_Id = correctAnswer_Id;
    }

    public String getBrand_Id() {
        return brand_Id;
    }

    public void setBrand_Id(String brand_Id) {
        this.brand_Id = brand_Id;
    }

    public String getCategory_Id() {
        return category_Id;
    }

    public void setCategory_Id(String category_Id) {
        this.category_Id = category_Id;
    }

    public String getStore_Id() {
        return store_Id;
    }

    public void setStore_Id(String store_Id) {
        this.store_Id = store_Id;
    }

    public String getMenu_Id() {
        return menu_Id;
    }

    public void setMenu_Id(String menu_Id) {
        this.menu_Id = menu_Id;
    }

    public String getVisited_date() {
        return visited_date;
    }

    private ArrayList<WindowChecklistAnswer> checkListAnswer = new ArrayList<>();

    public void setVisited_date(String visited_date) {
        this.visited_date = visited_date;
    }

    public ArrayList<WindowChecklistAnswer> getCheckListAnswer() {
        return checkListAnswer;
    }

    public void setCheckListAnswer(ArrayList<WindowChecklistAnswer> checkListAnswer) {
        this.checkListAnswer = checkListAnswer;
    }
    private int answered_cd = 0;

    public int getAnswered_cd() {
        return answered_cd;
    }

    public void setAnswered_cd(int answered_cd) {
        this.answered_cd = answered_cd;
    }

}
