public class Electronics extends Product{       //extends the product class

    private String brand;               //instance variables for size and colour
    private int warrantyPeriod;

    public Electronics(String productId, String productName, int numOfItemsAvailable, double price,String brand,int warrantyPeriod) {
        super(productId, productName, numOfItemsAvailable, price);      // Call the constructor of the superclass (Product)
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;           //initialize brand and warranty
    }

    public String getBrand() {
        return brand;               //setter and getter of the Electronic brand
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }                               //setter and getter of the warrenty period
    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }
}
