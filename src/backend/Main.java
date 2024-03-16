package backend;

import GUI.Login;

import javax.swing.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //check if user is admin or customer
        while (true){
            try {
                System.out.print("Enter 1 for admin, \nEnter 2 for Customer: ");
                int option = scanner.nextInt();
                if(option ==1 ){
                    //backend
                    WestminsterShoppingManager manager = new WestminsterShoppingManager();
                    List<Product> productList = manager.loadFromFile();
                    Console console = new Console(manager);
                    console.userChoice(productList);
                    break;
                } else if (option==2){
                    //frontend
                    Login login = new Login();
                    login.setTitle("Login");
                    login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    login.setSize(400, 200);
                    login.setLocationRelativeTo(null);
                    login.setVisible(true);
                    break;
                } else {
                    System.out.println("Incorrect answer please re-enter.");
                }
            } catch (Exception e) {
                System.out.println("Incorrect value, please re-enter");
                scanner.next(); // Consume invalid input
            }
        }
    }
}