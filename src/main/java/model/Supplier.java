package model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class Supplier {
    private String Supplier_ID;
    private String Supplier_Name;
    private String Company;
    private String Email;
    private String Product_ID;

    public Supplier(String supplier_ID, String supplier_Name, String company, String email, String product_ID) {
        Supplier_ID = supplier_ID;
        Supplier_Name = supplier_Name;
        Company = company;
        Email = email;
        Product_ID = product_ID;
    }
}
