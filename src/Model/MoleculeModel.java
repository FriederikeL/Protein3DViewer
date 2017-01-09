package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;

/**
 * Created by user on 01.01.2017.
 */
public class MoleculeModel {
    private ObservableList<Aminoacid> aminoAcidList = FXCollections.observableArrayList();
    private HashMap<Integer, Aminoacid> id_AminoAcidHash = new HashMap<>();
    private int lowestAAid = 1;
    private int highestAAid= 1;

    private int id = 0;
    public MoleculeModel(int id){
        this.id = id;
    }

    public MoleculeModel(){}
    public ObservableList<Aminoacid> getAminoAcidList() {
        return aminoAcidList;
    }
    public HashMap<Integer, Aminoacid> getId_AminoAcidHash(){
        return id_AminoAcidHash;
    }
    public Aminoacid getAminoAcidByID(int id){
        return id_AminoAcidHash.get(id);
    }
    public void setLowestAAid(int i){
        lowestAAid = i;
    }
    public int getLowestAAid(){
        return lowestAAid;
    }
    public void setHighestAAid(int i){
        highestAAid = i;
    }
    public int getHighestAAid(){
        return highestAAid;
    }

    public void addAminoAcidToList(Aminoacid newAcid){
        aminoAcidList.add(newAcid);
        id_AminoAcidHash.put(newAcid.getID(), newAcid);
    }

}
