package controller.Product;

import db.DBConnection;
import dto.OrderDetails;
import dto.Product;
import utilDBOPT.CRUDUtil;

import java.sql.*;
import java.util.List;

public class ProductController implements ProductService {

    @Override
    public boolean addProduct(Product product) {
        try {
            String SQL = "Insert into Product Values(?,?,?,?,?)";
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ClothifyManager", "root", "12345");
            PreparedStatement pstm = connection.prepareStatement(SQL);
            pstm.setObject(1, product.getProduct_ID());
            pstm.setObject(2, product.getProduct_Name());
            pstm.setObject(3, product.getPrice());
            pstm.setObject(4, product.getQty());
            pstm.setObject(5, product.getSupplier_ID());

            boolean isSucces = pstm.executeUpdate() > 0;
            return isSucces;

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }
    }

    @Override
    public boolean deleteProduct(String id) {
        String sql = "Delete from product where Product_ID='" + id + "';";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            boolean isDeleted = connection.createStatement().executeUpdate(sql) > 0;
            return isDeleted;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateProduct(Product product) {
        String sql = "UPDATE product SET Product_Name = ?, Price = ?, Qty = ? , Supplier_ID =? WHERE Product_ID = ?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, product.getProduct_Name());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getQty());
            preparedStatement.setString(4, product.getSupplier_ID());
            preparedStatement.setString(5, product.getProduct_ID());

            boolean isUpdated = preparedStatement.executeUpdate() > 0;
            return isUpdated;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Product serachProduct(String id) {
        String SQl = "SELECT * FROM Product WHERE Product_ID=?";
        try {
            ResultSet resultSet = CRUDUtil.execute(SQl, id);
            while (resultSet.next()) {
                return new Product(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getInt(4),
                        resultSet.getString(5)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public String getLastProID() {
        String sql = "SELECT Product_ID FROM Product ORDER BY Product_ID DESC LIMIT 1";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ClothifyManager", "root", "12345");
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getString("Product_ID");
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateStock(List<OrderDetails> orderDetails) {
        boolean isUpdateStock;
        for (OrderDetails orderDetail : orderDetails) {
            isUpdateStock = updateStock(orderDetail);
            if(!isUpdateStock){
                return false;
            }
        }
        return true;
    }

    public boolean updateStock(OrderDetails orderDetails) {
        String sql = "UPDATE Product SET Qty = Qty - ? WHERE Product_ID = ?";
        try {
            System.out.println("Updating stock for Product_ID: " + orderDetails.getProductID() +
                    ", Quantity to subtract: " + orderDetails.getQty());

            boolean isUpdated = CRUDUtil.execute(sql, orderDetails.getQty(), orderDetails.getProductID());

            if (isUpdated) {
                System.out.println("Stock updated successfully.");
            } else {
                System.out.println("Failed to update stock. Product may not exist or insufficient stock.");
            }

            return isUpdated;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            throw new RuntimeException("Error updating stock for product ID: " + orderDetails.getProductID(), e);
        }
    }


}
