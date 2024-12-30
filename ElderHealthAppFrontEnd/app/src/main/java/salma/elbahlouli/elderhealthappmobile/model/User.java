package salma.elbahlouli.elderhealthappmobile.model;

import java.util.List;

public class User {
    private Long id;  // Correspond à "id" dans le backend
    private String nom;  // Correspond à "nom" dans le backend
    private String login;  // Correspond à "login" dans le backend
    private String password;  // Correspond à "password" dans le backend
    private String telephone;  // Correspond à "telephone" dans le backend
    private List<Bilan> bilans;  // Correspond à la relation OneToMany avec "Bilan"
    private List<Medicine> medicines;  // Correspond à la relation OneToMany avec "Medicine"

    // Constructeur
    public User(Long id, String nom, String login, String password, String telephone, List<Bilan> bilans, List<Medicine> medicines) {
        this.id = id;
        this.nom = nom;
        this.login = login;
        this.password = password;
        this.telephone = telephone;
        this.bilans = bilans;
        this.medicines = medicines;
    }
    public User(Long id) {
        this.id = id;  // Cet ID peut être passé lors de la création de l'utilisateur
    }

    public User() {

    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<Bilan> getBilans() {
        return bilans;
    }

    public void setBilans(List<Bilan> bilans) {
        this.bilans = bilans;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
