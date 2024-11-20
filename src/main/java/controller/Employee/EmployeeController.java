package controller.Employee;

import db.DBConnection;
import dto.Employee;
import utilDBOPT.CRUDUtil;

import java.sql.*;

public class EmployeeController implements EmployeeService {
    @Override
    public boolean addEmployee(Employee employee) {
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
    public boolean deleteEmployee(String id) {
        String sql="Delete from employee where Employee_ID='"+id+"';";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            return connection.createStatement().executeUpdate(sql)>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean updateEmployee(Employee employee) {
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
    public Employee searchEmployee(String id) {
        String SQl = "SELECT * FROM Employee WHERE Employee_ID=?";
        try {
            ResultSet resultSet = CRUDUtil.execute(SQl, id);
            while (resultSet.next()) {
                return new Employee(
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
    public String getLastEmpID() {
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
