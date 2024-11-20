package controller.Order;

import controller.Product.ProductController;
import db.DBConnection;
import dto.Order;
import utilDBOPT.CRUDUtil;

import java.sql.*;

public class OrderController implements OrderService {
    @Override
    public boolean addOrder(Order order) {
        try {
            String SQL = "Insert into orders Values(?,?,?,?,?)";
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ClothifyManager", "root", "12345");
            PreparedStatement pstm = connection.prepareStatement(SQL);
            pstm.setObject(1,order.getOrder_ID());
            pstm.setObject(2,order.getDate());
            pstm.setObject(3,order.getTime());
            pstm.setObject(4,order.getDiscount());
            pstm.setObject(5,order.getCost());
            boolean isSucces = pstm.executeUpdate()>0;
            return isSucces;

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }
    }

    @Override
    public boolean deleteOrder(String id) {
        String sql="Delete from orders where Order_ID='"+id+"';";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            boolean isDeleted = connection.createStatement().executeUpdate(sql)>0;
            return isDeleted;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateOrder(Order order) {
        return false;
    }

    @Override
    public Order serachOrder(String id) {
        String SQl = "SELECT * FROM Orders WHERE Order_ID=?";
        try {
            ResultSet resultSet = CRUDUtil.execute(SQl, id);
            while (resultSet.next()) {
                return new Order(
                        resultSet.getString(1),
                        resultSet.getDate(2).toLocalDate(),
                        resultSet.getDouble(3),
                        resultSet.getDouble(4),
                        resultSet.getTime(5).toLocalTime()
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public String getLastOrdID() {
        String sql = "SELECT Order_ID FROM Orders ORDER BY Order_ID DESC LIMIT 1";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ClothifyManager", "root", "12345");
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getString("Order_ID");
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean placeOrder(Order order) throws SQLException {
        Connection connection =  DBConnection.getInstance().getConnection();
        try {
            String sql = "Insert into Orders(Order_Id,Date,Time,Discount,Cost) Values (?,?,?,?,?);";
            connection.setAutoCommit(false);
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setObject(1,order.getOrder_ID());
            pstm.setObject(2,order.getDate());
            pstm.setObject(3,order.getTime());
            pstm.setObject(4,order.getDiscount());
            pstm.setObject(5,order.getCost());
            boolean isOrderPlaced = pstm.executeUpdate() > 0;
            if(isOrderPlaced){
                boolean isOrderDetailAdd = new OrderDetailController().addOrderDetail(order.getOrderDetails());
                if(isOrderDetailAdd){
                    boolean isStockUpdate = new ProductController().updateStock(order.getOrderDetails());
                    if(isStockUpdate){
                        connection.commit();
                        return isStockUpdate;
                    }
                }
            }
            connection.rollback();
            return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }   
}
