<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head>
    <base href="<%=basePath%>">

    <title>My JSP 'index.jsp' starting page</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">

    <link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.2/demo/demo.css">
    <!-- <link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.3.3/themes/bootstrap/easyui.css"> -->
    <script type="text/javascript" src="js/jquery-easyui-1.3.2/jquery-1.8.0.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="js/jquery-easyui-1.3.2/jquery.easyui.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="js/jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>

    <script type="text/javascript">
        var url;

        $(function(){
            // TIP: 配合body解决页面跳动和闪烁问题
            $("body").css({visibility:"visible"});

            var loginFlag=$("#loginFlag").val();
            if("teacher"==loginFlag){
                $("#teacherShow").show();
                $("#systemShow").hide();
                $("#vipStudentShow").hide();
                $("#studentShow").hide();
            }
            if("system"==loginFlag){
                $("#systemShow").show();
                $("#teacherShow").hide();
                $("#vipStudentShow").hide();
                $("#studentShow").hide();
            }
            if("vipStudent"==loginFlag){
                $("#systemShow").hide();
                $("#teacherShow").hide();
                $("#vipStudentShow").show();
                $("#studentShow").hide();
            }
            if("student"==loginFlag){
                $("#systemShow").hide();
                $("#teacherShow").hide();
                $("#vipStudentShow").hide();
                $("#studentShow").show()
            }
        });

        //点击删除按钮
        function deleteUser(){
            var selectedRows=$("#dg").datagrid("getSelections");
            if(selectedRows.length==0){
                $.messager.alert("系统提示","请选择要删除的数据!");
                return;
            }
            var strIds=[];
            for(var i=0;i<selectedRows.length;i++){
                strIds.push(selectedRows[i].id);
            }
            var ids = strIds.join(",");
            $.messager.confirm("系统提示","您确定要删掉这<font color='red'>"+selectedRows.length+"</font>条数据么?",function(r){
                if(r){
                    $.post("repairManageAction!delete.action",{delIds:ids},function(result){
                        if(result.success){
                            $.messager.alert("系统提示",'您已成功删除<font color=red>'+result.delNums+"</font>条数据!");
                            $("#dg").datagrid("reload");
                        }else{
                            $.messager.alert("系统提示",result.errorMsg);
                        }
                    },"json");
                }
            });
        }

        //添加
        function newUser(){
            $("#dlg").dialog("open").dialog("setTitle","添加维修记录");
            $("#fm").form("clear");

            url="repairManageAction!save.action";
        }

        //编辑
        function editUser(){
            var selectedRows=$("#dg").datagrid("getSelections");
            if(selectedRows.length!=1){
                $.messager.alert("系统提示","请选择一条要修改的数据");
                return;
            }
            var row=selectedRows[0];

            $("#dlg").dialog("open").dialog("setTitle","编辑维修记录");
            $("#dorm_id").val(row.dorm_id);
            $("#building_id").val(row.building_id);
            $("#content").val(row.content);
            $("#state").val(row.state);
            url="repairManageAction!save.action?id="+row.id;
        }

        //保存
        function saveUser(){
            $("#fm").form("submit",{
                url:url,
                onSubmit:function(){
                    return $(this).form('validate');
                },
                success:function(result){
                    var result=eval("("+result+")");
                    if(result.errorMsg){
                        $.messager.alert("系统提示",result.errorMsg);
                        return;
                    }else{
                        $.messager.alert("系统提示","您已经保存成功！");
                        $("#dlg").dialog("close");
                        $("#dg").datagrid("reload");
                    }
                }
            });
        }

    </script>
</head>

<body style="margin:5px">
<input type="hidden" name="loginFlag" id="loginFlag" value="${sessionScope.loginFlag }"/>
<table id="dg" title="维修基本信息" class="easyui-datagrid" style="width:900px;height:400px"
       url="repairManageAction!execute.action"
       toolbar="#toolbar" pagination="true" pageList=[5,10,15]
       rownumbers="true" fitColumns="true"  fit="true" >
    <thead>
    <tr>
        <th field="id" checkbox="true">id</th>
        <th field="dorm_id" width="50">宿舍号</th>
        <th field="building_id" width="50">楼宇</th>
        <th field="content" width="50">维修内容</th>
        <th field="status" width="50">状态</th>
    </tr>
    </thead>
</table>

<div id="toolbar">
        <div id="systemShow" style="float: left">
        </div>
        <div id="teacherShow" style="float: left">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">编辑记录</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteUser()">删除记录</a>
        </div>
        <div id="vipStudentShow" style="float: left">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">添加记录</a>
        </div>
        <div id="studentShow" style="float: left">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">添加记录</a>
        </div>
    </div>

    <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
         closed="true" buttons="#dlg-buttons">
        <form id="fm" method="post">
            <table cellspacing="10px;">
                <tr>
                    <td>宿舍号：</td>
                    <td><input name="vo.dorm_id" id="dorm_id" class="easyui-validatebox" required="true" style="width: 200px;"></td>
                </tr>
                <tr>
                    <td>楼宇：</td>
                    <td><input name="vo.building_id" id="building_id" class="easyui-validatebox" required="true" style="width: 200px;"></td>
                </tr>
                <tr>
                    <td>维修内容：</td>
                    <td><input name="vo.content" id="content" class="easyui-validatebox" required="true" style="width: 200px;"></td>
                </tr>
                <tr>
                    <td>状态：</td>
                    <td>
                        <select name="vo.status" id="status" class="easyui-validatebox" required="true" style="width: 200px;">
                            <option value="0">未解决</option>
                            <option value="1">已解决</option>
                        </select>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">关闭</a>
    </div>
</body>
</html>
