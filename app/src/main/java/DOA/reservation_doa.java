package DOA;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import DatabaseHelper.DatabaseHelper;
import model.Reservation;

public class reservation_doa {
    private static reservation_doa ourInstance;
    private DatabaseHelper dbhelper;
    public static reservation_doa getInstance(Context context) {

        if(ourInstance == null){
            ourInstance = new reservation_doa(context);
        }
        return ourInstance;
    }

    private reservation_doa(Context context) {
        dbhelper = DatabaseHelper.getInstance(context, SQLiteDatabase.OPEN_READWRITE);
    }

    public long createReservation(Reservation res){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username",res.getUsername());
        cv.put("fname",res.getFacName());
        cv.put("date",res.getDate());
        cv.put("stime",res.getStime());
        cv.put("etime",res.getEtime());
        long resid = db.insert("reservation",null,cv);
        return resid;

    }

    public Cursor getReservationsForUser(String username) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"*"};
        String sqlTables = "reservation";

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
}
