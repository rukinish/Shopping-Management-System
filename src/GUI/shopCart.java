package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import backend.*;

public class shopCart extends JFrame {
        private final DefaultTableModel shoppingCartTableModel;
        private final JLabel totalLabel;
        private final JLabel categoryDiscountLabel;
        private final JLabel firstPurchaseDiscountLabel;
        private final JLabel finalTotalLabel;
        private final ShoppingCart shoppingCart;
        private int clothCount;
        private int electricCount;

        public shopCart(ShoppingCart shoppingCart) {

            this.shoppingCart = shoppingCart;
            shoppingCartTableModel = new DefaultTableModel(new Object[]{"Product ID", "Quantity", "Price"}, 0) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    //https://stackoverflow.com/questions/1990817/how-to-make-a-jtable-non-editable
                    return false;
                }
            };

            JTable shoppingCartTable = new JTable(shoppingCartTableModel);
            JScrollPane shoppingCartScrollPane = new JScrollPane(shoppingCartTable);
            totalLabel = new JLabel();
            categoryDiscountLabel = new JLabel(); // Initialize the new label
            firstPurchaseDiscountLabel = new JLabel();
            finalTotalLabel = new JLabel();

            // Create the shopping cart GUI
            JPanel shopCartPanel = new JPanel(new BorderLayout());
            shopCartPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
            shopCartPanel.setPreferredSize(new Dimension(400, 300));
            shopCartPanel.add(shoppingCartScrollPane, BorderLayout.CENTER);

            //total cost and other details
            JPanel calPanel = new JPanel(new GridLayout(0, 1));
            calPanel.add(totalLabel);
            calPanel.add(categoryDiscountLabel);
            calPanel.add(firstPurchaseDiscountLabel);
            calPanel.add(finalTotalLabel);
            shopCartPanel.add(calPanel, BorderLayout.SOUTH);
            add(shopCartPanel);
        }

            public void showShoppingCart() {

                shoppingCartTableModel.setRowCount(0);
                // Add products to the shopping cart table
                List<Product> products = shoppingCart.getProducts();

                //update shopping cart table
                for (Product product : products) {
                    String productinfo = null;
                    String  category = null;
                    if (product instanceof Clothing clothing){
                        category = "Clothing";
                        productinfo = clothing.toString();
                        clothCount++;
                    } else if (product instanceof Electronics electronics){
                        category = "Electronics";
                        productinfo = electronics.toString();
                        electricCount++;
                    }

                    String productDet = product.getProductID() + "\n" + productinfo ;
                    int quantity = 1;
                    double price = product.getPrice();

                    shoppingCartTableModel.addRow(new Object[]{productDet, quantity ,price});
                }

                // Calculate total cost
                double totalCost = shoppingCart.calculateTotalCost();
                totalLabel.setText("Total: $" + totalCost);

                // Calculate category discount
                double categoryDiscount = discountCat(totalCost);
                categoryDiscountLabel.setText("Three items in the same Category Discount (20%): -$" + categoryDiscount);

                // Calculate new user discount
//                User newUser = new User();
                
//                if (newUser.isNewUser()) {
//                    newDiscount = newUserDiscount(totalCost);
//                } else {
//                    newDiscount = 0;
//                }

                double newDiscount = 0;
                firstPurchaseDiscountLabel.setText("First Purchase Discount (10%): -$" + newDiscount);


                double finalTotal = totalCost - (categoryDiscount);
                finalTotalLabel.setText("Final Total: $" + finalTotal);

                pack();
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setVisible(true);
            }

//            public double newUserDiscount(double total){
//                double newDiscount = 0.0;
//                return newDiscount = total*0.1;
//            }

    //caluclaye 20% disocunt
            public double discountCat(double total){
                double categoryDiscount = 0.0;
                if ((clothCount>=3) || (electricCount>=3)){
                    categoryDiscount = (total * 0.2);
                }
                return categoryDiscount;
            }


}
