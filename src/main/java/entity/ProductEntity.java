package entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class ProductEntity {
    private String Product_ID;
    private String Product_Name;
    private String Supplier_ID;
    private Double Price;
    private int Qty;

    public ProductEntity(String product_ID, String product_Name, Double price, int qty, String supplier_ID) {
        Product_ID = product_ID;
        Product_Name = product_Name;
        Price = price;
        Qty = qty;
        Supplier_ID = supplier_ID;
    }
}
