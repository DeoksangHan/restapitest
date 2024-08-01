package com.hds.restapitest.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hds.restapitest.domain.Article;
import com.hds.restapitest.dto.ArticleResponse;

@Mapper
public interface ArticleMapper {
	 public List<Article> getArticleList() throws Exception;
	 public Article getArticleById(long id) throws Exception;
	 public int insertArticle(Article article) throws Exception;
	 public int updateArticle(Article article) throws Exception;
	 public int deleteArticle(long id) throws Exception;
	 
}
