package rs.SQLite;

/**
 * Created by Rale on 6/11/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.loopj.android.http.AsyncHttpClient.log;


public class FlightDAO extends FlightDBDAO {

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    public FlightDAO(Context context) {
        super(context);
    }

    public long save(Flight flight) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.FLIGHT_TOWN_FROM, flight.getTownFrom());
        values.put(DataBaseHelper.FLIGHT_TOWN_TO, flight.getTownTo());
        values.put(DataBaseHelper.FLIGHT_DATE_FROM, formatter.format(flight.getDateFrom()));
        values.put(DataBaseHelper.FLIGHT_DATE_TO, formatter.format(flight.getDateTo()));
        log.w("a11","a11");
        return database.insert(DataBaseHelper.FLIGHT_TABLE, null, values);

    }

    public ArrayList<Flight> getFlights() {
        ArrayList<Flight> flights = new ArrayList<Flight>();

        Cursor cursor = database.query(DataBaseHelper.FLIGHT_TABLE,
                new String[] { DataBaseHelper.ID_COLUMN,
                        DataBaseHelper.FLIGHT_TOWN_FROM,
                        DataBaseHelper.FLIGHT_TOWN_TO,
                        DataBaseHelper.FLIGHT_DATE_FROM,
                        DataBaseHelper.FLIGHT_DATE_TO}, null, null, null,
                null, null,null);
        log.w("a12","a12");
        while (cursor.moveToNext()) {
            Flight flight = new Flight();
            flight.setId(cursor.getInt(0));
            flight.setTownFrom(cursor.getString(1));
            flight.setTownTo(cursor.getString(2));
            try {
                flight.setDateFrom(formatter.parse(cursor.getString(3)));
            } catch (ParseException e) {
                flight.setDateFrom(null);
            }
            try {
                flight.setDateTo(formatter.parse(cursor.getString(4)));
            } catch (ParseException e) {
                flight.setDateTo(null);
            }
            log.w("a13","a13");

            flights.add(flight);
        }
        return flights;
    }

    private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN
            + " =?";


    public long update(Flight flight) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.FLIGHT_TOWN_FROM, flight.getTownFrom());
        values.put(DataBaseHelper.FLIGHT_TOWN_TO, flight.getTownTo());
        values.put(DataBaseHelper.FLIGHT_DATE_FROM, formatter.format(flight.getDateFrom()));
        values.put(DataBaseHelper.FLIGHT_DATE_TO, formatter.format(flight.getDateTo()));
        log.w("a14","a14");
        long result = database.update(DataBaseHelper.FLIGHT_TABLE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(flight.getId()) });
        log.w("a15","a15");
        return result;

    }

    public int delete(Flight flight) {
        return database.delete(DataBaseHelper.FLIGHT_TABLE, WHERE_ID_EQUALS,
                new String[] { flight.getId() + "" });
    }

    //Retrieves a single flight record with the given id
    public Flight getFlight(long id) {
        log.w("get","get");
        Flight flight = null;

        String sql = "SELECT * FROM " + DataBaseHelper.FLIGHT_TABLE
                + " WHERE " + DataBaseHelper.ID_COLUMN + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

        if (cursor.moveToNext()) {
            flight = new Flight();
            flight.setId(cursor.getInt(0));
            flight.setTownFrom(cursor.getString(1));
            flight.setTownTo(cursor.getString(2));
            try {
                flight.setDateFrom(formatter.parse(cursor.getString(3)));
            } catch (ParseException e) {
                flight.setDateFrom(null);
            }
            try {
                flight.setDateTo(formatter.parse(cursor.getString(4)));
            } catch (ParseException e) {
                flight.setDateTo(null);
            }
        }
        return flight;
    }


}
