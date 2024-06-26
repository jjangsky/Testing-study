package sample.cafekiosk.spring.domain.product;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product extends BaseEntity {
    /**
     * 보통 Entity를 생성할 때 BaseEntity를 상속받아
     * 엔티티가 생성된 시간, 수정 시간 등을 체크할 수 있음
     *
     * mainApplication 에서 @EnableJpaAuditing 어노테이션 추가 필요함
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String productNumber;

    @Enumerated(EnumType.STRING)
    private ProductType type;

    @Enumerated(EnumType.STRING)
    private ProductSellingType sellingType;

    private String name;

    private int price;
}
