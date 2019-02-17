package demo.com.tcsa.model;

/**
  * @Date 2018/7/11 下午5:24
  */
public class SimValueMatrixModel {

    private int cid1;
    private int cid2;
    private double[] simValueArray;

    /**
     * label = 0 : Plagiarism;
     * label = 1 : Not Plagiarism;
     */
    private int label;

    public SimValueMatrixModel(int cid1, int cid2) {
        this.cid1 = cid1;
        this.cid2 = cid2;
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

    public double[] getSimValueArray() {
        return simValueArray;
    }

    public void setSimValueArray(double[] simValueArray) {
        this.simValueArray = simValueArray;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }
}
