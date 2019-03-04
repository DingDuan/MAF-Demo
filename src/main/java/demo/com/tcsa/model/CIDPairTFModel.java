package demo.com.tcsa.model;

import demo.entity.TFModel;

/**
  * @Date 2018/8/13 下午7:52
  */
public class CIDPairTFModel {

    private int CID1;
    private int CID2;
    private double maxSimValue;
    private TFModel tfModel;
    private int label;

    public CIDPairTFModel(int CID1, int CID2, double maxSimValue, int label) {
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

    public TFModel getTfModel() {
        return tfModel;
    }

    public void setTfModel(TFModel tfModel) {
        this.tfModel = tfModel;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "CIDPairTFModel{" +
                "CID1=" + CID1 +
                ", CID2=" + CID2 +
                ", maxSimValue=" + maxSimValue +
                ", tfModel=" + tfModel +
                ", label=" + label +
                '}';
    }
}
