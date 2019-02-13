package com.business.action;

import com.business.bean.RepairVo;
import com.business.services.I_RepairManageService;
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

@Controller("repairAction")
@Scope("prototype")
public class RepairManageAction extends ActionSupport implements ServletRequestAware {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Resource I_RepairManageService repairService = null;
    HttpServletRequest request=null;

    private RepairVo vo = new RepairVo();
    private Long id;
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
            JSONArray jsonArray= JsonUtil.formatRsToJsonArray(repairService.Lists(vo, pageBean));
            int total=repairService.Count();
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
                flag=repairService.update(vo);
            }else{
                flag=repairService.add(vo);
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
            System.out.println("delIds============="+delIds);
            int delNums = str.length;
            if(str.length>1){
                for(int i=0;i<delNums;i++){
                    flag=repairService.delete(Long.parseLong(str[i]));
                }
            }else{
                flag=repairService.delete(Long.parseLong(delIds));
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
     * 	查询出所有的列表
     * */
    public String queryAllList(){
        return null;
    }

    public void setServletRequest(HttpServletRequest request) {
        this.request=request;
    }

    //get(),set()方法


    public I_RepairManageService getRepairService() {
        return repairService;
    }
    public void setRepairService(I_RepairManageService repairService) {
        this.repairService = repairService;
    }
    public HttpServletRequest getRequest() {
        return request;
    }
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
    public RepairVo getVo() {
        return vo;
    }
    public void setVo(RepairVo vo) {
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
}
