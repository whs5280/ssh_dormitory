package com.business.services;

import com.business.bean.ChargeVo;
import com.business.util.PageBean;
import org.hibernate.HibernateException;

import java.sql.ResultSet;
import java.util.List;

public interface I_ChargeManageService {
    public ResultSet Lists(ChargeVo vo, PageBean pageBean) throws HibernateException;
    public int Count() throws HibernateException;

    public boolean add(ChargeVo vo) throws HibernateException;
    public boolean delete(Long id) throws HibernateException;
    public boolean deleteBatch(String arr[]) throws HibernateException;
    public ChargeVo queryVo(Long id) throws HibernateException;
    public boolean update(ChargeVo vo) throws HibernateException;

    //查询出所有的list列表
    public List<ChargeVo> queryAllList() throws HibernateException;
}
