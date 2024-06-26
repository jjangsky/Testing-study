package sample.cafekiosk.spring.api.controller.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sample.cafekiosk.spring.api.ApiResponse;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.product.ProductService;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PostMapping("/api/v1/products/new")
    // @Valid를 추가하여 Request에 대한 값 검증
    public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest request){
       return  ApiResponse.ok(productService.createProduct(request));
    }

    @GetMapping("/api/v1/prodcuts/selling")
    public ApiResponse<List<ProductResponse>> getSellingProducts(){
        return ApiResponse.ok(productService.getSellingProducts());
    }


}
