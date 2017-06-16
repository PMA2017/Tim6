package rs.reservation.flights;

import java.io.Serializable;

/**
 * Created by Nemanja on 6/13/2017.
 */

public class FlightView implements Serializable {

    public String townFrom;
    public String townFromName;
    public String timeFrom;
    public String townTo;
    public String townToName;
    public String timeTo;
    public String type;
    public String duration;
    public String company;
    public String price;
    public boolean isFree;

}
