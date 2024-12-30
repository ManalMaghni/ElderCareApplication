package salma.elbahlouli.elderhealthappmobile.DTO;

public class RegisterResponse {

    private String message;  // Message de succès ou d'erreur
    private boolean success; // Indicateur de réussite de l'inscription

    // Champs utilisateur retournés par l'API
    private String nom;
    private String login;
    private String telephone;

    // Getters et setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
