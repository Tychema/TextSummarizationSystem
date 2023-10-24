package ynu.edu.textsummarizationsystem.beans;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "context")
public class Context implements Serializable{

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private long id;
  @Column(name = "userid")
  private long userid;
  @Column(name = "context")
  private String context;
  @Column(name = "summarization")
  private String summarization;
  @Column(name = "time")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
  private Date time;
  @Column(name = "contextlength")
  private long contextlength;

  public Context(Long id, long userid, String context, String summarization, Date time, long contextlength) {
    this.id = id;
    this.userid = userid;
    this.context = context;
    this.summarization = summarization;
    this.time = time;
    this.contextlength = contextlength;
  }

  public Context() {

  }


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getUserid() {
    return userid;
  }

  public void setUserid(long userid) {
    this.userid = userid;
  }


  public String getContext() {
    return context;
  }

  public void setContext(String context) {
    this.context = context;
  }


  public String getSummarization() {
    return summarization;
  }

  public void setSummarization(String summarization) {
    this.summarization = summarization;
  }


  public Date getTime() {
    return time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }


  public long getContextlength() {
    return contextlength;
  }

  public void setContextlength(long contextlength) {
    this.contextlength = contextlength;
  }

  public void setId(Long id) {
    this.id = id;
  }

}
