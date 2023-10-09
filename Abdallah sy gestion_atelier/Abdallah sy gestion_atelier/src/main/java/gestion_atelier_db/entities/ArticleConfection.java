package gestion_atelier_db.entities;
import java.util.ArrayList;

public class ArticleConfection extends AbsEntities {

    private double prix;
    private int qte;
    private Categorie categorie;
    private ArrayList<Unite> unites = new ArrayList<>();

    public ArticleConfection(String libelle, double prix, int qte2, Categorie categorie) {
        super();
        this.libelle = libelle;
        this.prix = prix;
        this.qte = qte2;
        this.categorie = categorie;
    }

    public ArticleConfection(int id, String libelle, double prix, double d, Categorie i) {
        super(id, libelle);
        this.prix = prix;
        this.qte = (int) d;
        this.categorie = i;
    }

    public ArticleConfection(int int1, String string, double double1, double double2, int id) {
    }

    public void addUnite(Unite unite) {
        unites.add(unite);
    }

    public ArrayList<Unite> getUnites() {
        return unites;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public double getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    @Override
    public String toString() {
        return "ArticleConfection"+super.toString() + ", Prix=" + prix + ", Quantité=" + qte
                + ", Catégorie=" + categorie.getLibelle() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((categorie == null) ? 0 : categorie.hashCode());
        long temp;
        temp = Double.doubleToLongBits(prix);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(qte);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ArticleConfection other = (ArticleConfection) obj;
        if (categorie == null) {
            if (other.categorie != null)
                return false;
        } else if (!categorie.equals(other.categorie))
            return false;
        if (Double.doubleToLongBits(prix) != Double.doubleToLongBits(other.prix))
            return false;
        if (Double.doubleToLongBits(qte) != Double.doubleToLongBits(other.qte))
            return false;
        return true;
    }
}
