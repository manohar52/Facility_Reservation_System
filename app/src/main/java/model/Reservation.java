package model;

import android.content.Context;

import DOA.reservation_doa;

public class Reservation {
    private long resId;
    private User user;
    private Facility facility;
    private String date;
    private String stime;
    private String etime;
    private String noshow;
    private String violation;
    private static Context context;

    public Reservation(Context ct,User user,Facility f,String date, String stime, String etime){
        if (ct!=null){
            context = ct;
        }
        this.setUser(user);
        this.setFacility(f);
        this.setDate(date);
        this.setStime(stime);
        this.setEtime(etime);
    }

    public long getResId() {
        return resId;
    }

    public void setResId(long resId) {
        this.resId = resId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
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
    }
}
