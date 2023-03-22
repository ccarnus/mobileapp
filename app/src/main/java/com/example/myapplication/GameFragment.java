package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class GameFragment extends Fragment {

    private Button post_btn;
    private Button get_btn;
    private EditText api_rest_text;

    private String url;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.url = "https://brightmindsapi.azurewebsites.net/user";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        this.get_btn = view.findViewById(R.id.button_GET);
        this.post_btn = view.findViewById(R.id.button_POST);
        this.api_rest_text = view.findViewById(R.id.API_REST_text_view);

        this.get_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

        return view;
    }


    private void getData() {
        // RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        // String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                api_rest_text.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                api_rest_text.setText(error.toString());
            }
        });
        mRequestQueue.add(mStringRequest);
    }

}