package com.business.dao.impl;

import com.business.bean.ChargeVo;
import com.business.bean.RepairVo;
import com.business.dao.I_RepairManageDao;
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

@Repository("repairDao")
@Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
public class RepairManageDaoImpl implements I_RepairManageDao {

    @Resource
    SessionFactory sessionFactory=null;
    private Session session=null;
    Log log= LogFactory.getLog(this.getClass());

    @SuppressWarnings("deprecation")
    public ResultSet Lists(RepairVo vo, PageBean pageBean) throws HibernateException {
        PreparedStatement ps =null;
        ResultSet rs=null;
        try{
            session=sessionFactory.getCurrentSession();
            Connection con = session.connection();
            StringBuffer sql=new StringBuffer("select id, dorm_id, building_id, content, status from repair where 1=1");
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


    public int Count() throws HibernateException {
        int i=0;
        try{
            session=sessionFactory.getCurrentSession();

            String hql=" from RepairVo ";
            Query query = session.createQuery(hql);
            i =query.list().size();

        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return i;
    }


    public boolean add(RepairVo vo) throws HibernateException {
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


    public boolean delete(Long id) throws HibernateException {
        boolean flag=false;
        try{
            session=sessionFactory.getCurrentSession();
            String hql="delete from RepairVo r where r.id=?";
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
            String hql="delete RepairVo r where r.id in(0";
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


    public RepairVo queryVo(Long id) throws HibernateException {
        RepairVo vo = null;
        try{
            session=sessionFactory.getCurrentSession();
            vo=(RepairVo) session.get(RepairVo.class,id);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return vo;
    }


    public boolean update(RepairVo vo) throws HibernateException {
        boolean flag=false;
        try{
            session=sessionFactory.getCurrentSession();
            String hql="update RepairVo r set r.dorm_id=?,r.building_id=?,r.content=?,r.status=? where r.id=?";
            Query query=session.createQuery(hql);
            int index=0;
            query.setString(index++,vo.getDorm_id());
            query.setString(index++,vo.getBuilding_id());
            query.setString(index++,vo.getContent());
            query.setInteger(index++,vo.getStatus());
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
    public List<RepairVo> queryAllList() throws HibernateException {
        List<RepairVo> allList=new ArrayList<RepairVo>();
        RepairVo vo=null;
        try{
            session=sessionFactory.getCurrentSession();
            String hql="select r.id, r.dorm_id, r.building_id, r.content, r.status from RepairVo r";
            Query query=session.createQuery(hql);
            List<Object[]> list=query.list();
            if(null!=list&&list.size()>0){
                for(int i=0;i<list.size();i++){
                    int index=0;
                    Object[] arr=list.get(i);
                    vo=new RepairVo();
                    vo.setId(Long.parseLong(String.valueOf(arr[index++])));
                    vo.setDorm_id(String.valueOf(arr[index++]));
                    vo.setBuilding_id(String.valueOf(arr[index++]));
                    vo.setContent(String.valueOf(arr[index++]));
                    vo.setStatus(Integer.parseInt(String.valueOf(arr[index++])));

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
