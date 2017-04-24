package rs.flightbooking;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Rale on 4/23/2017.
 */

public class Fragment1 extends Fragment {
    public Fragment1() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.activity_one, container, false);
        return rootView;
    }


}
