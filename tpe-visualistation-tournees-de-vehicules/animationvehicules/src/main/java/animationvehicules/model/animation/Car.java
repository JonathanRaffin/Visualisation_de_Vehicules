package animationvehicules.model.animation;

import org.jxmapviewer.viewer.GeoPosition;

public class Car {
    private int id;
    private GeoPosition pos;
    private GeoPosition currentRequestStart;
    private GeoPosition currentRequestEnd;
    private int currentNodeId = 0;

    /**
     * Créé une voiture.
     * 
     * @param id                  : son id.
     * @param pos                 : sa position.
     * @param currentRequestStart : la position de l'origine de la requête.
     * @param currentRequestEnd   : la position de la source de la requête.
     */
    public Car(int id, GeoPosition pos, GeoPosition currentRequestStart, GeoPosition currentRequestEnd) {
        this.id = id;
        this.pos = pos;
        this.currentRequestStart = currentRequestStart;
        this.currentRequestEnd = currentRequestEnd;
    }

    /**
     * Met à jour la requête de la voiture.
     * 
     * @param requestStartPos : position de l'origine de la requête.
     * @param requestEndPos   : position de la source de la requête.
     */
    public void setRequest(GeoPosition requestStartPos, GeoPosition requestEndPos) {
        this.currentRequestStart = requestStartPos;
        this.currentRequestEnd = requestEndPos;
        currentNodeId++;
    }

    /**
     * Met à jour la position de la voiture.
     * 
     * @param percentageCompletion : pourcentage du trajet effectué.
     */
    public void updatePos(double percentageCompletion) {
        double latDiff = currentRequestEnd.getLatitude() - currentRequestStart.getLatitude();
        double longDiff = currentRequestEnd.getLongitude() - currentRequestStart.getLongitude();

        double latIncrement = latDiff * percentageCompletion;
        double longIncrement = longDiff * percentageCompletion;

        double intermediateLat = currentRequestStart.getLatitude() + latIncrement;
        double intermediateLong = currentRequestStart.getLongitude() + longIncrement;

        pos = new GeoPosition(intermediateLat, intermediateLong);
    }

    /**
     * @return l'id de la voiture.
     */
    public int getId() {
        return id;
    }

    /**
     * @return la position de la voiture.
     */
    public GeoPosition getPos() {
        return pos;
    }

    /**
     * @return la position de l'origine de la requête.
     */
    public GeoPosition getCurrentRequestStart() {
        return currentRequestStart;
    }

    /**
     * @return la position de la source de la requête.
     */
    public GeoPosition getCurrentRequestEnd() {
        return currentRequestEnd;
    }

    /**
     * @return l'id du noued ou se trouve la voiture.
     */
    public int getCurrentNodeId() {
        return this.currentNodeId;
    }
}
