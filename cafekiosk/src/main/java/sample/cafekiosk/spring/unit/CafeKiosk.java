package sample.cafekiosk.spring.unit;

import lombok.Getter;
import sample.cafekiosk.spring.unit.beverage.Beverage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import sample.cafekiosk.spring.unit.order.Order;

@Getter
public class CafeKiosk {

    private final List<Beverage> beverages = new ArrayList<>();

    public void add(Beverage beverage) {
        // 인터페이스를 매개변수로 받아 아메리카노, 라떼 둘 다 받을 수 있음
        beverages.add(beverage);
    }

    public void remove(Beverage beverage){
        beverages.remove(beverage);
    }

    public void clear(){
        beverages.clear();
    }

    public int calculateTotalPrice() {
        int totalPrice = 0;
        for(Beverage beverage : beverages){
            totalPrice += beverage.getPrice();
        }
        return totalPrice;
    }

    public Order createOrder(){
        return new Order(LocalDateTime.now(), beverages);
    }
}
