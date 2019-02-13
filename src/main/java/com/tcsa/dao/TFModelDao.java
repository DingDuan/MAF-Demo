package com.tcsa.dao;

import com.tcsa.model.TFModel;

import java.util.List;

/**
  * @Author sunweisong
  * @Date 2018/6/26 下午3:13
  */
public interface TFModelDao {
    
    void saveTFModel(TFModel tfModel);

    List<TFModel> getTFModelListByMID(long mid);

    List<TFModel> searchTFModelListByParameter(int cid1, int cid2, int mid);
}
