package com.cpm.Nestle.delegates;

public class CoverageBean
{
    public String getSTORE_NAME() {
        return STORE_NAME;
    }

    public void setSTORE_NAME(String STORE_NAME) {
        this.STORE_NAME = STORE_NAME;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public String getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(String LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    String STORE_NAME;
    String LATITUDE;
    String LONGITUDE;
	protected int MID;
	protected String storeId;
	protected String Remark;
	protected String chECKOUT_TIME;
	public  String flag_from;
	String intime="";
	private String jcp_type="";

	public String getJcp_type() {
		return jcp_type;
	}

	public void setJcp_type(String jcp_type) {
		this.jcp_type = jcp_type;
	}

	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}

	String outTime="";

	public String getIntime() {
		return intime;
	}

	public void setIntime(String intime) {
		this.intime = intime;
	}

	public String getFlag_from() {
		return flag_from;
	}

	public void setFlag_from(String flag_from) {
		this.flag_from = flag_from;
	}

	public String getChECKOUT_TIME() {
		return chECKOUT_TIME;
	}

	public void setChECKOUT_TIME(String chECKOUT_TIME) {
		this.chECKOUT_TIME = chECKOUT_TIME;
	}

	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	protected String userId;
	protected String visitDate;
	private String latitude;
	private String longitude;
	private String reasonid="";
	private String reason="";
	private String image="";

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private String status="";

	public String getCkeckout_image() {
		return ckeckout_image;
	}

	public void setCkeckout_image(String ckeckout_image) {
		this.ckeckout_image = ckeckout_image;
	}

	private String ckeckout_image="";

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getMID() {
		return MID;
	}

	public void setMID(int mID) {
		MID = mID;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getReasonid() {
		return reasonid;
	}

	public void setReasonid(String reasonid) {
		this.reasonid = reasonid;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	
}
