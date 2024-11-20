package service.custom.impl;

import dto.OrderDetails;
import dto.Product;
import entity.OrderEntity;
import entity.ProductEntity;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.OrderDao;
import repository.custom.ProductDso;
import service.custom.ProductService;
import utilDBOPT.DaoType;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    @Override
    public boolean addProduct(Product product) {
        ProductEntity entity = new ModelMapper().map(product, ProductEntity.class);
        ProductDso productDao = DaoFactory.getInstance().getDaoType(DaoType.PRODUCT);
        productDao.save(entity);
        return false;
    }

    @Override
    public boolean deleteProduct(String id) {
        return false;
    }

    @Override
    public boolean updateProduct(Product product) {
        return false;
    }

    @Override
    public Product serachProduct(String name) {
        return null;
    }

    @Override
    public String getLastProID() {
        return "";
    }

    @Override
    public boolean updateStock(List<OrderDetails> orderDetails) {
        return false;
    }
}
