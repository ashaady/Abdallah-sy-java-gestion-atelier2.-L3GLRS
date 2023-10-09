package gestion_atelier_db.repositories;

import java.util.ArrayList;

import gestion_atelier_db.entities.AbsEntities;


public interface ITables<pro extends AbsEntities>{
   Object insert (pro data) ;
   int update (pro data);
   ArrayList<pro>findAll ();
   pro findById (int id);
   int delete (int id);
   int indexOf(int id);
}
