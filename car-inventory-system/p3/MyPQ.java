package cs1501.p3;

import java.util.NoSuchElementException;

public class MyPQ {
    protected Car[] pq;
    private int n; // Number of elements in the priority queue
    private static final int m = 30; // Maximum number of elements in the priority queue
    public Dlb dlb;
    public boolean price;

    public MyPQ(boolean Price) {
        pq = new Car[m];
        n = 0;
        dlb = new Dlb();
        price = Price;
    }

    public void add(Car car) {
        if (n == pq.length) {
            resize(); // add resize method
        }
        pq[n] = car;
        dlb.add(car.getVIN());
        dlb.updateIndex(car.getVIN(), n);
        swim(n); // add swim method
        n++;
    }

    public Car deleteMin() {
        if (n == 0) return null;
        Car min = pq[0];
        pq[0] = pq[n - 1];
        n--;
        sink(0);
        return min;
    }
    public Car peek() {
        if (n == 0) return null;
        return pq[0];
    }

    public void sink(int index) {
        while (2 * index + 1 <= n) {
            int j = 2 * index + 1;
            if (j < n-1 && greater(j, j + 1)) j++;
            if (!greater(index, j)) break;
            swap(index, j);
            index = j;
        }
    }

    public void swim(int index) {
        while (index > 0 && greater((index - 1) / 2, index)) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    public void swap(int i, int j) {
        Car swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        dlb.updateIndex(pq[i].Vin, i);
        dlb.updateIndex(pq[j].Vin, j);
    }

    private boolean greater(int i, int j) {
        if(price) return pq[i].compareToByPrice(pq[j]) > 0;
        else return pq[i].compareToByMileage(pq[j]) > 0;
    }

    private void resize() {
        Car[] newPq = new Car[pq.length * 2];
        System.arraycopy(pq, 0, newPq, 0, pq.length);
        pq = newPq;
        
    }
   public void delete(String vin) throws NoSuchElementException {
        int index = dlb.getIndex(vin);
        if (index == -1) {
            throw new NoSuchElementException("Car with VIN " + vin + " not found.");
        }
        swap(index, n - 1);
        dlb.updateIndex(vin, -1); // Set the index in DLB to -1 to indicate deletion
        n--;
        sink(index);
        swim(index);
    }
    







}

