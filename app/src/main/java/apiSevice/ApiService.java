package apiSevice;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import activity.MainActivity;
import app.AppController;
import model.NewsFeed;


public class ApiService {
  private static final String TAG = "ApiService";
  private Context context;


  //  private String url = " https://api.myjson.com/bins/w86a";
  private String url = "http://sanaebadi.ir/volleyDemo/get-data.php";


  public ApiService(Context context) {
    this.context = context;
  }

  public void getData(final onGetData onGetData) {
    MainActivity.showDialog();

    final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
      null, new Response.Listener<JSONArray>() {
      @Override
      public void onResponse(JSONArray response) {
        Log.d(TAG, "onResponse: " + response.toString());
        List<NewsFeed> feeds = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
          NewsFeed feed = new NewsFeed();

          try {
            JSONObject object = response.getJSONObject(i);

            feed.setId(object.getInt("id"));
            feed.setImageUrl(object.getString("image_url"));
            feed.setTitle(object.getString("title"));
            feed.setContent(object.getString("content"));
            feed.setRate(object.getInt("rate"));

            feeds.add(feed);


          } catch (JSONException e) {
            e.printStackTrace();
          }

        }

        onGetData.onGet(feeds);
        MainActivity.hideDialog();
      }

    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        try {
          onGetData.onGet(null);
          MainActivity.hideDialog();

          Log.e(TAG, "onErrorResponse: " + error.getMessage());

        } catch (Exception e) {
          //     Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
          Log.e(TAG, "onErrorResponse: " + e.toString());
        }

      }
    });

    request.setRetryPolicy(new DefaultRetryPolicy(8000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
      DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    AppController.getmIstance().getmRequestQueue().add(request);
  }

  public interface onGetData {
    void onGet(List<NewsFeed> feeds);
  }

}
