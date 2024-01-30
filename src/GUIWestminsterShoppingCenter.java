import javax.swing.*;                           //Import necessary java libraries
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;

public class GUIWestminsterShoppingCenter extends JFrame {      //extends the JFrame class

    // Declare GUI components
    private JPanel panel1;
    private JScrollPane scrollPane;
    private JPanel panel2;
    private JPanel panel3;
    private JButton shoppingCartButton;
    private JButton addToShoppingCartButton;
    private JComboBox<String> productCategory;
    private JTable productTable;

    private JLabel selectedProductLabel;
    private JTextArea productDetailsTextArea;
    private JLabel categoryLabel;


    private ArrayList<Product> productList;             //array lists that holds products
    private ArrayList<Product> shoppingCartList;

    GUIWestminsterShoppingCenter(ArrayList<Product> productList) {      // Constructor of the westminster shopping center GUI
        this.productList = productList;
        shoppingCartList = new ArrayList<Product>();            //list that hold products when user add products to cart

        ShoppingCart shoppingCart = new ShoppingCart(shoppingCartList);

        this.setLayout(null);           // Set the layout manager to null for absolute positioning


        // Panel 1 - panel that display all product table
        panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setPreferredSize(new Dimension(600, 500));
        panel1.setBackground(Color.LIGHT_GRAY);
        panel1.setBounds(0, 0, 600, 350);

        //create "Shopping Cart" button
        shoppingCartButton = new JButton("Shopping Cart");
        shoppingCartButton.addActionListener(e -> new GUIShoppingCart(shoppingCartList));
        shoppingCartButton.setBounds(420, 20, 130, 30);
        shoppingCartButton.setFocusable(false);
        panel1.add(shoppingCartButton);

        // Category label
        categoryLabel = new JLabel("Select Product Category");
        categoryLabel.setBounds(30, 50, 180, 20);
        panel1.add(categoryLabel);

        //Create combo box
        String[] comboBoxItems = {"All", "Electronic", "Clothing"};
        productCategory = new JComboBox<>(comboBoxItems);
        productCategory.addActionListener(this::actionPerformed);
        productCategory.setBounds(200, 50, 180, 40);
        panel1.add(productCategory);

        createTable(productList);               // Create the product table
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Panel 2 - Details Panel
        panel2 = new JPanel();
        panel2.setLayout(null);
        panel2.setPreferredSize(new Dimension(600, 370));
        panel2.setBackground(Color.yellow);
        panel2.setBounds(0, 350, 600, 370);

        // Panel 3 - Details Sub-panel
        panel3 = new JPanel();
        panel3.setLayout(null);
        panel3.setPreferredSize(new Dimension(450, 250));
        panel3.setBackground(Color.white);
        panel3.setBounds(70, 50, 450, 250);
        panel2.add(panel3, BorderLayout.CENTER);


        //creating "Add to shopping cart" button
        addToShoppingCartButton = new JButton("Add to shopping cart");
        addToShoppingCartButton.setBounds(225, 315, 170, 30);
        addToShoppingCartButton.setFocusable(false);
        panel2.add(addToShoppingCartButton);

        // Action listener for "Add to shopping cart" button
        addToShoppingCartButton.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();        // Get the selected row from the product table
            if (selectedRow != -1) {
                String productID = productTable.getValueAt(selectedRow, 0).toString();

                for (Product product : productList) {   // Iterate through the productList to find the selected product
                    if (product.getProductId().equals(productID)) {
                        if (product.getNumOfItemsAvailable()==0){       //// Display a message if no items are available
                            JOptionPane.showMessageDialog(GUIWestminsterShoppingCenter.this,
                                    "Sorry! There are no more items from this on stock.",
                                    "No more items",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        shoppingCart.addProduct(product);       // Add the selected product to the shopping cart using shopping cart class
                        product.setNumOfItemsAvailable(product.getNumOfItemsAvailable()-1);
                        break;      // Decrease the available quantity of the selected product
                    }
                }
                // Display a success message after adding the product to the cart
                JOptionPane.showMessageDialog(GUIWestminsterShoppingCenter.this,
                        "Selected product added to the cart!","Item added",JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(GUIWestminsterShoppingCenter.this,
                        "Please select a product before adding to the shopping cart.",
                        "No Product Selected",JOptionPane.WARNING_MESSAGE); // Display a message if no product is selected
            }

        });


                                //components for displaying selected product details
        Font font = new Font("Arial", Font.BOLD, 20);
        selectedProductLabel = new JLabel("Selected Product - Details");
        selectedProductLabel.setFont(font);
        selectedProductLabel.setBounds(10, 10, 250, 20);
        panel3.add(selectedProductLabel, BorderLayout.NORTH);

        productDetailsTextArea = new JTextArea();
        productDetailsTextArea.setBounds(10, 40, 430, 200);
        productDetailsTextArea.setEditable(false);

        font = new Font("Arial", Font.PLAIN, 16);
        productDetailsTextArea.setFont(font);
        panel3.add(productDetailsTextArea, BorderLayout.CENTER);

        this.add(panel1);
        this.add(panel2);

        // Set up frame properties
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Westminster Shopping Center");
        this.setVisible(true);
        this.setResizable(false);
        this.setSize(600, 750);             //set frame height and width

        // Call actionPerformed with an initial state to populate the table with all products
        actionPerformed(new ActionEvent(productCategory, ActionEvent.ACTION_PERFORMED, null));

        // Set up table cell renderer
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                // Custom rendering for table cells
                Component cellComponent = super.getTableCellRendererComponent(productTable, value, isSelected, hasFocus, row, column);

                // Highlight low stock items in red
                if (row < productList.size()) {
                    Product product = productList.get(row);

                    if (product.getNumOfItemsAvailable() <= 2) {
                        cellComponent.setBackground(Color.red);
                    }
                    else {
                        cellComponent.setBackground(Color.WHITE);
                    }

                }
                // Highlight selected row
                if (isSelected){
                    cellComponent.setBackground(table.getSelectionBackground());
                    cellComponent.setForeground(table.getSelectionForeground());
                }
                return cellComponent;
            }
        };

        productTable.setDefaultRenderer(Object.class,renderer);       //srt renderer of the product table
    }

    // Method to create and populate the product table
    private void createTable(ArrayList<Product> productList) {
        DefaultTableModel tableModel = new DefaultTableModel();

        // Set up table columns
        tableModel.addColumn("Product ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Category");
        tableModel.addColumn("Price (£)");
        tableModel.addColumn("Info");

        // Sort the product list based on product IDs
        Collections.sort(productList, Comparator.comparing(Product::getProductId));


        // Add rows to the table based on product type
        for (Product product : productList) {
            if (product instanceof Electronics) {
                tableModel.addRow(new Object[]{product.getProductId(), product.getProductName(), "Electronics",
                        String.valueOf(product.getPrice()), ((Electronics) product).getBrand() + "," +
                        ((Electronics) product).getWarrantyPeriod() + " weeks"});
            } else if (product instanceof Clothing) {
                tableModel.addRow(new Object[]{product.getProductId(), product.getProductName(), "Clothing",
                        String.valueOf(product.getPrice()), ((Clothing) product).getColour() + "," +
                        ((Clothing) product).getSize()});
            }
        }


        // Create the JTable with the populated table model
        productTable = new JTable(tableModel);
        Font tableFont = new Font("Arial", Font.PLAIN, 16);
        productTable.setFont(tableFont);
        productTable.setRowHeight(40);

        // Add a list selection listener to update the product details area
        productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (!e.getValueIsAdjusting()) {
                    // Get the selected row from the productTable
                    int selectedRow = productTable.getSelectedRow();

                    if (selectedRow != -1) {
                        // Get the product ID from the selected row
                        String productID = productTable.getValueAt(selectedRow, 0).toString();
                        String detailsText = null;

                        for (Product product : productList) {
                            // Check if the product ID matches
                            if (product.getProductId().equals(productID)) {
                                // Generate detailsText based on the type of product (Electronics or Clothing)
                                if (product instanceof Electronics) {
                                    detailsText = "Product ID: " + productID + "\n"
                                            + "Name: " + product.getProductName() + "\n"
                                            + "Category: Electronics\n"
                                            + "Price: " + product.getPrice() + "\n"
                                            + "Brand: " + ((Electronics) product).getBrand() + "\n"
                                            + "Warranty period: " + ((Electronics) product).getWarrantyPeriod() + " weeks\n"
                                            + "Number of available Items: " + product.getNumOfItemsAvailable();
                                } else if (product instanceof Clothing) {
                                    detailsText = "Product ID: " + productID + "\n"
                                            + "Name: " + product.getProductName() + "\n"
                                            + "Category: Clothing\n"
                                            + "Price: " + product.getPrice() + "\n"
                                            + "Size: " + ((Clothing) product).getSize() + "\n"
                                            + "Colour: " + ((Clothing) product).getColour() + "\n"
                                            + "Number of available Items: " + product.getNumOfItemsAvailable();
                                }
                                    // Set the detailsText in the productDetailsTextArea
                                productDetailsTextArea.setText(detailsText);
                            }
                        }
                    }
                }
            }
        });

        // Center-align cell contents
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        productTable.setDefaultRenderer(Object.class, centerRenderer);


        // Set up the scroll pane and add it to panel1
        scrollPane = new JScrollPane(productTable);
        productTable.setGridColor(Color.BLACK);

        panel1.add(scrollPane);
        scrollPane.setBounds(30, 100, 530, 220);

    }

    // Action performed method for handling category selection
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == productCategory) {
            String selectedCategory = productCategory.getSelectedItem().toString();

            // Update the table based on the selected category
            DefaultTableModel tableModel = (DefaultTableModel) productTable.getModel();
            tableModel.setRowCount(0);

            // Check if the selected category is "All" or matches the product's category
            for (Product product : productList) {
                if (selectedCategory.equals("All") ||
                        (selectedCategory.equals("Electronic") && product instanceof Electronics) ||
                        (selectedCategory.equals("Clothing") && product instanceof Clothing)) {

                    String[] rowData;

                    // Generate rowData based on the type of product (Electronics or Clothing)
                    if (product instanceof Electronics) {
                        rowData = new String[]{product.getProductId(), product.getProductName(), "Electronics",
                                String.valueOf(product.getPrice()), ((Electronics) product).getBrand() + "," +
                                ((Electronics) product).getWarrantyPeriod() + " weeks"};
                    } else if (product instanceof Clothing) {
                        rowData = new String[]{product.getProductId(), product.getProductName(), "Clothing",
                                String.valueOf(product.getPrice()), ((Clothing) product).getColour() + "," +
                                ((Clothing) product).getSize()};
                    } else {
                        continue;
                    }

                    // Add the generated rowData to the tableModel
                    tableModel.addRow(rowData);
                }
            }
        }
    }
}
