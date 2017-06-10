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

public class SendToServerTool {

    private IServerCaller _calller;

    public SendToServerTool(IServerCaller caller)
    {
        _calller = caller;
    }

    public void post(String tableName, RequestParams params)
    {
        HttpUtils.post("api/postToTable/" + tableName, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                NodeResponse madeResponse = makeNodeResponse(statusCode, headers, response);
                _calller.OnServerResponse(madeResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                NodeResponse madeResponse = makeNodeResponse(statusCode, headers, response);
                _calller.OnServerResponse(madeResponse);
            }
        });
    }

    public void checkIsLoginCorrect(RequestParams params)
    {
        HttpUtils.post("/api/User/postLoginData", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                NodeResponse madeResponse = makeNodeResponse(statusCode, headers, response);
                _calller.OnServerResponse(madeResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                NodeResponse madeResponse = makeNodeResponse(statusCode, headers, response);
                _calller.OnServerResponse(madeResponse);
            }
        });
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
