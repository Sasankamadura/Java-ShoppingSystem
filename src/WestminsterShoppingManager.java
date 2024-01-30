import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {        //implementing ShoppingManager interface

    private ArrayList<Product> productList;         // List to store products in the shopping system

    public WestminsterShoppingManager() {
        this.productList = new ArrayList<Product>();
    }

    @Override
    public void addProductToSystem(Product product) {       // Implementation of the addProductToSystem method from the ShoppingManager interface

        // Check if the product ID is unique and the maximum number of products in the system is not exceeded
        if (IsIdUnique(product.getProductId()) && productList.size() <= 50) {
            productList.add(product);
            System.out.println(product.getProductName()+" added to the system");
        } else if (productList.size() > 50) {
            System.out.println("Product can not add.\nMaximum products that can store in the system is 50");
        }
        else {
            System.out.println("Product can not add.\nEntered product is already available...");
        }
    }

    @Override
    public void removeProductFromSystem(String productId) {         // Implementation of the removeProductFromSystem method from the ShoppingManager interface

        boolean found = false;

        if (productList.isEmpty()) {
            System.out.println("Shop is empty..");
        } else {
            for (Product product : productList) {
                if (product.getProductId().equals(productId)) {
                    String tempProductName = product.getProductName();
                    productList.remove(product);

                    // Display information based on product type
                    if (product instanceof Electronics) {
                        System.out.println("Type: Electronics");
                        System.out.println("Product " + tempProductName
                                + " that have " + productId
                                + " Id removed from the shop...");
                        found = true;
                        break;

                    } else if (product instanceof Clothing) {
                        System.out.println("Type: Clothing");
                        System.out.println("Product " + tempProductName
                                + " that have " + productId
                                + " Id removed from the shop...");
                        found = true;
                        break;
                    }
                }
            }
        }
        if (!found) {
            System.out.println("Product with ID " + productId + " not found..");
        }
    }

    @Override
    public void printAllProductsInTheShop() {                // Implementation of the printAllProductsInTheShop method from the ShoppingManager interface
        if (productList.isEmpty()) {
            System.out.println("Product list is empty..");
        } else {
            System.out.println("----Product list----");

            // Sort the product list based on product IDs
            Collections.sort(productList, Comparator.comparing(Product::getProductId));

            // Display product information based on type
            for (Product product : productList) {
                if (product instanceof Electronics) {
                    System.out.println("Type: Electronics " +
                            "\nProduct name: " + product.getProductName()
                            + "\nProduct ID: " + product.getProductId()
                            + "\nPrice: " + product.getPrice() + " £"
                            + "\nBrand: " + ((Electronics) product).getBrand()
                            + "\nWarranty period: " + ((Electronics) product).getWarrantyPeriod()
                            + " weeks \nNumber of available Items: " + product.getNumOfItemsAvailable() + "\n");
                } else if (product instanceof Clothing) {
                    System.out.println("Type:Clothing " +
                            "\nProduct name: " + product.getProductName()
                            + "\nProduct ID: " + product.getProductId()
                            + "\nPrice: " + product.getPrice() +" £"
                            + "\nSize: " + ((Clothing) product).getSize()
                            + "\nColour: " + ((Clothing) product).getColour()
                            + "\nNumber of available Items: " + product.getNumOfItemsAvailable() + "\n");
                }
            }
        }
    }

    // Implementation of the saveToFile method from the ShoppingManager interface
    @Override
    public void saveToFile() {

        try (PrintWriter writer = new PrintWriter(new FileWriter("ProductListOfSystem.txt"))) {

            boolean productsAvailable = false;

            // Write product information to a text file
            for (Product product : productList) {
                if (product instanceof Electronics) {
                    // Display and write Electronics product details
                    writer.println("Type: Electronics " +
                            "\nProduct name: " + product.getProductName()
                            + "\nProduct ID: " + product.getProductId()
                            + "\nPrice: Rs" + product.getPrice()
                            + "\nBrand: " + ((Electronics) product).getBrand()
                            + "\nWarranty period: " + ((Electronics) product).getWarrantyPeriod()
                            + "\nNumber of available Items: " + product.getNumOfItemsAvailable()
                            + "\n---------------");
                    productsAvailable = true;
                } else if (product instanceof Clothing) {       // Display and write Clothing product details
                    writer.println("Type: Clothing " +
                            "\nProduct name: " + product.getProductName()
                            + "\nProduct ID: " + product.getProductId()
                            + "\nPrice: Rs" + product.getPrice()
                            + "\nSize: " + ((Clothing) product).getSize()
                            + "\nColour: " + ((Clothing) product).getColour()
                            + "\nNumber of available Items: " + product.getNumOfItemsAvailable()
                            + "\n---------------");
                    productsAvailable = true;
                }
            }
            if (productsAvailable){
                System.out.println("Data saved successfully..");
            }
            else {
                System.out.println("No Products in the system");
            }


        } catch (IOException e) {
            System.out.println("Can not create a txt file.");
        }
    }

    // Implementation of the loadFromFile method from the ShoppingManager interface
    @Override
    public void loadFromFile() {


        try (BufferedReader reader = new BufferedReader(new FileReader("ProductListOfSystem.txt"))){


            String line;
            Product currentProduct = null;

            // Read product information from a text file
            while ((line = reader.readLine()) != null) {

                if (line.equals("---------------")) {
                    if (currentProduct != null) {
                        productList.add(currentProduct);
                        currentProduct = null;
                    }
                    continue;
                }


                String[] parts = line.split(": ");

                if (parts.length == 2) {
                    String attributeName = parts[0].trim();
                    String attributeValue = parts[1].trim();

                    if (attributeName.equals("Type") && attributeValue.equals("Electronics") && currentProduct == null) {
                        currentProduct = new Electronics(null, null, 0, 0.0, null, 0);
                    } else if (attributeName.equals("Type") && attributeValue.equals("Clothing") && currentProduct == null) {
                        currentProduct = new Clothing(null, null, 0, 0.0, null, null);
                    }


                    // Set attributes of the product based on the loaded information
                    switch (attributeName) {

                        case "Type":
                            break;
                        case "Product ID":
                            if (currentProduct != null) {
                                currentProduct.setProductId(attributeValue);
                            }
                            break;
                        case "Product name":
                            if (currentProduct != null) {
                                currentProduct.setProductName(attributeValue);
                            }
                            break;
                        case "Number of available Items":
                            if (currentProduct != null) {
                                currentProduct.setNumOfItemsAvailable(Integer.parseInt(attributeValue));
                            }
                            break;
                        case "Price":
                            if (currentProduct != null) {
                                currentProduct.setPrice(Double.parseDouble(attributeValue.substring(2)));  // Removing "$" and parsing as double
                            }
                            break;
                        case "Brand":
                            if (currentProduct != null) {
                                assert currentProduct instanceof Electronics;
                                ((Electronics) currentProduct).setBrand(attributeValue);
                            }
                            break;
                        case "Warranty period":
                            if (currentProduct != null) {
                                assert currentProduct instanceof Electronics;
                                ((Electronics) currentProduct).setWarrantyPeriod(Integer.parseInt(attributeValue));
                            }
                            break;

                        case "Size":
                            if (currentProduct != null) {
                                assert currentProduct instanceof Clothing;
                                ((Clothing) currentProduct).setSize(attributeValue);
                            }
                            break;
                        case "Colour":
                            if (currentProduct != null) {
                                assert currentProduct instanceof Clothing;
                                ((Clothing) currentProduct).setColour(attributeValue);
                            }
                            break;
                    }
                }
            }

            System.out.println("Information loaded to the system.");


        } catch (IOException e) {
            System.out.println("Error lording the txt file..");
        }
    }

    //Method that check if a product ID is unique
    boolean IsIdUnique(String productId){
        for (Product product : productList) {
            if (product.getProductId().equals(productId)) {
                return false;
            }
        }
        return true;
    }

    // Method to open the GUI
    public void openGui(){
        new GUIWestminsterShoppingCenter(productList);
    }



    // Main method to run the program
    public static void main(String[] args){

        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        Scanner scanner = new Scanner(System.in);


        boolean runStatus = true;       // Flag to control the program loop

        // Main program loop
        while (runStatus){


            try{
                // Display manager menu options
                System.out.print("=====Manager Menu=====\n" +
                        "1.Add a new product.\n" +
                        "2.Remove a product.\n" +
                        "3.Print all products.\n" +
                        "4.Save data to a file.\n" +
                        "5.Load data from a file.\n" +
                        "6.Open Shopping center GUI.\n" +
                        "7.Exit from the program.\n" +
                        "Enter the option number : ");


                int option = scanner.nextInt();     // Get user input for menu


                switch (option){            // Switch statement to handle user choice
                    case 1 :
                        addAProduct(scanner,shoppingManager);
                        break;
                    case 2 :
                        deleteAProduct(scanner,shoppingManager);
                        break;
                    case 3:
                        shoppingManager.printAllProductsInTheShop();
                        break;
                    case 4:
                        shoppingManager.saveToFile();
                        break;
                    case 5:
                        shoppingManager.loadFromFile();
                        break;
                    case 6:
                        shoppingManager.openGui();
                        break;
                    case 7:
                        System.out.println("Exiting the program...\nGood bye..");
                        runStatus = false;
                        break;
                    default:
                        System.out.println("Invalid option...\nTry again..\n");
                        break;
                }


            } catch (Exception e) {         // catch invalid input
                System.out.println("Invalid input!\nTry again");
                scanner.next();
            }
        }
    }

    // Method to add a product
    static void addAProduct(Scanner scanner, WestminsterShoppingManager shoppingManager){

        System.out.println("Enter 1 to add Electronic product.");       //Ask to enter category
        System.out.println("Enter 2 to add Clothing product.");

        System.out.print("Enter product type :");

        try{                                            // Get product details
            int productType = scanner.nextInt();

            if (productType == 1 || productType == 2){

                System.out.print("Enter product ID :");
                String productId = scanner.next();

                System.out.print("Enter product name :");
                String productName = scanner.next();

                System.out.print("Enter number of items that available :");
                int numOfItemsAvailable = scanner.nextInt();

                if (numOfItemsAvailable <=0){                       // Check if the available items number correct
                    System.out.println("really.. Add 1 or more items! ");
                    addAProduct(scanner, shoppingManager);
                }


                System.out.print("Enter price :");
                double price = scanner.nextDouble();

                if (productType == 1){
                    System.out.print("Enter the Brand name :");
                    String brand = scanner.next();

                    System.out.print("Enter the warranty period (Weeks) :");
                    int warrantyPeriod = scanner.nextInt();


                    // Add electronic product to the system
                    shoppingManager.addProductToSystem(new Electronics(productId,productName,numOfItemsAvailable,price,brand,warrantyPeriod));

                }
                else{
                    System.out.print("Enter the size :");
                    String size = scanner.next();

                    if (containsNumber(size)) {
                        System.out.println("Size cannot contain numbers!\nTry again");
                        addAProduct(scanner, shoppingManager);
                    }

                    System.out.print("Enter the colour :");
                    String colour = scanner.next();

                    if (containsNumber(colour)) {
                        System.out.println("Colour cannot contain numbers!\nTry again");
                        addAProduct(scanner, shoppingManager);
                    }

                    // Add clothing product to the system
                    shoppingManager.addProductToSystem(new Clothing(productId,productName,numOfItemsAvailable,price,size,colour));
                }
            }
            else {
                System.out.println("Invalid product type.!\nTry again..");
            }
        } catch (Exception e) {         // invalid input handler
            System.out.println("Invalid input!\nTry again");
            scanner.next();
            addAProduct(scanner, shoppingManager);
        }
    }

    // Method to delete a product interactively
    static void deleteAProduct(Scanner scanner,WestminsterShoppingManager shoppingManager){

        System.out.print("Enter Product ID that needs to delete :");
        String removingProductId = scanner.next();
        shoppingManager.removeProductFromSystem(removingProductId);
    }

    // Method to check if a string contains a number
    private static boolean containsNumber(String inputString) {
        // Check if the string contains at least one digit
        for (char character : inputString.toCharArray()) {
            if (Character.isDigit(character)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }
}
