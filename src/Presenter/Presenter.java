package Presenter;

import Model.Protein;
import View.AtomView;
import View.BasedVisualization;
import View.BasicView;
import View.BondView;
import javafx.animation.ScaleTransition;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;

/**
 * Created by user on 09.01.2017.
 */
public class Presenter {
    private File pdbFile;
    private Stage stage;
    private Protein protein;
    private double downX;
    private double downY;
    BasedVisualization basicVisual = new BasedVisualization();
    private BasicView view;
    private Point2D dragPoint;
    private Point3D pivotPoint;

    private PerspectiveCamera camera = new PerspectiveCamera();


    public Presenter(BasicView view){
        this.view = view;


        view.fileMenu.getItems().get(0).setOnAction((event) -> {
            FileChooser fchooser = new FileChooser();
            fchooser.setTitle("Select PDF files");
            fchooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("pdb Files", "*.pdb"));
            File file = fchooser.showOpenDialog(stage);
            pdbFile = file;
            if (file != null) {
                protein = new Protein((file));
                view.setProtein(protein);
                view.print12Structure();
                showBasicVisualization(view);
                view.basicChecker.setSelected(true);
                view.bondSlideChecker.setDisable(false);
                view.atomSlideChecker.setDisable(false);
                camera = view.camera;
            } else {
                System.out.println("File selection cancelled.");
            }

        });


        view.basicChecker.setOnAction((event) ->{
            view.cartoonChecker.setSelected(false);
            view.ribbonChecker.setSelected(false);
        });
        view.ribbonChecker.setOnAction((event) ->{
            view.cartoonChecker.setSelected(false);
            view.basicChecker.setSelected(false);
        });
        view.cartoonChecker.setOnAction((event) ->{
            view.ribbonChecker.setSelected(false);
            view.basicChecker.setSelected(false);
        });

        //scale the Atom and the Bond ratio either if the slider gets clicked or dregged to a position
        view.atomSlider.setOnMouseClicked((event) ->{
            scaleAtoms();
        });
        view.atomSlider.setOnMouseDragged((event) ->{
            scaleAtoms();
        });
        view.bondSlider.setOnMouseClicked((event)->{
            scaleBonds();
        });
        view.bondSlider.setOnMouseDragged((event)->{
            scaleBonds();
        });

        view.atomSlideChecker.setOnAction((event) ->{
            if(view.atomSlideChecker.isSelected()){
                view.atomSlider.setDisable(false);

                }
            if(!view.atomSlideChecker.isSelected()){
                view.atomSlider.setDisable(true);
            }});
        view.bondSlideChecker.setOnAction((event) ->{
            if(view.bondSlideChecker.isSelected()){
                view.bondSlider.setDisable(false);}
            if(!view.bondSlideChecker.isSelected()){
                view.bondSlider.setDisable(true);
            }});

    }
    private void scaleAtoms(){
        for(AtomView atomview: basicVisual.getId_atomViewHash().values()){
            ScaleTransition st = new ScaleTransition(Duration.millis(10), atomview);
            st.setToX(view.atomSlider.getValue());
            st.setToY(view.atomSlider.getValue());
            st.setToZ(view.atomSlider.getValue());
            st.play();
        }
    }
    private void scaleBonds(){

        for(BondView bondview: basicVisual.getId_bondViewHash().values()){
            ScaleTransition st = new ScaleTransition(Duration.millis(10), bondview);
            st.setToX(view.bondSlider.getValue());
            st.setToY(view.bondSlider.getValue());
            st.setToZ(view.bondSlider.getValue());
            st.play();

        }

    }

        public void showBasicVisualization(BasicView view){
        basicVisual = new BasedVisualization(protein, this, view.atomSlider.getValue(), view.bondSlider.getValue());

        SubScene subscene = new SubScene(basicVisual, Toolkit.getDefaultToolkit().getScreenSize().getWidth() *0.5, Toolkit.getDefaultToolkit().getScreenSize().getHeight()*0.5, false, SceneAntialiasing.BALANCED );
        subscene.widthProperty().bind(view.graphPane.widthProperty());
        subscene.heightProperty().bind(view.graphPane.heightProperty());

        view.giveSubscene(subscene);
            setUpRotation(subscene, view.graphPane, basicVisual);

    }

    public void setUpRotation(SubScene scene, Pane pane, BasedVisualization basicV){
        scene.setOnMousePressed((me) -> {
            downX = me.getSceneX();
            downY = me.getSceneY();
        });

        scene.setOnMouseClicked((me)->{
            dragPoint = new Point2D(me.getSceneX(), me.getSceneY());
            //pivotPoint = new Point3D(camera.getTranslateX(), camera.getTranslateY(), camera.getTranslateZ());

        });

        scene.setOnMouseDragged((me) -> {
            double deltaX = me.getSceneX() - downX;
            double deltaY = me.getSceneY() - downY;

            /** AB HIER LÖSUNG AUS TUTORIUM !10.1.2017

            Rotate r = new Rotate();
            r.setPivotX(camera.getTranslateX());
            r.setPivotY(camera.getTranslateY());
            r.setPivotZ(camera.getTranslateZ());

            Point3D axis = new Point3D(-deltaY, deltaX, 0);

            double delta = new Point2D(deltaX,deltaY).magnitude();
            r.setAngle(delta);
            if (!basicV.getTransforms().isEmpty())
                basicV.getTransforms().setAll(r.createConcatenation(basicV.getTransforms().get(0)));
            else
                basicV.getTransforms().setAll(r);


            dragPoint= new Point2D(me.getSceneX(), me.getSceneY());
             **/



            Point3D dragDirection = new Point3D(deltaX, deltaY, 0);
            Point3D rotAxis = dragDirection.crossProduct(new Point3D(0, 0, 1));
             /** Ab Hier Lösung aus Remarks
            Rotate r = new Rotate(dragDirection.magnitude(), pane.getWidth()/2, pane.getHeight()/2, 0, rotAxis);
            if (!basicV.getTransforms().isEmpty())
                 basicV.getTransforms().setAll(r.createConcatenation(basicV.getTransforms().get(0)));
            else
                basicV.getTransforms().setAll(r);
             **/

            // funktioniert immernioch am besten..
            basicV.getTransforms().add(new Rotate(0.1* dragDirection.magnitude(), deltaX, deltaY, 0, rotAxis));


            downX = me.getSceneX();
            downY = me.getSceneY();
            me.consume();
        });

    }


    public File getPdbFile(){
        return pdbFile;
    }

    public void setCamera(PerspectiveCamera camera) {
        this.camera = camera;
    }


}
