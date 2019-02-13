package com.business.services.impl;

import com.business.bean.IllegalVo;
import com.business.dao.I_IllegalManageDao;
import com.business.services.I_IllegalManageService;
import com.business.util.PageBean;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.util.List;

@Service("illegalService")
public class IllegalManageServiceImpl implements I_IllegalManageService {

    @Resource I_IllegalManageDao illegalDao = null;
    boolean flag=false;

    public ResultSet illegalList(IllegalVo vo, PageBean pageBean) throws HibernateException {
        ResultSet rs=null;
        try{
            rs=illegalDao.illegalList(vo, pageBean);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return rs;
    }


    public int IllegalCount() throws HibernateException {
        int i=0;
        try{
            i=illegalDao.IllegalCount();
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return i;
    }


    public boolean addIllegal(IllegalVo vo) throws HibernateException {
        try{
            flag=illegalDao.addIllegal(vo);
        }catch(Exception ex){
            throw new HibernateException(ex);
        }
        return flag;
    }


    public boolean deleteIllegal(Long id) throws HibernateException {
        try{
            flag=illegalDao.deleteIllegal(id);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return flag;
    }


    public boolean deleteBatch(String[] arr) throws HibernateException {
        try{
            flag=illegalDao.deleteBatch(arr);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return flag;
    }


    public IllegalVo queryVo(Long id) throws HibernateException {
        IllegalVo vo = null;
        try{
            vo=illegalDao.queryVo(id);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return vo;
    }


    public boolean updateIllegalVo(IllegalVo vo) throws HibernateException {
        try{
            flag=illegalDao.updateIllegalVo(vo);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return flag;
    }


    public List<IllegalVo> queryAllList() throws HibernateException {
        List<IllegalVo> list=null;
        try{
            list=illegalDao.queryAllList();
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return list;
    }
}
