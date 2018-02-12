package manage;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import model.NewsFeed;


/**
 * Created by sanaebadi on 8/23/2017.
 */

public class AddPostsTask extends AsyncTask<Void, Integer, Void> {

  private static final String TAG = "AddPostsTask";
  private Context context;
  private List<NewsFeed> feeds;
  private DatabaseHelper helper;
  private ProgressDialog dialog;

  public AddPostsTask(Context context, List<NewsFeed> feeds, DatabaseHelper helper) {

    this.context = context;
    this.feeds = feeds;
    this.helper = helper;
  }


  @Override
  protected void onPreExecute() {
    super.onPreExecute();
    dialog = new ProgressDialog(context);
    dialog.setTitle("Saving Posts ...");
    dialog.setMessage("Saving Posts. Please Wait ...");
    dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    dialog.setIndeterminate(false);
    dialog.show();
  }

  @Override
  protected Void doInBackground(Void... params) {
    for (int i = 0; i < feeds.size(); i++) {

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      NewsFeed feed = feeds.get(i);
      ContentValues values = new ContentValues();
      values.put(DatabaseHelper.KEY_ID, feed.getId());
      values.put(DatabaseHelper.KEY_FEED_NAME, feed.getTitle());
      values.put(DatabaseHelper.KEY_IMAGE_URL, feed.getImageUrl());
      values.put(DatabaseHelper.KEY_CONTENT, feed.getContent());
      values.put(DatabaseHelper.KEY_RATING, feed.getRate());

      SQLiteDatabase database = helper.getWritableDatabase();
      long isInserted = database.insert(DatabaseHelper.NEWS_TABLE, null, values);
      publishProgress((i * 100) / feeds.size());
      Log.i(TAG, "addNews: " + isInserted);
    }

    return null;
  }

  @Override
  protected void onPostExecute(Void aVoid) {
    super.onPostExecute(aVoid);
    dialog.hide();
  }

  @Override
  protected void onProgressUpdate(Integer... values) {
    super.onProgressUpdate(values);
    dialog.setProgress(values[0]);
  }
}
