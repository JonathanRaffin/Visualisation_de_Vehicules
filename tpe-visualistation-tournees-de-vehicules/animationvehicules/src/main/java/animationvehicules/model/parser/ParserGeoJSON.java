package animationvehicules.model.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ParserGeoJSON {

    private File geoJsonFile;

    /*
     * List dans lesquelles seront stockés les informations.
     */
    private ArrayList<Integer> indexs;
    private ArrayList<Integer> indicesRequete;
    private ArrayList<Boolean> estOrigine;
    private ArrayList<Double> longitude;
    private ArrayList<Double> latitude;

    /*
     * Constructeur avec le fichier selectionné.
     */
    public ParserGeoJSON(String path) {
        geoJsonFile = new File(path);
        indexs = new ArrayList<>();
        indicesRequete = new ArrayList<>();
        estOrigine = new ArrayList<>();
        longitude = new ArrayList<>();
        latitude = new ArrayList<>();
    }

    /*
     * Parse le fichier.
     */
    public void parse() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(geoJsonFile);

            ArrayNode features = (ArrayNode) root.get("features");

            // Ajout des infos dans les listes pour toutes les features du fichier
            for (JsonNode feature : features) {
                JsonNode properties = feature.get("properties");
                indexs.add(properties.get("index").asInt());
                if (properties.get("indice_requete") != null)
                    indicesRequete.add(properties.get("indice_requete").asInt());
                if (properties.get("est_origine") != null)
                    estOrigine.add(properties.get("est_origine").asBoolean());

                JsonNode geometry = feature.get("geometry");
                longitude.add(((ArrayNode) geometry.get("coordinates")).get(0).asDouble());
                latitude.add(((ArrayNode) geometry.get("coordinates")).get(1).asDouble());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Retourne la liste des index du fichier.
     */
    public ArrayList<Integer> getIndexs() {
        return indexs;
    }

    /*
     * Retourne la liste de latitudes du fichier.
     */
    public ArrayList<Double> getLatitude() {
        return latitude;
    }

    /*
     * Retourne la liste de longitudes du fichier.
     */
    public ArrayList<Double> getLongitude() {
        return longitude;
    }

    /*
     * Affiche textuellement toutes les données du fichier.
     */
    @Override
    public String toString() {
        String text = "";
        for (int i = 0; i < indexs.size(); i++) {
            text += "--- " + i + " ---\n";
            text += "index: " + indexs.get(i) + "\n";
            text += "indice requete: " + indicesRequete.get(i) + "\n";
            text += "origine?: " + estOrigine.get(i) + "\n";
            text += "longitude: " + longitude.get(i) + "\n";
            text += "latitude: " + latitude.get(i) + "\n";
        }
        return text;
    }
}