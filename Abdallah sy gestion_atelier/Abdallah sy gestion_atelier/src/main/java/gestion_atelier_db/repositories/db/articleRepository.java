package gestion_atelier_db.repositories.db;

import gestion_atelier_db.entities.ArticleConfection;
import gestion_atelier_db.entities.Categorie;
import gestion_atelier_db.repositories.ITables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class articleRepository extends Mysql implements ITables<ArticleConfection> {

    public articleRepository() throws SQLException {
        super();
    }

    private Connection getConnection() {
        return super.connection;
    }

    @Override
    public Object insert(ArticleConfection data) {
        try {
            String sql = "INSERT INTO article_confection (libelle, prix, qte, categorie_id) VALUES (?, ?, ?, ?)";
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, data.getLibelle());
            statement.setDouble(2, data.getPrix());
            statement.setDouble(3, data.getQte());
            statement.setInt(4, data.getCategorie().getId());

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

        return 0; // Retourne 0 en cas d'échec de l'insertion
    }

    @Override
    public int update(ArticleConfection data) {
        try {
            String sql = "UPDATE article_confection SET libelle = ?, prix = ?, qte = ?, categorie_id = ? WHERE id = ?";
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, data.getLibelle());
            statement.setDouble(2, data.getPrix());
            statement.setDouble(3, data.getQte());
            statement.setInt(4, data.getCategorie().getId());
            statement.setInt(5, data.getId());

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }

        return 0; // Retourne 0 en cas d'échec de la mise à jour
    }
    @Override
    public ArrayList<ArticleConfection> findAll() {
        ArrayList<ArticleConfection> articles = new ArrayList<>();
        try {
            String sql = "SELECT ac.id, ac.libelle, ac.prix, ac.qte, ac.categorie_id, " +
                    "(SELECT libelle FROM categorie WHERE id = ac.categorie_id) AS categorie_libelle " +
                    "FROM article_confection ac";
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id"); // Récupération de l'ID correctement
                String libelle = rs.getString("libelle");
                double prix = rs.getDouble("prix");
                int qte = rs.getInt("qte");
                int categorieId = rs.getInt("categorie_id");
                String categorieLibelle = rs.getString("categorie_libelle");
                Categorie categorie = new Categorie(categorieId, categorieLibelle);
                ArticleConfection article = new ArticleConfection(id, libelle, prix, qte, categorie);
                articles.add(article);
            }
    
            rs.close();
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    
        return articles;
    }
    

    @Override
    public ArticleConfection findById(int id) {
        try {
            String sql = "SELECT ac.id, ac.libelle, ac.prix, ac.qte, c.id AS categorie_id, c.libelle AS categorie_libelle FROM article_confection ac JOIN categorie c ON ac.categorie_id = c.id WHERE ac.id = ?";
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String libelle = rs.getString("libelle");
                double prix = rs.getDouble("prix");
                int qte = rs.getInt("qte");
                int categorieId = rs.getInt("categorie_id");
                String categorieLibelle = rs.getString("categorie_libelle");
                Categorie categorie = new Categorie(categorieId, categorieLibelle);
                ArticleConfection article = new ArticleConfection(libelle, prix, qte, categorie);
                rs.close();
                return article;
            }

            rs.close();
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }

        return null; // Retourne null si l'article n'est pas trouvé
    }

    @Override
    public int delete(int id) {
        try {
            // Supprime les enregistrements liés dans la table article_confection
            String deleteArticlesSql = "DELETE FROM article_confection WHERE id = ?";
            Connection connection = getConnection();
            PreparedStatement deleteArticlesStatement = connection.prepareStatement(deleteArticlesSql);
            deleteArticlesStatement.setInt(1, id);
            deleteArticlesStatement.executeUpdate();

            return 1; // Retourne 1 si la suppression réussit
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }

        return 0; // Retourne 0 si la suppression échoue
    }

    @Override
    public int indexOf(int id) {
        ArrayList<ArticleConfection> articles = findAll();
        for (int i = 0; i < articles.size(); i++) {
            if (articles.get(i).getId() == id) {
                return i; // Retourne l'index s'il est trouvé
            }
        }
        return -1; // Retourne -1 si non trouvé
    }
}
