package digitalsigma.com.mxplayersample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private VideoAdapter mAdapter;
    private ArrayList<String > mList;
    private JzvdStd player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        addVideos();
        //jzvdStd.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
    }

    private void addVideos(){
        final String url = "http://hubalrasul.digitalsigma.io/api/videos";
        StringRequest req = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray dataArray = object.getJSONArray("data");
                    for (int a = 0; a < dataArray.length(); a++) {

                        JSONObject videos = dataArray.getJSONObject(a);
                        String vid_url = url + videos.getString("video");
                        //Log.d("response url", songUrl);
                        mList.add(vid_url);
                        Toast.makeText(MainActivity.this, "suc", Toast.LENGTH_SHORT).show();

                    }

                    //Collections.reverse(mList);
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(this).addToRequestQueue(req);

    }

    private void playVideo(String url,String videoName){
        player.setUp(url, videoName, Jzvd.SCREEN_WINDOW_NORMAL);
        player.startVideo();
    }


    private void init(){
        player = (JzvdStd) findViewById(R.id.videoplayer);
        mList = new ArrayList<>();
        mAdapter = new VideoAdapter(this, mList, new VideoAdapter.AdapterListener() {
            @Override
            public void iconTextViewOnClick(View v, int position) {
                player.setVisibility(View.VISIBLE);
                String url = "http://hubalrasul.digitalsigma.io/storage/video/ROhZe4ndfA4CZllSjpXwDqB89G8Z7bMjM0VM7Gc2.mp4";
                playVideo(url,"sweet");
            }

            @Override
            public void iconImageViewOnClick(View v, int position) {

            }

            @Override
            public void iconImageUnFollowOnClick(View v, int position) {

            }
        });
        mRecyclerView = findViewById(R.id.video_recycler);
        mRecyclerView.hasFixedSize();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

}
