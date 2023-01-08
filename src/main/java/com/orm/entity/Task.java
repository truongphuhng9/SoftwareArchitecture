package com.orm.entity;

public class Task {
    private int id;
    private String username;
    private String taskName;
    private String taskDesc;

    public int getId() {
        return id;
    }

    public Task(int id, String username, String taskName, String taskDesc) {
        this.id = id;
        this.username = username;
        this.taskName = taskName;
        this.taskDesc = taskDesc;
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
