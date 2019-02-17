package demo.com.tcsa.model;

import java.util.List;
import java.util.Map;

/**
  * @Date 2018/5/9 下午8:41
  */
public class ContestantTestModel {
    private String cid;
    private List<TestFileModel> testFileModelList;

    /**
     * 2018.06.21
     * 该选手写的所有测试片段
     */
    private Map<Integer, InvokeMethodModel> allTFMap = null;

    public Map<Integer, InvokeMethodModel> getAllTFMap() {
        return allTFMap;
    }

    public void setAllTFMap(Map<Integer, InvokeMethodModel> allTFMap) {
        this.allTFMap = allTFMap;
    }

    public ContestantTestModel(String cid) {
        this.cid = cid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public List<TestFileModel> getTestFileModelList() {
        return testFileModelList;
    }

    public void setTestFileModelList(List<TestFileModel> testFileModelList) {
        this.testFileModelList = testFileModelList;
    }


}
