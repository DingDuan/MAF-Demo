package demo.dao;

import demo.entity.TFModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
  * @Date 2018/6/26 下午3:13
  */
public interface TFModelDao extends JpaRepository<TFModel, Integer> {

    @Query(value = "select * from tf_model where mid=?1", nativeQuery = true)
    List<TFModel> getTFModelListByMID(Integer mid);

    @Query(value = "select id from tf_model where mid=?1 and cid=?2", nativeQuery = true)
    int getIdByMIDAndCid(int mid,int cid);
//
//    List<TFModel> searchTFModelListByParameter(int cid1, int cid2, int mid);
}
