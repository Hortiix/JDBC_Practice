package be.vdab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class   AbstractRepository {

    private static final  String URL = "jdbc:mysql://localhost/bieren";
    private static final String USER = "cursist";
    private static final String PASSWORD = "cursist";

    protected Connection getConection() throws SQLException {
       return DriverManager.getConnection(URL,USER,PASSWORD);
    }
}
