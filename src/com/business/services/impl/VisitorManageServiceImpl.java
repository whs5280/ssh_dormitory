package com.business.services.impl;

import com.business.bean.VisitorVo;
import com.business.dao.I_VisitorManageDao;
import com.business.services.I_VisitorManageService;
import com.business.util.PageBean;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.util.List;

@Service("visitorService")
public class VisitorManageServiceImpl implements I_VisitorManageService {

    @Resource I_VisitorManageDao visitordao = null;
    boolean flag=false;

    public ResultSet visitorList(VisitorVo vo, PageBean pageBean) throws HibernateException {
        ResultSet rs=null;
        try{
            rs=visitordao.visitorList(vo, pageBean);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return rs;
    }


    public int VisitorCount() throws HibernateException {
        int i=0;
        try{
            i=visitordao.VisitorCount();
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return i;
    }


    public boolean addVisitor(VisitorVo vo) throws HibernateException {
        try{
            flag=visitordao.addVisitor(vo);
        }catch(Exception ex){
            throw new HibernateException(ex);
        }
        return flag;
    }


    public boolean deleteVisitor(Long id) throws HibernateException {
        try{
            flag=visitordao.deleteVisitor(id);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return flag;
    }


    public boolean deleteBatch(String[] arr) throws HibernateException {
        try{
            flag=visitordao.deleteBatch(arr);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return flag;
    }


    public List<VisitorVo> queryAllVisitor() throws HibernateException {
        List<VisitorVo> list=null;
        try{
            list=visitordao.queryAllVisitor();
        }catch(Exception ex){
            ex.printStackTrace();
            throw new HibernateException(ex);
        }
        return list;
    }
}
