package rs.authentification;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rs.flightbooking.R;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setSubmitListener();
        setRegisterActivity();
    }

    private void setSubmitListener()
    {
        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new SignupSubmitButtonClickListener(this));
    }

    private void setRegisterActivity()
    {
        TextView register = (TextView) findViewById(R.id.registerHere);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, RegistrationActivity.class));
            }
        });


    }
}
