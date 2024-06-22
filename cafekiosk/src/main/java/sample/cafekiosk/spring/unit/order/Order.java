package sample.cafekiosk.spring.unit.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sample.cafekiosk.spring.unit.beverage.Beverage;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@RequiredArgsConstructor
public class Order {

    private final LocalDateTime orderDateTime;
    private final List<Beverage> beverages;

}
