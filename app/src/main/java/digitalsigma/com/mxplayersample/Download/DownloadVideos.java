package digitalsigma.com.mxplayersample.Download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;

import digitalsigma.com.mxplayersample.R;

/**
 * Created by Hima on 12/2/2018.
 */

public class DownloadVideos {
    private DownloadManager downloadManager;
    private long refer;
    private BroadcastReceiver downloadcomplete;
    private BroadcastReceiver notificationClick;
    private Context context;

    public DownloadVideos(Context context) {
        this.context = context;
    }

    public void startdownload(String fileName,String url) {

        File direct = new File(Environment.DIRECTORY_DOWNLOADS);

        if (!direct.exists()) {
            direct.mkdirs();
        }
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setDescription("My download");
        request.setDestinationInExternalFilesDir(context, direct.getPath(), fileName+".mp4");
        request.setVisibleInDownloadsUi(true);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        refer = downloadManager.enqueue(request);
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        notificationClick = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String extraID = DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS;
                long[] references = intent.getLongArrayExtra(extraID);
                for (long r : references) {
                    if (r == refer) {

                    }
                }
            }
        };
        context.registerReceiver(notificationClick, filter);

        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        downloadcomplete = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long r = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (refer == r) {
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(r);
                    Cursor cursor = downloadManager.query(query);
                    cursor.moveToFirst();
                    //get status of the download

                    int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                    Log.e("num",""+columnIndex);
                    if( cursor != null && cursor.moveToFirst() ){
                        int status = cursor.getInt(columnIndex);
                        int filenameIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
                        //String saveFilePath = cursor.getString(filenameIndex);
                        int columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
                        int reason = cursor.getInt(columnReason);

                        switch (status) {
                            case DownloadManager.STATUS_SUCCESSFUL:
                                // Toast.makeText(getApplicationContext(), "STATUS_SUCCESSFUL", Toast.LENGTH_SHORT).show();
                                new CustomNotificationDownload(context).startNewNotification("Download Complete","Click here to access the file", R.drawable.ic_downloadicone);
                                break;
                            case DownloadManager.STATUS_FAILED:
                                // do something                            break;
                            case DownloadManager.STATUS_PAUSED:
                                // do something                            break;
                            case DownloadManager.STATUS_PENDING:
                                // do something                            break;
                            case DownloadManager.STATUS_RUNNING:
                                // do something                            break;
                        }
                    }


                }
            }
        };
        context.registerReceiver(downloadcomplete, intentFilter);
    }

    public void onDestroyMethod(){
        context.unregisterReceiver(downloadcomplete);
        context.unregisterReceiver(notificationClick);
    }
}
