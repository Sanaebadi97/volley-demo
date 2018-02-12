package activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sanaebadi.gittest.R;
import com.squareup.picasso.Picasso;

import manage.DatabaseHelper;


public class DetailFeeds extends AppCompatActivity {

  TextView txt_content, txt_title;
  ImageView img_thumbnail;
  ProgressBar ratingBar_view;

  private Intent intent;
  private int id;
  private int rate;
  private String title;
  private String content;
  private String imageUrl;

  private Typeface typeface;
  private ProgressBar progressBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_feeds);

    init();

    intent = getIntent();
    getDataFromIntent();
    setData();

    typeface = Typeface.createFromAsset(getAssets(), "comicbd.ttf");
    txt_content.setTypeface(typeface);
    txt_title.setTypeface(typeface);

  }

  private void setData() {

    Picasso.with(this)
      .load(imageUrl)
      .into(img_thumbnail);



    txt_title.setText(title);
    txt_content.setText(content);
    ratingBar_view.setProgress(rate);


  }

  private void getDataFromIntent() {
    id = intent.getIntExtra(DatabaseHelper.KEY_ID, 0);
    rate = intent.getIntExtra(DatabaseHelper.KEY_RATING, 0);
    title = intent.getStringExtra(DatabaseHelper.KEY_FEED_NAME);
    content = intent.getStringExtra(DatabaseHelper.KEY_CONTENT);
    imageUrl = intent.getStringExtra(DatabaseHelper.KEY_IMAGE_URL);
  }

  private void init() {
    txt_content = (TextView) findViewById(R.id.txt_content);
    txt_title = (TextView) findViewById(R.id.txt_title);
    img_thumbnail = (ImageView) findViewById(R.id.img_thumbnail);
    ratingBar_view = (ProgressBar) findViewById(R.id.ratingBar_view);
  }
}
