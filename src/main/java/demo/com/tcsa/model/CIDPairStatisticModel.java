package demo.com.tcsa.model;

/**
  * @Date 2018/8/11 下午8:34
  */
public class CIDPairStatisticModel {

    private int CID1;
    private int CID2;
    private double maxSimValue;
    private int label;

    public CIDPairStatisticModel(int CID1, int CID2, double maxSimValue) {
        this.CID1 = CID1;
        this.CID2 = CID2;
        this.maxSimValue = maxSimValue;
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

    public double getMaxSimValue() {
        return maxSimValue;
    }

    public void setMaxSimValue(double maxSimValue) {
        this.maxSimValue = maxSimValue;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "CIDPairStatisticModel{" +
                "CID1=" + CID1 +
                ", CID2=" + CID2 +
                ", maxSimValue=" + maxSimValue +
                ", label=" + label +
                '}';
    }
}
