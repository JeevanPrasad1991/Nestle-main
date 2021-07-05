package com.cpm.Nestle.getterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MasterProgram implements Serializable {

    @SerializedName("ProgramId")
    @Expose
    private Integer programId;
    @SerializedName("ProgramName")
    @Expose
    private String programName;
    @SerializedName("SubProgramId")
    @Expose
    private Integer subProgramId;
    @SerializedName("SubProgramName")
    @Expose
    private String subProgramName;

    @SerializedName("ImagePath")
    @Expose
    private String ImagePath;

    @SerializedName("ProgramNormalIcon")
    @Expose
    private String ProgramNormalIcon;

    @SerializedName("ProgramTickIcon")
    @Expose
    private String ProgramTickIcon;

    @SerializedName("ProgramIconBaseColor")
    @Expose
    private String ProgramIconBaseColor;

    @SerializedName("SubProgramNormalIcon")
    @Expose
    private String SubProgramNormalIcon;

    @SerializedName("SubProgramTickIcon")
    @Expose
    private String SubProgramTickIcon;

    public String getProgramNormalIcon() {
        return ProgramNormalIcon;
    }

    public void setProgramNormalIcon(String programNormalIcon) {
        ProgramNormalIcon = programNormalIcon;
    }

    public String getProgramTickIcon() {
        return ProgramTickIcon;
    }

    public void setProgramTickIcon(String programTickIcon) {
        ProgramTickIcon = programTickIcon;
    }

    public String getProgramIconBaseColor() {
        return ProgramIconBaseColor;
    }

    public void setProgramIconBaseColor(String programIconBaseColor) {
        ProgramIconBaseColor = programIconBaseColor;
    }

    public String getSubProgramNormalIcon() {
        return SubProgramNormalIcon;
    }

    public void setSubProgramNormalIcon(String subProgramNormalIcon) {
        SubProgramNormalIcon = subProgramNormalIcon;
    }

    public String getSubProgramTickIcon() {
        return SubProgramTickIcon;
    }

    public void setSubProgramTickIcon(String subProgramTickIcon) {
        SubProgramTickIcon = subProgramTickIcon;
    }

    public String getSubProgramIconBaseColor() {
        return SubProgramIconBaseColor;
    }

    public void setSubProgramIconBaseColor(String subProgramIconBaseColor) {
        SubProgramIconBaseColor = subProgramIconBaseColor;
    }

    @SerializedName("SubProgramIconBaseColor")
    @Expose
    private String SubProgramIconBaseColor;


    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public Integer getProgramId() {
        return programId;
    }

    public void setProgramId(Integer programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public Integer getSubProgramId() {
        return subProgramId;
    }

    public void setSubProgramId(Integer subProgramId) {
        this.subProgramId = subProgramId;
    }

    public String getSubProgramName() {
        return subProgramName;
    }

    public void setSubProgramName(String subProgramName) {
        this.subProgramName = subProgramName;
    }

    ArrayList<MasterProgram> subprogramList = new ArrayList<>();

    public ArrayList<MasterProgram> getSubprogramList() {
        return subprogramList;
    }

    public void setSubprogramList(ArrayList<MasterProgram> subprogramList) {
        this.subprogramList = subprogramList;
    }

    public String getResion_name() {
        return resion_name;
    }

    public void setResion_name(String resion_name) {
        this.resion_name = resion_name;
    }

    public String getResion_id() {
        return resion_id;
    }

    public void setResion_id(String resion_id) {
        this.resion_id = resion_id;
    }

    public String resion_name;
    public String resion_id;
}
