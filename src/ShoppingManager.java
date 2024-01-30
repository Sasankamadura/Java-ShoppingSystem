public interface ShoppingManager {
    void addProductToSystem(Product product);       // Method to add a product to the system

    void removeProductFromSystem(String productId);     // Method to remove a product from the system

    void printAllProductsInTheShop();       // Method to print all the products in the system

    void saveToFile();      // Method to save data in to a text file

    void loadFromFile();        //Method to load the data in to the system

}
