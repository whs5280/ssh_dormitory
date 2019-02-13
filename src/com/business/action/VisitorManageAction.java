package com.business.action;

import com.business.bean.VisitorVo;
import com.business.services.I_VisitorManageService;
import com.business.util.JsonUtil;
import com.business.util.PageBean;
import com.business.util.ResponseUtil;
import com.business.util.Tools;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller("visitorAction")
@Scope("prototype")
public class VisitorManageAction extends ActionSupport implements ServletRequestAware {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Resource I_VisitorManageService visitorService = null;
    HttpServletRequest request=null;

    private VisitorVo vo = new VisitorVo();
    private String dv_id;
    private String page;
    private String rows;

    private String delIds;


    /**
     * 	默认方法，用来查询List
     * */
    public String execute() throws Exception{
        try{
            PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
            JSONObject result=new JSONObject();
            JSONArray jsonArray= JsonUtil.formatRsToJsonArray(visitorService.visitorList(vo, pageBean));
            int total=visitorService.VisitorCount();
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
            if(!Tools.isEmpty(dv_id)){
                vo.setDv_id(Long.parseLong(dv_id));
                System.out.println("id不为空！！！");
            }
            flag=visitorService.addVisitor(vo);
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
                result.put("errorMsg", "该记录已存在，请核实后重新录入！");
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
            int delNums = str.length;
            if(str.length>1){
                for(int i=0;i<delNums;i++){
                    flag=visitorService.deleteVisitor(Long.parseLong(str[i]));
                }
            }else{
                flag=visitorService.deleteVisitor(Long.parseLong(delIds));
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

    public String queryAllVisitor(){
        return null;
    }




    public void setServletRequest(HttpServletRequest request) {
        this.request=request;
    }

    //get(),set()方法


    public I_VisitorManageService getVisitorService() {
        return visitorService;
    }
    public void setVisitorService(I_VisitorManageService visitorService) {
        this.visitorService = visitorService;
    }
    public HttpServletRequest getRequest() {
        return request;
    }
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
    public VisitorVo getVo() {
        return vo;
    }
    public void setVo(VisitorVo vo) {
        this.vo = vo;
    }
    public String getDv_id() {
        return dv_id;
    }
    public void setDv_id(String dv_id) {
        this.dv_id = dv_id;
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
}
