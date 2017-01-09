package Presenter;

import Model.Protein;
import View.AtomView;
import View.BasedVisualization;
import View.BasicView;
import javafx.animation.ScaleTransition;
import javafx.geometry.Point3D;
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
        view.atomSlider.setOnMouseClicked((event) ->{
            scaleAtoms();
        });
        view.atomSlider.setOnMouseDragged((event) ->{
            scaleAtoms();
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

        public void showBasicVisualization(BasicView view){
        basicVisual = new BasedVisualization(protein, this, view.atomSlider.getValue(), 1);

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

        scene.setOnMouseDragged((me) -> {
            double deltaX = me.getSceneX() - downX;
            double deltaY = me.getSceneY() - downY;

            Point3D dragDirection = new Point3D(deltaX, deltaY, 0);
            Point3D rotAxis = dragDirection.crossProduct(new Point3D(0, 0, 1));

            basicV.getTransforms().add(new Rotate(0.1* dragDirection.magnitude(), deltaX, deltaY, 0, rotAxis));

            downX = me.getSceneX();
            downY = me.getSceneY();
            me.consume();
        });

    }


    public File getPdbFile(){
        return pdbFile;
    }

}
