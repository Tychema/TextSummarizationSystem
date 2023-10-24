package ynu.edu.textsummarizationsystem.beans;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "news")
public class News implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private long id;
  @Column(name = "title")
  private String title;
  @Column(name = "url")
  private String url;
  @Column(name = "detail")
  private String detail;
  @Column(name = "company")
  private String company;
  @Column(name = "time")
  private String time;
  @Column(name = "title2")
  private String title2;
  @Column(name = "datetime")
  private String datetime;
  @Column(name = "content")
  private String content;
  @Column(name = "scores")
  private String scores;
  @Column(name = "sentiment")
  private String sentiment;
  @Column(name = "summary")
  private String summary;



  public long getId() {
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

  public void setId(Long id) {
    this.id = id;
  }

  public News(long id, String title, String url, String detail, String company, String time, String title2, String datetime, String content, String scores, String sentiment, String summary) {
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
  }

  public News() {
  }

  public void setId(long id) {
    this.id = id;
  }

}
