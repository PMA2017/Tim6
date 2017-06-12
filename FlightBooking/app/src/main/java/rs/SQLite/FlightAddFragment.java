package rs.SQLite;

/**
 * Created by Rale on 6/11/2017.
 */

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import rs.flightbooking.R;

import static com.loopj.android.http.AsyncHttpClient.log;

//
public class FlightAddFragment extends Fragment implements OnClickListener{

    // UI references
    private EditText flTownFormEtxt;
    private EditText flTownToEtxt;
    private EditText flDateFromEtxt;
    private EditText flDateToEtxt;
    private Button addButton;
    private Button resetButton;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    DatePickerDialog datePickerDialog;
    DatePickerDialog datePickerDialog1;
    Calendar dateCalendar;
    Calendar dateCalendar1;

    Flight flight = null;
    private FlightDAO flightDAO;
    private AddFlightTask task;

    public static final String ARG_ITEM_ID = "flight_add_fragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flightDAO = new FlightDAO(getActivity());
        log.w("a1","a1");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_flight, container,
                false);

        findViewsById(rootView);

        setListeners();

        //For orientation change.
        if (savedInstanceState != null) {

            dateCalendar = Calendar.getInstance();
            if (savedInstanceState.getLong("dateCalendar") != 0)
                dateCalendar.setTime(new Date(savedInstanceState
                        .getLong("dateCalendar")));

            dateCalendar1 = Calendar.getInstance();
            if (savedInstanceState.getLong("dateCalendar1") != 0)
                dateCalendar1.setTime(new Date(savedInstanceState
                        .getLong("dateCalendar1")));

        }

        log.w("a2","a2");

        return rootView;
    }

   private void setListeners() {
        flDateFromEtxt.setOnClickListener(this);
       flDateToEtxt.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
       Calendar newCalendar1 = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getActivity(),
                new OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dateCalendar = Calendar.getInstance();
                        dateCalendar.set(year, monthOfYear, dayOfMonth);
                        flDateFromEtxt.setText(formatter.format(dateCalendar
                                .getTime()));
                    }

                }, newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));

       log.w("a3","a3");

                datePickerDialog1 = new DatePickerDialog(getActivity(),
               new OnDateSetListener() {

                   public void onDateSet(DatePicker view, int year,
                                         int monthOfYear, int dayOfMonth) {
                       dateCalendar1 = Calendar.getInstance();
                       dateCalendar1.set(year, monthOfYear, dayOfMonth);
                       flDateToEtxt.setText(formatter.format(dateCalendar1
                               .getTime()));
                   }

               }, newCalendar1.get(Calendar.YEAR),
               newCalendar1.get(Calendar.MONTH),
               newCalendar1.get(Calendar.DAY_OF_MONTH));

        addButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
    }

    protected void resetAllFields() {
        flTownFormEtxt.setText("");
        flTownToEtxt.setText("");
        flDateFromEtxt.setText("");
        flDateToEtxt.setText("");
    }

    private void setFlight() {
        flight = new Flight();
        flight.setTownFrom(flTownFormEtxt.getText().toString());
        flight.setTownTo(flTownToEtxt.getText().toString());
        if (dateCalendar != null)
            flight.setDateFrom(dateCalendar.getTime());

        if (dateCalendar1 != null)
            flight.setDateTo(dateCalendar1.getTime());

        log.w("a4","a4");
    }
   /* @Override
    public void onResume() {
        log.w("res","res");
        getActivity().setTitle(R.string.add_emp);
        getActivity().getActionBar().setTitle(R.string.add_emp);
        super.onResume();
        log.w("res1","res1");
    }*/

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if (dateCalendar != null)
            outState.putLong("dateCalendar", dateCalendar.getTime().getTime());

        if (dateCalendar1 != null)
            outState.putLong("dateCalendar1", dateCalendar1.getTime().getTime());
        log.w("a5","a5");
    }

    private void findViewsById(View rootView) {
        flTownFormEtxt = (EditText) rootView.findViewById(R.id.etxt_townFrom);
        flTownToEtxt = (EditText) rootView.findViewById(R.id.etxt_townTo);
        flDateFromEtxt = (EditText) rootView.findViewById(R.id.etxt_dateFrom);
        flDateFromEtxt.setInputType(InputType.TYPE_NULL);
        flDateToEtxt = (EditText) rootView.findViewById(R.id.etxt_dateTo);
        flDateToEtxt.setInputType(InputType.TYPE_NULL);
        addButton = (Button) rootView.findViewById(R.id.button_add);
        resetButton = (Button) rootView.findViewById(R.id.button_reset);
        log.w("a6","a6");
    }

    @Override
    public void onClick(View view) {
        if (view == flDateFromEtxt) {
            datePickerDialog.show();
        }else if(view == flDateToEtxt) {
            datePickerDialog1.show();
        }else if (view == addButton) {
            setFlight();
            task = new AddFlightTask(getActivity());
            task.execute((Void) null);
            log.w("a7","a7");

        } else if (view == resetButton) {
            resetAllFields();
        }
    }

    public class AddFlightTask extends AsyncTask<Void, Void, Long> {

        private final WeakReference<Activity> activityWeakRef;


        public AddFlightTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected Long doInBackground(Void... arg0) {

            log.w("a8", String.valueOf(flight.getDateFrom()));
            log.w("a9", String.valueOf(flight.getDateTo()));
            long result = flightDAO.save(flight);
            log.w("rr","rr");
            return result;
        }

        @Override
        protected void onPostExecute(Long result) {
            log.w("a22","a22");
            log.w("result", result.toString());

            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {

                log.w("a23","a23");

                if (result != -1) {

                    log.w("a24","a24");
                    Toast.makeText(activityWeakRef.get(), "Flight Saved",
                            Toast.LENGTH_LONG).show();
                }
            }
            log.w("a10","a10");
        }
    }
}
