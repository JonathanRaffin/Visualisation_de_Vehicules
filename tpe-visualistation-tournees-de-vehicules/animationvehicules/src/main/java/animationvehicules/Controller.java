package animationvehicules;

import java.util.ArrayList;

import animationvehicules.model.animation.AnimationInstance;
import animationvehicules.model.animation.AnimationInstanceAndSolution;
import animationvehicules.model.animation.AnimationSolution;
import animationvehicules.model.animation.Car;
import animationvehicules.model.animation.IAnimation;
import animationvehicules.model.parser.ParserGeoJSON;
import animationvehicules.model.parser.ParserInstance;
import animationvehicules.model.parser.ParserSolution;
import animationvehicules.view.gui.Control;
import animationvehicules.view.gui.GUI;

/**
 * Controller du projet.
 */
public class Controller {

    private final int speed = 1000;

    private IAnimation animation;
    private GUI gui;
    private Control guiControl;
    private ParserInstance parserInstance;
    private ParserSolution parserSolution;
    private ParserGeoJSON pgj;
    private ParserGeoJSON pgjTram;

    /**
     * Lance la fenetre de choix du fichier.
     * 
     * @throws InterruptedException
     */
    public Controller() throws InterruptedException {
        new ChoixVisualisation(this);
    }

    /**
     * Execute une animation avec une vitesse pré-défini.
     * 
     * @param animation : animation à visualiser.
     * @param fileName  : nom du fichier pour l'animation.
     * @throws InterruptedException
     */
    public void lancerAnimation(String animation, String fileName) throws InterruptedException {
        // Parser pour le fichier des localisations du tram
        this.pgjTram = new ParserGeoJSON("stations_localisations.geojson");
        this.pgjTram.parse();
        if (animation.equals("instance") || animation.equals("i")) {
            // Parser pour les fichiers d'instances
            this.parserInstance = new ParserInstance("instances/" + fileName + ".txt");
            this.pgj = new ParserGeoJSON("instances/" + "l" + fileName.substring(1) + ".geojson");
            pgj.parse();

            // Creation de l'animation d'instance dans le model et lancement de la vue
            this.animation = new AnimationInstance(this, speed, parserInstance, pgj);
            this.gui = new GUI(this, speed, pgj, pgjTram, parserInstance.getTimeHorizon());
            this.guiControl = new Control(this, this.gui, speed, parserInstance.getTimeHorizon());
            this.guiControl.startTimer((int) 1000 / speed);
        } else if (animation.equals("solution") || animation.equals("s")) {
            // Parser pour les fichiers d'instances et de solution
            this.parserInstance = new ParserInstance("instances/" + fileName + ".txt");
            this.parserSolution = new ParserSolution("solutions/s" + fileName.substring(1) + ".txt");
            this.pgj = new ParserGeoJSON("instances/l" + fileName.substring(1) + ".geojson");
            pgj.parse();

            // Creation de l'animation de solution dans le model et lancement de la vue
            this.animation = new AnimationSolution(this, speed, parserInstance, parserSolution, pgj, pgjTram);
            this.gui = new GUI(this, speed, pgj, pgjTram, parserInstance.getTimeHorizon());
            this.guiControl = new Control(this, this.gui, speed, parserInstance.getTimeHorizon());
            this.guiControl.startTimer((int) 1000 / speed);
        } else if (animation.equals("instancesAndSolution") || animation.equals("is")) {
            // Parser pour les fichiers d'instances et de solution
            this.parserInstance = new ParserInstance("instances/" + fileName + ".txt");
            this.parserSolution = new ParserSolution("solutions/s" + fileName.substring(1) + ".txt");
            this.pgj = new ParserGeoJSON("instances/l" + fileName.substring(1) + ".geojson");
            pgj.parse();

            // Creation de l'animation de solution dans le model et lancement de la vue
            this.animation = new AnimationInstanceAndSolution(this, speed, parserInstance, parserSolution, pgj, pgjTram);
            this.gui = new GUI(this, speed, pgj, pgjTram, parserInstance.getTimeHorizon());
            this.guiControl = new Control(this, this.gui, speed, parserInstance.getTimeHorizon());
            this.guiControl.startTimer((int) 1000 / speed);
        } else {
            System.out.println("Veuillez choisir entre 'instance' ('i'), 'solution' ('s') ou 'instances_solutions' ('is').");
        }
    }

    /**
     * Lance le check des informations en fonction du temps
     * 
     * @param time le temps actuel
     */
    public void dataChecking(int time) {
        animation.dataChecking(time);
    }

    /**
     * Affiche graphiquement le début du temps d'origine d'une requete
     * 
     * @param time le temps actuel
     * @param i    index de la requete
     */
    public void displayStartOriginTime(String animation, int time, int i) {
        gui.displayStartOriginTime(animation, time, i);
    }

    /**
     * Affiche graphiquement la fin du temps d'origine d'une requete
     * 
     * @param time le temps actuel
     * @param i    index de la requete
     */
    public void displayEndOriginTime(int time, int i) {
        gui.displayEndOriginTime(time, i);
    }

    /**
     * Affiche graphiquement le début du temps de destination d'une requete
     * 
     * @param time le temps actuel
     * @param i    index de la requete
     */
    public void displayStartDestinationTime(int time, int i) {
        gui.displayStartDestinationTime(time, i);
    }

    /**
     * Affiche graphiquement la fin du temps de destination d'une requete
     * 
     * @param time le temps actuel
     * @param i    index de la requete
     */
    public void displayEndDestinationTime(int time, int i) {
        gui.displayEndDestinationTime(time, i);
    }

    /**
     * Affiche graphiquement un véhicule qui quitte le depot
     * 
     * @param time  le temps actuel
     * @param i     index du vehicule
     * @param j     index de la requete
     * @param route
     */
    public void displayVehicleLeaveDepot(int time, int i, int j, ArrayList<String[]> route) {
        gui.displayVehicleLeaveDepot(time, i, j, route);
    }

    /**
     * Affiche graphiquement un véhicule qui retourne au depot
     * 
     * @param time le temps actuel
     * @param i    index du vehicule
     * @param j    index de la requete
     */
    public void displayVehicleBackToDepot(int time, int i, int j) {
        gui.displayVehicleBackToDepot(time, i, j);
    }

    /**
     * Affiche graphiquement un véhicule qui arrive à un point
     * 
     * @param time  le temps actuel
     * @param i     index du vehicule
     * @param j     index de la requete
     * @param route
     */
    public void displayVehicleArrivedOnPoint(int time, int i, int j, ArrayList<String[]> route) {
        gui.displayVehicleArrivedOnPoint(time, i, j, route);
    }

    /**
     * Affiche graphiquement un véhicule qui quitte un point
     * 
     * @param time  le temps actuel
     * @param i     index du vehicule
     * @param j     index de la requete
     * @param route
     */
    public void displayVehicleLeavePoint(int time, int i, int j, ArrayList<String[]> route) {
        gui.displayVehicleLeavePoint(time, i, j, route);
    }

    /**
     * Creer une voiture dans le modele et l'affiche dans la vue
     * 
     * @param nbVehicle        id du vehicule
     * @param indexSource      index du sommet source
     * @param indexDestination index du sommet destination
     */
    public void createCar(int nbVehicle, int indexSource, int indexDestination) {
        Car createdCar = ((IAnimation) this.animation).createCar(nbVehicle, indexSource, indexDestination);
        gui.getMap().createCar(createdCar);
    }

    /**
     * Met à jour la position d'une voiture dans le modele et l'affiche dans la vue
     * 
     * @param nbVehicle            id du vehicule
     * @param percentageCompletion pourcentage de completion de la requete actuelle
     */
    public void updateCarPosition(int nbVehicle, double percentageCompletion) {
        Car updatedCar = ((IAnimation) this.animation).updateCarPosition(nbVehicle, percentageCompletion);
        gui.getMap().updateCarPosition(updatedCar);
    }

    /**
     * Met à jour la requete d'une voiture dans le modele
     * 
     * @param nbVehicle        id du vehicule
     * @param indexSource      index du nouveau sommet source
     * @param indexDestination index du nouveau sommet destination
     */
    public void updateCarRoute(int nbVehicle, int indexSource, int indexDestination) {
        ((IAnimation) this.animation).updateCarRoute(nbVehicle, indexSource, indexDestination);
    }

    /**
     * Recupère le parser du fichier instance
     * 
     * @return le parser instance
     */
    public ParserInstance getInstanceParser() {
        return this.parserInstance;
    }

    /**
     * Recupère le parser du fichier solution
     * 
     * @return le parser solution
     */
    public ParserSolution getSolutionParser() {
        return this.parserSolution;
    }

    /**
     * Recupère le parser du fichier GeoJSON
     * 
     * @return le parser GeoJSON
     */
    public ParserGeoJSON getGeoJSONParser() {
        return this.pgj;
    }
}
