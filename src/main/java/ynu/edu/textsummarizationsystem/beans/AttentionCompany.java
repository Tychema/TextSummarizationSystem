package ynu.edu.textsummarizationsystem.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "attention_company")
public class AttentionCompany implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @Column(name = "userid")
    private Integer userid;
    @Column(name = "company")
    private String company;

    public long getId() {
        return id;
    }

    public Integer getUserid() {
        return userid;
    }

    public String getCompany() {
        return company;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public AttentionCompany() {
    }

    public AttentionCompany( Integer userid, String company) {
        this.userid = userid;
        this.company = company;
    }
}
