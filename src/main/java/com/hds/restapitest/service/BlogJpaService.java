package com.hds.restapitest.service;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hds.restapitest.domain.Article;
import com.hds.restapitest.dto.AddArticleRequest;
import com.hds.restapitest.dto.ArticleResponse;
import com.hds.restapitest.dto.UpdateArticleRequest;
import com.hds.restapitest.repository.BlogRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service // 빈으로 등록
public class BlogJpaService {
	
	private final BlogRepository blogRepository;
	
	// 블로그 글 추가 메서드
	public Article save(AddArticleRequest request) {
		return blogRepository.save(request.toEntity());
	}
	
	//
	public List<Article> findAll() {
		return blogRepository.findAll();
	}
	
	public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }
	
	public void delete(long id) {
        blogRepository.deleteById(id);
    }
	
	@Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        // @Transactional 설정으로 이 문으로 변경이 감지되어 내부적으로 JPA update가 호출된다....
        // 즉 이함수 마지막 부분에서 commit 이 호출 되는듯.... 변경된 캐시 정보가 commit 되는 상황 
        article.update(request.getTitle(), request.getContent());
        
        /*
         *  https://velog.io/@atelier3407/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-with-JPA-insert 
        JPA update를 사용하기 위해선 save 메소드를 사용할 수 있습니다. 
        save메소드는 파라미터 객체에 프라이머리 키가 존재한다면 update, 없다면 insert하는 특징이 있습니다.
        @Transactional는 변경을 감지합니다. 위 그림에서 영속성 컨텍스트에 영속화된 user라는 객체의 값이 달라진걸 확인하고 
        update문을 실행하는 것입니다.
        이를 더티 체킹이라고합니다
         * 
         */
        // 그래서 이 구문은 사실 필요 없다...........
        // 하지만 JPA 아닌걸로 변경할 때 대비해서 빠트리지 않도록 한번더 불러준다.... 오버헤드 심하지 않은 한에서....
        blogRepository.save(article); 

        return article;
    }
	
}
