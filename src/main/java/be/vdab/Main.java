package be.vdab;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        var repository = new PlantRepository();
        try {
            System.out.print(repository.verwijderdeBierenNullAlcohol());
            System.out.println(" Bieren verwijderde .");
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }
    }
}
