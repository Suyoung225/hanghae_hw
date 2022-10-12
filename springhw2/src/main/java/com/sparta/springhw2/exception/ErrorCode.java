package com.sparta.springhw2.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 공통
    COMMON_BAD_REQUEST_400(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    COMMON_INTERNAL_ERROR_500(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류가 발생하였습니다."),

    // JWT 관련
    JWT_BAD_TOKEN_401(HttpStatus.UNAUTHORIZED, "Token이 유효하지 않습니다."),

    // 회원가입 관련
    LOGINID_DUPLICATION_409(HttpStatus.CONFLICT, "이미 가입된 회원입니다."),


    // 로그인 관련
    USER_NOT_FOUND_404(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    PASSWORD_NOT_ACCEPTABLE_406(HttpStatus.NOT_ACCEPTABLE, "비밀번호 형식이 맞지 않습니다."),
    PASSWORD_NOT_MATCH_406(HttpStatus.NOT_ACCEPTABLE, "비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    ID_NOT_ACCEPTABLE_406(HttpStatus.NOT_ACCEPTABLE, "아이디 형식이 맞지 않습니다."),

    // NOT FOUND
    POSTING_ID_NOT_FOUND_404(HttpStatus.NOT_FOUND, "게시글 id를 찾을 수 없습니다."),
    COMMENT_ID_NOT_FOUND_404(HttpStatus.NOT_FOUND, "댓글 id를 찾을 수 없습니다."),

    // 수정, 삭제 권한
    EDIT_UNAUTHORIZED_403(HttpStatus.FORBIDDEN,"작성자만 수정할 수 있습니다."),
    DELETE_UNAUTHORIZED_403(HttpStatus.FORBIDDEN,"작성자만 삭제할 수 있습니다."),


    /**
     * 해당 주석 위로 enum 코드 추가 바랍니다.
     * 코드 추가시 간편하게 진행하기 위해 생성한 미사용 코드입니다. 사용하지마세요.
     * 생성이유 : enum 마지막 요소에 ; 을 입력해야하기에, 끝부분에 추가하게 될 경우 ; 을 재입력해야함
     */
    DO_NOT_USED(null, null);


    private final HttpStatus httpStatus;
    private final String message;
}