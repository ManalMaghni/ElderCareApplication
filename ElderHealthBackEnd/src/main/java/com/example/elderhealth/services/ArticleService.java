package com.example.elderhealth.services;

import com.example.elderhealth.entities.Article;
import com.example.elderhealth.repositories.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Article getArticleById(Long id) {
        return articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Article not found"));
    }

    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    public Article updateArticle(Long id, Article article) {
        Article existingArticle = getArticleById(id);

        if (article.getTheme() != null) {
            existingArticle.setTheme(article.getTheme());
        }

        if (article.getContenu() != null) {
            existingArticle.setContenu(article.getContenu());
        }

        return articleRepository.save(existingArticle);
    }


    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}
