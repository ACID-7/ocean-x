package controllers;

import database.PartDAO;
import database.SupplierDAO;
import models.Part;
import models.Supplier;

import java.util.List;

public class SupplierController {
    private SupplierDAO supplierDAO;
    private PartDAO partDAO;

    public SupplierController() {
        supplierDAO = new SupplierDAO();
        partDAO = new PartDAO();
    }

    public void addSupplier(Supplier supplier) {
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

    public List<Part> getAllParts() {
        return partDAO.getAllParts();
    }
}
