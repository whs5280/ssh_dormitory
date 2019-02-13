package com.business.dao.impl;

import com.business.bean.ChargeVo;
import com.business.dao.I_ChargeManagerDao;
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

@Repository("chargeDao")
@Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
public class ChargeManageDaoImpl implements I_ChargeManagerDao {

    @Resource
    SessionFactory sessionFactory=null;
    private Session session=null;
    Log log= LogFactory.getLog(this.getClass());

    @SuppressWarnings("deprecation")
    public ResultSet Lists(ChargeVo vo, PageBean pageBean) throws HibernateException {
        PreparedStatement ps =null;
        ResultSet rs=null;
        try{
            session=sessionFactory.getCurrentSession();
            Connection con = session.connection();
            StringBuffer sql=new StringBuffer("select id, dorm_id, building_id, month, charges from charge where 1=1");
            sql.append(" order by month desc");
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

            String hql=" from ChargeVo";
            Query query = session.createQuery(hql);
            i =query.list().size();

        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return i;
    }


    public boolean add(ChargeVo vo) throws HibernateException {
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
            String hql="delete from ChargeVo c where c.id=?";
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
            String hql="delete ChargeVo c where c.id in(0";
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


    public ChargeVo queryVo(Long id) throws HibernateException {
        ChargeVo vo = null;
        try{
            session=sessionFactory.getCurrentSession();
            vo=(ChargeVo) session.get(ChargeVo.class,id);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return vo;
    }


    public boolean update(ChargeVo vo) throws HibernateException {
        boolean flag=false;
        try{
            session=sessionFactory.getCurrentSession();
            String hql="update ChargeVo c set c.dorm_id=?,c.building_id=?,c.month=?,c.charges=? where c.id=?";
            Query query=session.createQuery(hql);
            int index=0;
            query.setLong(index++,vo.getDorm_id());
            query.setLong(index++,vo.getBuilding_id());
            query.setInteger(index++,vo.getMonth());
            query.setDouble(index++,vo.getCharges());
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
    public List<ChargeVo> queryAllList() throws HibernateException {
        List<ChargeVo> allList=new ArrayList<ChargeVo>();
        ChargeVo vo=null;
        try{
            session=sessionFactory.getCurrentSession();
            String hql="select v.id, v.dorm_id, v.building_id, v.month, v.charges from ChargeVo v";
            Query query=session.createQuery(hql);
            List<Object[]> list=query.list();
            if(null!=list&&list.size()>0){
                for(int i=0;i<list.size();i++){
                    int index=0;
                    Object[] arr=list.get(i);
                    vo=new ChargeVo();
                    vo.setId(Long.parseLong(String.valueOf(arr[index++])));
                    vo.setDorm_id(Long.parseLong(String.valueOf(arr[index++])));
                    vo.setBuilding_id(Long.parseLong(String.valueOf(arr[index++])));
                    vo.setMonth(Integer.parseInt(String.valueOf(arr[index++])));
                    vo.setCharges(Double.parseDouble(String.valueOf(arr[index++])));

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
