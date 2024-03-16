package backend;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class WestminsterShoppingManagerTest {
    private WestminsterShoppingManager shoppingManager;
    private List<Product> productList;

    @Before
    public void setUp() {
        // Initialize the WestminsterShoppingManager and productList before each test
        shoppingManager = new WestminsterShoppingManager();
        productList = new ArrayList<>();
    }

    @Test
    public void testAddProductClothing() {
        Clothing clothing = new Clothing("C001", "Shirt", 10, 19.99, "M", "Blue");
        shoppingManager.addProduct(clothing, productList);
        assertTrue(productList.contains(clothing));
    }

    @Test
    public void testAddProductElectronics() {
        Electronics electronics = new Electronics("E001", "Laptop", 5, 899.99, "Dell", 12);
        shoppingManager.addProduct(electronics, productList);
        assertTrue(productList.contains(electronics));
    }

    @Test
    public void testDeleteProduct() {
        // Add a product to the list
        Clothing clothing = new Clothing("C001", "Shirt", 10, 19.99, "M", "Blue");
        productList.add(clothing);

        // Delete the product
        shoppingManager.deleteProduct("C001", productList);

        // Check if product is removed 
        assertFalse(productList.contains(clothing));
    }

}