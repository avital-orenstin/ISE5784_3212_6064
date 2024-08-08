package renderer;

import primitives.Point;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.isZero;
import static primitives.Util.random;
/**
 * Represents a 2D board divided into sub-pixels, allowing for grid and jittered sampling techniques.
 */
public class paln_board {
    //The width of the board.
    double width;
    //The height of the board.
    double height;
    //The number of sub-pixels in the x-direction.
    int x;
   // The number of sub-pixels in the x-direction.
    int y;
    //The center point of the board.
    Point center_board;
    /**
     * Constructs a paln_board with specified dimensions and subdivisions.
     *
     * @param width  The width of the board.
     * @param height The height of the board.
     * @param x      The number of sub-pixels in the x-direction.
     * @param y     The number of sub-pixels in the x-direction.
     */
    public paln_board(double width, double height, int x, int y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the center point of the board.
     *
     * @param center_board The center point of the board.
     */
    public void setCenter_board(Point center_board) {
        this.center_board = center_board;
    }
    /**
     * Returns a list of sub-pixel points using jittered sampling.
     *
     * @param v_Right The right direction vector.
     * @param v_Up    The up direction vector.
     * @return A list of jittered sub-pixel points.
     */
    public List<Point> Jittered(Vector v_Right, Vector v_Up){
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < x; i++){
            for (int j = 0; j < y; j++){
                points.add(random_point(v_Right, v_Up, j, i));

            }
        }
        return points;
    }
    /**
     * Returns a list of sub-pixel points arranged in a grid.
     *
     * @param vectorX The right direction vector.
     * @param vectorY The up direction vector.
     * @return A list of grid sub-pixel points.
     */
    public List<Point> Grid(Vector vectorX, Vector vectorY){
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < this.y; i++){
            for (int j = 0; j < this.x; j++){
                points.add(centerPoint(vectorX, vectorY, j, i));
            }
        }
        return points;
    }

    /**
     * Calculates the center point of a specific sub-pixel in the grid.
     *
     * @param v_Right The right direction vector.
     * @param v_Up    The up direction vector.
     * @param j       The x-index of the sub-pixel.
     * @param i       The y-index of the sub-pixel.
     * @return The center point of the specified sub-pixel.
     */
    private Point centerPoint(Vector v_Right, Vector v_Up, int j, int i){
        Point center = this.center_board;

        double pixelHeightRatio = height / y;
        double pixelWidthRatio = width / x;

        double pixelY = -(i - (y - 1) / 2d) * pixelHeightRatio;
        // Calculate the x-coordinate of the pixel
        double pixelX = (j - (x - 1) / 2d) * pixelWidthRatio;

        // Adjust the pixel point along the x-axis if necessary
        if (!isZero(pixelX)) {
            center = center.add(v_Right.scale(pixelX));
        }
        // Adjust the pixel point along the y-axis if necessary
        if (!isZero(pixelY)) {
            center = center.add(v_Up.scale(pixelY));
        }
        return center;
    }
    /**
     * Calculates a random point within a specific sub-pixel using jittered sampling.
     *
     * @param v_Right The right direction vector.
     * @param v_Up    The up direction vector.
     * @param j       The x-index of the sub-pixel.
     * @param i       The y-index of the sub-pixel.
     * @return The random jittered point within the specified sub-pixel.
     */
    private Point random_point(Vector v_Right, Vector v_Up, int j, int i){
        Point center = centerPoint(v_Right, v_Up, j, i);
        double randomX = random(-((width - 1) / x) / 2, ((width - 1) / x) / 2);
        double randomY = random(-((height - 1) / y) / 2, ((height - 1) / y) / 2);
        if (!isZero(randomX)){
            center = center.add(v_Right.scale(randomX));
        }
        if (!isZero(randomY)){
            center = center.add(v_Up.scale(randomY));
        }
        return center;
    }
}