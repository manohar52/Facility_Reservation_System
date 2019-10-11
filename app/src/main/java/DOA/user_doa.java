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

            String[] sqlSelect = {"username", "fname", "lname", "role", "utaid", "phone", "vehicleno", "parkingpermit", "password"};
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
}
