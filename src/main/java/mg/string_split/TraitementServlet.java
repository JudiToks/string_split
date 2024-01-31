package mg.string_split;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.string_split.modeles.Categorie;
import mg.string_split.modeles.Filtre_methode;
import mg.string_split.modeles.connect.Connect;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "traitementServlet", value = "/traitement-servlet")
public class TraitementServlet extends HttpServlet
{
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            Connection connection = Connect.connectToPostgre();
            String search = request.getParameter("search");
            System.out.println("search string : "+search);
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
                    "where ");
            for (int k = 0; k < listCategorie.size(); k++)
            {
                for (int i = 0; i < listFiltre.size(); i++)
                {
                    if (search.contains(listCategorie.get(k).getNom()) && search.contains(listFiltre.get(i).getFilre()) && search.contains(listFiltre.get(i).getParam()))
                    {
                        if (search.contains("rapport"))
                        {
                            sqlBuilder.append("c.nom = '"+listCategorie.get(k).getNom()+"' ");
                            sqlBuilder.append(listFiltre.get(i).getDescription()+" ");
                            sqlBuilder.append("rapport ");
                            sqlBuilder.append(listFiltre.get(i).getOrdre()+";");
                        }
                        else
                        {
                            System.out.println("filtre : "+listFiltre.get(i).getFilre()+" | parametre : "+listFiltre.get(i).getParam()+" | order : "+listFiltre.get(i).getOrdre());
                            sqlBuilder.append("c.nom = '"+listCategorie.get(k).getNom()+"' ");
                            sqlBuilder.append(listFiltre.get(i).getDescription()+" ");
                            sqlBuilder.append(listFiltre.get(i).getParam()+" ");
                            sqlBuilder.append(listFiltre.get(i).getOrdre()+";");
                        }
                    }
                }
            }
            String[] sqlTabWhere = sqlBuilder.toString().split("where");
            String[] sqlTab = sqlTabWhere[1].split(";");
            String sql = sqlTabWhere[0]+"where "+sqlTab[0]+";";
            System.out.println("sql : "+sql);
            connection.close();

            RequestDispatcher dispatcher = request.getRequestDispatcher("result.jsp");
            dispatcher.forward(request, response);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        processRequest(req, resp);
    }
}
