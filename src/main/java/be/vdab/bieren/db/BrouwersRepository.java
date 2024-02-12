package be.vdab.bieren.db;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import be.vdab.bieren.*;

import java.util.*;

public class BrouwersRepository extends AbstractRepository {

    private boolean isInitialise;
//    private ArrayList<Brouwers> listVanBrouwers;
//
//    public BigDecimal gemiddeldeOmzet() throws SQLException {
//        var average = allBrouwers().stream().mapToDouble(Brouwers::getOmzet).average();
//        if (average.isPresent()) return BigDecimal.valueOf(average.getAsDouble());
//        return BigDecimal.ZERO;
//    }
//
//    public List<Brouwers> allBrouwers() throws SQLException {
//        if (isInitialise) return listVanBrouwers.stream().toList();
//        var sql = """
//                SELECT id,naam,adres,postcode,gemeente,omzet
//                FROM brouwers
//                """;
//        listVanBrouwers = new ArrayList<>();
//        try (ResultSet resultSet = getConection()
//                .prepareStatement(sql)
//                .executeQuery()) {
//            while (resultSet.next()) {
//                listVanBrouwers.add(new Brouwers(resultSet.getLong("id"), resultSet.getString("naam"), resultSet.getString("adres"), resultSet.getInt("postcode"), resultSet.getString("gemeente"), resultSet.getInt("omzet")));
//            }
//        }
//        return listVanBrouwers.stream().toList();
//    }
//
//    public List<Brouwers> brouwersOmzetHogerDanGemiddelde() {
//        return listVanBrouwers.stream().filter(brouwers -> {
//            try {
//                return gemiddeldeOmzet().compareTo(BigDecimal.valueOf(brouwers.getOmzet())) < 0;
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }).toList();
//    }

    public BigDecimal findGemiddeldeOmzet() throws SQLException {
        var sql = """
                select avg(omzet) as gemiddelde
                from brouwers
                """;
        try (var connection = getConection();
             var statement = connection.prepareStatement(sql)
        ) {
            var result = statement.executeQuery();
            result.next();
            return result.getBigDecimal("gemiddelde");
        }
    }

    private Brouwers naarBrouwer(ResultSet result) throws SQLException {
        return new Brouwers(result.getLong("id"), result.getString("naam"),
                result.getString("adres"), result.getInt("postcode"),
                result.getString("gemeente"), result.getInt("omzet"));
    }

    public List<Brouwers> findByOmzetGroterDanGemiddelde() throws SQLException {
        var brouwers = new ArrayList<Brouwers>();
        var sql = """
                select id, naam, adres, postcode, gemeente, omzet
                from brouwers
                where omzet > (select avg(omzet) from brouwers)
                order by omzet
                """;
        try (var connection = getConection()) {
            var statement = connection.prepareStatement(sql);
            for (var result = statement.executeQuery(); result.next(); ) {
                brouwers.add(naarBrouwer(result));
            }
            return brouwers;
        }
    }

    public List<Brouwers> allBrouwers() throws SQLException {
        var brouwers = new ArrayList<Brouwers>();
        var sql = """
                select id, naam, adres, postcode, gemeente, omzet
                from brouwers
                """;
        try (var connection = getConection()) {
            var statement = connection.prepareStatement(sql);
            for (var result = statement.executeQuery(); result.next(); ) {
                brouwers.add(naarBrouwer(result));
            }
            return brouwers;
        }
    }
    public Optional<Brouwers> findById(long id)throws SQLException{
        var sql = """
                select id, naam, adres, postcode, gemeente, omzet
                from brouwers
                where id = ? 
                """;
        try(var connection = getConection()){
            var statement = connection.prepareStatement(sql);
            statement.setLong(1,id);
            var result = statement.executeQuery();
            return result.next() ? Optional.of(naarBrouwer(result)):Optional.empty();
        }
    }
    public List<Brouwers> vanTotOmzet(int min,int max)throws SQLException{
        if (max < 0 && min > max) throw new IllegalArgumentException();
        var brouwers = new ArrayList<Brouwers>();
        var sql = """
                select id, naam, adres, postcode, gemeente, omzet
                from brouwers
                where omzet > ? AND omzet < ?
                ORDER BY OMZET , ID
                """;
        try (var connection = getConection()) {
            var statement = connection.prepareStatement(sql);
            statement.setInt(1,min);
            statement.setInt(2,max);
            for (var result = statement.executeQuery(); result.next(); ) {
                brouwers.add(naarBrouwer(result));
            }
            return brouwers;
        }
    }
    public List<Brouwers> vanTotOmzetProcedure(int min,int max)throws SQLException{
        if (max < 0 && min > max) throw new IllegalArgumentException();
        var brouwers = new ArrayList<Brouwers>();

        try (var connection = getConection()) {
            var statement = connection.prepareCall("{call VanTot(?,?)}");
            statement.setInt(1,min);
            statement.setInt(2,max);
            for (var result = statement.executeQuery(); result.next(); ) {
                brouwers.add(naarBrouwer(result));
            }
            return brouwers;
        }
    }
}
