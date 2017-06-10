package rs.authentification;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import rs.flightbooking.R;
import tools.SendToNodeServerTool;
import tools.ToastTool;


public class SingupSubmitButtonClickListener implements View.OnClickListener  {

    SingupActivity _singupActivity;
    private ToastTool _toastTool;
    private SendToNodeServerTool _nodeServer;

    private String _username;
    private String _password;

    public SingupSubmitButtonClickListener(SingupActivity singupActivity)
    {
        _singupActivity = singupActivity;
        _toastTool = new ToastTool(_singupActivity);
        _nodeServer = new SendToNodeServerTool("User");
    }

    @Override
    public void onClick(View v) {
        EditText usernameField = (EditText) _singupActivity.findViewById((R.id.username));
        EditText passwordField = (EditText) _singupActivity.findViewById((R.id.password));

        _username = usernameField.getText().toString();
        _password = passwordField.getText().toString();

    }

    private boolean doValidationAndCheckIsValid()
    {
        _toastTool.showList(new ArrayList<String>());
        return true;
    }

}
