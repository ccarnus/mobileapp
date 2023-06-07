package Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import View.ButtonAdapter;


public class CastFragment extends Fragment {

    private ImageButton randomizator_button;

    public CastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cast, container, false);

        //randomizator button
        randomizator_button = view.findViewById(R.id.cast_randomizator_button_view);
        Intent intent_randomizator = new Intent(getActivity(), RandomizatorActivity.class);
        randomizator_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent_randomizator);
            }
        });

        //list view button creation
        String[] buttonNames = {"Robotics", "Artificial Intelligence", "Mechanics", "Economics"};
        int[] buttonDrawables = {R.drawable.button_category_cast, R.drawable.button_category_cast, R.drawable.button_category_cast, R.drawable.button_category_cast};
        ListView category_button_listView = view.findViewById(R.id.button_category_list_view);
        ButtonAdapter adapter = new ButtonAdapter(getActivity().getApplicationContext(), buttonNames, buttonDrawables);
        category_button_listView.setAdapter(adapter);

        return view;
    }

}