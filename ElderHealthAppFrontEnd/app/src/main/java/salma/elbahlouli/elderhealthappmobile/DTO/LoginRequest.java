package salma.elbahlouli.elderhealthappmobile.DTO;

public class LoginRequest {

    private String login;
    private String password;

    // Default constructor
    public LoginRequest() {}

    // Constructor with parameters
    public LoginRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    // Getters and Setters
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}