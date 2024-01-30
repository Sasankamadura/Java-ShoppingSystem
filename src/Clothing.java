public class Clothing extends Product {     //extend the product class
    private String size;            //instance variables for size and colour
    private String colour;

    public Clothing(String productId, String productName, int numOfItemsAvailable, double price, String size,String colour) {
        super(productId, productName, numOfItemsAvailable, price);      // Call the constructor of the superclass (Product)
        this.size = size;
        this.colour = colour;             //set size and colour
    }

    //setters and getters of the size and colour of the clothing

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }


}
