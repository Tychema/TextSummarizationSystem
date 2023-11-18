package ynu.edu.textsummarizationsystem.beans;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="counters")
@TypeAlias("")
public class counters {
    @Id
    private String id;
    @Field("attentioncompany_value")
    private int attentioncompany_value;
    @Field("news_value")
    private int news_value;
    @Field("user_value")
    private int user_value;
    @Field("collectnews_value")
    private int collectnews_value;
    @Field("history_value")
    private int history_value;

    public counters() {
    }

    public counters(String id, int attentioncompany_value, int news_value, int user_value, int collectnews_value, int history_value) {
        this.id = id;
        this.attentioncompany_value = attentioncompany_value;
        this.news_value = news_value;
        this.user_value = user_value;
        this.collectnews_value = collectnews_value;
        this.history_value = history_value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAttentioncompany_value() {
        return attentioncompany_value;
    }

    public void setAttentioncompany_value(int attentioncompany_value) {
        this.attentioncompany_value = attentioncompany_value;
    }

    public int getNews_value() {
        return news_value;
    }

    public void setNews_value(int news_value) {
        this.news_value = news_value;
    }

    public int getUser_value() {
        return user_value;
    }

    public void setUser_value(int user_value) {
        this.user_value = user_value;
    }

    public int getCollectnews_value() {
        return collectnews_value;
    }

    public void setCollectnews_value(int collectnews_value) {
        this.collectnews_value = collectnews_value;
    }

    public int getHistory_value() {
        return history_value;
    }

    public void setHistory_value(int history_value) {
        this.history_value = history_value;
    }

    @Override
    public String toString() {
        return "counters{" +
                "id='" + id + '\'' +
                ", attentioncompany_value=" + attentioncompany_value +
                ", news_value=" + news_value +
                ", user_value=" + user_value +
                ", collectnews_value=" + collectnews_value +
                ", history_value=" + history_value +
                '}';
    }
}
