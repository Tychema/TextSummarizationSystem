package ynu.edu.textsummarizationsystem.beans;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="collect_news")
@TypeAlias("")
public class CollectNews {
    @Id
    private int id;
    private int userid;
    private int newid;

    public CollectNews() {
    }

    public CollectNews(int id, int userid, int newid) {
        this.id = id;
        this.userid = userid;
        this.newid = newid;
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

    @Override
    public String toString() {
        return "CollectNews{" +
                "id=" + id +
                ", userid=" + userid +
                ", newid=" + newid +
                '}';
    }
}
