package rs.settings;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;

import rs.flightbooking.MainActivity;
import rs.flightbooking.R;
import rs.flightbooking.databinding.ActivityContactBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_settings, container, false);
        final Activity thisActivity = this.getActivity();

        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();

        final Spinner languageSpinner = (Spinner)v.findViewById(R.id.languageSpinner);
        if(conf.locale.getLanguage().equals("sr"))
        {

            languageSpinner.setSelection(0);

        }
        else
        {
            languageSpinner.setSelection(1);
        }

        //important localization testing
        ((Spinner)v.findViewById(R.id.languageSpinner)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){

                }

                if(position==1) {
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ((Button)v.findViewById(R.id.apply)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String language = "en";
                if(languageSpinner.getSelectedItemPosition()==0)
                {
                    language = "sr";
                }
                else language = "en";
                Resources res = getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                if(conf.locale.getLanguage().equals(language)){
                    return;
                }

                Locale myLocale = new Locale(language);
                conf.locale = myLocale;
                res.updateConfiguration(conf, dm);
                Intent refresh = new Intent(thisActivity, MainActivity.class);
                startActivity(refresh);
                thisActivity.finish();
            }
        });


        return v;

    }

}
