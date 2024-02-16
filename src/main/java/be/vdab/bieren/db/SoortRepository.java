package be.vdab.bieren.db;

import be.vdab.bieren.Soorten;
import be.vdab.bieren.db.AbstractRepository;

import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SoortRepository extends AbstractRepository {

    public ArrayList<String> findAllNaam() throws SQLException {
        var sql = """
                    SELECT soorten.naam
                    FROM soorten
                """;
        var namen = new ArrayList<String>();
        var connection = getConection();
        var statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            namen.add(resultSet.getString(1));
        }
        return namen;
        /**
         * OR
         *
         * for (var resultSet = statement.executeQuery(); result.next(); ) {
         *      namen.add(result.getString( "naam"));
         * }
         */
    }

    private Soorten soortFactory(ResultSet resultSet) throws SQLException {
        return new Soorten(resultSet.getLong("id"), resultSet.getString("naam"));
    }

    public List<Soorten> findAllOrderByeNaam() throws SQLException {
        var sql = """
                    SELECT soorten.id,soorten.naam
                    FROM soorten
                    ORDER BY naam
                """;
        var soortens = new ArrayList<Soorten>();
        var connection = getConection();
        var statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            soortens.add(soortFactory(resultSet));
        }
        return soortens;
    }

    public List<String> bierenVanSorten(String soorten) throws SQLException {
        List<String> listOfBieren = new ArrayList<>();
        String sql = """
                SELECT bieren.naam
                FROM bieren
                where bieren.soortId = (select soorten.id from soorten where soorten.naam LIKE ?);
                """;
        try (var connection = getConection()) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            var statement = connection.prepareStatement(sql);
            statement.setString(1, soorten);
            for(var result = statement.executeQuery(); result.next();){
                listOfBieren.add(result.getString(1));
            }
            connection.commit();
        }

        return listOfBieren;
    }
}
