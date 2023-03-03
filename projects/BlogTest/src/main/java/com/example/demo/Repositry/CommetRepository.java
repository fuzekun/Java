package com.example.demo.Repositry;

import com.example.demo.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommetRepository extends JpaRepository<Comment,Integer> {
}
