package vn.edu.usth.email2;

public class User {

    String username, phone, password, email, id;

    public User() {

    }

    public User(String username, String phone, String password, String email, String id) {
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.id = id;
    }

    public User(String username, String phone, String email, String id) {
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
