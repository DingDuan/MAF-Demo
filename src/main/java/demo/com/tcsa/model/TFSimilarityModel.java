package demo.com.tcsa.model;

/**
  * @Author sunweisong
  * @Date 2018/6/25 下午5:19
  */
public class TFSimilarityModel {
    private int CID1;
    private int CID2;
    private int simValue;

    public TFSimilarityModel(int CID1, int CID2, int simValue) {
        this.CID1 = CID1;
        this.CID2 = CID2;
        this.simValue = simValue;
    }

    public int getCID1() {
        return CID1;
    }

    public void setCID1(int CID1) {
        this.CID1 = CID1;
    }

    public int getCID2() {
        return CID2;
    }

    public void setCID2(int CID2) {
        this.CID2 = CID2;
    }

    public int getSimValue() {
        return simValue;
    }

    public void setSimValue(int simValue) {
        this.simValue = simValue;
    }
}
