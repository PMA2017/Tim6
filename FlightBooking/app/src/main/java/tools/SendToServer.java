package tools;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import tools.response.ServerResponse;

import static com.loopj.android.http.AsyncHttpClient.log;

/**
 * Created by n.starcev on 6/8/2017.
 */

public class SendToServer {

    private IServerCaller _calller;

    public SendToServer(IServerCaller caller)
    {
        _calller = caller;
    }

    public void get(String tableName)
    {
        HttpUtils.get("/api/get/" + tableName, null, jsonHttpHandler);
    }



    public void checkFlights(int id)
    {
        log.w("usao81","usao81");
        HttpUtils.get("/api/User/getFlights/"+id ,null, jsonHttpHandler);
        log.w("usao82","usao82");
    }


    public void getAirline(){
        HttpUtils.get("api/getAirline", null, jsonHttpHandler);
    }


    public void post(String tableName, RequestParams params)
    {
        HttpUtils.post("api/postToTable/" + tableName, params, jsonHttpHandler);
    }

    public void checkIsLoginCorrect(RequestParams params)
    {
        log.w("usao7","usao7");
        HttpUtils.post("/api/User/postLoginData", params, jsonHttpHandler);
        log.w("usao71","usao71");
    }

    public void getDrivesAroundDate(RequestParams params)
    {
        HttpUtils.post("/api/generic/getDrivesAroundDate", params, jsonHttpHandler);
    }

    private ServerResponse makeServerResponse(int statusCode, Header[] headers, JSONObject response, JSONArray array)
    {
        ServerResponse retVal = new ServerResponse();
        retVal.statusCode = statusCode;
        retVal.headers = headers;
        retVal.responseObject = response;
        retVal.responseArray = array;
        return retVal;
    }

    private JsonHttpResponseHandler jsonHttpHandler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            log.w("usao72","usao72");
            ServerResponse madeResponse = makeServerResponse(statusCode, headers, response, null);
            log.w("usao73","usao73");
            _calller.OnServerResponse(madeResponse);

        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray arrayResponse){
            log.w("usao74","usao74");
            ServerResponse madeResponse = makeServerResponse(statusCode, headers, null, arrayResponse);
            log.w("usao75","usao75");
            _calller.OnServerResponse(madeResponse);

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
            log.w("usao76","usao76");
            ServerResponse madeResponse = makeServerResponse(statusCode, headers, response, null);
            log.w("usao77","usao77");
            _calller.OnServerResponse(madeResponse);

        }
    };

}
