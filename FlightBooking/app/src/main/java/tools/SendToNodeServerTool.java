package tools;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import rs.flightbooking.HttpUtils;

/**
 * Created by n.starcev on 6/8/2017.
 */

public class SendToNodeServerTool {

    private String _tableName;

    public SendToNodeServerTool()
    {

    }

    public SendToNodeServerTool(String tableName)
    {
        _tableName = tableName;
    }

    public boolean sendToServer(RequestParams params)
    {
        HttpUtils.post("api/postToTable/"+_tableName, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

            }
        });
        return true;
    }


}
