package View;

import Model.Atom;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

/**
 * Created by user on 09.01.2017.
 */
public class AtomView extends Group {
    private Sphere sphere;
    private int atomId;
    private PhongMaterial material = new PhongMaterial();
    private double ratioFactor;

    public AtomView(Atom atom, double ratioFactor){
        atomId = atom.getId();
        this.ratioFactor = ratioFactor;
        switch(atom.getAtomName()){
            case "N": visualizeN();
                break;
            case "C": visualizeC();
                break;
            case "CA": visualizeC();
                break;
            case "CB": visualizeC();
                break;
            case "O": visualizeO();
                break;
            default:
                System.out.println("Undefinded Atom: "+ atom.getAtomName());
        }
        sphere.setMaterial(material);
        sphere.setTranslateX(atom.getX());
        sphere.setTranslateY(atom.getY());
        sphere.setTranslateZ(atom.getZ());

        this.getChildren().add(sphere);
    }

    public int getAtomViewId(){
        return atomId;
    }

    private void visualizeN(){
        sphere = new Sphere(0.65 * ratioFactor);
        material.setDiffuseColor(Color.DARKBLUE.darker());
        material.setSpecularColor(Color.DARKBLUE.brighter());

    }
    private void visualizeC(){
        sphere = new Sphere(0.70* ratioFactor);
        material.setDiffuseColor(Color.BLACK.darker());
        material.setSpecularColor(Color.BLACK.brighter());
    }
    private void visualizeO(){
        sphere = new Sphere(0.60* ratioFactor);
        material.setDiffuseColor(Color.RED.darker());
        material.setSpecularColor(Color.RED.brighter());
    }


}
