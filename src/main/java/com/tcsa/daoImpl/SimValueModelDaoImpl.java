package com.tcsa.daoImpl;

import com.tcsa.dao.SimValueModelDao;
import com.tcsa.model.SimValueModel;
import com.tcsa.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
  * @Author sunweisong
  * @Date 2018/6/26 下午7:20
  */
public class SimValueModelDaoImpl implements SimValueModelDao {

    private static SimValueModelDaoImpl simValueModelDao = new SimValueModelDaoImpl();

    public static SimValueModelDaoImpl getInstance() {
        return simValueModelDao;
    }

    @Override
    public void saveSimValueModel(SimValueModel simValueModel) {
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(simValueModel);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    @Override
    public List<SimValueModel> searchSimValueByParameter(int cid1, int cid2) {
        String hql = "from SimValueModel model" +
                " where  model.cid1=:cid1" +
                " and model.cid2=:cid2";
        List<SimValueModel> simValueModelList = null;
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            Query query = session.createQuery(hql);
            query.setParameter("cid1", cid1);
            query.setParameter("cid2", cid2);
            simValueModelList = query.list();
            if (simValueModelList.size() == 0) {
                query.setParameter("cid1", cid2);
                query.setParameter("cid2", cid1);
                simValueModelList = query.list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
        return simValueModelList;
    }

    @Override
    public List<SimValueModel> searchSimValueByParameterByCategory(int cid1, int cid2, int category) {
        String hql = "from SimValueModel model"
                + " where model.category=:category"
                + " and model.cid1=:cid1"
                + " and model.cid2=:cid2";
        List<SimValueModel> simValueModelList = null;
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            Query query = session.createQuery(hql);
            query.setParameter("category", category);
            query.setParameter("cid1", cid1);
            query.setParameter("cid2", cid2);
            simValueModelList = query.list();
            if (simValueModelList.size() == 0) {
                query.setParameter("cid1", cid2);
                query.setParameter("cid2", cid1);
                simValueModelList = query.list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
        return simValueModelList;
    }

    @Override
    public List<SimValueModel> searchSimValueByParameter(int cid1, int cid2, int minSV, int maxSV) {
        String hql = "from SimValueModel model" +
                " where  model.cid1=:cid1" +
                " and model.cid2=:cid2" +
                " and model.simValue >= " + minSV +
                " and model.simValue <= " + maxSV;
        List<SimValueModel> simValueModelList = null;
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            Query query = session.createQuery(hql);
            query.setParameter("cid1", cid1);
            query.setParameter("cid2", cid2);
            simValueModelList = query.list();
            if (simValueModelList.size() == 0) {
                query.setParameter("cid1", cid2);
                query.setParameter("cid2", cid1);
                simValueModelList = query.list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
        return simValueModelList;
    }


    @Override
    public double searchSimValueByParameterByMID(int mid, int cid1, int cid2) {
        String hql = "from SimValueModel model" +
                " where model.mid=:mid and model.cid1=:cid1" +
                " and model.cid2=:cid2";
        List<SimValueModel> simValueModelList = null;
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            Query query = session.createQuery(hql);
            query.setParameter("mid", mid);
            query.setParameter("cid1", cid1);
            query.setParameter("cid2", cid2);
            query.setMaxResults(1);
            simValueModelList = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
        if (simValueModelList == null || simValueModelList.size() == 0) {
            return -1;
        }
        SimValueModel simValueModel = simValueModelList.get(0);
        if (simValueModel == null) {
            return -1;
        }
        double simValue = simValueModel.getSimValue();
        return simValue;
    }

    @Override
    public int searchMIDByParametersByMaxSimValue(int cid1, int cid2, double maxSimValue) {
        String hql = "from SimValueModel model" +
                " where model.simValue=:simValue and model.cid1=:cid1" +
                " and model.cid2=:cid2";
        List<SimValueModel> simValueModelList = null;
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            Query query = session.createQuery(hql);
            query.setParameter("simValue", maxSimValue);
            query.setParameter("cid1", cid1);
            query.setParameter("cid2", cid2);;
            simValueModelList = query.list();
            if (simValueModelList == null || simValueModelList.size() == 0) {
                query.setParameter("simValue", maxSimValue);
                query.setParameter("cid1", cid2);
                query.setParameter("cid2", cid1);;
                simValueModelList = query.list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
        if (simValueModelList == null || simValueModelList.size() == 0) {
            return -1;
        }
        SimValueModel simValueModel = simValueModelList.get(0);
        int MID = simValueModel.getMid();
        return MID;
    }


}
