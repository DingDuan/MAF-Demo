package demo.dao;

import demo.com.tcsa.model.PCLabelModel;

/**
 * @Date ${date} ${time}
 */
public interface PCLabelModelDao {
    void savePCLabelModel(PCLabelModel pcLabelModel);

    PCLabelModel queryByCID(int cid1, int cid2);
}
