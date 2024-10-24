package controller.Product;

import model.Product;

public interface ProductService {
    boolean addProduct(Product product);
    boolean deleteProduct(String id);
    boolean updateProduct(Product product);
    Product serachProduct(String name);
    String getLastProID();
}
