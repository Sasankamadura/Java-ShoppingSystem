public class User {     // Class representing user
    private String username;
    private String password;

    public User(String username, String password) {         // Constructor for the User class
        this.username = username;
        this.password = password;
    }

    public String getUsername() {           // Getter method to retrieve the username
        return username;
    }

    public void setUsername(String username) {          // Setter method to set the username
        this.username = username;
    }

    public String getPassword() {               // Getter method to retrieve the password
        return password;
    }

    public void setPassword(String password) {          // Setter method to set the password
        this.password = password;
    }
}
