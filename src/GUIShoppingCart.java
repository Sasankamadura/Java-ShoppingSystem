import javax.swing.*;                               //Import necessary java libraries
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class GUIShoppingCart extends JFrame {       //extends the JFrame class

    // Declare GUI components
    JTable cartTable;
    JScrollPane scrollPane;
    JLabel totalLabel;
    JLabel firstPurchaseDiscountLabel;
    JLabel threeItemsDiscountLabel;
    JLabel finalTotalLabel;
    JButton exit;
    GUIShoppingCart(ArrayList<Product> shoppingCartList) {          // Constructor for GUI shopping cart

        boolean productsFromSameCatogory = false;
        // Create a panel and set its background color
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        this.add(panel);

        // Create a DefaultTableModel to manage the data for the JTable
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Product");
        tableModel.addColumn("Quantity");
        tableModel.addColumn("Price (£)");

        DecimalFormat decimalFormat = new DecimalFormat("#.##");                // Create a DecimalFormat for formatting currency values

        // Iterate through the shopping cart list  and update the table
        for (Product product : shoppingCartList) {
            boolean productAlreadyExists = false;

            // Check if the product is already in the table, then update quantity and price
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                String productName = (String) tableModel.getValueAt(row, 0);

                if (productName.equals(getFormattedProductName(product))) {

                    //if the product is already in the table quantity of the product  increase
                    int quantity = (int) tableModel.getValueAt(row, 1);
                    double price = (double) tableModel.getValueAt(row, 2);

                    tableModel.setValueAt(quantity + 1, row, 1);            //set updated quantity
                    tableModel.setValueAt((price + product.getPrice()), row, 2);        //set updated price
                    productAlreadyExists = true;

                    if (quantity>=2){           //checks that cart has 3 items with same product
                        productsFromSameCatogory = true;
                    }
                    break;

                }
            }
            // If the product is not in the shopping cart, add a new row
            if (!productAlreadyExists) {
                tableModel.addRow(new Object[]{getFormattedProductName(product), 1, product.getPrice()});
            }
        }

        cartTable = new JTable(tableModel);         // Create a JTable
        Font tableFont = new Font("Arial", Font.PLAIN, 16);
        cartTable.setFont(tableFont);               //set font of the table
        cartTable.setRowHeight(50);                 //set row height

        // Set default cell renderer for center alignment of the table
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        cartTable.setDefaultRenderer(Object.class, centerRenderer);

        scrollPane = new JScrollPane(cartTable);            // Create a JScrollPane to display the table
        //cartTable.setGridColor(Color.BLACK);

        panel.setLayout(null);  // Use null layout for manual component placement
        scrollPane.setBounds(30, 50, 530, 280);

        panel.add(scrollPane);          //Add the scroll pane to the panel

        // Calculate subtotal, discounts, and final total
        double subtotal = calculateSubtotal(tableModel);
        double firstPurchaseDiscount = 0.1 * subtotal;

        double threeItemsDiscount = 0.0;
        if (productsFromSameCatogory){
            threeItemsDiscount = (subtotal >= 3) ? 0.2 * subtotal : 0.0;
        }
        double finalTotal = subtotal - firstPurchaseDiscount - threeItemsDiscount;

        // Display total, discounts and final total details separately
        totalLabel = new JLabel("Total: " + decimalFormat.format(subtotal)+" £");
        firstPurchaseDiscountLabel = new JLabel("First Purchase Discount (10%): -" + decimalFormat.format(firstPurchaseDiscount)+" £");
        threeItemsDiscountLabel = new JLabel("Three Items Discount (20%): -" + decimalFormat.format(threeItemsDiscount)+" £");
        finalTotalLabel = new JLabel("Final Total: " + decimalFormat.format(finalTotal)+" £");

        // Set bounds and formatting for those labels
        totalLabel.setBounds(150, 340, 300, 30);
        firstPurchaseDiscountLabel.setBounds(150, 370, 300, 30);
        threeItemsDiscountLabel.setBounds(150, 400, 300, 30);
        finalTotalLabel.setBounds(150, 430, 300, 30);

        Font font = new Font("Arial", Font.PLAIN, 15);

        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);        //set alignment
        totalLabel.setFont(font);                                   //set font

        firstPurchaseDiscountLabel.setHorizontalAlignment(SwingConstants.RIGHT);       //set alignment
        firstPurchaseDiscountLabel.setFont(font);                   //set font

        threeItemsDiscountLabel.setHorizontalAlignment(SwingConstants.RIGHT);       //set alignment
        threeItemsDiscountLabel.setFont(font);                      //set font

        font = new Font("Arial", Font.BOLD, 16);        //change plain font to bold font
        finalTotalLabel.setHorizontalAlignment(SwingConstants.RIGHT);           //set alignment
        finalTotalLabel.setFont(font);                              //set font

        exit = new JButton("Exit");
        exit.setBounds(10,370,300,30);
        panel.add(exit);
        //exit.actionLisner(e->);

        panel.add(totalLabel);                          // Add labels to the panel
        panel.add(firstPurchaseDiscountLabel);
        panel.add(threeItemsDiscountLabel);
        panel.add(finalTotalLabel);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);             // Set JFrame properties
        this.setTitle("Shopping Cart");
        this.setVisible(true);
        this.setResizable(false);
        this.setSize(600, 600);     // Adjusted the height and width of the frame
    }



    // Method that format the product name based on its type
    private String getFormattedProductName(Product product) {
        if (product instanceof Electronics) {
            return product.getProductId() + ", " + product.getProductName() + ", "
                    + ((Electronics) product).getWarrantyPeriod() + " weeks, " + ((Electronics) product).getBrand();
        } else if (product instanceof Clothing) {
            return product.getProductId() + ", " + product.getProductName() + ", "
                    + ((Clothing) product).getColour() + ", " + ((Clothing) product).getSize();
        }
        return "";
    }


    //Method that calculate subtotal of the products in the table
    private double calculateSubtotal(DefaultTableModel tableModel) {
        double subtotal = 0.0;

        for (int row = 0; row < tableModel.getRowCount(); row++) {          //Iterate for all the rows in the table
            subtotal += (double) tableModel.getValueAt(row, 2);
        }
        return subtotal;
    }
}
