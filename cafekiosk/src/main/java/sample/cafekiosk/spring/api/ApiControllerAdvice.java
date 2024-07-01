package sample.cafekiosk.spring.api;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


// @ControllerAdvice 는 기존 SpringMVC에서 사용하는것 이고
// @RestControllerAdvice는 응답값을 Json 타입으로 변환시켜서 반환
@RestControllerAdvice
public class ApiControllerAdvice {

    // 예외 처리
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 이거 확인
    @ExceptionHandler(BindException.class)
    public ApiResponse<Object> bindException(BindException e){
        return ApiResponse.of(
                HttpStatus.BAD_REQUEST, // 상태 코드
                e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), // 오류 메세지
                null
                /**
                 *  추가적으로 오류 메세지 반환 시 Default로 잡히는건
                 *  Request로 받아온 필드의 validate(ex: @NotNull ...) 어노테이션에
                 *  message 필드로 지정된 문자열이 반환됨
                 */
        );
    }


}
