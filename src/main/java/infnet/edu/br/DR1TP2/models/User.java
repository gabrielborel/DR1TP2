package infnet.edu.br.DR1TP2.models;

public class User {
    private Integer id;
    private String username;
    private String email;

    public User(Integer id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
