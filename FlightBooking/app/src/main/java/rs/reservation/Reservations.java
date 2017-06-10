package rs.reservation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.util.TypedValue;
import android.content.res.Resources;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;
import parsers.JSONParser;

import tools.HttpUtils;
import rs.flightbooking.R;

public class Reservations extends Fragment {

    public Reservations() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View rootView = inflater.inflate(R.layout.activity_reservations, container, false);

        Spinner spinnerFrom = (Spinner) rootView.findViewById(R.id.townFrom);
        Spinner spinnerTo = (Spinner) rootView.findViewById(R.id.townTo);

        ArrayList<String> towns = getDataForSpinner();
        setSpinnerWithData(towns,spinnerFrom);
        setSpinnerWithData(towns,spinnerTo);

        TextView dateFromText = (TextView) rootView.findViewById(R.id.dateTextFrom);
        Button dateFrom = (Button) rootView.findViewById(R.id.dateFrom);
        datePicker(dateFromText,dateFrom);

        TextView dateToText = (TextView) rootView.findViewById(R.id.dateTextTo);
        Button dateTo = (Button) rootView.findViewById(R.id.dateTo);
        datePicker(dateToText,dateTo);

        Button findButton = (Button) rootView.findViewById(R.id.FindButton);
        final Fragment fragment = this;
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spinnerFrom = (Spinner) rootView.findViewById(R.id.townFrom);
                Spinner spinnerTo = (Spinner) rootView.findViewById(R.id.townTo);
                TextView dateFromText = (TextView) rootView.findViewById(R.id.dateTextFrom);
                String textTownFrom = spinnerFrom.getSelectedItem().toString();
                String textTownTo = spinnerTo.getSelectedItem().toString();
                String textDateFrom = dateFromText.getText().toString();
                String errors = "";
                if(textTownFrom.equals("")) {
                    errors += "Input for town from is required\n";
                }
                if(textTownTo.equals("")) {
                    errors += "Input for town from is required\n";
                }
                if(textDateFrom.equals("")) {
                    errors += "Input for dete of departure is required\n";
                }
                if(!errors.equals("")) {
                    Toast.makeText(fragment.getActivity(),errors,Toast.LENGTH_LONG).show();
                }
            }
        });

        return rootView;
    }

    private ArrayList<String> getDataForSpinner()
    {
        final ArrayList<String> items = new ArrayList<>();
        items.add("Select town");
        final String retTowns[];
        HttpUtils.get("api/get/Town", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray arrayResponse) {
                String towns[] = JSONParser.GetAllTowns(arrayResponse);
                for(int i = 0; i < towns.length; i++) {
                    items.add(towns[i]);
                }
            }
        });
        return items;
    }

    private void setSpinnerWithData(ArrayList<String> towns, Spinner spinner) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item,towns);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner.setAdapter(dataAdapter);

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this.getActivity(), android.R.layout.simple_list_item_1, towns, "Select town");
        spinner.setAdapter(spinnerAdapter);
        //spinner.setSelection(3);

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinner);
            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, r.getDisplayMetrics());
            popupWindow.setHeight((int)px);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {

        }
    }

    private void datePicker(final TextView text, final Button button)
    {
        final DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear,
                                  int selectedMonth, int selectedDay) {
                int year = selectedYear;
                int month = selectedMonth;
                int day = selectedDay;
                text.setText(new StringBuilder().append(month + 1)
                        .append("-").append(day).append("-").append(year)
                        .append(" "));

            }
        };

        final Activity activity = this.getActivity();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year  = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day   = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog picker = new DatePickerDialog(activity,pickerListener,year,month,day);
                picker.show();
            }
        });
    }
}
