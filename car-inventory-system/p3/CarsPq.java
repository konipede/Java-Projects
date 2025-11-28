package cs1501.p3;

import java.util.NoSuchElementException;



public class CarsPq implements CarsPqInterface{
    public MyPQ allPrice;
    public MyPQ allMile;
    public Dlb MakeModel;

    public CarsPq(String fileName) {
        allPrice = new MyPQ(true);
        allMile = new MyPQ(false);
        MakeModel = new Dlb();
        // loadCarsFromFile(fileName);
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void remove(String vin) throws NoSuchElementException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Car getLowPrice() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Car getLowPrice(String make, String model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Car getLowMileage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Car getLowMileage(String make, String model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}