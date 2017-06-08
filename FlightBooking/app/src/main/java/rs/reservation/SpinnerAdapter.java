package rs.reservation;

import android.content.Context;
import android.graphics.Color;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter<String> {

    private String hint;

    public SpinnerAdapter(Context context, int textViewResourceId, ArrayList<String> towns, String hint) {

        super(context, textViewResourceId, towns);
        this.hint = hint;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = null;
        if (position >= getCount()) { //logic to display hint
            v = super.getView(0, convertView, parent);
            ((TextView) v.findViewById(android.R.id.text1)).setText("");
            ((TextView) v.findViewById(android.R.id.text1)).setHint(hint); //"Hint to be displayed"
        }
        else v = super.getView(position, convertView, parent);
        return v;
    }

    @Override
    public View getDropDownView (int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView tv = (TextView) view;
        if(position == 0){
            tv.setTextColor(Color.GRAY);
            tv.setEnabled(false);
            tv.setCursorVisible(false);
            tv.setSystemUiVisibility(View.INVISIBLE);
            //tv.setVisibility(View.INVISIBLE);
        }
        else {
            tv.setTextColor(Color.BLACK);
        }
        return view;
    }

    @Override
    public int getCount() {
        int count = super.getCount();
        //return count>0 ? count-1 : count ;
        return count;
    }
}