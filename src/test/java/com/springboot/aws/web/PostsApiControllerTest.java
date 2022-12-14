package com.springboot.aws.web;

import com.springboot.aws.domain.posts.Posts;
import com.springboot.aws.domain.posts.PostsRepository;
import com.springboot.aws.web.dto.PostsSaveRequestDto;
import com.springboot.aws.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @AfterAll
    public void tearDown(){
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_등록(){
        //given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto dto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("jun")
                .build();
        String url = "http://localhost:"+port + "/api/v1/posts";
        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url,dto,Long.class);
        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }
    @Test
    public void posts_수정(){
        //given
        Posts posts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("spring").build());
        Long updateId = posts.getId();
        String updateTitle = "updateTitle";
        String updateContent = "updateContent";
        PostsUpdateRequestDto updateDto = PostsUpdateRequestDto.builder()
                .title(updateTitle)
                .content(updateContent)
                .build();
        String url = "http://localhost:"+port+"/api/v1/posts/"+updateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity
                = new HttpEntity<>(updateDto);
        
        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(updateTitle);
        assertThat(all.get(0).getContent()).isEqualTo(updateContent);
    }
    @Test
    public void baseTimeEntity_등록(){
        LocalDateTime now = LocalDateTime.of(2022, 11, 11, 23, 0, 0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build()
        );
        List<Posts> all = postsRepository.findAll();
        Posts posts = all.get(0);
        System.out.println(">>>>>>>>>>>>>>>>>createDat="+posts.getCreatedDate()+",modifiedDate= "+
                posts.getModifyDate()
                );
        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifyDate()).isAfter(now);

    }
}