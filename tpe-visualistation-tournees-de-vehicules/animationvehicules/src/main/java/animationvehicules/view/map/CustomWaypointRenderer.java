package animationvehicules.view.map;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.WaypointRenderer;

/**
 * Le painter adapté pour le CustomWaypoint
 */
public class CustomWaypointRenderer implements WaypointRenderer<CustomWaypoint> {
    private final Map<Color, BufferedImage> map = new HashMap<Color, BufferedImage>();

    private BufferedImage origImage;

    /*
     * Constructeur - charge l'image du waypoint blanc
     */
    public CustomWaypointRenderer(String type) {
        URL resource;
        if (type == "car") {
            resource = getClass().getResource("img/car.png");
        } else if (type == "origine") {
            resource = getClass().getResource("img/origine.png");
        } else if (type == "source") {
            resource = getClass().getResource("img/source.png");
        }else if (type == "petitOrigine") {
            resource = getClass().getResource("img/petitOrigine.png");
        }else if (type == "petitSource") {
            resource = getClass().getResource("img/petitSource.png");
        } else {
            resource = getClass().getResource("img/waypoint_white.png");
        }
        try {
            origImage = ImageIO.read(resource);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     * Converti l'image de base avec la couleur en la multipliant
     * 
     * @param loadImg  L'image à convertir
     * @param newColor La couleur à appliquer
     * @return L'image convertie
     */
    private BufferedImage convert(BufferedImage loadImg, Color newColor) {
        int w = loadImg.getWidth();
        int h = loadImg.getHeight();
        BufferedImage imgOut = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        BufferedImage imgColor = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = imgColor.createGraphics();
        g.setColor(newColor);
        g.fillRect(0, 0, w + 1, h + 1);
        g.dispose();

        Graphics2D graphics = imgOut.createGraphics();
        graphics.drawImage(loadImg, 0, 0, null);
        graphics.setComposite(MultiplyComposite.Default);
        graphics.drawImage(imgColor, 0, 0, null);
        graphics.dispose();

        return imgOut;
    }

    /**
     * Réimplémente la méthode par défaut pour paint notre propre CustomWaypoint
     */
    @Override
    public void paintWaypoint(Graphics2D g, JXMapViewer viewer, CustomWaypoint w) {
        g = (Graphics2D) g.create();

        if (origImage == null)
            return;

        BufferedImage myImg = map.get(w.getColor());

        if (myImg == null) {
            myImg = convert(origImage, w.getColor());
            map.put(w.getColor(), myImg);
        }

        Point2D point = viewer.getTileFactory().geoToPixel(w.getPosition(), viewer.getZoom());

        int x = (int) point.getX();
        int y = (int) point.getY();

        g.drawImage(myImg, x - myImg.getWidth() / 2, y - myImg.getHeight(), null);

        String label = w.getLabel();

        FontMetrics metrics = g.getFontMetrics();
        int tw = metrics.stringWidth(label);
        int th = 1 + metrics.getAscent();

        g.drawString(label, x - tw / 2, y + th - myImg.getHeight());

        g.dispose();
    }
}