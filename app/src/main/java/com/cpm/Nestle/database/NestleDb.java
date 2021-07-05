package com.cpm.Nestle.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cpm.Nestle.delegates.CoverageBean;
import com.cpm.Nestle.getterSetter.*;
import com.cpm.Nestle.utilities.CommonString;
import com.cpm.Nestle.visitor.VisitorDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressLint("LongLogTag")
public class NestleDb extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "NestleDb7";
    public static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;
    Context context;

    public NestleDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void open() {
        try {
            db = this.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CommonString.CREATE_TABLE_COVERAGE_DATA);
            db.execSQL(CommonString.CREATE_TABLE_STORE_GEOTAGGING);
            db.execSQL(CommonString.CREATE_TABLE_PROGRAM_CHECKLIST);
            db.execSQL(CommonString.CREATE_TABLE_PROGRAM_CHECKLIST_PRESENT);
            db.execSQL(CommonString.CREATE_TABLE_VISICOOLER_PRESENT);
            db.execSQL(CommonString.CREATE_TABLE_VISICOOLER_CHECKLIST);
            db.execSQL(CommonString.CREATE_TABLE_STORE_PROFILE_DATA);
            db.execSQL(CommonString.CREATE_TABLE_POSM_PRESENT);
            db.execSQL(CommonString.CREATE_TABLE_POSM);
            db.execSQL(CommonString.CREATE_TABLE_PAIDVISIBILITY);
            db.execSQL(CommonString.CREATE_TABLE_COMP_PROMOTION);
            db.execSQL(CommonString.CREATE_TABLE_COMP_NPD);
            db.execSQL(CommonString.CREATE_TABLE_PROMOTION_DATA);
            db.execSQL(CommonString.CREATE_TABLE_VQPS);
            db.execSQL(CommonString.CREATE_TABLE_STORE_TIME);
            db.execSQL(CommonString.CREATE_TABLE_VISIBILITY_DRIVE_DATA);
            db.execSQL(CommonString.CREATE_TABLE_RDVISIBILITY_DRIVE_DATA);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int createtable(String sqltext) {
        try {
            db.execSQL(sqltext);
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }


    public void deleteTableWithStoreID(String storeid) {
        try {
            if (!db.isOpen()) {
                open();
            }

            db.delete(CommonString.TABLE_COVERAGE_DATA, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
            db.delete(CommonString.TABLE_STORE_GEOTAGGING, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
            db.delete(CommonString.TABLE_PROGRAM_CHECKLIST, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
            db.delete(CommonString.TABLE_VISICOOLER_PRESENT, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
            db.delete(CommonString.TABLE_PROGRAM_CHECKLIST_PRESENT, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
            db.delete(CommonString.TABLE_VISICOOLER_CHECKLIST, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);

            db.delete(CommonString.TABLE_POSM_PRESENT, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
            db.delete(CommonString.TABLE_POSM, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
            db.delete(CommonString.TABLE_PAIDVISIBILITY, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
            db.delete(CommonString.TABLE_PROMOTION_DATA, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
            db.delete(CommonString.TABLE_STORE_PROFILE_DATA, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
            db.delete(CommonString.TABLE_COMP_PROMOTION, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
            db.delete(CommonString.TABLE_COMP_NPD, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
            db.delete(CommonString.TABLE_VQPS, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePreviousUploadedData(String visit_date) {
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery("SELECT  * FROM " + CommonString.TABLE_COVERAGE_DATA + " WHERE " + CommonString.KEY_VISIT_DATE + " <>'" + visit_date + "'", null);
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                int icount = dbcursor.getCount();
                dbcursor.close();
                if (icount > 0) {
                    db.delete(CommonString.TABLE_COVERAGE_DATA, null, null);
                    db.delete(CommonString.TABLE_STORE_GEOTAGGING, null, null);
                    db.delete(CommonString.TABLE_PROGRAM_CHECKLIST, null, null);
                    db.delete(CommonString.TABLE_VISICOOLER_PRESENT, null, null);
                    db.delete(CommonString.TABLE_PROGRAM_CHECKLIST_PRESENT, null, null);
                    db.delete(CommonString.TABLE_VISICOOLER_CHECKLIST, null, null);

                    db.delete(CommonString.TABLE_POSM_PRESENT, null, null);
                    db.delete(CommonString.TABLE_POSM, null, null);
                    db.delete(CommonString.TABLE_PAIDVISIBILITY, null, null);
                    db.delete(CommonString.TABLE_PROMOTION_DATA, null, null);
                    db.delete(CommonString.TABLE_STORE_PROFILE_DATA, null, null);
                    db.delete(CommonString.TABLE_COMP_PROMOTION, null, null);
                    db.delete(CommonString.TABLE_COMP_NPD, null, null);
                    db.delete(CommonString.TABLE_VQPS, null, null);
                    db.delete(CommonString.TABLE_STORE_TIME, null, null);
                    db.delete(CommonString.TABLE_VISIBILITY_DRIVE_DATA, null, null);
                    db.delete(CommonString.TABLE_RDVISIBILITY_DRIVE_DATA, null, null);

                }

                dbcursor.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long updateStatus(String id, String status) {
        ContentValues values = new ContentValues();
        long l = 0;
        try {

            values.put("GeoTag", status);
            l = db.update(CommonString.TABLE_Journey_Plan, values, "StoreId" + "='" + id + "'", null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return l;
    }

    public void updateStatusNonemerchandise(String id, String status) {
        ContentValues values = new ContentValues();
        try {

            values.put("GeoTag", status);
            db.update("JourneyPlan_NonMerchandised", values, "StoreId" + "='" + id + "'", null);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateStatusNotCoveredemerchandise(String id, String status) {
        ContentValues values = new ContentValues();
        try {
            values.put("GeoTag", status);
            db.update("JourneyPlan_NotCovered", values, "StoreId" + "='" + id + "'", null);
        } catch (Exception ex) {
        }
    }


    public long InsertSTOREgeotag(String storeid, double lat, double longitude, String path, String status) {
        ContentValues values = new ContentValues();
        try {
            values.put("STORE_ID", storeid);
            values.put("LATITUDE", Double.toString(lat));
            values.put("LONGITUDE", Double.toString(longitude));
            values.put("FRONT_IMAGE", path);
            values.put("GEO_TAG", status);
            values.put("STATUS", status);

            return db.insert(CommonString.TABLE_STORE_GEOTAGGING, null, values);

        } catch (Exception ex) {
            Log.d("Database Exception ", ex.toString());
            return 0;
        }
    }

    public long updateInsertedGeoTagStatus(String id, String status) {
        ContentValues values = new ContentValues();
        try {
            values.put("GEO_TAG", status);
            values.put("STATUS", status);
            return db.update(CommonString.TABLE_STORE_GEOTAGGING, values, CommonString.KEY_STORE_ID + "='" + id + "'", null);
        } catch (Exception ex) {
            return 0;
        }
    }

    public ArrayList<CoverageBean> getCoverageDataPrevious(String visitdate) {

        ArrayList<CoverageBean> list = new ArrayList<CoverageBean>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_COVERAGE_DATA + " where " + CommonString.KEY_VISIT_DATE + " <> '" + visitdate + "'",
                    null);

            if (dbcursor != null) {

                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    CoverageBean sb = new CoverageBean();

                    sb.setStoreId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_ID)));
                    sb.setUserId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_USER_ID)));
                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE)));
                    sb.setLatitude(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LATITUDE)));
                    sb.setLongitude(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LONGITUDE)));
                    sb.setImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE)));
                    sb.setReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON)));
                    sb.setReasonid(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON_ID)));
                    sb.setMID(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ID))));
                    //sb.setCkeckout_image(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CHECKOUT_IMAGE)));

                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_COVERAGE_REMARK)));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception get JCP!", e.toString());
            return list;
        }
        return list;
    }

    public long updateCheckoutStatus(String id, String status, String table) {
        ContentValues values = new ContentValues();
        try {
            values.put("UploadStatus", status);
            return db.update(table, values, "StoreId " + " = '" + id + "'", null);
        } catch (Exception ex) {
            Log.e("Exception", " Mapping_JourneyPlan" + ex.toString());
            return 0;
        }
    }

    public long updateCheckoutStatusDBSR(String id, String status, String table) {
        ContentValues values = new ContentValues();
        try {
            values.put("UploadStatus", status);
            return db.update(table, values, "StoreId " + " = '" + id + "'", null);
        } catch (Exception ex) {
            Log.e("Exception", " Mapping_JourneyPlan" + ex.toString());
            return 0;
        }
    }


    public void updateStoreStatus(String storeid, String visitdate, String status) {
        try {
            ContentValues values = new ContentValues();
            values.put("UploadStatus", status);
            db.update("Mapping_JourneyPlan", values, "StoreId ='" + storeid + "' AND VisitDate ='" + visitdate + "'", null);
        } catch (Exception e) {

        }
    }

    public void updateStoreStatusNonMerchandised(String storeid, String visitdate, String status) {
        try {
            ContentValues values = new ContentValues();
            values.put("UploadStatus", status);
            db.update("JourneyPlan_NonMerchandised", values, "StoreId ='" + storeid + "' AND VisitDate ='" + visitdate + "'", null);
        } catch (Exception e) {

        }
    }


    public long InsertCoverageData(CoverageBean data) {
        long l = 0;
        l = db.delete(CommonString.TABLE_COVERAGE_DATA, CommonString.KEY_STORE_ID + "=" + data.getStoreId() + " AND " + CommonString.KEY_VISIT_DATE + "='" + data.getVisitDate() + "'", null);
        ContentValues values = new ContentValues();
        try {
            values.put(CommonString.KEY_STORE_ID, data.getStoreId());
            values.put(CommonString.KEY_USER_ID, data.getUserId());
            values.put(CommonString.KEY_VISIT_DATE, data.getVisitDate());
            values.put(CommonString.KEY_LATITUDE, data.getLatitude());
            values.put(CommonString.KEY_LONGITUDE, data.getLongitude());
            values.put(CommonString.KEY_IMAGE, data.getImage());
            values.put(CommonString.KEY_COVERAGE_REMARK, data.getRemark());
            values.put(CommonString.KEY_REASON_ID, data.getReasonid());
            values.put(CommonString.KEY_REASON, data.getReason());
            values.put(CommonString.KEY_CHECKOUT_IMAGE, data.getCkeckout_image());
            values.put(CommonString.KEY_STORE_FLAG, data.getFlag_from());
            values.put(CommonString.KEY_MID, data.getMID());
            values.put("CHECKOUT_TIME", data.getChECKOUT_TIME());
            l = db.insert(CommonString.TABLE_COVERAGE_DATA, null, values);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return l;
    }

    public long updateStoreStatusOnLeave(String storeid, String visitdate, String status, String designation) {
        long id = 0;
        try {
            if (designation.equalsIgnoreCase("DBSR")) {
                ContentValues values = new ContentValues();
                values.put("UploadStatus", status);
                id = db.update(CommonString.TABLE_Journey_Plan_DBSR_Saved, values, "StoreId =" + storeid + " and VisitDate ='" + visitdate + "'", null);

            } else {
                ContentValues values = new ContentValues();
                values.put("UploadStatus", status);
                id = db.update(CommonString.TABLE_Journey_Plan, values, "StoreId =" + storeid + " and VisitDate ='" + visitdate + "'", null);

            }

        } catch (Exception e) {
            return 0;
        }
        return id;
    }

    public boolean insertJCPData(JCPGetterSetter data) {
        db.delete("Mapping_JourneyPlan", null, null);
        List<MappingJourneyPlan> jcpList = data.getMappingJourneyPlan();
        ContentValues values = new ContentValues();
        try {
            if (jcpList.size() == 0) {
                return false;
            }
            for (int i = 0; i < jcpList.size(); i++) {
                values.put("Address", jcpList.get(i).getAddress());
                values.put("ChannelId", jcpList.get(i).getChannelId());
                values.put("CityId", jcpList.get(i).getCityId());
                values.put("CityName", jcpList.get(i).getCityName());
                values.put("ContactPerson", jcpList.get(i).getContactPerson());
                values.put("DistributorId", jcpList.get(i).getDistributorId());
                values.put("DistributorName", jcpList.get(i).getDistributorName());
                values.put("EmpId", jcpList.get(i).getEmpId());
                values.put("Email", jcpList.get(i).getEmail());
                values.put("GeoTag", jcpList.get(i).getGeoTag());
                values.put("GSTno", jcpList.get(i).getGSTno());
                values.put("GSTImage", jcpList.get(i).getGSTImage());
                values.put("Landmark", jcpList.get(i).getLandmark());
                values.put("Latitude", jcpList.get(i).getLatitude());
                values.put("Longitude", jcpList.get(i).getLongitude());
                values.put("Location", jcpList.get(i).getLocation());
                values.put("MID", jcpList.get(i).getMID());
                values.put("Mobile", jcpList.get(i).getMobile());
                values.put("Phone", jcpList.get(i).getPhone());
                values.put("Pincode", jcpList.get(i).getPincode());
                values.put("PosCompany", jcpList.get(i).getPosCompany());
                values.put("PosExist", jcpList.get(i).getPosExist());
                values.put("PosRemark", jcpList.get(i).getPosRemark());
                values.put("StoreId", jcpList.get(i).getStoreId());
                values.put("StateName", jcpList.get(i).getStateName());
                values.put("StoreCategory", jcpList.get(i).getStoreCategory());
                values.put("StoreCategoryId", jcpList.get(i).getStoreCategoryId());
                values.put("StoreCode", jcpList.get(i).getStoreCode());
                values.put("StateId", jcpList.get(i).getStateId());
                values.put("StoreName", jcpList.get(i).getStoreName());
                values.put("StoreType", jcpList.get(i).getStoreType());
                values.put("StoreTypeId", jcpList.get(i).getStoreTypeId());
                values.put("StoreClassId", jcpList.get(i).getStoreClassId());
                values.put("StoreClass", jcpList.get(i).getStoreClass());
                values.put("TaxType", jcpList.get(i).getTaxType());
                values.put("UploadStatus", jcpList.get(i).getUploadStatus());
                values.put("VisitDate", jcpList.get(i).getVisitDate());
                values.put("LastMonthScore", jcpList.get(i).getLastMonthScore());

                long id = db.insert("Mapping_JourneyPlan", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Exception in Jcp", ex.toString());
            return false;
        }
    }

    public boolean insertmasterprogram(JCPGetterSetter data) {
        db.delete("Master_Program", null, null);
        List<MasterProgram> jcpList = data.getMasterProgram();
        ContentValues values = new ContentValues();
        try {
            if (jcpList.size() == 0) {
                return false;
            }
            for (int i = 0; i < jcpList.size(); i++) {
                values.put("ProgramId", jcpList.get(i).getProgramId());
                values.put("ProgramName", jcpList.get(i).getProgramName());
                values.put("SubProgramId", jcpList.get(i).getSubProgramId());
                values.put("SubProgramName", jcpList.get(i).getSubProgramName());
                values.put("ImagePath", jcpList.get(i).getImagePath());
                values.put("ProgramNormalIcon", jcpList.get(i).getProgramNormalIcon());
                values.put("ProgramTickIcon", jcpList.get(i).getProgramTickIcon());
                values.put("ProgramIconBaseColor", jcpList.get(i).getProgramIconBaseColor());
                values.put("SubProgramNormalIcon", jcpList.get(i).getSubProgramNormalIcon());
                values.put("SubProgramTickIcon", jcpList.get(i).getSubProgramTickIcon());
                values.put("SubProgramIconBaseColor", jcpList.get(i).getSubProgramIconBaseColor());


                long id = db.insert("Master_Program", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Exception in Jcp", ex.toString());
            return false;
        }
    }

    public boolean insertJourneyPlan_NonMerchandised(JCPGetterSetter data) {
        db.delete("JourneyPlan_NonMerchandised", null, null);
        List<MappingJourneyPlan> jcpList = data.getJourneyPlan_deviation();
        ContentValues values = new ContentValues();
        try {
            if (jcpList.size() == 0) {
                return false;
            }
            for (int i = 0; i < jcpList.size(); i++) {
                values.put("Address", jcpList.get(i).getAddress());
                values.put("ChannelId", jcpList.get(i).getChannelId());
                values.put("CityId", jcpList.get(i).getCityId());
                values.put("CityName", jcpList.get(i).getCityName());
                values.put("ContactPerson", jcpList.get(i).getContactPerson());
                values.put("DistributorId", jcpList.get(i).getDistributorId());
                values.put("DistributorName", jcpList.get(i).getDistributorName());
                values.put("EmpId", jcpList.get(i).getEmpId());
                values.put("Email", jcpList.get(i).getEmail());
                values.put("GeoTag", jcpList.get(i).getGeoTag());
                values.put("GSTno", jcpList.get(i).getGSTno());
                values.put("GSTImage", jcpList.get(i).getGSTImage());
                values.put("Landmark", jcpList.get(i).getLandmark());
                values.put("Latitude", jcpList.get(i).getLatitude());
                values.put("Longitude", jcpList.get(i).getLongitude());
                values.put("Location", jcpList.get(i).getLocation());
                values.put("MID", jcpList.get(i).getMID());
                values.put("Mobile", jcpList.get(i).getMobile());
                values.put("Phone", jcpList.get(i).getPhone());
                values.put("Pincode", jcpList.get(i).getPincode());
                values.put("PosCompany", jcpList.get(i).getPosCompany());
                values.put("PosExist", jcpList.get(i).getPosExist());
                values.put("PosRemark", jcpList.get(i).getPosRemark());
                values.put("StoreId", jcpList.get(i).getStoreId());
                values.put("StateName", jcpList.get(i).getStateName());
                values.put("StoreCategory", jcpList.get(i).getStoreCategory());
                values.put("StoreCategoryId", jcpList.get(i).getStoreCategoryId());
                values.put("StoreCode", jcpList.get(i).getStoreCode());
                values.put("StateId", jcpList.get(i).getStateId());
                values.put("StoreName", jcpList.get(i).getStoreName());
                values.put("StoreType", jcpList.get(i).getStoreType());
                values.put("StoreTypeId", jcpList.get(i).getStoreTypeId());
                values.put("StoreClassId", jcpList.get(i).getStoreClassId());
                values.put("StoreClass", jcpList.get(i).getStoreClass());
                values.put("TaxType", jcpList.get(i).getTaxType());
                values.put("UploadStatus", jcpList.get(i).getUploadStatus());
                values.put("VisitDate", jcpList.get(i).getVisitDate());
                values.put("LastMonthScore", jcpList.get(i).getLastMonthScore());

                long id = db.insert("JourneyPlan_NonMerchandised", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Exception in Jcp", ex.toString());
            return false;
        }
    }


    public boolean insertJourneyPlan_NotCovered(JCPGetterSetter data) {
        db.delete("JourneyPlan_NotCovered", null, null);
        List<MappingJourneyPlan> jcpList = data.getJourneyPlan_deviation_nc();
        ContentValues values = new ContentValues();
        try {
            if (jcpList.size() == 0) {
                return false;
            }
            for (int i = 0; i < jcpList.size(); i++) {
                values.put("Address", jcpList.get(i).getAddress());
                values.put("ChannelId", jcpList.get(i).getChannelId());
                values.put("CityId", jcpList.get(i).getCityId());
                values.put("CityName", jcpList.get(i).getCityName());
                values.put("ContactPerson", jcpList.get(i).getContactPerson());
                values.put("DistributorId", jcpList.get(i).getDistributorId());
                values.put("DistributorName", jcpList.get(i).getDistributorName());
                values.put("EmpId", jcpList.get(i).getEmpId());
                values.put("Email", jcpList.get(i).getEmail());
                values.put("GeoTag", jcpList.get(i).getGeoTag());
                values.put("GSTno", jcpList.get(i).getGSTno());
                values.put("GSTImage", jcpList.get(i).getGSTImage());
                values.put("Landmark", jcpList.get(i).getLandmark());
                values.put("Latitude", jcpList.get(i).getLatitude());
                values.put("Longitude", jcpList.get(i).getLongitude());
                values.put("Location", jcpList.get(i).getLocation());
                values.put("MID", jcpList.get(i).getMID());
                values.put("Mobile", jcpList.get(i).getMobile());
                values.put("Phone", jcpList.get(i).getPhone());
                values.put("Pincode", jcpList.get(i).getPincode());
                values.put("PosCompany", jcpList.get(i).getPosCompany());
                values.put("PosExist", jcpList.get(i).getPosExist());
                values.put("PosRemark", jcpList.get(i).getPosRemark());
                values.put("StoreId", jcpList.get(i).getStoreId());
                values.put("StateName", jcpList.get(i).getStateName());
                values.put("StoreCategory", jcpList.get(i).getStoreCategory());
                values.put("StoreCategoryId", jcpList.get(i).getStoreCategoryId());
                values.put("StoreCode", jcpList.get(i).getStoreCode());
                values.put("StateId", jcpList.get(i).getStateId());
                values.put("StoreName", jcpList.get(i).getStoreName());
                values.put("StoreType", jcpList.get(i).getStoreType());
                values.put("StoreTypeId", jcpList.get(i).getStoreTypeId());
                values.put("StoreClassId", jcpList.get(i).getStoreClassId());
                values.put("StoreClass", jcpList.get(i).getStoreClass());
                values.put("TaxType", jcpList.get(i).getTaxType());
                values.put("UploadStatus", jcpList.get(i).getUploadStatus());
                values.put("VisitDate", jcpList.get(i).getVisitDate());

                long id = db.insert("JourneyPlan_NotCovered", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Exception in Jcp", ex.toString());
            return false;
        }
    }


    public boolean insertNonWorkingData(NonWorkingReasonGetterSetter nonWorkingdata) {
        db.delete("Non_Working_Reason", null, null);
        ContentValues values = new ContentValues();
        List<NonWorkingReason> data = nonWorkingdata.getNonWorkingReason();
        try {
            if (data.size() == 0) {
                return false;
            }

            for (int i = 0; i < data.size(); i++) {

                values.put("ReasonId", data.get(i).getReasonId());
                values.put("Reason", data.get(i).getReason());
                values.put("EntryAllow", data.get(i).getEntryAllow());
                values.put("ImageAllow", data.get(i).getImageAllow());
                values.put("GPSMandatory", data.get(i).getGPSMandatory());
                values.put("PosmTracking", data.get(i).getPosmTracking());

                long id = db.insert("Non_Working_Reason", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Database Exception  ", ex.toString());
            return false;
        }
    }

    public boolean isCoverageDataFilled(String visit_date) {
        boolean filled = false;
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_COVERAGE_DATA + " WHERE " + CommonString.KEY_VISIT_DATE + "<>'" + visit_date + "'", null);
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                int icount = dbcursor.getCount();
                dbcursor.close();
                if (icount > 0) {
                    filled = true;
                } else {
                    filled = false;
                }

            }

        } catch (Exception e) {
            return filled;
        }

        return filled;
    }


    public MappingJourneyPlan getSpecificStoreDataPrevious(String date, String store_id) {
        MappingJourneyPlan sb = new MappingJourneyPlan();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("select * from Mapping_JourneyPlan where VisitDate <> '" + date + "' and StoreId=" + store_id + "", null);
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {

                    sb.setAddress((dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address"))));
                    sb.setCityId((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("CityId"))));
                    sb.setCityName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CityName")));
                    sb.setContactPerson(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ContactPerson")));
                    sb.setEmail(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Email")));
                    sb.setEmpId((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("EmpId"))));
                    sb.setLandmark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Landmark")));
                    sb.setLocation(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Location")));
                    sb.setLongitude(dbcursor.getFloat(dbcursor.getColumnIndexOrThrow("Longitude")));
                    sb.setLatitude(dbcursor.getFloat(dbcursor.getColumnIndexOrThrow("Latitude")));
                    sb.setGeoTag(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GeoTag")));
                    sb.setMID((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("MID"))));
                    sb.setMobile(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Mobile")));
                    sb.setChannelId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ChannelId")));
                    sb.setPhone(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Phone")));
                    sb.setPincode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Pincode")));
                    sb.setStateId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StateId")));
                    sb.setStateName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StateName")));
                    sb.setStoreCode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreCode")));
                    sb.setStoreId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreId")));
                    sb.setStoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreName")));
                    sb.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreType")));
                    sb.setStoreTypeId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreTypeId")));
                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("UploadStatus")));
                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("VisitDate")));
                    sb.setStoreCategoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreCategoryId")));


                    sb.setDistributorId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("DistributorId")));
                    sb.setDistributorName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("DistributorName")));
                    sb.setStoreCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreCategory")));
                    sb.setStoreClassId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreClassId")));
                    sb.setStoreClass(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreClass")));
                    sb.setPosExist(Boolean.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosExist"))));
                    sb.setPosCompany(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosCompany")));
                    sb.setPosRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosRemark")));
                    sb.setTaxType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("TaxType")));
                    sb.setGSTno(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GSTno")));
                    sb.setGSTImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GSTImage")));
                    sb.setLastMonthScore(dbcursor.getString(dbcursor.getColumnIndexOrThrow("LastMonthScore")));


                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return sb;
            }

        } catch (Exception e) {
            Log.d("Exception get JCP!", e.toString());
            return sb;
        }

        return sb;
    }

    public ArrayList<CoverageBean> getSpecificCoverageData(String visitdate, String store_cd) {
        ArrayList<CoverageBean> list = new ArrayList<CoverageBean>();
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_COVERAGE_DATA + " where " + CommonString.KEY_VISIT_DATE + "='" + visitdate + "' AND " +
                    CommonString.KEY_STORE_ID + "='" + store_cd + "'", null);

            if (dbcursor != null) {

                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    CoverageBean sb = new CoverageBean();
                    sb.setStoreId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_ID)));
                    sb.setUserId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_USER_ID)));
                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE)));
                    sb.setLatitude(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LATITUDE)));
                    sb.setLongitude(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LONGITUDE)));
                    sb.setImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE)));
                    sb.setReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON)));
                    sb.setReasonid(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON_ID)));
                    sb.setCkeckout_image(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_COVERAGE_REMARK)));
                    sb.setMID(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_MID))));
                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_COVERAGE_REMARK)));
                    sb.setChECKOUT_TIME(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CHECKOUT_TIME")));
                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {

        }
        return list;
    }


    public ArrayList<MappingJourneyPlan> getSpecificStoreData(String table, String store_cd) {
        ArrayList<MappingJourneyPlan> list = new ArrayList<MappingJourneyPlan>();
        Cursor dbcursor = null;
        try {
            if (table != null) {
                if (table.equalsIgnoreCase("from_jcp")) {

                    dbcursor = db.rawQuery("SELECT  * from Mapping_JourneyPlan  " + "where StoreId ='" + store_cd + "'", null);

                } else if (table.equalsIgnoreCase("from_non_merchandized")) {

                    dbcursor = db.rawQuery("SELECT  * from JourneyPlan_NonMerchandised  " + "where StoreId ='" + store_cd + "'", null);

                } else if (table.equalsIgnoreCase("from_not_covered")) {

                    dbcursor = db.rawQuery("SELECT  * from JourneyPlan_NotCovered  " + "where StoreId ='" + store_cd + "'", null);

                }

            } else {
                dbcursor = db.rawQuery("SELECT  * from Mapping_JourneyPlan  " + "where StoreId ='" + store_cd + "'", null);
            }


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MappingJourneyPlan sb = new MappingJourneyPlan();

                    sb.setAddress((dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address"))));
                    sb.setCityId((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("CityId"))));
                    sb.setCityName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CityName")));
                    sb.setContactPerson(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ContactPerson")));
                    sb.setEmail(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Email")));
                    sb.setEmpId((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("EmpId"))));
                    sb.setLandmark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Landmark")));
                    sb.setLocation(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Location")));
                    sb.setLongitude(dbcursor.getFloat(dbcursor.getColumnIndexOrThrow("Longitude")));
                    sb.setLatitude(dbcursor.getFloat(dbcursor.getColumnIndexOrThrow("Latitude")));
                    sb.setGeoTag(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GeoTag")));
                    sb.setMID((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("MID"))));
                    sb.setMobile(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Mobile")));
                    sb.setChannelId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ChannelId")));
                    sb.setPhone(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Phone")));
                    sb.setPincode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Pincode")));
                    sb.setStateId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StateId")));
                    sb.setStateName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StateName")));
                    sb.setStoreCode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreCode")));
                    sb.setStoreId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreId")));
                    sb.setStoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreName")));
                    sb.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreType")));
                    sb.setStoreTypeId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreTypeId")));
                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("UploadStatus")));
                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("VisitDate")));
                    sb.setDistributorId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("DistributorId")));
                    sb.setDistributorName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("DistributorName")));
                    sb.setStoreCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreCategory")));
                    sb.setStoreClassId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreClassId")));
                    sb.setStoreClass(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreClass")));
                    sb.setPosExist(Boolean.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosExist"))));
                    sb.setPosCompany(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosCompany")));
                    sb.setPosRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosRemark")));
                    sb.setTaxType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("TaxType")));
                    sb.setGSTno(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GSTno")));
                    sb.setGSTImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GSTImage")));
                    sb.setStoreCategoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreCategoryId")));
                    sb.setLastMonthScore(dbcursor.getString(dbcursor.getColumnIndexOrThrow("LastMonthScore")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception get JCP!", e.toString());
            return list;
        }


        return list;
    }


    public ArrayList<NonWorkingReason> getNonWorkingEntryAllowData() {

        ArrayList<NonWorkingReason> list = new ArrayList<NonWorkingReason>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("SELECT * FROM Non_Working_Reason WHERE EntryAllow=1", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    NonWorkingReason sb = new NonWorkingReason();

                    sb.setReasonId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ReasonId"))));
                    sb.setReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason")));
                    sb.setEntryAllow("1".equalsIgnoreCase(dbcursor.getString(dbcursor.getColumnIndexOrThrow("EntryAllow"))));
                    sb.setImageAllow("1".equalsIgnoreCase(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ImageAllow"))));
                    sb.setGPSMandatory("1".equalsIgnoreCase(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GPSMandatory"))));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            return list;
        }
        return list;
    }

    public ArrayList<NonWorkingReason> getNonWorkingData() {
        ArrayList<NonWorkingReason> list = new ArrayList<NonWorkingReason>();
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery("SELECT * FROM Non_Working_Reason", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    NonWorkingReason sb = new NonWorkingReason();

                    sb.setReasonId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ReasonId"))));
                    sb.setReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason")));
                    sb.setEntryAllow("1".equalsIgnoreCase(dbcursor.getString(dbcursor.getColumnIndexOrThrow("EntryAllow"))));
                    sb.setImageAllow("1".equalsIgnoreCase(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ImageAllow"))));
                    sb.setGPSMandatory("1".equalsIgnoreCase(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GPSMandatory"))));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            return list;
        }
        return list;
    }


    public ArrayList<GeotaggingBeans> getinsertGeotaggingData(String storeid, String status) {
        ArrayList<GeotaggingBeans> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery("Select * from " + CommonString.TABLE_STORE_GEOTAGGING + "" +
                    " where " + CommonString.KEY_STORE_ID + " ='" + storeid + "' and " + CommonString.KEY_STATUS + " = '" + status + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    GeotaggingBeans geoTag = new GeotaggingBeans();
                    geoTag.setStoreid(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_ID)));
                    geoTag.setLatitude(Double.parseDouble(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LATITUDE))));
                    geoTag.setLongitude(Double.parseDouble(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LONGITUDE))));
                    geoTag.setImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow("FRONT_IMAGE")));
                    list.add(geoTag);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception Brands",
                    e.toString());
            return list;
        }
        return list;

    }


    public ArrayList<MappingJourneyPlan> getStoreData(String date) {
        ArrayList<MappingJourneyPlan> list = new ArrayList<MappingJourneyPlan>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("SELECT  * FROM Mapping_JourneyPlan  " + "WHERE VisitDate ='" + date + "' ORDER BY StoreName", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MappingJourneyPlan sb = new MappingJourneyPlan();
                    sb.setAddress((dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address"))));
                    sb.setCityId((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("CityId"))));
                    sb.setCityName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CityName")));
                    sb.setContactPerson(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ContactPerson")));
                    sb.setEmail(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Email")));
                    sb.setEmpId((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("EmpId"))));
                    sb.setLandmark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Landmark")));
                    sb.setLocation(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Location")));
                    sb.setLongitude(dbcursor.getFloat(dbcursor.getColumnIndexOrThrow("Longitude")));
                    sb.setLatitude(dbcursor.getFloat(dbcursor.getColumnIndexOrThrow("Latitude")));
                    sb.setGeoTag(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GeoTag")));
                    sb.setMID((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("MID"))));
                    sb.setMobile(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Mobile")));
                    sb.setChannelId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ChannelId")));
                    sb.setPhone(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Phone")));
                    sb.setPincode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Pincode")));
                    sb.setStateId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StateId")));
                    sb.setStateName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StateName")));
                    sb.setStoreCode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreCode")));
                    sb.setStoreId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreId")));
                    sb.setStoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreName")));
                    sb.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreType")));
                    sb.setStoreTypeId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreTypeId")));
                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("UploadStatus")));
                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("VisitDate")));
                    sb.setDistributorId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("DistributorId")));
                    sb.setDistributorName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("DistributorName")));
                    sb.setStoreCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreCategory")));
                    sb.setStoreClassId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreClassId")));
                    sb.setStoreClass(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreClass")));
                    sb.setPosExist(Boolean.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosExist"))));
                    sb.setPosCompany(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosCompany")));
                    sb.setPosRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosRemark")));
                    sb.setTaxType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("TaxType")));
                    sb.setGSTno(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GSTno")));
                    sb.setGSTImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GSTImage")));
                    sb.setStoreCategoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreCategoryId")));
                    sb.setLastMonthScore(dbcursor.getString(dbcursor.getColumnIndexOrThrow("LastMonthScore")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception get JCP!", e.toString());
            return list;
        }


        return list;
    }


    public ArrayList<MappingJourneyPlan> getStoreDataNonmerchandise(String date) {
        ArrayList<MappingJourneyPlan> list = new ArrayList<MappingJourneyPlan>();
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery("SELECT  * FROM JourneyPlan_NonMerchandised  " + "WHERE VisitDate ='" + date + "' ORDER BY StoreName", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MappingJourneyPlan sb = new MappingJourneyPlan();
                    sb.setAddress((dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address"))));
                    sb.setCityId((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("CityId"))));
                    sb.setCityName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CityName")));
                    sb.setContactPerson(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ContactPerson")));
                    sb.setEmail(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Email")));
                    sb.setEmpId((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("EmpId"))));
                    sb.setLandmark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Landmark")));
                    sb.setLocation(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Location")));
                    sb.setLongitude(dbcursor.getFloat(dbcursor.getColumnIndexOrThrow("Longitude")));
                    sb.setLatitude(dbcursor.getFloat(dbcursor.getColumnIndexOrThrow("Latitude")));
                    sb.setGeoTag(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GeoTag")));
                    sb.setMID((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("MID"))));
                    sb.setMobile(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Mobile")));
                    sb.setChannelId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ChannelId")));
                    sb.setPhone(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Phone")));
                    sb.setPincode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Pincode")));
                    sb.setStateId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StateId")));
                    sb.setStateName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StateName")));
                    sb.setStoreCode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreCode")));
                    sb.setStoreId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreId")));
                    sb.setStoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreName")));
                    sb.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreType")));
                    sb.setStoreTypeId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreTypeId")));
                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("UploadStatus")));
                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("VisitDate")));
                    sb.setDistributorId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("DistributorId")));
                    sb.setDistributorName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("DistributorName")));
                    sb.setStoreCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreCategory")));
                    sb.setStoreClassId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreClassId")));
                    sb.setStoreClass(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreClass")));
                    sb.setPosExist(Boolean.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosExist"))));
                    sb.setPosCompany(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosCompany")));
                    sb.setPosRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosRemark")));
                    sb.setTaxType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("TaxType")));
                    sb.setGSTno(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GSTno")));
                    sb.setGSTImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GSTImage")));
                    sb.setStoreCategoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreCategoryId")));
                    sb.setLastMonthScore(dbcursor.getString(dbcursor.getColumnIndexOrThrow("LastMonthScore")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception get JCP!", e.toString());
            return list;
        }


        return list;
    }


    public ArrayList<MappingJourneyPlan> getStoreDataNotCovered(String date) {
        ArrayList<MappingJourneyPlan> list = new ArrayList<MappingJourneyPlan>();
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery("SELECT  * FROM JourneyPlan_NotCovered  " + "WHERE VisitDate ='" + date + "' ORDER BY StoreName", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MappingJourneyPlan sb = new MappingJourneyPlan();
                    sb.setAddress((dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address"))));
                    sb.setCityId((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("CityId"))));
                    sb.setCityName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CityName")));
                    sb.setContactPerson(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ContactPerson")));
                    sb.setEmail(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Email")));
                    sb.setEmpId((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("EmpId"))));
                    sb.setLandmark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Landmark")));
                    sb.setLocation(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Location")));
                    sb.setLongitude(dbcursor.getFloat(dbcursor.getColumnIndexOrThrow("Longitude")));
                    sb.setLatitude(dbcursor.getFloat(dbcursor.getColumnIndexOrThrow("Latitude")));
                    sb.setGeoTag(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GeoTag")));
                    sb.setMID((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("MID"))));
                    sb.setMobile(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Mobile")));
                    sb.setChannelId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ChannelId")));
                    sb.setPhone(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Phone")));
                    sb.setPincode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Pincode")));
                    sb.setStateId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StateId")));
                    sb.setStateName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StateName")));
                    sb.setStoreCode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreCode")));
                    sb.setStoreId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreId")));
                    sb.setStoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreName")));
                    sb.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreType")));
                    sb.setStoreTypeId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreTypeId")));
                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("UploadStatus")));
                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("VisitDate")));
                    sb.setDistributorId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("DistributorId")));
                    sb.setDistributorName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("DistributorName")));
                    sb.setStoreCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreCategory")));
                    sb.setStoreClassId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreClassId")));
                    sb.setStoreClass(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreClass")));
                    sb.setPosExist(Boolean.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosExist"))));
                    sb.setPosCompany(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosCompany")));
                    sb.setPosRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosRemark")));
                    sb.setTaxType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("TaxType")));
                    sb.setGSTno(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GSTno")));
                    sb.setGSTImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GSTImage")));
                    sb.setStoreCategoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreCategoryId")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception get JCP!", e.toString());
            return list;
        }


        return list;
    }


    public ArrayList<CoverageBean> getCoverageData(String visitdate, String tagfrom) {
        ArrayList<CoverageBean> list = new ArrayList<CoverageBean>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_COVERAGE_DATA + " WHERE " + CommonString.KEY_VISIT_DATE + "='" + visitdate + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    CoverageBean sb = new CoverageBean();
                    sb.setStoreId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_ID)));
                    sb.setUserId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_USER_ID)));
                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE)));
                    sb.setLatitude(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LATITUDE)));
                    sb.setLongitude(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LONGITUDE)));
                    sb.setImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE)));
                    sb.setReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON)));
                    sb.setReasonid(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON_ID)));
                    sb.setCkeckout_image(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CHECKOUT_IMAGE)));
                    sb.setMID(Integer.parseInt(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_MID))));
                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_COVERAGE_REMARK)));
                    sb.setChECKOUT_TIME(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CHECKOUT_TIME")));
                    sb.setFlag_from(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_FLAG)));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();


        }

        return list;

    }

    //-----------------------------------------------------------------
    public boolean insertMenuMasterData(MenuMasterGetterSetter menuMasterGetterSetter) {
        db.delete("Menu_Master", null, null);
        ContentValues values = new ContentValues();
        List<MenuMaster> data = menuMasterGetterSetter.getMenuMaster();
        try {
            if (data.size() == 0) {
                return false;
            }

            for (int i = 0; i < data.size(); i++) {
                values.put("MenuId", data.get(i).getMenuId());
                values.put("MenuName", data.get(i).getMenuName());
                values.put("NormalIcon", data.get(i).getNormalIcon());
                values.put("TickIcon", data.get(i).getTickIcon());
                values.put("GreyIcon", data.get(i).getGreyIcon());
                values.put("MenuPath", data.get(i).getMenuPath());
                values.put("BGColor", data.get(i).getBGColor());
                values.put("MenuSequence", data.get(i).getMenuSequence());

                long id = db.insert("Menu_Master", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Database Exception  ", ex.toString());
            return false;
        }
    }

    //Insert MenuMapping
    public boolean insertMappingMenuData(MappingMenuGetterSetter mappingMenuGetterSetter) {
        db.delete("Mapping_Menu", null, null);
        ContentValues values = new ContentValues();
        List<MappingMenu> data = mappingMenuGetterSetter.getMappingMenu();
        try {
            if (data.size() == 0) {
                return false;
            }

            for (int i = 0; i < data.size(); i++) {

                values.put("MenuId", data.get(i).getMenuId());
                values.put("UserType", data.get(i).getUserType());
                values.put("StoretypeId", data.get(i).getStoretypeId());

                long id = db.insert("Mapping_Menu", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Database Exception  ", ex.toString());
            return false;
        }
    }

    //get Menu List data for Entry Menu
    public ArrayList<MenuMaster> getMenuData(MappingJourneyPlan jcp, String designation) {
        ArrayList<MenuMaster> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {

            if (jcp != null) {
                dbcursor = db.rawQuery("SELECT DISTINCT t1.MenuId,t1.MenuName,t1.NormalIcon,t1.TickIcon,t1.BGColor," +
                        "t1.GreyIcon,t1.MenuPath from Menu_Master t1 " +
                        "INNER join Mapping_Menu t2 on t1.MenuId=t2.MenuId where t2.StoretypeId=" +
                        jcp.getStoreTypeId() + " and t2.UserType ='" + designation + "' Order by t1.MenuSequence", null);

            } else {
                dbcursor = db.rawQuery("SELECT * From Menu_Master", null);

            }

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MenuMaster ch = new MenuMaster();
                    ch.setMenuId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("MenuId")));
                    ch.setMenuName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("MenuName")));
                    ch.setNormalIcon(dbcursor.getString(dbcursor.getColumnIndexOrThrow("NormalIcon")));
                    ch.setTickIcon(dbcursor.getString(dbcursor.getColumnIndexOrThrow("TickIcon")));
                    ch.setGreyIcon(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GreyIcon")));
                    ch.setMenuPath(dbcursor.getString(dbcursor.getColumnIndexOrThrow("MenuPath")));
                    ch.setBGColor(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BGColor")));
                    list.add(ch);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            return list;
        }

        return list;
    }

    public boolean insertMappingPromotionData(MappingPromotionGetterSetter mappingPromotionGetterSetter) {
        db.delete("Mapping_Promotion", null, null);
        ContentValues values = new ContentValues();
        List<MappingPromotion> data = mappingPromotionGetterSetter.getMappingPromotion();
        try {
            if (data.size() == 0) {
                return false;
            }

            for (int i = 0; i < data.size(); i++) {
                values.put("PromoId", data.get(i).getPromoId());
                values.put("ChannelId", data.get(i).getChannelId());
                values.put("StateId", data.get(i).getStateId());
                values.put("StoreTypeId", data.get(i).getStoreTypeId());
                values.put("CategoryId", data.get(i).getCategoryId());
                values.put("Promotion", data.get(i).getPromotion());


                long id = db.insert("Mapping_Promotion", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Database Exception  ", ex.toString());
            return false;
        }
    }


    public boolean insertMasterNonPromotion(MappingPromotionGetterSetter mappingPromotionGetterSetter) {
        db.delete("Master_NonPromotionReason", null, null);
        ContentValues values = new ContentValues();
        List<MasterNonPromotionReason> data = mappingPromotionGetterSetter.getMasterNonPromotionReason();
        try {
            if (data.size() == 0) {
                return false;
            }

            for (int i = 0; i < data.size(); i++) {
                values.put("ReasonId", data.get(i).getReasonId());
                values.put("Reason", data.get(i).getReason());

                long id = db.insert("Master_NonPromotionReason", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Database Exception  ", ex.toString());
            return false;
        }
    }


    public boolean insertMasterPromoType(MappingPromotionGetterSetter mappingPromotionGetterSetter) {
        db.delete("Master_PromoType", null, null);
        ContentValues values = new ContentValues();
        List<MasterPromoType> data = mappingPromotionGetterSetter.getMasterPromoType();
        try {
            if (data.size() == 0) {
                return false;
            }

            for (int i = 0; i < data.size(); i++) {
                values.put("PromoTypeId", data.get(i).getPromoTypeId());
                values.put("PromoType", data.get(i).getPromoType());

                long id = db.insert("Master_PromoType", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Database Exception  ", ex.toString());
            return false;
        }
    }

    public ArrayList<MasterNonPromotionReason> getnonpromotionreason() {
        ArrayList<MasterNonPromotionReason> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("select * from Master_NonPromotionReason", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterNonPromotionReason sb1 = new MasterNonPromotionReason();
                    sb1.setReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason")));
                    sb1.setReasonId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ReasonId")));
                    list.add(sb1);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching", e.toString());
            return list;
        }

        Log.d("Fetching non working", "-------------------");
        return list;
    }


    public ArrayList<MappingPromotion> getPromotionData(MappingJourneyPlan journeyPlan) {
        ArrayList<MappingPromotion> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("Select Distinct m1.CategoryId,m1.CategoryName,m.PromoId,m.Promotion FROM Mapping_Promotion m" +
                    " inner join Master_Category m1 on m1.CategoryId=m.CategoryId where m.ChannelId =" + journeyPlan.getChannelId() +
                    " and m.StoreTypeId =" + journeyPlan.getStoreTypeId() + " and m.StateId =" + journeyPlan.getStateId() + "", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MappingPromotion sb1 = new MappingPromotion();
                    sb1.setCategory_name(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CategoryName")));
                    sb1.setCategoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("CategoryId")));
                    sb1.setPromotion(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Promotion")));
                    sb1.setPromoId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("PromoId")));
                    sb1.setNonPromotionReasons(getnonpromotionreason());
                    sb1.setPresent("");
                    sb1.setImage1("");
                    sb1.setReason("");
                    sb1.setReasonId(0);
                    sb1.setIsChecked(-1);

                    list.add(sb1);

                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching", e.toString());
            return list;
        }

        Log.d("Fetching non working", "-------------------");
        return list;
    }


    public boolean insertMasterCategory(MasterCategoryGetterSetter masterCategoryGetterSetter) {
        db.delete("Master_Category", null, null);
        ContentValues values = new ContentValues();
        List<MasterCategory> data = masterCategoryGetterSetter.getMasterCategory();
        try {
            if (data.size() == 0) {
                return false;
            }

            for (int i = 0; i < data.size(); i++) {
                values.put("CategoryId", data.get(i).getCategoryId());
                values.put("CategoryName", data.get(i).getCategoryName());
                values.put("CategorySequence", data.get(i).getCategorySequence());
                values.put("CategoryImage", data.get(i).getCategoryImage());
                long id = db.insert("Master_Category", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Database Exception  ", ex.toString());
            return false;
        }
    }


    public ArrayList<MasterCompany> getcompanies() {
        ArrayList<MasterCompany> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("select distinct CompanyId,Company from Master_Company", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterCompany sb = new MasterCompany();
                    sb.setCompanyId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("CompanyId")));
                    sb.setCompany(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Company")));
                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            return list;
        }

        return list;
    }


    public ArrayList<MasterPromoType> getproType() {
        ArrayList<MasterPromoType> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("select * from Master_PromoType", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterPromoType sb = new MasterPromoType();
                    sb.setPromoTypeId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("PromoTypeId")));
                    sb.setPromoType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PromoType")));
                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            return list;
        }

        return list;
    }


    public ArrayList<AvailabilityGetterSetter> getcategories(boolean flagfor_brand, String CategoryId) {
        ArrayList<AvailabilityGetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {

            if (flagfor_brand) {
                dbcursor = db.rawQuery("Select DISTINCT t2.BrandId,t2.BrandName From Master_Category t1 Inner join Master_Brand" +
                        " t2 on t1.CategoryId=t2.CategoryId Where t2.CategoryId=" + CategoryId + " Order By t2.BrandSequence", null);
            } else {
                dbcursor = db.rawQuery("Select DISTINCT t1.CategoryId,t1.CategoryName From Master_Category t1" +
                        " Inner join Master_Brand t2 on t1.CategoryId=t2.CategoryId  Order By t1.CategorySequence", null);
            }

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    AvailabilityGetterSetter sb = new AvailabilityGetterSetter();
                    if (flagfor_brand) {

                        sb.setBrand_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BrandId")));
                        sb.setBrand(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BrandName")));
                    } else {

                        sb.setCategoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("CategoryId")));
                        sb.setCategoryName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CategoryName")));
                    }
                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            return list;
        }

        return list;
    }


    public ArrayList<MasterBrand> getBrandList() {
        ArrayList<MasterBrand> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {


            dbcursor = db.rawQuery("Select Distinct BrandName,BrandId from Master_Brand order by BrandSequence", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterBrand sb = new MasterBrand();
                    sb.setBrandId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("BrandId")));
                    sb.setBrandName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("BrandName")));
                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            return list;
        }

        return list;
    }

    public ArrayList<MappingJourneyPlan> getStoreDataCheking(String date, String store_id, String check_flag, String designation) {
        ArrayList<MappingJourneyPlan> list = new ArrayList<MappingJourneyPlan>();
        Cursor dbcursor = null;
        try {

            if (check_flag != null) {
                if (check_flag.equalsIgnoreCase(CommonString.TAG_FROM_JCP)) {

                    dbcursor = db.rawQuery("SELECT * from Mapping_JourneyPlan where VisitDate ='" + date + "' AND StoreId='" + store_id + "'", null);

                } else if (check_flag.equalsIgnoreCase(CommonString.TAG_FROM_NON_MERCHANDIZED)) {
                    dbcursor = db.rawQuery("SELECT * from JourneyPlan_NonMerchandised where VisitDate ='" + date + "' AND StoreId='" + store_id + "'", null);

                } else if (check_flag.equalsIgnoreCase(CommonString.TAG_FROM_NOT_COVERED)) {
                    dbcursor = db.rawQuery("SELECT * from JourneyPlan_NotCovered where VisitDate ='" + date + "' AND StoreId='" + store_id + "'", null);

                } else {
                    dbcursor = db.rawQuery("SELECT * from Mapping_JourneyPlan where VisitDate ='" + date + "' AND StoreId='" + store_id + "'", null);

                }
            } else {
                if (designation.equalsIgnoreCase("DBSR")) {
                    dbcursor = db.rawQuery("SELECT * from JournyPlan_DBSR where VisitDate ='" + date + "' AND StoreId='" + store_id + "'", null);
                } else {
                    dbcursor = db.rawQuery("SELECT * from Mapping_JourneyPlan where VisitDate ='" + date + "' AND StoreId='" + store_id + "'", null);

                }


            }

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MappingJourneyPlan sb = new MappingJourneyPlan();
                    sb.setAddress((dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address"))));
                    sb.setCityId((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("CityId"))));
                    sb.setCityName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CityName")));
                    sb.setContactPerson(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ContactPerson")));
                    sb.setEmail(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Email")));
                    sb.setEmpId((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("EmpId"))));
                    sb.setLandmark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Landmark")));
                    sb.setLocation(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Location")));
                    sb.setLongitude(dbcursor.getFloat(dbcursor.getColumnIndexOrThrow("Longitude")));
                    sb.setLatitude(dbcursor.getFloat(dbcursor.getColumnIndexOrThrow("Latitude")));
                    sb.setGeoTag(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GeoTag")));
                    sb.setMID((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("MID"))));
                    sb.setMobile(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Mobile")));
                    sb.setChannelId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ChannelId")));
                    sb.setPhone(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Phone")));
                    sb.setPincode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Pincode")));
                    sb.setStateId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StateId")));
                    sb.setStateName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StateName")));
                    sb.setStoreCode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreCode")));
                    sb.setStoreId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreId")));
                    sb.setStoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreName")));
                    sb.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreType")));
                    sb.setStoreTypeId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreTypeId")));
                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("UploadStatus")));
                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("VisitDate")));
                    sb.setDistributorId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("DistributorId")));
                    sb.setDistributorName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("DistributorName")));
                    sb.setStoreCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreCategory")));
                    sb.setStoreClassId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreClassId")));
                    sb.setStoreClass(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreClass")));
                    sb.setPosExist(Boolean.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosExist"))));
                    sb.setPosCompany(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosCompany")));
                    sb.setPosRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosRemark")));
                    sb.setTaxType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("TaxType")));
                    sb.setGSTno(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GSTno")));
                    sb.setGSTImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GSTImage")));
                    sb.setStoreCategoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreCategoryId")));
                    sb.setLastMonthScore(dbcursor.getString(dbcursor.getColumnIndexOrThrow("LastMonthScore")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception get JCP!", e.toString());
            return list;
        }


        return list;
    }

    public ArrayList<VisitorDetail> getVisitorLoginData(String visitdate) {

        ArrayList<VisitorDetail> list = new ArrayList<VisitorDetail>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT  * from TABLE_VISITOR_LOGIN where VISIT_DATE = '" + visitdate + "'"
                    , null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    VisitorDetail sb = new VisitorDetail();
                    sb.setEmpId(Integer.valueOf(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_EMP_CD))));
                    sb.setName(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_NAME)));
                    sb.setDesignation(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_DESIGNATION)));
                    sb.setIn_time_img(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_IN_TIME_IMAGE)));
                    sb.setOut_time_img(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_OUT_TIME_IMAGE)));
                    sb.setEmp_code(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_EMP_CODE)));
                    sb.setVisit_date(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE)));
                    sb.setIn_time(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_IN_TIME)));
                    sb.setOut_time(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_OUT_TIME)));
                    sb.setUpload_status(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_UPLOADSTATUS)));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }

        return list;

    }


    public long updateOutTimeVisitorLoginData(String out_time_image, String out_time, String emp_id) {
        long l = 0;
        ContentValues values = new ContentValues();

        try {
            values.put(CommonString.KEY_OUT_TIME_IMAGE, out_time_image);
            values.put(CommonString.KEY_OUT_TIME, out_time);

            l = db.update(CommonString.TABLE_VISITOR_LOGIN, values, CommonString.KEY_EMP_CD + "='" + emp_id + "'", null);

        } catch (Exception e) {
            Log.d("Exception when Udating Visitor Data!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());

        }
        return l;
    }


    public long InsertVisitorLogindata(ArrayList<VisitorDetail> visitorLoginGetterSetter) {
        db.delete(CommonString.TABLE_VISITOR_LOGIN, null, null);
        ContentValues values = new ContentValues();
        long l = 0;

        try {

            for (int i = 0; i < visitorLoginGetterSetter.size(); i++) {

                values.put(CommonString.KEY_EMP_CD, visitorLoginGetterSetter.get(i).getEmpId());
                values.put(CommonString.KEY_NAME, visitorLoginGetterSetter.get(i).getName());
                values.put(CommonString.KEY_DESIGNATION, visitorLoginGetterSetter.get(i).getDesignation());
                values.put(CommonString.KEY_IN_TIME_IMAGE, visitorLoginGetterSetter.get(i).getIn_time_img());
                values.put(CommonString.KEY_OUT_TIME_IMAGE, visitorLoginGetterSetter.get(i).getOut_time_img());
                values.put(CommonString.KEY_EMP_CODE, visitorLoginGetterSetter.get(i).getEmp_code());
                values.put(CommonString.KEY_VISIT_DATE, visitorLoginGetterSetter.get(i).getVisit_date());
                values.put(CommonString.KEY_IN_TIME, visitorLoginGetterSetter.get(i).getIn_time());
                values.put(CommonString.KEY_OUT_TIME, visitorLoginGetterSetter.get(i).getOut_time());
                values.put(CommonString.KEY_UPLOADSTATUS, visitorLoginGetterSetter.get(i).getUpload_status());

                l = db.insert(CommonString.TABLE_VISITOR_LOGIN, null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert TABLE_VISITOR_LOGIN", ex.toString());
        }
        return l;

    }

    public boolean isVistorDataExists(String emp_id, String visitdate) {
        Log.d("FetchingVisitor Login List --------------->Start<------------", "------------------");
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT  * from TABLE_VISITOR_LOGIN where EMP_CD = '" + emp_id + "' and " + CommonString.KEY_VISIT_DATE + " = '" + visitdate + "'", null);

            int count = 0;
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    count++;
                    dbcursor.moveToNext();
                }
                dbcursor.close();

                if (count > 0) {
                    return true;
                } else {
                    return false;
                }

            }

        } catch (Exception e) {
            Log.d("Exception when fetching Visitor Login!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return false;
        }

        Log.d("FetchingVisitor Login---------------------->Stop<-----------",
                "-------------------");
        return false;

    }

    public long updateVisitorUploadData(String empid) {
        long l = 0;
        ContentValues values = new ContentValues();
        try {

            values.put(CommonString.KEY_UPLOADSTATUS, CommonString.KEY_U);
            l = db.update(CommonString.TABLE_VISITOR_LOGIN, values, CommonString.KEY_EMP_CD + "='" + empid + "'", null);

        } catch (Exception e) {
            Log.d("Exception updating Visitor Upload status Data!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());

        }
        return l;
    }

    public boolean insertMaster_Brand(MasterBrandGetterSetter masterBrandGetterSetter) {
        db.delete("Master_Brand", null, null);
        ContentValues values = new ContentValues();
        List<MasterBrand> data = masterBrandGetterSetter.getMasterBrand();
        try {
            if (data.size() == 0) {
                return false;
            }

            for (int i = 0; i < data.size(); i++) {

                values.put("BrandId", data.get(i).getBrandId());
                values.put("BrandName", data.get(i).getBrandName());
                values.put("CategoryId", data.get(i).getCategoryId());
                values.put("CompanyId", data.get(i).getCompanyId());
                values.put("BrandSequence", data.get(i).getBrandSequence());

                long id = db.insert("Master_Brand", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Database Exception  ", ex.toString());
            return false;
        }
    }


    public boolean insertMaster_Company(MasterBrandGetterSetter masterBrandGetterSetter) {
        db.delete("Master_Company", null, null);
        ContentValues values = new ContentValues();
        List<MasterCompany> data = masterBrandGetterSetter.getMasterCompany();
        try {
            if (data.size() == 0) {
                return false;
            }

            for (int i = 0; i < data.size(); i++) {
                values.put("CompanySequence", data.get(i).getCompanySequence());
                values.put("CompanyId", data.get(i).getCompanyId());
                values.put("Company", data.get(i).getCompany());
                values.put("IsCompetitor", data.get(i).getIsCompetitor());

                long id = db.insert("Master_Company", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Database Exception  ", ex.toString());
            return false;
        }
    }


    public boolean insertMaster_Checklist(MasterChecklistGetterSetter masterChecklistGetterSetter) {
        db.delete("Master_Checklist", null, null);
        ContentValues values = new ContentValues();
        List<MasterChecklist> data = masterChecklistGetterSetter.getMasterChecklist();
        try {
            if (data.size() == 0) {
                return false;
            }

            for (int i = 0; i < data.size(); i++) {

                values.put("ChecklistId", data.get(i).getChecklistId());
                values.put("ChecklistName", data.get(i).getChecklistName());
                values.put("AnswerType", data.get(i).getAnswerType());
                values.put("QuestionImageAllow1", data.get(i).getQuestionImageAllow1());
                values.put("QuestionImageAllow2", data.get(i).getQuestionImageAllow2());
                values.put("Barcode", data.get(i).getBarcode());
                values.put("ChecklistSequence", data.get(i).getChecklistSequence());

                values.put("QuestionImageLeble1", data.get(i).getQuestionImageLeble1());
                values.put("QuestionImageLeble2", data.get(i).getQuestionImageLeble2());
                values.put("QuestionGridAllow1", data.get(i).getQuestionGridAllow1());
                values.put("QuestionGridAllow2", data.get(i).getQuestionGridAllow2());


                long id = db.insert("Master_Checklist", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Database Exception  ", ex.toString());
            return false;
        }
    }


    public boolean insertMaster_Asset(MasterAssetGetterSetter masterAssetGetterSetter) {
        db.delete("Master_Asset", null, null);
        ContentValues values = new ContentValues();
        List<MasterAsset> data = masterAssetGetterSetter.getMasterAsset();
        try {
            if (data.size() == 0) {
                return false;
            }

            for (int i = 0; i < data.size(); i++) {

                values.put("AssetId", data.get(i).getAssetId());
                values.put("AssetName", data.get(i).getAssetName());

                long id = db.insert("Master_Asset", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Database Exception  ", ex.toString());
            return false;
        }
    }


    public boolean insertmappingpaidVisibility(MasterAssetGetterSetter masterAssetGetterSetter) {
        db.delete("Mapping_PaidVisibility", null, null);
        ContentValues values = new ContentValues();
        List<MappingPaidVisibility> data = masterAssetGetterSetter.getMappingPaidVisibility();
        try {
            if (data.size() == 0) {
                return false;
            }

            for (int i = 0; i < data.size(); i++) {

                values.put("AssetId", data.get(i).getAssetId());
                values.put("StoreId", data.get(i).getStoreId());
                values.put("BrandId", data.get(i).getBrandId());

                long id = db.insert("Mapping_PaidVisibility", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Database Exception  ", ex.toString());
            return false;
        }
    }


    public boolean insertnonAssetReason(MasterAssetGetterSetter masterAssetGetterSetter) {
        db.delete("Master_NonAssetReason", null, null);
        ContentValues values = new ContentValues();
        List<MasterNonAssetReason> data = masterAssetGetterSetter.getMasterNonAssetReason();
        try {
            if (data.size() == 0) {
                return false;
            }

            for (int i = 0; i < data.size(); i++) {

                values.put("ReasonId", data.get(i).getReasonId());
                values.put("Reason", data.get(i).getReason());

                long id = db.insert("Master_NonAssetReason", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Database Exception  ", ex.toString());
            return false;
        }
    }


    public boolean insertAssetLocation(MasterAssetGetterSetter masterAssetGetterSetter) {
        db.delete("Master_AssetLocation", null, null);
        ContentValues values = new ContentValues();
        List<MasterAssetLocation> data = masterAssetGetterSetter.getMasterAssetLocation();
        try {
            if (data.size() == 0) {
                return false;
            }

            for (int i = 0; i < data.size(); i++) {

                values.put("LocationId", data.get(i).getLocationId());
                values.put("LocationName", data.get(i).getLocationName());

                long id = db.insert("Master_AssetLocation", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Database Exception  ", ex.toString());
            return false;
        }
    }

    public boolean insertMapping_Posm(MappingPosmGetterSetter mappingPosmGetterSetter) {
        db.delete("Mapping_Posm", null, null);
        ContentValues values = new ContentValues();
        List<MappingPosm> data = mappingPosmGetterSetter.getMappingPosm();
        try {
            if (data.size() == 0) {
                return false;
            }

            for (int i = 0; i < data.size(); i++) {
                values.put("PosmId", data.get(i).getPosmId());
                values.put("StateId", data.get(i).getStateId());
                values.put("StoreCategoryId", data.get(i).getStoreCategoryId());
                values.put("StoreClassId", data.get(i).getStoreClassId());
                values.put("StoreTypeId", data.get(i).getStoreTypeId());
                long id = db.insert("Mapping_Posm", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Database Exception  ", ex.toString());
            return false;
        }
    }

    public ArrayList<MasterProgram> getmasterProgram(MappingJourneyPlan jcp) {
        Log.d("Fetchecklidata->Start<-", "-");
        ArrayList<MasterProgram> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {

            if (jcp != null) {
                dbcursor = db.rawQuery("Select Distinct t1.ProgramId,t1.ProgramName,t1.ImagePath,t1.ProgramNormalIcon,t1.ProgramTickIcon,t1.ProgramIconBaseColor" +
                        " From Master_Program t1 inner join Mapping_Program t2 on t1.SubProgramId=t2.SubProgramId Where t2.StoreId=" +
                        jcp.getStoreId() + " Order By t1.ProgramId", null);

            } else {
                dbcursor = db.rawQuery("Select * From Master_Program", null);

            }

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterProgram ch = new MasterProgram();
                    if (jcp != null) {
                        ch = new MasterProgram();
                        int programId = dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ProgramId"));
                        ch.setProgramName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ProgramName")));
                        ch.setImagePath(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ImagePath")));
                        ch.setProgramNormalIcon(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ProgramNormalIcon")));
                        ch.setProgramTickIcon(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ProgramTickIcon")));
                        ch.setProgramIconBaseColor(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ProgramIconBaseColor")));
                        ch.setProgramId(programId);
                        ch.setSubprogramList(getmasterSubProgram(jcp, programId));
                    } else {
                        ch = new MasterProgram();
                        ch.setProgramId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ProgramId")));
                        ch.setProgramName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ProgramName")));
                        ch.setImagePath(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ImagePath")));
                        ch.setProgramNormalIcon(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ProgramNormalIcon")));
                        ch.setProgramTickIcon(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ProgramTickIcon")));
                        ch.setSubProgramNormalIcon(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SubProgramNormalIcon")));
                        ch.setSubProgramTickIcon(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SubProgramTickIcon")));
                    }

                    list.add(ch);

                    dbcursor.moveToNext();
                }

                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            return list;
        }
        return list;
    }


    public ArrayList<MasterProgram> getmasterSubProgram(MappingJourneyPlan jcp, int programId) {
        Log.d("Fetchecklidata->Start<-", "-");
        ArrayList<MasterProgram> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("Select Distinct t1.SubProgramId,t1.SubProgramName,t1.SubProgramNormalIcon,t1.SubProgramTickIcon,t1.SubProgramIconBaseColor" +
                    " From Master_Program t1 inner join Mapping_Program t2 on t1.SubProgramId=t2.SubProgramId Where t2.StoreId=" + jcp.getStoreId()
                    + " and t1.ProgramId=" + programId + " Order By t1.SubProgramName", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterProgram ch = new MasterProgram();
                    ch.setSubProgramId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("SubProgramId")));
                    ch.setSubProgramName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SubProgramName")));

                    ch.setSubProgramNormalIcon(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SubProgramNormalIcon")));
                    ch.setSubProgramTickIcon(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SubProgramTickIcon")));
                    ch.setSubProgramIconBaseColor(dbcursor.getString(dbcursor.getColumnIndexOrThrow("SubProgramIconBaseColor")));
                    list.add(ch);
                    dbcursor.moveToNext();
                }

                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            return list;
        }
        return list;
    }


    public ArrayList<MasterChecklistAnswer> getchecklistans(int checklistId) {
        Log.d("Fetchecklidata->Start<-", "-");
        ArrayList<MasterChecklistAnswer> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("SELECT DISTINCT Answer,AnswerId,ImageAllow1,ImageAllow2,ImageLable1,ImageLable2,ImageGridAllow1,ImageGridAllow2" +
                    " from Master_ChecklistAnswer where ChecklistId=" + checklistId + "", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterChecklistAnswer ch = new MasterChecklistAnswer();
                    ch.setAnswerId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("AnswerId")));
                    ch.setAnswer(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Answer")));

                    ch.setImageLable1(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ImageLable1")));
                    ch.setImageLable2(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ImageLable2")));

                    int ImageGridAllow1 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ImageGridAllow1"));
                    if (ImageGridAllow1 == 1) {
                        ch.setImageGridAllow1(true);
                    } else {
                        ch.setImageGridAllow1(false);
                    }

                    int ImageGridAllow2 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ImageGridAllow2"));
                    if (ImageGridAllow2 == 1) {
                        ch.setImageGridAllow2(true);
                    } else {
                        ch.setImageGridAllow2(false);
                    }

                    int ImageAllow1 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ImageAllow1"));
                    if (ImageAllow1 == 1) {
                        ch.setImageAllow1(true);
                    } else {
                        ch.setImageAllow1(false);
                    }

                    int ImageAllow2 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ImageAllow2"));
                    if (ImageAllow2 == 1) {
                        ch.setImageAllow2(true);
                    } else {
                        ch.setImageAllow2(false);
                    }


                    list.add(ch);
                    dbcursor.moveToNext();
                }

                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            return list;
        }
        return list;
    }


    public MasterChecklistAnswer getchecklistansusingtext(int checklistId, String txt_btn) {
        Log.d("Fetchecklidata->Start<-", "-");
        MasterChecklistAnswer ch = new MasterChecklistAnswer();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("SELECT DISTINCT Answer,AnswerId,ImageAllow1,ImageAllow2,ImageLable1,ImageLable2,ImageGridAllow1,ImageGridAllow2" +
                    " from Master_ChecklistAnswer where ChecklistId=" + checklistId + " And Answer='" + txt_btn + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    ch.setAnswerId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("AnswerId")));
                    ch.setAnswer(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Answer")));
                    ch.setImageLable1(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ImageLable1")));
                    ch.setImageLable2(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ImageLable2")));
                    int ImageGridAllow1 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ImageGridAllow1"));
                    if (ImageGridAllow1 == 1) {
                        ch.setImageGridAllow1(true);
                    } else {
                        ch.setImageGridAllow1(false);
                    }

                    int ImageGridAllow2 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ImageGridAllow2"));
                    if (ImageGridAllow2 == 1) {
                        ch.setImageGridAllow2(true);
                    } else {
                        ch.setImageGridAllow2(false);
                    }

                    int ImageAllow1 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ImageAllow1"));
                    if (ImageAllow1 == 1) {
                        ch.setImageAllow1(true);
                    } else {
                        ch.setImageAllow1(false);
                    }

                    int ImageAllow2 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ImageAllow2"));
                    if (ImageAllow2 == 1) {
                        ch.setImageAllow2(true);
                    } else {
                        ch.setImageAllow2(false);
                    }


                    dbcursor.moveToNext();
                }

                dbcursor.close();
                return ch;
            }
        } catch (Exception e) {
            return ch;
        }
        return ch;
    }


    public ArrayList<MasterChecklist> getmasterChecklist(MappingJourneyPlan jcp, MasterProgram current, boolean for_visicooler) {
        Log.d("Fetchecklidata->Start<-", "-");
        ArrayList<MasterChecklist> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {

            if (for_visicooler) {
                dbcursor = db.rawQuery("Select Distinct t1.VCId,t1.VCCategoryId,(t1.VisicoolerCategory || ' - ' || t1.Make|| ' ' || t1.VCType) as VisicoolerCategory," +
                        "t3.ChecklistId,t3.ChecklistName, t3.AnswerType,t3.QuestionImageAllow1,t3.QuestionImageAllow2,t3.Barcode," +
                        "t3.QuestionImageLeble1,t3.QuestionImageLeble2,t3.QuestionGridAllow1,t3.QuestionGridAllow2 From Mapping_Visicooler t1" +
                        " inner join Mapping_VisicoolerChecklist t2 on t1.VCCategoryId=t2.VCCategoryId" +
                        " inner join Master_Checklist t3 on t3.ChecklistId=t2.ChecklistId where t1.StoreId =" + jcp.getStoreId() +
                        " Order By t1.VCCategoryId,t3.ChecklistSequence", null);

            } else {

                dbcursor = db.rawQuery("SELECT DISTINCT t1.ChecklistId,t1.ChecklistName, t1.AnswerType,t1.QuestionImageAllow1," +
                        "t1.QuestionImageAllow2,t1.Barcode,t1.QuestionImageLeble1,t1.QuestionImageLeble2,t1.QuestionGridAllow1,t1.QuestionGridAllow2 from" +
                        " Master_Checklist t1 inner join Mapping_SubProgramChecklist t2 on t1.ChecklistId=t2.ChecklistId where t2.SubProgramId="
                        + current.getSubProgramId() + " Order By t1.ChecklistSequence", null);

            }

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterChecklist ch = new MasterChecklist();
                    if (for_visicooler) {
                        ch.setVVcId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("VCId")));
                        ch.setVisicooler_categoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("VCCategoryId")));
                        ch.setVisicooler_category(dbcursor.getString(dbcursor.getColumnIndexOrThrow("VisicoolerCategory")));
                    }

                    int checklistId = dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ChecklistId"));
                    ch.setChecklistName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ChecklistName")));
                    ch.setAnswerType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("AnswerType")));
                    ch.setQuestionImageLeble1(dbcursor.getString(dbcursor.getColumnIndexOrThrow("QuestionImageLeble1")));
                    ch.setQuestionImageLeble2(dbcursor.getString(dbcursor.getColumnIndexOrThrow("QuestionImageLeble2")));

                    int QuestionGridAllow1 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow("QuestionGridAllow1"));
                    if (QuestionGridAllow1 == 1) {
                        ch.setQuestionGridAllow1(true);
                    } else {
                        ch.setQuestionGridAllow1(false);
                    }

                    int QuestionGridAllow2 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow("QuestionGridAllow2"));
                    if (QuestionGridAllow2 == 1) {
                        ch.setQuestionGridAllow2(true);
                    } else {
                        ch.setQuestionGridAllow2(false);
                    }

                    int quesimg1 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow("QuestionImageAllow1"));
                    ch.setQuestionImageAllow1(quesimg1);
                    int qustimg2 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow("QuestionImageAllow2"));
                    if (qustimg2 == 1) {
                        ch.setQuestionImageAllow2(true);
                    } else {
                        ch.setQuestionImageAllow2(false);
                    }

                    int Barcode = dbcursor.getInt(dbcursor.getColumnIndexOrThrow("Barcode"));
                    if (Barcode == 1) {
                        ch.setBarcode(true);
                    } else {
                        ch.setBarcode(false);
                    }


                    ch.setChecklistId(checklistId);
                    ch.setImageAllow1(false);
                    ch.setImageAllow2(false);
                    ch.setChecklist_img1("");
                    ch.setChecklist_img2("");
                    ch.setAnswer("");
                    ch.setAnsId(0);
                    ch.setCheckedId(-1);
                    ch.setEdt_text_value("");
                    ch.setBinary_btn_value("");
                    ch.setQuestionImage1("");
                    ch.setQuestionImage2("");
                    ////new changesddd
                    ch.setChecklistImglevel1("");
                    ch.setChecklistImglevel2("");
                    ch.setChecklistimgGridAllow1(false);
                    ch.setChecklistimgGridAllow2(false);
                    ch.setAnswrs(getchecklistans(checklistId));

                    list.add(ch);
                    dbcursor.moveToNext();
                }

                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            return list;
        }
        return list;
    }

    public boolean IsSubProgramChecklistfilled(MappingJourneyPlan jcp, MasterProgram program, boolean for_visicooler_flag) {
        boolean filled = false;
        Cursor dbcursor = null;
        try {

            if (for_visicooler_flag) {
                dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_VISICOOLER_PRESENT + " WHERE " + CommonString.KEY_STORE_ID + "=" + jcp.getStoreId() +
                        " AND " + CommonString.KEY_VISIT_DATE + "='" + jcp.getVisitDate() + "'", null);
            } else {
                dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_PROGRAM_CHECKLIST_PRESENT + " WHERE " + CommonString.KEY_STORE_ID + "=" + jcp.getStoreId() +
                        " AND " + CommonString.KEY_VISIT_DATE + "='" + jcp.getVisitDate() + "' AND " + CommonString.KEY_SUB_PROGRAM_ID + "=" + program.getSubProgramId() + "", null);
            }

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                int icount = dbcursor.getInt(0);
                dbcursor.close();
                if (icount > 0) {
                    filled = true;
                } else {
                    filled = false;
                }
            }
        } catch (Exception e) {
            return filled;
        }
        return filled;
    }


    public boolean IsPosmfilled(MappingJourneyPlan jcp) {
        boolean filled = false;
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_POSM_PRESENT + " WHERE " + CommonString.KEY_STORE_ID + "=" + jcp.getStoreId() +
                    " AND " + CommonString.KEY_VISIT_DATE + "='" + jcp.getVisitDate() + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                int icount = dbcursor.getInt(0);
                dbcursor.close();
                if (icount > 0) {
                    filled = true;
                } else {
                    filled = false;
                }
            }
        } catch (Exception e) {
            return filled;
        }
        return filled;
    }


    public boolean VisicoolerCheckMapping(MappingJourneyPlan journeyPlan) {
        boolean filled = false;
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("SELECT * from Mapping_Visicooler Where StoreId=" + journeyPlan.getStoreId() + "", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                int icount = dbcursor.getInt(0);
                dbcursor.close();
                if (icount > 0) {
                    filled = true;
                } else {
                    filled = false;
                }
            }
        } catch (Exception e) {
            return filled;
        }
        return filled;
    }


    public ArrayList<MasterChecklist> getinsertedsubprogramChecklist(MappingJourneyPlan jcp, MasterProgram object) {
        ArrayList<MasterChecklist> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("select * From " + CommonString.TABLE_PROGRAM_CHECKLIST + " where " + CommonString.KEY_STORE_ID + "=" + jcp.getStoreId() +
                    " AND " + CommonString.KEY_VISIT_DATE + "='" + jcp.getVisitDate() + "' AND " + CommonString.KEY_SUB_PROGRAM_ID + "=" + object.getSubProgramId() + "", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterChecklist ch = new MasterChecklist();
                    int checklistId = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CHECKLIST_ID));
                    ch.setChecklistName(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CHECKLIST)));
                    ch.setAnswerType(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ANSWER_TYPE)));
                    ch.setQuestionImageAllow1(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QUESTION_IMAGEALLOW1)));
                    ch.setQuestionImageLeble1(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QUESTION_IMAGE_LEVEL1)));

                    int QuestionGridAllow1 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QUESTION_GRID1));
                    if (QuestionGridAllow1 == 1) {
                        ch.setQuestionGridAllow1(true);
                    } else {
                        ch.setQuestionGridAllow1(false);
                    }

                    int QuestionGridAllow2 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QUESTION_GRID2));
                    if (QuestionGridAllow2 == 1) {
                        ch.setQuestionGridAllow2(true);
                    } else {
                        ch.setQuestionGridAllow2(false);
                    }


                    ch.setQuestionImageLeble2(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QUESTION_IMAGE2_LEVEL2)));
                    ch.setChecklistImglevel1(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE_LEVEL1)));
                    ch.setChecklistImglevel2(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE_LEVEL2)));

                    int checklistgrid1 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE_GRID1));
                    if (checklistgrid1 == 1) {
                        ch.setChecklistimgGridAllow1(true);
                    } else {
                        ch.setChecklistimgGridAllow1(false);
                    }

                    int checklistgrid2 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE_GRID2));
                    if (checklistgrid2 == 1) {
                        ch.setChecklistimgGridAllow2(true);
                    } else {
                        ch.setChecklistimgGridAllow2(false);
                    }

                    int qimg2 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QUESTION_IMAGEALLOW2));
                    if (qimg2 == 1) {
                        ch.setQuestionImageAllow2(true);
                    } else {
                        ch.setQuestionImageAllow2(false);
                    }

                    ch.setQuestionImage1(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QUESTION_IMAGE1)));
                    ch.setQuestionImage2(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QUESTION_IMAGE2)));


                    ch.setChecklistId(checklistId);
                    int imageallow1 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE1_FLAG));
                    if (imageallow1 == 1) {
                        ch.setImageAllow1(true);
                    } else {
                        ch.setImageAllow1(false);
                    }

                    int imageallow2 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE2_FLAG));
                    if (imageallow2 == 1) {
                        ch.setImageAllow2(true);
                    } else {
                        ch.setImageAllow2(false);
                    }

                    ch.setChecklist_img1(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE1)));
                    ch.setChecklist_img2(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE2)));
                    ch.setAnswer(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ANSWER)));
                    ch.setAnsId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ANSWER_CD)));
                    ch.setCheckedId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CHECKED_iD)));
                    ch.setEdt_text_value(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_EDIT_VALUE)));
                    ch.setBinary_btn_value(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BINARY_VALUE)));
                    ch.setBinary_btn_ansId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BINARY_ANS_iD)));
                    ch.setAnswrs(getchecklistans(checklistId));

                    list.add(ch);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {

            return list;
        }
        return list;
    }


    public ArrayList<MasterChecklist> getinsertedVisicoolerChecklist(MappingJourneyPlan jcp) {
        ArrayList<MasterChecklist> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("select * From " + CommonString.TABLE_VISICOOLER_CHECKLIST + " where " + CommonString.KEY_STORE_ID + "=" + jcp.getStoreId() +
                    " AND " + CommonString.KEY_VISIT_DATE + "='" +
                    jcp.getVisitDate() + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterChecklist ch = new MasterChecklist();
                    ch.setVVcId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_VCiD)));
                    ch.setVisicooler_category(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CATEGORY)));
                    ch.setVisicooler_categoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CATEGORY_ID)));
                    int checklistId = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CHECKLIST_ID));
                    ch.setChecklistName(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CHECKLIST)));
                    ch.setAnswerType(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ANSWER_TYPE)));
                    ch.setQuestionImageAllow1(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QUESTION_IMAGEALLOW1)));
                    ch.setQuestionImageLeble1(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QUESTION_IMAGE_LEVEL1)));

                    int QuestionGridAllow1 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QUESTION_GRID1));
                    if (QuestionGridAllow1 == 1) {
                        ch.setQuestionGridAllow1(true);
                    } else {
                        ch.setQuestionGridAllow1(false);
                    }

                    int QuestionGridAllow2 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QUESTION_GRID2));
                    if (QuestionGridAllow2 == 1) {
                        ch.setQuestionGridAllow2(true);
                    } else {
                        ch.setQuestionGridAllow2(false);
                    }

                    ch.setQuestionImageLeble2(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QUESTION_IMAGE2_LEVEL2)));
                    ch.setChecklistImglevel1(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE_LEVEL1)));
                    ch.setChecklistImglevel2(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE_LEVEL2)));


                    int checklistgrid1 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE_GRID1));
                    if (checklistgrid1 == 1) {
                        ch.setChecklistimgGridAllow1(true);
                    } else {
                        ch.setChecklistimgGridAllow1(false);
                    }

                    int checklistgrid2 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE_GRID2));
                    if (checklistgrid2 == 1) {
                        ch.setChecklistimgGridAllow2(true);
                    } else {
                        ch.setChecklistimgGridAllow2(false);
                    }

                    int qimg2 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QUESTION_IMAGEALLOW2));
                    if (qimg2 == 1) {
                        ch.setQuestionImageAllow2(true);
                    } else {
                        ch.setQuestionImageAllow2(false);
                    }

                    int barcode = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BARSCAN_FLAG));
                    if (barcode == 1) {
                        ch.setBarcode(true);
                    } else {
                        ch.setBarcode(false);
                    }

                    ch.setQuestionImage1(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QUESTION_IMAGE1)));
                    ch.setQuestionImage2(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QUESTION_IMAGE2)));

                    ch.setChecklistId(checklistId);
                    int imageallow1 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE1_FLAG));
                    if (imageallow1 == 1) {
                        ch.setImageAllow1(true);
                    } else {
                        ch.setImageAllow1(false);
                    }

                    int imageallow2 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE2_FLAG));
                    if (imageallow2 == 1) {
                        ch.setImageAllow2(true);
                    } else {
                        ch.setImageAllow2(false);
                    }

                    ch.setChecklist_img1(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE1)));
                    ch.setChecklist_img2(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE2)));
                    ch.setAnswer(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ANSWER)));
                    ch.setAnsId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ANSWER_CD)));
                    ch.setCheckedId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CHECKED_iD)));
                    ch.setEdt_text_value(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_EDIT_VALUE)));
                    ch.setBinary_btn_value(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BINARY_VALUE)));
                    ch.setBinary_btn_ansId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BINARY_ANS_iD)));
                    ch.setAnswrs(getchecklistans(checklistId));

                    list.add(ch);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {

            return list;
        }
        return list;
    }


    public ArrayList<MasterPosm> getinsertedposm(MappingJourneyPlan jcp) {
        ArrayList<MasterPosm> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("select * From " + CommonString.TABLE_POSM + " where " + CommonString.KEY_STORE_ID + "=" + jcp.getStoreId() +
                    " AND " + CommonString.KEY_VISIT_DATE + "='" + jcp.getVisitDate() + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterPosm ch = new MasterPosm();
                    ch.setPosmName(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_POSM)));
                    ch.setPosmId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_POSM_ID)));
                    ch.setRefImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REFIMAGE)));
                    ch.setPosm_yesorno(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_POSM_AVAILEBILITY)));
                    ch.setPosm_img(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE)));
                    ch.setReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON)));
                    ch.setReasonId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON_ID)));
                    ch.setReasonArrayList(getPOSMReason());
                    list.add(ch);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            return list;
        }

        return list;
    }

    public ArrayList<MasterPosm> getinsertedposmforupload(String storeId, String visit_date) {
        ArrayList<MasterPosm> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("select * From " + CommonString.TABLE_POSM + " where " + CommonString.KEY_STORE_ID + "=" + storeId +
                    " AND " + CommonString.KEY_VISIT_DATE + "='" + visit_date + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterPosm ch = new MasterPosm();
                    ch.setPosmName(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_POSM)));
                    ch.setPosmId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_POSM_ID)));
                    ch.setRefImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REFIMAGE)));
                    ch.setPosm_yesorno(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_POSM_AVAILEBILITY)));
                    ch.setPosm_img(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE)));
                    ch.setReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON)));
                    ch.setReasonId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON_ID)));
                    ch.setReasonArrayList(getPOSMReason());
                    list.add(ch);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {

            return list;
        }
        return list;
    }


    public ArrayList<MasterChecklist> getVisicoolerChecklistforupload(String storeId, String visit_date) {
        ArrayList<MasterChecklist> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("select * From " + CommonString.TABLE_VISICOOLER_CHECKLIST + " where " + CommonString.KEY_STORE_ID + "=" + storeId +
                    " AND " + CommonString.KEY_VISIT_DATE + "='" + visit_date + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterChecklist ch = new MasterChecklist();
                    ch.setVVcId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_VCiD)));
                    ch.setVisicooler_category(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CATEGORY)));
                    ch.setVisicooler_categoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CATEGORY_ID)));
                    int checklistId = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CHECKLIST_ID));
                    ch.setChecklistName(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CHECKLIST)));
                    ch.setAnswerType(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ANSWER_TYPE)));
                    ch.setQuestionImageAllow1(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QUESTION_IMAGEALLOW1)));

                    int qimg2 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QUESTION_IMAGEALLOW2));
                    if (qimg2 == 1) {
                        ch.setQuestionImageAllow2(true);
                    } else {
                        ch.setQuestionImageAllow2(false);
                    }

                    int barcode = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BARSCAN_FLAG));
                    if (barcode == 1) {
                        ch.setBarcode(true);
                    } else {
                        ch.setBarcode(false);
                    }

                    ch.setQuestionImage1(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QUESTION_IMAGE1)));
                    ch.setQuestionImage2(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QUESTION_IMAGE2)));

                    ch.setChecklistId(checklistId);
                    int imageallow1 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE1_FLAG));
                    if (imageallow1 == 1) {
                        ch.setImageAllow1(true);
                    } else {
                        ch.setImageAllow1(false);
                    }

                    int imageallow2 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE2_FLAG));
                    if (imageallow2 == 1) {
                        ch.setImageAllow2(true);
                    } else {
                        ch.setImageAllow2(false);
                    }

                    ch.setChecklist_img1(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE1)));
                    ch.setChecklist_img2(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE2)));
                    ch.setAnswer(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ANSWER)));
                    ch.setAnsId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ANSWER_CD)));
                    ch.setCheckedId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CHECKED_iD)));
                    ch.setEdt_text_value(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_EDIT_VALUE)));
                    ch.setBinary_btn_value(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BINARY_VALUE)));
                    ch.setBinary_btn_ansId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BINARY_ANS_iD)));

                    list.add(ch);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {

            return list;
        }
        return list;
    }

    public MasterChecklist getsubprogramChecklistpresent(MappingJourneyPlan jcp, MasterProgram object, boolean flag_forvisicooler) {
        MasterChecklist list = new MasterChecklist();
        Cursor dbcursor = null;
        try {

            if (flag_forvisicooler) {
                dbcursor = db.rawQuery("select * From " + CommonString.TABLE_VISICOOLER_PRESENT + " where " + CommonString.KEY_STORE_ID + "=" + jcp.getStoreId() +
                        " AND " + CommonString.KEY_VISIT_DATE + "='" + jcp.getVisitDate() + "'", null);

            } else {
                dbcursor = db.rawQuery("select * From " + CommonString.TABLE_PROGRAM_CHECKLIST_PRESENT + " where " + CommonString.KEY_STORE_ID + "=" + jcp.getStoreId() +
                        " AND " + CommonString.KEY_VISIT_DATE + "='" + jcp.getVisitDate() + "' AND " + CommonString.KEY_SUB_PROGRAM_ID + "=" + object.getSubProgramId() + "", null);

            }

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {

                    list.setIschecked(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PRESENT)));

                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {

            return list;
        }
        return list;
    }



    public MasterChecklist getsubprogramChecklistpresentResionNew(MappingJourneyPlan jcp, MasterProgram object, boolean flag_forvisicooler) {
        MasterChecklist list = new MasterChecklist();
        Cursor dbcursor = null;
        try {

            if (flag_forvisicooler) {
                dbcursor = db.rawQuery("select * From " + CommonString.TABLE_VISICOOLER_PRESENT + " where " + CommonString.KEY_STORE_ID + "=" + jcp.getStoreId() +
                        " AND " + CommonString.KEY_VISIT_DATE + "='" + jcp.getVisitDate() + "'", null);

            } else {
                dbcursor = db.rawQuery("select * From " + CommonString.TABLE_PROGRAM_CHECKLIST_PRESENT + " where " + CommonString.KEY_STORE_ID + "=" + jcp.getStoreId() +
                        " AND " + CommonString.KEY_VISIT_DATE + "='" + jcp.getVisitDate() + "' AND " + CommonString.KEY_SUB_PROGRAM_ID + "=" + object.getSubProgramId() + "", null);

            }

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {

                    list.setIschecked(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PRESENT)));
                    list.setResion_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON_ID)));
                    list.setResion_name(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON)));

                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {

            return list;
        }
        return list;
    }

    public MasterPosm getinsertedposmpresent(MappingJourneyPlan jcp) {
        MasterPosm list = new MasterPosm();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("select * From " + CommonString.TABLE_POSM_PRESENT + " where " + CommonString.KEY_STORE_ID + "=" + jcp.getStoreId() +
                    " AND " + CommonString.KEY_VISIT_DATE + "='" + jcp.getVisitDate() + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {

                    list.setIspresnt(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PRESENT)));

                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {

            return list;
        }
        return list;
    }


    public MasterPosm getinsertedposmpresentforupload(String storeId, String visit_date) {
        MasterPosm list = new MasterPosm();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("select * From " + CommonString.TABLE_POSM_PRESENT + " where " + CommonString.KEY_STORE_ID + "=" + storeId +
                    " AND " + CommonString.KEY_VISIT_DATE + "='" + visit_date + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {

                    list.setIspresnt(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PRESENT)));

                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {

            return list;
        }
        return list;
    }


    public ArrayList<MasterChecklist> getsubprogramChecklistpresentforupload(String storeId, String visit_date, boolean flag_forvisicooler) {
        ArrayList<MasterChecklist> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {

            if (flag_forvisicooler) {
                dbcursor = db.rawQuery("select * From " + CommonString.TABLE_VISICOOLER_PRESENT + " where " + CommonString.KEY_STORE_ID + "=" + storeId +
                        " AND " + CommonString.KEY_VISIT_DATE + "='" + visit_date + "'", null);
            } else {
                dbcursor = db.rawQuery("select * From " + CommonString.TABLE_PROGRAM_CHECKLIST_PRESENT + " where " + CommonString.KEY_STORE_ID + "=" + storeId +
                        " AND " + CommonString.KEY_VISIT_DATE + "='" + visit_date + "'", null);

            }

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterChecklist sb = new MasterChecklist();
                    sb.setIschecked(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PRESENT)));
                    sb.setKeyId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ID)));
                    if (flag_forvisicooler == false) {
                        sb.setSubprogramId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SUB_PROGRAM_ID)));
                    }

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            return list;
        }

        return list;
    }


    public ArrayList<MasterChecklist> getsubprogramChecklistpresentforuploadResionNew(String storeId, String visit_date, boolean flag_forvisicooler) {
        ArrayList<MasterChecklist> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {

            if (flag_forvisicooler) {
                dbcursor = db.rawQuery("select * From " + CommonString.TABLE_VISICOOLER_PRESENT + " where " + CommonString.KEY_STORE_ID + "=" + storeId +
                        " AND " + CommonString.KEY_VISIT_DATE + "='" + visit_date + "'", null);
            } else {
                dbcursor = db.rawQuery("select * From " + CommonString.TABLE_PROGRAM_CHECKLIST_PRESENT + " where " + CommonString.KEY_STORE_ID + "=" + storeId +
                        " AND " + CommonString.KEY_VISIT_DATE + "='" + visit_date + "'", null);

            }

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterChecklist sb = new MasterChecklist();
                    sb.setIschecked(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PRESENT)));
                    sb.setResion_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON_ID)));
                    sb.setResion_name(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON)));
                    sb.setKeyId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ID)));
                    if (flag_forvisicooler == false) {
                        sb.setSubprogramId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SUB_PROGRAM_ID)));
                    }

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            return list;
        }

        return list;
    }

    public ArrayList<MasterChecklist> getsubprogramChecklistforupload(String storeId, String visit_date, int subprogramId) {
        ArrayList<MasterChecklist> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("select * From " + CommonString.TABLE_PROGRAM_CHECKLIST + " where " + CommonString.KEY_STORE_ID + "=" + storeId +
                    " AND " + CommonString.KEY_VISIT_DATE + "='" + visit_date + "' AND " + CommonString.KEY_SUB_PROGRAM_ID + "=" + subprogramId + "", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterChecklist ch = new MasterChecklist();
                    int checklistId = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CHECKLIST_ID));
                    ch.setChecklistName(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CHECKLIST)));
                    ch.setAnswerType(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ANSWER_TYPE)));
                    ch.setQuestionImageAllow1(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QUESTION_IMAGEALLOW1)));
                    int qimg2 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QUESTION_IMAGEALLOW2));
                    if (qimg2 == 1) {
                        ch.setQuestionImageAllow2(true);
                    } else {
                        ch.setQuestionImageAllow2(false);
                    }

                    ch.setQuestionImage1(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QUESTION_IMAGE1)));
                    ch.setQuestionImage2(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_QUESTION_IMAGE2)));


                    ch.setChecklistId(checklistId);
                    int imageallow1 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE1_FLAG));
                    if (imageallow1 == 1) {
                        ch.setImageAllow1(true);
                    } else {
                        ch.setImageAllow1(false);
                    }

                    int imageallow2 = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE2_FLAG));
                    if (imageallow2 == 1) {
                        ch.setImageAllow2(true);
                    } else {
                        ch.setImageAllow2(false);
                    }

                    ch.setChecklist_img1(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE1)));
                    ch.setChecklist_img2(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE2)));
                    ch.setAnswer(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ANSWER)));
                    ch.setAnsId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ANSWER_CD)));
                    ch.setCheckedId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CHECKED_iD)));
                    ch.setEdt_text_value(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_EDIT_VALUE)));
                    ch.setBinary_btn_value(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BINARY_VALUE)));
                    ch.setBinary_btn_ansId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BINARY_ANS_iD)));

                    list.add(ch);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {

            return list;
        }
        return list;
    }


    public long InsertSubprogramChecklist(MappingJourneyPlan jcp, MasterProgram current_object, String Ispresent, ArrayList<MasterChecklist> listDataChild,String resionName,String resionId) {
        long l = 0, common_id = 0;
        db.delete(CommonString.TABLE_PROGRAM_CHECKLIST_PRESENT, CommonString.KEY_STORE_ID + "=" + jcp.getStoreId() +
                " AND " + CommonString.KEY_VISIT_DATE + "='" + jcp.getVisitDate() + "' AND " + CommonString.KEY_SUB_PROGRAM_ID + "="
                + current_object.getSubProgramId() + "", null);
        db.delete(CommonString.TABLE_PROGRAM_CHECKLIST, CommonString.KEY_STORE_ID + "=" + jcp.getStoreId() +
                " AND " + CommonString.KEY_VISIT_DATE + "='" + jcp.getVisitDate() + "' AND " + CommonString.KEY_SUB_PROGRAM_ID + "="
                + current_object.getSubProgramId() + "", null);
        ContentValues values = null;

        try {
            values = new ContentValues();
            values.put(CommonString.KEY_VISIT_DATE, jcp.getVisitDate());
            values.put(CommonString.KEY_STORE_ID, jcp.getStoreId());
            values.put(CommonString.KEY_PRESENT, Ispresent);
            values.put(CommonString.KEY_SUB_PROGRAM_ID, current_object.getSubProgramId());
            values.put(CommonString.KEY_SUB_PROGRAM, current_object.getSubProgramName());
            values.put(CommonString.KEY_REASON_ID, resionId);
            values.put(CommonString.KEY_REASON, resionName);

            common_id = db.insert(CommonString.TABLE_PROGRAM_CHECKLIST_PRESENT, null, values);

            if (!Ispresent.equals("") && Ispresent.equals("1")) {
                if (listDataChild.size() > 0) {
                    for (int i = 0; i < listDataChild.size(); i++) {
                        values = new ContentValues();
                        values.put(CommonString.KEY_COMMON_ID, (int) common_id);
                        values.put(CommonString.KEY_VISIT_DATE, jcp.getVisitDate());
                        values.put(CommonString.KEY_STORE_ID, jcp.getStoreId());
                        values.put(CommonString.KEY_SUB_PROGRAM_ID, current_object.getSubProgramId());
                        values.put(CommonString.KEY_SUB_PROGRAM, current_object.getSubProgramName());
                        values.put(CommonString.KEY_CHECKLIST_ID, listDataChild.get(i).getChecklistId());
                        values.put(CommonString.KEY_CHECKLIST, listDataChild.get(i).getChecklistName());

                        values.put(CommonString.KEY_QUESTION_IMAGEALLOW1, listDataChild.get(i).getQuestionImageAllow1());
                        if (listDataChild.get(i).getQuestionImageAllow2()) {
                            values.put(CommonString.KEY_QUESTION_IMAGEALLOW2, 1);
                        } else {
                            values.put(CommonString.KEY_QUESTION_IMAGEALLOW2, 0);
                        }

                        values.put(CommonString.KEY_QUESTION_IMAGE1, listDataChild.get(i).getQuestionImage1());
                        values.put(CommonString.KEY_QUESTION_IMAGE2, listDataChild.get(i).getQuestionImage2());

                        values.put(CommonString.KEY_QUESTION_IMAGE_LEVEL1, listDataChild.get(i).getQuestionImageLeble1());
                        values.put(CommonString.KEY_QUESTION_IMAGE2_LEVEL2, listDataChild.get(i).getQuestionImageLeble2());

                        if (listDataChild.get(i).getQuestionGridAllow1()) {
                            values.put(CommonString.KEY_QUESTION_GRID1, 1);
                        } else {
                            values.put(CommonString.KEY_QUESTION_GRID1, 0);
                        }

                        if (listDataChild.get(i).getQuestionGridAllow2()) {
                            values.put(CommonString.KEY_QUESTION_GRID2, 1);
                        } else {
                            values.put(CommonString.KEY_QUESTION_GRID2, 0);
                        }

                        values.put(CommonString.KEY_IMAGE_LEVEL1, listDataChild.get(i).getChecklistImglevel1());
                        values.put(CommonString.KEY_IMAGE_LEVEL2, listDataChild.get(i).getChecklistImglevel2());

                        if (listDataChild.get(i).getChecklistimgGridAllow1()) {
                            values.put(CommonString.KEY_IMAGE_GRID1, 1);
                        } else {
                            values.put(CommonString.KEY_IMAGE_GRID1, 0);
                        }

                        if (listDataChild.get(i).getChecklistimgGridAllow2()) {
                            values.put(CommonString.KEY_IMAGE_GRID2, 1);
                        } else {
                            values.put(CommonString.KEY_IMAGE_GRID2, 0);
                        }

                        String answerType = listDataChild.get(i).getAnswerType();
                        values.put(CommonString.KEY_ANSWER_TYPE, answerType);
                        if (answerType.equalsIgnoreCase("Binary")) {
                            values.put(CommonString.KEY_BINARY_VALUE, listDataChild.get(i).getBinary_btn_value());
                            values.put(CommonString.KEY_BINARY_ANS_iD, listDataChild.get(i).getBinary_btn_ansId());
                            if (listDataChild.get(i).getImageAllow1()) {
                                values.put(CommonString.KEY_IMAGE1_FLAG, 1);
                            } else {
                                values.put(CommonString.KEY_IMAGE1_FLAG, 0);
                            }
                            if (listDataChild.get(i).getImageAllow2()) {
                                values.put(CommonString.KEY_IMAGE2_FLAG, 1);
                            } else {
                                values.put(CommonString.KEY_IMAGE2_FLAG, 0);
                            }

                            values.put(CommonString.KEY_IMAGE1, listDataChild.get(i).getChecklist_img1());
                            values.put(CommonString.KEY_IMAGE2, listDataChild.get(i).getChecklist_img2());
                            values.put(CommonString.KEY_ANSWER, "");
                            values.put(CommonString.KEY_ANSWER_CD, 0);
                            values.put(CommonString.KEY_EDIT_VALUE, "");
                            values.put(CommonString.KEY_CHECKED_iD, -1);
                        } else if (answerType.equalsIgnoreCase("Single Choice List")) {
                            values.put(CommonString.KEY_BINARY_VALUE, "");
                            values.put(CommonString.KEY_BINARY_ANS_iD, 0);
                            values.put(CommonString.KEY_ANSWER, listDataChild.get(i).getAnswer());
                            values.put(CommonString.KEY_ANSWER_CD, listDataChild.get(i).getAnsId());
                            if (listDataChild.get(i).getImageAllow1()) {
                                values.put(CommonString.KEY_IMAGE1_FLAG, 1);
                            } else {
                                values.put(CommonString.KEY_IMAGE1_FLAG, 0);
                            }

                            if (listDataChild.get(i).getImageAllow2()) {
                                values.put(CommonString.KEY_IMAGE2_FLAG, 1);
                            } else {
                                values.put(CommonString.KEY_IMAGE2_FLAG, 0);
                            }

                            values.put(CommonString.KEY_IMAGE1, listDataChild.get(i).getChecklist_img1());
                            values.put(CommonString.KEY_IMAGE2, listDataChild.get(i).getChecklist_img2());
                            values.put(CommonString.KEY_EDIT_VALUE, "");
                            values.put(CommonString.KEY_CHECKED_iD, listDataChild.get(i).getCheckedId());
                        } else if (answerType.equalsIgnoreCase("Text") || answerType.equalsIgnoreCase("Number")) {
                            values.put(CommonString.KEY_BINARY_VALUE, "");
                            values.put(CommonString.KEY_BINARY_ANS_iD, 0);
                            values.put(CommonString.KEY_ANSWER, "");
                            values.put(CommonString.KEY_ANSWER_CD, 0);
                            values.put(CommonString.KEY_IMAGE1_FLAG, 0);
                            values.put(CommonString.KEY_IMAGE1, "");
                            values.put(CommonString.KEY_IMAGE2_FLAG, 0);
                            values.put(CommonString.KEY_IMAGE2, "");
                            values.put(CommonString.KEY_EDIT_VALUE, listDataChild.get(i).getEdt_text_value());
                            values.put(CommonString.KEY_CHECKED_iD, -1);
                        } else if (answerType.equalsIgnoreCase("Image")) {
                            values.put(CommonString.KEY_BINARY_VALUE, "");
                            values.put(CommonString.KEY_BINARY_ANS_iD, 0);
                            values.put(CommonString.KEY_ANSWER, "");
                            values.put(CommonString.KEY_ANSWER_CD, 0);
                            values.put(CommonString.KEY_IMAGE1_FLAG, 0);
                            values.put(CommonString.KEY_IMAGE1, "");
                            values.put(CommonString.KEY_IMAGE2_FLAG, 0);
                            values.put(CommonString.KEY_IMAGE2, "");
                            values.put(CommonString.KEY_EDIT_VALUE, "");
                            values.put(CommonString.KEY_CHECKED_iD, -1);
                        }

                        l = db.insert(CommonString.TABLE_PROGRAM_CHECKLIST, null, values);

                    }
                }
            }

            if (common_id > 0) {
                return common_id;
            } else {
                return 0;
            }
        } catch (Exception ex) {
            return 0;
        }
    }


    public long InsertVisicooler(MappingJourneyPlan jcp, String Ispresent, ArrayList<MasterChecklist> listDataChild) {
        long l = 0, common_id = 0;
        db.delete(CommonString.TABLE_VISICOOLER_PRESENT, CommonString.KEY_STORE_ID + "=" + jcp.getStoreId() +
                " AND " + CommonString.KEY_VISIT_DATE + "='" + jcp.getVisitDate() + "'", null);
        db.delete(CommonString.TABLE_VISICOOLER_CHECKLIST, CommonString.KEY_STORE_ID + "=" + jcp.getStoreId() +
                " AND " + CommonString.KEY_VISIT_DATE + "='" + jcp.getVisitDate() + "'", null);
        ContentValues values = null;

        try {
            values = new ContentValues();
            values.put(CommonString.KEY_VISIT_DATE, jcp.getVisitDate());
            values.put(CommonString.KEY_STORE_ID, jcp.getStoreId());
            values.put(CommonString.KEY_PRESENT, Ispresent);

            common_id = db.insert(CommonString.TABLE_VISICOOLER_PRESENT, null, values);

            if (!Ispresent.equals("") && Ispresent.equals("1")) {
                if (listDataChild.size() > 0) {
                    for (int i = 0; i < listDataChild.size(); i++) {
                        values = new ContentValues();
                        values.put(CommonString.KEY_COMMON_ID, (int) common_id);
                        values.put(CommonString.KEY_VISIT_DATE, jcp.getVisitDate());
                        values.put(CommonString.KEY_STORE_ID, jcp.getStoreId());

                        values.put(CommonString.KEY_VCiD, listDataChild.get(i).getVVcId());
                        values.put(CommonString.KEY_CATEGORY, listDataChild.get(i).getVisicooler_category());
                        values.put(CommonString.KEY_CATEGORY_ID, listDataChild.get(i).getVisicooler_categoryId());

                        values.put(CommonString.KEY_CHECKLIST_ID, listDataChild.get(i).getChecklistId());
                        values.put(CommonString.KEY_CHECKLIST, listDataChild.get(i).getChecklistName());

                        values.put(CommonString.KEY_QUESTION_IMAGEALLOW1, listDataChild.get(i).getQuestionImageAllow1());
                        values.put(CommonString.KEY_QUESTION_IMAGE_LEVEL1, listDataChild.get(i).getQuestionImageLeble1());
                        values.put(CommonString.KEY_QUESTION_IMAGE2_LEVEL2, listDataChild.get(i).getQuestionImageLeble2());
                        if (listDataChild.get(i).getQuestionGridAllow1()) {
                            values.put(CommonString.KEY_QUESTION_GRID1, 1);
                        } else {
                            values.put(CommonString.KEY_QUESTION_GRID1, 0);
                        }

                        if (listDataChild.get(i).getQuestionGridAllow2()) {
                            values.put(CommonString.KEY_QUESTION_GRID2, 1);
                        } else {
                            values.put(CommonString.KEY_QUESTION_GRID2, 0);
                        }

                        values.put(CommonString.KEY_IMAGE_LEVEL1, listDataChild.get(i).getChecklistImglevel1());
                        values.put(CommonString.KEY_IMAGE_LEVEL2, listDataChild.get(i).getChecklistImglevel2());

                        if (listDataChild.get(i).getChecklistimgGridAllow1()) {
                            values.put(CommonString.KEY_IMAGE_GRID1, 1);
                        } else {
                            values.put(CommonString.KEY_IMAGE_GRID1, 0);
                        }

                        if (listDataChild.get(i).getChecklistimgGridAllow2()) {
                            values.put(CommonString.KEY_IMAGE_GRID2, 1);
                        } else {
                            values.put(CommonString.KEY_IMAGE_GRID2, 0);
                        }

                        values.put(CommonString.KEY_QUESTION_IMAGE1, listDataChild.get(i).getQuestionImage1());
                        values.put(CommonString.KEY_QUESTION_IMAGE2, listDataChild.get(i).getQuestionImage2());

                        if (listDataChild.get(i).getQuestionImageAllow2()) {
                            values.put(CommonString.KEY_QUESTION_IMAGEALLOW2, 1);
                        } else {
                            values.put(CommonString.KEY_QUESTION_IMAGEALLOW2, 0);
                        }

                        if (listDataChild.get(i).getBarcode()) {
                            values.put(CommonString.KEY_BARSCAN_FLAG, 1);
                        } else {
                            values.put(CommonString.KEY_BARSCAN_FLAG, 0);
                        }

                        String answerType = listDataChild.get(i).getAnswerType();
                        values.put(CommonString.KEY_ANSWER_TYPE, answerType);
                        if (answerType.equalsIgnoreCase("Binary")) {
                            values.put(CommonString.KEY_BINARY_VALUE, listDataChild.get(i).getBinary_btn_value());
                            values.put(CommonString.KEY_BINARY_ANS_iD, listDataChild.get(i).getBinary_btn_ansId());
                            values.put(CommonString.KEY_ANSWER, "");
                            values.put(CommonString.KEY_ANSWER_CD, 0);
                            values.put(CommonString.KEY_EDIT_VALUE, "");
                            values.put(CommonString.KEY_CHECKED_iD, -1);

                            if (listDataChild.get(i).getImageAllow1()) {
                                values.put(CommonString.KEY_IMAGE1_FLAG, 1);

                            } else {
                                values.put(CommonString.KEY_IMAGE1_FLAG, 0);
                            }

                            if (listDataChild.get(i).getImageAllow2()) {
                                values.put(CommonString.KEY_IMAGE2_FLAG, 1);
                            } else {
                                values.put(CommonString.KEY_IMAGE2_FLAG, 0);
                            }

                            values.put(CommonString.KEY_IMAGE1, listDataChild.get(i).getChecklist_img1());
                            values.put(CommonString.KEY_IMAGE2, listDataChild.get(i).getChecklist_img2());

                        } else if (answerType.equalsIgnoreCase("Single Choice List")) {
                            values.put(CommonString.KEY_BINARY_VALUE, "");
                            values.put(CommonString.KEY_BINARY_ANS_iD, 0);
                            values.put(CommonString.KEY_ANSWER, listDataChild.get(i).getAnswer());
                            values.put(CommonString.KEY_ANSWER_CD, listDataChild.get(i).getAnsId());
                            if (listDataChild.get(i).getImageAllow1()) {
                                values.put(CommonString.KEY_IMAGE1_FLAG, 1);
                            } else {
                                values.put(CommonString.KEY_IMAGE1_FLAG, 0);
                            }

                            if (listDataChild.get(i).getImageAllow2()) {
                                values.put(CommonString.KEY_IMAGE2_FLAG, 1);
                            } else {
                                values.put(CommonString.KEY_IMAGE2_FLAG, 0);
                            }

                            values.put(CommonString.KEY_IMAGE1, listDataChild.get(i).getChecklist_img1());
                            values.put(CommonString.KEY_IMAGE2, listDataChild.get(i).getChecklist_img2());
                            values.put(CommonString.KEY_EDIT_VALUE, "");
                            values.put(CommonString.KEY_CHECKED_iD, listDataChild.get(i).getCheckedId());
                        } else if (answerType.equalsIgnoreCase("Text") || answerType.equalsIgnoreCase("Number")) {
                            values.put(CommonString.KEY_BINARY_VALUE, "");
                            values.put(CommonString.KEY_BINARY_ANS_iD, 0);
                            values.put(CommonString.KEY_ANSWER, "");
                            values.put(CommonString.KEY_ANSWER_CD, 0);
                            values.put(CommonString.KEY_IMAGE1_FLAG, 0);
                            values.put(CommonString.KEY_IMAGE1, "");
                            values.put(CommonString.KEY_IMAGE2_FLAG, 0);
                            values.put(CommonString.KEY_IMAGE2, "");
                            values.put(CommonString.KEY_EDIT_VALUE, listDataChild.get(i).getEdt_text_value().replaceAll("[^a-zA-Z0-9]", "").trim());
                            values.put(CommonString.KEY_CHECKED_iD, -1);
                        } else if (answerType.equalsIgnoreCase("Image")) {
                            values.put(CommonString.KEY_BINARY_VALUE, "");
                            values.put(CommonString.KEY_BINARY_ANS_iD, 0);
                            values.put(CommonString.KEY_ANSWER, "");
                            values.put(CommonString.KEY_ANSWER_CD, 0);
                            values.put(CommonString.KEY_IMAGE1_FLAG, 0);
                            values.put(CommonString.KEY_IMAGE1, "");
                            values.put(CommonString.KEY_IMAGE2_FLAG, 0);
                            values.put(CommonString.KEY_IMAGE2, "");
                            values.put(CommonString.KEY_EDIT_VALUE, "");
                            values.put(CommonString.KEY_CHECKED_iD, -1);
                        }

                        l = db.insert(CommonString.TABLE_VISICOOLER_CHECKLIST, null, values);

                    }
                }
            }

            if (common_id > 0) {
                return common_id;
            } else {
                return 0;
            }
        } catch (Exception ex) {
            return 0;
        }
    }


    public long Insertposm(MappingJourneyPlan jcp, String Ispresent, ArrayList<MasterPosm> listDataChild) {
        long l = 0, common_id = 0;
        db.delete(CommonString.TABLE_POSM_PRESENT, CommonString.KEY_STORE_ID + "=" + jcp.getStoreId() +
                " AND " + CommonString.KEY_VISIT_DATE + "='" + jcp.getVisitDate() + "'", null);
        db.delete(CommonString.TABLE_POSM, CommonString.KEY_STORE_ID + "=" + jcp.getStoreId() +
                " AND " + CommonString.KEY_VISIT_DATE + "='" + jcp.getVisitDate() + "'", null);
        ContentValues values = null;

        try {
            values = new ContentValues();
            values.put(CommonString.KEY_VISIT_DATE, jcp.getVisitDate());
            values.put(CommonString.KEY_STORE_ID, jcp.getStoreId());
            values.put(CommonString.KEY_PRESENT, Ispresent);

            common_id = db.insert(CommonString.TABLE_POSM_PRESENT, null, values);

            if (!Ispresent.equals("") && Ispresent.equals("1")) {
                if (listDataChild.size() > 0) {
                    for (int i = 0; i < listDataChild.size(); i++) {
                        values = new ContentValues();
                        values.put(CommonString.KEY_COMMON_ID, (int) common_id);
                        values.put(CommonString.KEY_VISIT_DATE, jcp.getVisitDate());
                        values.put(CommonString.KEY_STORE_ID, jcp.getStoreId());
                        values.put(CommonString.KEY_POSM, listDataChild.get(i).getPosmName());
                        values.put(CommonString.KEY_POSM_ID, listDataChild.get(i).getPosmId());
                        values.put(CommonString.KEY_REFIMAGE, listDataChild.get(i).getRefImage());
                        String avai = listDataChild.get(i).getPosm_yesorno();
                        values.put(CommonString.KEY_POSM_AVAILEBILITY, avai);
                        if (avai != null && avai.equalsIgnoreCase("Yes")) {
                            values.put(CommonString.KEY_IMAGE, listDataChild.get(i).getPosm_img());
                            values.put(CommonString.KEY_REASON, "");
                            values.put(CommonString.KEY_REASON_ID, 0);
                        } else if (avai != null && avai.equalsIgnoreCase("No")) {
                            values.put(CommonString.KEY_IMAGE, "");
                            values.put(CommonString.KEY_REASON, listDataChild.get(i).getReason());
                            values.put(CommonString.KEY_REASON_ID, listDataChild.get(i).getReasonId());
                        }
                        l = db.insert(CommonString.TABLE_POSM, null, values);

                    }
                }
            }

            if (common_id > 0) {
                return common_id;
            } else {
                return 0;
            }
        } catch (Exception ex) {
            return 0;
        }
    }


    public long Insertpaidvisibility(MappingJourneyPlan jcp, ArrayList<MasterAsset> listDataChild) {
        long l = 0;
        db.delete(CommonString.TABLE_PAIDVISIBILITY, CommonString.KEY_STORE_ID + "=" + jcp.getStoreId() + " AND " + CommonString.KEY_VISIT_DATE + "='" + jcp.getVisitDate() + "'", null);
        ContentValues values = null;
        try {
            if (listDataChild.size() > 0) {
                for (int i = 0; i < listDataChild.size(); i++) {
                    values = new ContentValues();
                    values.put(CommonString.KEY_VISIT_DATE, jcp.getVisitDate());
                    values.put(CommonString.KEY_STORE_ID, jcp.getStoreId());
                    values.put(CommonString.KEY_CATEGORY_ID, listDataChild.get(i).getCategoryId());
                    values.put(CommonString.KEY_CATEGORY, listDataChild.get(i).getCategory());
                    values.put(CommonString.KEY_ASSET_ID, listDataChild.get(i).getAssetId());
                    values.put(CommonString.KEY_ASSET, listDataChild.get(i).getAssetName());
                    String avai = listDataChild.get(i).getIspresent();
                    values.put(CommonString.KEY_PRESENT, avai);

                    if (avai != null && avai.equalsIgnoreCase("Yes")) {
                        values.put(CommonString.KEY_IMAGE, listDataChild.get(i).getAsset_img());
                        values.put(CommonString.KEY_LOCATION, listDataChild.get(i).getLocation());
                        values.put(CommonString.KEY_LOCATION_ID, listDataChild.get(i).getLocationId());
                        values.put(CommonString.KEY_CHECKED_iD, listDataChild.get(i).getCheckedIdforlocation());

                        values.put(CommonString.KEY_REASON, "");
                        values.put(CommonString.KEY_REASON_ID, 0);
                        values.put(CommonString.KEY_NONASSET_CHECKED_iD, -1);
                    } else if (avai != null && avai.equalsIgnoreCase("No")) {
                        values.put(CommonString.KEY_IMAGE, "");
                        values.put(CommonString.KEY_LOCATION, "");
                        values.put(CommonString.KEY_LOCATION_ID, 0);
                        values.put(CommonString.KEY_CHECKED_iD, -1);
                        values.put(CommonString.KEY_REASON, listDataChild.get(i).getNon_reason());
                        values.put(CommonString.KEY_REASON_ID, listDataChild.get(i).getNonreasonId());
                        values.put(CommonString.KEY_NONASSET_CHECKED_iD, listDataChild.get(i).getCheckidfor_nonreason());
                    }

                    l = db.insert(CommonString.TABLE_PAIDVISIBILITY, null, values);

                }
            }

            if (l > 0) {
                return l;
            } else {
                return 0;
            }
        } catch (Exception ex) {
            return 0;
        }
    }


    public long insertpromotions(MappingJourneyPlan jcp, ArrayList<MappingPromotion> listDataChild) {
        long l = 0;
        db.delete(CommonString.TABLE_PROMOTION_DATA, CommonString.KEY_STORE_ID + "=" + jcp.getStoreId() + " AND " +
                CommonString.KEY_VISIT_DATE + "='" + jcp.getVisitDate() + "'", null);
        ContentValues values = null;
        try {
            if (listDataChild.size() > 0) {
                for (int i = 0; i < listDataChild.size(); i++) {
                    values = new ContentValues();
                    values.put(CommonString.KEY_VISIT_DATE, jcp.getVisitDate());
                    values.put(CommonString.KEY_STORE_ID, jcp.getStoreId());
                    values.put(CommonString.KEY_CATEGORY_ID, listDataChild.get(i).getCategoryId());
                    values.put(CommonString.KEY_CATEGORY, listDataChild.get(i).getCategory_name());
                    values.put(CommonString.KEY_PROMOTION_ID, listDataChild.get(i).getPromoId());
                    values.put(CommonString.KEY_PROMOTION, listDataChild.get(i).getPromotion());
                    String avai = listDataChild.get(i).getPresent();
                    values.put(CommonString.KEY_PRESENT, avai);
                    if (avai != null && avai.equalsIgnoreCase("Yes")) {
                        values.put(CommonString.KEY_IMAGE, listDataChild.get(i).getImage1());
                        values.put(CommonString.KEY_REASON, "");
                        values.put(CommonString.KEY_REASON_ID, 0);
                        values.put(CommonString.KEY_CHECKED_iD, -1);
                    } else if (avai != null && avai.equalsIgnoreCase("No")) {
                        values.put(CommonString.KEY_IMAGE, "");
                        values.put(CommonString.KEY_REASON, listDataChild.get(i).getReason());
                        values.put(CommonString.KEY_REASON_ID, listDataChild.get(i).getReasonId());
                        values.put(CommonString.KEY_CHECKED_iD, listDataChild.get(i).getIsChecked());
                    }

                    l = db.insert(CommonString.TABLE_PROMOTION_DATA, null, values);

                }
            }

            if (l > 0) {
                return l;
            } else {
                return 0;
            }
        } catch (Exception ex) {
            return 0;
        }
    }


    //posm
    public ArrayList<NonPosmReason> getPOSMReason() {
        Log.d("Fetching Data", "------------------");
        Cursor dbcursor = null;
        ArrayList<NonPosmReason> list = new ArrayList<>();

        try {
            dbcursor = db.rawQuery("select * from Non_Posm_Reason", null);
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    NonPosmReason reason = new NonPosmReason();
                    reason.setReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason")));
                    reason.setReasonId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ReasonId")));
                    list.add(reason);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception ", e.getMessage());
            return list;
        }

        return list;
    }


    public ArrayList<MasterAssetLocation> getassetlocation() {
        Log.d("Fetching Data", "------------------");
        Cursor dbcursor = null;
        ArrayList<MasterAssetLocation> list = new ArrayList<>();

        try {
            dbcursor = db.rawQuery("select * from Master_AssetLocation", null);
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterAssetLocation reason = new MasterAssetLocation();
                    reason.setLocationName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("LocationName")));
                    reason.setLocationId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("LocationId")));
                    list.add(reason);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception ", e.getMessage());
            return list;
        }
        return list;
    }


    public ArrayList<MasterNonAssetReason> getnonassetReason() {
        Log.d("Fetching Data", "------------------");
        Cursor dbcursor = null;
        ArrayList<MasterNonAssetReason> list = new ArrayList<>();

        try {
            dbcursor = db.rawQuery("select * from Master_NonAssetReason", null);
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterNonAssetReason reason = new MasterNonAssetReason();
                    reason.setReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason")));
                    reason.setReasonId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ReasonId")));
                    list.add(reason);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception ", e.getMessage());
            return list;
        }
        return list;
    }


    public ArrayList<MasterPosm> getPOSMDeploymentData(MappingJourneyPlan jcpGetset) {
        ArrayList<MasterPosm> list = new ArrayList<MasterPosm>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery(" SELECT DISTINCT PM.PosmId,PM.PosmName ,PM.RefImage FROM Mapping_Posm MP INNER JOIN Master_Posm PM on PM.PosmId = MP.PosmId" +
                    " where MP.StoretypeId =" + jcpGetset.getStoreTypeId() + " and  MP.StateId =" + jcpGetset.getStateId() + " and MP.StoreClassId =" +
                    jcpGetset.getStoreClassId() + " and MP.StoreCategoryId =" + jcpGetset.getStoreCategoryId() + "", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterPosm sb = new MasterPosm();
                    sb.setPosmId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("PosmId")));
                    sb.setPosmName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosmName")));
                    sb.setRefImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow("RefImage")));
                    sb.setReasonArrayList(getPOSMReason());
                    sb.setPosm_img("");
                    sb.setReason("");
                    sb.setReasonId(0);
                    sb.setPosm_yesorno("");

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception get JCP!", e.toString());
            return list;
        }
        return list;
    }


    public ArrayList<MasterAsset> getasset(MappingJourneyPlan jcpGetset) {
        ArrayList<MasterAsset> list = new ArrayList<MasterAsset>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("Select DISTINCT t1.CategoryId,t1.CategoryName,t4.AssetId,(t2.BrandName || ' - '|| t4.AssetName)as AssetName From"
                    + " Master_Category t1 Inner join Master_Brand t2 on t1.CategoryId=t2.CategoryId Inner join Mapping_PaidVisibility t3"
                    + " on t2.BrandId=t3.BrandId inner JOIN Master_Asset t4 on t4.AssetId=t3.AssetId where t3.StoreId=" + jcpGetset.getStoreId()
                    + " Order By t1.CategorySequence,t4.AssetName", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterAsset sb = new MasterAsset();
                    sb.setCategoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("CategoryId")));
                    sb.setCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CategoryName")));
                    sb.setAssetId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("AssetId")));
                    sb.setAssetName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("AssetName")));
                    sb.setLocations(getassetlocation());
                    sb.setNonAssetReasons(getnonassetReason());

                    sb.setIspresent("");
                    sb.setAsset_img("");
                    sb.setLocation("");
                    sb.setLocationId(0);
                    sb.setNon_reason("");
                    sb.setNonreasonId(0);
                    sb.setCheckedIdforlocation(-1);
                    sb.setCheckidfor_nonreason(-1);

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception get JCP!", e.toString());
            return list;
        }
        return list;
    }


    public ArrayList<MasterAsset> getassetforvqps() {
        ArrayList<MasterAsset> list = new ArrayList<MasterAsset>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("Select Distinct AssetId, AssetName From Master_Asset Order By AssetName", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterAsset sb = new MasterAsset();
                    sb.setAssetId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("AssetId")));
                    sb.setAssetName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("AssetName")));

                    list.add(sb);

                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception get JCP!", e.toString());
            return list;
        }
        return list;
    }


    public ArrayList<MasterAsset> getinsertedpaidVisibility(MappingJourneyPlan jcpGetset) {
        ArrayList<MasterAsset> list = new ArrayList<MasterAsset>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_PAIDVISIBILITY + " WHERE " + CommonString.KEY_STORE_ID + "=" + jcpGetset.getStoreId() + " AND "
                    + CommonString.KEY_VISIT_DATE + "='" + jcpGetset.getVisitDate() + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterAsset sb = new MasterAsset();

                    sb.setCategoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CATEGORY_ID)));
                    sb.setCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CATEGORY)));
                    sb.setAssetId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ASSET_ID)));
                    sb.setAssetName(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ASSET)));

                    sb.setIspresent(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PRESENT)));
                    sb.setAsset_img(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE)));
                    sb.setLocation(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LOCATION)));
                    sb.setLocationId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LOCATION_ID)));
                    sb.setCheckedIdforlocation(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CHECKED_iD)));

                    sb.setNonreasonId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON_ID)));
                    sb.setNon_reason(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON)));
                    sb.setCheckidfor_nonreason(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_NONASSET_CHECKED_iD)));

                    sb.setNonAssetReasons(getnonassetReason());
                    sb.setLocations(getassetlocation());

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception get JCP!", e.toString());
            return list;
        }

        return list;
    }

    public ArrayList<MasterAsset> getinsertedpaidVisibilityupload(String storeId, String visit_date) {
        ArrayList<MasterAsset> list = new ArrayList<MasterAsset>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_PAIDVISIBILITY + " WHERE " + CommonString.KEY_STORE_ID + "=" + storeId + " AND "
                    + CommonString.KEY_VISIT_DATE + "='" + visit_date + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterAsset sb = new MasterAsset();
                    sb.setCategoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CATEGORY_ID)));
                    sb.setAssetId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ASSET_ID)));
                    sb.setIspresent(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PRESENT)));
                    sb.setAsset_img(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE)));
                    sb.setLocation(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LOCATION)));
                    sb.setLocationId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LOCATION_ID)));
                    sb.setCheckedIdforlocation(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CHECKED_iD)));
                    sb.setNonreasonId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON_ID)));
                    sb.setNon_reason(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON)));
                    sb.setCheckidfor_nonreason(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_NONASSET_CHECKED_iD)));
                    sb.setNonAssetReasons(getnonassetReason());
                    sb.setLocations(getassetlocation());
                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception get JCP!", e.toString());
            return list;
        }
        return list;
    }


    public ArrayList<MappingPromotion> getinsertedpromotions(MappingJourneyPlan jcpGetset) {
        ArrayList<MappingPromotion> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_PROMOTION_DATA + " WHERE " + CommonString.KEY_STORE_ID + "="
                    + jcpGetset.getStoreId() + " AND " + CommonString.KEY_VISIT_DATE + "='" + jcpGetset.getVisitDate() + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MappingPromotion sb = new MappingPromotion();
                    sb.setCategoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CATEGORY_ID)));
                    sb.setCategory_name(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CATEGORY)));
                    sb.setPromoId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PROMOTION_ID)));
                    sb.setPromotion(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PROMOTION)));
                    sb.setPresent(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PRESENT)));
                    sb.setImage1(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE)));
                    sb.setReasonId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON_ID)));
                    sb.setReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON)));
                    sb.setIsChecked(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CHECKED_iD)));
                    sb.setNonPromotionReasons(getnonpromotionreason());

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception get JCP!", e.toString());
            return list;
        }

        return list;
    }

    public ArrayList<MappingPromotion> getinsertedpromotionsupload(String storeId, String visit_date) {
        ArrayList<MappingPromotion> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_PROMOTION_DATA + " WHERE " + CommonString.KEY_STORE_ID + "="
                    + storeId + " AND " + CommonString.KEY_VISIT_DATE + "='" + visit_date + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MappingPromotion sb = new MappingPromotion();
                    sb.setCategoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CATEGORY_ID)));
                    sb.setPromoId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PROMOTION_ID)));
                    sb.setPresent(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PRESENT)));
                    sb.setImage1(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE)));
                    sb.setReasonId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON_ID)));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception get JCP!", e.toString());
            return list;
        }
        return list;
    }


    public boolean insertMaster_ChecklistAnswer(MasterChecklistAnswerGetterSetter masterChecklistAnswerGetterSetter) {
        db.delete("Master_ChecklistAnswer", null, null);
        ContentValues values = new ContentValues();
        List<MasterChecklistAnswer> data = masterChecklistAnswerGetterSetter.getMasterChecklistAnswer();
        try {
            if (data.size() == 0) {
                return false;
            }

            for (int i = 0; i < data.size(); i++) {
                values.put("ChecklistId", data.get(i).getChecklistId());
                values.put("AnswerId", data.get(i).getAnswerId());
                values.put("Answer", data.get(i).getAnswer());
                values.put("Rating", data.get(i).getRating());
                values.put("ImageAllow1", data.get(i).getImageAllow1());
                values.put("ImageAllow2", data.get(i).getImageAllow2());

                values.put("ImageLable1", data.get(i).getImageLable1());
                values.put("ImageLable2", data.get(i).getImageLable2());

                values.put("ImageGridAllow1", data.get(i).getImageGridAllow1());
                values.put("ImageGridAllow2", data.get(i).getImageGridAllow2());

                long id = db.insert("Master_ChecklistAnswer", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Database Exception  ", ex.toString());
            return false;
        }
    }

    public boolean insertmappingsubprogramChecklist(MasterChecklistAnswerGetterSetter masterChecklistAnswerGetterSetter) {
        db.delete("Mapping_SubProgramChecklist", null, null);
        ContentValues values = new ContentValues();
        List<MappingSubProgramChecklist> data = masterChecklistAnswerGetterSetter.getMappingSubProgramChecklist();
        try {
            if (data.size() == 0) {
                return false;
            }

            for (int i = 0; i < data.size(); i++) {
                values.put("SubProgramId", data.get(i).getSubProgramId());
                values.put("ChecklistId", data.get(i).getChecklistId());

                long id = db.insert("Mapping_SubProgramChecklist", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Database Exception  ", ex.toString());
            return false;
        }
    }


    public long delete_table(String table_name) {
        long l = 0;
        try {
            l = db.delete(table_name, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return l;
    }

    public boolean insertmappingprogram(MasterChecklistAnswerGetterSetter masterChecklistAnswerGetterSetter) {
        db.delete("Mapping_Program", null, null);
        ContentValues values = new ContentValues();
        List<MappingProgram> data = masterChecklistAnswerGetterSetter.getMappingProgramList();
        try {
            if (data.size() == 0) {
                return false;
            }

            for (int i = 0; i < data.size(); i++) {
                values.put("SubProgramId", data.get(i).getSubProgramId());
                values.put("StoreId", data.get(i).getStoreId());

                long id = db.insert("Mapping_Program", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Database Exception  ", ex.toString());
            return false;
        }
    }

    public boolean insertmappingvisicoolerchecklist(MasterChecklistAnswerGetterSetter masterChecklistAnswerGetterSetter) {
        db.delete("Mapping_VisicoolerChecklist", null, null);
        ContentValues values = new ContentValues();
        List<MappingVisicoolerChecklist> data = masterChecklistAnswerGetterSetter.getMappingVisicoolerChecklist();
        try {
            if (data.size() == 0) {
                return false;
            }

            for (int i = 0; i < data.size(); i++) {
                values.put("ChecklistId", data.get(i).getChecklistId());
                values.put("VCCategoryId", data.get(i).getVCCategoryId());

                long id = db.insert("Mapping_VisicoolerChecklist", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Database Exception  ", ex.toString());
            return false;
        }
    }


    public boolean insertmappingvisicooler(MasterChecklistAnswerGetterSetter masterChecklistAnswerGetterSetter) {
        db.delete("Mapping_Visicooler", null, null);
        ContentValues values = new ContentValues();
        List<MappingVisicooler> data = masterChecklistAnswerGetterSetter.getMappingVisicooler();
        try {
            if (data.size() == 0) {
                return false;
            }

            for (int i = 0; i < data.size(); i++) {
                values.put("StoreId", data.get(i).getStoreId());
                values.put("VCId", data.get(i).getVCId());
                values.put("VCCategoryId", data.get(i).getVCCategoryId());
                values.put("VisicoolerCategory", data.get(i).getVisicoolerCategory());
                values.put("Make", data.get(i).getMake());
                values.put("VCType", data.get(i).getVCType());

                long id = db.insert("Mapping_Visicooler", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Database Exception  ", ex.toString());
            return false;
        }
    }

    public boolean insertMaster_PosmData(MasterPosmGetterSetter masterPosmGetterSetter) {
        db.delete("Master_Posm", null, null);
        ContentValues values = new ContentValues();
        List<MasterPosm> data = masterPosmGetterSetter.getMasterPosm();
        try {
            if (data.size() == 0) {
                return false;
            }

            for (int i = 0; i < data.size(); i++) {
                values.put("PosmId", data.get(i).getPosmId());
                values.put("PosmName", data.get(i).getPosmName());
                values.put("RefImage", data.get(i).getRefImage());

                long id = db.insert("Master_Posm", null, values);

                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Database Exception  ", ex.toString());
            return false;
        }
    }

    public long insertStoreProfileData(MappingJourneyPlan JCP, StoreProfileGetterSetter storeProfile) {
        db.delete(CommonString.TABLE_STORE_PROFILE_DATA, CommonString.KEY_STORE_ID + "='" + JCP.getStateId() +
                "' AND " + CommonString.KEY_VISIT_DATE + "='" + JCP.getVisitDate() + "'", null);
        ContentValues values = new ContentValues();
        long l = 0;
        try {

            db.beginTransaction();
            values.put(CommonString.KEY_STORE_ID, JCP.getStoreId());
            values.put(CommonString.KEY_STORE_NAME, JCP.getStoreName());
            values.put(CommonString.KEY_VISIT_DATE, JCP.getVisitDate());
            values.put(CommonString.KEY_STORE_TYPE, JCP.getStoreType());
            values.put(CommonString.KEY_STORE_TYPE_CD, JCP.getStoreTypeId());
            values.put(CommonString.KEY_STORE_CITY, JCP.getCityName());
            values.put(CommonString.KEY_STORE_CITY_iD, JCP.getCityId());

            values.put(CommonString.KEY_STORE_ADDRESS, storeProfile.getStore_addres());
            values.put(CommonString.KEY_STORE_LANDMARK, storeProfile.getLand_mark());
            values.put(CommonString.KEY_STORE_PINCODE, storeProfile.getPin_code());

            l = db.insert(CommonString.TABLE_STORE_PROFILE_DATA, null, values);

            db.setTransactionSuccessful();
            db.endTransaction();

        } catch (Exception ex) {
            Log.d("Database Exception", " while Insert Header Data " + ex.toString());
        }

        return l;
    }

    public StoreProfileGetterSetter getStoreProfileData(String store_cd, String visit_date) {
        StoreProfileGetterSetter sb = new StoreProfileGetterSetter();
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_STORE_PROFILE_DATA + " WHERE " + CommonString.KEY_STORE_ID + " ='"
                    + store_cd + "' AND " + CommonString.KEY_VISIT_DATE + "='" + visit_date + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    sb.setStore_type_cd((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_TYPE_CD))));
                    sb.setStore_type(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_TYPE)));
                    sb.setStoreCd(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_ID)));
                    sb.setStore_visit_date(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE)));
                    sb.setStore_name(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_NAME)));
                    sb.setStore_city(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_CITY)));
                    sb.setStore_cityId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_CITY_iD)));
                    sb.setStore_addres(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_ADDRESS)));
                    sb.setLand_mark(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_LANDMARK)));
                    sb.setPin_code(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_PINCODE)));
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return sb;
            }

        } catch (Exception e) {
            Log.d("Exception get JCP!", e.toString());
            return sb;
        }

        return sb;
    }

    public boolean insertJCP_DBSRSavedData(MappingJourneyPlan data) {
        db.delete(CommonString.TABLE_Journey_Plan_DBSR_Saved, "StoreId = " + data.getStoreId() + "", null);
        ContentValues values = new ContentValues();
        try {
            if (data == null) {
                return false;
            }
            values.put("Address", data.getAddress());
            values.put("ChannelId", data.getChannelId());
            values.put("CityId", data.getCityId());
            values.put("CityName", data.getCityName());
            values.put("ContactPerson", data.getContactPerson());
            values.put("DistributorId", data.getDistributorId());
            values.put("DistributorName", data.getDistributorName());
            values.put("EmpId", data.getEmpId());
            values.put("Email", data.getEmail());
            values.put("GeoTag", data.getGeoTag());
            values.put("GSTno", data.getGSTno());
            values.put("GSTImage", data.getGSTImage());
            values.put("Landmark", data.getLandmark());
            values.put("Latitude", data.getLatitude());
            values.put("Longitude", data.getLongitude());
            values.put("Location", data.getLocation());
            values.put("MID", data.getMID());
            values.put("Mobile", data.getMobile());
            values.put("Phone", data.getPhone());
            values.put("Pincode", data.getPincode());
            values.put("PosCompany", data.getPosCompany());
            values.put("PosExist", data.getPosExist());
            values.put("PosRemark", data.getPosRemark());
            values.put("StoreId", data.getStoreId());
            values.put("StateName", data.getStateName());
            values.put("StoreCategory", data.getStoreCategory());
            values.put("StoreCategoryId", data.getStoreCategoryId());
            values.put("StoreCode", data.getStoreCode());
            values.put("StateId", data.getStateId());
            values.put("StoreName", data.getStoreName());
            values.put("StoreType", data.getStoreType());
            values.put("StoreTypeId", data.getStoreTypeId());
            values.put("StoreClassId", data.getStoreClassId());
            values.put("StoreClass", data.getStoreClass());
            values.put("TaxType", data.getTaxType());
            values.put("UploadStatus", data.getUploadStatus());
            values.put("VisitDate", data.getVisitDate());
            //values.put("ManagerId", data.getManagerId());
            //values.put("WeeklyUpload", data.getWeeklyUpload());
            long id = db.insert(CommonString.TABLE_Journey_Plan_DBSR_Saved, null, values);
            if (id == -1) {
                throw new Exception();
            }

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }


    public ArrayList<CategoryMaster> getCategoryDBSRSavedData(String storeid, String visit_date) {

        ArrayList<CategoryMaster> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery("select * From " + CommonString.TABLE_CATEGORY_DBSR_DATA + " where " + CommonString.KEY_STORE_ID + " = " + storeid + " and " + CommonString.KEY_DDATE + " = '" + visit_date + "'", null);
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {

                    CategoryMaster categoryMaster = new CategoryMaster();
                    categoryMaster.setKey_Id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IID)));
                    categoryMaster.setCategoryId(Integer.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CATEGORY_CD))));
                    categoryMaster.setExist(Boolean.parseBoolean(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_EXISTORNOT))));
                    categoryMaster.setImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CATEGORY_IMAGE)));

                    list.add(categoryMaster);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {

            return list;
        }
        return list;
    }

    public boolean isDBSRDataFilled(int storeId, String date) {
        boolean filled = false;
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_CATEGORY_DBSR_DATA + " WHERE STORE_ID= '" + storeId + "' AND DATE ='" + date + "'", null);
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                int icount = dbcursor.getInt(0);
                dbcursor.close();
                if (icount > 0) {
                    filled = true;
                } else {
                    filled = false;
                }
            }
        } catch (Exception e) {

            return filled;
        }
        return filled;
    }


    public boolean insertJCP_DBSRData(JCPGetterSetter data) {
        db.delete("JournyPlan_DBSR", null, null);
        List<MappingJourneyPlan> jcpList = data.getJournyPlan_DBSR();
        ContentValues values = new ContentValues();
        try {
            if (jcpList.size() == 0) {
                return false;
            }
            for (int i = 0; i < jcpList.size(); i++) {
                values.put("Address", jcpList.get(i).getAddress());
                values.put("ChannelId", jcpList.get(i).getChannelId());
                values.put("CityId", jcpList.get(i).getCityId());
                values.put("CityName", jcpList.get(i).getCityName());
                values.put("ContactPerson", jcpList.get(i).getContactPerson());
                values.put("DistributorId", jcpList.get(i).getDistributorId());
                values.put("DistributorName", jcpList.get(i).getDistributorName());
                values.put("EmpId", jcpList.get(i).getEmpId());
                values.put("Email", jcpList.get(i).getEmail());
                values.put("GeoTag", jcpList.get(i).getGeoTag());
                values.put("GSTno", jcpList.get(i).getGSTno());
                values.put("GSTImage", jcpList.get(i).getGSTImage());
                values.put("Landmark", jcpList.get(i).getLandmark());
                values.put("Latitude", jcpList.get(i).getLatitude());
                values.put("Longitude", jcpList.get(i).getLongitude());
                values.put("Location", jcpList.get(i).getLocation());
                values.put("MID", jcpList.get(i).getMID());
                values.put("Mobile", jcpList.get(i).getMobile());
                values.put("Phone", jcpList.get(i).getPhone());
                values.put("Pincode", jcpList.get(i).getPincode());
                values.put("PosCompany", jcpList.get(i).getPosCompany());
                values.put("PosExist", jcpList.get(i).getPosExist());
                values.put("PosRemark", jcpList.get(i).getPosRemark());
                values.put("StoreId", jcpList.get(i).getStoreId());
                values.put("StateName", jcpList.get(i).getStateName());
                values.put("StoreCategory", jcpList.get(i).getStoreCategory());
                values.put("StoreCategoryId", jcpList.get(i).getStoreCategoryId());
                values.put("StoreCode", jcpList.get(i).getStoreCode());
                values.put("StateId", jcpList.get(i).getStateId());
                values.put("StoreName", jcpList.get(i).getStoreName());
                values.put("StoreType", jcpList.get(i).getStoreType());
                values.put("StoreTypeId", jcpList.get(i).getStoreTypeId());
                values.put("StoreClassId", jcpList.get(i).getStoreClassId());
                values.put("StoreClass", jcpList.get(i).getStoreClass());
                values.put("TaxType", jcpList.get(i).getTaxType());
                values.put("UploadStatus", jcpList.get(i).getUploadStatus());
                values.put("VisitDate", jcpList.get(i).getVisitDate());
                //values.put("ManagerId", jcpList.get(i).getManagerId());
                //values.put("WeeklyUpload", jcpList.get(i).getWeeklyUpload());

                long id = db.insert("JournyPlan_DBSR", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Exception in Jcp", ex.toString());
            return false;
        }

    }

    public ArrayList<MappingJourneyPlan> getStoreData_DBSR(String date) {
        ArrayList<MappingJourneyPlan> list = new ArrayList<MappingJourneyPlan>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("SELECT  * FROM JournyPlan_DBSR  " + "WHERE VisitDate ='" + date + "'", null);
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MappingJourneyPlan sb = new MappingJourneyPlan();
                    sb.setMID((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("MID"))));
                    sb.setAddress((dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address"))));
                    sb.setCityId((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("CityId"))));
                    sb.setCityName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CityName")));
                    sb.setContactPerson(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ContactPerson")));
                    sb.setEmail(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Email")));
                    sb.setEmpId((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("EmpId"))));
                    sb.setLandmark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Landmark")));
                    sb.setLocation(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Location")));
                    sb.setLongitude(dbcursor.getFloat(dbcursor.getColumnIndexOrThrow("Longitude")));
                    sb.setLatitude(dbcursor.getFloat(dbcursor.getColumnIndexOrThrow("Latitude")));
                    sb.setGeoTag(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GeoTag")));
                    sb.setMobile(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Mobile")));
                    sb.setChannelId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ChannelId")));
                    sb.setPhone(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Phone")));
                    sb.setPincode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Pincode")));
                    sb.setStateId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StateId")));
                    sb.setStateName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StateName")));
                    sb.setStoreCode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreCode")));
                    sb.setStoreId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreId")));
                    sb.setStoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreName")));
                    sb.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreType")));
                    sb.setStoreTypeId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreTypeId")));
                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("UploadStatus")));
                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("VisitDate")));
                    sb.setDistributorId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("DistributorId")));
                    sb.setDistributorName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("DistributorName")));
                    sb.setStoreCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreCategory")));
                    sb.setStoreClassId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreClassId")));
                    sb.setStoreClass(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreClass")));
                    sb.setPosExist(Boolean.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosExist"))));
                    sb.setPosCompany(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosCompany")));
                    sb.setPosRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosRemark")));
                    sb.setTaxType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("TaxType")));
                    sb.setGSTno(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GSTno")));
                    sb.setGSTImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GSTImage")));
                    sb.setStoreCategoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreCategoryId")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception get JCP!", e.toString());
            return list;
        }


        return list;
    }


    public ArrayList<MappingJourneyPlan> getStoreData_DBSR_Saved(String date) {
        ArrayList<MappingJourneyPlan> list = new ArrayList<MappingJourneyPlan>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT  * FROM Journey_Plan_DBSR_Saved  " + "WHERE VisitDate ='" + date + "'", null);
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MappingJourneyPlan sb = new MappingJourneyPlan();
                    sb.setMID((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("MID"))));
                    sb.setAddress((dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address"))));
                    sb.setCityId((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("CityId"))));
                    sb.setCityName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CityName")));
                    sb.setContactPerson(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ContactPerson")));
                    sb.setEmail(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Email")));
                    sb.setEmpId((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("EmpId"))));
                    sb.setLandmark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Landmark")));
                    sb.setLocation(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Location")));
                    sb.setLongitude(dbcursor.getFloat(dbcursor.getColumnIndexOrThrow("Longitude")));
                    sb.setLatitude(dbcursor.getFloat(dbcursor.getColumnIndexOrThrow("Latitude")));
                    sb.setGeoTag(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GeoTag")));
                    sb.setMobile(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Mobile")));
                    sb.setChannelId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ChannelId")));
                    sb.setPhone(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Phone")));
                    sb.setPincode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Pincode")));
                    sb.setStateId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StateId")));
                    sb.setStateName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StateName")));
                    sb.setStoreCode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreCode")));
                    sb.setStoreId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreId")));
                    sb.setStoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreName")));
                    sb.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreType")));
                    sb.setStoreTypeId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreTypeId")));
                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("UploadStatus")));
                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("VisitDate")));
                    sb.setDistributorId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("DistributorId")));
                    sb.setDistributorName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("DistributorName")));
                    sb.setStoreCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreCategory")));
                    sb.setStoreClassId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreClassId")));
                    sb.setStoreClass(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreClass")));
                    sb.setPosExist(Boolean.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosExist"))));
                    sb.setPosCompany(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosCompany")));
                    sb.setPosRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosRemark")));
                    sb.setTaxType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("TaxType")));
                    sb.setGSTno(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GSTno")));
                    sb.setGSTImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GSTImage")));
                    sb.setStoreCategoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreCategoryId")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {

            return list;
        }

        return list;
    }


    public ArrayList<MappingJourneyPlan> getSpecificStoreDataTable(String table) {
        ArrayList<MappingJourneyPlan> list = new ArrayList<MappingJourneyPlan>();
        Cursor dbcursor = null;
        try {

            if (table != null) {
                if (table.equalsIgnoreCase("from_jcp")) {

                    dbcursor = db.rawQuery("SELECT  * from Mapping_JourneyPlan ", null);

                } else if (table.equalsIgnoreCase("from_non_merchandized")) {

                    dbcursor = db.rawQuery("SELECT  * from JourneyPlan_NonMerchandised ", null);

                } else if (table.equalsIgnoreCase("from_not_covered")) {

                    dbcursor = db.rawQuery("SELECT  * from JourneyPlan_NotCovered ", null);

                }

            } else {
                dbcursor = db.rawQuery("SELECT  * from Mapping_JourneyPlan  ", null);
            }
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MappingJourneyPlan sb = new MappingJourneyPlan();
                    sb.setAddress((dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address"))));
                    sb.setCityId((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("CityId"))));
                    sb.setCityName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CityName")));
                    sb.setContactPerson(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ContactPerson")));
                    sb.setEmail(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Email")));
                    sb.setEmpId((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("EmpId"))));
                    sb.setLandmark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Landmark")));
                    sb.setLocation(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Location")));
                    sb.setLongitude(dbcursor.getFloat(dbcursor.getColumnIndexOrThrow("Longitude")));
                    sb.setLatitude(dbcursor.getFloat(dbcursor.getColumnIndexOrThrow("Latitude")));
                    sb.setGeoTag(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GeoTag")));
                    sb.setMID((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("MID"))));
                    sb.setMobile(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Mobile")));
                    sb.setChannelId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ChannelId")));
                    sb.setPhone(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Phone")));
                    sb.setPincode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Pincode")));
                    sb.setStateId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StateId")));
                    sb.setStateName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StateName")));
                    sb.setStoreCode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreCode")));
                    sb.setStoreId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreId")));
                    sb.setStoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreName")));
                    sb.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreType")));
                    sb.setStoreTypeId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreTypeId")));
                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("UploadStatus")));
                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("VisitDate")));
                    sb.setDistributorId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("DistributorId")));
                    sb.setDistributorName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("DistributorName")));
                    sb.setStoreCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreCategory")));
                    sb.setStoreClassId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreClassId")));
                    sb.setStoreClass(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreClass")));
                    sb.setPosExist(Boolean.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosExist"))));
                    sb.setPosCompany(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosCompany")));
                    sb.setPosRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosRemark")));
                    sb.setTaxType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("TaxType")));
                    sb.setGSTno(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GSTno")));
                    sb.setGSTImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GSTImage")));
                    sb.setStoreCategoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreCategoryId")));
                    sb.setLastMonthScore(dbcursor.getString(dbcursor.getColumnIndexOrThrow("LastMonthScore")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception get JCP!", e.toString());
            return list;
        }


        return list;
    }


    public ArrayList<MappingJourneyPlan> getSpecificDeviationJourneyPlan(String store_cd) {
        ArrayList<MappingJourneyPlan> list = new ArrayList<MappingJourneyPlan>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("SELECT  * from JourneyPlan_NonMerchandised  " + "where StoreId ='" + store_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MappingJourneyPlan sb = new MappingJourneyPlan();
                    sb.setAddress((dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address"))));
                    sb.setCityId((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("CityId"))));
                    sb.setCityName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CityName")));
                    sb.setContactPerson(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ContactPerson")));
                    sb.setEmail(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Email")));
                    sb.setEmpId((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("EmpId"))));
                    sb.setLandmark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Landmark")));
                    sb.setLocation(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Location")));
                    sb.setLongitude(dbcursor.getFloat(dbcursor.getColumnIndexOrThrow("Longitude")));
                    sb.setLatitude(dbcursor.getFloat(dbcursor.getColumnIndexOrThrow("Latitude")));
                    sb.setGeoTag(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GeoTag")));
                    sb.setMID((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("MID"))));
                    sb.setMobile(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Mobile")));
                    sb.setChannelId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ChannelId")));
                    sb.setPhone(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Phone")));
                    sb.setPincode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Pincode")));
                    sb.setStateId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StateId")));
                    sb.setStateName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StateName")));
                    sb.setStoreCode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreCode")));
                    sb.setStoreId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreId")));
                    sb.setStoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreName")));
                    sb.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreType")));
                    sb.setStoreTypeId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreTypeId")));
                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("UploadStatus")));
                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("VisitDate")));
                    sb.setDistributorId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("DistributorId")));
                    sb.setDistributorName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("DistributorName")));
                    sb.setStoreCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreCategory")));
                    sb.setStoreClassId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreClassId")));
                    sb.setStoreClass(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreClass")));
                    sb.setPosExist(Boolean.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosExist"))));
                    sb.setPosCompany(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosCompany")));
                    sb.setPosRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosRemark")));
                    sb.setTaxType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("TaxType")));
                    sb.setGSTno(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GSTno")));
                    sb.setGSTImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GSTImage")));
                    sb.setStoreCategoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreCategoryId")));
                    sb.setLastMonthScore(dbcursor.getString(dbcursor.getColumnIndexOrThrow("LastMonthScore")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception get JCP!", e.toString());
            return list;
        }


        return list;
    }

    public ArrayList<MappingJourneyPlan> getSpecificDeviationNotJourneyPlan(String store_cd) {
        ArrayList<MappingJourneyPlan> list = new ArrayList<MappingJourneyPlan>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("SELECT  * from JourneyPlan_NotCovered  " + "where StateId ='" + store_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MappingJourneyPlan sb = new MappingJourneyPlan();
                    sb.setAddress((dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address"))));
                    sb.setCityId((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("CityId"))));
                    sb.setCityName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CityName")));
                    sb.setContactPerson(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ContactPerson")));
                    sb.setEmail(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Email")));
                    sb.setEmpId((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("EmpId"))));
                    sb.setLandmark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Landmark")));
                    sb.setLocation(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Location")));
                    sb.setLongitude(dbcursor.getFloat(dbcursor.getColumnIndexOrThrow("Longitude")));
                    sb.setLatitude(dbcursor.getFloat(dbcursor.getColumnIndexOrThrow("Latitude")));
                    sb.setGeoTag(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GeoTag")));
                    sb.setMID((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("MID"))));
                    sb.setMobile(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Mobile")));
                    sb.setChannelId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ChannelId")));
                    sb.setPhone(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Phone")));
                    sb.setPincode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Pincode")));
                    sb.setStateId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StateId")));
                    sb.setStateName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StateName")));
                    sb.setStoreCode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreCode")));
                    sb.setStoreId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreId")));
                    sb.setStoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreName")));
                    sb.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreType")));
                    sb.setStoreTypeId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreTypeId")));
                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("UploadStatus")));
                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("VisitDate")));
                    sb.setDistributorId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("DistributorId")));
                    sb.setDistributorName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("DistributorName")));
                    sb.setStoreCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreCategory")));
                    sb.setStoreClassId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreClassId")));
                    sb.setStoreClass(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreClass")));
                    sb.setPosExist(Boolean.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosExist"))));
                    sb.setPosCompany(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosCompany")));
                    sb.setPosRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosRemark")));
                    sb.setTaxType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("TaxType")));
                    sb.setGSTno(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GSTno")));
                    sb.setGSTImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GSTImage")));
                    sb.setStoreCategoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreCategoryId")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception get JCP!", e.toString());
            return list;
        }


        return list;
    }


    public long updateTableMid(String id, Integer mid, String table) {
        ContentValues values = new ContentValues();
        try {
            values.put("MID", mid);
            return db.update(table, values, "StoreId " + " = '" + id + "'", null);
        } catch (Exception ex) {
            Log.e("Exception", " Mapping_JourneyPlan" + ex.toString());
            return 0;
        }
    }

    public void deletePreviousJouneyPlanDBSRData(String visit_date) {
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery("SELECT  * from Journey_Plan_DBSR_Saved where VisitDate <> '" + visit_date + "'", null);
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                int icount = dbcursor.getCount();
                dbcursor.close();
                if (icount > 0) {
                    db.delete(CommonString.TABLE_Journey_Plan_DBSR_Saved, null, null);
                }
                dbcursor.close();
            }

        } catch (Exception e) {

        }

    }


    public ArrayList<MappingJourneyPlan> getSpecificStore_DBSRSavedData(String store_cd) {
        ArrayList<MappingJourneyPlan> list = new ArrayList<MappingJourneyPlan>();
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery("SELECT  * from Journey_Plan_DBSR_Saved  " + "where StoreId ='" + store_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MappingJourneyPlan sb = new MappingJourneyPlan();
                    sb.setMID((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("MID"))));
                    sb.setAddress((dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address"))));
                    sb.setCityId((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("CityId"))));
                    sb.setCityName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CityName")));
                    sb.setContactPerson(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ContactPerson")));
                    sb.setEmail(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Email")));
                    sb.setEmpId((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("EmpId"))));
                    sb.setLandmark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Landmark")));
                    sb.setLocation(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Location")));
                    sb.setLongitude(dbcursor.getFloat(dbcursor.getColumnIndexOrThrow("Longitude")));
                    sb.setLatitude(dbcursor.getFloat(dbcursor.getColumnIndexOrThrow("Latitude")));
                    sb.setGeoTag(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GeoTag")));
                    sb.setMobile(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Mobile")));
                    sb.setChannelId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ChannelId")));
                    sb.setPhone(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Phone")));
                    sb.setPincode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Pincode")));
                    sb.setStateId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StateId")));
                    sb.setStateName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StateName")));
                    sb.setStoreCode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreCode")));
                    sb.setStoreId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreId")));
                    sb.setStoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreName")));
                    sb.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreType")));
                    sb.setStoreTypeId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreTypeId")));
                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("UploadStatus")));
                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("VisitDate")));
                    sb.setDistributorId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("DistributorId")));
                    sb.setDistributorName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("DistributorName")));
                    sb.setStoreCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreCategory")));
                    sb.setStoreClassId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreClassId")));
                    sb.setStoreClass(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreClass")));
                    sb.setPosExist(Boolean.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosExist"))));
                    sb.setPosCompany(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosCompany")));
                    sb.setPosRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosRemark")));
                    sb.setTaxType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("TaxType")));
                    sb.setGSTno(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GSTno")));
                    sb.setGSTImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GSTImage")));
                    sb.setStoreCategoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreCategoryId")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception get JCP!", e.toString());
            return list;
        }


        return list;
    }

    public boolean insertNon_Posm_ReasonData(NonPosmReasonGetterSetter auditQuestionMasterGetSet) {
        db.delete("Non_Posm_Reason", null, null);
        ContentValues values = new ContentValues();
        List<NonPosmReason> data = auditQuestionMasterGetSet.getNonPosmReason();
        try {
            if (data.size() == 0) {
                return false;
            }
            for (int i = 0; i < data.size(); i++) {

                values.put("Reason", data.get(i).getReason());
                values.put("ReasonId", data.get(i).getReasonId());

                long id = db.insert("Non_Posm_Reason", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }


    public ArrayList<MappingJourneyPlan> getStoreCheckTableNot_CoverdData(String date, String staus) {
        ArrayList<MappingJourneyPlan> list = new ArrayList<MappingJourneyPlan>();
        Cursor dbcursor = null;
        try {


         //   dbcursor = db.rawQuery("SELECT  * FROM Mapping_JourneyPlan WHERE UploadStatus ='" + staus + "'  AND VisitDate = '" + date + "'", null);
            dbcursor = db.rawQuery("SELECT  * FROM Mapping_JourneyPlan WHERE UploadStatus ='" + staus +"'  AND VisitDate = '" + date +
                    "' UNION SELECT  * FROM JourneyPlan_NonMerchandised  WHERE UploadStatus ='" + staus +"' and VisitDate = '" + date + "'", null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MappingJourneyPlan sb = new MappingJourneyPlan();
                    sb.setAddress((dbcursor.getString(dbcursor.getColumnIndexOrThrow("Address"))));
                    sb.setCityId((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("CityId"))));
                    sb.setCityName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("CityName")));
                    sb.setContactPerson(dbcursor.getString(dbcursor.getColumnIndexOrThrow("ContactPerson")));
                    sb.setEmail(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Email")));
                    sb.setEmpId((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("EmpId"))));
                    sb.setLandmark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Landmark")));
                    sb.setLocation(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Location")));
                    sb.setLongitude(dbcursor.getFloat(dbcursor.getColumnIndexOrThrow("Longitude")));
                    sb.setLatitude(dbcursor.getFloat(dbcursor.getColumnIndexOrThrow("Latitude")));
                    sb.setGeoTag(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GeoTag")));
                    sb.setMID((dbcursor.getInt(dbcursor.getColumnIndexOrThrow("MID"))));
                    sb.setMobile(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Mobile")));
                    sb.setChannelId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ChannelId")));
                    sb.setPhone(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Phone")));
                    sb.setPincode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Pincode")));
                    sb.setStateId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StateId")));
                    sb.setStateName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StateName")));
                    sb.setStoreCode(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreCode")));
                    sb.setStoreId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreId")));
                    sb.setStoreName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreName")));
                    sb.setStoreType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreType")));
                    sb.setStoreTypeId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreTypeId")));
                    sb.setUploadStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow("UploadStatus")));
                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow("VisitDate")));
                    sb.setDistributorId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("DistributorId")));
                    sb.setDistributorName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("DistributorName")));
                    sb.setStoreCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreCategory")));
                    sb.setStoreClassId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreClassId")));
                    sb.setStoreClass(dbcursor.getString(dbcursor.getColumnIndexOrThrow("StoreClass")));
                    sb.setPosExist(Boolean.valueOf(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosExist"))));
                    sb.setPosCompany(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosCompany")));
                    sb.setPosRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosRemark")));
                    sb.setTaxType(dbcursor.getString(dbcursor.getColumnIndexOrThrow("TaxType")));
                    sb.setGSTno(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GSTno")));
                    sb.setGSTImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow("GSTImage")));
                    sb.setStoreCategoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("StoreCategoryId")));
                    sb.setLastMonthScore(dbcursor.getString(dbcursor.getColumnIndexOrThrow("LastMonthScore")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception get JCP!", e.toString());
            return list;
        }


        return list;
    }


    public void updateStoreStatusDBSR(String storeid, String visitdate, String status) {
        try {
            ContentValues values = new ContentValues();
            values.put("UploadStatus", status);
            db.update("Journey_Plan_DBSR_Saved", values, "StoreId ='" + storeid + "' AND VisitDate ='" + visitdate + "'", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long deletecomppromotion(MappingJourneyPlan jcp, String keyId, boolean flagcomp_promotion) {
        long l = 0;
        try {
            String table = "";
            if (flagcomp_promotion) {
                table = CommonString.TABLE_COMP_PROMOTION;
            } else {
                table = CommonString.TABLE_COMP_NPD;
            }
            if (!db.isOpen()) {
                open();
            }
            if (keyId != null) {
                l = db.delete(table, CommonString.KEY_ID + "=" + keyId + "", null);
            } else {
                l = db.delete(table, CommonString.KEY_STORE_ID + "=" + jcp.getStoreId() + "", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }


    public long deleteVQPS(MappingJourneyPlan jcp, String keyId) {
        long l = 0;
        try {
            String table = CommonString.TABLE_VQPS;
            if (!db.isOpen()) {
                open();
            }
            if (keyId != null) {
                l = db.delete(table, CommonString.KEY_ID + "=" + keyId + "", null);
            } else {
                l = db.delete(table, CommonString.KEY_STORE_ID + "=" + jcp.getStoreId() + "", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }


    public long insertcomppromotion(MappingJourneyPlan jcp, ArrayList<MasterCompany> list) {
        long l = 0;
        l = db.delete(CommonString.TABLE_COMP_PROMOTION, CommonString.KEY_STORE_ID + "=" + jcp.getStoreId() + " AND "
                + CommonString.KEY_VISIT_DATE + "='" + jcp.getVisitDate() + "'", null);
        ContentValues values = new ContentValues();
        try {

            for (int i = 0; i < list.size(); i++) {
                values.put(CommonString.KEY_STORE_ID, jcp.getStoreId());
                values.put(CommonString.KEY_VISIT_DATE, jcp.getVisitDate());
                String present = list.get(i).getIspresent();
                values.put(CommonString.KEY_PRESENT, present);
                if (present != null && !present.equals("") && present.equals("1")) {
                    values.put(CommonString.KEY_BRAND, list.get(i).getBrand_name());
                    values.put(CommonString.KEY_BRAND_ID, list.get(i).getBrandId());
                    values.put(CommonString.KEY_COMPANY_ID, list.get(i).getCompanyId());
                    values.put(CommonString.KEY_COMPANY, list.get(i).getCompany());
                    values.put(CommonString.KEY_PROMOTYPE_iD, list.get(i).getProTypeId());
                    values.put(CommonString.KEY_PROMOTYPE, list.get(i).getPromoType());
                    values.put(CommonString.KEY_IMAGE2, list.get(i).getComp_img2());
                    values.put(CommonString.KEY_IMAGE1, list.get(i).getComp_img1());
                    values.put(CommonString.KEY_REMARK, list.get(i).getDesc());
                } else {

                    values.put(CommonString.KEY_BRAND, "");
                    values.put(CommonString.KEY_BRAND_ID, 0);
                    values.put(CommonString.KEY_COMPANY_ID, 0);
                    values.put(CommonString.KEY_COMPANY, "");
                    values.put(CommonString.KEY_PROMOTYPE_iD, 0);
                    values.put(CommonString.KEY_PROMOTYPE, "");
                    values.put(CommonString.KEY_IMAGE2, "");
                    values.put(CommonString.KEY_IMAGE1, "");
                    values.put(CommonString.KEY_REMARK, "");
                }

                l = db.insert(CommonString.TABLE_COMP_PROMOTION, null, values);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return l;
    }


    public long insertcompNPDLaunch(MappingJourneyPlan jcp, ArrayList<MasterCompany> list) {
        long l = 0;
        l = db.delete(CommonString.TABLE_COMP_NPD, CommonString.KEY_STORE_ID + "=" + jcp.getStoreId() + " AND "
                + CommonString.KEY_VISIT_DATE + "='" + jcp.getVisitDate() + "'", null);
        ContentValues values = new ContentValues();
        try {

            for (int i = 0; i < list.size(); i++) {
                values.put(CommonString.KEY_STORE_ID, jcp.getStoreId());
                values.put(CommonString.KEY_VISIT_DATE, jcp.getVisitDate());
                String present = list.get(i).getIspresent();
                values.put(CommonString.KEY_PRESENT, present);
                if (present != null && !present.equals("") && present.equals("1")) {
                    values.put(CommonString.KEY_COMPANY_ID, list.get(i).getCompanyId());
                    values.put(CommonString.KEY_COMPANY, list.get(i).getCompany());
                    values.put(CommonString.KEY_CATEGORY_ID, list.get(i).getCategoryId());
                    values.put(CommonString.KEY_CATEGORY, list.get(i).getCategorY());

                    values.put(CommonString.KEY_BRAND, list.get(i).getBrand_name());
                    values.put(CommonString.KEY_BRAND_ID, list.get(i).getBrandId());

                    values.put(CommonString.KEY_IMAGE2, list.get(i).getComp_img2());
                    values.put(CommonString.KEY_IMAGE1, list.get(i).getComp_img1());
                    values.put(CommonString.KEY_REMARK, list.get(i).getDesc());
                } else {

                    values.put(CommonString.KEY_COMPANY_ID, 0);
                    values.put(CommonString.KEY_COMPANY, "");
                    values.put(CommonString.KEY_CATEGORY_ID, 0);
                    values.put(CommonString.KEY_CATEGORY, "");

                    values.put(CommonString.KEY_BRAND, "");
                    values.put(CommonString.KEY_BRAND_ID, 0);
                    values.put(CommonString.KEY_IMAGE2, "");
                    values.put(CommonString.KEY_IMAGE1, "");
                    values.put(CommonString.KEY_REMARK, "");
                }

                l = db.insert(CommonString.TABLE_COMP_NPD, null, values);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return l;
    }


    public long insertVQPS(MappingJourneyPlan jcp, ArrayList<MasterCompany> list) {
        long l = 0;
        l = db.delete(CommonString.TABLE_VQPS, CommonString.KEY_STORE_ID + "=" + jcp.getStoreId() + " AND "
                + CommonString.KEY_VISIT_DATE + "='" + jcp.getVisitDate() + "'", null);
        ContentValues values = new ContentValues();
        try {

            for (int i = 0; i < list.size(); i++) {
                values.put(CommonString.KEY_STORE_ID, jcp.getStoreId());
                values.put(CommonString.KEY_VISIT_DATE, jcp.getVisitDate());
                String present = list.get(i).getIspresent();
                values.put(CommonString.KEY_PRESENT, present);
                if (present != null && !present.equals("") && present.equals("1")) {
                    values.put(CommonString.KEY_CATEGORY_ID, list.get(i).getCategoryId());
                    values.put(CommonString.KEY_CATEGORY, list.get(i).getCategorY());
                    values.put(CommonString.KEY_ASSET_ID, list.get(i).getAssetId());
                    values.put(CommonString.KEY_ASSET, list.get(i).getAsset_name());
                    values.put(CommonString.KEY_IMAGE, list.get(i).getComp_img1());
                } else {
                    values.put(CommonString.KEY_CATEGORY_ID, 0);
                    values.put(CommonString.KEY_CATEGORY, "");
                    values.put(CommonString.KEY_ASSET, "");
                    values.put(CommonString.KEY_ASSET_ID, 0);
                    values.put(CommonString.KEY_IMAGE, "");
                }

                l = db.insert(CommonString.TABLE_VQPS, null, values);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return l;
    }

    public ArrayList<MasterCompany> getinsertedcomp_promotion(MappingJourneyPlan jcp, boolean forcomp_promo) {
        ArrayList<MasterCompany> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {

            if (forcomp_promo) {
                dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_COMP_PROMOTION + " WHERE " + CommonString.KEY_STORE_ID + "=" + jcp.getStoreId() + " AND "
                        + CommonString.KEY_VISIT_DATE + "='" + jcp.getVisitDate() + "'", null);
            } else {
                dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_COMP_NPD + " WHERE " + CommonString.KEY_STORE_ID + "="
                        + jcp.getStoreId() + " AND " + CommonString.KEY_VISIT_DATE + "='" + jcp.getVisitDate() + "'", null);

            }

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterCompany sb = new MasterCompany();
                    sb.setKeyId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ID)));
                    sb.setIspresent(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PRESENT)));
                    sb.setBrand_name(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND)));
                    sb.setBrandId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND_ID)));
                    sb.setCompany(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_COMPANY)));
                    sb.setCompanyId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_COMPANY_ID)));

                    if (forcomp_promo) {
                        sb.setPromoType(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PROMOTYPE)));
                        sb.setProTypeId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PROMOTYPE_iD)));
                    } else {
                        sb.setCategorY(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CATEGORY)));
                        sb.setCategoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CATEGORY_ID)));
                    }

                    sb.setComp_img2(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE2)));
                    sb.setComp_img1(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE1)));
                    sb.setDesc(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REMARK)));
                    list.add(sb);
                    dbcursor.moveToNext();
                }

                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            return list;
        }

        return list;
    }


    public ArrayList<MasterCompany> getcomp_promotionforupload(String storeId, String visit_date, boolean forcomp_promo) {
        ArrayList<MasterCompany> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {

            if (forcomp_promo) {
                dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_COMP_PROMOTION + " WHERE " + CommonString.KEY_STORE_ID + "=" + storeId + " AND "
                        + CommonString.KEY_VISIT_DATE + "='" + visit_date + "'", null);
            } else {
                dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_COMP_NPD + " WHERE " + CommonString.KEY_STORE_ID + "="
                        + storeId + " AND " + CommonString.KEY_VISIT_DATE + "='" + visit_date + "'", null);

            }

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterCompany sb = new MasterCompany();
                    sb.setKeyId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ID)));
                    sb.setIspresent(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PRESENT)));
                    sb.setBrandId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND_ID)));
                    sb.setCompanyId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_COMPANY_ID)));

                    if (forcomp_promo) {
                        sb.setProTypeId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PROMOTYPE_iD)));
                    } else {
                        sb.setCategoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CATEGORY_ID)));
                    }

                    sb.setComp_img2(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE2)));
                    sb.setComp_img1(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE1)));
                    sb.setDesc(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REMARK)));
                    list.add(sb);
                    dbcursor.moveToNext();
                }

                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            return list;
        }

        return list;
    }


    public ArrayList<MasterCompany> getinsertedVQPS(MappingJourneyPlan jcp) {
        ArrayList<MasterCompany> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_VQPS + " WHERE " + CommonString.KEY_STORE_ID + "="
                    + jcp.getStoreId() + " AND " + CommonString.KEY_VISIT_DATE + "='" + jcp.getVisitDate() + "'", null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterCompany sb = new MasterCompany();
                    sb.setKeyId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ID)));
                    sb.setIspresent(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PRESENT)));
                    sb.setCategorY(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CATEGORY)));
                    sb.setCategoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CATEGORY_ID)));
                    sb.setAsset_name(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ASSET)));
                    sb.setAssetId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ASSET_ID)));
                    sb.setComp_img1(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE)));

                    list.add(sb);

                    dbcursor.moveToNext();
                }

                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            return list;
        }

        return list;
    }


    public ArrayList<MasterCompany> getVQPSforupload(String storeId, String visit_date) {
        ArrayList<MasterCompany> list = new ArrayList<>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_VQPS + " WHERE " + CommonString.KEY_STORE_ID + "="
                    + storeId + " AND " + CommonString.KEY_VISIT_DATE + "='" + visit_date + "'", null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterCompany sb = new MasterCompany();
                    sb.setKeyId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ID)));
                    sb.setIspresent(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PRESENT)));
                    sb.setCategorY(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CATEGORY)));
                    sb.setCategoryId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CATEGORY_ID)));
                    sb.setAsset_name(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ASSET)));
                    sb.setAssetId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ASSET_ID)));
                    sb.setComp_img1(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE)));

                    list.add(sb);

                    dbcursor.moveToNext();
                }

                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            return list;
        }

        return list;
    }

    public ArrayList<CoverageBean> getstorecheckinTym(MappingJourneyPlan jcp, String jcpType) {
        ArrayList<CoverageBean> list = new ArrayList<CoverageBean>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT  * FROM " + CommonString.TABLE_STORE_TIME + " WHERE " + CommonString.KEY_VISIT_DATE + "='"
                    + jcp.getVisitDate() + "' AND " + CommonString.KEY_STORE_ID + "=" + jcp.getStoreId() +
                    " AND " + CommonString.KEY_JCP_TYPE + "='" + jcpType + "'", null);

            if (dbcursor != null) {

                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    CoverageBean sb = new CoverageBean();

                    sb.setIntime(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CHECKIN_TIME)));
                    sb.setOutTime(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CHECKOUT_TIME)));
                    sb.setVisitDate(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE)));
                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            Log.d("Exception get JCP!", e.toString());
            return list;
        }

        return list;
    }

    public long InsertIntimeOutTime(CoverageBean data) {
        long l = 0;
        l = db.delete(CommonString.TABLE_STORE_TIME, CommonString.KEY_STORE_ID + "=" + data.getStoreId() + " AND " +
                CommonString.KEY_VISIT_DATE + "='" + data.getVisitDate() + "'", null);
        ContentValues values = new ContentValues();
        try {
            values.put(CommonString.KEY_STORE_ID, data.getStoreId());
            values.put(CommonString.KEY_VISIT_DATE, data.getVisitDate());
            values.put(CommonString.KEY_CHECKIN_TIME, data.getIntime());
            values.put(CommonString.KEY_CHECKOUT_TIME, data.getOutTime());
            values.put(CommonString.KEY_JCP_TYPE, data.getJcp_type());
            l = db.insert(CommonString.TABLE_STORE_TIME, null, values);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return l;
    }


    public long updateoutTime(MappingJourneyPlan jcp, String outTime, String table) {

        long l = 0;
        try {

            ContentValues values = new ContentValues();
            values.put(CommonString.KEY_CHECKOUT_TIME, outTime);
            l = db.update(CommonString.TABLE_STORE_TIME, values, CommonString.KEY_STORE_ID + " =" + jcp.getStoreId() +
                    " AND " + CommonString.KEY_VISIT_DATE + " ='" + jcp.getVisitDate() + "' AND " + CommonString.KEY_JCP_TYPE + "='" + table + "'", null);
        } catch (Exception ex) {
            Log.e("Exception", " Mapping_JourneyPlan" + ex.toString());
            return 0;
        }

        return l;
    }


    public long delete_storeTym(String storeid) {
        long l = 0;

        try {
            l = db.delete(CommonString.TABLE_STORE_TIME, CommonString.KEY_STORE_ID + "='" + storeid + "'", null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return l;
    }

    public long InsertVisibilityDriveData(ArrayList<ViisbilityDriveGetterSetter> data) {
        long l = 0;
        ContentValues values = new ContentValues();
        try {
            values.put(CommonString.KEY_USER_ID, data.get(0).getUserd());
            values.put(CommonString.KEY_VISIT_DATE, data.get(0).getVisitdate());
            values.put(CommonString.KEY_STORE_NAME, data.get(0).getStorename());
            values.put(CommonString.KEY_STORE_CITY, data.get(0).getCity());
            values.put(CommonString.KEY_SHOP_BOARD_IMAGE, data.get(0).getImage_shop_board());
            values.put(CommonString.KEY_MAGIC_STICK_ID, data.get(0).getMagic_stick_id());
            values.put(CommonString.KEY_MAGIC_STICK_NAME, data.get(0).getMagic_stick_name());
            values.put(CommonString.KEY_IMAGE_LONG, data.get(0).getImage_long());
            values.put(CommonString.KEY_IMAGE_CLOSE, data.get(0).getImage_close());
            values.put(CommonString.KEY_PRESENT, data.get(0).getPresent());
            values.put(CommonString.KEY_PRESENT_DEPLOYED, data.get(0).getPresent_deployed());
            values.put(CommonString.KEY_VISIBILITY_DEPLOYED_ID, data.get(0).getVisibility_deployed_id());
            values.put(CommonString.KEY_VISIBILITY_DEPLOYED_NAME, data.get(0).getVisibility_deployed_name());


            l = db.insert(CommonString.TABLE_VISIBILITY_DRIVE_DATA, null, values);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return l;
    }


    public boolean insertVisibilityDriveData(MappingVQPSVisibilityDriveGetterSetter nonWorkingdata) {
        db.delete("Mapping_VQPS_VisibilityDrive", null, null);
        ContentValues values = new ContentValues();
        List<MappingVQPSVisibilityDrive> data = nonWorkingdata.getMappingVQPSVisibilityDrive();
        try {
            if (data.size() == 0) {
                return false;
            }

            for (int i = 0; i < data.size(); i++) {

                values.put("PosmId", data.get(i).getPosmId());

                long id = db.insert("Mapping_VQPS_VisibilityDrive", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Database Exception  ", ex.toString());
            return false;
        }
    }

    public ArrayList<MappingVQPSVisibilityDrive> getVisibilityDriveData() {
        ArrayList<MappingVQPSVisibilityDrive> list = new ArrayList<MappingVQPSVisibilityDrive>();
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery(" SELECT DISTINCT Mp.PosmName, Mp.PosmId FROM Master_Posm Mp INNER JOIN Mapping_VQPS_VisibilityDrive Mv ON Mp.PosmId = Mv.PosmId ", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MappingVQPSVisibilityDrive sb = new MappingVQPSVisibilityDrive();

                    sb.setPosmId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("PosmId")));
                    sb.setPosmName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosmName")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            return list;
        }
        return list;
    }

    public ArrayList<MasterDriveNonVisibility> getMasterDriveNonVisibilityData() {
        ArrayList<MasterDriveNonVisibility> list = new ArrayList<MasterDriveNonVisibility>();
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery(" SELECT *  FROM Master_DriveNonVisibility ", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterDriveNonVisibility sb = new MasterDriveNonVisibility();

                    sb.setReasonId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ReasonId")));
                    sb.setReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            return list;
        }
        return list;
    }

    public ArrayList<MappingVQPSVisibilityDrive> getRDVisibilityDriveData() {
        ArrayList<MappingVQPSVisibilityDrive> list = new ArrayList<MappingVQPSVisibilityDrive>();
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery(" SELECT DISTINCT Mp.PosmName, Mp.PosmId FROM Master_Posm Mp INNER JOIN Mapping_RD_VisibilityDrive Mv ON Mp.PosmId = Mv.PosmId ", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MappingVQPSVisibilityDrive sb = new MappingVQPSVisibilityDrive();

                    sb.setPosmId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("PosmId")));
                    sb.setPosmName(dbcursor.getString(dbcursor.getColumnIndexOrThrow("PosmName")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            return list;
        }
        return list;
    }

    public ArrayList<ViisbilityDriveGetterSetter> getStoreChekingSup(String visitdate) {

        ArrayList<ViisbilityDriveGetterSetter> list = new ArrayList<ViisbilityDriveGetterSetter>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("select * From " + CommonString.TABLE_VISIBILITY_DRIVE_DATA + " where " + CommonString.KEY_VISIT_DATE + " = '" + visitdate + "'", null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    ViisbilityDriveGetterSetter pgs = new ViisbilityDriveGetterSetter();

                    pgs.setStorename(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_NAME)));
                    pgs.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_CITY)));
                    pgs.setMagic_stick_name(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_MAGIC_STICK_NAME)));
                    list.add(pgs);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            return list;
        }

        return list;

    }


    public long InsertRDVisibilityDriveData(ArrayList<ViisbilityDriveGetterSetter> data) {
        long l = 0;
        ContentValues values = new ContentValues();
        try {
            values.put(CommonString.KEY_USER_ID, data.get(0).getUserd());
            values.put(CommonString.KEY_VISIT_DATE, data.get(0).getVisitdate());
            values.put(CommonString.KEY_STORE_NAME, data.get(0).getStorename());
            values.put(CommonString.KEY_STORE_CITY, data.get(0).getCity());
            values.put(CommonString.KEY_SHOP_BOARD_IMAGE, data.get(0).getImage_shop_board());
            values.put(CommonString.KEY_MAGIC_STICK_ID, data.get(0).getMagic_stick_id());
            values.put(CommonString.KEY_MAGIC_STICK_NAME, data.get(0).getMagic_stick_name());
            values.put(CommonString.KEY_IMAGE_LONG, data.get(0).getImage_long());
            values.put(CommonString.KEY_IMAGE_CLOSE, data.get(0).getImage_close());
            values.put(CommonString.KEY_PRESENT, data.get(0).getPresent());
            values.put(CommonString.KEY_PRESENT_DEPLOYED, data.get(0).getPresent_deployed());
            values.put(CommonString.KEY_VISIBILITY_DEPLOYED_ID, data.get(0).getVisibility_deployed_id());
            values.put(CommonString.KEY_VISIBILITY_DEPLOYED_NAME, data.get(0).getVisibility_deployed_name());
            l = db.insert(CommonString.TABLE_RDVISIBILITY_DRIVE_DATA, null, values);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return l;
    }
    public ArrayList<ViisbilityDriveGetterSetter> getStoreRDVisibility(String visitDate) {

        ArrayList<ViisbilityDriveGetterSetter> list = new ArrayList<ViisbilityDriveGetterSetter>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("select * From " + CommonString.TABLE_RDVISIBILITY_DRIVE_DATA + " where " + CommonString.KEY_VISIT_DATE + " = '" + visitDate + "'", null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    ViisbilityDriveGetterSetter pgs = new ViisbilityDriveGetterSetter();

                    pgs.setStorename(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_NAME)));
                    pgs.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_CITY)));
                    pgs.setMagic_stick_name(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_MAGIC_STICK_NAME)));

                    list.add(pgs);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            return list;
        }

        return list;

    }




    public ArrayList<ViisbilityDriveGetterSetter> getStoreRDVisibilityUpload(String storeId, String visit_date) {

        ArrayList<ViisbilityDriveGetterSetter> list = new ArrayList<ViisbilityDriveGetterSetter>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_RDVISIBILITY_DRIVE_DATA + " WHERE " + CommonString.KEY_STORE_ID + "="
                    + storeId + " AND " + CommonString.KEY_VISIT_DATE + "='" + visit_date + "'", null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    ViisbilityDriveGetterSetter pgs = new ViisbilityDriveGetterSetter();

                    pgs.setStorename(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_NAME)));
                    pgs.setCity(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_CITY)));
                    pgs.setImage_shop_board(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SHOP_BOARD_IMAGE)));
                    pgs.setMagic_stick_id(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_MAGIC_STICK_ID)));
                    pgs.setPresent(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PRESENT)));
                    pgs.setImage_long(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE_LONG)));
                    pgs.setImage_close(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE_CLOSE)));




                    list.add(pgs);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            return list;
        }

        return list;

    }

    public boolean insertRDVisibilityDriveData(MappingRDVisibilityDriveGetterSetter nonWorkingdata) {
        db.delete("Mapping_RD_VisibilityDrive", null, null);
        ContentValues values = new ContentValues();
        List<MappingRDVisibilityDrive> data = nonWorkingdata.getMappingRDVisibilityDrive();
        try {
            if (data.size() == 0) {
                return false;
            }

            for (int i = 0; i < data.size(); i++) {

                values.put("PosmId", data.get(i).getPosmId());

                long id = db.insert("Mapping_RD_VisibilityDrive", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Database Exception  ", ex.toString());
            return false;
        }
    }



    public boolean insertMaster_DriveNonVisibilityData(MasterDriveNonVisibilityGetterSetter nonWorkingdata) {
        db.delete("Master_DriveNonVisibility", null, null);
        ContentValues values = new ContentValues();
        List<MasterDriveNonVisibility> data = nonWorkingdata.getMasterDriveNonVisibility();
        try {
            if (data.size() == 0) {
                return false;
            }

            for (int i = 0; i < data.size(); i++) {

                values.put("Reason", data.get(i).getReason());
                values.put("ReasonId", data.get(i).getReasonId());

                long id = db.insert("Master_DriveNonVisibility", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Database Exception  ", ex.toString());
            return false;
        }
    }

    public boolean insertMaster_NonProgramReason(MasterNonProgramReasonGetterSetter nonWorkingdata) {
        db.delete("Master_NonProgramReason", null, null);
        ContentValues values = new ContentValues();
        List<MasterNonProgramReason> data = nonWorkingdata.getMasterNonProgramReason();
        try {
            if (data.size() == 0) {
                return false;
            }

            for (int i = 0; i < data.size(); i++) {

                values.put("Reason", data.get(i).getReason());
                values.put("ReasonId", data.get(i).getReasonId());

                long id = db.insert("Master_NonProgramReason", null, values);
                if (id == -1) {
                    throw new Exception();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Database Exception  ", ex.toString());
            return false;
        }
    }

    public ArrayList<MasterNonProgramReason> getMaster_NonProgramReasonData() {
        ArrayList<MasterNonProgramReason> list = new ArrayList<MasterNonProgramReason>();
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery(" SELECT *  FROM Master_NonProgramReason ", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MasterNonProgramReason sb = new MasterNonProgramReason();

                    sb.setReasonId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow("ReasonId")));
                    sb.setReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow("Reason")));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }
        } catch (Exception e) {
            return list;
        }
        return list;
    }


}
