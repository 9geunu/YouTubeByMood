package com.example.youtubebymood.core;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubebymood.R;
import com.example.youtubebymood.core.model.YoutubeVideoModel;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoAdapterViewHolder>  {

    private Context mContext;
    final private VideoAdapterOnClickHandler videoAdapterOnClickHandler;
    private ArrayList<YoutubeVideoModel> youtubeVideoModelArrayList;

    public interface VideoAdapterOnClickHandler {
        void onClick(String videoId);
    }

    public VideoAdapter(Context context, VideoAdapterOnClickHandler clickHandler, ArrayList<YoutubeVideoModel> youtubeVideoModelArrayList){
        mContext = context;
        videoAdapterOnClickHandler = clickHandler;
        this.youtubeVideoModelArrayList = youtubeVideoModelArrayList;
    }

    @NonNull
    @Override
    public VideoAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        int layoutId = R.layout.video_list_item;

        View view = LayoutInflater.from(mContext).inflate(layoutId, viewGroup, false);

        view.setFocusable(true);

        return new VideoAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapterViewHolder holder, int position) {
        final YoutubeVideoModel youtubeVideoModel = youtubeVideoModelArrayList.get(position);

        holder.title.setText(youtubeVideoModel.getTitle());
        holder.duration.setText(youtubeVideoModel.getDuration());
        holder.uploadTime.setText(youtubeVideoModel.getUploadTime());
        holder.uploaderName.setText(youtubeVideoModel.getUploderName());
        holder.videoViews.setText(youtubeVideoModel.getViewCount());

        /*******************
         * Video Thumbnail *
         *******************/
        /*  initialize the thumbnail image view , we need to pass Developer Key */
        holder.thumbnail.initialize(Constants.APIKEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                //when initialization is sucess, set the video id to thumbnail to load
                youTubeThumbnailLoader.setVideo(youtubeVideoModel.getVideoId());

                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        //when thumbnail loaded successfully release the thumbnail loader as we are showing thumbnail in adapter
                        youTubeThumbnailLoader.release();
                    }

                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                        //print or show error when thumbnail load failed
                        Log.e(Constants.LOGTAG, "Youtube Thumbnail Error");
                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                //print or show error when initialization failed
                Log.e(Constants.LOGTAG, "Youtube Initialization Failure");

            }
        });



    }

    @Override
    public int getItemCount() {
        return youtubeVideoModelArrayList != null ? youtubeVideoModelArrayList.size() : 0;
    }

    class VideoAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final YouTubeThumbnailView thumbnail;

        final TextView title;
        final TextView videoViews;
        final TextView uploaderName;
        final TextView uploadTime;
        final TextView duration;

        VideoAdapterViewHolder(View view) {
            super(view);

            thumbnail = (YouTubeThumbnailView) view.findViewById(R.id.video_thumbnail);
            title = (TextView) view.findViewById(R.id.video_title);
            videoViews = (TextView) view.findViewById(R.id.video_views);
            uploaderName = (TextView) view.findViewById(R.id.uploader_name);
            uploadTime = (TextView) view.findViewById(R.id.upload_time);
            duration = (TextView) view.findViewById(R.id.duration);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            String adapterPosition = youtubeVideoModelArrayList.get(position).getVideoId();
            videoAdapterOnClickHandler.onClick(adapterPosition);
        }
    }
}
