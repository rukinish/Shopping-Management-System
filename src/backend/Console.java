package backend;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

//THE CLASS FOR CONSOLE
public class Console {
        private WestminsterShoppingManager shoppingManager;
        private Scanner scanner;

        public Console(WestminsterShoppingManager shoppingManager) {
            this.shoppingManager = shoppingManager;
            this.scanner = new Scanner(System.in);
        }

        public void userChoice(List<Product> productList) {
            //try catch
            while (true) {
                System.out.println("\n=== Shopping Manager Menu ===\n1. Add Product\n2. Delete Product\n" +
                        "3. Print Product List\n4. Save Products to File\n5. Exit");
                try {
                    System.out.print("Enter your choice from the menu: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    switch (choice) {
                        case 1:
                            addProductFromConsole(productList);
                            break;
                        case 2:
                            deleteProductFromConsole(productList);
                            break;
                        case 3:
                            shoppingManager.printProductList(productList);
                            break;
                        case 4:
                            shoppingManager.saveToFile(productList);
                            break;
                        case 5:
                            // Exit
                            System.exit(0);
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // Consume the invalid input to avoid an infinite loop
                }
            }
        }

        //method to add products
        private void addProductFromConsole(List<Product> productList) {
            // Implement logic to get user input for adding a product
            // Then call the corresponding method in backend.WestminsterShoppingManager
            while (true) {
                System.out.print("Enter product type \nEnter 1 for Clothing or 2 for Electronics: ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input! Please enter a NUMBER to select product type " +
                            "\nEnter 1 for Clothing or 2 for Electronics:  ");
                    scanner.next();
                }
                int type = scanner.nextInt();
                if (type == 2 || type == 1) {

                    System.out.print("Enter product ID: ");
                    String id = scanner.next();

                    //if false it wont run, user will be asked to enter
                    if (isIDUnique(id, productList)) {

                        //validation for name, only letters
                        System.out.print("Enter product name: ");
                        while (!scanner.hasNext("[a-zA-Z]+")) {
                            System.out.println("Invalid input. Please enter a valid name for clothing: ");
                            scanner.next();
                        }
                        String name = scanner.next();

                        //getting no of items
                        System.out.print("Enter number of available items: ");
                        while (!scanner.hasNextInt()) {
                            System.out.println("Invalid input. Please enter a valid number of available items: ");
                            scanner.next();
                        }
                        int availableItems = scanner.nextInt();

                        //price of product
                        System.out.print("Enter product price: ");
                        while (!scanner.hasNextDouble()) {
                            System.out.println("Invalid input. Please enter a valid product price: ");
                            scanner.next();
                        }
                        double price = scanner.nextDouble();

                        if (type == 1) {
                            //clothing size validation thru enum
                            System.out.print("Enter clothing size (XS, S, M, L, XL, XXL): ");
                            String size = scanner.next();
                            String[] validSizes = {"XS", "S", "M", "L", "XL", "XXL"};
                            boolean isValidSize = Arrays.asList(validSizes).contains(size.toUpperCase());
                            while (!isValidSize) {
                                System.out.println("Invalid size. Please enter a valid size (XS, S, M, L, XL, XXL): ");
                                size = scanner.next();
                                isValidSize = Arrays.asList(validSizes).contains(size.toUpperCase());
                            }

                            //colour validation thru string properties
                            System.out.print("Enter clothing color: ");
                            while (!scanner.hasNext("[a-zA-Z]+")) {
                                System.out.println("Invalid input. Please enter a valid clothing color: ");
                                scanner.next();
                            }
                            String color = scanner.next();

                            Clothing clothing = new Clothing(id, name, availableItems, price, size, color);
                            shoppingManager.addProduct(clothing, productList);
                        } else if (type == 2) {
                            //getting electronics brand
                            System.out.print("Enter electronics brand: ");
                            String brand = scanner.next();

                            //obtaining warranty period in
                            System.out.print("Enter warranty period in months: ");
                            while (!scanner.hasNextInt()) {
                                System.out.println("Invalid input. Please enter a valid warranty period in months: ");
                                scanner.next();
                            }
                            int warrantyPeriod = scanner.nextInt();

                            Electronics electronics = new Electronics(id, name, availableItems, price, brand, warrantyPeriod);
                            shoppingManager.addProduct(electronics, productList);
                        }
                        break; //product added succesfully
                    } else {
                        System.out.println("Product with this ID already exists. Please enter a unique ID.");
                    }
                } else {
                    System.out.print("Invalid input. \n" +
                            "Please enter a valid \nEnter 1 for Clothing or 2 for Electronics ");
                    //scanner.next(); // Consume the invalid input
                }
            }
        }

        // delete product
        private void deleteProductFromConsole(List<Product> productList) {
            System.out.print("Enter product ID: ");
            String id = scanner.next();

            // Check if the product ID is unique
            if(isIDUnique(id, productList)){
                System.out.println("Product not found. No deletion performed.");
            } else{
                // backend.Product ID is found, proceed with deletion
                shoppingManager.deleteProduct(id, productList);
                System.out.println("Product deleted successfully.");
            }
        }

        // to check if the ID is unique
        private boolean isIDUnique(String id,List<Product> productList){
        for (Product product : productList){
            if(product.getProductID().equals(id)){
                return false; //if id exist, false returned so user will be asked to enter again
            }
        }
        return true; //id doesnt exist
    }
}


