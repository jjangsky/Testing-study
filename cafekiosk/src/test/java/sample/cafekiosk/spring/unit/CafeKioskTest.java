package sample.cafekiosk.spring.unit;

import org.junit.jupiter.api.Test;
import sample.cafekiosk.spring.unit.beverage.Americano;

import static org.assertj.core.api.Assertions.assertThat;


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