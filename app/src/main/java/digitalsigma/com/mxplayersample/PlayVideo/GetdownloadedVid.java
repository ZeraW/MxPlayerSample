package digitalsigma.com.mxplayersample.PlayVideo;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Hima on 12/3/2018.
 */

public class GetdownloadedVid {

    public GetdownloadedVid() {}

    File[] files;

    public void getDownloadedVid(ArrayList<VideoModel> mList, VideoAdapter mAdapter) {
        File SDCardRoot = Environment.getExternalStorageDirectory(); // location where you want to store
        File file = new File(Environment.getExternalStorageDirectory(), SDCardRoot + "/hobelrasul");
        Log.d("Filesz", "URI : " + file);
        if (file.exists()) {
            //  Toast.makeText(DownLoadsActivity.this, "exist", Toast.LENGTH_SHORT).show();
            files = file.listFiles();

            Log.d("Filesz", "Size: " + files.length);
            //  Toast.makeText(DownLoadsActivity.this, ""+files[0], Toast.LENGTH_SHORT).show();
            for (int i = 0; i < files.length; i++) {

                String[] items = files[i].getName().split(".mp4");
                Log.d("Filesz", "" + files[i].toString());
                mList.add(new VideoModel(items[0], files[i].toString()));

            }
            mAdapter.notifyDataSetChanged();
        } else {

        }
    }


    public Boolean checkIfVideoExists(String myVideoTitle) {
        File SDCardRoot = Environment.getExternalStorageDirectory(); // location where you want to store
        File file = new File(Environment.getExternalStorageDirectory(), SDCardRoot + "/hobelrasul");
        Log.d("Filesz", "URI : " + file);
        if (file.exists()) {
            files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                String check = files[i].toString();
                if (check.contains(myVideoTitle)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

}
