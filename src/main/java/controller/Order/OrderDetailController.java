package controller.Order;

import dto.OrderDetails;
import utilDBOPT.CRUDUtil;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailController {
    public boolean addOrderDetail(List<OrderDetails> orderDetails){
        boolean isAdd = false;
        for(OrderDetails orderDetail:orderDetails){
            isAdd = addOrderDetail(orderDetail);
        }
        if(!isAdd){
            return false;
        }else {
            return true;
        }
    }

    public boolean addOrderDetail(OrderDetails orderDetails){
        String sql = "Insert into orderproduct VALUES (?,?,?)";
        try {
            Object execute = CRUDUtil.execute(sql,
                    orderDetails.getOrderID(),
                    orderDetails.getProductID(),
                    orderDetails.getQty());
            if(execute!=null){
                return true;
            }return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
