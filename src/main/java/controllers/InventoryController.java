package controllers;

import models.Inventory;
import services.InventoryService;
import java.util.List;

public class InventoryController {
    private InventoryService inventoryService;

    public InventoryController() {
        inventoryService = new InventoryService();
    }

    public void addInventory(Inventory inventory) {
        inventoryService.createInventory(inventory);
    }

    public void updateInventory(Inventory inventory) {
        inventoryService.updateInventory(inventory);
    }

    public void removeInventory(int partId) {
        inventoryService.removeInventory(partId);
    }

    public List<Inventory> getAllInventory() {
        return inventoryService.getAllInventory();
    }
}
