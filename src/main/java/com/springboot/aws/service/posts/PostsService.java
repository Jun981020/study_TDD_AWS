package com.springboot.aws.service.posts;

import com.springboot.aws.domain.posts.Posts;
import com.springboot.aws.domain.posts.PostsRepository;
import com.springboot.aws.web.dto.PostsListResponseDto;
import com.springboot.aws.web.dto.PostsResponseDto;
import com.springboot.aws.web.dto.PostsSaveRequestDto;
import com.springboot.aws.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }
    @Transactional
    public void delete(Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        postsRepository.delete(posts);
    }

}
