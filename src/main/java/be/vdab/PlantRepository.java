package be.vdab;

import java.sql.SQLException;

public class PlantRepository extends AbstractRepository{
    public int verwijderdeBierenNullAlcohol() throws SQLException{
        var sql = """
                delete from bieren
                where alcohol IS NULL
                """;
        try (var connection = super.getConection()){
            var statement = connection.prepareStatement(sql);
            return statement.executeUpdate();
        }
    }
}
