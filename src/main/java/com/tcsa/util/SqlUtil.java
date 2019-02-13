package com.tcsa.util;

import com.tcsa.dao.MUTModelDao;
import com.tcsa.dao.TFModelDao;
import com.tcsa.daoImpl.MUTModelDaoImpl;
import com.tcsa.daoImpl.TFModelDaoImpl;
import com.tcsa.model.ContestantTFModel;
import com.tcsa.model.MUTModel;
import com.tcsa.model.TFModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public class SqlUtil {

    private static TFModelDao tfModelDao = TFModelDaoImpl.getInstance();


    public SqlUtil() {
    }

    public SessionFactory getSessionFactory() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(MUTModel.class);
        // 编程配置映射，否则org.hibernate.MappingException: Unknown entity

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();

        return config.buildSessionFactory(serviceRegistry);
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/6/26 下午3:44
      * @author sunweisong
      */
    public static void writeTFsToDatabase(int MID,  List<ContestantTFModel> contestantTFModelList) {
        for (ContestantTFModel contestantTFModel :
                contestantTFModelList) {
            int CID = contestantTFModel.getCID();
            String testFragment = contestantTFModel.getTestFragment();
            int stateNumber = contestantTFModel.getStateNumber();
            int fragLength = contestantTFModel.getFragmentLength();
            TFModel tfModel = new TFModel(testFragment, fragLength, stateNumber, MID, CID);
            tfModelDao.saveTFModel(tfModel);
        }
    }




}