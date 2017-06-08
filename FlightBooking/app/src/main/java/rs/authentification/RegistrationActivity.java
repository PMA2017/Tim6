package rs.authentification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import rs.flightbooking.R;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        setSubmitListener();



    }

    private void setSubmitListener()
    {
        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new RegistrationSubmitButtonClickListener(this));
    }



}
