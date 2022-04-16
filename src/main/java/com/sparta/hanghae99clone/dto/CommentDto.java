package com.sparta.hanghae99clone.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {

    private Long commentId;

    private String nickname;

    private String contents;
}
