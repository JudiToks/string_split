package mg.string_split.modeles;

import mg.string_split.modeles.connect.Connect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Marque
{
    //    attributs
    int id_marque;
    String nom;
    int id_categorie;

    //    getters & setters
    public int getId_marque() {
        return id_marque;
    }
    public void setId_marque(int id_marque) {
        this.id_marque = id_marque;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public int getId_categorie() {
        return id_categorie;
    }
    public void setId_categorie(int id_categorie) {
        this.id_categorie = id_categorie;
    }

    //    functions
    public static List<Marque> getAllMarque(Connection connection)
    {
        boolean isOuvert = false;
        List<Marque> valiny = new ArrayList<>();
        String query = "select * from marque;";
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
                Marque temp = new Marque();
                temp.setId_marque(resultSet.getInt("id_marque"));
                temp.setNom(resultSet.getString("nom"));
                temp.setId_categorie(resultSet.getInt("id_categorie"));
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
            System.out.println("Marque getAllMarque issues");
            e.printStackTrace();
        }
        return valiny;
    }

    public static Marque getMarqueById(Connection connection, int id_marque)
    {
        boolean isOuvert = false;
        Marque valiny = new Marque();
        String query = "select * from marque where id_marque = "+id_marque+";";
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
                Marque temp = new Marque();
                temp.setId_marque(resultSet.getInt(1));
                temp.setNom(resultSet.getString(2));
                temp.setId_categorie(resultSet.getInt(3));
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
            System.out.println("Marque getMarqueById issues");
            e.printStackTrace();
        }
        return valiny;
    }
}
