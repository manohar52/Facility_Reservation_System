package model;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import DOA.user_doa;
import errorlog.AppLog;

public class SysUser {
    private String username;
    private String fname;
    private String lname;
    private String role;
    private int utaid;
    private int phone;
    private String vehicleno;
    private int parking;
    private String password;
    private static Hashtable<String,SysUser> users = new Hashtable<>();
    private static Context ct;

    protected SysUser(String username){
        this.username = username;
    }

    public static SysUser getUser(String username, Context context){
        if (ct == null){
            ct = context;
        }
       if (!users.containsKey(username)){
           SysUser currUser = new SysUser(username);
            users.put(username,currUser);
       }
       return users.get(username);
   }
    private Cursor authenticate(String username, String password) {
        user_doa udao = user_doa.getInstance(ct);
        Cursor c = udao.getUserData(username);
        if(c == null){
            return null;
        }
        String pwd = c.getString(c.getColumnIndex("password"));
        if (pwd.equals(password)){
            //authentication successful
            return c;
        }else{
            return null;
        }
    }
    private void setSessionforUser(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(ct);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("username", this.username);
        editor.commit();
    }
    public void logout(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(ct);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove("username");
        editor.commit();
    }
   public boolean login(String password){
        boolean status;
        Cursor c = this.authenticate(this.username, password);
        if (c == null){
            users.remove(this.username);

            return false;
        }else{
//            this.setUsername(c.getString(c.getColumnIndex("username")));
            this.setFname(c.getString(c.getColumnIndex("fname")));
            this.setFname(c.getString(c.getColumnIndex("lname")));
            this.setRole(c.getString(c.getColumnIndex("role")));
            this.setUtaid(c.getInt(c.getColumnIndex("utaid")));
            this.setPhone(c.getInt(c.getColumnIndex("phone")));
            this.setVehicle(c.getString(c.getColumnIndex("vehicleno")));
            this.setParking(c.getInt(c.getColumnIndex("parkingpermit")));
            this.setPassword(c.getString(c.getColumnIndex("password")));
            this.setSessionforUser();
            return true;
        }
   }

   public boolean register(Hashtable<String, String> regDetails){
        AppLog log = AppLog.getInstance();
        user_doa udoa = user_doa.getInstance(ct);

       boolean validUsername = udoa.checkUsername(this.username);
        if (validUsername == true){
            long updateStatus = udoa.createUser(regDetails);
            if (updateStatus != -1){
                return true;
            }else{
                log.addMessage("Error creating user, try again!");
                return false;
            }
        }
        else{
            users.remove(this.username);
            log.addMessage("Username already exists. Try with another username!");
            return false;
        }
   }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getFname(){
        return fname;
    }
    public void setFname(String fname){
        this.fname = fname;
    }
    public String getLname(){
        return lname;
    }
    public void setLname(String lname){
        this.lname = lname;
    }

    public String getRole(){
        return role;
    }
    public void setRole(String role){
        this.role = role;
    }

    public int getUtaid(){
        return utaid;
    }
    public void setUtaid(int utaid){
        this.utaid = utaid;
    }

    public int getPhone(){
        return phone;
    }
    public void setPhone(int phone){
        this.phone = phone;
    }

    public String getVehicle(){
        return vehicleno;
    }
    public void setVehicle(String vehicleno){
        this.vehicleno = vehicleno;
    }

    public int getParking(){
        return parking;
    }
    public void setParking(int parking){
        this.parking = parking;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String Password){
        this.password = Password;
    }

}