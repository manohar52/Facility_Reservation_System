package model;

import android.content.Context;
import android.database.Cursor;
import java.util.Hashtable;


import DOA.facility_doa;

public class FacilityType {
    private String ftype;
    private String fdesc;
    private float interval;
    private int duration;
    private String venue;
//    private List<String> facilities = new ArrayList<>();
    private static Context ct;
    private static Hashtable<String,FacilityType> facilityTypes = new Hashtable<>();

    private FacilityType(String fdesc){
        this.fdesc = fdesc;
    }

    public static FacilityType getInstance(String fdesc, Context context){

        if (ct == null){
            ct = context;
        }

        if (!facilityTypes.containsKey(fdesc)){
            FacilityType f = new FacilityType(fdesc);
            f.loadDetails();
            facilityTypes.put(fdesc,f);
        }
        return facilityTypes.get(fdesc);
    }

    private void loadDetails(){
        facility_doa fdoa = facility_doa.getInstance(ct);
        Cursor c = fdoa.getFacilityTypeData(this.fdesc);

//        this.setFtype(c.getString(c.getColumnIndex("fdesc")));
        this.setInterval(c.getFloat(c.getColumnIndex("interval")));
        this.setDuration(c.getInt(c.getColumnIndex("duration")));
        this.setVenue(c.getString(c.getColumnIndex("venue")));


//        Cursor c1 = fdoa.loadAllData(this.fdesc);
//        while(c.moveToNext()){
//            String name = c.getString(c.getColumnIndex("name"));
//            this.facilities.add(name);
//        }
    }


    public String getFtype() {
        return ftype;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype;
    }

    public String getFdesc() {
        return fdesc;
    }

    public void setFdesc(String fdesc) {
        this.fdesc = fdesc;
    }

    public float getInterval() {
        return interval;
    }

    public void setInterval(float interval) {
        this.interval = interval;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

//    public List<String> getFacilities() {
//        return facilities;
//    }
//
//    public void setFacilities(List<String> facilities) {
//        this.facilities = facilities;
//    }
}
