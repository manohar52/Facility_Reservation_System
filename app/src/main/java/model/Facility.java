package model;

import android.content.Context;
import android.database.Cursor;

import java.util.Hashtable;

import DOA.facility_doa;

public class Facility {
    private String name;
    private FacilityType ftype;
    private int availability;
    private String status;
    private String floor;
    private String wing;
    private int deposit;

    private static Context ct;
    private static Hashtable<String,Facility> facilities = new Hashtable<String,Facility>();

    private Facility(String name){
        this.name = name;
    }

    public static Hashtable<String,Facility> getAllFacilities(Context ct){
        facility_doa fdoa = facility_doa.getInstance(ct);
        Cursor c = fdoa.loadAllData();
        while(c.moveToNext()){
            String name = c.getString(c.getColumnIndex("name"));
            Facility f = new Facility(name);
            f.loadDetails(c);
            facilities.put(name,f);
        }
        return facilities;
    }

    public static Facility getInstance(String name, Context context){

        if (ct == null){
            ct = context;
        }

        if (!facilities.containsKey(name)){
            Facility f = new Facility(name);
            f.loadDetails();
            facilities.put(name,f);
        }
        return facilities.get(name);
    }
    private void loadFacilityDetails(Cursor c){

        this.setDeposit(c.getInt(c.getColumnIndex("deposit")));
    }
    private void loadDetails(){
        facility_doa fdoa = facility_doa.getInstance(ct);
        Cursor c = fdoa.getFacilityData(this.name);

        FacilityType ft = FacilityType.getInstance(c.getString(c.getColumnIndex("fdesc")),ct);
        this.setFtype(ft);

        this.setAvailability(c.getInt(c.getColumnIndex("availability")));
        this.setStatus(c.getString(c.getColumnIndex("status")));
        this.setFloor(c.getString(c.getColumnIndex("floor")));
        this.setWing(c.getString(c.getColumnIndex("wing")));
        this.setDeposit(c.getInt(c.getColumnIndex("deposit")));
    }
    private void loadDetails(Cursor c){
        FacilityType ft = FacilityType.getInstance(c.getString(c.getColumnIndex("fdesc")),ct);
        this.setFtype(ft);

        this.setAvailability(c.getInt(c.getColumnIndex("availability")));
        this.setStatus(c.getString(c.getColumnIndex("status")));
        this.setFloor(c.getString(c.getColumnIndex("floor")));
        this.setWing(c.getString(c.getColumnIndex("wing")));
        this.setDeposit(c.getInt(c.getColumnIndex("deposit")));
    }



    public boolean isAvailable(String date, String time){
        facility_doa fdoa = facility_doa.getInstance(ct);
        if(fdoa.checkAvailability(this, date, time)){
            return true;
        }
        else{
            return false;
        }
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public FacilityType getFtype() {
        return ftype;
    }

    public void setFtype(FacilityType ftype) {
        this.ftype = ftype;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getWing() {
        return wing;
    }

    public void setWing(String wing) {
        this.wing = wing;
    }
}
