package com.example.demo.model;

import lombok.Data;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by tuzhenyu on 17-7-20.
 * @author tuzhenyu
 */
@Entity
@Data
public class LoginTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    private Date expired;
    private int status;
    private String ticket;

    @Override
    public String toString() {
        return "LoginTicket{" +
                "id=" + id +
                ", userId=" + userId +
                ", expired=" + expired +
                ", status=" + status +
                ", ticket='" + ticket + '\'' +
                '}';
    }
}
