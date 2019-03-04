package demo.com.tcsa.model;

import demo.entity.TFModel;

import java.math.BigDecimal;
import java.util.List;

/**
  * @Date 2018/8/8 下午1:57
  */
public class SVTFModel {
    private int simValue;
    private int MID;
    private List<TFModel> tfModelList;

    public SVTFModel(int simValue, int MID) {
        this.simValue = simValue;
        this.MID = MID;
    }

    public int getMID() {
        return MID;
    }

    public void setMID(int MID) {
        this.MID = MID;
    }

    public int getSimValue() {
        return simValue;
    }

    public void setSimValue(int simValue) {
        this.simValue = simValue;
    }

    public List<TFModel> getTfModelList() {
        return tfModelList;
    }

    public void setTfModelList(List<TFModel> tfModelList) {
        this.tfModelList = tfModelList;
    }

    @Override
    public String toString() {
        return "SVTFModel{" +
                "simValue=" + simValue +
                ", MID=" + MID +
                ", tfModelList=" + tfModelList +
                '}';
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/8/10 上午12:47
      * @author sunweisong
      */
    public String getAveTFLength() {
        int totalLength = 0;
        for (TFModel model : this.getTfModelList()) {
            totalLength += model.getLength();
        }
        double aveLength = (double)totalLength / this.getTfModelList().size();
        String aveLengthStr = Double.toString(aveLength);
        String aveTFLengthStr = String.valueOf(new BigDecimal(aveLengthStr).setScale(0, BigDecimal.ROUND_HALF_UP));
        return aveTFLengthStr;
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/8/10 上午12:49
      * @author sunweisong
      */
    public String getAveTFStateNumber() {
        int totalStateNum = 0;
        for (TFModel model : this.getTfModelList()) {
            totalStateNum += model.getStateNum();
        }
        double aveNumber = (double)totalStateNum / this.getTfModelList().size();
        String aveNumberStr = Double.toString(aveNumber);
        String aveTFNumberStr = String.valueOf(new BigDecimal(aveNumberStr).setScale(0, BigDecimal.ROUND_HALF_UP));
        return aveTFNumberStr;
    }

    /**
      *
      * @param
      * @return
      * @throws
      * @date 2018/8/10 上午1:13
      * @author sunweisong
      */
    public int getMinTFLength() {
        int minTFLength = this.getTfModelList().get(0).getLength();
        for (TFModel model : this.getTfModelList()) {
            if (model.getLength() < minTFLength) {
                minTFLength = model.getLength();
            }
        }
        return minTFLength;
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/8/10 上午9:54
      * @author sunweisong
      */
    public int getMinTFStateNum() {
        int minTFStateNum = this.getTfModelList().get(0).getStateNum();
        for (TFModel model : this.getTfModelList()) {
            if (model.getStateNum() < minTFStateNum) {
                minTFStateNum = model.getStateNum();
            }
        }
        return minTFStateNum;
    }
}
