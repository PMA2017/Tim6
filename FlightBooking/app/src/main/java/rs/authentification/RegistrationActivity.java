package rs.authentification;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rs.flightbooking.R;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setSubmitListener();
        setSingupListener();
    }

    private void setSubmitListener()
    {
        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new RegistrationSubmitButtonClickListener(this));
    }

    private void setSingupListener()
    {
        TextView singup = (TextView) findViewById(R.id.singup);
        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, SingupActivity.class));
            }
        });
    }
}
