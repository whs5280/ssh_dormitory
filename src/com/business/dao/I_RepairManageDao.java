package com.business.dao;

import com.business.bean.RepairVo;
import com.business.util.PageBean;
import org.hibernate.HibernateException;

import java.sql.ResultSet;
import java.util.List;

public interface I_RepairManageDao {
    public ResultSet Lists(RepairVo vo, PageBean pageBean) throws HibernateException;
    public int Count() throws HibernateException;

    public boolean add(RepairVo vo) throws HibernateException;
    public boolean delete(Long id) throws HibernateException;
    public boolean deleteBatch(String arr[]) throws HibernateException;
    public RepairVo queryVo(Long id) throws HibernateException;
    public boolean update(RepairVo vo) throws HibernateException;

    //查询出所有的list列表
    public List<RepairVo> queryAllList() throws HibernateException;
}
