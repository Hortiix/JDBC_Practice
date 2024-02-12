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

}
