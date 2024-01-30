import static org.junit.jupiter.api.Assertions.*;       // Import necessary JUnit classes for testing
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;           // Import necessary Java IO classes
import java.io.PrintStream;

public class WestminsterShoppingManagerTest {

    @Test
    public void testAddProductToSystem() {      // Class for testing the WestminsterShoppingManager class
        // Test case for adding products to the shopping system
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();

        // Create sample electronic and clothing products
        Electronics electronicProduct = new Electronics("E001", "Laptop", 2, 999.99, "Dell", 12);
        Clothing clothingProduct = new Clothing("C001", "Shirt", 5, 29.99, "M", "Blue");

        shoppingManager.addProductToSystem(electronicProduct);      // Add products to the system
        shoppingManager.addProductToSystem(clothingProduct);

        // Check that the product list contains the added products
        assertTrue(shoppingManager.getProductList().contains(electronicProduct));
        assertTrue(shoppingManager.getProductList().contains(clothingProduct));
    }

    // Test case for removing a product from the shopping system
    @Test
    public void testRemoveProductFromSystem() {
        // Create an instance of WestminsterShoppingManager
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        //sample electronic product
        Electronics electronicProduct = new Electronics("E001", "Laptop", 2, 999.99, "Dell", 12);

        // Add to the system
        shoppingManager.addProductToSystem(electronicProduct);
        assertTrue(shoppingManager.getProductList().contains(electronicProduct));

        shoppingManager.removeProductFromSystem("E001");        // Remove the electronic product from the system

        // Assert that the product list no longer contains the electronic product
        assertFalse(shoppingManager.getProductList().contains(electronicProduct));
    }

    // Test case for printing all products in the shopping system
    @Test
    public void testPrintAllProductsInTheShop() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        Electronics electronicProduct = new Electronics("E001", "Laptop", 2, 999.99, "Dell", 12);

        shoppingManager.addProductToSystem(electronicProduct);


        // Redirect System.out to capture printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        shoppingManager.printAllProductsInTheShop();        // Call the method to print all

        // Reset System.out
        System.setOut(System.out);

        String printedOutput = outputStream.toString();
        assertTrue(printedOutput.contains("Type: Electronics"));
    }

    @Test           // Test case for checking if product ID is unique
    public void testIsIdUnique() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        Electronics electronicsProduct = new Electronics("E001", "Laptop", 2, 999.99, "Dell", 12);

        assertTrue(shoppingManager.IsIdUnique("E001"));

        shoppingManager.addProductToSystem(electronicsProduct);  // Add the electronic product to the system

        assertFalse(shoppingManager.IsIdUnique("E001"));        // Assert that the product ID is no longer unique
    }

    @Test
    public void testGUI() {                 // Test case for checking if open GUI or not
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        shoppingManager.openGui();
    }

    @Test
    public void testLoadTextFile() {        //Test case for Load text file
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        shoppingManager.loadFromFile();
    }

    @Test
    public void testSaveTextFile() {        //test case for save text file
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        shoppingManager.saveToFile();
    }

}
