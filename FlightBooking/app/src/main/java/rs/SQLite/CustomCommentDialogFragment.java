package rs.SQLite;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import rs.flightbooking.R;

import static com.loopj.android.http.AsyncHttpClient.log;

/**
 * Created by Rale on 6/14/2017.
 */

public class CustomCommentDialogFragment extends DialogFragment {

    // UI references
    private EditText commentCommentEtxt;

    private LinearLayout submitLayout;

    private Comment comment;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    CommentDAO commentDAO;

    private AddCommentTask task;

    public static final String ARG_ITEM_ID = "comment_dialog_fragment";



    public interface CommentDialogFragmentListener {
        void onFinishDialog();

    }

    public CustomCommentDialogFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        commentDAO = new CommentDAO(getActivity());

        /*Bundle bundle = this.getArguments();
        comment = bundle.getParcelable("selectedComment");*/

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View customDialogView = inflater.inflate(R.layout.fragment_add_comment, null);
        builder.setView(customDialogView);

        commentCommentEtxt = (EditText) customDialogView.findViewById(R.id.etxt_comment);

        submitLayout = (LinearLayout) customDialogView.findViewById(R.id.layout_submit);
        submitLayout.setVisibility(View.GONE);

    //    setValue();

        builder.setTitle(R.string.comment);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.add,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        /*comment.setComment(commentCommentEtxt.getText().toString());

                        long result = commentDAO.update(comment);*/

                        setComment();
                        task = new AddCommentTask(getActivity());
                        task.execute((Void) null);


                    }
                }).setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();

        return alertDialog;
    }

    private void setComment() {
        comment = new Comment();

    }

   /* private void setValue() {
        if (comment != null) {

            commentCommentEtxt.setText(comment.getComment());

        }
    }*/

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

