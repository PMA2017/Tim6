package rs.contact;


import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import parsers.JSONParser;
import rs.flightbooking.R;
import rs.flightbooking.databinding.ActivityContactBinding;
import tools.HttpUtils;
import tools.IServerCaller;
import tools.SendToServer;
import tools.ToastTool;
import tools.response.ServerResponse;

import static android.R.attr.data;
import static android.R.attr.dial;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment implements IServerCaller {

    private SendToServer _server;
    private  ActivityContactBinding binding;

    private ToastTool _toastTool = new ToastTool(this.getActivity());

    public ContactFragment() {
        // Required empty public constructor
        _server = new SendToServer(this);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_contact, container, false);


        _server.getAirline();
        binding = DataBindingUtil.inflate(
                inflater, R.layout.activity_contact, container, false);
        View view = binding.getRoot();
        final TextView phoneTextView = (TextView)view.findViewById(R.id.phoneNumberTextView);
        //here data must be an instance of the class MarsDataProvider
        view.findViewById(R.id.callButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                    dialIntent.setData(Uri.parse("tel:"+phoneTextView.getText()));
                    startActivity(dialIntent);
                } catch (ActivityNotFoundException activityException) {
                    Log.e("Dialing a Phone Number", "Dial failed", activityException);
                }
            }

        });

        return view;

    }

    @Override
    public void OnServerResponse(ServerResponse response) {
        if(response.statusCode==200) {
            Airline airline = JSONParser.getAirline(response.responseObject);
            binding.setAirline(airline);
        }
        else {
            Airline airline = new Airline("Default","Default","+3811100000","Belgrade");
            binding.setAirline(airline);
        }

    }


}
