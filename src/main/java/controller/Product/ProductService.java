package controller.Product;

import dto.OrderDetails;
import dto.Product;

import java.util.List;

public interface ProductService {
    boolean addProduct(Product product);
    boolean deleteProduct(String id);
    boolean updateProduct(Product product);
    Product serachProduct(String name);
    String getLastProID();
    boolean updateStock(List<OrderDetails> orderDetails);
}
