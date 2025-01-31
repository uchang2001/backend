package com.sparta.hanghae99clone.service;

import com.sparta.hanghae99clone.dto.request.PostRequestDto;
import com.sparta.hanghae99clone.dto.response.PostListResponseDto;
import com.sparta.hanghae99clone.model.Image;
import com.sparta.hanghae99clone.model.Post;
import com.sparta.hanghae99clone.model.User;
import com.sparta.hanghae99clone.repository.CommentRepository;
import com.sparta.hanghae99clone.repository.ImageRepository;
import com.sparta.hanghae99clone.repository.LikeRepository;
import com.sparta.hanghae99clone.repository.PostRepository;
import com.sparta.hanghae99clone.utill.Calculator;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final ImageRepository imageRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final Calculator calculator;
    private final LikeRepository likeRepository;

    public void save(String content,
        String uploadImageUrl,
        User user) {

        // 게시글 저장
        Post post = new Post(user, content);
        postRepository.save(post);

        // 이미지 저장
        Image image = new Image(uploadImageUrl, post);
        imageRepository.save(image);
    }

    public List<PostListResponseDto> findAll(User user) {
        List<Post> allPost = postRepository.findAll();
        List<PostListResponseDto> postResponseDtos = new ArrayList<>();

        for (Post post : allPost) {
            // 게시글에 해당하는 이미지 불러오기
            Image image = imageRepository.findByPost(post).orElseThrow(
                () -> new IllegalArgumentException("이미지가 존재하지 않습니다.")
            );
            // 게시글에 존재하는 댓글의 수
            Long commentCnt = Long.valueOf(commentRepository.findByPost(post).size());

            // 몇 분 전에 게시글이 작성되었는지 확인
            long dayBefore = ChronoUnit.MINUTES.between(post.getCreatedAt(), LocalDateTime.now());

            // 게시글의 존재 유무
            boolean likeStatus = likeRepository.existsByPostAndUser(post, user);
            postResponseDtos.add(new PostListResponseDto(post, image, calculator.time(dayBefore), commentCnt, likeStatus));
        }
        return postResponseDtos;
    }



    // 특정 게시글 수정
    @Transactional
    public void edit(Long postId,
        PostRequestDto postRequestDto) {
        Post post = postRepository.findById(postId).orElseThrow(
            () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        post.editContent(postRequestDto);
    }

    public void remove(Long postId) {
        System.out.println("postId = " + postId);
        List<Post> all = postRepository.findAll();
        for (Post post : all) {
            System.out.println("post.getId() = " + post.getId());
            System.out.println("post.getId().equals(postId) = " + post.getId().equals(postId));
        }
        postRepository.deleteById(postId);
    }
}
