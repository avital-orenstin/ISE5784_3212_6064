package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class Geometries implements Intersectable{
    List<Intersectable> geometries=new LinkedList<Intersectable>();

    public Geometries() {
    }

    public Geometries(Intersectable ... geometries) {
        add(geometries);

    }
    public  void add(Intersectable ... geometries)
    {

        Collections.addAll(this.geometries,geometries);
    }
    @Override
    public List<Point> findIntersections(Ray ray) {

        LinkedList<Point> intersections = new LinkedList<Point>();
        // temp - for save the points in the i geometry
        List<Point> temp;
        for (Intersectable i : geometries) {
            temp = i.findIntersections(ray);
            if (temp != null) {
                // if there are intersection points with i geometry - copy all these points to intersections list
                intersections.addAll(temp);
            }
        }
        if (intersections.isEmpty())
            return null;
        return intersections;

    }
}