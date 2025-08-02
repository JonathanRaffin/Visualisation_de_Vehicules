package animationvehicules.view.gui;

import animationvehicules.Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class Control extends MouseAdapter {

    private Timer timer;
    private GUI gui;
    private Controller ctrl;
    private JLabel imgPause, imgForward, imgBack;
    private JSlider slider;
    private boolean isPaused;
    private int time;

    /**
     * Initialise la boucle temporelle.
     * @param gui : L'interface graphique de la carte.
     * @param animation : Animation d'une instance ou d'une solution. 
     * @param speed : la vitesse de l'animation.
     * @param finalTime : l'horizon de temps.
     */
    public Control(Controller ctrl, GUI gui, int speed, int finalTime) {
        this.ctrl = ctrl;
        this.gui = gui;
        this.time = 0;
        this.isPaused = false;
        this.imgPause = this.gui.getImgPause();
        this.imgForward = this.gui.getImgForward();
        this.imgBack = this.gui.getImgBack();
        this.slider = this.gui.getSlider();
        this.imgPause.addMouseListener(this);
        this.imgForward.addMouseListener(this);
        this.imgBack.addMouseListener(this);
        this.timer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (time <= finalTime) {
                    // Use for loop to be faster
                    for(int i = 0; i < 5; i++){
                        ctrl.dataChecking(time);
                        time += 1;
                        slider.setValue(time);
                    }
                }
            }
        });
    }

    /**
     * Gère les actions des boutons pour manier le temps.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        /* Pause ou Lecture */
        if (e.getSource() == this.imgPause) {
            this.isPaused = !this.isPaused;
            if (this.isPaused) {
                this.imgPause.setIcon(new ImageIcon("resources/Play.png"));
                timer.stop();
            } else {
                this.imgPause.setIcon(new ImageIcon("resources/Pause.png"));
                timer.start();
            }
        }
        /* Avancer dans le temps */
        if (e.getSource() == this.imgForward) {
            this.time += 1000;
            SwingUtilities.invokeLater(() -> this.slider.setValue(this.time));
        }
        /* Reculer dans le temps */
        if (e.getSource() == this.imgBack) {
            this.time -= 1000;
            if (this.time < 0) {
                this.time = 0;
            }
            SwingUtilities.invokeLater(() -> this.slider.setValue(this.time));
        }
    }

    /**
     * Démarre le temps.
     */
    public void startTimer(int breakTime) {
        timer.start();
    }

    /**
     * Met à jour le temps.
     * @param time
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Permet de savoir si le temps est en pause ou non.
     * @return
     */
    public boolean isPaused() {
        return this.isPaused;
    }

    /**
     * Met sur Pause.
     */
    public void pauseTimer() {
        this.isPaused = true;
    }
}