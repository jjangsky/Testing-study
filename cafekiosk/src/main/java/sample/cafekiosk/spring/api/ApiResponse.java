package sample.cafekiosk.spring.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;

@Getter
public class ApiResponse<T> {
    // 공통 응답 처리

    private int code;
    private HttpStatus status;
    private String message;
    private T data;

    public ApiResponse(HttpStatus status, String message, T data) {
        this.code = status.value();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // http 상태값에 따른 반환
    public static <T> ApiResponse<T> of(HttpStatus httpStatus, T data) {
        return  of(httpStatus, httpStatus.name(), data);
    }

    // 메세지를 따로 받는 경우
    public static <T> ApiResponse<T> of(HttpStatus httpSatus, String message,  T data) {
        return new ApiResponse<>(httpSatus, message, data);
    }

    // 200 상태값에 따른 반환
    public static <T> ApiResponse<T> ok(T data) {
        return of(HttpStatus.OK, data);
    }
}
