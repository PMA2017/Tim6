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

}
