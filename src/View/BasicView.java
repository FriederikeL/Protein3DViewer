package View;

import Model.Protein;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;

import java.awt.*;

import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by user on 03.01.2017.
 */
public class BasicView extends BorderPane {
    //TODO okay wenn alles über public statt getter und setter läuft?

    MenuBar menubar = new MenuBar();
    TextArea textArea = new TextArea();
    public BorderPane graphPane = new BorderPane();
    Menu editMenu = new Menu("Edit");
    Menu viewMenu = new Menu("View");
    public Menu fileMenu = new Menu("File");
    private SubScene subscene;
    Protein protein;
    public VBox vbox = new VBox();
    public CheckBox basicChecker = new CheckBox("Show Basic View");
    public CheckBox ribbonChecker = new CheckBox("Show Ribbon");
    public CheckBox cartoonChecker = new CheckBox("Show Cartoon");
    public CheckBox atomSlideChecker = new CheckBox("Edit Atom Ratio");
    public CheckBox bondSlideChecker = new CheckBox("Edit Bond Ratio");
    public Button applyButton = new Button("Apply");
    PerspectiveCamera camera = new PerspectiveCamera(true);
    public Slider atomSlider = new Slider();
    public Slider bondSlider = new Slider();


    public BasicView(Stage primaryStage){
        createLayout(primaryStage);
        initFields(primaryStage);
    }

    public void setProtein(Protein protein){
        this.protein = protein;

    }
    public void setSubscene(SubScene sScene){
        subscene = sScene;
    }

    public void createLayout(Stage primaryStage){

        MenuItem open = new MenuItem("Open file");
        fileMenu.getItems().add(open);
        menubar.getMenus().addAll(fileMenu, viewMenu, editMenu);

        cartoonChecker.setPrefSize(150, 20);
        basicChecker.setPrefSize(150, 20);
        ribbonChecker.setPrefSize(150, 20);
        applyButton.setPrefSize(150, 20);
        atomSlideChecker.setPrefSize(150, 20);
        bondSlideChecker.setPrefSize(150, 20);
        atomSlideChecker.setDisable(true);
        bondSlideChecker.setDisable(true);

        atomSlider.setShowTickLabels(true);
        atomSlider.setShowTickMarks(true);
        atomSlider.setPadding(new javafx.geometry.Insets(0,2,0,2));
        atomSlider.setPrefSize(150, 40);
        atomSlider.setMinorTickCount(1);
        atomSlider.setMajorTickUnit(1);
        atomSlider.setBlockIncrement(1);
        atomSlider.setMin(0);
        atomSlider.setMax(3);
        atomSlider.setValue(1);
        atomSlider.setDisable(true);

        bondSlider.setShowTickMarks(true);
        bondSlider.setShowTickLabels(true);
        bondSlider.setPadding(new javafx.geometry.Insets(0,2,0,2));
        bondSlider.setPrefSize(150, 40);
        bondSlider.setMinorTickCount(1);
        bondSlider.setMajorTickUnit(1);
        bondSlider.setMin(0);
        bondSlider.setMax(3);
        bondSlider.setValue(1);
        bondSlider.setDisable(true);


        vbox.getChildren().addAll(cartoonChecker,basicChecker,ribbonChecker, atomSlideChecker, atomSlider, bondSlideChecker, bondSlider, applyButton);
        vbox.setPadding(new javafx.geometry.Insets(15, 12, 15, 12));
        vbox.setSpacing(10);
        vbox.setPrefSize(150.0, 150.0);




        graphPane.setPrefSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2, Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);

        camera.setNearClip(0.1);
        camera.setFarClip(Double.MAX_VALUE);
        camera.setTranslateZ(-40);
        camera.setTranslateX(+30);
        camera.setTranslateY(+15);

        textArea.setStyle("-fx-font-family: monospace");
        //this.getChildren().addAll(menubar, textArea, vbox, graphPane);

        setTop(menubar);
        setBottom(textArea);
        setRight(vbox);

        Scene scene = new Scene(this, Toolkit.getDefaultToolkit().getScreenSize().getWidth()*0.75, Toolkit.getDefaultToolkit().getScreenSize().getHeight()*0.75, false);

        primaryStage.setTitle("Your Protein ");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initFields(Stage primaryStage){

    }

    public void print12Structure(){
        textArea.setText(protein.getPrintableStructures());
        setBottom(textArea);
    }

    public void giveSubscene(SubScene sScene){
        sScene.setCamera(camera);
        graphPane.getChildren().add(sScene);

        this.setCenter(graphPane);

    }


}
