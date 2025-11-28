package cs1501.p3;

/**
 * Test driver that loads cars from a file
 * Usage: java CarInventoryFileTest cars.txt
 */
public class CarInventoryFileTest {
    
    public static void main(String[] args) {
        String fileName = "cars.txt";
        if (args.length > 0) {
            fileName = args[0];
        }
        
        System.out.println("=== Car Inventory System - File Loading Test ===\n");
        System.out.println("Loading cars from: " + fileName);
        
        // Create inventory and load from file
        CarsPq inventory = new CarsPq(fileName);
        
        System.out.println("\n--- Inventory Statistics ---");
        System.out.println("Total cars loaded: " + (inventory.allPrice != null ? inventory.allPrice.size() : 0));
        
        // Display lowest price car
        Car lowestPrice = inventory.getLowPrice();
        if (lowestPrice != null) {
            System.out.println("\nLowest Priced Car:");
            System.out.println("  " + lowestPrice);
            System.out.println("  Price: $" + lowestPrice.getPrice());
            System.out.println("  Mileage: " + lowestPrice.getMileage() + " miles");
        }
        
        // Display lowest mileage car
        Car lowestMileage = inventory.getLowMileage();
        if (lowestMileage != null) {
            System.out.println("\nLowest Mileage Car:");
            System.out.println("  " + lowestMileage);
            System.out.println("  Price: $" + lowestMileage.getPrice());
            System.out.println("  Mileage: " + lowestMileage.getMileage() + " miles");
        }
        
        // Test make/model specific queries
        System.out.println("\n--- Make/Model Queries ---");
        Car toyotaCamry = inventory.getLowPrice("Toyota", "Camry");
        if (toyotaCamry != null) {
            System.out.println("Lowest Priced Toyota Camry:");
            System.out.println("  " + toyotaCamry);
            System.out.println("  Price: $" + toyotaCamry.getPrice());
        }
        
        Car hondaCivic = inventory.getLowMileage("Honda", "Civic");
        if (hondaCivic != null) {
            System.out.println("\nLowest Mileage Honda Civic:");
            System.out.println("  " + hondaCivic);
            System.out.println("  Mileage: " + hondaCivic.getMileage() + " miles");
        }
        
        System.out.println("\n=== File Loading Test Complete ===");
    }
}

