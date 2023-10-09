package gestion_atelier_db.entities;

public abstract class AbsEntities {
    //Attributs   Instances
    protected static int nbr;
    protected int id;
    protected String libelle;

    public AbsEntities(String libelle) {
        this.id=++nbr;
        this.libelle = libelle;
    }
    //constructeur avec argument
    public AbsEntities(int id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }
    //sans argument
    public AbsEntities() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return " [id=" + id + ", libelle=" + libelle + "]";
    }
    
}
