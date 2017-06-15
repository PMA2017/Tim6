package rs.reservation.form;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

import static android.support.design.R.style.Theme_AppCompat;

/**
 * Created by Nemanja on 6/11/2017.
 */

public class ButtonClickListenerSetDate implements View.OnClickListener {

    private Activity _activity;
    private Button _button;

    public ButtonClickListenerSetDate(Activity activity, Button button)
    {
        _activity = activity;
        _button = button;
    }

    @Override
    public void onClick(View v) {
        Calendar c = Calendar.getInstance();
        int year  = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day   = c.get(Calendar.DAY_OF_MONTH);
        OnDateSetListener listener = new OnDateSetListener(_button);
        DatePickerDialog picker = new DatePickerDialog(_activity, listener,year,month,day);
        picker.show();
    }

    private class OnDateSetListener implements  DatePickerDialog.OnDateSetListener
    {
        private  Button _button;

        public OnDateSetListener(Button button)
        {
            _button = button;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
        {
            StringBuilder text = new StringBuilder().append(month + 1)
                    .append("/").append(dayOfMonth).append("/").append(year)
                    .append(" ");
            if(_button.getText().toString().contains(":")) {
                _button.setText(_button.getText().toString().split(":")[0]);
            }
            _button.setText(_button.getText() + ": " + text.toString());
            _button.setTag(month+"/"+dayOfMonth+"/"+year);

        }
    }
}
