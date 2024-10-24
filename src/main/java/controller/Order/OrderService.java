package controller.Order;

import model.Order;

public interface OrderService {
    boolean addOrder(Order order);
    boolean deleteOrder(String id);
    boolean updateOrder(Order order);
    Order serachOrder(String name);

    String getLastOrdID();
}
