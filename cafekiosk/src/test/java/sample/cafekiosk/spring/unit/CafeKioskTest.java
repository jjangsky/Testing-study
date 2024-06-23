package sample.cafekiosk.spring.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.spring.unit.beverage.Americano;
import sample.cafekiosk.spring.unit.beverage.Latte;
import sample.cafekiosk.spring.unit.order.Order;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class CafeKioskTest {

    @Test
    void add_manual_test() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        System.out.println(">>> 담긴 음료 수 :" + cafeKiosk.getBeverages().size());
        System.out.println(">>> 담긴 음료 :" + cafeKiosk.getBeverages().get(0).getName());

        /**
         * 이러한 방식의 테스트 코드 문제점
         * 1. 테스트 코드를 작성하였지만 최종 단계에서 사람의 확인이 필요(콘솔에 결과 출력)
         * 2. 타인이 보았을 때, 어떤 것을 검증해야 할 지 모름(틀린 상황인지도 확인 불가)
         */
    }

    /**
     * DisplayName 어노테이션
     * JUnit5에서 사용할 수 있는 어노테이션으로서 값을 넣어서
     * 해당 메소드가 어떤 역할을 하는지 표시할 수 있다.
     * 만약, 그 이하 버전이라면 메소드 명에 한글을 넣어서 작성할 수 도 있다.
     * ex) void 음료_1개_추가_테스트
     *
     * 또한, 기본 빌드 설정이 Gradle로 되어 있지만
     * Intellij로 변경하면 DisplayName으로 테스트 코드가 돌아갈때 이름 확인이 가능
     * (Setting - Build)
     *
     */
//    @DisplayName("음료 1개 추가 테스트")

    @DisplayName("음료를 1개 추가하면 주문 목록에 남긴다.")
    @Test
    void add() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        assertThat(cafeKiosk.getBeverages()).hasSize(1);
        assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    void addServeralBeverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano, 2);

        // isEqualTo 메소드는 객체 비교도 가능함
        assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
        assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);
    }

    @Test
    void addZeroBeverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        // 예외가 발생될 테스트 코드는 `assertThatThrownBy` 사용
        // isInstanceOf 메소드로 오류가 일치하는지 확인하고 hasMessage로 오류 내용이 일치하는지 확인
        // 만약 오류 내용이 일치하지 않으면 테스트 코드 실패로 이어짐
        assertThatThrownBy(() -> cafeKiosk.add(americano, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("음료는 1잔 이상 주문할 수 있습니다.");

    }

    @Test
    void remove() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);
        assertThat(cafeKiosk.getBeverages()).hasSize(1);

        cafeKiosk.remove(americano);
        assertThat(cafeKiosk.getBeverages()).isEmpty();

        cafeKiosk.clear();
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @Test
    void createOrder(){
        /**
         * 주문 시간 확인 후, 주문 목록 생성
         * 하지만, 해당 메소드는 항상 성공하는 case가 아니다.
         * 현재 주문 시간에 따라 성공 여부가 나뉘어짐
         * (정상적인 코드여도 주문 시간에 따라 테스트 실패할 수 있음)
         */

        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        Order order = cafeKiosk.createOrder();
        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");

    }

    @Test
    void createOrderWithCurrentTime(){
        // 정상 오픈 시간에 주문한 case
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        Order order = cafeKiosk.createOrder(LocalDateTime.of(2013, 1, 17, 10, 0));
        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");

    }

    @Test
    void createOrderWithOutsideTime(){
        // 정상 오픈 시간에 주문한 case가 아닌 경우
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        assertThatThrownBy(() -> cafeKiosk.createOrder(LocalDateTime.of(2023, 1, 17, 9 ,59)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 시간이 아닙니다. 관리자에게 문의하세요");

    }


    /**
     * TDD 방식
     */

    @Test
    void calculateTotalPrice(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano);
        cafeKiosk.add(latte);

        int totalPrice = cafeKiosk.calculateTotalPrice();
        assertThat(totalPrice).isEqualTo(8500);

    }

}