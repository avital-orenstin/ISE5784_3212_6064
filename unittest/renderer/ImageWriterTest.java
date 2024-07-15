package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Point;
import primitives.Ray;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for the ImageWriter class.
 */
class ImageWriterTest {

    /**
     * Test method for writing an image to a file.
     * This method creates an image with yellow background and red grid lines.
     */
    @Test
    void testWriteToImage() {
        ImageWriter imageWriter = new ImageWriter("yellow", 800, 500);

        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                imageWriter.writePixel(i, j, new Color(java.awt.Color.yellow));

                if (i % 50 == 0 || j % 50 == 0)
                    imageWriter.writePixel(i, j, new Color(java.awt.Color.red));
            }
        }
    }
}

