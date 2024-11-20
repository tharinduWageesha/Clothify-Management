package service.custom.impl;

import dto.Order;
import entity.OrderEntity;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.OrderDao;
import service.custom.OrderService;
import utilDBOPT.DaoType;

import java.sql.SQLException;

public class OrderServiceImpl implements OrderService {
    @Override
    public boolean addOrder(Order order) {
        OrderEntity entity = new ModelMapper().map(order, OrderEntity.class);
        OrderDao orderDao = DaoFactory.getInstance().getDaoType(DaoType.ORDER);
        orderDao.save(entity);
        return false;
    }

    @Override
    public boolean deleteOrder(String id) {
        return false;
    }

    @Override
    public boolean updateOrder(Order order) {
        return false;
    }

    @Override
    public Order serachOrder(String name) {
        return null;
    }

    @Override
    public boolean placeOrder(Order order) throws SQLException {
        return false;
    }

    @Override
    public String getLastOrdID() {
        return "";
    }
}
