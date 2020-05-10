package com.example.youtubebymood;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubebymood.core.Search;
import com.example.youtubebymood.core.VideoAdapter;
import com.example.youtubebymood.core.model.YoutubeVideoModel;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements VideoAdapter.VideoAdapterOnClickHandler {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Context context = this;
    private VideoAdapter.VideoAdapterOnClickHandler videoAdapterOnClickHandler = this;
    private String query;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        query = getIntent().getStringExtra("query");

        progressBar = (ProgressBar) findViewById(R.id.loading_indicator);

        setUpRecyclerView();

        showLoading();

        populateRecyclerView();
    }

    private void setUpRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_video);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void populateRecyclerView() {
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                final ArrayList<YoutubeVideoModel> youtubeVideoModelArrayList = Search.getYoutubeVideoList(query);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        VideoAdapter adapter = new VideoAdapter(context, videoAdapterOnClickHandler, youtubeVideoModelArrayList);
                        recyclerView.setAdapter(adapter);
                        showVideoView();
                    }
                });
            }
        }).start();
    }

    @Override
    public void onClick(String videoId) {
        startActivity(new Intent(ListActivity.this, YouTubePlayerActivity.class)
                .putExtra("video_id", videoId));
    }

    private void showLoading(){
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void showVideoView(){
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

}
