
package animationvehicules.view.map;

import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 * Classe permettant de définir comment combiner deux images (utile pour le
 * rendu graphique)
 */
public class MultiplyComposite implements Composite {
    public static final MultiplyComposite Default = new MultiplyComposite();

    /*
     * Effectue la multiplication de l'image pixel par pixel
     */
    @Override
    public CompositeContext createContext(ColorModel srcColorModel, ColorModel dstColorModel, RenderingHints hints) {
        return new CompositeContext() {
            @Override
            public void compose(Raster src, Raster dstIn, WritableRaster dstOut) {
                if (src.getSampleModel().getDataType() != DataBuffer.TYPE_INT
                        || dstIn.getSampleModel().getDataType() != DataBuffer.TYPE_INT
                        || dstOut.getSampleModel().getDataType() != DataBuffer.TYPE_INT) {
                    throw new IllegalStateException("Source and destination must store pixels as INT.");
                }

                // Obtient longueur et largeur de l'image a renvoyer
                int width = Math.min(src.getWidth(), dstIn.getWidth());
                int height = Math.min(src.getHeight(), dstIn.getHeight());

                // Tableaux représentant les couleurs d'un pixel au format RGBA
                int[] srcPixel = new int[4];
                int[] dstPixel = new int[4];

                // Tableaux representant les pixels pour l'image source et l'image de
                // destination
                int[] srcPixels = new int[width];
                int[] dstPixels = new int[width];

                // Boucle parcourant chacun des pixels pour les multiplier un par un
                for (int y = 0; y < height; y++) {
                    // Permet de récupérer une ligne de pixel de chaque image et les stocke dans
                    // srcPixels et dstPixels
                    src.getDataElements(0, y, width, 1, srcPixels);
                    dstIn.getDataElements(0, y, width, 1, dstPixels);

                    // Parcourt la ligne de pixel
                    for (int x = 0; x < width; x++) {
                        // Les canaux de chaque pixel (R G B A) sont extrait en décalant les x * 8 bits
                        // de la valeur entière vers la droite
                        // puis en masquant les bits indésirables à l'aide de l'opérateur logique ET
                        // avec 0xFF. Cela donne les valeurs des canaux.
                        int pixel = srcPixels[x];
                        srcPixel[0] = (pixel >> 16) & 0xFF; // R
                        srcPixel[1] = (pixel >> 8) & 0xFF; // G
                        srcPixel[2] = (pixel >> 0) & 0xFF; // B
                        srcPixel[3] = (pixel >> 24) & 0xFF; // A

                        pixel = dstPixels[x];
                        dstPixel[0] = (pixel >> 16) & 0xFF; // R
                        dstPixel[1] = (pixel >> 8) & 0xFF; // G
                        dstPixel[2] = (pixel >> 0) & 0xFF; // B
                        dstPixel[3] = (pixel >> 24) & 0xFF; // A

                        // Multiplie chaque canaux de chaque pixel de la ligne, puis on décale de 8 bits
                        // à droite (= division par 256)
                        // le résultat pour normaliser la valeur entre 0 et 255.
                        int[] result = new int[] {
                                (srcPixel[0] * dstPixel[0]) >> 8,
                                (srcPixel[1] * dstPixel[1]) >> 8,
                                (srcPixel[2] * dstPixel[2]) >> 8,
                                (srcPixel[3] * dstPixel[3]) >> 8
                        };

                        // On les replace à la bonne place dans le RGBA
                        dstPixels[x] = (result[3]) << 24 // A
                                | (result[0]) << 16 // R
                                | (result[1]) << 8 // G
                                | (result[2]); // B
                    }
                    // On définit les pixel de l'image qu'on vient de calculer.
                    dstOut.setDataElements(0, y, width, 1, dstPixels);
                }
            }

            @Override
            public void dispose() {
            }
        };
    }
}