import java.util.*;

public class ShoppingCart {
    private  ArrayList<Product> products;       // List that store cart products

    public ShoppingCart(ArrayList<Product> shoppingCartList) {      // Constructor for the ShoppingCart class
        this.products = shoppingCartList;
    }

    public void addProduct(Product product){
        products.add(product);
    }   // Method to add a product to the shopping cart
    public void removeProduct(Product product){
        products.remove(product);
    }   // Method to remove a product
    public double getTotalCost() {      // Method to calculate the total cost
        double totalCost = 0;
        for (Product product : products) {
            totalCost += product.getPrice();
        }
        return totalCost;
    }
}
