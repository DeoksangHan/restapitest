package com.hds.restapitest.controller;

import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.hds.restapitest.domain.Article;
import com.hds.restapitest.dto.ArticleListViewResponse;
import com.hds.restapitest.dto.ArticleViewResponse;
import com.hds.restapitest.service.BlogJpaService;

//import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BlogViewController {

	private final BlogJpaService blogService;

    @GetMapping("/articles")
    public String getArticles(HttpServletRequest request, Model model) {
    	// 로그인 정보 관련 테스트용으로 현재 세션값 모두 보기 
    	Enumeration<String> attributes = request.getSession().getAttributeNames();
    	while (attributes.hasMoreElements()) {
    	    String attribute = (String) attributes.nextElement();
    	    System.err.println(attribute+" : "+request.getSession().getAttribute(attribute));
    	}
    	
        List<ArticleListViewResponse> articles = blogService.findAll().stream().map(ArticleListViewResponse::new).collect(Collectors.toList());
    	
		/*
		 * List<ArticleListViewResponse> articles = blogService.findAll() .stream()
		 * .map(ArticleListViewResponse::new) .toList();
		 */        model.addAttribute("articles", articles);

        return "articleList"; // articleList.html 라는 뷰 조회  resource//templates/articleList.html
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));

        return "article";
    }
    
    @GetMapping("/new-article")
    // id 키를 가진 쿼리 파라미터의 값을 id 변수에 매핑 ( id는 없을 수도 있음 )
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {  // ID가 없으면 생성 
            model.addAttribute("article", new ArticleViewResponse());
        } else {  // id가 있으면 수정
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }

        return "newArticle";
    }
}
