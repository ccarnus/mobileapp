package View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import com.example.myapplication.R;

public class ButtonAdapter extends ArrayAdapter<String> {

    Context mContext;
    String[] mButtonNames;
    int[] mButtonDrawables;

    public ButtonAdapter(Context context, String[] buttonNames, int[] buttonDrawables) {
        super(context, R.layout.button_category_cast, buttonNames);
        this.mContext = context;
        this.mButtonNames = buttonNames;
        this.mButtonDrawables = buttonDrawables;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.button_category_cast, parent, false);

        ImageButton button = view.findViewById(R.id.button_category_cast);
        //button.setText(mButtonNames[position]);
        button.setBackgroundResource(mButtonDrawables[position]);

        return view;
    }
}
