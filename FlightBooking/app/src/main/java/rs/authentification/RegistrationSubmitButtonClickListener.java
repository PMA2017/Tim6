package rs.authentification;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import parsers.JSONParser;
import rs.flightbooking.R;
import tools.SendToNodeServerTool;
import tools.ToastTool;
import parsers.RequestParamParser;
import tools.response.NodeResponse;

/**
 * Created by n.starcev on 6/8/2017.
 */

public class RegistrationSubmitButtonClickListener implements View.OnClickListener {

    private RegistrationActivity _registrationActivity;
    private ToastTool _toastTool;
    private SendToNodeServerTool _nodeServer;

    private String _username;
    private String _firstname;
    private String _lastname;
    private String _password;
    private String _repeat;


    public RegistrationSubmitButtonClickListener(RegistrationActivity registrationActivity)
    {
        _registrationActivity = registrationActivity;
        _toastTool = new ToastTool(_registrationActivity);
        _nodeServer = new SendToNodeServerTool("User");
    }

    @Override
    public void onClick(View v)
    {
        EditText usernameField = (EditText) _registrationActivity.findViewById((R.id.username));
        EditText firstNameField = (EditText) _registrationActivity.findViewById((R.id.first_name));
        EditText lastNameField = (EditText) _registrationActivity.findViewById((R.id.last_name));
        EditText passwordField = (EditText) _registrationActivity.findViewById((R.id.password));
        EditText repeatPasswordField = (EditText) _registrationActivity.findViewById((R.id.repeat_password));

        _username = usernameField.getText().toString();
        _firstname = firstNameField.getText().toString();
        _lastname = lastNameField.getText().toString();
        _password = passwordField.getText().toString();
        _repeat = repeatPasswordField.getText().toString();

        boolean isValid = doValidationAndCheckIsValid();
        if(isValid) {
            RequestParams params = RequestParamParser.makeRequestParamsUser(_username,_firstname,_lastname,_password,"1");
            NodeResponse response = _nodeServer.post(params);
            boolean result = processResponse(response);
            if(result == true) {
                _registrationActivity.startActivity((new Intent(_registrationActivity, SingupActivity.class)));
            }
        }
    }

    private boolean doValidationAndCheckIsValid()
    {
        ArrayList<String> errors = new ArrayList<String>();
        if(_username.equals("")) {
            errors.add("Username can not be empty");
        }
        if(_firstname.equals("")) {
            errors.add("First name can not be empty");
        }
        if(_lastname.equals("")) {
            errors.add("Last name can not be empty");
        }
        if(_password.length() < 7) {
            errors.add("Password must have minimum 7 characters");
        }
        if(!_repeat.equals(_password)) {
            errors.add("Password and repeat password must be same");
        }

        if(errors.size() > 0) {
            _toastTool.showList(errors);
            return false;
        }
        return true;
    }

    private boolean processResponse(NodeResponse response)
    {
        if(response.statusCode == 200) {
            return true;
        }
        ArrayList<String> errors = JSONParser.getErrorsFromResponse(response.response);
        _toastTool.showList(errors);
        return false;
    }

}
