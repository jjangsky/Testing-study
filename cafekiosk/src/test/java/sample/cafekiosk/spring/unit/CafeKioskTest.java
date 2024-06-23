package sample.cafekiosk.spring.unit;

import org.junit.jupiter.api.Test;
import sample.cafekiosk.spring.unit.beverage.Americano;

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

}