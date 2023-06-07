package Controller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Model.Cast;
import View.CastAdapter;


public class RandomizatorActivity extends AppCompatActivity {

    private List<Cast> castList = new ArrayList<>();
    private ViewPager2 viewPager;
    private CastAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomizator);

        viewPager = findViewById(R.id.cast_view_pager_view);

        getCast();
    }

    private void getCast() {
        String url = "http://3.17.219.54:3000/cast";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Loop through each cast object in the response
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject castObject = response.getJSONObject(i);

                                String id = getStringOrDefault(castObject, "_id");
                                String title = getStringOrDefault(castObject, "title");
                                String description = getStringOrDefault(castObject, "description");
                                String department = getStringOrDefault(castObject, "department");
                                String type = getStringOrDefault(castObject, "type");
                                String castUrl = getStringOrDefault(castObject, "casturl");
                                String category = getStringOrDefault(castObject, "category");
                                String brightmindid = getStringOrDefault(castObject, "brightmindid");
                                String universitylogourl = getStringOrDefault(castObject, "universitylogourl");

                                // Retrieve comments fields
                                JSONObject commentsObject = castObject.getJSONObject("comments");
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

                                // Retriaving likes fields
                                JSONObject likesObject = castObject.getJSONObject("likes");
                                int likesCount = likesObject.getInt("count");
                                JSONArray likersArray = likesObject.getJSONArray("user");
                                String[] likersList = new String[likersArray.length()];
                                for (int j = 0; j < likersArray.length(); j++) {
                                    likersList[j] = likersArray.getString(j);
                                }

                                Cast cast = new Cast(id, title, description, department, category, type, brightmindid, castUrl, universitylogourl, likesCount, likersList, commentsCount, commentList);

                                castList.add(cast);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        adapter = new CastAdapter(castList, getApplicationContext());
                        viewPager.setAdapter(adapter);
                        viewPager.setPadding(10, 0, 10, 0);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error getting casts", Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(jsonArrayRequest);
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
}
