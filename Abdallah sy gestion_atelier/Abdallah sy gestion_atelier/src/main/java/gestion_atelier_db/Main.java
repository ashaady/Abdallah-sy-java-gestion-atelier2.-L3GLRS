package gestion_atelier_db;

import gestion_atelier_db.entities.Categorie;
import gestion_atelier_db.entities.ArticleConfection;
import gestion_atelier_db.repositories.ITables;
import gestion_atelier_db.repositories.db.*;
import gestion_atelier_db.services.*;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        ITables<Categorie> categorieRepository = new categorieRepository();
        CategorieService categorieService = new CategorieServiceImpl(categorieRepository);

        ITables<ArticleConfection> articleRepository = new articleRepository();
        ArticleConfectionService articleService = new ArticleConfectionServiceImpl(articleRepository, categorieService, articleRepository);

        int choix;
        do {
            System.out.println("-------MENU GENERAL-------");
            System.out.println("1---- Gestion des catégories");
            System.out.println("2---- Gestion des articles de confection");
            System.out.println("3---- Quitter");
            System.out.print("Choisir votre option : ");
            choix = scanner.nextInt();
            scanner.nextLine(); // Pour consommer la nouvelle ligne

            switch (choix) {
                case 1:
                    gestionCategories(categorieService);
                    break;
                case 2:
                    gestionArticles(articleService, categorieService);
                    break;
                case 3:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Option invalide. Veuillez choisir une option valide.");
                    break;
            }
        } while (choix != 3);
    }

private static void gestionCategories(CategorieService categorieService) {
    int choix;
    do {
        System.out.println("MENU CATEGORIE");
        System.out.println("1---- Ajout catégorie");
        System.out.println("2---- Lister  catégories");
        System.out.println("3---- Modifier  catégorie");
        System.out.println("4---- Supprimer  catégorie");
        System.out.println("5---- Rechercher  catégorie");
        System.out.println("6---- Supprimer plusieurs catégories");
        System.out.println("7 Menu principal");
        System.out.print("Choisir votre option : ");
        choix = scanner.nextInt();
        scanner.nextLine(); // Pour consommer la nouvelle ligne

        switch (choix) {
            case 1:
                effacer();
                System.out.println("Entrer le libellé de la catégorie :");
                Categorie categorie = new Categorie(scanner.nextLine());
                categorieService.add(categorie);
                break;
            case 2:
                effacer();
                System.out.println("Liste des catégories :");
                categorieService.getAll().forEach(System.out::println);
                break;
            case 3:
                effacer();
                System.out.println("Liste des catégories :");
                categorieService.getAll().forEach(System.out::println);
                System.out.println("Entrer l'id de la catégorie à modifier :");
                int idToUpdate = scanner.nextInt();
                scanner.nextLine(); // Pour consommer la nouvelle ligne
                Categorie categorieToUpdate = categorieService.show(idToUpdate);
                if (categorieToUpdate != null) {
                    System.out.println("Entrez le nouveau libellé :");
                    String newLibelle = scanner.nextLine();
                    categorieToUpdate.setLibelle(newLibelle);
                    categorieService.update(categorieToUpdate);
                    System.out.println("La catégorie a été modifiée.");
                } else {
                    System.out.println("Catégorie non trouvée.");
                }
                break;
            case 4:
                effacer();
                System.out.println("Liste des catégories :");
                categorieService.getAll().forEach(System.out::println);
                System.out.println("Entrer l'id de la catégorie à supprimer :");
                int idToDelete = scanner.nextInt();
                scanner.nextLine(); // Pour consommer la nouvelle ligne
                Categorie categorieToDelete = categorieService.show(idToDelete);
                if (categorieToDelete != null) {
                    categorieService.remove(categorieToDelete.getId());
                    System.out.println("La catégorie a été supprimée.");
                } else {
                    System.out.println("Catégorie non trouvée.");
                }
                break;
            case 5:
            effacer();;
            categorieService.getAll().forEach(System.out::println);
            System.out.println("\nEntrer l'ID de la catégorie à rechercher :");
            int categorieIdToSearch = scanner.nextInt();
            scanner.nextLine();
            Categorie categorieToSearch = categorieService.show(categorieIdToSearch);
            if (categorieToSearch != null) {
                effacer();;
                System.out.println("\n[Categorie number:"+ categorieToSearch.getId() + " libelle :" + categorieToSearch.getLibelle() + "]");
            } else {
                effacer();;
                System.out.println("Catégorie non trouvée !");
            }
            break;
            
            case 6:
                effacer();
                System.out.println("Entrer les IDs des catégories à supprimer (séparés par des virgules) :");
                String idsToDelete = scanner.nextLine();
                String[] idArray = idsToDelete.split(",");
                for (String idStr : idArray) {
                    try {
                        int id = Integer.parseInt(idStr.trim());
                        Categorie categorieToDeleteMultiple = categorieService.show(id);
                        if (categorieToDeleteMultiple != null) {
                            categorieService.remove(categorieToDeleteMultiple.getId());
                            System.out.println("Les catégories  ont ete supprimée.");
                        } else {
                            System.out.println("Catégorie avec l'ID " + id + " non trouvée.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("ID de catégorie invalide : " + idStr);
                    }
                }
                break;
            case 7:
                effacer();
                break;
            default:
                System.out.println("Option invalide. Veuillez choisir une option valide.");
                break;
        }
    } while (choix != 7);
}
    

private static void gestionArticles(ArticleConfectionService articleService, CategorieService categorieService) {
    int choix;
    do {
        System.out.println("ARTICLES DE CONFECTION");
        System.out.println("1---- Ajouter un article de confection");
        System.out.println("2---- Lister les articles de confection");
        System.out.println("3---- Modifier un article de confection");
        System.out.println("4---- Supprimer un article de confection");
        System.out.println("5---- Retour au menu principal");
        System.out.print("Choisir votre option : ");
        choix = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        switch (choix) {
            case 1:
                effacer();
                System.out.println("Entrer le libellé de l'article :");
                String libelleArticle = scanner.nextLine();
                System.out.println("Entrer le prix de l'article :");
                double prixArticle;
                try {
                    prixArticle = Double.parseDouble(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Prix invalide. Veuillez entrer un nombre.");
                    continue; // Repeat the loop
                }

                System.out.println("Entrer la quantité de l'article :");
                int qteArticle;
                try {
                    qteArticle = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Quantité invalide. Veuillez entrer un nombre.");
                    continue; // Repeat the loop
                }

                System.out.println("Liste des catégories disponibles :");
                categorieService.getAll().forEach(System.out::println);
                System.out.println("Entrer l'ID de la catégorie de l'article :");
                int categorieId;
                try {
                    categorieId = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("ID de catégorie invalide. Veuillez entrer un nombre.");
                    continue; // Repeat the loop
                }

                // Obtenir la catégorie correspondante à l'ID
                Categorie categorieArticle = categorieService.show(categorieId);
                if (categorieArticle != null) {
                    ArticleConfection article = new ArticleConfection(libelleArticle, prixArticle, qteArticle, categorieArticle);
                    articleService.add(article);
                    System.out.println("L'article de confection a été ajouté.");
                } else {
                    System.out.println("La catégorie n'existe pas.");
                }

                break;

            case 2:
                effacer();
                System.out.println("Liste des articles de confection :");
                articleService.getAll().forEach(System.out::println);
                break;

            case 3:
                effacer();
                System.out.println("Liste des articles :");
                articleService.getAll().forEach(System.out::println);
                System.out.println("Entrer l'id de l'article à modifier :");
                int idToUpdate = scanner.nextInt();
                scanner.nextLine(); // Pour consommer la nouvelle ligne
                ArticleConfection articleToUpdate = articleService.show(idToUpdate);
                if (articleToUpdate != null) {
                    System.out.println("Entrez le nouveau libellé :");
                    String newLibelle = scanner.nextLine();
                    System.out.println("Entrez le nouveau prix :");
                    double newPrix;
                    try {
                        newPrix = Double.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Prix invalide. Veuillez entrer un nombre.");
                        continue; // Repeat the loop
                    }
                    System.out.println("Entrez la nouvelle quantité :");
                    int newQte;
                    try {
                        newQte = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Quantité invalide. Veuillez entrer un nombre.");
                        continue; // Repeat the loop
                    }

                    // Mettre à jour toutes les propriétés de l'article
                    articleToUpdate.setLibelle(newLibelle);
                    articleToUpdate.setPrix(newPrix);
                    articleToUpdate.setQte(newQte);

                    // Mettre à jour l'article dans la base de données
                    articleService.update(articleToUpdate);
                    System.out.println("L'article a été modifié.");
                } else {
                    System.out.println("Article non trouvé.");
                }
                break;

            case 4:
                effacer();
                System.out.println("Liste des articles :");
                articleService.getAll().forEach(System.out::println);
                System.out.println("Entrer l'id de l'article à supprimer :");
                int idToDelete = scanner.nextInt();
                scanner.nextLine(); // Pour consommer la nouvelle ligne
                ArticleConfection articleToDelete = articleService.show(idToDelete);
                if (articleToDelete != null) {
                    articleService.remove(articleToDelete.getId());
                    System.out.println("L'article a été supprimé.");
                } else {
                    System.out.println("Article non trouvé.");
                }
                break;

            case 5:
                effacer();
                break;
            default:
                System.out.println("Option invalide. Veuillez choisir une option valide.");
                break;
        }
    } while (choix != 5);
}

private static void effacer() {
}

}