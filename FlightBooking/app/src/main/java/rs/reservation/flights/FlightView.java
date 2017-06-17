package rs.reservation.flights;

import java.io.Serializable;

/**
 * Created by Nemanja on 6/13/2017.
 */

public class FlightView implements Serializable {

    public Integer id;
    public String townFrom;
    public String townFromName;
    public String timeFrom;
    public String dateFrom;
    public String townTo;
    public String townToName;
    public String timeTo;
    public String dateTo;
    public String type;
    public String duration;
    public String company;
    public String price;
    public boolean isFree;

}
