package gestion_atelier_db.entities;

public class Unite extends AbsEntities {
    

    public Unite(){
        super();
    }
    
    public Unite(int id, String libelle){
        super(id,libelle);
    }
    @Override
    public String toString() {
        return "Unite" + super.toString();
    }
    
}
