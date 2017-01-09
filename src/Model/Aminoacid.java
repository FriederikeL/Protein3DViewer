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
        acidID = findAcidId(name);
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


    public String findAcidId(String name){
        switch(name) {
            case "ALA": return("A");
            case "ARG": return("R");
            case "ASN": return("N");
            case "ASP": return("D");
            case "CYS": return("C");
            case "GLN": return("Q");
            case "GLU": return("E");
            case "GLY": return("E");
            case "HIS": return("H");
            case "ILE": return("I");
            case "LEU": return("L");
            case "LYS": return("K");
            case "MET": return("M");
            case "PHE": return("F");
            case "PRO": return("P");
            case "SER": return("S");
            case "THR": return("T");
            case "TRP": return("W");
            case "TYR": return("Y");
            case "VAL": return("V");


            default:
                System.out.println("couldn't identify Aminoacid " + name);
                return(" ");
        }

    }
    public void addAtom(Atom newAtom){
        atomList.add(newAtom);
    }

}
