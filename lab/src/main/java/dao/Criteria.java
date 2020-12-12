package dao;

public class Criteria {
    private String email;
    private String username;
    private Integer userId;
    private Integer publicationId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserId(Integer userId) { this.userId = userId; }

    public Integer getUserId() { return userId; }

    public boolean existUserId() { return userId != null; }

    public void setPublicationId(Integer publicationId) { this.publicationId = publicationId; }

    public Integer getPublicationId() { return publicationId; }

    public boolean existPublicationId() { return publicationId != null; }
}