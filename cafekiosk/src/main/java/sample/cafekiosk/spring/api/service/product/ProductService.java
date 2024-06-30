package sample.cafekiosk.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;

import java.util.List;
import java.util.stream.Collectors;

/**
 * readOnly = true -> 읽기 전용
 * CRUD에서 CUD 작업이 동작하지 않음, read only
 * JPA : CUD 스냅샷 저장, 변경 감지 X
 *
 * CQRS - Command / Read
 * 보통 서비스에서 읽기 작업이 압도적으로 많다 8:2 정도
 * 그래서 읽기와 쓰기에 대한 책임을 분리하여 서로 연관이 없도록 만들자
 * 만약 조회가 활발하여 시스템에 부화가 될 경우 쓰기 작업이 동작하지 않으면 장애가 된다.
 * 물론, 반대 상황도 마찬가지다.
 * 그 첫 번째 과정이 트랙잭션의 read only ture 작성
 * MYSQL에서 read용 DB와 Write 용 DB를 나눌 수 있는데
 * 이러한 트랜잭셔널 read only 여부에 따라 나눠서 등록할 수 있음
 * -> DB Endpoint 분리 가능
 *
 * 결론적으로 Class 단위에는 read only true를 걸고
 * write 작업이 일어나는 메소드에 트랜잭셔널 어노테이션 부여
 */

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Business Layer
     * 비즈니스 로직을 구현하는 역할
     * Persistence Layer와 상호작용(Data를 읽고 쓰는 행위)를 통해 비즈니스 로직을 전개시킨다.
     * 트랜잭션을 보장해야한다.
     */


    /**
     * 상품 등록시 동시성 이슈 발생 가능성 있음
     * 1. 상품 번호 필드에 유니크 제약조건 추가
     * 2. 상품번호를 UUID로 만들어서 정책적으로 해결
     */
    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {
        // productNumber
        // 001 002 003 004
        // DB에서 마지막 저장된 Product의 상품 번호를 읽어와서 +1 처리
        // ex) 마지막 상품번호가 009 이면 010 으로 저장
        String nextProductNumber = createNextProductNumber();

        Product product = request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);


        return ProductResponse.of(savedProduct);

    }

    // 다음 상품 번호 생성
    private String createNextProductNumber(){
        String latestProductNumber = productRepository.findLatestProduct();
        if(latestProductNumber == null){
            return "001";
        }

        Integer latestProductNumberInt = Integer.valueOf(latestProductNumber);
        int nextProductNumberInt = latestProductNumberInt + 1;

        // 9 -> 009, 10 -> 010
        return String.format("%03d", nextProductNumberInt);
    }


    public List<ProductResponse> getSellingProducts(){
        List<Product> prodcuts = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        /**
         * of 메소드를 사용하여 Entity 객체를 Dto 객체로 변환
         * of 메소드는 내부에 Builder 사용
         */

        return prodcuts.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());



    }


}
