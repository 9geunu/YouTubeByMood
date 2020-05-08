package com.example.youtubebymood.core;

import android.util.Log;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Search {

    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();

    /** Global instance of the max number of videos we want returned (50 = upper limit per page). */
    private static final long NUMBER_OF_VIDEOS_RETURNED = 25;
    private static YouTube youtube;

    public static  ArrayList<YoutubeVideoModel> getYoutubeVideoList() {
        Log.d("log", "main start");
        try {
            youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) {}
            }).setApplicationName("youtube-by-mood").build();

            String queryTerm = getInputQuery("비 오는 날 노래");

            YouTube.Search.List search = youtube.search().list("id,snippet");

            search.setKey(Constants.APIKEY);
            search.setQ(queryTerm);
            search.setType("video");
            /*
             * This method reduces the info returned to only the fields we need and makes calls more
             * efficient.
             */
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            SearchListResponse searchResponse = search.execute();

            List<SearchResult> searchResultList = searchResponse.getItems();

            if (searchResultList != null) {
                return createYouTubeList(searchResultList.iterator(), queryTerm);
            }
        } catch (GoogleJsonResponseException e) {
            Log.d("log","There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
            return null;
        } catch (IOException e) {
            Log.d("log","There was an IO error: " + e.getCause() + " : " + e.getMessage());
            return null;
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
        return null;
    }

    private static String getInputQuery(String inputQuery) throws IOException {
        inputQuery.replace("","+");

        return inputQuery;
    }

    private static ArrayList<YoutubeVideoModel> createYouTubeList(Iterator<SearchResult> iteratorSearchResults, String query) {



        Log.d("log","\n=============================================================");
        Log.d("log",
                "   First " + NUMBER_OF_VIDEOS_RETURNED + " videos for search on \"" + query + "\".");
        Log.d("log","=============================================================\n");

        if (!iteratorSearchResults.hasNext()) {
            Log.d("log"," There aren't any results for your query.");
        }
        ArrayList<YoutubeVideoModel> youtubeVideoModelArrayList = new ArrayList<>();

        while (iteratorSearchResults.hasNext()) {

            SearchResult singleVideo = iteratorSearchResults.next();
            ResourceId rId = singleVideo.getId();

            // Double checks the kind is video.
            if (rId.getKind().equals("youtube#video")) {
                Thumbnail thumbnail = (Thumbnail) singleVideo.getSnippet().getThumbnails().get("default");

                Log.d("log"," Video Id: " + rId.getVideoId());
                Log.d("log"," Title: " + singleVideo.getSnippet().getTitle());
                Log.d("log"," Thumbnail: " + thumbnail.getUrl());
                Log.d("log","\n-------------------------------------------------------------\n");

                ArrayList<Object> detail = getDetail(rId.getVideoId());

                youtubeVideoModelArrayList.add(new YoutubeVideoModel(
                        rId.getVideoId(),
                        singleVideo.getSnippet().getTitle(),
                        singleVideo.getSnippet().getChannelTitle(),
                        singleVideo.getSnippet().getPublishedAt().toString(),
                        detail.get(0).toString(),
                        detail.get(1).toString()
                ));
            }
        }
        return youtubeVideoModelArrayList;
    }

    private static ArrayList<Object> getDetail(String videoId){
        ArrayList<Object> detailArray = new ArrayList<>();

        try {
            YouTube.Videos.List video = null;

            video = youtube.videos().list("contentDetails,statistics");
            video.setKey(Constants.APIKEY);
            video.setId(videoId);
            VideoListResponse videoResponse = video.execute();

            List<Video> videoResult = videoResponse.getItems();
            BigInteger viewCount = videoResult.get(0).getStatistics().getViewCount();
            String duration = videoResult.get(0).getContentDetails().getDuration();
            duration.replace("PT","").replace("H",":")
                    .replace("M",":").replace("S","");

            detailArray.add(viewCount);
            detailArray.add(duration);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return detailArray;
    }
}