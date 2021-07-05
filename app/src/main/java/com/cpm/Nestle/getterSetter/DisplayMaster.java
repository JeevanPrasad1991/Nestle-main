
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DisplayMaster implements Serializable {

    @SerializedName("Display_Id")
    @Expose
    private Integer displayId;
    @SerializedName("Display")
    @Expose
    private String display;

    public Integer getDisplayId() {
        return displayId;
    }

    public void setDisplayId(Integer displayId) {
        this.displayId = displayId;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    String quantity="";
    String img_close_up = "";
    String img_long_shot = "";
    int answered_id = -1;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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

    public int getAnswered_id() {
        return answered_id;
    }

    public void setAnswered_id(int answered_id) {
        this.answered_id = answered_id;
    }

    public int getKey_Id() {
        return Key_Id;
    }

    public void setKey_Id(int Key_Id) {
        this.Key_Id = Key_Id;
    }

    int Key_Id;
}
