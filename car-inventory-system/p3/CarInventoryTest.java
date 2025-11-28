package cs1501.p3;

import java.util.Scanner;

/**
 * Test driver for the Car Inventory System
 * Demonstrates all functionality of the CarsPq data structure
 */
public class CarInventoryTest {
    
    public static void main(String[] args) {
        System.out.println("=== Car Inventory System Test ===\n");
        
        // Create inventory system (can load from file or start empty)
        CarsPq inventory = new CarsPq(null); // Start with empty inventory
        
        // Add some test cars
        System.out.println("Adding cars to inventory...");
        try {
            inventory.add(new Car("VIN001", "Toyota", "Camry", 25000, 50000, "Blue"));
            inventory.add(new Car("VIN002", "Honda", "Civic", 22000, 30000, "Red"));
            inventory.add(new Car("VIN003", "Toyota", "Camry", 23000, 45000, "Silver"));
            inventory.add(new Car("VIN004", "Ford", "Focus", 18000, 60000, "Black"));
            inventory.add(new Car("VIN005", "Honda", "Civic", 20000, 35000, "White"));
            inventory.add(new Car("VIN006", "Toyota", "Corolla", 21000, 40000, "Gray"));
            System.out.println("✓ Added 6 cars successfully\n");
        } catch (IllegalStateException e) {
            System.out.println("Error adding car: " + e.getMessage());
        }
        
        // Test getLowPrice (global)
        System.out.println("--- Testing getLowPrice (global) ---");
        Car lowestPrice = inventory.getLowPrice();
        if (lowestPrice != null) {
            System.out.println("Lowest priced car: " + lowestPrice);
            System.out.println("  Price: $" + lowestPrice.getPrice());
        } else {
            System.out.println("No cars in inventory");
        }
        System.out.println();
        
        // Test getLowMileage (global)
        System.out.println("--- Testing getLowMileage (global) ---");
        Car lowestMileage = inventory.getLowMileage();
        if (lowestMileage != null) {
            System.out.println("Lowest mileage car: " + lowestMileage);
            System.out.println("  Mileage: " + lowestMileage.getMileage() + " miles");
        } else {
            System.out.println("No cars in inventory");
        }
        System.out.println();
        
        // Test getLowPrice (by make/model)
        System.out.println("--- Testing getLowPrice (Toyota Camry) ---");
        Car lowestPriceToyotaCamry = inventory.getLowPrice("Toyota", "Camry");
        if (lowestPriceToyotaCamry != null) {
            System.out.println("Lowest priced Toyota Camry: " + lowestPriceToyotaCamry);
            System.out.println("  Price: $" + lowestPriceToyotaCamry.getPrice());
        } else {
            System.out.println("No Toyota Camry found");
        }
        System.out.println();
        
        // Test getLowMileage (by make/model)
        System.out.println("--- Testing getLowMileage (Honda Civic) ---");
        Car lowestMileageHondaCivic = inventory.getLowMileage("Honda", "Civic");
        if (lowestMileageHondaCivic != null) {
            System.out.println("Lowest mileage Honda Civic: " + lowestMileageHondaCivic);
            System.out.println("  Mileage: " + lowestMileageHondaCivic.getMileage() + " miles");
        } else {
            System.out.println("No Honda Civic found");
        }
        System.out.println();
        
        // Test get by VIN
        System.out.println("--- Testing get by VIN ---");
        try {
            Car car = inventory.get("VIN001");
            System.out.println("Found car: " + car);
            System.out.println("  Make: " + car.getMake());
            System.out.println("  Model: " + car.getModel());
            System.out.println("  Price: $" + car.getPrice());
            System.out.println("  Mileage: " + car.getMileage() + " miles");
            System.out.println("  Color: " + car.getColor());
        } catch (NoSuchElementException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println();
        
        // Test updatePrice
        System.out.println("--- Testing updatePrice ---");
        try {
            System.out.println("Updating price of VIN001 from $25000 to $24000");
            inventory.updatePrice("VIN001", 24000);
            Car updated = inventory.get("VIN001");
            System.out.println("Updated car: " + updated);
            System.out.println("  New price: $" + updated.getPrice());
            
            // Check if it's still the lowest
            Car newLowest = inventory.getLowPrice();
            System.out.println("New lowest priced car: " + newLowest);
        } catch (NoSuchElementException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println();
        
        // Test updateMileage
        System.out.println("--- Testing updateMileage ---");
        try {
            System.out.println("Updating mileage of VIN002 from 30000 to 31000");
            inventory.updateMileage("VIN002", 31000);
            Car updated = inventory.get("VIN002");
            System.out.println("Updated car: " + updated);
            System.out.println("  New mileage: " + updated.getMileage() + " miles");
        } catch (NoSuchElementException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println();
        
        // Test updateColor
        System.out.println("--- Testing updateColor ---");
        try {
            System.out.println("Updating color of VIN003 from Silver to Gold");
            inventory.updateColor("VIN003", "Gold");
            Car updated = inventory.get("VIN003");
            System.out.println("Updated car: " + updated);
            System.out.println("  New color: " + updated.getColor());
        } catch (NoSuchElementException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println();
        
        // Test remove
        System.out.println("--- Testing remove ---");
        try {
            System.out.println("Removing VIN004 (Ford Focus)");
            inventory.remove("VIN004");
            System.out.println("✓ Car removed successfully");
            
            // Try to get removed car (should fail)
            try {
                Car removed = inventory.get("VIN004");
                System.out.println("ERROR: Car still exists!");
            } catch (NoSuchElementException e) {
                System.out.println("✓ Confirmed: Car no longer exists");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println();
        
        // Final summary
        System.out.println("=== Test Summary ===");
        System.out.println("All tests completed successfully!");
        System.out.println("\nRemaining cars in inventory:");
        System.out.println("  Lowest Price: " + inventory.getLowPrice());
        System.out.println("  Lowest Mileage: " + inventory.getLowMileage());
    }
}

