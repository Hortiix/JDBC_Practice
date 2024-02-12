package be.vdab;

import be.vdab.bieren.db.BierenRepository;
import be.vdab.bieren.db.BrouwersRepository;
import be.vdab.bieren.db.SoortRepository;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class Main {
private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        SoortRepository soortRepository = new SoortRepository();
        BierenRepository bierenRepository = new BierenRepository();
        BrouwersRepository brouwersRepository = new BrouwersRepository();
        while (true) {
            tonenMenu();
            switch (sc.nextInt()) {
                case 1 -> System.exit(0);
                case 2 -> tonenVerwijderdeBierenNullAlcohol(bierenRepository);
                case 3 -> findAllNaamSoort(soortRepository);
                case 4 -> brouwersRepMix(brouwersRepository);
                case 5 -> soortRepository.findAllOrderByeNaam().forEach(System.out::println);
                case 6 -> omzetTussen(brouwersRepository);
                case 7 -> findbyId(brouwersRepository);
            }
        }


    }

    private static void findbyId(BrouwersRepository brouwersRepository) {
        System.out.println("Give me the id");
        long id = sc.nextLong();
        try {
            brouwersRepository.findById(id).ifPresentOrElse(System.out::println,
                    ()-> System.out.println("Niet gevonden."));
        } catch (SQLException e) {
            System.err.println(e.getErrorCode());
        }
    }

    private static void tonenMenu() {
        var sb = new StringBuilder("MENU");
        sb.append(2).append(" = tonenVerwijderdeBierenNullAlcohol")
                .append("\n").append(3).append("findAllNaamSoort")
                .append("\n").append(4).append("brouwersRepMix")
                //.append("\n").append(5).append("soortRepository")
                .append("\n").append(5).append("findAllOrderByeNaam")
                .append("\n").append(6).append("omzetTussen");
        sb.append("\n").append(7).append("findById");
        System.out.println(sb);
    }

    public static void omzetTussen(BrouwersRepository brouwersRepository){
        System.out.println("Give me the min value");
        int min = sc.nextInt();
        System.out.println("give me the max value");
        int max = sc.nextInt();
        System.out.println("chose your methode ?");
        System.out.println("1 for classic methode");
        System.out.println("2 for procedure methode");

        if (sc.nextInt() == 1){
            try {
                brouwersRepository.vanTotOmzet(min,max).forEach(System.out::println);
            } catch (SQLException e) {
                System.err.println(e.getErrorCode());
            }
        }
        try{
            brouwersRepository.vanTotOmzetProcedure(min,max).forEach(System.out::println);
        } catch (SQLException e){
            System.err.println(e.getErrorCode());
        }

    }
    public static void brouwersRepMix(BrouwersRepository brouwersRepository) {
        try {
            System.out.println("gemiddel omzet :" + brouwersRepository.findGemiddeldeOmzet());
            brouwersRepository.findByOmzetGroterDanGemiddelde();
            brouwersRepository.allBrouwers().forEach(System.out::println);
        } catch (SQLException e) {
            System.err.println(e.getErrorCode());
        }
    }

    public static void findAllNaamSoort(SoortRepository soortRepository) {
        try {
            System.out.println("naam of soorten");
            soortRepository.findAllNaam().forEach(System.out::println);
        } catch (SQLException sqlException) {
            System.err.println(sqlException.getErrorCode());
        }
    }

    public static void tonenVerwijderdeBierenNullAlcohol(BierenRepository bierenRepository) {
        try {
            System.out.print(bierenRepository.verwijderdeBierenNullAlcohol());
            System.out.println(" Bieren verwijderde .");
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }
    }
}
