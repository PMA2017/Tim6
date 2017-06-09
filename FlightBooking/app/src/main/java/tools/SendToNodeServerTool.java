package tools;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import parsers.JSONParser;
import rs.flightbooking.HttpUtils;
import tools.response.NodeResponse;

/**
 * Created by n.starcev on 6/8/2017.
 */

public class SendToNodeServerTool extends AsyncHttpClient {

    private String _tableName;

    public SendToNodeServerTool()
    {

    }

    public SendToNodeServerTool(String tableName)
    {
        _tableName = tableName;
    }

    public NodeResponse post(RequestParams params)
    {
        final Thread thread = new Thread();
        final NodeResponse[] retVal = new NodeResponse[1];
            HttpUtils.post("api/postToTable/" + _tableName, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    retVal[0] = makeNodeResponse(statusCode, headers, response);
                    thread.notify();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                    retVal[0] = makeNodeResponse(statusCode, headers, response);
                    thread.notify();
                }
            });
        try {
            thread.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return retVal[0];
    }

    private NodeResponse makeNodeResponse(int statusCode, Header[] headers, JSONObject response)
    {
        NodeResponse retVal = new NodeResponse();
        retVal.statusCode = statusCode;
        retVal.headers = headers;
        retVal.response = response;
        return retVal;
    }


}
