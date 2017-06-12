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
        open();
        log.w("a16","a16");
    }

    public void open() throws SQLException {

        if(dbHelper == null)
            dbHelper = DataBaseHelper.getHelper(mContext);
        database = dbHelper.getWritableDatabase();
        log.w("a17","a17");
    }

    public void close() {
        dbHelper.close();
        database = null;
    }
}
