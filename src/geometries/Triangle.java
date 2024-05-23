package geometries;

import primitives.Point;

import java.util.List;

/**
 * The Triangle class represents a triangle in three-dimensional space.
 * A triangle is a polygon with three vertices.
 *@author Isca Fitousi and Avital Orenstin
 */
public class Triangle extends Polygon {

    /**
     * Constructs a triangle with the specified vertices.
     *
     * @param vertex1 The first vertex of the triangle.
     * @param vertex2 The second vertex of the triangle.
     * @param vertex3 The third vertex of the triangle.
     */
    public Triangle(Point vertex1, Point vertex2, Point vertex3) {
        vertices.addLast(vertex1);
        vertices.addLast(vertex2);
        vertices.addLast(vertex3);
    }
}