package Controller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import Model.TrackCategory;
import View.TrackCategoryAdapter;

public class TrackFragment extends Fragment {

    private ListView categoryListView;
    private TrackCategoryAdapter categoryAdapter;
    private List<TrackCategory> categoryList;

    public TrackFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryList = generateCategoryList();
        categoryAdapter = new TrackCategoryAdapter(getActivity(), categoryList);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_track, container, false);
        categoryListView = view.findViewById(R.id.track_category_list_view);
        categoryListView.setAdapter(categoryAdapter);
        return view;
    }

    private List<TrackCategory> generateCategoryList() {
        List<TrackCategory> categories = new ArrayList<>();
        categories.add(new TrackCategory("Robotics", R.drawable.ic_robot, 75));
        categories.add(new TrackCategory("Artificial Intelligence", R.drawable.ic_artificial_intelligence, 50));
        categories.add(new TrackCategory("Mechanics", R.drawable.ic_mechanics, 90));
        categories.add(new TrackCategory("Economics", R.drawable.ic_economics, 30));
        // Add more categories here if needed
        return categories;
    }
}
