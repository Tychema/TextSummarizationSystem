package ynu.edu.textsummarizationsystem.beans;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="history_records")
@TypeAlias("")
public class HistoryRecords {
    @Id
    private int id;
    private int userid;
    private int newid;
    private String clicktime;


    public HistoryRecords() {
    }

    public HistoryRecords(int userid, int newid, String clicktime) {
        this.userid = userid;
        this.newid = newid;
        this.clicktime = clicktime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getNewid() {
        return newid;
    }

    public void setNewid(int newid) {
        this.newid = newid;
    }

    public String getClicktime() {
        return clicktime;
    }

    public void setClicktime(String clicktime) {
        this.clicktime = clicktime;
    }

    @Override
    public String toString() {
        return "HistoryRecords{" +
                "id=" + id +
                ", userid=" + userid +
                ", newid=" + newid +
                ", clicktime='" + clicktime + '\'' +
                '}';
    }
}
