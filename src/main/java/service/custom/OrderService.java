package service.custom;

import dto.Order;

import java.sql.SQLException;

public interface OrderService{
    boolean addOrder(Order order);
    boolean deleteOrder(String id);
    boolean updateOrder(Order order);
    Order serachOrder(String name);
    boolean placeOrder(Order order) throws SQLException;
    String getLastOrdID();

}
