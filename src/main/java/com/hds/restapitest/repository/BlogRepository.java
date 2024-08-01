package com.hds.restapitest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hds.restapitest.domain.Article;

public interface BlogRepository extends JpaRepository<Article, Long> {

}
