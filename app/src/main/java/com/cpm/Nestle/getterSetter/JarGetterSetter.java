package com.cpm.Nestle.getterSetter;

import java.io.Serializable;

public class JarGetterSetter implements Serializable {

    private String present_cd;
    private String present_name="";
    private String working_cd;
    private String working_name="";
    private String location_cd;
    private String location="";
    private String purity_name="";
    private String visit_date="";
    public String getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(String visit_date) {
        this.visit_date = visit_date;
    }

    public String getPurity_name() {
        return purity_name;
    }

    public void setPurity_name(String purity_name) {
        this.purity_name = purity_name;
    }

    private String planogram_cd;
    private String planogram_name="";
    private String image_close_up="";
    private String image_long_shot="";

    public String getImage_long_shot() {
        return image_long_shot;
    }

    public void setImage_long_shot(String image_long_shot) {
        this.image_long_shot = image_long_shot;
    }

    public String getPresent_cd() {
        return present_cd;
    }

    public void setPresent_cd(String present_cd) {
        this.present_cd = present_cd;
    }

    public String getPresent_name() {
        return present_name;
    }

    public void setPresent_name(String present_name) {
        this.present_name = present_name;
    }

    public String getWorking_cd() {
        return working_cd;
    }

    public void setWorking_cd(String working_cd) {
        this.working_cd = working_cd;
    }

    public String getWorking_name() {
        return working_name;
    }

    public void setWorking_name(String working_name) {
        this.working_name = working_name;
    }

    public String getLocation_cd() {
        return location_cd;
    }

    public void setLocation_cd(String location_cd) {
        this.location_cd = location_cd;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getPlanogram_cd() {
        return planogram_cd;
    }

    public void setPlanogram_cd(String planogram_cd) {
        this.planogram_cd = planogram_cd;
    }

    public String getPlanogram_name() {
        return planogram_name;
    }

    public void setPlanogram_name(String planogram_name) {
        this.planogram_name = planogram_name;
    }

    public String getImage_close_up() {
        return image_close_up;
    }

    public void setImage_close_up(String image_close_up) {
        this.image_close_up = image_close_up;
    }

    private String cheklist="";
    private String cheklist_cd="";

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer_cd() {
        return answer_cd;
    }

    public void setAnswer_cd(String answer_cd) {
        this.answer_cd = answer_cd;
    }

    private String answer="";
    private String answer_cd="";
    private String store_id="";

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getCheklist() {
        return cheklist;
    }

    public void setCheklist(String cheklist) {
        this.cheklist = cheklist;
    }

    public String getCheklist_cd() {
        return cheklist_cd;
    }

    public void setCheklist_cd(String cheklist_cd) {
        this.cheklist_cd = cheklist_cd;
    }
}
