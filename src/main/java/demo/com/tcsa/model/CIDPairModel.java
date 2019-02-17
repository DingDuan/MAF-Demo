package demo.com.tcsa.model;

/**
  * @Author sunweisong
  * @Date 2018/8/8 下午12:10
  */
public class CIDPairModel {
    private String CID1;
    private String CID2;

    public CIDPairModel(String CID1, String CID2) {
        this.CID1 = CID1;
        this.CID2 = CID2;
    }

    public String getCID1() {
        return CID1;
    }

    public void setCID1(String CID1) {
        this.CID1 = CID1;
    }

    public String getCID2() {
        return CID2;
    }

    public void setCID2(String CID2) {
        this.CID2 = CID2;
    }

    @Override
    public String toString() {
        return "CIDPairModel{" +
                "CID1='" + CID1 + '\'' +
                ", CID2='" + CID2 + '\'' +
                '}';
    }
}
