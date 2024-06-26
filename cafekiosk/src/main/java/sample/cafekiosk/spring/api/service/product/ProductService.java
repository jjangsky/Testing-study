package sample.cafekiosk.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingType;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProducts(){
        List<Product> prodcuts = productRepository.findAllBySellingTypeIn(ProductSellingType.forDisplay());

        /**
         * of 메소드를 사용하여 Entity 객체를 Dto 객체로 변환
         * of 메소드는 내부에 Builder 사용
         */

        return prodcuts.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());



    }

}
