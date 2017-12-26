<%--
  Created by IntelliJ IDEA.
  User: hewater
  Date: 2017/12/16
  Time: 23:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>流水记录</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.5.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.5.3/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.5.3/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
</head>
<body>
<h2>流水记录</h2>
<div class="easyui-layout" style="width:1024px;height:600px;" id="center">
    <div data-options="region:'north'" style="height:50px">
        <div style="margin-top:10px;">
            <form id="f_main" method="post" enctype="multipart/form-data" style="float:left">
                酒类型:<input id="jiuCombo" name="rJId" style="width:60px;"/>&nbsp;&nbsp;
                人员姓名:<input id="userCombo" name="rUId" style="width:60px;"/>
                &nbsp;<input id="filebox" name="file" data-options="prompt:'上传文件...'"/>
                <a href="#" id="saveFile"  style="margin-right:40px;margin-left: 10px">保存</a>
            </form>
            <form style="float:left"><input id="searchBox" style="width:400px;height:32px;font-size: 20px;"></form>

        </div>
    </div>
    <div data-options="region:'south',split:true" style="height:50px;">
        <a href="#" id="addJiu" style="margin-right:20px;margin-left: 10px">新增酒类型</a>
        <a href="#" id="addUser"  style="margin-right:40px;margin-left: 10px">新增人员</a>
    </div>

    <%--<div data-options="region:'east',split:true" title="East" style="width:100px;"></div>--%>
    <%--<div data-options="region:'west',split:true" title="West" style="width:100px;"></div>--%>

    <div data-options="region:'center',title:'数据查询',iconCls:'icon-ok'">
        <table id="dataGrid">
            <%--<thead>--%>
            <%--<tr>--%>
                <%--<th data-options="field:'itemid'" width="80">Item ID</th>--%>
                <%--<th data-options="field:'productid'" width="100">Product ID</th>--%>
                <%--<th data-options="field:'listprice',align:'right'" width="80">List Price</th>--%>
                <%--<th data-options="field:'unitcost',align:'right'" width="80">Unit Cost</th>--%>
                <%--<th data-options="field:'attr1'" width="150">Attribute</th>--%>
                <%--<th data-options="field:'status',align:'center'" width="60">Status</th>--%>
            <%--</tr>--%>
            <%--</thead>--%>
        </table>
    </div>
</div>
<script>
    $('#dataGrid').datagrid({
        method:'get',
        border:'false',
        singleSelect:true,
        fit:true,
        fitColumns:true,
        columns:[[
            {field:'name',title:'姓名',width:150,align:'center'},
            {field:'jName',title:'酒名',width:150,align:'center'},
            {field:'oTime',title:'日期',width:150,align:'center'}
        ]]
    });
    $('#addJiu').linkbutton({
        iconCls:'icon-add',
        onClick:function () {
            var divDlg = '<div id="addJiuDlg" style="text-align: center"></div>';
            $(divDlg).appendTo($("#center"));
            $('#addJiuDlg').dialog({
                title: '增加酒类型',
                width: 350,
                height: 205,
                href:getContextPath()+'/home/jiu.do',
                closed: false,
                cache: false,
                modal: true,
            });
        }
    });
    $('#addUser').linkbutton({
        iconCls:'icon-add',
        onClick:function () {
            var divDlg = '<div id="addUserDlg" style="text-align: center"></div>';
            $(divDlg).appendTo($("#center"));
            $('#addUserDlg').dialog({
                title: '增加人员',
                width: 350,
                height: 205,
                href:getContextPath()+'/home/user.do',
                closed: false,
                cache: false,
                modal: true,
            });
        }
    });


    $('#jiuCombo').combobox({
        url:getContextPath()+"/home/getJiuListApi.do",
        valueField: 'id',
        textField:'jName',
        editable:false,
        required:true,
        missingMessage:'必选项',
        invalidMessage:'必选项',
        onLoadSuccess:function (data) {
            if(data && data[0]) {
                $('#jiuCombo').combobox('select', data[0].id);
            }
        }
    });
    $('#userCombo').combobox({
        url:getContextPath()+"/home/getUserListApi.do",
        valueField: 'id',
        textField:'name',
        editable:false,
        required:true,
        missingMessage:'必选项',
        invalidMessage:'必选项'
    });
    $('#filebox').filebox({
        prompt:'请选择文件',
        required:true,
        missingMessage:'必选项',
        invalidMessage:'必选项'
    });
    $('#saveFile').linkbutton({
        iconCls:'icon-save',
        onClick:function () {
            $('#f_main').form('submit',{
                url:getContextPath()+"/home/batchInsertOrder.do",
                onSubmit:function(){
                    var flag = $(this).form('enableValidation').form('validate');
                    if(!flag){
                        $.messager.alert('我的消息','信息不完整！','info');
                    }
                    if(flag){
                        load();
                    }
                    return flag;
                },
                success:function(data){
                    disLoad();
                    $.messager.alert('我的消息','上传成功！','info');
                    $('#addJiuDlg').dialog('close');
                    $('#jiuCombo').combobox('reload',getContextPath()+"/home/getJiuListApi.do");
                }
            });
        }
    });

    //弹出加载层
    function load() {
        $("<div class=\"datagrid-mask\"></div>").css({ display: "block", width: "100%", height: $(window).height() }).appendTo("body");
        $("<div class=\"datagrid-mask-msg\"></div>").html("正在上传，请稍候...").appendTo("body").css({ display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(window).height() - 45) / 2 });
    }

    //取消加载层
    function disLoad() {
        $(".datagrid-mask").remove();
        $(".datagrid-mask-msg").remove();
    }
    $('#searchBox').textbox({
        width:400,
        prompt:'根据条形码或姓名查找...',
        icons: [{
            iconCls:'icon-search',
            handler: function(e){
                var v = $(e.data.target).textbox('getValue');
                if (v){
                    $('#dataGrid').datagrid('reload', getContextPath()+"/home/queryByCode.do?code="+v);
                }else{
                    $.messager.alert('我的消息','输入信息不能为空！','info');
                }
            }
        }]
    });
    $('#searchBox').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
            var v = $('#searchBox').textbox('getValue');
            if (v){
                $('#dataGrid').datagrid('reload', getContextPath()+"/home/queryByCode.do?code="+v);
            }else{
                $.messager.alert('我的消息','输入信息不能为空！','info');
            }
        }
    });

    function getContextPath() {
        var localObj = window.location;
        var contextPath = localObj.pathname.split("/")[1];
        return localObj.protocol + "//" + localObj.host + "/" + contextPath;
    }
</script>
</body>
</html>
