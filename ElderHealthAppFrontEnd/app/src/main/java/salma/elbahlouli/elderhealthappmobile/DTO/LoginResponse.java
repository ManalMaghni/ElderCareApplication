package salma.elbahlouli.elderhealthappmobile.DTO;

public class LoginResponse {
    private String message;
    private int user_id;

    public LoginResponse(int userId, String message) {
        this.message = message;
        this.user_id = userId;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}