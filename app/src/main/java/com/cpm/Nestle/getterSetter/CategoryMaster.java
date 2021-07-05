
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CategoryMaster {

    @SerializedName("Category_Id")
    @Expose
    private Integer categoryId;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("Category_Sequence")
    @Expose
    private Integer categorySequence;

    private String store_id;
    private String menu_id;

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getCategorySequence() {
        return categorySequence;
    }

    public void setCategorySequence(Integer categorySequence) {
        this.categorySequence = categorySequence;
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

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey_Id() {
        return key_Id;
    }

    public void setKey_Id(String key_Id) {
        this.key_Id = key_Id;
    }

    String key_Id;
    String image;
    String categoryPlanogramImageurl;

    public String getCategoryPlanogramImageurl() {
        return categoryPlanogramImageurl;
    }

    public void setCategoryPlanogramImageurl(String categoryPlanogramImageurl) {
        this.categoryPlanogramImageurl = categoryPlanogramImageurl;
    }

    boolean exist;
    int reasonId;
    String reason;

    private String Brand_Id ="0";
    private String Brand="";
    private String Category_Facing="";
    private String Category_Image="";
    private String Brand_Facing="";
    private String Checklist_Question="";
    private String Checklist_Question_Id="0";
    private String Checklist_Correct_Answer_Id="0";
    ArrayList<ChecklistMaster> checklistQuestions = new ArrayList<>();

    public ArrayList<ChecklistMaster> getChecklistQuestions() {
        return checklistQuestions;
    }

    public void setChecklistQuestions(ArrayList<ChecklistMaster> checklistQuestions) {
        this.checklistQuestions = checklistQuestions;
    }

    public String getBrand_Id() {
        return Brand_Id;
    }

    public void setBrand_Id(String brand_Id) {
        Brand_Id = brand_Id;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getCategory_Facing() {
        return Category_Facing;
    }

    public void setCategory_Facing(String category_Facing) {
        Category_Facing = category_Facing;
    }

    public String getCategory_Image() {
        return Category_Image;
    }

    public void setCategory_Image(String category_Image) {
        Category_Image = category_Image;
    }

    public String getBrand_Facing() {
        return Brand_Facing;
    }

    public void setBrand_Facing(String brand_Facing) {
        Brand_Facing = brand_Facing;
    }

    public String getChecklist_Question() {
        return Checklist_Question;
    }

    public void setChecklist_Question(String checklist_Question) {
        Checklist_Question = checklist_Question;
    }

    public String getChecklist_Question_Id() {
        return Checklist_Question_Id;
    }

    public void setChecklist_Question_Id(String checklist_Question_Id) {
        Checklist_Question_Id = checklist_Question_Id;
    }

    public String getChecklist_Correct_Answer_Id() {
        return Checklist_Correct_Answer_Id;
    }

    public void setChecklist_Correct_Answer_Id(String checklist_Correct_Answer_Id) {
        Checklist_Correct_Answer_Id = checklist_Correct_Answer_Id;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    String percentage="";

    private int brand_facing_sum= 0 ;

    public int getBrand_facing_sum() {
        return brand_facing_sum;
    }

    public void setBrand_facing_sum(int brand_facing_sum) {
        this.brand_facing_sum = brand_facing_sum;
    }


    private String promotion;
    private String Id;
    private String image1="",image2="";
    private String present= "";

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }
}
