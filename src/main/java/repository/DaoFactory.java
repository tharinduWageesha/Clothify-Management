package repository;

import repository.custom.impl.*;
import utilDBOPT.DaoType;

public class DaoFactory {
    private DaoFactory(){}
    private static DaoFactory instance;

    public static DaoFactory getInstance(){
        return instance==null?instance=new DaoFactory():instance;
    }

    public <T extends SuperDao>T getDaoType(DaoType type){
        switch (type){
            case USER:return (T) new UserDaoImpl();
            case ORDER:return (T) new OrderDaoImpl();
            case EMPLOYEE:return (T) new EmployeeDaoImpl();
            case SUPPLIER:return (T) new SupplierDaoImpl();
            case PRODUCT:return (T) new ProductDaoImpl();
        }
        return null;
    }
}
