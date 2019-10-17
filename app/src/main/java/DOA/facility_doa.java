package DOA;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import java.util.ArrayList;
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

    public ArrayList<String> getAllFacilityTypes() {
            SQLiteDatabase db = dbhelper.getWritableDatabase();
            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
            ArrayList<String> result = new ArrayList<String>();

            String[] sqlSelect = {"FDESC"};
            String sqlTables = "facility_type";
            qb.setTables(sqlTables);

            Cursor c = qb.query(db, sqlSelect, null, null,
                    null, null, null);
        try {
            while (c.moveToNext()) {
                result.add(c.getString(c.getColumnIndex("fdesc")));
            }
        }
        finally {
                c.close();
            }
        return result;
    }

    public Cursor loadAllData(String fdesc){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"name"};
        String sqlTables = "facility";

        qb.setTables(sqlTables);
        qb.appendWhere("fdesc = \"" + fdesc + "\"");
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);
        if  (c.getCount() > 0){
            c.moveToFirst();
            return c;
        }else{
            return null;
        }
    }

    public Cursor getFacilityTypeData(String fdesc) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"*"};
        String sqlTables = "facility_type";

        qb.setTables(sqlTables);
        qb.appendWhere("fdesc = \"" + fdesc + "\"");
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);
        if  (c.getCount() > 0){
            c.moveToFirst();
            return c;
        }else{
            return null;
        }
    }
}
