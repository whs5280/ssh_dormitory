package com.business.action;

import com.business.bean.ChargeVo;
import com.business.services.I_ChargeManageService;
import com.business.util.*;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;

@Controller("chargeAction")
@Scope("prototype")
public class ChargeManageAction extends ActionSupport implements ServletRequestAware {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Resource I_ChargeManageService chargeService = null;
    HttpServletRequest request=null;

    private ChargeVo vo = new ChargeVo();
    private Long id;
    private String page;
    private String rows;

    private String delIds;
    private int t_name;


    /**
     * 	默认方法，用来查询List
     * */
    public String execute() throws Exception{
        try{
            PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
            JSONObject result=new JSONObject();
            System.out.println("t_name=="+t_name);
            vo.setMonth(t_name);
            JSONArray jsonArray= JsonUtil.formatRsToJsonArray(chargeService.Lists(vo, pageBean));
            int total=chargeService.Count();
            result.put("rows", jsonArray);
            result.put("total", total);
            ResponseUtil.write(ServletActionContext.getResponse(), result);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 	根据得到的id，判断其是否为空。若为空，则执行增加操作，反之，执行修改
     * */
    public String save() throws Exception{
        boolean flag=false;
        JSONObject result=new JSONObject();
        try{
            if(!Tools.isEmpty(id)){
                vo.setId(id);
                System.out.println("id不为空！！！");
            }
            if(!Tools.isEmpty(id)){
                flag=chargeService.update(vo);
            }else{
                flag=chargeService.add(vo);
            }
            if(flag){
                result.put("success","true");
            }else{
                result.put("success","true");
                result.put("errorMsg","保存失败！！");
            }
        }catch(Exception ex){
            String msg = ex.getCause().getMessage() ;
            if(msg != null && msg.contains("org.hibernate.exception.ConstraintViolationException")){
                result.put("success", "true");
                result.put("errorMsg", "该记录名称已存在，请核实后重新录入！");
            }
        } finally{
            ResponseUtil.write(ServletActionContext.getResponse(), result);
        }
        return null;
    }

    /**
     * 	删除一条或者多条记录
     * */
    public String delete() throws Exception{
        boolean flag=false;
        try{
            JSONObject result=new JSONObject();
            String str[]=delIds.split(",");
            System.out.println("delIds============="+delIds);
            int delNums = str.length;
            if(str.length>1){
                for(int i=0;i<delNums;i++){
                    flag=chargeService.delete(Long.parseLong(str[i]));
                }
            }else{
                flag=chargeService.delete(Long.parseLong(delIds));
            }

            if(flag){
                result.put("success", "true");
                result.put("delNums", delNums);
            }else{
                result.put("errorMsg", "Sorry！删除失败");
            }
            ResponseUtil.write(ServletActionContext.getResponse(), result);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 	用模版导出查询后的数据
     * */
    public String export() throws Exception{
        try{
            Workbook wb=new HSSFWorkbook();
            if(null==vo){
                vo=new ChargeVo();
            }
            vo.setMonth(t_name);
            ResultSet rs = chargeService.Lists(vo, null);
            wb= ExcelUtil.fillExcelDataWithTemplate(rs, "chargeExporTemplate.xls");
            ResponseUtil.export(ServletActionContext.getResponse(), wb, "费用.xls");
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 	查询出所有的列表
     * */
    public String queryAllList(){
        return null;
    }

    public void setServletRequest(HttpServletRequest request) {
        this.request=request;
    }

    //get(),set()方法

    public I_ChargeManageService getChargeService() {
        return chargeService;
    }
    public void setChargeService(I_ChargeManageService chargeService) {
        this.chargeService = chargeService;
    }
    public HttpServletRequest getRequest() {
        return request;
    }
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
    public ChargeVo getVo() {
        return vo;
    }
    public void setVo(ChargeVo vo) {
        this.vo = vo;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPage() {
        return page;
    }
    public void setPage(String page) {
        this.page = page;
    }
    public String getRows() {
        return rows;
    }
    public void setRows(String rows) {
        this.rows = rows;
    }
    public String getDelIds() {
        return delIds;
    }
    public void setDelIds(String delIds) {
        this.delIds = delIds;
    }
    public int getT_name() {
        return t_name;
    }
    public void setT_name(int t_name) {
        this.t_name = t_name;
    }
}
