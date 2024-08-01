package com.hds.restapitest.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hds.restapitest.domain.Article;
import com.hds.restapitest.dto.AddArticleRequest;
import com.hds.restapitest.dto.ArticleListViewResponse;
import com.hds.restapitest.dto.ArticleResponse;
import com.hds.restapitest.dto.ArticleViewResponse;
import com.hds.restapitest.dto.UpdateArticleRequest;
import com.hds.restapitest.service.BlogJpaService;
import com.hds.restapitest.service.BlogMybatisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@RestController // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
@Slf4j
public class BlogApiController {
	
	private final BlogJpaService blogJpaService;
	private final BlogMybatisService blogMybatisService;


	
    //Jpa 호출 테스트..............    
	
	@PostMapping("/api/articles")
	public ResponseEntity<ArticleViewResponse> addArticle(@RequestBody AddArticleRequest request) {
		Article savedArticle = blogJpaService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ArticleViewResponse(savedArticle));
	}
	
	@GetMapping("/api/articles")
	public ResponseEntity<List<ArticleListViewResponse>> findAllArticles() {
		/*
		 * List<ArticleListViewResponse> articles = blogJpaService.findAll() .stream()
		 * .map(ArticleListViewResponse::new) .toList();
		 */		
		
        List<ArticleListViewResponse> articles = blogJpaService.findAll().stream().map(ArticleListViewResponse::new).collect(Collectors.toList());
		
		
		return ResponseEntity.ok()
				.body(articles);
	}


	@GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleViewResponse> findArticle(@PathVariable long id) {
        Article article = blogJpaService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleViewResponse(article));
    }
	
    @PutMapping("/api/articles/{id}")
    public ResponseEntity<ArticleViewResponse> updateArticle(@PathVariable long id,
                                                 @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = blogJpaService.update(id, request);

        //return ResponseEntity.ok()
        //        .body(updatedArticle);

    
        return ResponseEntity.ok()
                .body(new ArticleViewResponse(updatedArticle));

    
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        blogJpaService.delete(id);

        return ResponseEntity.ok()
                .build();
    }
    
    
    //Mybatis 호출 테스트..............    
	@GetMapping("/api/mybatis/articles")
	public ResponseEntity<List<ArticleListViewResponse>> getArticleList() throws Exception {
		/*
		 * List<ArticleListViewResponse> articles = blogMybatisService.getArticleList()
		 * .stream() .map(ArticleListViewResponse::new) .toList();
		 */		
        List<ArticleListViewResponse> articles = blogMybatisService.getArticleList().stream().map(ArticleListViewResponse::new).collect(Collectors.toList());
				
				

		return ResponseEntity.ok()
				.body(articles);
		
	}
    
	@GetMapping("/api/mybatis/articles/{id}")
    public ResponseEntity<ArticleViewResponse> getArticleById(@PathVariable long id) throws Exception {
        Article article = blogMybatisService.getArticleById(id);
        log.info("article info : {}", article.toString());
        log.info("ArticleResponse info : {}", (new ArticleViewResponse(article)).toString());
        
        return ResponseEntity.ok()
                .body(new ArticleViewResponse(article));
    }
	
	@PostMapping("/api/mybatis/articles")
	public ResponseEntity<ArticleViewResponse> addMybatisArticle(@RequestBody AddArticleRequest request) throws Exception {
		Article new_article = request.toEntity();
		int ret = blogMybatisService.insertArticle(new_article);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ArticleViewResponse(new_article));
	}
	
	
    @PutMapping("/api/mybatis/articles/{id}")
    public ResponseEntity<ArticleViewResponse> updateMybatisArticle(@PathVariable long id,
                                                 @RequestBody UpdateArticleRequest request) throws Exception {

    	Article updatedArticle = request.toEntity();
    	updatedArticle.setId(id);
		int ret = blogMybatisService.updateArticle(updatedArticle);

        return ResponseEntity.ok()
                .body(new ArticleViewResponse(updatedArticle));
    
    }
	
    @DeleteMapping("/api/mybatis/articles/{id}")
    public ResponseEntity<Void> deleteMybatisArticle(@PathVariable long id) throws Exception {

    	int ret = blogMybatisService.deleteArticle(id);

        return ResponseEntity.ok()
                .build();
    }
    
    
}
