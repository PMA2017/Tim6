package rs.reservation.form;

import android.view.View;
import android.widget.CompoundButton;

import rs.flightbooking.R;

/**
 * Created by Nemanja on 6/11/2017.
 */

public class CheckboxChangedListener implements CompoundButton.OnCheckedChangeListener {

    private View _rootView;
    private boolean _returnLayoutVisible = true;
    private View _returnLayout = null;

    public CheckboxChangedListener(View rootView)
    {
        _rootView = rootView;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(_returnLayout == null) {
            _returnLayout = (View) _rootView.findViewById(R.id.returnLayout);
        }
        if(_returnLayoutVisible == true) {
            _returnLayout.setVisibility(View.INVISIBLE);
            _returnLayoutVisible = false;
        } else {
            _returnLayout.setVisibility(View.VISIBLE);
            _returnLayoutVisible = true;
        }
    }
}
