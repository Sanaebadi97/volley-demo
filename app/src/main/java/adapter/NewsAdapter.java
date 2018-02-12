package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.sanaebadi.gittest.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import activity.DetailFeeds;
import manage.DatabaseHelper;
import model.NewsFeed;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

  private List<NewsFeed> newsFeeds;
  private Context context;
  private static final String TAG = "NewsAdapter";

  public NewsAdapter(List<NewsFeed> newsFeeds, Context context) {
    this.newsFeeds = newsFeeds;
    this.context = context;
  }

  @Override
  public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context)
      .inflate(R.layout.singleitem_recyclerview, parent, false);
   Typeface typeface = Typeface.createFromAsset(context.getAssets(), "comicbd.ttf");
    return new MyViewHolder(view, typeface);
  }

  @Override
  public void onBindViewHolder(final MyViewHolder holder, int position) {
    final NewsFeed feed = newsFeeds.get(position);
    holder.txt_content.setText(feed.getContent());
    holder.txt_title.setText(feed.getTitle());
    holder.ratingBar_view.setProgress(feed.getRate());

    Picasso.with(context)
      .load(feed.getImageUrl())
      .into(holder.img_thumbnail);


    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(context, DetailFeeds.class);
        intent.putExtra(DatabaseHelper.KEY_ID, feed.getId());
        intent.putExtra(DatabaseHelper.KEY_IMAGE_URL, feed.getImageUrl());
        intent.putExtra(DatabaseHelper.KEY_FEED_NAME, feed.getTitle());
        intent.putExtra(DatabaseHelper.KEY_CONTENT, feed.getContent());
        intent.putExtra(DatabaseHelper.KEY_RATING, feed.getRate());

        context.startActivity(intent);
      }
    });


  }

  @Override
  public int getItemCount() {
    try {
      if (newsFeeds != null) {
        return newsFeeds.size();
      } else {
        return 0;
      }

    } catch (NullPointerException e) {
      Log.e(TAG, "getItemCount: " + e.getMessage());
    }
    return newsFeeds.size();
  }

  public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView txt_content, txt_title;
    ImageView img_thumbnail, ima_fav;
    ProgressBar ratingBar_view;

    public MyViewHolder(View itemView, Typeface typeface) {
      super(itemView);

      txt_content = (TextView) itemView.findViewById(R.id.txt_content);
      txt_title = (TextView) itemView.findViewById(R.id.txt_title);
      img_thumbnail = (ImageView) itemView.findViewById(R.id.img_thumbnail);
      ratingBar_view = (ProgressBar) itemView.findViewById(R.id.ratingBar_view);

      ratingBar_view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Toast.makeText(context, "Rated By User :" + newsFeeds.get(getAdapterPosition()).getRate(), Toast.LENGTH_SHORT).show();
        }
      });

      txt_content.setTypeface(typeface);
      txt_title.setTypeface(typeface);
    }
  }
}
