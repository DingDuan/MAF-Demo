package demo.com.tcsa.model;

/**
  * @Author sunweisong
  * @Date 2018/8/10 下午4:27
  */
public class TFMinInfo {
    private int cid1;
    private int cid2;

    private int mid;
    private int minTFLength;
    private int minTFStateNum;

    public TFMinInfo(int cid1, int cid2, int mid, int minTFLength, int minTFStateNum) {
        this.cid1 = cid1;
        this.cid2 = cid2;
        this.mid = mid;
        this.minTFLength = minTFLength;
        this.minTFStateNum = minTFStateNum;
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

    public double getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getMinTFLength() {
        return minTFLength;
    }

    public void setMinTFLength(int minTFLength) {
        this.minTFLength = minTFLength;
    }

    public int getMinTFStateNum() {
        return minTFStateNum;
    }

    public void setMinTFStateNum(int minTFStateNum) {
        this.minTFStateNum = minTFStateNum;
    }

    @Override
    public String toString() {
        return "TFMinInfo{" +
                "cid1=" + cid1 +
                ", cid2=" + cid2 +
                ", mid=" + mid +
                ", minTFLength=" + minTFLength +
                ", minTFStateNum=" + minTFStateNum +
                '}';
    }
}
