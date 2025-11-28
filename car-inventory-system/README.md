# Car Inventory System

A high-performance car inventory management system implementing priority queues and DLB (De La Briandais) trees for efficient car data management and retrieval.

## Overview

This system allows for efficient storage and querying of car inventory data. Cars can be organized by price or mileage using priority queues, and fast lookups are enabled through DLB tree structures.

## Features

- **Priority Queue Management**: Separate priority queues for price and mileage
- **DLB Tree Implementation**: Fast string-based searching for VINs and make/model combinations
- **Efficient Updates**: O(log n) updates for price and mileage changes
- **Multiple Query Methods**: Support for global and make/model-specific queries

## Data Structures

### Core Classes

- **`Car`**: Represents a car with VIN, make, model, price, mileage, and color
- **`CarsPq`**: Main data structure managing all cars with priority queues
- **`MyPQ`**: Custom priority queue implementation with indexable access
- **`Dlb`**: De La Briandais tree for efficient string searching
- **`DlbNode`**: Node structure for DLB tree with embedded priority queues

## Implementation Details

### Priority Queues
- Two main priority queues: one sorted by price, one by mileage
- Each make/model combination has its own priority queues
- Uses binary heap with swim/sink operations for O(log n) operations

### DLB Tree
- Used for fast VIN lookups and make/model indexing
- Enables O(m) search time where m is the length of the search string
- Stores indices to priority queue elements for O(1) access

## Usage

```java
// Create a new car inventory system
CarsPq inventory = new CarsPq("cars.txt");

// Add a car
Car car = new Car("VIN123", "Toyota", "Camry", 25000, 50000, "Blue");
inventory.add(car);

// Get a car by VIN
Car found = inventory.get("VIN123");

// Update price
inventory.updatePrice("VIN123", 24000);

// Update mileage
inventory.updateMileage("VIN123", 51000);
```

## Package Structure

All classes are in the `cs1501.p3` package.

## Algorithms & Complexity

- **Add Car**: O(log n) for heap operations + O(m) for DLB insertion
- **Get by VIN**: O(m) for DLB search + O(1) for array access
- **Update Price/Mileage**: O(log n) for heap rebalancing
- **Space Complexity**: O(n) where n is the number of cars

## Author

Kerem

