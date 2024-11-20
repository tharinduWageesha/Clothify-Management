package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Data
@NoArgsConstructor
public class OrderDetails {
    private  String orderID;
    private String productID;
    private int qty;
}
