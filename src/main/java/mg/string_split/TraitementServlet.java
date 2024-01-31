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
            String[] paramtres = {"prix", "qualite"};
            List<Filtre_methode> listFiltre = Filtre_methode.getAllFiltre(connection);
            List<Categorie> listCategorie = Categorie.getAllCategorie(connection);
            StringBuilder sqlBuilder = new StringBuilder("select\n" +
                    "    produit.nom,\n" +
                    "    m.nom,\n" +
                    "    c.nom,\n" +
                    "    prix,\n" +
                    "    qualite\n" +
                    "from produit\n" +
                    "    join marque m on produit.id_marque = m.id_marque\n" +
                    "    join categorie c on m.id_categorie = c.id_categorie\n" +
                    "where c.nom = ");
            for (int k = 0; k < listCategorie.size(); k++)
            {
                if (search.contains(listCategorie.get(k).getNom()))
                {
                    sqlBuilder.append("'"+listCategorie.get(k).getNom()+"' ");
                    for (int i = 0; i < listFiltre.size(); i++)
                    {
                        if (search.contains(listFiltre.get(i).getFilre()))
                        {
                            sqlBuilder.append(listFiltre.get(i).getDescription()+" ");
                            for (int j = 0; j < paramtres.length; j++)
                            {
                                if (search.contains(paramtres[j]))
                                {
                                    sqlBuilder.append(paramtres[j]+" ");
                                    if (search.contains(listFiltre.get(i).getFilre()) && search.contains(listFiltre.get(i).getParam()))
                                    {
                                        sqlBuilder.append(listFiltre.get(i).getOrdre()+";");
                                    }
                                }
                            }
                        }
                    }
                }
            }
            String[] sqlTab = sqlBuilder.toString().split(";");
            String sql = sqlTab[0]+";";
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
