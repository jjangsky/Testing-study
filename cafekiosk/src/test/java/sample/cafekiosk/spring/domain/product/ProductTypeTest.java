package sample.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTypeTest {

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockType(){

        // given
        ProductType givenType = ProductType.HANDMADE;

        // when
        boolean result = ProductType.containsStockType(givenType);

        // then
        assertThat(result).isFalse();

    }

    /**
     * `@ParameterizedTest` 를 사용하여 테스트 시, 여러가지 값을 넣어 테스트를 할 수 있다.
     * 값을 넣는 방식은 다양하다
     * `@CsvSource` 로 넣을 수 도 있다
     * -> 해당 예시에서는 파라미터 인자로 2가지를 넣었는데
     *  상품의 타입과 그러 인한 예상 결과값을 넣음
     */
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @CsvSource({"HANDMADE,false","BOTTLE,true","BAKERY,true"})
    @ParameterizedTest
    void containsStockType4(ProductType productType, boolean expected) {
        // when
        boolean result = ProductType.containsStockType(productType);

        // then
        assertThat(result).isEqualTo(expected);
    }

    /**
     * CsvSource 왜에도 `@MethodSource 로 파라미터를 넣을 수 있음
     * -> 별도의 메소드로 분리처리
     * (메소드 이름으로 인식 )
     */
    private static Stream<Arguments> provideProductTypesForCheckingStockType() {
        return Stream.of(
                Arguments.of(ProductType.HANDMADE, false),
                Arguments.of(ProductType.BOTTLE, true),
                Arguments.of(ProductType.BAKERY, true)
        );
    }

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @MethodSource("provideProductTypesForCheckingStockType")
    @ParameterizedTest
    void containsStockType5(ProductType productType, boolean expected) {
        // when
        boolean result = ProductType.containsStockType(productType);

        // then
        assertThat(result).isEqualTo(expected);
    }

}