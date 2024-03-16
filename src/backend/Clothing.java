package backend;

public class Clothing extends Product {

    protected String size;
    protected String colour;

    public Clothing(String productID, String name, int noOfAvailItems, double price) {
        super(productID, name, noOfAvailItems, price);
    }

    public Clothing(String productID, String name, int noOfAvailItems, double price, String size, String colour) {
        super(productID, name, noOfAvailItems, price);
        this.size = size;
        this.colour = colour;
    }

    public String getSize() {
        return size;
    }

    public String getColour() {
        return colour;
    }

    @Override
    public void displayInfo() {
            System.out.println( super.toString() +
                    "\nsize='" + size +
                    "\ncolour='" + colour);
    }

    @Override
    public String toString() {
        return  size + ", " + colour;
    }
}
