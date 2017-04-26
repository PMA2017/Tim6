package rs.flightbooking;

import  static rs.flightbooking.Constants.FIRST_COLUMN;
import  static rs.flightbooking.Constants.SECOND_COLUMN;
import  static rs.flightbooking.Constants.THIRD_COLUMN;
import  static rs.flightbooking.Constants.FOURTH_COLUMN;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rale on 4/26/2017.
 */

public class ListViewAdapter extends BaseAdapter {

    public ArrayList<HashMap<String,String>> list;
    Activity activity;

    public ListViewAdapter(Activity activity,ArrayList<HashMap<String,String>> list){
        super();
        this.activity=activity;
        this.list=list;

    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private  class ViewHolder{
        TextView txtFirst;
        TextView txtSecond;
        TextView txtThird;
        TextView txtFourth;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){
            convertView=inflater.inflate(R.layout.column_row,null);
            holder= new ViewHolder();

            holder.txtFirst = (TextView) convertView.findViewById(R.id.TextFirst);
            holder.txtSecond = (TextView) convertView.findViewById(R.id.TextSecond);
            holder.txtThird = (TextView) convertView.findViewById(R.id.TextThird);
            holder.txtFourth = (TextView) convertView.findViewById(R.id.TextFourth);

            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        HashMap<String,String> map=list.get(position);
        holder.txtFirst.setText(map.get(FIRST_COLUMN));
        holder.txtSecond.setText(map.get(SECOND_COLUMN));
        holder.txtThird.setText(map.get(THIRD_COLUMN));
        holder.txtFourth.setText(map.get(FOURTH_COLUMN));

        return  convertView;
    }
}
