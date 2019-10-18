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
        user_doa udao = user_doa.getInstance(ct);
        Cursor c = udao.getUserData(username);
        this.username = username;
        this.setFname(c.getString(c.getColumnIndex("fname")));
        this.setLname(c.getString(c.getColumnIndex("lname")));
        this.setRole(c.getString(c.getColumnIndex("role")));
        this.setUtaid(c.getInt(c.getColumnIndex("utaid")));
        this.setPhone(c.getInt(c.getColumnIndex("phone")));
        this.setVehicle(c.getString(c.getColumnIndex("vehicleno")));
        this.setParking(c.getInt(c.getColumnIndex("parkingpermit")));
        this.setPassword(c.getString(c.getColumnIndex("password")));
    }

    public static SysUser getUser(String username, Context context){
        if (ct == null){
            ct = context;
        }
        SysUser currUser;
        if (!users.containsKey(username)) {
            user_doa udao = user_doa.getInstance(ct);
            Cursor c = udao.getUserData(username);
            String role = c.getString(c.getColumnIndex("role"));
            if (c == null) {
                return null;
            }
            switch (role) {
                case "UR":
                    currUser = new User(username);
                    break;
                case "AD":
                    currUser = new Admin(username);
                    break;
                case "FM":
                    currUser = new FacilityManager(username);
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + role);
            }
        }
        else {
            currUser = users.get(username);
        }
        return currUser;

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

        if(this.password.equals(password)){
            this.setSessionforUser();
            users.put(this.username,this);
            return true;
        }
        else{
            return false;
        }
   }

   public static boolean register(Hashtable<String, String> regDetails,Context ctxt){
        ct = ctxt;
        AppLog log = AppLog.getInstance();
        user_doa udoa = user_doa.getInstance(ct);

       boolean validUsername = udoa.checkUsername(regDetails.get("username"));
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
            log.addMessage("Username already exists. Try with another username!");
            return false;
        }
   }

   public void updateProfile(Context context){
        user_doa udao = user_doa.getInstance(context);
            udao.updateUserProfile(this);
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