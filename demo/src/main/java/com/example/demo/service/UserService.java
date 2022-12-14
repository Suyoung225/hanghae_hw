package com.example.demo.service;

import com.example.demo.dto.TokenResponse;
import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.dto.UserTokenResponseDto;
import com.example.demo.entity.Auth;
import com.example.demo.entity.User;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.RequestException;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.repository.AuthRepository;
import com.example.demo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.example.demo.jwt.JwtTokenProvider.AUTHORIZATION_HEADER;
import static com.example.demo.jwt.JwtTokenProvider.BEARER_TYPE;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public UserResponseDto register(UserRequestDto userRequestDto){
        Optional<User> found = userRepository.findByUsername(userRequestDto.getUsername());
        if(found.isPresent()){
            throw new RequestException(ErrorCode.LOGINID_DUPLICATION_409);
        }
        String regExpId = "^[a-zA-Z0-9]{4,12}$";
        String regExpPw = "^[a-zA-Z0-9]{4,32}$";
        if(!Pattern.matches(regExpId, userRequestDto.getUsername())){
            throw new RequestException(ErrorCode.ID_NOT_ACCEPTABLE_406);
        }else if(!Pattern.matches(regExpPw, userRequestDto.getPassword())){
            throw new RequestException(ErrorCode.PASSWORD_NOT_ACCEPTABLE_406);
        }

        if(!userRequestDto.getPassword().equals(userRequestDto.getPasswordCheck())){
            throw new RequestException(ErrorCode.PASSWORD_NOT_MATCH_406);
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
                        .orElseThrow(() -> new RequestException(ErrorCode.USER_NOT_FOUND_404));

        if (!passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword())) {
            throw new RequestException(ErrorCode.USER_NOT_FOUND_404);
        }
        String accessToken = jwtTokenProvider.createAccessToken(userRequestDto.getUsername());
        String refreshToken = jwtTokenProvider.createRefreshToken(userRequestDto.getUsername());

        Optional <Auth> found = Optional.ofNullable(authRepository.findByUserId(user.getId()));
        if(found.isEmpty()){
            Auth auth = Auth.builder()
                    .user(user)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
            authRepository.save(auth);
            System.out.println("??? ?????????");
        }else{
            Auth auth = authRepository.findByUserId(user.getId());
            auth.accessUpdate(accessToken);
            auth.refreshUpdate(refreshToken);
            authRepository.save(auth);
        }

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
        //accessToken??? ???????????? refreshToken??? ????????? accessToken??? ?????? ??????(refreshToken??? ????????? ?????????)
        if(!jwtTokenProvider.isValidAccessToken(accessToken)){  //????????????????????? ?????? ????????? api?????? ????????? ??????????????? ??? ???????????? ????????????.
            System.out.println("Access ?????? ?????????");
            if(jwtTokenProvider.isValidRefreshToken(refreshToken)){     //????????? Refresh ????????? ????????????
                System.out.println("Refresh ????????? ?????????");
                Claims claimsToken = jwtTokenProvider.getClaimsToken(refreshToken);
                String username = claimsToken.getSubject();
                Optional<User> user = userRepository.findByUsername(username);
                Auth auth = authRepository.findByUserId(user.get().getId());
                String tokenFromDB = auth.getRefreshToken().substring(7);
                System.out.println("tokenFromDB = " + tokenFromDB);
                if(refreshToken.equals(tokenFromDB)) {   //DB??? refresh????????? ??????????????? ????????? ????????? ??????
                    String at = jwtTokenProvider.createAccessToken(username);
                    auth.accessUpdate(at);
                    authRepository.save(auth);
                    System.out.println("Access ?????? ????????? ??????");
                    return TokenResponse.builder()
                            .ACCESS_TOKEN(at)
                            .REFRESH_TOKEN(BEARER_TYPE+refreshToken)
                            .build();
                }
                else{
                    //DB??? Refresh????????? ????????? Refresh????????? ????????? ????????? ????????? ??????
                    System.out.println("Refresh Token Tampered");
                    throw new RequestException(ErrorCode.JWT_BAD_TOKEN_401);
                }
            }
            else{
                throw new RequestException(ErrorCode.JWT_BAD_TOKEN_401);
            }
        }
        return TokenResponse.builder()
                .ACCESS_TOKEN(request.getHeader(AUTHORIZATION_HEADER))
                .REFRESH_TOKEN(request.getHeader("REFRESH_TOKEN"))
                .build();
    }

}
