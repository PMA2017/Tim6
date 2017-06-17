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

import model.Flight;

import static com.loopj.android.http.AsyncHttpClient.log;
import static rs.flights.FlightAddFragment.list_integer;


public class FlightDAO extends FlightDBDAO {

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    public FlightDAO(Context context) {
        super(context);
    }

    public long save(Flight flight) {

        log.w("usao21","usao21");
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.ID_COLUMN, flight.getId());
        values.put(DataBaseHelper.FLIGHT_TOWN_FROM, flight.getTownFrom());
        values.put(DataBaseHelper.FLIGHT_TOWN_TO, flight.getTownTo());
        values.put(DataBaseHelper.FLIGHT_TOWN_FROM_MARK, flight.getTownFromMark());
        values.put(DataBaseHelper.FLIGHT_TOWN_TO_MARK, flight.getTownToMark());
        values.put(DataBaseHelper.FLIGHT_PRICE, flight.getPrice());
        values.put(DataBaseHelper.FLIGHT_COMPANY, flight.getCompany());
        values.put(DataBaseHelper.FLIGHT_DATE_FROM, flight.getDate1());
        values.put(DataBaseHelper.FLIGHT_DATE_TO, flight.getDate2());
        values.put(DataBaseHelper.FLIGHT_TIME_FROM, flight.getTime1());
        values.put(DataBaseHelper.FLIGHT_TIME_TO, flight.getTime2());
        values.put(DataBaseHelper.FLIGHT_DURATION, flight.getDuration());
        values.put(DataBaseHelper.FLIGHT_FROM_LATITUDE, flight.getTownFromLatitude());
        values.put(DataBaseHelper.FLIGHT_TO_LATITUDE, flight.getTownToLatitude());
        values.put(DataBaseHelper.FLIGHT_FROM_LONGITUDE, flight.getTownFromLongitude());
        values.put(DataBaseHelper.FLIGHT_TO_LONGITUDE, flight.getTownToLongitude());


    log.w("flightDB","flightDB");
        return database.insert(DataBaseHelper.FLIGHT_TABLE, null, values);

    }

    public ArrayList<Flight> getFlights() {
        ArrayList<Flight> flights = new ArrayList<Flight>();

        Cursor cursor = database.query(DataBaseHelper.FLIGHT_TABLE,
                new String[] { DataBaseHelper.ID_COLUMN,
                        DataBaseHelper.FLIGHT_TOWN_FROM,
                        DataBaseHelper.FLIGHT_TOWN_TO,
                        DataBaseHelper.FLIGHT_TOWN_FROM_MARK,
                        DataBaseHelper.FLIGHT_TOWN_TO_MARK,
                        DataBaseHelper.FLIGHT_PRICE,
                        DataBaseHelper.FLIGHT_COMPANY,
                        DataBaseHelper.FLIGHT_DATE_FROM,
                        DataBaseHelper.FLIGHT_DATE_TO,
                        DataBaseHelper.FLIGHT_TIME_FROM,
                        DataBaseHelper.FLIGHT_TIME_TO,
                        DataBaseHelper.FLIGHT_DURATION,
                        DataBaseHelper.FLIGHT_FROM_LATITUDE,
                        DataBaseHelper.FLIGHT_TO_LATITUDE,
                        DataBaseHelper.FLIGHT_FROM_LONGITUDE,
                        DataBaseHelper.FLIGHT_TO_LONGITUDE
                }, null, null, null,
                null, null,null);

        while (cursor.moveToNext()) {

                Flight flight = new Flight();
                flight.setId(cursor.getInt(0));
                flight.setTownFrom(cursor.getString(1));
                flight.setTownTo(cursor.getString(2));
                flight.setTownFromMark(cursor.getString(3));
                flight.setTownToMark(cursor.getString(4));
                flight.setPrice(cursor.getString(5));
                flight.setCompany(cursor.getString(6));
                flight.setDate1(cursor.getString(7));
                flight.setDate2(cursor.getString(8));
                flight.setTime1(cursor.getString(9));
                flight.setTime2(cursor.getString(10));
                flight.setDuration(cursor.getString(11));
            flight.setTownFromLatitude(cursor.getInt(12));
            flight.setTownToLatitude(cursor.getInt(13));
            flight.setTownFromLongitude(cursor.getInt(14));
            flight.setTownToLongitude(cursor.getInt(15));

            for(int i=0;i<list_integer.size();i++){
                if(list_integer.get(i)==cursor.getInt(0)) {
                    flights.add(flight);
                }
            }

        }
        return flights;
    }

    private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN
            + " =?";


    public long update(Flight flight) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.ID_COLUMN, flight.getId());
        values.put(DataBaseHelper.FLIGHT_TOWN_FROM, flight.getTownFrom());
        values.put(DataBaseHelper.FLIGHT_TOWN_TO, flight.getTownTo());
        values.put(DataBaseHelper.FLIGHT_TOWN_FROM_MARK, flight.getTownFromMark());
        values.put(DataBaseHelper.FLIGHT_TOWN_TO_MARK, flight.getTownToMark());
        values.put(DataBaseHelper.FLIGHT_PRICE, flight.getPrice());
        values.put(DataBaseHelper.FLIGHT_COMPANY, flight.getCompany());
        values.put(DataBaseHelper.FLIGHT_DATE_FROM, flight.getDate1());
        values.put(DataBaseHelper.FLIGHT_DATE_TO, flight.getDate2());
        values.put(DataBaseHelper.FLIGHT_TIME_FROM, flight.getTime1());
        values.put(DataBaseHelper.FLIGHT_TIME_TO, flight.getTime2());
        values.put(DataBaseHelper.FLIGHT_DURATION, flight.getDuration());
        values.put(DataBaseHelper.FLIGHT_FROM_LATITUDE, flight.getTownFromLatitude());
        values.put(DataBaseHelper.FLIGHT_TO_LATITUDE, flight.getTownToLatitude());
        values.put(DataBaseHelper.FLIGHT_FROM_LONGITUDE, flight.getTownFromLongitude());
        values.put(DataBaseHelper.FLIGHT_TO_LONGITUDE, flight.getTownToLongitude());
        long result = database.update(DataBaseHelper.FLIGHT_TABLE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(flight.getId()) });
        return result;

    }

    public int delete(Flight flight) {
        return database.delete(DataBaseHelper.FLIGHT_TABLE, WHERE_ID_EQUALS,
                new String[] { flight.getId() + "" });
    }

    //Retrieves a single flight record with the given id
    public Flight getFlight(long id) {
        Flight flight = null;

        String sql = "SELECT * FROM " + DataBaseHelper.FLIGHT_TABLE
                + " WHERE " + DataBaseHelper.ID_COLUMN + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

        if (cursor.moveToNext()) {
            flight.setId(cursor.getInt(0));
            flight.setTownFrom(cursor.getString(1));
            flight.setTownTo(cursor.getString(2));
            flight.setTownFromMark(cursor.getString(3));
            flight.setTownToMark(cursor.getString(4));
            flight.setPrice(cursor.getString(5));
            flight.setCompany(cursor.getString(6));
            flight.setDate1(cursor.getString(7));
            flight.setDate2(cursor.getString(8));
            flight.setTime1(cursor.getString(9));
            flight.setTime2(cursor.getString(10));
            flight.setDuration(cursor.getString(11));
            flight.setTownFromLatitude(cursor.getInt(12));
            flight.setTownToLatitude(cursor.getInt(13));
            flight.setTownFromLongitude(cursor.getInt(14));
            flight.setTownToLongitude(cursor.getInt(15));

        }
        return flight;
    }


}
