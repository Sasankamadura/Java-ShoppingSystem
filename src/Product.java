abstract class Product {            // Abstract class that inherit to Electronics and Clothing

    //Common attributes for subclasses
    private String productId;
    private String productName;
    private int numOfItemsAvailable;
    private double price;

    // Constructor for the Product class
    public Product(String productId, String productName, int numOfItemsAvailable, double price) {       //constructor of the Product class
        this.productId = productId;
        this.productName = productName;
        this.numOfItemsAvailable = numOfItemsAvailable;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }  // Getter method to retrieve the product ID

    public String getProductName() {
        return productName;
    }       // Getter method to retrieve the product name

    public int getNumOfItemsAvailable() {       // Getter method to retrieve the number of items available
        return numOfItemsAvailable;
    }

    public double getPrice() {          // Getter method to retrieve the price of the product
        return price;
    }
    public void setProductId(String productId) {        // Setter method to set the product ID
        this.productId = productId;
    }

    public void setProductName(String productName) {        // Setter method to set the product name
        this.productName = productName;
    }

    public void setNumOfItemsAvailable(int numOfItemsAvailable) {       // Setter method to set the number of items available
        this.numOfItemsAvailable = numOfItemsAvailable;
    }

    public void setPrice(double price) {            // Setter method to set the price of the product
        this.price = price;
    }
}
