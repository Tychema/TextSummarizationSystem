package ynu.edu.textsummarizationsystem.beans;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;


@Document(collection="attention_company")
@TypeAlias("")
public class AttentionCompany implements Serializable{
    @Id
    private Integer id;
    @Field("userid")
    private Integer userid;
    private String company;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }


    public AttentionCompany(Integer id, Integer userid, String company) {
        this.id = id;
        this.userid = userid;
        this.company = company;
    }

    public AttentionCompany() {
    }

    @Override
    public String toString() {
        return "AttentionCompany{" +
                "id=" + id +
                ", userid=" + userid +
                ", company='" + company + '\'' +
                '}';
    }
}
