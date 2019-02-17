package demo.com.tcsa.model;

/**
  * @Date 2018/8/14 上午9:18
  */
public class StatisticTFRowInfo {
    private int CID1;
    private int CID2;
    private int length;
    private int stateNum;
    private double maxSimValue;
    private int label;

    public StatisticTFRowInfo(int CID1, int CID2, int length, int stateNum, double maxSimValue, int label) {
        this.CID1 = CID1;
        this.CID2 = CID2;
        this.length = length;
        this.stateNum = stateNum;
        this.maxSimValue = maxSimValue;
        this.label = label;
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

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getStateNum() {
        return stateNum;
    }

    public void setStateNum(int stateNum) {
        this.stateNum = stateNum;
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
        return "StatisticTFRowInfo{" +
                "CID1=" + CID1 +
                ", CID2=" + CID2 +
                ", length=" + length +
                ", stateNum=" + stateNum +
                ", maxSimValue=" + maxSimValue +
                ", label=" + label +
                '}';
    }
}
