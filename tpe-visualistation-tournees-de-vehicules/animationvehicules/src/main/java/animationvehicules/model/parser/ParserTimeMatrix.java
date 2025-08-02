package animationvehicules.model.parser;

public class ParserTimeMatrix extends Parser {

    /**
     * Ouvre un fichier et enregistre chaque données.
     * 
     * @param path : le chemin du fichier à analyser.
     */
    public ParserTimeMatrix(String path) {
        super(path);
        this.openingFile();
    }

    /**
     * Retourne le temps entre la source et la destination.
     * 
     * @param source      : arrêt où on se situe.
     * @param destination : arrêt où on souhaite aller.
     * @return le temps en secondes.
     */
    public int getTime(int source, int destination) {
        return Integer.valueOf(this.lines.get(source - 1)[destination - 1]);
    }

}