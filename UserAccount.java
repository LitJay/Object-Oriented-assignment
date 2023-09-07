import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserAccount {
    public String username;
    public String password;
    public String name;
    public String phoneNumber;
    public String email;

    public UserAccount(String username, String password, String name, String phoneNumber, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Register a new user
    public static void registerUser(String username, String password, String name, String phoneNumber, String email) {
        CreateUserFile(username, password, name, phoneNumber, email);
    }
    public static void CreateUserFile(String username, String password, String name, String phoneNumber, String email) {
      UserAccount newUser = new UserAccount(username, password, name, phoneNumber, email);
      saveUserInfo(newUser);
  
      // Create a text file with the user's username as the filename
      try {
          String fileName = username + ".txt";
          File userFile = new File(fileName);
  
          if (userFile.createNewFile()) {
              System.out.println("User file created: " + fileName);
          } else {
              System.out.println("User file already exists: " + fileName);
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

    // Save user information to a text file
    private static void saveUserInfo(UserAccount user) {
        try (FileWriter writer = new FileWriter("user_accounts.txt", true);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            // Save user information as a new line in the text file
            String userInfo = user.username + "," + user.password + "," + user.name + "," + user.phoneNumber + "," + user.email;
            bufferedWriter.write(userInfo);
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Authenticate user login
    public static boolean signIn(String username, String password) 
    {
  
      List<UserAccount> users = loadUserAccounts();
        for (UserAccount user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                System.out.println("Login Sucessful");
                return true; // Authentication successful

            }
            else
            {
              System.out.println("Login Unsucessful");
            }
        }
        return false; // Authentication failed
    }

    // Load user accounts from the text file
    public static List<UserAccount> loadUserAccounts() 
    {
        List<UserAccount> users = new ArrayList<>();
        try (FileReader reader = new FileReader("user_accounts.txt");
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] userInfo = line.split(",");
                if (userInfo.length == 5) {
                    UserAccount user = new UserAccount(userInfo[0], userInfo[1], userInfo[2], userInfo[3], userInfo[4]);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static void updateAccountInfo(String username, String newUsername,String newPassword, String newName,String newPhoneNumber,String newEmail) {
      List<UserAccount> users = loadUserAccounts();
      for (UserAccount user : users) {
          if (user.username.equals(newUsername)) {
              if (!newPassword.isEmpty()) {
                  user.password = newPassword;
              }
              if (!newPhoneNumber.isEmpty()) {
                  user.phoneNumber = newPhoneNumber;
              }
              if(!user.email.isEmpty())
              {
                user.email=newEmail;
              }
              if(!newName.isEmpty())
              {
                user.name=newName;
              }
              updateUserFile(users);
              System.out.println("User information updated successfully!");
              return; // Found and updated the user, exit the loop
          }
      }
      System.out.println("User not found. Update failed.");
  }
  public static UserAccount getUserByUsername(String username) {
    List<UserAccount> users = loadUserAccounts();
    for (UserAccount user : users) {
        if (user.username.equals(username)) {
            return user; // Return the user with the matching username
        }
    }
    return null; // If no user with the given username is found
}

    // Update user account information

    // Update the user's file with ticket information
    private static void updateUserFile(List<UserAccount> users) {
      try (FileWriter writer = new FileWriter("user_accounts.txt");
           BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
          for (UserAccount user : users) {
              String userInfo = user.username + "," + user.password + "," + user.name + "," + user.phoneNumber + "," + user.email;
              bufferedWriter.write(userInfo);
              bufferedWriter.newLine();
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
  }
  


}
