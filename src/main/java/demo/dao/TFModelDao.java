package demo.dao;

import demo.com.tcsa.model.TFModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
  * @Date 2018/6/26 下午3:13
  */
public interface TFModelDao extends JpaRepository<TFModel, Integer> {
    
//    void saveTFModel(TFModel tfModel);
//
//    List<TFModel> getTFModelListByMID(long mid);
//
//    List<TFModel> searchTFModelListByParameter(int cid1, int cid2, int mid);
}
