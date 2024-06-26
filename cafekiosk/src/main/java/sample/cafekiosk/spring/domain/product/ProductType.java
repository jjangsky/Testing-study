package sample.cafekiosk.spring.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductType {

    HANDMADE("제조 음룍"),
    BOTTLE("병 음룍"),
    BAKERY("베이커리");

    private final String text;
}
