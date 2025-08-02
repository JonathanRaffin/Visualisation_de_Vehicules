package animationvehicules.model.animation;

import animationvehicules.Controller;
import animationvehicules.model.parser.ParserGeoJSON;
import animationvehicules.model.parser.ParserInstance;

public class AnimationInstance implements IAnimation {

    protected ParserInstance instance;
    protected ParserGeoJSON pgj;
    private Controller ctrl;

    /**
     * Initalise l'instance à analyser.
     * 
     * @param speed : vitesse pour la gestion du temps.
     */
    public AnimationInstance(Controller ctrl, int speed, ParserInstance pi, ParserGeoJSON pgj)
            throws InterruptedException {
        this.ctrl = ctrl;
        this.instance = pi;
        this.pgj = pgj;
    }

    /**
     * Vérifie les conditions et renvoie vers l'affichage correct.
     * Indique pour les requêtes, le temps d'origine et de destination.
     */
    @Override
    public void dataChecking(int time) {
        int nbRequest = this.instance.getNumberRequest();
        for (int i = 1; i <= nbRequest; i++) {
            if (time == this.instance.getTimeStartSource(i)) {
                ctrl.displayStartOriginTime("Instance", time, i);
            } else if (time == this.instance.getTimeEndSource(i)) {
                ctrl.displayEndOriginTime(time, i);
            } else if (time == this.instance.getTimeStartDestination(i)) {
                ctrl.displayStartDestinationTime(time, i);
            } else if (time == this.instance.getTimeEndDestination(i)) {
                ctrl.displayEndDestinationTime(time, i);
            }
        }
    }

    @Override
    public Car createCar(int numCar, int indexSource, int indexDestination) {
        throw new UnsupportedOperationException("Unimplemented method 'createCar'");
    }

    @Override
    public Car updateCarPosition(int numCar, double percentageCompletion) {
        throw new UnsupportedOperationException("Unimplemented method 'updateCarPosition'");
    }

    @Override
    public void updateCarRoute(int numCar, int indexSource, int indexDestination) {
        throw new UnsupportedOperationException("Unimplemented method 'updateCarRoute'");
    }
}
