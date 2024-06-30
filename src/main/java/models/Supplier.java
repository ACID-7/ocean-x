package models;

import java.util.List;

public class Supplier {
    private int supplierId;
    private String name;
    private String contactInfo;
    private List<Part> suppliedParts;

    public Supplier(int supplierId, String name, String contactInfo, List<Part> suppliedParts) {
        this.supplierId = supplierId;
        this.name = name;
        this.contactInfo = contactInfo;
        this.suppliedParts = suppliedParts;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<Part> getSuppliedParts() {
        return suppliedParts;
    }

    public void setSuppliedParts(List<Part> suppliedParts) {
        this.suppliedParts = suppliedParts;
    }
}
