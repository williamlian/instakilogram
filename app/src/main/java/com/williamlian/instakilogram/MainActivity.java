package com.williamlian.instakilogram;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.williamlian.instakilogram.model.Comment;
import com.williamlian.instakilogram.model.Post;

import org.json.JSONException;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeContainer;
    private PostAdaptor postAdaptor;
    private ArrayList<Post> popularPosts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        postAdaptor = new PostAdaptor(MainActivity.this, popularPosts);
        ListView lv_posts = (ListView) findViewById(R.id.lv_posts);
        lv_posts.setAdapter(postAdaptor);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPosts();
            }
        });
        swipeContainer.setColorSchemeResources(R.color.colorPrimary);

        //initial refresh
        swipeContainer.setRefreshing(true);
        fetchPosts();
    }

    public void fetchPosts() {
        try {
            Post.getPopular(new Post.GetPopularCallback() {
                @Override
                public void onLoad(ArrayList<Post> posts) {
                    postAdaptor.clear();
                    postAdaptor.addAll(posts);
                    postAdaptor.notifyDataSetChanged();
                    swipeContainer.setRefreshing(false);
                }

                @Override
                public void onFail(String error) {
                    System.out.println("ERROR!: " + error);
                    swipeContainer.setRefreshing(false);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
