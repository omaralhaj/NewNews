package com.example.android.newnews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.android.newnews.R.id.news;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>>, SwipeRefreshLayout.OnRefreshListener {

    private final String REQUEST_URL =
            "http://content.guardianapis.com/search?api-key=71980f4d-d2c7-4a4e-882d-cb4f1f7b0d61&show-tags=contributor";
    NewsAdapter newsAdapter;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        ArrayList<News> news = new ArrayList<News>();
        newsAdapter = new NewsAdapter(MainActivity.this, news);
        ListView listView = (ListView) findViewById(R.id.news);
        listView.setAdapter(newsAdapter);
        refreshLayout.setOnRefreshListener(this);
        getLoaderManager().initLoader(1, null, this).forceLoad();
    }


    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this, REQUEST_URL);
    }


    @Override
    public void onLoadFinished(Loader<ArrayList<News>> loader, ArrayList<News> data) {
        ListView listView = (ListView) findViewById(news);
        TextView t = (TextView) findViewById(R.id.empty_list_view);
        if (data.isEmpty()) {
            listView.setVisibility(View.GONE);
            t.setVisibility(View.VISIBLE);
            t.setText("No News");
        } else {
            newsAdapter = new NewsAdapter(this, data);
            listView.setAdapter(newsAdapter);
        }
    }
    @Override
    public void onLoaderReset(Loader<ArrayList<News>> loader) {
        newsAdapter.clear();
    }

    @Override
    public void onRefresh() {
        newsAdapter.clear();
        getLoaderManager().restartLoader(1, null, MainActivity.this);
        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }
}

