package Controller;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingsActivity extends AppCompatActivity {

    private Button update_button;
    private ImageView profile_picture_view;
    private EditText username_edit_text;
    private EditText email_edit_text;
    private EditText role_edit_text;
    private EditText department_edit_text;
    private Button change_picture;
    public Button create_cast;
    private String email;
    private String department;
    private String username;
    private String role;
    private String pictureUrl;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        update_button = findViewById(R.id.update_user_settings_view);
        username_edit_text = findViewById(R.id.settings_username_edit_text_view);
        role_edit_text = findViewById(R.id.settings_role_edit_text_view);
        department_edit_text = findViewById(R.id.settings_department_edit_text_view);
        email_edit_text = findViewById(R.id.settings_email_edit_text_view);
        profile_picture_view = findViewById(R.id.settings_picture_view);

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser();
            }
        });

        getUser();
    }

    private void updateUser() {
        String url = "http://3.17.219.54:3000/user/6451cab97522e1e7a212fadc";

        // Create a new JsonObject to hold the user data to be updated
        JSONObject userData = new JSONObject();
        try {
            userData.put("email", email_edit_text.getText());
            userData.put("username", username_edit_text.getText());
            userData.put("role", role_edit_text.getText());
            userData.put("department", department_edit_text.getText());
            userData.put("score", 30);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a new JsonObjectRequest with the data and the endpoint
        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, userData,
                response -> {
                    // Success response
                    Log.d("Response", "User updated successfully");
                },
                error -> {
                    // Error response
                    Log.e("Response Error", error.getMessage());
                });

        // Add the request to the request queue using the Volley library
        Volley.newRequestQueue(SettingsActivity.this).add(putRequest);
    }

    private void getUser() {
        String url = "http://3.17.219.54:3000/user/6451cab97522e1e7a212fadc";
        mRequestQueue = Volley.newRequestQueue(SettingsActivity.this);
        StringRequest request_get = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("clement", String.valueOf(jsonObject));
                            email = jsonObject.optString("email", "");
                            department = jsonObject.optString("department", "");
                            username = jsonObject.optString("username", "");
                            role = jsonObject.optString("role", "");
                            pictureUrl = jsonObject.optString("profilePictureUrl", "");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        updateWithUser();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("clement", String.valueOf(error));
                    }
                });
        mRequestQueue.add(request_get);
    }

    private void updateWithUser() {
        username_edit_text.setText(username);
        role_edit_text.setText(role);
        email_edit_text.setText(email);
        department_edit_text.setText(department);
        Picasso picasso = Picasso.get();
        picasso.load(pictureUrl).resize(500, 500).centerCrop().into(profile_picture_view, new Callback() {
            @Override
            public void onSuccess() {
                Log.d("clement", "Image loaded successfully");
            }

            @Override
            public void onError(Exception e) {
                Log.d("clement", "Error loading image: " + e.getMessage());
            }
        });
    }
}
