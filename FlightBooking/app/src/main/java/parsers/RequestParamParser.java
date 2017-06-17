package parsers;

import com.loopj.android.http.RequestParams;

/**
 * Created by n.starcev on 6/8/2017.
 */

public class RequestParamParser {

    public static RequestParams makeRequestParamsUser(String username, String firstName, String lastName, String password, String role)
    {
        RequestParams rp = new RequestParams();
        rp.add("Username",username);
        rp.add("FirstName",firstName);
        rp.add("LastName",lastName);
        rp.add("Password",password);
        rp.add("Role",role);
        return rp;
    }

    public static RequestParams makeRequestParamsUserLogin(String username, String password)
    {
        RequestParams rp = new RequestParams();
        rp.add("Username",username);
        rp.add("Password",password);
        return rp;
    }

    public static RequestParams makeRequestParamsGetAroundDateCriteria(String townFrom, String townTo, String dateFrom, String dateTo, String passengers)
    {
        RequestParams rp = new RequestParams();
        rp.add("MarkTownFrom",townFrom);
        rp.add("MarkTownTo",townTo);
        rp.add("DateFrom",dateFrom);
        if(dateTo != null) {
            rp.add("DateTo", dateTo);
        }
        rp.add("Passengers",passengers);
        return rp;
    }

}
