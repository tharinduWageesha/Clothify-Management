package service.custom;

import dto.Employee;

public interface EmployeeService {
    boolean addEmployee(Employee employee);
    boolean deleteEmployee(String id);
    boolean updateEmployee(Employee employee);
    Employee searchEmployee(String id);

    String getLastEmpID();
}
