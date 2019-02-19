package demo.dao;

import demo.com.tcsa.model.MUTModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MUTModelDao extends JpaRepository<MUTModel, Integer> {

    /**
     * 将PUT分析结果保存到数据库
     *
     * @param putMethod PUT分析结果
     */
//    void saveMUTModel(MUTModel putMethod);
////
////    List<MUTModel> getMUTModelList();
////
////    List<Integer> getAllMUTIDList();

}
