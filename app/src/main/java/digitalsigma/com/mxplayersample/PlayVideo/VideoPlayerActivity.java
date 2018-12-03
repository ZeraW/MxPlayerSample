package digitalsigma.com.mxplayersample.PlayVideo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import digitalsigma.com.mxplayersample.Download.DownloadVideos;
import digitalsigma.com.mxplayersample.R;

public class VideoPlayerActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private VideoAdapter mAdapter;
    private ArrayList<VideoModel> mList;
    private JzvdStd player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_main);

        setTitle(getIntent().getStringExtra("title"));
        init();

        //to open video activity
        if (getIntent().getStringExtra("vid").equals("1")) {
            new AddVideoRequest(this).addVideos(mList, mAdapter);
            //to open downloads activity
        } else if (getIntent().getStringExtra("vid").equals("2")) {
            new GetdownloadedVid().getDownloadedVid(mList, mAdapter);
        }

    }


    private void playVideo(String url, String videoName) {
        player.setUp(url, videoName, Jzvd.SCREEN_WINDOW_NORMAL);
        //player.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
        player.startVideo();
    }

    private void init() {
        player = (JzvdStd) findViewById(R.id.videoplayer);
        mList = new ArrayList<>();
        // i used getintent to hide the download btn in case of downloaded video activity is opened
        mAdapter = new VideoAdapter(this,getIntent().getStringExtra("vid"), mList, new VideoAdapter.AdapterListener() {
            @Override
            public void iconTextViewOnClick(View v, String title, String url) {
                player.setVisibility(View.VISIBLE);
                playVideo(url, title);
            }

            @Override
            public void iconDownloadViewOnClick(View v, final String title, final String url) {

                if (new GetdownloadedVid().checkIfVideoExists(title)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(VideoPlayerActivity.this);
                    builder.setMessage("الفيديو موجود فى التحميلات");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(VideoPlayerActivity.this);
                    builder.setMessage("هل تريد تحميل فديو ' " + title + " '");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {

                            new DownloadVideos(VideoPlayerActivity.this).startdownload(title, url);
                            dialog.dismiss();
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }

            @Override
            public void iconImageUnFollowOnClick(View v, int position) {

            }
        });
        mRecyclerView = findViewById(R.id.video_recycler);
        mRecyclerView.hasFixedSize();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new DownloadVideos(VideoPlayerActivity.this).onDestroyMethod();
    }
}
