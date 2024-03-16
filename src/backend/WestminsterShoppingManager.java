package backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class WestminsterShoppingManager implements ShoppingManager {
    // interface segregation
    //back end of the system
    @Override
    public void addProduct(Product product, List<Product> productList) {
        if (productList.size() >= 50) {
            System.out.println("You have reached the limit to add products to the system");
        } else {
            productList.add(product);
            System.out.println("Product added to the system succesfully");
        }
    }

    @Override
    public void deleteProduct(String productId, List<Product> productList) {
        for (Product product : productList) {
            if (product.getProductID().equals(productId)) {
                productList.remove(product);
                System.out.println(product.getName() + "Product deleted succesfully" +
                        "The remaining number of products: " + productList.size());
                return;
            }
        }
        System.out.println("Product with ID entered is not found.");
    }

    @Override
    public void printProductList(List<Product> productList) {
        if (productList.isEmpty()) {
            System.out.println("No products in the list");
        } else {
            //interates and sorts the whole list according to id
            productList.sort(Comparator.comparing(Product::getProductID));
            System.out.println("Products in the list: " + "\n");
            for (Product product : productList) {
                if (product instanceof Electronics) {
                    System.out.println("Electronics:");
                } else if (product instanceof Clothing) {
                    System.out.println("Clothing:");
                }
                product.displayInfo();
            }
        }
    }

    public void saveToFile(List<Product> productList) {
        try {
            // FileWriter object with the filename, it will automatically create a new file
            // or overwrite an existing file with the same name.
            FileWriter writer = new FileWriter("Products.txt");
            for (Product product : productList) {
                if (product instanceof Electronics) {
                    writer.write("\nElectronics:");
                } else if (product instanceof Clothing) {
                    writer.write("\nClothing:");
                }
                writer.write("\nProduct ID: " + product.getProductID() +
                        "\nProduct Name: " + product.getName() +
                        "\nAvailable Items: " + product.getNoOfAvailItems() +
                        "\nPrice: " + product.getPrice());
                if (product instanceof Electronics electronics) {
                    writer.write("\nBrand: " + electronics.getBrand() +
                            "\nWarranty Period: " + electronics.getWarranty());
                } else if (product instanceof Clothing clothing) {
                    writer.write("\nSize: " + clothing.getSize() +
                            "\nColour: " + clothing.getColour());
                }
            }
            writer.close();
            System.out.println("Products saved to the file.");

        } catch (IOException e) {
            System.out.println("An error occurred while saving the file.");
            e.printStackTrace();
        }
    }

    public List<Product> loadFromFile() {
        List<Product> productList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("products.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.equals("Electronics:") || line.equals("Clothing:")) {
                    String id = reader.readLine().split(":")[1].trim();
                    String name = reader.readLine().split(":")[1].trim();
                    int availableItems = Integer.parseInt(reader.readLine().split(":")[1].trim());
                    double price = Double.parseDouble(reader.readLine().split(":")[1].trim());

                    if (line.equals("Electronics:")) {
                        String brand = reader.readLine().split(":")[1].trim();
                        int warranty = Integer.parseInt(reader.readLine().split(":")[1].trim());
                        productList.add(new Electronics(id, name, availableItems, price, brand, warranty));
                    } else if (line.equals("Clothing:")) {
                        String size = reader.readLine().split(":")[1].trim();
                        String color = reader.readLine().split(":")[1].trim();
                        productList.add(new Clothing(id, name, availableItems, price, size, color));
                    }
                }
            }
            System.out.println("Products loaded from file.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return productList;
    }
}