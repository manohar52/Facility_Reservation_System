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
    private static Context ct;
    private DatabaseHelper dbhelper;
    public static reservation_doa getInstance(Context context) {
        if (ct == null){
            ct = context;
        }
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
        cv.put("stime",res.getStime(true));
        cv.put("etime",res.getEtime(true));
        cv.put("noshow",Integer.parseInt(res.getNoshow()));
        cv.put("violation",Integer.parseInt(res.getViolation()));
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

    public void updateReservation(Reservation res) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        ContentValues cv = new ContentValues();
        String[] args = { String.valueOf(res.getResId())};


        cv.put("date",(res.getDate()));
        cv.put("stime",res.getStime(true));
        cv.put("etime",res.getEtime(true));
        cv.put("noshow",res.getNoshow());
        cv.put("violation",res.getViolation());
        int ar = db.update("reservation",cv,"resid = ?",args);
    }

    public void deleteReservation(long resId) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] args = { String.valueOf(resId)};

        int ar = db.delete("reservation","resid = ?",args);
    }

    public Cursor getReservationsForFM(String rDate, String rTime, String fdesc, String uname) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String query;
        String[] whereclause;
        String query1 = "select r.resid,r.username,r.fname,r.date,r.stime,r.etime, " +
                "r.noshow,r.violation " +
                "from reservation as r " +
                "INNER JOIN facility as f on r.fname = f.name " +
                "INNER JOIN facility_type as ft on f.fdesc = ft.fdesc " +
//                "where r.date = ? and r.stime <= ? and r.etime >= ? and f.fdesc = ? ";
                "where r.date = ? and r.etime >= ? and f.fdesc = ? ";

        String query2 = "select r.resid,r.username,r.fname,r.date,r.stime,r.etime, " +
                "r.noshow,r.violation " +
                "from reservation as r " +
                "INNER JOIN facility as f on r.fname = f.name " +
                "INNER JOIN facility_type as ft on f.fdesc = ft.fdesc " +
//                "where r.date = ? and r.stime <= ? and r.etime >= ? and f.fdesc = ? " +
                "where r.date = ? and r.etime >= ? and f.fdesc = ? " +
                "and username = ?";

        if(uname.equals("")){
            query = query1;
            whereclause = new String[]{rDate, rTime, fdesc};
        }else{
            query = query2;
            whereclause = new String[]{rDate, rTime, fdesc, uname};
        }

        Cursor c = db.rawQuery(query,whereclause);
        if  (c.getCount() > 0){
            c.moveToFirst();
            return c;
        }else{
            return null;
        }
    }

    public Cursor getReservationsForByDate(String rDate, String rTime, String uname) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] whereclause;

        String query = "select * " +
                "from reservation" +
//                " where date = ? and stime <= ? and etime >= ? " +
                " where date = ? and etime >= ? " +
                "and username = ?";

            whereclause = new String[]{rDate, rTime, uname};

        Cursor c = db.rawQuery(query,whereclause);
        if  (c.getCount() > 0){
            c.moveToFirst();
            return c;
        }else{
            return null;
        }

    }
}
