package View;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * Created by user on 09.01.2017.
 */
public class BondView extends Group {
    private Point3D startPoint;
    private Point3D endPoint;
    public Cylinder cylinder;
    private Point3D yAxis = new Point3D(0, 1, 0);
    private Point3D diff;
    private Point3D mid;
    private Point3D axisOfRotation;
    private DoubleProperty startX = new SimpleDoubleProperty();
    private DoubleProperty startY = new SimpleDoubleProperty();
    private DoubleProperty startZ = new SimpleDoubleProperty();
    private DoubleProperty endX = new SimpleDoubleProperty();
    private DoubleProperty endY = new SimpleDoubleProperty();
    private DoubleProperty endZ = new SimpleDoubleProperty();


    public BondView(DoubleProperty startXProperty, DoubleProperty startYProperty, DoubleProperty startZProperty, DoubleProperty endXProperty, DoubleProperty endYProperty, DoubleProperty endZProperty){
        endX.bind(endXProperty);
        endY.bind(endYProperty);
        endZ.bind(endZProperty);
        startX.bind(startXProperty);
        startY.bind(startYProperty);
        startZ.bind(startZProperty);
        cylinder = new Cylinder(0.2,0);
        startPoint = new Point3D(startX.getValue(), startY.getValue(), startZ.getValue());
        endPoint = new Point3D(endX.getValue(), endY.getValue(), endZ.getValue());

        modulateCylinder(startPoint, endPoint);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.GREY.darker());
        material.setSpecularColor(Color.GREY.brighter());
        cylinder.setMaterial(material);
        startXProperty.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                startPoint = new Point3D(startX.getValue(), startY.getValue(), startZ.getValue());
                endPoint = new Point3D(endX.getValue(), endY.getValue(), endZ.getValue());
                modulateCylinder(startPoint, endPoint);
            }

        });
        // recreate the bonds if a atom position changes
        startYProperty.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                startPoint = new Point3D(startX.getValue(), startY.getValue(), startZ.getValue());
                endPoint = new Point3D(endX.getValue(), endY.getValue(), endZ.getValue());
                modulateCylinder(startPoint, endPoint);
            }
        });
        startZProperty.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                startPoint = new Point3D(startX.getValue(), startY.getValue(), startZ.getValue());
                endPoint = new Point3D(endX.getValue(), endY.getValue(), endZ.getValue());
                modulateCylinder(startPoint, endPoint);
            }
        });

        endXProperty.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                startPoint = new Point3D(startX.getValue(), startY.getValue(), startZ.getValue());
                endPoint = new Point3D(endX.getValue(), endY.getValue(), endZ.getValue());
                modulateCylinder(startPoint, endPoint);
            }
        });

        endYProperty.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                startPoint = new Point3D(startX.getValue(), startY.getValue(), startZ.getValue());
                endPoint = new Point3D(endX.getValue(), endY.getValue(), endZ.getValue());
                modulateCylinder(startPoint, endPoint);
            }
        });

        endZProperty.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                startPoint = new Point3D(startX.getValue(), startY.getValue(), startZ.getValue());
                endPoint = new Point3D(endX.getValue(), endY.getValue(), endZ.getValue());
                modulateCylinder(startPoint, endPoint);
            }
        });

        this.getChildren().add(cylinder);
    }
    private void modulateCylinder(Point3D startPoint, Point3D endPoint) {
        diff = startPoint.subtract(endPoint);
        double height = diff.magnitude();

        mid = startPoint.midpoint(endPoint);
        Translate moveToMidPoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

        axisOfRotation = diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

        cylinder.setHeight(height);
        cylinder.getTransforms().addAll(moveToMidPoint, rotateAroundCenter);
    }
}
