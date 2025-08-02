package animationvehicules.view.map;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;

import animationvehicules.model.animation.Car;
import animationvehicules.model.parser.ParserGeoJSON;

public class GeoJsonMapViewer {
    private ParserGeoJSON pgj, pgjTram;
    private JXMapViewer mapViewer;
    private CompoundPainter<JXMapViewer> painter;
    private Map<Integer, Map<Integer, List<Painter<JXMapViewer>>>> roadMap;
    private List<Painter<JXMapViewer>> tramPainters;
    private Set<CustomWaypoint> carsWaypoints;

    public GeoJsonMapViewer(ParserGeoJSON pgj, ParserGeoJSON pgjTram) {
        this.pgj = pgj;
        this.pgjTram = pgjTram;
        this.mapViewer = new JXMapViewer();
        this.painter = new CompoundPainter<>();
        this.roadMap = new HashMap<>();
        this.carsWaypoints = new HashSet<>();
        this.tramPainters = new ArrayList<>();
        addTramPainter("stations_localisations_bereult.geojson");
        addTramPainter("stations_localisations_fleuri.geojson");
        addTramPainter("stations_localisations_hameau.geojson");
        addTramPainter("stations_localisations_jardin.geojson");
        addTramPainter("stations_localisations_plage.geojson");
    }

    /**
     * Retourne une carte avec des tracés de routes et des marqueurs de waypoints.
     */
    public JXMapViewer displayBlankMap() {
        // Create a TileFactoryInfo for OpenStreetMap
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        this.mapViewer.setTileFactory(tileFactory);

        GeoPosition leHavre = new GeoPosition(49.4938, 0.1077);

        // Set the focus
        this.mapViewer.setZoom(7);
        this.mapViewer.setAddressLocation(leHavre);

        // Add tram painters to the compound painter
        for (Painter<JXMapViewer> tramPainter : tramPainters) {
            this.painter.addPainter(tramPainter);
        }

        this.displayRoad(); // Display roads after adding tram painters
        return this.mapViewer;
    }

    /**
     * Ajoute un tracé de ligne de tram à partir d'un fichier GeoJSON.
     * 
     * @param path Le chemin vers le fichier GeoJSON du tram.
     */
    public void addTramPainter(String path) {
        ParserGeoJSON pgjTram = new ParserGeoJSON(path);
        pgjTram.parse();
        List<GeoPosition> tramTrack = new ArrayList<>();
        for (int i = 0; i < pgjTram.getLatitude().size(); i++) {
            GeoPosition pos = new GeoPosition(pgjTram.getLatitude().get(i), pgjTram.getLongitude().get(i));
            tramTrack.add(pos);
        }
        RoutePainter tramPainter = new RoutePainter(tramTrack);
        tramPainters.add(tramPainter);
    }

    /**
     * Affiche les routes sur la carte.
     */
    public void displayRoad() {
        this.mapViewer.setOverlayPainter(this.painter);
    }

    /**
     * Creer une voiture graphiquement
     * 
     * @param car la voiture à creer graphiquement
     */
    public void createCar(Car car) {
        /*
         * BUG de couleurs - pour l'instant toutes les voitures sonnt blanches, mais
         * elles seront colorées par la même couleur du trajet.
         */
        CustomWaypoint carWaypoint = new CustomWaypoint("", /* AnimationSolution.getColorRoad(numCar) */Color.WHITE,
                car.getPos());

        this.carsWaypoints.add(carWaypoint);

        WaypointPainter<CustomWaypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(carsWaypoints);
        waypointPainter.setRenderer(new CustomWaypointRenderer("car"));

        this.painter.addPainter(waypointPainter);
    }

    /**
     * Met à jour la position d'un vehicule graphiquement
     * 
     * @param car le vehicule à mettre à jour graphiquement
     */
    public void updateCarPosition(Car car) {
        int i = 1;
        for (CustomWaypoint waypoint : carsWaypoints) {
            if (i == car.getId()) {
                waypoint.setPosition(car.getPos());
                waypoint.setPosition(car.getPos());
            }
            i++;
        }
        this.mapViewer.repaint();
    }

    /**
     * Crée les points et la route à tracer.
     * 
     * @param vehicleId        Identifiant du véhicule.
     * @param requestId        Identifiant de la requête.
     * @param indexSource      Index de la source.
     * @param indexDestination Index de la destination.
     * @param color            Couleur de la route.
     */
    public void createRoad(String animation, int vehicleId, int requestId, int indexSource, int indexDestination, Color color) {
        List<GeoPosition> track = new ArrayList<>();
        Set<CustomWaypoint> waypointOrigine = new HashSet<>();
        Set<CustomWaypoint> waypointSource = new HashSet<>();

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
        track.add(positionSource);

        if(animation == "InstanceAndSolution"){
            waypointOrigine.add(new CustomWaypoint("", Color.WHITE, positionSource));
        } else {
            waypointOrigine.add(new CustomWaypoint("", Color.GREEN, positionSource));
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
        track.add(positionDestination);
        if(animation == "InstanceAndSolution"){
            waypointSource.add(new CustomWaypoint("", Color.WHITE, positionDestination));
        } else {
            waypointSource.add(new CustomWaypoint("", Color.RED, positionDestination));
        }

        // Crée la route entre les 2 points
        RoutePainter routePainter;
        if(animation == "InstanceAndSolution"){
            routePainter = new RoutePainter(track, color, 2);
        } else {
            routePainter = new RoutePainter(track, color);
        }

        // Create a waypoint painter that takes the waypoint "origine"
        WaypointPainter<CustomWaypoint> waypointPainterOrigine = new WaypointPainter<>();
        waypointPainterOrigine.setWaypoints(waypointOrigine);
        if(animation == "InstanceAndSolution"){
            waypointPainterOrigine.setRenderer(new CustomWaypointRenderer("petitOrigine"));
        } else {
            waypointPainterOrigine.setRenderer(new CustomWaypointRenderer("origine"));
        }

        // Create a waypoint painter that takes the waypoint "source"
        WaypointPainter<CustomWaypoint> waypointPainterSource = new WaypointPainter<>();
        waypointPainterSource.setWaypoints(waypointSource);
        if(animation == "InstanceAndSolution"){
            waypointPainterSource.setRenderer(new CustomWaypointRenderer("petitSource"));
        } else {
            waypointPainterSource.setRenderer(new CustomWaypointRenderer("source"));
        }

        // Create a compound painter that uses both the route-painter and the
        // waypoint-painter
        List<Painter<JXMapViewer>> listPainters = new ArrayList<>();
        listPainters.add(routePainter);
        listPainters.add(waypointPainterOrigine);
        listPainters.add(waypointPainterSource);

        // Add the new painters to the compound painter
        for (Painter<JXMapViewer> painter : listPainters) {
            this.painter.addPainter(painter);
        }

        Map<Integer, List<Painter<JXMapViewer>>> requestMap = roadMap.computeIfAbsent(vehicleId, k -> new HashMap<>());
        List<Painter<JXMapViewer>> paintersList = requestMap.computeIfAbsent(requestId, k -> new ArrayList<>());
        paintersList.addAll(listPainters);
    }

    /**
     * Supprime le tracé, les points et la route, d'une requête.
     * 
     * @param vehicleId Identifiant du véhicule.
     * @param requestId Identifiant de la requête.
     */
    public void removeRoad(int vehicleId, int requestId) {
        Map<Integer, List<Painter<JXMapViewer>>> requestMap = roadMap.get(vehicleId);
        if (requestMap != null) {
            List<Painter<JXMapViewer>> paintersToRemove = requestMap.get(requestId);
            if (paintersToRemove != null) {
                for (Painter<JXMapViewer> painter : paintersToRemove) {
                    this.painter.removePainter(painter);
                }
                requestMap.remove(requestId);
            }
            if (requestMap.isEmpty()) {
                roadMap.remove(vehicleId);
            }
        }
    }
}
