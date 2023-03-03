package com.example.demo.Repositry;

import com.example.demo.model.LoginTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginTicketRepository extends JpaRepository<LoginTicket,Integer> {
    LoginTicket getByTicket(String ticket);

}
