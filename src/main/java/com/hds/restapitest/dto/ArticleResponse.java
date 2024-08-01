package com.hds.restapitest.dto;

import com.hds.restapitest.domain.Article;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor // 모든 필드값을 파라미터로 받는 생성자 추가 
@Getter
@ToString
public class ArticleResponse {

	private final String title;
	private final String content;
	
	public ArticleResponse(Article article) {
		this.title = article.getTitle();
		this.content = article.getContent();
 	}

}
