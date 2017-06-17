package rs.authentification;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import parsers.JSONParser;
import parsers.RequestParamParser;


import rs.flights.FlightAddFragment;
import rs.flightbooking.MainActivity;
import rs.flightbooking.R;
import tools.IServerCaller;
import tools.SendToServer;
import tools.ToastTool;
import tools.response.ServerResponse;
import tools.Session;

import static com.loopj.android.http.AsyncHttpClient.log;

public class SignupSubmitButtonClickListener implements View.OnClickListener, IServerCaller {

    SignupActivity _signupActivity;
    private ToastTool _toastTool;
    private SendToServer _server;

    private String _username;
    private String _password;

    public SignupSubmitButtonClickListener(SignupActivity signupActivity)
    {
        _signupActivity = signupActivity;
        _toastTool = new ToastTool(_signupActivity);
        _server = new SendToServer(this);
    }

    @Override
    public void onClick(View v) {
        log.w("usao51","usao51");
        EditText usernameField = (EditText) _signupActivity.findViewById((R.id.username));
        EditText passwordField = (EditText) _signupActivity.findViewById((R.id.password));

        _username = usernameField.getText().toString();
        _password = passwordField.getText().toString();

        log.w("usao52","usao52");
        RequestParams params = RequestParamParser.makeRequestParamsUserLogin(_username,_password);
        _server.checkIsLoginCorrect(params);
        log.w("usao53","usao53");

    }

    private boolean doValidationAndCheckIsValid()
    {
        return true;
    }

    @Override
    public void OnServerResponse(ServerResponse response)
    {

        ArrayList<String> errors = JSONParser.getErrorsFromUserResponse(response.responseObject);
        if(response.statusCode == 200) {

            String username = JSONParser.getUsername(response.responseObject);

            int id = JSONParser.getUserId(response.responseObject);

            Session session = new Session(_signupActivity.getApplicationContext());
            session.setUsername(username);
            session.setId(String.valueOf(id));




            _signupActivity.startActivity((new Intent(_signupActivity, MainActivity.class)));
        } else {
            _toastTool.showList(errors);
        }
    }
}
