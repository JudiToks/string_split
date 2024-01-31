package mg.string_split.modeles.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect
{
    public static Connection connectToPostgre() throws SQLException {
        Connection connection = null;
        String url = "jdbc:postgresql://localhost:5432/string_split";
        String user = "postgres";
        String password = "root";
        try
        {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("PostgreSQL JDBC driver not found!");
            e.printStackTrace();
            connection.close();
        }
        catch (SQLException e)
        {
            System.out.println("Connection failed!");
            e.printStackTrace();
            connection.close();
        }
        return connection;
    }
}
