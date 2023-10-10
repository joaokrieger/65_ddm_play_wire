package com.ddm.playwire.model;

public class User {

    private int UserId;
    private String username;
    private String password;

    public User(int userId, String username, String password) {
        UserId = userId;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
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

    @Override
    public String toString() {
        return "User{" +
                "UserId=" + UserId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
