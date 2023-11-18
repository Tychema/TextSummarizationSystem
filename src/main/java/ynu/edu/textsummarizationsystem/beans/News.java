package ynu.edu.textsummarizationsystem.beans;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection="news")
@TypeAlias("")
public class News implements Serializable {
  @Id
  private Integer id;
  private String title;
  private String url;
  private String detail;
  private String company;
  private String time;
  private String title2;
  private String datetime;
  private String content;
  private String scores;

  public int getClick() {
    return click;
  }

  public void setClick(int click) {
    this.click = click;
  }

  private String sentiment;
  private String summary;
  private int click;

  public Integer getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }


  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }


  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }


  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }


  public String getTitle2() {
    return title2;
  }

  public void setTitle2(String title2) {
    this.title2 = title2;
  }


  public String getDatetime() {
    return datetime;
  }

  public void setDatetime(String datetime) {
    this.datetime = datetime;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public String getScores() {
    return scores;
  }

  public void setScores(String scores) {
    this.scores = scores;
  }


  public String getSentiment() {
    return sentiment;
  }

  public void setSentiment(String sentiment) {
    this.sentiment = sentiment;
  }


  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public News() {
  }

  public News(Integer id, String title, String url, String detail, String company, String time, String title2, String datetime, String content, String scores, String sentiment, String summary, int click) {
    this.id = id;
    this.title = title;
    this.url = url;
    this.detail = detail;
    this.company = company;
    this.time = time;
    this.title2 = title2;
    this.datetime = datetime;
    this.content = content;
    this.scores = scores;
    this.sentiment = sentiment;
    this.summary = summary;
    this.click = click;
  }
}
