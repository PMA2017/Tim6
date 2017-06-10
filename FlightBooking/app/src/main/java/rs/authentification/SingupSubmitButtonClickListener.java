package rs.authentification;


import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.RequestParams;

import java.util.ArrayList;


import parsers.RequestParamParser;

import rs.flightbooking.R;
import tools.IServerCaller;
import tools.SendToServerTool;
import tools.ToastTool;
import tools.response.NodeResponse;


public class SingupSubmitButtonClickListener implements View.OnClickListener, IServerCaller {

    SingupActivity _singupActivity;
    private ToastTool _toastTool;
    private SendToServerTool _nodeServer;

    private String _username;
    private String _password;

    public SingupSubmitButtonClickListener(SingupActivity singupActivity)
    {
        _singupActivity = singupActivity;
        _toastTool = new ToastTool(_singupActivity);
        _nodeServer = new SendToServerTool(this);
    }

    @Override
    public void onClick(View v) {
        EditText usernameField = (EditText) _singupActivity.findViewById((R.id.username));
        EditText passwordField = (EditText) _singupActivity.findViewById((R.id.password));

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
        _toastTool.showList(new ArrayList<String>());
    }
}
