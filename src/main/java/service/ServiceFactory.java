package service;

import service.custom.impl.OrderServiceImpl;
import service.custom.impl.UserServiceImpl;
import service.custom.impl.EmployeeServiceImpl;
import service.custom.impl.SupplierServiceImpl;
import service.custom.impl.ProductServiceImpl;
import utilDBOPT.ServiceType;

public class ServiceFactory {
    private static ServiceFactory instance;
    private ServiceFactory(){}

    public static ServiceFactory getInstance() {
        return instance==null ? instance=new ServiceFactory():instance;
    }

    public<T extends SuperService>T getServiceTyoe(ServiceType type){
        switch (type){
            case USER:return (T) new UserServiceImpl();
            case ORDER:return (T) new OrderServiceImpl();
            case EMPLOYEE:return (T) new EmployeeServiceImpl();
            case SUPPLIER:return (T) new SupplierServiceImpl();
            case PRODUCT:return (T) new ProductServiceImpl();
        }
        return null;
    }
}
