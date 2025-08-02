package animationvehicules.model.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Parser {

    protected String path;
    protected ArrayList<String[]> lines;

    /**
     * Ouvre un fichier et enregistre chaque données.
     * 
     * @param path : chemin du fichier à analyser.
     */
    public Parser(String path) {
        this.path = path;
        this.lines = new ArrayList<>();
    }

    /**
     * Permet de changer de fichiers.
     * 
     * @param newPath : le nouveau chemin du fichier à analyser.
     */
    public void init(String newPath) {
        this.path = newPath;
        this.openingFile();
    }

    /**
     * Ouvre un fichier et enregistre chaque données dans une liste.
     * La liste contient toutes les lignes du fichier.
     * Chaque lignes est représentées par une liste de String qui sont les valeurs.
     */
    public void openingFile() {
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader reader = new BufferedReader(fileReader);
            String line = reader.readLine();
            while (line != null) {
                String[] str = line.split(" ");
                this.lines.add(str);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Affiche toutes les lignes du fichier.
     */
    @Override
    public String toString() {
        String chaine = "";
        for (String[] str : this.lines) {
            for (String subStr : str) {
                chaine += subStr + " ";
            }
            chaine += "\n";
        }
        return chaine;
    }
}