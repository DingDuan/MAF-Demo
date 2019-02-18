package demo.com.tcsa.daoImpl;

import demo.dao.TFModelDao;
import demo.com.tcsa.model.TFModel;
import demo.com.tcsa.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
  * @Date 2018/6/26 下午3:09
  */
public class TFModelDaoImpl implements TFModelDao {

    private static TFModelDaoImpl tfModelDao = new TFModelDaoImpl();

    public static TFModelDaoImpl getInstance() {
        return tfModelDao;
    }

    @Override
    public void saveTFModel(TFModel tfModel) {
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(tfModel);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
    }

    @Override
    public List<TFModel> getTFModelListByMID(long mid) {
        List<TFModel> tfModelList = null;
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            tfModelList = session.createQuery("from TFModel where mid = " + mid)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
        return tfModelList;
    }

    @Override
    public List<TFModel> searchTFModelListByParameter(int cid1, int cid2
            , int mid) {
        String hql = "from TFModel model" +
                " where model.mid=:mid" +
                " and (model.cid=:cid1 or model.cid=:cid2)";
        List<TFModel> tfModelList = null;
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            Query query = session.createQuery(hql);
            query.setParameter("mid", mid);
            query.setParameter("cid1", cid1);
            query.setParameter("cid2", cid2);
            tfModelList = query.list();
            if (tfModelList.size() == 0) {
                query.setParameter("mid", mid);
                query.setParameter("cid1", cid2);
                query.setParameter("cid2", cid1);
                tfModelList = query.list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
        return tfModelList;
    }


}
