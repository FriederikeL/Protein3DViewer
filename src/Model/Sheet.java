package Model;

/**
 * Created by user on 03.01.2017.
 */
public class Sheet {
    private int id;
    private int numberOfStrands;
    private int seqNum_initRes;
    private int seqNum_endRes;
    private int strandType;
    private String resName_currentStrand;
    private int resSeqNum_currentStrand;
    private String resName_prevStrand;
    private int resSeqNum_prevStrand;

    public Sheet(int id, int strandNum, int initNum, int endNum, int strandT, String nameCurrent, int numCurrent, String namePrev, int numPrev){
    this.id = id;
        numberOfStrands = strandNum;
        seqNum_initRes =initNum;
        seqNum_endRes = endNum;
        strandType = strandT;
        resName_currentStrand =nameCurrent;
        resName_prevStrand = namePrev;
        resSeqNum_currentStrand = numCurrent;
        resSeqNum_prevStrand = numPrev;
    }

    public int getInitPos(){
        return seqNum_initRes;
    }
    public int getEndPos(){
        return seqNum_endRes;
    }
}
