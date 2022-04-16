package com.sparta.hanghae99clone.security;

import com.google.gson.Gson;
import com.sparta.hanghae99clone.security.jwt.JwtTokenUtils;
import com.sparta.hanghae99clone.dto.response.LoginResponseDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FormLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "BEARER";

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) throws IOException {
        final UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        // Token 생성
        final String token = JwtTokenUtils.generateJwtToken(userDetails);
        response.addHeader(AUTH_HEADER, TOKEN_TYPE + " " + token);
        System.out.println(token);
        LoginResponseDto dto=new LoginResponseDto();
        dto.setUsername(userDetails.getUsername());
        String resp= new Gson().toJson(dto);
        response.getWriter().write(resp);
    }

}
