package sample.cafekiosk.spring.api.service.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sample.cafekiosk.spring.IntegrationTestSupport;
import sample.cafekiosk.spring.client.mail.MailsendClient;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.order.OrderStatus;
import sample.cafekiosk.spring.domain.orderproduct.OrderProduct;
import sample.cafekiosk.spring.domain.orderproduct.OrderProductRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;


class OrderStatisticsServiceTest extends IntegrationTestSupport {

    @Autowired
    private OrderStatisticsService orderStatisticsService;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MailSendHistoryRepository mailSendHistoryRepository;

//    @MockBean // component로 선언되어 빈 설정 가능
//    private MailsendClient mailsendClient;
    /**
     * 이전의 Mcok 테스트에서는 Service Layer를 단순하게 Mocking 처리만 하였지만
     * Mock 이라는 것이 가짜 객체를 넣어놓고 이 객체가 어떻게 행동할 수 있도록
     * 설정하고 그에 대한 결과를 리턴하는것 까지 지정할 수 있다.
     */


    @AfterEach
    void tearDown(){
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        mailSendHistoryRepository.deleteAllInBatch();
    }

    @DisplayName("결제완료 주문들을 조회하여 매출 통계 메일을 전송한다.")
    @Test
    void sendOrderStatisticsMail(){
        // given
        LocalDateTime now = LocalDateTime.of(2024, 3, 5, 10, 0);

        Product product1 = createProdcut(HANDMADE, "001", 1000);
        Product product2 = createProdcut(HANDMADE, "002", 3000);
        Product product3 = createProdcut(HANDMADE, "003", 5000);
        List<Product> products = List.of(product1, product2, product3);
        productRepository.saveAll(products);

        Order order1 = createPaymentCompletedOrder(LocalDateTime.of(2024, 7, 2, 23 ,59), products);
        Order order2 = createPaymentCompletedOrder(now, products);
        Order order3 = createPaymentCompletedOrder(LocalDateTime.of(2024, 3, 5, 23 ,59), products);
        Order order4 = createPaymentCompletedOrder(LocalDateTime.of(2024, 3, 6, 0 ,0), products);

        /**
         * 가짜 객체를 선언 후,
         * 여기서는 `sendEmail` Method 인데 사실 어떤 값이 인자로 갈지 모르니까
         * any라는 타입을 이용하여 String Type의 값을 인자로 넣었다 라고 설정하고
         * 그 이후 true 값을 반환한다고 설정
         * (stubing)
         */
        Mockito.when(mailSendClient.sendEmail(any(String.class), any(String.class), any(String.class), any(String.class)))
                .thenReturn(true);

        // when
        boolean result = orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2024, 3, 5), "test@test.com");

        // then
        // 메일 발송 검증
        assertThat(result).isTrue();

        // 메일 내역 검증
        List<MailSendHistory> histories = mailSendHistoryRepository.findAll();
        assertThat(histories).hasSize(1)
                .extracting("content")
                .contains("총 매출 합계는 18000원 입니다.");


    }

    private Order createPaymentCompletedOrder(LocalDateTime now, List<Product> products) {
        Order order = Order.builder()
                         .products(products)
                         .orderStatus(OrderStatus.PAYMENT_COMPLETED)
                         .registeredDateTime(now)
                         .build();
        return orderRepository.save(order);
    }

    private Product createProdcut(ProductType type, String productNumber, int price){
        // given 시점에 상품 등록하는게 너무 길어서 사용
        return Product.builder()
                .type(type)
                .productNumber(productNumber)
                .price(price)
                .sellingStatus(SELLING)
                .name("테스트이름")
                .build();

    }

}