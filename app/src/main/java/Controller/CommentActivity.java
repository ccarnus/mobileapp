package Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Model.Cast;
import View.CommentAdapter;



// Callback interface for getCast
interface GetCastCallback {
    void onGetCastSuccess();
    void onGetCastFailure();
}

public class CommentActivity extends AppCompatActivity {

    public Button post_button;
    public ListView comments_list;
    public EditText comment_edit;
    public TextView cast_title;
    public String MY_USER_NAME = "Clement09";
    public Cast cast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        post_button = findViewById(R.id.comment_activity_post_comment_button);
        comments_list = findViewById(R.id.comment_activity_comments_list_view);
        comment_edit = findViewById(R.id.comment_activity_edit_comment_view);
        cast_title = findViewById(R.id.comment_activity_cast_title);

        //retrieving the cast object
        Intent intent = getIntent();
        cast = (Cast) intent.getSerializableExtra("cast");

        //handling the display
        updateTitle(cast);
        updateComments(cast);

        post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cast.CommentCast(getApplicationContext(), MY_USER_NAME, String.valueOf(comment_edit.getText()), new Cast.CommentCastCallback() {
                    @Override
                    public void onCommentCastSuccess() {
                        getCastAndUpdateComments();
                    }

                    @Override
                    public void onCommentCastFailure() {
                        // Handle failure case
                    }
                });
                //clear the text
                comment_edit.setText("");
                hideKeyboard();
            }
        });

    }

    private void getCastAndUpdateComments() {
        getCast(cast.getId(), new GetCastCallback() {
            @Override
            public void onGetCastSuccess() {
                updateComments(cast);
            }

            @Override
            public void onGetCastFailure() {
                // Handle failure case
            }
        });
    }

    public void updateTitle(Cast cast){
        cast_title.setText(cast.getTitle());
    }

    public void updateComments(Cast cast){
        List<Cast.Comment> commentList = cast.getCommentList();

        // Create and set the adapter
        CommentAdapter adapter = new CommentAdapter(this, commentList);
        comments_list.setAdapter(adapter);
    }

    private void getCast(String ID, GetCastCallback callback) {
        String url = "http://3.17.219.54:3000/cast/" + ID;
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String id = getStringOrDefault(response, "_id");
                            String title = getStringOrDefault(response, "title");
                            String description = getStringOrDefault(response, "description");
                            String department = getStringOrDefault(response, "department");
                            String type = getStringOrDefault(response, "type");
                            String castUrl = getStringOrDefault(response, "casturl");
                            String category = getStringOrDefault(response, "category");
                            String brightmindid = getStringOrDefault(response, "brightmindid");
                            String universitylogourl = getStringOrDefault(response, "universitylogourl");

                            // Retrieve comments fields
                            JSONObject commentsObject = response.getJSONObject("comments");
                            int commentsCount = commentsObject.getInt("count");
                            JSONArray commentsArray = commentsObject.getJSONArray("comment");
                            List<Cast.Comment> commentList = new ArrayList<>();
                            for (int j = 0; j < commentsArray.length(); j++) {
                                JSONObject commentObject = commentsArray.getJSONObject(j);
                                String author = getStringOrDefault(commentObject, "author");
                                String content = getStringOrDefault(commentObject, "content");

                                Cast.Comment comment = new Cast.Comment(author, content);
                                commentList.add(comment);
                            }

                            // Retrieving likes fields
                            JSONObject likesObject = response.getJSONObject("likes");
                            int likesCount = likesObject.getInt("count");
                            JSONArray likersArray = likesObject.getJSONArray("user");
                            String[] likersList = new String[likersArray.length()];
                            for (int j = 0; j < likersArray.length(); j++) {
                                likersList[j] = likersArray.getString(j);
                            }

                            cast = new Cast(id, title, description, department, category, type, brightmindid, castUrl, universitylogourl, likesCount, likersList, commentsCount, commentList);
                            callback.onGetCastSuccess();

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error getting casts", Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(jsonObjectRequest);
    }

    private String getStringOrDefault(JSONObject jsonObject, String fieldName) {
        String value = "";
        try {
            value = jsonObject.getString(fieldName);
        } catch (JSONException e) {
            // Field does not exist, so value remains ""
        }
        return value;
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(comment_edit.getWindowToken(), 0);
    }

}