package rs.reservation.form;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Parcelable;
import android.view.View;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Date;

import rs.flightbooking.R;
import rs.reservation.flights.FlightView;
import rs.reservation.flights.ReservationFlights;
import tools.IServerCaller;
import tools.ToastTool;
import tools.response.ServerResponse;

/**
 * Created by Nemanja on 6/13/2017.
 */

public class ButtonFindClick implements View.OnClickListener {

    private Activity _activity;
    private ToastTool _tool;

    public ButtonFindClick(Activity activity)
    {
        _activity = activity;
        _tool = new ToastTool(_activity);
    }

    @Override
    public void onClick(View v) {
        ReservationFlights fragment = new ReservationFlights();
        Bundle bundle=new Bundle();

        CheckBox returnCheck = (CheckBox) _activity.findViewById(R.id.checkbox);

        AutoCompleteTextView fromTown = (AutoCompleteTextView) _activity.findViewById(R.id.from);
        AutoCompleteTextView toTown = (AutoCompleteTextView) _activity.findViewById(R.id.to);

        Button departButton = (Button) _activity.findViewById(R.id.dateDepart);
        Button returnButton = (Button) _activity.findViewById(R.id.dateReturn);

        Spinner passangersSpinner = (Spinner) _activity.findViewById(R.id.personsSpinner);

        boolean isReturn = returnCheck.isChecked();
        String from = fromTown.getText().toString();
        String to = toTown.getText().toString();
        String depart = (String) departButton.getTag();
        String returnn = (String) returnButton.getTag();
        String passangers = passangersSpinner.getSelectedItem().toString();

        boolean result = doValidationAndCheckIsCorrect(isReturn, from, to, depart, returnn);
        if(!result) {
            return;
        }

        bundle.putBoolean("isReturn",isReturn);
        bundle.putString("from",from);
        bundle.putString("to",to);
        bundle.putString("depart", depart);
        bundle.putString("return", returnn);
        bundle.putString("passangers",passangers);

        fragment.setArguments(bundle);
        FragmentManager fm= _activity.getFragmentManager();
        FragmentTransaction t=fm.beginTransaction();
        t.replace(R.id.main_content,fragment);
        t.addToBackStack(null);
        t.commit();
    }

    private boolean doValidationAndCheckIsCorrect(boolean isReturn, String from, String to, String depart, String returnn)
    {
        ArrayList<String> errors = new ArrayList<String>();
        String error = "";
        if(from.equals("")) {
            error = _activity.getResources().getString(R.string.reservations_form_town_from_error);
            errors.add(error);
        }
        if(to.equals("")) {
            error = _activity.getResources().getString(R.string.reservations_form_town_to_error);
            errors.add(error);
        }
        if(depart == null) {
            error = _activity.getResources().getString(R.string.reservations_form_depart_date_error);
            errors.add(error);
        }
        if(returnn == null) {
            error = _activity.getResources().getString(R.string.reservations_form_return_date_error);
            errors.add(error);
        }
        if(errors.size() > 0) {
            _tool.showList(errors);
            return false;
        }
        return true;
    }

}
