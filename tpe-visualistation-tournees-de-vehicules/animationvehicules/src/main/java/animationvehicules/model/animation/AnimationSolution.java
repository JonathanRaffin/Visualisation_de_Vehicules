package animationvehicules.model.animation;

import animationvehicules.Controller;
import animationvehicules.model.parser.ParserGeoJSON;
import animationvehicules.model.parser.ParserInstance;
import animationvehicules.model.parser.ParserSolution;
import java.util.ArrayList;
import java.util.List;

import org.jxmapviewer.viewer.GeoPosition;

public class AnimationSolution implements IAnimation {

    protected ParserInstance instance;
    protected ParserSolution solution;
    protected ParserGeoJSON pgj;
    protected ParserGeoJSON pgjTram;
    private List<Car> cars;
    private Controller ctrl;

    /**
     * Initalise la solution à analyser.
     * 
     * @param speed : vitesse pour la gestion du temps.
     */
    public AnimationSolution(Controller ctrl, int speed, ParserInstance pi, ParserSolution ps, ParserGeoJSON pgj,
            ParserGeoJSON pgjTram)
            throws InterruptedException {
        this.ctrl = ctrl;
        this.instance = pi;
        this.solution = ps;
        this.pgj = pgj;
        this.pgjTram = pgjTram;
        this.cars = new ArrayList<>();
    }

    /**
     * Vérifie les conditions.
     * Indique quand un véhicules part ou revient au dépôt.
     * Indique chaque arrêt pour chaque véhicules (arrivée et départ)
     */
    @Override
    public void dataChecking(int time) {
        int nbSolution = this.solution.getNumberRoads();
        int tempService = 0;
        for (int i = 0; i < nbSolution; i++) {
            int nbVehicle = i + 1;
            ArrayList<String[]> route = this.solution.getRoad(i);
            for (int j = 0; j < this.solution.getNumberNodesForRoad(route); j++) {
                if (this.solution.getIdRequestSolution(route, j) != -1) {
                    // on recupere le temps de service
                    tempService = this.instance.getTimeService(this.solution.getIdRequestSolution(route, j) + 1);
                }
                if (time == this.solution.getTimeSolution(route, j)) {
                    if (this.solution.getIdRequestSolution(route, j) == -1) {
                        if (time == 0) {
                            this.ctrl.displayVehicleLeaveDepot(time, i, j, route);
                        } else {
                            this.ctrl.displayVehicleBackToDepot(time, i, j);
                        }
                    }
                    if (this.solution.getIdNodesSolution(route, j) != 0) {
                        ctrl.displayVehicleArrivedOnPoint(time, i, j, route);
                    }
                } else if (time == this.solution.getTimeSolution(route, j) + tempService) {
                    if (this.solution.getIdNodesSolution(route, j) != 0) {
                        this.ctrl.displayVehicleLeavePoint(time, i, j, route);
                    }
                }
                if (j == cars.get(nbVehicle - 1).getCurrentNodeId()) {
                    double percentageCompletion = (((double) time) - ((double) this.solution.getTimeSolution(route, j)))
                            / ((((double) this.solution.getTimeSolution(route, j + 1))
                                    - ((double) this.solution.getTimeSolution(route, j))));
                    this.ctrl.updateCarPosition(nbVehicle, percentageCompletion);
                }
            }
        }
    }

    @Override
    public Car createCar(int numCar, int indexSource, int indexDestination) {
        // Point de la source de la requête
        GeoPosition startPositionCar;
        if (indexSource >= 0 && indexSource < this.pgj.getLatitude().size()) {
            startPositionCar = new GeoPosition(this.pgj.getLatitude().get(indexSource),
                    this.pgj.getLongitude().get(indexSource));
        } else {
            indexSource -= this.pgj.getLatitude().size();
            startPositionCar = new GeoPosition(this.pgjTram.getLatitude().get(indexSource),
                    this.pgjTram.getLongitude().get(indexSource));
        }
        // Point de la destination de la requête
        GeoPosition positionDestination;
        // System.out.println("Test tmp: " + this.pgj.getLatitude().size());
        if (indexDestination >= 0 && indexDestination < this.pgj.getLatitude().size()) {
            positionDestination = new GeoPosition(this.pgj.getLatitude().get(indexDestination),
                    this.pgj.getLongitude().get(indexDestination));
        } else {
            indexDestination -= this.pgj.getLatitude().size();
            positionDestination = new GeoPosition(this.pgjTram.getLatitude().get(indexDestination),
                    this.pgjTram.getLongitude().get(indexDestination));
        }
        Car car = new Car(numCar, startPositionCar, startPositionCar, positionDestination);
        this.cars.add(car);
        return car;
    }

    @Override
    public Car updateCarPosition(int numCar, double percentageCompletion) {
        Car car = null;
        for (Car carX : cars) {
            if (carX.getId() == numCar) {
                car = carX;
            }
        }
        car.updatePos(percentageCompletion);
        return car;
    }

    @Override
    public void updateCarRoute(int numCar, int indexSource, int indexDestination) {
        // Point de la source de la requête
        GeoPosition positionSource;
        if (indexSource >= 0 && indexSource < this.pgj.getLatitude().size()) {
            positionSource = new GeoPosition(this.pgj.getLatitude().get(indexSource),
                    this.pgj.getLongitude().get(indexSource));
        } else {
            indexSource -= this.pgj.getLatitude().size();
            positionSource = new GeoPosition(this.pgjTram.getLatitude().get(indexSource),
                    this.pgjTram.getLongitude().get(indexSource));
        }

        // Point de la destination de la requête
        GeoPosition positionDestination;
        // System.out.println("Test tmp: " + this.pgj.getLatitude().size());
        if (indexDestination >= 0 && indexDestination < this.pgj.getLatitude().size()) {
            positionDestination = new GeoPosition(this.pgj.getLatitude().get(indexDestination),
                    this.pgj.getLongitude().get(indexDestination));
        } else {
            indexDestination -= this.pgj.getLatitude().size();
            positionDestination = new GeoPosition(this.pgjTram.getLatitude().get(indexDestination),
                    this.pgjTram.getLongitude().get(indexDestination));
        }
        Car car = cars.get(numCar - 1);
        car.setRequest(positionSource, positionDestination);
    }
}
