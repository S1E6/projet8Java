package com.model.projet8;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

/**
 *
 * @author SIEG
 */
public class ConnectDB{

    private final String url = "jdbc:postgresql://localhost:5432/projet8";
    private final String user = "postgres";
    private final String password = "pathetic";
    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}