package sample.cafekiosk.spring.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum ProductSellingType {

    SELLING("판매중"),
    HOLD("판매보류"),
    STOP_SEELING("판매중지");

    private final String text;


    // 판매중, 판매보류 상태만 반환
    public static List<ProductSellingType> forDisplay() {
        return List.of(SELLING, HOLD);
    }
}
