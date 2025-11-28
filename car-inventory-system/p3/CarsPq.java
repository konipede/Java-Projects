package cs1501.p3;

import java.util.NoSuchElementException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CarsPq implements CarsPqInterface {
    public MyPQ allPrice;
    public MyPQ allMile;
    public Dlb MakeModel;

    public CarsPq(String fileName) {
        allPrice = new MyPQ(true);
        allMile = new MyPQ(false);
        MakeModel = new Dlb();
        if (fileName != null && !fileName.isEmpty()) {
            loadCarsFromFile(fileName);
        }
    }

    private void loadCarsFromFile(String fileName) {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String vin = parts[0].trim();
                    String make = parts[1].trim();
                    String model = parts[2].trim();
                    int price = Integer.parseInt(parts[3].trim());
                    int mileage = Integer.parseInt(parts[4].trim());
                    String color = parts[5].trim();
                    Car car = new Car(vin, make, model, price, mileage, color);
                    add(car);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
        } catch (Exception e) {
            System.err.println("Error loading file: " + e.getMessage());
        }
    }

    @Override
    public void add(Car c) throws IllegalStateException {
        allMile.add(c);
        allPrice.add(c);
        MakeModel.add(c.getMake() + c.getModel());
        DlbNode node = MakeModel.getNode(MakeModel.root, c.getMake() + c.getModel(), 0);
        if (node.mileHeap == null) node.setmileHeap(new MyPQ(false));
        if (node.priceHeap == null) node.setPriceHeap(new MyPQ(true));
        node.mileHeap.add(c);
        node.priceHeap.add(c);
    }

    @Override
    public Car get(String vin) throws NoSuchElementException {
      int index = allPrice.dlb.getIndex(vin);
      return allPrice.pq[index];
    }

    @Override
    public void updatePrice(String vin, int newPrice) throws NoSuchElementException {
        int index = allPrice.dlb.getIndex(vin);
         Car car = allPrice.pq[index];
         car.setPrice(newPrice);
         allPrice.sink(index);
         allPrice.swim(index);
         index = allMile.dlb.getIndex(vin);
          car = allMile.pq[index];
         car.setPrice(newPrice);
        String label = car.getMake() + car.getModel();
       DlbNode node = MakeModel.getNode(MakeModel.root, label, 0);
      int i = node.mileHeap.dlb.getIndex(car.getVIN());
      node.mileHeap.pq[i].setPrice(newPrice);
      i = node.priceHeap.dlb.getIndex(car.getVIN());
      node.priceHeap.pq[i].setPrice(newPrice);
      node.priceHeap.sink(i);
      node.priceHeap.swim(i);
    }

    @Override
    public void updateMileage(String vin, int newMileage) throws NoSuchElementException {
        int index = allMile.dlb.getIndex(vin);
        Car car = allMile.pq[index];
        car.setMileage(newMileage);
        allMile.sink(index);
        allMile.swim(index);
        index = allPrice.dlb.getIndex(vin);
         car = allPrice.pq[index];
        car.setMileage(newMileage);
       String label = car.getMake() + car.getModel();
      DlbNode node = MakeModel.getNode(MakeModel.root, label, 0);
     int i = node.priceHeap.dlb.getIndex(car.getVIN());
     node.priceHeap.pq[i].setMileage(newMileage);
     i = node.mileHeap.dlb.getIndex(car.getVIN());
     node.mileHeap.pq[i].setMileage(newMileage);
     node.mileHeap.sink(i);
     node.mileHeap.swim(i);
    }

    @Override
    public void updateColor(String vin, String newColor) throws NoSuchElementException {
        int index = allPrice.dlb.getIndex(vin);
        if (index == -1) {
            throw new NoSuchElementException("Car with VIN " + vin + " not found.");
        }
        Car car = allPrice.pq[index];
        car.setColor(newColor);
    }

    @Override
    public void remove(String vin) throws NoSuchElementException {
        int index = allPrice.dlb.getIndex(vin);
        if (index == -1) {
            throw new NoSuchElementException("Car with VIN " + vin + " not found.");
        }
        Car car = allPrice.pq[index];
        
        // Remove from allPrice
        allPrice.delete(vin);
        
        // Remove from allMile
        allMile.delete(vin);
        
        // Remove from make/model specific heaps
        String label = car.getMake() + car.getModel();
        DlbNode node = MakeModel.getNode(MakeModel.root, label, 0);
        if (node != null) {
            if (node.priceHeap != null) {
                node.priceHeap.delete(vin);
            }
            if (node.mileHeap != null) {
                node.mileHeap.delete(vin);
            }
        }
    }

    @Override
    public Car getLowPrice() {
        if (allPrice.n == 0) return null;
        return allPrice.peek();
    }

    @Override
    public Car getLowPrice(String make, String model) {
        String label = make + model;
        DlbNode node = MakeModel.getNode(MakeModel.root, label, 0);
        if (node == null || node.priceHeap == null || node.priceHeap.n == 0) {
            return null;
        }
        return node.priceHeap.peek();
    }

    @Override
    public Car getLowMileage() {
        if (allMile.n == 0) return null;
        return allMile.peek();
    }

    @Override
    public Car getLowMileage(String make, String model) {
        String label = make + model;
        DlbNode node = MakeModel.getNode(MakeModel.root, label, 0);
        if (node == null || node.mileHeap == null || node.mileHeap.n == 0) {
            return null;
        }
        return node.mileHeap.peek();
    }
}