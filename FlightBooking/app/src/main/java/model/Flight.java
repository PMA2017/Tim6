package model;

/**
 * Created by Rale on 6/11/2017.
 */

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import parsers.JSONParser;
import rs.flightbooking.R;
import tools.IServerCaller;
import tools.SendToServer;
import tools.Session;
import tools.response.ServerResponse;

public class Flight implements Parcelable {

    private int id;
    private String townFrom;
    private String townTo;
    private String townFromMark;
    private String townToMark;
    private String price;
    private String company;
    private String date1;
    private String date2;
    private String time1;
    private String time2;
    private String duration;
    private Double townFromLatitude;
    private Double townToLatitude;
    private Double townFromLongitude;
    private Double townToLongitude;

    private Integer rating = 0;



    public Flight() {
        super();

    }


    private Flight(Parcel in) {
        super();
        this.id = in.readInt();
        this.townFrom = in.readString();
        this.townTo = in.readString();
        this.townFromMark = in.readString();
        this.townToMark = in.readString();
        this.price = in.readString();
        this.company = in.readString();
        this.date1 = in.readString();
        this.date2 = in.readString();
        this.time1 = in.readString();
        this.time2 = in.readString();
        this.duration = in.readString();
        this.townFromLatitude = in.readDouble();
        this.townToLatitude = in.readDouble();
        this.townFromLongitude = in.readDouble();
        this.townToLongitude = in.readDouble();

    }

    public Flight(int id, String townFrom, String townTo, String townFromMark, String townToMark, String price, String company, String date1, String date2, String time1, String time2, String duration, Double townFromLatitude, Double townToLatitude, Double townFromLongitude, Double townToLongitude) {
        this.id = id;
        this.townFrom = townFrom;
        this.townTo = townTo;
        this.townFromMark = townFromMark;
        this.townToMark = townToMark;
        this.price = price;
        this.company = company;
        this.date1 = date1;
        this.date2 = date2;
        this.time1 = time1;
        this.time2 = time2;
        this.duration = duration;
        this.townFromLatitude = townFromLatitude;
        this.townToLatitude = townToLatitude;
        this.townFromLongitude = townFromLongitude;
        this.townToLongitude = townToLongitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTownFrom() {
        return townFrom;
    }

    public void setTownFrom(String townFrom) {
        this.townFrom = townFrom;
    }

    public String getTownTo() {
        return townTo;
    }

    public void setTownTo(String townTo) {
        this.townTo = townTo;
    }

    public String getTownFromMark() {
        return townFromMark;
    }

    public void setTownFromMark(String townFromMark) {
        this.townFromMark = townFromMark;
    }

    public String getTownToMark() {
        return townToMark;
    }

    public void setTownToMark(String townToMark) {
        this.townToMark = townToMark;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Double getTownFromLatitude() {
        return townFromLatitude;
    }

    public void setTownFromLatitude(Double townFromLatitude) {
        this.townFromLatitude = townFromLatitude;
    }

    public Double getTownToLatitude() {
        return townToLatitude;
    }

    public void setTownToLatitude(Double townToLatitude) {
        this.townToLatitude = townToLatitude;
    }

    public Double getTownFromLongitude() {
        return townFromLongitude;
    }

    public void setTownFromLongitude(Double townFromLongitude) {
        this.townFromLongitude = townFromLongitude;
    }

    public Double getTownToLongitude() {
        return townToLongitude;
    }

    public void setTownToLongitude(Double townToLongitude) {
        this.townToLongitude = townToLongitude;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }


    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", townFrom='" + townFrom + '\'' +
                ", townTo='" + townTo + '\'' +
                ", townFromMark='" + townFromMark + '\'' +
                ", townToMark='" + townToMark + '\'' +
                ", price='" + price + '\'' +
                ", company='" + company + '\'' +
                ", date1='" + date1 + '\'' +
                ", date2='" + date2 + '\'' +
                ", time1='" + time1 + '\'' +
                ", time2='" + time2 + '\'' +
                ", duration='" + duration + '\'' +
                ", townFromLatitude=" + townFromLatitude +
                ", townToLatitude=" + townToLatitude +
                ", townFromLongitude=" + townFromLongitude +
                ", townToLongitude=" + townToLongitude +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Flight other = (Flight) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(getId());
        parcel.writeString(getTownFrom());
        parcel.writeString(getTownTo());
        parcel.writeString(getTownFromMark());
        parcel.writeString(getTownToMark());
        parcel.writeString(getPrice());
        parcel.writeString(getCompany());
        parcel.writeString(getDate1());
        parcel.writeString(getDate2());
        parcel.writeString(getTime1());
        parcel.writeString(getTime2());
        parcel.writeString(getDuration());


    }



    public static final Creator<Flight> CREATOR = new Creator<Flight>() {
        public Flight createFromParcel(Parcel in) {
            return new Flight(in);
        }

        public Flight[] newArray(int size) {
            return new Flight[size];
        }
    };

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
