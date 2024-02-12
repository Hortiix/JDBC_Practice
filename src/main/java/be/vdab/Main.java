package be.vdab;

import be.vdab.bieren.db.BierenRepository;
import be.vdab.bieren.db.BrouwersRepository;
import be.vdab.bieren.db.SoortRepository;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        BierenRepository bierenRepository = new BierenRepository();
        try {
            System.out.print(bierenRepository.verwijderdeBierenNullAlcohol());
            System.out.println(" Bieren verwijderde .");
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }
        SoortRepository soortRepository = new SoortRepository();
        try {
            System.out.println("naam of soorten");
            soortRepository.findAllNaam().forEach(System.out::println);
        }catch (SQLException sqlException){
            System.err.println(sqlException.getErrorCode());
        }
        try {
            BrouwersRepository brouwersRepository= new BrouwersRepository();
            System.out.println("gemiddel omzet :"+brouwersRepository.findGemiddeldeOmzet());
            brouwersRepository.findByOmzetGroterDanGemiddelde();
            brouwersRepository.allBrouwers().forEach(System.out::println);
            soortRepository.findAllOrderByeNaam().forEach(System.out::println);
        } catch (SQLException e) {
            System.err.println(e.getErrorCode());
        }
    }
}
