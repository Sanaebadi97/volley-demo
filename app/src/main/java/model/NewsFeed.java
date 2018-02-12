package model;

/**
 * Created by sanaebadi on 7/30/2017.
 */

public class NewsFeed {
  private int id;
  private String imageUrl;
  private String title;
  private String content;
  private int rate;


  public NewsFeed() {

  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public int getRate() {
    return rate;
  }

  public void setRate(int rate) {
    this.rate = rate;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


}
