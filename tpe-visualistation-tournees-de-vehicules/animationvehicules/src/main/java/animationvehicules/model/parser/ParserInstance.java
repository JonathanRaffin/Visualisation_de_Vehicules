package animationvehicules.model.parser;

public class ParserInstance extends Parser {

    /**
     * Ouvre un fichier et enregistre chaque données.
     * 
     * @param path : le chemin du fichier à analyser.
     */
    public ParserInstance(String path) {
        super(path);
        this.openingFile();
    }

    /**
     * @return le nombre de requêtes de l'instance.
     */
    public int getNumberRequest() {
        return Integer.valueOf(this.lines.get(0)[0]);
    }

    /**
     * @return le nombre de véhicules de l'instance.
     */
    public int getNumberVehicles() {
        return Integer.valueOf(this.lines.get(0)[1]);
    }

    /**
     * @return le nombre d'arrêts de l'instance.
     */
    public int getNumberStop() {
        return Integer.valueOf(this.lines.get(0)[2]);
    }

    /**
     * @return la capacité des véhicules de l'instance.
     */
    public int getVehiclesCapacity() {
        return Integer.valueOf(this.lines.get(0)[3]);
    }

    /**
     * @return l'horizon de temps de l'instance.
     */
    public int getTimeHorizon() {
        return Integer.valueOf(this.lines.get(0)[4]);
    }

    /**
     * Retourne l'indice de l'origine pour la requête n°id.
     * 
     * @param id : index de la requête.
     * @return : l'indice de l'origine.
     */
    public int getIndexSource(int id) {
        return Integer.valueOf(this.lines.get(id)[0]);
    }

    /**
     * Retourne la fenêtre de temps de l'origine pour la requête n°id.
     * 
     * @param id : index de la requête.
     * @return : la fenêtre de temps de l'origine.
     */
    public String getTimeSource(int id) {
        return this.lines.get(id)[1] + " " + this.lines.get(id)[2];
    }

    /**
     * Retourne le temps de départ de l'origine pour la requête n°id.
     * 
     * @param id : index de la requête.
     * @return : le temps de départ de l'origine.
     */
    public int getTimeStartSource(int id) {
        return Integer.valueOf(this.lines.get(id)[1]);
    }

    /**
     * Retourne le temps de fin de l'origine pour la requête n°id.
     * 
     * @param id : index de la requête.
     * @return : le temps de fin de l'origine.
     */
    public int getTimeEndSource(int id) {
        return Integer.valueOf(this.lines.get(id)[2]);
    }

    /**
     * Retourne l'indice de la destination pour la requête n°id.
     * 
     * @param id : index de la requête.
     * @return : l'indice de la destination.
     */
    public int getIndexDestination(int id) {
        return Integer.valueOf(this.lines.get(id)[3]);
    }

    /**
     * Retourne la fenêtre de temps de la destination pour la requête n°id.
     * 
     * @param id : index de la requête.
     * @return : la fenêtre de temps de la destination.
     */
    public String getTimeDestination(int id) {
        return this.lines.get(id)[4] + " " + this.lines.get(id)[5];
    }

    /**
     * Retourne le temps de départ de la destination pour la requête n°id.
     * 
     * @param id : index de la requête.
     * @return : le temps de départ de la destination.
     */
    public int getTimeStartDestination(int id) {
        return Integer.valueOf(this.lines.get(id)[4]);
    }

    /**
     * Retourne le temps de fin de la destination pour la requête n°id.
     * 
     * @param id : index de la requête.
     * @return : le temps de fin de la destination.
     */
    public int getTimeEndDestination(int id) {
        return Integer.valueOf(this.lines.get(id)[5]);
    }

    /**
     * Retourne le temps maximum du trajet pour la requête n°id.
     * 
     * @param id : index de la requête.
     * @return : le temps maximum du trajet.
     */
    public int getTimeMax(int id) {
        return Integer.valueOf(this.lines.get(id)[6]);
    }

    /**
     * Retourne la quantité de personnes pour la requête n°id.
     * 
     * @param id : index de la requête.
     * @return : la quantité de personnes.
     */
    public int getNumberOfPeople(int id) {
        return Integer.valueOf(this.lines.get(id)[7]);
    }

    /**
     * Retourne le temps de service pour la requête n°id.
     * 
     * @param id : index de la requête.
     * @return : le temps de service.
     */
    public int getTimeService(int id) {
        return Integer.valueOf(this.lines.get(id)[8]);
    }

    /**
     * Affiche les informations générales de l'instance.
     * Ce sont les infos de la première ligne du fichier.
     */
    public void displayInfoGeneral() {
        System.out.println("Nb de requetes: " + this.getNumberRequest());
        System.out.println("Nb de véhicules: " + this.getNumberVehicles());
        System.out.println("Nb d'arrets: " + this.getNumberStop());
        System.out.println("Capacite du véhicules: " + this.getVehiclesCapacity());
        System.out.println("Horizon de temps: " + this.getTimeHorizon());
    }

    /**
     * Affiche toutes les infos de la requête n°id.
     * 
     * @param id : index de la requête.
     */
    public void displayRequest(int id) {
        System.out.println("Indice de l'origine: " + this.getIndexSource(id));
        System.out.println("Fenêtre de temps de l'origine: " + this.getTimeSource(id));
        System.out.println("Temps de départ de l'origine: " + this.getTimeStartSource(id));
        System.out.println("Temps de fin  de l'origine: " + this.getTimeEndSource(id));
        System.out.println("Indice de la destination: " + this.getIndexDestination(id));
        System.out.println("Fenêtre de temps de la destination: " + this.getTimeDestination(id));
        System.out.println("Temps de départ  de la destination: " + this.getTimeStartDestination(id));
        System.out.println("Temps de fin  de la destination: " + this.getTimeEndDestination(id));
        System.out.println("Temps max: " + this.getTimeMax(id));
        System.out.println("Nombres de personnes: " + this.getNumberOfPeople(id));
        System.out.println("Temps de service: " + this.getTimeService(id));
    }

}