package Model;

/**
 * Created by user on 03.01.2017.
 */
public class Helix {
    private int id;
    private String aminoacid;
    private String chain;
    private int seqNum_initRes;
    private int seqNum_endRes;
    private int helixClass;

    public Helix(int id, String amino, String chain, int initNum, int endNum, int hClass){
        this.id = id;
        aminoacid = amino;
        this.chain = chain;
        seqNum_initRes = initNum;
        seqNum_endRes = endNum;
        helixClass = hClass;
    }
    public int getInitPos(){
        return seqNum_initRes;
    }
    public int getEndPos(){
        return seqNum_endRes;
    }
}
