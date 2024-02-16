package be.vdab;

import be.vdab.bieren.BierenPerBrouwer;
import be.vdab.bieren.db.BierenRepository;
import be.vdab.bieren.db.BrouwersRepository;
import be.vdab.bieren.db.SoortRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        SoortRepository soortRepository = new SoortRepository();
        BierenRepository bierenRepository = new BierenRepository();
        BrouwersRepository brouwersRepository = new BrouwersRepository();
       // brouwersRepository.allBrouwers().forEach(System.out::println);
        bierenRepository.brouwer1Faillietai();
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
                case 8 -> tonenBiereMaand(bierenRepository);
                case 9 -> tonenBierenPerBrouwers(brouwersRepository);
            }
        }


    }

    private static void tonenBierenPerBrouwers(BrouwersRepository brouwersRepository) {
        List<BierenPerBrouwer> result = null;
        try {
            result = brouwersRepository.aantalBierenPerBrouwer();
        } catch (SQLException e) {
           System.err.println(e.getErrorCode());
        }
        assert result != null;
        result.forEach(System.out::println);
    }

    private static void tonenBiereMaand(BierenRepository bierenRepository) {
        int mand;
        do {
            System.out.println("Give me the nummer of the mand");
            mand = sc.nextInt();
        }
        while (mand > 12 || mand < 1);
        try {
            bierenRepository.tonenAllBierenVanXMand(mand);
        } catch (SQLException e) {
            System.err.println(e.getErrorCode());
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
        var sb = new StringBuilder("MENU : ");
        sb
                .append("\n").append(1).append("Exit")
                .append("\n").append(2).append(" tonenVerwijderdeBierenNullAlcohol")
                .append("\n").append(3).append(" findAllNaamSoort")
                .append("\n").append(4).append(" brouwersRepMix")
                .append("\n").append(5).append(" findAllOrderByeNaam")
                .append("\n").append(6).append(" omzetTussen")
                .append("\n").append(7).append(" findById")
                .append("\n").append(8).append(" tonenBiereMaand")
                .append("\n").append(9).append(" tonenBierenPerBrouwers");
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
