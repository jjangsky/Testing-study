package sample.cafekiosk.spring.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;

class OrderTest {
    /**
     * Entity Class의 메소드에 대한 단위 테스트
     */
    @DisplayName("주문 생성 시 상품 리스트에서 주문의 총 금액을 계산한다.")
    @Test
    void calculateTotalPrice(){
        // given
        List<Product> products = List.of(
                createProdcut("001", 1000),
                createProdcut("002", 2000)
        );
        // when
        Order order = Order.create(products, LocalDateTime.now());
        // then
        assertThat(order.getTotalPrice()).isEqualTo(3000);
    }

    @DisplayName("주문 생성 시 초기 상태는 init 이다.")
    @Test
    void init(){
        // given
        List<Product> products = List.of(
                createProdcut("001", 1000),
                createProdcut("002", 2000)
        );
        // when
        Order order = Order.create(products, LocalDateTime.now());
        // then
        assertThat(order.getOrderStatus()).isEqualByComparingTo(OrderStatus.INIT);
        // Enum Class 비교 시 isEqualByComparingTo 메서드 사용하여 Class의 값 자체를 비교
    }

    @DisplayName("주문 생성 시 주문 등록 시간을 기록한다.")
    @Test
    void registeredDateTime(){
        // given
        LocalDateTime registeredDateTime = LocalDateTime.now();
        List<Product> products = List.of(
                createProdcut("001", 1000),
                createProdcut("002", 2000)
        );
        // when
        Order order = Order.create(products, registeredDateTime);
        // then
        assertThat(order.getRegisteredDateTime()).isEqualTo(registeredDateTime);
    }

    private Product createProdcut(String productNumber, int price){
        // given 시점에 상품 등록하는게 너무 길어서 사용
        return Product.builder()
                .productNumber(productNumber)
                .price(price)
                .sellingStatus(SELLING)
                .name("테스트이름")
                .build();

    }

}