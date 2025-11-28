package cs1501.p3;

public class Car implements CarInterface {

    protected String vin;
    protected String make;
    protected String model;
    protected int price;
    protected int mileage;
    protected String color;

    public Car(String vin, String make, String model, int price, int mileage, String color) {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.price = price;
        this.mileage = mileage;
        this.color = color;
    }
    @Override
    public String getVIN() {
        return this.vin;
    }

    @Override
    public String getMake() {
        return this.make;
    }

    @Override
    public String getModel() {
        return this.model;
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public int getMileage() {
        return this.mileage;
    }

    @Override
    public String getColor() {
        return this.color;
    }

    @Override
    public void setPrice(int newPrice) {
        this.price = newPrice;
    }

    @Override
    public void setMileage(int newMileage) {
        this.mileage = newMileage;
    }

    @Override
    public void setColor(String newColor) {
        this.color = newColor;
    }

    public int compareToByPrice(Car car) {
        return Integer.compare(this.getPrice(), car.getPrice());
    }

    public int compareToByMileage(Car car) {
        return Integer.compare(this.getMileage(), car.getMileage());
    }

    @Override
    public String toString() {
        return getColor() + " " + getMake() + " " + getModel() + " " + getVIN();
    }
}