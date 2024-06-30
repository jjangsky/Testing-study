package sample.cafekiosk.spring.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * SELECT *
     *   FROM PRODUCT
     *  WHERE selling_type in ('SELLING', 'HOLD');
     */
    List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> sellingTypes);
    /**
     * Q. 네이밍으로 쿼리문으로 명확하게 날라가는데 왜 테스트가 필요한가?
     *
     * 지금은 간단한 쿼리이지만 where의 조건이 많아진다거나 파라미터 설정 이슈, 동적쿼리 여부
     * 및 QueryDsl을 실행할 수 있고 심지어 JPA 환경이 아닐수 도 있음
     * 그리고 미래에 어떤 형태로 변경될 지 모르기 때문에 보장을 해줘야함
     */

    List<Product> findAllByProductNumberIn(List<String> productNumbers);

    // 가장 마지막에 등록된 상품의 코드 찾아오기
    @Query(value = "select p.product_number from Product p order by id desc limit 1", nativeQuery = true)
    String findLatestProduct();
}
