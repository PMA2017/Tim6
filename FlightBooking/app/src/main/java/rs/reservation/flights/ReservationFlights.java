package rs.reservation.flights;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.loopj.android.http.RequestParams;

import parsers.JSONParser;
import parsers.RequestParamParser;

import java.util.ArrayList;
import java.util.Date;

import rs.flightbooking.R;
import rs.reservation.ticket.Ticket;
import tools.DateUtil;
import tools.IServerCaller;
import tools.SendToServer;
import tools.response.ServerResponse;

import java.util.Calendar;

public class ReservationFlights extends Fragment implements IServerCaller {

    private SendToServer _server;
    private View _rootView;
    private ListView _flightList;
    private FlightAdapter _flightAdapter;

    private ArrayList<ArrayList<FlightView>> _flights = new ArrayList<ArrayList<FlightView>>();
    private FlightView _flightDepart = null;
    private boolean _returnSection = false;

    private boolean _isReturn;
    private String _from;
    private String _to;
    private String _fromName;
    private String _toName;
    private String _depart;
    private String _return;
    private String _passangers;

    private Date _departDate;
    private Date _returnDate;

    public ReservationFlights()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _rootView = inflater.inflate(R.layout.activity_reservation_flights, container, false);
        _flightList = (ListView) _rootView.findViewById(R.id.flightList);
        reloadData();
        RequestParams params = RequestParamParser.makeRequestParamsGetAroundDateCriteria(_from, _to, _depart, _return, _passangers);
        _server = new SendToServer(this);
        _server.getDrivesAroundDate(params);
        //mainMock();
        return _rootView;
    }

    private void reloadData()
    {
        Bundle bundle = this.getArguments();

        _isReturn = bundle.getBoolean("isReturn");
        _from = bundle.getString("from").split("\\(")[1].split("\\)")[0];
        _to = bundle.getString("to").split("\\(")[1].split("\\)")[0];
        _fromName = bundle.getString("from").split("\\(")[0].trim();
        _toName = bundle.getString("to").split("\\(")[0].trim();
        _depart = bundle.getString("depart");
        _return = bundle.getString("return");
        _departDate = convertStringDateFromFormIntoDate(_depart);
        if(_return != null) {
            _returnDate = convertStringDateFromFormIntoDate(_return);
        }
        _passangers = bundle.getString("passangers");
    }

    public void setChangeDateButtons(int start)
    {
        ArrayList<Button> buttons = new ArrayList<Button>();
        Button button1 = (Button) _rootView.findViewById(R.id.first);
        Button button2 = (Button) _rootView.findViewById(R.id.second);
        Button button3 = (Button) _rootView.findViewById(R.id.third);
        Button button4 = (Button) _rootView.findViewById(R.id.fourth);
        Button button5 = (Button) _rootView.findViewById(R.id.fifth);

        button1.setTag(_flights.get(0+start));
        button2.setTag(_flights.get(1+start));
        button3.setTag(_flights.get(2+start));
        button4.setTag(_flights.get(3+start));
        button5.setTag(_flights.get(4+start));

        button1.setTextColor(Color.BLACK);
        button2.setTextColor(Color.BLACK);
        button3.setTextColor(Color.RED);
        button4.setTextColor(Color.BLACK);
        button5.setTextColor(Color.BLACK);

        button1.setText(establishText(-2));
        button2.setText(establishText(-1));
        button3.setText(establishText(0));
        button4.setText(establishText(1));
        button5.setText(establishText(2));

        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        buttons.add(button5);

        button1.setOnClickListener(new ChangeDateButtonClickListener(buttons, button1,this.getActivity(),_flightList));
        button2.setOnClickListener(new ChangeDateButtonClickListener(buttons, button2,this.getActivity(),_flightList));
        button3.setOnClickListener(new ChangeDateButtonClickListener(buttons, button3,this.getActivity(),_flightList));
        button4.setOnClickListener(new ChangeDateButtonClickListener(buttons, button4,this.getActivity(),_flightList));
        button5.setOnClickListener(new ChangeDateButtonClickListener(buttons, button5,this.getActivity(),_flightList));
    }

    public void doNextProcess(FlightView flight)
    {
        if(_isReturn && !_returnSection) {
            _returnSection = true;
            _flightDepart = flight;
            changeToReturnSection();
        } else {
            makeTicket(flight);
        }
    }

    private String establishText(int daysTopDown)
    {
        return getDateForChangeDateButtons(DateUtil.addDays(getDepartOrReturnDate(),daysTopDown));
    }

    private Date getDepartOrReturnDate()
    {
        if(_returnSection) {
            return _returnDate;
        }
        return _departDate;
    }

    private String getDateForChangeDateButtons(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        return new Integer(date.getMonth()).toString() + "/" + new Integer(dayOfMonth).toString();
    }

    private Date convertStringDateFromFormIntoDate(String dateFromForm)
    {
        Integer month = new Integer(dateFromForm.split("/")[0]);
        Integer day = new Integer(dateFromForm.split("/")[1]);
        Integer year = new Integer(dateFromForm.split("/")[2]);
        Date date = new Date(year,month,day);
        return date;
    }

    private void changeToReturnSection()
    {
        setChangeDateButtons(5);
        _flightAdapter.setFlights(_flights.get(7));
        _flightAdapter.notifyDataSetChanged();
    }

    private void makeTicket(FlightView flight)
    {
        Ticket fragment = new Ticket();
        Bundle bundle = new Bundle();

        if(_flightDepart != null) {
            bundle.putSerializable("departFlight",_flightDepart);
            bundle.putSerializable("returnFlight",flight);
        } else {
            bundle.putSerializable("departFlight",flight);
            bundle.putSerializable("returnFlight",null);
        }

        fragment.setArguments(bundle);
        FragmentManager fm= this.getFragmentManager();
        FragmentTransaction t=fm.beginTransaction();
        t.replace(R.id.main_content,fragment);
        t.addToBackStack(null);
        t.commit();
    }

    private void reloadAfterServerCall()
    {
        for(int i = 0; i < _flights.size(); i++) {
            ArrayList<FlightView> flightsInDay = _flights.get(i);
            for(int j = 0; j < flightsInDay.size(); j++) {
                flightsInDay.get(j).townFrom = _from;
                flightsInDay.get(j).townFromName = _fromName;
                flightsInDay.get(j).townTo = _to;
                flightsInDay.get(j).townToName = _toName;
            }
        }
    }

    @Override
    public void OnServerResponse(ServerResponse response) {
        _flights = JSONParser.getAllFlightsFromGetAroungDateResponse(response.responseObject, _isReturn);
        setChangeDateButtons(0);
        reloadAfterServerCall();
        _flightAdapter = new FlightAdapter(this,R.layout.reservation_flights_one_row,_flights.get(2));
        _flightList.setAdapter(_flightAdapter);
    }

    private void mainMock()
    {
        _flights.add(mockData1());
        _flights.add(new ArrayList<FlightView>());
        _flights.add(mockData1());
        _flights.add(mockData2());
        _flights.add(new ArrayList<FlightView>());

        _flights.add(new ArrayList<FlightView>());
        _flights.add(mockData2());
        _flights.add(mockData2());
        _flights.add(new ArrayList<FlightView>());
        _flights.add(mockData1());
    }

    private ArrayList<FlightView> mockData1()
    {
        ArrayList<FlightView> flights = new ArrayList<FlightView>();
        FlightView flight1 = new FlightView();
        flight1.townFrom = "BEG";
        flight1.townFromName = "Belgrad";
        flight1.townTo = "LOS";
        flight1.townToName = "Los Angeles";
        flight1.timeFrom = "10:35";
        flight1.timeTo = "13:00";
        flight1.type = "DIRECT";
        flight1.duration = "11h 25m";
        flight1.company = "Air Serbia";
        flight1.price = "830 €";
        flight1.isFree = true;
        flights.add(flight1);

        FlightView flight2 = new FlightView();
        flight2.townFrom = "BEG";
        flight2.townFromName = "Belgrad";
        flight2.townTo = "LOS";
        flight2.townToName = "Los Angeles";
        flight2.timeFrom = "07:45";
        flight2.timeTo = "20:15";
        flight2.type = "1 STOP";
        flight2.duration = "22h 30m";
        flight2.company = "Air Serbia";
        flight2.price = "760 €";
        flight2.isFree = false;
        flights.add(flight2);
        return flights;
    }

    private ArrayList<FlightView> mockData2()
    {
        ArrayList<FlightView> flights = new ArrayList<FlightView>();
        FlightView flight1 = new FlightView();
        flight1.townFrom = "ROM";
        flight1.townFromName = "Roma";
        flight1.townTo = "NWK";
        flight1.townToName = "New York";
        flight1.timeFrom = "13:35";
        flight1.timeTo = "16:00";
        flight1.type = "DIRECT";
        flight1.duration = "11h 25m";
        flight1.company = "Air Serbia";
        flight1.price = "910 €";
        flight1.isFree = true;
        flights.add(flight1);

        FlightView flight2 = new FlightView();
        flight2.townFrom = "ROM";
        flight2.townFromName = "Roma";
        flight2.townTo = "NWK";
        flight2.townToName = "New York";
        flight2.timeFrom = "06:45";
        flight2.timeTo = "17:15";
        flight2.type = "1 STOP";
        flight2.duration = "20h 30m";
        flight2.company = "Air Serbia";
        flight2.price = "730 €";
        flight2.isFree = false;
        flights.add(flight2);
        return flights;
    }

}
