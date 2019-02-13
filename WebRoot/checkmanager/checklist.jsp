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
            }
            if("system"==loginFlag){
                $("#systemShow").show();
                $("#teacherShow").hide();
                $("#vipStudentShow").hide();
            }
            if("vipStudent"==loginFlag){
                $("#systemShow").hide();
                $("#teacherShow").hide();
                $("#vipStudentShow").show();
            }
        });

        //查询
        function searchUser(){
            $("#dg").datagrid('load',{
                t_name:$("#t_name").val()
            });
        }

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
                    $.post("illegalManageAction!delete.action",{delIds:ids},function(result){
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
            $("#dlg").dialog("open").dialog("setTitle","添加违规记录");
            $("#fm").form("clear");

            url="illegalManageAction!save.action";
        }

        //编辑
        function editUser(){
            var selectedRows=$("#dg").datagrid("getSelections");
            if(selectedRows.length!=1){
                $.messager.alert("系统提示","请选择一条要修改的数据");
                return;
            }
            var row=selectedRows[0];

            $("#dlg").dialog("open").dialog("setTitle","编辑违规记录");
            $("#illegal_stu").val(row.illegal_stu);
            $("#illegal_content").val(row.illegal_content);
            $("#illegal_time").val(row.illegal_time);
            url="illegalManageAction!save.action?id="+row.id;
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

        //用模版导出用户
        function exportUser(){
            $('#condition').submit();
        }
    </script>
</head>
<body style="margin:5px">
    <input type="hidden" name="loginFlag" id="loginFlag" value="${sessionScope.loginFlag }"/>
    <table id="dg" title="违规记录基本信息" class="easyui-datagrid" style="width:900px;height:400px"
           url="illegalManageAction!execute.action"
           toolbar="#toolbar" pagination="true" pageList=[5,10,15]
           rownumbers="true" fitColumns="true"  fit="true" >
        <thead>
        <tr>
            <th field="id" checkbox="true">id</th>
            <th field="illegal_stu" width="50">学号</th>
            <th field="illegal_content" width="50">违规内容</th>
            <th field="illegal_time" width="50">时间</th>
        </tr>
        </thead>
    </table>

    <div id="toolbar">
        <div id="systemShow" style="float: left">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="exportUser()">导出记录</a>
        </div>
        <div id="teacherShow" style="float: left">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">添加记录</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">编辑记录</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteUser()">删除记录</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="exportUser()">导出记录</a>
        </div>
        <div id="vipStudentShow" style="float: left">
        </div>
        <div>
            <form id="condition" name="condition" action="illegalManageAction!export.action" method="post">
                &nbsp;&nbsp;<input type="text" id="t_name" name="t_name" placeholder="请输入要查询的学号">
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="btn" plain="true" onclick="searchUser()">搜索</a>
            </form>
        </div>
    </div>

    <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
         closed="true" buttons="#dlg-buttons">
        <form id="fm" method="post">
            <table cellspacing="10px;">
                <tr>
                    <td>学号：</td>
                    <td><input name="vo.illegal_stu" id="illegal_stu" class="easyui-validatebox" required="true" style="width: 200px;"></td>
                </tr>
                <tr>
                    <td>违规内容：</td>
                    <td><input name="vo.illegal_content" id="illegal_content" class="easyui-validatebox" required="true" style="width: 200px;"></td>
                </tr>
                <tr>
                    <td>时间：</td>
                    <td><input name="vo.illegal_time" id="illegal_time" class="easyui-validatebox" required="true" type="date" style="width: 200px;"></td>
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
