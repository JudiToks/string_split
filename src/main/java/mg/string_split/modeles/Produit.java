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
    double rapport;

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
    public double getRapport() {
        return rapport;
    }
    public void setRapport(double rapport) {
        this.rapport = rapport;
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

    public static String getSqlQuery(Connection connection, String search)
    {
        String valiny = "";
        try
        {
            List<Filtre_methode> listFiltre = Filtre_methode.getAllFiltre(connection);
            List<Categorie> listCategorie = Categorie.getAllCategorie(connection);
            StringBuilder sqlBuilder = new StringBuilder("select\n" +
                    "    produit.nom,\n" +
                    "    m.nom,\n" +
                    "    c.nom,\n" +
                    "    prix,\n" +
                    "    qualite,\n" +
                    "    (qualite/prix) as rapport\n" +
                    "from produit\n" +
                    "    join marque m on produit.id_marque = m.id_marque\n" +
                    "    join categorie c on m.id_categorie = c.id_categorie\n" +
                    "");
            for (int k = 0; k < listCategorie.size(); k++)
            {
                for (int i = 0; i < listFiltre.size(); i++)
                {
                    if (!search.contains(listCategorie.get(k).getNom()) && search.contains(listFiltre.get(i).getFilre()) && search.contains(listFiltre.get(i).getParam()))
                    {
                        if (search.contains("rapport"))
                        {
                            sqlBuilder.append(listFiltre.get(i).getDescription()+" ");
                            sqlBuilder.append("rapport ");
                            sqlBuilder.append(listFiltre.get(i).getOrdre()+";");
                        }
                        else
                        {
                            sqlBuilder.append(listFiltre.get(i).getDescription()+" ");
                            sqlBuilder.append(listFiltre.get(i).getParam()+" ");
                            sqlBuilder.append(listFiltre.get(i).getOrdre()+";");
                        }
                    }
                    if (search.contains(listCategorie.get(k).getNom()) && search.contains(listFiltre.get(i).getFilre()) && search.contains(listFiltre.get(i).getParam()))
                    {
                        sqlBuilder.append("where ");
                        if (search.contains("rapport"))
                        {
                            sqlBuilder.append("c.nom = '"+listCategorie.get(k).getNom()+"' ");
                            sqlBuilder.append(listFiltre.get(i).getDescription()+" ");
                            sqlBuilder.append("rapport ");
                            sqlBuilder.append(listFiltre.get(i).getOrdre()+";");
                        }
                        else
                        {
                            sqlBuilder.append("c.nom = '"+listCategorie.get(k).getNom()+"' ");
                            sqlBuilder.append(listFiltre.get(i).getDescription()+" ");
                            sqlBuilder.append(listFiltre.get(i).getParam()+" ");
                            sqlBuilder.append(listFiltre.get(i).getOrdre()+";");
                        }
                    }
                }
            }
            String sql = "";
            if (sqlBuilder.toString().contains("where"))
            {
                String[] sqlTabWhere = sqlBuilder.toString().split("where");
                String[] sqlTab = sqlTabWhere[1].split(";");
                sql = sqlTabWhere[0]+"where "+sqlTab[0]+";";
            }
            else
            {
                String[] sqlTab = sqlBuilder.toString().split(";");
                sql = sqlTab[0]+";";
            }
            System.out.println("sql : "+sql);
            valiny = sql;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return valiny;
    }

    public static List<Produit> searchEverythink(Connection connection, String query)
    {
        boolean isOuvert = false;
        List<Produit> valiny = new ArrayList<>();
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
                temp.setMarque(resultSet.getString(2));
                temp.setNom(resultSet.getString(1));
                temp.setPrix(resultSet.getDouble("prix"));
                temp.setQualite(resultSet.getInt("qualite"));
                temp.setRapport(resultSet.getDouble("rapport"));
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
            System.out.println("Produit searchEverythink issues");
            e.printStackTrace();
        }
        return valiny;
    }
}
