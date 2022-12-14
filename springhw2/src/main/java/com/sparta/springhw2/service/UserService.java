package com.sparta.springhw2.service;


import com.sparta.springhw2.dto.TokenResponse;
import com.sparta.springhw2.dto.UserRequestDto;
import com.sparta.springhw2.dto.UserResponseDto;
import com.sparta.springhw2.dto.UserTokenResponseDto;
import com.sparta.springhw2.entity.Auth;
import com.sparta.springhw2.entity.User;
import com.sparta.springhw2.exception.ErrorCode;
import com.sparta.springhw2.exception.RequestException;
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

        String accessToken = jwtTokenProvider.createAccessToken(user.getId(),user.getUsername());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId(),user.getUsername());

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
                int userId = (int)claimsToken.get("userId");
                String username = (String)claimsToken.get("username");
                Optional<User> user = userRepository.findByUsername(username);
                Auth auth = authRepository.findByUserId((long) userId);
                String tokenFromDB = authRepository.findByUserId(user.get().getId()).getRefreshToken();
                System.out.println("tokenFromDB = " + tokenFromDB);
                if(refreshToken.equals(tokenFromDB)) {   //DB??? refresh????????? ??????????????? ????????? ????????? ??????
                    System.out.println("Access ?????? ????????? ??????");
                    accessToken = jwtTokenProvider.createAccessToken((long)userId, username);
                    auth.accessUpdate(accessToken);
                    authRepository.save(auth);
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
                .ACCESS_TOKEN(accessToken)
                .REFRESH_TOKEN(refreshToken)
                .build();
    }

}
