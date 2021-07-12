package com.example.youtubebymood.core.model;

import com.google.api.client.util.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class YoutubeVideoModel {
    private String videoId, title, uploderName, duration, viewCount;
    private DateTime uploadTime;

    public YoutubeVideoModel
            (String videoId,
             String title,
             String uploderName,
             DateTime uploadTime,
             String duration,
             String viewCount){
        this.videoId = videoId;
        this.title = title;
        this.uploderName = uploderName;
        this.uploadTime = uploadTime;
        this.duration = duration;
        this.viewCount = viewCount;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getTitle() {
        return title;
    }

    public String getUploderName() {
        return uploderName;
    }

    public String getUploadTime() {
        //TODO 업로드 타임 실제 유튜브 처럼 변경

        return String.valueOf(uploadTime.getValue());
    }

    public String getDuration() {
        //TODO 영상 길이도 유튜브 처럼 변경
        return duration;
    }

    public String getViewCount() {
        return "조회수 " + viewCount + "회";
    }

    @Override
    public String toString() {
        return "YoutubeVideoModel{" +
                "videoId='" + videoId + '\'' +
                ", title='" + title + '\'' +
                ", uploaderName='" + uploderName + '\'' +
                ", uploadTime='" + uploadTime + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
