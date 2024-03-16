package GUI;

import backend.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//access the user detail file
public class userDataManager {
        private List<User> userDetails;

        public userDataManager() {
            this.userDetails = loadUserDetails();
        }

        public List<User> loadUserDetails() {
            List<User> userDetails = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader("UserDetails.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String username = line;
                    String password = reader.readLine();
                    // Create a User object and add it to the list
                    User user = new User(username, password);
                    userDetails.add(user);
                }
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }

            return userDetails;
        }



    }


