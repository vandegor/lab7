package com.example.adam.lab7;

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

    public void onSubmit(View view) {
        try {
            BlogEndpointController blogEndpointController = new BlogEndpointController();
            blogEndpointController.start(null);
            final int currentItem = mViewPager.getCurrentItem();
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
                            response.body();
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            String jsonObject = gson.toJson(response.body());
                            responseBodyValue.getText().clear();
                            responseBodyValue.getText().append(jsonObject);
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            android.util.Log.e("Failed to " + tabTitleMap.get(currentItem), t.getMessage(),t);
                        }
                    });
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
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
