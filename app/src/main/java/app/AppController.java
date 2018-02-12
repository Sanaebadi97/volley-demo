package app;

import android.app.Application;
import android.graphics.ImageFormat;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by sanaebadi on 7/30/2017.
 */

public class AppController extends Application {
  private RequestQueue mRequestQueue;
  private static AppController mIstance;
  private static final String TAG = "AppController";

  public AppController() {

  }

  @Override
  public void onCreate() {
    super.onCreate();
    mIstance = this;
  }

  public static synchronized AppController getmIstance() {
    return mIstance;
  }

  public RequestQueue getmRequestQueue() {
    if (mRequestQueue == null) {
      mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }
    return mRequestQueue;
  }

  public <T> void addToRequestQueu(Request<T> request, String tag) {
    request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
    getmRequestQueue().add(request);
  }

  public <T> void addToRequestQueu(Request<T> request) {
    request.setTag(TAG);
    getmRequestQueue().add(request);
  }

  public void cancelPendingRequest(Object tag) {
    if (mRequestQueue != null) {
      mRequestQueue.cancelAll(tag);
    }
  }
}
