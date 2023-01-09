package com.orm.entity;

import com.orm.MyORM.Annotation.Column;
import com.orm.MyORM.Annotation.Id;
import com.orm.MyORM.Annotation.Table;


@Table(value = "users")
public class User {
    @Id
    @Column
    private int id;

    @Column
    private String username;
    @Column
    private String fullname;
    @Column
    private String password;

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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
