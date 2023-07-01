package View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

import Model.Cast;

public class CommentAdapter extends ArrayAdapter<Cast.Comment> {
    private Context context;
    private List< Cast.Comment> commentList;

    public CommentAdapter(Context context, List<Cast.Comment> commentList) {
        super(context, 0, commentList);
        this.context = context;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_comment, parent, false);
        }

        Cast.Comment comment = commentList.get(position);

        TextView authorTextView = convertView.findViewById(R.id.comment_author_text);
        TextView contentTextView = convertView.findViewById(R.id.comment_content_text);

        authorTextView.setText(comment.getAuthor());
        contentTextView.setText(comment.getContent());

        return convertView;
    }
}
