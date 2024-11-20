package service.custom;

import dto.Supplier;

public interface SupplierService {
    boolean addSupplier(Supplier supplier);
    boolean deleteSupplier(String id);
    boolean updateSupplier(Supplier supplier);
    Supplier serachSupplier(String name);

    String getLastSupID();
}
