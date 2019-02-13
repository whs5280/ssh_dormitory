package com.business.services;

import com.business.bean.VisitorVo;
import com.business.util.PageBean;
import org.hibernate.HibernateException;

import java.sql.ResultSet;
import java.util.List;

public interface I_VisitorManageService {
    public ResultSet visitorList(VisitorVo vo, PageBean pageBean) throws HibernateException;
    public int VisitorCount() throws HibernateException;

    /*
    来访记录不支持修改
     */
    public boolean addVisitor(VisitorVo vo) throws HibernateException;
    public boolean deleteVisitor(Long id) throws HibernateException;
    public boolean deleteBatch(String arr[]) throws HibernateException;

    //查询出所有的list列表
    public List<VisitorVo> queryAllVisitor() throws HibernateException;
}
