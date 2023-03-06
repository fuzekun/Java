package model;

import java.io.Serializable;
import java.sql.Date;

public class User implements Serializable {
    private int id; // 用户编号
    private String username; // 用户姓名
    private String password; // 用户密码
    private String gender; // 用户性别
    private String email; // 用户邮箱
    private String telephone; // 用户联系电话
    private String introduce; // 用户介绍
    private String activeCode; // 激活码
    private String role; // 用户角色
    private int state; // 用户状态
    private Date registTime;// 注册时间

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getIntroduce() {
        return introduce;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public String getRole() {
        return role;
    }

    public int getState() {
        return state;
    }

    public Date getRegistTime() {
        return registTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", introduce='" + introduce + '\'' +
                ", activeCode='" + activeCode + '\'' +
                ", role='" + role + '\'' +
                ", state=" + state +
                ", registTime=" + registTime +
                '}';
    }
}
