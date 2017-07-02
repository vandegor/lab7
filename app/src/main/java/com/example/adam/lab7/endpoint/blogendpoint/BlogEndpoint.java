package com.example.adam.lab7.endpoint.blogendpoint;

import com.example.adam.lab7.model.json.Comment;
import com.example.adam.lab7.model.json.Entry;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BlogEndpoint {


    @POST("blog/entry")
    Call<Void> postEntry(@Body Entry entry);

    @GET("blog/entry")
    Call<List<Entry>> getEntries();

    @GET("blog/entry/{entryId}")
    Call<Entry> getEntry(@Path("entryId") int entryId);

    @PUT("blog/entry/{entryId}")
    Call<Void> putEntry(@Path("entryId") int entryId, @Body Entry entry);

    @DELETE("blog/entry/{entryId}")
    Call<Void> deleteEntry(@Path("entryId") int entryId);

    @DELETE("blog/entry")
    Call<Void> deleteEntries();

    @PUT("blog/entry/{entryId}/comment")
    Call<Void> putEntryComment(@Path("entryId") int entryId, @Body Comment comment);

    @GET("blog/entry/{entryId}/comment")
    Call<List<Comment>> getEntryComments(@Path("entryId") int entryId);

    @DELETE("blog/entry/{entryId}/comment/{commentId}")
    Call<Void> deleteEntryComment(@Path("entryId") int entryId, @Path("commentId") int commentId);
}