package demo.dao;

import demo.entity.TFModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
  * @Date 2018/6/26 下午7:18
  */
@Repository
public interface SimValueModelDao extends JpaRepository<TFModel, Integer> {

//    void saveSimValueModel(SimValueModel simValueModel);
//
//    List<SimValueModel> searchSimValueByParameter(int cid1, int cid2);
//
//    List<SimValueModel> searchSimValueByParameterByCategory(int cid1, int cid2, int category);
//
//    List<SimValueModel> searchSimValueByParameter(int cid1, int cid2, int minSV, int maxSV);
//
//    double searchSimValueByParameterByMID(int mid, int cid1, int cid2);
//
//    int searchMIDByParametersByMaxSimValue(int cid1, int cid2, double maxSimValue);
}
