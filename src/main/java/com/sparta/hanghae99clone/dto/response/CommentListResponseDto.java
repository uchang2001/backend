package com.sparta.hanghae99clone.dto.response;

import com.sparta.hanghae99clone.dto.CommentDto;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentListResponseDto {

    private Long id;

    private String imageFile;

    private String title;

    private String content;

    private String dayBefore;

    private Long commentCnt;

    private String userImage;

    private String nickname;

    private List<CommentDto> comments;
}
