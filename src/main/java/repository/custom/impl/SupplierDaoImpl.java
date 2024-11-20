package repository.custom.impl;

import db.DBConnection;
import dto.Supplier;
import entity.SupplierEntity;
import repository.custom.SupplierDao;
import utilDBOPT.CRUDUtil;

import java.sql.*;

public class SupplierDaoImpl implements SupplierDao {

    @Override
    public boolean save(SupplierEntity supplier) {
        try {
            String SQL = "Insert into Supplier Values(?,?,?,?,?)";
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ClothifyManager", "root", "12345");
            PreparedStatement pstm = connection.prepareStatement(SQL);
            pstm.setObject(1,supplier.getSupplier_ID());
            pstm.setObject(2,supplier.getSupplier_Name());
            pstm.setObject(3,supplier.getEmail());
            pstm.setObject(4,supplier.getCompany());
            pstm.setObject(5,supplier.getProduct_ID());
            boolean isSucces = pstm.executeUpdate()>0;
            return isSucces;

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }
    }

    @Override
    public boolean delete(String id) {
        String sql="Delete from supplier where Supplier_ID='"+id+"';";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            boolean isDeleted = connection.createStatement().executeUpdate(sql)>0;
            return isDeleted;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(SupplierEntity supplier) {
        String sql = "UPDATE Supplier SET Supplier_Name = ?, Company = ?, Email = ? , Product_ID =? WHERE Supplier_ID = ?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, supplier.getSupplier_Name());
            preparedStatement.setString(2, supplier.getCompany());
            preparedStatement.setString(3, supplier.getEmail());
            preparedStatement.setString(4, supplier.getProduct_ID());
            preparedStatement.setString(5,supplier.getSupplier_ID());

            boolean isUpdated = preparedStatement.executeUpdate() > 0;
            return isUpdated;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SupplierEntity serach(String name) {
        String SQl = "SELECT * FROM Supplier WHERE Supplier_ID=?";
        try {
            ResultSet resultSet = CRUDUtil.execute(SQl, name);
            while (resultSet.next()) {
                return new SupplierEntity(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public String getLastID() {
        String sql = "SELECT Supplier_ID FROM Supplier ORDER BY Supplier_ID DESC LIMIT 1";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ClothifyManager", "root", "12345");
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getString("Supplier_ID");
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
