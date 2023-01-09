package com.orm.entity;

import com.orm.MyORM.Annotation.Column;
import com.orm.MyORM.Annotation.Id;
import com.orm.MyORM.Annotation.Table;

@Table(value = "tasks")
public class Task {
    @Id
    @Column
    private int id;
    @Column
    private int userId;
    @Column
    private String taskName;
    @Column
    private String taskDesc;

    public Task() {
    }

    public Task(int id, int userId, String taskName, String taskDesc) {
        this.id = id;
        this.userId = userId;
        this.taskName = taskName;
        this.taskDesc = taskDesc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }
}
