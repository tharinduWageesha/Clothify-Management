package entity;

import dto.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@ToString
public class OrderEntity {
    private String order_ID;
    private LocalDate date;
    private double discount;
    private double cost;
    private LocalTime time;
    private List<OrderDetails> orderDetails;

    public OrderEntity(String order_ID, LocalDate date, double discount, double cost, LocalTime time) {
        this.order_ID = order_ID;
        this.date = date;
        this.discount = discount;
        this.cost = cost;
        this.time = time;
    }

}
