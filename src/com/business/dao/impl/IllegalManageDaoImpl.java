package com.business.dao.impl;

import com.business.bean.IllegalVo;
import com.business.dao.I_IllegalManageDao;
import com.business.util.PageBean;
import com.business.util.Tools;
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

@Repository("illegalDao")
@Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
public class IllegalManageDaoImpl implements I_IllegalManageDao {

    @Resource
    SessionFactory sessionFactory=null;
    private Session session=null;
    Log log= LogFactory.getLog(this.getClass());

    @SuppressWarnings("deprecation")
    public ResultSet illegalList(IllegalVo vo, PageBean pageBean) throws HibernateException {
        PreparedStatement ps =null;
        ResultSet rs=null;
        try{
            session=sessionFactory.getCurrentSession();
            Connection con = session.connection();
            StringBuffer sql=new StringBuffer("select id, illegal_stu, illegal_content,illegal_time from illegal where 1=1");
            if((null!=vo)&&(!Tools.isEmpty(vo.getIllegal_stu()))){
                sql.append(" and illegal_stu like '%").append(vo.getIllegal_stu()).append("%'");
            }
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


    public int IllegalCount() throws HibernateException {
        int i=0;
        try{
            session=sessionFactory.getCurrentSession();

            String hql=" from IllegalVo";
            Query query = session.createQuery(hql);
            i =query.list().size();

        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return i;
    }


    public boolean addIllegal(IllegalVo vo) throws HibernateException {
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


    public boolean deleteIllegal(Long id) throws HibernateException {
        boolean flag=false;
        try{
            session=sessionFactory.getCurrentSession();
            String hql="delete from IllegalVo i where i.id=?";
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
            String hql="delete IllegalVo i where i.id in(0";
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


    public IllegalVo queryVo(Long id) throws HibernateException {
        IllegalVo vo = null;
        try{
            session=sessionFactory.getCurrentSession();
            vo=(IllegalVo) session.get(IllegalVo.class,id);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return vo;
    }


    public boolean updateIllegalVo(IllegalVo vo) throws HibernateException {
        boolean flag=false;
        try{
            session=sessionFactory.getCurrentSession();
            String hql="update IllegalVo i set i.illegal_stu=?,i.illegal_content=?,i.illegal_time=? where i.id=?";
            Query query=session.createQuery(hql);
            int index=0;
            query.setLong(index++,vo.getIllegal_stu());
            query.setString(index++,vo.getIllegal_content());
            query.setString(index++,vo.getIllegal_time());
            query.setLong(index++,vo.getId());
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


    @SuppressWarnings("unchecked")
    public List<IllegalVo> queryAllList() throws HibernateException {
        List<IllegalVo> allList=new ArrayList<IllegalVo>();
        IllegalVo vo=null;
        try{
            session=sessionFactory.getCurrentSession();
            String hql="select i.id, i.illegal_stu, i.illegal_content, i.illegal_time from IllegalVo i";
            Query query=session.createQuery(hql);
            List<Object[]> list=query.list();
            if(null!=list&&list.size()>0){
                for(int i=0;i<list.size();i++){
                    int index=0;
                    Object[] arr=list.get(i);
                    vo=new IllegalVo();
                    vo.setId(Long.parseLong(String.valueOf(arr[index++])));
                    vo.setIllegal_stu(Long.parseLong(String.valueOf(arr[index++])));
                    vo.setIllegal_content(String.valueOf(arr[index++]));
                    vo.setIllegal_time(String.valueOf(arr[index++]));
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
