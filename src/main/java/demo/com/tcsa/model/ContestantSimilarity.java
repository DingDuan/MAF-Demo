package demo.com.tcsa.model;

import java.util.List;

/**
  * @Date 2018/6/25 下午10:58
  */
public class ContestantSimilarity {
    private int CID;
    private List<TFSimilarityModel> tfSimilarityModelList;

    public ContestantSimilarity(int CID) {
        this.CID = CID;
    }

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    public List<TFSimilarityModel> getTfSimilarityModelList() {
        return tfSimilarityModelList;
    }

    public void setTfSimilarityModelList(List<TFSimilarityModel> tfSimilarityModelList) {
        this.tfSimilarityModelList = tfSimilarityModelList;
    }
}
