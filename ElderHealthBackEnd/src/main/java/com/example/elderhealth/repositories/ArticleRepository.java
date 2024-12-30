package com.example.elderhealth.repositories;

import com.example.elderhealth.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
