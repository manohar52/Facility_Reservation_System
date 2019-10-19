package model;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import DOA.reservation_doa;

public class Reservation {
    private long resId;
    private String username;
    private String facName;
    private String date;
    private String stime;
    private String etime;
    private String noshow;
    private String violation;
    private static Context context;
//    private static Hashtable<String,Reservation> resList = new Hashtable<>();
    private static List<Reservation> resList = new ArrayList<>();
    public Reservation(Context ct,String username,String fname,String date, String stime, String etime){
        if (ct!=null){
            context = ct;
        }
        this.setUsername(username);
        this.setFacName(fname);
        this.setDate(date);
        this.setStime(stime);
        this.setEtime(etime);
    }

    public static List<Reservation> getAllReservations(Context ct,String username){

        reservation_doa rdoa = reservation_doa.getInstance(context);


        if (resList.isEmpty()){
            Cursor c = rdoa.getReservationsForUser(username);
            do{
                long resid = c.getLong(c.getColumnIndex("resid"));
                String fname = c.getString(c.getColumnIndex("fname"));
                String date = c.getString(c.getColumnIndex("date"));
                String stime = c.getString(c.getColumnIndex("stime"));
                String etime = c.getString(c.getColumnIndex("etime"));
                String ns = c.getString(c.getColumnIndex("noshow"));
                String vio = c.getString(c.getColumnIndex("violation"));

                Reservation reservation = new Reservation(ct,username,fname,date,stime,etime);
                reservation.setResId(resid);
                reservation.setNoshow(ns);
                reservation.setViolation(vio);

                resList.add(reservation);
            }while(c.moveToNext());
        }
        return resList;
    }

    public long getResId() {
        return resId;
    }

    public void setResId(long resId) {
        this.resId = resId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getNoshow() {
        return noshow;
    }

    public void setNoshow(String noshow) {
        this.noshow = noshow;
    }

    public String getViolation() {
        return violation;
    }

    public void setViolation(String violation) {
        this.violation = violation;
    }

    public void save() {
        reservation_doa rdoa = reservation_doa.getInstance(context);
        this.resId = rdoa.createReservation(this);
        resList.add(this);

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFacName() {
        return facName;
    }

    public void setFacName(String facName) {
        this.facName = facName;
    }
}
