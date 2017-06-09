package tools.response;

import cz.msebera.android.httpclient.Header;

import org.json.JSONObject;

/**
 * Created by n.starcev on 6/9/2017.
 */

public class NodeResponse {
    public int statusCode;
    public Header[] headers;
    public JSONObject response;
}
