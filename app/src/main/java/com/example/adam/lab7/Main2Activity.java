package com.example.adam.lab7;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.adam.lab7.endpoint.blogendpoint.impl.BlogEndpointController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main2Activity extends AppCompatActivity {

    private static final Map<Integer, String> tabTitleMap;

    private static String uri;

    static {
        Map<Integer, String> aMap = new HashMap<>();
        aMap.put(0, "postEntry");
        aMap.put(1, "getEntries");
        aMap.put(2, "getEntry");
        aMap.put(3, "putEntry");
        aMap.put(4, "deleteEntry");
        aMap.put(5, "deleteEntries");
        aMap.put(6, "putEntryComment");
        aMap.put(7, "getEntryComments");
        aMap.put(8, "deleteEntryComment");
        tabTitleMap = Collections.unmodifiableMap(aMap);
    }

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private int currentPage;

    public static String getUri() {
        return uri;
    }

    public static void setUri(String uri) {
        Main2Activity.uri = uri;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    public void onSubmit(final View view) {
        final int currentItem = mViewPager.getCurrentItem();
        try {
            BlogEndpointController blogEndpointController = new BlogEndpointController(getResources().getString(R.string.defaultURL));
            blogEndpointController.start(getUri());
            Fragment fragment = mSectionsPagerAdapter.getItem(currentItem);
            EditText entryIdValue = (EditText) mViewPager.getFocusedChild().findViewById(R.id.entryIdValue);
            EditText commentIdValue = (EditText) mViewPager.getFocusedChild().findViewById(R.id.commentIdValue);
            EditText requestBodyValue = (EditText) mViewPager.getFocusedChild().findViewById(R.id.requestBodyValue);
            final EditText responseBodyValue = (EditText) mViewPager.getFocusedChild().findViewById(R.id.responseBodyValue);
            for (Method method : BlogEndpointController.class.getMethods()) {
                if (method.getName().equals(mSectionsPagerAdapter.getPageTitle(currentItem))) {
                    Call call = (Call) method.invoke(blogEndpointController,
                            entryIdValue.getText().toString(),
                            commentIdValue.getText().toString(),
                            requestBodyValue.getText().toString());
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            int code = response.code();
                            String message = response.message();
                            Object body = response.body();

                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            if (body != null) {
                                String jsonObject;
                                jsonObject = gson.toJson(body);
                                responseBodyValue.getText().clear();
                                responseBodyValue.getText().append(jsonObject);
                            } else {
                                Snackbar.make(view, "code: " + code + " message: " + message, Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }

                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            String error = "Failed to " + tabTitleMap.get(currentItem) + " Cause: " + t.getMessage();
                            Snackbar.make(view, error, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            android.util.Log.e(error, t.getMessage(), t);
                        }
                    });
                    break;
                }
            }

        } catch (InvocationTargetException | IllegalArgumentException | IllegalAccessException e) {
            String error = "Failed to " + tabTitleMap.get(currentItem) + " Cause: " + e.getMessage();
            android.util.Log.e(error, e.getMessage(), e);
            Snackbar.make(view, error, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main2, container, false);
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 9;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitleMap.get(position);
        }
    }
}
