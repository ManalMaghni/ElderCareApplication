package salma.elbahlouli.elderhealthappmobile.model;

public class Bilan {
    private Long id;
    private String nomTest;
    private String valeur;

    public Bilan(Long id, String nomTest, String valeur) {
        this.id = id;
        this.nomTest = nomTest;
        this.valeur = valeur;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomTest() {
        return nomTest;
    }

    public void setNomTest(String nomTest) {
        this.nomTest = nomTest;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
}
