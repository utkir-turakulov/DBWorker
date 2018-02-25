package mkdg.com.dbworker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper implements IDatabaseHandler{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "userManager";
    private static final String TABLE_NAME = "userData";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE table IF NOT EXISTS " + TABLE_NAME +
                               " (" + KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, FIO TEXT, DATE_OF_BIRTH TEXT, PLACE TEXT, SEX TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    @Override
    public void createUser(User user) {

    }

    @Override
    public User getUser() {
        return null;
    }

    @Override
    public void editUser(User user) {

    }

    @Override
    public void sortBy(String filterType) {

    }

    @Override
    public void yearFilter() {

    }

    @Override
    public void sexFilter() {

    }
}
