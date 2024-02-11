package be.vdab;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final  String URL = "jdbc:mysql://localhost/bieren";
    private static final String USER = "cursist";
    private static final String PASSWORD = "cursist";

    public static void main(String[] args) {
        connect();
    }
    public static void connect(){

    }
}
