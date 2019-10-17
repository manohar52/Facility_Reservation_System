package model;

import android.content.Context;
import android.database.Cursor;

import java.util.Hashtable;

import DOA.facility_doa;

public class Facility {
    private String name;
    private String ftype;
    private String fdesc;
    private float interval;
    private int duration;
    private String venue;
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
    private void loadDetails(Cursor c){
        this.setFtype(c.getString(c.getColumnIndex("ftype")));
        this.setFdesc(c.getString(c.getColumnIndex("fdesc")));
        this.setInterval(c.getFloat(c.getColumnIndex("interval")));
        this.setDuration(c.getInt(c.getColumnIndex("duration")));
        this.setVenue(c.getString(c.getColumnIndex("venue")));
        this.setDeposit(c.getInt(c.getColumnIndex("deposit")));
    }
    private void loadDetails(){
        facility_doa fdoa = facility_doa.getInstance(ct);
        Cursor c = fdoa.getFacilityData(this.name);

        this.setFtype(c.getString(c.getColumnIndex("ftype")));
        this.setFdesc(c.getString(c.getColumnIndex("fdesc")));
        this.setInterval(c.getFloat(c.getColumnIndex("interval")));
        this.setDuration(c.getInt(c.getColumnIndex("duration")));
        this.setVenue(c.getString(c.getColumnIndex("venue")));
        this.setDeposit(c.getInt(c.getColumnIndex("deposit")));
    }

    public boolean isAvailable(String date, String time){

        return true;
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

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getInterval() {
        return interval;
    }

    public void setInterval(float interval) {
        this.interval = interval;
    }

    public String getFdesc() {
        return fdesc;
    }

    public void setFdesc(String fdesc) {
        this.fdesc = fdesc;
    }

    public String getFtype() {
        return ftype;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype;
    }
}
