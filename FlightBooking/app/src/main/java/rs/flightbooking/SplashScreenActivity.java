package rs.flightbooking;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;

import org.json.JSONArray;

import rs.SQLite.FlightDAO;
import rs.SQLite.FlightDBDAO;
import rs.authentification.RegistrationActivity;
import rs.authentification.SignupActivity;
import rs.reservation.form.Reservations;
import tools.IServerCaller;
import tools.SendToServer;
import tools.Session;
import tools.response.ServerResponse;


/**
 * Created by Milos on 5/20/2017.
 */

public class SplashScreenActivity extends Activity implements IServerCaller {
    private static int SPLASH_TIME_OUT = 3000; // splash ce biti vidljiv minimum SPLASH_TIME_OUT milisekundi

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        //getFlightsFromNodeServer();
        // uradi inicijalizaciju u pozadinksom threadu
        new InitTask().execute();
    }

    private class InitTask extends AsyncTask<Void, Void, Void>
    {
        private long startTime;

        @Override
        protected void onPreExecute()
        {
            startTime = System.currentTimeMillis();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            continueLogin();
            return null;
        }

        private void continueLogin()
        {
            // sacekaj tako da splash bude vidljiv minimum SPLASH_TIME_OUT milisekundi
            long timeLeft = SPLASH_TIME_OUT - (System.currentTimeMillis() - startTime);
            if(timeLeft < 0) timeLeft = 0;
            SystemClock.sleep(timeLeft);

            startMainActivity();
        }
    }


    private void startMainActivity()
    {
        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
        finish();
    }

    private void getFlightsFromNodeServer()
    {
        SendToServer server = new SendToServer(this);
        Session session = new Session(this.getApplicationContext());
        Integer id = new Integer(session.getId());
        server.checkFlights(id);
    }

    @Override
    public void OnServerResponse(ServerResponse response) {
        JSONArray array = response.responseArray;
        FlightDAO dao = new FlightDAO(this);
       // startMainActivity();
    }

}
