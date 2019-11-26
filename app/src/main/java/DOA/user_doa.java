package DOA;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import DatabaseHelper.DatabaseHelper;
import model.SysUser;

public class user_doa {
    private static user_doa ourInstance;
    private DatabaseHelper dbhelper;

    public static user_doa getInstance(Context context) {
        if(ourInstance == null){
            ourInstance = new user_doa(context);
        }
        return ourInstance;
    }

    private user_doa(Context context) {
        dbhelper = DatabaseHelper.getInstance(context, SQLiteDatabase.OPEN_READWRITE);
    }

    public Cursor getUserData(String username) {
            SQLiteDatabase db = dbhelper.getWritableDatabase();
            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

            String[] sqlSelect = {"*"};
            String sqlTables = "user";

            qb.setTables(sqlTables);
            qb.appendWhere("username = \"" + username + "\"");
            Cursor c = qb.query(db, sqlSelect, null, null,
                    null, null, null);
            if  (c.getCount() > 0){
                c.moveToFirst();
                return c;
            }else{
                return null;
            }
    }

    public boolean checkUsername(String username) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = {"username"};
        String sqlTables = "user";
        qb.setTables(sqlTables);

        qb.appendWhere("username = \"" + username + "\"");
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);
        if  (c.getCount() > 0){
            return false;       //username exists on database
        }
        else{
            return true;    // valid username
        }
    }
    public void updateUserProfile(SysUser user){

        SQLiteDatabase db = dbhelper.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        ContentValues cv = new ContentValues();
        String[] args = { String.valueOf(user.getUsername())};

        cv.put("fname",user.getFname());
        cv.put("lname",user.getLname());
        cv.put("utaid",user.getUtaid());
        cv.put("phone",user.getPhone());
        cv.put("vehicleno",user.getVehicle());
        cv.put("parkingpermit",user.getParking());
        cv.put("password",user.getPassword());

        int ar = db.update("user",cv,"username = ?",args);

    }
    public long createUser(Hashtable<String,String> regDetails){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String curKey;
        Set<String> keys = regDetails.keySet();
        Iterator<String> itr = keys.iterator();

        while (itr.hasNext()){
            curKey = itr.next();
            if (curKey == "role"){
                switch (regDetails.get(curKey)){
                    case "Admin":
                        cv.put(curKey,"AD");
                        break;
                    case "User":
                        cv.put(curKey,"UR");
                        break;
                    case "Facility Manager":
                        cv.put(curKey,"FM");
                        break;
                }
                continue;
            }
        cv.put(curKey,regDetails.get(curKey));
        }
        long status = db.insert("user",null,cv);
        return status;
    }

    public Cursor getUsernamesByLastname(String lname) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = {"username"};
        String sqlTables = "user";
        qb.setTables(sqlTables);

        qb.appendWhere("lname LIKE \"%" + lname + "%\"");
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);
        if  (c.getCount() > 0){
            c.moveToFirst();
            return c;       //username exists on database
        }
        else{
            return null;    // valid username
        }
    }

    public void updateUserRole(String uname, String newRole) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        ContentValues cv = new ContentValues();
        String[] args = { uname };

        cv.put("role",newRole);
        int ar = db.update("user",cv,"username = ?",args);
    }

    public void revokeUser(String uname) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        ContentValues cv = new ContentValues();
        String[] args = { uname };

        cv.put("revoked","1");
        int ar = db.update("user",cv,"username = ?",args);
    }

    public int getNoOfNoshows(SysUser sysUser) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"*"};
        String sqlTables = "reservation";

        qb.setTables(sqlTables);
        qb.appendWhere("username = \"" + sysUser.getUsername() + "\" and noshow = 1");
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);
        if(c !=null) {
            return c.getCount();
        }else{
            return 0;
        }
    }


    public int getNoOfViolations(SysUser sysUser) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"*"};
        String sqlTables = "reservation";

        qb.setTables(sqlTables);
        qb.appendWhere("username = \"" + sysUser.getUsername() + "\" and violation = 1");
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);
        if(c !=null) {
            return c.getCount();
        }else{
            return 0;
        }
    }
}
