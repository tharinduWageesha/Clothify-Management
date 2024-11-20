package service.custom.impl;

import dto.Employee;
import entity.EmployeeEntity;
import entity.OrderEntity;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.EmployeeDao;
import repository.custom.OrderDao;
import service.custom.EmployeeService;
import utilDBOPT.DaoType;

public class EmployeeServiceImpl implements EmployeeService {
    @Override
    public boolean addEmployee(Employee employee) {
        EmployeeEntity entity = new ModelMapper().map(employee, EmployeeEntity.class);
        EmployeeDao empDao = DaoFactory.getInstance().getDaoType(DaoType.EMPLOYEE);
        empDao.save(entity);
        return false;
    }

    @Override
    public boolean deleteEmployee(String id) {
        return false;
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        return false;
    }

    @Override
    public Employee searchEmployee(String id) {
        return null;
    }

    @Override
    public String getLastEmpID() {
        return "";
    }
}
