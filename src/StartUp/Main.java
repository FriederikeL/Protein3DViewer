package StartUp;

import Model.MoleculeModel;
import Model.Protein;
import Presenter.Presenter;
import View.BasedVisualization;
import View.BasicView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by user on 03.01.2017.
 */
public class Main extends Application {
    public Main() {
        super();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        BasicView view = new BasicView(primaryStage);
        Presenter presenter = new Presenter(view);


        //TODO: wird noch ausgelagert durch GUI Befehle

    }


    public static void main(String[] args) {
        Application.launch();

    }



}
