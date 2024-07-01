package sample.cafekiosk.spring.api.controller.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.product.ProductService;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Controller 계층에서 테스트할 수 있도록 관련 기능만 모여있음
 */
@WebMvcTest(controllers = ProductControllerTest.class)
class ProductControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    /**
     * site.mockito.org 참고
     *
     * MockBean을 사용하면 이미 Bean으로 등록되어 있는 ProductService를
     * 대신하여 컨테이너에 Bean 형태로 등록할 수 있다.
     */
    @MockBean
    private ProductService productService;

    @DisplayName("신규 상품을 등록한다.")
    @Test
    void createProduct() throws Exception {
        // given

        // 파라미터 생성
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        /**
         * 1. perform으로 요청을 보내는 역할을 함
         * 2. 화면에서 Request를 받아왔다고 가정할 때 Post 요청인 경우 객체 형식이면 직렬화 과정이 필요함
         */
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/new")
                .content(objectMapper.writeValueAsString(request)) // 파라미터 객체 직렬화
                .contentType(MediaType.APPLICATION_JSON) // 타입은 JSON
        )
                // 검증
                .andDo(MockMvcResultHandlers.print()) // 상세 로그 확인
                .andExpect(MockMvcResultMatchers.status().isOk()); // 결과 비교


    }

}
