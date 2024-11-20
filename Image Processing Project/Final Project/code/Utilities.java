import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Utility class for loading and exporting images.
 */
public final class Utilities
{
    /**
     * Loads a grayscale image from a PGM file.
     *
     * @param filename the path to the PGM file
     * @return a 2D array representing the loaded image
     */
    public static Short[][] loadData(String filename)
    {
        try {
            Scanner scanner = new Scanner(new File(filename));
            // Skip the first three lines
            scanner.nextLine(); // P2
            String[] dimensions = scanner.nextLine().split(" ");
            int width = Integer.parseInt(dimensions[0]);
            int height = Integer.parseInt(dimensions[1]);
            scanner.nextLine(); // Max pixel value

            Short[][] image = new Short[height][width];
            for (int h = 0; h < height; h++) {
                for (int w = 0; w < width; w++) {
                    image[h][w] = Short.parseShort(scanner.next());
                }
            }
            scanner.close();
            return image;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Exports a QuadTreeImage to a PGM file.
     *
     * @param image    the QuadTreeImage to export
     * @param filename the path to the output file
     * @param <Pixel>  the type of elements in the QuadTreeImage
     */
    public static <Pixel extends Number> void exportImage(QuadTreeImage<Pixel> image, String filename)
    {
        try {
            PrintWriter writer = new PrintWriter(new File(filename));
            writer.println("P2");
            writer.println(image.getSize() + " " + image.getSize());
            writer.println("255");

            for (TreeNode<Pixel> node : image) {
                writer.print(node.value + " ");
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
