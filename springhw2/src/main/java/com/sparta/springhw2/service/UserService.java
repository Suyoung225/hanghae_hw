package com.sparta.springhw2.service;


import com.sparta.springhw2.dto.TokenResponse;
import com.sparta.springhw2.dto.UserRequestDto;
import com.sparta.springhw2.dto.UserResponseDto;
import com.sparta.springhw2.dto.UserTokenResponseDto;
import com.sparta.springhw2.entity.Auth;
import com.sparta.springhw2.entity.User;
import com.sparta.springhw2.jwt.JwtTokenProvider;
import com.sparta.springhw2.repository.AuthRepository;
import com.sparta.springhw2.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserResponseDto register(UserRequestDto userRequestDto){
        Optional<User> found = userRepository.findByUsername(userRequestDto.getUsername());
        if(found.isPresent()){
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }
        String regExpId = "^[a-zA-Z0-9]{4,12}$";
        String regExpPw = "^[a-zA-Z0-9]{4,32}$";
        if(!Pattern.matches(regExpId, userRequestDto.getUsername())){
            throw new IllegalArgumentException("id 형식이 맞지 않습니다.");
        }else if(!Pattern.matches(regExpPw, userRequestDto.getPassword())){
            throw new IllegalArgumentException("비밀번호 형식이 맞지 않습니다.");
        }

        if(!userRequestDto.getPassword().equals(userRequestDto.getPasswordCheck())){
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }
        User user = User.builder()
                .username(userRequestDto.getUsername())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .build();
        userRepository.save(user);

        return new UserResponseDto(user);

    }

    @Transactional
    public UserTokenResponseDto doLogin(UserRequestDto userRequestDto, HttpServletResponse response) throws Exception {
        User user = userRepository.findByUsername(userRequestDto.getUsername())
                        .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword())) {
            throw new NoSuchElementException("사용자를 찾을 수 없습니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(user.getId(),user.getUsername());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId(),user.getUsername());

        Auth auth = Auth.builder()
                .user(user)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        authRepository.save(auth);



        response.addHeader("ACCESS_TOKEN",accessToken);
        response.addHeader("REFRESH_TOKEN",refreshToken);

        return new UserTokenResponseDto(user,accessToken,refreshToken);
    }

    @Transactional
    public TokenResponse issueAccessToken(HttpServletRequest request){
        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        System.out.println("accessToken = " + accessToken);
        System.out.println("refreshToken = " + refreshToken);
        //accessToken이 만료됐고 refreshToken이 맞으면 accessToken을 새로 발급(refreshToken의 내용을 통해서)
        if(!jwtTokenProvider.isValidAccessToken(accessToken)){  //클라이언트에서 토큰 재발급 api로의 요청을 확정해주면 이 조건문은 필요없다.
            System.out.println("Access 토큰 만료됨");
            if(jwtTokenProvider.isValidRefreshToken(refreshToken)){     //들어온 Refresh 토큰이 유효한지
                System.out.println("Refresh 토큰은 유효함");
                Claims claimsToken = jwtTokenProvider.getClaimsToken(refreshToken);
                Long userId = (Long)claimsToken.get("userId");
                String username = (String)claimsToken.get("username");
                Optional<User> user = userRepository.findByUsername(username);
                String tokenFromDB = authRepository.findByUserId(user.get().getId()).get().getRefreshToken();
                System.out.println("tokenFromDB = " + tokenFromDB);
                if(refreshToken.equals(tokenFromDB)) {   //DB의 refresh토큰과 지금들어온 토큰이 같은지 확인
                    System.out.println("Access 토큰 재발급 완료");
                    accessToken = jwtTokenProvider.createAccessToken(userId, username);
                }
                else{
                    //DB의 Refresh토큰과 들어온 Refresh토큰이 다르면 중간에 변조된 것임
                    System.out.println("Refresh Token Tampered");
                    throw new IllegalArgumentException ("Refresh 토큰이 다릅니다. 다시 로그인해주세요.");
                }
            }
            else{
                throw new IllegalArgumentException ("Refresh 토큰이 유효하지 않습니다. 다시 로그인 해주세요.");
            }
        }
        return TokenResponse.builder()
                .ACCESS_TOKEN(accessToken)
                .REFRESH_TOKEN(refreshToken)
                .build();
    }

}