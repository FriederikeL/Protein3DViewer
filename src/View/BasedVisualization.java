package View;

import Model.Aminoacid;
import Model.MoleculeModel;
import Model.Protein;
import Presenter.Presenter;
import javafx.scene.Group;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by user on 09.01.2017.
 */
public class BasedVisualization extends Group {
    private Group atomGroup = new Group();
    private Group bondGroup= new Group();
    private HashMap<Integer, AtomView> id_atomViewHash = new HashMap<>();
    private HashMap<int[], BondView> id_bondViewHash = new HashMap<>();
    private Protein protein;
    private Presenter presenter;
    private String[] visualizeable = {"N", "C", "CA", "CB", "O"};
    private double atomRatioFactor;
    private double bondRatioFactor;

    public BasedVisualization(){};

    public BasedVisualization(Protein protein, Presenter presenter, double atomRatioFactor, double bondRatioFactor){
        this.protein = protein;
        this.presenter = presenter;
        this.atomRatioFactor = atomRatioFactor;
        this.bondRatioFactor = bondRatioFactor;

        initProteinView();
        this.getChildren().addAll(atomGroup);

    }

    public Group getAtomGroup(){
        return atomGroup;
    }
    public HashMap<Integer, AtomView> getId_atomViewHash(){
        return id_atomViewHash;
    }

    private void initProteinView(){
        //TODO: Bisher nur auf ein Modell ausgelegt. Okay so?
        MoleculeModel model = protein.getModelList().get(0);
        Aminoacid actualAcid;
        AtomView newAtom;
        for(int i =0; i< model.getAminoAcidList().size();i++){
            actualAcid = model.getAminoAcidList().get(i);
            for(int j = 0; j < actualAcid.getAtomList().size(); j++ ){
                if(Arrays.asList(visualizeable).contains(actualAcid.getAtomList().get(j).getAtomName())){
                    newAtom = new AtomView(actualAcid.getAtomList().get(j), atomRatioFactor);
                    atomGroup.getChildren().add(newAtom);
                    id_atomViewHash.put(newAtom.getAtomViewId(), newAtom);
                }
            }
        }
    }

}
