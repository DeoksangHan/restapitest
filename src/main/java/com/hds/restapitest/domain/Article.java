package com.hds.restapitest.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

//import jakarta.persistence.*;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 추가 
@Entity
@Table(name="article") 
public class Article {

    @Id  // id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 1 씩 증가
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false) // title 이라는 not null 컬럼과 매핑
    private String title;

    @Column(name = "content", nullable = false)
    private String content;
    
    @CreatedDate // 엔터티가 생성될 때 생성 시간 저장
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @LastModifiedDate  // 엔터티가 수정될 때 수정 시간 저장
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder // 빌더 패턴으로 객체 생성 
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }
    /*
     * 빌터 패턴 사용하지 않을때
     * new Article("abc", "def");
     * 
     * 빌더패턴 사용할 때
     * Article.builer()
     *   .title("abc")
     *   .content("def")
     *   .build();
     * 
     */

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}