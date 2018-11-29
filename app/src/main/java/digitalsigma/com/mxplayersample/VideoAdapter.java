package digitalsigma.com.mxplayersample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Hima on 11/29/2018.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    Context context;
    private List<String> mList;
    public AdapterListener onClickListener;

    public VideoAdapter(Context context, List<String> mList, AdapterListener onClickListener) {
        this.context = context;
        this.mList = mList;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_video_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mList.size() > 0) {
            return mList.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        TextView textView ;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            textView = mView.findViewById(R.id.row_videoList_Title);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.iconTextViewOnClick(v, getAdapterPosition());
                }
            });
            //messageText = (TextView) mView.findViewById(R.id.message_text_layout);

        }
    }

    public interface AdapterListener {

        void iconTextViewOnClick(View v, int position);

        void iconImageViewOnClick(View v, int position);

        void iconImageUnFollowOnClick(View v, int position);
    }

}

