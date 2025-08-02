package animationvehicules.view.gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.*;

import animationvehicules.Controller;
import animationvehicules.model.parser.ParserGeoJSON;
import animationvehicules.view.map.GeoJsonMapViewer;

/**
 * Interface graphique pour l'utilisateur.
 * Construction de la fenêtre et affichage des différentes zones.
 */
public class GUI extends JFrame {

    private Container contentPane;
    private GeoJsonMapViewer map;
    private Integer finalTime;
    private JPanel timeManagement, player, controlsPanel, navigationSlider;
    private JLabel imgPause, imgForward, imgBack;
    protected JSlider slider;
    private Controller ctrl;

    public GUI(Controller ctrl, Integer speed, ParserGeoJSON pgj, ParserGeoJSON pgjTram, int finalTime)
            throws InterruptedException {
        super("Le Havre");
        super.setMinimumSize(new Dimension(1400, 1000));
        this.ctrl = ctrl;
        this.contentPane = getContentPane();
        this.contentPane.setLayout(new BorderLayout());

        this.map = new GeoJsonMapViewer(pgj, pgjTram);
        this.initMap();

        this.finalTime = finalTime;
        this.timeManagement = new JPanel();
        this.imgPause = new JLabel();
        this.imgBack = new JLabel();
        this.imgForward = new JLabel();
        this.initTimeManagement(0, this.finalTime);

        super.pack();
        super.setVisible(true);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public GeoJsonMapViewer getMap() {
        return this.map;
    }

    public JLabel getImgPause() {
        return this.imgPause;
    }

    public JLabel getImgForward() {
        return this.imgForward;
    }

    public JLabel getImgBack() {
        return this.imgBack;
    }

    public JSlider getSlider() {
        return this.slider;
    }

    /**
     * Zone pour la map.
     */
    private void initMap() {
        this.contentPane.add(this.map.displayBlankMap(), BorderLayout.CENTER);
    }

    /**
     * Zone où l'utilisateur va pouvoir gérer le temps.
     */
    private void initTimeManagement(int startTime, int finalTime) {
        this.timeManagement.setPreferredSize(new Dimension(1300, 200));
        this.timeManagement.setLayout(new BorderLayout());

        this.player = new JPanel();
        this.player.setLayout(new BorderLayout());

        this.initControlsPanel();
        this.initNavigationSlider(startTime, finalTime);

        this.player.add(this.controlsPanel, BorderLayout.NORTH);
        this.player.add(createJPanelBlankArea(50, 140), BorderLayout.WEST);
        this.player.add(createJPanelBlankArea(50, 140), BorderLayout.EAST);
        this.player.add(this.navigationSlider, BorderLayout.CENTER);

        this.timeManagement.add(this.player, BorderLayout.CENTER);
        this.contentPane.add(this.timeManagement, BorderLayout.SOUTH);
    }

    /**
     * Zone des boutons.
     */
    private void initControlsPanel() {
        this.controlsPanel = new JPanel();
        this.controlsPanel.setLayout(new BorderLayout());
        this.controlsPanel.setPreferredSize(new Dimension(1400, 90));

        JPanel zoneButton = new JPanel();
        zoneButton.setLayout(new FlowLayout());
        zoneButton.setPreferredSize(new Dimension(1400, 60));
        zoneButton.setBackground(Color.white);

        this.imgBack.setIcon(new ImageIcon("resources/Back.png"));
        zoneButton.add(this.imgBack);
        this.imgPause.setIcon(new ImageIcon("resources/Pause.png"));
        zoneButton.add(this.imgPause);
        this.imgForward.setIcon(new ImageIcon("resources/Forward.png"));
        zoneButton.add(this.imgForward);

        this.controlsPanel.add(createJPanelBlankArea(1400, 20), BorderLayout.NORTH);
        this.controlsPanel.add(zoneButton, BorderLayout.CENTER);
    }

    /**
     * Zone du Slider.
     */
    public void initNavigationSlider(int startTime, int finalTime) {
        this.navigationSlider = new JPanel();
        this.navigationSlider.setPreferredSize(new Dimension(1400, 140));
        this.navigationSlider.setBackground(Color.white);
        this.slider = new JSlider(startTime, finalTime, startTime);
        this.slider.setPreferredSize(new Dimension(1250, 100));
        this.slider.setBackground(Color.white);
        this.slider.setPaintTicks(true);
        this.slider.setPaintLabels(true);
        this.slider.setMajorTickSpacing(3600);
        this.slider.setMinorTickSpacing(900);
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        for (int i = startTime; i <= finalTime; i++) {
            if (i % 3600 == 0) {
                JLabel label = new JLabel(getTimeFormatGUI(i));
                labelTable.put(i, label);
            } else {
                labelTable.put(i, new JLabel(""));
            }
        }
        this.slider.setLabelTable(labelTable);
        this.navigationSlider.add(this.slider);
    }

    /**
     * Créé un JPanel vide (utile pour le rendu).
     */
    public JPanel createJPanelBlankArea(int width, int height) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(width, height));
        panel.setBackground(Color.white);
        return panel;
    }

    /**
     * Retourne l'heure en version "Heures:Minutes" (et non en secondes).
     * 
     * @param time : temps à afficher.
     */
    public String getTimeFormatGUI(int time) {
        int startHour = 8;
        time += startHour * 3600;
        int hours = time / 3600;
        int minutes = (time % 3600) / 60;
        String result = String.format("%02d:%02d", hours, minutes);
        return result;
    }

    public void displayStartOriginTime(String animation, int time, int i) {
        String newTime = getTimeFormatGUI(time);
        System.out.println("Début temps d'Origine - Requête n°" + i + ". (" + newTime + " / " + time + ")");
        int indexSource = this.ctrl.getGeoJSONParser().getIndexs()
                .indexOf(this.ctrl.getInstanceParser().getIndexSource(i));
        int indexDestination = this.ctrl.getGeoJSONParser().getIndexs()
                .indexOf(this.ctrl.getInstanceParser().getIndexDestination(i));
        if(animation == "InstanceAndSolution"){
            this.map.createRoad(animation, 0, i, indexSource, indexDestination, Color.GRAY);
        } else {
            this.map.createRoad(animation, 0, i, indexSource, indexDestination, Color.BLUE);
        }
        this.map.displayRoad();
    }

    public void displayEndOriginTime(int time, int i) {
        String newTime = getTimeFormatGUI(time);
        System.out.println("Fin temps d'Origine - Requête n°" + i + ". (" + newTime + " / " + time + ")");
    }

    public void displayStartDestinationTime(int time, int i) {
        String newTime = getTimeFormatGUI(time);
        System.out.println("Début temps Destination - Requête n°" + i + ". (" + newTime + " / " + time + ")");

    }

    public void displayEndDestinationTime(int time, int i) {
        String newTime = getTimeFormatGUI(time);
        System.out.println("Fin temps Destination - Requête n°" + i + ". (" + newTime + " / " + time + ")");
        this.map.removeRoad(0, i);
        this.map.displayRoad();
    }

    public void displayVehicleLeaveDepot(int time, int i, int j, ArrayList<String[]> route) {
        int nbVehicle = i + 1;
        String color = getColorTerminal(i + 1);
        String timeFormat = getTimeFormatGUI(time);
        System.out.println(color + "(" + timeFormat + ") Le véhicule n°" + nbVehicle
                + " part du dépôt. (" + j + ")\u001B[0m");
        this.map.createRoad("TMP", i, j, this.ctrl.getSolutionParser().getIdNodesSolution(route, j),
                this.ctrl.getSolutionParser().getIdNodesSolution(route, j + 1), getColorRoad(nbVehicle));
        this.ctrl.createCar(nbVehicle, this.ctrl.getSolutionParser().getIdNodesSolution(route, 0),
                this.ctrl.getSolutionParser().getIdNodesSolution(route, 1));
        this.map.displayRoad();
    }

    public void displayVehicleBackToDepot(int time, int i, int j) {
        int nbVehicle = i + 1;
        String color = getColorTerminal(i + 1);
        String timeFormat = getTimeFormatGUI(time);
        System.out.println(color + "(" + timeFormat + ") Le véhicule n°" + nbVehicle
                + " est revenu au dépôt. (" + j + ")\u001B[0m");
        this.map.removeRoad(i, j - 1);
        this.map.displayRoad();
    }

    public void displayVehicleArrivedOnPoint(int time, int i, int j, ArrayList<String[]> route) {
        int nbVehicle = i + 1;
        String color = getColorTerminal(i + 1);
        String timeFormat = getTimeFormatGUI(time);
        int point = this.ctrl.getSolutionParser().getIdNodesSolution(route, j);
        System.out.println(color + "(" + timeFormat + ") Le véhicule n°" + nbVehicle
                + " est arrivée au point n°" + point + ". (" + j + ")\u001B[0m");
        this.map.removeRoad(i, j - 1);
        this.map.createRoad("TMP", i, j, this.ctrl.getSolutionParser().getIdNodesSolution(route, j),
                this.ctrl.getSolutionParser().getIdNodesSolution(route, j + 1), getColorRoad(nbVehicle));
        this.ctrl.updateCarRoute(nbVehicle, this.ctrl.getSolutionParser().getIdNodesSolution(route, j),
                this.ctrl.getSolutionParser().getIdNodesSolution(route, j + 1));
        this.map.displayRoad();
    }

    public void displayVehicleLeavePoint(int time, int i, int j, ArrayList<String[]> route) {
        int nbVehicle = i + 1;
        String color = getColorTerminal(i + 1);
        String timeFormat = getTimeFormatGUI(time);
        int point = this.ctrl.getSolutionParser().getIdNodesSolution(route, j);
        System.out.println(color + "(" + timeFormat + ") Le véhicule n°" + nbVehicle
                + " repart du point n°" + point + ". (" + j + ")\u001B[0m");
    }

    /** Retourne le code couleur pour le terminal en fonction de l'id du véhicule */
    public String getColorTerminal(int id) {
        id += 1;
        return "\u001B[3" + id + "m";
    }

    /** Retourne une couleur en fonction de l'id du véhicule */
    public static Color getColorRoad(int id) {
        Color color;
        switch (id) {
            case 1:
                color = Color.GREEN;
                break;
            case 2:
                color = Color.YELLOW;
                break;
            case 3:
                color = Color.BLUE;
                break;
            case 4:
                color = Color.PINK;
                break;
            case 5:
                color = Color.CYAN;
                break;
            case 6:
                color = Color.MAGENTA;
                break;
            case 7:
                color = Color.ORANGE;
                break;
            case 8:
                color = Color.GRAY;
                break;
            case 9:
                color = Color.RED;
                break;
            default:
                color = Color.WHITE;
                break;
        }
        return color;
    }
}
