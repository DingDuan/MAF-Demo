package demo.com.tcsa.daoImpl;

import demo.com.tcsa.dao.PCLabelModelDao;
import demo.com.tcsa.model.PCLabelModel;
import demo.com.tcsa.model.SimValueModel;
import demo.com.tcsa.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
  * @Date 2018/7/19 下午12:25
  */
public class PCLabelModelDaoImpl implements PCLabelModelDao {

    private static PCLabelModelDaoImpl pcLabelModelDao = new PCLabelModelDaoImpl();

    public static PCLabelModelDaoImpl getInstance() {
        return pcLabelModelDao;
    }

    @Override
    public void savePCLabelModel(PCLabelModel pcLabelModel) {
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(pcLabelModel);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    @Override
    public PCLabelModel queryByCID(int cid1, int cid2) {
        String hql = "from PCLabelModel model" +
                " where  model.cid1=:cid1" +
                " and model.cid2=:cid2";
        Session session = null;
        PCLabelModel model = null;
        try {
            session = HibernateUtil.getSession();
            Query query = session.createQuery(hql);
            query.setParameter("cid1", cid1);
            query.setParameter("cid2", cid2);
            model = (PCLabelModel)query.uniqueResult();
            if (model == null) {
                query.setParameter("cid1", cid2);
                query.setParameter("cid2", cid1);
                model = (PCLabelModel)query.uniqueResult();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
        return model;
    }
}
