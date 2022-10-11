package com.sparta.springhw2.interceptor;

import com.sparta.springhw2.exception.ErrorCode;
import com.sparta.springhw2.exception.RequestException;
import com.sparta.springhw2.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class JwtTokenInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        System.out.println("JwtToken 호출");
        String accessToken = request.getHeader("ACCESS_TOKEN");
        System.out.println("AccessToken:" + accessToken);
        String refreshToken = request.getHeader("REFRESH_TOKEN");
        System.out.println("RefreshToken:" + refreshToken);

        if (jwtTokenProvider.isValidAccessToken(accessToken) && jwtTokenProvider.isValidRefreshToken(refreshToken)) {
            return true;
        }else{
            throw new RequestException(ErrorCode.JWT_BAD_TOKEN_401);
        }
    }

}
