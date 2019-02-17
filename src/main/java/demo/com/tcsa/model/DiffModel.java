package demo.com.tcsa.model;

/**
  *
  * @Date 2018/8/5 下午1:54
  */
public class DiffModel {
    private int CID1;
    private int CID2;
    private String cid1FileName;
    private String cid2FileName;
    private double simValue;

    private int isMax;

    public DiffModel(int CID1, int CID2, String cid1FileName, String cid2FileName, double simValue) {
        this.CID1 = CID1;
        this.CID2 = CID2;
        this.cid1FileName = cid1FileName;
        this.cid2FileName = cid2FileName;
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

    public String getCid1FileName() {
        return cid1FileName;
    }

    public void setCid1FileName(String cid1FileName) {
        this.cid1FileName = cid1FileName;
    }

    public String getCid2FileName() {
        return cid2FileName;
    }

    public void setCid2FileName(String cid2FileName) {
        this.cid2FileName = cid2FileName;
    }

    public double getSimValue() {
        return simValue;
    }

    public void setSimValue(double simValue) {
        this.simValue = simValue;
    }

    public int getIsMax() {
        return isMax;
    }

    public void setIsMax(int isMax) {
        this.isMax = isMax;
    }

    @Override
    public String toString() {
        return "DiffModel{" +
                "CID1=" + CID1 +
                ", CID2=" + CID2 +
                ", cid1FileName='" + cid1FileName + '\'' +
                ", cid2FileName='" + cid2FileName + '\'' +
                ", simValue=" + simValue +
                ", isMax=" + isMax +
                '}';
    }
}
