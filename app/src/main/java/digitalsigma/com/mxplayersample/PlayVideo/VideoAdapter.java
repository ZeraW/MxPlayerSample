package digitalsigma.com.mxplayersample.PlayVideo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import digitalsigma.com.mxplayersample.R;

/**
 * Created by Hima on 11/29/2018.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private Context context;
    private List<VideoModel> mList;
    private AdapterListener onClickListener;
    private String check;



    public VideoAdapter(Context context,String check, List<VideoModel> mList, AdapterListener onClickListener) {
        this.context = context;
        this.mList = mList;
        this.onClickListener = onClickListener;
        this.check=check;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_video_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.textView.setText(mList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        if (mList.size() > 0) {
            return mList.size();
        } else {
            return 0;
        }
    }

    public VideoAdapter() {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        TextView textView;
        ImageView downloadVid;


        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            textView = mView.findViewById(R.id.row_videoList_Title);
            downloadVid = itemView.findViewById(R.id.DownloadVideos);

            if (check.equals("2")){
                downloadVid.setVisibility(View.INVISIBLE);
            }


            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onClickListener.iconTextViewOnClick(v,mList.get(getAdapterPosition()).getTitle(),mList.get(getAdapterPosition()).getUrl());
                }
            });

            downloadVid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.iconDownloadViewOnClick(v,mList.get(getAdapterPosition()).getTitle(),mList.get(getAdapterPosition()).getUrl());
                }
            });

            //messageText = (TextView) mView.findViewById(R.id.message_text_layout);

        }
    }

    public interface AdapterListener {

        void iconTextViewOnClick(View v, String title, String url);

        void iconDownloadViewOnClick(View v, String title, String url);

        void iconImageUnFollowOnClick(View v, int position);
    }

}
