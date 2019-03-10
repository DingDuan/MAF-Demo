package demo.dao;

import demo.entity.SimValueModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
  * @Date 2018/6/26 下午7:18
  */
@Repository
public interface SimValueModelDao extends JpaRepository<SimValueModel, Integer> {

    @Query(value = "select * from sim_value_model where cid1=?1 and cid2=?2", nativeQuery = true)
    List<SimValueModel> searchSimValueByPair(int cid1, int cid2);
//
//    List<SimValueModel> searchSimValueByParameterByCategory(int cid1, int cid2, int category);
//
//    List<SimValueModel> searchSimValueByParameter(int cid1, int cid2, int minSV, int maxSV);
//
//    double searchSimValueByParameterByMID(int mid, int cid1, int cid2);
//
//    int searchMIDByParametersByMaxSimValue(int cid1, int cid2, double maxSimValue);
}
