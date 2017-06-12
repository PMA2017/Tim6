package rs.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.loopj.android.http.AsyncHttpClient.log;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "flightDB";
    private static final int DATABASE_VERSION = 1;

    public static final String FLIGHT_TABLE = "flight";

    public static final String ID_COLUMN = "id";
    public static final String FLIGHT_TOWN_FROM = "town_from";
    public static final String FLIGHT_TOWN_TO = "town_to";
    public static final String FLIGHT_DATE_FROM = "date_from";
    public static final String FLIGHT_DATE_TO = "date_to";

    public static final String CREATE_FLIGHT_TABLE = "CREATE TABLE "
            + FLIGHT_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY, "
            + FLIGHT_TOWN_FROM + " TEXT, " + FLIGHT_TOWN_TO + " TEXT, "
            + FLIGHT_DATE_FROM + " DATE, " + FLIGHT_DATE_TO + " DATE" + ")";



    private static DataBaseHelper instance;

    public static synchronized DataBaseHelper getHelper(Context context) {

        if (instance == null) {
            log.w("m","m");
            log.w("tabela",CREATE_FLIGHT_TABLE);
            log.w("dateTo",FLIGHT_DATE_TO);
            instance = new DataBaseHelper(context);
        }
        log.w("m1","m1");
        return instance;
    }

    private DataBaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        log.w("m2","m2");

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        log.w("ff","ff");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FLIGHT_TABLE);
        log.w("ff1","ff1");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        log.w("ff2","ff2");
    }
}