package View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

import Model.TrackCategory;

public class TrackCategoryAdapter extends BaseAdapter {

    private Context context;
    private List<TrackCategory> categoryList;

    public TrackCategoryAdapter(Context context, List<TrackCategory> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_track_category, parent, false);
        }

        TrackCategory category = categoryList.get(position);

        TextView categoryNameTextView = convertView.findViewById(R.id.track_categoryNameTextView);
        categoryNameTextView.setText(category.getName());

        ProgressBar progressBar = convertView.findViewById(R.id.track_progressBar);
        progressBar.setProgress(category.getProgress());

        ImageView track_category_icon = convertView.findViewById(R.id.track_categoryIconImageView);
        track_category_icon.setImageResource(category.getDrawableId());

        return convertView;
    }
}
