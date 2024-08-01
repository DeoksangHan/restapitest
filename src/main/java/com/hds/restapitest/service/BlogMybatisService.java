package com.hds.restapitest.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hds.restapitest.domain.Article;
import com.hds.restapitest.dto.AddArticleRequest;
import com.hds.restapitest.dto.ArticleResponse;
import com.hds.restapitest.dto.UpdateArticleRequest;
import com.hds.restapitest.mapper.ArticleMapper;
import com.hds.restapitest.repository.BlogRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service // 빈으로 등록
public class BlogMybatisService {
	
	private final ArticleMapper articleMapper;
	
	public List<Article> getArticleList() throws Exception {
		return articleMapper.getArticleList();
	}

	public Article getArticleById(long id) throws Exception {
		return articleMapper.getArticleById(id);
	}
	
	public int insertArticle(Article article) throws Exception {
		return articleMapper.insertArticle(article);
	}

	public int updateArticle(Article article) throws Exception {
		return articleMapper.updateArticle(article);
	}

	public int deleteArticle(long id) throws Exception {
		return articleMapper.deleteArticle(id);
	}
}
