package com.sparta.hanghae99clone.controller;

//import com.sparta.hanghae99clone.dto.SignupRequestDto;
import com.sparta.hanghae99clone.dto.request.SignupRequestDto;
import com.sparta.hanghae99clone.dto.response.IsLoginResponseDto;
import com.sparta.hanghae99clone.model.User;
import com.sparta.hanghae99clone.security.UserDetailsImpl;
import com.sparta.hanghae99clone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UserController {

    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;

    }


    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public String registerUser(@RequestParam("image") MultipartFile multipartFile ,SignupRequestDto requestDto)throws IOException {
        try {
            userService.registerUser(multipartFile, requestDto);
        }
        catch(Exception e){
            return "Signup Failed...";
        }
        return "Signup Success!";
    }


    @GetMapping("/user/islogin")
    public IsLoginResponseDto isloginUser(@AuthenticationPrincipal UserDetailsImpl user){

        IsLoginResponseDto result=new IsLoginResponseDto();
        result.setUsername(user.getUser().getUsername());
        result.setNickname(user.getNickname());
        return result;


    }




}
    // 회원 관련 정보 받기
//    @PostMapping("/user/userinfo")
//    @ResponseBody
//    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        String username = userDetails.getUser().getUsername();
////        UserRoleEnum role = userDetails.getUser().getRole();
////        boolean isAdmin = (role == UserRoleEnum.ADMIN);
//
//        return new UserInfoDto(username, isAdmin);
//    }
