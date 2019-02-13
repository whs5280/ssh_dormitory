package com.business.services.impl;

import com.business.bean.ChargeVo;
import com.business.dao.I_ChargeManagerDao;
import com.business.services.I_ChargeManageService;
import com.business.util.PageBean;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.util.List;

@Service("chargeService")
public class ChargeManageServiceImpl implements I_ChargeManageService {

    @Resource
    I_ChargeManagerDao chargeDao = null;
    boolean flag=false;


    public ResultSet Lists(ChargeVo vo, PageBean pageBean) throws HibernateException {
        ResultSet rs=null;
        try{
            rs=chargeDao.Lists(vo, pageBean);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return rs;
    }


    public int Count() throws HibernateException {
        int i=0;
        try{
            i=chargeDao.Count();
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return i;
    }


    public boolean add(ChargeVo vo) throws HibernateException {
        try{
            flag=chargeDao.add(vo);
        }catch(Exception ex){
            throw new HibernateException(ex);
        }
        return flag;
    }


    public boolean delete(Long id) throws HibernateException {
        try{
            flag=chargeDao.delete(id);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return flag;
    }


    public boolean deleteBatch(String[] arr) throws HibernateException {
        try{
            flag=chargeDao.deleteBatch(arr);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return flag;
    }


    public ChargeVo queryVo(Long id) throws HibernateException {
        ChargeVo vo = null;
        try{
            vo=chargeDao.queryVo(id);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return vo;
    }


    public boolean update(ChargeVo vo) throws HibernateException {
        try{
            flag=chargeDao.update(vo);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return flag;
    }


    public List<ChargeVo> queryAllList() throws HibernateException {
        List<ChargeVo> list=null;
        try{
            list=chargeDao.queryAllList();
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return list;
    }
}
