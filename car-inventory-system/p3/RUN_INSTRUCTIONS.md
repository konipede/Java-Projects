# How to Run the Car Inventory System

## Prerequisites
- Java JDK 8 or higher installed
- All source files in the same directory

## Compilation

Compile all Java files:
```bash
cd car-inventory-system/p3
javac *.java
```

## Running the Tests

### Option 1: Run the Interactive Test (No File Required)
This test creates cars programmatically and demonstrates all functionality:
```bash
java cs1501.p3.CarInventoryTest
```

### Option 2: Run the File Loading Test
This test loads cars from a file:
```bash
java cs1501.p3.CarInventoryFileTest
```

Or specify a custom file:
```bash
java cs1501.p3.CarInventoryFileTest cars.txt
```

## File Format

The `cars.txt` file should have the following format (CSV):
```
VIN,Make,Model,Price,Mileage,Color
VIN001,Toyota,Camry,25000,50000,Blue
VIN002,Honda,Civic,22000,30000,Red
```

Example:
```
VIN001,Toyota,Camry,25000,50000,Blue
VIN002,Honda,Civic,22000,30000,Red
VIN003,Toyota,Camry,23000,45000,Silver
```

## Features Demonstrated

1. **Adding Cars**: Add cars to the inventory system
2. **Getting by VIN**: Retrieve a specific car by its VIN
3. **Lowest Price**: Find the car with the lowest price (globally or by make/model)
4. **Lowest Mileage**: Find the car with the lowest mileage (globally or by make/model)
5. **Update Price**: Update a car's price and maintain heap order
6. **Update Mileage**: Update a car's mileage and maintain heap order
7. **Update Color**: Update a car's color
8. **Remove Car**: Remove a car from the inventory

## Expected Output

The test programs will demonstrate:
- Adding multiple cars
- Querying for lowest price and mileage
- Updating car attributes
- Removing cars
- Make/model specific queries

## Troubleshooting

- **ClassNotFoundException**: Make sure you're in the parent directory of `cs1501/p3/` or use the full package path
- **FileNotFoundException**: Ensure `cars.txt` exists in the same directory, or the program will start with an empty inventory
- **Compilation Errors**: Ensure all `.java` files are in the same directory

