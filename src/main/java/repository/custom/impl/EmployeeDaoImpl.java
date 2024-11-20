package repository.custom.impl;

import db.DBConnection;
import dto.Employee;
import entity.EmployeeEntity;
import repository.custom.EmployeeDao;
import utilDBOPT.CRUDUtil;

import java.sql.*;

public class EmployeeDaoImpl implements EmployeeDao {


    @Override
    public boolean save(EmployeeEntity employee) {
        String SQL = "Insert into Employee Values(?,?,?,?,?)";
        try {
            return CRUDUtil.execute(SQL,
                    employee.getEmpID(),
                    employee.getName(),
                    employee.getEmail(),
                    employee.getContactNo(),
                    employee.getStatus());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) {
        String sql="Delete from employee where Employee_ID='"+id+"';";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            return connection.createStatement().executeUpdate(sql)>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(EmployeeEntity employee) {
        String sql = "UPDATE Employee SET Employee_Name = ?,  Email = ?, ContactNo = ? , Status =? WHERE Employee_ID = ?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getEmail());
            preparedStatement.setString(3, employee.getContactNo());
            preparedStatement.setString(4, employee.getStatus());
            preparedStatement.setString(5,employee.getEmpID());

            boolean isUpdated = preparedStatement.executeUpdate() > 0;
            return isUpdated;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeEntity serach(String name) {
        String SQl = "SELECT * FROM Employee WHERE Employee_ID=?";
        try {
            ResultSet resultSet = CRUDUtil.execute(SQl, name);
            while (resultSet.next()) {
                return new EmployeeEntity(
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
        String sql = "SELECT Employee_ID FROM Employee ORDER BY Employee_ID DESC LIMIT 1";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ClothifyManager", "root", "12345");
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getString("Employee_ID");
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
