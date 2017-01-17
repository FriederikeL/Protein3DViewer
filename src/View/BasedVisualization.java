package View;

import Model.Aminoacid;
import Model.Atom;
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
    private AtomView previousAlphaC = null;

    public BasedVisualization(){};

    public BasedVisualization(Protein protein, Presenter presenter, double atomRatioFactor, double bondRatioFactor){
        this.protein = protein;
        this.presenter = presenter;
        this.atomRatioFactor = atomRatioFactor;
        this.bondRatioFactor = bondRatioFactor;

        initProteinViews();
        initBondViews();
        System.out.println(bondGroup.getTranslateX());
        this.getChildren().addAll(bondGroup, atomGroup);
    }
    public Group getAtomGroup(){
        return atomGroup;
    }
    public HashMap<Integer, AtomView> getId_atomViewHash(){
        return id_atomViewHash;
    }
    public HashMap<int[], BondView> getId_bondViewHash() {
        return id_bondViewHash;
    }

    private void initProteinViews(){
        MoleculeModel model = protein.getModelList().get(0);
        Aminoacid actualAcid;
        AtomView newAtomView;
        Atom actualAtom;
        for(int i =0; i< model.getAminoAcidList().size();i++){
            actualAcid = model.getAminoAcidList().get(i);
            for(int j = 0; j < actualAcid.getAtomList().size(); j++ ){
                if(Arrays.asList(visualizeable).contains(actualAcid.getAtomList().get(j).getAtomName())){
                    actualAtom = actualAcid.getAtomList().get(j);
                    newAtomView = new AtomView(actualAtom, atomRatioFactor);
                    newAtomView.setTranslateX(actualAtom.getX());
                    newAtomView.setTranslateY(actualAtom.getY());
                    newAtomView.setTranslateZ(actualAtom.getZ());
                    atomGroup.getChildren().add(newAtomView );
                    id_atomViewHash.put(newAtomView.getAtomViewId(), newAtomView );
                }
            }
        }
    }
    //check if required elements are in the aminoacids atomlist. Conect the correct atoms to each other
    //TODO: optional only vosualize the Atoms that belong to ribbone
    private void initBondViews(){
        MoleculeModel model = protein.getModelList().get(0);
        Aminoacid actualAcid;
        Atom n;
        Atom c;
        Atom cb;
        Atom ca;
        AtomView alphaC;
        AtomView atomView2;

        for(int i = model.getLowestAAid(); i<= model.getHighestAAid(); i++) {
            if (model.getId_AminoAcidHash().containsKey(i)) {
                actualAcid = model.getAminoAcidByID(i);
                n = actualAcid.getAtomByElement("N");
                c= actualAcid.getAtomByElement("C");
                cb = actualAcid.getAtomByElement("CB");
                ca = actualAcid.getAtomByElement("CA");

                if(n != null && previousAlphaC != null){
                    atomView2 = id_atomViewHash.get(n.getId());
                    bindAtoms(previousAlphaC, atomView2);
                }
                if(ca != null){
                    alphaC = id_atomViewHash.get(n.getId());
                    if(ca != null){
                        atomView2 =id_atomViewHash.get(ca.getId());
                        bindAtoms(alphaC, atomView2);
                    }
                    if(cb != null){
                        atomView2 = id_atomViewHash.get(cb.getId());
                        bindAtoms(alphaC, atomView2);
                    }
                    if(c != null){
                        atomView2 = id_atomViewHash.get(c.getId());
                        bindAtoms(alphaC, atomView2);
                    }
                    if(n != null){
                        atomView2= id_atomViewHash.get(n.getId());
                        bindAtoms(alphaC, atomView2);
                    }
                    previousAlphaC = alphaC;
                }
                else{
                    previousAlphaC = null;
                }

            }
        }

    }

    private void bindAtoms(AtomView alphaC, AtomView atomView2){
        int[] bondId = new int[2];
        BondView newView;
        newView = new BondView(alphaC.translateXProperty(), alphaC.translateYProperty(), alphaC.translateZProperty(), atomView2.translateXProperty(), atomView2.translateYProperty(), atomView2.translateZProperty());
        bondGroup.getChildren().add(newView);
        bondId[0] =atomView2.getAtomViewId();
        bondId[1] = alphaC.getAtomViewId();
        id_bondViewHash.put(bondId, newView);

    }

}
