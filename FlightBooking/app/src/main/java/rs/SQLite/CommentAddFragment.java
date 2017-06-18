package rs.SQLite;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import rs.flightbooking.R;

import static com.loopj.android.http.AsyncHttpClient.log;

/**
 * Created by Rale on 6/14/2017.
 */

public class CommentAddFragment  extends Fragment implements View.OnClickListener {

    // UI references
    private EditText flCommentEtxt;

    private Button addButton;
    private Button resetButton;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

 

    Comment comment = null;
    private CommentDAO commentDAO;
    private CommentAddFragment.AddCommentTask task;

    public static final String ARG_ITEM_ID = "comment_add_fragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commentDAO = new CommentDAO(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_comment, container,
                false);

        findViewsById(rootView);

        setListeners();


        return rootView;
    }

    private void setListeners() {

        addButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
    }

    protected void resetAllFields() {
        flCommentEtxt.setText("");

    }

    private void setComment() {
        comment = new Comment();

    }


    private void findViewsById(View rootView) {
        flCommentEtxt = (EditText) rootView.findViewById(R.id.etxt_comment);
        addButton = (Button) rootView.findViewById(R.id.button_add1);
        resetButton = (Button) rootView.findViewById(R.id.button_reset1);
    }

    @Override
    public void onClick(View view) {
      if (view == addButton) {
            setComment();
            task = new CommentAddFragment.AddCommentTask(getActivity());
            task.execute((Void) null);

        } else if (view == resetButton) {
            resetAllFields();
        }
    }

    public class AddCommentTask extends AsyncTask<Void, Void, Long> {

        private final WeakReference<Activity> activityWeakRef;


        public AddCommentTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected Long doInBackground(Void... arg0) {

            long result = commentDAO.save(comment);

            return result;
        }

        @Override
        protected void onPostExecute(Long result) {

            log.w("result", result.toString());

            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {


                if (result != -1) {

                    Toast.makeText(activityWeakRef.get(), "Comment Saved",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
