package Controller;

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
    private ListView category_button_listView;

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

        randomizator_button = view.findViewById(R.id.cast_randomizator_button_view);
        RandomizatorFragment randomizator_fragment = new RandomizatorFragment();
        randomizator_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,randomizator_fragment).commit();
            }
        });

        //list view creation
        String[] buttonNames = {"Button 1", "Button 2", "Button 3", "Button 4", "Button 5", "Button 6", "Button 7", "Button 8"};
        int[] buttonDrawables = {R.drawable.button_category_cast, R.drawable.button_category_cast, R.drawable.button_category_cast, R.drawable.button_category_cast, R.drawable.button_category_cast, R.drawable.button_category_cast, R.drawable.button_category_cast, R.drawable.button_category_cast};
        ListView category_button_listView = view.findViewById(R.id.button_category_list_view);
        ButtonAdapter adapter = new ButtonAdapter(getActivity().getApplicationContext(), buttonNames, buttonDrawables);
        category_button_listView.setAdapter(adapter);

        return view;
    }

}