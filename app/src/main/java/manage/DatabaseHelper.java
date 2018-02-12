package manage;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import model.NewsFeed;

/**
 * Created by sanaebadi on 8/16/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
  private static final String TAG = "DatabaseHelper";
  private static final String DATABASE_NAME = "newsFeed";
  private static final int DATABASE_VERSION = 1;

  public static final String NEWS_TABLE = "news";

  public static final String KEY_ID = "id";
  public static final String KEY_IMAGE_URL = "image_url";
  public static final String KEY_FEED_NAME = "feed_name";
  public static final String KEY_CONTENT = "content";
  public static final String KEY_RATING = "rating";
  public static final String KEY_FAVORITE = "favorite";

  private static final String CREATE_NEWS_TABLE = "CREATE TABLE IF NOT EXISTS " +
    NEWS_TABLE + "(" +
    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
    KEY_FEED_NAME + " TEXT ," +
    KEY_IMAGE_URL + " TEXT ," +
    KEY_CONTENT + " TEXT ," +
    KEY_RATING + " INTEGER ," +
    KEY_FAVORITE + " INTEGER DEFAULT 0 );";

  private Context context;

  public DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    this.context = context;
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    try {
      db.execSQL(CREATE_NEWS_TABLE);
    } catch (SQLException e) {
      Log.e(TAG, "SQLException: " + e.toString());
    }
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  }


  public void addListNews(List<NewsFeed> feeds) {
    AddPostsTask addPostsTask = new AddPostsTask(context, feeds, this);
    addPostsTask.execute();
  }

  public List<NewsFeed> getAllNews() {
    List<NewsFeed> feeds = new ArrayList<>();

    SQLiteDatabase database = this.getReadableDatabase();
    Cursor cursor = database.rawQuery("SELECT * FROM " + NEWS_TABLE, null);
    cursor.moveToFirst();
    if (cursor.getCount() > 0) {
      while (!cursor.isAfterLast()) {
        NewsFeed feed = new NewsFeed();
        feed.setId(cursor.getInt(0));
        feed.setTitle(cursor.getString(1));
        feed.setImageUrl(cursor.getString(2));
        feed.setContent(cursor.getString(3));
        feed.setRate(cursor.getInt(4));

        feeds.add(feed);
        cursor.moveToNext();

      }
    }

    cursor.close();
    database.close();
    return feeds;
  }


}
