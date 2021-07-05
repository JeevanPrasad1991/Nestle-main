
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterCategory {

    @SerializedName("CategoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("CategoryName")
    @Expose
    private String categoryName;
    @SerializedName("CategorySequence")
    @Expose
    private Integer categorySequence;

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    @SerializedName("CategoryImage")
    @Expose
    private String categoryImage;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategorySequence() {
        return categorySequence;
    }

    public void setCategorySequence(Integer categorySequence) {
        this.categorySequence = categorySequence;
    }

}
