package activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.sanaebadi.gittest.R;

import java.util.List;

import adapter.NewsAdapter;
import apiSevice.ApiService;
import manage.DatabaseHelper;
import model.NewsFeed;


public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";
  private RecyclerView recyclerView;
  private ApiService apiService;
  private static ProgressDialog dialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    dialog = new ProgressDialog(MainActivity.this);
    dialog.setMessage("please wait...");
    dialog.setCancelable(false);



    setupRecyclerView();
    getAllNewsFromDatabase();

    apiService = new ApiService(this);
    apiService.getData(new ApiService.onGetData() {
      @Override
      public void onGet(List<NewsFeed> feeds) {
        DatabaseHelper helper = new DatabaseHelper(MainActivity.this);
        helper.addListNews(feeds);

        NewsAdapter adapter = new NewsAdapter(feeds, MainActivity.this);
        recyclerView.setAdapter(adapter);
      }
    });
  }

  private void setupRecyclerView() {
    recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,
      LinearLayoutManager.VERTICAL, false));
  }

  private void getAllNewsFromDatabase() {
    DatabaseHelper helper = new DatabaseHelper(MainActivity.this);
    List<NewsFeed> feeds = helper.getAllNews();
    NewsAdapter adapter = new NewsAdapter(feeds, this);
    recyclerView.setAdapter(adapter);
  }

  public static void showDialog() {
    if (!dialog.isShowing()) {
      dialog.show();
    }

  }

  public static void hideDialog() {
    if (dialog.isShowing()) {
      dialog.hide();
    }
  }

}
