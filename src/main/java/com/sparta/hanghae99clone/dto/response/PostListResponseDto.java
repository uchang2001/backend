package com.sparta.hanghae99clone.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostListResponseDto {

    private Long postId;

    private String imageFile;

    private String content;

    private String dayBefore;

    private String commentCnt;

    private String nickname;
}
