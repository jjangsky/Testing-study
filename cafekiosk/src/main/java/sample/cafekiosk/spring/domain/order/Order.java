package sample.cafekiosk.spring.domain.order;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.BaseEntity;
import sample.cafekiosk.spring.domain.orderproduct.OrderProduct;

import java.time.LocalDateTime;
import java.util.List;

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
    private List<OrderProduct> orderProducts;
}
