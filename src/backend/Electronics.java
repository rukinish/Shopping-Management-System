package backend;

public class Electronics extends Product{

    protected String brand;
    protected int warranty;
    public Electronics(String productID, String name, int noOfAvailItems, double price) {
        super(productID, name, noOfAvailItems, price);
    }

    public Electronics(String productID, String name, int noOfAvailItems, double price, String brand, int warranty) {
        super(productID, name, noOfAvailItems, price);
        this.brand = brand;
        this.warranty = warranty;
    }

    public String getBrand() {
        return brand;
    }

    public int getWarranty() {
        return warranty;
    }


    @Override
    public void displayInfo() {
        System.out.println( super.toString() +
                "\nBrand: " + brand +
                "\nWarranty Period: " + warranty);
    }

    @Override
    public String toString() {
        return brand + ", " + warranty ;
    }
}
