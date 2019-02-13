package com.business.services.impl;

import com.business.bean.RepairVo;
import com.business.dao.I_RepairManageDao;
import com.business.services.I_RepairManageService;
import com.business.util.PageBean;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.util.List;

@Service("repairService")
public class RepairManageServiceImpl implements I_RepairManageService {

    @Resource
    I_RepairManageDao repairDao = null;
    boolean flag=false;

    public ResultSet Lists(RepairVo vo, PageBean pageBean) throws HibernateException {
        ResultSet rs=null;
        try{
            rs=repairDao.Lists(vo, pageBean);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return rs;
    }


    public int Count() throws HibernateException {
        int i=0;
        try{
            i=repairDao.Count();
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return i;
    }


    public boolean add(RepairVo vo) throws HibernateException {
        try{
            flag=repairDao.add(vo);
        }catch(Exception ex){
            throw new HibernateException(ex);
        }
        return flag;
    }


    public boolean delete(Long id) throws HibernateException {
        try{
            flag=repairDao.delete(id);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return flag;
    }


    public boolean deleteBatch(String[] arr) throws HibernateException {
        try{
            flag=repairDao.deleteBatch(arr);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return flag;
    }


    public RepairVo queryVo(Long id) throws HibernateException {
        RepairVo vo = null;
        try{
            vo=repairDao.queryVo(id);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return vo;
    }


    public boolean update(RepairVo vo) throws HibernateException {
        try{
            flag=repairDao.update(vo);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return flag;
    }


    public List<RepairVo> queryAllList() throws HibernateException {
        List<RepairVo> list=null;
        try{
            list=repairDao.queryAllList();
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return list;
    }
}
