package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by user on 03.01.2017.
 */
public class Aminoacid {
    private ObservableList<Atom> atomList = FXCollections.observableArrayList();
    private int id;
    private String acidID;
    private String name;
    private String secondStructure = "-";

    public Aminoacid(int id, String name){
        this.id = id;
        this.name = name;
    }

    public ObservableList<Atom> getAtomList() {
        return atomList;
    }
    public int getID(){
        return id;
    }

    public String getAcidID(){
        return acidID;
    }

    public String getSecondStructure() {
        return secondStructure;
    }

    public void setSecondStructure(String secondStructure) {
        this.secondStructure = secondStructure;
    }


    public void addAtom(Atom newAtom){
        atomList.add(newAtom);
    }

    public Atom getAtomByElement(String element){
        for(int i = 0; i< atomList.size(); i++){
            if(atomList.get(i).getAtomName().equals(element)){
                return atomList.get(i);
            }
        }
        return null;
    }

}
