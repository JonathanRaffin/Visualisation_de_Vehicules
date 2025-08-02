package animationvehicules.view.map;

import java.awt.Color;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

/**
 * Un point avec une couleur et du texte
 */
public class CustomWaypoint extends DefaultWaypoint {
    private final String label;
    private final Color color;

    /**
     * @param label le texte
     * @param color la couleur
     * @param coord les coordinées
     */
    public CustomWaypoint(String label, Color color, GeoPosition coord) {
        super(coord);
        this.label = label;
        this.color = color;
    }

    /**
     * @return le label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return la couleur
     */
    public Color getColor() {
        return color;
    }

}