package GUI;

import backend.User;

import  java.awt.*;

import java.util.List;
import javax.swing.*;


public class Login extends JFrame {

    private JTextField username;
    private JPasswordField password;
    private JButton loginButton, createAccButton;
    private JLabel lblUserId, lblPassword;

    public Login() {
        super("Login");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        //username section
        lblUserId = new JLabel("Username");
        panel.add(lblUserId);
        username = new JTextField(10);
        panel.add(username);

        //passowrd section
        lblPassword = new JLabel("Password");
        panel.add(lblPassword);
        password = new JPasswordField(10);
        panel.add(password);

        //create password
        createAccButton = new JButton("Create an account");
        panel.add(createAccButton);
        createAccButton.addActionListener(e -> {
            SignUp signupframe = new SignUp();
            signupframe.setTitle("Sign Up");
            signupframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            signupframe.setSize(400, 300);
            signupframe.setLocationRelativeTo(null);
            signupframe.setVisible(true);
            dispose();

        });

        // login button
        loginButton = new JButton("Login");
        panel.add(loginButton);
        loginButton.addActionListener(e -> {
            String userN = username.getText();
            String pw = new String(password.getPassword());

            // userDetails is a List<User>
            userDataManager dataManager = new userDataManager();
            List<User> userDetails = dataManager.loadUserDetails();

            //checks if user is already registered user
            boolean userExists = false;
            for (User user : userDetails) {
                if (user.getUsername().equals(userN) && user.getPassword().equals(pw)) {
                    userExists = true;
                    break;
                }
            }
            if (userExists) {
                openShopCenter();
                dispose(); //close frame
            } else {
                // User is not registered, display invalid message
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });


        // Add padding
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack(); // Automatically set the size based on the components
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //open shopping center gui
    private void openShopCenter() {
        shopCenter frame = new shopCenter();
        frame.setTitle("Shopping Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Close the current login frame if needed
        this.dispose();
    }

}
