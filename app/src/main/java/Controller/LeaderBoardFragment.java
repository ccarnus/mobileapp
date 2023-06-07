package Controller;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import Model.User;
import View.UserAdapter;

public class LeaderBoardFragment extends Fragment {


    private String url;
    private RequestQueue mRequestQueue;
    private RecyclerView recyclerViewLeaderboard;

    public LeaderBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.url = "http://3.17.219.54/user/leaderboard/by/score";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        this.recyclerViewLeaderboard = view.findViewById(R.id.recycler_view_leader_board_view);

        getLeaderboard();

        return view;
    }

    private void getLeaderboard(){
        mRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<User> userList = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject userJson = response.getJSONObject(i);
                                Log.d("clement",String.valueOf(userJson));
                                String username = userJson.optString("username","not defined");
                                String role = userJson.optString("role", "not defined");
                                String email = userJson.optString("email", "not defined");
                                String pictureURL = userJson.optString("profilePictureUrl", "not defined");
                                String department = userJson.optString("department", "not defined");
                                int score = userJson.optInt("score", 0);
                                User user = new User(email,username,role,department,score, pictureURL);
                                userList.add(user);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        UserAdapter adapter = new UserAdapter(userList);
                        LinearLayoutManager linear_layout_manager_recycler_view = new LinearLayoutManager(getActivity().getApplicationContext());
                        linear_layout_manager_recycler_view.setReverseLayout(true);
                        linear_layout_manager_recycler_view.setStackFromEnd(true);
                        recyclerViewLeaderboard.setLayoutManager(linear_layout_manager_recycler_view);
                        recyclerViewLeaderboard.setHasFixedSize(true);
                        recyclerViewLeaderboard.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        mRequestQueue.add(request);
    }

}