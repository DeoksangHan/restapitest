package com.hds.restapitest.dto;

import com.hds.restapitest.domain.Article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateArticleRequest {
    private String title;
    private String content;
    
    public Article toEntity() { // 생성자를 사용해 객체 생성
		return Article.builder()
			.title(title)
			.content(content)
			.build();
	}
}