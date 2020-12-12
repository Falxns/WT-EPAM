package entity;

public class EntityUser extends Entity{
    private String username;
    private String password;
    private String email;
    private EntityRole role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public EntityRole getRole() {
        return role;
    }

    public void setRole(EntityRole role) {
        this.role = role;
    }
}