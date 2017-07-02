package com.example.adam.lab7.endpoint.blogendpoint.impl;

import com.example.adam.lab7.endpoint.blogendpoint.BlogEndpoint;
import com.example.adam.lab7.model.json.Comment;
import com.example.adam.lab7.model.json.Entry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BlogEndpointController {
    private Gson gson;
    private Retrofit retrofit;
    private BlogEndpoint blogEndpoint;

    public void start(String url) {
        String defaultURL = "http://192.168.43.228:8080/lab/rest/";
        gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(url == null || url.equals("") ? defaultURL : url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        blogEndpoint = retrofit.create(BlogEndpoint.class);
    }


    public Call postEntry(String entryId, String commentId, String object) {
        return blogEndpoint.postEntry(gson.fromJson(object, Entry.class));
    }

    public Call<List<Entry>> getEntries(String entryId, String commentId, String object) {
        return blogEndpoint.getEntries();
    }

    public Call<Entry> getEntry(String entryId, String commentId, String object) {
        return blogEndpoint.getEntry(Integer.valueOf(entryId));
    }

    public Call putEntry(String entryId, String commentId, String object) {
        return blogEndpoint.putEntry(Integer.valueOf(entryId), gson.fromJson(object, Entry.class));
    }

    public Call deleteEntry(String entryId, String commentId, String object) {
        return blogEndpoint.deleteEntry(Integer.valueOf(entryId));
    }

    public Call deleteEntries(String entryId, String commentId, String object) {
        return blogEndpoint.deleteEntries();
    }

    public Call putEntryComment(String entryId, String commentId, String object) {
        return blogEndpoint.putEntryComment(Integer.valueOf(entryId), gson.fromJson(object, Comment.class));
    }

    public Call<List<Comment>> getEntryComments(String entryId, String commentId, String object) {
        return blogEndpoint.getEntryComments(Integer.valueOf(entryId));
    }

    public Call deleteEntryComment(String entryId, String commentId, String object) {
        return blogEndpoint.deleteEntryComment(Integer.valueOf(entryId), Integer.valueOf(commentId));
    }

}
