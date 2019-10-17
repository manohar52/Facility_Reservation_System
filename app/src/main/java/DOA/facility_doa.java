package DOA;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import DatabaseHelper.DatabaseHelper;
import model.SysUser;

public class facility_doa {
    private static facility_doa ourInstance;
    private DatabaseHelper dbhelper;

    public static facility_doa getInstance(Context context) {
        if(ourInstance == null){
            ourInstance = new facility_doa(context);
        }
        return ourInstance;
    }

    private facility_doa(Context context) {
        dbhelper = DatabaseHelper.getInstance(context, SQLiteDatabase.OPEN_READWRITE);
    }

    public Cursor loadAllData(){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"*"};
        String sqlTables = "facility";

        qb.setTables(sqlTables);
//        qb.appendWhere("name = \"" + name + "\"");
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);
        if  (c.getCount() > 0){
            c.moveToFirst();
            return c;
        }else{
            return null;
        }
    }
    public Cursor getFacilityData(String name) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"*"};
        String sqlTables = "facility";

        qb.setTables(sqlTables);
        qb.appendWhere("name = \"" + name + "\"");
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);
        if  (c.getCount() > 0){
            c.moveToFirst();
            return c;
        }else{
            return null;
        }
    }

    public Hashtable<String,String> getAllFacilityTypes() {
            SQLiteDatabase db = dbhelper.getWritableDatabase();
            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
            Hashtable<String,String> result = new Hashtable<String, String>();
            String sqlquery = "SELECT DISTINCT FTYPE, FDESC FROM facility";
            String[] sqlSelect = {"DISTINCT FTYPE", "FDESC"};
            String sqlTables = "facility";
            qb.setTables(sqlTables);
//            qb.appendWhere("username = \"" + username + "\"");

            Cursor c = qb.query(db, sqlSelect, null, null,
                    null, null, null);
        try {
            while (c.moveToNext()) {
                result.put(c.getString(1),c.getString(0));
            }
        }
        finally {
                c.close();
            }
        return result;
    }


//    public void updateUserProfile(SysUser user){
//
//        SQLiteDatabase db = dbhelper.getWritableDatabase();
//        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
//        ContentValues cv = new ContentValues();
//        String[] args = { String.valueOf(user.getUsername())};
//
//        cv.put("fname",user.getFname());
//        cv.put("lname",user.getLname());
//        cv.put("utaid",user.getUtaid());
//        cv.put("phone",user.getPhone());
//        cv.put("vehicleno",user.getVehicle());
//        cv.put("parkingpermit",user.getParking());
//        cv.put("password",user.getPassword());
//
//        int ar = db.update("user",cv,"username = ?",args);
//
//    }
//
}
