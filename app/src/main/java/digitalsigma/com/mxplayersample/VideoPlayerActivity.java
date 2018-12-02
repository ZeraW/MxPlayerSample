package digitalsigma.com.mxplayersample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class VideoPlayerActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private VideoAdapter mAdapter;
    private ArrayList<VideoModel> mList;
    private JzvdStd player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        new AddVideoRequest(this).addVideos(mList,mAdapter);

    }

    private void playVideo(String url,String videoName){
        player.setUp(url, videoName, Jzvd.SCREEN_WINDOW_NORMAL);
        //player.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
        player.startVideo();
    }

    private void init(){
        player = (JzvdStd) findViewById(R.id.videoplayer);
        mList = new ArrayList<>();
        mAdapter = new VideoAdapter(this, mList, new VideoAdapter.AdapterListener() {
            @Override
            public void iconTextViewOnClick(View v, String title,String url) {
                player.setVisibility(View.VISIBLE);
                playVideo(url,title);
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
