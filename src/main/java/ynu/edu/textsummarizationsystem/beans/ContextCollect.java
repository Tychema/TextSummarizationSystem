package ynu.edu.textsummarizationsystem.beans;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "context_collect")
public class ContextCollect implements Serializable{

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

  public ContextCollect(Long id, long userid, String context, String summarization, Date time) {
    this.id = id;
    this.userid = userid;
    this.context = context;
    this.summarization = summarization;
    this.time = time;
  }

  public ContextCollect() {

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

  public void setTime(java.sql.Timestamp time) {
    this.time = time;
  }

}
