package com.example.demo.Repositry;


import com.example.demo.model.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleTagRepository extends JpaRepository<ArticleTag,Integer> {
}
