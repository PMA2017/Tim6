package tools;

import android.app.Activity;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * Created by n.starcev on 6/8/2017.
 */

public class ToastTool {

    private Activity _activity;

    public ToastTool(Activity activity)
    {
        _activity = activity;
    }

    public void showList(ArrayList<String> list)
    {
        String error = convertArrayListToLineString(list);
        Toast.makeText(_activity, error, Toast.LENGTH_LONG).show();
    }

    private String convertArrayListToLineString(ArrayList<String> list)
    {
        String retVal = "";
        for(int i = 0; i < list.size(); i++) {
            retVal += list.get(i).toString();
            if(list.size() -1 != i) {
                retVal += "\n";
            }
        }
        return retVal;
    }

    public void showString(String error)
    {
        Toast.makeText(_activity, error, Toast.LENGTH_LONG).show();
    }
}
