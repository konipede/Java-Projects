# Car Inventory System - Functionality Examples

This document demonstrates all the functionality of the Car Inventory System with code examples.

## 1. Creating an Inventory System

```java
// Create an empty inventory
CarsPq inventory = new CarsPq(null);

// Or load from a file
CarsPq inventory = new CarsPq("cars.txt");
```

## 2. Adding Cars

```java
Car car1 = new Car("VIN001", "Toyota", "Camry", 25000, 50000, "Blue");
Car car2 = new Car("VIN002", "Honda", "Civic", 22000, 30000, "Red");
Car car3 = new Car("VIN003", "Toyota", "Camry", 23000, 45000, "Silver");
Car car4 = new Car("VIN004", "Ford", "Focus", 18000, 60000, "Black");

inventory.add(car1);
inventory.add(car2);
inventory.add(car3);
inventory.add(car4);
```

**Result**: All 4 cars are added to the inventory and organized in priority queues by price and mileage.

## 3. Finding Lowest Priced Car (Global)

```java
Car lowestPrice = inventory.getLowPrice();
// Returns: Ford Focus at $18,000 (lowest price globally)
```

## 4. Finding Lowest Mileage Car (Global)

```java
Car lowestMileage = inventory.getLowMileage();
// Returns: Honda Civic at 30,000 miles (lowest mileage globally)
```

## 5. Finding Lowest Price for Specific Make/Model

```java
Car lowestPriceToyotaCamry = inventory.getLowPrice("Toyota", "Camry");
// Returns: Silver Toyota Camry at $23,000 (lowest priced Camry)
```

## 6. Finding Lowest Mileage for Specific Make/Model

```java
Car lowestMileageHondaCivic = inventory.getLowMileage("Honda", "Civic");
// Returns: Red Honda Civic at 30,000 miles (lowest mileage Civic)
```

## 7. Getting a Car by VIN

```java
Car found = inventory.get("VIN002");
// Returns: Red Honda Civic with all details
System.out.println(found.getMake());    // "Honda"
System.out.println(found.getModel());    // "Civic"
System.out.println(found.getPrice());   // 22000
System.out.println(found.getMileage()); // 30000
System.out.println(found.getColor());   // "Red"
```

## 8. Updating Car Price

```java
// Before: Lowest price is $18,000 (Ford Focus)
inventory.updatePrice("VIN001", 24000);
// After: Price updated, heap automatically reorganized
// New lowest price might be different if VIN001 was the lowest
```

**How it works**: The price is updated and the priority queues automatically maintain heap order using swim/sink operations.

## 9. Updating Car Mileage

```java
// Before: Lowest mileage is 30,000 (Honda Civic)
inventory.updateMileage("VIN004", 55000);
// After: Mileage updated, heap automatically reorganized
```

**How it works**: The mileage is updated in all relevant priority queues (global and make/model specific) and heaps are rebalanced.

## 10. Updating Car Color

```java
inventory.updateColor("VIN003", "Gold");
Car updated = inventory.get("VIN003");
System.out.println(updated.getColor()); // "Gold"
```

## 11. Removing a Car

```java
// Remove the Ford Focus
inventory.remove("VIN004");

// Try to get it (will throw NoSuchElementException)
try {
    Car removed = inventory.get("VIN004");
} catch (NoSuchElementException e) {
    // Car no longer exists
}
```

**How it works**: The car is removed from:
- Global price priority queue
- Global mileage priority queue  
- Make/model specific price priority queue
- Make/model specific mileage priority queue

## Complete Example Workflow

```java
// 1. Create inventory
CarsPq inventory = new CarsPq(null);

// 2. Add cars
inventory.add(new Car("VIN001", "Toyota", "Camry", 25000, 50000, "Blue"));
inventory.add(new Car("VIN002", "Honda", "Civic", 22000, 30000, "Red"));
inventory.add(new Car("VIN003", "Toyota", "Camry", 23000, 45000, "Silver"));
inventory.add(new Car("VIN004", "Ford", "Focus", 18000, 60000, "Black"));

// 3. Query operations
Car cheapest = inventory.getLowPrice();           // Ford Focus $18,000
Car lowestMiles = inventory.getLowMileage();      // Honda Civic 30,000 miles
Car cheapestCamry = inventory.getLowPrice("Toyota", "Camry"); // $23,000

// 4. Update operations
inventory.updatePrice("VIN001", 24000);          // Update price
inventory.updateMileage("VIN004", 55000);       // Update mileage
inventory.updateColor("VIN003", "Gold");        // Update color

// 5. Remove operation
inventory.remove("VIN004");                      // Remove Ford Focus

// 6. Verify removal
Car cheapestAfter = inventory.getLowPrice();     // Now Toyota Camry $23,000
```

## Data Structure Features

### Priority Queues
- **Global Price Queue**: All cars sorted by price (min-heap)
- **Global Mileage Queue**: All cars sorted by mileage (min-heap)
- **Make/Model Price Queues**: Separate queues for each make/model combination
- **Make/Model Mileage Queues**: Separate queues for each make/model combination

### DLB Tree
- **VIN Lookup**: O(m) time where m is VIN length
- **Make/Model Indexing**: Fast string-based searching for make/model combinations
- **Index Storage**: Stores indices to priority queue elements for O(1) access

### Time Complexities
- **Add Car**: O(log n) for heap operations + O(m) for DLB insertion
- **Get by VIN**: O(m) for DLB search + O(1) for array access
- **Update Price/Mileage**: O(log n) for heap rebalancing
- **Remove Car**: O(log n) for heap operations
- **Get Lowest Price/Mileage**: O(1) - just peek at heap root

## File Format

When loading from a file, use CSV format:
```
VIN,Make,Model,Price,Mileage,Color
VIN001,Toyota,Camry,25000,50000,Blue
VIN002,Honda,Civic,22000,30000,Red
```

Example `cars.txt`:
```
VIN001,Toyota,Camry,25000,50000,Blue
VIN002,Honda,Civic,22000,30000,Red
VIN003,Toyota,Camry,23000,45000,Silver
VIN004,Ford,Focus,18000,60000,Black
VIN005,Honda,Civic,20000,35000,White
```

