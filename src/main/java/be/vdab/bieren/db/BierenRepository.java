package be.vdab.bieren.db;

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

    public void brouwer1Faillietai()throws SQLException{
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

}
