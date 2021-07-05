package com.cpm.Nestle.getterSetter;

public class ViisbilityDriveGetterSetter
{
    public String getUserd() {
        return userd;
    }

    public void setUserd(String userd) {
        this.userd = userd;
    }

    protected String userd;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    protected String storeId;
	protected String visitdate;
	protected String storename;
	protected String city;
	public String magic_stick_name;
	public String magic_stick_id;

    public String getVisibility_deployed_name() {
        return visibility_deployed_name;
    }

    public void setVisibility_deployed_name(String visibility_deployed_name) {
        this.visibility_deployed_name = visibility_deployed_name;
    }

    public String getVisibility_deployed_id() {
        return visibility_deployed_id;
    }

    public void setVisibility_deployed_id(String visibility_deployed_id) {
        this.visibility_deployed_id = visibility_deployed_id;
    }

    public String getPresent_deployed() {
        return present_deployed;
    }

    public void setPresent_deployed(String present_deployed) {
        this.present_deployed = present_deployed;
    }

    public String visibility_deployed_name;
	public String visibility_deployed_id;
    public String present_deployed;
	public String image_shop_board="";
	public String image_long="";
	public String image_close="";

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String present;

    public String getVisitdate() {
        return visitdate;
    }

    public void setVisitdate(String visitdate) {
        this.visitdate = visitdate;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMagic_stick_name() {
        return magic_stick_name;
    }

    public void setMagic_stick_name(String magic_stick_name) {
        this.magic_stick_name = magic_stick_name;
    }

    public String getMagic_stick_id() {
        return magic_stick_id;
    }

    public void setMagic_stick_id(String magic_stick_id) {
        this.magic_stick_id = magic_stick_id;
    }

    public String getImage_shop_board() {
        return image_shop_board;
    }

    public void setImage_shop_board(String image_shop_board) {
        this.image_shop_board = image_shop_board;
    }

    public String getImage_long() {
        return image_long;
    }

    public void setImage_long(String image_long) {
        this.image_long = image_long;
    }

    public String getImage_close() {
        return image_close;
    }

    public void setImage_close(String image_close) {
        this.image_close = image_close;
    }
}
