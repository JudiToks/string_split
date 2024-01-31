package mg.string_split.modeles;

import mg.string_split.modeles.connect.Connect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Categorie
{
    int id_categorie;
    String nom;

    //    getters & setters
    public int getId_categorie() {
        return id_categorie;
    }
    public void setId_categorie(int id_categorie) {
        this.id_categorie = id_categorie;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    //    functions
    public static List<Categorie> getAllCategorie(Connection connection)
    {
        boolean isOuvert = false;
        List<Categorie> valiny = new ArrayList<>();
        String query = "select * from categorie;";
        try
        {
            if (connection == null)
            {
                connection = Connect.connectToPostgre();
                isOuvert = true;
            }
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next())
            {
                Categorie temp = new Categorie();
                temp.setId_categorie(resultSet.getInt("id_categorie"));
                temp.setNom(resultSet.getString("nom"));
                valiny.add(temp);
            }
            resultSet.close();
            statement.close();
            if (isOuvert)
            {
                connection.close();
            }
        }
        catch (Exception e)
        {
            System.out.println("Categorie getAllCategorie issues");
            e.printStackTrace();
        }
        return valiny;
    }

    public static Categorie getCategorieById(Connection connection, int id_categorie)
    {
        boolean isOuvert = false;
        Categorie valiny = new Categorie();
        String query = "select * from categorie where id_categorie = "+id_categorie+";";
        try
        {
            if (connection == null)
            {
                connection = Connect.connectToPostgre();
                isOuvert = true;
            }
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next())
            {
                Categorie temp = new Categorie();
                temp.setId_categorie(resultSet.getInt(1));
                temp.setNom(resultSet.getString(2));
                valiny = temp;
            }
            resultSet.close();
            statement.close();
            if (isOuvert)
            {
                connection.close();
            }
        }
        catch (Exception e)
        {
            System.out.println("Categorie getCategorieById issues");
            e.printStackTrace();
        }
        return valiny;
    }
}
