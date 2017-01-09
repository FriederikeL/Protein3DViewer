package Model;

/**
 * Created by user on 01.01.2017.
 */
public class Atom {
    private double y;
    private double z;
    private double x;
    //TODO: Die beiden Felder als Enum? deklarieren
    private String aminoacid;
    private String atomName;
    private String chain;
    private int id;
    private int res_seq_num;
    private String element;

    public Atom(int id, String atomName, String residueName, String chainID, int seqNum, double x, double y, double z, String element){
        this.id = id;
        this.atomName = atomName;
        aminoacid = residueName;
        chain = chainID;
        res_seq_num = seqNum;
        this.x = x;
        this.y = y;
        this.z = z;
        this.element =element;
    }


    public int getRes_seq_num() {
        return res_seq_num;
    }

    public String getElement() {
        return element;
    }

    public String getAtomName() {
        return atomName;
    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getAminoacidName() {
        return aminoacid;
    }

    public String getSecStrk() {
        return chain;
    }

    public double getZ() {
        return z;
    }
    public int getId(){
        return id;
    }

}
