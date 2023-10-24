package ynu.edu.textsummarizationsystem.beans;

import java.io.Serializable;

import javax.persistence.*;


@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userid;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "management")
    private  int management;

    public User() {
        super();
    }

    public User(Long userid,String username, String password,int management) {
        super();
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.management = management;
    }

    public int getManagement(){
        return  management;
    }
    public void setManagement(int management){
        this.management=management;
    }

    public Long getUserId() {
        return userid;
    }

    public void setId(Long userid) {
        this.userid = userid;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassWord() {
        return password;
    }

    public void setPassWord(String password) {
        this.password = password;
    }

}
