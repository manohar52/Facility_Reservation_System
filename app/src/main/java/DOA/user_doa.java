package DOA;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
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
}
