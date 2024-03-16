package GUI;

import backend.User;

import  java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.swing.*;

public class SignUp extends JFrame {
    private JTextField userID, pwd, pwdConfirm;
    private JButton newAccButton, btnNewButton;
    private JLabel lblUserId, lblPassword, lblConfirmPassword, lblNewLabel;
    private Component verticalStrut;



    public SignUp() {
        // https://www.ebhor.com/registration-form-java/
        //https://www.youtube.com/live/F-7sAj7Qklw?si=nLygbut7lM3ZJ6Ky
        //https://youtu.be/dCIef0griJ0
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        JPanel panel = new JPanel();
        GridBagConstraints gbc_panel = new GridBagConstraints();
        add(panel, gbc_panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel panel_5 = new JPanel();
        panel.add(panel_5);
        panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));

        JPanel panel_6 = new JPanel();
        panel_5.add(panel_6);
        panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.X_AXIS));

        lblNewLabel = new JLabel();
        panel_6.add(lblNewLabel);

        JPanel panel_4 = new JPanel();
        panel_5.add(panel_4);
        panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));

        verticalStrut = Box.createVerticalStrut(20);
        panel_4.add(verticalStrut);

        JPanel panel_1 = new JPanel();
        panel_4.add(panel_1);
        FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
        flowLayout.setVgap(0);
        flowLayout.setHgap(0);
        flowLayout.setAlignment(FlowLayout.LEFT);

        lblUserId = new JLabel("Choose a username");
        panel_1.add(lblUserId);

        userID = new JTextField();
        panel_4.add(userID);
        userID.setColumns(10);

        JPanel panel_3 = new JPanel();
        panel_4.add(panel_3);
        FlowLayout flowLayout_1 = (FlowLayout) panel_3.getLayout();
        flowLayout_1.setVgap(0);
        flowLayout_1.setHgap(0);
        flowLayout_1.setAlignment(FlowLayout.LEFT);

        lblPassword = new JLabel("Choose a password");
        panel_3.add(lblPassword);

        pwd = new JTextField();
        panel_4.add(pwd);
        pwd.setColumns(10);

        JPanel panel_7 = new JPanel();
        FlowLayout flowLayout_2 = (FlowLayout) panel_7.getLayout();
        flowLayout_2.setAlignment(FlowLayout.LEFT);
        panel_4.add(panel_7);

        lblConfirmPassword = new JLabel("Conform your password");
        lblConfirmPassword.setHorizontalAlignment(SwingConstants.LEFT);
        panel_7.add(lblConfirmPassword);

        pwdConfirm = new JTextField();
        pwdConfirm.setColumns(10);
        panel_4.add(pwdConfirm);

        JPanel panel_2 = new JPanel();
        panel_4.add(panel_2);

        newAccButton = new JButton("Create my account");
        newAccButton.addActionListener(e ->  {
            String user = userID.getText();
            String password = pwd.getText();
            String confirmPassword = pwdConfirm.getText();

            // Check if both passwords match
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit the method if passwords do not match
            }

            // Passwords match, proceed to save to file or database
            saveLoginCred(user, password);

            // Optionally, you can clear the text fields after successfully creating an account
            userID.setText("");
            pwd.setText("");
            pwdConfirm.setText("");


            // Optionally, show a success message or navigate to the login view
            JOptionPane.showMessageDialog(this, "Account created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            new Login();
            dispose();
        });

        btnNewButton = new JButton("Back to login");
        btnNewButton.addActionListener(e -> {
            new Login();
            dispose();
        });
        panel_2.add(btnNewButton);
        panel_2.add(newAccButton);

        panel_4.setMaximumSize( new Dimension(300, 200) );

    }

    //write new user credtionals in file
    public void saveLoginCred(String username, String password) {
        userDataManager dataManager = new userDataManager();
        List<User> userDetails = dataManager.loadUserDetails();

        try (FileWriter writer = new FileWriter("UserDetails.txt", true)) {
            // Append a newline after each user's credentials
            writer.write(username + "\n" + password + "\n");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the file.");
            e.printStackTrace();
        }
    }


}

