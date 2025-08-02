package animationvehicules;

import animationvehicules.model.parser.ParserGeoJSON;
import animationvehicules.model.parser.ParserInstance;
import animationvehicules.model.parser.ParserSolution;
import animationvehicules.model.parser.ParserTimeMatrix;

/**
 * Permet de tester les Parser.
 */
public class DemoParser {

    public DemoParser() {
        String file = "i1_1_0";

        ParserInstance parser = new ParserInstance("instances/" + file + ".txt");
        System.out.println("========== Informations générales : ==========");
        parser.displayInfoGeneral();
        System.out.println();

        for (int i = 1; i <= parser.getNumberRequest(); i++) {
            System.out.println("========== Requête n°" + i + ": ==========");
            parser.displayRequest(i);
        }

        System.out.println();
        System.out.println("========== Temps entre arrets 42 et 1: ==========");
        ParserTimeMatrix parserTime = new ParserTimeMatrix("instances/" + "d" + file.substring(1) + ".txt");
        System.out.println("Time : " + parserTime.getTime(42, 1));

        System.out.println();
        ParserSolution parserSol = new ParserSolution("solutions/" + "s" + file.substring(1) + ".txt");
        for (int h = 0; h < parserSol.getNumberRoads(); h++) {
            System.out.println("========== Véhicules " + h + ": ==========");
            parserSol.displayRoad(parserSol.getRoad(h));
            System.out.println("Nb de sommets visités: " + parserSol.getNumberNodesForRoad(parserSol.getRoad(h)));
        }

        parserSol.displayDataForRoad(parserSol.getRoad(0));

        ParserGeoJSON pgj = new ParserGeoJSON("instances/" + "l" + file.substring(1) + ".geojson");
        pgj.parse();
        System.out.println(pgj);
    }

    public static void main(String[] args) {
        new DemoParser();
    }

}
