package rs.reservation.ticket;

import android.app.Activity;

import rs.flightbooking.DatabaseSync;
import rs.flightbooking.R;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import parsers.RequestParamParser;
import rs.reservation.flights.FlightView;
import tools.IServerCaller;
import tools.SendToServer;
import tools.ToastTool;
import tools.response.ServerResponse;
import tools.Session;

/**
 * Created by n.starcev on 6/17/2017.
 */
public class MakeReservationClickListener implements View.OnClickListener, IServerCaller {

    private Ticket _ticket;
    private Activity _activity;
    private ArrayList<FlightView> _flights;
    private Integer _passangers;
    private SendToServer _sendToServer;

    public MakeReservationClickListener(Ticket ticket, ArrayList<FlightView> flights, Integer passangers)
    {
        _ticket = ticket;
        _activity = ticket.getActivity();
        _flights = flights;
        _passangers = passangers;
        _sendToServer = new SendToServer(this);
    }

    @Override
    public void onClick(View v) {
        Session session = new Session(_activity.getApplicationContext());
        String username = session.getUsername();
        RequestParams params = RequestParamParser.makeRequestParamsReserveTicket(_flights,username,_passangers);
        _sendToServer.reserveTickets(params);
    }

    @Override
    public void OnServerResponse(ServerResponse response) {
        if(response.statusCode == 200) {
            Button button = (Button) _activity.findViewById(R.id.reservation);
            button.setEnabled(false);
            button.setBackgroundColor(Color.GRAY);
            ToastTool tool = new ToastTool(_activity);
            DatabaseSync.list_integer.clear();
            _activity.stopService(new Intent(_activity, DatabaseSync.class));
            _activity.startService(new Intent(_activity, DatabaseSync.class));
            String display = _activity.getResources().getString(R.string.reservation_successfully);
            tool.showString(display);
        }
    }
}
