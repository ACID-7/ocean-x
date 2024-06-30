package services;

import database.SupplierDAO;
import models.Supplier;
import java.util.List;

public class SupplierService {
    private SupplierDAO supplierDAO;

    public SupplierService() {
        supplierDAO = new SupplierDAO();
    }

    public void createSupplier(Supplier supplier) {
        supplierDAO.addSupplier(supplier);
    }

    public void updateSupplier(Supplier supplier) {
        supplierDAO.updateSupplier(supplier);
    }

    public void removeSupplier(int supplierId) {
        supplierDAO.removeSupplier(supplierId);
    }

    public List<Supplier> getAllSuppliers() {
        return supplierDAO.getAllSuppliers();
    }
}
