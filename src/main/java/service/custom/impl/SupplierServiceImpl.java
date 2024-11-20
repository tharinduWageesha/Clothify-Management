package service.custom.impl;

import dto.Supplier;
import entity.OrderEntity;
import entity.SupplierEntity;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.OrderDao;
import repository.custom.SupplierDao;
import service.custom.SupplierService;
import utilDBOPT.DaoType;

public class SupplierServiceImpl implements SupplierService {
    @Override
    public boolean addSupplier(Supplier supplier) {
        SupplierEntity entity = new ModelMapper().map(supplier, SupplierEntity.class);
        SupplierDao supplierDao = DaoFactory.getInstance().getDaoType(DaoType.SUPPLIER);
        supplierDao.save(entity);
        return false;
    }

    @Override
    public boolean deleteSupplier(String id) {
        return false;
    }

    @Override
    public boolean updateSupplier(Supplier supplier) {
        return false;
    }

    @Override
    public Supplier serachSupplier(String name) {
        return null;
    }

    @Override
    public String getLastSupID() {
        return "";
    }
}
