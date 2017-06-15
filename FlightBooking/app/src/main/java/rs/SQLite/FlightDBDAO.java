package rs.SQLite;

/**
 * Created by Rale on 6/11/2017.
 */


import android.content.Context;
        import android.database.SQLException;
        import android.database.sqlite.SQLiteDatabase;

        import static com.loopj.android.http.AsyncHttpClient.log;

public class FlightDBDAO {

    protected SQLiteDatabase database;
    private DataBaseHelper dbHelper;
    private Context mContext;

    public FlightDBDAO(Context context) {
        this.mContext = context;
        dbHelper = DataBaseHelper.getHelper(mContext);
        log.w("flightDB1","flightDB1");
        open();
    }

    public void open() throws SQLException {

        if(dbHelper == null)
            dbHelper = DataBaseHelper.getHelper(mContext);
        log.w("flightDB2","flightDB2");
        database = dbHelper.getWritableDatabase();
        log.w("flightDB3","flightDB3");
    }

    public void close() {
        dbHelper.close();
        database = null;
    }
}
