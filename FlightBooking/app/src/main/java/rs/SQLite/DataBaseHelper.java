package rs.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.loopj.android.http.AsyncHttpClient.log;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "flightDB";
    private static final int DATABASE_VERSION = 3;

    public static final String FLIGHT_TABLE = "flightModify";
    public static final String COMMENT_TABLE = "comment";

    public static final String ID_COLUMN = "id";
    public static final String FLIGHT_TOWN_FROM = "town_from";
    public static final String FLIGHT_TOWN_TO = "town_to";
    public static final String FLIGHT_TOWN_FROM_MARK = "town_from_mark";
    public static final String FLIGHT_TOWN_TO_MARK = "town_to_mark";
    public static final String FLIGHT_PRICE = "price";
    public static final String FLIGHT_COMPANY = "company";
    public static final String FLIGHT_DATE_FROM = "date1";
    public static final String FLIGHT_DATE_TO = "date2";
    public static final String FLIGHT_TIME_FROM = "time1";
    public static final String FLIGHT_TIME_TO = "time2";
    public static final String FLIGHT_DURATION = "duration";

    public static final String CREATE_FLIGHT_TABLE = "CREATE TABLE "
            + FLIGHT_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY, "
            + FLIGHT_TOWN_FROM + " TEXT, " + FLIGHT_TOWN_TO + " TEXT, "
            + FLIGHT_TOWN_FROM_MARK + " TEXT, " + FLIGHT_TOWN_TO_MARK + " TEXT, "
            + FLIGHT_PRICE + " TEXT, " + FLIGHT_COMPANY + " TEXT, "
            + FLIGHT_DATE_FROM + " TEXT, " + FLIGHT_DATE_TO + " TEXT, "
            + FLIGHT_TIME_FROM + " TEXT, " + FLIGHT_TIME_TO + " TEXT, "
            + FLIGHT_DURATION + " TEXT" + ")";

    public static final String COMMENT_COMMENT = "comment";


    public static final String CREATE_COMMENT_TABLE = "CREATE TABLE "
            + COMMENT_TABLE + "(" + ID_COLUMN + " INTEGER PRIMARY KEY, "
            + COMMENT_COMMENT + " TEXT" + ")";



    private static DataBaseHelper instance;

    public static synchronized DataBaseHelper getHelper(Context context) {

        if (instance == null) {

            instance = new DataBaseHelper(context);
        }
        return instance;
    }

    private DataBaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_FLIGHT_TABLE);
        db.execSQL(CREATE_COMMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}