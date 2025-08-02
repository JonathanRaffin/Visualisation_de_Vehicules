package animationvehicules.model.parser;

import java.util.ArrayList;

public class ParserSolution extends Parser {

    /**
     * Ouvre un fichier et enregistre chaque données.
     * 
     * @param path : le chemin du fichier à analyser.
     */
    public ParserSolution(String path) {
        super(path);
        this.openingFile();
    }

    /**
     * Retourne l'indice de la requête.
     * 
     * @param route : route utilisé.
     * @param id    : index de l'étape à analyser (colonne).
     * @return : l'indice de la requête.
     */
    public int getIdRequestSolution(ArrayList<String[]> route, int id) {
        return Integer.valueOf(route.get(0)[id]);
    }

    /**
     * Retourne l'indice du sommet.
     * 
     * @param route : route utilisé.
     * @param id    : index de l'étape à analyser (colonne).
     * @return : l'indice du sommet.
     */
    public int getIdNodesSolution(ArrayList<String[]> route, int id) {
        return Integer.valueOf(route.get(1)[id]);
    }

    /**
     * Retourne le temps de la solution.
     * 
     * @param route : route utilisé.
     * @param id    : index de l'étape à analyser (colonne).
     * @return : le temps de la solution.
     */
    public int getTimeSolution(ArrayList<String[]> route, int id) {
        return Integer.valueOf(route.get(2)[id]);
    }

    /**
     * Retourne une route (3 lignes).
     * 
     * @param idRoute : index de la route souhaité.
     * @return : une route.
     */
    public ArrayList<String[]> getRoad(int idRoute) {
        idRoute = idRoute * 3;
        ArrayList<String[]> tmp = new ArrayList<>();
        tmp.add(this.lines.get(idRoute));
        tmp.add(this.lines.get(idRoute + 1));
        tmp.add(this.lines.get(idRoute + 2));
        return tmp;
    }

    /**
     * Affiche une route.
     * 
     * @param route : la route à afficher.
     */
    public void displayRoad(ArrayList<String[]> route) {
        String chaine = "";
        for (String[] str : route) {
            for (String subStr : str) {
                chaine += subStr + " ";
            }
            chaine += "\n";
        }
        System.out.println(chaine);
    }

    /**
     * Retourne le nombres de routes pour la solution.
     * 
     * @return : le nombres de routes.
     */
    public int getNumberRoads() {
        return this.lines.size() / 3;
    }

    /**
     * Retourne le nombre de sommets visités lors de cette route.
     * 
     * @param route : route uilisé.
     * @return : nombres de sommets visités.
     */
    public int getNumberNodesForRoad(ArrayList<String[]> route) {
        return route.get(0).length;
    }

    /**
     * Affiche étape par étape la route.
     * 
     * @param route : route utilisé.
     */
    public void displayDataForRoad(ArrayList<String[]> route) {
        for (int i = 0; i < this.getNumberNodesForRoad(route); i++) {
            System.out.println("===== Etape n°" + i + " =====");
            System.out.println("Id Requête: " + this.getIdRequestSolution(route, i));
            System.out.println("Id Sommets: " + this.getIdNodesSolution(route, i));
            System.out.println("Temps: " + this.getTimeSolution(route, i));
        }
    }
}
