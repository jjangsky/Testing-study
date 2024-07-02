package sample.cafekiosk.spring.domain.order;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.BaseEntity;
import sample.cafekiosk.spring.domain.orderproduct.OrderProduct;
import sample.cafekiosk.spring.domain.product.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED) // TODO: 이거 확인
@Table(name= "orders") // order 자체가 예약어라 사용 지양
@Entity
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private int totalPrice;

    private LocalDateTime registeredDateTime;

    /**
     * Product 같은 경우는 상품이 주문 정보를 알 필요 없어
     * 엔티티에 필드 설정을 안넣었지만
     * Order 같은 경우는 어떤 상품이 담겼는지 확인이 필요하여
     * 엔티티 필드 설정 -> 연관관계 주인은 order로 설정
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // TODO cascade 확인
    private List<OrderProduct> orderProducts = new ArrayList<>();


    // 단위테스트 작성 필요
    @Builder
    private Order(List<Product> products, OrderStatus orderStatus, LocalDateTime registeredDateTime) {
        this.orderStatus = orderStatus;
        this.totalPrice = calculateTotalPrice(products);
        this.registeredDateTime = registeredDateTime;
        this.orderProducts = products.stream()
                .map(product -> new OrderProduct(this, product))
                .collect(Collectors.toList());
        /**
         * 여기서 주의할 것이 LocalDateTime 같은 경우 시간 자체가 외부 요소라
         * now() 메소드를 사용하기 보다는 시간을 Parma으로 받아서 사용
         * -> 테스트 하기 용이하다.
         */

    }

    public static Order create(List<Product> products, LocalDateTime registeredDateTime) {
        return Order.builder()
                .orderStatus(OrderStatus.INIT)
                .products(products)
                .registeredDateTime(registeredDateTime)
                .build();
    }

    // 총 합을 구하는 메소드
    private int calculateTotalPrice(List<Product> products){
        return products.stream()
                .mapToInt(Product::getPrice)
                .sum();
    }



}
