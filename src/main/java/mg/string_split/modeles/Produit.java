package mg.string_split.modeles;

import mg.string_split.modeles.connect.Connect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Produit
{
    //    attributs
    int id_produit;
    String marque;
    String nom;
    double prix;
    int qualite;

    //    getters & setters
    public int getId_produit() {
        return id_produit;
    }
    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }
    public String getMarque() {
        return marque;
    }
    public void setMarque(String marque) {
        this.marque = marque;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public double getPrix() {
        return prix;
    }
    public void setPrix(double prix) {
        this.prix = prix;
    }
    public int getQualite() {
        return qualite;
    }
    public void setQualite(int qualite) {
        this.qualite = qualite;
    }

    //    functions
    public static List<Produit> getAllProduit(Connection connection)
    {
        boolean isOuvert = false;
        List<Produit> valiny = new ArrayList<>();
        String query = "select * from produit;";
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
                Produit temp = new Produit();
                temp.setId_produit(resultSet.getInt("id_produit"));
                temp.setMarque(Marque.getMarqueById(connection, resultSet.getInt("id_marque")).getNom());
                temp.setNom(resultSet.getString("nom"));
                temp.setPrix(resultSet.getDouble("prix"));
                temp.setQualite(resultSet.getInt("qualite"));
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
            System.out.println("Produit getAllProduit issues");
            e.printStackTrace();
        }
        return valiny;
    }
}
