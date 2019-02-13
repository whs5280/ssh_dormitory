package com.business.dao.impl;

import com.business.bean.VisitorVo;
import com.business.dao.I_VisitorManageDao;
import com.business.util.PageBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository("vistorDao")
@Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
public class VisitorManageDaoImpl implements I_VisitorManageDao {

    @Resource
    SessionFactory sessionFactory=null;
    private Session session=null;
    Log log= LogFactory.getLog(this.getClass());

    @SuppressWarnings("deprecation")
    public ResultSet visitorList(VisitorVo vo, PageBean pageBean) throws HibernateException {
        PreparedStatement ps =null;
        ResultSet rs=null;
        try{
            session=sessionFactory.getCurrentSession();
            Connection con = session.connection();
            StringBuffer sql=new StringBuffer("select dv_id,visitor_name,visitor_event,visitor_time from dorm_visitor where 1=1");
            if(null!=pageBean){
                sql.append(" limit "+pageBean.getStart()+","+pageBean.getRows());
            }
            ps = con.prepareStatement(sql.toString());
            rs=ps.executeQuery();

        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return rs;
    }


    public int VisitorCount() throws HibernateException {
        int i=0;
        try{
            session=sessionFactory.getCurrentSession();

            String hql="from VisitorVo";
            Query query = session.createQuery(hql);
            i =query.list().size();
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return i;
    }


    public boolean addVisitor(VisitorVo vo) throws HibernateException {
        boolean flag=true;
        try{
            session=sessionFactory.getCurrentSession();
            session.save(vo);
        }catch(Exception ex){
            flag=false;
            throw new HibernateException(ex);
        }
        return flag;
    }


    public boolean deleteVisitor(Long id) throws HibernateException {
        boolean flag=false;
        try{
            session=sessionFactory.getCurrentSession();
            String hql="delete from VisitorVo v where v.dv_id=?";
            Query query=session.createQuery(hql);
            query.setLong(0,id);
            int i=query.executeUpdate();
            if(i>0){
                flag=true;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return flag;
    }


    public boolean deleteBatch(String[] arr) throws HibernateException {
        log.info("[批量删除数据:用HQL的方式和用 in拼接sql语句] 实现类开始");
        boolean flag=true;
        try{
            session=sessionFactory.getCurrentSession();
            String hql="delete VisitorVo v where v.dv_id in(0";
            for(int m=0;m<arr.length;m++){
                hql+=","+arr[m];
            }
            hql=hql+")";
            Query query=session.createQuery(hql);
            int delInt=query.executeUpdate();
            System.out.println("删除数目："+delInt);

            log.info("[批量删除数据:用HQL的方式和用 in拼接sql语句]  实现类成功");
        }catch(Exception ex){
            ex.printStackTrace();
            log.error("[批量删除数据:用HQL的方式和用 in拼接sql语句]  实现类失败，失败原因："+ex);
            flag=true;
        }
        return flag;
    }


    @SuppressWarnings("unchecked")
    public List<VisitorVo> queryAllVisitor() throws HibernateException {
        List<VisitorVo> allList=new ArrayList<VisitorVo>();
        VisitorVo vo=null;
        try{
            session=sessionFactory.getCurrentSession();
            String hql="select v.dv_id,v.visitor_name,v.visitor_event,v.visitor_time from VisitorVo v";
            Query query=session.createQuery(hql);
            List<Object[]> list=query.list();
            if(null!=list&&list.size()>0){
                for(int i=0;i<list.size();i++){
                    int index=0;
                    Object[] arr=list.get(i);
                    vo=new VisitorVo();
                    vo.setDv_id(Long.parseLong(String.valueOf(arr[index++])));
                    vo.setVisitor_name(String.valueOf(arr[index++]));
                    vo.setVisitor_event(String.valueOf(arr[index++]));
                    vo.setVisitor_time(String.valueOf(arr[index++]));
                    allList.add(vo);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return allList;

    }
}
