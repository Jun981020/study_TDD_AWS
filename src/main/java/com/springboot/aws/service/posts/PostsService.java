package com.springboot.aws.service.posts;

import com.springboot.aws.domain.posts.Posts;
import com.springboot.aws.domain.posts.PostsRepository;
import com.springboot.aws.web.dto.PostsResponseDto;
import com.springboot.aws.web.dto.PostsSaveRequestDto;
import com.springboot.aws.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto dto){
        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        posts.update(dto.getTitle(),dto.getContent());
        return id;
    }
    public PostsResponseDto findById(Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당게시글이 없습니다. id=" + id));
        return new PostsResponseDto(posts);
    }
}
