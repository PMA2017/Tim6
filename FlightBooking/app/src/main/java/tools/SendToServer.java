package tools;

import android.content.Intent;

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
        HttpUtils.get("/api/User/getFlights/"+id ,null, jsonHttpHandler);
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
        HttpUtils.post("/api/User/postLoginData", params, jsonHttpHandler);
    }

    public void getDrivesAroundDate(RequestParams params)
    {
        HttpUtils.post("/api/generic/getDrivesAroundDate", params, jsonHttpHandler);
    }

    public void reserveTickets(RequestParams params)
    {
        HttpUtils.post("/api/reserveTicket", params, jsonHttpHandler);
    }

    public void getRatingForFlight(Integer flightId, Integer userId)
    {
        HttpUtils.get("/api/getRatingForFlight/"+flightId.toString()+"/"+userId.toString(),null,jsonHttpHandler);
    }

    public void getRatingsForFlights(Integer userId,RequestParams params){
        HttpUtils.post("/api/getRatingsForFlights/"+userId.toString(),params,jsonHttpHandler);
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
            ServerResponse madeResponse = makeServerResponse(statusCode, headers, response, null);
            _calller.OnServerResponse(madeResponse);

        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray arrayResponse){
            ServerResponse madeResponse = makeServerResponse(statusCode, headers, null, arrayResponse);
            _calller.OnServerResponse(madeResponse);

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
            ServerResponse madeResponse = makeServerResponse(statusCode, headers, response, null);
            _calller.OnServerResponse(madeResponse);

        }
    };
}
