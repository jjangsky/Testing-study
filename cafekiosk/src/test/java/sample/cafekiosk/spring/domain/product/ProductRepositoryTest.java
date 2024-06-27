package sample.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.*;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;


/**
 * SpringBootTest 와 DataJPATest
 * 둘 다 비슷한 환경의 테스트 이지만 차이점을 보자면
 * DataJpaTest가 속도가 더 빠르다(JPA 관련 기능만 갖고 있음)
 *
 */
@ActiveProfiles("test") // yml의 버전을 설정할 수 있음
@SpringBootTest
class ProductRepositoryTest {

    /**
     * Repository 테스트는 단위 테스트에 가까움
     * 계층 자체게 데이터베이스 Access 로직만 갖고 있어서
     */

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
    @Test
    void findAllBySellingStatusIn(){
        // given
        Product product1 = Product.builder()
                .productNumber("001")
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .name("아메리카노")
                .price(4000)
                .build();
        Product product2 = Product.builder()
                .productNumber("002")
                .type(HANDMADE)
                .sellingStatus(HOLD)
                .name("카페라떼")
                .price(4500)
                .build();
        Product product3 = Product.builder()
                .productNumber("003")
                .type(HANDMADE)
                .sellingStatus(STOP_SELLING)
                .name("팥빙수")
                .price(7000)
                .build();
        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> products = productRepository.findAllBySellingStatusIn(List.of(SELLING, HOLD));

        // then
        /**
         * List 형태의 대한 검증
         * 1. 사이즈 체크 `.hasSize`
         * 2. extracting -> 검증하고자 하는 필드만 추출
         * 3. containsExactlyInAnyOrder -> 순서 상관 없이 확인
         * (contatinsExactly 는 순서 검증도 함)
         */
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", SELLING),
                        tuple("002", "카페라떼", HOLD)
                );


    }

}