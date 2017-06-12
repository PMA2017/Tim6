package rs.SQLite;

/**
 * Created by Rale on 6/11/2017.
 */

import java.util.Date;
import android.os.Parcel;
import android.os.Parcelable;

public class Flight implements Parcelable {

    private int id;
    private String townFrom;
    private String townTo;
    private Date dateFrom;
    private Date dateTo;

    public Flight() {
        super();
    }

    private Flight(Parcel in) {
        super();
        this.id = in.readInt();
        this.townFrom = in.readString();
        this.townTo = in.readString();
        this.dateFrom = new Date(in.readLong());
        this.dateTo = new Date(in.readLong());
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

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public String toString() {
        return "Flight [id=" + id + ", townFrom=" + townFrom + ", townTo="
                + townTo + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
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
        parcel.writeLong(getDateFrom().getTime());
        parcel.writeLong(getDateTo().getTime());
    }

    public static final Parcelable.Creator<Flight> CREATOR = new Parcelable.Creator<Flight>() {
        public Flight createFromParcel(Parcel in) {
            return new Flight(in);
        }

        public Flight[] newArray(int size) {
            return new Flight[size];
        }
    };

}
