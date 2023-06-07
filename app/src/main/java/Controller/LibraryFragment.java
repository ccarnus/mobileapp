package Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class LibraryFragment extends Fragment {

    public ImageButton create_cast_button;

    public LibraryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        create_cast_button = view.findViewById(R.id.library_create_cast_button);
        create_cast_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start_create_cast_activity = new Intent(getActivity(), CreateCastActivity.class);
                startActivity(start_create_cast_activity);
            }
        });

        return view;
    }
}