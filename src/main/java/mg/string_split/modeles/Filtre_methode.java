package mg.string_split.modeles;

import mg.string_split.modeles.connect.Connect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Filtre_methode
{
    int id_filtre_methode;
    String description;
    String ordre;
    String filre;
    String param;

    public int getId_filtre_methode() {
        return id_filtre_methode;
    }

    public void setId_filtre_methode(int id_filtre_methode) {
        this.id_filtre_methode = id_filtre_methode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getOrdre() {
        return ordre;
    }

    public void setOrdre(String ordre) {
        this.ordre = ordre;
    }

    public String getFilre() {
        return filre;
    }

    public void setFilre(String filre) {
        this.filre = filre;
    }

    public static List<Filtre_methode> getAllFiltre(Connection connection)
    {
        boolean isOuvert = false;
        List<Filtre_methode> valiny = new ArrayList<>();
        String query = "select id_filtre_methode, description, ordre, nom, param from filtre_methode join filtre on filtre.id_filte = filtre_methode.id_filtre;";
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
                Filtre_methode temp = new Filtre_methode();
                temp.setId_filtre_methode(resultSet.getInt("id_filtre_methode"));
                temp.setDescription(resultSet.getString("description"));
                temp.setOrdre(resultSet.getString("ordre"));
                temp.setFilre(resultSet.getString("nom"));
                temp.setParam(resultSet.getString("param"));
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
            System.out.println("Filtre getAllFiltre issues");
            e.printStackTrace();
        }
        return valiny;
    }
}
