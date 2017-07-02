package com.example.adam.lab7;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.adam.lab7.endpoint.blogendpoint.BlogEndpoint;
import com.example.adam.lab7.endpoint.blogendpoint.impl.BlogEndpointController;
import com.example.adam.lab7.model.json.Entry;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onPostEntry(View view) {
    }

    public void onGetEntries(View view) {

    }

    public void onGetEntry(View view) {

    }

    public void onPutEntry(View view) {

    }

    public void onDeleteEntry(View view) {

    }

    public void onDeleteEntries(View view) {

    }

    public void onPutEntryComment(View view) {

    }

    public void onGetEntryComments(View view) {

    }

    public void onDeleteEntryComment(View view) {

    }
}
