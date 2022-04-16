package com.sparta.hanghae99clone.service;

import com.sparta.hanghae99clone.dto.request.SignupRequestDto;
//import com.sparta.hanghae99clone.dto.SignupRequestDto;
import com.sparta.hanghae99clone.model.User;
import com.sparta.hanghae99clone.repository.UserRepository;
//import com.sparta.springcore.model.UserRoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    private final S3Uploader s3Uploader;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, S3Uploader s3Uploader) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.s3Uploader = s3Uploader;
    }

    public void registerUser(MultipartFile multipartFile, SignupRequestDto requestDto)throws IOException {
        // 회원 ID 중복 확인
        String username = requestDto.getUsername();
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }

        // 패스워드 암호화
        System.out.println(requestDto.getNickname());
        System.out.println(requestDto.getUsername());
        System.out.println(requestDto.getPassword());
        String password = passwordEncoder.encode(requestDto.getPassword());
        String nickname = requestDto.getNickname();
        String imageSrc= s3Uploader.upload(multipartFile,"static");


        // 사용자 ROLE 확인
//        UserRoleEnum role = UserRoleEnum.USER;
//        if (requestDto.isAdmin()) {
//            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
//                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
//            }
//            role = UserRoleEnum.ADMIN;
//        }

        User user = new User(username, password, nickname, imageSrc);
        userRepository.save(user);
    }
}