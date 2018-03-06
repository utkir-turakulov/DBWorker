package mkdg.com.dbworker;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper implements IDatabaseHandler {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "userManager";
    private static final String TABLE_NAME = "userData";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    final String LOG_TAG = "myLogs";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " ( '" + KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT, FIO TEXT, DATE_OF_BIRTH TEXT, PLACE TEXT, SEX TEXT ); ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void clearUserList() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM " + TABLE_NAME + ";" + " VACUUM; " + "REINDEX " + DATABASE_NAME + "." + TABLE_NAME + ";");
            Log.d(LOG_TAG, "Table deleted");
        } catch (SQLException e) {
            Log.d(LOG_TAG, e.toString());
        }
        db.close();
    }

    @Override
    public void insertUser(User user) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Log.d(LOG_TAG, user.getFIO() + "','" + user.getDateOfBirth() + "','" + user.getBirthPlace() + "','" + user.getSex());
            String query = "INSERT INTO " + TABLE_NAME + "( FIO,DATE_OF_BIRTH,PLACE,SEX ) VALUES ('" + user.getFIO() + "','" + user.getDateOfBirth() + "','" + user.getBirthPlace() + "','" + user.getSex() + "');";
            db.execSQL(query);
            db.close();
        } catch (Exception e) {
            Log.d(LOG_TAG, e.toString());
        }
    }

    @Override
    public List<User> getUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<User> userList = new ArrayList<>();
        User user = null;
        String[] projection = {KEY_ID, "FIO", "DATE_OF_BIRTH", "PLACE", "SEX"};

        Cursor cursor = db.query(TABLE_NAME, projection,
                null, null, null, null, null, null);

        int id = cursor.getColumnIndex(KEY_ID);
        int fio_id = cursor.getColumnIndex("FIO");
        int date_id = cursor.getColumnIndex("DATE_OF_BIRTH");
        int place_id = cursor.getColumnIndex("PLACE");
        int sex_id = cursor.getColumnIndex("SEX");

        logCursor(cursor);

        if (cursor.moveToFirst()) {
            do {

                try {
                    Log.d(LOG_TAG, "Id: " + cursor.getString(id) + " FIO: " + cursor.getString(fio_id));
                    user = new User(cursor.getString(fio_id), cursor.getString(date_id), cursor.getString(place_id), cursor.getString(sex_id));
                    userList.add(user);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();
        return userList;
    }

    private void logCursor(Cursor c) {
        if (c != null) {
            if (c.moveToFirst()) {
                String str;
                do {
                    str = "";
                    for (String cn : c.getColumnNames()) {
                        str = str.concat(cn + " = " + c.getString(c.getColumnIndex(cn)) + "; ");
                    }
                    Log.d(LOG_TAG, str);
                } while (c.moveToNext());
            }
        } else
            Log.d(LOG_TAG, "Cursor is null");
    }

    @Override
    public void editUser(User user) {

    }

    @Override
    public List<User> sortBy(String filterType) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<User> userList = new ArrayList<>();
        User user = null;
        String[] projection = {KEY_ID, "FIO", "DATE_OF_BIRTH", "PLACE", "SEX"};
        Cursor cursor = db.query(TABLE_NAME, projection,
                null, null, null, null, filterType, null);

        int id = cursor.getColumnIndex(KEY_ID);
        int fio_id = cursor.getColumnIndex("FIO");
        int date_id = cursor.getColumnIndex("DATE_OF_BIRTH");
        int place_id = cursor.getColumnIndex("PLACE");
        int sex_id = cursor.getColumnIndex("SEX");

        logCursor(cursor);

        if (cursor.moveToFirst()) {
            do {

                try {
                    Log.d(LOG_TAG, "Id: " + cursor.getString(id) + " FIO: " + cursor.getString(fio_id));
                    user = new User(cursor.getString(fio_id), cursor.getString(date_id), cursor.getString(place_id), cursor.getString(sex_id));
                    userList.add(user);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();
        return userList;
    }

    @Override
    public void yearFilter() {

    }

    @Override
    public void sexFilter() {

    }
}
