package rs.contact;

/**
 * Created by Milos on 6/11/2017.
 */

public class Airline {


    private String name;
    private String address;
    private String phone_number;
    private String city;

    public Airline(String name, String address, String phone_number, String city)
    {
        this.name = name;
        this.address = address;
        this.phone_number = phone_number;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getCity() {
        return city;
    }


}
