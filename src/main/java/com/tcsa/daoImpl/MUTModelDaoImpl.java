package com.tcsa.daoImpl;

import com.tcsa.dao.MUTModelDao;
import com.tcsa.model.MUTModel;
import com.tcsa.util.HibernateUtil;
import com.tcsa.util.SqlUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class MUTModelDaoImpl implements MUTModelDao {

    private static MUTModelDaoImpl MUTModelDao = new MUTModelDaoImpl();
    private SqlUtil sqlUtil;

    private MUTModelDaoImpl() {
        sqlUtil = new SqlUtil();
    }

    public static MUTModelDaoImpl getInstance() {
        return MUTModelDao;
    }

    @Override
    public void saveMUTModel(MUTModel mutModel) {
        SessionFactory sessionFactory = sqlUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.saveOrUpdate(mutModel);

        tx.commit();
        session.close();
        sessionFactory.close();
    }

    @Override
    public List<MUTModel> getMUTModelList() {
        List<MUTModel> mutModelList = null;
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            mutModelList = session.createQuery("from MUTModel ").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
        return mutModelList;
    }

    @Override
    public List<Integer> getAllMUTIDList() {
        List<Integer> midList = null;
        Session session = null;
        try {
            session = HibernateUtil.getSession();
            midList = session.createQuery("select methodId from MUTModel").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
        return midList;
    }
}
