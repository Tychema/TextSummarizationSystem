package ynu.edu.textsummarizationsystem.beans;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;


@Document(collection="user")
@TypeAlias("")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private int id;
    private String username;
    private String password;
    private  int management;

    public User() {
    }

    public User(int id, String username, String password, int management) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.management = management;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getManagement() {
        return management;
    }

    public void setManagement(int management) {
        this.management = management;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", management=" + management +
                '}';
    }
}
