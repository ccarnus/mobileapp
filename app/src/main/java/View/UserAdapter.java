package View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

import Model.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> mUserList;

    public UserAdapter(List<User> userList) {
        mUserList = userList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = mUserList.get(position);
        holder.mScoreTextView.setText(String.valueOf(user.getScore()));
        holder.mUsernameTextView.setText(String.valueOf(user.getUsername()));
        holder.mRoleTextView.setText(String.valueOf(user.getRole()));
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mScoreTextView;
        public TextView mUsernameTextView;
        public TextView mRoleTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mScoreTextView = itemView.findViewById(R.id.leader_board_item_score_view);
            mUsernameTextView = itemView.findViewById(R.id.leader_board_item_name_view);
            mRoleTextView = itemView.findViewById(R.id.leader_board_item_role_view);
        }
    }
}
