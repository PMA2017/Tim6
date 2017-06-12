package rs.reservation;

import android.app.Activity;
import android.app.DatePickerDialog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import parsers.JSONParser;

import rs.flightbooking.R;
import tools.IServerCaller;
import tools.SendToServer;
import tools.response.ServerResponse;

public class Reservations extends Fragment implements IServerCaller {

    private SendToServer _server;
    private View _rootView;

    private ArrayList<String> _towns;

    public Reservations()
    {
        _server = new SendToServer(this);
        _towns = new ArrayList<String>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        _rootView = inflater.inflate(R.layout.activity_reservations, container, false);

        setCheckboxListener();
        setSpinner();
        setButtonsDateListeners();

        _server.get("Town");

        return _rootView;
    }

    private void setCheckboxListener()
    {
        CheckBox checkBox = (CheckBox) _rootView.findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(new CheckboxChangedListener(_rootView));
    }

    private void setSpinner()
    {
        Spinner personsSpinner = (Spinner) _rootView.findViewById(R.id.personsSpinner);
        ArrayList<String> elements = new ArrayList<String>();
        for(int i = 0; i < 4; i++) {
            String element = new Integer(i+1).toString();
            elements.add(element);
        }
        personsSpinner.setAdapter(new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,elements));
    }

    private void setTowns(ArrayList<String> towns)
    {
        AutoCompleteTextView from = (AutoCompleteTextView) _rootView.findViewById(R.id.from);
        AutoCompleteTextView to = (AutoCompleteTextView) _rootView.findViewById(R.id.to);

        from.setAdapter(new ArrayAdapter<String>(this.getActivity(),R.layout.support_simple_spinner_dropdown_item,towns));
        to.setAdapter(new ArrayAdapter<String>(this.getActivity(),R.layout.support_simple_spinner_dropdown_item,towns));
    }

    private void setButtonsDateListeners()
    {
        Button depart = (Button) _rootView.findViewById(R.id.dateDepart);
        Button ret = (Button) _rootView.findViewById(R.id.dateReturn);

        depart.setOnClickListener(new ButtonClickListenerSetDate(this.getActivity(), depart));
        ret.setOnClickListener(new ButtonClickListenerSetDate(this.getActivity(), ret));
    }

    public void OnServerResponse(ServerResponse response)
    {
        _towns = JSONParser.getAllTowns(response.responseArray);
        if(response.statusCode == 200) {
            setTowns(_towns);
        }
    }
}
