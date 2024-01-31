package mg.string_split;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.string_split.modeles.Categorie;
import mg.string_split.modeles.Filtre_methode;
import mg.string_split.modeles.Produit;
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
            String sql = Produit.getSqlQuery(connection, search);
            List<Produit> listProduit = Produit.searchEverythink(connection, sql);
            request.setAttribute("listProduit", listProduit);
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
