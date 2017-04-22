package rs.flightbooking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void letovi(View view){

        startActivity(new Intent(this,ActivityOne.class));

    }

    public void avio_kompanije(View view){

        startActivity(new Intent(this,ActivityTwo.class));

    }

    public void registracija(View view){

        startActivity(new Intent(this,ActivityThree.class));

    }


}
