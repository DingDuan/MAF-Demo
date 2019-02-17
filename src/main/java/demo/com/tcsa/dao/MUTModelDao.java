package demo.com.tcsa.dao;

import demo.com.tcsa.model.MUTModel;

import java.util.List;

public interface MUTModelDao {

    /**
     * 将PUT分析结果保存到数据库
     *
     * @param putMethod PUT分析结果
     */
    void saveMUTModel(MUTModel putMethod);

    List<MUTModel> getMUTModelList();

    List<Integer> getAllMUTIDList();

}
