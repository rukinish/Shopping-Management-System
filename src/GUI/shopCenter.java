package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.table.TableRowSorter;

import backend.*;

import java.awt.*;
import java.util.List;


public class shopCenter extends JFrame {
    private final JComboBox<String> categoryComboBox;
    private final JTable tableProduct;
    private final DefaultTableModel tabelDetail;
    private final JTextArea selectedProductDetails;
    private final WestminsterShoppingManager manager;
    private final ShoppingCart cart;
    private User user;

    public shopCenter() {
        manager = new WestminsterShoppingManager();
        List<Product> productList = manager.loadFromFile();

        // Top panel 
       //JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("  Select Product Category  ");

        //drop down box with catagories
        categoryComboBox = new JComboBox<>(new String[] {"All", "Clothing", "Electronics"} );
        Dimension preferredSizeCCB = new Dimension(300, 30);
        categoryComboBox.setPreferredSize(preferredSizeCCB);
        categoryComboBox.addActionListener(e -> {
            String selectedCategory = (String) categoryComboBox.getSelectedItem();
            updateProductTable(selectedCategory); //method at the bottom
        });


        // In the ShopGUI 
        cart = new ShoppingCart();
        shopCart shoppingCart = new shopCart(cart);
        setSize(700, 600);

        //shopping
        JButton shoppingCartButton = new JButton("Shopping Cart");
        Dimension preferredSize = new Dimension(150, 30);
        shoppingCartButton.setPreferredSize(preferredSize);
        shoppingCartButton.addActionListener(e -> shoppingCart.showShoppingCart());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(titleLabel);
        topPanel.add(categoryComboBox);
        topPanel.add(shoppingCartButton);
        add(topPanel, BorderLayout.NORTH);

        //middle part
        JPanel middlePanel = new JPanel(new BorderLayout());
        //product table
        tabelDetail = new DefaultTableModel(new Object[]{"Product ID", "Name", "Category", "Price $", "Additonal Info"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // This causes all cells to be non-editable
                // https://stackoverflow.com/questions/1990817/how-to-make-a-jtable-non-editable
                return false;
            }
        };
        //sort table
        TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<>(tabelDetail);
        
        tableProduct = new JTable(tabelDetail);
        tableProduct.setRowSorter(tableRowSorter);
        tableProduct.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = tableProduct.getSelectedRow();
            if (selectedRow >= 0) {
                Product selectedProduct = (Product) tabelDetail.getValueAt(selectedRow, tabelDetail.getColumnCount() - 1);
                displayProductDetails(selectedProduct);
            }
        });

        JScrollPane scrollPane = new JScrollPane(tableProduct);
        middlePanel.add(scrollPane, BorderLayout.CENTER);

        //bottom panael
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Display section at the bottom with product details
        JPanel displayPanel = new JPanel(new BorderLayout());
        selectedProductDetails = new JTextArea();
        selectedProductDetails.setEditable(false);
        selectedProductDetails.setPreferredSize(new Dimension(200, 220));
        displayPanel.add(selectedProductDetails, BorderLayout.CENTER);

        //add to cart button
       // JPanel cartPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addToCartButton = new JButton("Add to Shopping Cart");
        addToCartButton.addActionListener(e -> {
            int selectedRow = tableProduct.getSelectedRow();

            //allow user to select more than one rows
            if (selectedRow >= 0) {
                //object of the selected row is being saved
                Product selectedProduct = (Product) tabelDetail.getValueAt(selectedRow, tabelDetail.getColumnCount() - 1);

                //checking if selected row items are in stock
                if (selectedProduct.getNoOfAvailItems() <= 0) {
                    JOptionPane.showMessageDialog(this, "This product is out of stock.", "Out of Stock",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    cart.addProduct(selectedProduct);
                    //decrease available items
                    selectedProduct.setNoOfAvailItems(selectedProduct.getNoOfAvailItems() - 1);

                    //change the main productlist as well, doing this coz productlist and cart are two diff things
                    for (Product product : productList) {
                        if (product.getProductID().equals(selectedProduct.getProductID())) {
                            product.setNoOfAvailItems(product.getNoOfAvailItems() - 1);
                            break;
                        }
                    }
                    manager.saveToFile(productList);
                    manager.loadFromFile();
                    JOptionPane.showMessageDialog(this, "Product added to the shopping cart.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "No product selected.");
            }
        });

        //logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            Login frame = new Login();
            frame.setTitle("Login");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            dispose();
        });

        // Add components to the bottom panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addToCartButton);
        buttonPanel.add(logoutButton);

        bottomPanel.add(displayPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add panels to the frame
        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();

        middlePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        //https://stackoverflow.com/questions/10017517/java-swing-jtable-set-color-for-selected-row-but-not-cell
        //https://docs.oracle.com/javase/tutorial/uiswing/components/table.html#editrender
        //less than 3 are red
        tableProduct.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override //done by custom render
            // It is called to get the rendering component for a cell
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                Product product = (Product) table.getValueAt(row, table.getColumnCount() - 1);
                if (product.getNoOfAvailItems() <= 3) {
                    c.setBackground(Color.RED);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(table.getBackground());
                    c.setForeground(table.getForeground());
                }
                return c;
            }
        });
    }

    //the table update
    private void updateProductTable(String category) {
        // Clear existing rows
        tabelDetail.setRowCount(0);


        //taking from database to arraylist
        List<Product> productList = manager.loadFromFile();

        for (Product product : productList) {
            String productCategory = product instanceof Clothing ? "Clothing" : "Electronics";

            if (category.equals("All") || productCategory.equals(category)) {
                tabelDetail.addRow(new Object[]{
                        product.getProductID(),
                        product.getName(),
                        productCategory,
                        product.getPrice(),
                        product //itll give the tosrting method of the respective class
                });
            }
        }
    }


    //the display details at the bottom of the frame
    private void displayProductDetails(Product product) {
        selectedProductDetails.setText("\n     Selected Product - Details\n\n");
        selectedProductDetails.append("     Product ID: " + product.getProductID() +
                "\n\n     Product Name: " + product.getName() + "\n\n     Price: " +
                product.getPrice() + "\n\n");

        if (product instanceof Clothing clothing) {
            selectedProductDetails.append("     Size: " + clothing.getSize() +
                    "\n\n     Color: " + clothing.getColour() + "\n\n");
            } else if (product instanceof Electronics electronics) {
                selectedProductDetails.append("     Brand: " + electronics.getBrand() +
                        "\n\n     Warranty Period: " + electronics.getWarranty() + "\n\n");
        }
    }
}








