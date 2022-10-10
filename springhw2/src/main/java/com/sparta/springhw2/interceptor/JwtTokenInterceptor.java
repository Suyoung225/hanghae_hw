package com.sparta.springhw2.interceptor;

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
            throw new IllegalArgumentException("Token이 유효하지 않습니다.");
        }
        // !accessToken.equals("") && !refreshToken.equals("") &&
        /* response.setStatus(401);
        response.setHeader("ACCESS_TOKEN", accessToken);
        response.setHeader("REFRESH_TOKEN", refreshToken);
        response.setHeader("msg", "Check the tokens.");
        return false;*/
    }

}
