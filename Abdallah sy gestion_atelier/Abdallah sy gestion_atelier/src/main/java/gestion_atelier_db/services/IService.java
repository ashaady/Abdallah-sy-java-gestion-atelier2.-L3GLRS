package gestion_atelier_db.services;

import java.util.ArrayList;



public interface IService<leibou> {
       Object  add(leibou data);
       ArrayList<leibou> getAll();
       int update(leibou data);
       leibou show(int id);
       int remove(int i);
       int[] remove(int[] ids);
}



    