package digitalsigma.com.mxplayersample;

import android.content.Context;
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
import java.util.HashMap;
import java.util.Map;

public class AddVideoRequest {

    Context context;

    public AddVideoRequest(Context context) {
        this.context = context;
    }

    public void addVideos(final ArrayList<VideoModel> mList, final VideoAdapter mAdapter) {
        final String url = "http://hubalrasul.digitalsigma.io/api/videos";
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray dataArray = object.getJSONArray("data");
                    for (int a = 0; a < dataArray.length(); a++) {

                        JSONObject videos = dataArray.getJSONObject(a);
                        String vid_name = videos.getString("title");
                        String vid_url = "http://hubalrasul.digitalsigma.io" + videos.getString("video");
                        //Log.d("response url", songUrl);
                        mList.add(new VideoModel(vid_name, vid_url));


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
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");

                return params;
            }
        };

        MySingleton.getInstance(context).addToRequestQueue(req);

    }
}
