import geopandas as gpd
import matplotlib.pyplot as plt

# Pour afficher le Havre, on choisit d'afficher uniquement dans le cadre donne par les coordonnees suivantes:
min_lat = 49.4579778
max_lat = 49.6
min_lon = 0.0560226
max_lon = 0.2857169
# coastline_LeHavre depend de ce cadre. Mais on peut tout a fait changer de cadre, c'est comme vous voulez

if __name__ == "__main__":
    reseau_tram = gpd.read_file("reseau_tram.geojson")
    stations = gpd.read_file("stations_localisations.geojson")
    data_coastline = gpd.read_file("coastline_LeHavre.geojson")
    
    fig, ax = plt.subplots()
    ax.set_xlim(min_lon, max_lon)
    ax.set_ylim(min_lat, max_lat)
    ax = plt.gca()
    plt.show()  
    
    reseau_tram.plot(color='#980808', ax=ax, zorder=2)
    stations.plot(color='#980808', ax=ax,zorder=3)
    data_coastline.plot(ax=ax, color='k')