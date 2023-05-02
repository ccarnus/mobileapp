package Controller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
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

public class RandomizatorFragment extends Fragment {

    private List<Cast> cast_list = new ArrayList<>();
    public ViewPager2 view_pager;
    public CastAdapter adapter;

    public RandomizatorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_randomizator, container, false);
        this.view_pager = view.findViewById(R.id.cast_view_pager_view);

        getCast();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter.releasePlayer();
    }


    private void getCast() {
        String url = "https://brightmindsapi.azurewebsites.net/cast";
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // loop through each cast object in the response
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject castObject = response.getJSONObject(i);

                                String id = getStringOrDefault(castObject,"_id");
                                String title = getStringOrDefault(castObject,"title");
                                String description = getStringOrDefault(castObject,"description");
                                String department = getStringOrDefault(castObject,"department");
                                String type = getStringOrDefault(castObject,"type");
                                String castUrl = getStringOrDefault(castObject,"casturl");
                                String category = getStringOrDefault(castObject,"category");
                                String brightmindid = getStringOrDefault(castObject,"brightmindid");
                                String universitylogourl = getStringOrDefault(castObject,"universitylogourl");

                                Cast cast = new Cast(title, description, department, category,  type, brightmindid, castUrl, universitylogourl);

                                cast_list.add(cast);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        adapter = new CastAdapter(cast_list, getActivity().getApplicationContext());
                        Log.d("clement",String.valueOf(cast_list.size()));
                        view_pager.setAdapter(adapter);
                        view_pager.setPadding(10,0,10,0);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), "Error getting casts", Toast.LENGTH_SHORT).show();
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