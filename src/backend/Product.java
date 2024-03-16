package backend;

public abstract class Product {
    private String productID;
    private String name;
    private int noOfAvailItems;
    private double price;

    //private  String productType;

    public Product(String productID, String name, int noOfAvailItems, double price) {
        this.productID = productID;
        this.name = name;
        this.noOfAvailItems = noOfAvailItems;
        this.price = price;
    }

    public String getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public int getNoOfAvailItems() {
        return noOfAvailItems;
    }

    public void setNoOfAvailItems(int noOfAvailItems) {
        this.noOfAvailItems = noOfAvailItems;
    }

    public double getPrice() {
        return price;
    }


    public abstract void displayInfo();

    @Override
    public String toString() {
        return "backend.Product ID: " + getProductID() +
                "\nbackend.Product Name: " + getName() +
                "\nAvailable Items: " + getNoOfAvailItems() +
                "\nPrice: " + getPrice() ;
    }
}
