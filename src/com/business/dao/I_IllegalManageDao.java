package com.business.dao;

import com.business.bean.IllegalVo;
import com.business.util.PageBean;
import org.hibernate.HibernateException;

import java.sql.ResultSet;
import java.util.List;

public interface I_IllegalManageDao {
    public ResultSet illegalList(IllegalVo vo, PageBean pageBean) throws HibernateException;
    public int IllegalCount() throws HibernateException;

    public boolean addIllegal(IllegalVo vo) throws HibernateException;
    public boolean deleteIllegal(Long id) throws HibernateException;
    public boolean deleteBatch(String arr[]) throws HibernateException;
    public IllegalVo queryVo(Long id) throws HibernateException;
    public boolean updateIllegalVo(IllegalVo vo) throws HibernateException;

    //查询出所有的list列表
    public List<IllegalVo> queryAllList() throws HibernateException;
}
