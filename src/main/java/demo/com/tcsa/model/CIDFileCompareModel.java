package demo.com.tcsa.model;


public class CIDFileCompareModel {

    private int cid1;
    private int cid2;

    private String cid1FileName;
    private String cid2FileName;

    private int simValue;

    private int isMax;

    public CIDFileCompareModel(int cid1, int cid2, String cid1FileName, String cid2FileName, int simValue) {
        this.cid1 = cid1;
        this.cid2 = cid2;
        this.cid1FileName = cid1FileName;
        this.cid2FileName = cid2FileName;
        this.simValue = simValue;
    }

    public int getCid1() {
        return cid1;
    }

    public void setCid1(int cid1) {
        this.cid1 = cid1;
    }

    public int getCid2() {
        return cid2;
    }

    public void setCid2(int cid2) {
        this.cid2 = cid2;
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

    public int getSimValue() {
        return simValue;
    }

    public void setSimValue(int simValue) {
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
        return "CIDFileCompareModel{" +
                "cid1=" + cid1 +
                ", cid2=" + cid2 +
                ", cid1FileName='" + cid1FileName + '\'' +
                ", cid2FileName='" + cid2FileName + '\'' +
                ", simValue=" + simValue +
                ", isMax=" + isMax +
                '}';
    }
}
