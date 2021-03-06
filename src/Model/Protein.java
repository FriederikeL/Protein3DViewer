package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;


/**
 * Created by user on 01.01.2017.
 *Find information on: ftp://ftp.wwpdb.org/pub/pdb/doc/format_descriptions/Format_v33_A4.pdf
 *
 * HELIX Identification of helical substructures.
 * SHEET Identification of sheet substructures.
 * -> Secundary Structure
 *
 * LINK Identification of inter-residue bonds.
 *HETNAM Compound name of the heterogens.
 * TER Chain terminator.
 *MODEL Specification of model number for multiple structures in a single coordinate entry.
 *ENDMDL End-of-model record for multiple structures in a single coordinate entry.
 *
 * In COMPND: CHAIN Comma-separated list of chain identifier(s).

 */


//TODO: Deal with H2O at the end of file 1ey4.pdb
public class Protein {
    private ObservableList<MoleculeModel> modelList = FXCollections.observableArrayList();
    private String header;
    private String title = "";
    //Store all information for helix and sheets for the later sekundary structure
    private ArrayList<Helix> helixListe = new ArrayList();
    private ArrayList<Sheet> sheetListe = new ArrayList();
    private String primaryStructure = "";

    public Protein(File file){
        read(file);
        initSecStructure();
    }
    public Protein(){};

    public ObservableList<MoleculeModel> getModelList(){
        return modelList;
    }


    public void read(File file){
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            String[] splittet;
            int actualAAID = 0;
            Atom actualAtom;
            boolean setLowestAAidToModel = true;
            MoleculeModel actualModel = new MoleculeModel();
            while ((line = br.readLine()) != null) {
                splittet = line.split(" ");

                switch(splittet[0]){
                    //TODO: Check if correct beginning
                    case "HEADER": header = line.substring(11);
                        break;
                    //TODO: Check if correct concatenating
                    case "TITLE": title = title.concat(line.substring(11));
                        break;
                    case "HELIX": helixListe.add(createHelix(line));
                        break;
                    case "SHEET": sheetListe.add(createSheet(line));
                        break;
                    case "SEQRES": primaryStructure = primaryStructure.concat(parseSEQRES(line));
                        break;
                    case "MODEL":
                        actualModel = new MoleculeModel(Integer.parseInt(line.substring(10, 14).replace(" ", "")));
                        setLowestAAidToModel = true;
                        break;
                    case "TER":
                        //TODO: Add this model to list at the end
                        actualModel.setHighestAAid(actualAAID);
                        modelList.add(actualModel);
                        break;
                    case "ATOM": actualAtom = createAtom(line);
                        if(setLowestAAidToModel){
                        actualModel.setLowestAAid(actualAtom.getRes_seq_num());
                            setLowestAAidToModel = false;
                    }
                        if (actualAtom.getRes_seq_num() != actualAAID){
                            actualModel.addAminoAcidToList(new Aminoacid(actualAtom.getRes_seq_num(), actualAtom.getAminoacidName()));
                            actualAAID = actualAtom.getRes_seq_num();
                        }
                        actualModel.getAminoAcidByID(actualAtom.getRes_seq_num()).addAtom(actualAtom);
                        break;
                    default:
                        break;
                        //System.out.println("line geskipped");

                }

            }

        }
        catch (FileNotFoundException e){
            System.out.println("File not found!");
        }
        catch(IOException e){
            System.out.println("Could not read File");
    }
    System.out.println("Protein created!");
    }
    private Helix createHelix(String line){
        int id = Integer.parseInt(line.substring(7, 10).replace(" ",""));
        String amino = line.substring(15, 18).replace(" ","");
        String chain = line.substring(19, 20).replace(" ","");
        int initNum = Integer.parseInt(line.substring(21, 25).replace(" ", ""));
        int endNum = Integer.parseInt(line.substring(33, 37).replace(" ", ""));
        int hclass = Integer.parseInt(line.substring(38, 40).replace(" ", ""));

        return new Helix(id, amino, chain, initNum, endNum, hclass);
    }

    private Sheet createSheet(String line){
        int numCurrent = 0;
        int numPrev = 0;
        int id = Integer.parseInt(line.substring(7, 10).replace(" ",""));
        int strandNum = Integer.parseInt(line.substring(14, 16).replace(" ",""));
        int initNum = Integer.parseInt(line.substring(22, 26).replace(" ",""));
        int endNum = Integer.parseInt(line.substring(33, 37).replace(" ",""));
        int strandT = Integer.parseInt(line.substring(38, 40).replace(" ",""));
        String nameCurrent = line.substring(45, 48).replace(" ","");
        String namePrev = line.substring(60, 63).replace(" ","");
            if (line.charAt(53) != ' '){
            numCurrent = Integer.parseInt(line.substring(50, 54).replace(" ",""));
        }
        if(line.charAt(68)!= ' '){
            numPrev = Integer.parseInt(line.substring(65, 69).replace(" ",""));
        }

        return new Sheet(id, strandNum, initNum, endNum, strandT, nameCurrent, numCurrent, namePrev, numPrev);

    }
    private Atom createAtom(String line){
        int id = Integer.parseInt(line.substring(6, 11).replace(" ",""));
        String atomName = line.substring(12, 16).replace(" ","");
        String residueName = line.substring(17, 20).replace(" ","");
        String chainID = line.substring(21, 22).replace(" ","");
        int seqNum = Integer.parseInt(line.substring(22, 26).replace(" ",""));
        double x = Double.parseDouble(line.substring(30, 38).replace(" ",""));
        double y = Double.parseDouble(line.substring(38, 46).replace(" ",""));
        double z = Double.parseDouble(line.substring(46, 54).replace(" ",""));
        String element = line.substring(76, 78).replace(" ","");

        return new Atom(id, atomName, residueName, chainID, seqNum, x, y, z, element);
    }

    private void initSecStructure(){
        for(int i = 0; i< modelList.size(); i++){
            for(int j = 0; j < helixListe.size(); j++){
                    for(int aminoPosition = helixListe.get(j).getInitPos(); aminoPosition <= helixListe.get(j).getEndPos(); aminoPosition++){
                        if(modelList.get(i).getId_AminoAcidHash().containsKey(aminoPosition)){
                            modelList.get(i).getAminoAcidByID(aminoPosition).setSecondStructure("H");
                        }
                }
            }
            for(int j = 0; j < sheetListe.size();j++){
                for(int aminoPosition = sheetListe.get(j).getInitPos(); aminoPosition <= sheetListe.get(j).getEndPos(); aminoPosition++){
                    if(modelList.get(i).getId_AminoAcidHash().containsKey(aminoPosition)){
                        modelList.get(i).getAminoAcidByID(aminoPosition).setSecondStructure("E");
                    }
                }
            }

        }

    }
    public String getPrintableStructures(){
        String secstrk = "";
        String structures = "";
        int split = 130;
        int counter = 1;
        int end = primaryStructure.length();
        MoleculeModel model = modelList.get(0);
        secstrk = secstrk.concat(stringFiller(secstrk, "-", primaryStructure.length()-model.getAminoAcidList().size()));
        for(int i = model.getLowestAAid(); i<= model.getHighestAAid(); i++){
            if (model.getId_AminoAcidHash().containsKey(i)) {
                secstrk = secstrk.concat(model.getAminoAcidByID(i).getSecondStructure());
            }
        }
        if(secstrk.length() > split){
            structures = structures.concat(stringFiller("", " ",22)+stringFiller(Integer.toString(counter)," ", counter+128)+(counter+128)+"\n"+stringFiller("Primary structure:"," ", 22) + primaryStructure.substring(0, 130)+ "\n" + stringFiller("Secundary structure:"," ", 22) + secstrk.substring(0,130) + "\n\n");
            secstrk = secstrk.substring(130);
            primaryStructure = primaryStructure.substring(130);
            counter = counter+129;
        }
        structures = structures.concat(stringFiller("", " ",22)+stringFiller(Integer.toString(counter)," ", secstrk.length()-1)+end+"\n"+stringFiller("Primary structure:"," ", 22) + primaryStructure+ "\n" + stringFiller("Secundary structure:"," ", 22) + secstrk);

        return(structures);

    }

    private String stringFiller(String str, String filler, int lenght){
        while(str.length() < lenght){
            str = str.concat(filler);
        }
        return(str);
    }

    public String parseSEQRES(String line){
        line = line.substring(19);
        String[] aAnames = line.split(" ");
        String name;
        String primstructure = "";
        for(int i = 0; i< aAnames.length; i++) {
            name = aAnames[i];

            switch (name) {
                case "ALA":primstructure = primstructure.concat("A");
                    break;
                case "ARG":primstructure = primstructure.concat("R");
                    break;
                case "ASN":primstructure = primstructure.concat("N");
                    break;
                case "ASP": primstructure = primstructure.concat("D");
                    break;
                case "CYS":primstructure = primstructure.concat("C");
                    break;
                case "GLN":primstructure = primstructure.concat("Q");
                    break;
                case "GLU":primstructure = primstructure.concat("E");
                    break;
                case "GLY":primstructure = primstructure.concat("G");
                    break;
                case "HIS":primstructure = primstructure.concat("H");
                    break;
                case "ILE":primstructure = primstructure.concat("I");
                    break;
                case "LEU":primstructure = primstructure.concat("L");
                    break;
                case "LYS":primstructure = primstructure.concat("K");
                    break;
                case "MET":primstructure = primstructure.concat("M");
                    break;
                case "PHE":primstructure = primstructure.concat("F");
                    break;
                case "PRO":primstructure = primstructure.concat("P");
                    break;
                case "SER":primstructure = primstructure.concat("S");
                    break;
                case "THR":primstructure = primstructure.concat("T");
                    break;
                case "TRP":primstructure = primstructure.concat("W");
                    break;
                case "TYR":primstructure = primstructure.concat("Y");
                    break;
                case "VAL":primstructure = primstructure.concat("V");
                    break;

                default:
                    System.out.println("couldn't identify Aminoacid " + name);
                    return (" ");
            }
        }
        return primstructure;

    }


}
