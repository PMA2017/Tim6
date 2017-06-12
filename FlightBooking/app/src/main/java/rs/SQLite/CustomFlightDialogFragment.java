package rs.SQLite;

/**
 * Created by Rale on 6/11/2017.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import rs.flightbooking.MainActivity;
import rs.flightbooking.R;

import static com.loopj.android.http.AsyncHttpClient.log;


public class CustomFlightDialogFragment extends DialogFragment {

    // UI references
    private EditText flightTownFromEtxt;
    private EditText flightTownToEtxt;
    private EditText flightDateFromEtxt;
    private EditText flightDateToEtxt;
    private LinearLayout submitLayout;

    private Flight flight;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    FlightDAO flightDAO;

    public static final String ARG_ITEM_ID = "flight_dialog_fragment";



    public interface FlightDialogFragmentListener {
        void onFinishDialog();

    }

    public CustomFlightDialogFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        flightDAO = new FlightDAO(getActivity());

        Bundle bundle = this.getArguments();
        flight = bundle.getParcelable("selectedFlight");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View customDialogView = inflater.inflate(R.layout.fragment_add_flight, null);
        builder.setView(customDialogView);

        flightTownFromEtxt = (EditText) customDialogView.findViewById(R.id.etxt_townFrom);
        flightTownToEtxt = (EditText) customDialogView.findViewById(R.id.etxt_townTo);
        flightDateFromEtxt = (EditText) customDialogView.findViewById(R.id.etxt_dateFrom);
        flightDateToEtxt = (EditText) customDialogView.findViewById(R.id.etxt_dateTo);
        submitLayout = (LinearLayout) customDialogView.findViewById(R.id.layout_submit);
        submitLayout.setVisibility(View.GONE);

        setValue();

        builder.setTitle(R.string.update_flight);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.update,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            flight.setDateFrom(formatter.parse(flightDateFromEtxt.getText().toString()));

                        } catch (ParseException e) {
                            Toast.makeText(getActivity(),
                                    "Invalid date format!",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        try {
                        flight.setDateTo(formatter.parse(flightDateToEtxt.getText().toString()));
                        } catch (ParseException e) {
                        Toast.makeText(getActivity(),
                                "Invalid date format!",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                        flight.setTownFrom(flightTownFromEtxt.getText().toString());
                        flight.setTownTo(flightTownToEtxt.getText().toString());
                        long result = flightDAO.update(flight);

                        /*if (result > 0) {
                            FlightActivity activity = (FlightActivity) getActivity();
                            activity.onFinishDialog();
                        } else {
                            Toast.makeText(getActivity(),
                                    "Unable to update flight",
                                    Toast.LENGTH_SHORT).show();
                        }*/
                    }
                }).setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();

        return alertDialog;
    }

    private void setValue() {
        if (flight != null) {

            flightTownFromEtxt.setText(flight.getTownFrom());
            flightTownToEtxt.setText(flight.getTownTo() + "");
            flightDateFromEtxt.setText(formatter.format(flight.getDateFrom()));
            flightDateToEtxt.setText(formatter.format(flight.getDateTo()));
        }
    }
}
