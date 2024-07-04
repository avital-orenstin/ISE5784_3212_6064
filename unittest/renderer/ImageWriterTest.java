package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

/**
 * This class contains a JUnit test for the ImageWriter class.
 * The test verifies the functionality of writing pixels to an image file.
 */
class ImageWriterTest {
    /**
     * Test method to verify writing pixels to an image file.
     * This test creates a yellow image with a red grid of lines spaced pixels apart.
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