package com.williamlian.instakilogram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.williamlian.instakilogram.model.Post;

import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ListView lv_posts = (ListView) findViewById(R.id.lv_posts);
        final ArrayList<Post> popularPhotos = new ArrayList<>();
        //ArrayList<Post> popularPhotos = Mock.getPopularPhotos();
        try {
            Post.getPopular(new Post.GetPopularCallback() {
                @Override
                public void onLoad(ArrayList<Post> posts) {
                    popularPhotos.clear();
                    popularPhotos.addAll(posts);
                    lv_posts.setAdapter(new PostAdaptor(MainActivity.this, popularPhotos));
                }

                @Override
                public void onFail(String error) {
                    System.out.println("ERROR!: " + error);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
