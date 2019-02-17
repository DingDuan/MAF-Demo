package com.tcsa.dao;

import com.tcsa.model.PCLabelModel;

/**
 * @Date ${date} ${time}
 */
public interface PCLabelModelDao {
    void savePCLabelModel(PCLabelModel pcLabelModel);

    PCLabelModel queryByCID(int cid1, int cid2);
}
