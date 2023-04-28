package View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.List;

import Model.Cast;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {

    private List<Cast> mCastList;
    private Context mcontext;
    public SimpleExoPlayer player;


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
        holder.mCategoryTextView.setText(String.valueOf(cast.getCategory()));
        holder.mDepartmentTextView.setText(String.valueOf(cast.getDepartment()));
        holder.mTypeTextView.setText(String.valueOf(cast.getType()));
        holder.mDescriptionTextView.setText(String.valueOf(cast.getDescription()));
        holder.mBrightmindid.setText(String.valueOf(cast.getBrightmindid()));
        holder.mUniversity.setText(String.valueOf(cast.getUniversity()));

        /* player */
        holder.mPlayer = new SimpleExoPlayer.Builder(mcontext).build();
        holder.mVideoView.setPlayer(holder.mPlayer);
        holder.mVideoView.setKeepScreenOn(true);
        /*holder.mPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == Player.STATE_BUFFERING) {
                    holder.talk_progressbar.setVisibility(View.VISIBLE);
                } else if (playbackState == Player.STATE_READY) {
                    holder.talk_progressbar.setVisibility(View.GONE);
                }
            }
        });*/

        /* Video */
        MediaItem mediaItem = MediaItem.fromUri("https://"+cast.getCasturl().split("//")[1]);
        holder.mPlayer.setMediaItem(mediaItem);
        holder.mVideoView.setUseController(false);
        holder.mPlayer.prepare();

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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitleTextView;
        public TextView mCategoryTextView;
        public TextView mDepartmentTextView;
        public TextView mDescriptionTextView;
        public TextView mTypeTextView;
        public TextView mUniversity;
        public TextView mBrightmindid;
        public PlayerView mVideoView;
        public SimpleExoPlayer mPlayer;
        public boolean is_playing;

        public ViewHolder(View itemView) {
            super(itemView);
            is_playing = false;
            mTitleTextView = itemView.findViewById(R.id.cast_title_view);
            mCategoryTextView = itemView.findViewById(R.id.cast_category_view);
            mDepartmentTextView = itemView.findViewById(R.id.cast_department_view);
            mDescriptionTextView = itemView.findViewById(R.id.cast_description_view);
            mTypeTextView = itemView.findViewById(R.id.cast_type_view);
            mUniversity = itemView.findViewById(R.id.cast_university_view);
            mBrightmindid = itemView.findViewById(R.id.cast_brightmindid_view);
            mVideoView = itemView.findViewById(R.id.cast_video_view);
        }
    }
}
