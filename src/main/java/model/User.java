package model;

public abstract class User {
    protected String userID;
    protected String name;
    protected String email;
    protected String password;
    protected String phoneNumber;
    protected String address;

    public User(String userID, String name, String email, String password, String phoneNumber, String address) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Getters and Setters
    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

