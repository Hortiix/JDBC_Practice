package be.vdab.bieren.db;

import java.sql.Connection;
import java.sql.SQLException;

public class BierenRepository extends AbstractRepository {
    public int verwijderdeBierenNullAlcohol() throws SQLException {
        var sql = """
                delete from bieren
                where alcohol IS NULL
                """;
        var connection = super.getConection();
        var statement = connection.prepareStatement(sql);
        return statement.executeUpdate();
    }

    public void brouwer1Faillietai() throws SQLException {
        var udateBierenVanafAlcool = """
                UPDATE bieren
                SET brouwerId = 2
                WHERE bieren.brouwerId = 1 AND alcohol > 8.5
                """;
        var udateBierenRest = """
                UPDATE bieren
                SET brouwerId = 3
                WHERE bieren.brouwerId = 1 AND alcohol <= 8.5
                """;
        var delete = """
                DELETE FROM bieren
                where brouwerId = 1
                """;
        var connection = getConection();
        var statementAlc = connection.prepareStatement(udateBierenVanafAlcool);
        var statementRest = connection.prepareStatement(udateBierenRest);
        var statementDel = connection.prepareStatement(delete);
        connection.setAutoCommit(false);
        System.out.println(statementRest.executeUpdate());
        System.out.println(statementAlc.executeUpdate());
        System.out.println(statementDel.executeUpdate());
        connection.commit();
    }

    public void tonenAllBierenVanXMand(int mand) throws SQLException {
        var sql = """
                SELECT id,naam,brouwerId,soortId,alcohol,sinds
                FROM bieren
                WHERE MONTH(sinds) = ?
                ORDER BY sinds;
                """;
        var connection = getConection();
        var statement = connection.prepareStatement(sql);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        connection.setAutoCommit(false);
        statement.setInt(1, mand);
        StringBuilder sb = new StringBuilder("RESULT FOR MAND -> ").append(mand);
        for (var result = statement.executeQuery(); result.next(); ) {
            String formattedResult = String.format("\n\t%d\n\t%s\n\t%f\n\t%s", result.getLong("id"), result.getString("naam"), result.getDouble("alcohol"), result.getDate("sinds").toString());
            sb.append("\n\n").append(formattedResult);
        }
        connection.commit();
        System.out.println(sb.toString());

    }

}
