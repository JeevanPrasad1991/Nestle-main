
package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MasterPosm {
    @SerializedName("PosmId")
    @Expose
    private Integer posmId;
    @SerializedName("PosmName")
    @Expose
    private String posmName;
    @SerializedName("RefImage")
    @Expose
    private String refImage;

    public Integer getPosmId() {
        return posmId;
    }

    public void setPosmId(Integer posmId) {
        this.posmId = posmId;
    }

    public String getPosmName() {
        return posmName;
    }

    public void setPosmName(String posmName) {
        this.posmName = posmName;
    }

    public String getRefImage() {
        return refImage;
    }

    public void setRefImage(String refImage) {
        this.refImage = refImage;
    }

    String posm_img="";
    String reason="";
    String Ispresnt="";

    public String getIspresnt() {
        return Ispresnt;
    }

    public void setIspresnt(String ispresnt) {
        Ispresnt = ispresnt;
    }

    public String getPosm_yesorno() {
        return posm_yesorno;
    }

    public void setPosm_yesorno(String posm_yesorno) {
        this.posm_yesorno = posm_yesorno;
    }

    String posm_yesorno="";
    int reasonId=0;

    public String getPosm_img() {
        return posm_img;
    }

    public void setPosm_img(String posm_img) {
        this.posm_img = posm_img;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getReasonId() {
        return reasonId;
    }

    public void setReasonId(int reasonId) {
        this.reasonId = reasonId;
    }

    public ArrayList<NonPosmReason> getReasonArrayList() {
        return reasonArrayList;
    }

    public void setReasonArrayList(ArrayList<NonPosmReason> reasonArrayList) {
        this.reasonArrayList = reasonArrayList;
    }

    ArrayList<NonPosmReason>reasonArrayList=new ArrayList<>();
    int checkedId=-1;

    public int getCheckedId() {
        return checkedId;
    }

    public void setCheckedId(int checkedId) {
        this.checkedId = checkedId;
    }
}
