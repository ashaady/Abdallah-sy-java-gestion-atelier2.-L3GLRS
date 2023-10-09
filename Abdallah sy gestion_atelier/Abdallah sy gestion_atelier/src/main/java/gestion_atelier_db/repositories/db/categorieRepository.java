package gestion_atelier_db.repositories.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import gestion_atelier_db.entities.Categorie;
import gestion_atelier_db.repositories.ITables;

public class categorieRepository extends Mysql implements ITables<Categorie> {

        public categorieRepository() throws SQLException {
        super();
    }

    private Connection getConnection() {
        return super.connection;
    }
    @Override
    public Object insert(Categorie data) {
        try {
            String sql = "INSERT INTO categorie (libelle) VALUES (?)";
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, data.getLibelle());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    data.setId(generatedKeys.getInt(1));
                }
            }

            return rowsInserted;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }

        return 0; // Retourne 0 si l'insertion échoue
    }

    @Override
    public int update(Categorie data) {
        try {
            String sql = "UPDATE categorie SET libelle = ? WHERE id = ?";
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, data.getLibelle());
            statement.setInt(2, data.getId());

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }

        return 0; 
    }

    @Override
    public ArrayList<Categorie> findAll() {
        ArrayList<Categorie> categories = new ArrayList<>();
        try {
            String sql = "SELECT id, libelle FROM categorie";
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Categorie categorie = new Categorie(rs.getInt("id"), rs.getString("libelle"));
                categories.add(categorie);
            }

            rs.close();
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }

        return categories;
    }

    @Override
    public Categorie findById(int id) {
        try {
            String sql = "SELECT id, libelle FROM categorie WHERE id = ?";
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Categorie categorie = new Categorie(rs.getInt("id"), rs.getString("libelle"));
                rs.close();
                return categorie;
            }

            rs.close();
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }

        return null; // Retourne null si la catégorie n'est pas trouvée
    }

    @Override
    public int delete(int id) {
        try {
            // Delete related records in the article_confection table
            String deleteArticlesSql = "DELETE FROM article_confection WHERE categorie_id = ?";
            Connection connection = getConnection();
            PreparedStatement deleteArticlesStatement = connection.prepareStatement(deleteArticlesSql);
            deleteArticlesStatement.setInt(1, id);
            deleteArticlesStatement.executeUpdate();

            // Delete the record in the categorie table
            String deleteCategorieSql = "DELETE FROM categorie WHERE id = ?";
            PreparedStatement deleteCategorieStatement = connection.prepareStatement(deleteCategorieSql);
            deleteCategorieStatement.setInt(1, id);

            int rowsDeleted = deleteCategorieStatement.executeUpdate();

            return rowsDeleted;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }

        return 0; // Retourne 0 si la suppression échoue
    }

    @Override
    public int indexOf(int id) {
        ArrayList<Categorie> categories = findAll();
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId() == id) {
                return i; // Retourne l'index si trouvé
            }
        }
        return -1; // Retourne -1 si non trouvé
    }
}