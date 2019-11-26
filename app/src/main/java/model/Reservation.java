package model;

import android.content.Context;
import android.database.Cursor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
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
        this.setNoshow("0");
        this.setViolation("0");
    }
public static Reservation getInstance(long id){
    Iterator iter = resList.iterator();
    while (iter.hasNext()){
        Reservation r = (Reservation) iter.next();
        if (r.getResId() == id){
            return r;
        }
    }
    return null;
}
    public static List<Reservation> getAllReservations(Context ct,String username){

        reservation_doa rdoa = reservation_doa.getInstance(context);


//        if (resList.isEmpty()){
            resList.clear();
            Cursor c = rdoa.getReservationsForUser(username);
            if(c !=null){
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
//        }
        return resList;
    }

    public static List<Reservation> getFMReservations(Context ct, String rDate, String rTime, String fdesc, String uname) {
        reservation_doa rdoa = reservation_doa.getInstance(ct);
        resList.clear();
        rTime = timeConvertTo24(rTime);
        Cursor c = rdoa.getReservationsForFM(rDate,rTime,fdesc,uname);
        if(c !=null){
            do{
                long resid = c.getLong(c.getColumnIndex("resid"));
                String fname = c.getString(c.getColumnIndex("fname"));
                String date = c.getString(c.getColumnIndex("date"));
                String stime = c.getString(c.getColumnIndex("stime"));
                String etime = c.getString(c.getColumnIndex("etime"));
                String ns = c.getString(c.getColumnIndex("noshow"));
                String vio = c.getString(c.getColumnIndex("violation"));
                String un = c.getString(c.getColumnIndex("username"));

                Reservation reservation = new Reservation(ct,un,fname,date,stime,etime);
                reservation.setResId(resid);
                reservation.setNoshow(ns);
                reservation.setViolation(vio);

                resList.add(reservation);
            }while(c.moveToNext());
        }
        return resList;
    }

    public static List<Reservation> getAllReservationsByDate(Context ct, String currUsername, String rDate, String rTime) {

        reservation_doa rdoa = reservation_doa.getInstance(ct);
        resList.clear();
        rTime = timeConvertTo24(rTime);
        Cursor c = rdoa.getReservationsForByDate(rDate,rTime, currUsername);
        if(c !=null){
            do{
                long resid = c.getLong(c.getColumnIndex("resid"));
                String fname = c.getString(c.getColumnIndex("fname"));
                String date = c.getString(c.getColumnIndex("date"));
                String stime = c.getString(c.getColumnIndex("stime"));
                String etime = c.getString(c.getColumnIndex("etime"));
                String ns = c.getString(c.getColumnIndex("noshow"));
                String vio = c.getString(c.getColumnIndex("violation"));
                String un = c.getString(c.getColumnIndex("username"));

                Reservation reservation = new Reservation(ct,un,fname,date,stime,etime);
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
        return timeConvertTo12(stime);
//        return stime;
    }
    public String getStime(boolean val) {
        return stime;
    }

    public void setStime(String stime) {
//        this.stime = stime;
        this.stime = timeConvertTo24(stime);
    }

    public String getEtime() {
//        return etime;
        return timeConvertTo12(etime);
    }

    public String getEtime(boolean val) {
        return etime;
    }

    public void setEtime(String etime) {
//        this.etime = etime;
        this.etime = timeConvertTo24(etime);
    }



    public String getNoshow() {
        return noshow;
    }

    public void setNoshow(String noshow) {
        if(noshow == null){
            this.noshow = "0";
        }
        else{
            this.noshow = noshow;
        }
    }

    public String getViolation() {
        return violation;
    }

    public void setViolation(String violation) {

        if(violation == null){
            this.violation = "0";
        }
        else {
            this.violation = violation;
        }
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

    public void update(){
        reservation_doa rdoa = reservation_doa.getInstance(context);
        rdoa.updateReservation(this);
    }

    public boolean update(String newDate, String newStime) {

        Facility f = Facility.getInstance(this.facName,context);
        if (!f.isAvailable(newDate,newStime)){
            return false;
        }
        reservation_doa rdoa = reservation_doa.getInstance(context);

        this.date = newDate;
        this.stime = newStime;


        SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
        try {
            Date temp = df.parse(newStime);
            Calendar cal = new GregorianCalendar();
            cal.setTime(temp);
            float interval = f.getFtype().getInterval();
            if ( interval < 1){
                int min = (int)(interval*60);
                cal.add(Calendar.MINUTE,min);
            }else{
                cal.add(Calendar.HOUR, (int) interval);
            }
            Date temptime = cal.getTime();
            this.etime = df.format(temptime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.setStime(stime);
        this.setEtime(etime);
        rdoa.updateReservation(this);
        return true;
    }
    public String getNoshowViolationStatus(){
        String ret = null;
        if (this.noshow.equals("1") || this.violation.equals("1")) {
            if(this.noshow.equals("1")){
                ret = "No Show";
            }
            if(this.violation.equals("1")){
                ret = "Violation";
            }
        }
        return ret;
    }
    public void delete(){
        reservation_doa rdoa = reservation_doa.getInstance(context);
        rdoa.deleteReservation(this.getResId());
        for (int counter = 0; counter < resList.size(); counter++)
        {
            if (this.equals(resList.get(counter))){
                resList.remove(counter);
                break;
            }
        }
    }

    public static String timeConvertTo24(String time){
        SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");
        try {
            return date24Format.format(date12Format.parse(time));
        } catch (ParseException e) {
            return time;
        }
    }
    private static String timeConvertTo12(String time){
        SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm aa");
        SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");
        try {
            return date12Format.format(date24Format.parse(time));
        } catch (ParseException e) {
            return time;
        }
    }
}
