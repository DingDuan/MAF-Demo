package demo.com.tcsa.model;

import java.util.List;

/**
  * @Date 2018/6/25 下午11:12
  */
public class ContestantSimilarityByMID {
    private int MID;
    private List<ContestantSimilarity> contestantSimilarityList;

    public ContestantSimilarityByMID(int MID) {
        this.MID = MID;
    }

    public int getMID() {
        return MID;
    }

    public void setMID(int MID) {
        this.MID = MID;
    }

    public List<ContestantSimilarity> getContestantSimilarityList() {
        return contestantSimilarityList;
    }

    public void setContestantSimilarityList(List<ContestantSimilarity> contestantSimilarityList) {
        this.contestantSimilarityList = contestantSimilarityList;
    }
}
