package DOA;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
        cv.put("username",res.getUser().getUsername());
        cv.put("fname",res.getFacility().getName());
        cv.put("date",res.getDate());
        cv.put("stime",res.getStime());
        cv.put("etime",res.getEtime());
        long resid = db.insert("user",null,cv);
        return resid;

    }
}
