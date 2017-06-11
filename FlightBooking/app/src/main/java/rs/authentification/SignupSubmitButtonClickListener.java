package rs.authentification;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import parsers.JSONParser;
import parsers.RequestParamParser;
import rs.flightbooking.MainActivity;
import rs.flightbooking.R;
import tools.IServerCaller;
import tools.SendToServer;
import tools.ToastTool;
import tools.response.NodeResponse;
import tools.Session;

public class SignupSubmitButtonClickListener implements View.OnClickListener, IServerCaller {

    SignupActivity _signupActivity;
    private ToastTool _toastTool;
    private SendToServer _nodeServer;

    private String _username;
    private String _password;

    public SignupSubmitButtonClickListener(SignupActivity signupActivity)
    {
        _signupActivity = signupActivity;
        _toastTool = new ToastTool(_signupActivity);
        _nodeServer = new SendToServer(this);
    }

    @Override
    public void onClick(View v) {
        EditText usernameField = (EditText) _signupActivity.findViewById((R.id.username));
        EditText passwordField = (EditText) _signupActivity.findViewById((R.id.password));

        _username = usernameField.getText().toString();
        _password = passwordField.getText().toString();

        RequestParams params = RequestParamParser.makeRequestParamsUserLogin(_username,_password);
        _nodeServer.checkIsLoginCorrect(params);
    }

    private boolean doValidationAndCheckIsValid()
    {
        return true;
    }

    @Override
    public void OnServerResponse(NodeResponse response)
    {
        ArrayList<String> errors = JSONParser.getErrorsFromUserResponse(response.responseObject);
        if(response.statusCode == 200) {
            String username = JSONParser.getUsername(response.responseObject);
            Session session = new Session(_signupActivity.getApplicationContext());
            session.setUsername(username);
            _signupActivity.startActivity((new Intent(_signupActivity, MainActivity.class)));
        } else {
            _toastTool.showList(errors);
        }
    }
}