package services;

import database.InventoryDAO;
import models.Inventory;
import java.util.List;

public class InventoryService {
    private InventoryDAO inventoryDAO;

    public InventoryService() {
        inventoryDAO = new InventoryDAO();
    }

    public void createInventory(Inventory inventory) {
        inventoryDAO.addInventory(inventory);
    }

    public void updateInventory(Inventory inventory) {
        inventoryDAO.updateInventory(inventory);
    }

    public void removeInventory(int partId) {
        inventoryDAO.removeInventory(partId);
    }

    public List<Inventory> getAllInventory() {
        return inventoryDAO.getAllInventory();
    }
}
