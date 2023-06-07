package Controller;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class CommentActivity extends AppCompatActivity {

    public Button post_button;
    public ListView comments_list;
    public EditText comment_edit;
    public TextView cast_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        post_button = findViewById(R.id.comment_activity_post_comment_button);
        comments_list = findViewById(R.id.comment_activity_comments_list_view);
        comment_edit = findViewById(R.id.comment_activity_edit_comment_view);
        cast_title = findViewById(R.id.comment_activity_cast_title);

    }

}