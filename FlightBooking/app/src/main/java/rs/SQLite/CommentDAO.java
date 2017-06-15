package rs.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Rale on 6/14/2017.
 */

public class CommentDAO extends CommentDBDAO {

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    public CommentDAO(Context context) {
        super(context);
    }

    public long save(Comment com) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.COMMENT_COMMENT, com.getComment());


        return database.insert(DataBaseHelper.COMMENT_TABLE, null, values);

    }

    public ArrayList<Comment> getComments() {
        ArrayList<Comment> coms = new ArrayList<Comment>();

        Cursor cursor = database.query(DataBaseHelper.COMMENT_TABLE,
                new String[] { DataBaseHelper.ID_COLUMN,
                        DataBaseHelper.COMMENT_COMMENT
                        }, null, null, null,
                null, null,null);

        while (cursor.moveToNext()) {
            Comment com = new Comment();
            com.setId(cursor.getInt(0));
            com.setComment(cursor.getString(1));


            coms.add(com);
        }
        return coms;
    }

    private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN
            + " =?";


    public long update(Comment com) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.COMMENT_COMMENT, com.getComment());
        
        long result = database.update(DataBaseHelper.COMMENT_TABLE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(com.getId()) });
        return result;

    }

    public int delete(Comment com) {
        return database.delete(DataBaseHelper.COMMENT_TABLE, WHERE_ID_EQUALS,
                new String[] { com.getId() + "" });
    }

    //Retrieves a single com record with the given id
    public Comment getComment(long id) {
        Comment com = null;

        String sql = "SELECT * FROM " + DataBaseHelper.COMMENT_TABLE
                + " WHERE " + DataBaseHelper.ID_COLUMN + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

        if (cursor.moveToNext()) {
            com = new Comment();
            com.setId(cursor.getInt(0));
            com.setComment(cursor.getString(1));

        }
        return com;
    }


}
