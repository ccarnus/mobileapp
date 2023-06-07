package View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.List;

import Controller.CommentActivity;
import Model.Cast;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {

    public String MY_USER_NAME = "test@gatech.edu";

    private List<Cast> mCastList;
    private Context mcontext;
    private SimpleExoPlayer mPlayer;

    public CastAdapter(List<Cast> castList, Context context) {
        mCastList = castList;
        mcontext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Cast cast = mCastList.get(position);
        holder.mTitleTextView.setText(String.valueOf(cast.getTitle()));
        //setting number of likes and comments
        holder.mLikes.setText(String.valueOf(cast.getCount_likes()));
        holder.mComments.setText(String.valueOf(cast.getCount_comments()));

        //button like/comment/share
        if(isItLiked(cast.getLikers_list(),MY_USER_NAME)){
            holder.like_button.setImageResource(R.drawable.like_filled_ic);
            holder.like_button.setEnabled(false);
        }
        holder.like_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cast.LikeCast(mcontext.getApplicationContext(), cast.getId(), MY_USER_NAME);
                holder.like_button.setImageResource(R.drawable.like_filled_ic);
                holder.like_button.setEnabled(false);
                holder.mLikes.setText(String.valueOf(cast.getCount_likes()+1));
            }
        });
        holder.comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, CommentActivity.class);
                intent.putExtra("cast",cast);
                mcontext.startActivity(intent);
            }
        });
        holder.share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cast.ShareCast(mcontext.getApplicationContext());
            }
        });

        /* player */
        mPlayer = new SimpleExoPlayer.Builder(mcontext).build();
        holder.mVideoView.setPlayer(mPlayer);
        holder.mVideoView.setKeepScreenOn(true);

        /* Video */
        MediaItem mediaItem = MediaItem.fromUri("http://"+cast.getCasturl().split("//")[1]);
        mPlayer.setMediaItem(mediaItem);
        holder.mVideoView.setUseController(false);
        mPlayer.prepare();

        holder.mVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(holder.is_playing){
                    holder.mVideoView.getPlayer().pause();
                    holder.is_playing = false;
                }
                else{
                    holder.mVideoView.getPlayer().play();
                    holder.is_playing = true;
                }
                return false;
            }
        });

    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        if(holder.mVideoView.getPlayer() != null) {
            holder.mVideoView.getPlayer().play();
            holder.is_playing = true;
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

        if(holder.mVideoView.getPlayer() != null) {
            holder.mVideoView.getPlayer().pause();
            holder.is_playing = false;
        }
    }

    @Override
    public int getItemCount() {
        return mCastList.size();
    }

    public Boolean isItLiked(String[] name_list, String name){
        Boolean is_liked = false;
        for(String currentEmail: name_list){
            if(currentEmail.equals(name)){
                is_liked = true;
            }
        }
        return is_liked;
    }

    public void releasePlayer() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitleTextView;
        public TextView mLikes;
        public TextView mComments;
        public PlayerView mVideoView;
        public boolean is_playing;
        public ImageButton comment_button, share_button, like_button;

        public ViewHolder(View itemView) {
            super(itemView);
            is_playing = false;
            mTitleTextView = itemView.findViewById(R.id.cast_title_view);
            mLikes = itemView.findViewById(R.id.number_of_likes_view);
            mComments = itemView.findViewById(R.id.number_of_comments_view);
            comment_button = itemView.findViewById(R.id.commentIcon);
            share_button = itemView.findViewById(R.id.shareIcon);
            like_button = itemView.findViewById(R.id.likeIcon);
            mVideoView = itemView.findViewById(R.id.cast_video_view);
        }
    }
}
