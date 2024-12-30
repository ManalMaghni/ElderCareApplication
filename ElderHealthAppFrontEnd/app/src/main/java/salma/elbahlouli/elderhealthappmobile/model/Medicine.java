package salma.elbahlouli.elderhealthappmobile.model;

public class Medicine {
    private int id;
    private String nom;
    private String dosage;
    private String heure; // Format HH:mm
    private User user;
    public Medicine(String nom, String dosage, String heure,User user) {
        this.nom = nom;
        this.dosage = dosage;
        this.heure = heure;
        this.user= user;
    }

    public Medicine() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public User getUser() {
        return this.user=user;
    }

    public void setUser(User user) {
    }
}
