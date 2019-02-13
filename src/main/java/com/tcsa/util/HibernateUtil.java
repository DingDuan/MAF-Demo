package com.tcsa.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @Author weisongsun
 * @Date ${date} ${time}
 */
public class HibernateUtil {
    //线程锁，保证线程安全，在用的时候只有这一个session，别人无法处理
    private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
    //定义常量是为了实现单例，不允许更改赋值
    private static final SessionFactory sessionFactory = buildFactory();


    /**
      * buildFactory方法,读取hibernate.cfg.xml配置，加载数据库和实体类
      * @param
      * @return
      * @throws
      * @date 2018/5/4 下午2:29
      * @author sunweisong
      */
    private static SessionFactory buildFactory() {
        Configuration cfg = new Configuration().configure();
        return  cfg.buildSessionFactory();
    }

    /**
      * 获取session
      * @param
      * @return
      * @throws
      * @date 2018/5/4 下午2:23
      * @author sunweisong
      */
    public static Session getSession(){
        //获取线程锁threadLocal中的session
        Session session = threadLocal.get();
        //如果threadLocal中是空的就新建一个session
        if(session == null){
            session = sessionFactory.openSession();
            //将新建的session放入threadLocal中
            threadLocal.set(session);
        }
        return session;
    }

    /**
      * 关闭当前session
      * @param
      * @return
      * @throws
      * @date 2018/5/4 下午2:35
      * @author sunweisong
      */
    public static void closeSession(){
        //先获取threadLocal中的session
        Session session = threadLocal.get();
        //如果不是空，就把session关闭，并且把threadLocal清空
        if(session != null){
            session.close();
            threadLocal.set(null);
        }
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/5/9 下午3:19
      * @author sunweisong
      */
    public static void closeSessionFactory() {
        if (sessionFactory.isOpen()) {
            sessionFactory.close();
        }
    }
}
