package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@AllArgsConstructor
@Data
@NoArgsConstructor
@ToString
public class CartTM {
    private String productID;
    private String proname;
    private int qty;
    private double unitPrice;
    private double total;
}
