package salma.elbahlouli.elderhealthappmobile.DTO;

public class RegisterRequest {

    private String nom;       // Nom complet
    private String login;     // Nom d'utilisateur
    private String telephone; // Numéro de téléphone
    private String password;  // Mot de passe

    // Constructeur
    public RegisterRequest(String nom, String login, String telephone, String password) {
        this.nom = nom;
        this.login = login;
        this.telephone = telephone;
        this.password = password;
    }

    // Getters et setters
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
